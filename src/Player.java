import java.util.LinkedList;

public class Player {
    public String playerName;
    public static int playerNum = 0;
    public int status; //Whether a player has passed. 0 for initial value; 1 for has passed.
    public static int endFlag = 0;


    protected int currentScore; //Total score of hand cards.
    private static int turnNum;
    private int token;
    private int aceNum = 0;
    protected LinkedList<SingleCard> openCards = new LinkedList<SingleCard>();
    protected LinkedList<SingleCard> closeCards = new LinkedList<SingleCard>();

    Player() {
        initialization();
        playerName = "player " + Integer.toString(playerNum);
        System.out.println();
    }

    Player(String name) {
        initialization();
        playerName = name;
    }

    Player(int t) {
        initialization();
        playerName = "player " + Integer.toString(playerNum);
        token = t;
    }

    public void initialHand() {
        SingleCard firstCloseCard = Dealer.deal();
        closeCards.add(firstCloseCard);
        System.out.printf("%s takes * (hidden) \n", playerName);
        computeScoreWhenAddingNewCard(firstCloseCard);

        SingleCard firstOpenCard = Dealer.deal();
        openCards.add(firstOpenCard);
        System.out.printf("%s takes %s %d \n", playerName, firstOpenCard.getSuit(), firstOpenCard.getRank() + 1);
        computeScoreWhenAddingNewCard(firstOpenCard);

    }

    public void stand() {
        status = 1;
        System.out.println(playerName + " passes.");
        turnNum++;
    }

    public void hit() {
        if (status == 0) {
            SingleCard newCard = Dealer.deal();
            openCards.add(newCard);
            computeScoreWhenAddingNewCard(newCard);

            // Judge whether Bust.
            if (currentScore > 21) {
                System.out.println(playerName + " Bust!");
                status = 1;
            }

            turnNum++;
        } else {
            stand();
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

    private void computeScoreWhenAddingNewCard(SingleCard aCard) {
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
    private void initialization() {
        status = 0;
        turnNum = 1;
        playerNum++;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }
}
