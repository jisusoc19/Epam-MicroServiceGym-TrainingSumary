package com.TaskMicro.TrainerRequestDto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class TrainerRequestD {
    private String username;
    private String firstName;
    private String lastName;
    @JsonProperty("isActive")
    private boolean isActive;

    private Date trainingDate;
    private Long trainingDuration;
    private String actionType;
}

