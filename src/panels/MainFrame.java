package panels;

import panels.bookings.BookingPanel;
import panels.rooms.RoomPanel;
import services.BookingService;
import services.RoomService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainFrame(RoomService roomService, BookingService bookingService) {
        setTitle("Система просмотра и бронирования номеров");
        setSize(1500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.add(new RoomPanel(roomService), "Комнаты");
        contentPanel.add(new BookingPanel(), "Бронирование");
        contentPanel.add(new ReportsPanel(), "Отчеты");

        add(buildSidebar(), BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel buildSidebar() {
        JButton btnRooms = new JButton("Комнаты");
        JButton btnBookings = new JButton("Бронирование");
        JButton btnReports = new JButton("Отчеты");

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(0, 1, 5, 5));
        sidebar.add(btnRooms);
        sidebar.add(btnBookings);
        sidebar.add(btnReports);

        btnRooms.addActionListener(e -> cardLayout.show(contentPanel, "Комнаты"));
        btnBookings.addActionListener(e -> cardLayout.show(contentPanel, "Бронирование"));
        btnReports.addActionListener(e -> cardLayout.show(contentPanel, "Отчеты"));

        return sidebar;
    }
}
