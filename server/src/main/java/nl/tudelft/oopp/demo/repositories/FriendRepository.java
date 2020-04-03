package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Friend;
import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    @Query("select distinct f from Friend f"
            + " where f.user1.username = ?1 or f.user2.username = ?1")
    List<Friend> findByUsername(String username);

    Friend findByUser1AndUser2(User user1, User user2);
}
