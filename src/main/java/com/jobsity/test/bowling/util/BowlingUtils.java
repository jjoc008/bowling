package com.jobsity.test.bowling.util;

import java.util.Arrays;

public class BowlingUtils {

    public static Integer getNumericValue(String value){

        if("F".equals(value)){
            return 0;
        }else if ("X".equals(value) || "/".equals(value)) {
            return 10;
        }else{
            return Integer.valueOf(value);
        }
    }

    public static Integer sumPoints(Integer ... points){

       return Arrays.asList(points).stream()
                .mapToInt(i -> i.intValue())
                .sum();

    }

}
