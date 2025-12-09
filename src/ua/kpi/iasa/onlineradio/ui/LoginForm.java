package ua.kpi.iasa.onlineradio.ui;

import ua.kpi.iasa.onlineradio.models.*;
import ua.kpi.iasa.onlineradio.repositories.IUserRepository;

import javax.swing.*;
import java.awt.*;
import java.util.Optional;

public class LoginForm extends JFrame {
    private final IUserRepository userRepo;
    private final MusicLibrary library;
    private final Streamer streamer;

    public LoginForm(IUserRepository userRepo, MusicLibrary library, Streamer streamer) {
        this.userRepo = userRepo;
        this.library = library;
        this.streamer = streamer;


        setTitle("Вхід в систему");
        setSize(350, 180);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));


        JPanel fieldsPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        fieldsPanel.add(new JLabel("Логін:"));
        fieldsPanel.add(usernameField);
        fieldsPanel.add(new JLabel("Пароль:"));
        fieldsPanel.add(passwordField);

        add(fieldsPanel, BorderLayout.CENTER);


        JButton loginButton = new JButton("Увійти");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loginButton);
        add(buttonPanel, BorderLayout.SOUTH);


        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Optional<User> userOpt = userRepo.findByUsername(username);

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.getPasswordHash().equals(password)) {
                    JOptionPane.showMessageDialog(this, "Вітаємо, " + user.getUsername() + "!");
                    openMainForm(user);
                } else {
                    showError("Невірний пароль");
                }
            } else {
                showError("Користувача не знайдено");
            }
        });
    }

    private void openMainForm(User user) {

        if (user instanceof Administrator) {
            new AdminForm(library).setVisible(true);
        } else {
            new PlayerForm(user, streamer).setVisible(true);
        }
        this.dispose();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Помилка", JOptionPane.ERROR_MESSAGE);
    }
}