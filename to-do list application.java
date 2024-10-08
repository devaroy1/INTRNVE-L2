import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ToDoList {

    private List<String> tasks;
    private static final String FILE_NAME = "tasks.txt";

    public ToDoList() {
        tasks = new ArrayList<>();
        loadTasks();
    }

    private void loadTasks() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String task;
            while ((task = br.readLine()) != null) {
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("No previous tasks found, starting fresh!");
        }
    }

    private void saveTasks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (String task : tasks) {
                bw.write(task);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks.");
        }
    }

    public void addTask(String task) {
        if (task != null && !task.trim().isEmpty()) {
            tasks.add(task);
            saveTasks();
            System.out.println("Task added: " + task);
        } else {
            System.out.println("Task description cannot be empty.");
        }
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
        }
    }

    public void updateTask(int index, String newTask) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, newTask);
            saveTasks();
            System.out.println("Task updated: " + newTask);
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            String removedTask = tasks.remove(index);
            saveTasks();
            System.out.println("Task deleted: " + removedTask);
        } else {
            System.out.println("Invalid task index.");
        }
    }

    public static void main(String[] args) {
        ToDoList todoList = new ToDoList();
        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println("\nEnter a command (add, view, update, delete, exit):");
            command = scanner.nextLine();

            switch (command.toLowerCase()) {
                case "add":
                    System.out.println("Enter task description:");
                    String taskDescription = scanner.nextLine();
                    todoList.addTask(taskDescription);
                    break;

                case "view":
                    todoList.viewTasks();
                    break;

                case "update":
                    System.out.println("Enter task index to update:");
                    int updateIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    System.out.println("Enter new task description:");
                    String newTaskDescription = scanner.nextLine();
                    todoList.updateTask(updateIndex, newTaskDescription);
                    break;

                case "delete":
                    System.out.println("Enter task index to delete:");
                    int deleteIndex = Integer.parseInt(scanner.nextLine()) - 1;
                    todoList.deleteTask(deleteIndex);
                    break;

                case "exit":
                    System.out.println("Exiting the application.");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid command. Please try again.");
            }
        }
    }
}