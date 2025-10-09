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
	private Mario mario;

	//TODO fill your code
	
	public Game(int nLevel) {
		
		this.nLevel = nLevel;
		initLevel0();
	}
	
	public String positionToString(int col, int row) {
		
		
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
		return 0;
	}

	public int numLives() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public String toString() {
		// TODO returns a textual representation of the object
		return "TODO: Hola soy el game";
	}
	
	
	private void initLevel0() {
		this.nLevel = 0;
		this.remainingTime = 100;
		
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
	
}
