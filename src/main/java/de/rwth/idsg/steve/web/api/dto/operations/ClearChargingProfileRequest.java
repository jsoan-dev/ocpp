package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.ClearChargingProfileFilterType;
import de.rwth.idsg.steve.web.dto.ocpp.ClearChargingProfileParams;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ocpp.cp._2015._10.ChargingProfilePurposeType;

@Getter
@Setter
public class ClearChargingProfileRequest extends AbstractMultipleChargePointRequest {

    @NotNull
    private ClearChargingProfileFilterType filterType;

    @Positive
    private Integer chargingProfilePk;

    @Min(0)
    private Integer connectorId;

    private ChargingProfilePurposeType chargingProfilePurpose;

    private Integer stackLevel;

    public ClearChargingProfileParams toParams(ChargePointHelperService helperService) {
        ClearChargingProfileParams params = new ClearChargingProfileParams();
        params.setFilterType(filterType);
        params.setChargingProfilePk(chargingProfilePk);
        params.setConnectorId(connectorId);
        params.setChargingProfilePurpose(chargingProfilePurpose);
        params.setStackLevel(stackLevel);
        apply(params, helperService);
        return params;
    }
}
