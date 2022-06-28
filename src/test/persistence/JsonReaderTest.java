/*
 This code was modified from the JsonReaderTest class created by:
 Title: JsonSerializationDemo/JsonReader.java
 Author: Paul Carter
 Date: 2021-03-07
 Code version: 20210307
 Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

 */

package persistence;

import model.Inventory;
import model.InventoryUtility;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    Inventory instance;

    @BeforeEach
    public void setup() {
        instance = InventoryUtility.getInstance();
        instance.removeAllItemsFromInventory();
    }

    @Test
    public void openNonExistentFileTest() {
        JsonReader testReader = new JsonReader("./data/FileNotHere.json");
        try {
            testReader.read();
            fail("IOException expected!");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void testEmptyInventoryFile() {
        JsonReader testReader = new JsonReader("./data/emptyInventory.json");
        try {
            instance = testReader.read();
            assertEquals("Test Inventory", instance.getInvName());
            assertEquals(0,instance.size());
        } catch (IOException e) {
            fail("IOException should not have occurred");
        }
    }

    @Test
    public void testReadAnInventory() {
        JsonReader testReader = new JsonReader("./data/completeInventory.json");
        try {
            instance = testReader.read();
            assertEquals("First Inventory", instance.getInvName());
            assertEquals(2,instance.size());
            Item simpleItem = instance.getItemById(12);
            assertEquals("Chair",simpleItem.getCustomStringAttribute("Name"));
            Item complexItem = instance.getItemById(3);
            assertEquals("Desk",complexItem.getCustomStringAttribute("Name"));
            assertEquals(4.1,complexItem.getCustomNumberAttribute("Length"));
            assertEquals(7,complexItem.getCustomNumberAttribute("Height"));
            assertEquals(8,complexItem.getCustomNumberAttribute("Width"));
            assertEquals("furniture",complexItem.getCustomStringAttribute("Type"));
        } catch (IOException e) {
            fail("IOException should not have occurred");
        }
    }
}
