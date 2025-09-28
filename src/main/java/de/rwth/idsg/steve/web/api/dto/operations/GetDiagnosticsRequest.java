package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.GetDiagnosticsParams;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class GetDiagnosticsRequest extends AbstractMultipleChargePointRequest {

    @NotBlank(message = "Location is required")
    private String location;

    private Integer retries;
    private Integer retryInterval;
    private DateTime start;
    private DateTime stop;

    public GetDiagnosticsParams toParams(ChargePointHelperService helperService) {
        GetDiagnosticsParams params = new GetDiagnosticsParams();
        params.setLocation(location);
        params.setRetries(retries);
        params.setRetryInterval(retryInterval);
        params.setStart(start);
        params.setStop(stop);
        apply(params, helperService);
        return params;
    }
}
