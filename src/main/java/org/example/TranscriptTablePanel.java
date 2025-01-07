package main.java.org.example;

import main.java.repo.AudioFiles;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class TranscriptTablePanel extends JPanel {
    private DefaultTableModel model;
    private File[] files;

    public TranscriptTablePanel(MainFrame mainFrame) {
        setLayout(new BorderLayout());

        // Table Data (Initial Empty State)
        String[] columnNames = {"Time", "Speaker", "Transcript"};
        model = new DefaultTableModel(columnNames, 0); // Start with 0 rows
        JTable table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        loadDefaultData();

        table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Avoid unnecessary events during updates
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        String duration = (String) model.getValueAt(selectedRow, 0);
                        String transcript = (String) model.getValueAt(selectedRow, 2);

                        File file = files[selectedRow]; // Retrieve the file from the array
                        System.out.println(file.getAbsoluteFile());

                        mainFrame.updateDetails(transcript, duration, file.getAbsoluteFile());
                    }
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadDefaultData() {
        File assetsDir = new File("assets");

        if (assetsDir.exists() && assetsDir.isDirectory()) {
            File[] mp3Files = assetsDir.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp3"));

            if (mp3Files != null) {
                files = new File[mp3Files.length]; // Initialize the files array to hold references to the audio files
                for (int i = 0; i < mp3Files.length; i++) {
                    File file = mp3Files[i];
                    try {
                        // Load the audio file and its metadata
                        AudioFiles song = new AudioFiles(file.getPath());
                        String title = file.getName();
                        String duration = song.getFileLength();
                        String speaker = "Speaker 1"; // Default speaker

                        model.addRow(new Object[]{duration, speaker, title});

                        files[i] = file;
                    } catch (Exception e) {
                        System.err.println("Error processing file: " + file.getName());
                        e.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "No MP3 files found in assets directory.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Assets directory not found.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}