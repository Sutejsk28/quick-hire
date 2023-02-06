package com.sutej.QuickHire.Dto;

import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.TaskStatus;

import java.time.LocalDateTime;

public class TaskMapper {
    public static TaskEntity taskRequestToEntity(TaskRequest taskRequest){
        TaskEntity task = new TaskEntity();
        task.setName(taskRequest.getName());
        task.setTaskCategory(taskRequest.getTaskCategory());
        task.setCreatedTime(LocalDateTime.now());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(TaskStatus.NOT_ASSIGN);
        task.setLatitude(taskRequest.getLatitude());
        task.setLongitude(taskRequest.getLongitude());

        return task;

    }
}
