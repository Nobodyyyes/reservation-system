package services.impl;

import enums.RoomStatus;
import enums.RoomType;
import models.Room;
import repositories.RoomRepository;
import services.RoomService;

import java.time.LocalDateTime;
import java.util.List;

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room create(String number, RoomType roomType, double price) {
        Room room = new Room();
        room.setNumber(number);
        room.setRoomType(roomType);
        room.setRoomStatus(RoomStatus.AVAILABLE);
        room.setPrice(price);
        room.setCreatedAt(LocalDateTime.now());

        return roomRepository.save(room);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @Override
    public Room getRoomById(Long id) {
        Room room = roomRepository.findById(id);

        if (room == null) {
            throw new RuntimeException("Room with ID [%s] not found".formatted(id));
        }

        return room;
    }

    @Override
    public Room updateRoom(Room room) {
        Room existing = roomRepository.findById(room.getId());
        if (existing == null) {
            throw new RuntimeException("Room not found");
        }
        return roomRepository.update(room);
    }

    @Override
    public List<Room> getRoomsByStatus(RoomStatus status) {
        return roomRepository.findByStatus(status);
    }

    @Override
    public boolean deleteRoom(Long roomId) {
        Room existing = roomRepository.findById(roomId);
        if (existing == null) {
            throw new RuntimeException("Room not found");
        }
        return roomRepository.delete(roomId);
    }

    public boolean isRoomAvailable(Long roomId) {
        Room room = roomRepository.findById(roomId);
        return room != null && room.getRoomStatus() == RoomStatus.AVAILABLE;
    }
}
