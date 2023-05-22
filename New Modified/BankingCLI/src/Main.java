import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static User[] users = new User[1];
    static String currentUser;

    public static void printHeading(String input) {
        int promptLen = input.length() % 2 == 0 ? input.length() : input.length() + 1;
        String prompt = String.format("%"+ promptLen +"s", input);
        
        String bar = "================================================================";
        String side = "||";
        
        int gapAmount = ((bar.length() - side.length() - prompt.length() - side.length()) / 2);
        String gap = "";
        for (int i = 0; i < gapAmount; i++) {
            gap = gap + " ";
        }
        
        String title = String.format("%s%s%s%s%s", side, gap, prompt, gap, side);
        System.out.println(bar);
        System.out.print(title);
        System.out.println(bar);
    }

    public static String promptedLineInput(String prompt) {
        System.out.println(prompt);
        String out = sc.nextLine();
        sc.nextLine();
        return out;
    }
    
    public static String promptedStringInput(String prompt) {
        System.out.println(prompt);
        String out = sc.next();
        sc.nextLine();
        return out;
    }

    public static int promptedIntInput(String prompt) {
        System.out.println(prompt);
        int out = sc.nextInt();
        return out;
    }

    public static double promptedDoubleInput(String prompt) {
        System.out.println(prompt);
        double out = sc.nextDouble();
        return out;
    }

    public static void notFound() {
        printHeading("PAGE NOT FOUND");
    }

    public static boolean confirm() {
        while (true) {
            System.out.println("Are you sure?");
            System.out.print("(Y/n) : ");
            String option = String.format("%s", sc.nextLine().charAt(0));
            sc.nextLine();
            if (option.equalsIgnoreCase("y")) return true;
            if (option.equalsIgnoreCase("n")) return false;
            System.out.println("Invalid Input");
        }
    }
    
    public static void loginMenu() {
        printHeading("LOGIN");
        String username = checkUsername();
        if (passwordAttempt(username)) {
            currentUser = username;
        }
    }
    
    public static String checkUsername() {
        while (true) {
            String userInput = promptedStringInput("Username: ");
            for (User user : users) if (user.username.equals(userInput)) return userInput;
            System.out.println("Username doesn't ");
        }
    }
    
    public static boolean passwordAttempt(String username) {
        int limit = 3;
        for (int i = 0; i < limit; i++) {
            String userInput = promptedStringInput("Password: ");
            if (passwordCheck(username, userInput)) return true;
            if (i < 1) System.out.println("Wrong Password");
            if (i == 1) System.out.println("Wrong Password, last attempt");
        }
        return false;
    }
    
    public static boolean passwordCheck(String username, String userInput) {
        for (User user : users) if (user.username.equals(username)) if (user.password.equals(userInput)) return true;
        return false;
    }

    public static void exit() {
        printHeading("Exit");
        System.out.println("Exit Succesfully");
    }
    
    public static void startMenu() {
        printHeading("WELCOME");
        System.out.println("1. Sign in");
        System.out.println("2. Sign up");
        System.out.println("3. Exit");
        String option = String.format("%s", promptedStringInput("Option: ").charAt(0));
        switch (option) {
            case "1" -> loginMenu();
            case "3" -> exit();
        }
    }
    
    public static void main(String[] args) {
        users[0] = new User("admin", "admin", "0000000000000000", "000000000000", 5_000_000, "admin", "admin");
    }
}
