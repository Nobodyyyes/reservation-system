package panels.rooms;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class RoomActionButtonRenderer extends JPanel implements TableCellRenderer {

    private final JButton btnEdit = new JButton("Изменить");
    private final JButton btndelete = new JButton("Удалить");

    public RoomActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        add(btnEdit);
        add(btndelete);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
