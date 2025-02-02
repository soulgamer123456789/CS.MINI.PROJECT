/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cs.mini.project;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.commons.csv.*;
import java.io.FileWriter;
import java.io.IOException;


public class CsMiniProject extends JFrame {
    public CsMiniProject() {
        setTitle("Workshop Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu dataEntryMenu = new JMenu("Data Entry");
        JMenuItem addEmployeeItem = new JMenuItem("Add Employee");
        JMenuItem addFacilitatorItem = new JMenuItem("Add Facilitator");

        dataEntryMenu.add(addEmployeeItem);
        dataEntryMenu.add(addFacilitatorItem);
        menuBar.add(dataEntryMenu);
        setJMenuBar(menuBar);

        addEmployeeItem.addActionListener(e -> new EmployeePanel());
        addFacilitatorItem.addActionListener(e -> new FacilitatorPanel());
    }

    public static void appendToCSV(String filePath, String[] data) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath, true); // Append mode (true)
            CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT)) {
            csvPrinter.println();
            csvPrinter.printRecord((Object[]) data); // This already adds a new line
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CsMiniProject().setVisible(true));
    }
}

class EmployeePanel extends JFrame {

    private final JTextField nameField;
    private final JTextField deptField;
    private final JTextField emailField;
    private final JButton saveButton;

    public EmployeePanel() {
        setTitle("Add Employee");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Department:"));
        deptField = new JTextField();
        add(deptField);

        add(new JLabel("Email ID:"));
        emailField = new JTextField();
        add(emailField);

        saveButton = new JButton("Save");
        add(saveButton);

        saveButton.addActionListener(e -> saveEmployee());
        setVisible(true);
    }

    private void saveEmployee() {
        String name = nameField.getText().trim();
        String dept = deptField.getText().trim();
        String email = emailField.getText().trim();

        if (name.isEmpty() || dept.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] data = {name, dept, email};
        try {
            CsMiniProject.appendToCSV("employees.csv", data);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving the employee!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        JOptionPane.showMessageDialog(this, "Employee saved successfully!");
    }
}

class FacilitatorPanel extends JFrame {
    private final JTextField nameField;
    private final JTextField emailField;
    private final JComboBox<String> expertiseBox;
    private final JButton saveButton;

    public FacilitatorPanel() {
        setTitle("Add Facilitator");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Expertise Area:"));
        String[] expertiseOptions = {"Leadership", "Data Analytics", "Communication Skills"};
        expertiseBox = new JComboBox<>(expertiseOptions);
        add(expertiseBox);

        add(new JLabel("Email ID:"));
        emailField = new JTextField();
        add(emailField);

        saveButton = new JButton("Save");
        add(saveButton);

        saveButton.addActionListener(e -> saveFacilitator());
        setVisible(true);
    }

    private void saveFacilitator() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String expertise = (String) expertiseBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] data = {name, expertise, email};
        try {
            CsMiniProject.appendToCSV("facilitators.csv", data);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving the facilitator!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Facilitator saved successfully!");
    }
}