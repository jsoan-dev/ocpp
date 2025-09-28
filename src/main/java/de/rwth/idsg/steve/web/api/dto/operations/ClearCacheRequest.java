package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.MultipleChargePointSelect;

public class ClearCacheRequest extends AbstractMultipleChargePointRequest {

    public MultipleChargePointSelect toParams(ChargePointHelperService helperService) {
        MultipleChargePointSelect params = new MultipleChargePointSelect();
        apply(params, helperService);
        return params;
    }
}
