package enums;

public enum RoomStatus {
    AVAILABLE("Доступно"),
    MAINTENANCE("Занято");

    private final String description;

    RoomStatus(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
