package main.java.org.example;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;

public class FiltersPanel extends JPanel {
    public FiltersPanel(DefaultTableModel model, File file) {
        // Set layout and border for the main panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Result Panel
        add(new ResultPanel(model));

        // Player Label
        JLabel playerLbl = new JLabel("Player:");
        playerLbl.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel playerLabelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Left-aligned panel for Player label
        playerLabelPanel.add(playerLbl);
        add(playerLabelPanel);

        // Add Player Panel
        add(new PlayerPanel(model,file));

        // Note Label
        JLabel note = new JLabel("(Double-click transcript line to play audio)");
        note.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        JPanel notePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Left-aligned panel for Note label
        notePanel.add(note);
        add(notePanel);

        // Checkbox Panel with left alignment
        JPanel checkBoxPanelContainer = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // Container for left alignment
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS)); // Vertical layout for checkboxes
        checkBoxPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Create checkboxes
        JCheckBox showFlaggedFiles = new JCheckBox("Show Flagged Files Only");
        JCheckBox showFlaggedTranscriptLines = new JCheckBox("Show Flagged Transcript Lines Only");
        JCheckBox showRedactedAudio = new JCheckBox("Show Redacted Audio Only");

        // Add checkboxes to the checkbox panel
        checkBoxPanel.add(showFlaggedFiles);
        checkBoxPanel.add(showFlaggedTranscriptLines);
        checkBoxPanel.add(showRedactedAudio);

        // Add checkbox panel to container and main panel
        checkBoxPanelContainer.add(checkBoxPanel);
        add(checkBoxPanelContainer);

        // Add spacing
        add(Box.createVerticalStrut(20));

        // Search Field
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
