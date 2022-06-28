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
import org.json.JSONObject;


import java.io.*;

// Represents a writer that writes a JSON representation of workroom to file
public class JsonWriter {
    private PrintWriter writer;
    private String fileName;

    // EFFECTS: constructs writer to write to theFileName file
    public JsonWriter(String theFileName, String theFilePath) {
        this.fileName = theFilePath + theFileName + ".json";
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(fileName);
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of inventory to file
    public void write(Inventory theInventory) {
        JSONObject json = new JSONObject();
        json.put("Name", theInventory.getInvName());
        json.put("Items", theInventory.toJson());
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
