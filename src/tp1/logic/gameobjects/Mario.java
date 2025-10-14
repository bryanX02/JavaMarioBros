package tp1.logic.gameobjects;

import tp1.logic.Direction;
import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class Mario extends GameObject implements MovableObject {

	private Game game;
	private Direction direction;;
	
	
	public Mario(Game game, Position pos) {
		super();
		this.game = game;
		this.pos = pos;
		this.direction = Direction.RIGHT;
	}

	/**
	 *  En Mario.update() se deberán considerar tres aspectos:

El movimiento automático.
Las posibles acciones del jugador.
Las colisiones con otros objetos.

El movimiento automático de Mario es muy parecido al de Goomba, pero con algunas diferencias:

Mario comienza caminando hacia la derecha (no hacia la izquierda).
Cuando Mario muere avisa al game de que ha muerto para que haga los ajustes necesarios:
Perder una vida. Tiene inicialmente tres vidas.
Resetear la partida. La partida termina cuando se queda sin vida mostrando el mensaje "Game over".
Además de este movimiento automático, en Mario.update() se deberán procesar también:

Las acciones añadidas por el jugador (almacenadas en la clase ActionList).
Las colisiones con otros objetos del tablero (por ejemplo: Goombas o la puerta de salida).
Esto se verá en las siguientes secciones del enunciado.

	 */
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
				game.marioDies();
			}
		}
		
		
		
	}
	
	// FunciÃ³n que : devuelve el icono, 🧍, 🚶o 🧍, según su direcció

	public String getIcon() {
	 
		return "";
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
	
	
}
