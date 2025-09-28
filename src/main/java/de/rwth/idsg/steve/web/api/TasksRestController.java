package de.rwth.idsg.steve.web.api;

import de.rwth.idsg.steve.ocpp.CommunicationTask;
import de.rwth.idsg.steve.repository.TaskStore;
import de.rwth.idsg.steve.repository.dto.TaskOverview;
import de.rwth.idsg.steve.web.api.dto.TaskDetailsResponse;
import de.rwth.idsg.steve.web.dto.ocpp.ChargePointSelection;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "task-controller", description = "Inspect and manage asynchronous OCPP tasks")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
public class TasksRestController {

    private final TaskStore taskStore;

    @GetMapping
    public List<TaskOverview> getTasks() {
        return taskStore.getOverview();
    }

    @GetMapping("/{taskId}")
    public TaskDetailsResponse getTask(@PathVariable("taskId") Integer taskId) {
        CommunicationTask<? extends ChargePointSelection, ?> task = taskStore.get(taskId);
        return TaskDetailsResponse.of(taskId, task);
    }

    @DeleteMapping("/finished")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearFinished() {
        taskStore.clearFinished();
    }

    @DeleteMapping("/unfinished")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearUnfinished() {
        taskStore.clearUnfinished();
    }
}
