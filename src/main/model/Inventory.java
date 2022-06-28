package model;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Inventory {
    private String invName;
    private HashMap<Integer, Item> itemsInInv;

    //MODIFIES: this
    // EFFECTS: initializes itemsInInv as an empty
    //          hashmap of type <Integer, Item>.
    public Inventory(String theName) {
        this.itemsInInv = new HashMap<>();
        this.invName = theName;
    }

    /*
     *MODIFIES: this, itemsInInv LinkedList
     *EFFECTS: adds Item to LinkedList
     *          returns true if item is successfully added
     *          returns false if item was not added
     */
    public boolean addItemToInv(Item theItem) {
        if (theItem == null) {
            System.out.println("The item is null.");
            return false;
        }
        if (itemsInInv.containsKey(theItem.getIdNum())) {
            return false;
        } else {
            EventLog.getInstance().logEvent(new Event("Item of ID: "
                    + theItem.getIdNum() + " and Name "
                    + theItem.getCustomStringAttribute("Name")
                    + " added to the inventory"));
            itemsInInv.put(theItem.getIdNum(), theItem);
            return true;
        }
    }

    /*
     *REQUIRES: ItemID >= 0, must be a unique ID
     *MODIFIES: this, itemsInInv LinkedList
     *EFFECTS: removes item with ID
     *          returns true if item was removed
     *          returns false if item was not removed
     */
    public boolean removeItemFromInv(Integer itemId) {
        if (itemsInInv.containsKey(itemId)) {
            EventLog.getInstance().logEvent(new Event("Item: "
                    + itemsInInv.get(itemId).getCustomStringAttribute("Name")
                    + " removed from the inventory"));
            itemsInInv.remove(itemId);
            return true;
        } else {
            return false;
        }
    }

    /*
     *MODIFIES: this, itemsInInv LinkedList
     *EFFECTS: removes item with itemName
     *          returns true if item was removed
     *          returns false if item was not removed
     */
    public boolean removeItemFromInv(String itemName) {
        Item selectedItem = (getItemByName(itemName));
        if (selectedItem != null) {
            EventLog.getInstance().logEvent(new Event("Item: "
                    + getItemByName(itemName).getCustomStringAttribute("Name")
                    + " removed from the inventory"));
            itemsInInv.remove(selectedItem.getIdNum());
            return true;
        }
        return false;
    }

    public String getInvName() {
        return invName;
    }

    public void setInvName(String invName) {
        this.invName = invName;
    }


    //EFFECTS: returns Hashmap of all items in the inventory
    //         with Integer key and Item values
    public HashMap<Integer, Item> getItemsInInv() {
        return itemsInInv;
    }

    //EFFECTS: returns an integer representing the
    //  number of items in the inventory.
    public Integer size() {
        return itemsInInv.size();
    }

    //EFFECTS: returns Item if Item from inventory with ID theId exists
    //         returns null if Item from inventory with ID theId does not exist
    public Item getItemById(Integer theId) {
        return itemsInInv.get(theId);
    }

    //EFFECTS: returns Item if Item from inventory with name itemName exists
    //         returns null if Item from inventory with name does not exist
    public Item getItemByName(String itemName) {
        for (Map.Entry<Integer, Item> entry : itemsInInv.entrySet()) {
            Integer key = entry.getKey();
            Item value = entry.getValue();
            if (value.getName().equals(itemName)) {
                return itemsInInv.get(key);
            }
        }
        return null;
    }

    //REQUIRES: theId >= 0
    //EFFECTS: returns true if an item in the inventory exists with theId
    //         returns false if an item does not exist in the inventory with theId
    public boolean doesIdExist(Integer theId) {
        return itemsInInv.containsKey(theId);
    }

    //EFFECTS: returns true if an item is in inventory with the name itemName
    //         returns false an item with itemName is not found in the inventory
    public boolean doesNameExist(String itemName) {
        return getItemByName(itemName) != null;
    }

    //EFFECTS: returns an Integer representing the ID of an item if Item is not null
    //         returns -1 if Item is null (no item found corresponding to itemName)
    public Integer itemNameToId(String itemName) {
        Item selectedItem = getItemByName(itemName);
        if (selectedItem != null) {
            return selectedItem.getIdNum();
        }
        return -1;
    }

    //REQUIRES: theId >= 0, replacement is not null
    //MODIFIES: this
    //EFFECTS: removes old item from inventory
    //         adds new item to inventory
    public void replaceItem(Integer theId, Item replacement) {
        EventLog.getInstance().logEvent(new Event("Item: "
                + itemsInInv.get(theId).getCustomStringAttribute("Name") + " replaced by "
                + replacement.getCustomStringAttribute("Name")));
        itemsInInv.remove(theId);
        itemsInInv.put(replacement.getIdNum(), replacement);
    }


    //EFFECTS: returns a JSONObject with all key = Integer
    //         and value item.toJson() in itemsInInv HashMap
    public JSONObject toJson() {
        JSONObject itemSetJson = new JSONObject();
        for (Integer itemId : itemsInInv.keySet()) {
            itemSetJson.put(itemId.toString(), itemsInInv.get(itemId).toJson());
        }
        return itemSetJson;
    }

    //MODIFIES: this
    //EFFECTS: Re-initializes the inventory item list itemInInv.
    public void removeAllItemsFromInventory() {
        itemsInInv = new HashMap<>();
        EventLog.getInstance().logEvent(new Event("Inventory was cleared of all items."));
    }
}
