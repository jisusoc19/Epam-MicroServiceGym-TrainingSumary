package com.TaskMicro.Service.Trainer;

import com.TaskMicro.Entity.Trainer;
import com.TaskMicro.TrainerRequestDto.TrainerRequestD;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface ItrainerService {
    public List<Trainer> findAll();
    Trainer save(TrainerRequestD trainerDto);
    public void delete(String username,String actionType);
    public Trainer findByUserName(String username);
    public Map<String,Integer> SumaryCount( TrainerRequestD trainerdto);
}
