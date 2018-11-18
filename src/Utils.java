import java.util.*;

class Utils {
    static final int openFlag = 0;
    static final int closeFlag = 1;
    static final int endFlag = 0;
    static final int continueFlag = 1;

    static void printToQueue(Queue<String> outputQueue, String str) {
        outputQueue.offer(str);
//        System.out.println(str);
    }

    static void printFromQueue(Queue<String> outputQueue) {
        for (String q : outputQueue) {
            System.out.println(q);
        }
    }
    static void printSuitRank(SingleCard card, Player player, int openOrClose) {
        if (openOrClose == 0){ //open flag = 0
            switch (card.getRank()) {
                case 0:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " A " );
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " A " );
                    break;
                case 10:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " J " );
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " J " );
                    break;
                case 11:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " Q " );
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " Q " );
                    break;
                case 12:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " K " );
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " K " );
                    break;
                default:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " " + (card.getRank() + 1));
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " " + (card.getRank() + 1));
                    break;
            }
        }
        else if (openOrClose == 1){
            switch (card.getRank()) {
                case 0:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " A " + " (hidden) " );
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " A " + " (hidden) ");
                    break;
                case 10:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " J " + " (hidden) ");
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " J " + " (hidden) ");
                    break;
                case 11:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " Q " + " (hidden) ");
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " Q " + " (hidden) ");
                    break;
                case 12:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " K " + " (hidden) ");
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " K " + " (hidden) ");
                    break;
                default:
                    Utils.printToQueue(player.getOutputQueue(),player.playerName + " takes " + card.getSuit() + " " + (card.getRank() + 1) + " (hidden) ");
                    Utils.printToQueue(Game.getGameQueue(),player.playerName + " takes " + card.getSuit() + " " + (card.getRank() + 1) + " (hidden) ");
                    break;
            }
        }
    }

    static void clearConsole() {
        // clear console output
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (Exception e){
            System.out.println("Console clear error!");
        }
    }

}
