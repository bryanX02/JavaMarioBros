package tp1;

import java.util.Locale;

import tp1.control.Controller;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.logic.GameStatus;
import tp1.view.ConsoleColorsView;
import tp1.view.ConsoleView;
import tp1.view.GameView;
import tp1.view.Messages;

public class Main {

	/**
	 * Entry point
	 * 
	 * @param args Arguments for the game.
	 */
	public static void main(String[] args) {
		// Required to avoid issues with tests
        Locale.of("es", "ES");
		
		try {
			
			int nLevel = 0;
			if (args.length != 0) nLevel = Integer.parseInt(args[0]);

            Game game = new Game(nLevel);
            
            GameModel modelGame = game;
            GameStatus statusGame = game;
            
            // 3. Mantenemos tu lógica de colores, pero pasamos la interfaz GameStatus
            GameView view = args.length > 1 ? new ConsoleView(statusGame) : new ConsoleColorsView(statusGame);
            
            // 4. Pasamos la interfaz GameModel al controlador
            Controller controller = new Controller(modelGame, view);
					
			controller.run();

		} catch (NumberFormatException e) {
			System.out.println(String.format(Messages.LEVEL_NOT_A_NUMBER_ERROR, args[0]));
		}
		
		/*
		 * DUDAS
		 * 
		 * Cuando un objeto se mueve y debajo no tiene suelo, cae en el mismo turno?
		 * Cuando un objeto se quiere mover y tiene a otro objeto en frente, se para cambiando su direccion, y se mueve en el mismo turno o el siguiente?
		 */
	}
}
