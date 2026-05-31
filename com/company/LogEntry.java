package com.company;

public class LogEntry
{
    String dateTime;
    String level;
    String log;

    public LogEntry(String dateTime, String level, String log)
    {
        this.dateTime = dateTime;
        this.level = level;
        this.log = log;
    }

    @Override
    public String toString()
    {
        return "[" + dateTime + "] " + level + ": {" + log + "}";
    }
}