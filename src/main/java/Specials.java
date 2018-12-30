public enum Specials {

    BUY_ONE_GET_ONE_FREE("4");

    private String itemLimit;

    private Specials(String itemLimit){
        this.itemLimit = itemLimit;
    }

    public String getItemLimit(){
        return this.itemLimit;
    }

}
