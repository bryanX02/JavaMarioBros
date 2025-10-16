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
	

	// Funcion que devuelve el objeto de la lista que tenga la posici�n dada por par�metro
	public GameObject getObjFromPos(Position pos) {
		
		// Variables
		GameObject object = null;
		boolean encontrado = false;
		int i = 0;
		
		// Se busca el land en la posicion, si no lo encuentra se recibe un null
		while (i < landList.size() && !encontrado) {
			
			if (landList.get(i).isOnPosition(pos))
				encontrado = true;
			else
				i++;
		}
		
		if (encontrado)
			object = landList.get(i);
		else {
			
			i=0;
			while (i < goombaList.size() && !encontrado) {
				
				if (goombaList.get(i).isOnPosition(pos))
					encontrado = true;
				else
					i++;
			}
			
			if (encontrado)
				object = goombaList.get(i);
		}
		
		if (mario.isOnPosition(pos))
			object = mario;
		else if (exit.isOnPosition(pos))
			object = exit;
		
		
		
		return object;
		
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


	public void remove(Goomba goomba) {
		goombaList.remove(goomba);
		
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
		
		// Comprueba si Mario est� en la puerta de salida
		if (!mario.interactWith(exit)) {
			// Actualiza a los Goombas (no podemos usar foreach porque podemos eliminar goombas)
			for (int i = 0; i < goombaList.size(); i++) {
				goombaList.get(i).update();
			}
		}
		
		
		
	}

}
