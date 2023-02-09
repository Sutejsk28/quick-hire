package com.sutej.QuickHire.Services;

import com.sutej.QuickHire.Dto.TaskMapper;
import com.sutej.QuickHire.Dto.TaskRequest;
import com.sutej.QuickHire.Entities.TaskEntity;
import com.sutej.QuickHire.Enums.Rating;
import com.sutej.QuickHire.Enums.TaskStatus;
import com.sutej.QuickHire.Repository.TaskCategoryRepository;
import com.sutej.QuickHire.Repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskServicesTest {

    @Mock private TaskRepository taskRepository;
    @Mock private TaskCategoryRepository taskCategoryRepository;
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
        taskServices = new TaskServices(taskRepository,taskCategoryRepository);
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
    @Disabled
    void getTaskById() {
        //given
        long id = task.getTaskId();

        //when
        taskServices.getTaskById(id);

        //then
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(long.class);
        verify(taskRepository).findById(longArgumentCaptor.capture());
        Long capturedId = longArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
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
    @Disabled
    void updateStatus() {
        //given
        Long id = task.getTaskId();
        TaskStatus status = TaskStatus.ON_GOING;

        //when
        taskServices.updateStatus(id,status);

        //then
        ArgumentCaptor<Long> idArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<TaskEntity> taskArgumentCaptor = ArgumentCaptor.forClass(task.getClass());
        verify(taskRepository).findById(idArgumentCaptor.capture());
        verify(taskRepository).save(taskArgumentCaptor.capture());
        Long capturedId = idArgumentCaptor.getValue();
        TaskEntity capturedTask = taskArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
        assertThat(capturedTask.getTaskId()).isEqualTo(task.getTaskId());
    }

    @Test
    @Disabled
    void updateRating() {
        //given
        Long id = task.getTaskId();
        Rating rating = Rating.FIVE;

        //when
        taskServices.updateRating(id,rating);

        //then
        ArgumentCaptor<Long> longArgumentCapture = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<TaskEntity> taskEntityArgumentCaptor = ArgumentCaptor.forClass(TaskEntity.class);
        verify(taskRepository).findById(longArgumentCapture.capture());
        verify(taskRepository).save(taskEntityArgumentCaptor.capture());
        Long capturedId = longArgumentCapture.getValue();
        TaskEntity capturedTaskEntity = taskEntityArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(id);
        assertThat(capturedTaskEntity).isEqualTo(task);
    }

    @Test
    void addCategory() {

    }
}