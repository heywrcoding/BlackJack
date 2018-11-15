import java.security.KeyStore;
import java.util.*;
import java.io.IOException;

public class Game {
    private int endFlag = 0; //0 for initial value, 1 for game end to check winner.

    private int playerNumInGame = 0;
    private int currentTurn;
    private static int passedPlayerNum = 0;
    private Player[] players;
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

    Game(int pNum, int botNum) {

        Utils.printToQueue(gameQueue, "*********************");
        Utils.printToQueue(gameQueue, "Game Log:");
        Utils.printToQueue(gameQueue, "*********************");

        int deckNum = 1;
        Deck deck = new Deck(deckNum);

        if (botNum > pNum) {
            Utils.printToQueue(gameQueue, "Error! The number of Bots must be less than of Players!");
            System.out.println("Error! The number of Bots must be less than of Players!");
        } else {
            players = new Player[pNum - 1];
            for (int i = 0; i < pNum - 1; i++)
                players[i] = new Player();
            dealer = new Dealer("Dealer");

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
        //Deal the initial two hand cards.
        initialHand();

        //Hit or Stand.
        while (playerNumInGame > passedPlayerNum) {
            hitOrStand();

            // clear console output
             try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor(); // 清屏命令
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

            } else {
                printScore();
                Utils.printToQueue(gameQueue, "Winner is " + players[winnerIndex].playerName);
            }
        }

        Utils.printFromQueue(gameQueue);
    }

    private void initialHand() {

        for (int i = 0; i < playerNumInGame - 1; i++) {
            getClosedCard(players[i]);
            getOpenCard(players[i]);
        }

//        dealer.initialHand:
        getClosedCard(dealer);
        getOpenCard(dealer);
    }

    private void hitOrStand() {
        for (int i = 0; i < playerNumInGame - 1; i++) {
            if (players[i].getStatus() == 0) {
                Utils.printToQueue(players[i].getOutputQueue(), players[i].playerName + ", do you want to hit another card ? (1 for yes, others for no)");
                Utils.printFromQueue(players[i].getOutputQueue());
                yesOrNoFlag = scanner.nextInt();
                Utils.printToQueue(players[i].getOutputQueue(), yesOrNoFlag + "");
                if (yesOrNoFlag == 1) {
                    printPublicInfo(players[i], 0, players[i].hit());
                    if (players[i].getBustFlag() == 1){
                        passedPlayerNum++;
                    }
                } else {
                    players[i].stand();
                    passedPlayerNum++;
                }
            } else {
                players[i].stand();
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
        endFlag = 1;
        Player.endFlag = 1;
        scoreBoard = new int[playerNumInGame];
        int i;

        if (players[0].getBustFlag() == 0) {    //Initialization.
            winnerIndex = 0;
            winnerScore = players[0].getScore();
        }

        for (i = 0; i < playerNumInGame; i++) {
            if (i < playerNumInGame - 1) {
                if (players[i].getBustFlag() == 0)
                    scoreBoard[i] = players[i].getScore();
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
                        if (players[i].getBustFlag() == 0) {
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
            Utils.printToQueue(gameQueue, players[i].playerName + " gets " + players[i].getScore());
        }
        Utils.printToQueue(gameQueue, dealer.playerName + " gets " + dealer.getScore());
    }

    private void getOpenCard(Player p) {
        SingleCard firstOpenCard = Dealer.deal();
        p.openCards.add(firstOpenCard);

        Utils.printSuitRank(firstOpenCard, p, 0);
        p.computeScoreWhenAddingNewCard(firstOpenCard);

        printPublicInfo(p, 0, firstOpenCard);
    }

    private void getClosedCard(Player p) {
        SingleCard firstCloseCard = Dealer.deal();
        p.getCloseCards().add(firstCloseCard);

        Utils.printSuitRank(firstCloseCard, p, 1);
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
                if (!players[i].playerName.equals(p.playerName))
                    Utils.printToQueue(players[i].getOutputQueue(),p.playerName + " takes " + card.getSuit() + " " + (card.getRank() + 1));
            }
        }
        else {   //close flag = 1
            for (int i = 0; i < playerNumInGame - 1; i++) {
                if (!players[i].playerName.equals(p.playerName))
                    Utils.printToQueue(players[i].getOutputQueue(),p.playerName + " takes " +  " ** " + " (hidden) ");
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
