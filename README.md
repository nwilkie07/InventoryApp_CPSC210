# Inventory Manager

## Built By

___
Nicholas Wilkie. A design engineer, musician, swimmer, 
and aspiring software developer.

https://www.linkedin.com/in/nicholasrwilkie/

## Proposal

___

### Features/Design:

This is a pure java desktop application. It will allow a user to add items they own to their inventory. The inventory
will be searchable for various characteristics defined when an item is added. Image processing and other techniques will
be applied to reduce user input when adding items to the inventory. The application will allow control over the status
of items (for example: on loan, worn out, scrapped, etc.). The application should also be able to transfer items between
users inventories. In the UI, items will be displayed and the view can be sorted. Additional features will be added or
removed from this readme as the application is developed.

Feature List (subject to change):

- Adding items to Inventory
- Searching items in Inventory
- Color and other metadata extracted from pictures
- Items have editable status
- User sign in
- Remove items from inventory
- Scrap items
- Categorize inventory items by bin or type

### Target Audience:

Inventory software is expensive, complicated, and often hard to use. This software is designed for individuals or small
business's that require a smarter and more efficient inventory system. It should operate as an extension of a person's
business or hobby, with intuitive and simple user interaction.

### Why build this?

I've been building computers, assembling speakers, building circuits, and repairing electronics for many years; I have
amassed a significant collection of tools, cables, circuit components, and other miscellaneous items. Currently, I have
a simple organization system setup, but I still waste time searching for items. I also struggle to keep track of what
components have been consumed by projects or lent out to individuals. There is software in existence for managing an
inventory of items, but they can be costly or lack the exact set of features I require.

This project also provides an excellent opportunity to learn java, due the number of different features I could add to
this software. There are opportunities to use java's excellent handling of images to automatically add information on
items, to build a neural net for identifying items, use ocr for converting item lists to text, etc.

## Challenges

___

## User Stories
- As a user, I want to be able to add an item to my inventory.
- As a user, I want to be able to delete an item from my inventory.
- As a user, I want to be able to select an item and change its properties.
- As a user, I want to be able to view a list of the items in my inventory with associated properties.
- As a user, I want to be able to save an inventory to a file.
- As a user, I want to be able to load an inventory from a file.
- As a user, I want to be able to add custom attributes to items.

#Phase 4: Task 2

Fri Nov 26 16:00:51 PST 2021
Added Item attribute image: ./data/1630173696230.jpg to the item Test
Fri Nov 26 16:00:51 PST 2021
Item of ID: 121 and Name Test added to the inventory
Fri Nov 26 16:01:06 PST 2021
Added Item attribute image: ./data/1630173696230.jpg to the item Chair
Fri Nov 26 16:01:06 PST 2021
Item of ID: 12 and Name Chair added to the inventory
Fri Nov 26 16:01:14 PST 2021
Item: Chair removed from the inventory

#Phase 4: Task 3
The overall application functions well but there are currently many different classes all passing JPanel's
back to the main class which handles the GUI. A lot of the functionality for setting up these GUI components
could be extracted to an abstract class to make the handling of all window elements easier. Currently, 
Inventory is managed by a Utility Class to maintain a single instance. It would be better to properly add an
implementation of the singleton design for this Class. The InventoryWindow which manages the main GUI is passed
to almost every Class so it could use a Singleton or Utility implementation as well for easier use.

Desired Changes
- Abstract Windows class for setting up the multiple GUI interfaces
- Singleton Implementation of Inventory instead of a Utility Class
- Singleton Implementation of Inventory Window or Utility Class
- Move methods out of InventoryWindow to a separate class (file IO and other operations)
