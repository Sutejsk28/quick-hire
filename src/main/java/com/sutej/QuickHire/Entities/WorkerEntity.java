package com.sutej.QuickHire.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "worker_table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long workerId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private UserEntity user;

    @NotBlank(message = "Experience cannot be blank")
    private int experience;

    @NotNull(message= "Add at least one skill set")
    private List<String> skillSet;

    @OneToMany(cascade = CascadeType.ALL)
    @Nullable
    private List<TaskEntity> tasksList;

}
