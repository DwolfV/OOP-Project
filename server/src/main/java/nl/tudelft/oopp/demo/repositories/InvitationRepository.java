package nl.tudelft.oopp.demo.repositories;

import java.util.List;
import nl.tudelft.oopp.demo.entities.Invitation;
import nl.tudelft.oopp.demo.entities.RoomReservation;
import nl.tudelft.oopp.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    List<Invitation> findByRoomReservationId(long id);

    @Query("select i from Invitation i " +
            "where i.guest.username = ?1")
    List<Invitation> findByGuestUsername(String name);

    Invitation findByRoomReservationAndGuest(RoomReservation roomReservation, User user);
}
