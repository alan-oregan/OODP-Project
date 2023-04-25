# OODP Design Patterns Assignment

__Author:__ _Alan O'Regan_
__Student Number:__ _B00133474_

#### Application Overview:
This is a point of sale computer program for a coffee shop.
The program displays the menu from an inventory file,allows the user to process orders and then save them to a transaction file.

## Functional Requirements
---


## User Requirements and Interface
---
![Screenshot of GUI]()


### UML Class Diagram
---
![[images/OODP-Project-UML.drawio.png]]

## Design Patterns
---

### Behavioural

#### Command


### Structural

#### Decorator


### Creational

#### Singletons

As I was implementing the singleton pattern I thought why not make it a static class, and so I looked into the idea and found this: 

> A Singletons can implement interfaces, inherit from other classes and allow inheritance. While a static class cannot inherit their instance members. So Singletons is more flexible than static classes and can maintain state.

_source https://net-informations.com/faq/netfaq/singlestatic.htm

#### Transaction

