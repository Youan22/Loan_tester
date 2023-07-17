import java.util.Date;
import java.util.Scanner;

public class loan {
    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;
    private Date loanDate;

    /** Default constructor */
    public loan() {
        this(2.5, 1, 1000);
    }

    /** Construct a loan with specified annual interest rate,
     * number of years, and loan amount */
    public loan(double annualInterestRate, int numberOfYears, double loanAmount) {
        this.annualInterestRate = annualInterestRate;
        this.numberOfYears = numberOfYears;
        this.loanAmount = loanAmount;
        this.loanDate = new Date();
    }

    /** Return annualInterestRate */
    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    /** Set a new annualInterestRate */
    public void setAnnualInterestRate(double annualInterestRate) {
        this.annualInterestRate = annualInterestRate;
    }

    /** Return numberOfYears */
    public int getNumberOfYears() {
        return numberOfYears;
    }

    /** Set a new numberOfYears */
    public void setNumberOfYears(int numberOfYears) {
        this.numberOfYears = numberOfYears;
    }

    /** Return loanAmount */
    public double getLoanAmount() {
        return loanAmount;
    }

    /** Set a new loanAmount */
    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    /** Find monthly payment */
    public double getMonthlyPayment() {
        double monthlyInterestRate = annualInterestRate / 1200;
        double monthlyPayment = loanAmount * monthlyInterestRate /
                (1 - Math.pow(1 / (1 + monthlyInterestRate), numberOfYears * 12));
        return monthlyPayment;
    }

    /** Find total payment */
    public double getTotalPayment() {
        double totalPayment = getMonthlyPayment() * numberOfYears * 12;
        return totalPayment;
    }

    /** Return loan date */
    public Date getLoanDate() {
        return loanDate;
    }

    /** Calculate interest paid */
    public double getInterestPaid() {
        double totalPayment = getTotalPayment();
        double interestPaid = totalPayment - loanAmount;
        return interestPaid;
    }

    /** Calculate remaining balance */
    public double getRemainingBalance() {
        double monthlyPayment = getMonthlyPayment();
        double remainingBalance = loanAmount;
        for (int month = 1; month <= numberOfYears * 12; month++) {
            double monthlyInterest = remainingBalance * (annualInterestRate / 1200);
            double principalPayment = monthlyPayment - monthlyInterest;
            remainingBalance -= principalPayment;
        }
        return remainingBalance;
    }

    public static void main(String[] args) {
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for input
        System.out.print("Enter annual interest rate: ");
        double annualInterestRate = scanner.nextDouble();

        System.out.print("Enter number of years: ");
        int numberOfYears = scanner.nextInt();

        System.out.print("Enter loan amount: ");
        double loanAmount = scanner.nextDouble();

        // Create an instance of the Loan class with user input
        loan loan = new loan(annualInterestRate, numberOfYears, loanAmount);

        // Use the instance to call methods and perform operations
        double monthlyPayment = loan.getMonthlyPayment();
        double totalPayment = loan.getTotalPayment();
        double interestPaid = loan.getInterestPaid();
        double remainingBalance = loan.getRemainingBalance();

        // Print the results
        System.out.println("Monthly Payment: " + monthlyPayment);
        System.out.println("Total Payment: " + totalPayment);
        System.out.println("Interest Paid: " + interestPaid);
        System.out.println("Remaining Balance: " + remainingBalance);

        // Close the scanner
        scanner.close();
    }
}
