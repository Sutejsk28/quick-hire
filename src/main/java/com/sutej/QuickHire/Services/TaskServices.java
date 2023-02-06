package com.sutej.QuickHire.Services;

import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Repository.TaskRepository;
import lombok.Data;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
