package com.company;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogAnalyzer {
    //Count entries by level
    public static Map<String, Long> countByLevel(List<LogEntry> entries) {
        return entries.stream()
                .collect(Collectors.groupingBy(e -> e.level, Collectors.counting()));
    }

    //Filter entries by level
    public static List<LogEntry> filterByLevel(List<LogEntry> entries, String level) {
        return entries.stream()
                .filter(e -> e.level.equalsIgnoreCase(level))
                .collect(Collectors.toList());
    }

    //Search by keyword in logs
    public static List<LogEntry> searchByKeyword(List<LogEntry> entries, String keyword) {
        return entries.stream()
                .filter(e -> e.log.toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    //Search by date — checks if dateTime contains the given date string
    public static List<LogEntry> searchByDate(List<LogEntry> entries, String date) {
        return entries.stream()
                .filter(e -> e.dateTime.contains(date))
                .collect(Collectors.toList());
    }

    //Top N most frequent logs
    public static Map<String, Long> topMessages(List<LogEntry> entries, int topN) {
        return entries.stream()
                .collect(Collectors.groupingBy(e -> e.log, Collectors.counting()))
                .entrySet().stream()
                .sorted((a, b) -> Long.compare(a.getValue(), b.getValue()))
                .limit(topN)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, java.util.LinkedHashMap::new));
    }


    //Lowest N appearing logs
    public static Map<String, Long> leastMessages(List<LogEntry> entries, int leastN) {
        return entries.stream()
                .collect(Collectors.groupingBy(e -> e.log, Collectors.counting()))
                .entrySet().stream()
                .sorted((b, a) -> Long.compare(a.getValue(), b.getValue()))
                .limit(leastN)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, java.util.LinkedHashMap::new));
    }
}