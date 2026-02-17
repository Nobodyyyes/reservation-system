package panels.rooms.dialogs;

import enums.RoomStatus;
import enums.RoomType;
import models.Room;
import services.RoomService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

public class CreateRoomDialog extends JDialog {

    private final RoomService roomService;

    private Room createdRoom;

    private JTextField roomNumberField;
    private JComboBox<RoomType> roomTypeCombo;
    private JComboBox<RoomStatus> roomStatusCombo;
    private JTextField priceField;

    public CreateRoomDialog(Frame parent, RoomService roomService) {
        super(parent, "Создание комнаты", true);
        this.roomService = roomService;

        setSize(400, 300);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        fieldsBuild();
        populatePanel(panel);

        JButton createBtn = new JButton("Создать");

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(createBtn);

        add(panel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        createBtn.addActionListener(e -> createRoom());
    }

    private void fieldsBuild() {
        roomNumberField = new JTextField();

        roomTypeCombo = new JComboBox<>(RoomType.values());
        roomTypeCombo.setSelectedItem(RoomType.STANDARD);

        roomStatusCombo = new JComboBox<>(RoomStatus.values());
        roomStatusCombo.setSelectedItem(RoomStatus.AVAILABLE);

        priceField = new JTextField();
    }

    private void populatePanel(JPanel panel) {
        panel.add(new JLabel("Номер:"));
        panel.add(roomNumberField);

        panel.add(new JLabel("Тип:"));
        panel.add(roomTypeCombo);

        panel.add(new JLabel("Статус:"));
        panel.add(roomStatusCombo);

        panel.add(new JLabel("Цена:"));
        panel.add(priceField);
    }

    private void createRoom() {
        String roomNumber = roomNumberField.getText().trim();
        RoomType roomType = (RoomType) roomTypeCombo.getSelectedItem();
        RoomStatus roomStatus = (RoomStatus) roomStatusCombo.getSelectedItem();
        Double roomPrice = Double.valueOf(priceField.getText().trim());

        try {
            Room newRoom = new Room();
            newRoom.setNumber(roomNumber);
            newRoom.setRoomType(roomType);
            newRoom.setRoomStatus(roomStatus);
            newRoom.setPrice(roomPrice);
            newRoom.setCreatedAt(LocalDateTime.now());

            createdRoom = roomService.create(newRoom);

            JOptionPane.showMessageDialog(this, "Комната создана!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ошибка создания комнаты: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Room showDialog() {
        setVisible(true);
        return createdRoom;
    }
}
