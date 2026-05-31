package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogReader
{
    public static List<String> readLines(String filePath) throws IOException
    {
        List<String> lines = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        while ((line = br.readLine()) != null)
        {
            if (!line.trim().isEmpty())
            {
                lines.add(line);
            }
        }

        br.close();
        return lines;
    }
}