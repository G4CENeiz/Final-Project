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
        // 082336750134 -> 12 digits
        // 08912888374 -> 11 digits
        int length = numbers.length();

        if (length < 11 || length > 13)
            return false;

        for (int i = 0; i < length; i++) {
            // validate if each element is a number

            int current = Integer.parseInt(String.format("%c", numbers.charAt(i)));
            // first digit should be 0
            if (i == 0 && current != 0)
                return false;

            // second digit should be 8
            if (i == 1 && current != 8)
                return false;
        }
        return true;
    }

    static boolean validateIDCardNumber(String numbers) {
        // 3573051004040001
        // 3573056204040001
        int length = numbers.length();
        if (length != 16)
            return false;
        for (int i = 0; i < length; i++) {
            switch (numbers.charAt(i)) {
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' -> {
                }
                default -> {
                    return false;
                }
            }
        }
        return true;
    }

    static int[] debtReadjustment(double installmentMin, double inverseReturnOfPoweredInterest, double interestInMonth, int downPayment, int downPaymentPercentage) {
        boolean repeat = true;
        double creditLimit;
        double debtMax;
        int debt;
        int[] out = new int[2];
        do {
            creditLimit = installmentMin
                    * (inverseReturnOfPoweredInterest / interestInMonth);
            debtMax = creditLimit + downPayment;
            write(String.format("Maximum proposed debt: %,.0f\n", debtMax));
            write("House price: ");
            debt = input.nextInt();
            if (debt > debtMax) {
                writeln("Please enter a value smaller than the maximum");
            } else {
                repeat = false;
            }
        } while (repeat);
        if (downPayment < (double) (debt / 100) * downPaymentPercentage) {
            write(String.format("Minimum down payment amount: %,.0f\n", (double) (debt / 100) * downPaymentPercentage));
            repeat = true;
            do {
                write("Down payment: ");
                downPayment = input.nextInt();
                if (downPayment < (debt / 100) * downPaymentPercentage) {
                    writeln("Please enter a value bigger than the minimum!");
                } else {
                    repeat = false;
                }
            } while (repeat);
        } else {
            out[0] = debt;
            out[1] = downPayment;
            return out;
        }
        if (downPayment > (double) (debt / 100) * downPaymentPercentage) {
            out = debtReadjustment(installmentMin, inverseReturnOfPoweredInterest, interestInMonth, downPayment,downPaymentPercentage);
        }
        out[0] = debt;
        out[1] = downPayment;
        return out;
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
        input.nextLine();
        write("Enter your name: ");
        name = input.nextLine();
        boolean i = true;
        do {
            write("Enter your phone number: ");
            phoneNumber = input.next();
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
        int id = getUserID();
        int buildingArea;
        int creditFacilities;
        int downPayment;
        int downPaymentPercentage = 10;
        int tenor;
        int debt;
        int salary = Integer.parseInt(profile[id][3]);
        double installment;
        double interest = 7.25;
        double creditLimit;
        double debtMax;
        double installmentMin = salary < 5_000_000 ? salary * 0.5 : salary * 0.55;
        double salaryMin;
        printHeading("MORTGAGE LOAN");
        writeln("""
                Purpose of Credit
                1. Buying a house
                2. Renovating""");
        String menu = promptedTextInput("menu: ");
        if (menu.equals("1")) {
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
        } else if (menu.equals("2")) {
            downPaymentPercentage += 20;
        }
        writeln("How many Credit Facilities do you have");
        boolean repeat = true;
        do {
            write("Credit Facility: ");
            creditFacilities = input.nextInt();
            if (creditFacilities < 1) {
                writeln("Please enter a positive value!");
            } else {
                repeat = false;
            }
        } while (repeat);
        if (creditFacilities > 2)
            downPaymentPercentage += 10;
        writeln("Maximum 20 years tenor");
        repeat = true;
        do {
            write("Tenor: ");
            tenor = input.nextInt();
            if (tenor < 1 || tenor > 20) {
                writeln("Please enter a value between 1 to 20");
            } else {
                repeat = false;
            }
        } while (repeat);
        double interestInMonth = ((interest / 100) / 12);
        double interestPowerBase = (1 + ((interest / 100) / 12));
        int tenorMonth = tenor * 12;
        double inverseReturnOfPoweredInterest = 1 - (1 / powerDouble(interestPowerBase, tenorMonth));
        repeat = true;
        do {
            creditLimit = installmentMin
                    * (inverseReturnOfPoweredInterest / interestInMonth);
            debtMax = creditLimit * (1 / ((double) (100 - downPaymentPercentage) / 100));
            write(String.format("Maximum proposed debt: %,.0f\n", debtMax));
            write("House price: ");
            debt = input.nextInt();
            if (debt > debtMax) {
                writeln("Please enter a value smaller than the maximum");
            } else {
                repeat = false;
            }
        } while (repeat);
        write(String.format("Minimum down payment amount: %,.0f\n", (double) (debt / 100) * downPaymentPercentage));
        repeat = true;
        do {
            write("Down payment: ");
            downPayment = input.nextInt();
            if (downPayment < (debt / 100) * downPaymentPercentage) {
                writeln("Please enter a value bigger than the minimum!");
            } else {
                repeat = false;
            }
        } while (repeat);
        // region to be refactored as a function
        if (downPayment > (double) (debt / 100) * downPaymentPercentage) {
            int[] out = debtReadjustment(installmentMin, inverseReturnOfPoweredInterest, interestInMonth, downPayment,downPaymentPercentage);
            debt = out[0];
            downPayment = out[1];
        }
        // endregion
        double debtPrincipal = (tenor < 6 ? (debt - downPayment) * 0.9 : (debt - downPayment));
        double debtInterest = debtPrincipal * interestInMonth;

        installment = debtInterest / inverseReturnOfPoweredInterest;
        salaryMin = installment < 2_500_000 ? installment * 2 : installment * (1 / 0.55);
        write(String.format("%14s IDR %,d\n", "Installment", (long) installment));
        write(String.format("%14s IDR %,d\n", "Debt principal", (long) debtPrincipal));
        write(String.format("%14s IDR %,d\n", "Minimum Income", (long) salaryMin));

        String prompt = "Mortgage Application";
        String ordinal;
        switch (String.valueOf(creditFacilities).charAt(String.valueOf(creditFacilities).length() - 1)) {
            case '1' -> ordinal = "st";
            case '2' -> ordinal = "nd";
            case '3' -> ordinal = "rd";
            default -> ordinal = "th";
        }
        double downPaymentPercentageByDebt = ((double) downPayment / debt) * 100;

        String[] varValue = {
                String.format(": %d%s", creditFacilities, ordinal),
                String.format(": IDR %,d", debt),
                String.format(": IDR %,d", downPayment),
                String.format(": IDR %,d", (debt - downPayment)),
                String.format(": IDR %,.0f", debtPrincipal),
                String.format(": %d", tenor),
                String.format(": %.2f%s", interest, "%"),
                String.format(": IDR %,.2f", installment),
                String.format(": IDR %,.2f", salaryMin)
        };

        String[] varName = {

                "Credit facility ",
                "House price ",
                String.format("Down payment %.2f%s ", downPaymentPercentageByDebt, "%"),
                "Debt principal ",
                "Debt principal discounted ",
                "Tenor ",
                "Interest ",
                "installment ",
                "Minimum income "
        };

        String barTop = "================================================================";
        String barBot = "||============================================================||";
        String side = "||";
        int heading = barTop.length();
        int headingSpacing = (heading - (2 * side.length()) - prompt.length()) / 2;
        String contentSpacing = String.format("%s%" + (heading - (2 * side.length())) + "s%s", side, " ", side);
        String title = String.format("%s%" + headingSpacing + "s%s%" + headingSpacing + "s%s", side, " ", prompt, " ",
                side);
        int fit = 0;
        for (String varNameElement : varName) {
            if (fit < varNameElement.length()) {
                fit = varNameElement.length();
            }
        }
        insertMortgageDetail(barTop);
        insertMortgageDetail(title);
        insertMortgageDetail(barBot);
        insertMortgageDetail(contentSpacing);
        for (int i = 0; i < varName.length; i++) {
            if (debtPrincipal == (debt - downPayment)) {
                if (i == 4) {
                    continue;
                }
            }
            int paddingLeft = ((heading / 2) - side.length() - fit);
            int paddingRight = ((heading / 2) - side.length() - varValue[i].length());
            String content = String.format("%s%" + paddingLeft + "s%-" + fit + "s%s%" + paddingRight + "s%s", side,
                    " ", varName[i], varValue[i], " ", side);
            insertMortgageDetail(content);
            insertMortgageDetail(contentSpacing);
        }
        insertMortgageDetail(barBot);

        if (confirm()) {
            creditMortgage[id][0] = debt;
            creditMortgage[id][1] = tenor;
            creditMortgage[id][2] = downPayment;
            creditMortgage[id][3] = installment;
        } else {
        creditMortgageDetail[id] = "";
        }
        newLoanMenu();
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
            String content;
            String prompt = "ACCOUNT INFO";
            String barTop = "================================================================================";
            String barBot = "||============================================================================||";
            String side = "||";
            int heading = barTop.length();
            String contentSpacing = String.format("%s%" + (heading - (2 * side.length())) + "s%s", side, " ", side);
            int titlePadding = (heading - (2 * side.length()) - prompt.length()) / 2;

            String[] varName = {
                    "Name ",
                    "Phone number ",
                    "ID card number ",
                    "salary "
            };
            String title = String.format("%s%" + titlePadding + "s%s%" + titlePadding + "s%s", side, " ", prompt, " ",
                    side);

            writeln(barTop);
            writeln(title);
            writeln(barBot);
            writeln(contentSpacing);

            int fitVarName = 0;
            for (String varNameElement : varName) {
                if (fitVarName < varNameElement.length()) {
                    fitVarName = varNameElement.length();
                }
            }
            int fitVarValue = 0;
            for (String varValueElement : profile[id]) {
                if (fitVarValue < varValueElement.length()) {
                    fitVarValue = varValueElement.length();
                }
            }

            for (int i = 0; i < varName.length; i++) {
                String var = String.format("%-" + fitVarName + "s: %-" + fitVarValue + "s", varName[i], profile[id][i]);
                int padding = (heading - (side.length() * 2)
                        - (var.length() % 2 == 0 ? var.length() : (var.length() + 1))) / 2;
                if (var.length() % 2 != 0) {
                    content = String.format("%s%" + padding + "s%s%" + padding + "s %s", side, " ", var, " ", side);
                } else {
                    content = String.format("%s%" + padding + "s%s%" + padding + "s%s", side, " ", var, " ", side);
                }
                writeln(content);
                writeln(contentSpacing);
            }
            writeln(barBot);
        } else {
            printHeading("ACCOUNT INFO");
            notFound();
        }
        write("Exit?\n");
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
