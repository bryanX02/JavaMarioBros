package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Mario;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.ExitDoor;

public class Game {

	public static final int DIM_X = 30;
	public static final int DIM_Y = 15;
	
	private GameObjectContainer gameObjects;
	private int nLevel;
	private int remainingTime;
	private int numPoints;
	private int numLives;
	private Boolean isReset = false;
	private Mario mario;
	private Boolean finished = false;
	private Boolean victory = false;

	//TODO fill your code
	
	public Game(int nLevel) {
		
		this.nLevel = nLevel;
		
		reset(nLevel);
		
	}
	
	public boolean isFinished() {
		return finished;
	}
	

	public String positionToString(int row, int col) {
		Position pos = new Position(row, col);
		
		String cellContent = gameObjects.getObjectsToStringAt(pos);
		
		// Lógica para la cabeza de Mario grande, comprobando si está fuera del tablero
		if (mario != null && !mario.getPos().isOut() && mario.isBig()) {
			Position headPos = new Position(mario.getPos().getRow() - 1, mario.getPos().getCol());
			if (pos.equals(headPos)) {
				cellContent += mario.toString();
			}
		}
		
		if (!cellContent.isEmpty()) {
			return cellContent;
		}

		return " ";
	}

	public boolean playerWins() {
		// TODO Auto-generated method stub
		return victory;
	}
	public boolean playerLoses() {
		// TODO Auto-generated method stub
		return !victory && finished;
	}

	public int remainingTime() {
		// TODO Auto-generated method stub
		return this.remainingTime;
	}

	public int points() {
		// TODO Auto-generated method stub
		return this.numPoints;
	}

	public int numLives() {
		// TODO Auto-generated method stub
		return this.numLives;
	}

	@Override
	public String toString() {
		// TODO returns a textual representation of the object
		return "TODO: Hola soy el game";
	}
	
	
	private void initLevel0(Boolean isReset) {
		this.nLevel = 0;
		this.remainingTime = 100;
		
		if (!isReset) {
			this.numPoints = 0;
			this.numLives = 3;
		}

		
		// 1. Mapa
		gameObjects = new GameObjectContainer();
		for(int col = 0; col < 15; col++) {
			gameObjects.add(new Land(new Position(13,col)));
			gameObjects.add(new Land(new Position(14,col)));		
		}

		gameObjects.add(new Land(new Position(Game.DIM_Y-3,9)));
		gameObjects.add(new Land(new Position(Game.DIM_Y-3,12)));
		for(int col = 17; col < Game.DIM_X; col++) {
			gameObjects.add(new Land(new Position(Game.DIM_Y-2, col)));
			gameObjects.add(new Land(new Position(Game.DIM_Y-1, col)));		
		}

		gameObjects.add(new Land(new Position(9,2)));
		gameObjects.add(new Land(new Position(9,5)));
		gameObjects.add(new Land(new Position(9,6)));
		gameObjects.add(new Land(new Position(9,7)));
		gameObjects.add(new Land(new Position(5,6)));
		
		// Salto final
		int tamX = 8, tamY= 8;
		int posIniX = Game.DIM_X-3-tamX, posIniY = Game.DIM_Y-3;
		
		for(int col = 0; col < tamX; col++) {
			for (int fila = 0; fila < col+1; fila++) {
				gameObjects.add(new Land(new Position(posIniY- fila, posIniX+ col)));
			}
		}

		gameObjects.add(new ExitDoor(new Position(Game.DIM_Y-3, Game.DIM_X-1)));

		// 3. Personajes
		this.mario = new Mario(this, new Position(Game.DIM_Y-3, 0));
		gameObjects.add(this.mario);

		gameObjects.add(new Goomba(this, new Position(0, 19)));
	}
	
	// Es el mismo que el nivel 0 con más goombas en las posiciones (4,6), (12,6), (12,8), (10,10),
	// (12,11), (12,14) y (0,19)
	private void initLevel1(Boolean isReset) {

		initLevel0(isReset);
		this.nLevel = 1;
		
		gameObjects.add(new Goomba(this, new Position(4, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 6)));
		gameObjects.add(new Goomba(this, new Position(12, 8)));
		gameObjects.add(new Goomba(this, new Position(10, 10)));
		gameObjects.add(new Goomba(this, new Position(12, 11)));
		gameObjects.add(new Goomba(this, new Position(12, 14)));
		
	
	}
	
	/*Se llevará a cabo el reseteo de todo el tablero y del time, que también volverá a su valor inicial. No obstante, tanto el total de puntos como el número de vidas se mantienen igual que estaban (no sufren el efecto del comando). */
	public boolean reset(int level) {
		boolean isAReset = (this.mario != null); // Es un reset si el juego ya estaba inicializado
		
		if (level == 0) {
			initLevel0(isAReset);
		} else if (level == 1) {
			initLevel1(isAReset);
		} else {
			// Si el nivel no existe, se reinicia al nivel actual
			reset(this.nLevel);
		}
		return true;
	}
	
	public Boolean solidObjectDown(Position pos) {
		
		Position downPos = new Position(pos.getRow() + 1, pos.getCol());
		return solidObjectAt(downPos);
	}
	
	/*
	 * Ejecución de acciones en Mario
		En cada turno, las acciones del jugador se aplican antes del movimiento automático de Mario. El proceso en Mario.update() es:
		
		Las acciones indicadas por el jugador se registran en una lista de acciones pendientes.
		Cada acción se ejecuta secuencialmente, modificando la posición o el estado de Mario según corresponda. Esto permite encadenar varias acciones en un único ciclo, logrando movimientos más complejos (por ejemplo: RIGHT UP UP → Mario avanza y escala dos casillas).
		La lista de acciones debe dejarse vacía para evitar su repetición en el siguiente turno.
		Si alguna acción ha cambiado la posición de Mario en ese turno, el movimiento automático no se aplica. Si no se ha movido, Mario realiza su movimiento automático normal.
		Coordinación desde el Game
		El método Game.update() coordina el ciclo de juego, integrando las acciones de Mario y el movimiento automático de otros objetos. Los métodos implicados en Game son:
		
		public void addAction(Action act) → Añade una acción a la lista de acciones de Mario (invocado desde el controlador cuando el jugador introduce un comando).
		public void update() → Ejecuta un ciclo completo del juego:
		Llama a GameObjectContainer.update().
		Este actualiza primero a Mario (Mario.update()) y luego al update de los enemigos (Goombas).
		Por tanto, el método update() de Game debe actualizar el tiempo y solicitar al GameObjectContainer que realice una actualización de todos los objetos que tiene. Es decir Mario y Goombas.
	 */
	public void addAction(Action act) {
		this.mario.addAction(act);
	}
	
	// Por tanto, el método update() de Game debe actualizar el tiempo y solicitar al GameObjectContainer que realice una actualización de todos los objetos que tiene. Es decir Mario y Goombas.
	public void update() {
		
		this.remainingTime--;
		// Actualiza todos los objetos del juego (Mario y Goombas)
		gameObjects.update();
		if (this.remainingTime <= 0) {
			marioDies();
		}
		
		
	}

	public boolean solidObjectAt(Position pos) {
		return gameObjects.getSolidObjectAt(pos) instanceof Land;
	}

	public void remove(Goomba goomba) {
		
		gameObjects.remove(goomba);
		
	}

	/*
	 * Cuando Mario muere avisa al game de que ha muerto para que haga los ajustes necesarios:
		Perder una vida. Tiene inicialmente tres vidas.
		Resetear la partida. La partida termina cuando se queda sin vida mostrando el mensaje "Game over".	
	 */
	public void marioDies() {
		this.numLives--;
		
		if (this.numLives == 0) {
			this.finished = true;
		} else {
			reset(this.nLevel);
		}
		
	}

	// El método marioExited() será el encargado de actualizar el estado de la partida, sumando a los puntos del jugador el valor resultante de la multiplicación entre el tiempo restante y 10. Además, marcará que la partida ha finalizado en victoria, mostrando por consola el mensaje Thanks, Mario! Your mission is complete..
	public void marioExited() {
		this.numPoints += this.remainingTime * 10;
		this.finished = true;
		this.victory = true;
		
	}
	
	public void addPoints(int points) {
		this.numPoints += points;
	}
	
	public void doInteractionsFrom(Mario mario) {
		gameObjects.doInteractionsFrom(mario);
	}


	public int getLevel() {
	    return this.nLevel;
	}

	public void clearActions() {
		this.mario.clearActions();
		
	}
	
}
