import java.util.LinkedList;
public class Player {
    public String playerName;
    public static int playerNum = 0;

    private int currentScore; //Total score of hand cards.
    private int turnNum;
    private int token;
    private int status; //Whether a player has passed. 0 for initial value; 1 for has passed.
    private int aceNum = 0;
    private LinkedList<SingleCard> openCards = new LinkedList<SingleCard>();
    private LinkedList<SingleCard> closeCards = new LinkedList<SingleCard>();

    Player(){
        status = 0;
        turnNum = 1;
        playerNum++;
        playerName = "player " + Integer.toString(playerNum);
    }
    Player(String name){
        status = 0;
        turnNum = 1;
        playerNum++;
        playerName = name;
    }
    Player(int t){
        status = 0;
        turnNum = 1;
        playerNum++;
        playerName = "player " + Integer.toString(playerNum);
        token = t;
    }
    public void initialHand(){
        SingleCard firstCloseCard = Dealer.deal();
        closeCards.add(firstCloseCard);
        System.out.printf("%s takes * (hidden) ", playerName);
        computeScoreWhenAddingNewCard(firstCloseCard);

        SingleCard firstOpenCard = Dealer.deal();
        openCards.add(firstOpenCard);
        System.out.printf("%s takes %s ", playerName, firstOpenCard.getSuit());
        computeScoreWhenAddingNewCard(firstOpenCard);

    }
    public void stand(){
        status = 1;
        System.out.println(playerName + " passes.");
    }
    public void hit(){
        if(status == 0){
            SingleCard newCard = Dealer.deal();
            openCards.add(newCard);
            computeScoreWhenAddingNewCard(newCard);

            // Judge whether Bust.
            if (currentScore > 21){
                System.out.println(playerName + " Bust!");
                status = 1;
            }
        }
    }
    public void doubleDown(){

    }
    public void split(){

    }
    public void surrender(){

    }
    private void computeScoreWhenAddingNewCard(SingleCard aCard){
        if (aCard.getRank() < 10 && aCard.getRank() != 0){
            currentScore = currentScore + aCard.getRank() + 1;
        }
        else if (aCard.getRank() >= 10){
            currentScore = currentScore + 10;
        }
        else {
            aceNum++;
            if (currentScore > 10){
                currentScore = currentScore + 1;
            }
        }

    }
}
