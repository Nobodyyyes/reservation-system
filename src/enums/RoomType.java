package enums;

public enum RoomType {
    STANDARD("Стандарт"),
    DELUXE("Делюкс"),
    VIP("Вип");

    private final String description;

    RoomType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
