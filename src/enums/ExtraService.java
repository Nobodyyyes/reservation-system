package enums;

public enum ExtraService {
    FOOD("Питание", 2000),
    FREE_WIFI("Бесплатный Wi-fi", 500),
    PARKING("Парковка", 1000);

    private final String description;
    private final int price;

    ExtraService(String description, int price) {
        this.description = description;
        this.price = price;
    }

    @Override
    public String toString() {
        return description;
    }

    public int getPrice() {
        return price;
    }
}
