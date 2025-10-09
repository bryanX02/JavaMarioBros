package tp1.logic.gameobjects;

import tp1.logic.Position;

public class GameObject {
	
	// Atributos
	protected Position pos;
	

	public boolean isOnPosition(Position pos) {
		return this.pos.equals(pos);
	}
	


	public Position getPos() {
		return pos;
	}
}
