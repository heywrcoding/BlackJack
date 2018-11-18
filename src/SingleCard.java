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
    String getSuit() {
        return suit;
    }

    int getRank() {
        return rank;
    }
}
