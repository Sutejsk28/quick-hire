package com.sutej.QuickHire.Controllers;

import com.sutej.QuickHire.Dto.WorkerRequest;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Entities.WorkerEntity;
import com.sutej.QuickHire.Services.WorkerServices;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worker")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerController {

    @Autowired
    private WorkerServices workerServices;

    @PostMapping("/")
    public ResponseEntity<WorkerEntity> addWorker(@RequestBody WorkerRequest workerRequest){
        return new ResponseEntity<WorkerEntity>(workerServices.addWorker(workerRequest), HttpStatus.OK);
    }

    @GetMapping("/all-tasks")
    public ResponseEntity<List<TaskEntity>> getAllTasksByWorker(){
        return new ResponseEntity<List<TaskEntity>>(workerServices.getAllTasksByWorker(), HttpStatus.OK);
    }

}
