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

                if (parts.length > 3) {
                    task.createdAt = parts[3];
                }

                tasks.add(task);
            }
        } catch (Exception e) {
            System.out.println("No previous tasks found");
        }
    }

    // ================= CLEAR ALL =================
    static void clearAllTasks() {

        if (tasks.isEmpty()) {
            System.out.println("No tasks to clear");
            pause();
            return;
        }

        System.out.print("Are you sure you want to delete ALL tasks? (yes/no): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            tasks.clear();
            saveTasks();
            System.out.println("All tasks cleared!");
        } else {
            System.out.println("Operation cancelled");
        }

        pause();
    }

    // ================= CLEAR SCREEN =================
    static void clearScreen() {
        for (int i = 0; i < 40; i++) {
            System.out.println();
        }
    }

    // ================= PAUSE =================
    static void pause() {
        System.out.println("\nPress Enter to continue...");
        sc.nextLine();
    }

    // ================= MAIN =================
    public static void main(String[] args) {

        loadTasks();

        while (true) {

            clearScreen();

            System.out.println("===== TASK MANAGER =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Complete");
            System.out.println("4. Delete Task");
            System.out.println("5. Exit");
            System.out.println("6. Clear All Tasks");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number!");
                sc.nextLine();
                pause();
                continue;
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addTask();
                case 2 -> {
                    viewTasks();
                    pause();
                }
                case 3 -> markComplete();
                case 4 -> deleteTask();
                case 5 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                case 6 -> clearAllTasks();
                default -> {
                    System.out.println("Invalid choice");
                    pause();
                }
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
        pause();
    }

    // ================= VIEW TASKS =================
    static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available");
            return;
        }

        System.out.println("\n--- Pending Tasks ---");

        int count = 1;
        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).isCompleted) {
                System.out.println(count + ". " + tasks.get(i));
                count++;
            }
        }

        if (count == 1) {
            System.out.println("No pending tasks");
        }

        System.out.println("\n--- Completed Tasks ---");

        count = 1;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).isCompleted) {
                System.out.println(count + ". " + tasks.get(i));
                count++;
            }
        }

        if (count == 1) {
            System.out.println("No completed tasks");
        }
    }

    // ================= MARK COMPLETE =================
    static void markComplete() {
        System.out.println("\n--- Pending Tasks ---");

        ArrayList<Integer> pendingIndexes = new ArrayList<>();
        int count = 1;

        for (int i = 0; i < tasks.size(); i++) {
            if (!tasks.get(i).isCompleted) {
                System.out.println(count + ". " + tasks.get(i));
                pendingIndexes.add(i);
                count++;
            }
        }

        if (pendingIndexes.isEmpty()) {
            System.out.println("No pending tasks");
            pause();
            return;
        }

        System.out.print("Enter task number: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input!");
            sc.nextLine();
            pause();
            return;
        }

        int choice = sc.nextInt();

        if (choice >= 1 && choice <= pendingIndexes.size()) {
            int realIndex = pendingIndexes.get(choice - 1);
            tasks.get(realIndex).markComplete();
            saveTasks();
            System.out.println("Marked as complete!");
        } else {
            System.out.println("Invalid choice!");
        }

        pause();
    }

    // ================= DELETE TASK =================
    static void deleteTask() {
        System.out.println("\n--- All Tasks ---");

        ArrayList<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
            indexes.add(i);
        }

        System.out.print("Enter task number: ");

        if (!sc.hasNextInt()) {
            System.out.println("Invalid input!");
            sc.nextLine();
            pause();
            return;
        }

        int choice = sc.nextInt();

        if (choice >= 1 && choice <= indexes.size()) {
            int realIndex = indexes.get(choice - 1);
            tasks.remove(realIndex);
            saveTasks();
            System.out.println("Task deleted!");
        } else {
            System.out.println("Invalid choice!");
        }

        pause();
    }
}
