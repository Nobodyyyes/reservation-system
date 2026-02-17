package panels.rooms.dialogs;

import enums.RoomStatus;
import enums.RoomType;
import models.Room;
import services.RoomService;

import javax.swing.*;
import java.awt.*;

public class EditRoomDialog extends JDialog {

    private boolean saved = false;
    private final Room room;
    private final RoomService roomService;

    private JTextField roomNumberField;
    private JComboBox<RoomType> roomTypeCombo;
    private JComboBox<RoomStatus> roomStatusCombo;
    private JTextField priceField;

    public EditRoomDialog(Frame owner, RoomService roomService, Room room) {
        super(owner, "Редактирование комнаты", true);
        this.room = room;
        this.roomService = roomService;

        setSize(400, 350);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));

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
        roomNumberField = new JTextField(room.getNumber());

        roomTypeCombo = new JComboBox<>(RoomType.values());
        roomTypeCombo.setSelectedItem(room.getRoomType());

        roomStatusCombo = new JComboBox<>(RoomStatus.values());
        roomStatusCombo.setSelectedItem(room.getRoomStatus());

        priceField = new JTextField(String.valueOf(room.getPrice()));
    }

    private JPanel panelSettings(JPanel formPanel) {
        formPanel.add(new JLabel("Номер комнаты"));
        formPanel.add(roomNumberField);

        formPanel.add(new JLabel("Тип"));
        formPanel.add(roomTypeCombo);

        formPanel.add(new JLabel("Статус"));
        formPanel.add(roomStatusCombo);

        formPanel.add(new JLabel("Цена"));
        formPanel.add(priceField);

        return formPanel;
    }

    private void saveChanges() {
        String roomNumber = roomNumberField.getText().trim();
        RoomType roomType = (RoomType) roomTypeCombo.getSelectedItem();
        RoomStatus roomStatus = (RoomStatus) roomStatusCombo.getSelectedItem();
        Double roomPrice = Double.valueOf(priceField.getText().trim());

        try {
            room.setNumber(roomNumber);
            room.setRoomType(roomType);
            room.setRoomStatus(roomStatus);
            room.setPrice(roomPrice);

            roomService.updateRoom(room);

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
