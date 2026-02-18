package panels.bookings.dialogs;

import enums.BookingStatus;
import models.Booking;
import services.BookingService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class EditBookingDialog extends JDialog {

    private boolean saved = false;

    private final Booking booking;
    private final BookingService bookingService;

    private JTextField roomNumberField;
    private JTextField clientNameField;
    private JTextField clientMsisdnField;
    private JSpinner endDateSpinner;
    private JComboBox<BookingStatus> bookingStatusCombo;

    public EditBookingDialog(Frame owner, BookingService bookingService, Booking booking) {
        super(owner, "Редактирование брони", true);
        this.booking = booking;
        this.bookingService = bookingService;

        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(8, 2, 5, 5));

        fieldsBuild();

        JButton btnSave = new JButton("Сохранить");
        JButton btnCancel = new JButton("Отмена");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        add(panelSettings(formPanel), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> saveChanges());
        btnCancel.addActionListener(e -> dispose());
    }

    private void fieldsBuild() {
        roomNumberField = new JTextField(booking.getRoomNumber());
        clientNameField = new JTextField(booking.getClientName());
        clientMsisdnField = new JTextField(booking.getClientMsisdn());

        endDateSpinner = new JSpinner(new SpinnerDateModel());
        endDateSpinner.setValue(java.util.Date.from(booking.getEndDate()
                .atStartOfDay(ZoneId.systemDefault()).toInstant()));

        bookingStatusCombo = new JComboBox<>(BookingStatus.values());
        bookingStatusCombo.setSelectedItem(booking.getBookingStatus());
    }

    private JPanel panelSettings(JPanel formPanel) {
        formPanel.add(new JLabel("Номер комнаты"));
        formPanel.add(roomNumberField);

        formPanel.add(new JLabel("Имя клиента"));
        formPanel.add(clientNameField);

        formPanel.add(new JLabel("Номер клиента"));
        formPanel.add(clientMsisdnField);

        formPanel.add(new JLabel("Дата конца бронирования"));
        formPanel.add(endDateSpinner);

        formPanel.add(new JLabel("Статус"));
        formPanel.add(bookingStatusCombo);

        return formPanel;
    }

    private void saveChanges() {
        String roomNumber = roomNumberField.getText().trim();
        String clientName = clientNameField.getText().trim();
        String clientMsisdn = clientMsisdnField.getText().trim();
        LocalDate endDate = LocalDate.from(LocalDateTime.ofInstant(
                ((java.util.Date) endDateSpinner.getValue()).toInstant(),
                java.time.ZoneId.systemDefault()
        ));
        BookingStatus bookingStatus = (BookingStatus) bookingStatusCombo.getSelectedItem();

        try {
            booking.setRoomNumber(roomNumber);
            booking.setClientName(clientName);
            booking.setClientMsisdn(clientMsisdn);
            booking.setEndDate(endDate);
            booking.setBookingStatus(bookingStatus);

            bookingService.update(booking);

            saved = true;
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка при сохранении изменений: " + e.getMessage(),
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean isSaved() {
        return saved;
    }
}
