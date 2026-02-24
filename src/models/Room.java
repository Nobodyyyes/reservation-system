package models;

import enums.ExtraService;
import enums.RoomStatus;
import enums.RoomType;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

public class Room {

    private Long id;
    private String number;
    private RoomType roomType;
    private RoomStatus roomStatus;
    private Double price;
    private LocalDateTime createdAt;
    private String extraServicesString;
    private Set<ExtraService> extraServices = EnumSet.noneOf(ExtraService.class);

    public Room() {
    }

    public Room(Long id,
                String number,
                RoomType roomType,
                RoomStatus roomStatus,
                Double price,
                LocalDateTime createdAt,
                String extraServicesString,
                Set<ExtraService> extraServices) {
        this.id = id;
        this.number = number;
        this.roomType = roomType;
        this.roomStatus = roomStatus;
        this.price = price;
        this.createdAt = createdAt;
        this.extraServicesString = extraServicesString;
        this.extraServices = extraServices;
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

    public String getExtraServicesString() {
        return extraServicesString;
    }

    public void setExtraServicesString(String extraServicesString) {
        this.extraServicesString = extraServicesString;
    }

    public Set<ExtraService> getExtraServices() {
        return extraServices;
    }

    public void setExtraServices(Set<ExtraService> extraServices) {
        this.extraServices = extraServices;
    }

    public String getExtraServicesStringStr() {
        if (extraServices == null || extraServices.isEmpty()) return "—";
        return extraServices.stream()
                .map(Objects::toString)
                .sorted()
                .collect(java.util.stream.Collectors.joining(", "));
    }
}
