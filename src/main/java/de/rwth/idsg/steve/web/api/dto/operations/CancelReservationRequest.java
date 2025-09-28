package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.CancelReservationParams;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CancelReservationRequest extends AbstractSingleChargePointRequest {

    @NotNull
    @Min(0)
    private Integer reservationId;

    public CancelReservationParams toParams(ChargePointHelperService helperService) {
        CancelReservationParams params = new CancelReservationParams();
        params.setReservationId(reservationId);
        apply(params, helperService);
        return params;
    }
}
