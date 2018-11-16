import java.util.*;

public class Game {
//    private int endFlag = 0; //0 for initial value, 1 for game end to check winner.
    private int playerNumInGame = 0;
    private int currentTurn;
    private static int passedPlayerNum = 0;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Dealer dealer;
    private Scanner scanner = new Scanner(System.in);
    private int yesOrNoFlag = 1;
    private int[] scoreBoard;
    private int botDealerFlag; //Flag bit to open/close bot Dealer. 0 for close, 1 for open.
    private int botDealerOpFlag; // Flag bit to identify bot Dealer's Operation. 0 for no operation, 1 for hitOrStand.
    private int botLevel = 1; // Difficulty level.
    private int winnerIndex = -1;
    private int winnerScore;
    private static Queue<String> gameQueue = new LinkedList<String>();
    private int betTotalAccount = 0;

    Game(int pNum, int botNum, int wager) {

        Utils.printToQueue(gameQueue, "*********************");
        Utils.printToQueue(gameQueue, "Game Log:");
        Utils.printToQueue(gameQueue, "*********************");

        int deckNum = 1;
        Deck deck = new Deck(deckNum);

        if (botNum > pNum) {
            Utils.printToQueue(gameQueue, "Error! The number of Bots must be less than of Players!");
            System.out.println("Error! The number of Bots must be less than of Players!");
        } else {
//            player = new Player[pNum - 1];
            for (int i = 0; i < pNum - 1; i++)
//                Player player = new Player(wager);
                players.add(new Player(wager));
            dealer = new Dealer("Dealer", wager);

            playerNumInGame = Player.playerNum;
            currentTurn = 1;
            scoreBoard = new int[pNum];
            if (botNum == 1) {
                Utils.printToQueue(gameQueue, "Dealer will be bot.");
                System.out.println("Dealer will be bot.");
                botDealerFlag = 1;
            }

        }

    }

    void play() {
        while (true) {
            start();     //a game

            //no players, game over
            if (playerNumInGame <= 1) {
                break;
            }

            //stop players with no wager after a game
            for (int i = 0; i < playerNumInGame - 1; i++) {
                if (players.get(i).getWager() <= 0) {
                    players.remove(i);
                    playerNumInGame--;
                }
            }
            if (dealer.getWager() <= 0) {
                Utils.printToQueue(gameQueue, "Dealer has no wager, game over, players win.");
                break;
            }

            //ask whether players want any more game
            for (int i = 0; i < playerNumInGame - 1; i++) {
                if (players.get(i).isContinue() == Utils.endFlag) {
                    players.remove(i);
                    playerNumInGame--;
                }
            }

        }
    }

    void start() {

        for (int i = 0; i < playerNumInGame - 1; i++) {
            betTotalAccount += players.get(i).makeBet();
        }
        betTotalAccount += dealer.makeBet();

        //Deal the initial two hand cards.
        initialHand();

        //Hit or Stand.
        while (playerNumInGame > passedPlayerNum) {
            hitOrStand();

            // clear console output
             try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }


        if (passedPlayerNum == playerNumInGame) {
            checkWinner();
            if (winnerIndex == -1){
                Utils.printToQueue(gameQueue, "No winner!");
            }
            else if (winnerIndex == playerNumInGame - 1) {
                printScore();
                Utils.printToQueue(gameQueue, "Winner is " + dealer.playerName);
                dealer.wager += betTotalAccount;

            } else {
                printScore();
                Utils.printToQueue(gameQueue, "Winner is " + players.get(winnerIndex).playerName);
                players.get(winnerIndex).wager += betTotalAccount;
            }
        }

        Utils.printFromQueue(gameQueue);
    }

    private void initialHand() {

        for (int i = 0; i < playerNumInGame - 1; i++) {
            getClosedCard(players.get(i));
            getOpenCard(players.get(i));
        }

//        dealer.initialHand:
        getClosedCard(dealer);
        getOpenCard(dealer);
    }

    private void hitOrStand() {
        for (int i = 0; i < playerNumInGame - 1; i++) {
            if (players.get(i).getStatus() == 0) {
                Utils.printToQueue(players.get(i).getOutputQueue(), players.get(i).playerName + ", do you want to hit another card ? (1 for yes, others for no)");
                Utils.printFromQueue(players.get(i).getOutputQueue());
                yesOrNoFlag = scanner.nextInt();
                Utils.printToQueue(players.get(i).getOutputQueue(), yesOrNoFlag + "");
                if (yesOrNoFlag == 1) {
                    printPublicInfo(players.get(i), 0, players.get(i).hit());
                    if (players.get(i).getBustFlag() == 1){
                        passedPlayerNum++;
                    }
                } else {
                    players.get(i).stand();
                    passedPlayerNum++;
                }
            } else {
                players.get(i).stand();
            }


        }
        if (botDealerFlag == 1) {
            if (dealer.getStatus() == 0){

                botDealerOpFlag = 1;
                printPublicInfo(dealer, 0, dealer.botDealer(botDealerOpFlag, botLevel));
                botDealerOpFlag = 0;

                if (dealer.getStatus() == 1){
                    passedPlayerNum++;
                }
            }
        }
    }

    private void checkWinner() {

        Utils.printToQueue(gameQueue, "--------check winner----------");
//        endFlag = 1;
        Player.endFlag = 1;
        scoreBoard = new int[playerNumInGame];
        int i;

        if (players.get(0).getBustFlag() == 0) {    //Initialization.
            winnerIndex = 0;
            winnerScore = players.get(0).getScore();
        }

        for (i = 0; i < playerNumInGame; i++) {
            if (i < playerNumInGame - 1) {
                if (players.get(i).getBustFlag() == 0)
                    scoreBoard[i] = players.get(i).getScore();
                else
                    scoreBoard[i] = 0;

            } else {
                if (dealer.getBustFlag() == 0)
                    scoreBoard[i] = dealer.getScore();
                else
                    scoreBoard[i] = 0;

            }

            //怎么算平局
            if (i > 0) {
                if (scoreBoard[i] > scoreBoard[i - 1]) {
                    if (i < playerNumInGame - 1) {
                        if (players.get(i).getBustFlag() == 0) {
                            winnerIndex = i;
                            winnerScore = scoreBoard[i];
                        }
                    }
                    else {
                        if (dealer.getBustFlag() == 0) {
                            winnerIndex = i;
                            winnerScore = scoreBoard[i];
                        }
                    }
                }
            }
        }

    }

    private void printScore() {
        for (int i = 0; i < playerNumInGame - 1; i++) {
            Utils.printToQueue(gameQueue, players.get(i).playerName + " gets " + players.get(i).getScore());
        }
        Utils.printToQueue(gameQueue, dealer.playerName + " gets " + dealer.getScore());
    }

    private void getOpenCard(Player p) {
        SingleCard firstOpenCard = Dealer.deal();
        p.openCards.add(firstOpenCard);

        Utils.printSuitRank(firstOpenCard, p, Utils.openFlag);
        p.computeScoreWhenAddingNewCard(firstOpenCard);

        printPublicInfo(p, 0, firstOpenCard);
    }

    private void getClosedCard(Player p) {
        SingleCard firstCloseCard = Dealer.deal();
        p.getCloseCards().add(firstCloseCard);

        Utils.printSuitRank(firstCloseCard, p, Utils.closeFlag);
        p.computeScoreWhenAddingNewCard(firstCloseCard);

        printPublicInfo(p, 1, Dealer.deal());
    }


    private void hit(Player p) {
        if (p.getStatus() == 0) {
            SingleCard newCard = Dealer.deal();
            p.getOpenCards().add(newCard);
            p.computeScoreWhenAddingNewCard(newCard);

            Utils.printToQueue(p.getOutputQueue(),p.playerName + " takes " + newCard.getSuit() + " " + (newCard.getRank() + 1));
            Utils.printToQueue(Game.getGameQueue(), p.playerName + " takes " + newCard.getSuit() + " " + (newCard.getRank() + 1));

            printPublicInfo(p, 0, newCard);

            // Judge whether Bust.
            p.judgeBust();
        }
    }

    private void printPublicInfo(Player p, int openOrClose, SingleCard card) {
        if (openOrClose == 0){   //open flag = 0
            for (int i = 0; i < playerNumInGame - 1; i++) {
                if (!players.get(i).playerName.equals(p.playerName))
                    Utils.printToQueue(players.get(i).getOutputQueue(),p.playerName + " takes " + card.getSuit() + " " + (card.getRank() + 1));
            }
        }
        else {   //close flag = 1
            for (int i = 0; i < playerNumInGame - 1; i++) {
                if (!players.get(i).playerName.equals(p.playerName))
                    Utils.printToQueue(players.get(i).getOutputQueue(),p.playerName + " takes " +  " ** " + " (hidden) ");
            }
        }
    }

    public int getWinnerIndex() {
        return winnerIndex;
    }

    public void setWinnerIndex(int winnerIndex) {
        this.winnerIndex = winnerIndex;
    }

    public int getWinnerScore() {
        return winnerScore;
    }

    public void setWinnerScore(int winnerScore) {
        this.winnerScore = winnerScore;
    }

    public static Queue<String> getGameQueue() {
        return gameQueue;
    }
}
