package com.sutej.QuickHire.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private UserEntity user;

    @NotBlank(message = "Experience cannor be blank")
    private int experience;

    @OneToMany
    @NotNull(message= "Add at least one skill set")
    private List<String> skillSet;

}
