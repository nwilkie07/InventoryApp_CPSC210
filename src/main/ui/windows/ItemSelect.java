package ui.windows;

import model.Inventory;
import model.InventoryUtility;
import model.Item;
import ui.InventoryWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;

// Represents the Window with all functionality for deleting, editing, or showing items
// in the users inventory.
public class ItemSelect {
    private JList<String> itemNames = new JList<>();
    private JPanel masterPanel = new JPanel();
    private JButton show = new JButton("Show");
    private JButton edit = new JButton("Edit");
    private JButton delete = new JButton("Delete");
    private GridBagConstraints constr = new GridBagConstraints();
    private Inventory theInventory = InventoryUtility.getInstance();
    private InventoryWindow mainWindow;
    private ItemDataPopUp popUpMenu = new ItemDataPopUp(mainWindow);

    //EFFECTS: constructs the user interface for deleting, editing, or showing items in the inventory.
    public ItemSelect(InventoryWindow theWindow) {
        masterPanel.setLayout(new GridBagLayout());
        buttonSetup();

        mainWindow = theWindow;

        constr.gridwidth = 1;
        constr.gridx = 0;
        constr.gridy = 1;
        masterPanel.add(show, constr);

        constr.gridx = 1;
        masterPanel.add(edit, constr);

        constr.gridx = 2;
        masterPanel.add(delete, constr);


        masterPanel.setVisible(true);
    }

    //EFFECTS: Adds abstract actions to the show, edit, and delete buttons with methods
    //         to be called when the buttons are clicked.
    public void buttonSetup() {
        show.addActionListener(new AbstractAction("show") {
            @Override
            public void actionPerformed(ActionEvent e) {
                showItem();
            }
        });

        edit.addActionListener(new AbstractAction("edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                editItem();
            }
        });

        delete.addActionListener(new AbstractAction("delete") {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteItem();
            }
        });
    }

    //EFFECTS: Creates a list of the names of all items in the inventory.
    //         Calls the method to add the List to the JList UI element.
    public void getItemNames() {
        System.out.println("Size: " + theInventory.size());
        String[] items = new String[theInventory.getItemsInInv().size()];
        HashMap<Integer, Item> itemSet = theInventory.getItemsInInv();
        int i = 0;
        for (int id : itemSet.keySet()) {
            items[i] = itemSet.get(id).getName();
            i += 1;
        }
        itemNames.setListData(items);
        setupList();
    }

    //EFFECTS: Sets up and adds JScrollPane to the main JPanel
    private void setupList() {
        constr.gridx = 0;
        constr.gridy = 0;
        constr.gridwidth = 3;
        constr.fill = GridBagConstraints.HORIZONTAL;
        JScrollPane scroll = new JScrollPane();
        scroll.setViewportView(itemNames);
        itemNames.setLayoutOrientation(JList.VERTICAL);
        masterPanel.add(scroll, constr);

    }

    //EFFECTS: returns the JPanel containing all UI elements to display.
    public JPanel getMasterPanel() {
        return masterPanel;
    }

    //Removes the selected item in the JList element from the inventory.
    public void deleteItem() {
        if (theInventory.removeItemFromInv(itemNames.getSelectedValue())) {
            popUpMenu.displayText("Item removed successfully.");
        } else {
            popUpMenu.displayItem("Error. Item not removed.");
        }

        mainWindow.goToItemSelect();
    }

    //EFFECTS: Opens up a new window will all item properties for editing.
    public void editItem() {

    }

    //EFFECTS: Displays a popUp with the properties of the
    //         selected item form the JList element.
    public void showItem() {
        ItemDataPopUp data = new ItemDataPopUp(mainWindow);
        data.displayItem(itemNames.getSelectedValue());
    }
}