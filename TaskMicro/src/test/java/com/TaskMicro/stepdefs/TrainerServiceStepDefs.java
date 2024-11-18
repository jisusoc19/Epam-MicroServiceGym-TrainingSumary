package com.TaskMicro.stepdefs;

import com.TaskMicro.Entity.Trainer;
import com.TaskMicro.Repository.ITrainerRepo;
import com.TaskMicro.Service.Trainer.ITrainerServiceImpl;
import com.TaskMicro.TrainerRequestDto.TrainerRequestDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@CucumberContextConfiguration
public class TrainerServiceStepDefs {

    @Mock
    private ITrainerRepo trainerRepo;

    @InjectMocks
    private ITrainerServiceImpl trainerService;

    private TrainerRequestDto trainerDto;
    private Trainer savedTrainer;
    private Exception capturedException;
    private TrainerRequestDto trainerRequestDto;
    private Exception expectedException;

    @Given("I have invalid trainer details")
    public void i_have_invalid_trainer_details() {
        trainerDto = new TrainerRequestDto();
        trainerDto.setUsername(null);
        trainerDto.setActive(false);
    }

    @When("I try to save the new trainer")
    public void i_try_to_save_the_new_trainer() {
        try {
            savedTrainer = trainerService.save(trainerDto);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("an error is thrown due to invalid trainer data")
    public void an_error_is_thrown_due_to_invalid_trainer_data() {
        assertNull(savedTrainer);
        assertNotNull(capturedException);
    }

    @Given("An existing trainer with a unique username")
    public void an_existing_trainer_with_a_unique_username() {
        trainerDto = new TrainerRequestDto();

        Trainer existingTrainer = new Trainer();
        existingTrainer.setUsername("existingUser");

        when(trainerRepo.findByUsername("existingUser")).thenReturn(Optional.of(existingTrainer));
    }


    @And("I have new valid details for this trainer")
    public void i_have_new_valid_details_for_this_trainer() {
        trainerDto.setFirstName("Updated");
        trainerDto.setLastName("User New");
    }

    @When("I update the trainer")
    public void i_update_the_trainer() {
        try {
            savedTrainer = trainerService.save(trainerDto);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the trainer details are updated successfully")
    public void the_trainer_details_are_updated_successfully() {
        assertNotNull(savedTrainer);
        assertEquals("Updated", savedTrainer.getFirstName());
        assertNull(capturedException);
    }

    @Given("A trainer with specific username exists")
    public void a_trainer_with_specific_username_exists() {
        when(trainerRepo.findByUsername("john.doe")).thenReturn(Optional.of(new Trainer()));
    }

    @When("I search for a trainer by username")
    public void i_search_for_a_trainer_by_username() {
        savedTrainer = trainerService.findByUserName("john.doe");
    }

    @Then("the trainer details are returned correctly")
    public void the_trainer_details_are_returned_correctly() {
        assertNotNull(savedTrainer);
        assertEquals("john.doe", savedTrainer.getUsername());
    }

    @Given("Failed to update Existing Trainer with bad Credentials")
    public void failed_to_update_existing_trainer_with_bad_credentials() {

        TrainerRequestDto trainerRequestDto = new TrainerRequestDto();
    }

    @When("user Update fail Trainer with Username wrong")
    public void user_update_fail_trainer_with_wrong_username() {
        when(trainerRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        try {
            trainerService.save(trainerRequestDto);
        } catch (IllegalArgumentException e) {
            expectedException = e;
        }
    }

    @Then("Return IllegalArgumentException")
    public void return_illegal_argument_exception() {
        assertNotNull(expectedException);
        assertTrue(expectedException instanceof IllegalArgumentException);
    }


    @Given("Failed to Delete null Trainer")
    public void failed_to_delete_null_trainer() {
        trainerRequestDto = null;
    }

    @When("Trainer Fail Delete")
    public void trainer_fail_delete() {
        try {
            trainerService.delete(trainerRequestDto);
        } catch (NullPointerException e) {
            expectedException = e;
        }
    }

    @Then("Return NullPointerException")
    public void return_null_pointer_exception() {
        assertNotNull(expectedException);
        assertTrue(expectedException instanceof NullPointerException);
    }

    @Given("Existing Trainer to delete")
    public void existing_trainer_to_delete() {
        trainerRequestDto = new TrainerRequestDto("Jeus","asdas","asdasd",true,new Date(),10l,"ADD");
        when(trainerRepo.findByUsername(trainerRequestDto.getUsername())).thenReturn(Optional.of(new Trainer()));
    }

    @When("Trainer delete by username and Status")
    public void trainer_delete_by_username_and_status() {
        trainerService.delete(trainerRequestDto);
    }

    @Then("Trainer Change Status to DELETE")
    public void trainer_change_status_to_delete() {
        verify(trainerRepo);
    }
}