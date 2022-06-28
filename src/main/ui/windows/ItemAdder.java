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
import java.util.ArrayList;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

//Represents the Window with all functionality for adding items to the inventory
//  and adding a picture to an item.
public class ItemAdder {
    private JTextField listLength = new JTextField();
    private JButton listLengthButton = new JButton();
    private JButton submitButton = new JButton();
    private JButton imageButton = new JButton();
    private JPanel properties = new JPanel();
    private JPanel masterPanel = new JPanel(new FlowLayout());
    private ArrayList<JTextField> textBoxes = new ArrayList<>();
    private InventoryWindow mainApp;
    private Inventory theInventory = InventoryUtility.getInstance();
    private GridBagConstraints itemDesign = new GridBagConstraints();
    private Dimension fieldSize = new Dimension(200, 25);
    private JFileChooser filePicker = new JFileChooser();
    private String imagePath = "./data/sleepy-cat.jpg";
    ItemDataPopUp popUpMenu = new ItemDataPopUp(mainApp);

    //EFFECTS: constructs the user interface for adding items to the inventory.
    public ItemAdder(InventoryWindow theWindow) {
        mainApp = theWindow;

        JPanel submit = new JPanel();
        GridBagLayout dynamicGrid = new GridBagLayout();
        submit.setLayout(dynamicGrid);
        JPanel sizeControl = new JPanel();
        sizeControl.setLayout(dynamicGrid);
        properties.setLayout(dynamicGrid);

        setButtonDimAndText();
        buttonActionSetup();
        imageButtonActionSetup();

        itemDesign.gridx = 0;
        itemDesign.gridy = 0;
        sizeControl.add(listLength, itemDesign);
        submit.add(submitButton, itemDesign);
        itemDesign.gridx = 1;


        sizeControl.add(listLengthButton, itemDesign);
        addMandatoryProperty("Name");
        addMandatoryProperty("ID Number");

        masterPanel.add(sizeControl, 0);
        masterPanel.add(properties, 1);
        masterPanel.add(imageButton, 2);
        masterPanel.add(submitButton, 3);
        mainApp.setPanel(masterPanel);
    }

    //EFFECTS: Sets all buttons to specified dimensions.
    private void setButtonDimAndText() {
        listLengthButton.setText("Update Number of Properties");
        submitButton.setText("Submit Item");
        imageButton.setText("Add Image");
        listLength.setPreferredSize(new Dimension(100, 50));
        listLengthButton.setPreferredSize(new Dimension(200, 50));
        submitButton.setPreferredSize(new Dimension(150, 50));
        imageButton.setPreferredSize(new Dimension(100, 50));
    }

    //EFFECTS: adds AbstractActions to each button with associated methods
    private void buttonActionSetup() {
        listLength.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent propQuantity) {
                changePropQuantity();
            }
        });
        submitButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent addItem) {
                submitButtonPressed();
            }
        });
        listLengthButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent setPropLength) {
                changePropQuantity();
            }
        });
    }

    //EFFECTS: adds AbstractActions to each button for selecting an image.
    private void imageButtonActionSetup() {
        imageButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent setImage) {
                try {
                    filePicker.showOpenDialog(mainApp);
                    imagePath = "./data/" + filePicker.getSelectedFile().getName();
                } catch (NullPointerException itsNull) {
                    popUpMenu.displayText("No image file selected.");
                }
            }
        });
    }

    //EFFECTS: Computes the requested change to the number
    //         of properties for the item that are displayed
    //         and calls the associated method to add or remove
    //         text boxes used for the properties.
    public void changePropQuantity() {
        try {
            int add = (parseInt(listLength.getText()) * 2 - properties.getComponentCount());
            if (add > 0) {
                addToGrid(add);
            } else if (add < 0) {
                if ((properties.getComponentCount() + add) > 3) {
                    removeFromGrid(Math.abs((add)));
                }

            }
        } catch (NumberFormatException num) {
            popUpMenu.displayText("Incorrect number format or no number specified.");
        }


    }

    //EFFECTS: Calls method to add the item to the Inventory
    //         and clear the data from the property text boxes.
    public void submitButtonPressed() {
        try {
            processItem();
            clearFields();
        } catch (NumberFormatException noId) {
            popUpMenu.displayText("Incorrect Id or No Id added.");
        }

    }

    //MODIFIES: this
    //EFFECTS: Sets text in all the text boxes to default.
    public void clearFields() {
        textBoxes.get(1).setText("");
        textBoxes.get(3).setText("");

        for (int i = 4; i < textBoxes.size(); i += 2) {
            textBoxes.get(i + 1).setText("");
            textBoxes.get(i).setText("");
        }
    }

    //MODIFIES: this
    //EFFECTS: Removes the requested number of rows,
    //         specified by the integer remove, of
    //         text boxes displayed to the user.
    public void removeFromGrid(int remove) {
        System.out.println("remove:" + remove);
        int theLength = properties.getComponentCount() - 1;
        if (remove > 1) {
            for (int i = 0; i < remove; i++) {
                properties.remove(theLength - i);
                textBoxes.remove(theLength - i);

            }
        } else {
            properties.remove(theLength);
            properties.remove(theLength - 1);
            textBoxes.remove(theLength - 1);
        }

        properties.revalidate();
        properties.repaint();
    }

    //MODIFIES: this
    //EFFECTS: Adds the required name and ID text fields to the GUI
    public void addMandatoryProperty(String theName) {
        JTextField temp = new JTextField();
        temp.setPreferredSize(fieldSize);
        temp.setText(theName);
        temp.setEditable(false);
        itemDesign.gridx = 0;
        itemDesign.gridy = properties.getComponentCount() / 2;
        textBoxes.add(temp);
        properties.add(temp, itemDesign);

        itemDesign.gridx = 1;
        itemDesign.gridy = properties.getComponentCount() / 2;
        temp = new JTextField();
        temp.setPreferredSize(fieldSize);
        textBoxes.add(temp);
        properties.add(temp, itemDesign);

        properties.revalidate();
        properties.repaint();
    }

    //MODIFIES: this
    //EFFECTS: Adds the number of rows of text boxes specified by
    //         grow to the GUI.
    public void addToGrid(int grow) {

        for (int i = 0; i < grow; i++) {
            JTextField temp = new JTextField();
            temp.setPreferredSize(fieldSize);
            if ((i + 1) % 2 == 0) {
                itemDesign.gridx = 0;
            } else {
                itemDesign.gridx = 1;
            }

            itemDesign.gridy = properties.getComponentCount() / 2;
            textBoxes.add(temp);
            properties.add(temp, itemDesign);
        }
        properties.revalidate();
        properties.repaint();
    }

    //MODIFIES: theInventory
    //EFFECTS: Gets all text from the property text boxes
    //         and adds the data as properties to an item.
    //         The item is then added to the Inventory.
    public void processItem() throws NumberFormatException {
        String name = textBoxes.get(1).getText();
        Integer id = parseInt(textBoxes.get(3).getText());
        Item blueprint = new Item(name, id);
        String theProperty;
        String theValue;
        for (int i = 4; i < textBoxes.size(); i += 2) {
            theProperty = textBoxes.get(i + 1).getText();
            theValue = textBoxes.get(i).getText();
            if (isNumInput(theValue)) {
                blueprint.setCustomAttribute(theProperty, parseDouble(theValue));
            } else {
                blueprint.setCustomAttribute(theProperty, theValue);
            }
        }
        if (!imagePath.equals("./data/sleepy-cat.jpg")) {
            processImage();
        }
        blueprint.setCustomAttribute("image", imagePath);
        theInventory.addItemToInv(blueprint);
        popUpMenu.displayText("Item added Successfully.");
    }

    // EFFECTS: returns true if the input value is a Double number
    //          else returns false
    public boolean isNumInput(String value) {
        try {
            parseDouble(value);
            return true;
        } catch (NumberFormatException num) {
            return false;
        }
    }

    //EFFECTS: returns the JPanel containing all UI elements to display.
    public JPanel getMasterPanel() {
        return masterPanel;
    }

    //EFFECTS: Copies the image for the item into the ./data/ folder.
    public void processImage() {
        try {
            BufferedImage loadedImage = ImageIO.read(new File(filePicker.getSelectedFile().getAbsolutePath()));
            File outputImage = new File(imagePath);
            ImageIO.write(loadedImage, "jpg", outputImage);
        } catch (IOException noFile) {
            System.out.println("No file when changing image.");
        }
    }

}
