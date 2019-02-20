package com.jobsity.test.bowling.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileUtil {

    public static List<String> readFile (String pathFile) throws IOException {
        File file = new File(pathFile);
        return FileUtils.readLines(file, "UTF-8");
    }
}
