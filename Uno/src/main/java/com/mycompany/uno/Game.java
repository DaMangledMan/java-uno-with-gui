/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.uno;

/**
 *
 * @author jacob
 */
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Game {
    private int currentPlayer;
    private String[] playerIds;
    
    private UnoDeck deck;
    private ArrayList<ArrayList<UnoCard>> playersHands;
    private ArrayList<UnoCard> discards;

    private UnoCard.Color validColor;
    private UnoCard.Value validValue;

    boolean directionClockwise;

    public Game(String[] pids) {
        this.deck = new UnoDeck();
        deck.reset();
        this.discards = new ArrayList<UnoCard>();

        this.playerIds = pids;
        this.currentPlayer = 0;
        this.directionClockwise = true;

        this.playersHands = new ArrayList<ArrayList<UnoCard>>();
        for (int i = 0; i < playerIds.length; i++) {
            ArrayList<UnoCard> hand = new ArrayList<UnoCard>(Arrays.asList(deck.drawMultiple(7)));
            playersHands.add(hand);
        }
    }

    public UnoCard start() {
        UnoCard card = deck.drawOne();
        this.validColor = card.getColor();
        this.validValue = card.getValue();
        discards.add(card);

        if (card.getColor() == UnoCard.Color.Wild || 
        card.getValue() == UnoCard.Value.DrawTwo) {
            this.start();
        }

        if (card.getValue() == UnoCard.Value.Skip) {
            JLabel message = new JLabel(this.playerIds[this.currentPlayer] + " has been skipped!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            
            if (this.directionClockwise) {
                this.currentPlayer = (this.currentPlayer + 1) % playerIds.length;
            }
            else {
                this.currentPlayer = (this.currentPlayer - 1) % playerIds.length;
                if (this.currentPlayer < 0) {this.currentPlayer += playerIds.length;}
            }
        }

        if (card.getValue() == UnoCard.Value.Reverse) {
            JLabel message = new JLabel(this.playerIds[this.currentPlayer] + ", the Direction has been Reversed!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);

            this.directionClockwise = !this.directionClockwise;
        }
        
        return card;
    }

    public UnoCard getLastDiscard() {
        return new UnoCard(this.validColor, this.validValue);
    }

    public String getCardName(UnoCard card) {
        return card.toString();
    }

    public boolean isGameOver() {
        for (ArrayList<UnoCard> hand : this.playersHands) {
            if (hand.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public String getCurrentPlayerName() {
        return this.playerIds[this.currentPlayer];
    }

    public String getPreviousPlayerName() {
        int index;
        if (this.directionClockwise) {
            index = (this.currentPlayer - 1) % playerIds.length;
            if (index < 0) {index += playerIds.length;}
        }
        else {
            index = (this.currentPlayer + 1) % playerIds.length;
        }
        return this.playerIds[index];
    }

    public ArrayList<UnoCard> getPlayerHand(String pid) {
        int index = Arrays.asList(this.playerIds).indexOf(pid);
        return this.playersHands.get(index);
    }

    public int getPlayerHandSize(String pid) {
        return this.getPlayerHand(pid).size();
    }

    public UnoCard getPlayerCard(String pid, int choiceIndex) {
        return this.getPlayerHand(pid).get(choiceIndex);
    }

    public boolean hasEmptyHand(String pid) {
        return this.getPlayerHand(pid).isEmpty();
    }

    public boolean isValidCardPlay(UnoCard card) {
        return card.getColor() == this.validColor || card.getValue() == this.validValue || card.getColor() == UnoCard.Color.Wild;
    }

    public void checkPlayerTurn(String pid) throws InvalidPlayerTurnException {
        if (!this.getCurrentPlayerName().equals(pid)) {
            throw new InvalidPlayerTurnException(pid, this.getCurrentPlayerName());
        }
    }

    public void submitDrawCard(String pid) throws InvalidPlayerTurnException {
        checkPlayerTurn(pid);

        if (deck.isEmpty()) {
            deck.shuffleDiscards(discards);
            discards.clear();
        }

        getPlayerHand(pid).add(deck.drawOne());

        if (this.directionClockwise) {
            this.currentPlayer = (this.currentPlayer + 1) % playerIds.length;
        }
        else {
            this.currentPlayer = (this.currentPlayer - 1) % playerIds.length;
            if (this.currentPlayer < 0) {this.currentPlayer += playerIds.length;}
        }
    }

    public void setValidCard(UnoCard card) {
        this.validColor = card.getColor();
        this.validValue = card.getValue();
    }

    public void setValidColor(UnoCard.Color color) {
        this.validColor = color;
    }

    public void submitPlayerCard(String pid, UnoCard card, UnoCard.Color declaredColor) 
        throws InvalidPlayerTurnException, InvalidCardSubmissionExpception {
        
        checkPlayerTurn(pid);
        
        if (!isValidCardPlay(card)) {
            JLabel message = new JLabel("Invalid Player Move, expected either color: " + this.validColor + " or value: " + this.validValue + ", recieved: " + card.toString());
            message.setFont(new Font("Arial", Font.BOLD, 30));
            JOptionPane.showMessageDialog(null, message);

            throw new InvalidCardSubmissionExpception(new UnoCard(this.validColor, this.validValue), card);
        }

        this.playersHands.get(this.currentPlayer).remove(card);
        
        
        if (hasEmptyHand(this.getCurrentPlayerName())) {
            JLabel message = new JLabel(this.getCurrentPlayerName() + " has won the game!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            System.exit(0);
        }

        this.validColor = card.getColor();
        this.validValue = card.getValue();
        discards.add(card);

        if (card.getValue() == UnoCard.Value.Reverse) {
            this.directionClockwise = !this.directionClockwise;

            JLabel message = new JLabel(this.getPreviousPlayerName() + " has changed the direction!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
        }

        if (this.directionClockwise) {
            this.currentPlayer = (this.currentPlayer + 1) % playerIds.length;
        }
        else {
            this.currentPlayer = (this.currentPlayer - 1) % playerIds.length;
            if (this.currentPlayer < 0) {this.currentPlayer += playerIds.length;}
        }

        pid = this.getCurrentPlayerName();

        if (card.getColor() == UnoCard.Color.Wild) {
            this.validColor = declaredColor;
        }

        if (card.getValue() == UnoCard.Value.DrawTwo) {
            submitDrawCard(pid);
            submitDrawCard(pid);

            JLabel message = new JLabel(pid + " drew two cards!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            
            if (this.directionClockwise) {
                this.currentPlayer = (this.currentPlayer + 1) % playerIds.length;
            }
            else {
                this.currentPlayer = (this.currentPlayer - 1) % playerIds.length;
                if (this.currentPlayer < 0) {this.currentPlayer += playerIds.length;}
            }
        }

        if (card.getValue() == UnoCard.Value.WildFour) {
            this.validColor = declaredColor;

            submitDrawCard(pid);
            submitDrawCard(pid);
            submitDrawCard(pid);
            submitDrawCard(pid);

            JLabel message = new JLabel(pid + " drew four cards!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            
            if (this.directionClockwise) {
                this.currentPlayer = (this.currentPlayer + 1) % playerIds.length;
            }
            else {
                this.currentPlayer = (this.currentPlayer - 1) % playerIds.length;
                if (this.currentPlayer < 0) {this.currentPlayer += playerIds.length;}
            }
        }

        if (card.getValue() == UnoCard.Value.Skip) {
            JLabel message = new JLabel(pid + " has been skipped!");
            message.setFont(new Font("Arial", Font.BOLD, 48));
            JOptionPane.showMessageDialog(null, message);
            
            if (this.directionClockwise) {
                this.currentPlayer = (this.currentPlayer + 1) % playerIds.length;
            }
            else {
                this.currentPlayer = (this.currentPlayer - 1) % playerIds.length;
                if (this.currentPlayer < 0) {this.currentPlayer += playerIds.length;}
            }
        }
    }
}






// Exception Extensions

class InvalidPlayerTurnException extends Exception {
    String PlayerId;

    public InvalidPlayerTurnException(String pid, String currentPlayer) {
        super("It is not " + pid + "'s turn; it should be " + currentPlayer + "'s turn!");
        this.PlayerId = pid;
    }

    public String getPID() {
        return this.PlayerId;
    }
}

class InvalidCardSubmissionExpception extends Exception {
    private UnoCard expected;
    private UnoCard recieved;

    public InvalidCardSubmissionExpception(UnoCard expected, UnoCard recieved) {
        super("Expected: " + expected.toString() + ", Recieved: " + recieved.toString());
        this.expected = expected;
        this.recieved = recieved;
    }

    public UnoCard getExpected() {
        return this.expected;
    }

    public UnoCard getRecieved() {
        return this.recieved;
    }
}