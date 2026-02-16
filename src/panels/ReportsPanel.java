package panels;

import javax.swing.*;
import java.awt.*;

public class ReportsPanel extends JPanel {

    private JTable table;
    private JPanel topButtonPanel;

    public ReportsPanel() {
        setLayout(new BorderLayout());

        topButtonPanel = new JPanel();
        JButton generateBtn = new JButton("Сгенерировать отчет");
        JButton exportBtn = new JButton("Экпорт");

        topButtonPanel.add(generateBtn);
        topButtonPanel.add(exportBtn);

        add(topButtonPanel, BorderLayout.NORTH);

        String[] columns = {"ID", "Номер комнаты", "Количество броней", "Доход"};
        Object[][] data = {};
        table = new JTable(data, columns);
        add(new JScrollPane(table), BorderLayout.CENTER);

        generateBtn.addActionListener(e -> System.out.println("Generate Report clicked"));
        exportBtn.addActionListener(e -> System.out.println("Export CSV clicked"));
    }
}
