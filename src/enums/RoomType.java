package enums;

public enum RoomType {
    STANDARD("Стандарт", 1000),
    DELUXE("Делюкс", 5000),
    VIP("Вип", 10000);

    private final String description;
    private final int basePrice;

    RoomType(String description, int basePrice) {
        this.description = description;
        this.basePrice = basePrice;
    }

    @Override
    public String toString() {
        return description;
    }

    public int getBasePrice() {
        return basePrice;
    }
}
