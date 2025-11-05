package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.Game;
import tp1.logic.Position;

//
public abstract class MovingObject extends GameObject {

	protected Direction direction;
	protected boolean isFalling;

	public MovingObject(Game game, Position pos) {
		super(game, pos);
		this.isFalling = false; // Se recalculará en el update de la subclase
	}
	
	// Implementación básica de 'isSolid' para objetos móviles
	@Override
	public boolean isSolid() {
		return false; // Mario y Goomba no son sólidos
	}

	
}