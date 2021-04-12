# FOP2 Mini Project – 2021

## Program Description

*Cafe Point of Sale System*
Created by: *Alan O'Regan*
Student Number: *B00133474*

This is a point of sale computer program for a coffee shop.
The program displays the menu from an inventory file, allows the user to process orders and save them to a transaction file.

***

## The System Menu

The **menu items** are read from an **inventory csv file** in the format: ***[item name], [price]***
Each menu item name and price is added to an **ArrayList of MenuItem objects**.

### Item Number options

The user is **allowed** to enter as **many items** as they want from the list of menu items.

### The Remove Option

The user **can delete** items from their order by selecting the this option and entering the **item order number** from the current order displayed.

### The Payment Option

The user **complete the transaction** by selecting this option and entering the required information.

The program will then *process* the customers transaction using the following *steps*:

1. The total price is calculated from ordered item prices within **MenuItem ArrayList**.
2. The user selects *CASH or CARD* transaction.
   - If **cash transaction** then the **amount tendered** will be entered and **change** calculated from the price difference if any.
   - If **card transaction** then the **card type** (Visa/Master) will be selected.

3. The program gets the transaction **timestamp** in the format *(dd/MM/yyyy HH:mm:ss)* and saves the transaction in an **ArrayList of TransactionItems**.

An appropriate **receipt** will then be generated on screen.

### The Exit Option

When the coffee shop owner exits the system, all the days transactions are written to a **transaction.csv** file from the **ArrayList of TransactionItems**.

***

## Transaction Formats

### Cash Transaction format

#### [Date and time stamp], [Item/s Purchased], [Price], [Amount tendered], [Change given]

### Card Transaction format

#### [Date and time stamp], [Item/s Purchased], [Price], [Card Type]

***

## References Used

- <https://www.youtube.com/watch?v=yO_ctH4mEk4>
- <https://www.w3schools.com/java/java_arraylist.asp>
- <https://www.youtube.com/watch?v=lcWV7hLYByk>
- <https://stackoverflow.com/questions/2979383/java-clear-the-console>
- <https://www.youtube.com/watch?v=Vs2ZR7-LJO0>
- <https://www.w3schools.com/java/java_files_read.asp>
- <https://www.w3schools.com/java/java_files_create.asp>
- <https://stackoverflow.com/questions/2255500/can-i-multiply-strings-in-java-to-repeat-sequences>
- <https://www.w3schools.com/java/java_arraylist.asp>
- <https://www.tutorialspoint.com/convert-from-string-to-double-in-java#:~:text=To%20convert%20String%20to%20double%2C%20use%20the%20valueOf()%20method>.
- <https://www.geeksforgeeks.org/format-specifiers-in-java/>
- <https://stackoverflow.com/questions/6264576/number-of-decimal-digits-in-a-double>
