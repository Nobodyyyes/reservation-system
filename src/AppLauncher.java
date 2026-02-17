import configurations.DatabaseConnectionConfiguration;
import panels.MainFrame;
import repositories.BookingsRepository;
import repositories.RoomRepository;
import repositories.impl.BookingsRepositoryImpl;
import repositories.impl.RoomRepositoryImpl;
import services.BookingService;
import services.RoomService;
import services.impl.BookingServiceImpl;
import services.impl.RoomServiceImpl;

import javax.swing.*;
import java.sql.Connection;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            Connection connection = DatabaseConnectionConfiguration.getConnection();

            RoomRepository roomRepository = new RoomRepositoryImpl(connection);
            RoomService roomService = new RoomServiceImpl(roomRepository);

            BookingsRepository bookingsRepository = new BookingsRepositoryImpl(connection);
            BookingService bookingService = new BookingServiceImpl(bookingsRepository);

            MainFrame mainFrame = new MainFrame(roomService, bookingService);
            mainFrame.setVisible(true);
        });
    }
}
