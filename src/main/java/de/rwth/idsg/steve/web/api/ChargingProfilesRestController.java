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

import de.rwth.idsg.steve.SteveException;
import de.rwth.idsg.steve.repository.ChargePointRepository;
import de.rwth.idsg.steve.repository.ChargingProfileRepository;
import de.rwth.idsg.steve.repository.dto.ChargingProfile;
import de.rwth.idsg.steve.repository.dto.ChargingProfileAssignment;
import de.rwth.idsg.steve.web.api.dto.ChargingProfileDetailsResponse;
import de.rwth.idsg.steve.web.api.dto.ChargingProfileMetadataResponse;
import de.rwth.idsg.steve.web.dto.ChargingProfileAssignmentQueryForm;
import de.rwth.idsg.steve.web.dto.ChargingProfileForm;
import de.rwth.idsg.steve.web.dto.ChargingProfileQueryForm;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "charging-profile-controller", description = "Operations for managing charging profiles")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/charging-profiles", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChargingProfilesRestController {

    private final ChargingProfileRepository chargingProfileRepository;
    private final ChargePointRepository chargePointRepository;

    @GetMapping
    public List<ChargingProfile.Overview> getProfiles(@ParameterObject ChargingProfileQueryForm params) {
        log.debug("Charging profile overview request: {}", params);
        return chargingProfileRepository.getOverview(params);
    }

    @GetMapping("/{chargingProfilePk}")
    public ChargingProfileDetailsResponse getProfile(@PathVariable("chargingProfilePk") int chargingProfilePk) {
        try {
            ChargingProfile.Details details = chargingProfileRepository.getDetails(chargingProfilePk);
            return ChargingProfileDetailsResponse.of(details);
        } catch (SteveException e) {
            throw new SteveException.NotFound(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ChargingProfileDetailsResponse createProfile(@RequestBody @Valid ChargingProfileForm form) {
        log.debug("Create charging profile request: {}", form);
        int chargingProfilePk = chargingProfileRepository.add(form);
        return getProfile(chargingProfilePk);
    }

    @PutMapping(value = "/{chargingProfilePk}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChargingProfileDetailsResponse updateProfile(@PathVariable("chargingProfilePk") int chargingProfilePk,
                                                        @RequestBody @Valid ChargingProfileForm form) {
        form.setChargingProfilePk(chargingProfilePk);
        log.debug("Update charging profile request: {}", form);
        chargingProfileRepository.update(form);
        return getProfile(chargingProfilePk);
    }

    @DeleteMapping("/{chargingProfilePk}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProfile(@PathVariable("chargingProfilePk") int chargingProfilePk) {
        log.debug("Delete charging profile request: {}", chargingProfilePk);
        chargingProfileRepository.delete(chargingProfilePk);
    }

    @GetMapping("/assignments")
    public List<ChargingProfileAssignment> getAssignments(@ParameterObject ChargingProfileAssignmentQueryForm params) {
        log.debug("Charging profile assignments request: {}", params);
        return chargingProfileRepository.getAssignments(params);
    }

    @GetMapping("/metadata")
    public ChargingProfileMetadataResponse getMetadata() {
        return new ChargingProfileMetadataResponse(
            chargingProfileRepository.getBasicInfo(),
            chargePointRepository.getChargeBoxIds()
        );
    }
}
