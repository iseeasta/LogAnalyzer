package com.company;

import java.io.*;
import java.time.LocalDateTime;
import java.util.Random;

public class LogGenerator
{
    //IOException for logs
    public static void generate(String filepath) throws IOException
    {
        //ALl these are levels that groups types of logs
        String[] lowLevels = {"TRACE", "DEBUG", "SILLY"};
        String[] highLevels = {"WARN", "ALERT", "EMERGENCY", "FATAL", "ERROR"};
        String[] contextualLevels = {"NOTICE", "SUGGESTION", "INFO", "SUCCESS"};

        //All log messages of each type of levels
        String[] traceLogs = {
                "Entering method getUserById with param id=42",
                "Loop iteration 3 of 10 in processItems()",
                "Cache lookup initiated for key=user_session_abc",
                "SQL query built: SELECT * FROM users WHERE id=?",
                "Deserializing response body to UserDTO",
                "Calling external service at endpoint /api/v1/data",
                "Thread pool queue size: 4",
                "Reading config value: db.pool.size=10",
                "Mapper converting Entity -> DTO",
                "Pagination applied: page=2, size=20",
                "Token extracted from Authorization header",
                "Filter chain processing request to /users",
                "Resolved handler: UserController.getUser()",
                "Request interceptor triggered for path=/api",
                "Connection borrowed from pool, active=3"
        };

        String[] debugLogs = {
                "User object loaded: {id=5, role=ADMIN}",
                "JWT expiry: 3600s, issued at 1716900000",
                "Database query returned 12 rows",
                "Retry attempt 2/3 for service call",
                "Cache miss for key=product_99, fetching from DB",
                "Request payload: {username='ansh', action='login'}",
                "Bean 'UserService' initialized successfully",
                "Scheduled job 'cleanupExpiredSessions' triggered",
                "File upload received: report.pdf, size=2.3MB",
                "Auth token validated, userId=7",
                "Feature flag 'new_dashboard' is enabled",
                "Email queued for delivery to user@example.com",
                "Transaction ID assigned: txn-8821aa",
                "Rate limiter: 47/100 requests used this minute",
                "Session created for userId=12, ttl=30min"
        };

        String[] sillyLogs = {
                "Yes I am actually running, I promise",
                "Null walked in... I handled it like a pro",
                "Coffee.java loaded successfully",
                "Doing the thing you asked, hold on",
                "Object created. It's alive. Alive!!",
                "No segfaults today. You're welcome",
                "Stack overflow? Not on my watch",
                "Checked the vibes. Vibes: nominal",
                "Looping again because one time was not enough",
                "Garbage collected. RIP those objects",
                "This line runs 10,000 times. You asked for it",
                "Totally not a memory leak. Totally",
                "Deploying to prod on a Friday. Bold move",
                "assertEquals passed. Somewhere a dev relaxed",
                "I have no idea what this method does either"
        };

        String[] warnLogs = {
                "Response time exceeded 2s threshold: 3.1s",
                "Deprecated API endpoint /v1/users still in use",
                "Connection pool nearing limit: 18/20 active",
                "Retrying failed request, attempt 2 of 3",
                "Config value 'timeout' not set, using default 5000ms",
                "User userId=9 has 4 failed login attempts",
                "Disk usage at 78%, consider cleanup",
                "JWT token expiring in 5 minutes for userId=3",
                "Slow query detected: 1.8s on table transactions",
                "Unrecognized field 'legacyId' in request body, ignoring",
                "External service latency high: avg 900ms",
                "Thread pool utilization at 85%",
                "Memory usage reached 75% of heap limit",
                "Fallback to secondary DB due to primary latency",
                "SSL certificate expiring in 14 days"
        };

        String[] alertLogs = {
                "CPU usage spiked to 92% on node app-server-1",
                "Database primary replica unreachable",
                "Unusual login detected from IP 203.0.113.42 for userId=5",
                "API error rate crossed 5% in last 60 seconds",
                "Scheduled backup failed to complete",
                "External payment gateway returning 503",
                "Message queue depth exceeded 10,000 items",
                "Admin account userId=1 logged in from new device",
                "Service health check failing: OrderService",
                "Redis memory usage at 90%",
                "Flood of 429 responses detected from client 10.0.0.7",
                "Disk I/O wait time critically high on db-host-2",
                "Config reload failed, running on stale config",
                "Auth service response time degraded: 4.2s avg",
                "Circuit breaker OPEN for InventoryService"
        };

        String[] emergencyLogs = {
                "System out of memory, JVM cannot allocate heap",
                "All database connections exhausted, service down",
                "Root filesystem 100% full, writes failing",
                "Critical data corruption detected in payments table",
                "Total cluster failure, no healthy nodes remaining",
                "Production secrets exposed in application logs",
                "Kernel panic detected on host db-primary",
                "Complete loss of network connectivity on all instances",
                "Unauthorized root access detected on prod server",
                "All microservices unreachable, platform is down",
                "Security breach: customer PII potentially exfiltrated",
                "Cold storage backup chain broken, recovery at risk",
                "DDoS attack saturating all inbound bandwidth",
                "Primary and failover DB both offline simultaneously",
                "Emergency shutdown triggered by watchdog process"
        };

        String[] fatalLogs = {
                "Application failed to start: missing required bean 'DataSource'",
                "Unrecoverable exception in main thread, shutting down",
                "NullPointerException in critical payment processing pipeline",
                "Stack overflow in recursive call chain, process terminating",
                "Failed to bind to port 8080: address already in use",
                "Database migration v12 failed, schema in inconsistent state",
                "Security manager blocked JVM startup due to policy violation",
                "Cannot load application properties, file missing or corrupt",
                "OutOfMemoryError: Java heap space, process killed",
                "SSL handshake failure, cannot establish secure connections",
                "Thread deadlock detected and unresolvable, halting",
                "Corrupt classpath entry preventing application bootstrap",
                "License validation failed, application will not start",
                "Critical dependency 'AuthService' unavailable at startup",
                "JVM crash due to internal error in native library"
        };

        String[] errorLogs = {
                "Failed to save order for userId=8, DB constraint violation",
                "NullPointerException at UserService.java:142",
                "HTTP 500 returned for POST /api/checkout",
                "Email delivery failed for address invalid@nodomain.xyz",
                "File not found: /uploads/invoice_2024.pdf",
                "Transaction rolled back due to concurrent modification",
                "Failed to parse date '31-13-2024' in request body",
                "Redis connection refused on host cache-01:6379",
                "OAuth token exchange failed: invalid_grant",
                "CSV import aborted: malformed row at line 204",
                "S3 upload failed for file profile_pic_userId44.jpg",
                "Timeout after 5000ms waiting for AnalyticsService",
                "Serialization error converting OrderEntity to JSON",
                "Permission denied writing to /var/log/app/output.log",
                "Unexpected HTTP 404 from internal service /inventory/check"
        };

        String[] noticeLogs = {
                "Application started on port 8080 in 3.2s",
                "User userId=15 registered successfully",
                "Scheduled maintenance window begins in 30 minutes",
                "New environment config loaded: production",
                "Admin userId=1 changed role of userId=7 to MODERATOR",
                "Database migration v11 completed successfully",
                "Feature flag 'beta_checkout' enabled for 10% of users",
                "Service restarted after planned rolling update",
                "SSL certificate renewed, valid until 2026-11-01",
                "Audit log export initiated by admin userId=2",
                "New API version v3 is now live",
                "GDPR data deletion request completed for userId=33",
                "Cron job 'archiveOldReports' completed in 14s",
                "Rate limit policy updated: 200 req/min per client",
                "System entering read-only mode for DB maintenance"
        };

        String[] suggestionLogs = {
                "Consider adding an index on column 'created_at' in orders",
                "Password for userId=6 has not changed in 90 days",
                "API response could be cached; TTL of 60s recommended",
                "UserDTO has unused field 'middleName', consider removing",
                "Enable HTTP/2 on nginx for improved multiplexing",
                "Pagination not applied to /reports endpoint, response may be large",
                "JWT secret length is below recommended 256-bit minimum",
                "Log level set to TRACE in production, consider changing to INFO",
                "Consider compressing payloads over 1MB with gzip",
                "Table 'sessions' has 2.1M rows with no archival policy",
                "Connection pool max size may be too low for peak traffic",
                "Email templates are hardcoded, consider externalizing them",
                "Dependency 'commons-lang 2.6' has a known CVE, upgrade available",
                "Profile endpoint /me runs 6 queries; a join could reduce to 1",
                "Heap allocation trending upward; consider reviewing object retention"
        };

        String[] infoLogs = {
                "Processing order orderId=1042 for userId=5",
                "User userId=3 logged in from IP 192.168.1.10",
                "Sending confirmation email to user@example.com",
                "Background job 'syncInventory' started",
                "Fetching product catalog, total items: 320",
                "Payment of ₹1,499 received for orderId=887",
                "Report generation requested by userId=12",
                "User userId=9 updated their profile",
                "Search query received: 'wireless headphones', results: 14",
                "Cart checked out by userId=6, 3 items, total ₹2,340",
                "Password reset link sent to reset@example.com",
                "Session expired for userId=22, redirecting to login",
                "New comment posted on ticket #4421",
                "Admin exported user data, count=500",
                "Webhook received from Razorpay for event=payment.captured"
        };

        String[] successLogs = {
                "User userId=4 logged in successfully",
                "Order orderId=203 placed and confirmed",
                "Password changed successfully for userId=11",
                "File uploaded: avatar_userId7.png",
                "Email verified for newuser@example.com",
                "Payment of ₹899 processed for orderId=512",
                "Profile updated successfully for userId=19",
                "Database backup completed: backup_2025-05-30.sql.gz",
                "Two-factor authentication enabled for userId=8",
                "Deployment to production completed without errors",
                "Cache warmed successfully, 240 entries loaded",
                "API key rotated successfully for clientId=88",
                "Data export for userId=5 ready for download",
                "Subscription upgraded to PRO for userId=27",
                "All 12 health checks passed, system fully operational"
        };

        //File creation then writing file
        BufferedWriter file = new BufferedWriter((new FileWriter(filepath)));
        Random rd = new Random();

        //Now,the loop will be used to write logs for particular no. of time
        for(int start = 0; start <= 500; start++)
        {
            //
            String lowLevel = lowLevels[rd.nextInt(lowLevels.length)];
            String highLevel = highLevels[rd.nextInt(highLevels.length)];
            String contextualLevel = contextualLevels[rd.nextInt(contextualLevels.length)];


            //Low Level
            String traceLog = traceLogs[rd.nextInt(traceLogs.length)];
            String debugLog = debugLogs[rd.nextInt(debugLogs.length)];
            String sillyLog = sillyLogs[rd.nextInt(sillyLogs.length)];
            //High Level
            String warnLog = warnLogs[rd.nextInt(warnLogs.length)];
            String alertLog = alertLogs[rd.nextInt(alertLogs.length)];
            String emergencyLog = emergencyLogs[rd.nextInt(emergencyLogs.length)];
            String fatalLog = fatalLogs[rd.nextInt(fatalLogs.length)];
            String errorLog = errorLogs[rd.nextInt(errorLogs.length)];
            //Conceptual Level
            String noticeLog = noticeLogs[rd.nextInt(noticeLogs.length)];
            String suggestionLog = suggestionLogs[rd.nextInt(suggestionLogs.length)];
            String infoLog = infoLogs[rd.nextInt(infoLogs.length)];
            String successLog = successLogs[rd.nextInt(successLogs.length)];


            //Current time with starting timestamp for logs
            String dateTime = LocalDateTime.now().plusMinutes(start).toString();


            //The particular format in which log will be written
            file.write("[" + dateTime + "]" + lowLevel + ": {" + traceLog + "}\n");
            file.write("[" + dateTime + "]" + highLevel + ": {" + warnLog + "}\n");
            file.write("[" + dateTime + "]" + contextualLevel + ": {" + infoLog + "}\n");
            file.write("[" + dateTime + "]" + lowLevel + ": {" + debugLog + "}\n");
            file.write("[" + dateTime + "]" + highLevel + ": {" + errorLog + "}\n");
            file.write("[" + dateTime + "]" + contextualLevel + ": {" + noticeLog + "}\n");
            file.write("[" + dateTime + "]" + lowLevel + ": {" + sillyLog + "}\n");
            file.write("[" + dateTime + "]" + highLevel + ": {" + fatalLog + "}\n");
            file.write("[" + dateTime + "]" + contextualLevel + ": {" + successLog + "}\n");
            file.write("[" + dateTime + "]" + highLevel + ": {" + alertLog + "}\n");
            file.write("[" + dateTime + "]" + contextualLevel + ": {" + suggestionLog + "}\n");
            file.write("[" + dateTime + "]" + highLevel + ": {" + emergencyLog + "}\n");


            //After,once every time the loop completes ,the next round will start after leaving 1 line
            file.newLine();
        }

        //now close file and print message and the pathfile
        file.close();
        System.out.println("Log file generated: " + filepath);
    }
}
