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
    static double[][] creditMortgage = new double[1][4];
    static String[] creditMortgageDetail = new String[1];
    static String[][] profile = new String[1][4];

    public static void main(String[] args) {
        credential[0][0] = "admin";
        credential[0][1] = "admin";
        loginMenu();
    }

    // region login
    static String usernameCheck() {
        while (true) {
            write("Username: ");
            String userInput = input.next();
            for (String[] strings : credential) {
                if (strings[0] == null)
                    continue;
                if (strings[0].equals(userInput))
                    return userInput;
                if (userInput.equalsIgnoreCase("register"))
                    return userInput;
                if (userInput.equalsIgnoreCase("quit"))
                    return userInput;
            }
            printPromptSplit(
                    "The username you've entered doesn't exist in our system please re-enter your username correctly");
        }
    }

    static boolean passwordCheck() {
        int limit = 0;
        while (limit < 3) {
            write("Password: ");
            String userInput = input.next();
            for (String[] strings : credential) {
                if (strings[0].equals(username)) {
                    if (strings[1].equals(userInput)) {
                        return true;
                    }
                }
            }
            if (limit < 1)
                writeln("Wrong password");
            if (limit == 1)
                writeln("""
                        Wrong password, Last attempt
                        if you fail again, you would need to re-enter your username""");
            limit++;
        }
        return false;
    }

    static boolean attemptLogin() {
        username = usernameCheck();
        if (!(username.equalsIgnoreCase("register") || username.equalsIgnoreCase("quit"))) {
            boolean password = passwordCheck();
            if (password) {
                if (profile[getUserID()][0] == null)
                    setProfile();
                mainMenu();
            } else {
                if (attemptLogin()) {
                    if (username.equalsIgnoreCase("register"))
                        registerMenu();
                    if (username.equalsIgnoreCase("quit"))
                        quitMenu();
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
        String bar;
        String side = "||";
        if (prompt.length() % 2 == 0) {
            heading = 64;
            bar = "================================================================";
        } else {
            heading = 65;
            bar = "=================================================================";
        }
        int gap = ((heading - (side.length() * 2) - prompt.length()) / 2);
        String title = String.format("%s%" + gap + "s%s%" + gap + "s%s\n", side, " ", prompt, " ", side);
        writeln(bar);
        write(title);
        writeln(bar);
    }

    static void printPromptSplit(String prompt) {
        String[] promptSplit = prompt.split("\\s");
        int i = 0;
        while (i < promptSplit.length) {
            int limit = 0;
            while (limit < 65 && i < promptSplit.length && (limit + promptSplit[i].length()) < 65) {
                write(String.format("%s ", promptSplit[i]));
                limit = limit + (promptSplit[i].length() + 1);
                i++;
            }
            writeln("");
        }
    }

    static void mortgageApplicationDetail(double... detail) {
        int id = getUserID();
        String prompt = "Mortgage Application";
        String ordinal;
        switch (String.valueOf(detail[3]).charAt(String.valueOf(detail[3]).length() - 1)) {
            case '1' -> ordinal = "st";
            case '2' -> ordinal = "nd";
            case '3' -> ordinal = "rd";
            default -> ordinal = "th";
        }
        double downPaymentPercentageByDebt = (detail[2] / detail[0]) * 100;

        String[] varValue = {
                String.format(": %.0f%s", detail[3], ordinal),
                String.format(": IDR %,.0f", detail[0]),
                String.format(": IDR %,.0f", detail[2]),
                String.format(": IDR %,.0f", (detail[0] - detail[2])),
                String.format(": %.0f", detail[1]),
                String.format(": %.2f%s", detail[4], "%"),
                String.format(": IDR %,.2f", detail[5]),
                String.format(": IDR %,.2f", (detail[5] * 2))
        };

        String[] varName = {

                "Credit facility ",
                "House price ",
                String.format("Down payment %.2f%s ", downPaymentPercentageByDebt, "%"),
                "Debt principal ",
                "Tenor ",
                "Interest ",
                "installment ",
                "Minimum income "
        };
        int heading = 64;
        String barTop = "================================================================";
        String barBot = "||============================================================||";
        String side = "||";
        int headingSpacing = (heading - (2 * side.length()) - prompt.length()) / 2;
        String contentSpacing = String.format("%s%" + (heading - (2 * side.length())) + "s%s", side, " ", side);
        String title = String.format("%s%" + headingSpacing + "s%s%" + headingSpacing + "s%s", side, " ", prompt, " ", side);
        int fit = 0;
        for (String s : varName) {
            if (fit < s.length()) {
                fit = s.length();
            }
        }
        insertMortgageDetail(barTop);
        insertMortgageDetail(title);
        insertMortgageDetail(barBot);
        insertMortgageDetail(contentSpacing);
        for (int i = 0; i < varName.length; i++) {
            int paddingLeft = ((heading / 2) - side.length() - fit);
            int paddingRight = ((heading / 2) - side.length() - varValue[i].length());
            String content = String.format("%s%" + paddingLeft + "s%-" + fit + "s%s%" + paddingRight + "s%s", side,
                    " ", varName[i], varValue[i], " ", side);
            insertMortgageDetail(content);
            insertMortgageDetail(contentSpacing);
        }
        insertMortgageDetail(barBot);

        if (confirm()) {
            creditMortgage[id][0] = detail[0];
            creditMortgage[id][1] = detail[1];
            creditMortgage[id][2] = detail[2];
            creditMortgage[id][3] = detail[5];
        }
        newLoanMenu();
    }

    static void write(String s) {
        System.out.print(s);
    }

    static void writeln(String s) {
        System.out.println(s);
    }

    // endregion
    // region etc
    static void newStringArray(String[][] data) {
        String[][] old = data;
        data = new String[old.length + 1][old[0].length];
        for (int row = 0; row < old.length; row++) {
            for (int col = 0; col < old[row].length; col++) {
                data[row][col] = old[row][col];
            }
        }
    }

    static void newString(String[] data) {
        String[] old = data;
        data = new String[data.length + 1];
        for (int i = 0; i < old.length; i++) {
            data[i] = old[i];
        }
    }

    static void newDoubleArray(double[][] data) {
        double[][] old = data;
        data = new double[data.length + 1][data[0].length];
        for (int row = 0; row < old.length; row++) {
            for (int col = 0; col < old[row].length; col++) {
                data[row][col] = old[row][col];
            }
        }
    }

    static String promptedTextInput(String prompt) {
        write(prompt);
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
        writeln("""
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
        while (true) {
            write("Are you sure (y/n): ");
            String userInput = input.next();
            if (userInput.equalsIgnoreCase("y")) {
                return true;
            } else if (userInput.equalsIgnoreCase("n")) {
                return false;
            }
            writeln("Please enter a valid input!");
        }
    }

    static int getUserID() {
        int i = 0;
        if (!credential[i][0].equals(username)) {
            do {
                i++;
            } while (!credential[i][0].equals(username));
        }
        return i;
    }

    static void insertMortgageDetail(String s) {
        write(s + "\n");
        creditMortgageDetail[getUserID()] += s + "\n";
    }

    static boolean validatePhoneNumber(String numbers) {
        // 0895388899808 -> 13 digits
        // 082336750134  -> 12 digits
        // 08912888374   -> 11 digits
        int length = numbers.length();

        if (length < 11 || length > 13) return false;

        for (int i = 0; i < length; i++) {
            // validate if each element is a number

            int current = Integer.parseInt(String.format("%c", numbers.charAt(i)));
            // first digit should be 0
            if (i == 0 && current != 0) return false;

            // second digit should be 8
            if (i == 1 && current != 8) return false;
        }

        return true;
    }

    static boolean validateIDCardNumber(String numbers) {
        // 3573051004040001
        // 3573056204040001
        int length = numbers.length();
        if (length != 16) return false;
        for (int i = 0; i < length; i++) {
            switch (numbers.charAt(i)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> {
                    break;
                }
                default -> {
                    return false;
                }
            }
        }
        return true;
    }

    // endregion
    // region menu
    static void loginMenu() {
        printHeading("LOGIN");
        printPromptSplit(
                "If you don't already have an account please type \"register\" in the username input, if you want to quit type \"quit\" in the username input");
        if (attemptLogin()) {
            if (username.equalsIgnoreCase("register"))
                registerMenu();
            if (username.equalsIgnoreCase("quit"))
                quitMenu();
        }
    }

    static void registerMenu() {
        printHeading("REGISTER");
        newStringArray(credential);
        newStringArray(profile);
        newDoubleArray(creditMortgage);
        newString(creditMortgageDetail);
        creditMortgageDetail[creditMortgageDetail.length - 1] = "";
        credential[credential.length - 1][0] = promptedTextInput("Enter your username: ");
        credential[credential.length - 1][1] = promptedTextInput("Enter your password: ");
        loginMenu();
    }

    static void setProfile() {
        int id = getUserID();
        String name, phoneNumber, IDCardNumber, salary;
        write("Enter your name: ");
        name = input.nextLine();
        input.next();
        boolean i = true;
        do {
            write("Enter your phone number: ");
            phoneNumber = input.nextLine();
            input.next();
            if (validatePhoneNumber(phoneNumber)) {
                i = false;
            } else {
                writeln("Please enter a valid phone number");
            }
        } while (i);
        writeln("Please enter your ID card number in this format");
        writeln("example: 3573052004691337");
        i = true;
        do {
            write("Enter your ID card number: ");
            IDCardNumber = input.next();
            if (validateIDCardNumber(IDCardNumber)) {
                i = false;
            } else {
                writeln("Please enter a valid ID card number");
            }
        } while (i);
        salary = promptedTextInput("Enter your salary: ");

        if (confirm()) {
            profile[id][0] = name;
            profile[id][1] = phoneNumber;
            profile[id][2] = IDCardNumber;
            profile[id][3] = salary;
            writeln(profile[id][0]);
            writeln(profile[id][1]);
            writeln(profile[id][2]);
            writeln(profile[id][3]);
        } else {
            setProfile();
        }
    }

    static void mainMenu() {
        printHeading("MENU");
        writeln("""
                1. Credit card menu
                2. Loan menu
                3. Account information
                4. Log out
                5. Quit the program""");
        switch (promptedTextInput("menu: ")) {
            case "1" -> creditCardMenu();
            case "2" -> loanMenu();
            case "3" -> accountInfoMenu();
            case "4" -> loginMenu();
            case "5" -> quitMenu();
        }
    }

    // region mainMenu
    static void creditCardMenu() {
        printHeading("CREDIT CARD");
        writeln("""
                1. Apply for a credit card
                2. Owned Credit card
                3. Back to main menu""");
        switch (promptedTextInput("menu: ")) {
            case "1" -> newCreditCard();
            case "2" -> ownedCreditCard();
            case "3" -> mainMenu();
        }
    }

    // region creditCardMenu
    static void newCreditCard() {
        printHeading("APPLY FOR A CREDIT CARD");
        writeln("""
                1. General purpose
                2. Travel
                3. Lifestyle
                4. Priority
                5. Back to credit card menu""");
        switch (promptedTextInput("menu: ")) {
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
        writeln("""
                1. Apply for a loan
                2. Current loan status
                3. Back to main menu""");
        switch (promptedTextInput("menu: ")) {
            case "1" -> newLoanMenu();
            case "2" -> accountLoanInfo();
            case "3" -> mainMenu();
        }
    }

    // region loanMenu
    static void newLoanMenu() {
        printHeading("APPLY FOR A LOAN");
        writeln("""
                1. Personal
                2. Auto
                3. Mortgage
                4. Refinancing
                5. Back to loan menu""");
        switch (promptedTextInput("menu: ")) {
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
        int salary = Integer.parseInt(profile[getUserID()][3]);
        double installment;
        double interest = 7.25;
        double creditLimit;
        double debtMax;
        double installmentMin = salary < 5_000_000 ? salary * 0.5 : salary * 0.55;
        printHeading("MORTGAGE LOAN");
        writeln("""
                Purpose of Credit
                1. Buying a house
                2. Renovating""");
        String s = promptedTextInput("menu: ");
        if (s.equals("1")) {
            writeln("""
                    Collateral Type
                    1. House
                    2. Apartment
                    3. Shop""");
            String collateralType = promptedTextInput("menu: ");
            if (collateralType.equals("1") || collateralType.equals("2")) {
                write("Building Area (m2): ");
                buildingArea = input.nextInt();
                if (buildingArea > 70)
                    downPaymentPercentage += 5;
            }
        } else if (s.equals("2")) {
            downPaymentPercentage += 20;
        }
        writeln("How many Credit Facilities do you have");
        boolean i = true;
        do {
            write("Credit Facility: ");
            creditFacilities = input.nextInt();
            if (creditFacilities < 1) {
                writeln("Please enter a positive value!");
            } else {
                i = false;
            }
        } while (i);
        if (creditFacilities > 2)
            downPaymentPercentage += 10;
        writeln("Maximum 20 years tenor");
        i = true;
        do {
            write("Tenor: ");
            tenor = input.nextInt();
            if (tenor < 1 || tenor > 20) {
                writeln("Please enter a value between 1 to 20");
            } else {
                i = false;
            }
        } while (i);
        double interestInMonth = ((interest / 100) / 12);
        double interestPowerBase = (1 + ((interest / 100) / 12));
        int tenorMonth = tenor * 12;
        double inverseReturnOfPoweredInterest = 1 - (1 / powerDouble(interestPowerBase, tenorMonth));
        i = true;
        do {
            creditLimit = installmentMin
                    * (inverseReturnOfPoweredInterest / interestInMonth);
            debtMax = creditLimit * (double) (1 / (1 + (downPaymentPercentage / 100)));
            write(String.format("Maximum proposed debt: %,.0f\n", debtMax));
            write("House price: ");
            debt = input.nextInt();
            if (debt > debtMax) {
                writeln("Please enter a value smaller than the maximum");
            } else {
                i = false;
            }
        } while (i);
        write(String.format("Minimum down payment amount: %,d\n", (debt / 100) * downPaymentPercentage));
        i = true;
        do {
            write("Down payment: ");
            downPayment = input.nextInt();
            if (downPayment < (debt / 100) * downPaymentPercentage) {
                writeln("Please enter a value bigger than the minimum!");
            } else {
                i = false;
            }
        } while (i);
        double downPaymentMin = (double) (debt / 100) * downPaymentPercentage;
        if (downPayment > downPaymentMin) {
            i = true;
            do {
                creditLimit = installmentMin
                        * (inverseReturnOfPoweredInterest / interestInMonth);
                double actualDownPaymentPercentage = ((double) downPayment / debt) * 100;
                debtMax = creditLimit * (1 / (1 + (actualDownPaymentPercentage / 100)));
                write(String.format("Maximum proposed debt: %,.0f\n", debtMax));
                write("House price: ");
                debt = input.nextInt();
                if (debt > debtMax) {
                    writeln("Please enter a value smaller than the maximum");
                } else {
                    i = false;
                }
            } while (i);
        }
        double debtInterest = (debt - downPayment) * interestInMonth;

        installment = debtInterest / inverseReturnOfPoweredInterest;
        write(String.format("%14s IDR %,d\n", "Installment", (long) installment));
        write(String.format("%14s IDR %,d\n", "Debt principal", (debt - downPayment)));
        write(String.format("%14s IDR %,d\n", "Minimum Income", (long) installment * 2));

        mortgageApplicationDetail(debt, tenor, downPayment, creditFacilities, interest, installment);
    }

    static void refinancingLoanApplication() {
        printHeading("REFINANCING LOAN");
        notFound();
        newLoanMenu();
    }

    // endregion
    static void accountLoanInfo() {
        printHeading("LOAN STATUS");
        if (creditMortgage[getUserID()][0] == 0) {
            notFound();
        } else {
            write(creditMortgageDetail[getUserID()]);
        }
        write("Exit?");
        if (confirm()) {
            loanMenu();
        } else {
            accountLoanInfo();
        }
    }

    // endregion
    static void accountInfoMenu() {
        int id = getUserID();
        if (profile[id][0] != null) {
            int heading = 64;
            String prompt = "ACCOUNT INFO";
            String barTop = "================================================================";
            String barBot = "||============================================================||";
            String side = "||";
            String contentSpacing = String.format("%s%" + (heading - (2 * side.length())) + "s%s", side, " ", side);
            int headingSpacing = (heading - (2 * side.length()) - prompt.length()) / 2;

            String[] varName = {
                    "Name",
                    "Phone number",
                    "ID card number",
                    "salary"
            };

            writeln(barTop);
            writeln(String.format("%s%" + headingSpacing + "s%s%" + headingSpacing + "s%s", side, " ", prompt, " ", side));
            writeln(barBot);
            writeln(contentSpacing);

            int fit = 0;
            for (String s : varName) {
                if (fit < s.length()) {
                    fit = s.length();
                }
            }

            for (int i = 0; i < varName.length; i++) {
                int paddingLeft = ((heading / 2) - side.length() - fit);
                int paddingRight = ((heading / 2) - side.length() - profile[id][i].length());
                writeln(String.format("%s%" + paddingLeft + "s%-" + fit + "s%s%" + paddingRight + "s%s", side,
                        " ", varName[i], String.format(": %s", profile[id][i]), " ", side));
                writeln(contentSpacing);
            }
            writeln(barBot);
        } else {
        printHeading("ACCOUNT INFO");
        notFound();
        }
        write("Exit?");
        if (confirm()) {
            mainMenu();
        } else {
            accountInfoMenu();
        }
    }

    static void quitMenu() {
        printHeading("QUIT SUCCESSFULLY");
    }
    // endregion
    // endregion
}
