import java.util.ArrayList;

public class Player {

    //Content standard: ArrayList used to represent the player's hand as it provides
    //more flexibility (vs a fixed array object) to accomodate the dynamic size of a
    //player's hand over the course of a game
    private ArrayList<Card> hand;
    private int points;
    private String name;

    public Player(String name)
    {
        this.name = name;
        points = 0;
    }

    public Player(String name, ArrayList<Card> hand)
    {
        this.hand = hand;
        this.name = name;
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
