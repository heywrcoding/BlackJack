class SingleCard {
    private String suit;
    private int rank;

    SingleCard() {
        String[] card = Deck.draw();
        suit = card[0];
        rank = Integer.parseInt(card[1]);
    }

    //    private void setter (){
//
//    }
    public String getSuit() {
        return suit;
    }

    public int getRank() {
        return rank;
    }
}
