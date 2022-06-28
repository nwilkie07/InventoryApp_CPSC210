package model;

import java.time.LocalDateTime;

//Manages a single Inventory instance, a singleton of Inventory
public class InventoryUtility {
    private static Inventory instance = null;

    //EFFECTS: Constructs Inventory single instance with empty inventory.
    public InventoryUtility() {

    }

    //EFFECTS: Constructs a single static instance of an Inventory
    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory("Inventory_" + LocalDateTime.now());
        }
        return instance;
    }

}
