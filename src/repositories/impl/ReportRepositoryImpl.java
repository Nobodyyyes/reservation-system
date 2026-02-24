package repositories.impl;

import models.RoomReportRow;
import repositories.ReportRepository;

import java.time.LocalDate;
import java.util.List;

public class ReportRepositoryImpl implements ReportRepository {

    @Override
    public List<RoomReportRow> getRoomReport(LocalDate from, LocalDate to) {
        return List.of();
    }
}
