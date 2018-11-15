import java.util.*;

public class Player {
    public String playerName;
    public static int playerNum = 0;
    public static int endFlag = 0;

    protected int status; //Whether a player has passed. 0 for initial value; 1 for has passed.
    protected int currentScore; //Total score of hand cards.
    private static int turnNum;
    private int token;
    private int aceNum = 0;
    protected LinkedList<SingleCard> openCards = new LinkedList<SingleCard>();
    protected LinkedList<SingleCard> closeCards = new LinkedList<SingleCard>();
    private int bustFlag = 0; //0 for not bust, 1 for bust.
    private Queue<String> outputQueue = new LinkedList<String>();

    Player() {
        this("", 0);
        playerName = "player " + Integer.toString(playerNum);
        System.out.println();
    }

    Player(String name) {
        this(name,0);
//        playerName = name;
    }

    Player(String name, int t) {
        status = 0;
        turnNum = 1;
        playerNum++;
        token = t;
        if (name.equals(""))
            playerName = "player " + Integer.toString(playerNum);
        else
            playerName = name;

        Utils.printToQueue(outputQueue, "*********************");
        Utils.printToQueue(outputQueue, playerName + "'s game interface: ");
        Utils.printToQueue(outputQueue, "*********************");

        Utils.printToQueue(outputQueue,playerName + ", welcome!");
        Utils.printToQueue(Game.getGameQueue(), playerName + ", welcome!");
    }



    public void stand() {
        status = 1;
        System.out.println(playerName + " passes.");
        Utils.printToQueue(outputQueue,playerName + " passes.");
        Utils.printToQueue(Game.getGameQueue(), playerName + " passes.");
        turnNum++;
    }

    public SingleCard hit() {
        if (status == 0) {
            SingleCard newCard = Dealer.deal();
            openCards.add(newCard);
            computeScoreWhenAddingNewCard(newCard);

            Utils.printSuitRank(newCard,this, 0);

            // Judge whether Bust.
            judgeBust();
            return newCard;
        } else {
            stand();
            return Dealer.deal();
        }

    }

    public void doubleDown() {

    }

    public void split() {

    }

    public void surrender() {

    }

    public int getScore() {
        if (endFlag == 1) {
            if (currentScore > 11) {
                return currentScore;
            } else if (aceNum > 0) {
                return currentScore + 10;
            }
        }

        return 0;
    }

    public void computeScoreWhenAddingNewCard(SingleCard aCard) {
        if (aCard.getRank() < 10 && aCard.getRank() != 0) {
            currentScore = currentScore + aCard.getRank() + 1;
        } else if (aCard.getRank() >= 10) {
            currentScore = currentScore + 10;
        } else {
            aceNum++;
            if (currentScore > 10) {
                currentScore = currentScore + 1;
            }
        }

    }

    public void judgeBust() {
        if (currentScore > 21) {
            System.out.println(playerName + " Bust!");
            Utils.printToQueue(outputQueue,playerName + " Bust!");
            Utils.printToQueue(Game.getGameQueue(), playerName + " Bust!");
            bustFlag = 1;
            status = 1;
        }
    }

//    private void printOpenDraw(SingleCard firstOpenCard) {
//        System.out.printf("%s takes %s %d \n", playerName, firstOpenCard.getSuit(), firstOpenCard.getRank() + 1);
//    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBustFlag() {
        return bustFlag;
    }

    public Queue<String> getOutputQueue() {
        return outputQueue;
    }

    public LinkedList<SingleCard> getOpenCards() {
        return openCards;
    }

    public LinkedList<SingleCard> getCloseCards() {
        return closeCards;
    }
}
