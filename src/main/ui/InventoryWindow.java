package ui;

import model.Event;
import model.EventLog;
import model.Inventory;
import model.InventoryUtility;
import model.Item;
import org.json.JSONException;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.windows.ItemAdder;
import ui.windows.ItemDataPopUp;
import ui.windows.ItemSelect;
import ui.windows.ItemShelf;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

// Represents the main User GUI for managing an inventory with a menu bar for navigation.
// It displays different screens (JPanel) for each set of actions to be performed on an inventory.
public class InventoryWindow extends JFrame {
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private JFrame mainWindow = new JFrame();
    private JScrollPane scrollPane = new JScrollPane();
    private Inventory theInventory = InventoryUtility.getInstance();
    private ItemAdder theAdder = new ItemAdder(this);
    private ItemSelect theItemSelect = new ItemSelect(this);
    private JFileChooser filePicker = new JFileChooser();
    private JMenuItem open = new JMenuItem("Open");
    private JMenuItem saveAs = new JMenuItem("Save As");
    private JMenuItem imageShelf = new JMenuItem("Image Shelf");
    private JMenuItem detailedShelf = new JMenuItem("Detailed Shelf");
    private JMenuItem addItems = new JMenuItem("Add Items");
    private JMenuItem editItems = new JMenuItem("Edit Items");
    private ItemDataPopUp popUpMenu = new ItemDataPopUp(this);


    //EFFECTS: Constructs the main user GUI window and menu bar
    public InventoryWindow() {
        FileNameExtensionFilter fileType = new FileNameExtensionFilter("Json", "json");
        filePicker.setFileFilter(fileType);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(SCREEN_SIZE);

        setupFileActions();
        setupNavigationActions();

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenu navMenu = new JMenu("Navigation");
        menuBar.add(navMenu);
        fileMenu.add(open);
        fileMenu.add(saveAs);
        navMenu.add(imageShelf);
        navMenu.add(detailedShelf);
        navMenu.add(addItems);
        navMenu.add(editItems);
        printOutEvents();
        mainWindow.pack();
        mainWindow.setJMenuBar(menuBar);
        mainWindow.setSize(SCREEN_SIZE);
        mainWindow.setVisible(true);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //EFFECTS: Displays all events recorded from using the application to the user.
    public void printOutEvents() {
        mainWindow.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Iterator<Event> theIterator = EventLog.getInstance().iterator();

                while (theIterator.hasNext()) {
                    System.out.println(theIterator.next());
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Constructs and adds AbstractActions to each file I/O button.
    private void setupFileActions() {
        open.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                readInvFromFile();
            }
        });

        saveAs.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    writeInvToFile();
                } catch (NullPointerException noSaveFileSelected) {
                    popUpMenu.displayText("No save file name specified.");
                }

            }
        });

        detailedShelf.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToDetailedShelf();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: Constructs and adds AbstractActions to each navigation button.
    private void setupNavigationActions() {
        addItems.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToItemAdder();
            }
        });

        imageShelf.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToImageShelf();
            }
        });

        editItems.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goToItemSelect();
            }
        });
    }

    //REQUIRES: panel != null
    //MODIFIES: this
    //EFFECTS: Sets the content pane to the JPanel panel.
    public void setPanel(JPanel panel) {
        mainWindow.setContentPane(panel);
        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    //REQUIRES: panel != null
    //MODIFIES: this
    //EFFECTS: Sets the content pane to the JScrollPane panel.
    public void setPanel(JScrollPane panel) {
        mainWindow.setContentPane(panel);
        mainWindow.setVisible(true);
        mainWindow.revalidate();
        mainWindow.repaint();
    }

    //EFFECTS: Gets the constructed JPanel from the itemAdder class and
    //         sends it to this setPanel method.
    public void goToItemAdder() {
        setPanel(theAdder.getMasterPanel());
    }

    //EFFECTS: Gets the constructed JPanel from the itemSelect class and
    //         sends it to this setPanel method.
    public void goToItemSelect() {
        theItemSelect.getItemNames();
        setPanel(theItemSelect.getMasterPanel());
    }

    //EFFECTS: Gets the constructed JPanel from the itemShelf class and
    //         sends it to this setPanel method.
    public void goToImageShelf() {
        ItemShelf theShelf = new ItemShelf(this);
        try {
            theShelf.imageShelf();
        } catch (IOException e) {
            System.out.println("Ran into error when loading inventory items for shelf.");
        }
        setPanel(theShelf.getMasterPanel());
    }

    //EFFECTS: Gets the constructed JPanel from the itemShelf class and
    //         sends it to this setPanel method.
    public void goToDetailedShelf() {
        ItemShelf theShelf = new ItemShelf(this);
        try {
            theShelf.textShelf();
        } catch (IOException e) {
            System.out.println("Ran into error when loading inventory items for shelf.");
        }
        setPanel(theShelf.getMasterPanel());
    }

    // EFFECTS: Shows file picker GUI element to select a file to open.
    //          Processes the input. Reads in inventory json file,
    //          clears current inventory, and adds items from the file.
    public void readInvFromFile() {
        filePicker.showOpenDialog(mainWindow);
        File file = filePicker.getSelectedFile();
        try {
            JsonReader fileReader = new JsonReader(file.getAbsolutePath());
            fileReader.read();
        } catch (IOException e) {
            System.out.println("There was an IOException.");
        } catch (NullPointerException itsNull) {
            popUpMenu.displayText("No file selected.");
        } catch (JSONException wrongFileType) {
            popUpMenu.displayText("Wrong file type selected.");
        }
    }

    // MODIFIES: this
    // EFFECTS: Shows file picker GUI element to set a file save location and file name.
    //          Processes input.
    //          Writes Inventory to Json file in selected file location.
    public void writeInvToFile() throws NullPointerException {
        filePicker.showSaveDialog(mainWindow);
        String newPath = filePicker.getSelectedFile().getPath();
        if (filePicker.getSelectedFile().getPath().contains(".json")) {
            newPath = newPath.substring(0, newPath.length() - 5);
        }
        JsonWriter writer = new JsonWriter("", newPath);
        try {
            writer.open();
            writer.write(InventoryUtility.getInstance());
            writer.close();
            //System.out.println("File written successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("File not found exception.");
        }
    }

    //EFFECTS: Returns a formatted string of all the properties of theItem.
    public String itemPropertyReport(Item theItem) throws NullPointerException {
        HashMap<String, String> tempStringData = theItem.getAllStringAttributes();
        HashMap<String, Double> tempNumData = theItem.getAllNumberAttributes();

        StringBuilder reportBuilder = new StringBuilder(theItem.getName() + "\n" + theItem.getIdNum() + "\n");
        for (String keyStr : tempStringData.keySet()) {
            if (!keyStr.equals("Name")) {
                reportBuilder.append(keyStr).append(": ").append(tempStringData.get(keyStr)).append("\n");
            }
        }
        StringBuilder report = new StringBuilder(reportBuilder.toString());
        for (String keyStr : tempNumData.keySet()) {
            report.append(keyStr).append(": ").append(tempNumData.get(keyStr)).append("\n");
        }
        return report.toString();
    }
}
