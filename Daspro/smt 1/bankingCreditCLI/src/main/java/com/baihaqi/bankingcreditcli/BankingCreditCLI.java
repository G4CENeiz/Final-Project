/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.baihaqi.bankingcreditcli;

import java.util.Scanner;

/**
 *
 * @author G4CE-PC
 *         Muhammad Baihaqi Aulia Asy'ari
 *         2241720145 - TI 1I - 19
 */
public class BankingCreditCLI {
    final static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        String[][] credential = new String[1][2];
        credential[0][0] = "admin";
        credential[0][1] = "admin";
        loginMenu(credential);
    }

    // region login
    static String usernameCheck(String[][] cred) {
        while (true) {
            System.out.print("Username: ");
            String userInput = input.next();
            for (String[] strings : cred) {
                if (strings[0] == null)
                    continue;
                if (strings[0].equals(userInput))
                    return userInput;
                if (userInput.equalsIgnoreCase("register"))
                    return userInput;
            }
            printPromptSplit(
                    "The username you've entered doesn't exist in our system please re-enter your username correctly");
        }
    }

    static boolean passwordCheck(String[][] cred, String username) {
        int limit = 0;
        while (limit < 3) {
            System.out.print("Password: ");
            String userInput = input.next();
            for (String[] strings : cred) {
                if (strings[0].equals(username)) {
                    if (strings[1].equals(userInput)) {
                        return true;
                    }
                }
            }
            if (limit < 1)
                System.out.println("Wrong password");
            if (limit == 1)
                System.out.println("""
                        Wrong password, Last attempt
                        if you fail again, you would need to re-enter your username""");
            limit++;
        }
        return false;
    }

    static boolean attemptLogin(String[][] cred) {
        String username = usernameCheck(cred);
        if (!username.equalsIgnoreCase("register")) {
            boolean password = passwordCheck(cred, username);
            if (password) {
                mainMenu(cred, username);
            } else {
                if (attemptLogin(cred)) {
                    registerMenu(cred);
                }
            }
            return false;
        }
        return true;
    }

    // endregion
    // region print
    static void printHeading(String prompt) {
        int heading;
        String bar = "", side = "||";
        if (prompt.length() % 2 == 0) {
            heading = 64;
        } else {
            heading = 65;
        }
        for (int i = 0; i < heading; i++) {
            bar += "=";
        }
        System.out.println(bar);
        System.out.printf("%s%" + ((heading - 4 - prompt.length()) / 2) + "s%s%" + ((heading - 4 - prompt.length()) / 2)
                + "s%s\n", side, " ", prompt, " ", side);
        System.out.println(bar);
    }

    static void printPromptSplit(String prompt) {
        String[] promptSplit = prompt.split("\\s");
        int i = 0;
        while (i < promptSplit.length) {
            int limit = 0;
            while (limit < 65 && i < promptSplit.length && (limit + promptSplit[i].length()) < 65) {
                System.out.printf("%s ", promptSplit[i]);
                limit = limit + (promptSplit[i].length() + 1);
                i++;
            }
            System.out.println();
        }
    }

    // endregion
    // region etc
    static String[][] newCred(String[][] old) {
        String[][] credential = new String[old.length + 1][old[0].length];
        for (int row = 0; row < old.length; row++) {
            for (int col = 0; col < old[row].length; col++) {
                credential[row][col] = old[row][col];
            }
        }
        return credential;
    }

    static String menuInput() {
        System.out.print("menu: ");
        return input.next();
    }

    static double powerDouble(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        } else {
            return base * powerDouble(base, exponent - 1);
        }
    }

    static void notFound() {
        System.out.println("""
                 _  _    ___  _  _  \s
                | || |  / _ \\| || | \s
                | || |_| | | | || |_\s
                |__   _| | | |__   _|
                   | | | |_| |  | | \s
                   |_|  \\___/   |_| \s
                                    \s
                                    \s
                  _  _       _      __                      _\s
                 | \\| | ___ | |_   / _| ___  _  _  _ _   __| |
                 | .` |/ _ \\|  _| |  _|/ _ \\| || || ' \\ / _` |
                 |_|\\_|\\___/ \\__| |_|  \\___/ \\_,_||_||_|\\__,_|
                                                             \s""");
    }

    // endregion
    // region menu
    static void loginMenu(String[][] cred) {
        printHeading("LOGIN");
        printPromptSplit("If you don't already have an account please type \"register\" in the username input");
        if (attemptLogin(cred)) {
            registerMenu(cred);
        }
    }

    static void registerMenu(String[][] cred) {
        printHeading("REGISTER");
        String[][] credential = newCred(cred);
        System.out.print("Enter your username: ");
        credential[credential.length - 1][0] = input.next();
        System.out.print("Enter your password: ");
        credential[credential.length - 1][1] = input.next();
        loginMenu(credential);
    }

    static void mainMenu(String[][] cred, String user) {
        printHeading("MENU");
        System.out.println("""
                1. Credit card menu
                2. Loan menu
                3. Account information
                4. Log out""");
        switch (menuInput()) {
            case "1" -> creditCardMenu(cred, user);
            case "2" -> loanMenu(cred, user);
            case "3" -> accountInfoMenu(cred, user);
            case "4" -> loginMenu(cred);
        }
    }

    // region mainMenu
    static void creditCardMenu(String[][] cred, String user) {
        printHeading("CREDIT CARD");
        System.out.println("""
                1. Apply for a credit card
                2. Owned Credit card
                3. Back to main menu""");
        switch (menuInput()) {
            case "1" -> newCreditCard(cred, user);
            case "2" -> ownedCreditCard(cred, user);
            case "3" -> mainMenu(cred, user);
        }
    }

    // region creditCardMenu
    static void newCreditCard(String[][] cred, String user) {
        printHeading("APPLY FOR A CREDIT CARD");
        System.out.println("""
                1. General purpose
                2. Travel
                3. Lifestyle
                4. Priority
                5. Back to credit card menu""");
        switch (menuInput()) {
            case "1" -> generalPurposeCreditCardApplication(cred, user);
            case "2" -> travelCreditCardApplication(cred, user);
            case "3" -> lifestyleCreditCardApplication(cred, user);
            case "4" -> priorityCreditCardApplication(cred, user);
            case "5" -> creditCardMenu(cred, user);
        }
    }

    // region newCreditCard
    static void generalPurposeCreditCardApplication(String[][] cred, String user) {
        printHeading("GENERAL PURPOSE CREDIT CARD APPLICATION");
        notFound();
        newCreditCard(cred, user);
    }

    static void travelCreditCardApplication(String[][] cred, String user) {
        printHeading("TRAVEL CREDIT CARD APPLICATION");
        notFound();
        newCreditCard(cred, user);
    }

    static void lifestyleCreditCardApplication(String[][] cred, String user) {
        printHeading("LIFESTYLE CREDIT CARD APPLICATION");
        notFound();
        newCreditCard(cred, user);
    }

    static void priorityCreditCardApplication(String[][] cred, String user) {
        printHeading("PRIORITY CREDIT CARD APPLICATION");
        notFound();
        newCreditCard(cred, user);
    }

    // endregion
    static void ownedCreditCard(String[][] cred, String user) {
        printHeading("OWNED CREDIT CARD");
        notFound();
        creditCardMenu(cred, user);
    }

    // endregion
    static void loanMenu(String[][] cred, String user) {
        printHeading("LOAN");
        System.out.println("""
                1. Apply for a loan
                2. Current loan status
                3. Back to main menu""");
        switch (menuInput()) {
            case "1" -> newLoanMenu(cred, user);
            case "2" -> accountLoanInfo(cred, user);
            case "3" -> mainMenu(cred, user);
        }
    }

    // region loanMenu
    static void newLoanMenu(String[][] cred, String user) {
        printHeading("APPLY FOR A LOAN");
        System.out.println("""
                1. Personal
                2. Auto
                3. Mortgage
                4. Refinancing
                5. Back to loan menu""");
        switch (menuInput()) {
            case "1" -> personalLoanApplication(cred, user);
            case "2" -> autoLoanApplication(cred, user);
            case "3" -> mortgageLoanApplication();
            case "4" -> refinancingLoanApplication(cred, user);
            case "5" -> loanMenu(cred, user);
        }
    }

    // region newLoanMenu
    static void personalLoanApplication(String[][] cred, String user) {
        printHeading("PERSONAL LOAN");
        notFound();
        newLoanMenu(cred, user);
    }

    static void autoLoanApplication(String[][] cred, String user) {
        printHeading("AUTO LOAN");
        notFound();
        newLoanMenu(cred, user);
    }

    static void mortgageLoanApplication() {
        int buildingArea;
        int creditFacilities;
        int downPayment;
        int downPaymentPercentage = 10;
        int tenor;
        int debt;
        double installment;
        double interest = 7.25;
        printHeading("MORTGAGE LOAN");
        System.out.println("""
                Purpose of Credit
                1. Buying a house
                2. Renovating""");
        String s = menuInput();
        if (s.equals("1")) {
            System.out.println("""
                    Collateral Type
                    1. House
                    2. Apartment
                    3. Shop""");
            String collateralType = menuInput();
            if (collateralType.equals("1") || collateralType.equals("2")) {
                System.out.print("Building Area (m2): ");
                buildingArea = input.nextInt();
                if (buildingArea > 70)
                    downPaymentPercentage += 5;
            }
        } else if (s.equals("2")) {
            downPaymentPercentage += 20;
        }
        System.out.println("How many Credit Facilities do you have");
        boolean i = true;
        do {
            System.out.print("Credit Facility: ");
            creditFacilities = input.nextInt();
            if (creditFacilities < 1) {
                System.out.println("Please enter a positive value!");
            } else {
                i = false;
            }
        } while (i);
        if (creditFacilities > 2)
            downPaymentPercentage += 10;
        System.out.print("House price: ");
        debt = input.nextInt();
        System.out.printf("Minimum down payment amount: %,d\n", (debt / 100) * downPaymentPercentage);
        i = true;
        do {
            System.out.print("Down payment: ");
            downPayment = input.nextInt();
            if (downPayment < debt * downPaymentPercentage / 100) {
                System.out.println("Please enter a value bigger than the minimum!");
            } else {
                i = false;
            }
        } while (i);
        System.out.println("Maximum 20 years tenor");
        i = true;
        do {
            System.out.print("Tenor: ");
            tenor = input.nextInt();
            if (tenor < 1 || tenor > 20) {
                System.out.println("Please enter a value between 1 to 20");
            } else {
                i = false;
            }
        } while (i);
        double debtInterest = (debt - downPayment) * ((interest / 100) / 12);
        double interestPowerBase = (1 + ((interest / 100) / 12));
        int tenorMonth = tenor * 12;
        double inverseReturnOfPoweredInterest = 1 - (1 / powerDouble(interestPowerBase, tenorMonth));

        installment = debtInterest / inverseReturnOfPoweredInterest;
        System.out.printf("%14s IDR %,d\n", "Installment", (long) installment);
        System.out.printf("%14s IDR %,d\n", "Debt principal", (debt - downPayment));
        System.out.printf("%14s IDR %,d\n", "Minimum Income", (long) installment * 2);
    }

    static void refinancingLoanApplication(String[][] cred, String user) {
        printHeading("REFINANCING LOAN");
        notFound();
        newLoanMenu(cred, user);
    }

    // endregion
    static void accountLoanInfo(String[][] cred, String user) {
        printHeading("LOAN STATUS");
        notFound();
        loanMenu(cred, user);
    }

    // endregion
    static void accountInfoMenu(String[][] cred, String user) {
        printHeading("ACCOUNT INFO");
        notFound();
        mainMenu(cred, user);

    }
    // endregion
    // endregion
}
