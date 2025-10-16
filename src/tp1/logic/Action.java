package tp1.logic;

/**
 * Represents the allowed actions in the game
 *
 */
public enum Action {
	LEFT(-1,0), RIGHT(1,0), DOWN(0,1), UP(0,-1), STOP(0,0);
	
	private int x;
	private int y;
	
	private Action(int x, int y) {
		this.x=x;
		this.y=y;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public static Action parse(String input) {
	    for (Action action : Action.values()) {
	        if (action.name().equalsIgnoreCase(input)) {
	            return action;
	        }
	    }
	    // Comprobación de abreviaturas
	    switch (input.toLowerCase()) {
	        case "l": return LEFT;
	        case "r": return RIGHT;
	        case "u": return UP;
	        case "d": return DOWN;
	        case "s": return STOP;
	        default: return null;
	    }
	}
	
}
