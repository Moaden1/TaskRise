import java.time.LocalDate;
import java.util.*;

public class TasksManager {
    private HashMap<String, Task> ttsMap; //task to score map (changed to name to task map - 'nttmap')
    private PriorityQueue<Task> taskPQ;
    private PriorityQueue<Task> completedTasksPQ;
    private HashMap<String, Task> ctMap;
    //TODO: Finish hashmap and pq methods for completed tasks and the option to add a removed/completed task back

    public TasksManager() {
        this.ttsMap = new HashMap<>();
        this.taskPQ = new PriorityQueue<>();
        this.completedTasksPQ = new PriorityQueue<>(); //removed this whole thang
        this.ctMap = new HashMap<>();
    }

    public HashMap<String, Task> getMap() {
        return this.ttsMap;
    }

    public HashMap<String, Task> getCtMap() {
        return this.ctMap;
    }

    public PriorityQueue<Task> getPQ() {
        return this.taskPQ;
    }

    public void getTopTask() {
        if (taskPQ.peek() != null) {
            taskPQ.peek().printTaskDetails();
        } else {
            System.out.println("No top task exists...there's no tasks added yet!");
        }
    } //todo: separate this getTopTask from actual getter for top task if needed

    public PriorityQueue<Task> getCompletedTasksPQ() {
        return this.completedTasksPQ;
    }

    public void addTask(Task task) {
        // insert task into the right
        taskPQ.add(task);
        ttsMap.put(task.getTaskName(), task);
    }

    public void addTask(String taskName, String taskType, int importance, int relevance, LocalDate deadline) {
        Task t = new Task(taskName, taskType, importance, relevance, deadline);
        taskPQ.add(t);
        ttsMap.put(taskName, t);
    }

    public void addTask(String taskName, String taskType, int importance, int relevance, LocalDate deadline, String desc) {
        Task t = new Task(taskName, taskType, importance, relevance, deadline, desc);
        taskPQ.add(t);
        ttsMap.put(taskName, t);
    }

    public void removeTask(String taskName) { //maybe use task description or taskName instead
        Task task = ttsMap.get(taskName);
        if (task != null && taskPQ.contains(task)) {
            // remove from both map and pq
            taskPQ.remove(task);
            ttsMap.remove(taskName);
            ctMap.put(taskName, task);
            completedTasksPQ.add(task);
        } else {
            throw new NoSuchElementException("Error: Sorry but that task does not exist");
        }
    }

    // need a print tasks list, marked completed list,
    public void printTasks() {
        HashMap<String, Task> map = this.getMap();
        int count = 1;
        for (Map.Entry<String, Task> E : map.entrySet()) {
            System.out.println("\t Task Number " + count + ": " + E.getKey());
            E.getValue().printTaskDetails();
            count++;
        }
    }

    // prints and retrieves top completed task
    public Task getTopCompletedTask() {
        if (completedTasksPQ.isEmpty()) {
            System.out.println("Sorry, no tasks have been completed");
            return null;
        }
        completedTasksPQ.peek().printTaskDetails();
        return this.completedTasksPQ.peek();
    }

    public void printCompletedTasks() {
        HashMap<String, Task> map = this.getCtMap();
        int count = 1;
        for (Map.Entry<String, Task> E : map.entrySet()) {
            System.out.println("\t Completed Task #" + count + ": " + E.getKey());
            E.getValue().printTaskDetails();
            count++;
        }
    }
}