import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameViewer extends JFrame {
    private Game game;
    private Image[] cardImages;
    private static final int WINDOW_WIDTH = 800,
            WINDOW_HEIGHT = 800;
    private Image welcomeImage;
    private Image oceanImage;
    private Image cardBack;

    public GameViewer(Game game) {
        this.game = game;
        cardImages = new Image[52];
        cardImages[0] = new ImageIcon("Resources/1.png").getImage();
        for (int i = 0; i < 52; i++) {
            cardImages[i] = new ImageIcon("Resources/"+ i + ".png").getImage();
        }
                cardBack = new ImageIcon("Resources/back.png").getImage();
                welcomeImage = new ImageIcon("Resources/Welcome image.png").getImage();
                oceanImage = new ImageIcon("Resources/ocean.png").getImage();

                this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setTitle("Go Fish");
                this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
                this.setVisible(true);
    }

    public void paint (Graphics g){
        g.setColor(Color.white);
        g.fillRect(0, 0, 800, 800);
        g.drawImage(oceanImage, 0, 0, this);
        g.setFont(new Font("Serif", Font.BOLD, 50));
        g.setColor(Color.black);
        g.drawString("Welcome to Go Fish!", 125, 150);

        g.drawImage(welcomeImage, 150, 40, this);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("Instructions: Each player starts with 7 cards and remaining cards will be", 25, 525);
        g.drawString("in the deck. Then, one player will ask the other player for a particular", 25, 550);
        g.drawString("rank of card. For example, player 1 may ask player 2 if they have any", 25, 575);
        g.drawString("nines. If player 2 has any nines, then they must give all of their nines", 25, 600);
        g.drawString("to the asking player, and player 1 gets to go again. And if player 2", 25, 625);
        g.drawString("doesn't have any nines, then they says Go Fish and the asking player", 25, 650);
        g.drawString("takes a card from the deck of cards. Then it becomes Player 2's turn.  If", 25, 675);
        g.drawString("the players gets all four cards of a rank, they can then make a book", 25, 700);
        g.drawString("with those 4 cards and remove them from their hand. The game is over", 25, 725);
        g.drawString("when either player runs out of cards or there are no more cards in the", 25, 750);
        g.drawString("deck. The winner is who has made the most books.", 25, 775);

        g.drawImage(cardImages[0], 50, 50, 100, 150,this);

        startGame(g);

        game.draw(g);

    }
    public void startGame(Graphics g) {
        g.setColor(Color.white);
        g.fillRect(0, 0, 800, 800);
    }
}

