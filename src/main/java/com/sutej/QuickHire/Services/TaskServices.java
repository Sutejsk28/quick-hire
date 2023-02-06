package com.sutej.QuickHire.Services;

import com.sutej.QuickHire.Dto.TaskMapper;
import com.sutej.QuickHire.Dto.TaskRequest;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Repository.TaskRepository;
import lombok.Data;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Data
public class TaskServices {

    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        return taskRepository.findAllByOrderByCreatedTime();
    }

    public List<Task> getAllTasksByFilter(TaskStatus status) {
        return taskRepository.findAllByStatusOrderByCreatedTime(status);
    }

    public List<Task> getAllNearest(Double lat, Double lng) {
        return taskRepository.findAllOrderByLoc(lat, lng);
    }

    public TaskEntity createNewTask(TaskRequest taskRequest) {
        TaskEntity newTask = TaskMapper.taskRequestToEntity(taskRequest);
        taskRepository.save(newTask);
        return newTask;
    }


    public TaskEntity updateStatus(Long id, TaskStatus taskStatus) {
        Optional<TaskEntity> task = taskRepository.findById(id);

        if (task.isPresent()){
            throw new RuntimeException("task not found");
        }
        task.get().setStatus(taskStatus);
        return taskRepository.save(task.get());
    }

    public TaskEntity updateRating(Long id, TaskRequest taskRequest) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (!task.isPresent()){
            throw new RuntimeException("task not found");
        }
        task.get().setRating(taskRequest.getRating());
        return taskRepository.save(task.get());
    }
}
