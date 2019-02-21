package com.jobsity.test.bowling.services;

import com.jobsity.test.bowling.model.InputRoll;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BowlingServiceImplTest {

    private File testFile1;
    private BowlingServiceImpl bowlingServiceImpl;

    @Before
    public void setup(){

        this.bowlingServiceImpl = new BowlingServiceImpl();

        ClassLoader classLoader = getClass().getClassLoader();
        testFile1 = new File(classLoader.getResource("TestFile.txt").getFile());

    }

    @Test
    public void testReadInputs() throws IOException {

        Map<String, List<InputRoll>> inputs = this.bowlingServiceImpl.readInputs(this.testFile1.getAbsolutePath());

        Assert.assertNotNull(inputs);
        Assert.assertEquals(2, inputs.size());
        Assert.assertEquals(true, inputs.containsKey("John"));
        Assert.assertEquals(true, inputs.containsKey("Jeff"));

    }

    @Test
    public void testReadProcess() throws IOException {

        Map<String, List<InputRoll>> inputs = this.bowlingServiceImpl.readInputs(this.testFile1.getAbsolutePath());

        Assert.assertNotNull(inputs);
        Assert.assertEquals(2, inputs.size());
        Assert.assertEquals(true, inputs.containsKey("John"));
        Assert.assertEquals(true, inputs.containsKey("Jeff"));

    }



}
