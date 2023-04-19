package BankingCLI;

import BankingCLI.User.*;
import java.util.Scanner;


public class Login {
    MainBank        bank        = new MainBank();
    Extra           xtr         = new Extra();
    Scanner         sc          = new Scanner(System.in);

    public String usernameCheck() {
        while (true) {
            xtr.write("Username: ");
            String userInput = sc.next();
            for (Credential cred : MainBank.creds) {
                if (cred.username == null)
                    continue;
                if (cred.username.equals(userInput))
                    return userInput;
                if (userInput.equalsIgnoreCase("register"))
                    return userInput;
                if (userInput.equalsIgnoreCase("quit"))
                    return userInput;
            }
            xtr.printPromptSplit(
                    "The username you've entered doesn't exist in our system please re-enter your username correctly");
        }
    }

    public boolean passwordCheck(String username) {
        int limit = 0;
        while (limit < 3) {
            xtr.write("Password: ");
            String userInput = sc.next();
            for (Credential cred : MainBank.creds) {
                if (cred.username.equals(username)) {
                    if (cred.password.equals(userInput)) {
                        return true;
                    }
                }
            }
            if (limit < 1)
                xtr.writeln("Wrong password");
            if (limit == 1)
                xtr.writeln("""
                        Wrong password, Last attempt
                        if you fail again, you would need to re-enter your username""");
            limit++;
        }
        return false;
    }

    public boolean validatePhoneNumber(String numbers) {
        /*
         * 0895388899808 -> 13 digits
         * 082336750134 -> 12 digits
         * 08912888374 -> 11 digits
         */
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

    public boolean validateIDCardNumber(String numbers) {
        /*
         * 3573051004040001
         * 3573056204040001
         */
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

    public boolean attemptLogin() {
        String username = usernameCheck();
        if (!(username.equalsIgnoreCase("register") || username.equalsIgnoreCase("quit"))) {
            boolean password = passwordCheck(username);
            if (password) {
                bank.curentUser = username;
                for (Credential cred : MainBank.creds) {
                    if (cred.username == username && cred.userProfile == null) {
                        setProfile();
                    }
                }
                Menu.mainMenu();
            } else {
                if (attemptLogin()) {
                    if (username.equalsIgnoreCase("register"))
                        registerMenu();
                    if (username.equalsIgnoreCase("quit"))
                        Menu.quitMenu();
                }
            }
            return false;
        }
        return true;
    }

    public void loginMenu() {
        xtr.printHeading("LOGIN");
        xtr.printPromptSplit(
                "If you don't already have an account please type \"register\" in the username input, if you want to quit type \"quit\" in the username input");
        if (attemptLogin()) {
            if (bank.curentUser.equalsIgnoreCase("register"))
                registerMenu();
            if (bank.curentUser.equalsIgnoreCase("quit"))
                Menu.quitMenu();
        }
    }

    public void registerMenu() {
        xtr.printHeading("REGISTER");
        bank.expandCredential();
        MainBank.creds[MainBank.creds.length-1].username = xtr.promptedTextInput("Enter your username: ");
        MainBank.creds[MainBank.creds.length-1].password = xtr.promptedTextInput("Enter your password: ");
        loginMenu();
    }

    public void setProfile() {
        String name, phoneNum, nationalID; 
        double salary;
        boolean sure;
        boolean invalid;

        sure = xtr.confirm();
        do{
            sc.nextLine();
            xtr.write("Enter your name: ");
            name = sc.nextLine();
        } while (!sure);

        sure = xtr.confirm();
        do{
            invalid = true;
            do {
                xtr.write("Enter your phone number: ");
                phoneNum = sc.next();
                if (validatePhoneNumber(phoneNum)) {
                    invalid = false;
                } else {
                    xtr.writeln("Please enter a valid phone number");
                }
            } while (invalid);
        } while (!sure);


        xtr.writeln("Please enter your ID card number in this format");
        xtr.writeln("example: 3573052004691337");
        sure = xtr.confirm();
        do {
            invalid = true;
            do {
                xtr.write("Enter your ID card number: ");
                nationalID = sc.next();
                if (validateIDCardNumber(nationalID)) {
                    invalid = false;
                } else {
                    xtr.writeln("Please enter a valid ID card number");
                }
            } while (invalid);
        } while (!sure);

        sure = xtr.confirm();
        do {
            salary = Double.parseDouble(xtr.promptedTextInput("Enter your salary: "));
        } while (!sure);

        if (xtr.confirm()) {
            for (Credential cred : MainBank.creds) {
                if (cred.username == bank.curentUser) {
                    cred.userProfile = new Profile(name, phoneNum, nationalID, salary);
                    xtr.writeln(name);
                    xtr.writeln(phoneNum);
                    xtr.writeln(nationalID);
                    xtr.writeln(String.valueOf(salary));
                }
            }
        } else {
            setProfile();
        }
    }
}
