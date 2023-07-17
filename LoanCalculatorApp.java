
import java.io.*;
import java.util.Date;
import java.util.Scanner;

// LoanCalculator interface
interface LoanCalculator {
    double calculateMonthlyPayment();
    double calculateTotalPayment();
}

// Abstract Loan class with common attributes and methods
abstract class Loan implements LoanCalculator {
    private double annualInterestRate;
    private int numberOfYears;
    private double loanAmount;
    private Date loanDate;

    /** Default constructor */
    public Loan() {
        this(2.5, 1, 1000);
    }

    /** Construct a loan with specified annual interest rate,
     * number of years, and loan amount */
    public Loan(double annualInterestRate, int numberOfYears, double loanAmount) {
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

    /** Return loan date */
    public Date getLoanDate() {
        return loanDate;
    }

    /** Save loan details to a file */
    public void saveLoanDetailsToFile(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(annualInterestRate);
            printWriter.println(numberOfYears);
            printWriter.println(loanAmount);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** Read loan details from a file */
    public void readLoanDetailsFromFile(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            annualInterestRate = Double.parseDouble(bufferedReader.readLine());
            numberOfYears = Integer.parseInt(bufferedReader.readLine());
            loanAmount = Double.parseDouble(bufferedReader.readLine());
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

// HomeLoan class that extends Loan
class HomeLoan extends Loan {
    private double downPayment;

    public HomeLoan(double annualInterestRate, int numberOfYears, double loanAmount, double downPayment) {
        super(annualInterestRate, numberOfYears, loanAmount);
        this.downPayment = downPayment;
    }

    @Override
    public double calculateMonthlyPayment() {
        double remainingLoanAmount = getLoanAmount() - downPayment;
        double monthlyInterestRate = getAnnualInterestRate() / 1200;
        int numberOfMonths = getNumberOfYears() * 12;
        double monthlyPayment = remainingLoanAmount * monthlyInterestRate /
                (1 - Math.pow(1 / (1 + monthlyInterestRate), numberOfMonths));
        return monthlyPayment;
    }

    @Override
    public double calculateTotalPayment() {
        double monthlyPayment = calculateMonthlyPayment();
        int numberOfMonths = getNumberOfYears() * 12;
        double totalPayment = monthlyPayment * numberOfMonths;
        return totalPayment;
    }
}


public class LoanCalculatorApp {
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

        // Create an instance of HomeLoan
        HomeLoan homeLoan = new HomeLoan(annualInterestRate, numberOfYears, loanAmount, 0.0);

        // Use the instance to call methods and perform operations
        double monthlyPayment = homeLoan.calculateMonthlyPayment();
        double totalPayment = homeLoan.calculateTotalPayment();

        // Print the results
        System.out.println("Monthly Payment: " + monthlyPayment);
        System.out.println("Total Payment: " + totalPayment);

        // Save loan details to a file
        homeLoan.saveLoanDetailsToFile("loan_details.txt");

        // Read loan details from a file
        homeLoan.readLoanDetailsFromFile("loan_details.txt");

        // Close the scanner
        scanner.close();
    }
}
