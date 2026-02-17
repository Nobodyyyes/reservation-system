package enums;

public enum BookingStatus {
    ACTIVE("Активный "),
    CANCELLED("Отмененный"),
    COMPLETED("Забронированный");

    private final String description;

    BookingStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
