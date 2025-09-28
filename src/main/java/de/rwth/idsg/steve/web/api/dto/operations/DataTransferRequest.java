package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.DataTransferParams;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataTransferRequest extends AbstractMultipleChargePointRequest {

    @NotBlank
    private String vendorId;

    private String messageId;

    private String data;

    public DataTransferParams toParams(ChargePointHelperService helperService) {
        DataTransferParams params = new DataTransferParams();
        params.setVendorId(vendorId);
        params.setMessageId(messageId);
        params.setData(data);
        apply(params, helperService);
        return params;
    }
}
