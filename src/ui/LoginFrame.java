package ui;

import database.Session;
import database.UserDAO;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginFrame() {

        setTitle("Secure Image Steganography Tool");
        setSize(450, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Secure Image Steganography Tool");
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(45, 20, 350, 30);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setBounds(50, 80, 100, 25);

        usernameField = new JTextField();
        usernameField.setBounds(150, 80, 220, 25);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(50, 130, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 130, 220, 25);

        loginButton = new JButton("Login");
        loginButton.setBounds(90, 210, 120, 35);

        registerButton = new JButton("Register");
        registerButton.setBounds(230, 210, 120, 35);

        panel.add(title);
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);

        loginButton.addActionListener(e -> loginUser());

        registerButton.addActionListener(e -> {

            new RegisterFrame();

            dispose();

        });

    }

    private void loginUser() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields.");

            return;

        }

        UserDAO dao = new UserDAO();

        if (dao.loginUser(username, password)) {

            Session.setCurrentUser(username);

            JOptionPane.showMessageDialog(this,
                    "Login Successful!");

            new DashboardFrame();

            dispose();

        }

        else {

            JOptionPane.showMessageDialog(this,
                    "Invalid Username or Password.");

        }

    }

}