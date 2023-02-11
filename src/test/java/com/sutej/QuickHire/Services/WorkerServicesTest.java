package com.sutej.QuickHire.Services;

import com.sutej.QuickHire.Dto.AuthenticationResponse;
import com.sutej.QuickHire.Dto.LoginRequest;
import com.sutej.QuickHire.Dto.RegisterRequest;
import com.sutej.QuickHire.Dto.WorkerRequest;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Entities.UserEntity;
import com.sutej.QuickHire.Entities.WorkerEntity;
import com.sutej.QuickHire.Enums.Rating;
import com.sutej.QuickHire.Enums.Roles;
import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Repository.TaskRepository;
import com.sutej.QuickHire.Repository.UserRepository;
import com.sutej.QuickHire.Repository.WorkerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class WorkerServicesTest {

    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AuthenticationServices authenticationServices;

    WorkerServices workerServices;
    WorkerEntity worker;
    WorkerRequest workerRequest;

    @BeforeEach
    void setUp() {
        worker = new WorkerEntity();
        worker.setUser(new UserEntity());
        worker.setExperience(2);
        List<TaskEntity> tasks = new ArrayList<>();
        worker.setTasksList(tasks);
        List<String> skills = new ArrayList<>();
        worker.setSkillSet(skills);
        workerServices = new WorkerServices(workerRepository,userRepository);
    }

    @Test
    @Disabled
    void addWorker() {

        //given
        worker = new WorkerEntity();

        worker.setExperience(2);

        List<String> skillSet = new ArrayList<>();
        skillSet.add("communication"); skillSet.add("coding");
        worker.setSkillSet(skillSet);

        UserEntity user = new UserEntity();
        user.setUsername("sutej");
        user.setEmail("sutej@123.com");
        user.setPassword("123456789");
        user.setCreatedTime(Instant.now());
        user.setAddress("Roy road");
        user.setCity("Belagavi");
        user.setCountry("India");
        user.setRoles(Roles.WORKER);
        userRepository.save(user);
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(user.getUsername());
        registerRequest.setEmail(user.getEmail());
        registerRequest.setPassword(user.getPassword());
        registerRequest.setAddress(user.getAddress());
        registerRequest.setCity(user.getCity());
        registerRequest.setCountry(user.getCountry());
        AuthenticationResponse authenticationResponse = authenticationServices.userRegistration(registerRequest);
        LoginRequest loginRequest =  new LoginRequest();
        loginRequest.setUsername(user.getUsername());
        loginRequest.setPassword(user.getPassword());
        authenticationServices.userLogin(loginRequest);

        log.debug(authenticationResponse.getAuthenticationToken());
        worker.setUser(user);

        List<TaskEntity> tasksList = new ArrayList<>(3);
        worker.setTasksList(tasksList);
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
        worker.getTasksList().add(task3);

        workerRepository.save(worker);
        WorkerRequest workerRequest = new WorkerRequest();
        workerRequest.setSkillsSet(worker.getSkillSet());
        workerRequest.setExperience(worker.getExperience());
        //when
        workerServices.addWorker(workerRequest);

        //then
        ArgumentCaptor<WorkerEntity> workerEntityArgumentCaptor = ArgumentCaptor.forClass(WorkerEntity.class);
        verify(workerRepository).save(workerEntityArgumentCaptor.capture());
        WorkerEntity capturedWorker = workerEntityArgumentCaptor.getValue();
        assertThat(capturedWorker).isEqualTo(worker);

    }

    @Test
    void getAllTasksByWorker() {
        //given
        Long id = 256L;
        when(workerRepository.findById(id)).thenReturn(Optional.ofNullable(worker));

        //when
        workerServices.getAllTasksByWorker(id);

        //then
        verify(workerRepository).findById(id);
    }
}