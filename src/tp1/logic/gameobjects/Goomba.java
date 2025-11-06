package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

// Representa a un Goomba en el juego.
public class Goomba extends MovingObject {
	
	public Goomba(GameWorld game, Position pos) {
		super(game, pos);
		super.direction = Direction.LEFT;
	}
	
	public String toString() {
		
		return Messages.GOOMBA;
		
	}
	

	/*El comportamiento de un Goomba es completamente automático:
	Si se encuentra sobre un objeto sólido, avanza un paso por turno en la dirección actual (empieza moviéndose hacia la izquierda).
	Si choca con un objeto sólido o con la pared lateral del tablero, invierte su dirección.
	Si no tiene suelo debajo, cae una casilla hacia abajo hasta volver a encontrarse con un objeto sólido.
	Si sale del tablero por abajo, muere.
	Cuando un Goomba muere, debe ser eliminado de la lista de Goombas.*/
	@Override
	public void update() {
		if (!isAlive()) return;

	    if (game.solidObjectDown(this.pos)) {
	        // Movimiento horizontal normal si hay suelo
	        int moveDir = (this.direction == Direction.RIGHT) ? 1 : -1;
	        Position nextPos = new Position(this.pos.getRow(), this.pos.getCol() + moveDir);
	        
	        if (game.solidObjectAt(nextPos) || nextPos.isOnBorder()) {
	            this.direction = (this.direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
	        } else {
	            this.pos = nextPos;
	        }
	        
	    } else {
	        // Caída automática: se mueve solo una casilla hacia abajo por turno.
	        this.pos = new Position(this.pos.getRow() + 1, this.pos.getCol());
	        
	        if (this.pos.isOut()) {
	            this.die();
	        }
	    }
	}
	
	@Override
	public boolean interactWith(GameItem other) {
		// Goomba inicia la interacción (si choca con algo)
		if (other.isOnPosition(pos)) {
			return other.receiveInteraction(this);
		}
		return false;
	}
	
	/**
	 * Este método es llamado por Mario cuando hay una colisión.
	 * Marca al Goomba como muerto.
	 */
	@Override
	public boolean receiveInteraction(Mario mario) {
		// La lógica de P1 es que Goomba siempre muere
		this.die();
		return true;
	}
	

}
