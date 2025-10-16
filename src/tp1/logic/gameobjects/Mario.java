package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.Direction;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario extends GameObject implements MovableObject {

	private Game game;
	private Direction direction;;
	private ActionList actions;
	
	public Mario(Game game, Position pos) {
		super();
		this.game = game;
		this.pos = pos;
		this.direction = Direction.RIGHT;
		this.actions = new ActionList();
	}


	/*
	 * En cada turno, las acciones del jugador se aplican antes del movimiento automático de Mario. El proceso en Mario.update() es:

Las acciones indicadas por el jugador se registran en una lista de acciones pendientes.
Cada acción se ejecuta secuencialmente, modificando la posición o el estado de Mario según corresponda. Esto permite encadenar varias acciones en un único ciclo, logrando movimientos más complejos (por ejemplo: RIGHT UP UP → Mario avanza y escala dos casillas).
La lista de acciones debe dejarse vacía para evitar su repetición en el siguiente turno.
Si alguna acción ha cambiado la posición de Mario en ese turno, el movimiento automático no se aplica. Si no se ha movido, Mario realiza su movimiento automático normal.
	 */
	public void update() {
		
		
		// Ejecuta las acciones en la lista de acciones pendientes
		boolean hasMoved = false;
		for (Action action : actions) {
			switch (action) {
			case LEFT:
				this.direction = Direction.LEFT;
				Position nextPosLeft = new Position(this.pos.getRow(), this.pos.getCol() - 1);
				if (!game.solidObjectAt(nextPosLeft) && !nextPosLeft.isOnBorder()) {
					this.pos = nextPosLeft;
					hasMoved = true;
				}
				break;
			case RIGHT:
				this.direction = Direction.RIGHT;
				Position nextPosRight = new Position(this.pos.getRow(), this.pos.getCol() + 1);
				if (!game.solidObjectAt(nextPosRight) && !nextPosRight.isOnBorder()) {
					this.pos = nextPosRight;
					hasMoved = true;
				}
				break;
			case UP:
				Position nextPosUp = new Position(this.pos.getRow() - 1, this.pos.getCol());
				if (!game.solidObjectAt(nextPosUp) && !nextPosUp.isOnBorder()) {
					this.pos = nextPosUp;
					hasMoved = true;
				}
				break;
			case DOWN:
				Position downPos = new Position(this.pos.getRow() + 1, this.pos.getCol());
				if (!game.solidObjectDown(downPos)) {
					do {
						this.pos = new Position(this.pos.getRow() + 1, this.pos.getCol());
						downPos = new Position(this.pos.getRow() + 1, this.pos.getCol());
					} while (!game.solidObjectDown(downPos) && !this.pos.isOut());
					if (this.pos.isOut()) {
						game.marioDies();
					}
					hasMoved = true;
				} else {
					this.direction = Direction.STOP;
				}
				break;
			case STOP:
				this.direction = Direction.STOP;
				break;
			}
		
			
		}
		// Limpia la lista de acciones pendientes
		this.actions.clear();
		// Si no se ha movido, realiza su movimiento automático normal
		if (!hasMoved) {
			Position downPos = new Position (this.pos.getRow() + 1, this.pos.getCol());
			Position nextPos = new Position (this.pos.getRow(), this.pos.getCol() + (this.direction == Direction.RIGHT ? 1 : -1));
			
			
			// Si se encuentra sobre un objeto sólido, avanza un paso por turno en la dirección actual (empieza moviéndose hacia la izquierda).
			if (game.solidObjectAt(downPos)){
				
				// Si choca con un objeto sólido o con la pared lateral del tablero, invierte su dirección.
				if (game.solidObjectAt(nextPos) || nextPos.isOnBorder()) {
					this.direction = (this.direction == Direction.LEFT) ? Direction.RIGHT : Direction.LEFT;
				}
				this.pos = new Position(this.pos.getRow(), this.pos.getCol() + (this.direction == Direction.RIGHT ? 1 : -1));
				
			} else {
				// Si no tiene suelo debajo, cae una casilla hacia abajo hasta volver a encontrarse con un objeto sólido.
				do {
					this.pos = new Position(this.pos.getRow() + 1, this.pos.getCol());
					downPos = new Position (this.pos.getRow() + 1, this.pos.getCol());
				} while (!game.solidObjectDown(downPos) && !this.pos.isOut());
				// Si sale del tablero por abajo, muere.
				if (this.pos.isOut()) {
					game.marioDies();
				}
			}
		}
		
		
	}
	
	// FunciÃ³n que : devuelve el icono, 🧍, 🚶o 🧍, según su direcció
	// Si Mario esta en up o down, el icono no cambia

	public String getIcon() {
	 
		switch (this.direction) {
		case LEFT:
			return Messages.MARIO_LEFT;
		case RIGHT:
			return Messages.MARIO_RIGHT;
		case UP:
			if (this.direction == Direction.LEFT) {
				return Messages.MARIO_LEFT;
			} else if (this.direction == Direction.RIGHT) {
				return Messages.MARIO_RIGHT;
			} else {
				return Messages.MARIO_STOP;
			}
		case DOWN:
			if (this.direction == Direction.LEFT) {
				return Messages.MARIO_LEFT;
			} else if (this.direction == Direction.RIGHT) {
				return Messages.MARIO_RIGHT;
			} else {
				return Messages.MARIO_STOP;
			}
		case STOP:
			return Messages.MARIO_STOP;
		default:
			return Messages.MARIO_STOP;
		}
	}
 
	// Funcion que devuelve una representaciÃ³n de Mario
	public String toString() {
	
		return Messages.MARIO_STOP;
	}

	@Override
	public void changeDirection(int dir) {
		
		switch (dir) {
		case 0:
			this.direction = Direction.RIGHT;
			break;
		case 1:
			this.direction = Direction.LEFT;
			break;
		case 2:
			this.direction = Direction.UP;
			break;
		case 3:
			this.direction = Direction.DOWN;
			break;
		case 4:
			// STOP
			break;
		}
		
	}


	public void addAction(Action act) {
		
		this.actions.addAction(act);
		
	}

	/*
	 * Una vez que conocemos cómo realizar movimientos y aplicar las acciones pedidas por comando, pasamos a analizar las colisiones entre objetos. En esta sección se tratará la colisión entre Mario y las puertas de salida (ExitDoor).

Esta colisión se gestionará mediante el método de la clase Mario:
Este método debe comprobar si Mario se encuentra en la misma posición que la puerta de salida y, en caso afirmativo, invocar al método de la clase Game: public void marioExited()
	 */
	public boolean interactWith(ExitDoor door) {
		if (door.isOnPosition(this.pos)) {
			game.marioExited();
			return true;
		}
		return false;
	}

}