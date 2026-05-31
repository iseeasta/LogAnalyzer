--
## Database Schema

**Table: logs**
| Column   | Type         | Description              |
|----------|--------------|--------------------------|
| id       | SERIAL PK    | Auto-increment primary key|
| log_time | VARCHAR(50)  | Timestamp of log entry   |
| level    | VARCHAR(20)  | Log level (ERROR, INFO..) |
| log      | TEXT         | Full log message          |

--