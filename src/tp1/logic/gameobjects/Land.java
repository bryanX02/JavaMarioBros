package tp1.logic.gameobjects;

import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

// Clase Land que representa el terreno sólido en el juego
public class Land extends GameObject {
	
	public Land(GameWorld game, Position pos) {
		super(game, pos); // Constructor actualizado
	}

	@Override
	public String toString() {
		return Messages.LAND;
	}

	@Override
	public boolean isSolid() {
		return true; // Land es sólido
	}

	@Override
	public void update() {
		// Land no hace nada
	}
	
	@Override
	public boolean interactWith(GameItem other) {
		// Land es pasivo, pero inicia la llamada de double-dispatch
		if (other.isOnPosition(this.pos)) {
	        return other.receiveInteraction(this);
	    }
	    return false;
	}
}