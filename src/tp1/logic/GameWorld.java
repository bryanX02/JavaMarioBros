package tp1.logic;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;

public interface GameWorld {
    public boolean solidObjectDown(Position pos);
    public boolean solidObjectAt(Position pos);
    public void marioDies();
    public void marioExited();
    public void addPoints(int points);
    public void doInteractionsFrom(Mario mario);
    public void remove(GameObject obj);
}