// Anya Kothari
// 2/19/24
// This program uses various methods to play the game Go Fish. It also has a front-end of game that displays the status of the game through card images.
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;


public class Game {
    public Player p1;
    public Player p2;
    private Deck deck;
    private boolean startGame;
    private int gameState;
    private boolean isGameOver = false;
    final static int HANDSIZE = 7;
    final static int BOOKSIZE = 4;
    final static int STATE_GOFISH = 1;
    final static int STATE_CATCH = 2;
    final static int STATE_P1WINS = 3;
    final static int STATE_P2WINS = 4;
    final static int STATE_P1P2TIE = 5;
    private GameViewer window;
    private String userInput;
    private String continueNextTurn;
    private boolean player1Turn;

    // Constructor that creates the deck and initializes two players
    public Game() {
        this.window = new GameViewer(this);
        // Create a standard deck of 52
        String ranks[] = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String suits[] = {"Spades","Hearts", "Diamonds","Clubs"};
        int values[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        deck = new Deck(ranks, suits, values);

        // Prompt for user's names
        Scanner input = new Scanner(System.in);
        System.out.println("Enter first player's name: ");
        String player1name = input.nextLine();
        userInput = player1name;
        System.out.println("Enter second player's name: ");
        String player2name = input.nextLine();
        userInput = player2name;
        startGame = true;
        isGameOver = false;

        // Create a 7-card hand for each of the players
        ArrayList<Card> player1hand = new ArrayList<Card>();
        ArrayList<Card> player2hand = new ArrayList<Card>();
        for (int i = 0; i<HANDSIZE; i++) {
            player1hand.add(deck.deal());
            player2hand.add(deck.deal());
        }

        // Create 2 players, each with their name and hand of cards
        p1 = new Player(player1name, player1hand);
        p2 = new Player(player2name, player2hand);
        player1Turn = true;
        window.repaint();
    }

    // Getter methods to access instance variables from GameViewer
    public boolean isStartGame() {
        return startGame;
    }
    public boolean getGameOverState()
    {
        return isGameOver;
    }
    public int getGameState() {
        return gameState;
    }
    public Boolean getPlayer1Turn() {
        return player1Turn;
    }
    public Deck getDeck() {
        return deck;
    }

    // Prints out instructions for the game
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

    // Method that checks if a given card value is found within a given hand of cards
    private boolean isValueInHand(int value, ArrayList<Card> hand) {
        for (int i=0; i<hand.size(); i++) {
            if (hand.get(i).getPoint() == value) {
                return true;
            }
        }
        return false;
    }

    // Method that is used to check if a player had a book in the initial hand they started with
    private void checkForBookAtStart() {
        for (int i=1; i<=13;i++) {
            checkForBook(i,p1);
            checkForBook(i,p2);
        }
    }

    // Method that checks if a book has been formed within a player's hand
    private void checkForBook(int value, Player player) {
        int matches = 0;
        ArrayList<Card> hand = player.getHand();
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getPoint() == value) {
                matches++;
            }
        }
        // Checking if the number of matches equals the size of a book
        if (matches == BOOKSIZE) {
            // A book is created and point is added
            createBook(value, hand);
            player.addPoints(1);
            window.repaint();
            System.out.println("Congrats, " + player.getName() +" made a book!");
        }

    }

    // Method that creates a book by removing the cards making up the book from the given hand
    private void createBook(int value, ArrayList<Card> hand) {
        for (int i = hand.size() - 1; i >= 0; i--) {
            if (hand.get(i).getPoint() == value) {
                hand.remove(i);
            }
        }
    }

    // Method that transfers cards from the responding player to the asking player
    private int updateHands(int value, ArrayList<Card> fromHand, ArrayList<Card> toHand ) {
        Card temp;
        int counter = 0;

        for (int i = fromHand.size() - 1; i >= 0; i--) {
            if (fromHand.get(i).getPoint() == value) {
                temp = fromHand.remove(i);
                toHand.add(temp);
                counter++;
            }
        }
        // Returns the number of cards in the responding players hand that matches what the other player is asking for
        return counter;
    }

    // Method that manages the game and allows users take turns asking for cards until the game is over
    public void playGame() {
        Scanner input = new Scanner(System.in);
        Player askingPlayer;
        Player respondingPlayer;
        int numMatches;

        // Welcome the users and display instructions for the game
        printInstructions();

        System.out.println("Checking to see if anyone got lucky with a book in their starting hand...");
        checkForBookAtStart();

        while (!isGameOver) {
            if (player1Turn) {
                askingPlayer = p1;
                respondingPlayer = p2;
            }
            else {
                askingPlayer = p2;
                respondingPlayer = p1;
            }
            // Updating window based on who's turn it is
            window.repaint();
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

            // Check if the number entered is valid
            while (askValue < 1 || askValue > 13 || !isValueInHand(askValue, hand)) {
                System.out.println("That number is invalid.");
                System.out.println("Please enter a number between 1-13 corresponding to a card in your hand!");
                askValue = input.nextInt();
            }

            // Check if the responding player has the card that was asked for
            if (isValueInHand(askValue, respondingPlayer.getHand())) {
                System.out.print("Nice, you got a catch! ");
                window.repaint();
                gameState = STATE_CATCH;
                numMatches = updateHands(askValue, respondingPlayer.getHand(), askingPlayer.getHand());
                System.out.println("You received " + numMatches + " cards from the other player!");
                checkForBook(askValue, askingPlayer);
            }
            else {
                System.out.println("Go Fish!");
                gameState = STATE_GOFISH;
                window.repaint();
                    // Add a card from the deck and change turns
                    Card drawCard = deck.deal();
                    askingPlayer.getHand().add(drawCard);
                    checkForBook(drawCard.getPoint(),askingPlayer);
                    player1Turn = !player1Turn;
            }
            // Check these conditions to see if the game is over
            if (deck.isEmpty() || askingPlayer.getHand().isEmpty() || respondingPlayer.getHand().isEmpty()) {
                isGameOver = true;
                window.repaint();
            }
        }
        // Print the winner of the game
        System.out.println("The game is over!");
        isGameOver = true;
        window.repaint();
        System.out.println(p1.getName() + " made " + p1.getPoints() + " books.");
        System.out.println(p2.getName() + " made " + p2.getPoints() + " books.");
        if (p1.getPoints() > p2.getPoints()) {
            System.out.println("The winner of this game of Go Fish is " + p1.getName());
            gameState = STATE_P1WINS;
        }
        else if (p1.getPoints() < p2.getPoints()) {
            System.out.println("The winner of this game of Go Fish is " + p2.getName());
            gameState = STATE_P2WINS;
        }
        else {
            System.out.println("It is a tie!");
            gameState = STATE_P1P2TIE;
            //window.repaint();
        }
        window.repaint();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.playGame();
    }
}

