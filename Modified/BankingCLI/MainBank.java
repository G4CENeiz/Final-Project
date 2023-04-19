package BankingCLI;

import BankingCLI.User.*;

public class MainBank {
    public static Credential[] creds = new Credential[1];
    static Login login = new Login();
    public String curentUser;

    public void expandCredential() {
        Credential[] old = creds;
        creds = new Credential[creds.length + 1];
        for (int i = 0; i < old.length; i++) {
            creds[i] = old[i];
        }
    }

    public static void main(String[] args) {
        /*
        creds[0].username = "admin";
        creds[0].password = "admin";
        creds[0].userProfile = new Profile("Admin", "", "", 0);
         */
        creds[0] = new Credential("admin", "admin");
        creds[0].userProfile = new Profile("Admin", "", "", 0);
        login.loginMenu();
    }
}
