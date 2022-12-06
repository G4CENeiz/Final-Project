/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.baihaqi.bankingcreditcli;

import java.util.Scanner;

/**
 *
 * @author G4CE-PC
 * Muhammad Baihaqi Aulia Asy'ari
 * 2241720145 - TI 1I - 19
 */
public class BankingCreditCLI {
final static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Hello World!");
        String[][] credential = new String[1][2];
        credential[0][0] = "admin";
        credential[0][1] = "admin";
        loginMenu(credential);
    }
    //region login
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
            printPromptSplit("The username you've entered doesn't exist in our system please re-enter your username correctly");
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
        } return true;
    }
    //endregion
    //region print
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
        System.out.printf("%s%"+((heading-4-prompt.length())/2)+"s%s%"+((heading-4-prompt.length())/2)+"s%s\n", side, " ", prompt, " ", side);
        System.out.println(bar);
    }
    static void printPromptSplit(String prompt) {
        String[] promptSplit = prompt.split("\\s");
        int i = 0;
        while (i < promptSplit.length) {
            int limit = 0;
            while (limit < 65 && i < promptSplit.length && (limit+promptSplit[i].length()) < 65) {
                System.out.printf("%s ",promptSplit[i]);
                limit = limit + (promptSplit[i].length() + 1);
                i++;
            }
            System.out.println();
        }
    }
    //endregion
    //region etc
    static String[][] newCred(String[][] old) {
        String[][] credential = new String[old.length+1][old[0].length];
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
    //endregion
    //region menu
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
        credential[credential.length-1][0] = input.next();
        System.out.print("Enter your password: ");
        credential[credential.length-1][1] = input.next();
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
        }
    }
    //region mainMenu
    static void creditCardMenu(String[][] cred, String user) {
        printHeading("CREDIT CARD");
        System.out.println("""
                    1. Apply for a credit card
                    2. Owned Credit card
                    3. Back to main menu""");
        switch (menuInput()) {
            case "1" -> newCreditCard(cred, user);
            case "2" -> ownedCreditCard();
            case "3" -> mainMenu(cred, user);
        }
    }
    //region creditCardMenu
    static void newCreditCard(String[][] cred, String user) {
        printHeading("APPLY FOR A CREDIT CARD");
        System.out.println("""
                    1. General purpose
                    2. Travel
                    3. Lifestyle
                    4. Priority
                    5. Back to credit card menu""");
        switch (menuInput()) {
            case "1" -> generalPurposeCreditCardApplication();
            case "2" -> travelCreditCardApplication();
            case "3" -> lifestyleCreditCardApplication();
            case "4" -> priorityCreditCardApplication();
            case "5" -> creditCardMenu(cred, user);
        }
    }
    //region newCreditCard
    static void generalPurposeCreditCardApplication() {
        printHeading("GENERAL PURPOSE CREDIT CARD APPLICATION");
        /*
        int limitMin    = 2_000_000;
        int limitMax    = 300_000_000;
        int incomeMin   = (limitMin / 6) * 10;
        float interest  = 1.75F;
         */
    }
    static void travelCreditCardApplication() {
        printHeading("TRAVEL CREDIT CARD APPLICATION");
    }
    static void lifestyleCreditCardApplication() {
        printHeading("LIFESTYLE CREDIT CARD APPLICATION");
    }
    static void priorityCreditCardApplication() {
        printHeading("PRIORITY CREDIT CARD APPLICATION");
    }
    //endregion
    static void ownedCreditCard() {
        printHeading("OWNED CREDIT CARD");
    }
    //endregion
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
    //region loanMenu
    static void newLoanMenu(String[][] cred, String user) {
        printHeading("APPLY FOR A LOAN");
        System.out.println("""
                1. Personal
                2. Auto
                3. Mortgage
                4. Refinancing
                5. Back to loan menu""");
        switch (menuInput()) {
            case "1" -> personalLoanApplication();
            case "2" -> autoLoanApplication();
            case "3" -> mortgageLoanApplication();
            case "4" -> refinancingLoanApplication();
            case "5" -> loanMenu(cred, user);
        }
    }
    //region newLoanMenu
    static void personalLoanApplication() {
        printHeading("PERSONAL LOAN");
    }
    static void autoLoanApplication() {
        printHeading("AUTO LOAN");
    }
    static void mortgageLoanApplication() {
        int buildingArea;
        int creditFacilities;
        int downPayment;
        int downPaymentPercentage = 10;
        int tenor;
        int price;
        int installment;
        float interest = 7.25f;
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
                System.out.print("Building Area: ");
                buildingArea = input.nextInt();
                if (buildingArea > 70)
                    downPaymentPercentage += 5;
            }
            System.out.print("How many Credit Facilities do you have");
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
            if (creditFacilities == 2) downPaymentPercentage += 5;
            else if (creditFacilities > 2) downPaymentPercentage += 10;
            System.out.print("House price: ");
            price = input.nextInt();
            System.out.printf("Minimum down payment amount: %d", price * downPaymentPercentage / 100);
            i = true;
            do {
                System.out.print("Down payment: ");
                downPayment = input.nextInt();
                if (downPayment < price * downPaymentPercentage / 100) {
                    System.out.println("Please enter a value bigger than the minimum!");
                } else {
                    i = false;
                }
            } while (i);
            System.out.print("Maximum 20 years tenor");
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
            installment = (price * (downPaymentPercentage/100) / 12) / (1 - 1 / (1 + (downPaymentPercentage/100) / 12) ^ (tenor * 12));
            System.out.printf("%14s IDR%,d", "Installment", installment);
            System.out.printf("%14s IDR%,d", "Debt principal", (price - downPayment));
            System.out.printf("%14s IDR%,d", "Minimum Income", installment * 2);
        } else if (s.equals("2")) {
            downPaymentPercentage += 20;
            System.out.print("How many Credit Facilities do you have");
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
            if (creditFacilities > 2) downPaymentPercentage += 10;
            System.out.print("House price: ");
            price = input.nextInt();
            System.out.printf("Minimum down payment amount: %d", price * downPaymentPercentage / 100);
            i = true;
            do {
                System.out.print("Down payment: ");
                downPayment = input.nextInt();
                if (downPayment < price * downPaymentPercentage / 100) {
                    System.out.println("Please enter a value bigger than the minimum!");
                } else {
                    i = false;
                }
            } while (i);
            System.out.print("Maximum 20 years tenor");
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
            installment = (price * (downPaymentPercentage/100) / 12) / (1 - 1 / (1 + (downPaymentPercentage/100) / 12) ^ (tenor * 12));
            System.out.printf("%14s IDR%,d", "Installment", installment);
            System.out.printf("%14s IDR%,d", "Debt principal", (price - downPayment));
            System.out.printf("%14s IDR%,d", "Minimum Income", installment * 2);
        }
    }
    static void refinancingLoanApplication() {
        printHeading("REFINANCING LOAN");
    }
    //endregion
    static void accountLoanInfo(String[][] cred, String user) {
        printHeading("LOAN STATUS");
    }
    //endregion
    static void accountInfoMenu(String[][] cred, String user) {
        printHeading("ACCOUNT INFO");
    }
    //endregion
    //endregion
}
