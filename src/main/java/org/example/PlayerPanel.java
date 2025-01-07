package main.java.org.example;

import javazoom.jl.player.advanced.AdvancedPlayer;
import main.java.repo.AudioFiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Hashtable;

public class PlayerPanel extends JPanel {
    private String duration;
    private final JSlider progressSlider;
    private AudioFiles audioFiles;
    private int currentTimeMillis;
    private int currentFrame;
    private AdvancedPlayer player;
    private boolean isPaused;
    private boolean songEnded;
    private static final Object playSignal = new Object();
    private JLabel timeLabel;

    public PlayerPanel(DefaultTableModel model, File file) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(400, 150));
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new BoxLayout(controlsPanel, BoxLayout.X_AXIS));

        JButton playPauseButton = new JButton("▶");
        playPauseButton.setPreferredSize(new Dimension(50, 50));

        progressSlider = new JSlider(0, 100, 0);
        progressSlider.setBackground(null);
        progressSlider.setUI(new CustomSliderUI(progressSlider));

        progressSlider.addChangeListener(e -> {
            if (progressSlider.getValueIsAdjusting() && audioFiles != null) {
                int newFrame = progressSlider.getValue();
                currentFrame = newFrame;
                currentTimeMillis = (int) (newFrame / (2.08 * audioFiles.getFrame()));
                if (!isPaused) {
                    stopSong();
                    playCurrentSong();
                }
            }
        });

        duration = "00:00:00";
        if (model != null && model.getRowCount() > 0) {
            duration = (String) model.getValueAt(0, 2);
        }

        timeLabel = new JLabel("00:00:00 / " + duration);
        timeLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        controlsPanel.add(playPauseButton);
        playPauseButton.addActionListener(e -> {
            if (file != null) {
                if (player == null || songEnded) {
                    loadAndPlayAudio(file);
                    playPauseButton.setText("❚❚");
                } else {
                    togglePauseResume(playPauseButton);
                }
            }
        });

        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(progressSlider);
        controlsPanel.add(Box.createHorizontalStrut(10));
        controlsPanel.add(timeLabel);

        add(controlsPanel, BorderLayout.SOUTH);
    }

    private void loadAndPlayAudio(File file) {
        try {
            if (!songEnded) {
                stopSong();
            }

            audioFiles = new AudioFiles(file.getAbsolutePath());
            updatePlaybackSlider(audioFiles);
            currentTimeMillis = 0;
            currentFrame = 0;
            playCurrentSong();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading audio file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void stopSong() {
        if (player != null) {
            player.close();
            player = null;
            songEnded = true;
        }
    }

    public void playCurrentSong() {
        if (audioFiles == null) return;
        try {
            FileInputStream fileInputStream = new FileInputStream(audioFiles.getSongPath());
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            player = new AdvancedPlayer(bufferedInputStream);

            songEnded = false;
            startMusicThread();
            startPlaybackSliderThread();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error playing audio file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void startPlaybackSliderThread() {
        new Thread(() -> {
            try {
                while (!songEnded) {
                    synchronized (playSignal) {
                        if (isPaused) {
                            playSignal.wait();
                        }
                    }

                    currentTimeMillis += 1000;
                    int calculatedFrame = (int) ((double) currentTimeMillis * 2.08 * audioFiles.getFrame());
                    SwingUtilities.invokeLater(() -> {
                        setPlaybackSliderValue(calculatedFrame);
                        updateTimeLabel();
                    });
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void setPlaybackSliderValue(int value) {
        progressSlider.setValue(value);
    }

    private void startMusicThread() {
        new Thread(() -> {
            try {
                if (isPaused) {
                    synchronized (playSignal) {
                        isPaused = false;
                        playSignal.notify();
                    }

                    player.play(currentFrame, Integer.MAX_VALUE);
                } else {
                    player.play();
                }
                songEnded = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void togglePauseResume(JButton playPauseButton) {
        if (isPaused) {
            synchronized (playSignal) {
                isPaused = false;
                playSignal.notify();
            }
            playPauseButton.setText("❚❚");
        } else {
            isPaused = true;
            playPauseButton.setText("▶");
            stopSong();

        }
    }

    public void updatePlaybackSlider(AudioFiles song) {
        progressSlider.setMaximum(song.getMp3File().getFrameCount());
        progressSlider.setPaintLabels(true);
    }

    private void updateTimeLabel() {
        int elapsedSeconds = currentTimeMillis / 1000;
        int minutes = elapsedSeconds / 60;
        int seconds = elapsedSeconds % 60;
        String elapsedTime = String.format("%02d:%02d", minutes, seconds);

        timeLabel.setText(elapsedTime + " / " + duration);
    }

    private static class CustomSliderUI extends javax.swing.plaf.basic.BasicSliderUI {
        public CustomSliderUI(JSlider b) {
            super(b);
        }

        @Override
        public void paintThumb(Graphics g) {
            super.paintThumb(g);
        }
    }
}
