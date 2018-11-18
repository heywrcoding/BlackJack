import java.util.*;

public class Player {
    public String playerName;
    public static int playerNum = 0;
    public static int endFlag = 0;

    protected int status; //Whether a player has passed. 0 for initial value; 1 for has passed.
    protected int currentScore; //Total score of hand cards.
    private static int turnNum;
    int wager;
    private int aceNum = 0;
    protected LinkedList<SingleCard> openCards = new LinkedList<SingleCard>();
    protected LinkedList<SingleCard> closeCards = new LinkedList<SingleCard>();
    private int bustFlag = 0; //0 for not bust, 1 for bust.
    private Queue<String> outputQueue = new LinkedList<String>();

    Player() {
        this("", 0);
    }

    Player(String name) {
        this(name,0);
    }

    Player(int t) {
        this("", t);
    }

    Player(String name, int t) {
        status = 0;
        turnNum = 1;
        playerNum++;
        wager = t;
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

            Utils.printSuitRank(newCard,this, Utils.openFlag);

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

    public int makeBet() {
        Utils.printToQueue(outputQueue, playerName + ", now you have " + wager + " wager. Please make a bet: (Unit: $1)");
        Utils.printToQueue(Game.getGameQueue(), playerName + ", now you have " + wager +" wager. Please make a bet: (Unit: $1)");

        Utils.printFromQueue(outputQueue);
        Scanner scanner = new Scanner(System.in);
        int betAccount = 0;
        while (true) {
            if (!scanner.hasNextInt()){   //filter any character other than integers
                System.out.println("You have typed in invalid character, please typed again: ");
                String test = scanner.next();
                continue;
            }
            betAccount = scanner.nextInt();
            if (betAccount > wager){      //filter all bet more than current wager
                System.out.println("You have typed in invalid character, please typed again: ");
                continue;
            }
            break;
        }
//        int betAccount = scanner.nextInt();
        wager -= betAccount;
        Utils.printToQueue(outputQueue, betAccount + "");
        Utils.printToQueue(Game.getGameQueue(), betAccount + "");
        return betAccount;
    }

    public int isContinue() {
        Utils.printToQueue(outputQueue, playerName + ", now you have " + wager + " wager. Do you want another game? (0 for end, 1 for continue)");
        Utils.printToQueue(Game.getGameQueue(), playerName + ", now you have " + wager + " wager. Do you want another game? (0 for end, 1 for continue)");

        Utils.printFromQueue(outputQueue);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNext("[0-1]")) {
            System.out.println("You have typed in invalid character, please typed again: ");
            scanner.next();
        }
        int i = scanner.nextInt();
        Utils.printToQueue(outputQueue, i + "");
        Utils.printToQueue(Game.getGameQueue(), i + "");
        return i;
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

    public int getWager() {
        return wager;
    }
}
