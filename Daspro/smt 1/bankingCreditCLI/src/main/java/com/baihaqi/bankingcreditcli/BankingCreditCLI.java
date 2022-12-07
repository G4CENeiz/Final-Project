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
    static String[][] credential = new String[1][2];
    static String username;

    public static void main(String[] args) {
        credential[0][0] = "admin";
        credential[0][1] = "admin";
        loginMenu();
    }

    // region login
    static String usernameCheck() {
        while (true) {
            System.out.print("Username: ");
            String userInput = input.next();
            for (String[] strings : credential) {
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

    static boolean passwordCheck() {
        int limit = 0;
        while (limit < 3) {
            System.out.print("Password: ");
            String userInput = input.next();
            for (String[] strings : credential) {
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

    static boolean attemptLogin() {
        username = usernameCheck();
        if (!username.equalsIgnoreCase("register")) {
            boolean password = passwordCheck();
            if (password) {
                mainMenu();
            } else {
                if (attemptLogin()) {
                    registerMenu();
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

    static void mortgageApplicationDetail(int debt, int tenor, int downPaymentPercentage, int downPayment, int creditFacilities, double interest, double installment) {
        String prompt = "Mortgage Application";
        String ordinal;
        switch (String.valueOf(creditFacilities).charAt(String.valueOf(creditFacilities).length()-1)) {
            case '1' -> ordinal = "st";
            case '2' -> ordinal = "nd";
            case '3' -> ordinal = "rd";
            default -> ordinal = "th";
        }

        String[] varValue = {
                String.format("%d%s", creditFacilities, ordinal),
                String.format("IDR %,d", debt),
                String.format("IDR %,d", downPayment),
                String.format("IDR %,d", (debt - downPayment)),
                String.format("%d", tenor),
                String.format("%.2f%s", interest, "%"),
                String.format("IDR %,.2f", installment),
                String.format("IDR %,.2f", (installment * 2))
        };

        String[] varName = {
                "Credit facility: ",
                "House price: ",
                String.format("Down payment %d%s: ", downPaymentPercentage, "%"),
                "Debt principal: ",
                "Tenor: ",
                "Interest: ",
                "installment: ",
                "Minimum income: "
        };
        int heading;
        String barTop = "", side = "||", barBot = "";
        heading = 64;
        for (int i = 0; i < heading; i++) {
            barTop += "=";
        }
        barBot += side;
        for (int i = 0; i < heading - (2 * side.length()); i++) {
            barBot += "=";
        }
        barBot += side;
        System.out.println(barTop);
        int headingSpacing = (heading - (2 * side.length()) - prompt.length()) / 2;
        System.out.printf("%s%" + headingSpacing + "s%s%" + headingSpacing
                + "s%s\n", side, " ", prompt, " ", side);
        System.out.println(barBot);

        String contentSpacing = String.format("%s%"+ (heading - (2 * side.length())) +"s%s\n", side, " ", side);
        System.out.print(contentSpacing);

        for (int i = 0; i < varName.length; i++) {
            int paddingLeft = ((heading / 2) - side.length() - varName[i].length());
            int paddingRight = ((heading / 2) - side.length() - varValue[i].length());
            System.out.printf("%s%" + paddingLeft + "s%s%s%" + paddingRight + "s%s\n", side, " ", varName[i], varValue[i], " ", side);
            System.out.print(contentSpacing);
        }
        System.out.println(barBot);
    }

    // endregion
    // region etc
    static void newCred() {
        String[][] credentialOld = credential;
        credential = new String[credentialOld.length + 1][credentialOld[0].length];
        for (int row = 0; row < credentialOld.length; row++) {
            for (int col = 0; col < credentialOld[row].length; col++) {
                credential[row][col] = credentialOld[row][col];
            }
        }
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

    static boolean confirm() {
        while (true){
            System.out.print("Are you sure (y/n): ");
            String userInput = input.next();
            if (userInput.equalsIgnoreCase("y")) {
                return true;
            } else if (userInput.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Please enter a valid input!");
        }
    }
    // endregion
    // region menu
    static void loginMenu() {
        printHeading("LOGIN");
        printPromptSplit("If you don't already have an account please type \"register\" in the username input");
        if (attemptLogin()) {
            registerMenu();
        }
    }

    static void registerMenu() {
        printHeading("REGISTER");
        newCred();
        System.out.print("Enter your username: ");
        credential[credential.length - 1][0] = input.next();
        System.out.print("Enter your password: ");
        credential[credential.length - 1][1] = input.next();
        loginMenu();
    }

    static void mainMenu() {
        printHeading("MENU");
        System.out.println("""
                1. Credit card menu
                2. Loan menu
                3. Account information
                4. Log out""");
        switch (menuInput()) {
            case "1" -> creditCardMenu();
            case "2" -> loanMenu();
            case "3" -> accountInfoMenu();
            case "4" -> loginMenu();
        }
    }

    // region mainMenu
    static void creditCardMenu() {
        printHeading("CREDIT CARD");
        System.out.println("""
                1. Apply for a credit card
                2. Owned Credit card
                3. Back to main menu""");
        switch (menuInput()) {
            case "1" -> newCreditCard();
            case "2" -> ownedCreditCard();
            case "3" -> mainMenu();
        }
    }

    // region creditCardMenu
    static void newCreditCard() {
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
            case "5" -> creditCardMenu();
        }
    }

    // region newCreditCard
    static void generalPurposeCreditCardApplication() {
        printHeading("GENERAL PURPOSE CREDIT CARD APPLICATION");
        notFound();
        newCreditCard();
    }

    static void travelCreditCardApplication() {
        printHeading("TRAVEL CREDIT CARD APPLICATION");
        notFound();
        newCreditCard();
    }

    static void lifestyleCreditCardApplication() {
        printHeading("LIFESTYLE CREDIT CARD APPLICATION");
        notFound();
        newCreditCard();
    }

    static void priorityCreditCardApplication() {
        printHeading("PRIORITY CREDIT CARD APPLICATION");
        notFound();
        newCreditCard();
    }

    // endregion
    static void ownedCreditCard() {
        printHeading("OWNED CREDIT CARD");
        notFound();
        creditCardMenu();
    }

    // endregion
    static void loanMenu() {
        printHeading("LOAN");
        System.out.println("""
                1. Apply for a loan
                2. Current loan status
                3. Back to main menu""");
        switch (menuInput()) {
            case "1" -> newLoanMenu();
            case "2" -> accountLoanInfo();
            case "3" -> mainMenu();
        }
    }

    // region loanMenu
    static void newLoanMenu() {
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
            case "5" -> loanMenu();
        }
    }

    // region newLoanMenu
    static void personalLoanApplication() {
        printHeading("PERSONAL LOAN");
        notFound();
        newLoanMenu();
    }

    static void autoLoanApplication() {
        printHeading("AUTO LOAN");
        notFound();
        newLoanMenu();
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
            if (downPayment < (debt / 100) * downPaymentPercentage) {
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

        mortgageApplicationDetail(debt, tenor, downPaymentPercentage, downPayment, creditFacilities, interest, installment);
    }

    static void refinancingLoanApplication() {
        printHeading("REFINANCING LOAN");
        notFound();
        newLoanMenu();
    }

    // endregion
    static void accountLoanInfo() {
        printHeading("LOAN STATUS");
        notFound();
        loanMenu();
    }

    // endregion
    static void accountInfoMenu() {
        printHeading("ACCOUNT INFO");
        notFound();
        mainMenu();

    }
    // endregion
    // endregion
}
