package com.sutej.QuickHire.Repository;

import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    List<TaskEntity> findAllByOrderByCreatedTime();

    List<TaskEntity> findAllByStatusOrderByCreatedTime(TaskStatus status);

    @Query(value="SELECT t from TaskEntity t " +
            "ORDER BY ((t.latitude- :userLat)*(t.latitude-:userLat)) + ((t.longitude - :userLng)*(t.longitude - :userLng)) ASC,createdTime")
    List<TaskEntity> findAllOrderByLoc(Double userLat,Double userLng);
}
