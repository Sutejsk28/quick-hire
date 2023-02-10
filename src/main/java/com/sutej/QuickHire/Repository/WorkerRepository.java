package com.sutej.QuickHire.Repository;

import com.sutej.QuickHire.Entities.UserEntity;
import com.sutej.QuickHire.Entities.WorkerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WorkerRepository extends JpaRepository<WorkerEntity,Long> {
    Optional<WorkerEntity> findByUser(UserEntity user);
}
