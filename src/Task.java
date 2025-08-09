import java.time.LocalDate;
// import java.time.LocalDateTime;         //add functionality later for specific timing or...
import java.time.temporal.ChronoUnit; // simply ask user which of two tasks
                                    // are due first on day instead of calculating myself here

// this class defines a task, resort tasks as needed (after priority scoring)
public class Task {
    private final String taskType; //professional, personal, academic (NECESSARY)....define taskType class later?
    private String taskName;
    private LocalDate deadline;
    private int priorityScore; //calculated later, todo: create function for this, may not have to store as field as it may be stored elsewhere..maybe in taskmamanger
    private int importance; //through user prompting (add ability to modify this, don't make final)
    private int daysLeft; //maybe consider not storing this as a field here
    private String desc; //optional if the user wants to attach a description to task

    // note: main class should handle prompting, task delegations, anything non-logic alongside setup
    // todo: write errors for constructor parameters to catch right away....throw illegal arg exceptioon
    // todo:  for importance (must be 1-10), taskType (must type in Per, Pro, Aca or ALL ..add functionality
    // to inclide combination of multiple)
    public Task(String taskName, String taskType, int importance) {
        this.taskType = taskType;
        this.taskName = taskName;
        this.importance = importance;
        this.desc = "No description (yet)"; //generate default description later
        // todo: make this base const call const with most methods, essentially reversing constructor chaining
    }

    public Task(String taskName, String taskType, int importance, LocalDate deadline, int daysLeft) {
        this(taskName, taskType, importance);
        this.deadline = deadline;
        this.daysLeft = getDaysLeft(LocalDate.now(), deadline);
    }

    // constructor if user specifies description for task, otherwise using default descriptor or possibly generating one?
    public Task(String taskName, String taskType, int importance, LocalDate deadline, int daysLeft, String desc) {
       /* this.taskType = taskType;
        this.taskName = taskName;
        this.deadline = deadline;
        this.importance = importance;
        this.daysLeft = getDaysLeft(LocalDate.now(), deadline); */
        this(taskName, taskType, importance, deadline, daysLeft);
        this.desc = desc;
    }

    // getter/setTo methods
    public int getDaysLeft(LocalDate current, LocalDate deadline) {
        if (deadline.isBefore(current)) {
            throw new IllegalArgumentException("Deadline must be after current date");
        }
        return (int) ChronoUnit.DAYS.between(current, deadline);;
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
    }

    public int getPriorityScore() {
        return this.priorityScore;
    }
    // instance methods
}