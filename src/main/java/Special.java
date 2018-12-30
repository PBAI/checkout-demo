public enum Special {

    BUY_ONE_GET_ONE_FREE("2", "4", Percentage.FIFTY_PERCENT.getPercentageValueString());

    private String itemLimit;
    private String itemMinimum;
    private String percentOffRegPrice;

    private Special(String itemMinimum, String itemLimit, String percentOffRegPrice){
        this.itemMinimum = itemMinimum;
        this.itemLimit = itemLimit;
        this.percentOffRegPrice = percentOffRegPrice;
    }

    public String getItemLimit(){
        return this.itemLimit;
    }

    public String getItemMinimum(){
        return this.itemMinimum;
    }

    public String getPercentOffRegPrice(){
        return this.percentOffRegPrice;
    }

}
