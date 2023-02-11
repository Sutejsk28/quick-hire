package com.sutej.QuickHire.Controllers;

import com.sutej.QuickHire.Dto.TaskCategoryRequest;
import com.sutej.QuickHire.Dto.TaskRequest;
import com.sutej.QuickHire.Entities.TaskCategoryEntity;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.Rating;
import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @GetMapping("/")
    public ResponseEntity<List<TaskEntity>> getAllTasks(@RequestParam(required = false) TaskStatus status,
                                                  @RequestParam(required = false) Double lat,
                                                  @RequestParam(required = false) Double lng){
        List<TaskEntity> tasks = null;

        if (status != null)
            tasks = taskServices.getAllTasksByFilter(status);
        else if(lat!=null && lng!=null) {
            tasks= taskServices.getAllNearest(lat,lng);
        }
        else
            tasks = taskServices.getAllTasks();

        return new ResponseEntity<List<TaskEntity>>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskEntity> getTaskById(@PathVariable Long id){
        TaskEntity task = taskServices.getTaskById(id);
        return new ResponseEntity<TaskEntity>(task,HttpStatus.OK);
    }

    @PostMapping("/category")
    public ResponseEntity<TaskCategoryEntity> addCategory(@RequestBody TaskCategoryRequest taskCategoryRequest){
        return new ResponseEntity<TaskCategoryEntity>(taskServices.addCategory(taskCategoryRequest), HttpStatus.ACCEPTED);
    }

    @PostMapping("/")
    public ResponseEntity<TaskEntity> addTask(@RequestBody TaskRequest taskRequest){
        return new ResponseEntity<TaskEntity>(taskServices.createNewTask(taskRequest), HttpStatus.ACCEPTED);
    }

    @PostMapping("/assign-worker")
    public ResponseEntity<TaskEntity> assignWorker(@RequestBody String workerName, Long taskId){
        return new ResponseEntity<TaskEntity>(taskServices.assignWorker(workerName, taskId), HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<TaskEntity> updateStatus(@PathVariable("id") Long id, @RequestParam String status) {
        TaskStatus taskStatus = TaskStatus.valueOf(status);
        //System.out.println(taskStatus.getName());
        return new ResponseEntity<TaskEntity>(taskServices.updateStatus(id,taskStatus), HttpStatus.ACCEPTED);
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<TaskEntity> updateRating(@PathVariable("id") Long id, @RequestParam Rating rating) {
        return new ResponseEntity<TaskEntity>(taskServices.updateRating(id,rating), HttpStatus.ACCEPTED);
    }

}
