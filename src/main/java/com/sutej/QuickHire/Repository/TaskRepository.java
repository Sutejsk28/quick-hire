package com.sutej.QuickHire.Repository;

import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.config.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    List<Task> findAllByOrderByCreatedTime();

    List<Task> findAllByStatusOrderByCreatedTime(TaskStatus status);

    @Query(value="SELECT t from Task t " +
            "ORDER BY ((t.latitude- :userLat)*(t.latitude-:userLat)) + ((t.longitude - :userLng)*(t.longitude - :userLng)) ASC,CreatedTime")
    List<Task> findAllOrderByLoc(Double userLat,Double userLng);
}
