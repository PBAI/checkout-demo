public enum Special {

    BUY_ONE_GET_ONE_FREE(4),
    THREE_FOR_FIVE(6);

    private int itemLimit;

    private Special(int itemLimit){
        this.itemLimit = itemLimit;
    }

    public int getItemLimit(){
        return this.itemLimit;
    }

}
