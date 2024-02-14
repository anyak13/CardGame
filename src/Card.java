import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
public class Card {
    private String rank;
    private String suit;
    private int point;
    private GameViewer window;

    private Image cardImage;
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

    public void draw(Graphics g)
    {
        g.drawImage(cardImage, 50, 50, 50, 50, window);
    }
}
