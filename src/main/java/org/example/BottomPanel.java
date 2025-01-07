package main.java.org.example;

import javax.swing.*;
import java.awt.*;

public class BottomPanel extends JPanel {
    public BottomPanel() {
        setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton generateReportButton = new JButton("Generate Report");

        add(saveButton);
        add(generateReportButton);
    }
}
