// Anya Kothari
// 2/19/24
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame {
    private Game game;
    private static final int WINDOW_WIDTH = 800,
            WINDOW_HEIGHT = 800;
    private Image welcomeImage;
    private Image oceanImage;
    private Image cardBack;
    public static final int cardWith = 100;
    public static final int cardHeight = 150;

    private String action;

    // Constructor that initializes the game and images
    public GameViewer(Game game) {
        this.game = game;
        cardBack = new ImageIcon("Resources/back.png").getImage();
        welcomeImage = new ImageIcon("Resources/Welcome image.png").getImage();
        oceanImage = new ImageIcon("Resources/ocean.png").getImage();

        // Setup the window and the buffer strategy
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Go Fish");
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setVisible(true);
    }

    public void paint (Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        // Draws the background image
        g.drawImage(oceanImage, 0, 0, this);
        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.setColor(Color.white);
        // Sets up the starting page with a image and the instructions
        g.drawString("Welcome to Go Fish!", 125, 150);
        g.drawImage(welcomeImage, 150, 40, this);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        int xInstructions = 25;
        int yInstruction = 525;
        g.drawString("Instructions: Each player starts with 7 cards and remaining cards will be", xInstructions, yInstruction);
        g.drawString("in the deck. Then, one player will ask the other player for a particular", xInstructions, yInstruction + 25);
        g.drawString("rank of card. For example, player 1 may ask player 2 if they have any", xInstructions, yInstruction + (25 * 2));
        g.drawString("nines. If player 2 has any nines, then they must give all of their nines", xInstructions, yInstruction + (25 * 3));
        g.drawString("to the asking player, and player 1 gets to go again. And if player 2", xInstructions, yInstruction + (25 * 4));
        g.drawString("doesn't have any nines, then they says Go Fish and the asking player", xInstructions, yInstruction + (25 * 5));
        g.drawString("takes a card from the deck of cards. Then it becomes Player 2's turn.  If", xInstructions, yInstruction + (25 * 6));
        g.drawString("the players gets all four cards of a rank, they can then make a book", xInstructions, yInstruction + (25 * 7));
        g.drawString("with those 4 cards and remove them from their hand. The game is over", xInstructions, yInstruction + (25 * 8));
        g.drawString("when either player runs out of cards or there are no more cards in the", xInstructions, yInstruction + (25 * 9));
        g.drawString("deck. The winner is who has made the most books.", xInstructions, yInstruction + (25 * 10));

        // Transtitions from instruction page to the actual game when the user finishes inputting the names
        if (game.isStartGame()) {
            startGame(g);
        }
        // Goes to a different screen when the game is over
        if (game.getGameOverState()) {
            winningScreen(g);
        }
    }
    // Method that starts the game
    public void startGame(Graphics g) {
        g.setColor(new Color(173,216,230));
        g.fillRect(0, 0, 800, 800);
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        g.drawString(game.p1.getName(), 50, 75);
        g.drawString(game.p2.getName(), 675, 75);
        // Draw card back image representing the deck in the middle
        if (game.getDeck().getCardsLeft() != 0) {
            g.drawImage(cardBack, 400 - cardWith / 2, 400 - cardHeight / 2, cardWith, cardHeight, this);
        }
        // Displays the current score
        g.drawString("Score", 340, 600);
        g.drawString(String.valueOf(game.p1.getPoints()), 285, 675);
        g.drawString(String.valueOf(game.p2.getPoints()), 480, 675);
        // Displays whos turn it is and each player's cards
        if (game.getPlayer1Turn()) {
            g.drawString(game.p1.getName() + "'s turn", 300, 200);
            // If it is p1's turn, draw their cards up and p2's cards down
            for (int i = 0; i < game.p1.getHand().size(); i++) {
                game.p1.getHand().get(i).draw(g, 25, 100 + (i * 40));
            }
            for (int i = 0; i < game.p2.getHand().size(); i++) {
                game.p2.getHand().get(i).draw(g, 675, 100 + (i * 40));
                game.p2.getHand().get(i).drawBacks(g, cardBack, 675, 100 + (i * 40));
            }
        }
        // If it is p2's turn, draw their cards up and p1's cards down
        else {
            g.drawString(game.p2.getName() + "'s turn", 300, 200);
            for (int i = 0; i < game.p2.getHand().size(); i++) {
                game.p2.getHand().get(i).draw(g, 675, 100 + (i * 40));
            }
            for (int i = 0; i < game.p1.getHand().size(); i++) {
                game.p1.getHand().get(i).draw(g, 25, 100 + (i * 40));
                game.p1.getHand().get(i).drawBacks(g, cardBack, 25, 100 + (i * 40));
            }
        }
        // Display "Go Fish" when someone needs to pull from the deck
        if (game.getGameState() == Game.STATE_GOFISH) {
                action = "Go Fish";
                g.drawString(action, 325, 150);
        }
        if (game.getGameState() == Game.STATE_CATCH) {
            action = "You Got a Catch";
            g.drawString(action, 250, 150);
        }
    }

    // Method that sets up the window when someone wins the game
    public void winningScreen(Graphics g) {
        g.setColor(new Color(173,216,230));
        g.fillRect(0, 0, 800, 800);
        g.setColor(Color.black);
        // Draws who the winner was or if it was a tie
        if (game.getGameState() == Game.STATE_P1WINS) {
            g.drawString(game.p1.getName() + " wins!", 300, 400);
        }
        else if (game.getGameState() == Game.STATE_P2WINS) {
            g.drawString(game.p2.getName() + " wins!", 300, 400);
        }
        else g.drawString("It's a Tie", 300, 400);
    }
}


