package panels.bookings.dialogs;

import javax.swing.*;
import java.awt.*;

public class CancelBookingDialog extends JDialog {

    private boolean confirmed = false;

    public CancelBookingDialog(Frame owner, Long bookingId) {
        super(owner, "Подтверждение отмены", true);

        setSize(350, 180);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout(10, 10));

        JLabel message = new JLabel(
                "Вы действительно хотите отменить бронь: %s".formatted(bookingId),
                SwingConstants.CENTER
        );

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(message, BorderLayout.CENTER);

        JButton btnConfirm = new JButton("Да");
        JButton btnCancel = new JButton("Отмена");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnCancel);

        btnConfirm.addActionListener(e -> {
            confirmed = true;
            dispose();
        });

        btnCancel.addActionListener(e -> {
            confirmed = false;
            dispose();
        });

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
