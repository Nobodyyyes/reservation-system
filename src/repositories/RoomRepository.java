package repositories;

import enums.RoomStatus;
import models.Room;

import java.util.List;

public interface RoomRepository {

    Room save(Room room);

    Room update(Room room);

    boolean delete(Long id);

    boolean existsByNumber(String number);

    Room findById(Long roomId);

    Room findByNumberExact(String number);

    List<Room> findAll();

    List<Room> findByStatus(RoomStatus roomStatus);

    void updateStatus(Long roomId, RoomStatus status);
}
