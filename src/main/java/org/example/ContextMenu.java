package main.java.org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContextMenu extends JPopupMenu {
    public ContextMenu(JTable table) {
        JMenuItem editSpeakerName = new JMenuItem("Edit Speaker Name");
        JMenuItem editTranscriptLine = new JMenuItem("Edit Transcript Line");
        JMenuItem flagTranscriptLine = new JMenuItem("Flag Transcript Line");
        JMenuItem removeFlagTranscriptLine = new JMenuItem("Remove Flag Transcript Line");
        JMenuItem setSpeakerColor = new JMenuItem("Set Speaker Color");
        JMenu switchTo = new JMenu("Switch to");
        JMenuItem switchToSpeaker1 = new JMenuItem("Speaker 1");
        JMenuItem switchToSpeaker2 = new JMenuItem("Speaker 2");

        // Add Action Listeners for the "Switch to" menu items
        switchToSpeaker1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchSpeaker(table, "Speaker 1");
            }
        });

        switchToSpeaker2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchSpeaker(table, "Speaker 2");
            }
        });

        // Action Listener for editing speaker name (example functionality)
        editSpeakerName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String currentName = (String) table.getValueAt(selectedRow, 1); // Assuming Speaker column is at index 1
                    String newName = JOptionPane.showInputDialog(table, "Edit Speaker Name:", currentName);
                    if (newName != null && !newName.trim().isEmpty()) {
                        table.setValueAt(newName, selectedRow, 1);
                    }
                }
            }
        });

        add(editSpeakerName);
        add(editTranscriptLine);
        add(flagTranscriptLine);
        add(removeFlagTranscriptLine);
        add(setSpeakerColor);
        add(switchTo);
        switchTo.add(switchToSpeaker1);
        switchTo.add(switchToSpeaker2);
    }
    private void switchSpeaker(JTable table, String speakerName) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            table.setValueAt(speakerName, selectedRow, 1); // Assuming Speaker column is at index 1
            JOptionPane.showMessageDialog(table,
                    "Row " + selectedRow + " switched to " + speakerName,
                    "Speaker Switch",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(table,
                    "No row selected. Please select a row to switch the speaker.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
