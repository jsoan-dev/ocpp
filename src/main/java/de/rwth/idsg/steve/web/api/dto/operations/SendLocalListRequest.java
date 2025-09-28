/*
 * SteVe - SteckdosenVerwaltung - https://github.com/steve-community/steve
 * Copyright (C) 2013-2025 SteVe Community Team
 * All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
