package panels.rooms;

import models.Room;
import panels.rooms.dialogs.DeleteRoomDialog;
import panels.rooms.dialogs.EditRoomDialog;
import services.RoomService;

import javax.swing.*;
import java.awt.*;

public class RoomActionButtonEditor extends DefaultCellEditor {

    private final JPanel panel = new JPanel();
    private final JTable table;
    private final RoomService roomService;
    private final RoomPanel roomPanel;
    private Room currentRoom;

    public RoomActionButtonEditor(JTable table,
                                  RoomService roomService,
                                  RoomPanel roomPanel) {
        super(new JCheckBox());
        this.table = table;
        this.roomService = roomService;
        this.roomPanel = roomPanel;

        JButton btnEdit = new JButton("Изменить");
        JButton btnDelete = new JButton("Удалить");

        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panel.add(btnEdit);
        panel.add(btnDelete);

        btnEdit.addActionListener(e -> updateHabit());
        btnDelete.addActionListener(e -> deleteHabit());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {

        Long roomId = (Long) table.getValueAt(row, 0);
        currentRoom = roomService.getRoomById(roomId);

        return panel;
    }

    private void updateHabit() {
        if (currentRoom == null) {
            JOptionPane.showMessageDialog(table,
                    "Не удалось определить комнату для редактирования",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            fireEditingStopped();
        }

        EditRoomDialog dialog = new EditRoomDialog(
                (Frame) SwingUtilities.getWindowAncestor(table),
                roomService,
                currentRoom
        );

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            roomPanel.loadRooms();
        }

        fireEditingStopped();
    }

    private void deleteHabit() {
        if (currentRoom == null) {
            JOptionPane.showMessageDialog(table,
                    "Не удалось определить комнату для удаления",
                    "Ошибка",
                    JOptionPane.ERROR_MESSAGE);
            fireEditingStopped();
        }

        DeleteRoomDialog dialog = new DeleteRoomDialog(
                (Frame) SwingUtilities.getWindowAncestor(table),
                currentRoom.getNumber()
        );

        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            roomService.deleteRoom(currentRoom.getId());
            roomPanel.loadRooms();
        }

        fireEditingStopped();
    }
}
