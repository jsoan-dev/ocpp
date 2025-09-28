package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.GetCompositeScheduleParams;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ocpp.cp._2015._10.ChargingRateUnitType;

@Getter
@Setter
public class GetCompositeScheduleRequest extends AbstractMultipleChargePointRequest {

    @NotNull
    @Min(0)
    private Integer connectorId;

    @NotNull
    @Positive
    private Integer durationInSeconds;

    private ChargingRateUnitType chargingRateUnit;

    public GetCompositeScheduleParams toParams(ChargePointHelperService helperService) {
        GetCompositeScheduleParams params = new GetCompositeScheduleParams();
        params.setConnectorId(connectorId);
        params.setDurationInSeconds(durationInSeconds);
        params.setChargingRateUnit(chargingRateUnit);
        apply(params, helperService);
        return params;
    }
}
