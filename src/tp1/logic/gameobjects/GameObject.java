package tp1.logic.gameobjects;

import tp1.logic.Game;
import tp1.logic.GameWorld;
import tp1.logic.Position;


public abstract class GameObject implements GameItem {
	
	protected Position pos;
	protected GameWorld game;
	private boolean isAlive;

	public GameObject(GameWorld game, Position pos) {
		this.game = game;
		this.pos = pos;
		this.isAlive = true;
	}

	// --- Métodos de GameItem ---

	@Override
	public boolean isOnPosition(Position pos) {
		// Asumimos que Mario Grande (isBig) maneja su propia cabeza
		// Esta comprobación es solo para los pies (la 'pos' base)
		return this.pos.equals(pos);
	}

	@Override
	public boolean isAlive() {
		return isAlive;
	}
	
	public void die() {
		this.isAlive = false;
	}

	// --- Implementaciones por defecto (triviales) para interacciones ---
	//
	// Las subclases sobreescribirán solo las que les afecten.
	
	@Override
	public boolean interactWith(GameItem item) {
		// El double dispatch se implementa en las subclases concretas
		return false; 
	}
	
	@Override
	public boolean receiveInteraction(Land obj) {
		return false; // Por defecto, nada pasa al chocar con Land
	}

	@Override
	public boolean receiveInteraction(ExitDoor obj) {
		return false; // Por defecto, nada pasa
	}

	@Override
	public boolean receiveInteraction(Mario obj) {
		return false; // Por defecto, nada pasa
	}

	@Override
	public boolean receiveInteraction(Goomba obj) {
		return false; // Por defecto, nada pasa
	}

	// --- Métodos abstractos que las subclases DEBEN implementar ---

	/**
	 * Realiza la lógica de actualización del objeto para un ciclo.
	 */
	public abstract void update();
	
	/**
	 * Devuelve el icono que representa este objeto.
	 */
	public abstract String toString();

	
	public Position getPos() {
		return pos;
	}
	
	public Position clonePos() {
		return new Position(pos.getRow(), pos.getCol());
	}
}