package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario extends GameObject{

	private Game game;
	
	
	public Mario(Game game, Position pos) {
		super();
		this.game = game;
		this.pos = pos;
	}

	/**
	 *  Implements the automatic update	
	 */
	public void update() {
		//TODO fill your code
	}
	
	// FunciÃ³n que : devuelve el icono, 🧍, 🚶o 🧍, según su direcció

	public String getIcon() {
	 
		return "";
	}
 
	// Funcion que devuelve una representaciÃ³n de Mario
	public String toString() {
	
		return Messages.MARIO_STOP;
	}
}
