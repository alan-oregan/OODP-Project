# FOP2 Mini Project – 2021

***

## Program Description

*Cafe Point of Sale System*
Created by: *Alan O'Regan*
Student Number: *B00133474*

This is a point of sale computer program for a coffee shop.
The program displays the menu from an inventory file, allows the user to process orders and save them to a transaction file.

## The System Menu

***

The **menu items** are read from an **inventory csv file**.
Each menu item name and price is added to an **ArrayList of MenuItem objects**.

### Item Number options

The user is **allowed** to enter as **many items** as they want from the list of menu items.

### The Remove Option

The user **can delete** items from their order by selecting this option and entering the **item order number** from the **current order** displayed.

### The Payment Option

The user **complete the transaction** by selecting this option and entering the required information.

The program will then *process* the customers transaction using the following *steps*:

1. The total price is calculated from ordered item prices within **MenuItem ArrayList**.
2. The user selects *Cash/Card* transaction.
   - If **cash transaction** then the **amount tendered** will be entered and **change** calculated from the price difference if any.
   - If **card transaction** then the users **card type** *Visa/Master* will be selected.

3. The program gets the transaction **timestamp** in the format *(dd/MM/yyyy HH:mm:ss)* and saves the transaction in an **ArrayList of TransactionItems**.

An appropriate **receipt** will then be generated on screen.
The user can then **press enter to continue** to enter another order and the previous order details are **cleared from the screen**.

### The Exit Option

When the coffee shop owner exits the system, all the days transactions are written to a **transaction.csv** file from the **ArrayList of TransactionItems**.

## CSV File Formats

***

### Inventory Format

- ***[item name], [price]***

### Transactions Format

- **Cash Transaction format**
  - ***[Date and time stamp], [Item/s Purchased]\*, [Total Price], [Amount tendered], [Change given]***
- **Card Transaction format**
  - ***[Date and time stamp], [Item/s Purchased]\*, [Total Price], [Card Type]***

*\*The items purchased contain the price separated by a colon **:** and each each ordered item separated by a forward slash **/***

## References used to help make the project

***

- [Java Clear Console Stack Overflow Answer](https://stackoverflow.com/a/38365871)
- [Repeating sequence Stack Overflow Answer](https://stackoverflow.com/a/49656610)
- [Number of decimal digits in a double Stack Overflow Answer](https://stackoverflow.com/a/6264613)
- [Java Text File I/O Introduction](https://www.youtube.com/watch?v=yO_ctH4mEk4)
- [Text-based Menu Driven Program in Java [Part 2/3]](https://www.youtube.com/watch?v=lcWV7hLYByk)
- [Java Keyboard class for User Input Validation](https://www.youtube.com/watch?v=Vs2ZR7-LJO0)
- [Java ArrayList W3Schools](https://www.w3schools.com/java/java_arraylist.asp)
- [Java read files W3Schools](https://www.w3schools.com/java/java_files_read.asp)
- [Java write files W3Schools](https://www.w3schools.com/java/java_files_create.asp)
- [Format specifiers GeeksForGeeks](https://www.geeksforgeeks.org/format-specifiers-in-java/)

## UML Class Diagram

***

![Mini Project UML Class Diagram](Mini%20Project%20UML%20Class%20Diagram.png)
