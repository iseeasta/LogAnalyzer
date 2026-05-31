package com.company;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main
{
    static final String LOG_FILE = "app.log";

    public static void main(String[] args)
    {
        try (Scanner scAn = new Scanner(System.in))
        {
            System.out.println("========================================");
            System.out.println("       Multithreaded Log Analyzer       ");
            System.out.println("========================================");

            //Setup DB table
            DatabaseHandler.createTable();

            //Generate log file
            System.out.println("\nGenerating log file...");
            LogGenerator.generate(LOG_FILE);

            //Read lines
            System.out.println("Reading log file...");
            List<String> lines = LogReader.readLines(LOG_FILE);
            System.out.println("Total lines read: " + lines.size());

            //Parse with multithreading
            System.out.println("Parsing with 4 threads...");
            List<LogEntry> entries = LogParser.parseAll(lines);
            System.out.println("Parsing complete. Total entries: " + entries.size());

            //Insert into DB
            System.out.println("Inserting into PostgreSQL...");
            DatabaseHandler.insertAll(entries);

            //CLI Menu
            boolean running = true;

            while (running)
            {
                System.out.println("\n========================================");
                System.out.println("1.  Show count by level");
                System.out.println("2.  Show all ERROR entries");
                System.out.println("3.  Show all WARN entries");
                System.out.println("4.  Search by keyword");
                System.out.println("5.  Search by date");
                System.out.println("6.  Show top 5 most frequent log");
                System.out.println("7.  Show least 5 appearing log");
                System.out.println("8.  Fetch ERROR entries from DB");
                System.out.println("9.  Clear DB logs");
                System.out.println("10. Exit");
                System.out.print("Choose: ");

                int choice = scAn.nextInt();
                scAn.nextLine();

                switch (choice)
                {
                    case 1:
                        Map<String, Long> counts = LogAnalyzer.countByLevel(entries);
                        counts.forEach((level, count) ->
                                System.out.println(level + " : " + count));
                        break;

                    case 2:
                        List<LogEntry> errors = LogAnalyzer.filterByLevel(entries, "ERROR:");
                        errors.forEach(e -> System.out.println(e));
                        System.out.println("Total: " + errors.size());
                        break;

                    case 3:
                        List<LogEntry> warns = LogAnalyzer.filterByLevel(entries, "WARN:");
                        warns.forEach(e -> System.out.println(e));
                        System.out.println("Total: " + warns.size());
                        break;

                    case 4:
                        System.out.print("Enter keyword: ");
                        String keyword = scAn.nextLine();
                        List<LogEntry> kwResults = LogAnalyzer.searchByKeyword(entries, keyword);
                        kwResults.forEach(e -> System.out.println(e));
                        System.out.println("Total: " + kwResults.size());
                        break;

                    case 5:
                        System.out.print("Enter date (eg- 2026-05-30): ");
                        String date = scAn.nextLine();
                        List<LogEntry> dateResults = LogAnalyzer.searchByDate(entries, date);
                        dateResults.forEach(e -> System.out.println(e));
                        System.out.println("Total: " + dateResults.size());
                        break;

                    case 6:
                        Map<String, Long> top = LogAnalyzer.topMessages(entries, 5);
                        top.forEach((log, count) ->
                                System.out.println("[" + count + "x] " + log));
                        break;

                    case 7:
                        Map<String, Long> least = LogAnalyzer.leastMessages(entries, 5);
                        least.forEach((log, count) ->
                                System.out.println("[" + count + "x] " + log));
                        break;

                    case 8:
                        List<LogEntry> dbErrors = DatabaseHandler.fetchByLevel("ERROR:");
                        dbErrors.forEach(e -> System.out.println(e));
                        System.out.println("Total from DB: " + dbErrors.size());
                        break;

                    case 9:
                        DatabaseHandler.clearLogs();
                        break;

                    case 10:
                        running = false;
                        System.out.println("Exiting.");
                        break;

                    default:
                        System.out.println("Invalid choice.");
                        break;
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}