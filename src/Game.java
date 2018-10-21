import java.util.*;

public class Game {
    public int endFlag = 0; //0 for initial value, 1 for game end to check winner.

    private int playerNumInGame = 0;
    private int currentTurn;
    public static int passedPlayerNum = 0;
    public Player[] players;
    public Dealer dealer;
    private Scanner scanner = new Scanner(System.in);
    private int yesOrNoFlag = 1;
    private int[] scoreBoard;
    private int botDealerFlag; //Flag bit to open/close bot Dealer. 0 for close, 1 for open.
    private int botDealerOpFlag; // Flag bit to identify bot Dealer's Operation. 0 for no operation, 1 for hitOrStand.
    private int botLevel = 1; // Difficulty level.
    private int winnerIndex = 0;
    private int max;

    Game(int pNum, int botNum) {
        if (botNum > pNum) {
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
                System.out.println("Dealer will be bot.");
                botDealerFlag = 1;
            }

        }
    }

    public void play() {
        //Deal the initial two hand cards.
        for (int i = 0; i < playerNumInGame - 1; i++) {
            players[i].initialHand();
        }
        dealer.initialHand();

        //Hit or Stand.
        while (playerNumInGame > passedPlayerNum) {
            hitOrStand();
        }

        if (passedPlayerNum == playerNumInGame) {
            checkWinner();
            if (winnerIndex == playerNumInGame - 1){
                System.out.println("Winner is " + dealer.playerName);
                printScore();
            }
            else {
                System.out.println("Winner is " + players[winnerIndex].playerName);
                printScore();
            }
        }
    }

    private void hitOrStand() {
        for (int i = 0; i < playerNumInGame - 1; i++) {
            if (players[i].getStatus() == 0) {
                System.out.println(players[i].playerName + ", do you want to hit another card ? (1 for yes, others for no)");
                yesOrNoFlag = scanner.nextInt();
                if (yesOrNoFlag == 1) {
                    players[i].hit();
                } else {
                    players[i].stand();
                    passedPlayerNum++;
                }
            } else {
                players[i].stand();
            }

        }
        if (botDealerFlag == 1) {
            botDealerOpFlag = 1;
            dealer.botDealer(botDealerOpFlag, botLevel);
            botDealerOpFlag = 0;
        }

    }

    private void checkWinner() {
        endFlag = 1;
        Player.endFlag = 1;
        scoreBoard = new int[playerNumInGame];
        int i;
        max = players[0].getScore(); //Initialization.
        for (i = 0; i < playerNumInGame; i++) {
            if (i < playerNumInGame - 1) {
                scoreBoard[i] = players[i].getScore();

            } else {
                scoreBoard[i] = dealer.getScore();

            }

            if (i > 0) {
                if (scoreBoard[i] > scoreBoard[i - 1]) {
                    winnerIndex = i;
                    max = scoreBoard[i];
                }
            }
        }

    }

    private void printScore() {
        for (int i = 0; i < playerNumInGame - 1; i++) {
            System.out.printf("%s gets %d, ", players[i].playerName, players[i].getScore());
        }
        System.out.println(dealer.playerName + " gets " + dealer.getScore());
    }


}
