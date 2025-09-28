package de.rwth.idsg.steve.web.api;

import de.rwth.idsg.steve.repository.ChargePointRepository;
import de.rwth.idsg.steve.repository.ReservationRepository;
import de.rwth.idsg.steve.repository.ReservationStatus;
import de.rwth.idsg.steve.repository.dto.Reservation;
import de.rwth.idsg.steve.service.OcppTagService;
import de.rwth.idsg.steve.web.dto.ReservationQueryForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "reservation-controller", description = "Operations related to querying reservations")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationsRestController {

    private final ReservationRepository reservationRepository;
    private final ChargePointRepository chargePointRepository;
    private final OcppTagService ocppTagService;

    @GetMapping
    public List<Reservation> getReservations(@Valid @ParameterObject ReservationQueryForm params) {
        log.debug("Reservation query request: {}", params);
        return reservationRepository.getReservations(params);
    }

    @GetMapping("/metadata")
    public ReservationMetadataResponse getMetadata() {
        return new ReservationMetadataResponse(
            chargePointRepository.getChargeBoxIds(),
            ocppTagService.getIdTags(),
            ReservationStatus.getValues()
        );
    }

    public record ReservationMetadataResponse(List<String> chargeBoxIds,
                                               List<String> idTags,
                                               List<String> statuses) {
    }
}
