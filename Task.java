public class Task {
    String title;
    String priority;
    boolean isCompleted;
    String createdAt;

    public Task(String title, String priority) {
        this.title = title;
        this.priority = priority;
        this.isCompleted = false;
        this.createdAt = java.time.LocalDateTime.now().toString();
    }

    public void markComplete() {
        this.isCompleted = true;
    }

    @Override
    public String toString() {
        return title + " [" + priority + "] " +
               (isCompleted ? "✔" : "❌") +
               " | Created: " + createdAt;
    }
}