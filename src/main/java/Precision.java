public enum Precision {

    TWO_DECIMAL_PLACES(2);

    private int integerValue;

    private Precision(int integerValue){
        this.integerValue = integerValue;
    }

    public int getIntegerValue(){
        return this.integerValue;
    }

}
