package com.yorku.parking.view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.yorku.parking.controller.*;
import com.yorku.parking.model.*;

public class ManagerDashboard extends JPanel {
    private final MainFrame mainFrame;
    private final UserManager userManager;
    private final JComboBox<String> lotSelector;
    private final JList<String> spaceList;
    private final DefaultListModel<String> listModel;

    private ParkingLot currentLot;

    public ManagerDashboard(MainFrame mainFrame, UserManager userManager) {
        this.mainFrame = mainFrame;
        this.userManager = userManager;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lotLabel = new JLabel("Select Parking Lot: ");
        lotSelector = new JComboBox<>();

        for (ParkingLot lot : userManager.getParkingLots()) {
            lotSelector.addItem(lot.getName());
        }

        lotSelector.addActionListener(e -> {
            updateCurrentLot();
            refreshSpaces();
        });

        topPanel.add(lotLabel);
        topPanel.add(lotSelector);

        // ✅ Show create-manager button for SuperManager only
        JButton createManagerBtn = new JButton("➕ Create Manager");
        createManagerBtn.addActionListener(e -> mainFrame.loadManagerCreationPanel());

        User loggedUser = userManager.getLoggedInUser();
        if (loggedUser instanceof SuperManager) {
            topPanel.add(Box.createHorizontalStrut(20));
            topPanel.add(createManagerBtn);
        }

        add(topPanel, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        spaceList = new JList<>(listModel);
        spaceList.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(spaceList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton enableButton = new JButton("Enable Space");
        enableButton.addActionListener(e -> {
            int index = spaceList.getSelectedIndex();
            if (index >= 0 && currentLot != null) {
                currentLot.getSpaces().get(index).enableSpace();
                refreshSpaces();
            }
        });
        buttonPanel.add(enableButton);

        JButton disableButton = new JButton("Disable Space");
        disableButton.addActionListener(e -> {
            int index = spaceList.getSelectedIndex();
            if (index >= 0 && currentLot != null) {
                currentLot.getSpaces().get(index).disableSpace();
                refreshSpaces();
            }
        });
        buttonPanel.add(disableButton);

        add(buttonPanel, BorderLayout.SOUTH);

        if (lotSelector.getItemCount() > 0) {
            lotSelector.setSelectedIndex(0);
            updateCurrentLot();
            refreshSpaces();
        }
    }

    private void updateCurrentLot() {
        String selectedLotName = (String) lotSelector.getSelectedItem();
        currentLot = userManager.getParkingLotByName(selectedLotName);
    }

    public void refreshSpaces() {
        listModel.clear();
        if (currentLot == null) return;

        for (IParkingSpace space : currentLot.getSpaces()) {
            listModel.addElement(space.getDescription() + " - " + space.getStatus());
        }
    }
}
