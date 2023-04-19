package BankingCLI.User;

public class Credential {
    public String username;
    public String password;
    public Profile userProfile;

    public Credential() {
        
    }
    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Credential(Profile userProfile) {
        this.userProfile = userProfile;
    }
}
