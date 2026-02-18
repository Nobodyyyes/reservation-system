package panels.bookings;

import models.Booking;
import panels.bookings.dialogs.CancelBookingDialog;
import panels.bookings.dialogs.EditBookingDialog;
import services.BookingService;

import javax.swing.*;
import java.awt.*;

public class BookingActionButtonEditor extends DefaultCellEditor {

    private final JPanel panel = new JPanel();
    private final JTable table;
    private final BookingService bookingService;
    private final BookingPanel bookingPanel;

    private Booking currentBooking;

    public BookingActionButtonEditor(JTable table, BookingService bookingService, BookingPanel bookingPanel) {
        super(new JCheckBox());
        this.table = table;
        this.bookingService = bookingService;
        this.bookingPanel = bookingPanel;

        JButton btnEdit = new JButton("Изменить");
        JButton btnCancel = new JButton("Отменить");

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.add(btnEdit);
        panel.add(btnCancel);

        btnEdit.addActionListener(e -> createBooking());
        btnCancel.addActionListener(e -> cancelBooking());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {

        Long bookingId = (Long) table.getValueAt(row, 0);
        currentBooking = bookingService.findById(bookingId);

        return panel;
    }

    private void createBooking() {
        if (currentBooking == null) {
            JOptionPane.showMessageDialog(table,
                    "Не удалось определить бронь для редактирования",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            fireEditingStopped();
        }

        EditBookingDialog dialog = new EditBookingDialog(
                (Frame) SwingUtilities.getWindowAncestor(table),
                bookingService,
                currentBooking
        );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            bookingPanel.loadBookings();
        }

        fireEditingStopped();
    }

    private void cancelBooking() {
        if (currentBooking == null) {
            JOptionPane.showMessageDialog(table,
                    "Не удалось определить бронь для удаления",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            fireEditingStopped();
        }

        CancelBookingDialog dialog = new CancelBookingDialog(
                (Frame) SwingUtilities.getWindowAncestor(table),
                currentBooking.getId()
        );

        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            bookingService.cancel(currentBooking.getId());
            bookingPanel.loadBookings();
        }

        fireEditingStopped();
    }
}
