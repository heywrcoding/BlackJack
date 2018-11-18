import java.io.*;
public class Main {
    public static void main (String[] args) throws Exception {

//        testDraw();



//        int deckNum = 1;
//        Deck deck = new Deck(deckNum);
        Game game = new Game(2,1, 1000);
        game.play();
    }

    public static void testDraw() {
        int deckNum = 1;
        Deck deck = new Deck(deckNum);
        SingleCard[] cards = new SingleCard[52];
        for (int i = 0; i < 51; i++) {
            cards[i] = new SingleCard();
            System.out.printf("%s ", cards[i].getSuit());
            System.out.println(cards[i].getRank());
        }
        cards[51] = new SingleCard();

    }

    public static void playGame() {

    }
}
