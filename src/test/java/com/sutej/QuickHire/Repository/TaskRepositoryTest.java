package com.sutej.QuickHire.Repository;

import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Entities.UserEntity;
import com.sutej.QuickHire.Entities.WorkerEntity;
import com.sutej.QuickHire.Enums.Rating;
import com.sutej.QuickHire.Enums.Roles;
import com.sutej.QuickHire.Enums.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class TaskRepositoryTest {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    void findAllOrderByLoc() {

        //given
        UserEntity user = new UserEntity();
        user.setUsername("sutej");
        user.setEmail("sutej@123.com");
        user.setPassword("123456789");
        user.setCreatedTime(Instant.now());
        user.setAddress("Roy road");
        user.setCity("Belagavi");
        user.setCountry("India");
        user.setRoles(Roles.USER);

        userRepository.save(user);
        WorkerEntity worker = new WorkerEntity();
        worker.setTasksList(new ArrayList<>());
        worker.setSkillSet(new ArrayList<>());
        worker.setUser(user);
        worker.setExperience(2);
        workerRepository.save(worker);
        LocalDateTime createdTime1 = LocalDateTime.MAX;
        TaskEntity task1 = new TaskEntity();
        task1.setName("foo1");
        task1.setDescription("description1");
        task1.setCategoryId(232L);
        task1.setLatitude(433442d);
        task1.setLongitude(767244d);
        task1.setCreatedTime(createdTime1);
        task1.setStatus(TaskStatus.NOT_ASSIGNED);
        task1.setAssignedWorker(worker);
        task1.setRating(Rating.FOUR);
        taskRepository.save(task1);
        worker.getTasksList().add(task1);
        LocalDateTime createdTime2 = LocalDateTime.now();
        TaskEntity task2 = new TaskEntity(
        );
        task2.setName("foo2");
        task2.setDescription("description2");
        task2.setCategoryId(2433L);
        task2.setLatitude(433442d);
        task2.setLongitude(767244d);
        task2.setCreatedTime(createdTime2);
        task2.setStatus(TaskStatus.NOT_ASSIGNED);
        task2.setAssignedWorker(worker);
        task2.setRating(Rating.FIVE);
        taskRepository.save(task2);
        worker.getTasksList().add(task2);

        LocalDateTime createdTime3 = LocalDateTime.MIN;
        TaskEntity task3 = new TaskEntity();
        task3.setName("foo3");
        task3.setDescription("description3");
        task3.setCategoryId(76632L);
        task3.setLatitude(433442d);
        task3.setLongitude(767244d);
        task3.setCreatedTime(createdTime3);
        task3.setStatus(TaskStatus.NOT_ASSIGNED);
        task3.setAssignedWorker(worker);
        task3.setRating(Rating.THREE);
        taskRepository.save(task3);

        //when
        List<TaskEntity> tasks = taskRepository.findAllOrderByLoc(433442d,767244d);

        //then
        assertThat(tasks.get(0).getCreatedTime()).isEqualTo(task1.getCreatedTime());
        assertThat(tasks.get(1).getCreatedTime()).isEqualTo(task2.getCreatedTime());
        assertThat(tasks.get(2).getCreatedTime()).isEqualTo(task3.getCreatedTime());

    }
}