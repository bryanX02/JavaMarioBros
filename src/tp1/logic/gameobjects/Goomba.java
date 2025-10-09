package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.Position;
import tp1.view.Messages;

public class Goomba extends GameObject {
	
	private Game game;

	public Goomba(Game game, Position pos) {
		super();
		this.game = game;
		this.pos = pos;
		
	}
	
	public String toString() {
		
		return Messages.GOOMBA;
		
	}
	

}
