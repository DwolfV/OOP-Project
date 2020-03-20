package nl.tudelft.oopp.demo.helperclasses;

public class UserInfo {
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    /**
     * Create an instance of the helper class UserInfo.
     * @param email The email of the user.
     * @param role The role of the user - for now only USER and ADMIN.
     * @param firstName The first name of the user.
     * @param lastName The last name of the user.
     * @param username The username.
     * @param password The password.
     */
    public UserInfo(String email, String role, String firstName, String lastName, String username, String password) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
