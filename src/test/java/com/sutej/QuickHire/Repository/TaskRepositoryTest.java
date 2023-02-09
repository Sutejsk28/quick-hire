package com.sutej.QuickHire.Repository;

import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.Rating;
import com.sutej.QuickHire.Enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Test
    void findAllOrderByLoc() {

        //given
        LocalDateTime createdTime1 = LocalDateTime.MAX;
        TaskEntity task1 = new TaskEntity(
                2333L,
                "foo1",
                "description1",
                232L,
                433442d,
                767244d,
                createdTime1,
                TaskStatus.NOT_ASSIGNED,
                Rating.FOUR,
                null
        );
        taskRepository.save(task1);
        LocalDateTime createdTime2 = LocalDateTime.now();
        TaskEntity task2 = new TaskEntity(
                2433L,
                "foo2",
                "description2",
                3332L,
                433442d,
                767244d,
                createdTime2,
                TaskStatus.NOT_ASSIGNED,
                Rating.THREE,
                null
        );
        taskRepository.save(task2);
        LocalDateTime createdTime3 = LocalDateTime.MIN;
        TaskEntity task3 = new TaskEntity(
                56633L,
                "foo2",
                "description2",
                76632L,
                433442d,
                767244d,
                createdTime3,
                TaskStatus.NOT_ASSIGNED,
                Rating.TWO,
                null
        );
        taskRepository.save(task3);

        //when
        List<TaskEntity> tasks = taskRepository.findAllOrderByLoc(433442d,767244d);

        //then
        assertThat(tasks.get(0).getCreatedTime()).isEqualTo(task1.getCreatedTime());
        assertThat(tasks.get(1).getCreatedTime()).isEqualTo(task2.getCreatedTime());
        assertThat(tasks.get(2).getCreatedTime()).isEqualTo(task3.getCreatedTime());

    }
}