public enum Special {

    BUY_ONE_GET_ONE_FREE(2, 4, Percentage.FIFTY_PERCENT);

    private int itemLimit;
    private int itemMinimum;
    private Percentage percentOffRegPrice;

    private Special(int itemMinimum, int itemLimit, Percentage percentOffRegPrice){
        this.itemMinimum = itemMinimum;
        this.itemLimit = itemLimit;
        this.percentOffRegPrice = percentOffRegPrice;
    }

    public int getItemLimit(){
        return this.itemLimit;
    }

    public int getItemMinimum(){
        return this.itemMinimum;
    }

    public Percentage getPercentOffRegPrice(){
        return this.percentOffRegPrice;
    }

}
