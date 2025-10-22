import java.io.*;
import java.util.*;

class Employee implements Serializable {
    int id;
    String name;
    String department;
    double salary;

    public Employee(int id, String name, String department, double salary) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return String.format("ID: %d | Name: %s | Dept: %s | Salary: %.2f", id, name, department, salary);
    }
}

public class EmployeeManagementSystem {
    private static final String FILE_NAME = "employees.txt";
    private static ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        loadEmployees();
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== Employee Management System =====");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1 -> addEmployee(sc);
                case 2 -> viewEmployees();
                case 3 -> searchEmployee(sc);
                case 4 -> updateEmployee(sc);
                case 5 -> deleteEmployee(sc);
                case 6 -> saveEmployees();
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 6);

        System.out.println("Exiting system. Goodbye!");
    }

    private static void addEmployee(Scanner sc) {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Salary: ");
        double salary = sc.nextDouble();

        employees.add(new Employee(id, name, dept, salary));
        saveEmployees();
        System.out.println("✅ Employee added successfully!");
    }

    private static void viewEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found!");
            return;
        }
        System.out.println("\n--- Employee List ---");
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    private static void searchEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to search: ");
        int id = sc.nextInt();
        for (Employee e : employees) {
            if (e.id == id) {
                System.out.println("Found: " + e);
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    private static void updateEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to update: ");
        int id = sc.nextInt();
        sc.nextLine();

        for (Employee e : employees) {
            if (e.id == id) {
                System.out.print("Enter new name: ");
                e.name = sc.nextLine();
                System.out.print("Enter new department: ");
                e.department = sc.nextLine();
                System.out.print("Enter new salary: ");
                e.salary = sc.nextDouble();
                saveEmployees();
                System.out.println("✅ Employee updated successfully!");
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    private static void deleteEmployee(Scanner sc) {
        System.out.print("Enter Employee ID to delete: ");
        int id = sc.nextInt();
        Iterator<Employee> it = employees.iterator();
        while (it.hasNext()) {
            if (it.next().id == id) {
                it.remove();
                saveEmployees();
                System.out.println("✅ Employee deleted successfully!");
                return;
            }
        }
        System.out.println("Employee not found!");
    }

    // ---------- File Handling ----------
    private static void saveEmployees() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    private static void loadEmployees() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            employees = (ArrayList<Employee>) ois.readObject();
        } catch (Exception e) {
            employees = new ArrayList<>();
        }
    }
}