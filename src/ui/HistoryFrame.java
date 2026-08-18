package ui;

import database.HistoryDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class HistoryFrame extends JFrame {

    private JTable table;
    private JButton openButton;
    private JButton saveButton;
    private JButton backButton;

    public HistoryFrame() {

        setTitle("History");
        setSize(800,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        initializeComponents();

        setVisible(true);
    }

    private void initializeComponents() {

        JPanel panel = new JPanel(new BorderLayout(10,10));

        JLabel title = new JLabel("Operation History",SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI",Font.BOLD,22));

        String[] columns = {
                "Operation",
                "Image",
                "Date & Time"
        };

        DefaultTableModel model = new DefaultTableModel(columns,0){

            @Override
            public boolean isCellEditable(int row,int column){
                return false;
            }

        };

        HistoryDAO dao = new HistoryDAO();

        ArrayList<String[]> history = dao.getHistory();

        for(String[] row : history){

            model.addRow(new Object[]{
                    row[1],
                    row[2],
                    row[3]
            });

        }

        table = new JTable(model);

        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI",Font.PLAIN,13));
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,14));

        JScrollPane scrollPane = new JScrollPane(table);

        JPanel buttonPanel = new JPanel();

        openButton = new JButton("Open Image");
        saveButton = new JButton("Save As");
        backButton = new JButton("Back");

        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(backButton);

        panel.add(title,BorderLayout.NORTH);
        panel.add(scrollPane,BorderLayout.CENTER);
        panel.add(buttonPanel,BorderLayout.SOUTH);

        add(panel);

        openButton.addActionListener(e -> openImage());

        saveButton.addActionListener(e -> saveImage());

        backButton.addActionListener(e -> {

            new DashboardFrame();
            dispose();

        });

    }

    private File getSelectedImage(){

        int row = table.getSelectedRow();

        if(row==-1){

            JOptionPane.showMessageDialog(this,
                    "Please select an image.");

            return null;

        }

        String imageName = table.getValueAt(row,1).toString();

        File file = new File("output/" + imageName);

        if(!file.exists()){

            JOptionPane.showMessageDialog(this,
                    "Image not found.");

            return null;

        }

        return file;

    }

    private void openImage(){

        File file = getSelectedImage();

        if(file==null)
            return;

        try{

            Desktop.getDesktop().open(file);

        }

        catch(Exception e){

            JOptionPane.showMessageDialog(this,
                    "Unable to open image.");

        }

    }

    private void saveImage(){

        File source = getSelectedImage();

        if(source==null)
            return;

        JFileChooser chooser = new JFileChooser();

        chooser.setSelectedFile(new File(source.getName()));

        int option = chooser.showSaveDialog(this);

        if(option==JFileChooser.APPROVE_OPTION){

            File destination = chooser.getSelectedFile();

            try{

                Files.copy(source.toPath(),
                        destination.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);

                JOptionPane.showMessageDialog(this,
                        "Image saved successfully!");

            }

            catch(IOException e){

                JOptionPane.showMessageDialog(this,
                        "Unable to save image.");

            }

        }

    }

}