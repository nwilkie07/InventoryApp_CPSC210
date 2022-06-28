package model;

import org.json.JSONObject;

import java.util.HashMap;

public class Item {
    private HashMap<String, String> stringAttributes;
    private HashMap<String, Double> numberAttributes;
    private int idNumber;

    /*
    EFFECTS: sets the name of the item to theName.
              sets the idNum to theId.
              initializes empty stringAttributes Hashmap.
              initializes empty numberAttributes Hashmap.
    */
    public Item(String theName, Integer theId) {
        stringAttributes = new HashMap<>();
        numberAttributes = new HashMap<>();
        idNumber = theId;
        stringAttributes.put("Name", theName);
        System.out.println(stringAttributes);
    }

    /*
    EFFECTS:  initializes empty stringAttributes Hashmap.
              initializes empty numberAttributes Hashmap.
    */
    public Item() {
        stringAttributes = new HashMap<>();
        numberAttributes = new HashMap<>();
    }

    public Integer getIdNum() {
        return this.idNumber;
    }

    public void setIdNum(Integer idNum) {
        this.idNumber = idNum;
    }

    public String getName() {
        return this.stringAttributes.get("Name");
    }

    public void setName(String name) {
        this.stringAttributes.replace("Name", name);
    }

    //MODIFIES: numberAttributes
    //EFFECTS: Attempts to add a key value pair (String, double) to the numberAttributes hashMap
    public void setCustomAttribute(String name, double theAttribute) {
        this.numberAttributes.put(name, theAttribute);
        EventLog.getInstance().logEvent(new Event("Added Item attribute " + name + ": " + theAttribute
                + " to the item " + stringAttributes.get("Name")));
    }

    //MODIFIES: stringAttributes
    //EFFECTS: Attempts to add a key value pair (String, String) to the stringAttributes hashMap
    public void setCustomAttribute(String name, String theAttribute) {
        this.stringAttributes.put(name, theAttribute);
        EventLog.getInstance().logEvent(new Event("Added Item attribute " + name + ": " + theAttribute
                + " to the item " + stringAttributes.get("Name")));
    }

    //EFFECTS: Attempts to return a double value from the numberAttributes hashMap based on an attrName key.
    public double getCustomNumberAttribute(String attrName) {
        return (numberAttributes.get(attrName));
    }

    //EFFECTS: Attempts to return a String value from the numberAttributes hashMap based on an attrName key.
    public String getCustomStringAttribute(String attrName) {
        return stringAttributes.get(attrName);
    }

    //MODIFIES: numberAttributes
    //EFFECTS: Attempts to return a double value from the numberAttributes hashMap based on an attrName key.
    public void replaceCustomNumberAttribute(String attrName, double newValue) {
        numberAttributes.replace(attrName, newValue);
        EventLog.getInstance().logEvent(new Event("Replaced Item attribute " + attrName + " with new value: "
                + newValue + " for item " + stringAttributes.get("Name")));
    }

    //MODIFIES: stringAttributes
    //EFFECTS: Attempts to return a string value from the stringAttributes hashMap based on an attrName key.
    public void replaceCustomStringAttribute(String attrName, String newValue) {
        stringAttributes.replace(attrName, newValue);
        EventLog.getInstance().logEvent(new Event("Replaced Item attribute " + attrName + " with new value: "
                + newValue + " for item " + stringAttributes.get("Name")));
    }

    //EFFECTS: returns the items numberAttributes HashMap with String key and Double values
    public HashMap<String, Double> getAllNumberAttributes() {
        return numberAttributes;
    }

    //EFFECTS: returns the items stringAttributes HashMap with String key and String values
    public HashMap<String, String> getAllStringAttributes() {
        return stringAttributes;
    }

    //EFFECTS: returns true if the provided attribute key is in the numberAttributes hashmap
    //         returns false otherwise
    public boolean isNumberAttribute(String theAttrKey) {
        return (numberAttributes.containsKey(theAttrKey));
    }

    //EFFECTS: returns true if the provided attribute key is in the stringAttributes hashmap
    //         returns false otherwise
    public boolean isStringAttribute(String theAttrKey) {
        return (stringAttributes.containsKey(theAttrKey));
    }

    //EFFECTS: returns a JSONObject containing all key value pairs
    //         from the stringAttributes and numberAttributes Hashmaps
    public JSONObject toJson() {
        JSONObject tempJson = new JSONObject();
        for (String keyStr : stringAttributes.keySet()) {
            tempJson.put(keyStr, stringAttributes.get(keyStr));
        }
        for (String keyStr : numberAttributes.keySet()) {
            tempJson.put(keyStr, numberAttributes.get(keyStr));
        }
        return tempJson;
    }


}
