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
import de.rwth.idsg.steve.repository.UserRepository;
import de.rwth.idsg.steve.repository.dto.User;
import de.rwth.idsg.steve.service.OcppTagService;
import de.rwth.idsg.steve.utils.ControllerHelper;
import de.rwth.idsg.steve.web.api.dto.UserDetailsResponse;
import de.rwth.idsg.steve.web.api.dto.UserMetadataResponse;
import de.rwth.idsg.steve.web.dto.UserForm;
import de.rwth.idsg.steve.web.dto.UserQueryForm;
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

@Tag(name = "user-controller", description = "Operations for managing end users and their OCPP tags")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsersRestController {

    private final UserRepository userRepository;
    private final OcppTagService ocppTagService;

    @GetMapping
    public List<User.Overview> getUsers(@ParameterObject UserQueryForm params) {
        log.debug("User overview request: {}", params);
        return userRepository.getOverview(params);
    }

    @GetMapping("/{userPk}")
    public UserDetailsResponse getUser(@PathVariable("userPk") int userPk) {
        try {
            User.Details details = userRepository.getDetails(userPk);
            List<String> availableIdTags = ocppTagService.getIdTagsWithoutUser();
            return UserDetailsResponse.of(details, availableIdTags);
        } catch (SteveException e) {
            throw new SteveException.NotFound(e.getMessage());
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public UserDetailsResponse createUser(@RequestBody @Valid UserForm form) {
        log.debug("Create user request: {}", form);
        int userPk = userRepository.add(form);
        return getUser(userPk);
    }

    @PutMapping(value = "/{userPk}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserDetailsResponse updateUser(@PathVariable("userPk") int userPk, @RequestBody @Valid UserForm form) {
        form.setUserPk(userPk);
        log.debug("Update user request: {}", form);
        userRepository.update(form);
        return getUser(userPk);
    }

    @DeleteMapping("/{userPk}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userPk") int userPk) {
        log.debug("Delete user request: {}", userPk);
        userRepository.delete(userPk);
    }

    @GetMapping("/metadata")
    public UserMetadataResponse getMetadata() {
        List<String> availableIdTags = ocppTagService.getIdTagsWithoutUser();
        return new UserMetadataResponse(ControllerHelper.COUNTRY_DROPDOWN, availableIdTags);
    }
}
