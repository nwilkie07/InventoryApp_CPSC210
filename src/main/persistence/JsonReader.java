/*
 This code was modified from the JsonReader class created by:
 Title: JsonSerializationDemo/JsonReader.java
 Author: Paul Carter
 Date: 2021-03-07
 Code version: 20210307
 Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

 */

package persistence;


import model.Inventory;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import model.InventoryUtility;
import model.Item;
import org.json.*;

import static java.lang.Integer.parseInt;


// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // MODIFIES: source
    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads inventory from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Inventory read() throws IOException, JSONException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseInventory(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        } catch (UncheckedIOException wrongFile) {
            System.out.println("Wrong file type selected.");
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses inventory from JSON object and returns it
    private Inventory parseInventory(JSONObject jsonObject) {
        String invName = jsonObject.getString("Name");
        Inventory fileInv = InventoryUtility.getInstance();
        fileInv.setInvName(invName);
        fileInv.removeAllItemsFromInventory();
        JSONObject itemSet = jsonObject.getJSONObject("Items"); //key: item ID value: item attributes

        for (String key : itemSet.keySet()) {
            //key:item ID's, should be a json object with item attributes
            JSONObject anItem = itemSet.getJSONObject(key);
            Item tempItem = new Item(); //new item for adding data to
            tempItem.setIdNum(parseInt(key));
            for (String attrKey : anItem.keySet()) {
                if (anItem.get(attrKey).getClass() == String.class) {
                    tempItem.setCustomAttribute(attrKey, anItem.getString(attrKey));
                } else {
                    tempItem.setCustomAttribute(attrKey, anItem.getDouble(attrKey));
                }
                fileInv.addItemToInv(tempItem);
            }

        }
        return fileInv;
    }


}
