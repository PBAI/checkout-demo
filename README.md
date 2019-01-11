# checkout-demo
A point of sale library demonstration

This project is a library in which the intended functionality is accessed
through the ApplicationGateway class. The public methods found there 
will allow another application to add SalesUnits (items) to the inventory.
Also items can be added to a Cart object that represents a shopping cart.
Additionally items may be marked down, or put on special, and the pre
tax total is returned when getTotal() is called. 
