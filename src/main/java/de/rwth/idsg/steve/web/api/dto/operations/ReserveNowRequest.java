package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.ReserveNowParams;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class ReserveNowRequest extends AbstractSingleChargePointRequest {

    @NotNull
    @Min(1)
    private Integer connectorId;

    @NotNull
    @Future
    private DateTime expiry;

    @NotBlank
    private String idTag;

    public ReserveNowParams toParams(ChargePointHelperService helperService) {
        ReserveNowParams params = new ReserveNowParams();
        params.setConnectorId(connectorId);
        params.setExpiry(expiry);
        params.setIdTag(idTag);
        apply(params, helperService);
        return params;
    }
}
