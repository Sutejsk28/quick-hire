package com.sutej.QuickHire.Dto;

import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TaskMapper {

    @Autowired


    public static TaskEntity taskRequestToEntity(TaskRequest taskRequest){
        TaskEntity task = new TaskEntity();
        task.setName(taskRequest.getName());
        task.setCategoryId(taskRequest.getCategoryId());
        task.setCreatedTime(LocalDateTime.now());
        task.setDescription(taskRequest.getDescription());
        task.setStatus(TaskStatus.NOT_ASSIGNED);
        task.setLatitude(taskRequest.getLatitude());
        task.setLongitude(taskRequest.getLongitude());
        task.setRating(taskRequest.getRating());
        return task;
    }
}
