package nl.tudelft.oopp.demo.repositories;

import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    List<RoomReservation> findByUserId(long userId);

    List<RoomReservation> findByUserIdAndRoomId(long userId, long roomId);

}
