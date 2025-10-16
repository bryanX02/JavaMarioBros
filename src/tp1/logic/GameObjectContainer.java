package tp1.logic;

import java.util.ArrayList;
import java.util.List;

import tp1.logic.gameobjects.ExitDoor;
import tp1.logic.gameobjects.GameObject;
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
	

	/**
	 * Devuelve una cadena con los iconos de TODOS los objetos en una posición.
	 */
	public String getObjectsToStringAt(Position pos) {
		StringBuilder sb = new StringBuilder();

		if (mario != null && mario.isOnPosition(pos)) {
			sb.append(mario.toString());
		}
		for (Goomba goomba : goombaList) {
			if (goomba.isAlive() && goomba.isOnPosition(pos)) {
				sb.append(goomba.toString());
			}
		}
		if (exit != null && exit.isOnPosition(pos)) {
			sb.append(exit.toString());
		}

		// El suelo solo se añade si la celda está vacía de otros objetos
		if (sb.length() == 0) {
			for (Land land : landList) {
				if (land.isOnPosition(pos)) {
					sb.append(land.toString());
					break; 
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Devuelve el primer objeto sólido (Land) en una posición.
	 * Se usa para las comprobaciones de colisión.
	 */
	public GameObject getSolidObjectAt(Position pos) {
		for (Land land : landList) {
			if (land.isOnPosition(pos)) {
				return land;
			}
		}
		return null;
	}
	
	// métodos sobrecargados que servirán para añadir los diferentes tipos de objeto al juego
	public void add(Land land) {
		
		landList.add(land);
		
	}
	public void add(Goomba goomba) {
		
		goombaList.add(goomba);
		
	}
	public void add(ExitDoor exit) {
		
		this.exit = exit;
		
	}
	public void add(Mario mario) {
		
		this.mario = mario;
		
	}


//	El método marioExited() será el encargado de actualizar el estado de la partida, sumando a los puntos del jugador el valor resultante de la multiplicación entre el tiempo restante y 10. Además, marcará que la partida ha finalizado en victoria, mostrando por consola el mensaje Thanks, Mario! Your mission is complete..
//
//	La comprobación de esta interacción se realizará automáticamente en cada ciclo de actualización del juego, dentro de GameObjectContainer.update(). Para ello, será necesario recorrer todas las instancias de ExitDoor (en caso de existir varias; si solo hay una, se comprobará únicamente esa) y verificar si se produce la colisión con Mario.
//
//	Ahora, el flujo del update de Game cambiaría ligeramente:
//	GameObjectContainer.update()
//    ├─> Mario.update() ──> ejecutar acciones y/o movimiento automático
//    ├─> checkMarioinExit ──> comprobar si Mario colisiona con alguna puerta de salida
//    └─> for g in Goombas:
//           g.update() ──> movimiento automático y caída
	public void update() {
		
		
		// Actualiza a Mario
		mario.update();
		
		// Comprueba si Mario est� en la puerta de salida. No podemos usar 
		if (!mario.interactWith(exit)) {
			// Actualiza a los Goombas
			goombaList.forEach(g -> g.update());
		}
		
		// Comprueba colisiones de Goombas con Mario (después de que los Goombas se muevan)
		doInteractionsFrom(mario);
		
		// Elimina los Goombas muertos
		cleanup();
		
	}
	
	/**
	 * Realiza las interacciones de Mario con todos los Goombas
	 */
	public void doInteractionsFrom(Mario mario) {
		for (Goomba goomba : goombaList) {
			if (goomba.isAlive()) {
				mario.interactWith(goomba);
			}
		}
	}
	
	/**
	 * Elimina los objetos muertos del contenedor.
	 */
	private void cleanup() {
		goombaList.removeIf(g -> !g.isAlive());
	}


	public void remove(Goomba goomba) {
		goombaList.remove(goomba);
		
	}

}
