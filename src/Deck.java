import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class Deck {
    private ArrayList<Card> cards;
    private int cardsLeft;

    private Image cardImage;
    private GameViewer window;

    public Deck(String[] ranks, String[] suits, int[] values) {
        cards = new ArrayList<Card>();
        cardsLeft = 0;

        // Content standard: used nested for loops here to iterate through all the
        // suits, and for each suit, created a card of each rank

        for (int i = 0; i < ranks.length; i++)
        {
            for (int j = 0; j < suits.length; j++)
            {
                cardImage = new ImageIcon("Resources/"+ i + ".png").getImage();
                cards.add(new Card(ranks[j],suits[i],values[j], cardImage));
                cardsLeft++;
            }
        }

        /*for (int i=0;i<cardsLeft;i++)
        {
            System.out.println(cards.get(i));
        }*/
        shuffle();
        /*for (int i=0;i<cardsLeft;i++)
        {
            System.out.println(cards.get(i));
        }*/

    }
    public boolean isEmpty() {
        if (cardsLeft == 0)
        {
            return true;
        }
        return false;
    }

    public int getCardsLeft() {
        return cardsLeft;
    }

    public Card deal() {
        if (cardsLeft == 0)
            return null;
        cardsLeft--;
        return cards.get(cardsLeft);
    }

    // Content standard: this helper method utilizes the Math class, specifically the Random method
    // in order to simulate the shuffling of the card deck
    public void shuffle() {
        Card temp;
        for (int i = cardsLeft - 1; i >= 0; i--)
        {
            int r = (int)(Math.random() * i);
            temp = cards.get(i);
            cards.set(i,cards.get(r));
            cards.set(r,temp);
        }
    }

    public void draw(Graphics g) {
        int xP1StartingCard = 0;
        int yP1StartingCard = 0;
        int xP2StartingCard = 700;
        int yP2StartingCard = 0;
        for (int i = 0; i < 7; i++) {
            g.drawImage(cardImage, xP1StartingCard, yP1StartingCard+(150*i), window);
        }
    }
}
