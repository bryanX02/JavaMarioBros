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
            
            // ahora pasamos la interfaz GameStatus
            GameView view = args.length > 1 ? new ConsoleView(statusGame) : new ConsoleColorsView(statusGame);
            
            // y la interfaz GameModel al controlador
            Controller controller = new Controller(modelGame, view);
					
			controller.run();

		} catch (NumberFormatException e) {
			System.out.println(String.format(Messages.LEVEL_NOT_A_NUMBER_ERROR, args[0]));
		}
		
		/*
		 * DUDAS
		 * 
		 * Sigo sin entender el fallo del test 06. Creo que el expected es incorrecto.
		 */
	}
}
