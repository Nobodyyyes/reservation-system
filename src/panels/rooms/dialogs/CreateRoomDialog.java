package panels.rooms.dialogs;

import enums.ExtraService;
import enums.RoomStatus;
import enums.RoomType;
import models.Room;
import services.RoomService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CreateRoomDialog extends JDialog {

    private final RoomService roomService;

    private Room createdRoom;

    private JTextField roomNumberField;
    private JComboBox<RoomType> roomTypeCombo;
    private JTextField priceField;

    private final Map<ExtraService, JCheckBox> extraCheckboxes = new LinkedHashMap<>();

    public CreateRoomDialog(Frame parent, RoomService roomService) {
        super(parent, "Создание комнаты", true);
        this.roomService = roomService;

        setSize(520, 380);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        buildFields();
        populateForm(formPanel);

        JButton createBtn = new JButton("Создать");
        JButton cancelBtn = new JButton("Отмена");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(cancelBtn);
        bottomPanel.add(createBtn);

        add(formPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        roomTypeCombo.addActionListener(e -> recalcPrice());
        extraCheckboxes.values().forEach(cb -> cb.addActionListener(e -> recalcPrice()));

        recalcPrice();

        createBtn.addActionListener(e -> createRoom());
        cancelBtn.addActionListener(e -> {
            createdRoom = null;
            dispose();
        });
    }

    private void buildFields() {
        roomNumberField = new JTextField(18);

        roomTypeCombo = new JComboBox<>(RoomType.values());
        roomTypeCombo.setSelectedItem(RoomType.STANDARD);

        priceField = new JTextField(10);
        priceField.setEditable(false);
        priceField.setHorizontalAlignment(SwingConstants.CENTER);

        for (ExtraService extra : ExtraService.values()) {
            JCheckBox cb = new JCheckBox(humanizeExtra(extra) + " (+" + extra.getPrice() + ")");
            extraCheckboxes.put(extra, cb);
        }
    }

    private void populateForm(JPanel panel) {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 10, 6, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;

        c.gridx = 0; c.weightx = 0;
        panel.add(new JLabel("Номер:"), c);
        c.gridx = 1; c.weightx = 1;
        panel.add(roomNumberField, c);

        c.gridy++;
        c.gridx = 0; c.weightx = 0;
        panel.add(new JLabel("Тип:"), c);
        c.gridx = 1; c.weightx = 1;
        panel.add(roomTypeCombo, c);

        c.gridy++;
        c.gridx = 0; c.weightx = 0;
        c.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Доп. услуги:"), c);

        JPanel extrasPanel = new JPanel(new GridLayout(0, 1, 4, 4));
        extraCheckboxes.values().forEach(extrasPanel::add);

        JScrollPane extrasScroll = new JScrollPane(extrasPanel);
        extrasScroll.setPreferredSize(new Dimension(260, 140));

        c.gridx = 1; c.weightx = 1;
        panel.add(extrasScroll, c);

        c.gridy++;
        c.gridx = 0; c.weightx = 0;
        c.anchor = GridBagConstraints.WEST;
        panel.add(new JLabel("Цена (итог):"), c);
        c.gridx = 1; c.weightx = 1;
        panel.add(priceField, c);

        c.gridy++;
        c.gridx = 0;
        c.gridwidth = 2;
        JLabel hint = new JLabel("Цена = базовая цена типа + сумма выбранных услуг");
        hint.setForeground(Color.GRAY);
        panel.add(hint, c);
    }

    private void recalcPrice() {
        RoomType type = (RoomType) roomTypeCombo.getSelectedItem();
        long total = (type == null) ? 0 : type.getBasePrice();

        for (Map.Entry<ExtraService, JCheckBox> entry : extraCheckboxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                total += entry.getKey().getPrice();
            }
        }

        priceField.setText(String.valueOf(total));
    }

    private Set<ExtraService> getSelectedExtras() {
        Set<ExtraService> selected = EnumSet.noneOf(ExtraService.class);
        for (Map.Entry<ExtraService, JCheckBox> entry : extraCheckboxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }

    private void createRoom() {
        String roomNumber = roomNumberField.getText().trim();
        RoomType roomType = (RoomType) roomTypeCombo.getSelectedItem();

        double roomPrice;
        try {
            roomPrice = Double.parseDouble(priceField.getText().trim());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Некорректная цена", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Room newRoom = new Room();
            newRoom.setNumber(roomNumber);
            newRoom.setRoomType(roomType);
            newRoom.setRoomStatus(RoomStatus.AVAILABLE);
            newRoom.setPrice(roomPrice);
            newRoom.setCreatedAt(LocalDateTime.now());
            newRoom.setExtraServices(getSelectedExtras());

            createdRoom = roomService.create(newRoom);

            JOptionPane.showMessageDialog(this, "Комната создана!");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка создания комнаты: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private String humanizeExtra(ExtraService extra) {
        return switch (extra.name()) {
            case "FOOD" -> "Питание";
            case "FREE_WIFI" -> "Бесплатный Wi-Fi";
            case "PARKING" -> "Парковка";
            default -> extra.name();
        };
    }

    public Room showDialog() {
        setVisible(true);
        return createdRoom;
    }
}