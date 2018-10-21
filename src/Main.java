public class Main {
    public static void main(String[] args) {

//        testDraw();
        Game game = new Game(2,1);
        game.play();

    }

    public static void testDraw() {
        int deckNum = 1;
        Deck deck = new Deck(deckNum);
        SingleCard[] cards = new SingleCard[5];
        for (int i = 0; i < 5; i++) {
            cards[i] = new SingleCard();
            System.out.printf("%s ", cards[i].getSuit());
            System.out.println(cards[i].getRank());
        }

    }

    public static void playGame() {

    }
}
