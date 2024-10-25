package com.TaskMicro.Test.Unit;

import com.TaskMicro.Entity.Trainer;
import com.TaskMicro.Repository.ITrainerRepo;
import com.TaskMicro.Service.Trainer.ITrainerServiceImpl;
import com.TaskMicro.Service.Trainer.ItrainerService;
import com.TaskMicro.TrainerRequestDto.TrainerRequestD;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
public class ITrainerServiceImplTest {
    @InjectMocks
    private ITrainerServiceImpl trainerService;
    @Mock
    private ItrainerService trainerServicei;
    @Mock
    private ITrainerRepo trainerRepo;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testSumaryCount_Null() {
        TrainerRequestD trainerDto = null;
        HashMap<String, Integer> result = trainerService.SumaryCount(trainerDto);
        assertNull(result);
    }
    @Test
    public void testSumaryCount_ValidInput() {
        TrainerRequestD trainerDto = new TrainerRequestD();
        trainerDto.setUsername("johndoe");
        trainerDto.setTrainingDate(new Date());  // Use la fecha actual para la prueba

        Trainer trainer = new Trainer();
        trainer.setSummaryduration(new HashMap<>());

        when(trainerRepo.findByUsername("johndoe")).thenReturn(Optional.of(trainer));

        HashMap<String, Integer> result = trainerService.SumaryCount(trainerDto);
        SimpleDateFormat monthYearFormat = new SimpleDateFormat("MMMM-yyyy");
        String monthYear = monthYearFormat.format(new Date());

        assertNotNull(result);
        assertTrue(result.containsKey(monthYear));
        assertEquals(1, (int) result.get(monthYear));
    }
    @Test
    public void testFindByUsername() {
        Trainer trainer = trainerService.findByUserName(null);
        assertNull(trainer);
    }
    @Test
    public void testSave_WithNullOrInactive() {
        // Prueba con TrainerRequestD nulo
        Trainer result = trainerService.save(null);
        assertNull(result);

        // Prueba con TrainerRequestD inactivo
        TrainerRequestD inactiveTrainer = new TrainerRequestD();
        inactiveTrainer.setActive(false);
        result = trainerService.save(inactiveTrainer);
        assertNull(result);
    }
    @Test
    public void testSave_WithIncorrectAction() {
        TrainerRequestD trainerDto = new TrainerRequestD();
        trainerDto.setActive(true);
        trainerDto.setActionType("DELETE");  // Acción incorrecta

        Trainer result = trainerService.save(trainerDto);
        assertNull(result);
    }
    @Test
    public void testSave_corretaction() {
        TrainerRequestD trainerDto = new TrainerRequestD();
        trainerDto.setActive(true);
        trainerDto.setActionType("ADD");


        Exception exception = assertThrows(NullPointerException.class, () -> {
            trainerService.save(trainerDto);
        });

    }

    @Test
    void testFindAll_WithValidData() {
        Trainer trainer1 = new Trainer();
        trainer1.setUsername("johndoe");
        Trainer trainer2 = new Trainer();
        trainer2.setUsername("janedoe");

        List<Trainer> expectedTrainers = Arrays.asList(trainer1, trainer2);
        when(trainerRepo.findAll()).thenReturn(expectedTrainers);

        List<Trainer> result = trainerService.findAll();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("johndoe", result.get(0).getUsername());
        assertEquals("janedoe", result.get(1).getUsername());
    }


}


