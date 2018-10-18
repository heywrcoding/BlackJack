public class SingleCard {
    private String suit;
    private String rank;

    SingleCard(){
        String[] card = Deck.draw();
        suit = card[0];
        rank = card[1];
    }
//    private void setter (){
//
//    }
    public String getSuit(){
        return suit;
    }
    public String getRank(){
        return rank;
    }
}
