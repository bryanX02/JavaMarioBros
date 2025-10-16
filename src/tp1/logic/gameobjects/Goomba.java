package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class Goomba extends GameObject implements MovableObject {
	
	private Game game;
	private Direction direction;
	private boolean alive;

	public Goomba(Game game, Position pos) {
		super();
		this.game = game;
		this.pos = pos;
		this.direction = Direction.LEFT;
		this.alive = true;
	}
	
	public String toString() {
		
		return Messages.GOOMBA;
		
	}
	
	public boolean isAlive() {
		return alive;
	}

	/*El comportamiento de un Goomba es completamente automático:
	Si se encuentra sobre un objeto sólido, avanza un paso por turno en la dirección actual (empieza moviéndose hacia la izquierda).
	Si choca con un objeto sólido o con la pared lateral del tablero, invierte su dirección.
	Si no tiene suelo debajo, cae una casilla hacia abajo hasta volver a encontrarse con un objeto sólido.
	Si sale del tablero por abajo, muere.
	Cuando un Goomba muere, debe ser eliminado de la lista de Goombas.*/
	public void update() {
	    if (!alive) return;

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
	            this.alive = false;
	        }
	    }
	}
	
	/**
	 * Este método es llamado por Mario cuando hay una colisión.
	 * Marca al Goomba como muerto.
	 */
	public boolean receiveInteraction(Mario mario) {
		this.alive = false;
		return true;
	}
	

}
