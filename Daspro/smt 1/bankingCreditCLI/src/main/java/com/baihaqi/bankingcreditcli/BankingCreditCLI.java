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
    static String[][] newCred(String[][] old) {
        String[][] credential = new String[old.length+1][old[0].length];
        for (int row = 0; row < old.length; row++) {
            for (int col = 0; col < old[row].length; col++) {
                credential[row][col] = old[row][col];
            }
        }
        return credential;
    }
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
    static void registerMenu(String[][] list) {
        printHeading("REGISTER");
        String[][] credential = newCred(list);
        System.out.print("Enter your username: ");
        credential[credential.length-1][0] = input.next();
        System.out.print("Enter your password: ");
        credential[credential.length-1][1] = input.next();
        loginMenu(credential);
    }
    static void loginMenu(String[][] list) {
        printHeading("LOGIN");
        String prompt = "If you don't already have an account please type \"register\" in the username input";
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
        if (!attemptLogin(list)) {
            registerMenu(list);
        }
    }
    static boolean attemptLogin(String[][] list) {
        String username = usernameCheck(list);
        if (!username.equalsIgnoreCase("register")) {
            boolean password = passwordCheck(list, username);
            if (password) {
                mainMenu();
            } else {
                if (!attemptLogin(list)) {
                    registerMenu(list);
                }
            }
            return true;
        } return false;
    }
    static void mainMenu() {
        printHeading("MENU");
        System.out.println("""
                           1. Credit card menu
                           2. Loan menu
                           3. Account information""");
    }
    static String usernameCheck(String[][] list) {
        while (true) {
            System.out.print("Username: ");
            String userInput = input.next();
            for (String[] strings : list) {
                if (strings[0] == null)
                    continue;
                if (strings[0].equals(userInput))
                    return userInput;
                if (userInput.equalsIgnoreCase("register"))
                    return userInput;
            }
                System.out.println("""
                        The username you've entered doesn't exist in our system
                        pleas re-enter your username correctly""");
        }
    }
    static boolean passwordCheck(String[][] list, String username) {
        int limit = 0;
        while (limit < 3) {
            System.out.print("Password: ");
            String userinput = input.next();
            for (String[] strings : list) {
                if (strings[0].equals(username)) {
                    if (strings[1].equals(userinput)) {
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
}
