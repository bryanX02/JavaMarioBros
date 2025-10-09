package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.Goomba;
import tp1.logic.gameobjects.Land;
import tp1.logic.gameobjects.Mario;

public class GameObjectContainer {
	

	// Atributos
	private List<Land> landList;
	private List<Goomba> goombaList;
	private ExitDoor exit;
	private Mario mario;
	
	
	public GameObjectContainer() {
		super();
		this.landList = new ArrayList();
		this.goombaList = new ArrayList();
		this.exit = null;
		this.mario = null;
	}
	

	
	

}
