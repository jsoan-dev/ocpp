package de.rwth.idsg.steve.web.api.dto.operations;

import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.web.dto.ocpp.SendLocalListParams;
import de.rwth.idsg.steve.web.dto.ocpp.SendLocalListUpdateType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SendLocalListRequest extends AbstractMultipleChargePointRequest {

    @NotNull
    private Integer listVersion;

    @NotNull
    private SendLocalListUpdateType updateType;

    private Boolean sendEmptyListWhenFull;
    private List<String> deleteList;
    private List<String> addUpdateList;

    public SendLocalListParams toParams(ChargePointHelperService helperService) {
        SendLocalListParams params = new SendLocalListParams();
        params.setListVersion(listVersion);
        params.setUpdateType(updateType);
        params.setSendEmptyListWhenFull(sendEmptyListWhenFull);
        params.setDeleteList(deleteList);
        params.setAddUpdateList(addUpdateList);
        apply(params, helperService);
        return params;
    }
}
