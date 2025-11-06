package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

// Clase ExitDoor que representa la puerta de salida en el juego
public class ExitDoor extends GameObject {

	public ExitDoor(GameWorld game, Position pos) {
		super(game, pos); // Constructor actualizado
	}
	
	@Override
	public String toString() {
		return Messages.EXIT_DOOR;
	}

	@Override
	public boolean isSolid() {
		return false; // ExitDoor no es sólida
	}

	@Override
	public void update() {
		// ExitDoor no hace nada
	}
	
	@Override
	public boolean interactWith(GameItem other) {
		// ExitDoor es pasiva
		if (other.isOnPosition(this.pos)) {
	        return other.receiveInteraction(this);
	    }
	    return false;
	}
	
	@Override
	public boolean receiveInteraction(Mario mario) {
		game.marioExited(); // Llama al método de Game
		return true;
	}
}