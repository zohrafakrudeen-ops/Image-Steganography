package ui;

import database.HistoryDAO;
import database.Session;
import operation.ImageEncoder;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class EncodeFrame extends JFrame {

    private JTextField imagePathField;
    private JTextArea messageArea;

    private JButton browseButton;
    private JButton encodeButton;
    private JButton backButton;

    private String selectedImagePath;

    public EncodeFrame() {

        setTitle("Encode Message");
        setSize(650, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel title = new JLabel("Encode Secret Message");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setBounds(180, 20, 300, 30);

        JLabel imageLabel = new JLabel("Image");
        imageLabel.setBounds(30, 80, 100, 25);

        imagePathField = new JTextField();
        imagePathField.setBounds(100, 80, 380, 25);
        imagePathField.setEditable(false);

        browseButton = new JButton("Browse");
        browseButton.setBounds(500, 80, 100, 25);

        JLabel messageLabel = new JLabel("Message");
        messageLabel.setBounds(30, 130, 100, 25);

        messageArea = new JTextArea();

        JScrollPane scrollPane = new JScrollPane(messageArea);
        scrollPane.setBounds(100, 130, 500, 150);

        encodeButton = new JButton("Encode");
        encodeButton.setBounds(180, 330, 120, 35);

        backButton = new JButton("Back");
        backButton.setBounds(340, 330, 120, 35);

        panel.add(title);
        panel.add(imageLabel);
        panel.add(imagePathField);
        panel.add(browseButton);
        panel.add(messageLabel);
        panel.add(scrollPane);
        panel.add(encodeButton);
        panel.add(backButton);

        add(panel);

        browseButton.addActionListener(e -> chooseImage());

        encodeButton.addActionListener(e -> encodeMessage());

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

    private void encodeMessage() {

        if (selectedImagePath == null) {

            JOptionPane.showMessageDialog(this,
                    "Please select an image.");

            return;

        }

        String message = messageArea.getText();

        if (message.isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please enter a secret message.");

            return;

        }

        try {

            String timeStamp = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));

            String imageName = "encoded_" + timeStamp + ".png";

            String outputImage = "output/" + imageName;

            ImageEncoder encoder = new ImageEncoder();

            encoder.encode(selectedImagePath,
                    outputImage,
                    message);

            HistoryDAO dao = new HistoryDAO();

            dao.addHistory(
                    Session.getCurrentUser(),
                    "Encode",
                    imageName
            );

            JOptionPane.showMessageDialog(this,
                    "Message hidden successfully!\n\nSaved as:\n"
                            + outputImage);

        }

        catch (Exception ex) {

            JOptionPane.showMessageDialog(this,
                    ex.getMessage());

        }

    }

}