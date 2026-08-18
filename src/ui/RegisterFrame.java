package ui;

import database.UserDAO;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton registerButton;
    private JButton backButton;

    public RegisterFrame() {

        setTitle("Register");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(130, 20, 200, 30);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(50, 80, 100, 25);

        usernameField = new JTextField();
        usernameField.setBounds(150, 80, 220, 25);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 130, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 220, 25);

        registerButton = new JButton("Register");
        registerButton.setBounds(80, 220, 120, 35);

        backButton = new JButton("Back");
        backButton.setBounds(240, 220, 120, 35);

        panel.add(title);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(backButton);

        add(panel);

        registerButton.addActionListener(e -> registerUser());

        backButton.addActionListener(e -> {

            new LoginFrame();
            dispose();

        });

    }

    private void registerUser() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields.");

            return;

        }

        UserDAO dao = new UserDAO();

        boolean success = dao.registerUser(username, password);

        if (success) {

            JOptionPane.showMessageDialog(this,
                    "Registration Successful!");

            new LoginFrame();
            dispose();

        }

        else {

            JOptionPane.showMessageDialog(this,
                    "Username already exists!");

        }

    }

}