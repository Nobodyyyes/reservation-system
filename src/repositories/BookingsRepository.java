package repositories;

import models.Booking;

import java.util.List;

public interface BookingsRepository {

    Booking save(Booking booking);

    Booking update(Booking booking);

    boolean delete(Long id);

    Booking findById(Long id);

    List<Booking> findByRoomId(Long roomId);

    List<Booking> findAll();
}
