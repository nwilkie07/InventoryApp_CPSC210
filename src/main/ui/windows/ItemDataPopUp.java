package ui.windows;

import model.Inventory;
import model.InventoryUtility;
import model.Item;
import ui.InventoryWindow;

import javax.swing.*;
import java.awt.*;

//GUI pop up window for displaying Inventory item properties.
public class ItemDataPopUp {
    JFrame popUpWindow;
    JTextArea propText;
    JScrollPane scrollPane;
    Inventory theInventory = InventoryUtility.getInstance();
    InventoryWindow mainWindow;

    //EFFECTS: Constructs a GUI popUp Window.
    public ItemDataPopUp(InventoryWindow theWindow) {
        mainWindow = theWindow;
        popUpWindow = new JFrame();
        propText = new JTextArea();
        scrollPane = new JScrollPane();
        scrollPane.setViewportView(propText);
        propText.setSize(new Dimension(200, 200));
        popUpWindow.setSize(new Dimension(200, 200));
        popUpWindow.setLocation((InventoryWindow.SCREEN_SIZE.width - popUpWindow.getWidth()) / 2,
                (InventoryWindow.SCREEN_SIZE.height - popUpWindow.getHeight()) / 2);
        popUpWindow.add(propText);
    }

    //REQUIRES: item != null
    //MODIFIES: this
    //EFFECTS: Sets the text in the popUp window to the properties of the Item selected.
    //         Sets the popUp window to visible.
    public void displayItem(Item selected) {
        try {
            propText.setText(mainWindow.itemPropertyReport(selected));
            popUpWindow.setVisible(true);
            popUpWindow.repaint();
            popUpWindow.revalidate();
        } catch (NullPointerException itsNull) {
            displayText("Item to perform action on not selected.");
        }

    }

    //REQUIRES: itemId != null
    //EFFECTS: Method overload. Gets the item from the inventory by ID
    //         and passes it to the displayItem method.
    public void displayItem(int itemId) {
        displayItem(theInventory.getItemById(itemId));
    }

    //REQUIRES: itemName != null
    //MODIFIES: this
    //EFFECTS: Method overload. Gets the item from the inventory by name
    //         and passes it to the displayItem method.
    public void displayItem(String itemName) {
        displayItem(theInventory.getItemByName(itemName));
    }

    //MODIFIES: this
    //EFFECTS: Sets the text in the popUp window to the properties of the Item selected.
    //         Sets the popUp window to visible.
    public void displayText(String theMessage) {
        propText.setText(theMessage);
        propText.setAlignmentX(SwingConstants.CENTER);
        propText.setAlignmentY(SwingConstants.CENTER);
        popUpWindow.setVisible(true);
        popUpWindow.repaint();
        popUpWindow.revalidate();
    }


}
