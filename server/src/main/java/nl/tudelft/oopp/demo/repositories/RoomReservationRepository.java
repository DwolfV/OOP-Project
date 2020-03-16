package nl.tudelft.oopp.demo.repositories;

import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;

import nl.tudelft.oopp.demo.entities.RoomReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    List<RoomReservation> findByUserId(long userId);

    List<RoomReservation> findByUserIdAndRoomId(long userId, long roomId);

    List<RoomReservation> findByDate(Date date);

    List<RoomReservation> findByDateAndRoomId(Date date, long roomId);

    @Query("SELECT startTime, endTime FROM RoomReservation WHERE room_id = :room_id AND date = :date")
    List<Object[]> findStartAndEndTimesByRoomIdAndDate(@Param("room_id") long roomId, @Param("date") Date date);

}
