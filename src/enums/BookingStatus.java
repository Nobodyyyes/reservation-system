package enums;

public enum BookingStatus {
    ACTIVE("Свободнен"),
    CANCELLED("Отмененен"),
    COMPLETED("Забронирован");

    private final String description;

    BookingStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
