package panels.bookings;

import javax.swing.*;
import java.awt.*;

public class BookingPanel extends JPanel {

    private JTable table;
    private JPanel topButtonPanel;

    public BookingPanel() {
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

        String[] columns = {"ID", "Номер комнаты", "Имя клиента", "Номер клиента", "Дата бронирования", "Конец даты бронирования", "Статус", "Дата создания"};
        Object[][] data = {};
        table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addBtn.addActionListener(e -> System.out.println("Add Booking clicked"));
        editBtn.addActionListener(e -> System.out.println("Edit Booking clicked"));
        deleteBtn.addActionListener(e -> System.out.println("Delete Booking clicked"));
        refreshBtn.addActionListener(e -> System.out.println("Refresh Booking clicked"));
    }

    private void loadBookings() {

    }

    private void createBookings() {

    }

    private void updateBookings() {

    }

    private void deleteBookings() {

    }
}
