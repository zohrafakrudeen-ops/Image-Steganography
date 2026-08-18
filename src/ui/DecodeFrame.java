package ui;

import database.HistoryDAO;
import database.Session;
import operation.ImageDecoder;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DecodeFrame extends JFrame {

    private JTextField imagePathField;
    private JButton browseButton;
    private JButton decodeButton;
    private JButton backButton;

    private String selectedImagePath;

    public DecodeFrame() {

        setTitle("Decode Message");
        setSize(650, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Decode Secret Message");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(180, 20, 300, 30);

        JLabel imageLabel = new JLabel("Image");
        imageLabel.setBounds(30, 80, 100, 25);

        imagePathField = new JTextField();
        imagePathField.setBounds(100, 80, 380, 25);
        imagePathField.setEditable(false);

        browseButton = new JButton("Browse");
        browseButton.setBounds(500, 80, 100, 25);

        decodeButton = new JButton("Decode");
        decodeButton.setBounds(180, 180, 120, 35);

        backButton = new JButton("Back");
        backButton.setBounds(340, 180, 120, 35);

        panel.add(title);
        panel.add(imageLabel);
        panel.add(imagePathField);
        panel.add(browseButton);
        panel.add(decodeButton);
        panel.add(backButton);

        add(panel);

        browseButton.addActionListener(e -> chooseImage());

        decodeButton.addActionListener(e -> decodeMessage());

        backButton.addActionListener(e -> {

            new DashboardFrame();

            dispose();

        });

    }

    private void chooseImage() {

        JFileChooser chooser = new JFileChooser();

        int result = chooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();

            selectedImagePath = file.getAbsolutePath();

            imagePathField.setText(selectedImagePath);

        }

    }

    private void decodeMessage() {

        if (selectedImagePath == null) {

            JOptionPane.showMessageDialog(this,
                    "Please select an image.");

            return;

        }

        try {

            ImageDecoder decoder = new ImageDecoder();

            String message = decoder.decode(selectedImagePath);

            File file = new File(selectedImagePath);

            HistoryDAO dao = new HistoryDAO();

            dao.addHistory(
                    Session.getCurrentUser(),
                    "Decode",
                    file.getName()
            );

            JOptionPane.showMessageDialog(this,
                    "Hidden Message:\n\n" + message);

        }

        catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage());

        }

    }

}