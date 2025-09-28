package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.UpdateFirmwareParams;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class UpdateFirmwareRequest extends AbstractMultipleChargePointRequest {

    @NotBlank(message = "Location is required")
    private String location;

    private Integer retries;
    private Integer retryInterval;

    @NotNull(message = "retrieveTimestamp is required")
    @Future(message = "Retrieve timestamp must be in the future")
    private DateTime retrieveTimestamp;

    public UpdateFirmwareParams toParams(ChargePointHelperService helperService) {
        UpdateFirmwareParams params = new UpdateFirmwareParams();
        params.setLocation(location);
        params.setRetries(retries);
        params.setRetryInterval(retryInterval);
        params.setRetrieve(retrieveTimestamp);
        apply(params, helperService);
        return params;
    }
}
