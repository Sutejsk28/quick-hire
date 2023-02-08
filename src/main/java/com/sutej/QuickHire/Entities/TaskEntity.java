package com.sutej.QuickHire.Entities;

import com.sutej.QuickHire.Enums.Rating;
import com.sutej.QuickHire.Enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "task_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    private String name;
    private String description;

    private Long categoryId;

    private Double latitude;
    private Double longitude;

    private LocalDateTime createdTime;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private Rating rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id")
    private WorkerEntity assignedWorker;

}
