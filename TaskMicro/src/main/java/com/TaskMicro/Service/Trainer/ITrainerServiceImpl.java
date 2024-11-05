package com.TaskMicro.Service.Trainer;

import com.TaskMicro.Entity.Trainer;
import com.TaskMicro.Repository.ITrainerRepo;
import com.TaskMicro.TrainerRequestDto.TrainerRequestDto;
import com.TaskMicro.TrainerRequestDto.TrainerRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Transactional
@Slf4j
public class ITrainerServiceImpl implements ItrainerService {

    private final ITrainerRepo trainerRepo;

    public ITrainerServiceImpl(ITrainerRepo trainerRepo) {
        this.trainerRepo = trainerRepo;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Trainer> findAll() {
        List<Trainer> lista = trainerRepo.findAll();
        if (lista == null || lista.isEmpty()) {
            log.error("Error en TrainerServiceImpl metodo findAllLista");
            throw new NullPointerException("La lista esta vacia");
        }
        log.info("Lista de Trainee Ejecutada");
        return lista;
    }


    @Override
    public Trainer save(TrainerRequestDto trainerDto) {
        Optional<Trainer> optionalTrainer = trainerRepo.findByUsername(trainerDto.getUsername());
        if (optionalTrainer.isPresent()) {
            Trainer existingTrainer = optionalTrainer.get();
            existingTrainer.setSummaryduration(SumaryCount(trainerDto));
            trainerRepo.save(existingTrainer);
            log.info("Trainer existente actualizado");
            return existingTrainer;
        }
        if (trainerDto == null || trainerDto.isActive()==false) {
            log.error("Error en TrainerServiceImpl método save: TrainerDto es nulo o inactivo");
            return null;
        }

        if (!"ADD".equals(trainerDto.getActionType())) {
            log.error("La acción a tomar es incorrecta");
            return null;
        }

        Trainer trainer = new Trainer();
        trainer.setFirstName(trainerDto.getFirstName());
        trainer.setLastName(trainerDto.getLastName());
        trainer.setStatus(trainerDto.isActive());
        trainer.setUsername(trainerDto.getUsername());
        trainerRepo.save(trainer);
        trainer.setSummaryduration(SumaryCount(trainerDto));
        trainerRepo.save(trainer);
        log.info("La acción a tomar es la correcta y el trainer ha sido guardado");
        return trainer;
    }

    @Override
    public void delete(TrainerRequestDto trainerDto) {

        if (trainerDto==null) {
            log.error("No se encontró el TRAINER con el username " + trainerDto.getUsername().toString() + ", no se puede eliminar");
            throw new NullPointerException("No hay Trainer para Borrar");

        }
        if(!"DELETE".equals(trainerDto.getActionType())){
        log.info("Trainer con el username " + trainerDto.getUsername().toString() + " no se pudo eliminar Por action incorrecta");
        }
        Trainer trainer = trainerRepo.findByUsername(trainerDto.getUsername()).orElse(null);
        trainer.setStatus(false);
        trainerRepo.save(trainer);
        log.info("Trainer desactivado correctamente");
    }

    @Override
    @Transactional(readOnly = true)
    public Trainer findByUserName(String username) {
        Trainer trainer = trainerRepo.findByUsername(username).orElse(null);
        if (trainer==null){
            log.error("No se encontro el Trainer con el username"+username);
        }
        log.info("trainer con el username"+username+"encontrado con exito");
        return trainerRepo.findByUsername(username).orElse(null);
    }

    @Override
    public HashMap<String, Integer> SumaryCount(TrainerRequestDto trainerdto) {
        if (trainerdto==null){
            log.error("esta llegnado nulo");
            return null;
        }
        Trainer trainer = trainerRepo.findByUsername(trainerdto.getUsername()).orElse(null);
        log.info(String.valueOf(trainer));
        if(trainer==null){
            log.error("no se pudo encontrar el trainer");
            throw new NullPointerException("no hay TRAINER en sumaryCount");
        }
        Map<String, Integer> monthCount = trainer.getSummaryduration();
        if (monthCount == null) {
            monthCount = new HashMap<>();
        }
        Date trainingDate = trainerdto.getTrainingDate();
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM-yyyy");

        String monthYear = monthYearFormat.format(trainingDate);
        monthCount.put(monthYear, monthCount.getOrDefault(monthYear, 0) + 1);

        return new HashMap<>(monthCount);
    }
}