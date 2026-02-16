package models;

import enums.RoomStatus;
import enums.RoomType;

import java.time.LocalDateTime;

public class Room {

    private Long id;
    private String number;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private Double price;
    private LocalDateTime createdAt;

    public Room() {
    }

    public Room(Long id, String number, RoomType roomType, RoomStatus roomStatus, Double price, LocalDateTime createdAt) {
        this.id = id;
        this.number = number;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.price = price;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
