  import java.io.*;
  import java.util.*;
  
  /**
   * PayrollSystem class for managing employees using inheritance, polymorphism, collections, and file handling.
   * Includes menu-driven interface.
   * Student: Yash Raj, Roll No: 2401010008, Program: B.Tech. CSE Core, Faculty: Lucky Verma
   */
  public class PayrollSystem {
      private ArrayList<Employee> employees;
      private int nextId;
      private final String FILE_NAME = "payroll.dat";
      
      // Constructor
      public PayrollSystem() {
          employees = new ArrayList<>();
          nextId = 1;
          loadData();  // Load on startup
      }
      
      // Add Employee
      public void addEmployee() {
          Scanner scanner = new Scanner(System.in);
          System.out.println("1. Manager 2. Developer");
          System.out.print("Enter type: ");
          int type = scanner.nextInt();
          scanner.nextLine();  // Consume newline
          System.out.print("Enter Name: ");
          String name = scanner.nextLine();
          System.out.print("Enter Base Salary: ");
          double baseSalary = scanner.nextDouble();
          if (baseSalary <= 0) {
              System.out.println("Invalid salary.");
              return;
          }
          Employee emp = null;
          if (type == 1) {
              System.out.print("Enter Bonus: ");
              double bonus = scanner.nextDouble();
              emp = new Manager(nextId++, name, baseSalary, bonus);
          } else if (type == 2) {
              System.out.print("Enter Overtime Hours: ");
              int hours = scanner.nextInt();
              System.out.print("Enter Overtime Rate: ");
              double rate = scanner.nextDouble();
              emp = new Developer(nextId++, name, baseSalary, hours, rate);
          } else {
              System.out.println("Invalid type.");
              return;
          }
          employees.add(emp);
          System.out.println("Employee added with ID: " + emp.getId());
      }
      
      // Display Payroll
      public void displayPayroll() {
          if (employees.isEmpty()) {
              System.out.println("No employees to display.");
              return;
          }
          for (Employee emp : employees) {
              emp.displayDetails();  // Polymorphism in action
              System.out.println("---");
          }
      }
      
      // Save Data
      public void saveData() {
          try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
              oos.writeObject(employees);
              oos.writeInt(nextId);
              System.out.println("Data saved successfully.");
          } catch (IOException e) {
              System.out.println("Error saving data: " + e.getMessage());
          }
      }
      
      // Load Data
      public void loadData() {
          try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
              employees = (ArrayList<Employee>) ois.readObject();
              nextId = ois.readInt();
              System.out.println("Data loaded successfully.");
          } catch (IOException | ClassNotFoundException e) {
              System.out.println("No existing data found. Starting fresh.");
          }
      }
      
      // Main Menu
      public void mainMenu() {
          Scanner scanner = new Scanner(System.in);
          int choice;
          do {
              System.out.println("\nEmployee Payroll Management System");
              System.out.println("1. Add Employee");
              System.out.println("2. Display Payroll");
              System.out.println("3. Save Data");
              System.out.println("4. Load Data");
              System.out.println("5. Exit");
              System.out.print("Enter your choice: ");
              choice = scanner.nextInt();
              switch (choice) {
                  case 1: addEmployee(); break;
                  case 2: displayPayroll(); break;
                  case 3: saveData(); break;
                  case 4: loadData(); break;
                  case 5: saveData(); System.out.println("Exiting..."); break;
                  default: System.out.println("Invalid choice.");
              }
          } while (choice != 5);
          scanner.close();
      }
      
      // Main Method
      public static void main(String[] args) {
          PayrollSystem system = new PayrollSystem();
          system.mainMenu();
      }
  }
  
