public enum Percentage {

    TEN_PERCENT(".10"),
    TWENTY_FIVE_PERCENT(".25"),
    FIFTY_PERCENT(".50");

    private String percentageValueString;

    Percentage(String percentValueString){
        this.percentageValueString= percentValueString;
    }

    public String getPercentageValueString(){
        return this.percentageValueString;
    }
}
