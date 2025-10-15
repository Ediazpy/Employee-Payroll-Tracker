/*
 * Employee.java
 * Author: Emmanuel Diaz
 * Part of the EmployeePayTrack system
 */

package EmployeePayTrack;

import java.io.File;

public class Employee
{
    // ------------------------------------------------------------
    // ATTRIBUTES
    // ------------------------------------------------------------
    private int id;
    private String name;
    private double weeklyPay;
    private double yearToDatePay;

    // ------------------------------------------------------------
    // CONSTRUCTORS
    // ------------------------------------------------------------
    public Employee()
    {
        this.id = 0;
        this.name = "N/A";
        this.weeklyPay = 0.0;
        this.yearToDatePay = 0.0;
    }

    public Employee(int id, String name, double weeklyPay, double yearToDatePay)
    {
        this.id = id;
        this.name = name;
        this.weeklyPay = weeklyPay;
        this.yearToDatePay = yearToDatePay;
    }

    // ------------------------------------------------------------
    // GETTERS
    // ------------------------------------------------------------
    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public double getWeeklyPay()
    {
        return weeklyPay;
    }

    public double getYearToDatePay()
    {
        return yearToDatePay;
    }

    // ------------------------------------------------------------
    // SETTERS WITH VALIDATION
    // ------------------------------------------------------------
    public void setId(int id)
    {
        if (id <= 0)
        {
            System.out.println("⚠️ Invalid ID. Must be a positive number.");
            return;
        }
        this.id = id;
    }

    public void setName(String name)
    {
        if (name == null || name.trim().isEmpty() || !name.matches("^[a-zA-Z\\s]+$"))
        {
            System.out.println("⚠️ Invalid name. Must contain only letters and spaces.");
            return;
        }
        this.name = name.trim();
    }

    public void setWeeklyPay(double weeklyPay)
    {
        this.weeklyPay = Math.max(0, weeklyPay);
    }

    public void setYearToDatePay(double yearToDatePay)
    {
        this.yearToDatePay = Math.max(0, yearToDatePay);
    }

    // ------------------------------------------------------------
    // HELPER METHODS
    // ------------------------------------------------------------
    /** Add amount to current weekly pay */
    public void addWeeklyPay(double amount)
    {
        this.weeklyPay += amount;
    }

    /** Add weekly pay to year-to-date, then reset weekly pay */
    public void closeOutWeek()
    {
        this.yearToDatePay += this.weeklyPay;
        this.weeklyPay = 0;
    }

    /** Update weekly pay manually (used when editing pay or commission updates) */
    public void editPay(double newPay)
    {
        this.weeklyPay = Math.max(0, newPay);
    }

    /** Get normalized invoice filename */
    public String getInvoiceFileName()
    {
        // Always store under /invoices folder with lowercase normalized name
        String safeName = name.trim().toLowerCase().replaceAll("\\s+", "_");
        return "invoices" + File.separator + safeName + "_" + id + ".csv";
    }

    // ------------------------------------------------------------
    // FILE OUTPUT
    // ------------------------------------------------------------
    @Override
    public String toString()
    {
        // Always save formatted with 2 decimal places for consistency
        return String.format("%d,%s,%.2f,%.2f", id, name, weeklyPay, yearToDatePay);
    }
}
