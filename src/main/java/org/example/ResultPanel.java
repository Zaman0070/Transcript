

package main.java.org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;


public class ResultPanel extends JPanel {
    private JTable table;

    public ResultPanel(DefaultTableModel model) {
        setLayout(new BorderLayout());

        // Create JTable with the passed model
        table = new JTable(model);

        // Styling the table
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Center align the header text
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("SansSerif", Font.BOLD, 14));
        ((DefaultTableCellRenderer) tableHeader.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        // Center align content in columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); // ID column
        table.getColumnModel().getColumn(2).setCellRenderer(centerRenderer); // Length column

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setPreferredSize(new Dimension(400, 200));

        // Add table and header to the panel
        add(scrollPane, BorderLayout.CENTER);
    }
}
