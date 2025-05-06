package com.yorku.parking.view;

import com.yorku.parking.model.IParkingSpace;
import com.yorku.parking.model.Observer;

/**
 * A GUI component that observes changes in parking space status and updates the display.
 */
public class GUIComponent implements Observer {

    /**
     * Called when a parking space's status changes.
     * Updates the GUI to reflect the new status of the given parking space.
     *
     * @param space The parking space whose status has changed.
     */
    @Override
    public void update(IParkingSpace space) {
        // Log the update (for demonstration; replace with actual GUI logic)
        System.out.println("GUI updated: Space " + space.getDescription() + " is now " + space.getStatus());

        // TODO: Add real GUI update logic here
        // For example, refresh a list of spaces, update a label, or repaint a panel
    }
}