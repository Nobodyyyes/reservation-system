package services.impl;

import enums.BookingStatus;
import models.Booking;
import repositories.BookingsRepository;
import services.BookingService;

import java.time.LocalDate;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingsRepository bookingsRepository;

    public BookingServiceImpl(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public Booking create(Booking booking) {
        if (!isRoomAvailable(booking.getRoomId(), booking.getStartDate(), booking.getEndDate())) {
            throw new RuntimeException("Room is not available for the selected dates");
        }
        booking.setBookingStatus(BookingStatus.ACTIVE);
        return bookingsRepository.save(booking);
    }

    @Override
    public Booking update(Booking booking) {
        if (!isRoomAvailableForUpdate(booking)) {
            throw new RuntimeException("Room is not available for the selected dates");
        }
        return bookingsRepository.update(booking);
    }

    @Override
    public boolean delete(Long id) {
        return bookingsRepository.delete(id);
    }

    @Override
    public Booking findById(Long id) {
        Booking booking = bookingsRepository.findById(id);

        if (booking == null) {
            throw new RuntimeException("Booking by ID [%s] not found".formatted(id));
        }

        return booking;
    }

    @Override
    public List<Booking> getAll() {
        return bookingsRepository.findAll();
    }

    @Override
    public boolean isRoomAvailable(Long roomId, LocalDate start, LocalDate end) {
        List<Booking> bookings = bookingsRepository.findByRoomId(roomId);
        for (Booking b : bookings) {
            if (!(end.isBefore(b.getStartDate()) || start.isAfter(b.getEndDate()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isRoomAvailableForUpdate(Booking booking) {
        List<Booking> bookings = bookingsRepository.findByRoomId(booking.getRoomId());
        for (Booking b : bookings) {
            if (b.getId().equals(booking.getId())) continue;
            if (!(booking.getEndDate().isBefore(b.getStartDate()) || booking.getStartDate().isAfter(b.getEndDate()))) {
                return false;
            }
        }
        return true;
    }
}
