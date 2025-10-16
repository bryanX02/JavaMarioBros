package tp1.control;

import tp1.logic.Action;
import tp1.logic.Game;
import tp1.view.GameView;
import tp1.view.Messages;

/**
 *  Accepts user input and coordinates the game execution logic
 */
public class Controller {

	private Game game;
	private GameView view;

	public Controller(Game game, GameView view) {
		this.game = game;
		this.view = view;
	}


	/**
	 * Runs the game logic, coordinate Model(game) and View(view)
	 * 
	 */
	/*
	 * Una vez que ya sabemos dibujar el tablero, vamos a empezar con los primeros comandos. Los comandos serán los siguientes:

[h]elp: Este comando solicita a la aplicación que muestre la ayuda relativa a cómo utilizar los comandos. Se mostrará una línea por cada comando. Cada línea tiene el nombre del comando seguida por ':' y una breve descripción de lo que hace el comando.
Command > help

Available commands:
   [a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+: user performs actions
   [u]pdate | "": user does not perform any action
   [r]eset [numLevel]: reset the game to initial configuration if not numLevel else load the numLevel map
   [h]elp: print this help message
   [e]xit: exits the game
[e]xit: Este comando permite salir de la aplicación, mostrando previamente el mensaje Player leaves game.
[r]eset [numLevel]: este comando permitirá el reinicio de la partida, con el nivel indicado en [numLevel]. Si el nivel no existe, deberá mostrar el mapa del nivel actual.
[a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+: este comando permitirá al usuario realizar una acción.
[u]pdate | "": se actualiza el juego (el usuario no realiza ninguna acción).
La aplicación debe permitir comandos escritos en minúsculas, mayúsculas o mezcla de ambas y la aplicación debe permitir el uso de la primera letra del comando (o la indicada entre corchetes, si esa letra ya se utiliza) en lugar del comando completo. Por otro lado, si el comando está vacío se identifica como [u]pdate y si el usuario ejecuta un comando que no cambia el estado del juego, o un comando erróneo, el tablero no se debe repintar. Además, si el comando está mal escrito, no existe, o no se puede ejecutar, la aplicación mostrará el siguiente mensaje de error Error: Unknown command: comandoTecleadoPorElUsuario.

En primer lugar, implementa el comando exit y haz que el juego termine cuando se muestre este comando. Posteriormente, realiza el comando help, que muestre este mensaje. Los comandos deberán añadirse en un bucle dentro de la clase Controller.

Posteriormente, tenemos que añadir el comando reset[numLeve]. Se llevará a cabo el reseteo de todo el tablero y del time, que también volverá a su valor inicial. No obstante, tanto el total de puntos como el número de vidas se mantienen igual que estaban (no sufren el efecto del comando).

Los comandos [a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+ y [u]pdate | "" se explicarán en las siguientes secciones.
	 */
	public void run() {
		boolean exit = false;
		// Variable para controlar cuándo se debe pintar el tablero
		boolean pintarTablero = true; 
		boolean invalidAction = false;

		view.showWelcome();

		do {
			// Solo pintamos el tablero si la variable de control es true
			if (pintarTablero) {
				view.showGame();
			}
			// Por defecto, no se repintará en el siguiente ciclo
			pintarTablero = false; 

			String[] command = view.getPrompt();

			// Comando como help, exit o reset no pueden tener parámetros adicionales. Se debe lanzar un error si los tienen
			switch (command[0].toLowerCase()) {
				case "h":
				case "help":
					if (command.length > 1) {
						view.showError(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
						break;
					} else 
						view.showHelp();
					break;
				case "e":
				case "exit":
					if (command.length > 1) {
						view.showError(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
						break;
					}else {
						view.showMessage(Messages.PLAYER_QUITS);
						exit = true;
					}
					break;
				case "r":
				case "reset":
					if (command.length > 1) {
						try {
							int level = Integer.parseInt(command[1]);
							game.reset(level);
						} catch (NumberFormatException e) {
							view.showError(Messages.LEVEL_NOT_A_NUMBER_ERROR);
						}
					} else {
						game.reset(game.getLevel()); // Resetea al nivel actual
					}
					// Un reset siempre repinta el tablero
					pintarTablero = true;
					break;
				case "a":
				case "action":
					if (command.length > 1) {
						for (int i = 1; i < command.length; i++) {
							Action action = Action.parse(command[i]);
							if (action != null) {
								game.addAction(action);
								
								
							} else {
								view.showError(Messages.UNKNOWN_ACTION.formatted(command[i]));
								invalidAction = true;
							}
						}
						
						if (invalidAction) {
							game.clearActions(); // Limpiamos las acciones si hubo alguna inválida
						} 
						game.update();
						// El update solo se ejecuta si la acción es válida
						
						pintarTablero = true;
						
					} else {
						view.showError(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
				
					}
					break;
				case "u":
				case "update":
				case "":
					game.update();
					// Un update siempre repinta el tablero
					pintarTablero = true;
					break;
				default:
					// Un comando desconocido no repinta el tablero
					view.showError(Messages.UNKNOWN_COMMAND.formatted(command[0]));
					break;
			}

			if (game.isFinished()) {
				// Al finalizar, mostramos el tablero final una última vez
				view.showGame(); 
				if (game.playerWins()) {
					view.showMessage(Messages.MARIO_WINS);
				} else {
					view.showMessage(Messages.GAME_OVER);
				}
				exit = true;
			}

			invalidAction = false;
		} while (!exit);
	}

}
