package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class Goomba extends GameObject implements MovableObject {
	
	private Game game;
	private Direction direction;

	public Goomba(Game game, Position pos) {
		super();
		this.game = game;
		this.pos = pos;
		this.direction = Direction.LEFT;
		
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
	
	public void update() {
		
		
		Position downPos = new Position (this.pos.getRow() + 1, this.pos.getCol());
		Position nextPos = new Position (this.pos.getRow(), this.pos.getCol());
	
		
		// Si se encuentra sobre un objeto sólido, avanza un paso por turno en la dirección actual (empieza moviéndose hacia la izquierda).
		if (game.solidObjectAt(downPos)){
			
			nextPos.move(this.direction);
			
			// Si choca con un objeto sólido o con la pared lateral del tablero, invierte su dirección.
			if (game.solidObjectAt(nextPos) || nextPos.isOnBorder()) {
				if (this.direction == Direction.LEFT) {
					this.direction = Direction.RIGHT;
				} else {
					this.direction = Direction.LEFT;
				}
				
			}
			this.pos.move(this.direction);
			
		} else {
			// Si no tiene suelo debajo, cae una casilla hacia abajo hasta volver a encontrarse con un objeto sólido.
			do {
				this.pos.move(Direction.DOWN);
				downPos = new Position (this.pos.getRow() + 1, this.pos.getCol());
			} while (!game.solidObjectDown(downPos) && !this.pos.isOut());
			
			// Si sale del tablero por abajo, muere.
			if (this.pos.isOut()) {
				game.remove(this);
			}
			
		}
		
	
		
	}

	@Override
	public void changeDirection(int dir) {

		
		
	}
	

}
