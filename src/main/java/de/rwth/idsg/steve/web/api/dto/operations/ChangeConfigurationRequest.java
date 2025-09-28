package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.ChangeConfigurationParams;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeConfigurationRequest extends AbstractMultipleChargePointRequest {

    private String configurationKey;
    private String customConfigurationKey;

    @NotNull(message = "Configuration key type is required")
    private ChangeConfigurationParams.ConfigurationKeyType keyType = ChangeConfigurationParams.ConfigurationKeyType.PREDEFINED;

    private String value;

    public ChangeConfigurationParams toParams(ChargePointHelperService helperService) {
        ChangeConfigurationParams params = new ChangeConfigurationParams();
        params.setKeyType(keyType);
        params.setConfKey(configurationKey);
        params.setCustomConfKey(customConfigurationKey);
        params.setValue(value);
        apply(params, helperService);
        return params;
    }
}
