public class Main {
    // The main method is required for a Java program to run
    public static void main(String[] args) {
        
        // Create a new Deck
        UnoDeck deck = new UnoDeck();

        // Resets and shuffles the Deck
        deck.reset();

        // Tells us the size of the deck
        System.out.println("Deck size: " + deck.getCardsInDeck());

        // Draws a card
        UnoCard card = deck.drawOne();

        // Shows us the card
        System.out.println("Drew: " + card.toString());

        // Tells us the new size of the deck
        System.out.println("Deck size: " + deck.getCardsInDeck());

        // Draws four cards
        UnoCard[] cards = deck.drawMultiple(4);

        // Shows us all four cards
        for (UnoCard c : cards) {
            System.out.println(c.toString());
        }

        // Tells us the new size of the deck
        System.out.println("Deck size: " + deck.getCardsInDeck());
    }
}