package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.repository.dto.ChargePointSelect;
import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.MultipleChargePointSelect;

import java.util.List;

public abstract class AbstractMultipleChargePointRequest extends AbstractChargePointSelectionRequest {

    protected void apply(MultipleChargePointSelect params, ChargePointHelperService helperService) {
        List<ChargePointSelect> chargePoints = resolveChargePoints(helperService);
        params.setChargePointSelectList(chargePoints);
    }
}
