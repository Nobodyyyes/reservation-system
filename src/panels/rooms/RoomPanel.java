package panels.rooms;

import enums.RoomStatus;
import enums.RoomType;
import models.Room;
import panels.rooms.dialogs.CreateRoomDialog;
import services.RoomService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RoomPanel extends JPanel {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final DefaultTableModel tableModel;
    private final RoomService roomService;
    private final JTextField searchField;

    private List<Room> cachedRooms = List.of();
    private JComboBox<RoomType> roomTypeCombo;
    private JComboBox<RoomStatus> roomStatusCombo;

    public RoomPanel(RoomService roomService) {
        this.roomService = roomService;

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
                new String[]{
                        "ID",
                        "Номер",
                        "Тип",
                        "Статус",
                        "Цена",
                        "Дата создания",
                        "Услуги",
                        "Действие"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 7;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tableSettings(table);
        centerSpecificationColumns(table);

        searchField = new JTextField(15);
        optimizeSearch(searchField);

        roomTypeCombo = new JComboBox<>();
        roomTypeCombo = roomTypeSettings();

        roomStatusCombo = new JComboBox<>();
        roomStatusCombo = roomStatusSettings();

        JButton btnCreate = new JButton("Создать комнату");
        JButton btnRefresh = new JButton("Обновить список");

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftTopPanel.add(btnCreate);
        leftTopPanel.add(btnRefresh);

        JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightTopPanelSettings(rightTopPanel);

        JButton btnReset = new JButton("Сброс");
        btnReset.addActionListener(e -> resetButton());
        rightTopPanel.add(btnReset);

        topPanel.add(leftTopPanel, BorderLayout.WEST);
        topPanel.add(rightTopPanel, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        loadRooms();

        btnCreate.addActionListener(e -> createRoom());
        btnRefresh.addActionListener(e -> loadRooms());
        roomTypeCombo.addActionListener(e -> searchRoom());
        roomStatusCombo.addActionListener(e -> searchRoom());
    }

    private void tableSettings(JTable table) {
        table.getColumn("Действие").setCellRenderer(new RoomActionButtonRenderer());
        table.getColumn("Действие").setCellEditor(new RoomActionButtonEditor(table, roomService, this));
        table.getColumn("Действие").setPreferredWidth(220);
        table.getColumn("Действие").setMinWidth(180);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.getColumn("Услуги").setPreferredWidth(250);
        table.getColumn("Услуги").setMinWidth(200);
        table.setRowHeight(30);
    }

    private void centerSpecificationColumns(JTable table) {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        table.getColumn("Номер").setCellRenderer(centerRenderer);
        table.getColumn("Тип").setCellRenderer(centerRenderer);
        table.getColumn("Статус").setCellRenderer(centerRenderer);
        table.getColumn("Цена").setCellRenderer(centerRenderer);
        table.getColumn("Дата создания").setCellRenderer(centerRenderer);
    }

    private void optimizeSearch(JTextField searchField) {
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                searchRoom();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                searchRoom();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                searchRoom();
            }
        });
    }

    private JComboBox<RoomType> roomTypeSettings() {
        roomTypeCombo.addItem(null);
        for (RoomType t : RoomType.values()) roomTypeCombo.addItem(t);

        roomTypeCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "Все типы" : value.toString());
                return this;
            }
        });

        return roomTypeCombo;
    }

    private JComboBox<RoomStatus> roomStatusSettings() {
        roomStatusCombo.addItem(null);
        for (RoomStatus t : RoomStatus.values()) roomStatusCombo.addItem(t);

        roomStatusCombo.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value == null ? "Все статусы" : value.toString());
                return this;
            }
        });

        return roomStatusCombo;
    }

    private void rightTopPanelSettings(JPanel rightTopPanel) {
        rightTopPanel.add(new JLabel("Тип: "));
        rightTopPanel.add(roomTypeCombo);

        rightTopPanel.add(new JLabel("Статус: "));
        rightTopPanel.add(roomStatusCombo);

        rightTopPanel.add(new JLabel("Номер комнаты: "));
        rightTopPanel.add(searchField);
    }

    private void resetButton() {
        searchField.setText("");
        roomTypeCombo.setSelectedItem(null);
        roomStatusCombo.setSelectedItem(null);
        renderRooms(cachedRooms);
    }

    public void loadRooms() {
        try {
            cachedRooms = roomService.getAllRooms();
            renderRooms(cachedRooms);
            searchRoom();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка загрузки комнат: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void renderRooms(List<Room> rooms) {
        tableModel.setRowCount(0);
        for (Room room : rooms) {
            tableModel.addRow(new Object[]{
                    room.getId(),
                    room.getNumber(),
                    room.getRoomType(),
                    room.getRoomStatus(),
                    room.getPrice(),
                    formatDate(room.getCreatedAt()),
                    room.getExtraServicesStringStr(),
                    ""
            });
        }
    }

    private String formatDate(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DATE_FORMAT);
    }

    private void createRoom() {
        CreateRoomDialog dialog = new CreateRoomDialog(
                (Frame) SwingUtilities.getWindowAncestor(this),
                roomService
        );

        Room newRoom = dialog.showDialog();
        if (newRoom != null) {
            loadRooms();
        }
    }

    private void searchRoom() {
        String q = searchField.getText();
        String needle = (q == null) ? "" : q.trim().toLowerCase();

        RoomType selectedType = (RoomType) roomTypeCombo.getSelectedItem();
        RoomStatus selectedStatus = (RoomStatus) roomStatusCombo.getSelectedItem();

        List<Room> filtered = cachedRooms.stream()
                .filter(r -> {
                    boolean okNumber = needle.isEmpty()
                            || (r.getNumber() != null && r.getNumber().toLowerCase().contains(needle));

                    boolean okType = selectedType == null
                            || (r.getRoomType() == selectedType);

                    boolean okStatus = selectedStatus == null
                            || (r.getRoomStatus() == selectedStatus);

                    return okNumber && okType && okStatus;
                })
                .toList();

        renderRooms(filtered);
    }
}
