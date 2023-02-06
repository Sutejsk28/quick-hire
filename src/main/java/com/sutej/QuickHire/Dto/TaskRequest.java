package com.sutej.QuickHire.Dto;

import com.sutej.QuickHire.Entities.TaskCategoryEntity;
import com.sutej.QuickHire.Enums.Rating;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskRequest {
    private String name;
    private String description;

    private TaskCategoryEntity taskCategory;

    private Double latitude;
    private Double longitude;

    private Rating rating;
}
