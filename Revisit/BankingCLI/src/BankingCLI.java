import java.util.Scanner;

public class BankingCLI {
    //#region variable 
    public static Scanner input = new Scanner(System.in);
    public static int width = 32;
    public static String sideFrame = "||";
    public static String barFrame = "==";
    public static String blankString = " ";
    public static int label;
    //#endregion
    //#region Prompted Input 
    public static String promtedUserInputString(String prompt) {
        System.out.print(prompt);
        String userInput = input.nextLine();
        input.nextLine();
        return userInput;
    }
    public static int promtedUserInputInt(String prompt) {
        System.out.print(prompt);
        int userInput = input.nextInt();
        input.nextLine();
        return userInput;
    }
    //#endregion
    public static boolean confirm() {
        while (true) {
            String userInput = promtedUserInputString("Are you sure (Y/n) : ");
            if (userInput.equalsIgnoreCase("n")) {
                return false;
            } else {
                return true;
            }
        }
    }
    public static void setWidth() {
        System.out.print("Enter the width of your CLI or expected max-width of the print \ninputed max-width will be rounded to be even \n");
        do width = promtedUserInputInt("width: "); while (!confirm());
    }
    public static void printBar() {
        int barLength = ((barFrame.length() * width) - (2 * sideFrame.length())) / barFrame.length();
        System.out.print(sideFrame);
        for (int i = 0; i < barLength; i++) {
            System.out.print(barFrame);
        }
        System.out.print(sideFrame);
        System.out.println();
    }
    public static void printHeader(String headerName) {
        int headerLength = (headerName.length() % 2 == 0) ? headerName.length() : headerName.length() + 1;
        int gap = ((barFrame.length() * width) - (2 * sideFrame.length()) - headerLength) / 2;
        String base = String.format("%s%%%ds%%%ds%%%ds%s", sideFrame, gap, headerLength, gap, sideFrame);
        String header = String.format(base, blankString, headerName, blankString);
        printBar();
        System.out.println(header);
        printBar();
    }
    public static void printEnumerate(String item) {
        label += 1;
        int itemLength = (barFrame.length() * width) - (2 * sideFrame.length()) - (2 * blankString.length()) - String.format("%2d", label).length() - ".".length();
        String format = String.format("%s%s%2d.%s%%-%ds%s%n", sideFrame, blankString, label, blankString, itemLength, sideFrame);
        System.out.printf(format, item);
    }
    public static void menuExit() {
        printHeader("EXIT SUCCESSFUL");
    }
    public static void menuLogin() {
        printHeader("LOGIN");
        
    }
    public static void menuWelcome() {
        printHeader("Welcome");
        label = 0;
        printEnumerate("Login");
        printEnumerate("Register");
        label = -1;
        printEnumerate("Exit");
        printBar();
        int menu = promtedUserInputInt("menu: ");
        switch (menu) {
            case 1 -> {}
            case 2 -> {}
            case 0 -> menuExit();
        }
    }
    public static void main(String[] args) {
        // setWidth();
        menuWelcome();
    }
}
