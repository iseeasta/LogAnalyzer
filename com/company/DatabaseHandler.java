package com.company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler
{
    private static final String URL  = "jdbc:postgresql://localhost:5432/LogDB";
    private static final String USER = "iseeasta";
    private static final String PASS = "iseeasta@24680";

    private static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    //Create table if not exists
    public static void createTable() throws SQLException
    {
        String sql = "CREATE TABLE IF NOT EXISTS logs (" +
                "id SERIAL PRIMARY KEY, " +
                "log_time VARCHAR(50), " +
                "level VARCHAR(20), " +
                "log TEXT)";

        Connection conn  = getConnection();
        Statement  stmt  = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
        conn.close();
    }

    //Insert single entry
    //Did'nt got used anywhere or got called as it's slow.
    public static void insertEntry(LogEntry entry) throws SQLException
    {
        String sql = "INSERT INTO logs (log_time, level, log) VALUES (?, ?, ?)";

        Connection        conn = getConnection();
        PreparedStatement ps   = conn.prepareStatement(sql);

        ps.setString(1, entry.dateTime);
        ps.setString(2, entry.level);
        ps.setString(3, entry.log);
        ps.executeUpdate();

        ps.close();
        conn.close();
    }

    //Batch insert
    public static void insertAll(List<LogEntry> entries) throws SQLException
    {
        String sql = "INSERT INTO logs (log_time, level, log) VALUES (?, ?, ?)";

        Connection        conn = getConnection();
        PreparedStatement ps   = conn.prepareStatement(sql);

        conn.setAutoCommit(false);

        for (LogEntry entry : entries)
        {
            ps.setString(1, entry.dateTime);
            ps.setString(2, entry.level);
            ps.setString(3, entry.log);
            ps.addBatch();
        }

        ps.executeBatch();
        conn.commit();

        ps.close();
        conn.close();

        System.out.println("Inserted " + entries.size() + " entries into DB.");
    }

    //Fetch all entries
    //Also didn't got called by anyone as it wasn't needed.
    public static List<LogEntry> fetchAll() throws SQLException
    {
        List<LogEntry> entries = new ArrayList<>();
        String sql             = "SELECT log_time, level, log FROM logs";

        Connection conn = getConnection();
        Statement  stmt = conn.createStatement();
        ResultSet  rs   = stmt.executeQuery(sql);

        while (rs.next())
        {
            entries.add(new LogEntry(
                    rs.getString("log_time"),
                    rs.getString("level"),
                    rs.getString("log")
            ));
        }

        rs.close();
        stmt.close();
        conn.close();

        return entries;
    }

    //Fetch by level from DB
    public static List<LogEntry> fetchByLevel(String level) throws SQLException
    {
        List<LogEntry> entries = new ArrayList<>();
        String sql             = "SELECT log_time, level, log FROM logs WHERE level = ?";

        Connection        conn = getConnection();
        PreparedStatement ps   = conn.prepareStatement(sql);
        ps.setString(1, level.toUpperCase());
        ResultSet rs = ps.executeQuery();

        while (rs.next())
        {
            entries.add(new LogEntry(
                    rs.getString("log_time"),
                    rs.getString("level"),
                    rs.getString("log")
            ));
        }

        rs.close();
        ps.close();
        conn.close();

        return entries;
    }

    //Clear all logs from DB
    public static void clearLogs() throws SQLException
    {
        Connection conn = getConnection();
        Statement  stmt = conn.createStatement();
        stmt.execute("TRUNCATE TABLE logs");
        stmt.close();
        conn.close();
        System.out.println("All logs cleared from DB.");
    }
}