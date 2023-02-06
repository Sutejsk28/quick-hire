package com.sutej.QuickHire.Controllers;

import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Services.TaskServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/task/")
public class TaskController {

    @Autowired
    private TaskServices taskServices;

    @GetMapping("/tasks")
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
}
