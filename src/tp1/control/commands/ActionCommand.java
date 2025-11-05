package tp1.control.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tp1.logic.Action;
import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.view.GameView;
import tp1.view.Messages;

public class ActionCommand extends AbstractCommand {

	private static final String NAME = "action";
	private static final String SHORTCUT = "a";
	private static final String DETAILS = "[a]ction [[R]IGHT | [L]EFT | [U]P | [D]OWN | [S]TOP]+";
	private static final String HELP = "user performs actions";
	
	private List<String> actionStrings;

	public ActionCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	
	private ActionCommand(List<String> actions) {
		this();
		this.actionStrings = actions;
	}

	@Override
	public void execute(GameModel game, GameView view) {
		// Replicamos la lógica del P1 Controller
		if (actionStrings == null || actionStrings.isEmpty()) {
			view.showError(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			return; // No repintar
		}

		boolean invalidActionFound = false;
		for (String actionStr : actionStrings) {
			Action action = Action.parse(actionStr);
			if (action != null) {
				game.addAction(action);
			} else {
				invalidActionFound = true;
			}
		}

		if (invalidActionFound) {
			game.clearActions(); // Limpiamos acciones si una fue inválida
		}
		
		game.update();
		view.showGame(); // Repintamos después de la acción
	}

	@Override
	public Command parse(String[] commandWords) {
		if (matchCommandName(commandWords[0])) {
			if (commandWords.length > 1) { 
				List<String> actions = new ArrayList<>(Arrays.asList(commandWords).subList(1, commandWords.length));
				return new ActionCommand(actions);
			}
			// Si es solo "action", se ignora y devuelve null (activando "Unknown command")
		}
		return null;
		
	}
}