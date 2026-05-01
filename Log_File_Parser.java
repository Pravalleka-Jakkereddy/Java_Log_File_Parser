import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

class LogFileParser 
{
	static final String FILE_NAME = "logs.txt";

    	static final String[] LOG_LEVELS = {"INFO", "WARNING", "ERROR"};

    	static final String[] MESSAGES = 
	{
        	"User logged in",
            	"File not found",
            	"Disk space running low",
            	"Connection established",
            	"Application started",
            	"Unexpected error occurred",
            	"Database connection failed"
    	};

    	static Scanner sc = new Scanner(System.in);

    // ---------------- MAIN ----------------
    	public static void main(String[] args) 
	{

        	while (true) 
		{
            		System.out.println("\n===== Log File Parser =====");
            		System.out.println("1. Generate Log");
            		System.out.println("2. View Logs");
            		System.out.println("3. Filter Logs by Level");
            		System.out.println("4. Search Logs");
            		System.out.println("5. Log Statistics");
            		System.out.println("6. Exit");
            		System.out.print("Choose an option: ");

            		int choice;
            		try 
			{
                		choice = Integer.parseInt(sc.nextLine());
            		} 
			catch (Exception e) 
			{
                		System.out.println("Invalid input!");
                		continue;
            		}

            switch (choice) {
                case 1:
                    generateLog();
                    break;
                case 2:
                    viewLogs();
                    break;
                case 3:
                    filterLogsByLevel();
                    break;
                case 4:
                    searchLogs();
                    break;
                case 5:
                    logStatistics();
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // ---------------- GENERATE LOG ----------------
    public static void generateLog() {
        try {
            // Timestamp
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = now.format(formatter);

            // Random selection
            Random random = new Random();
            String level = LOG_LEVELS[random.nextInt(LOG_LEVELS.length)];
            String message = MESSAGES[random.nextInt(MESSAGES.length)];

            // Combine
            String logEntry = timestamp + " [" + level + "] " + message;

            // Write to file
            FileWriter writer = new FileWriter(FILE_NAME, true);
            writer.write(logEntry + "\n");
            writer.close();

            System.out.println("New log entry added!");

        } catch (IOException e) {
            System.out.println("Error writing to log file.");
        }
    }

    // ---------------- VIEW LOGS ----------------
    public static void viewLogs() {
        try {
            List<String> logs = Files.readAllLines(Paths.get(FILE_NAME));

            if (logs.isEmpty()) {
                System.out.println("No logs found.");
                return;
            }

            System.out.println("\n--- Log Entries ---");
            for (String log : logs) {
                System.out.println(log);
            }

        } catch (IOException e) {
            System.out.println("No logs found.");
        }
    }

    // ---------------- FILTER LOGS ----------------
    public static void filterLogsByLevel() {

        System.out.print("Enter log level (INFO, WARNING, ERROR): ");
        String level = sc.nextLine().toUpperCase();

        if (!level.equals("INFO") && !level.equals("WARNING") && !level.equals("ERROR")) {
            System.out.println("Invalid log level!");
            return;
        }

        try {
            List<String> logs = Files.readAllLines(Paths.get(FILE_NAME));
            boolean found = false;

            System.out.println("\n--- " + level + " Logs ---");

            for (String log : logs) {
                if (log.contains("[" + level + "]")) {
                    System.out.println(log);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No " + level + " logs found.");
            }

        } catch (IOException e) {
            System.out.println("No logs found.");
        }
    }

    // ---------------- SEARCH LOGS ----------------
    public static void searchLogs() {

        System.out.print("Enter keyword to search in logs: ");
        String keyword = sc.nextLine().toLowerCase();

        try {
            List<String> logs = Files.readAllLines(Paths.get(FILE_NAME));
            boolean found = false;

            System.out.println("\n--- Logs containing '" + keyword + "' ---");

            for (String log : logs) {
                if (log.toLowerCase().contains(keyword)) {
                    System.out.println(log);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No logs found containing '" + keyword + "'.");
            }

        } catch (IOException e) {
            System.out.println("No logs found.");
        }
    }

    // ---------------- LOG STATISTICS ----------------
    public static void logStatistics() {
        try {
            List<String> logs = Files.readAllLines(Paths.get(FILE_NAME));

            if (logs.isEmpty()) {
                System.out.println("No logs found to analyze.");
                return;
            }

            int info = 0, warning = 0, error = 0;

            for (String log : logs) {
                if (log.contains("[INFO]")) info++;
                else if (log.contains("[WARNING]")) warning++;
                else if (log.contains("[ERROR]")) error++;
            }

            String recentLog = logs.get(logs.size() - 1);

            System.out.println("\n--- Log Statistics ---");
            System.out.println("Total logs: " + logs.size());
            System.out.println("INFO logs: " + info);
            System.out.println("WARNING logs: " + warning);
            System.out.println("ERROR logs: " + error);
            System.out.println("Most recent log: " + recentLog);

        } catch (IOException e) {
            System.out.println("No logs found to analyze.");
        }
    }
}