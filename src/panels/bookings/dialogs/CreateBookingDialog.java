package panels.bookings.dialogs;

import enums.BookingStatus;
import models.Booking;
import services.BookingService;
import services.RoomService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CreateBookingDialog extends JDialog {

    private final BookingService bookingService;
    private final RoomService roomService;

    private Booking createdBooking;

    private JTextField roomNumberField;
    private JTextField clientNameField;
    private JTextField clientMsisdnField;
    private JSpinner endDateSpinner;

    public CreateBookingDialog(Frame parent, BookingService bookingService, RoomService roomService) {
        super(parent, "Создание брони", true);
        this.bookingService = bookingService;
        this.roomService = roomService;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));

        fieldsBuild();
        populatePanel(panel);

        JButton createBtn = new JButton("Создать");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createBtn);

        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        createBtn.addActionListener(e -> createBooking());
    }

    private void fieldsBuild() {
        roomNumberField = new JTextField();
        clientNameField = new JTextField();
        clientMsisdnField = new JTextField();

        SpinnerDateModel dateModel = new SpinnerDateModel();
        endDateSpinner = new JSpinner(dateModel);
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(endDateSpinner, "yyyy-MM-dd");
        endDateSpinner.setEditor(dateEditor);
    }

    private void populatePanel(JPanel panel) {
        panel.add(new JLabel("Номер комнаты"));
        panel.add(roomNumberField);

        panel.add(new JLabel("Имя клиента"));
        panel.add(clientNameField);

        panel.add(new JLabel("Номер клиента"));
        panel.add(clientMsisdnField);

        panel.add(new JLabel("Дата конца бронирования"));
        panel.add(endDateSpinner);
    }

    private void createBooking() {
        String roomNumber = roomNumberField.getText().trim();
        String clientName = clientNameField.getText().trim();
        String clientMsisdn = clientMsisdnField.getText().trim();
        LocalDate endDate = LocalDate.from(LocalDateTime.ofInstant(
                ((java.util.Date) endDateSpinner.getValue()).toInstant(),
                java.time.ZoneId.systemDefault()
        ));

        try {
            Booking booking = new Booking();
            booking.setRoomNumber(roomNumber);
            booking.setClientName(clientName);
            booking.setClientMsisdn(clientMsisdn);
            booking.setStartDate(LocalDate.now());
            booking.setEndDate(endDate);
            booking.setBookingStatus(BookingStatus.COMPLETED);
            booking.setCreatedAt(LocalDateTime.now());

            createdBooking = bookingService.create(booking);

            JOptionPane.showMessageDialog(this, "Бронь создана!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка создания брони: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Booking showDialog() {
        setVisible(true);
        return createdBooking;
    }
}
