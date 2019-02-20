package com.jobsity.test.bowling.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
    public void testReadInputs(){


        try {
            this.bowlingServiceImpl.readInputs();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
