class Dealer extends Player {
    Dealer(String name) {
        super(name);
    }

    static SingleCard deal() {
        return new SingleCard();
    }
    SingleCard botDealer(int operationFlag, int botLevel) {
        if (operationFlag == 1) {
            if (botLevel == 1){
                //High-low strategy
                if (currentScore < 17) {
                    return hit();
                }
                else {
                    stand();
                    return deal();
                }
                //else if
            }
        }
        return deal();
    }
}
