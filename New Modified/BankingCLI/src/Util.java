import java.util.Scanner;

public class Util {
    static Scanner sc = new Scanner(System.in);
    
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
}
