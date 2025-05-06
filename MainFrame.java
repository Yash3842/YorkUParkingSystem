package com.yorku.parking.view;

import javax.swing.*;
import java.awt.*;
import com.yorku.parking.controller.*;
import com.yorku.parking.model.*;

public class MainFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel mainPanel;
    private final UserManager userManager;

    public MainFrame() {
        setTitle("YorkU Parking Booking System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        userManager = new UserManager();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(this, userManager), "Login");
        mainPanel.add(new RegistrationPanel(this, userManager), "Registration");
        mainPanel.add(new ManagerDashboard(this, userManager), "ManagerDashboard");
        mainPanel.add(new ManagerCreationPanel(this, userManager), "ManagerCreation"); // ✅ NEW

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }

    public void switchPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public void addPanel(String name, JPanel panel) {
        mainPanel.add(panel, name);
    }

    public void loadUserDashboard(Client client) {
        UserDashboard dashboard = new UserDashboard(this, userManager, client);
        mainPanel.add(dashboard, "UserDashboard");
        switchPanel("UserDashboard");
    }

    public void loadManagerCreationPanel() {
        switchPanel("ManagerCreation");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
