package com.sutej.QuickHire.Repository;

import com.sutej.QuickHire.Entities.TaskCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskCategoryRepository extends JpaRepository<TaskCategoryEntity,Long> {
}
