// Anya Kothari
// 2/19/24
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
public class Card {
    private String rank;
    private String suit;
    private int point;
    private GameViewer window;
    private Image cardImage;
    public static final int CARD_WIDTH = 100;
    public static final int CARD_HEIGHT = 150;
    public Card(String rank, String suit, int point, Image cardImage) {
        this.rank = rank;
        this.suit = suit;
        this.point = point;
        this.cardImage = cardImage;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String toString() {
        return rank + " of " + suit;
    }

    // Draw method so each card can draw itself
    public void draw(Graphics g, int x, int y) {
        g.drawImage(cardImage, x, y, CARD_WIDTH, CARD_HEIGHT, window);
    }
    // Method to draw the back of a card
    public void drawBacks(Graphics g, Image i, int x, int y) {
        g.drawImage(i, x, y, CARD_WIDTH, CARD_HEIGHT, window);
    }
}
