package nl.tudelft.oopp.demo.helperclasses;

public class User {

    private long id;
    private String email;
    private String role;
    private String first_name;
    private String last_name;

    public User(){

    }

    /**
     * Create a new User instance.
     *
     * @param id A unique ID for the User.
     * @param email The email of the User.
     * @param role The role of the User, like student, employee, admin.
     * @param first_name The first name of the User.
     * @param last_name The last name of the User.
     */
    public User(long id,
                String email,
                String role,
                String first_name,
                String last_name) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
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

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId();
    }

    @Override
    public String toString() {
        return "User{"
                + "id=" + id
                + ", email='" + email + '\''
                + ", role='" + role + '\''
                + ", first_name='" + first_name + '\''
                + ", last_name='" + last_name + '\''
                + '}';
    }
}
