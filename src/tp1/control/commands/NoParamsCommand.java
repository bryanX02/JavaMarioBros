package tp1.control.commands;

public abstract class NoParamsCommand extends AbstractCommand {

	public NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
	}

	@Override
	public Command parse(String[] commandWords) {
		if (commandWords.length == 1) {
			if (matchCommandName(commandWords[0])) {
				// Si coincide y no tiene parámetros, devolvemos esta instancia
				return this; 
			}
		}
		return null;
	}
}
