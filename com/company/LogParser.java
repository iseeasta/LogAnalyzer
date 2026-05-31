package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LogParser
{
    //Parse single line into LogEntry
    public static LogEntry parseLine(String line)
    {
        try
        {
            //Format: [2024-01-15T10:23:01]LEVEL: {log}
            int closeBrack = line.indexOf(']');

            String dateTime = line.substring(1, closeBrack).trim();
            String remaining = line.substring(closeBrack + 1).trim();

            String[] parts   = remaining.split(" ", 2);
            String level     = parts[0].trim();
            String log   = parts.length > 1 ? parts[1].trim() : "";

            return new LogEntry(dateTime, level, log);
        }
        catch (Exception e)
        {
            //If line is malformed, return as unknown
            return new LogEntry("UNKNOWN", "UNKNOWN", line);
        }
    }

    //Parse all lines — multithreaded
    public static List<LogEntry> parseAll(List<String> lines) throws InterruptedException
    {
        List<LogEntry> entries = Collections.synchronizedList(new ArrayList<>());
        int threadCount        = 4;
        int chunkSize          = lines.size() / threadCount;
        ExecutorService pool   = Executors.newFixedThreadPool(threadCount);

        for (int t = 0; t < threadCount; t++)
        {
            int start = t * chunkSize;
            int end   = (t == threadCount - 1) ? lines.size() : start + chunkSize;

            List<String> chunk = lines.subList(start, end);

            pool.submit(() ->
            {
                for (String line : chunk)
                {
                    entries.add(parseLine(line));
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.MINUTES);

        return entries;
    }
}