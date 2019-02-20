package com.jobsity.test.bowling;

import com.jobsity.test.bowling.exceptions.InconsistentDataException;
import com.jobsity.test.bowling.model.Frame;
import com.jobsity.test.bowling.model.InputRoll;
import com.jobsity.test.bowling.services.BowlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class BowlingApplication implements CommandLineRunner {

    @Autowired
    BowlingService bowlingService;

    public static void main(String[] args) {
        SpringApplication.run(BowlingApplication.class, args);
    }

    @Override
    public void run(String... args) {

        System.out.println("Response");
        System.out.println();

        Map<String, List<InputRoll>> readInputs = null;
        try {
            readInputs = this.bowlingService.readInputs();

            Map<String, List<Frame>> listMap = this.bowlingService.process(readInputs);

            this.bowlingService.processResults(listMap);
            this.bowlingService.showResults(listMap);
        } catch (IOException | InconsistentDataException e) {
           throw new RuntimeException(e);
        }

    }
}
