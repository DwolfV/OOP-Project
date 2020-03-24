package nl.tudelft.oopp.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.lang.Nullable;

@Entity
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @NotNull
    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "role")
    private String role;

    @NotNull
    @Column(name = "firstName")
    private String firstName;

    @NotNull
    @Column(name = "lastName")
    private String lastName;

    @NotNull
    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Nullable
    private Set<Event> events = new HashSet<>();

    public User() {
    }


    /**
     * Creates a new instance of the User entity.
     * @param email - The email of the user.
     * @param role - The role of the user (for example: student, admin etc.)
     * @param firstName - The first name of the user.
     * @param lastName - The last name of the user.
     * @param username - The username.
     */

    public User(String email, String role, String firstName, String lastName, String username) {
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    /**
     * Create a new User instance.
     *
     * @param id         A unique ID for the User.
     * @param email      The email of the User.
     * @param role       The role of the User, like student, employee, admin.
     * @param firstName The first name of the User.
     * @param lastName  The last name of the User.
     */

    public User(long id, String email, String role, String firstName, String lastName, String username) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
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

    @JsonIgnore
    @Nullable
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(@Nullable Set<Event> events) {
        this.events = events;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
        return getId() == user.getId()
            && Objects.equals(getEmail(), user.getEmail())
            && Objects.equals(getRole(), user.getRole())
            && Objects.equals(getFirstName(), user.getFirstName())
            && Objects.equals(getLastName(), user.getLastName())
            && Objects.equals(getUsername(), user.getUsername())
            && Objects.equals(getEvents(), user.getEvents());
    }

    @Override
    public String toString() {
        return "User{"
            + "id=" + id
            + ", email='" + email + '\''
            + ", role='" + role + '\''
            + ", first_name='" + firstName + '\''
            + ", last_name='" + lastName + '\''
            + ", username='" + username + '\''
            + ", events=" + events
            + '}';
    }
}
