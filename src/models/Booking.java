package models;

import enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Booking {

    private Long id;
    private Long roomId;
    private String roomNumber;
    private String clientName;
    private String clientMsisdn;
    private String clientPassportId;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookingStatus bookingStatus;
    private LocalDateTime createdAt;

    public Booking() {
    }

    public Booking(Long id,
                   Long roomId,
                   String roomNumber,
                   String clientName,
                   String clientMsisdn,
                   String clientPassportId,
                   LocalDate startDate,
                   LocalDate endDate,
                   BookingStatus bookingStatus,
                   LocalDateTime createdAt) {
        this.id = id;
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.clientName = clientName;
        this.clientMsisdn = clientMsisdn;
        this.clientPassportId = clientPassportId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookingStatus = bookingStatus;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientMsisdn() {
        return clientMsisdn;
    }

    public void setClientMsisdn(String clientMsisdn) {
        this.clientMsisdn = clientMsisdn;
    }

    public String getClientPassportId() {
        return clientPassportId;
    }

    public void setClientPassportId(String clientPassportId) {
        this.clientPassportId = clientPassportId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
