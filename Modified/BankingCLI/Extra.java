package BankingCLI;

import java.util.Scanner;

public class Extra {
    Scanner sc = new Scanner(System.in);

    public void printHeading(String prompt) {
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

    public void printPromptSplit(String prompt) {
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

    public void write(String s) {
        System.out.print(s);
    }

    public void writeln(String s) {
        System.out.println(s);
    }

    public String promptedTextInput(String prompt) {
        write(prompt);
        String out = sc.next();
        sc.nextLine();
        return out;
    }

    public double PowerOfExponent(double base, int exponent) {
        if (exponent == 0) {
            return 1;
        } else {
            return base * PowerOfExponent(base, exponent - 1);
        }
    }

    public void notFound() {
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

    public boolean confirm() {
        while (true) {
            write("Are you sure (y/n): ");
            String userInput = sc.next();
            if (userInput.equalsIgnoreCase("y")) {
                return true;
            } 
            if (userInput.equalsIgnoreCase("n")) {
                return false;
            }
            writeln("Please enter a valid input!");
        }
    }


}
