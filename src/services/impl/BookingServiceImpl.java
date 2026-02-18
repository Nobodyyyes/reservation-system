package services.impl;

import enums.BookingStatus;
import models.Booking;
import models.Room;
import repositories.BookingRepository;
import services.BookingService;
import services.RoomService;

import java.time.LocalDate;
import java.util.List;

public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    public BookingServiceImpl(BookingRepository bookingRepository, RoomService roomService) {
        this.bookingRepository = bookingRepository;
        this.roomService = roomService;
    }

    @Override
    public Booking create(Booking booking) {
        if (booking.getRoomId() == null) {
            Room room = roomService.getByNumber(booking.getRoomNumber());
            booking.setRoomId(room.getId());
            booking.setRoomNumber(room.getNumber());
        }

        if (booking.getStartDate() == null || booking.getEndDate() == null) {
            throw new IllegalArgumentException("Даты бронирования не заданы");
        }
        if (booking.getEndDate().isBefore(booking.getStartDate())) {
            throw new IllegalArgumentException("Дата окончания меньше даты начала");
        }

        if (!isRoomAvailable(booking.getRoomId(), booking.getStartDate(), booking.getEndDate())) {
            throw new RuntimeException("Комната занята на выбранные даты");
        }

        if (booking.getBookingStatus() == null) {
            booking.setBookingStatus(BookingStatus.ACTIVE);
        }

        return bookingRepository.save(booking);
    }

    @Override
    public Booking update(Booking booking) {
        if (!isRoomAvailableForUpdate(booking)) {
            throw new RuntimeException("Room is not available for the selected dates");
        }
        return bookingRepository.update(booking);
    }

    @Override
    public boolean delete(Long id) {
        return bookingRepository.delete(id);
    }

    @Override
    public Booking findById(Long id) {
        Booking booking = bookingRepository.findById(id);

        if (booking == null) {
            throw new RuntimeException("Booking by ID [%s] not found".formatted(id));
        }

        return booking;
    }

    @Override
    public List<Booking> getAll() {
        return bookingRepository.findAll();
    }

    @Override
    public boolean isRoomAvailable(Long roomId, LocalDate start, LocalDate end) {
        List<Booking> bookings = bookingRepository.findByRoomId(roomId);
        for (Booking b : bookings) {
            if (!(end.isBefore(b.getStartDate()) || start.isAfter(b.getEndDate()))) {
                return false;
            }
        }
        return true;
    }

    private boolean isRoomAvailableForUpdate(Booking booking) {
        List<Booking> bookings = bookingRepository.findByRoomId(booking.getRoomId());
        for (Booking b : bookings) {
            if (b.getId().equals(booking.getId())) continue;
            if (!(booking.getEndDate().isBefore(b.getStartDate()) || booking.getStartDate().isAfter(b.getEndDate()))) {
                return false;
            }
        }
        return true;
    }
}
