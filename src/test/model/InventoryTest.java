package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory collection;
    Item clock;
    Item desk;
    Item phone;

    @BeforeEach
    public void setupInv() {
        collection = InventoryUtility.getInstance();
        collection.removeAllItemsFromInventory();
        clock = new Item("DeskClock", 10);
        desk = new Item("Computer Desk", 15);
        phone = new Item("Samsung Galaxy S3", 20);
        collection.addItemToInv(desk);
        collection.addItemToInv(clock);
    }

    @Test
    public void addItems() {
        assertTrue(collection.addItemToInv(phone));
        assertEquals(3, collection.size());
        assertEquals(phone, collection.getItemById(20));

    }

    @Test
    public void removeItems() {
        assertTrue(collection.removeItemFromInv("Computer Desk"));
        assertEquals(1, collection.size());
        assertFalse(collection.removeItemFromInv("Not There"));
        assertEquals(1, collection.size());
        assertFalse(collection.removeItemFromInv(1000));
        assertEquals(1, collection.size());
        assertTrue(collection.removeItemFromInv(10));
        assertEquals(0, collection.size());
    }

    @Test
    public void addItemTwice() {
        assertFalse(collection.addItemToInv(desk));
    }

    @Test
    public void addIncompatibleItem() {
        assertFalse(collection.addItemToInv(null));
    }

    @Test
    public void removeNonExistentItems() {
        assertFalse(collection.removeItemFromInv(50));
        assertFalse(collection.removeItemFromInv("Cheese"));

    }

    @Test
    public void getterSetterTest() {
        collection.setInvName("Closet");
        assertEquals("Closet",collection.getInvName());
        HashMap<Integer, Item> current = collection.getItemsInInv();
        assertEquals(clock,current.get(10));
        assertEquals(desk, current.get(15));
    }

    @Test
    public void getItemByIdTest() {
       assertEquals(collection.getItemById(10), clock);
       assertNotEquals(collection.getItemById(550), clock);
    }

    @Test
    public void getItemByNameTest() {
        assertEquals(collection.getItemByName("DeskClock"), clock);
        assertNull(collection.getItemByName("Its Nothing"));
    }

    @Test
    public void doesIdExistTest()
    {
        assertTrue(collection.doesIdExist(15));
        assertFalse(collection.doesIdExist(5000));
    }

    @Test
    public void doesNameExistTest()
    {
        assertTrue(collection.doesNameExist("DeskClock"));
        assertFalse(collection.doesNameExist("Nothing is There"));
    }

    @Test
    public void itemNameToIdTest() {
        assertEquals(collection.itemNameToId("DeskClock"),10);
        assertEquals(collection.itemNameToId("Nothing Here"), -1);
    }

    @Test
    public void replaceItemTest() {
        collection.replaceItem(10,phone);
        assertEquals(phone,collection.getItemById(20));
    }

    @Test
    public void toJsonTest() {
        Inventory testInventory = new Inventory("Inventory1");
        Item simpleItem = new Item("Chair",10);
        Item complexItem = new Item("Desk",12);
        complexItem.setCustomAttribute("Color","Yellow");
        complexItem.setCustomAttribute("Weight", 20.1);

        testInventory.addItemToInv(simpleItem);
        testInventory.addItemToInv(complexItem);
        JSONObject inventoryJson = testInventory.toJson();


        JSONObject itemSet = new JSONObject();
        JSONObject itemOne = new JSONObject();
        JSONObject itemTwo = new JSONObject();
        itemOne.put("Name","Chair");
        itemTwo.put("Name","Desk");
        itemTwo.put("Color","Yellow");
        itemTwo.put("Weight",20.1);
        itemSet.put(String.valueOf(10), itemOne);
        itemSet.put(String.valueOf(12), itemTwo);

        assertEquals(itemSet.toString(),inventoryJson.toString());
    }
}
