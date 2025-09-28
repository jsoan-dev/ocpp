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

package de.rwth.idsg.steve.web.api.dto;

import de.rwth.idsg.steve.repository.dto.ChargePoint;
import de.rwth.idsg.steve.utils.mapper.ChargePointDetailsMapper;
import de.rwth.idsg.steve.web.dto.ChargePointForm;
import jooq.steve.db.tables.records.AddressRecord;
import jooq.steve.db.tables.records.ChargeBoxRecord;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * Lightweight DTO that exposes the same information as the MVC layer, but in a REST friendly structure.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Accessors(fluent = true)
public final class ChargePointDetailsResponse {

    private final ChargePointForm form;
    private final List<String> registrationStatusOptions;
    private final ChargeBoxRecord chargeBox;
    private final AddressRecord address;

    public static ChargePointDetailsResponse of(ChargePoint.Details details, List<String> registrationStatusOptions) {
        ChargePointForm form = ChargePointDetailsMapper.mapToForm(details);
        ChargeBoxRecord chargeBox = details.getChargeBox();
        AddressRecord address = details.getAddress();
        return new ChargePointDetailsResponse(form, registrationStatusOptions, chargeBox, address);
    }
}
