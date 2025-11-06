package tp1.control.commands;

import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ResetCommand extends AbstractCommand {

	private static final String NAME = "reset";
	private static final String SHORTCUT = "r";
	private static final String DETAILS = "[r]eset [numLevel]";
	private static final String HELP = "reset the game to initial configuration if not numLevel else load the numLevel map";

	private String levelString; // Almacenamos el nivel como string

	public ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
		this.levelString = null;
	}
	
	// Constructor privado para la instancia parseada
	private ResetCommand(String level) {
		this();
		this.levelString = level;
	}

	@Override
	public void execute(GameModel game, GameView view) {
		
		boolean resetSuccess = false;
		
		if (levelString == null) {
			// Sin parámetro: resetea el nivel actual
			resetSuccess = game.reset(game.getLevel());
		} else {
			// Con parámetro: intenta parsear
			try {
				int level = Integer.parseInt(levelString);
				// Capturamos el resultado de reset()
				resetSuccess = game.reset(level); 
				if (!resetSuccess) {
					// Si devuelve false, mostramos el error que espera el test
					view.showError(Messages.INVALID_LEVEL_NUMBER); 
					return; // No repintamos
				}
			} catch (NumberFormatException e) {
				// Mantenemos el error específico de P1
				view.showError(Messages.LEVEL_NOT_A_NUMBER_ERROR);
				return; // No repintamos si hay error
			}
		}
		if (resetSuccess) {
			view.showGame(); 
		}
	}

	@Override
	public Command parse(String[] commandWords) {
		if (matchCommandName(commandWords[0])) {
			if (commandWords.length == 1) {
				// reset sin nivel
				return new ResetCommand(); 
			} 
			if (commandWords.length == 2) {
				// reset con nivel (como string)
				return new ResetCommand(commandWords[1]); 
			}
		}
		return null;
	}
}