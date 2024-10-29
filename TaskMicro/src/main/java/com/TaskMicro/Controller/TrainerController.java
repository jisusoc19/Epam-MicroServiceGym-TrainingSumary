package com.TaskMicro.Controller;

import com.TaskMicro.Entity.Trainer;
import com.TaskMicro.Service.Trainer.ItrainerService;
import com.TaskMicro.TrainerRequestDto.TrainerRequestDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainerController {
    private final ItrainerService traineService;



    public TrainerController(ItrainerService traineService) {
        this.traineService = traineService;
    }

    @GetMapping("/trainer")
    public ResponseEntity<List<Trainer>> getAll(){
        List<Trainer> trainer = traineService.findAll();
        return ResponseEntity.ok(trainer);

    }
    @GetMapping("/trainer/{username}")
    public ResponseEntity<Trainer> getUserName(@PathVariable String username){
        Trainer trainer = traineService.findByUserName(username);
        return ResponseEntity.ok((Trainer) trainer);

    }

    @PostMapping("/trainer")
    public ResponseEntity<?> save(@RequestBody TrainerRequestDto trainerdto){
        Trainer trainer = traineService.save(trainerdto);
        if (trainer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el trainer");
        }
        return ResponseEntity.ok(trainer);

    }
    @DeleteMapping("/trainer")
    public ResponseEntity<?> delete(@RequestBody TrainerRequestDto trainerDto){

        if (trainerDto==null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al borrar el trainer");
        }
        traineService.delete(trainerDto);
        return ResponseEntity.ok("Username Borrado " + trainerDto.getUsername() +" con exito ");

    }


}
