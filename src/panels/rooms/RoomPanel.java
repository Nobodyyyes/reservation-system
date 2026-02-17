package panels.rooms;

import models.Room;
import panels.rooms.dialogs.CreateRoomDialog;
import services.RoomService;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RoomPanel extends JPanel {

    private final DefaultTableModel tableModel;
    private final RoomService roomService;
    private final JTextField searchField;

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
                        "Действие"
                }, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        tableSettings(table);
        centerSpecificationColumns(table);

        searchField = new JTextField(100);

        JButton btnCreate = new JButton("Создать");
        JButton btnSearch = new JButton("Поиск");

        JPanel topPanel = new JPanel(new BorderLayout());

        JPanel leftTopPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftTopPanel.add(btnCreate);

        JPanel rightTopPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightTopPanel.add(new JLabel("Название: "));
        rightTopPanel.add(searchField);
        rightTopPanel.add(btnSearch);

        topPanel.add(leftTopPanel, BorderLayout.WEST);
        topPanel.add(rightTopPanel, BorderLayout.EAST);

        add(scrollPane, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        loadRooms();

        btnCreate.addActionListener(e -> createRoom());
        btnSearch.addActionListener(e -> searchRoom());
    }

    private void tableSettings(JTable table) {
        table.getColumn("Действие").setCellRenderer(new RoomActionButtonRenderer());
        table.getColumn("Действие").setCellEditor(new RoomActionButtonEditor(table, roomService, this));
        table.getColumn("Действие").setPreferredWidth(220);
        table.getColumn("Действие").setMinWidth(180);
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
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

    public void loadRooms() {
        try {
            List<Room> rooms = roomService.getAllRooms();
            tableModel.setRowCount(0);

            for (Room room : rooms) {
                tableModel.addRow(new Object[]{
                        room.getId(),
                        room.getNumber(),
                        room.getRoomType(),
                        room.getRoomStatus(),
                        room.getPrice(),
                        room.getCreatedAt(),
                        ""
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Ошибка загрузки комнат: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
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

    }
}
