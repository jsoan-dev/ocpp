package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.GetConfigurationParams;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetConfigurationRequest extends AbstractMultipleChargePointRequest {

    private List<String> configurationKeys;
    private List<String> customConfigurationKeys;

    public GetConfigurationParams toParams(ChargePointHelperService helperService) {
        GetConfigurationParams params = new GetConfigurationParams();
        params.setConfKeyList(configurationKeys);
        if (customConfigurationKeys != null && !customConfigurationKeys.isEmpty()) {
            params.setCommaSeparatedCustomConfKeys(String.join(",", customConfigurationKeys));
        }
        apply(params, helperService);
        return params;
    }
}
