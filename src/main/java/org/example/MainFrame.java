package main.java.org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class MainFrame extends JFrame {
    private ResultPanel resultPanel;
    private FiltersPanel filtersPanel;
    private BottomPanel bottomPanel;
    private TranscriptTablePanel transcriptTablePanel;
    private DefaultTableModel model;

    public MainFrame() {
        setTitle("File Transcription Results");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1400, 750);
        setResizable(false);
        setLayout(new BorderLayout());

        // Initialize the DefaultTableModel
        String[] columnNames = {"ID", "File", "Length"};
        model = new DefaultTableModel(columnNames, 0); // Initialize with columns

        // Initialize components
        filtersPanel = new FiltersPanel(model,null); // Passing model to FiltersPanel
        bottomPanel = new BottomPanel();
        transcriptTablePanel = new TranscriptTablePanel(this);

        // Top Panel for result information
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel resultLabel = new JLabel("Displaying results (1 of 1)");
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel transcriptLabel = new JLabel("Transcripts");
        transcriptLabel.setBorder(BorderFactory.createEmptyBorder(10, 220, 10, 10));

        JTextField searchField = new JTextField(20);
        searchField.setText("Find/Replace");

        JButton flagFileButton = new JButton("Flag File");
        JButton flagTranscriptLineButton = new JButton("Flag Transcript Line");

        // Add components to the top panel
        topPanel.add(resultLabel);
        topPanel.add(transcriptLabel);
        topPanel.add(searchField);
        topPanel.add(flagFileButton);
        topPanel.add(flagTranscriptLineButton);

        // Add the top panel to the frame
        add(topPanel, BorderLayout.NORTH);

        // Add the ResultPanel and TranscriptTablePanel to the center
        add(transcriptTablePanel, BorderLayout.CENTER);

        // Left Panel: Filters
        add(filtersPanel, BorderLayout.WEST);

        // Bottom Panel: Save and Generate Report
        add(bottomPanel, BorderLayout.SOUTH);
    }


    // Getter methods for panels if needed


    public FiltersPanel getFiltersPanel() {
        return filtersPanel;
    }

    public BottomPanel getBottomPanel() {
        return bottomPanel;
    }

    public TranscriptTablePanel getTranscriptTablePanel() {
        return transcriptTablePanel;
    }
    public void updateDetails(String fileName, String duration, File file) {
        model.setRowCount(0);
        model.addRow(new Object[]{"1", fileName, duration});
        filtersPanel.removeAll();
        filtersPanel.add(new FiltersPanel(model,file));
        filtersPanel.revalidate();
        filtersPanel.repaint();
    }

}
