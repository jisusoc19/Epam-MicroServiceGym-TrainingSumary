package com.TaskMicro.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "trainer")
@Setter
@Getter
@Data
@Slf4j
public class Trainer implements Serializable {

    @Id
    private String id;

    private String username;

    @Indexed
    private String firstName;

    @Indexed
    private String lastName;

    private Boolean status;


    private Map<String,Integer> summaryduration;


    private static final long serialVersionUID = 1L;


}
