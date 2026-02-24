package models;

public record RoomReportRow(
        long roomId,
        String roomNumber,
        String roomType,
        String roomStatus,
        long bookingsCount,
        long totalNights,
        double revenue,
        double avgNights,
        long activeCount,
        long cancelledCount,
        long completedCount
) {
}
