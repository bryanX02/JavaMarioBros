package tp1.control;

import tp1.logic.Game;
import tp1.view.GameView;

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
		
		view.showWelcome();
		
		do {
			
			view.showGame();
			
			String[] command = view.getPrompt();
			
			switch (command[0].toLowerCase()) {
				case "h":
				case "help":
					view.showHelp();
					break;
				case "e":
				case "exit":
					view.showMessage("Player leaves game.");
					exit = true;
					break;
				case "r":
				case "reset":
					if (command.length > 1) {
						try {
							int level = Integer.parseInt(command[1]);
							if (!game.reset(level)) {
								view.showError("Error: No such level: " + level);
							}
						} catch (NumberFormatException e) {
							view.showError("Error: Invalid level: " + command[1]);
						}
					} else {
						game.reset(0);
					}
					break;
				case "a":
				case "action":
					if (command.length > 1) {
						for (int i = 1; i < command.length; i++) {
							switch (command[i].toLowerCase()) {
								case "r":
								case "right":
									//game.playerAction(Game.Action.RIGHT);
									break;
								case "l":
								case "left":
									//game.playerAction(Game.Action.LEFT);
									break;
								case "u":
								case "up":
									//game.playerAction(Game.Action.UP);
									break;
								case "d":
								case "down":
									//game.playerAction(Game.Action.DOWN);
									break;
								case "s":
								case "stop":
									//game.playerAction(Game.Action.STOP);
									break;
								default:
									view.showError("Error: Unknown action: " + command[i]);
									break;
								}
						}
						//game.update();
					} else {
						view.showError("Error: No actions provided.");
					}
					break;
				case "u":
				case "update":
				case "":
					game.update();
					break;
				default:
					view.showError("Error: Unknown command: " + command[0]);
					break;
			
			}
			/*if (game.isGameOver()) {
				view.showGame(game);
				if (game.playerWins()) {
					view.showMessage("Player wins!");
				} else {
					view.showMessage("Game over!");
				}
				exit = true;	
				
			}*/
			
		} while (!exit);
				
			
			
	}

}
