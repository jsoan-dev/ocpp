package de.rwth.idsg.steve.web.api;

import de.rwth.idsg.steve.ocpp.OcppProtocol;
import de.rwth.idsg.steve.ocpp.OcppVersion;
import de.rwth.idsg.steve.repository.ChargePointRepository;
import de.rwth.idsg.steve.repository.dto.ChargePoint;
import de.rwth.idsg.steve.repository.dto.ConnectorStatus;
import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.service.ChargePointRegistrationService;
import de.rwth.idsg.steve.service.dto.UnidentifiedIncomingObject;
import de.rwth.idsg.steve.utils.ControllerHelper;
import de.rwth.idsg.steve.web.api.dto.ChargePointDetailsResponse;
import de.rwth.idsg.steve.web.api.dto.ChargePointMetadataResponse;
import de.rwth.idsg.steve.web.dto.ChargePointForm;
import de.rwth.idsg.steve.web.dto.ChargePointQueryForm;
import de.rwth.idsg.steve.web.dto.ConnectorStatusForm;
import de.rwth.idsg.steve.web.dto.OcppJsonStatus;
import de.rwth.idsg.steve.web.dto.Statistics;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jooq.steve.db.tables.records.ChargeBoxRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ocpp.cs._2015._10.RegistrationStatus;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Tag(name = "charge-point-controller",
    description = "Operations related to managing charge points, their metadata and runtime status.")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/charge-points", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChargePointsRestController {

    private final ChargePointRepository chargePointRepository;
    private final ChargePointRegistrationService chargePointRegistrationService;
    private final ChargePointHelperService chargePointHelperService;

    private static final List<String> UP_TO_OCPP15_REGISTRATION_STATUS_LIST = Arrays.stream(ocpp.cs._2012._06.RegistrationStatus.values())
        .map(ocpp.cs._2012._06.RegistrationStatus::value)
        .toList();

    private static final List<String> OCPP16_REGISTRATION_STATUS_LIST = Arrays.stream(RegistrationStatus.values())
        .map(RegistrationStatus::value)
        .toList();

    @GetMapping
    public List<ChargePoint.Overview> getChargePoints(@ParameterObject ChargePointQueryForm params) {
        log.debug("Charge point overview request: {}", params);
        List<ChargePoint.Overview> overview = chargePointRepository.getOverview(params);
        log.debug("Charge point overview result: {} entries", overview.size());
        return overview;
    }

    @GetMapping("/{chargeBoxPk}")
    public ChargePointDetailsResponse getChargePoint(@PathVariable("chargeBoxPk") int chargeBoxPk) {
        ChargePoint.Details details = chargePointRepository.getDetails(chargeBoxPk);
        List<String> registrationStatusList = getRegistrationStatusList(details.getChargeBox());
        return ChargePointDetailsResponse.of(details, registrationStatusList);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ChargePointDetailsResponse createChargePoint(@RequestBody @Valid ChargePointForm form) {
        log.debug("Create charge point request: {}", form);
        int chargeBoxPk = chargePointRepository.addChargePoint(form);
        chargePointRegistrationService.removeUnknown(Collections.singletonList(form.getChargeBoxId()));
        ChargePoint.Details details = chargePointRepository.getDetails(chargeBoxPk);
        return ChargePointDetailsResponse.of(details, getRegistrationStatusList(details.getChargeBox()));
    }

    @PutMapping(value = "/{chargeBoxPk}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChargePointDetailsResponse updateChargePoint(@PathVariable("chargeBoxPk") int chargeBoxPk,
                                                        @RequestBody @Valid ChargePointForm form) {
        form.setChargeBoxPk(chargeBoxPk);
        log.debug("Update charge point request: {}", form);
        chargePointRepository.updateChargePoint(form);
        ChargePoint.Details details = chargePointRepository.getDetails(chargeBoxPk);
        return ChargePointDetailsResponse.of(details, getRegistrationStatusList(details.getChargeBox()));
    }

    @DeleteMapping("/{chargeBoxPk}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteChargePoint(@PathVariable("chargeBoxPk") int chargeBoxPk) {
        log.debug("Delete charge point request: {}", chargeBoxPk);
        chargePointRepository.deleteChargePoint(chargeBoxPk);
    }

    @GetMapping("/unknown")
    public List<String> getUnknownChargePoints() {
        return getUnknownChargeBoxIds();
    }

    @PostMapping(value = "/unknown", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<String> promoteUnknownChargePoints(@RequestBody @NotEmpty List<String> chargeBoxIds) {
        log.debug("Promote unknown charge points request: {}", chargeBoxIds);
        chargePointRepository.addChargePointList(chargeBoxIds);
        chargePointRegistrationService.removeUnknown(chargeBoxIds);
        return chargeBoxIds;
    }

    @DeleteMapping("/unknown/{chargeBoxId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUnknownChargePoint(@PathVariable("chargeBoxId") String chargeBoxId) {
        log.debug("Remove unknown charge point request: {}", chargeBoxId);
        chargePointRegistrationService.removeUnknown(Collections.singletonList(chargeBoxId));
    }

    @GetMapping("/connector-status")
    public List<ConnectorStatus> getConnectorStatus(@ParameterObject ConnectorStatusForm form) {
        return chargePointHelperService.getChargePointConnectorStatus(form);
    }

    @GetMapping("/json-status")
    public List<OcppJsonStatus> getJsonStatus() {
        return chargePointHelperService.getOcppJsonStatus();
    }

    @GetMapping("/statistics")
    public Statistics getStatistics() {
        return chargePointHelperService.getStats();
    }

    @GetMapping("/metadata")
    public ChargePointMetadataResponse getMetadata() {
        List<String> unknown = getUnknownChargeBoxIds();
        return new ChargePointMetadataResponse(
            ControllerHelper.COUNTRY_DROPDOWN,
            UP_TO_OCPP15_REGISTRATION_STATUS_LIST,
            OCPP16_REGISTRATION_STATUS_LIST,
            UP_TO_OCPP15_REGISTRATION_STATUS_LIST,
            unknown
        );
    }

    @GetMapping("/protocols")
    public List<OcppVersion> getSupportedProtocols() {
        return List.of(OcppVersion.V_12, OcppVersion.V_15, OcppVersion.V_16);
    }

    private List<String> getRegistrationStatusList(ChargeBoxRecord chargeBoxRecord) {
        if (chargeBoxRecord.getOcppProtocol() == null) {
            return UP_TO_OCPP15_REGISTRATION_STATUS_LIST;
        }

        OcppProtocol protocol = OcppProtocol.fromCompositeValue(chargeBoxRecord.getOcppProtocol());
        return switch (protocol.getVersion()) {
            case V_12, V_15 -> UP_TO_OCPP15_REGISTRATION_STATUS_LIST;
            case V_16 -> OCPP16_REGISTRATION_STATUS_LIST;
            default -> throw new IllegalArgumentException("Unknown OCPP version: " + protocol.getVersion());
        };
    }

    private List<String> getUnknownChargeBoxIds() {
        return chargePointRegistrationService.getUnknownChargePoints().stream()
            .map(UnidentifiedIncomingObject::getKey)
            .toList();
    }
}
