package repositories.impl;

import enums.BookingStatus;
import models.Booking;
import repositories.BookingRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingRepositoryImpl implements BookingRepository {

    private final Connection connection;

    public BookingRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Booking save(Booking booking) {
        String sql = "INSERT INTO bookings (room_id, client_name, client_msisdn, start_date, end_date, booking_status, room_number, client_passport_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) RETURNING id, created_at";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, booking.getRoomId());
            ps.setString(2, booking.getClientName());
            ps.setString(3, booking.getClientMsisdn());
            ps.setDate(4, Date.valueOf(booking.getStartDate()));
            ps.setDate(5, Date.valueOf(booking.getEndDate()));
            ps.setString(6, booking.getBookingStatus().name());
            ps.setString(7, booking.getRoomNumber());
            ps.setString(8, booking.getClientPassportId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                booking.setId(rs.getLong("id"));
                booking.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    @Override
    public Booking update(Booking booking) {
        String sql = "UPDATE bookings SET room_id=?, client_name=?, client_msisdn=?, start_date=?, end_date=?, booking_status=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, booking.getRoomId());
            ps.setString(2, booking.getClientName());
            ps.setString(3, booking.getClientMsisdn());
            ps.setDate(4, Date.valueOf(booking.getStartDate()));
            ps.setDate(5, Date.valueOf(booking.getEndDate()));
            ps.setString(6, booking.getBookingStatus().name());
            ps.setLong(7, booking.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booking;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM bookings WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Booking findById(Long id) {
        String sql = "SELECT * FROM bookings WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRow(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Booking> findByRoomId(Long roomId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE room_id=? ORDER BY start_date";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, roomId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookings.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookings;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings ORDER BY id";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                bookings.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return bookings;
    }

    private Booking mapRow(ResultSet rs) throws SQLException {
        Booking booking = new Booking();
        booking.setId(rs.getLong("id"));
        booking.setRoomId(rs.getLong("room_id"));
        booking.setRoomNumber(rs.getString("room_number"));
        booking.setClientName(rs.getString("client_name"));
        booking.setClientMsisdn(rs.getString("client_msisdn"));
        booking.setStartDate(rs.getDate("start_date").toLocalDate());
        booking.setEndDate(rs.getDate("end_date").toLocalDate());
        booking.setBookingStatus(BookingStatus.valueOf(rs.getString("booking_status")));
        booking.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        booking.setClientPassportId(rs.getString("client_passport_id"));
        return booking;
    }
}
