public class Dealer extends Player {
    public Dealer(String name) {
        super(name);
    }

    public static SingleCard deal() {
        SingleCard card = new SingleCard();
        return card;
    }
    public void botDealer(int operationFlag, int botLevel) {
        if (operationFlag == 1) {
            if (botLevel == 1){
                //High-low strategy
                if (currentScore < 11) {
                    hit();
                }
                else {
                    stand();
                    Game.passedPlayerNum++;
                }
                //else if
            }
        }

    }
}
