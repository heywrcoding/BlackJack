/**
 *  Rank: 0 - A, 1 - 2, ..., 13 - K
 */
import java.util.Random;
public class Deck {
    private int totalNum;
    private int heartNum;
    private int spadeNum;
    private int clubNum;
    private int diamondNum;
    private int[] heart = new int[13];
    private int[] spade = new int[13];
    private int[] club = new int[13];
    private int[] diamond = new int[13];
//    private String[] card =

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
    public String[] draw(){
        if(totalNum == 0){
            String[] wrongMessage = {"wrong", "wrong"};
            return wrongMessage;
        }
        // Begin drawing
        String[] card = randomCardGen();
        //unfinished
        totalNum--;
        switch (suit){
            case "heart":
                heartNum--;
                heart[rank] = 0;
                break;
            case "spade":
                spadeNum--;
                spade[rank] = 0;
                break;
            case "club":
                clubNum--;
                club[rank] = 0;
                break;
            case "diamond":
                diamondNum--;
                diamond[rank] = 0;
                break;
        }

        return card;
    }
    private String[] randomCardGen(){
        Random random = new Random();
        int randomNum = random.nextInt(totalNum);
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
