# Multithreaded Log Analyzer

A CLI-based log analysis tool built in pure Java. Generates structured log files, parses them using multithreading, stores results in PostgreSQL, and provides search and analysis through an interactive terminal menu.

## What It Does

- Generates a realistic fake log file with 6000+ entries across 9 different log levels
- Parses all entries using 4 parallel threads via `ExecutorService`
- Stores parsed data into PostgreSQL using JDBC batch insert
- Provides a CLI menu with 9 analysis options — filter, search, count, and query

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Concurrency | `ExecutorService`, `Collections.synchronizedList` |
| Streams | Java 8 Stream API, Lambdas |
| Database | PostgreSQL |
| DB Connection | JDBC (`PreparedStatement`, batch insert) |
| Build Tool | Maven |

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=openjdk&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![JDBC](https://img.shields.io/badge/JDBC-Batch%20Insert-4479A1?style=for-the-badge)
![Threads](https://img.shields.io/badge/Threads-4%20Parallel-brightgreen?style=for-the-badge)
![Status](https://img.shields.io/badge/Status-Complete-success?style=for-the-badge)

---

## Project Structure

```
LogAnalyzer/
├── pom.xml
├── Screenshots/
└── com/company/
    │
    ├── Main.java                        → Entry point, CLI menu
    ├── LogEntry.java                    → Data container for one parsed log line
    ├── LogGenerator.java                → Generates fake app.log with 6000+ lines
    ├── LogReader.java                   → Reads log file line by line via BufferedReader
    ├── LogParser.java                   → Parses raw lines into LogEntry (multithreaded)
    ├── LogAnalyzer.java                 → Analysis logic using Java 8 Streams
    ├── DatabaseHandler.java             → PostgreSQL connection, batch insert, fetch, clear
    │
    ├── logs/
    │   └── app.log                      → Generated at runtime
    │
    └── textfile/
        ├── diagram&Schema/
        │   ├── erDiagram.md             → ER diagram of DB schema
        │   └── schema.md                → Table structure documentation
        │
        ├── explination/
        │   ├── ComplexLogic/
        │   │   └── Why&Neccesity.txt    → Explains confusing parts and design decisions
        │   ├── Analyzer.txt             → LogAnalyzer.java explanation
        │   ├── ConfusingParts.txt       → Questions and answers while building
        │   ├── Entry.txt                → LogEntry.java explanation
        │   ├── Generator.txt            → LogGenerator.java explanation
        │   ├── Handler.txt              → DatabaseHandler.java explanation
        │   ├── main.txt                 → Main.java explanation
        │   ├── Parser.txt               → LogParser.java explanation
        │   └── Reader.txt               → LogReader.java explanation
        │
        ├── failures/
        │   └── Failures.txt             → Bugs and errors faced during development
        │
        └── LimitAndFuture/
            ├── Future.txt               → Future roadmap
            └── Limitation.txt           → Known limitations
```

---

## Database Schema

**Table: logs**

| Column | Type | Description |
|---|---|---|
| id | SERIAL PRIMARY KEY | Auto-increment |
| log_time | VARCHAR(50) | Timestamp of log entry |
| level | VARCHAR(20) | Log level (ERROR:, WARN:, INFO:...) |
| log | TEXT | Full log message |

---

## Log Levels Supported

| Group | Levels |
|---|---|
| Low Level | `TRACE`, `DEBUG`, `SILLY` |
| High Level | `WARN`, `ALERT`, `EMERGENCY`, `FATAL`, `ERROR` |
| Contextual | `NOTICE`, `SUGGESTION`, `INFO`, `SUCCESS` |

---

## CLI Menu

```
1.  Show count by level
2.  Show all ERROR entries
3.  Show all WARN entries
4.  Search by keyword
5.  Search by date
6.  Show top 5 most frequent logs
7.  Show least 5 appearing logs
8.  Fetch ERROR entries from DB
9.  Clear DB logs
10. Exit
```

## Screenshots

<p align="center">
  <img src="Screenshots/Screenshot%20From%202026-05-30%2021-29-03.png" width="800"/>
</p>

---

## How Multithreading Works

```
6012 lines split into 4 equal chunks
│
├── Thread 0 → lines 0     to 1502
├── Thread 1 → lines 1503  to 3005
├── Thread 2 → lines 3006  to 4508
└── Thread 3 → lines 4509  to 6011
         │
         ▼
All threads write to Collections.synchronizedList
         │
         ▼
pool.awaitTermination() — waits for all 4 threads to finish
         │
         ▼
Full entries list returned to Main
```

---

## Setup & Run

**1. Clone the repo**
```bash
git clone https://github.com/iseeasta/LogAnalyzer
cd LogAnalyzer
```

**2. Create the database**
```bash
sudo -u postgres psql
```
```sql
CREATE DATABASE logdb;
```

**3. Update DB credentials in `DatabaseHandler.java`**
```java
static final String URL  = "jdbc:postgresql://localhost:5432/logdb";
static final String USER = "your_username";
static final String PASS = "your_password";
```

**4. Build and run**
```bash
mvn clean compile
mvn exec:java -Dexec.mainClass="com.company.Main"
```
---

![GitHub](https://img.shields.io/badge/GitHub-iseeasta-181717?style=for-the-badge&logo=github&logoColor=white)
![LinkedIn](https://img.shields.io/badge/LinkedIn-iseeasta-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)
![Gmail](https://img.shields.io/badge/Gmail-iseeasta@gmail.com-D14836?style=for-the-badge&logo=gmail&logoColor=white)
