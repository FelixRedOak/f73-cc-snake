package snake;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

// KeyListener: Tastatureingaben -> Richtung ändern
// JPanel: Zeichenbereich

// 3x Einen Bereich in neue Klasse auslagern
// 5x einzelne Methode verbessern / auslagern
// 100x Instanzvariablen lokal machen
// Sonarqube-Meldungen bearbeiten
public class GameviewAndKeyListener extends JPanel implements KeyListener {

	private SnakeMovement snakeMovement;

	// TODO: separate class containing ImageIcons?
	// TODO: add this to Direction?
	private ImageIcon rightmouth = new ImageIcon("rightmouth.png");
	private ImageIcon upmouth = new ImageIcon("upmouth.png");
	private ImageIcon downmouth = new ImageIcon("downmouth.png");
	private ImageIcon leftmouth = new ImageIcon("leftmouth.png");
	private ImageIcon snakeimage = new ImageIcon("snakeimage.png");

	private int[] enemyxpos = { 25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450,
			475, 500, 525, 550, 575, 600, 625, 650, 675, 700, 725, 750, 775, 800, 825, 850 };
	private int[] enemyypos = { 75, 100, 125, 150, 175, 200, 225, 250, 275, 300, 325, 350, 375, 400, 425, 450, 475, 500,
			525, 550, 575, 600, 625 };
	private ImageIcon enemyimage;
	private Random random = new Random();
	private int xpos = random.nextInt(34);
	private int ypos = random.nextInt(23);

	private int score = 0;
	private int moves = 0;

	private ImageIcon titleImage;

	public GameviewAndKeyListener() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		snakeMovement = new SnakeMovement(this);
	}

	@Override
	public void paint(Graphics g) {
		if (moves == 0) {
			snakeMovement.getXCoordinates()[2] = 50;
			snakeMovement.getXCoordinates()[1] = 75;
			snakeMovement.getXCoordinates()[0] = 100;

			snakeMovement.getYCoordinates()[2] = 100;
			snakeMovement.getYCoordinates()[1] = 100;
			snakeMovement.getYCoordinates()[0] = 100;

		}
		// draw title image border
		g.setColor(Color.WHITE);
		g.drawRect(24, 10, 851, 51);

		// draw the title image
		titleImage = new ImageIcon("snaketitle.jpg");
		titleImage.paintIcon(this, g, 25, 11);

		// draw border for gameplay
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 851, 577);

		// draw background for the gameplay
		g.setColor(Color.black);
		g.fillRect(25, 75, 850, 575);

		// draw scores
		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Scores: " + score, 780, 30);

		g.setColor(Color.WHITE);
		g.setFont(new Font("arial", Font.PLAIN, 14));
		g.drawString("Length: " + snakeMovement.getLengthOfSnake(), 780, 50);

		rightmouth = new ImageIcon("rightmouth.png");
		rightmouth.paintIcon(this, g, snakeMovement.getXCoordinates()[SnakeMovement.HEAD_POSITION],
				snakeMovement.getYCoordinates()[SnakeMovement.HEAD_POSITION]);

		// TODO: use HEAD_POSIION variable instead of "0"?
		// TODO: mouth-image -> map?
		for (int snakePartIndex = 0; snakePartIndex < snakeMovement.getLengthOfSnake(); snakePartIndex++) {
			if (snakePartIndex == 0) {
				// TODO: use arrow notation
				ImageIcon headIcon = switch (snakeMovement.getHeadDirection()) {
					case RIGHT: yield rightmouth;
					case LEFT: yield leftmouth;
					case UP: yield upmouth;
					case DOWN: yield downmouth;
					default: throw new IllegalArgumentException("Unexpected value: " + snakeMovement.getHeadDirection());
				};
				paintSnakePart(g, headIcon, snakePartIndex);
			} else {
				paintSnakePart(g, snakeimage, snakePartIndex);				
			}

		}

		enemyimage = new ImageIcon("enemy.png");

		if ((enemyxpos[xpos] == snakeMovement.getXCoordinates()[0]
				&& enemyypos[ypos] == snakeMovement.getYCoordinates()[0])) {
			// TODO: maybe replace set... by reset/inc
			snakeMovement.setLengthOfSnake(snakeMovement.getLengthOfSnake() + 1);
			score++;
			xpos = random.nextInt(34);
			ypos = random.nextInt(23);
		}

		enemyimage.paintIcon(this, g, enemyxpos[xpos], enemyypos[ypos]);
		for (int b = 1; b < snakeMovement.getLengthOfSnake(); b++) {
			if (snakeMovement.getXCoordinates()[b] == snakeMovement.getXCoordinates()[0]
					&& snakeMovement.getYCoordinates()[b] == snakeMovement.getYCoordinates()[0]) {
				snakeMovement.setHeadDirection(Direction.NONE);

				g.setColor(Color.WHITE);
				g.setFont(new Font("arial", Font.BOLD, 50));
				g.drawString("GAME OVER", 300, 300);

				g.setFont(new Font("arial", Font.BOLD, 20));
				g.drawString("Press Space to Restart", 350, 340);
			}
		}

		g.dispose();
	}

	private void paintSnakePart(Graphics graphics, ImageIcon imageIcon, int snakePartIndex) {
		imageIcon.paintIcon(this, graphics, snakeMovement.getXCoordinates()[snakePartIndex], snakeMovement.getYCoordinates()[snakePartIndex]);
	}

	@Override
	public void keyTyped(KeyEvent ke) {
		// interface KeyListener needs this - we only use keyPressed
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO: Prüfen ob headDirection == NONE -> funktionale Änderung
		switch (e.getKeyCode()) {
		case KeyEvent.VK_SPACE:
			reactOnSpaceKey();
			break;
		case KeyEvent.VK_RIGHT:
			reactOnArrowKey(Direction.RIGHT);
			break;
		case KeyEvent.VK_LEFT:
			reactOnArrowKey(Direction.LEFT);
			break;
		case KeyEvent.VK_UP:
			reactOnArrowKey(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			reactOnArrowKey(Direction.DOWN);
			break;
		default:
			break;
		}
	}

	private void reactOnSpaceKey() {
		moves = 0;
		score = 0;
		snakeMovement.setLengthOfSnake(SnakeMovement.START_SNAKE_LENGTH);
		repaint();
	}

	private void reactOnArrowKey(Direction choosenDirection) {
		moves++;
		if (snakeMovement.getHeadDirection() != choosenDirection.getOpposite()) {
			snakeMovement.setHeadDirection(choosenDirection);
		}
	}

	@Override
	public void keyReleased(KeyEvent ke) {
	}

}
