package nl.tudelft.oopp.demo.entities;


import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "Friend")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user1", referencedColumnName = "id")
    private User user1;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user2", referencedColumnName = "id")
    private User user2;

    /**
     * Empty constructor.
     */
    public Friend() {

    }

    /**
     * Constructor for the class Friend.
     *
     * @param user1 - the one of the users that is part of the friendship
     * @param user2 - the other user who is part of he friendship
     */
    public Friend(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Friend friend = (Friend) o;
        return id == friend.id;
    }

    @Override
    public String toString() {
        return "Friend{"
                + "id=" + id
                + ", user1=" + user1
                + ", user2=" + user2
                + '}';
    }
}
