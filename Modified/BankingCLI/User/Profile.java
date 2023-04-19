package BankingCLI.User;

import BankingCLI.Loans.Loans;

public class Profile {
    public String name;
    public String phoneNum;
    public String NationalID;
    public double salary;
    public Loans loansData;

    public Profile(String name, String phoneNum, String NationalID, double salary) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.NationalID = NationalID;
        this.salary = salary;
    }
}
