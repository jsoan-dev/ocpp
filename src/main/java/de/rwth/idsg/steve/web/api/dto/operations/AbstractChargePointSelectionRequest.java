package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.SteveException;
import de.rwth.idsg.steve.ocpp.OcppVersion;
import de.rwth.idsg.steve.repository.dto.ChargePointSelect;
import de.rwth.idsg.steve.service.ChargePointHelperService;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public abstract class AbstractChargePointSelectionRequest {

    @NotNull(message = "OCPP version is required")
    private OcppVersion version;

    @NotEmpty(message = "At least one chargeBoxId must be provided")
    private List<String> chargeBoxIds;

    protected List<ChargePointSelect> resolveChargePoints(ChargePointHelperService helperService) {
        List<ChargePointSelect> chargePoints = helperService.getChargePointsWithIds(version, chargeBoxIds);
        if (chargePoints.isEmpty()) {
            throw new SteveException.NotFound("Could not resolve any charge points for the provided identifiers");
        }
        return chargePoints;
    }
}
