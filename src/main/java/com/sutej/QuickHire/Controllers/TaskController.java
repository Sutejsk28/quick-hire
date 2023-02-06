package com.sutej.QuickHire.Controllers;

import com.sutej.QuickHire.Dto.TaskRequest;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @GetMapping("/")
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) TaskStatus status,
                                                  @RequestParam(required = false) Double lat,
                                                  @RequestParam(required = false) Double lng){
        List<Task> tasks = null;

        if (status != null)
            tasks = taskServices.getAllTasksByFilter(status);
        else if(lat!=null && lng!=null) {
            tasks= taskServices.getAllNearest(lat,lng);
        }
        else
            tasks = taskServices.getAllTasks();

        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<TaskEntity> addTask(@RequestBody TaskRequest taskRequest){
        return new ResponseEntity<TaskEntity>(taskServices.createNewTask(taskRequest), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskEntity> updateStatus(@PathVariable("id") Long id, @RequestBody TaskStatus taskStatus) {
        return new ResponseEntity<TaskEntity>(taskServices.updateStatus(id,taskStatus), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskEntity> updateRating(@PathVariable("id") Long id, @RequestBody TaskRequest taskRequest) {
        return new ResponseEntity<TaskEntity>(taskServices.updateRating(id,taskRequest), HttpStatus.ACCEPTED);
    }

}
