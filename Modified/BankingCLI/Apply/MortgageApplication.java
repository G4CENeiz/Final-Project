package BankingCLI.Apply;

import java.util.Scanner;

import BankingCLI.Extra;
import BankingCLI.MainBank;
import BankingCLI.Loans.MortgageDetail;
import BankingCLI.User.Credential;

public class MortgageApplication {
    //  region instantiation
    static Scanner     sc      = new Scanner(System.in);
    static Extra       xtr     = new Extra();
    static MainBank    bank    = new MainBank();
    //  endregion

    public void apply() {
        //  region varible hoist
        int area;
        int creditFacilities;
        double downPayment;
        double downPaymentPercentageMin = 10;
        int tenor;
        double debt;
        double salary = 0;

        for (Credential cred : MainBank.creds) {
            if (cred.username == bank.curentUser) {
                salary = cred.userProfile.salary;
            }
        }

        double installment;
        double interest = 7.25;
        double creditLimit;
        double debtMax;
        double installmentMin = salary < 5_000_000 ? salary * 0.5 : salary * 0.55;
        double salaryMin;
        double debtPrincipal;
        double debtInterest;
        double downPaymentPercentage;
        boolean invalid;
        boolean sure;
        //  endregion

        xtr.printHeading("MORTGAGE LOAN");

        //  region purpose
        xtr.writeln("Purpose of Credit");
        xtr.writeln("1. Buying a house");
        xtr.writeln("2. Renovating");
        String option = xtr.promptedTextInput("menu: ");
        if (option.equals("1")) {
            xtr.writeln("Collateral Type");
            xtr.writeln("1. House");
            xtr.writeln("2. Apartment");
            xtr.writeln("3. Shop");
            String collatType = xtr.promptedTextInput("menu: ");
            if (collatType.equals("1") || collatType.equals("2")) {
                xtr.write("Building Area (m2): ");
                area = sc.nextInt();
                if (area > 70)
                    downPaymentPercentageMin += 5;
            }
        } else if (option.equals("2")) {
            downPaymentPercentageMin += 20;
        }
        //  endregion
        
        //  region credit facilities
        xtr.writeln("How many Credit Facilities do you have");
        sure = xtr.confirm();
        do {
            invalid = true;
            do {
                xtr.write("Credit Facility: ");
                creditFacilities = sc.nextInt();
                if (creditFacilities < 1) {
                    xtr.writeln("Please enter a positive value!");
                } else {
                    invalid = false;
                }
            } while (invalid);
        } while (!sure);
        if (creditFacilities > 2) {
            downPaymentPercentageMin += 10;
        }
        //  endregion

        //  region tenor
        xtr.writeln("Maximum 20 years tenor");
        invalid = true;
        do {
            xtr.write("Tenor: ");
            tenor = sc.nextInt();
            if (tenor < 1 || tenor > 20) {
                xtr.writeln("Please enter a value between 1 to 20");
            } else {
                invalid = false;
            }
        } while (invalid);
        //  endregion

        //  region interest
        double interestInMonth = ((interest / 100) / 12);
        double interestPowerBase = (1 + ((interest / 100) / 12));
        int tenorMonth = tenor * 12;
        double inverseReturnOfPoweredInterest = 1 - (1 / xtr.PowerOfExponent(interestPowerBase, tenorMonth));
        //  endregion

        //  region Debt Proposing
        invalid = true;
        do {
            creditLimit = installmentMin
                    * (inverseReturnOfPoweredInterest / interestInMonth);
            debtMax = creditLimit * (1 / ((double) (100 - downPaymentPercentageMin) / 100));
            xtr.write(String.format("Maximum proposed debt: %,.0f\n", debtMax));
            xtr.write("House price: ");
            debt = sc.nextInt();
            if (debt > debtMax) {
                xtr.writeln("Please enter a value smaller than the maximum");
            } else {
                invalid = false;
            }
        } while (invalid);
        //  endregion

        //  region Down Payment
        xtr.write(String.format("Minimum down payment amount: %,.0f\n", (double) (debt / 100) * downPaymentPercentageMin));
        invalid = true;
        do {
            xtr.write("Down payment: ");
            downPayment = sc.nextInt();
            if (downPayment < (debt / 100) * downPaymentPercentageMin) {
                xtr.writeln("Please enter a value bigger than the minimum!");
            } else {
                invalid = false;
            }
        } while (invalid);
        //  endregion

        //  region Readjust debt
        if (downPayment > (double) (debt / 100) * downPaymentPercentageMin) {
            double[] out = debtReadjustment(installmentMin, inverseReturnOfPoweredInterest, interestInMonth, downPayment,downPaymentPercentageMin);
            debt = out[0];
            downPayment = out[1];
        }
        //  endregion

        //  region finalizing
        debtPrincipal = (debt - downPayment);
        debtInterest = debtPrincipal * interestInMonth;

        installment = debtInterest / inverseReturnOfPoweredInterest;
        salaryMin = installment < 2_500_000 ? installment * 2 : installment * (1 / 0.55);

        downPaymentPercentage = ((double) downPayment / debt) * 100;

        xtr.write(String.format("%14s IDR %,d\n", "Installment", (long) installment));
        xtr.write(String.format("%14s IDR %,d\n", "Debt principal", (long) debtPrincipal));
        xtr.write(String.format("%14s IDR %,d\n", "Minimum Income", (long) salaryMin));

        for (Credential cred : MainBank.creds) {
            if (cred.username == bank.curentUser) {
                cred.userProfile.loansData.mortgage = new MortgageDetail(creditFacilities, debt, downPayment, downPaymentPercentage, tenor, interest, installment, salaryMin);
            }
        }
        //  endregion
    }

    static double[] debtReadjustment(double installmentMin, double inverseReturnOfPoweredInterest, double interestInMonth, double downPayment, double downPaymentPercentageMin) {
        boolean invalid = true;
        double creditLimit;
        double debtMax;
        double debt;
        double[] out = new double[2];
        do {
            creditLimit = installmentMin
                    * (inverseReturnOfPoweredInterest / interestInMonth);
            debtMax = creditLimit + downPayment;
            xtr.write(String.format("Maximum proposed debt: %,.0f\n", debtMax));
            xtr.write("House price: ");
            debt = sc.nextInt();
            if (debt > debtMax) {
                xtr.writeln("Please enter a value smaller than the maximum");
            } else {
                invalid = false;
            }
        } while (invalid);
        if (downPayment < (double) (debt / 100) * downPaymentPercentageMin) {
            xtr.write(String.format("Minimum down payment amount: %,.0f\n", (double) (debt / 100) * downPaymentPercentageMin));
            invalid = true;
            do {
                xtr.write("Down payment: ");
                downPayment = sc.nextInt();
                if (downPayment < (debt / 100) * downPaymentPercentageMin) {
                    xtr.writeln("Please enter a value bigger than the minimum!");
                } else {
                    invalid = false;
                }
            } while (invalid);
        } else {
            out[0] = debt;
            out[1] = downPayment;
            return out;
        }
        if (downPayment > (double) (debt / 100) * downPaymentPercentageMin) {
            out = debtReadjustment(installmentMin, inverseReturnOfPoweredInterest, interestInMonth, downPayment,downPaymentPercentageMin);
        }
        out[0] = debt;
        out[1] = downPayment;
        return out;
    }
}
