package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {
    Item chair;
    Item emptyItem;

    @BeforeEach
    public void setupItem() {
        chair = new Item("Desk Chair", 56);
        chair.setCustomAttribute("Color","Red");
        chair.setCustomAttribute("Weight", 50.1);
        emptyItem = new Item();
    }

    @Test
    public void checkEmptyItemTest() {
        assertEquals(0,emptyItem.getIdNum());
        assertNull(emptyItem.getName());
        assertEquals(0,emptyItem.getAllNumberAttributes().size());
        assertEquals(0,emptyItem.getAllStringAttributes().size());
    }


    @Test
    public void getterTest(){
        assertEquals("Desk Chair", chair.getName());
        assertEquals(56, chair.getIdNum());
        assertEquals(50.1,chair.getCustomNumberAttribute("Weight"));
        assertEquals("Red",chair.getCustomStringAttribute("Color"));
    }

    @Test
    public void setGetTest() {
        chair.setIdNum(5);
        assertEquals(5,chair.getIdNum());
        chair.setName("Oak");
        assertEquals("Oak",chair.getName());

    }

    @Test
    public void replaceGetTest() {
        chair.replaceCustomNumberAttribute("Weight",55);
        assertEquals(55,chair.getCustomNumberAttribute("Weight"));
        chair.replaceCustomStringAttribute("Color","Blue");
        assertEquals("Blue",chair.getCustomStringAttribute("Color"));
    }

    @Test
    public void getAllAttributesTest() {
        chair.setCustomAttribute("Type","Furniture");
        chair.setCustomAttribute("Length", 12);
        HashMap<String, Double> numTestMap = chair.getAllNumberAttributes();
        HashMap<String, String> stringTestMap = chair.getAllStringAttributes();

        assertEquals("Furniture",stringTestMap.get("Type"));
        assertEquals("Red",stringTestMap.get("Color"));
        assertEquals(12,numTestMap.get("Length"));
        assertEquals(50.1,numTestMap.get("Weight"));
    }

    @Test
    public void isNumberStringTest() {
        assertTrue(chair.isNumberAttribute("Weight"));
        assertFalse(chair.isNumberAttribute("Test"));
        assertTrue(chair.isStringAttribute("Color"));
        assertFalse(chair.isStringAttribute("Test"));
    }

    @Test
    public void toJsonTest() {
        Item simpleItem = new Item("Chair",10);
        JSONObject simpleExpected = new JSONObject();
        simpleExpected.put("Name","Chair");
        JSONObject simpleItemJson = simpleItem.toJson();
        assertEquals(simpleExpected.toString(),simpleItemJson.toString());

        Item complexItem = new Item("Desk",12);
        complexItem.setCustomAttribute("Color","Yellow");
        complexItem.setCustomAttribute("Weight", 20.1);
        JSONObject complexItemJson = complexItem.toJson();
        JSONObject complexExpected = new JSONObject();
        complexExpected.put("Name","Desk");
        complexExpected.put("Color","Yellow");
        complexExpected.put("Weight",20.1);
        assertEquals(complexExpected.toString(),complexItemJson.toString());
    }

    @Test
    public void toEmptyJsonTest() {
        JSONObject empty = emptyItem.toJson();
        JSONObject nothing = new JSONObject();
        assertEquals(nothing.toString(),empty.toString());
    }
}
