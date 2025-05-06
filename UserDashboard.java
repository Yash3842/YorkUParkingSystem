package com.yorku.parking.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import com.yorku.parking.controller.*;
import com.yorku.parking.model.*;

public class UserDashboard extends JPanel implements Observer {
    private final MainFrame mainFrame;
    private final UserManager userManager;
    private final Client client;
    private final JComboBox<String> lotSelector;
    private final DefaultListModel<String> listModelLeft;
    private final DefaultListModel<String> listModelRight;
    private final JList<String> spaceListLeft;
    private final JList<String> spaceListRight;
    private final BookingManager bookingManager;
    private List<IParkingSpace> currentSpaces;

    public UserDashboard(MainFrame mainFrame, UserManager userManager, Client client) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;
        this.client = client;
        this.bookingManager = new BookingManager();

        setLayout(new BorderLayout(15, 15));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("🚗 Parking Space Booking", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        topPanel.add(titleLabel, BorderLayout.NORTH);

        lotSelector = new JComboBox<>();
        for (ParkingLot lot : userManager.getParkingLots()) {
            lotSelector.addItem(lot.getName());
        }

        lotSelector.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lotSelector.setPreferredSize(new Dimension(200, 30));
        lotSelector.addActionListener(e -> refreshSpaces());
        topPanel.add(lotSelector, BorderLayout.SOUTH);

        listModelLeft = new DefaultListModel<>();
        listModelRight = new DefaultListModel<>();
        spaceListLeft = new JList<>(listModelLeft);
        spaceListRight = new JList<>(listModelRight);

        spaceListLeft.setFont(new Font("Monospaced", Font.PLAIN, 14));
        spaceListRight.setFont(new Font("Monospaced", Font.PLAIN, 14));

        spaceListLeft.setCellRenderer(new ColoredListCellRenderer());
        spaceListRight.setCellRenderer(new ColoredListCellRenderer());

        spaceListLeft.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        spaceListRight.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Parking Space A"));
        leftPanel.add(new JScrollPane(spaceListLeft), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Parking Space B"));
        rightPanel.add(new JScrollPane(spaceListRight), BorderLayout.CENTER);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        legendPanel.add(makeLegend("Available", new Color(0, 128, 0)));
        legendPanel.add(makeLegend("Unavailable", Color.RED));
        legendPanel.add(makeLegend("Charging Station", Color.BLUE));
        legendPanel.add(makeLegend("Disabled", Color.BLACK));

        JPanel mainContainer = new JPanel(new BorderLayout(10, 10));
        mainContainer.add(topPanel, BorderLayout.NORTH);
        mainContainer.add(centerPanel, BorderLayout.CENTER);
        mainContainer.add(legendPanel, BorderLayout.SOUTH);
        add(mainContainer, BorderLayout.CENTER);

        JButton bookButton = new JButton("Book Selected Space");
        bookButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bookButton.setBackground(Color.DARK_GRAY);
        bookButton.setForeground(Color.BLACK);
        bookButton.setFocusPainted(false);
        bookButton.setPreferredSize(new Dimension(250, 45));

        bookButton.addActionListener(e -> {
            IParkingSpace selectedSpace = null;
            int leftIndex = spaceListLeft.getSelectedIndex();
            int rightIndex = spaceListRight.getSelectedIndex();

            if (leftIndex >= 0) {
                selectedSpace = currentSpaces.get(leftIndex);
            } else if (rightIndex >= 0) {
                selectedSpace = currentSpaces.get((currentSpaces.size() / 2) + rightIndex);
            }

            if (selectedSpace != null && selectedSpace.getStatus().equals("Available")) {
                String licensePlate = JOptionPane.showInputDialog(this, "Enter your license plate number:");
                if (licensePlate == null || licensePlate.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "⚠️ License plate is required.");
                    return;
                }

                double rate = client.getHourlyRate();
                int confirm = JOptionPane.showConfirmDialog(this,
                        "A 1-hour non-refundable fee of $" + rate + " will be charged.\nProceed?",
                        "Confirm Payment",
                        JOptionPane.YES_NO_OPTION);

                if (confirm != JOptionPane.YES_OPTION) return;

                Command bookCommand = new BookSpaceCommand(bookingManager, client, selectedSpace);
                bookingManager.executeCommand(bookCommand);

                selectedSpace.markOccupied(); // ✅ Mark it as occupied so others can't book

                Payment payment = new Payment(new CreditCardStrategy()) {
                    @Override
                    public boolean processPayment(double amount) {
                        return true;
                    }
                };
                payment.processPayment(rate);

                ManageBookingPanel manageBookingPanel = new ManageBookingPanel(mainFrame, userManager, client, selectedSpace);
                mainFrame.addPanel("ManageBooking", manageBookingPanel);
                mainFrame.switchPanel("ManageBooking");
            } else {
                JOptionPane.showMessageDialog(this, "⚠️ Please select an available parking space.");
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(bookButton);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshSpaces();

        for (ParkingLot lot : userManager.getParkingLots()) {
            for (IParkingSpace space : lot.getSpaces()) {
                space.addObserver(this);
            }
        }
    }

    public void refreshSpaces() {
        listModelLeft.clear();
        listModelRight.clear();

        String selectedLot = (String) lotSelector.getSelectedItem();
        ParkingLot lot = userManager.getParkingLotByName(selectedLot);
        if (lot == null) return;

        currentSpaces = lot.getSpaces();
        int half = (int) Math.ceil(currentSpaces.size() / 2.0);

        for (int i = 0; i < currentSpaces.size(); i++) {
            IParkingSpace space = currentSpaces.get(i);
            String desc;

            if (space.getDescription().toLowerCase().contains("accessible")) {
                desc = "Accessible Parking Space, ";
            } else if (space.getDescription().toLowerCase().contains("charging")) {
                desc = "Charging Station Parking, ";
            } else {
                desc = "Basic Parking Space, ";
            }

            desc += space.getStatus();

            if (i < half) {
                listModelLeft.addElement(desc);
            } else {
                listModelRight.addElement(desc);
            }
        }
    }

    @Override
    public void update(IParkingSpace space) {
        refreshSpaces();
    }

    private class ColoredListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String text = value.toString().toLowerCase();

            if (text.contains("disabled")) {
                label.setForeground(Color.BLACK);
            } else if (text.contains("charging")) {
                label.setForeground(Color.BLUE);
            } else if (text.contains("available")) {
                label.setForeground(new Color(0, 128, 0));
            } else {
                label.setForeground(Color.RED);
            }

            return label;
        }
    }

    private JPanel makeLegend(String labelText, Color color) {
        JPanel box = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel colorSquare = new JPanel();
        colorSquare.setBackground(color);
        colorSquare.setPreferredSize(new Dimension(15, 15));
        colorSquare.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JLabel label = new JLabel(" " + labelText);
        box.add(colorSquare);
        box.add(label);
        return box;
    }
}
