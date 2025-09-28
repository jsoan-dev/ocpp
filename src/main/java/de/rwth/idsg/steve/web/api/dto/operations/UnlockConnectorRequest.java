package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.UnlockConnectorParams;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnlockConnectorRequest extends AbstractSingleChargePointRequest {

    @NotNull
    @Min(1)
    private Integer connectorId;

    public UnlockConnectorParams toParams(ChargePointHelperService helperService) {
        UnlockConnectorParams params = new UnlockConnectorParams();
        params.setConnectorId(connectorId);
        apply(params, helperService);
        return params;
    }
}
