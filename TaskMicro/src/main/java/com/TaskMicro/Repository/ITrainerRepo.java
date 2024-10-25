package com.TaskMicro.Repository;

import com.TaskMicro.Entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ITrainerRepo extends JpaRepository<Trainer,Long> {

    Optional<Trainer> findByUsername(String username);

}
