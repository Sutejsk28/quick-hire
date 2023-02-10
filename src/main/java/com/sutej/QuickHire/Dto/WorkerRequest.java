package com.sutej.QuickHire.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerRequest {
    int experience;
    List<String> skillsSet;
}
