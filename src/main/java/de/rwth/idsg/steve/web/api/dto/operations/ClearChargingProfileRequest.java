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
import de.rwth.idsg.steve.web.dto.ocpp.ClearChargingProfileFilterType;
import de.rwth.idsg.steve.web.dto.ocpp.ClearChargingProfileParams;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import ocpp.cp._2015._10.ChargingProfilePurposeType;

@Getter
@Setter
public class ClearChargingProfileRequest extends AbstractMultipleChargePointRequest {

    @NotNull
    private ClearChargingProfileFilterType filterType;

    @Positive
    private Integer chargingProfilePk;

    @Min(0)
    private Integer connectorId;

    private ChargingProfilePurposeType chargingProfilePurpose;

    private Integer stackLevel;

    public ClearChargingProfileParams toParams(ChargePointHelperService helperService) {
        ClearChargingProfileParams params = new ClearChargingProfileParams();
        params.setFilterType(filterType);
        params.setChargingProfilePk(chargingProfilePk);
        params.setConnectorId(connectorId);
        params.setChargingProfilePurpose(chargingProfilePurpose);
        params.setStackLevel(stackLevel);
        apply(params, helperService);
        return params;
    }
}
