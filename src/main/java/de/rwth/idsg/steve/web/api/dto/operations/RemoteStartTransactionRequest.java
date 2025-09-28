package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.RemoteStartTransactionParams;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RemoteStartTransactionRequest extends AbstractSingleChargePointRequest {

    private Integer connectorId;

    @NotBlank(message = "idTag is required")
    private String idTag;

    private Integer chargingProfilePk;

    public RemoteStartTransactionParams toParams(ChargePointHelperService helperService) {
        RemoteStartTransactionParams params = new RemoteStartTransactionParams();
        params.setConnectorId(connectorId);
        params.setIdTag(idTag);
        params.setChargingProfilePk(chargingProfilePk);
        apply(params, helperService);
        return params;
    }
}
