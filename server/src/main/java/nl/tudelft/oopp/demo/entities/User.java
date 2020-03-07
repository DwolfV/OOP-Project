package nl.tudelft.oopp.demo.entities;

import com.sun.istack.NotNull;
import org.springframework.lang.Nullable;

import java.util.Date;
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
    @Column(name = "first_name")
    private String first_name;

    @NotNull
    @Column(name = "last_name")
    private String last_name;

    @Column(name = "birth_date")
    private Date birth_date;

    @NotNull
    @Column(name = "user_name", unique = true, nullable = false)
    private String username;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Nullable
    private Set<Event> events = new HashSet<>();

    public User() {
    }

    public User(String email, String role, String first_name, String last_name, Date birth_date, String username) {
        this.email = email;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
        this.username = username;
    }
    /**
     * Create a new User instance.
     *
     * @param id A unique ID for the User.
     * @param email The email of the User.
     * @param role The role of the User, like student, employee, admin.
     * @param first_name The first name of the User.
     * @param last_name The last name of the User.
     * @param birth_date The birth date of the User.
     */

    public User(long id, String email, String role, String first_name, String last_name, Date birth_date, String username) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
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

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                Objects.equals(getEmail(), user.getEmail()) &&
                Objects.equals(getRole(), user.getRole()) &&
                Objects.equals(getFirst_name(), user.getFirst_name()) &&
                Objects.equals(getLast_name(), user.getLast_name()) &&
                Objects.equals(getBirth_date(), user.getBirth_date()) &&
                Objects.equals(getUsername(), user.getUsername()) &&
                Objects.equals(getEvents(), user.getEvents());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", birth_date=" + birth_date +
                ", username='" + username + '\'' +
                ", events=" + events +
                '}';
    }
}
