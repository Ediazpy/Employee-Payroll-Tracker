/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package EmployeePayTrack;

import java.io.*;
import java.util.*;

/**
 * Irrigation Payroll Tracker - Tracks employee pay, invoices, and commissions.
 * Author: Emmanuel Diaz
 */
public class EmployeePayTrack
{

    private static ArrayList<Employee> employees = new ArrayList<>();
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {

        loadEmployees(); // Load saved employee data at startup

        int choice = 0;
        final int EXIT_VALUE = 9;

        do
        {
            displayMainMenu();
            choice = getIntInput("\nEnter your choice: ");

            switch (choice)
            {
                case 1 ->
                {
                    viewWeeklyPay();
                    pause();
                }
                case 2 ->
                {
                    viewYTD();
                    pause();
                }
                case 3 ->
                {
                    editPay();
                    pause();
                }
                case 4 ->
                {
                    manageInvoices();
                    pause();
                }
                case 5 ->
                {
                    closeOutMenu();
                    pause();
                }
                case 6 ->
                {
                    addEmployee();
                    pause();
                }
                case 7 ->
                {
                    showHelp();
                    pause();
                }
                case 8 ->
                {
                    showAbout();
                    pause();
                }
                case 9 ->
                    System.out.println("\nGoodbye!");
                default ->
                {
                    System.out.println("\nInvalid option. Try again.");
                    pause();
                }
            }
        }
        while (choice != EXIT_VALUE);

        saveEmployees(); // Save all employee data before exit
    }

    // ------------------------------------------------------------
    // MENU OPTIONS
    // ------------------------------------------------------------
    // ------------------------------------------------------------
// VIEW WEEKLY PAY (single employee or all)
// ------------------------------------------------------------
// ------------------------------------------------------------
// VIEW YEARLY PAY (single employee or all)
// ------------------------------------------------------------
    // ------------------------------------------------------------
// DISPLAY MAIN MENU (boxed format)
// ------------------------------------------------------------
    private static void displayMainMenu()
    {
        System.out.println();
        System.out.println("+-------------------------------------------+");
        System.out.println("|               PAYROLL MENU                |");
        System.out.println("+-------------------------------------------+");
        System.out.println("| 1) View Weekly Pay                        |");
        System.out.println("| 2) View Year-to-Date (YTD) Pay            |");
        System.out.println("| 3) Edit Employee Info                     |");
        System.out.println("| 4) Manage Invoices                        |");
        System.out.println("| 5) Close Out Week                         |");
        System.out.println("| 6) Add New Employee                       |");
        System.out.println("| 7) Help                                   |");
        System.out.println("| 8) About                                  |");
        System.out.println("| 9) Exit                                   |");
        System.out.println("+-------------------------------------------+");
    }

    private static void viewWeeklyPay()
    {
        if (employees.isEmpty())
        {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n--- View Weekly Pay ---");
        System.out.println("1) View all employees");
        System.out.println("2) View a specific employee");
        int choice = getIntInput("Enter your choice: ");

        if (choice == 1)
        {
            List<String[]> table = new ArrayList<>();
            table.add(new String[]
            {
                "Employee", "ID", "Current Weekly Pay ($)"
            });

            for (Employee emp : employees)
            {
                table.add(new String[]
                {
                    emp.getName(),
                    String.valueOf(emp.getId()),
                    String.format("%.2f", emp.getWeeklyPay())
                });
            }

            if (table.size() > 1)
            {
                printBoxedTable(table);
                System.out.println("\n(Note: Weekly pay reflects the most recent commission totals.)");
            }
            else
            {
                System.out.println("No weekly pay data found.");
            }
        }
        else if (choice == 2)
        {
            int id = getIntInput("Enter Employee ID: ");
            Employee emp = findEmployee(id);

            if (emp == null)
            {
                System.out.println("Employee not found.");
                return;
            }

            List<String[]> table = new ArrayList<>();
            table.add(new String[]
            {
                "Employee", "ID", "Current Weekly Pay ($)"
            });
            table.add(new String[]
            {
                emp.getName(),
                String.valueOf(emp.getId()),
                String.format("%.2f", emp.getWeeklyPay())
            });

            printBoxedTable(table);
            System.out.println("\n(Note: Weekly pay reflects the most recent commission totals.)");
        }
        else
        {
            System.out.println("Invalid choice.");
        }
    }

    // ------------------------------------------------------------
// VIEW YEAR-TO-DATE (single employee or all)
// ------------------------------------------------------------
    private static void viewYTD()
    {
        if (employees.isEmpty())
        {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n--- View Year-to-Date Pay ---");
        System.out.println("1) View all employees");
        System.out.println("2) View a specific employee");
        int choice = getIntInput("Enter your choice: ");

        if (choice == 1)
        {
            List<String[]> table = new ArrayList<>();
            table.add(new String[]
            {
                "Employee", "ID", "Weekly Pay ($)", "YTD Pay ($)"
            });
            for (Employee emp : employees)
            {
                table.add(new String[]
                {
                    emp.getName(),
                    String.valueOf(emp.getId()),
                    String.format("%.2f", emp.getWeeklyPay()),
                    String.format("%.2f", emp.getYearToDatePay())
                });
            }
            printBoxedTable(table);
        }
        else if (choice == 2)
        {
            int id = getIntInput("Enter Employee ID: ");
            Employee emp = findEmployee(id);

            if (emp == null)
            {
                System.out.println("Employee not found.");
                return;
            }

            List<String[]> table = new ArrayList<>();
            table.add(new String[]
            {
                "Employee", "ID", "Weekly Pay ($)", "YTD Pay ($)"
            });
            table.add(new String[]
            {
                emp.getName(),
                String.valueOf(emp.getId()),
                String.format("%.2f", emp.getWeeklyPay()),
                String.format("%.2f", emp.getYearToDatePay())
            });

            printBoxedTable(table);
        }
        else
        {
            System.out.println("Invalid choice.");
        }
    }

    // ------------------------------------------------------------
// EDIT EMPLOYEE INFO (NAME OR ID)
// ------------------------------------------------------------
    private static void editEmployeeInfo()
    {
        System.out.print("\nEnter Employee ID to edit: ");
        int id = getIntInput("");

        Employee emp = findEmployee(id);
        if (emp == null)
        {
            System.out.println("Employee not found.");
            return;
        }

        System.out.printf("\nEditing Employee: %s (ID: %d)\n", emp.getName(), emp.getId());
        System.out.println("1) Change Name");
        System.out.println("2) Change ID");
        System.out.println("3) Cancel");

        int choice = getIntInput("\nEnter your choice: ");

        switch (choice)
        {
            case 1 ->
            {
                String newName;
                while (true)
                {
                    System.out.print("Enter new name: ");
                    newName = scan.nextLine().trim();

                    if (newName.isEmpty())
                    {
                        System.out.println("Name cannot be blank.\n");
                        continue;
                    }

                    if (!newName.matches("^[a-zA-Z\\s]+$"))
                    {
                        System.out.println("Invalid name. Only letters and spaces allowed.\n");
                        continue;
                    }

                    // Check for duplicate name
                    boolean nameExists = false;
                    for (Employee e : employees)
                    {
                        if (e.getName().equalsIgnoreCase(newName) && e.getId() != emp.getId())
                        {
                            nameExists = true;
                            break;
                        }
                    }

                    if (nameExists)
                    {
                        System.out.println("An employee with that name already exists.\n");
                        continue;
                    }

                    break; // valid name
                }

                emp.setName(newName);
                System.out.println("Name updated successfully!");
            }

            case 2 ->
            {
                int newId;
                while (true)
                {
                    newId = getIntInput("Enter new ID: ");

                    // Prevent duplicate ID
                    boolean idExists = false;
                    for (Employee e : employees)
                    {
                        if (e.getId() == newId && e != emp)
                        {
                            idExists = true;
                            break;
                        }
                    }

                    if (idExists)
                    {
                        System.out.println("That ID already exists. Please try another.\n");
                        continue;
                    }

                    break; // valid ID
                }

                emp.setId(newId);
                System.out.println("ID updated successfully!");
            }

            case 3 ->
                System.out.println("Edit canceled.");

            default ->
                System.out.println("Invalid option.");
        }

        saveEmployees();
    }

    private static void addEmployee()
    {
        System.out.println("\n--- Add New Employee ---");

        // Get and validate ID
        System.out.print("Enter new employee ID: ");
        int id = getIntInput("");

        //Check for duplicate ID
        if (findEmployee(id) != null)
        {
            System.out.println("Employee ID already exists. Please use a unique ID.");
            return;
        }

        // Keep asking for a valid name
        String name = "";
        while (true)
        {
            System.out.print("Enter employee name: ");
            name = scan.nextLine().trim();

            //Validate input
            if (name.isEmpty())
            {
                System.out.println("Invalid name. Cannot be blank.\n");
                continue;
            }
            if (!name.matches("^[a-zA-Z\\s]+$"))
            {
                System.out.println("Invalid name. Only letters and spaces are allowed.\n");
                continue;
            }

            //Check for duplicate name (different ID)
            boolean nameExists = false;
            for (Employee emp : employees)
            {
                if (emp.getName().equalsIgnoreCase(name))
                {
                    nameExists = true;
                    break;
                }
            }

            if (nameExists)
            {
                System.out.printf("An employee named '%s' already exists in the system.%n", name);
                System.out.println("Would you like to re-enter a new name? (yes/no): ");
                String choice = scan.nextLine().trim().toLowerCase();

                if (choice.equals("yes") || choice.equals("y"))
                {
                    continue; // loop again for a new name
                }
                else
                {
                    System.out.println("Employee creation canceled.");
                    return;
                }
            }

            break; //exit loop when valid name and unique
        }

        // Create and save employee
        Employee newEmp = new Employee(id, name, 0.0, 0.0);
        employees.add(newEmp);
        saveEmployees();

        System.out.println("New employee added successfully!");
    }

    // ------------------------------------------------------------
    // INVOICE MANAGEMENT
    // ------------------------------------------------------------
    private static void manageInvoices()
    {
        System.out.print("Enter Employee ID: ");
        int id = getIntInput("");

        Employee emp = findEmployee(id);
        if (emp == null)
        {
            System.out.println("Employee not found.");
            return;
        }

        int choice;
        do
        {
            System.out.println("\n--- Manage Invoices for " + emp.getName() + " ---");
            System.out.println("1) View Invoices");
            System.out.println("2) Add Invoice");
            System.out.println("3) Edit Invoice");
            System.out.println("4) Change Invoice Status (Paid/Unpaid)");
            System.out.println("5) Back");
            System.out.print("Enter choice: ");
            choice = getIntInput("");

            switch (choice)
            {
                case 1 ->
                    viewInvoices(emp);
                case 2 ->
                    addInvoice(emp);
                case 3 ->
                    editInvoice(emp);
                case 4 ->
                    changeInvoiceStatus(emp);
            }
        }
        while (choice != 5);
    }

    private static void viewInvoices(Employee emp)
    {
        File file = new File(emp.getInvoiceFileName());
        if (!file.exists())
        {
            System.out.println("No invoices found for " + emp.getName());
            return;
        }

        System.out.printf("\n--- Invoices for %s (ID: %d) ---\n", emp.getName(), emp.getId());
        printBoxHeader();

        double totalSales = 0;
        double totalTips = 0;
        double totalMaterials = 0;
        double totalFees = 0;
        double totalCommission = 0;
        int invoiceCount = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            br.readLine(); // Skip header line

            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");

                if (parts.length >= 9)
                {
                    // Print formatted invoice row
                    System.out.println(formatBoxLine(parts));

                    // Safely parse numeric values
                    try
                    {
                        totalSales += Double.parseDouble(parts[4]);
                        totalTips += Double.parseDouble(parts[5]);
                        totalMaterials += Double.parseDouble(parts[6]);
                        totalFees += Double.parseDouble(parts[7]);
                        totalCommission += Double.parseDouble(parts[8]);
                        invoiceCount++;
                    }
                    catch (NumberFormatException e)
                    {
                        // Skip malformed numeric data
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error reading invoices: " + e.getMessage());
            return;
        }

        printBoxFooter();

        if (invoiceCount == 0)
        {
            System.out.println("No valid invoices found for " + emp.getName());
            return;
        }

        // Display summary section neatly
        System.out.println("\n--- Weekly Summary ---");
        System.out.printf("Total Invoices: %d%n", invoiceCount);
        System.out.printf("Total Sales: $%.2f%n", totalSales);
        System.out.printf("Total Tips: $%.2f%n", totalTips);
        System.out.printf("Total Materials: $%.2f%n", totalMaterials);
        System.out.printf("Total Fees: $%.2f%n", totalFees);
        System.out.printf("Total Commission: $%.2f%n", totalCommission);
    }

    // ------------------------------------------------------------
// ADD INVOICE (prevents duplicates)
// ------------------------------------------------------------
    private static void addInvoice(Employee emp)
    {
        System.out.println("\n--- Add Invoice for " + emp.getName() + " ---");

        System.out.print("Invoice Number: ");
        String number = scan.nextLine().trim();

        File file = new File(emp.getInvoiceFileName());
        boolean newFile = !file.exists();

        // 🔍 Check for duplicates first
        if (file.exists())
        {
            try (BufferedReader br = new BufferedReader(new FileReader(file)))
            {
                String line;
                br.readLine(); // skip header
                while ((line = br.readLine()) != null)
                {
                    String[] parts = line.split(",");
                    if (parts.length > 0 && parts[0].equalsIgnoreCase(number))
                    {
                        System.out.println("An invoice with this number already exists.");
                        System.out.print("Would you like to edit it instead? (yes/no): ");
                        String answer = scan.nextLine().trim().toLowerCase();
                        if (answer.equals("yes") || answer.equals("y"))
                        {
                            editInvoice(emp); // reuse edit feature
                            return;
                        }
                        else
                        {
                            System.out.println("Invoice creation canceled.");
                            return;
                        }
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println("Error reading existing invoices: " + e.getMessage());
            }
        }

        // If we reach here, it’s a new unique invoice
        System.out.print("Customer: ");
        String customer = scan.nextLine().trim();

        // Date validation
        String date;
        do
        {
            System.out.print("Date (MM/DD/YYYY): ");
            date = scan.nextLine().trim();
            if (!date.matches("\\d{2}/\\d{2}/\\d{4}"))
            {
                System.out.println("Invalid date format. Try again.");
                date = "";
            }
        }
        while (date.isEmpty());

        System.out.print("Status (Paid/Unpaid): ");
        String status = scan.nextLine().trim();

        System.out.print("Was credit card used? (yes/no): ");
        boolean creditCardUsed = scan.nextLine().trim().equalsIgnoreCase("yes");

        System.out.print("Total Sale: ");
        double total = getDoubleInput("");

        System.out.print("Tip: ");
        double tip = getDoubleInput("");

        System.out.print("Materials/Parts: ");
        double materials = getDoubleInput("");

        System.out.print("Fees (include backflow/permit): ");
        double fees = getDoubleInput("");

        // Create invoice and compute commission
        Invoice invoice = new Invoice(number, customer, date, emp.getName(),
                status, creditCardUsed, total, tip, materials, fees);

        // Create directory if needed
        File dir = new File("invoices");
        if (!dir.exists())
        {
            dir.mkdir();
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(file, true)))
        {
            if (newFile)
            {
                writer.println("Invoice#,Customer,Date,Status,Total,Tip,Materials,Fees,Commission");
            }

            writer.printf("%s,%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f%n",
                    number, customer, date, status, total, tip, materials, fees, invoice.getCommission());

            System.out.println("Invoice added successfully!");
        }
        catch (IOException e)
        {
            System.out.println("Error writing invoice: " + e.getMessage());
            return;
        }

        // Update employee pay
        emp.setWeeklyPay(emp.getWeeklyPay() + invoice.getCommission());
        saveEmployees();
        loadEmployees(); // refresh data
        System.out.printf("Commission of $%.2f added to weekly pay for %s.%n",
                invoice.getCommission(), emp.getName());
    }

    // ------------------------------------------------------------
// EDIT EXISTING INVOICE
// ------------------------------------------------------------
    private static void editInvoice(Employee emp)
    {
        File file = new File(emp.getInvoiceFileName());
        if (!file.exists())
        {
            System.out.println("No invoices found for " + emp.getName());
            return;
        }

        System.out.print("Enter Invoice Number to Edit: ");
        String targetNumber = scan.nextLine().trim();

        List<String> lines = new ArrayList<>();
        boolean found = false;

        double oldCommission = 0;
        double newCommission = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String header = br.readLine();
            if (header != null)
            {
                lines.add(header);
            }

            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if (parts.length < 9)
                {
                    lines.add(line);
                    continue;
                }

                String invoiceNum = parts[0].trim();
                if (invoiceNum.equalsIgnoreCase(targetNumber))
                {
                    found = true;

                    // Parse old values
                    String customer = parts[1];
                    String date = parts[2];
                    String status = parts[3];
                    boolean creditCardUsed = false;
                    double total = 0, tip = 0, materials = 0, fees = 0;

                    try
                    {
                        total = Double.parseDouble(parts[4]);
                        tip = Double.parseDouble(parts[5]);
                        materials = Double.parseDouble(parts[6]);
                        fees = Double.parseDouble(parts[7]);
                        oldCommission = Double.parseDouble(parts[8]);
                    }
                    catch (NumberFormatException e)
                    {
                        // ignore parse errors
                    }

                    System.out.println("\nEditing Invoice: " + invoiceNum);
                    System.out.println("(Leave blank to keep current value)");

                    // Let user modify fields
                    System.out.print("New Customer (current: " + customer + "): ");
                    String newCustomer = scan.nextLine().trim();
                    if (!newCustomer.isEmpty())
                    {
                        customer = newCustomer;
                    }

                    System.out.print("New Date (MM/DD/YYYY, current: " + date + "): ");
                    String newDate = scan.nextLine().trim();
                    if (!newDate.isEmpty())
                    {
                        date = newDate;
                    }

                    System.out.print("New Status (current: " + status + "): ");
                    String newStatus = scan.nextLine().trim();
                    if (!newStatus.isEmpty())
                    {
                        status = newStatus;
                    }

                    System.out.print("Was credit card used? (yes/no, current: " + (creditCardUsed ? "yes" : "no") + "): ");
                    String ccInput = scan.nextLine().trim();
                    if (ccInput.equalsIgnoreCase("yes") || ccInput.equalsIgnoreCase("no"))
                    {
                        creditCardUsed = ccInput.equalsIgnoreCase("yes");
                    }

                    System.out.print("New Total (current: " + total + "): ");
                    String newTotalStr = scan.nextLine().trim();
                    if (!newTotalStr.isEmpty())
                    {
                        total = Double.parseDouble(newTotalStr);
                    }

                    System.out.print("New Tip (current: " + tip + "): ");
                    String newTipStr = scan.nextLine().trim();
                    if (!newTipStr.isEmpty())
                    {
                        tip = Double.parseDouble(newTipStr);
                    }

                    System.out.print("New Materials (current: " + materials + "): ");
                    String newMatStr = scan.nextLine().trim();
                    if (!newMatStr.isEmpty())
                    {
                        materials = Double.parseDouble(newMatStr);
                    }

                    System.out.print("New Fees (current: " + fees + "): ");
                    String newFeesStr = scan.nextLine().trim();
                    if (!newFeesStr.isEmpty())
                    {
                        fees = Double.parseDouble(newFeesStr);
                    }

                    // Recalculate commission using same formula as Invoice class
                    newCommission = Invoice.calculateCommissionStatic(creditCardUsed, total, tip, materials, fees);

                    // Save updated invoice
                    lines.add(String.format("%s,%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%.2f",
                            invoiceNum, customer, date, status,
                            total, tip, materials, fees, newCommission));
                }
                else
                {
                    lines.add(line);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error editing invoice: " + e.getMessage());
            return;
        }

        if (!found)
        {
            System.out.println("Invoice not found.");
            return;
        }

        // Save the updated file
        try (PrintWriter writer = new PrintWriter(new FileWriter(file)))
        {
            for (String l : lines)
            {
                writer.println(l);
            }
            System.out.println("\nInvoice updated successfully!");
        }
        catch (IOException e)
        {
            System.out.println("Error saving invoice: " + e.getMessage());
        }

        // Adjust employee’s pay difference
        double diff = newCommission - oldCommission;
        if (diff != 0)
        {
            emp.setWeeklyPay(emp.getWeeklyPay() + diff);
            saveEmployees();

            if (diff > 0)
            {
                System.out.printf("Commission increased by $%.2f for %s.%n", diff, emp.getName());
            }
            else
            {
                System.out.printf("Commission decreased by $%.2f for %s.%n", Math.abs(diff), emp.getName());
            }
        }
        else
        {
            System.out.println("Commission unchanged.");
        }
    }
    // ------------------------------------------------------------
// CHANGE INVOICE STATUS (Paid / Unpaid)
// ------------------------------------------------------------

    private static void changeInvoiceStatus(Employee emp)
    {
        File file = new File(emp.getInvoiceFileName());
        if (!file.exists())
        {
            System.out.println("No invoices found for " + emp.getName());
            return;
        }

        System.out.print("Enter Invoice Number to change status: ");
        String targetNumber = scan.nextLine().trim();

        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String header = br.readLine();
            if (header != null)
            {
                lines.add(header);
            }

            String line;
            while ((line = br.readLine()) != null)
            {
                String[] parts = line.split(",");
                if (parts.length < 9)
                {
                    lines.add(line);
                    continue;
                }

                if (parts[0].equalsIgnoreCase(targetNumber))
                {
                    found = true;
                    String currentStatus = parts[3];

                    System.out.printf("Current Status: %s%n", currentStatus);
                    System.out.print("Enter new status (Paid/Unpaid): ");
                    String newStatus = scan.nextLine().trim();

                    if (!newStatus.equalsIgnoreCase("paid") && !newStatus.equalsIgnoreCase("unpaid"))
                    {
                        System.out.println("Invalid status. Only 'Paid' or 'Unpaid' allowed.");
                        lines.add(line); // keep original
                        continue;
                    }

                    // replace status in the array
                    parts[3] = capitalizeFirst(newStatus);

                    // rebuild line
                    String updatedLine = String.join(",", parts);
                    lines.add(updatedLine);
                    System.out.printf("Invoice %s status changed to %s.%n", parts[0], parts[3]);
                }
                else
                {
                    lines.add(line);
                }
            }
        }
        catch (IOException e)
        {
            System.out.println("Error reading invoices: " + e.getMessage());
            return;
        }

        if (!found)
        {
            System.out.println("Invoice not found.");
            return;
        }

        // rewrite file
        try (PrintWriter writer = new PrintWriter(new FileWriter(file)))
        {
            for (String l : lines)
            {
                writer.println(l);
            }
            System.out.println("Invoice file updated successfully!");
        }
        catch (IOException e)
        {
            System.out.println("Error saving invoice: " + e.getMessage());
        }
    }

// helper to make first letter uppercase
    private static String capitalizeFirst(String text)
    {
        if (text == null || text.isEmpty())
        {
            return text;
        }
        return text.substring(0, 1).toUpperCase() + text.substring(1).toLowerCase();
    }

    // ------------------------------------------------------------
    // CLOSE OUT WEEK
    // ------------------------------------------------------------
    private static void closeOutMenu()
    {
        System.out.print("\nEnter Employee ID to close out: ");
        int id = getIntInput("");

        Employee emp = findEmployee(id);
        if (emp == null)
        {
            System.out.println("Employee not found.");
            return;
        }

        closeOutWeek(emp);
    }

    private static void closeOutWeek(Employee emp)
    {
        System.out.println("\n--- Close Out Week for " + emp.getName() + " ---");
        double weeklyPay = emp.getWeeklyPay();

        if (weeklyPay <= 0)
        {
            System.out.println("No commission or weekly pay to close out for " + emp.getName() + ".");
            return;
        }

        System.out.printf("Current Weekly Pay: $%.2f%n", weeklyPay);
        System.out.print("Confirm payout to irrigator? (yes/no): ");
        String confirm = scan.nextLine().trim().toLowerCase();

        if (confirm.equals("yes") || confirm.equals("y"))
        {
            emp.addWeeklyPay(weeklyPay);
            emp.editPay(0);
            saveEmployees();
            loadEmployees(); // 🔄 refresh the data

            System.out.printf("Weekly payout of $%.2f processed for %s.%n", weeklyPay, emp.getName());
            System.out.println("Weekly pay reset to $0.00. Invoices remain saved.");
            System.out.println("Employee data reloaded successfully!");

        }
        else
        {
            System.out.println("Close-out canceled.");
        }
    }

    // ------------------------------------------------------------
    // FILE OPERATIONS
    // ------------------------------------------------------------
    // ------------------------------------------------------------
// LOAD EMPLOYEES FROM CSV (with data validation)
// ------------------------------------------------------------
    private static void loadEmployees()
    {
        employees.clear();
        File file = new File("employees.csv");

        if (!file.exists())
        {
            System.out.println("No existing employee file found. A new one will be created when you save.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file)))
        {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null)
            {
                // Skip header
                if (firstLine || line.trim().isEmpty())
                {
                    firstLine = false;
                    continue;
                }

                String[] parts = line.split(",");

                if (parts.length >= 4)
                {
                    try
                    {
                        int id = Integer.parseInt(parts[0].trim());
                        String name = parts[1].trim();
                        double weeklyPay = Double.parseDouble(parts[2].trim());
                        double yearToDatePay = Double.parseDouble(parts[3].trim());

                        employees.add(new Employee(id, name, weeklyPay, yearToDatePay));
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Skipping invalid employee line: " + line);
                    }
                }
            }

            System.out.println("Loaded " + employees.size() + " employee(s) from employees.csv");

            // Optional: Debug print to verify loaded data
            for (Employee emp : employees)
            {
                System.out.printf("Loaded -> %s (ID: %d) Weekly: %.2f | YTD: %.2f%n",
                        emp.getName(), emp.getId(), emp.getWeeklyPay(), emp.getYearToDatePay());
            }
        }
        catch (IOException e)
        {
            System.out.println("Error loading employees: " + e.getMessage());
        }
    }

    private static void saveEmployees()
    {
        File file = new File("employees.csv");

        try (PrintWriter writer = new PrintWriter(new FileWriter(file)))
        {
            writer.println("ID,Name,Weekly_Pay($),Year_To_Date_Pay($)");
            for (Employee emp : employees)
            {
                writer.println(emp.toString());
            }
            System.out.println("\nEmployees saved successfully to employees.csv");
        }
        catch (IOException e)
        {
            System.out.println("Error saving employees: " + e.getMessage());
        }
    }

    // ------------------------------------------------------------
    // UTILITY METHODS
    // ------------------------------------------------------------
    private static Employee findEmployee(int id)
    {
        for (Employee emp : employees)
        {
            if (emp.getId() == id)
            {
                return emp;
            }
        }
        return null;
    }

    private static void pause()
    {
        System.out.print("\nPress Enter to continue...");
        System.out.print("\n\n");
        scan.nextLine();
    }

    private static void printBoxedTable(List<String[]> rows)
    {
        if (rows.isEmpty())
        {
            return;
        }

        int numCols = rows.get(0).length;
        int[] colWidths = new int[numCols];

        // Determine column widths
        for (String[] row : rows)
        {
            for (int i = 0; i < numCols; i++)
            {
                colWidths[i] = Math.max(colWidths[i], row[i].length());
            }
        }

        // Helper to print border lines
        Runnable printBorder = () ->
        {
            System.out.print("+");
            for (int i = 0; i < numCols; i++)
            {
                System.out.print("-".repeat(colWidths[i] + 2));
                System.out.print("+");
            }
            System.out.println();
        };

        // Print top border
        printBorder.run();

        // Print header row
        String[] header = rows.get(0);
        System.out.print("|");
        for (int i = 0; i < numCols; i++)
        {
            System.out.printf(" %-" + colWidths[i] + "s |", header[i]);
        }
        System.out.println();

        // Header divider
        printBorder.run();

        // Print data rows
        for (int r = 1; r < rows.size(); r++)
        {
            String[] row = rows.get(r);
            System.out.print("|");
            for (int i = 0; i < numCols; i++)
            {
                System.out.print("-".repeat(Math.min(colWidths[i] + 2, 15)));

            }
            System.out.println();
        }

        // Bottom border
        printBorder.run();
    }

    private static int getIntInput(String message)
    {
        while (true)
        {
            try
            {
                if (!message.isEmpty())
                {
                    System.out.print(message);
                }
                return Integer.parseInt(scan.nextLine());
            }
            catch (NumberFormatException e)
            {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }

    private static double getDoubleInput(String message)
    {
        while (true)
        {
            try
            {
                if (!message.isEmpty())
                {
                    System.out.print(message);
                }
                return Double.parseDouble(scan.nextLine());
            }
            catch (NumberFormatException e)
            {
                System.out.print("Invalid input. Enter a numeric value: ");
            }
        }
    }
    // ------------------------------------------------------------
// FORMATTED INVOICE TABLE HELPERS
// ------------------------------------------------------------

    private static void printBoxHeader()
    {
        System.out.println("+---------------+---------------+------------+----------+---------+---------+-----------+---------+--------------+");
        System.out.println("| Invoice#      | Customer      | Date       | Status   | Total   | Tip     | Materials | Fees    | Commission   |");
        System.out.println("+---------------+---------------+------------+----------+---------+---------+-----------+---------+--------------+");
    }

    private static String formatBoxLine(String[] parts)
    {
        // Make sure we have safe defaults
        String[] safe = new String[9];
        for (int i = 0; i < safe.length; i++)
        {
            safe[i] = (i < parts.length) ? parts[i].trim() : "";
        }

        return String.format("| %-13s | %-13s | %-10s | %-8s | %-7s | %-7s | %-9s | %-7s | %-12s |",
                safe[0], safe[1], safe[2], safe[3],
                safe[4], safe[5], safe[6], safe[7], safe[8]);
    }

    private static void printBoxFooter()
    {
        System.out.println("+---------------+---------------+------------+----------+---------+---------+-----------+---------+--------------+");
    }
    // ------------------------------------------------------------
// ABOUT INFORMATION
// ------------------------------------------------------------

    private static void showAbout()
    {
        System.out.println("\n--------------------------------------------");
        System.out.println("EMPLOYEE Payroll Tracker");
        System.out.println("--------------------------------------------");
        System.out.println("Version: 1.0.0");
        System.out.println("Developed by: Emmanuel Diaz");
        System.out.println("Company: Journey One Landscape Team (JOLT)");
        System.out.println("Description: Tracks irrigation employees' weekly commissions, invoices, and year-to-date earnings.");
        System.out.println("GitHub: https://github.com/Ediazpy");
        System.out.println("--------------------------------------------");

        try
        {
            // Open GitHub page automatically
            java.awt.Desktop.getDesktop().browse(new java.net.URI("https://github.com/Ediazpy"));
            System.out.println("Opening GitHub page in your browser...");
        }
        catch (Exception e)
        {
            System.out.println("Unable to open browser. Visit the link above manually.");
        }
    }
    // ------------------------------------------------------------
// HELP INFORMATION
// ------------------------------------------------------------
private static void showHelp()
{
    System.out.println("\n--------------------------------------------");
    System.out.println("HELP MENU - HOW TO USE EMPLOYEE PAYROLL TRACKER");
    System.out.println("--------------------------------------------");
    System.out.println("1) View Weekly Pay");
    System.out.println("   Displays all employees and their current weekly pay.");
    System.out.println();
    System.out.println("2) View Year-to-Date (YTD) Pay");
    System.out.println("   Shows the total earnings for each employee for the year.");
    System.out.println();
    System.out.println("3) Edit Employee Info");
    System.out.println("   Allows you to update an employee's ID or name if needed.");
    System.out.println();
    System.out.println("4) Manage Invoices");
    System.out.println("   Lets you view, add, edit, or change invoice status for each employee.");
    System.out.println();
    System.out.println("5) Close Out Week");
    System.out.println("   Adds the employee's current weekly pay to their YTD total and resets weekly pay.");
    System.out.println();
    System.out.println("6) Add New Employee");
    System.out.println("   Registers a new employee and creates their individual CSV file.");
    System.out.println();
    System.out.println("7) Help");
    System.out.println("   Displays this help menu explaining all options.");
    System.out.println();
    System.out.println("8) About");
    System.out.println("   Displays program details, author information, and GitHub link.");
    System.out.println();
    System.out.println("9) Exit");
    System.out.println("   Saves all data and exits the program safely.");
    System.out.println("--------------------------------------------");
}


}
