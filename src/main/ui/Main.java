package ui;

import model.Event;
import model.EventLog;

import java.util.Iterator;

//Constructs an InventoryWindow object.
public class Main {
    public static void main(String[] args) {
        new InventoryWindow();
        Iterator<Event> theIterator = EventLog.getInstance().iterator();
        while (theIterator.hasNext()) {
            System.out.println(theIterator.next());
        }
    }
}
