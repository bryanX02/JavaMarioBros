package tp1.control.commands;

import java.util.Arrays;
import java.util.List;

import tp1.view.Messages;

public class CommandGenerator {

	// Añadimos todos los comandos a la lista
	private static final List<Command> availableCommands = Arrays.asList(
			new ActionCommand(),
			new UpdateCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new ExitCommand()
	);

	public static Command parse(String[] commandWords) {	
		// Tratamos el array vacío o nulo
		if (commandWords == null || commandWords.length == 0) {
			commandWords = new String[]{ "" }; // Lo convertimos en "update"
		}
		
		for (Command c: availableCommands) {
			Command parsedCommand = c.parse(commandWords);
			if (parsedCommand != null) {
				return parsedCommand;
			}
		}
		return null;
	}
		
	public static String commandHelp() {
		StringBuilder commands = new StringBuilder();
		commands.append(Messages.HELP_AVAILABLE_COMMANDS).append(Messages.LINE_SEPARATOR);
		
		for (Command c: availableCommands) {
			// Usamos el método helpText() de AbstractCommand
			commands.append(c.helpText());
		}
		
		return commands.toString();
	}
}