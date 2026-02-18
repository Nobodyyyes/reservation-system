package services.impl;

import enums.RoomStatus;
import models.Room;
import repositories.RoomRepository;
import services.RoomService;

import java.util.List;

public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Room create(Room newRoom) {

        if (newRoom.getNumber() == null || newRoom.getNumber().isBlank()) {
            throw new IllegalArgumentException("Номер комнаты не может быть пустым");
        }

        if (roomRepository.existsByNumber(newRoom.getNumber().trim())) {
            throw new RuntimeException("Комната с номером [%s] уже существует".formatted(newRoom.getNumber()));
        }

        Room room = new Room();
        room.setNumber(newRoom.getNumber());
        room.setRoomType(newRoom.getRoomType());
        room.setRoomStatus(newRoom.getRoomStatus());
        room.setPrice(newRoom.getPrice());
        room.setCreatedAt(newRoom.getCreatedAt());

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
    public Room getByNumber(String number) {
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Number is null");
        }

        Room room = roomRepository.findByNumberExact(number.trim());
        if (room == null) {
            throw new RuntimeException("Room by number [%s] not found".formatted(number));
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
    public void updateStatus(Long roomId, RoomStatus status) {
        roomRepository.updateStatus(roomId, status);
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
