package az.orient.hotelmgsystem.repository;

import az.orient.hotelmgsystem.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByActive(Integer active);

    Reservation findReservationByIdAndActive(Long id, Integer active);

    List<Reservation> findByRoomIdAndActiveAndCheckInDateBeforeAndCheckOutDateAfter(Long roomId, Integer active, Date checkOutDate, Date checkInDate);

}
