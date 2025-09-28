package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.TriggerMessageEnum;
import de.rwth.idsg.steve.web.dto.ocpp.TriggerMessageParams;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TriggerMessageRequest extends AbstractMultipleChargePointRequest {

    @NotNull
    private TriggerMessageEnum triggerMessage;

    private Integer connectorId;

    public TriggerMessageParams toParams(ChargePointHelperService helperService) {
        TriggerMessageParams params = new TriggerMessageParams();
        params.setTriggerMessage(triggerMessage);
        params.setConnectorId(connectorId);
        apply(params, helperService);
        return params;
    }
}
