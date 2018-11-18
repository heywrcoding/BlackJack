/**
 *  Rank: 0 - A, 1 - 2, ..., 12 - K
 */
import java.util.Random;
class Deck {
    private static int totalNum;
    private static int heartNum;
    private static int spadeNum;
    private static int clubNum;
    private static int diamondNum;
    private static int[] heart = new int[13];
    private static int[] spade = new int[13];
    private static int[] club = new int[13];
    private static int[] diamond = new int[13];


    Deck(int deckNum){
        totalNum = 52 * deckNum;
        heartNum = 13 * deckNum;
        spadeNum = 13 * deckNum;
        clubNum = 13 * deckNum;
        diamondNum = 13 * deckNum;
        for(int rank = 0; rank < 13; rank++){
            heart[rank] = deckNum;
            spade[rank] = deckNum;
            club[rank] = deckNum;
            diamond[rank] = deckNum;
        }
    }
    static String[] draw(){
        if(totalNum == 0){
            return new String[] {"wrong", "wrong"};
        }
        // Begin drawing
        String[] card = randomCardGen();
        String suit = card[0];
        int rank = Integer.parseInt(card[1]);
        totalNum--;
        switch (suit){
            case "heart":
                heartNum--;
                heart[rank]--;
                break;
            case "spade":
                spadeNum--;
                spade[rank]--;
                break;
            case "club":
                clubNum--;
                club[rank]--;
                break;
            case "diamond":
                diamondNum--;
                diamond[rank]--;
                break;
        }

        return card;
    }
    private static String[] randomCardGen(){
        Random random = new Random();
        int randomNum;
        if (totalNum == 1) {
            randomNum = 1;
        }
        else {
            randomNum = random.nextInt(totalNum - 1) + 1;
        }
        String[] card = new String[2];
        int rank = 0;
        String suit = "wrong";
        outer:
        while (randomNum > 0){
            int count;
            for(count = 0; count < 13; count++){
                randomNum = randomNum - heart[count];
                if(randomNum == 0){
                    rank = count;
                    suit = "heart";
                    break outer;
                }
            }
            for(count = 0; count < 13; count++){
                randomNum = randomNum - spade[count];
                if(randomNum == 0){
                    rank = count;
                    suit = "spade";
                    break outer;
                }
            }
            for(count = 0; count < 13; count++){
                randomNum = randomNum - club[count];
                if(randomNum == 0){
                    rank = count;
                    suit = "club";
                    break outer;
                }
            }
            for(count = 0; count < 13; count++){
                randomNum = randomNum - diamond[count];
                if(randomNum == 0){
                    rank = count;
                    suit = "diamond";
                    break outer;
                }
            }
        }

        card[0] = suit;
        card[1] = Integer.toString(rank);
        return card;
    }
}
