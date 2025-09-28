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

import de.rwth.idsg.steve.ocpp.CommunicationTask;
import de.rwth.idsg.steve.ocpp.OcppVersion;
import de.rwth.idsg.steve.ocpp.RequestResult;
import de.rwth.idsg.steve.ocpp.TaskOrigin;
import de.rwth.idsg.steve.web.dto.ocpp.ChargePointSelection;
import lombok.Value;
import org.joda.time.DateTime;

import java.util.Map;

@Value
public class TaskDetailsResponse {
    int taskId;
    String operationName;
    TaskOrigin origin;
    String caller;
    ChargePointSelection params;
    Map<String, RequestResult> results;
    Map<String, OcppVersion> versions;
    DateTime startTimestamp;
    DateTime endTimestamp;
    int responseCount;
    int resultSize;
    int errorCount;

    public static TaskDetailsResponse of(int taskId, CommunicationTask<? extends ChargePointSelection, ?> task) {
        return new TaskDetailsResponse(
            taskId,
            task.getOperationName(),
            task.getOrigin(),
            task.getCaller(),
            task.getParams(),
            task.getResultMap(),
            task.getVersionMap(),
            task.getStartTimestamp(),
            task.getEndTimestamp(),
            task.getResponseCount().get(),
            task.getResultSize(),
            task.getErrorCount().get()
        );
    }
}
