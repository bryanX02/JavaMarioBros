package tp1.logic.gameobjects;

import tp1.logic.Action;
import tp1.logic.ActionList;
import tp1.logic.Direction;
import tp1.logic.Game;
import tp1.logic.GameWorld;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario extends MovingObject {

	private ActionList actions;
	private Direction lastHorizontalDirection;
	private boolean isBig;
	
	public Mario(GameWorld game, Position pos) {
		super(game, pos);
		this.direction = Direction.RIGHT;
		this.lastHorizontalDirection = Direction.RIGHT;
		this.actions = new ActionList();
		this.isBig = true;
		this.isFalling = false;
	}


	/*
	 * En cada turno, las acciones del jugador se aplican antes del movimiento automático de Mario. El proceso en Mario.update() es:

Las acciones indicadas por el jugador se registran en una lista de acciones pendientes.
Cada acción se ejecuta secuencialmente, modificando la posición o el estado de Mario según corresponda. Esto permite encadenar varias acciones en un único ciclo, logrando movimientos más complejos (por ejemplo: RIGHT UP UP → Mario avanza y escala dos casillas).
La lista de acciones debe dejarse vacía para evitar su repetición en el siguiente turno.
Si alguna acción ha cambiado la posición de Mario en ese turno, el movimiento automático no se aplica. Si no se ha movido, Mario realiza su movimiento automático normal.
	 */
	@Override
	public void update() {
	    boolean hasMovedByAction = false;
	    this.isFalling = !game.solidObjectDown(this.pos);

	    // 1. Ejecuta las acciones pendientes del jugador
	    if (!actions.isEmpty()) {
	        for (Action action : actions) {
	            if (action == Action.DOWN) {
	                if (!game.solidObjectDown(this.pos)) {
	                    // La ACCIÓN de bajar sí usa el bucle para caer hasta el fondo
	                	Position currentFallPos = this.pos;
	                    while (!game.solidObjectDown(currentFallPos) && !currentFallPos.isOut()) {
	                        currentFallPos = new Position(currentFallPos.getRow() + 1, currentFallPos.getCol());
	                    }
	                    this.pos = currentFallPos;
	                    if (this.pos.isOut()) { game.marioDies(); return; }
	                    hasMovedByAction = true;
	                    game.doInteractionsFrom(this);
	                } else {
	                    this.direction = Direction.STOP;
	                }
	            } else {
	                Position nextPos = this.pos;
	                switch (action) {
	                    case LEFT:
	                        this.direction = Direction.LEFT;
	                        this.lastHorizontalDirection = Direction.LEFT;
	                        nextPos = new Position(this.pos.getRow(), this.pos.getCol() - 1);
	                        break;
	                    case RIGHT:
	                        this.direction = Direction.RIGHT;
	                        this.lastHorizontalDirection = Direction.RIGHT;
	                        nextPos = new Position(this.pos.getRow(), this.pos.getCol() + 1);
	                        break;
	                    case UP:
	                        Position headCheckPos = new Position(this.pos.getRow() - (isBig ? 2 : 1), this.pos.getCol());
	                        if (!game.solidObjectAt(headCheckPos)) {
	                            nextPos = new Position(this.pos.getRow() - 1, this.pos.getCol());
	                        }
	                        break;
	                    case STOP:
	                        this.direction = Direction.STOP;
	                        break;
	                }

	                if (canMoveTo(nextPos)) {
	                    this.pos = nextPos;
	                    hasMovedByAction = true;
	                    game.doInteractionsFrom(this);
	                }
	            }
	        }
	        actions.clear();
	    }

	    // 2. Aplica el movimiento automático (solo caída) si ninguna acción ha movido a Mario.
	    if (!hasMovedByAction) {
	        if (game.solidObjectDown(this.pos)) {
	            
	            // Movimiento horizontal automático si está sobre suelo firme
	            if (direction != Direction.STOP) {
	                int move = (direction == Direction.RIGHT) ? 1 : -1;
	                Position nextPos = new Position(pos.getRow(), pos.getCol() + move);
	                
	                if (canMoveTo(nextPos)) {
	                    this.pos = nextPos;
	                    game.doInteractionsFrom(this);
	                } else if (game.solidObjectAt(nextPos) || nextPos.isOut()) {
	                    // Si choca, invierte la dirección
	                    this.direction = (this.direction == Direction.RIGHT) ? Direction.LEFT : Direction.RIGHT;
	                    this.lastHorizontalDirection = this.direction;
	                }
	            }
	            
	        } else {
	            // Caída automática de una casilla
	            this.pos = new Position(this.pos.getRow() + 1, this.pos.getCol());
	            if (pos.isOut()) {
	                game.marioDies();
	                return;
	            }
	            game.doInteractionsFrom(this);
	        }
	    }
	}
	
	/**
	 * Comprueba si Mario puede moverse a una nueva posición,
	 * teniendo en cuenta si es grande.
	 */
	private boolean canMoveTo(Position newPos) {
		if (newPos.equals(this.pos)) return false; // No es un movimiento

	    if (newPos.isOut()) return false; // Comprueba si los pies están fuera
	    
		boolean canMove = !game.solidObjectAt(newPos);
		if (isBig) {
			Position newHeadPos = new Position(newPos.getRow() - 1, newPos.getCol());
			
			if (newHeadPos.isOut()) return false; // Comprueba si la cabeza está fuera
			
			canMove = canMove && !game.solidObjectAt(newHeadPos);
		}
		return canMove;
	}
	
	public boolean isBig() {
		return this.isBig;
	}
	public boolean isFalling() { 
		return this.isFalling; 
	}
	/**
	 * Devuelve el icono de Mario según su dirección actual.
	 */
	public String getIcon() {
		// Prioridad 1: Si la dirección es STOP, el icono siempre es el de parado.
		if (this.direction == Direction.STOP) {
			return Messages.MARIO_STOP;
		}
		
		// Prioridad 2: Si se está moviendo activamente a la izquierda o derecha.
		if (this.direction == Direction.LEFT) {
			return Messages.MARIO_LEFT;
		}
		if (this.direction == Direction.RIGHT) {
			return Messages.MARIO_RIGHT;
		}
		
		// Fallback (para movimiento vertical): Se usa la última dirección horizontal guardada.
		if (this.lastHorizontalDirection == Direction.LEFT) {
			return Messages.MARIO_LEFT;
		}
		
		// Por defecto, o si la última dirección fue derecha.
		return Messages.MARIO_RIGHT;
	}

	public String toString() {
		return getIcon();
	}


	public void addAction(Action act) {
		
		this.actions.addAction(act);
		
	}

	@Override
	public boolean interactWith(GameItem other) {
		// Mario (el jugador) inicia las interacciones
		Position headPos = isBig ? new Position(pos.getRow() - 1, pos.getCol()) : pos;
		
		// Comprueba si los pies O la cabeza de Mario están en la posición del 'other'
		if (other.isOnPosition(this.pos) || (isBig && other.isOnPosition(headPos))) {
			 return other.receiveInteraction(this);
		}
		return false;
	}
	
	/**
	 * Llamado por ExitDoor (via double-dispatch) cuando Mario
	 * interactúa con la puerta.
	 * (Lógica de P1 de interactWith(ExitDoor))
	 */
	@Override
	public boolean receiveInteraction(ExitDoor door) {
		game.marioExited();
		return true;
	}
	
	/**
	 * Llamado por Goomba (via double-dispatch) cuando Mario
	 * interactúa con un Goomba.
	 * (Lógica de P1 de interactWith(Goomba))
	 */
	@Override
	public boolean receiveInteraction(Goomba goomba) {
		if (isFalling) {
			// Mario cae sobre el Goomba
			game.addPoints(100);
			goomba.die();
		} else {
			// Choque lateral
			game.addPoints(100);
			goomba.die();
			if (isBig) {
				this.isBig = false;
			} else {
				game.marioDies();
			}
			// (Mantenemos la lógica de P1: no invertimos dirección en el choque)
		}
		return true;
	}

	public void clearActions() {
		this.actions.clear();
		
	}
	
	@Override
	public boolean isOnPosition(Position pos) {
	    // Comprueba si la posición coincide con los pies
	    if (this.pos.equals(pos)) {
	        return true;
	    }
	    // Si es grande, comprueba también la cabeza
	    if (isBig && !this.pos.isOut()) {
	        Position headPos = new Position(this.pos.getRow() - 1, this.pos.getCol());
	        return headPos.equals(pos);
	    }
	    return false;
	}


	public void setPosition(Position position) {
		
		this.pos = position;
		
	}


}