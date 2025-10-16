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

package de.rwth.idsg.steve.web.api;

import de.rwth.idsg.steve.repository.ChargePointRepository;
import de.rwth.idsg.steve.repository.ReservationRepository;
import de.rwth.idsg.steve.repository.ReservationStatus;
import de.rwth.idsg.steve.repository.dto.Reservation;
import de.rwth.idsg.steve.service.OcppTagService;
import de.rwth.idsg.steve.web.dto.ReservationQueryForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "reservation-controller", description = "Operations related to querying reservations")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationsRestController {

    private final ReservationRepository reservationRepository;
    private final ChargePointRepository chargePointRepository;
    private final OcppTagService ocppTagService;

    @GetMapping
    public List<Reservation> getReservations(@Valid @ParameterObject ReservationQueryForm params) {
        log.debug("Reservation query request: {}", params);
        return reservationRepository.getReservations(params);
    }

    @GetMapping("/metadata")
    public ReservationMetadataResponse getMetadata() {
        return new ReservationMetadataResponse(
            chargePointRepository.getChargeBoxIds(),
            ocppTagService.getIdTags(),
            ReservationStatus.getValues()
        );
    }

    public record ReservationMetadataResponse(List<String> chargeBoxIds,
                                               List<String> idTags,
                                               List<String> statuses) {
    }
}
