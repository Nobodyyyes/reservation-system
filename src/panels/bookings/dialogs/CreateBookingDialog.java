package panels.bookings.dialogs;

import enums.BookingStatus;
import models.Booking;
import models.Room;
import services.BookingService;
import services.RoomService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class CreateBookingDialog extends JDialog {

    private final BookingService bookingService;
    private final RoomService roomService;

    private Booking createdBooking;

    private JComboBox<Room> roomCombo;
    private JSpinner startDateSpinner;
    private JSpinner endDateSpinner;

    private JTextField clientNameField;
    private JTextField clientMsisdnField;
    private JTextField clientPassportIdField;

    public CreateBookingDialog(Frame parent, BookingService bookingService, RoomService roomService) {
        super(parent, "Создание брони", true);
        this.bookingService = bookingService;
        this.roomService = roomService;

        setSize(450, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));

        buildFields();
        populatePanel(panel);

        JButton createBtn = new JButton("Создать");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createBtn);

        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        reloadAvailableRooms();

        startDateSpinner.addChangeListener(e -> reloadAvailableRooms());
        endDateSpinner.addChangeListener(e -> reloadAvailableRooms());

        createBtn.addActionListener(e -> createBooking());
    }

    private void buildFields() {
        roomCombo = new JComboBox<>();
        roomCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Room r) {
                    setText(r.getNumber() + " (" + r.getRoomType() + ")");
                } else {
                    setText("Выберите комнату...");
                }
                return this;
            }
        });

        clientNameField = new JTextField();
        clientMsisdnField = new JTextField();
        clientPassportIdField = new JTextField();

        startDateSpinner = buildDateSpinner(new Date());
        endDateSpinner = buildDateSpinner(new Date());
    }

    private JSpinner buildDateSpinner(Date initial) {
        SpinnerDateModel model = new SpinnerDateModel(initial, null, null, java.util.Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        spinner.setEditor(new JSpinner.DateEditor(spinner, "yyyy-MM-dd"));
        return spinner;
    }

    private void populatePanel(JPanel panel) {
        panel.add(new JLabel("Комната"));
        panel.add(roomCombo);

        panel.add(new JLabel("Имя клиента"));
        panel.add(clientNameField);

        panel.add(new JLabel("Номер клиента"));
        panel.add(clientMsisdnField);

        panel.add(new JLabel("ID паспорта клиента"));
        panel.add(clientPassportIdField);

        panel.add(new JLabel("Дата начала"));
        panel.add(startDateSpinner);

        panel.add(new JLabel("Дата окончания"));
        panel.add(endDateSpinner);
    }

    private void reloadAvailableRooms() {
        LocalDate start = toLocalDate((Date) startDateSpinner.getValue());
        LocalDate end = toLocalDate((Date) endDateSpinner.getValue());

        roomCombo.removeAllItems();

        if (end.isBefore(start)) {
            roomCombo.addItem(null);
            roomCombo.setSelectedItem(null);
            return;
        }

        List<Room> available = bookingService.getAvailableRooms(start, end);

        if (available.isEmpty()) {
            roomCombo.addItem(null);
            roomCombo.setSelectedItem(null);
            return;
        }

        for (Room r : available) {
            roomCombo.addItem(r);
        }
        roomCombo.setSelectedIndex(0);
    }

    private LocalDate toLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void createBooking() {
        Room selectedRoom = (Room) roomCombo.getSelectedItem();
        if (selectedRoom == null) {
            JOptionPane.showMessageDialog(this, "Нет доступных комнат на выбранные даты", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String clientName = clientNameField.getText().trim();
        String clientMsisdn = clientMsisdnField.getText().trim();
        String clientPassportId = clientPassportIdField.getText().trim();

        LocalDate start = toLocalDate((Date) startDateSpinner.getValue());
        LocalDate end = toLocalDate((Date) endDateSpinner.getValue());

        try {
            Booking booking = new Booking();
            booking.setRoomId(selectedRoom.getId());
            booking.setRoomNumber(selectedRoom.getNumber());
            booking.setClientName(clientName);
            booking.setClientMsisdn(clientMsisdn);
            booking.setClientPassportId(clientPassportId);
            booking.setStartDate(start);
            booking.setEndDate(end);
            booking.setBookingStatus(BookingStatus.ACTIVE);

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
