package com.yorku.parking.view;

import javax.swing.*;
import java.awt.*;
import com.yorku.parking.controller.*;
import com.yorku.parking.model.*;

public class LoginPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserManager userManager;

    public LoginPanel(MainFrame mainFrame, UserManager userManager) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;

        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JLabel title = new JLabel("YorkU Parking Login");
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
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginBtn.setOpaque(true);
        loginBtn.setBackground(Color.BLACK);
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setBorderPainted(false);
        loginBtn.setContentAreaFilled(true);
        loginBtn.setFocusPainted(false);
        loginBtn.setPreferredSize(new Dimension(140, 40));
        add(loginBtn, gbc);

        gbc.gridy++;
        JButton switchBtn = new JButton("New user? Register here");
        switchBtn.setFont(new Font("SansSerif", Font.PLAIN, 12));
        switchBtn.setFocusPainted(false);
        switchBtn.setContentAreaFilled(false);
        switchBtn.setBorderPainted(false);
        switchBtn.setForeground(new Color(0, 102, 204));
        add(switchBtn, gbc);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword());

            User user = userManager.login(email, password);
            if (user != null) {
                if (user instanceof Manager || user instanceof SuperManager) {
                    mainFrame.switchPanel("ManagerDashboard");
                } else {
                    mainFrame.loadUserDashboard((Client) user);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        switchBtn.addActionListener(e -> mainFrame.switchPanel("Registration"));
    }
}

