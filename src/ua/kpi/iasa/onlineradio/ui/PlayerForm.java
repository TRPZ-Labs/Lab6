package ua.kpi.iasa.onlineradio.ui;

import ua.kpi.iasa.onlineradio.models.Streamer;
import ua.kpi.iasa.onlineradio.models.Track;
import ua.kpi.iasa.onlineradio.models.User;

import javax.swing.*;
import java.awt.*;

public class PlayerForm extends JFrame {
    private final User currentUser;
    private final Streamer streamer;

    private JLabel artistLabel;
    private JLabel titleLabel;
    private JLabel statusLabel;

    public PlayerForm(User user, Streamer streamer) {
        this.currentUser = user;
        this.streamer = streamer;

        setTitle("Online Radio Player - " + user.getUsername());
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        infoPanel.setBackground(Color.DARK_GRAY);

        statusLabel = new JLabel("Радіо зупинено", SwingConstants.CENTER);
        statusLabel.setForeground(Color.LIGHT_GRAY);

        titleLabel = new JLabel("---", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);

        artistLabel = new JLabel("---", SwingConstants.CENTER);
        artistLabel.setForeground(Color.CYAN);

        infoPanel.add(statusLabel);
        infoPanel.add(titleLabel);
        infoPanel.add(artistLabel);

        add(infoPanel, BorderLayout.CENTER);


        JPanel controlsPanel = new JPanel();
        JButton playButton = new JButton("▶ Play");
        JButton nextButton = new JButton("⏭ Next");
        JButton likeButton = new JButton("❤️ Like");

        controlsPanel.add(playButton);
        controlsPanel.add(nextButton);
        controlsPanel.add(likeButton);

        add(controlsPanel, BorderLayout.SOUTH);


        playButton.addActionListener(e -> {
            streamer.play();
            statusLabel.setText("Зараз грає:");
            updateTrackInfo();
        });

        nextButton.addActionListener(e -> {
            streamer.nextTrack();
            updateTrackInfo();
        });

        likeButton.addActionListener(e -> {
            Track current = streamer.getCurrentTrack();
            if (current != null) {
                current.addLike(currentUser);
                JOptionPane.showMessageDialog(this,
                        "Ви вподобали трек: " + current.getTitle() +
                                "\nВсього лайків: " + current.getLikeCount());
            } else {
                JOptionPane.showMessageDialog(this, "Нічого не грає!");
            }
        });
    }

    private void updateTrackInfo() {
        Track track = streamer.getCurrentTrack();
        if (track != null) {
            titleLabel.setText(track.getTitle());
            artistLabel.setText(track.getArtist());
        }
    }
}