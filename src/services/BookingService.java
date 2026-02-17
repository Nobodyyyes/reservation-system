package services;

import models.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {

    Booking create(Booking booking);

    Booking update(Booking booking);

    boolean delete(Long id);

    Booking findById(Long id);

    List<Booking> getAll();

    boolean isRoomAvailable(Long roomId, LocalDate start, LocalDate end);
}
