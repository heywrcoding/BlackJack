public class Main {
    public static void main(String[] args) {
        int deckNum = 1;
        Deck deck = new Deck(deckNum);
        SingleCard[] cards = new SingleCard[5];
        for(int i = 0; i < 5; i++){
            cards[i] = new SingleCard();
            System.out.printf("%s ", cards[i].getSuit());
            System.out.println(cards[i].getRank());
        }
    }
}
