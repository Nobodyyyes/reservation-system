package services;

import models.Booking;
import models.Room;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking create(Booking booking);

    Booking update(Booking booking);

    boolean cancel(Long id);

    Booking findById(Long id);

    List<Booking> getAll();

    List<Room> getAvailableRooms(LocalDate start, LocalDate end);

    boolean isRoomAvailable(Long roomId, LocalDate start, LocalDate end);
}
