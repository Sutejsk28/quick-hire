package com.sutej.QuickHire.Services;

import com.sutej.QuickHire.Dto.TaskMapper;
import com.sutej.QuickHire.Dto.TaskRequest;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.Rating;
import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Repository.TaskCategoryRepository;
import com.sutej.QuickHire.Repository.TaskRepository;
import com.sutej.QuickHire.Repository.UserRepository;
import com.sutej.QuickHire.Repository.WorkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServicesTest {

    @Mock private TaskRepository taskRepository;
    @Mock private TaskCategoryRepository taskCategoryRepository;
    @Mock private UserRepository userRepository;
    @Mock private WorkerRepository workerRepository;
    @Autowired TaskEntity task;
    TaskServices taskServices;

    @BeforeEach
    void setUp() {
        task = new TaskEntity(
                2333L,
                "foo1",
                "description1",
                232L,
                433442d,
                767244d,
                LocalDateTime.now(),
                TaskStatus.NOT_ASSIGNED,
                Rating.FOUR,
                null
        );
        taskServices = new TaskServices(taskRepository,userRepository,workerRepository,taskCategoryRepository);
    }

    @Test
    void getAllTasks() {
        //when
        taskServices.getAllTasks();
        //then
        verify(taskRepository).findAllByOrderByCreatedTime();
    }

    @Test
    void getAllTasksByFilter() {

        //given
        TaskStatus status = TaskStatus.NOT_ASSIGNED;

        //when
        taskServices.getAllTasksByFilter(status);

        //then
        ArgumentCaptor<TaskStatus> statusArgumentCaptor = ArgumentCaptor.forClass(TaskStatus.class);
        verify(taskRepository).findAllByStatusOrderByCreatedTime(statusArgumentCaptor.capture());
        TaskStatus capturedStatus = statusArgumentCaptor.getValue();
        assertThat(capturedStatus).isEqualTo(status);
    }

    @Test
    void getAllNearest() {
        //given
        Double lat=433442d,lng=767244d;

        //when
        taskServices.getAllNearest(lat,lng);

        //then
        ArgumentCaptor<Double> latArgumentCaptor = ArgumentCaptor.forClass(lat.getClass());
        ArgumentCaptor<Double> lngArgumentCaptor = ArgumentCaptor.forClass(lng.getClass());
        verify(taskRepository).findAllOrderByLoc(latArgumentCaptor.capture(),lngArgumentCaptor.capture());
        Double capturedLat = latArgumentCaptor.getValue();
        Double capturedLng = lngArgumentCaptor.getValue();
        assertThat(capturedLat).isEqualTo(lat);
        assertThat(capturedLng).isEqualTo(lng);
    }

    @Test
    void getTaskById() {
        //given
        Long id = 201L;
        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));

        //when
        taskServices.getTaskById(id);

        //then
        verify(taskRepository).findById(id);
    }

    @Test
    void createNewTask() {
        //given
        LocalDateTime createdTime1 = LocalDateTime.MAX;
        TaskRequest task = new TaskRequest(
                "foo1",
                "description1",
                232L,
                433442d,
                767244d,
                Rating.FOUR
        );

        //when
        taskServices.createNewTask(task);
        TaskEntity newTask = TaskMapper.taskRequestToEntity(task);

        //then
        ArgumentCaptor<TaskEntity> newTaskArgumentCaptor = ArgumentCaptor.forClass(newTask.getClass());
        verify(taskRepository).save(newTaskArgumentCaptor.capture());
        TaskEntity capturedTask = newTaskArgumentCaptor.getValue();
        assertThat(capturedTask.getTaskId()).isEqualTo(newTask.getTaskId());
    }

    @Test
    void updateStatus() {
        //given
        Long id = 201L;
        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(task)).thenReturn(task);
        TaskStatus status = TaskStatus.ON_GOING;

        //when
        taskServices.updateStatus(id,status);

        //then
        verify(taskRepository).save(task);
    }

    @Test
    void updateRating() {
        //given
        Long id = 201L;
        when(taskRepository.findById(id)).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(task)).thenReturn(task);
        Rating rating = Rating.FIVE;

        //when
        taskServices.updateRating(id,rating);

        //then
        verify(taskRepository).save(task);
    }

    @Test
    void addCategory() {

    }
}