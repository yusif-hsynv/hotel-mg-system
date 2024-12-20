package az.orient.hotelmgsystem.repository;

import az.orient.hotelmgsystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    List<Room> findAllByActive(Integer active);

    Room findRoomByIdAndActive(Long id, Integer active);
}
