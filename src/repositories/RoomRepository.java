package repositories;

import enums.RoomStatus;
import models.Room;

import java.util.List;

public interface RoomRepository {

    Room save(Room room);

    Room update(Room room);

    boolean delete(Long id);

    Room findById(Long roomId);

    List<Room> findAll();

    List<Room> findByStatus(RoomStatus roomStatus);
}
