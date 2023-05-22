public class User {
    String firstName,
        lastName,
        nationalId,
        phoneNumber,
        username,
        password;
    double salary;
    boolean admin;

    public User(String firstName, String lastName, String nationalId, String phoneNumber, double salary, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.username = username;
        this.password = password;
    }
}
