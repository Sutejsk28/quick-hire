package com.sutej.QuickHire.Services;

import com.sutej.QuickHire.Dto.TaskCategoryRequest;
import com.sutej.QuickHire.Dto.TaskMapper;
import com.sutej.QuickHire.Dto.TaskRequest;
import com.sutej.QuickHire.Entities.TaskCategoryEntity;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Entities.UserEntity;
import com.sutej.QuickHire.Entities.WorkerEntity;
import com.sutej.QuickHire.Enums.Rating;
import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Repository.TaskCategoryRepository;
import com.sutej.QuickHire.Repository.TaskRepository;
import com.sutej.QuickHire.Repository.UserRepository;
import com.sutej.QuickHire.Repository.WorkerRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Data
public class TaskServices {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkerRepository workerRepository;
    @Autowired
    private TaskCategoryRepository taskCategoryRepository;

    public List<TaskEntity> getAllTasks() {
        return taskRepository.findAllByOrderByCreatedTime();
    }

    public List<TaskEntity> getAllTasksByFilter(TaskStatus status) {
        return taskRepository.findAllByStatusOrderByCreatedTime(status);
    }

    public List<TaskEntity> getAllNearest(Double lat, Double lng) {
        return taskRepository.findAllOrderByLoc(lat, lng);
    }

    public TaskEntity getTaskById(Long id) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (!task.isPresent()){
            throw new RuntimeException("Task not found");
        }
        return task.get();
    }

    public TaskEntity createNewTask(TaskRequest taskRequest) {
        TaskEntity newTask = TaskMapper.taskRequestToEntity(taskRequest);
        taskRepository.save(newTask);
        return newTask;
    }


    public TaskEntity updateStatus(Long id, TaskStatus taskStatus) {
        Optional<TaskEntity> task = taskRepository.findById(id);

        if (!task.isPresent()){
            throw new RuntimeException("task not found");
        }
        task.get().setStatus(taskStatus);
        return taskRepository.save(task.get());
    }

    public TaskEntity updateRating(Long id, Rating rating) {
        Optional<TaskEntity> task = taskRepository.findById(id);
        if (!task.isPresent()){
            throw new RuntimeException("task not found");
        }
        task.get().setRating(rating);
        return taskRepository.save(task.get());
    }

    public TaskCategoryEntity addCategory(TaskCategoryRequest taskCategoryRequest) {
        TaskCategoryEntity taskCategory = new TaskCategoryEntity();
        taskCategory.setCategoryName(taskCategoryRequest.getCategoryName());
        return taskCategoryRepository.save(taskCategory);
    }


    public TaskEntity assignWorker(String workerName, Long taskId) {
        UserEntity user = userRepository.findByUsername(workerName).orElseThrow();
        WorkerEntity worker = workerRepository.findByUser(user).orElseThrow();
        TaskEntity task = taskRepository.findById(taskId).orElseThrow();
        task.setAssignedWorker(worker);
        task.setStatus(TaskStatus.ON_GOING);
        taskRepository.save(task);
        worker.getTasksList().add(task);
        workerRepository.save(worker);
        return task;
    }
}
