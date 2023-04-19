package BankingCLI;

import BankingCLI.User.Credential;
import BankingCLI.Apply.*;;

public class Menu {
    static MainBank bank    = new MainBank();
    static Login    log     = new Login();
    static Extra    xtr     = new Extra();

    static MortgageApplication mortgageApplication = new MortgageApplication();

    public static void mainMenu() {
        xtr.printHeading("MENU");
        xtr.writeln("""
                1. Credit card menu
                2. Loan menu
                3. Account information
                4. Log out
                5. Quit the program""");
        switch (xtr.promptedTextInput("menu: ")) {
            case "1" -> creditCardMenu();
            case "2" -> loanMenu();
            case "3" -> accountInfoMenu();
            case "4" -> log.loginMenu();
            case "5" -> quitMenu();
        }
    }

    public static void creditCardMenu() {
        xtr.printHeading("CREDIT CARD");
        xtr.writeln("""
                1. Apply for a credit card
                2. Owned Credit card
                3. Back to main menu""");
        switch (xtr.promptedTextInput("menu: ")) {
            case "1" -> newCreditCard();
            case "2" -> ownedCreditCard();
            case "3" -> mainMenu();
        }
    }

    public static void newCreditCard() {
        xtr.printHeading("APPLY FOR A CREDIT CARD");
        xtr.writeln("""
                1. General purpose
                2. Travel
                3. Lifestyle
                4. Priority
                5. Back to credit card menu""");
        switch (xtr.promptedTextInput("menu: ")) {
            case "1" -> generalPurposeCreditCardApplication();
            case "2" -> travelCreditCardApplication();
            case "3" -> lifestyleCreditCardApplication();
            case "4" -> priorityCreditCardApplication();
            case "5" -> creditCardMenu();
        }
    }

    public static void generalPurposeCreditCardApplication() {
        xtr.printHeading("GENERAL PURPOSE CREDIT CARD APPLICATION");
        xtr.notFound();
        newCreditCard();
    }

    public static void travelCreditCardApplication() {
        xtr.printHeading("TRAVEL CREDIT CARD APPLICATION");
        xtr.notFound();
        newCreditCard();
    }

    public static void lifestyleCreditCardApplication() {
        xtr.printHeading("LIFESTYLE CREDIT CARD APPLICATION");
        xtr.notFound();
        newCreditCard();
    }

    public static void priorityCreditCardApplication() {
        xtr.printHeading("PRIORITY CREDIT CARD APPLICATION");
        xtr.notFound();
        newCreditCard();
    }

    static void ownedCreditCard() {
        xtr.printHeading("OWNED CREDIT CARD");
        xtr.notFound();
        creditCardMenu();
    }

    static void loanMenu() {
        xtr.printHeading("LOAN");
        xtr.writeln("""
                1. Apply for a loan
                2. Current loan status
                3. Back to main menu""");
        switch (xtr.promptedTextInput("menu: ")) {
            case "1" -> newLoanMenu();
            case "2" -> accountLoanInfo();
            case "3" -> mainMenu();
        }
    }

    static void newLoanMenu() {
        xtr.printHeading("APPLY FOR A LOAN");
        xtr.writeln("""
                1. Personal
                2. Auto
                3. Mortgage
                4. Refinancing
                5. Back to loan menu""");
        switch (xtr.promptedTextInput("menu: ")) {
            case "1" -> personalLoanApplication();
            case "2" -> autoLoanApplication();
            case "3" -> mortgageApplication.apply();
            case "4" -> refinancingLoanApplication();
            case "5" -> loanMenu();
        }
    }

    static void personalLoanApplication() {
        xtr.printHeading("PERSONAL LOAN");
        xtr.notFound();
        newLoanMenu();
    }

    static void autoLoanApplication() {
        xtr.printHeading("AUTO LOAN");
        xtr.notFound();
        newLoanMenu();
    }

    static void refinancingLoanApplication() {
        xtr.printHeading("REFINANCING LOAN");
        xtr.notFound();
        newLoanMenu();
    }

    static void accountLoanInfo() {
        xtr.printHeading("LOAN STATUS");
        for (Credential cred : MainBank.creds) {
            if (cred.username == bank.curentUser) {
                if (cred.userProfile.loansData.mortgage == null) {
                    xtr.notFound();
                } else {
                    cred.userProfile.loansData.mortgage.print();
                }
            }
        }
        xtr.write("Exit?");
        if (xtr.confirm()) {
            loanMenu();
        } else {
            accountLoanInfo();
        }
    }

    static void accountInfoMenu() {
        for (Credential cred : MainBank.creds) {
            if (cred.username == bank.curentUser) {
                String[] profile = new String[4];
                profile[0] = cred.userProfile.name;
                profile[1] = cred.userProfile.phoneNum;
                profile[2] = cred.userProfile.NationalID;
                profile[3] = String.valueOf(cred.userProfile.salary);
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

                xtr.writeln(barTop);
                xtr.writeln(title);
                xtr.writeln(barBot);
                xtr.writeln(contentSpacing);

                int fitVarName = 0;
                for (String varNameElement : varName) {
                    if (fitVarName < varNameElement.length()) {
                        fitVarName = varNameElement.length();
                    }
                }
                int fitVarValue = 0;
                for (String varValueElement : profile) {
                    if (fitVarValue < varValueElement.length()) {
                        fitVarValue = varValueElement.length();
                    }
                }

                for (int i = 0; i < varName.length; i++) {
                    String var = String.format("%-" + fitVarName + "s: %-" + fitVarValue + "s", varName[i], profile[i]);
                    int padding = (heading - (side.length() * 2)
                            - (var.length() % 2 == 0 ? var.length() : (var.length() + 1))) / 2;
                    if (var.length() % 2 != 0) {
                        content = String.format("%s%" + padding + "s%s%" + padding + "s %s", side, " ", var, " ", side);
                    } else {
                        content = String.format("%s%" + padding + "s%s%" + padding + "s%s", side, " ", var, " ", side);
                    }
                    xtr.writeln(content);
                    xtr.writeln(contentSpacing);
                }
                xtr.writeln(barBot);
                xtr.write("Exit?\n");
                if (xtr.confirm()) {
                    mainMenu();
                } else {
                    accountInfoMenu();
                }
            }
        }
    }
    
    public static void quitMenu() {
        xtr.printHeading("QUIT SUCCESSFULLY");
    }
}
