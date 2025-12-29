import java.time.LocalDate;
// import java.time.LocalDateTime;         //add functionality later for specific timing or..
import java.time.temporal.ChronoUnit; // simply ask user which of two tasks comes first if deadlines align (must check deadlines of every task given)
import java.util.HashSet;
import java.util.Set;

public class Task implements Comparable<Task> {
    private final String taskType; //professional, personal, academic (NECESSARY)....define taskType class later?
    private final String taskName;
    private LocalDate deadline;
    private final int importance; //through user prompting (add ability to modify this, don't make final)
    private String desc; //optional if the user wants to attach a description to task
    private final int relevance;

    public Task(String taskName, String taskType, int importance, int relevance) {

       /* if (importance > 10 || importance <= 0) {
            throw new IllegalArgumentException("Error: Importance must be a number from 1 to 10");
        } else if (relevance > 10 || relevance <= 0) {
            throw new IllegalArgumentException("Error: Relevance must be a number from 1 to 10");
        }
        Set<String> validTypes = populateSet(new HashSet<>());
        if (!validTypes.contains(taskType)) {
            throw new IllegalArgumentException("Error: Please type either Professional/Per, Per/Personal, or Academic/Aca");
        }*/
        this.taskType = taskType;
        this.taskName = taskName;
        this.importance = importance;
        this.relevance = relevance;
        this.deadline = null; //no deadline given
        this.desc = "There is no description for this task!"; //generate default description later
        // todo: make this base const call const with most methods, essentially reversing constructor chaining
    }

    public Task(String taskName, String taskType, int importance, int relevance, LocalDate deadline) {
        this(taskName, taskType, importance, relevance);
        this.deadline = deadline;
    }

    // constructor if user specifies description for task, otherwise using default descriptor or possibly generating one?
    public Task(String taskName, String taskType, int importance, int relevance, LocalDate deadline, String desc) {
        this(taskName, taskType, importance, relevance, deadline);
        this.desc = desc;
    }

    // made to ensure tasks in task manager are sorted properly in pq
    // note: Sorts by descending priority (higher score = higher priority)
    public int compareTo(Task other) {
        return Integer.compare(other.getPriorityScore(), this.getPriorityScore());
    }

    private Set<String> populateSet(Set<String> s) {
        s.add("Per");
        s.add("Pro");
        s.add("Aca");
        s.add("Personal");
        s.add("Professional");
        s.add("Academic");
        return s;
    }

    // getter/setTo methods
    public int getDaysLeft(LocalDate current, LocalDate deadline) {
       if (deadline == null) {
           return -1; //i.e. no deadline applicable here
       }
       if (deadline.isBefore(current)) { //possibly ignore making this an error to accoutn for late tasks..easy fix honestlyh
           return 0;
           //throw new IllegalArgumentException("Deadline must be after current date");
       }
        return (int) ChronoUnit.DAYS.between(current, deadline);
    }

    // getter methods
    public String getTaskName() {
        return this.taskName;
    }

    public String getTaskType() {
        return this.taskType;
    }

    public LocalDate getDeadline() {
        return this.deadline;
    }

    public int getImportance() {
        return this.importance;
    }

    public String getDescription() {
        return this.desc;
    } //unused

    public int getRelevance() {
        return this.relevance;
    }

    public int getPriorityScore() {
        return calcPriorityScore(getDaysLeft(LocalDate.now(), getDeadline()), getImportance(), getRelevance());
    }

    private int calcPriorityScore(int daysLeft, int importance, int relevance) {
        int score = 0;
        if (daysLeft < 0) { //could also mean overdue, but we are only handling tasks that are not past a deadline/have no deadline
            // just calculate based on importance and relevance
            score = (importance * 8) + (relevance * 4); //since no deadline, must add a weight..here lessening multipliers so tasks with priorty have higher score
        } else {
            if (daysLeft == 0) {
                score = ((importance * 15) + (relevance * 10));
            } else {
                score = ((importance * 10) + (relevance * 7)) / daysLeft; //if less days left, score is higher
            }
        }
        return score;
    }

    public void printTaskDetails() {
        System.out.println("\t Task Name: " + this.taskName);
        System.out.println("\t Task PriorityScore: " + this.getPriorityScore());
        System.out.println("\t Task Description: " + this.desc);
        System.out.print(Main.ConsoleStyles.RESET);
        System.out.println();
    }
}