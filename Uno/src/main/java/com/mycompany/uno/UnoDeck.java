/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 *
 * @author jacob
 */

import java.util.ArrayList;
import java.util.Random;

public class UnoDeck {
    private UnoCard[] cards;
    private int cardsInDeck;

    public UnoDeck() {
        cards = new UnoCard[108];
    }
    
    public void reset() {
        UnoCard.Color[] colors = UnoCard.Color.values();
        cardsInDeck = 0;

        for(int i = 0; i < colors.length-1; i++) {
            UnoCard.Color color = colors[i];

            UnoCard.Value[] values = new UnoCard.Value[] {UnoCard.Value.One, UnoCard.Value.Two, UnoCard.Value.Three, UnoCard.Value.Four, UnoCard.Value.Five, UnoCard.Value.Six, UnoCard.Value.Seven, UnoCard.Value.Eight, UnoCard.Value.Nine, UnoCard.Value.DrawTwo, UnoCard.Value.Skip, UnoCard.Value.Reverse};
            for(UnoCard.Value value : values){
                this.cards[this.cardsInDeck++] = new UnoCard(color, value);
                this.cards[this.cardsInDeck++] = new UnoCard(color, value);
            }
        }
        UnoCard.Value[] values = new UnoCard.Value[] {UnoCard.Value.Wild, UnoCard.Value.WildFour, UnoCard.Value.WildFour};
        for(UnoCard.Value value : values) {
            for(int i = 0; i < 4; i++) {
                this.cards[this.cardsInDeck++] = new UnoCard(UnoCard.Color.Wild, value);
            }
        }
        
        this._shuffle();
    }

    public void shuffleDiscards(ArrayList<UnoCard> discards) {
        this.cards = discards.toArray(new UnoCard[discards.size()]);
        this.cardsInDeck = this.cards.length;
        this._shuffle();
    }

    private void _shuffle() {
        int n = this.cards.length;
        Random rand = new Random();

        for(int i = 0; i < n; i++) {
            int randIndex = rand.nextInt(n);
            UnoCard randCard = this.cards[randIndex];
            this.cards[randIndex] = this.cards[i];
            this.cards[i] = randCard;
        }
    }

    public boolean isEmpty() {
        return cardsInDeck == 0;
    }

    public UnoCard drawOne() throws IllegalArgumentException {
        if (this.isEmpty()) {
            throw new IllegalArgumentException("Deck is empty");
        }
        return this.cards[--this.cardsInDeck];
    }

    public UnoCard[] drawMultiple(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Must draw at least one card");
        }
        UnoCard[] drawnCards = new UnoCard[n];
        for (int i = 0; i < n; i++) {
            drawnCards[i] = this.drawOne();
        }
        return drawnCards;
    }

    public int getCardsInDeck() {
        return this.cardsInDeck;
    }
}
