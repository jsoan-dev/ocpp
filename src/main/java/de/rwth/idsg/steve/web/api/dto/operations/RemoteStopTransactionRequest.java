package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.RemoteStopTransactionParams;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoteStopTransactionRequest extends AbstractSingleChargePointRequest {

    @NotNull(message = "transactionId is required")
    private Integer transactionId;

    public RemoteStopTransactionParams toParams(ChargePointHelperService helperService) {
        RemoteStopTransactionParams params = new RemoteStopTransactionParams();
        params.setTransactionId(transactionId);
        apply(params, helperService);
        return params;
    }
}
