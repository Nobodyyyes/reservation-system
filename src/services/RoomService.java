package services;

import enums.RoomStatus;
import enums.RoomType;
import models.Room;

import java.util.List;

public interface RoomService {

    Room create(String number, RoomType roomType, double price);

    List<Room> getAllRooms();

    Room getRoomById(Long id);

    Room updateRoom(Room room);

    List<Room> getRoomsByStatus(RoomStatus status);

    boolean deleteRoom(Long roomId);
}
