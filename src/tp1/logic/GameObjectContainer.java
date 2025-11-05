package tp1.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tp1.logic.gameobjects.GameObject;
import tp1.logic.gameobjects.Mario;

public class GameObjectContainer {
	
	// Una única lista para todos los objetos
	private List<GameObject> gameObjects;
	
	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
	}

	// Método 'add' único
	public void add(GameObject obj) {
		gameObjects.add(obj);
	}
	
	public String getObjectsToStringAt(Position pos) {
		// Filtra la lista de gameObjects y une sus 'toString'
		String cellContent = gameObjects.stream()
            .filter(obj -> obj.isOnPosition(pos) && obj.isAlive())
            .map(GameObject::toString)
            .collect(Collectors.joining("")); // Une los iconos
		
		
		return cellContent;
	}

	/**
	 * Devuelve el primer objeto sólido en una posición.
	 * Ahora usa el método polimórfico isSolid()
	 */
	public GameObject getSolidObjectAt(Position pos) {
		for (GameObject obj : gameObjects) {
			if (obj.isAlive() && obj.isOnPosition(pos) && obj.isSolid()) {
				return obj;
			}
		}
		return null;
	}
	
	private GameObject getMario() {
		for (GameObject obj : gameObjects) {
			// Tenemos que encontrar a Mario
			if (obj instanceof Mario) {
				return obj;
			}
		}
		return null;
	}
	
	/**
	 * Bucle de actualización polimórfico
	 */
	public void update() {
		// 1. Actualiza todos los objetos
		for (GameObject obj : gameObjects) {
			if (obj.isAlive()) {
				obj.update();
			}
		}
		
		// 2. Realiza interacciones (usando la lógica de P1)
		Mario mario = (Mario)getMario();
		if (mario != null && mario.isAlive()) {
			doInteractionsFrom(mario);
		}
		
		// 3. Limpia objetos muertos
		cleanup();
	}
	
	/**
	 * Realiza interacciones polimórficas (Double-Dispatch)
	 */
	public void doInteractionsFrom(Mario mario) {
		for (GameObject obj : gameObjects) {
			if (obj.isAlive() && obj != mario) {
				// 1. Mario comprueba si choca con obj
				// 1. Mario (collider) comprueba si choca con obj (collidee)
	            // Llama a obj.receiveInteraction(mario)
	            mario.interactWith(obj); 
	            
	            // 2. Obj (collidee) comprueba si choca con Mario (collider)
	            // Llama a mario.receiveInteraction(obj)
	            obj.interactWith(mario);
			}
		}
	}
	
	private void cleanup() {
		// Elimina objetos muertos de la lista
		gameObjects.removeIf(obj -> !obj.isAlive());
	}

	public void remove(GameObject obj) {
		gameObjects.remove(obj);
	}
}