package com.TaskMicro.Repository;

import com.TaskMicro.Entity.Trainer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITrainerRepo extends MongoRepository<Trainer,String> {

    Optional<Trainer> findByUsername(String username);
    Boolean existsByUsername(String username);
}
