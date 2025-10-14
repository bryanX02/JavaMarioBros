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

	//TODO fill your code
	
	public Game(int nLevel) {
		
		this.nLevel = nLevel;
		
		switch(nLevel) {
		
		case 0: initLevel0(isReset);
		break;
		
		case 1: initLevel1(isReset);
		break;
		
		default: initLevel0(isReset);
		
		}
		
	}
	
	public boolean isFinished() {
		return finished;
	}
	

	public String positionToString(int row, int col) {
		
		
		// Variables
		String element = " ";
		Position box = new Position(col, row);
		GameObject obj;
		
		
	
		obj = gameObjects.getObjFromPos(box);
		
		if (obj != null) {
			element = obj.toString();
		}
	
		// Si no hubiese coincidido con ninguna posici�n de los elementos del juego devolver�a " "
		return element;
	}

	public boolean playerWins() {
		// TODO Auto-generated method stub
		return false;
	}

	public int remainingTime() {
		// TODO Auto-generated method stub
		return 100;
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

		this.nLevel = 1;
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

		gameObjects.add(new Goomba(this, new Position(4,6)));
		gameObjects.add(new Goomba(this, new Position(12,6)));
		gameObjects.add(new Goomba(this, new Position(12,8)));
		gameObjects.add(new Goomba(this, new Position(10,10)));
		gameObjects.add(new Goomba(this, new Position(12,11)));
		gameObjects.add(new Goomba(this, new Position(12,14)));
		gameObjects.add(new Goomba(this, new Position(0,19)));
		
	
	}
	
	/*Se llevará a cabo el reseteo de todo el tablero y del time, que también volverá a su valor inicial. No obstante, tanto el total de puntos como el número de vidas se mantienen igual que estaban (no sufren el efecto del comando). */
	public boolean reset(int nLevel) {
		
		this.isReset = true;
		
		if (nLevel < 0) {
			return false;
		}
		
		switch(nLevel) {
		
		case 0: initLevel0(isReset);
		break;
		
		case 1: initLevel1(isReset);
		break;
		
		default: return false;
		
		}
		
		return true;
	}
	
	public Boolean solidObjectDown(Position pos) {
		
		Position downPos = new Position (pos.getRow(), pos.getCol() + 1);
		GameObject objDown = gameObjects.getObjFromPos(downPos);
		
		if (objDown != null) {
			return true;
		}
		
		return false;
	}
	
	/*
	 * Para que todo funcione, también será necesario implementar el método public void update() en la clase Game.

		Este método deberá:
		
		Llamar al método update() de la clase GameObjectContainer.
		El contenedor, a su vez, llamará a los métodos update() de los objetos del tablero.
		Es muy importante respetar el orden en que se actualizan los objetos, para que las pruebas no os den problemas:
		
		Primero Mario (para que sus acciones y colisiones se procesen antes).
		Después los Goombas.
		De este modo se garantiza un comportamiento coherente en cada ciclo de juego.
	 */
	public void update() {
		
		gameObjects.update();
		
		
	}

	public boolean solidObjectAt(Position pos) {
		GameObject objAt = gameObjects.getObjFromPos(pos);
		
		if (objAt != null) {
			return true;
		}
		
		return false;
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
			System.out.println("Game Over");
		} else {
			reset(this.nLevel);
		}
		
	}
	
}
