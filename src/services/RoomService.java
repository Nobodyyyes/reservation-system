package services;

import enums.RoomStatus;
import models.Room;

import java.util.List;

public interface RoomService {

    Room create(Room newRoom);

    List<Room> getAllRooms();

    Room getRoomById(Long id);

    Room updateRoom(Room room);

    List<Room> getRoomsByStatus(RoomStatus status);

    boolean deleteRoom(Long roomId);
}
