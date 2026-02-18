package repositories.impl;

import enums.RoomStatus;
import enums.RoomType;
import models.Room;
import repositories.RoomRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepository {

    private final Connection connection;

    public RoomRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Room save(Room room) {
        String sql = "INSERT INTO rooms (number, room_type, room_status, price) VALUES (?, ?, ?, ?) RETURNING id";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, room.getNumber());
            ps.setString(2, room.getRoomType().name());
            ps.setString(3, room.getRoomStatus().name());
            ps.setDouble(4, room.getPrice());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                room.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room;
    }

    @Override
    public Room update(Room room) {
        String sql = "UPDATE rooms SET number=?, room_type=?, room_status=?, price=? WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, room.getNumber());
            ps.setString(2, room.getRoomType().name());
            ps.setString(3, room.getRoomStatus().name());
            ps.setDouble(4, room.getPrice());
            ps.setLong(5, room.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room;
    }

    @Override
    public boolean delete(Long id) {
        String sql = "DELETE FROM rooms WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByNumber(String number) {
        String sql = "SELECT COUNT(*) FROM rooms WHERE number = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }

    @Override
    public Room findById(Long roomId) {
        String sql = "SELECT * FROM rooms WHERE id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, roomId);
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
    public Room findByNumberExact(String number) {
        String sql = "SELECT * FROM rooms WHERE number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();
            return rs.next() ? mapRow(rs) : null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Room> findAll() {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms ORDER BY id";
        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                rooms.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    @Override
    public List<Room> findByStatus(RoomStatus roomStatus) {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE room_status=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, roomStatus.name());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rooms.add(mapRow(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return rooms;
    }

    private Room mapRow(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getLong("id"));
        room.setNumber(rs.getString("number"));
        room.setRoomType(RoomType.valueOf(rs.getString("room_type")));
        room.setRoomStatus(RoomStatus.valueOf(rs.getString("room_status")));
        room.setPrice(rs.getDouble("price"));
        room.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        return room;
    }
}
