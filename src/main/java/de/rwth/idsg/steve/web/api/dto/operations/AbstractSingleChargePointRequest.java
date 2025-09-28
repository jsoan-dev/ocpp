package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.SteveException;
import de.rwth.idsg.steve.repository.dto.ChargePointSelect;
import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.SingleChargePointSelect;

import java.util.List;

public abstract class AbstractSingleChargePointRequest extends AbstractChargePointSelectionRequest {

    protected void apply(SingleChargePointSelect params, ChargePointHelperService helperService) {
        List<ChargePointSelect> chargePoints = resolveChargePoints(helperService);
        if (chargePoints.size() != 1) {
            throw new SteveException.BadRequest("Exactly one chargeBoxId has to be provided for this operation");
        }
        params.setChargePointSelectList(chargePoints);
    }
}
