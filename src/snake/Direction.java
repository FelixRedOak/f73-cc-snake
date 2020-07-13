package snake;

public enum Direction {
	
	UP, DOWN, LEFT, RIGHT, NONE;
	
	Direction getOpposite() {
		switch (this) {
			case UP: return DOWN;
			case DOWN: return UP;
			case LEFT: return RIGHT;
			case RIGHT: return LEFT;
			default: return NONE;
		}
	}
	
}
