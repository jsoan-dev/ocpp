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

import de.rwth.idsg.steve.ocpp.OcppVersion;
import de.rwth.idsg.steve.repository.dto.ChargePointSelect;
import de.rwth.idsg.steve.service.ChargePointHelperService;
import de.rwth.idsg.steve.service.ChargePointServiceClient;
import de.rwth.idsg.steve.web.api.dto.operations.CancelReservationRequest;
import de.rwth.idsg.steve.web.api.dto.operations.ChangeAvailabilityRequest;
import de.rwth.idsg.steve.web.api.dto.operations.ChangeConfigurationRequest;
import de.rwth.idsg.steve.web.api.dto.operations.ClearCacheRequest;
import de.rwth.idsg.steve.web.api.dto.operations.ClearChargingProfileRequest;
import de.rwth.idsg.steve.web.api.dto.operations.DataTransferRequest;
import de.rwth.idsg.steve.web.api.dto.operations.GetCompositeScheduleRequest;
import de.rwth.idsg.steve.web.api.dto.operations.GetConfigurationRequest;
import de.rwth.idsg.steve.web.api.dto.operations.GetDiagnosticsRequest;
import de.rwth.idsg.steve.web.api.dto.operations.GetLocalListVersionRequest;
import de.rwth.idsg.steve.web.api.dto.operations.OperationEnqueueResponse;
import de.rwth.idsg.steve.web.api.dto.operations.OperationsChargePointMetadata;
import de.rwth.idsg.steve.web.api.dto.operations.RemoteStartTransactionRequest;
import de.rwth.idsg.steve.web.api.dto.operations.RemoteStopTransactionRequest;
import de.rwth.idsg.steve.web.api.dto.operations.ReserveNowRequest;
import de.rwth.idsg.steve.web.api.dto.operations.ResetRequest;
import de.rwth.idsg.steve.web.api.dto.operations.SendLocalListRequest;
import de.rwth.idsg.steve.web.api.dto.operations.SetChargingProfileRequest;
import de.rwth.idsg.steve.web.api.dto.operations.TriggerMessageRequest;
import de.rwth.idsg.steve.web.api.dto.operations.UnlockConnectorRequest;
import de.rwth.idsg.steve.web.api.dto.operations.UpdateFirmwareRequest;
import de.rwth.idsg.steve.web.dto.ocpp.MultipleChargePointSelect;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "operations-controller", description = "Expose OCPP remote operations through the REST API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/operations", produces = MediaType.APPLICATION_JSON_VALUE)
public class OperationsRestController {

    private final ChargePointServiceClient chargePointServiceClient;
    private final ChargePointHelperService chargePointHelperService;

    @GetMapping("/metadata/charge-points")
    public List<OperationsChargePointMetadata> getChargePointMetadata() {
        return EnumSet.allOf(OcppVersion.class).stream()
            .map(version -> new OperationsChargePointMetadata(version, extractChargeBoxIds(version)))
            .toList();
    }

    @PostMapping(value = "/change-availability", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse changeAvailability(@RequestBody @Valid ChangeAvailabilityRequest request) {
        int taskId = chargePointServiceClient.changeAvailability(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/change-configuration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse changeConfiguration(@RequestBody @Valid ChangeConfigurationRequest request) {
        int taskId = chargePointServiceClient.changeConfiguration(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/clear-cache", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse clearCache(@RequestBody @Valid ClearCacheRequest request) {
        MultipleChargePointSelect params = request.toParams(chargePointHelperService);
        int taskId = chargePointServiceClient.clearCache(params);
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/get-diagnostics", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse getDiagnostics(@RequestBody @Valid GetDiagnosticsRequest request) {
        int taskId = chargePointServiceClient.getDiagnostics(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse reset(@RequestBody @Valid ResetRequest request) {
        int taskId = chargePointServiceClient.reset(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/update-firmware", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse updateFirmware(@RequestBody @Valid UpdateFirmwareRequest request) {
        int taskId = chargePointServiceClient.updateFirmware(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/remote-start-transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse remoteStartTransaction(@RequestBody @Valid RemoteStartTransactionRequest request) {
        int taskId = chargePointServiceClient.remoteStartTransaction(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/remote-stop-transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse remoteStopTransaction(@RequestBody @Valid RemoteStopTransactionRequest request) {
        int taskId = chargePointServiceClient.remoteStopTransaction(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/reserve-now", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse reserveNow(@RequestBody @Valid ReserveNowRequest request) {
        int taskId = chargePointServiceClient.reserveNow(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/cancel-reservation", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse cancelReservation(@RequestBody @Valid CancelReservationRequest request) {
        int taskId = chargePointServiceClient.cancelReservation(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/unlock-connector", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse unlockConnector(@RequestBody @Valid UnlockConnectorRequest request) {
        int taskId = chargePointServiceClient.unlockConnector(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/trigger-message", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse triggerMessage(@RequestBody @Valid TriggerMessageRequest request) {
        int taskId = chargePointServiceClient.triggerMessage(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/set-charging-profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse setChargingProfile(@RequestBody @Valid SetChargingProfileRequest request) {
        int taskId = chargePointServiceClient.setChargingProfile(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/clear-charging-profile", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse clearChargingProfile(@RequestBody @Valid ClearChargingProfileRequest request) {
        int taskId = chargePointServiceClient.clearChargingProfile(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/get-composite-schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse getCompositeSchedule(@RequestBody @Valid GetCompositeScheduleRequest request) {
        int taskId = chargePointServiceClient.getCompositeSchedule(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/data-transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse dataTransfer(@RequestBody @Valid DataTransferRequest request) {
        int taskId = chargePointServiceClient.dataTransfer(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/get-configuration", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse getConfiguration(@RequestBody @Valid GetConfigurationRequest request) {
        int taskId = chargePointServiceClient.getConfiguration(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/get-local-list-version", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse getLocalListVersion(@RequestBody @Valid GetLocalListVersionRequest request) {
        int taskId = chargePointServiceClient.getLocalListVersion(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    @PostMapping(value = "/send-local-list", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OperationEnqueueResponse sendLocalList(@RequestBody @Valid SendLocalListRequest request) {
        int taskId = chargePointServiceClient.sendLocalList(request.toParams(chargePointHelperService));
        return new OperationEnqueueResponse(taskId);
    }

    private List<String> extractChargeBoxIds(OcppVersion version) {
        List<ChargePointSelect> chargePoints = chargePointHelperService.getChargePoints(version);
        return chargePoints.stream().map(ChargePointSelect::getChargeBoxId).sorted().collect(Collectors.toList());
    }
}
