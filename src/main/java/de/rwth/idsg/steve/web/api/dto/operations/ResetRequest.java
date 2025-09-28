package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.ResetParams;
import de.rwth.idsg.steve.web.dto.ocpp.ResetType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetRequest extends AbstractMultipleChargePointRequest {

    @NotNull(message = "Reset type is required")
    private ResetType resetType;

    public ResetParams toParams(ChargePointHelperService helperService) {
        ResetParams params = new ResetParams();
        params.setResetType(resetType);
        apply(params, helperService);
        return params;
    }
}
