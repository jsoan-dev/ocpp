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
