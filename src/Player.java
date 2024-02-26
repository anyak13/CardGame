// Anya Kothari
// 2/19/24
import java.util.ArrayList;

public class Player {
    private ArrayList<Card> hand;
    private int points;
    private String name;

    // Constructor that initializes name and set points to 0
    public Player(String name) {
        this.name = name;
        points = 0;
    }

    // Constructor than intializes the ArrayList, name, and sets points to 0
    public Player(String name, ArrayList<Card> newHand) {
        this.name = name;
        hand = new ArrayList<Card>();
        hand = newHand;
        this.points = 0;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public void addPoints(int pointsToAdd) {
        points += pointsToAdd;
    }

    public void addCard(Card cardToAdd) {
        hand.add(cardToAdd);
    }

    public String toString() {
        String response;
        response =  name + " has " + points + " points\n";
        response += name + "'s cards: ";
        if (hand != null) {
            for (int i = 0; i < hand.size(); i++) {
                response += hand.get(i) + " ";
            }
        }
        return response;
    }
}
