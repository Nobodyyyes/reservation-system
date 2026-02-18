package panels.bookings;

import models.Booking;
import panels.bookings.dialogs.CreateBookingDialog;
import services.BookingService;
import services.RoomService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookingPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final BookingService bookingService;
    private final RoomService roomService;

    private List<Booking> bookings = List.of();

    public BookingPanel(BookingService bookingService, RoomService roomService) {
        this.bookingService = bookingService;
        this.roomService = roomService;

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "ID комнаты",
                        "Номер комнаты",
                        "Имя клиента",
                        "Номер клиента",
                        "Дата бронирования",
                        "Конец даты бронирования",
                        "Статус",
                        "Дата создания",
                        "Действие"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 9;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tableSettings(table);
        centerSpecificationColumns(table);

        JButton createBtn = new JButton("Создать бронь");

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftTopPanel.add(createBtn);

        topPanel.add(leftTopPanel, BorderLayout.WEST);

        add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        loadBookings();

        createBtn.addActionListener(e -> createBooking());
    }

    private void tableSettings(JTable table) {
        table.getColumn("Действие").setCellRenderer(new BookingActionButtonRenderer());
        table.getColumn("Действие").setCellEditor(new BookingActionButtonEditor(table, bookingService, this));
        table.getColumn("Действие").setPreferredWidth(220);
        table.getColumn("Действие").setMinWidth(180);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setWidth(0);
        table.setRowHeight(30);
    }

    private void centerSpecificationColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumn("Номер комнаты").setCellRenderer(centerRenderer);
        table.getColumn("Имя клиента").setCellRenderer(centerRenderer);
        table.getColumn("Номер клиента").setCellRenderer(centerRenderer);
        table.getColumn("Дата бронирования").setCellRenderer(centerRenderer);
        table.getColumn("Конец даты бронирования").setCellRenderer(centerRenderer);
        table.getColumn("Статус").setCellRenderer(centerRenderer);
        table.getColumn("Дата создания").setCellRenderer(centerRenderer);
    }

    public void loadBookings() {
        try {
            bookings = bookingService.getAll();
            renderBookings(bookings);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка загрузки бронирований: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void renderBookings(List<Booking> bookings) {
        tableModel.setRowCount(0);
        for (Booking booking : bookings) {
            tableModel.addRow(new Object[]{
                    booking.getId(),
                    booking.getRoomId(),
                    booking.getRoomNumber(),
                    booking.getClientName(),
                    booking.getClientMsisdn(),
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getBookingStatus(),
                    booking.getCreatedAt(),
                    ""
            });
        }
    }

    private void createBooking() {
        CreateBookingDialog dialog = new CreateBookingDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                bookingService,
                roomService
        );

        Booking newBooking = dialog.showDialog();
        if (newBooking != null) {
            loadBookings();
        }
    }
}
