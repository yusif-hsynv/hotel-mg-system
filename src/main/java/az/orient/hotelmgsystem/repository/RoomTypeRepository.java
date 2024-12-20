package az.orient.hotelmgsystem.repository;

import az.orient.hotelmgsystem.entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Long> {
    List<RoomType> findAllByActive(Integer active);

    RoomType findRoomTypeByIdAndActive(Long id, Integer active);
}
