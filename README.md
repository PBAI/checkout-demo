# checkout-demo
A point of sale library demonstration

This project is a library in which the intended functionality is accessed
through the ApplicationGateway class. The public methods found there 
will allow another application to add SalesUnits (items) to the inventory.
Also items can be added to a Cart object that represents a shopping cart.
Additionally items may be marked down, or put on special, and the pre
tax total is returned when getTotal() is called.

PUBLIC METHODS:
    
    public void addItemNamesToInventory(String...itemNames);
This adds item names to be contained within 
the fictional inventory. Items cannot be 
added to the shopping cart without 
being found within the inventory.
    
    
    public void markdownItemByPercentage(String itemName, Percentage markdownPercentage);
This allows one to set items for markdown.
The Percentage enum supports ten, 
twenty-five, and fifty percent 
markdowns.

    public void putItemOnSpecial(String itemName, Special specialForItem);
This allows a user to put items on special. The current specials 
supported by the Special enum are buy one get one free and
three for five. 

    public void addItemToCart(SalesUnit itemToAdd);
This is to add an item to the shopping cart.
Simply pass the item instance.

    public void scanItemToTotal(SalesUnit itemToScan);
This allows for scanning a single item to the overall
shopping pre-tax total. Multiple items scanned at 
once is not supported.

    public void removeOneItemFromTotal(SalesUnit item);
This allows for removal of a single item from the total.
The running total will reflect the item removal.

    public BigDecimal getTotal();
Returns the running total.
    
    public Map<String, List<SalesUnit>> getItemsInCart();
Returns the items in the cart.
    
    public List<String> getInventory();
Returns the inventory list.
    
    public Map<String, String> getMarkdownMap();
Returns the items currently listed as marked down.

    public Map<String, Special> getSpecialsMap();
Returns the items currently on special.
    

