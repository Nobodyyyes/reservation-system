package panels.bookings;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class BookingActionButtonRenderer extends JPanel implements TableCellRenderer {

    private final JButton btnEdit = new JButton("Изменить");
    private final JButton btndelete = new JButton("Удалить");

    public BookingActionButtonRenderer() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 0));
        add(btnEdit);
        add(btndelete);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return this;
    }
}
