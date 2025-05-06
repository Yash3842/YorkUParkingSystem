package com.yorku.parking.view;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.yorku.parking.controller.UserManager;
import com.yorku.parking.model.*;

public class ManageBookingPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserManager userManager;
    private final Client client;
    private final IParkingSpace bookedSpace;

    private LocalDateTime bookingStartTime;
    private boolean bookingCanceled = false;
    private boolean extended = false;
    private double additionalCost = 0.0;

    public ManageBookingPanel(MainFrame mainFrame, UserManager userManager, Client client, IParkingSpace space) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;
        this.client = client;
        this.bookedSpace = space;

        this.bookingStartTime = LocalDateTime.now().plusSeconds(10); // Simulate future start time

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("🔧 Manage Your Booking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        infoPanel.setBorder(BorderFactory.createTitledBorder("Booking Details"));

        infoPanel.add(new JLabel("📍 Space: " + bookedSpace.getDescription()));
        infoPanel.add(new JLabel("🆔 ID: " + bookedSpace.getId()));
        infoPanel.add(new JLabel("⏰ Booking Start Time: " +
                bookingStartTime.format(DateTimeFormatter.ofPattern("hh:mm a"))));
        infoPanel.add(new JLabel("🚘 License Plate: [Registered]"));

        add(infoPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton cancelBtn = new JButton("Cancel Booking");
        cancelBtn.addActionListener(e -> {
            LocalDateTime now = LocalDateTime.now();
            if (now.isAfter(bookingStartTime)) {
                JOptionPane.showMessageDialog(this,
                        "⛔ You cannot cancel the booking after the start time.",
                        "Cancellation Not Allowed",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to cancel the booking?",
                    "Cancel Booking", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bookingCanceled = true;
                bookedSpace.enableSpace();
                JOptionPane.showMessageDialog(this, "❌ Booking cancelled.");
                mainFrame.switchPanel("Login");
            }
        });

        JButton extendBtn = new JButton("Extend Booking");
        extendBtn.addActionListener(e -> {
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(bookingStartTime)) {
                JOptionPane.showMessageDialog(this,
                        "⛔ You can only extend after the booking start time.",
                        "Extension Not Allowed",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String input = JOptionPane.showInputDialog(this,
                    "Enter number of extra hours to extend:",
                    "Extend Booking",
                    JOptionPane.QUESTION_MESSAGE);
            if (input != null && !input.isEmpty()) {
                try {
                    int hours = Integer.parseInt(input);
                    additionalCost = hours * client.getHourlyRate();
                    extended = true;
                    JOptionPane.showMessageDialog(this,
                            "✅ Booking extended.\nAdditional cost: $" + additionalCost);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Please enter a valid number of hours.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton proceedBtn = new JButton("Proceed to Payment");
        proceedBtn.addActionListener(e -> {
            if (bookingCanceled) {
                JOptionPane.showMessageDialog(this,
                        "No payment required. Booking was cancelled.",
                        "Cancelled", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.switchPanel("Login");
            } else {
                PaymentOptionsPanel paymentPanel = new PaymentOptionsPanel(mainFrame, userManager, client, bookedSpace, additionalCost);
                mainFrame.addPanel("PaymentOptions", paymentPanel);
                mainFrame.switchPanel("PaymentOptions");
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(extendBtn);
        buttonPanel.add(proceedBtn);
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
