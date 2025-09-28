package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.SetChargingProfileParams;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SetChargingProfileRequest extends AbstractMultipleChargePointRequest {

    @NotNull
    @Min(0)
    private Integer connectorId;

    @NotNull
    @Positive
    private Integer chargingProfilePk;

    private Integer transactionId;

    public SetChargingProfileParams toParams(ChargePointHelperService helperService) {
        SetChargingProfileParams params = new SetChargingProfileParams();
        params.setConnectorId(connectorId);
        params.setChargingProfilePk(chargingProfilePk);
        params.setTransactionId(transactionId);
        apply(params, helperService);
        return params;
    }
}
