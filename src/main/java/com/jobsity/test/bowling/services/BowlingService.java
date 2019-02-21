package com.jobsity.test.bowling.services;

import com.jobsity.test.bowling.exceptions.InconsistentDataException;
import com.jobsity.test.bowling.model.Frame;
import com.jobsity.test.bowling.model.InputRoll;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface BowlingService {

    Map<String, List<InputRoll>> readInputs(String path) throws IOException;

    Map<String, List<Frame>> process(Map<String, List<InputRoll>> mapRolls) throws InconsistentDataException;

    void processResults(Map<String, List<Frame>> mapRolls) throws InconsistentDataException;

    void showResults(Map<String, List<Frame>> mapFrames) throws InconsistentDataException;
}
