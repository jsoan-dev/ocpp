package de.rwth.idsg.steve.web.api.dto;

import de.rwth.idsg.steve.ocpp.CommunicationTask;
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
    Map<String, ?> versions;
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
