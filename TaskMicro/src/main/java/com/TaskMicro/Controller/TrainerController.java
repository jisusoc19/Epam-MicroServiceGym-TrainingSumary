package com.TaskMicro.Controller;

import com.TaskMicro.Entity.Trainer;
import com.TaskMicro.Service.Trainer.ItrainerService;
import com.TaskMicro.TrainerRequestDto.TrainerRequestD;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TrainerController {
    private ItrainerService traineService;

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
    public ResponseEntity<?> save(@RequestBody TrainerRequestD trainerdto){
        Trainer trainer = traineService.save(trainerdto);
        if (trainer == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al guardar el trainer");
        }
        return ResponseEntity.ok(trainer);

    }

}
