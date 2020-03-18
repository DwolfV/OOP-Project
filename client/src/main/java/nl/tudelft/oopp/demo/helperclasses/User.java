package nl.tudelft.oopp.demo.helperclasses;

public class User {

    private long id;
    private String email;
    private String role;
    private String firstName;
    private String lastName;

    public User(){

    }

    /**
     * Create a new User instance.
     *
     * @param email The email of the User.
     * @param role The role of the User, like student, employee, admin.
     * @param firstName The first name of the User.
     * @param lastName The last name of the User.
     */
    public User(String email,
                String role,
                String firstName,
                String lastName) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", email='" + email + '\''
                + ", role='" + role + '\''
                + ", first_name='" + firstName + '\''
                + ", last_name='" + lastName + '\''
                + '}';
    }
}
