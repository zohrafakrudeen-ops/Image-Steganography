package ui;

import database.HistoryDAO;
import database.Session;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private JButton encodeButton;
    private JButton decodeButton;
    private JButton historyButton;
    private JButton logoutButton;

    public DashboardFrame() {

        setTitle("Dashboard");
        setSize(550, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        HistoryDAO dao = new HistoryDAO();

        int encodeCount = dao.getOperationCount("Encode");
        int decodeCount = dao.getOperationCount("Decode");
        int totalCount = dao.getTotalOperations();

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Secure Image Steganography Tool");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBounds(75, 20, 420, 35);

        JLabel welcome = new JLabel(
                "Welcome, " + Session.getCurrentUser() + "!"
        );
        welcome.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setBounds(125, 65, 300, 30);

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(3, 1, 5, 5));
        statsPanel.setBorder(
                BorderFactory.createTitledBorder("Your Statistics")
        );
        statsPanel.setBounds(120, 110, 300, 110);

        JLabel encodeLabel =
                new JLabel("Images Encoded : " + encodeCount);
        encodeLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        JLabel decodeLabel =
                new JLabel("Images Decoded : " + decodeCount);
        decodeLabel.setFont(
                new Font("Segoe UI", Font.PLAIN, 15)
        );

        JLabel totalLabel =
                new JLabel("Total Operations : " + totalCount);
        totalLabel.setFont(
                new Font("Segoe UI", Font.BOLD, 15)
        );

        statsPanel.add(encodeLabel);
        statsPanel.add(decodeLabel);
        statsPanel.add(totalLabel);

        encodeButton = new JButton("Encode Message");
        encodeButton.setBounds(170, 250, 200, 40);

        decodeButton = new JButton("Decode Message");
        decodeButton.setBounds(170, 300, 200, 40);

        historyButton = new JButton("View History");
        historyButton.setBounds(170, 350, 200, 40);

        logoutButton = new JButton("Logout");
        logoutButton.setBounds(170, 410, 200, 40);

        panel.add(title);
        panel.add(welcome);
        panel.add(statsPanel);
        panel.add(encodeButton);
        panel.add(decodeButton);
        panel.add(historyButton);
        panel.add(logoutButton);

        add(panel);

        encodeButton.addActionListener(e -> {

            new EncodeFrame();
            dispose();

        });

        decodeButton.addActionListener(e -> {

            new DecodeFrame();
            dispose();

        });

        historyButton.addActionListener(e -> {

            new HistoryFrame();
            dispose();

        });

        logoutButton.addActionListener(e -> {

            Session.logout();

            new LoginFrame();

            dispose();

        });
    }
}