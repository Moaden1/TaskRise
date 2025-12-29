import java.io.Console;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

// idea: make more personable by collecting user data and utlizing it to be more "connected"
// i.e. "Welcome to TaskRise -name of user here-".
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TasksManager tm = new TasksManager();
        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "Welcome.\nToday's date is: " + LocalDate.now() + "\n" + ConsoleStyles.RESET); //checking LD.Now();

        boolean running = true;
        while (running) {
            // call function
            startupMessage();
            String choice = scanner.nextLine().trim(); //enforcing input consistency
            switch (choice) {
                case "1": // Add task
                    try {
                        System.out.print(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "Task name: " + ConsoleStyles.RESET);
                        String name = scanner.nextLine();
                        String tType = "";  //scanner.nextLine();
                        Set<String> validTpes = populateSet(new HashSet<String>());
                        while (true) {
                            System.out.print(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "Task type: " + ConsoleStyles.RESET);
                            tType = scanner.nextLine().trim().toLowerCase();
                            if (validTpes.contains(tType)) {
                                break;
                            } else {
                                System.out.println(ConsoleStyles.YELLOW_BG_HIGH + "Error: Please use aca/academic, per/personal, or pro/professional" + ConsoleStyles.RESET);
                            }
                        }
                        int importance = -1;
                        while (true) {
                            try {
                                System.out.print(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "Please enter this task importance (1-10): ");
                                importance = Integer.parseInt(scanner.nextLine());
                                if (importance < 10 && importance > 0) {
                                    break;
                                } else {
                                    System.out.println(ConsoleStyles.YELLOW_BG_HIGH
                                            + "Error: Please enter number between 1 to 10 (inclusive)" + ConsoleStyles.RESET);
                                }
                            } catch (NumberFormatException exception) {
                                System.out.println(ConsoleStyles.RED_BG_HIGH + "Please enter a valid integer number." + ConsoleStyles.RESET);
                            }
                        }
                        int relevance = -1; //Integer.parseInt(scanner.nextLine());
                        while (true) {
                            try {
                                System.out.print(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "Please enter this task's relevance (1-10): " + ConsoleStyles.RESET);
                                relevance = Integer.parseInt(scanner.nextLine());
                                if (relevance < 10 && relevance > 0) {
                                    break;
                                } else {
                                    System.out.println(ConsoleStyles.RED_BG +
                                            "Error: Please enter number between 1 to 10 (inclusive)" + ConsoleStyles.RESET);
                                }
                            } catch (NumberFormatException exception) {
                                System.out.println(ConsoleStyles.RED_BG_HIGH + "Please enter a valid integer number.");
                            }
                        }
                        System.out.print(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "Deadline (YYYY-MM-DD): " + ConsoleStyles.RESET);
                        LocalDate deadline = LocalDate.parse(scanner.nextLine());
                        System.out.print(ConsoleStyles.WHITE_BG + ConsoleStyles.BLACK_UNDERLINE + ConsoleStyles.WHITE_HIGH +
                                "Would you like to add a description for this task? Y/N " + ConsoleStyles.RESET);
                        String wantDesc = scanner.nextLine();
                        if (wantDesc.equalsIgnoreCase("Y")) {
                            System.out.print(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "Type task description below: " + ConsoleStyles.RESET + "\n");
                            String desc = scanner.nextLine();
                            tm.addTask(name, tType, importance, relevance, deadline, desc);
                        } else {
                            tm.addTask(name, tType, importance, relevance, deadline);
                            System.out.println(ConsoleStyles.GREEN_BG + "Task successfully added." + ConsoleStyles.RESET);
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println(ConsoleStyles.RED_BG_HIGH + "Error: Invalid date format. Please use YYYY-MM-DD." + ConsoleStyles.RESET);
                    } catch (NumberFormatException e) {
                        System.out.println(ConsoleStyles.RED_BG_HIGH + "Error: Urgency and importance must be numbers." + ConsoleStyles.RESET);
                    }
                    break;

                case "2": // Remove task - TODO: ACCOUNT FOR LOWERCASE AND SPECIFY TO USER NAME IS NEEDED EXPLICITLY, TRIM INPUT AS WELL (MAINTAINING CONSISTENCY)
                    if (tm.getPQ().isEmpty()) {
                        System.out.println(ConsoleStyles.YELLOW_BG + "No task(s) exist currently, add a task first please." + ConsoleStyles.RESET);
                        break;
                    }
                    System.out.print("Enter task name to remove: ");
                    String removeName = scanner.nextLine().trim();
                    try {
                        tm.removeTask(removeName);
                        System.out.println(ConsoleStyles.GREEN_BG + " Task successfully removed." + ConsoleStyles.RESET);
                    } catch (Exception e) { //extra check although already checked no tasks added (empty pq)
                        System.out.println(ConsoleStyles.RED_BG_HIGH + "Error in removing task: " +
                                e.getMessage() + ConsoleStyles.RESET);
                    }
                    break;

                case "3": // View top task
                    if (tm.getPQ().isEmpty()) {
                        System.out.println(ConsoleStyles.YELLOW_BG +
                                "Good and bad news... No task(s) exist currently, add a task first please." +
                                ConsoleStyles.RESET + "\n");
                        break;
                    }
                 /*   if (tm.getTopTask() != null) { */
                        System.out.print(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLUE_BOLD + " Your #1 priority: \n" + ConsoleStyles.RESET);
                        tm.getTopTask(); //Color consistency broken - Fixd
                        System.out.println();
                   /* } else {
                        System.out.println(" Congrats! No tasks available.");
                    } */
                    break;

                case "4": // View all tasks
                    if (tm.getPQ().isEmpty()) {
                        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + ConsoleStyles.GREEN_BOLD_HIGH +
                                "Excellent! No tasks available. \n" + ConsoleStyles.RESET);
                    } else {
                        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLUE_BOLD + "All tasks: \n" + ConsoleStyles.RESET);
                        tm.printTasks();
                        System.out.println();
                    }
                    break;

                case "5": // Exiting
                    running = false;
                    System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + ConsoleStyles.GREEN_BOLD_HIGH +
                            "Thanks for using TaskRise, please take care!" + ConsoleStyles.RESET);
                    break;

                default:
                    System.out.println(ConsoleStyles.YELLOW_BG_HIGH + "UH OH! Invalid option, try again." + ConsoleStyles.RESET);
            }
        }
        scanner.close();
    }

    private static Set<String> populateSet(Set<String> s) {
        s.add("per");
        s.add("pro");
        s.add("aca");
        s.add("personal");
        s.add("professional");
        s.add("academic");
        return s;
    }

    private static void startupMessage() {
        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + ConsoleStyles.PURPLE_BOLD + "=== Task Rise === " + ConsoleStyles.RESET);
        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "1. Add task" + ConsoleStyles.RESET);
        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE +"2. Remove task" + ConsoleStyles.RESET);
        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "3. View top priority task" + ConsoleStyles.RESET);
        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "4. View all tasks" + ConsoleStyles.RESET);
        System.out.println(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "5. Exit" + ConsoleStyles.RESET);
        System.out.print(ConsoleStyles.WHITE_BG_HIGH + ConsoleStyles.BLACK_UNDERLINE + "Choose an option:" + ConsoleStyles.RESET + " " + ConsoleStyles.RESET);
    }
    // ANSI CODES ADDED ON DEC 28TH 2025 @21:40.
    static class ConsoleStyles { //inherently static (package private)
        // Reset To Default Scheme
        public static final String RESET = "\u001B[0m";

        // Normal Colors
        public static final String BLACK = "\u001B[0;30m";
        public static final String RED = "\u001B[0;31m";
        public static final String GREEN = "\u001B[0;32m";
        public static final String YELLOW = "\u001B[0;33m";
        public static final String BLUE = "\u001B[0;34m";
        public static final String PURPLE = "\u001B[0;35m";
        public static final String CYAN = "\u001B[0;36m";
        public static final String WHITE = "\u001B[0;37m";

        // Bold
        public static final String BLACK_BOLD = "\u001B[1;30m";
        public static final String RED_BOLD = "\u001B[1;31m";
        public static final String GREEN_BOLD = "\u001B[1;32m";
        public static final String YELLOW_BOLD = "\u001B[1;33m";
        public static final String BLUE_BOLD = "\u001B[1;34m";
        public static final String PURPLE_BOLD = "\u001B[1;35m";
        public static final String CYAN_BOLD = "\u001B[1;36m";
        public static final String WHITE_BOLD = "\u001B[1;37m";

        // Underline
        public static final String BLACK_UNDERLINE = "\u001B[4;30m";
        public static final String RED_UNDERLINE = "\u001B[4;31m";
        public static final String GREEN_UNDERLINE = "\u001B[4;32m";
        public static final String YELLOW_UNDERLINE = "\u001B[4;33m";
        public static final String BLUE_UNDERLINE = "\u001B[4;34m";
        public static final String PURPLE_UNDERLINE = "\u001B[4;35m";
        public static final String CYAN_UNDERLINE = "\u001B[4;36m";
        public static final String WHITE_UNDERLINE = "\u001B[4;37m";

        // Background
        public static final String BLACK_BG = "\u001B[40m";
        public static final String RED_BG = "\u001B[41m";
        public static final String GREEN_BG = "\u001B[42m";
        public static final String YELLOW_BG = "\u001B[43m";
        public static final String BLUE_BG = "\u001B[44m";
        public static final String PURPLE_BG = "\u001B[45m";
        public static final String CYAN_BG = "\u001B[46m";
        public static final String WHITE_BG = "\u001B[47m";

        // High Intensity
        public static final String BLACK_HIGH = "\u001B[0;90m";
        public static final String RED_HIGH = "\u001B[0;91m";
        public static final String GREEN_HIGH = "\u001B[0;92m";
        public static final String YELLOW_HIGH = "\u001B[0;93m";
        public static final String BLUE_HIGH = "\u001B[0;94m";
        public static final String PURPLE_HIGH = "\u001B[0;95m";
        public static final String CYAN_HIGH = "\u001B[0;96m";
        public static final String WHITE_HIGH = "\u001B[0;97m";

        // Bold High Intensity
        public static final String BLACK_BOLD_HIGH = "\u001B[1;90m";
        public static final String RED_BOLD_HIGH = "\u001B[1;91m";
        public static final String GREEN_BOLD_HIGH = "\u001B[1;92m";
        public static final String YELLOW_BOLD_HIGH = "\u001B[1;93m";
        public static final String BLUE_BOLD_HIGH = "\u001B[1;94m";
        public static final String PURPLE_BOLD_HIGH = "\u001B[1;95m";
        public static final String CYAN_BOLD_HIGH = "\u001B[1;96m";
        public static final String WHITE_BOLD_HIGH = "\u001B[1;97m";

        // High Intensity Backgrounds
        public static final String BLACK_BG_HIGH = "\u001B[0;100m";
        public static final String RED_BG_HIGH = "\u001B[0;101m";
        public static final String GREEN_BG_HIGH = "\u001B[0;102m";
        public static final String YELLOW_BG_HIGH = "\u001B[0;103m";
        public static final String BLUE_BG_HIGH = "\u001B[0;104m";
        public static final String PURPLE_BG_HIGH = "\u001B[0;105m";
        public static final String CYAN_BG_HIGH = "\u001B[0;106m";
        public static final String WHITE_BG_HIGH = "\u001B[0;107m";
    }
}