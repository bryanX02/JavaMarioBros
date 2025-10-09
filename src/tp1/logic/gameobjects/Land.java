package tp1.logic.gameobjects;

import tp1.logic.Position;
import tp1.view.Messages;

public class Land extends GameObject{
	
	// Atributos

	
	
	
	public Land(Position pos) {
		super();
		this.pos = pos;
	}



	public String toString() {
		
		return Messages.LAND;
		
	}

}
