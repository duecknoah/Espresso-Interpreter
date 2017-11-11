import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

public class ESPInterpreter {
	private Variable [] variable_table;
	private ESPErrorHandler error_handler;
	private String[] program;
	
	public ESPInterpreter() {
		variable_table = new Variable['z'];
		for (int i = 0; i < 'z'; i++) {
			variable_table[ i ] = new Variable();
		}
		error_handler = new ESPErrorHandler();
	}
	
	/*
	 * This is just a dummy method to show you how to use class 
	 * Variable, set a value for a variable, and handle the exception
	 * if tried to use an unknown variable
	 */
	public void ShowHowToUseVariableClass() {
		char c = 'F';
		variable_table[c].setValue(10);
		
		try {
			int value = variable_table[c].getValue();
			System.out.println("variable " +c +" is: " + value);
		} catch (UndefinedVariableException e) {
			System.out.println("error: variable " +c +" is not defined.");
		}
	}

	// Loads the given file into the interpreter
	public void load(File f) {
		// Attempt to open the file
		Stream<String> stream_data = null;
		try {
			stream_data = Files.lines(f.toPath());
			program = stream_data.toArray(String[]::new);
		}
		catch (FileNotFoundException e) {
			System.out.println("The file " + f + " doesn't exist.");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			// Prevent resource leaks by closing the BufferedReader
			// if it is open
			if (stream_data != null)
				stream_data.close();
		}
	}

	// Executes the loaded program
	public void execute() {
		if (program == null) {
			System.out.println("No program loaded.");
			return;
		}

		int lineNum = 0;
		while (lineNum < program.length) {
			System.out.println(program[lineNum]);
			lineNum ++;
		}
	}
	
	public static void main( String [ ] args ) {
		// args[0] will be the name of input file, for example text.esp
		System.out.println("The input file is " + args[0]);
		ESPInterpreter interp = new ESPInterpreter();

		System.out.println(Expression.convertToPostFix("x % 2"));
		interp.load(new File(args[0]));
		interp.execute();
		System.out.println("Done.");
	}

}
