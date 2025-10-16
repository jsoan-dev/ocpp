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
import de.rwth.idsg.steve.web.dto.ocpp.UpdateFirmwareParams;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.joda.time.DateTime;

@Getter
@Setter
public class UpdateFirmwareRequest extends AbstractMultipleChargePointRequest {

    @NotBlank(message = "Location is required")
    private String location;

    private Integer retries;
    private Integer retryInterval;

    @NotNull(message = "retrieveTimestamp is required")
    @Future(message = "Retrieve timestamp must be in the future")
    private DateTime retrieveTimestamp;

    public UpdateFirmwareParams toParams(ChargePointHelperService helperService) {
        UpdateFirmwareParams params = new UpdateFirmwareParams();
        params.setLocation(location);
        params.setRetries(retries);
        params.setRetryInterval(retryInterval);
        params.setRetrieve(retrieveTimestamp);
        apply(params, helperService);
        return params;
    }
}
