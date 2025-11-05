package tp1.control.commands;

import tp1.logic.Game;
import tp1.logic.GameModel;
import tp1.view.GameView;

public class UpdateCommand extends NoParamsCommand {

	private static final String NAME = "update";
	private static final String SHORTCUT = "u";
	private static final String DETAILS = "[u]pdate | \"\"";
	private static final String HELP = "user does not perform any action";

	public UpdateCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public void execute(GameModel game, GameView view) {
		game.update();
		view.showGame(); // Repintamos el tablero
	}

	@Override
	public Command parse(String[] commandWords) {
		if (commandWords.length == 1) {
			// Caso especial: "" (Intro) también es update
			if (commandWords[0].isEmpty() || matchCommandName(commandWords[0])) {
				return this;
			}
		}
		return null;
	}
}