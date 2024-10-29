package com.TaskMicro.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Data
@Slf4j
@Table(name = "trainer")
public class Trainer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    private String lastName;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "summaryduration")
    @ElementCollection
    @CollectionTable(name = "Duration_Month", joinColumns = @JoinColumn(name = "username"))
    @MapKeyColumn(name = "Mes")
    private Map<String,Integer> summaryduration;


    private static final long serialVersionUID = 1L;


}
