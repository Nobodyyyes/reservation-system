package repositories;

import models.RoomReportRow;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository {

    List<RoomReportRow> getRoomReport(LocalDate from, LocalDate to);
}
