package main.java.org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class FiltersPanel extends JPanel {
    public FiltersPanel(DefaultTableModel model, File file) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(new ResultPanel(model));

        JLabel playerLbl = new JLabel("Player:");
        playerLbl.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel playerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Left-aligned panel for Player label
        playerLabelPanel.add(playerLbl);
        add(playerLabelPanel);

        add(new PlayerPanel(model,file));

        JLabel note = new JLabel("(Double-click transcript line to play audio)");
        note.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Left-aligned panel for Note label
        notePanel.add(note);
        add(notePanel);

        JPanel checkBoxPanelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Container for left alignment
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS)); // Vertical layout for checkboxes
        checkBoxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JCheckBox showFlaggedFiles = new JCheckBox("Show Flagged Files Only");
        JCheckBox showFlaggedTranscriptLines = new JCheckBox("Show Flagged Transcript Lines Only");
        JCheckBox showRedactedAudio = new JCheckBox("Show Redacted Audio Only");

        checkBoxPanel.add(showFlaggedFiles);
        checkBoxPanel.add(showFlaggedTranscriptLines);
        checkBoxPanel.add(showRedactedAudio);

        checkBoxPanelContainer.add(checkBoxPanel);
        add(checkBoxPanelContainer);

        add(Box.createVerticalStrut(20));

        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JLabel filterLabel = new JLabel("Filters:");
        filterLabel.setPreferredSize(new Dimension(50, 30));
        searchFieldPanel.add(filterLabel);
        JTextField searchField = new JTextField(25); // Wider field
        searchField.setText("Search file name, transcript, and speaker name");
        searchField.setMaximumSize(new Dimension(400, 30)); // Restrict max size
        searchFieldPanel.add(searchField);
        add(searchFieldPanel);
    }
}
