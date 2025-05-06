package com.yorku.parking.view;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

import com.yorku.parking.controller.UserManager;
import com.yorku.parking.model.*;

public class PaymentOptionsPanel extends JPanel {
    private static final long serialVersionUID = 1L;

    private final MainFrame mainFrame;
    private final UserManager userManager;
    private final Client client;
    private final IParkingSpace space;
    private final double additionalCost;

    private boolean userCheckedIn = false;

    public PaymentOptionsPanel(MainFrame mainFrame, UserManager userManager, Client client, IParkingSpace space, double additionalCost) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;
        this.client = client;
        this.space = space;
        this.additionalCost = additionalCost;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("💳 Select Payment Method", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        JButton creditBtn = new JButton("Pay with Credit Card");
        JButton debitBtn = new JButton("Pay with Debit Card");
        JButton mobileBtn = new JButton("Pay with Mobile");

        creditBtn.addActionListener(e -> processFinalPayment(new CreditCardStrategy(), "Credit Card"));
        debitBtn.addActionListener(e -> processFinalPayment(new DebitCardStrategy(), "Debit Card"));
        mobileBtn.addActionListener(e -> processFinalPayment(new MobilePaymentStrategy(), "Mobile Payment"));

        buttonPanel.add(creditBtn);
        buttonPanel.add(debitBtn);
        buttonPanel.add(mobileBtn);
        add(buttonPanel, BorderLayout.CENTER);

        Timer noShowTimer = new Timer();
        noShowTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!userCheckedIn) {
                    JOptionPane.showMessageDialog(PaymentOptionsPanel.this,
                            "⚠️ No check-in within 1 hour.\nNon-refundable 1-hour deposit forfeited.",
                            "No-Show Penalty", JOptionPane.WARNING_MESSAGE);
                    space.enableSpace();
                    mainFrame.switchPanel("UserDashboard");
                }
            }
        }, 60000); // 1 min for demo
    }

    private void processFinalPayment(PaymentMethodStrategy strategy, String methodName) {
        userCheckedIn = true;
        double total = client.getHourlyRate() + additionalCost;

        Payment payment = new Payment(strategy);
        payment.processPayment(total);

        System.out.println("Final payment: $" + total + " via " + methodName);

        JOptionPane.showMessageDialog(this,
                "✅ Payment of $" + total + " completed using " + methodName,
                "Payment Successful", JOptionPane.INFORMATION_MESSAGE);

        JOptionPane.showMessageDialog(this,
                "🎉 Thank you for your booking!\nYou will now be redirected to the login page.",
                "Booking Complete", JOptionPane.PLAIN_MESSAGE);

        mainFrame.switchPanel("Login");
    }
}

