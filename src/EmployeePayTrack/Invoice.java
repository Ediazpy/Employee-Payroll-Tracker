/*
 * Invoice.java
 * Stores all invoice data for the irrigator and writes it cleanly to CSV.
 * Author: Emmanuel Diaz
 */
package EmployeePayTrack;

public class Invoice
{
    private String invoiceNumber;
    private String customer;
    private String date;
    private String employeeName;
    private String status;
    private boolean creditCardUsed;
    private double total;
    private double tip;
    private double materials;
    private double fees; // includes permit/backflow
    private double commission;

    // ---------------- Constructor ----------------
    public Invoice(String invoiceNumber, String customer, String date, String employeeName,
            String status, boolean creditCardUsed, double total, double tip,
            double materials, double fees)
    {

        this.invoiceNumber = invoiceNumber;
        this.customer = customer;
        this.date = date;
        this.employeeName = employeeName;
        this.status = status;
        this.creditCardUsed = creditCardUsed;
        this.total = total;
        this.tip = tip;
        this.materials = materials;
        this.fees = fees;
        this.commission = calculateCommission();
    }

    // ---------------- Commission Logic ----------------
    private double calculateCommission()
    {
        double commission;

        if (creditCardUsed)
        {
            // Credit card used formula
            commission = ((total - materials - fees) / 2) + tip;
        }
        else if (materials >= 35)
        {
            //High materials case
            commission = ((total - materials - fees) / 2) + materials + tip;
        }
        else
        {
            //Standard low materials case
            commission = ((total - materials - fees) / 2) + tip;
        }

        return commission;
    }
    public static double calculateCommissionStatic(boolean creditCardUsed, double total, double tip, double materials, double fees)
{
    double commission;
    if (creditCardUsed)
    {
        commission = ((total - materials - fees) / 2) + tip;
    }
    else
    {
        if (materials >= 35)
        {
            commission = ((total - materials - fees) / 2) + materials + tip;
        }
        else
        {
            commission = ((total - materials - fees) / 2) + tip;
        }
    }
    return commission;
}


    // ---------------- Getters ----------------
    public double getCommission()
    {
        return commission;
    }

    public String getEmployeeName()
    {
        return employeeName;
    }

    public String getInvoiceFileName()
    {
        return employeeName.replaceAll(" ", "_") + "_invoices.csv";
    }

    // ---------------- CSV Output ----------------
    @Override
    public String toString()
    {
        return String.format("%s,%s,%s,%s,%s,%b,%.2f,%.2f,%.2f,%.2f,%.2f",
                invoiceNumber, customer, date, employeeName, status,
                creditCardUsed, total, tip, materials, fees, commission);
    }

    public static String getHeader()
    {
        return "Invoice#,Customer,Date,Employee,Status,CreditCardUsed,Total,Tip,Materials,Fees,Commission";
    }
}
