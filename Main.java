import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Main {

    static ArrayList<Task> tasks = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // ================= SAVE TASKS =================
    static void saveTasks() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("tasks.txt"))) {
            for (Task task : tasks) {
                writer.write(task.title + "," + task.priority + "," + task.isCompleted + "," + task.createdAt);
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("Error saving tasks");
        }
    }

    // ================= LOAD TASKS =================
    static void loadTasks() {
        try (BufferedReader reader = new BufferedReader(new FileReader("tasks.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                Task task = new Task(parts[0], parts[1]);

                task.isCompleted = Boolean.parseBoolean(parts[2]);

                // handle old data (without timestamp)
                if (parts.length > 3) {
                    task.createdAt = parts[3];
                }

                tasks.add(task);
            }
        } catch (Exception e) {
            System.out.println("No previous tasks found");
        }
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        loadTasks();

        while (true) {
            System.out.println("\n===== TASK MANAGER =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Complete");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");

            // Input validation
            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> viewTasks();
                case 3 -> markComplete();
                case 4 -> deleteTask();
                case 5 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    // ================= ADD TASK =================
    static void addTask() {
        System.out.print("Enter task: ");
        String title = sc.nextLine();

        System.out.print("Priority (High/Medium/Low): ");
        String priority = sc.nextLine();

        tasks.add(new Task(title, priority));
        saveTasks();

        System.out.println("Task added!");
    }

    // ================= VIEW TASKS =================
    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available");
            return;
        }

        // Sort by priority
        tasks.sort((a, b) -> a.priority.compareToIgnoreCase(b.priority));

        System.out.println("\n--- Pending Tasks ---");
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).isCompleted) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }

        System.out.println("\n--- Completed Tasks ---");
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).isCompleted) {
               System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    // ================= MARK COMPLETE =================
    static void markComplete() {
        viewTasks();

        System.out.print("Enter task number: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input!");
            sc.nextLine();
            return;
        }

        int index = sc.nextInt() - 1;

        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markComplete();
            saveTasks();
            System.out.println("Marked as complete!");
        } else {
            System.out.println("Invalid index!");
        }
    }

    // ================= DELETE TASK =================
    static void deleteTask() {
        viewTasks();

       System.out.print("Enter task number: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input!");
            sc.nextLine();
            return;
        }

       int index = sc.nextInt() - 1;

        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            saveTasks();
            System.out.println("Task deleted!");
        } else {
            System.out.println("Invalid index!");
        }
    }
}