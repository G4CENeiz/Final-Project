public class Login {
    public static User[] users;
    static String currentUser;

    private static boolean passwordCheck(String username, String userInput) {
        for (User user : users) if (user.username.equals(username)) if (user.password.equals(userInput)) return true;
        return false;
    }

    private static boolean passwordAttempt(String username) {
        int limit = 3;
        for (int i = 0; i < limit; i++) {
            String userInput = Util.promptedStringInput("Password: ");
            if (passwordCheck(username, userInput)) return true;
            if (i < 1) System.out.println("Wrong Password");
            if (i == 1) System.out.println("Wrong Password, last attempt");
        }
        return false;
    }

    private static String checkUsername() {
        while (true) {
            String userInput = Util.promptedStringInput("Username: ");
            for (User user : users) if (user.username.equals(userInput)) return userInput;
            System.out.println("Username doesn't ");
        }
    }
    
    public static void loginMenu() {
        Util.printHeading("LOGIN");
        String username = checkUsername();
        if (passwordAttempt(username)) {
            currentUser = username;
        }
    }
}
