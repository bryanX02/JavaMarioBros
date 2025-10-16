package tp1.logic;

import java.util.ArrayList;

public class ActionList extends ArrayList<Action> {
	
	
//	 Tipos de acciones
//	Las acciones disponibles están definidas en el enum Action y controlan tanto el movimiento de Mario como su icono:
//
//	LEFT / RIGHT: Mario cambia su dirección horizontal a la indicada y avanza un paso en esa dirección. Estas acciones actualizan el icono de Mario según la dirección y permiten reemplazar la dirección STOP previamente asignada.
//	UP: Mario asciende una casilla verticalmente. No altera la dirección horizontal ni el icono; solo afecta la posición vertical.
//	DOWN: Si Mario está en el aire, cae hasta alcanzar un suelo sólido o salir del tablero. Si está en el suelo, se detiene horizontalmente, cambia su dirección a STOP y el icono se actualiza a 🧑 (doble si Mario es grande), indicando que está quieto horizontalmente.
//	STOP: Indica que Mario no tiene dirección horizontal activa. Es decir, paraliza a Mario. El icono mostrado es 🧑 (doble si Mario es grande). La dirección STOP solo cambia con acciones LEFT o RIGHT, actualizando el icono correspondiente. Si la acción es UP, Mario se mueve verticalmente, pero el icono no cambia. Si la acción es DOWN en el suelo, no hay cambios adicionales y el icono permanece 🧑.
//	Clase ActionList
//	La clase ActionList gestiona las acciones que el jugador añade para Mario:
//
//	Almacena internamente una lista de Action.
//	Permite acumular varias acciones para ejecutarlas secuencialmente en un mismo ciclo de juego.
//	Aplica restricciones para evitar combinaciones incoherentes. En todos los casos, las acciones adicionales no generan error, simplemente se ignoran. En concreto:
//	LEFT/RIGHT: si aparecen ambas, se mantiene la primera y la otra se ignora. Se permiten repeticiones de la misma dirección (p.ej. RIGHT RIGHT mueve dos pasos a la derecha), pero las combinaciones opuestas respetan la primera ocurrencia y tienen un máximo de 4 ejecuciones por turno.
//	UP/DOWN: si aparecen ambas, se mantiene la primera y la otra se ignora. Las acciones verticales (UP o DOWN) también tienen un máximo de 4 ejecuciones por turno; las adicionales se ignoran.

	private int count;
	private final int MAX_ACTIONS = 4;
	
	public ActionList() {
		super();
		count = 0;
	}
	
	// LEFT/RIGHT: si aparecen ambas, se mantiene la primera y la otra se ignora. Se permiten repeticiones de la misma dirección (p.ej. RIGHT RIGHT mueve dos pasos a la derecha), pero las combinaciones opuestas respetan la primera ocurrencia y tienen un máximo de 4 ejecuciones por turno.
	// UP/DOWN: si aparecen ambas, se mantiene la primera y la otra se ignora. Las acciones verticales (UP o DOWN) también tienen un máximo de 4 ejecuciones por turno; las adicionales se ignoran.
	public void addAction (Action action) {
		if (count < MAX_ACTIONS) {
			if (action == Action.LEFT || action == Action.RIGHT) {
				boolean hasOpposite = false;
				for (int i = 0; i < count; i++) {
					if ((this.get(i) == Action.LEFT && action == Action.RIGHT) ||
						(this.get(i) == Action.RIGHT && action == Action.LEFT)) {
						hasOpposite = true;
						break;
					}
				}
				if (!hasOpposite) {
					this.add(action);
				}
			} else if (action == Action.UP || action == Action.DOWN) {
				boolean hasOpposite = false;
				for (int i = 0; i < count; i++) {
					if ((this.get(i) == Action.UP && action == Action.DOWN) ||
						(this.get(i) == Action.DOWN && action == Action.UP)) {
						hasOpposite = true;
						break;
					}
				}
				if (!hasOpposite) {
					this.add(action);
				}
			} else if (action == Action.STOP) {
				this.add(action);
			}
		}
	}
	
	public ArrayList<Action> getActions() {
		ArrayList<Action> result = new ArrayList<Action>();
		System.arraycopy(this, 0, result, 0, count);
		return result;
	}
	
	public void clear() {
		super.clear();
		count = 0;
	}
	
	
	
	
	

}
