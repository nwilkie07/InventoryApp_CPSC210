/*
 This code was modified from the JsonWriter class created by:
 Title: JsonSerializationDemo/JsonReader.java
 Author: Paul Carter
 Date: 2021-03-07
 Code version: 20210307
 Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

 */
package persistence;

import model.Inventory;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @BeforeEach
    public void writerSetup() {

    }

    @Test
    public void writeInvalidFile() {
        JsonWriter testWriter = new JsonWriter("fileNot\0There" ,"./data/");
        try {
            testWriter.open();
            fail("IOException was expected");
        } catch (IOException e) {
            //pass
        }
    }

    @Test
    public void writeEmptyInventory() {

        try {
            Inventory testInventory = new Inventory("Test Inventory");
            JsonWriter testWriter = new JsonWriter("emptyInventory", "./data/");
            testWriter.open();
            testWriter.write(testInventory);
            testWriter.close();

            JsonReader testReader = new JsonReader("./data/emptyInventory.json");
            Inventory readIn = testReader.read();
            assertEquals("Test Inventory",readIn.getInvName());
            assertEquals(0,readIn.size());

        } catch (IOException e) {
            fail("Exception should not have been thrown.");
        }
    }

    @Test
    public void writeInventoryWithCustomAttributes() {

        try {
            Inventory testInventory = new Inventory("First Inventory");
            Item simpleItem = new Item("Chair",10);
            Item complexItem = new Item("Desk",12);
            complexItem.setCustomAttribute("Color","Red");
            complexItem.setCustomAttribute("Weight",15.5);
            testInventory.addItemToInv(simpleItem);
            testInventory.addItemToInv(complexItem);

            JsonWriter testWriter = new JsonWriter("testInventory", "./data/");
            testWriter.open();
            testWriter.write(testInventory);
            testWriter.close();

            JsonReader testReader = new JsonReader("./data/testInventory.json");
            Inventory readIn = testReader.read();
            Item simpleItemIn = readIn.getItemById(10);
            Item complexItemIn = readIn.getItemById(12);

            assertEquals("Chair",simpleItemIn.getCustomStringAttribute("Name"));
            assertEquals("Desk",complexItemIn.getCustomStringAttribute("Name"));
            assertEquals("Red",complexItemIn.getCustomStringAttribute("Color"));
            assertEquals(15.5,complexItemIn.getCustomNumberAttribute("Weight"));
            assertEquals("First Inventory",readIn.getInvName());
        } catch (IOException e) {
            fail("IOException should not have occurred");
        }
    }
}
