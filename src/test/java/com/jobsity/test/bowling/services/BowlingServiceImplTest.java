package com.jobsity.test.bowling.services;

import com.jobsity.test.bowling.exceptions.InconsistentDataException;
import com.jobsity.test.bowling.model.Frame;
import com.jobsity.test.bowling.model.InputRoll;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BowlingServiceImplTest {
    private BowlingServiceImpl bowlingServiceImpl;

    @Before
    public void setup(){

        this.bowlingServiceImpl = new BowlingServiceImpl();

    }

    @Test
    public void testReadInputs() throws IOException {

        File testFile = new File("src/test/resources/TestFile.txt");

        Map<String, List<InputRoll>> inputs = this.bowlingServiceImpl.readInputs(testFile.getAbsolutePath());

        Assert.assertNotNull(inputs);
        Assert.assertEquals(2, inputs.size());
        Assert.assertEquals(true, inputs.containsKey("John"));
        Assert.assertEquals(true, inputs.containsKey("Jeff"));

    }

    @Test
    public void testReadProcess() throws IOException, InconsistentDataException {

        File testFileOnePlayer = new File("src/test/resources/TestFileOnePlayer.txt");

        Map<String, List<InputRoll>> inputs = this.bowlingServiceImpl.readInputs(testFileOnePlayer.getAbsolutePath());
        Map<String, List<Frame>> frames = this.bowlingServiceImpl.parseRolls(inputs);

        Assert.assertNotNull(inputs);
        Assert.assertEquals(1, inputs.size());
        Assert.assertEquals(true, inputs.containsKey("Jeff"));
        Assert.assertEquals("/", frames.get("Jeff").get(1).getRollTwo());
        Assert.assertEquals("X", frames.get("Jeff").get(7).getRollTwo());

    }

    @Test
    public void testProcessResults() throws IOException, InconsistentDataException {

        File testFileOnePlayer = new File("src/test/resources/TestFileOnePlayer.txt");

        Map<String, List<InputRoll>> inputs = this.bowlingServiceImpl.readInputs(testFileOnePlayer.getAbsolutePath());
        Map<String, List<Frame>> frames = this.bowlingServiceImpl.parseRolls(inputs);
        this.bowlingServiceImpl.processResults(frames);

        Assert.assertNotNull(frames);
        Assert.assertEquals(1, frames.size());
        Assert.assertEquals(true, frames.containsKey("Jeff"));
        Assert.assertEquals(10, frames.get("Jeff").size());

        Assert.assertEquals(Integer.valueOf(66), frames.get("Jeff").get(3).getScore());
        Assert.assertEquals(Integer.valueOf(120), frames.get("Jeff").get(7).getScore());
        Assert.assertEquals(Integer.valueOf(167), frames.get("Jeff").get(9).getScore());

    }

    @Test
    public void testShowResults() throws IOException, InconsistentDataException {

        String responseExpected = "Frame       1         2         3         4         5         6         7         8         9         10        \n" +
                "Jeff        \n" +
                "Pinfalls         X    7    /    9    0         X    0    8    8    /    F    6         X         X    X    8    1    \n" +
                "Score       20        39        48        66        74        84        90        120       148       167       \n";

        File testFileOnePlayer = new File("src/test/resources/TestFileOnePlayer.txt");

        Map<String, List<InputRoll>> inputs = this.bowlingServiceImpl.readInputs(testFileOnePlayer.getAbsolutePath());
        Map<String, List<Frame>> frames = this.bowlingServiceImpl.parseRolls(inputs);
        this.bowlingServiceImpl.processResults(frames);
        String response = this.bowlingServiceImpl.showResults(frames);

        Assert.assertNotNull(frames);
        Assert.assertEquals(responseExpected, response);
    }

    @Test
    public void testPerfectGame() throws IOException, InconsistentDataException {

        File testFileOnePlayer = new File("src/test/resources/TestPerfectGame.txt");

        Map<String, List<InputRoll>> inputs = this.bowlingServiceImpl.readInputs(testFileOnePlayer.getAbsolutePath());
        Map<String, List<Frame>> frames = this.bowlingServiceImpl.parseRolls(inputs);
        this.bowlingServiceImpl.processResults(frames);

        Assert.assertNotNull(frames);
        Assert.assertEquals(1, frames.size());
        Assert.assertEquals(true, frames.containsKey("Jeff"));
        Assert.assertEquals(10, frames.get("Jeff").size());

        Assert.assertEquals(Integer.valueOf(300), frames.get("Jeff").get(9).getScore());

    }

    @Test
    public void testZeroGame() throws IOException, InconsistentDataException {

        File testFileOnePlayer = new File("src/test/resources/TestZeroGame.txt");

        Map<String, List<InputRoll>> inputs = this.bowlingServiceImpl.readInputs(testFileOnePlayer.getAbsolutePath());
        Map<String, List<Frame>> frames = this.bowlingServiceImpl.parseRolls(inputs);
        this.bowlingServiceImpl.processResults(frames);

        Assert.assertNotNull(frames);
        Assert.assertEquals(1, frames.size());
        Assert.assertEquals(true, frames.containsKey("Jeff"));
        Assert.assertEquals(10, frames.get("Jeff").size());

        Assert.assertEquals(Integer.valueOf(0), frames.get("Jeff").get(9).getScore());

    }

}
