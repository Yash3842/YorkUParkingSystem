package com.yorku.parking.view;

import javax.swing.*;
import java.awt.*;
import com.yorku.parking.controller.UserManager;

public class ManagerCreationPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserManager userManager;

    public ManagerCreationPanel(MainFrame mainFrame, UserManager userManager) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("Create New Manager");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        add(emailField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        JPasswordField passwordField = new JPasswordField(20);
        add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton createBtn = new JButton("Create Manager");
        add(createBtn, gbc);

        createBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean created = userManager.registerManager(email, password);
            if (created) {
                JOptionPane.showMessageDialog(this, "✅ Manager created successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Manager with this email already exists.", "Error", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
}
