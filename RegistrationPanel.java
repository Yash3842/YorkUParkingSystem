package com.yorku.parking.view;

import javax.swing.*;
import java.awt.*;
import com.yorku.parking.controller.UserManager;
import com.yorku.parking.util.UserCSVLogger;

public class RegistrationPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserManager userManager;

    public RegistrationPanel(MainFrame mainFrame, UserManager userManager) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JLabel title = new JLabel("Register New User");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(Color.DARK_GRAY);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.anchor = GridBagConstraints.LINE_END;
        add(emailLabel, gbc);

        JTextField emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(emailField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.anchor = GridBagConstraints.LINE_END;
        add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(passwordField, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        JLabel typeLabel = new JLabel("User Type:");
        typeLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.anchor = GridBagConstraints.LINE_END;
        add(typeLabel, gbc);

        String[] types = {"Student", "Faculty", "Staff", "Visitor"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        typeBox.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        add(typeBox, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton registerBtn = new JButton("Register");
        registerBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        registerBtn.setOpaque(true);
        registerBtn.setBackground(Color.BLACK);
        registerBtn.setForeground(Color.WHITE);
        registerBtn.setBorderPainted(false);
        registerBtn.setContentAreaFilled(true);
        registerBtn.setFocusPainted(false);
        registerBtn.setPreferredSize(new Dimension(140, 40));
        add(registerBtn, gbc);

        gbc.gridy++;
        JButton switchBtn = new JButton("Already have an account? Login");
        switchBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        switchBtn.setFocusPainted(false);
        switchBtn.setContentAreaFilled(false);
        switchBtn.setBorderPainted(false);
        switchBtn.setForeground(new Color(0, 102, 204));
        add(switchBtn, gbc);

        registerBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());
            String type = (String) typeBox.getSelectedItem();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (UserCSVLogger.emailExists(email)) {
                JOptionPane.showMessageDialog(this,
                        "⚠️ This email is already registered.\nPlease use another email or log in.",
                        "Duplicate Email",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            userManager.registerClient(email, password, type);
            JOptionPane.showMessageDialog(this, "✅ Registration successful! Please login.");
            mainFrame.switchPanel("Login");
        });

        switchBtn.addActionListener(e -> mainFrame.switchPanel("Login"));
    }
}
