package tp1.logic.gameobjects;

import tp1.logic.Position;

public interface GameItem {
	
	public boolean isSolid();
	public boolean isAlive();
	public boolean isOnPosition(Position pos); 

	public boolean interactWith(GameItem item);
	public boolean receiveInteraction(Land obj);
	public boolean receiveInteraction(ExitDoor obj);
	public boolean receiveInteraction(Mario obj);
	public boolean receiveInteraction(Goomba obj);
}