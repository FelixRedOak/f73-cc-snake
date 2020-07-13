package snake;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class SnakeMovement implements ActionListener {

	private static final int RIGHT_LIMIT_COORDINATE = 850;
	private static final int LEFT_LIMIT_COORDINATE = 25;
	static final int HEAD_POSITION = 0;
	private static final int TOP_LIMIT_COORDINATE = 75;
	private static final int BOTTOM_LIMIT_COORDINATE = 625;
	private static final int STEP_LENGTH = 25;
	
	static final int START_SNAKE_LENGTH = 3;
	private static final int MAX_SNAKE_LENGTH = 750;
	
	private static final int DELAY_IN_MS = 100;

	private Timer timer;

	// TODO Datentyp "Position" einführen und nur ein Array benutzen
	private int[] xCoordinates = new int[MAX_SNAKE_LENGTH];
	private int[] yCoordinates = new int[MAX_SNAKE_LENGTH];

	// TODO: check if "right" is better
	private Direction headDirection = Direction.NONE;

	private int lengthOfSnake = START_SNAKE_LENGTH;

	private Component gamefieldComponent;

	public SnakeMovement(Component gamefieldComponent) {
		this.gamefieldComponent = gamefieldComponent;
		this.timer = new Timer(DELAY_IN_MS, this);
		this.timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		switch (headDirection) {
			case UP:			
				moveUp();
				break;
			case DOWN:			
				moveDown();
				break;
			case LEFT:			
				moveLeft();
				break;
			case RIGHT:			
				moveRight();
				break;
			default:
				break;
		}
	}

	private void moveDown() {
		for (int i = lengthOfSnake; i >= HEAD_POSITION; i--) {
			if (i == HEAD_POSITION) {
				// x coordinate of head stays the same
				yCoordinates[HEAD_POSITION] = yCoordinates[HEAD_POSITION] + STEP_LENGTH;
			} else {
				moveTailElementForward(i);
			}
			if (yCoordinates[i] > BOTTOM_LIMIT_COORDINATE) {
				yCoordinates[i] = TOP_LIMIT_COORDINATE;
			}
		}
		
		gamefieldComponent.repaint();
	}

	private void moveUp() {
		for (int i = lengthOfSnake; i >= HEAD_POSITION; i--) {
			if (i == HEAD_POSITION) {
				// x coordinate of head stays the same
				yCoordinates[HEAD_POSITION] = yCoordinates[HEAD_POSITION] - STEP_LENGTH;
			} else {
				moveTailElementForward(i);
			}
			if (yCoordinates[i] < TOP_LIMIT_COORDINATE) {
				yCoordinates[i] = BOTTOM_LIMIT_COORDINATE;
			}
		}
		
		gamefieldComponent.repaint();
	}

	private void moveLeft() {
		for (int i = lengthOfSnake; i >= HEAD_POSITION; i--) {
			if (i == HEAD_POSITION) {
				xCoordinates[HEAD_POSITION] = xCoordinates[HEAD_POSITION] - STEP_LENGTH;
				// y coordinate of head stays the same
			} else {
				moveTailElementForward(i);
			}
			if (xCoordinates[i] < LEFT_LIMIT_COORDINATE) {
				xCoordinates[i] = RIGHT_LIMIT_COORDINATE;
			}
		}
		
		gamefieldComponent.repaint();
	}

	private void moveRight() {
		for (int i = lengthOfSnake; i >= HEAD_POSITION; i--) {
			if (i == HEAD_POSITION) {
				xCoordinates[HEAD_POSITION] = xCoordinates[HEAD_POSITION] + STEP_LENGTH;
				// y coordinate of head stays the same
			} else {
				moveTailElementForward(i);
			}
			if (xCoordinates[i] > RIGHT_LIMIT_COORDINATE) {
				xCoordinates[i] = LEFT_LIMIT_COORDINATE;
			}
		}
		
		gamefieldComponent.repaint();
	}
	
	private void moveTailElementForward(int i) {
		xCoordinates[i] = xCoordinates[i - 1];
		yCoordinates[i] = yCoordinates[i - 1];
	}

	public int[] getXCoordinates() {
		return xCoordinates;
	}

	public int[] getYCoordinates() {
		return yCoordinates;
	}

	public Direction getHeadDirection() {
		return headDirection;
	}

	public void setHeadDirection(Direction headDirection) {
		this.headDirection = headDirection;
	}

	public int getLengthOfSnake() {
		return lengthOfSnake;
	}

	public void setLengthOfSnake(int lengthOfSnake) {
		this.lengthOfSnake = lengthOfSnake;
	}

}
