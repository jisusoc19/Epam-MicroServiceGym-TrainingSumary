package com.TaskMicro.MQ;


import com.TaskMicro.Service.Trainer.ItrainerService;
import com.TaskMicro.TrainerRequestDto.TrainerRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class MessageListener {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ItrainerService trainerService;

    public MessageListener(ItrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @JmsListener(destination = "MicroService-Trainer-ADD")
    public void listenadd(String message) {
        try{
            TrainerRequestDto trainerDto = objectMapper.readValue(message,TrainerRequestDto.class);
            trainerService.save(trainerDto);
            log.info("Received message: " + message);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }
    @JmsListener(destination = "MicroService-Trainer-DELETE")
    public void listendelete(String message) {
        try{
            TrainerRequestDto trainerDto = objectMapper.readValue(message,TrainerRequestDto.class);
            trainerService.delete(trainerDto);
            log.info("Received message: " + message);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}