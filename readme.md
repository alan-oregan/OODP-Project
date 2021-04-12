#Cafe Point of Sale System

This is a point of sale computer program for a Coffee shop
The Program displays the menu and allows the user to process an order.

##The System Menu

The **menu items** are read from a **csv inventory file** in the format: ***[menu item], [price]***
Each menu item name and price is added to an ArrayList of MenuItem Objects.


The user is **allowed** to enter as **many items** as they want.
The user can delete an item from there order
The transaction information is processed in the program and a receipt is generated
The program keeps a record of transactions for the user by saving them to a transaction file
 
will then process the customers transaction using the following steps:1.Pick coffee item from the system menu2.Select CASH or CARD transactiona.If cash transaction then the amount tendered will be entered and a receipt will be generated on screen.b.If card transaction then the card type (Visa/Master) will be selected and a receipt will be generated on screen.

 when the coffee shop owner exits the system,all the days transactions must be written to a transaction file

 Cash Transaction format[Date and time stamp], [Item Purchased], [Price], [Amount tendered], [Change given]Card Transaction format[Date and time stamp], [Item Purchased], [Price], [Cardtype]



 // https://www.youtube.com/watch?v=yO_ctH4mEk4 
// https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java/16956747 
// https://www.w3schools.com/java/java_arraylist.asp 
// https://www.youtube.com/watch?v=lcWV7hLYByk 
// https://stackoverflow.com/questions/2979383/java-clear-the-console 
// https://www.youtube.com/watch?v=Vs2ZR7-LJO0 
// https://www.w3schools.com/java/java_files_read.asp 
// https://www.w3schools.com/java/java_files_create.asp 
// https://stackoverflow.com/questions/2255500/can-i-multiply-strings-in-java-to-repeat-sequences 
// https://stackoverflow.com/questions/16956720/how-to-create-an-2d-arraylist-in-java/16956747 
// https://www.w3schools.com/java/java_arraylist.asp 
// https://www.tutorialspoint.com/convert-from-string-to-double-in-java#:~:text=To%20convert%20String%20to%20double%2C%20use%20the%20valueOf()%20method. 
// https://www.geeksforgeeks.org/format-specifiers-in-java/ 
// https://stackoverflow.com/questions/6264576/number-of-decimal-digits-in-a-double 
