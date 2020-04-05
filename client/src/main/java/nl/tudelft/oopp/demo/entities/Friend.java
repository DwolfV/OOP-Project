package nl.tudelft.oopp.demo.entities;

public class Friend {

    private long id;
    private User user1;
    private User user2;

    public Friend(){

    }

    /**
     * Create a new Friendship.
     *
     * @param user1 - a user that is part of the friendship
     * @param user2 - the other user that is part of the friendship
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
