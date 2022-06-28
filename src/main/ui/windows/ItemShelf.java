package ui.windows;

import model.Inventory;
import model.InventoryUtility;
import model.Item;
import ui.InventoryWindow;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

//Represents a window displaying all item names with their associated pictures.
public class ItemShelf {
    GridLayout display;
    Inventory theInventory;
    JPanel outerShelf;
    JPanel innerShelf;
    ItemDataPopUp itemDataPopUp;
    JScrollPane scrollPane;
    InventoryWindow mainWindow;
    JButton tempButton;

    //EFFECTS: Constructs the itemShelf GUI components.
    public ItemShelf(InventoryWindow theWindow) {
        mainWindow = theWindow;
        scrollPane = new JScrollPane();
        display = new GridLayout(0, 4);
        theInventory = InventoryUtility.getInstance();
        innerShelf = new JPanel(display);
        outerShelf = new JPanel();
        scrollPane.setViewportView(innerShelf);
        itemDataPopUp = new ItemDataPopUp(mainWindow);
    }

    //EFFECTS: Adds buttons to the UI with the item name and image as the icon.
    public void imageShelf() throws IOException {
        HashMap<Integer, Item> items = theInventory.getItemsInInv();
        String filepath;

        for (int id : items.keySet()) {
            if (items.get(id).getCustomStringAttribute("image") != null) {
                filepath = items.get(id).getCustomStringAttribute("image");
            } else {
                filepath = "./data/sleepy-cat.jpg";
            }
            BufferedImage theIcon;
            theIcon = ImageIO.read(new File(filepath));
            Image scaled = theIcon.getScaledInstance(400, 300, BufferedImage.SCALE_SMOOTH);
            Icon tempIcon = new ImageIcon(scaled);

            tempButton = new JButton(items.get(id).getName(), tempIcon);
            tempButton.setSize(new Dimension(400, 400));
            tempButton.setVerticalTextPosition(SwingConstants.TOP);
            tempButton.setBackground(Color.white);
            tempButton.setHorizontalTextPosition(SwingConstants.CENTER);
            addActionListeners(id);
            innerShelf.add(tempButton);
        }
    }

    //EFFECTS: Creates buttons and text for inventory items and adds them to the JPanel
    public void textShelf() throws IOException {
        HashMap<Integer, Item> items = theInventory.getItemsInInv();
        String filepath;

        for (int id : items.keySet()) {
            if (items.get(id).getCustomStringAttribute("image") != null) {
                filepath = items.get(id).getCustomStringAttribute("image");
            } else {
                filepath = "./data/sleepy-cat.jpg";
            }
            BufferedImage theIcon;
            theIcon = ImageIO.read(new File(filepath));
            Image scaled = theIcon.getScaledInstance(400, 300, BufferedImage.SCALE_SMOOTH);
            Icon tempIcon = new ImageIcon(scaled);

            JLabel tempLabel = new JLabel(tempIcon);
            tempLabel.setSize(new Dimension(400, 400));
            tempLabel.setVerticalTextPosition(SwingConstants.TOP);
            tempLabel.setBackground(Color.white);
            tempLabel.setHorizontalTextPosition(SwingConstants.CENTER);
            innerShelf.add(tempLabel);

            JTextPane tempText = new JTextPane();
            tempText.setText(mainWindow.itemPropertyReport(items.get(id)));
            innerShelf.add(tempText);
        }
    }

    //EFFECT: adds an action listener to each item button to display text info on the item in a popUp
    private void addActionListeners(int theId) {
        tempButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                itemDataPopUp.displayItem(theId);
            }
        });
    }

    //EFFECTS: returns the JPanel containing all UI elements to display.
    public JScrollPane getMasterPanel() {
        return scrollPane;
    }
}
