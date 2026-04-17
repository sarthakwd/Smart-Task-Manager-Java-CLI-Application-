import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Task {
    String title;
    String priority;
    boolean isCompleted;
    String createdAt;

    public Task(String title, String priority) {
        this.title = title;
        this.priority = priority;
        this.isCompleted = false;
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
this.createdAt = LocalDateTime.now().format(formatter);
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
