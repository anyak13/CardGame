import java.util.ArrayList;
import java.util.Scanner;



public class Game {
    private Player p1;
    private Player p2;
    private Deck deck;

    final static int HANDSIZE = 7;
    final static int BOOKSIZE = 4;

    // Constructor for the Game class that prepares for the game to be played
    // Creates the deck and initializes two players with user-input names and an initial hand
    public Game() {

        // create a standard deck of 52
        // Content standard: used arrays for the input variables into the Deck constructor
        // which made sense as these are fixed in size (i.e. there are always 4 suits in a
        // standard card deck, with 13 cards/ranks per suit.)  this fixed nature makes the
        // array structure preferred over the more dynamic arraylist alternative

        String ranks[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String suits[] = {"Hearts", "Clubs", "Diamonds", "Spades"};
        int values[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        deck = new Deck(ranks, suits, values);

        // prompt for user names to prepare to create 2 players
        Scanner input = new Scanner(System.in);
        System.out.println("Enter first player's name: ");
        String player1name = input.nextLine();
        System.out.println("Enter second player's name: ");
        String player2name = input.nextLine();

        // create a 7-card hand for each of the players
        ArrayList<Card> player1hand = new ArrayList<Card>();
        ArrayList<Card> player2hand = new ArrayList<Card>();;
        for (int i = 0; i<HANDSIZE; i++)
        {
            player1hand.add(deck.deal());
            player2hand.add(deck.deal());
        }

        // create 2 players, each with their name and hand of cards
        p1 = new Player(player1name, player1hand);
        p2 = new Player(player2name, player2hand);
    }

    // Simply displays instructions for the implemented version of Go-Fish
    public void printInstructions() {
        System.out.print("Hello " + p1.getName() + " and " + p2.getName() + " and ");
        System.out.println("welcome to Go Fish! \nInstructions: Each player starts with 7 cards and remaining cards will be in central pool. Then, one player will ask the other player for ");
        System.out.println("a particular rank of card. For example, player 1 may ask player 2 if they have any nines. If player 2 has any nines, ");
        System.out.println("then they must give all of their nines to the asking player, and player 1 gets to go again. And if player 2 doesn't have any nines, then they says Go Fish  ");
        System.out.println("and the asking player takes a card from the pool of cards.  Then it becomes Player 2's turn.  If the players gets all four cards of a rank, whether from the ");
        System.out.println("other player or from the deck, they can then make a book with those 4 cards and remove them from their hand.");
        System.out.println("The game is over when either player runs out of cards or there are no more cards in the deck. The winner is who has ");
        System.out.println("made the most books (groups of 4 cards) during the game.\n");
    }

    // simple helper method that is responsible for seeing if a given card value is
    // found within a given hand of cards
    private Boolean isValueInHand(int value, ArrayList<Card> hand)
    {
        for (int i=0; i<hand.size(); i++)
        {
            if (hand.get(i).getPoint() == value)
            {
                return true;
            }
        }
        return false;
    }

// helper method that is used to check the edge case of if a player had a book
// in the initial hand they started with (that type of book wouldn't otherwise
// have been identified since the regular book checks are value specific
private void checkForBookAtStart()
{
    for (int i=1; i<=13;i++)
    {
        checkForBook(i,p1);
        checkForBook(i,p2);
    }
}

    // helper method responsible for determining if a book has been formed within
    // a player's hand, and when that condition is true, it then uses other
    // helper methods to modify the hand accordingly
private void checkForBook(int value, Player player)
    {
        int matches = 0;
        ArrayList<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++)
        {
            if (hand.get(i).getPoint() == value) matches++;
        }
        if (matches == BOOKSIZE)
        {
            createBook(value, hand);
            player.addPoints(1);
            System.out.println("Congrats, " + player.getName() +" made a book!");
        }

    }

    // helper method that is invoked when a book has been identified and it
    // does the task of removing the cards making up the book from the given hand
    private void createBook(int value, ArrayList<Card> hand)
    {
        /*for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getPoint() == value) hand.remove(i);
        }*/
        for (int i = hand.size() - 1; i >= 0; i--) {
            if (hand.get(i).getPoint() == value) hand.remove(i);
        }
    }

    // helper method that is used to transfer one or more cards from one player to another player,
    // for the case where a player asks for a card value that is held by the other player
    // returns the number of matching cards that were moved from one player to the other
    private int updateHands(int value, ArrayList<Card> fromHand, ArrayList<Card> toHand )
    {
        Card temp;
        int counter = 0;

        // Content standard: this for loop is a good example of an algorithm
        // to traverse and modify the content of two different ArrayList objects
        for (int i = fromHand.size() - 1; i >= 0; i--) {
            if (fromHand.get(i).getPoint() == value)
            {
                temp = fromHand.remove(i);
                toHand.add(temp);
                counter++;
            }
        }
        return counter;
    }



    // this is the primary function that manages the game play
    // it lets the users take turns asking for cards until the game is over
    public void playGame() {


        Boolean player1Turn = true;
        Boolean gameOver = false;
        Scanner input = new Scanner(System.in);
        Player askingPlayer;
        Player respondingPlayer;
        int numMatches;

        // Welcome the users and display instructions for the game
        printInstructions();

        System.out.println("Checking to see if anyone got lucky with a book in their starting hand...");
        checkForBookAtStart();

        // Content standard: Boolean expressions are an important aspect of managing this
        // central while loop.  One Boolean variable (gameOver) determines the loop's exit condition
        // and another Boolean variable (player1Turn) keeps track of whose turn it s
        while (!gameOver) {
            if (player1Turn)
            {
                askingPlayer = p1;
                respondingPlayer = p2;
            }
            else
            {
                askingPlayer = p2;
                respondingPlayer = p1;
            }

            System.out.print("It is " + askingPlayer.getName() + "'s turn! ");
            System.out.println("You currently have " + askingPlayer.getPoints() + " books made.");
            System.out.println("These are the cards in your hand:");
            ArrayList<Card> hand = askingPlayer.getHand();
            for (int i = 0; i < hand.size(); i++) {
                System.out.print(hand.get(i) + " | ");
            }
            System.out.println();
            System.out.print("What card do you want to ask your opponent for? ");
            System.out.println("(Enter a number between 1-13: with 1 for Ace, 11 for Jack, 12 for Queen, or 13 for King.)");
            int askValue = input.nextInt();

            //we assume the user enters expected integer input, and then we do some basic error checking for number validity
            while (askValue < 1 || askValue > 13 || !isValueInHand(askValue, askingPlayer.getHand())) {
                System.out.println("That number is invalid.");
                System.out.println("Please enter a number between 1-13 corresponding to a card in your hand!");
                askValue = input.nextInt();
            }

            if (isValueInHand(askValue, respondingPlayer.getHand())) {
                System.out.print("Nice, you got a catch! ");
                numMatches = updateHands(askValue, respondingPlayer.getHand(), askingPlayer.getHand());
                System.out.println("You received " + numMatches + " cards from the other player!");
                checkForBook(askValue,askingPlayer);
            } else {
                System.out.println("Go Fish!");
                Card drawCard = deck.deal();
                askingPlayer.getHand().add(drawCard);
                checkForBook(drawCard.getPoint(),askingPlayer);
                player1Turn = !player1Turn;
            }
            // check these conditions to see if the game is over
            if (deck.isEmpty() || askingPlayer.getHand().isEmpty() || respondingPlayer.getHand().isEmpty())
            {
                gameOver = true;
            }

        }
        System.out.println("The game is over!");
        System.out.println(p1.getName() + " made " + p1.getPoints() + " books.");
        System.out.println(p2.getName() + " made " + p2.getPoints() + " books.");
        if (p1.getPoints() > p2.getPoints())
        {
            System.out.println("The winner of this game of Go Fish is " + p1.getName());
        }
        else if (p1.getPoints() < p2.getPoints())
        {
            System.out.println("The winner of this game of Go Fish is " + p2.getName());
        }
        else System.out.println("It is a tie!");


    }

    public static void main(String[] args) {

        Game game = new Game();
        game.playGame();

    }

}

