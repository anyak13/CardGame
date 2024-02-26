// Anya Kothari
// 2/19/24
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Deck {
    public ArrayList<Card> cards;
    private int cardsLeft;
    private Image cardImage;
    public Deck(String[] ranks, String[] suits, int[] values) {
        cards = new ArrayList<Card>();
        cardsLeft = 0;

        int cardNum = 1;
        // Creates an image for each card in the deck
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                cardImage = new ImageIcon("Resources/" + cardNum + ".png").getImage();
                cards.add(new Card(ranks[i],suits[j],values[i], cardImage));
                cardsLeft++;
                cardNum++;
            }
        }
        shuffle();
    }
    public boolean isEmpty() {
        if (cardsLeft == 0) {
            return true;
        }
        return false;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal() {
        if (cardsLeft == 0) {
            return null;
        }
        cardsLeft--;
        return cards.get(cardsLeft);
    }

    // Shuffles the deck of cards
    public void shuffle() {
        Card temp;
        for (int i = cardsLeft - 1; i >= 0; i--) {
            int r = (int)(Math.random() * i);
            temp = cards.get(i);
            cards.set(i,cards.get(r));
            cards.set(r,temp);
        }
    }
}
