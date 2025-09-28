package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.AvailabilityType;
import de.rwth.idsg.steve.web.dto.ocpp.ChangeAvailabilityParams;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeAvailabilityRequest extends AbstractMultipleChargePointRequest {

    private Integer connectorId;

    @NotNull(message = "Availability type is required")
    private AvailabilityType availabilityType;

    public ChangeAvailabilityParams toParams(ChargePointHelperService helperService) {
        ChangeAvailabilityParams params = new ChangeAvailabilityParams();
        params.setAvailType(availabilityType);
        params.setConnectorId(connectorId);
        apply(params, helperService);
        return params;
    }
}
