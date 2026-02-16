package panels;

import javax.swing.*;
import java.awt.*;

public class RoomsPanel extends JPanel {

    private JTable table;
    private JPanel topButtonPanel;

    public RoomsPanel() {
        setLayout(new BorderLayout());

        topButtonPanel = new JPanel();
        JButton addBtn = new JButton("Добавить");
        JButton editBtn = new JButton("Редактировать");
        JButton deleteBtn = new JButton("Удалить");
        JButton refreshBtn = new JButton("Обновить");

        topButtonPanel.add(addBtn);
        topButtonPanel.add(editBtn);
        topButtonPanel.add(deleteBtn);
        topButtonPanel.add(refreshBtn);

        add(topButtonPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Номер", "Тип", "Статус", "Цена", "Дата создания"};
        Object[][] data = {};
        table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadRooms() {

    }

    private void createRoom() {

    }

    private void updateRoom() {

    }

    private void deleteRoom() {

    }
}
