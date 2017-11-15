import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.stream.Stream;

public class ESPInterpreter {
	private Variable [] variable_table;
	private ErrorHandler error_handler;
	private String[] program;
	private String programName;
	private Scanner userInput;
	
	public ESPInterpreter() {
		variable_table = new Variable['z' + 1];
		for (int i = 0; i <= 'z'; i++) {
			variable_table[ i ] = new Variable();
		}
		error_handler = new ErrorHandler();
		userInput = new Scanner(System.in);
		programName = null;
	}

	// The error handler for the interpreter.
	// A inner class is used here as we need access
	// to many of the private variables of the interpreter
	public class ErrorHandler {
		/**
		 * Outputs the error of the line
		 * in a human readable form
		 */
		public void outputErr(int lineNum, ESPException e) {
			boolean isSyntaxError = e instanceof InvalidSyntaxException;

			System.out.println("Line " + (lineNum + 1) + ": "
			+ program[lineNum] + '\n'
			+ ((isSyntaxError) ? "Syntax error: " : "") 
			+ e.getClass().getName() + ": " + e.getMessage()
			);
		}
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
			programName = f.getName();
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
		int interpDepth = 0; // the depth the interpreter is in the code (ex. a line with a tab is depth of 1)
		try {
			while (lineNum < program.length) {
				String line = program[lineNum];
				ESPStatement lineType;
				int lineDepth = 0;
				// Calculate depth of line
				for (int i = 0; i < line.length(); i ++) {
					if (line.charAt(i) != ' ')
						break;
					lineDepth ++;
				}

				// If this line is lower depth than the interpreter,
				// than we must be out of our current code block now
				// so set our depth to the lines current depth
				if (interpDepth > lineDepth)
					interpDepth = lineDepth;
				else if (interpDepth < lineDepth) {
				// Else if the line number is deeper than our interpreters
				// depth, than we are not in that code block, so don't read it
					lineNum ++;
					continue;
				}

				line = line.substring(interpDepth);
				lineType = ESPStatement.getType(line);

				switch(lineType) {
				case INPUT: {
					// Allow user to input an integer and assign its value
					// to the desired variable
					inputInteger(line.charAt(5));
					break;
				}
				case ASSIGNMENT: {
					// ex. x = 5 * 2
					// assign the evaluation of a infix expression to the variable v
					Variable v = variable_table[line.charAt(0)];
					String postfix = Expression.convertToPostFix(line.substring(4));
					int result = Expression.evalPostfix(postfix, variable_table);
					v.setValue(result);
					break;
				}
				case OUTPUT: {
					// ex. print x
					// outputs the infix expression to the screen
					String postfix = Expression.convertToPostFix(line.substring(6));
					int result = Expression.evalPostfix(postfix, variable_table);
					System.out.println(result);
					break;
				}
				case IF: {
					// ex. if ( x + 5 ) > 5
					// runs the statements inside the if statement (lines that are tabbed in)
					String statement = line.substring(3);
					boolean result = Expression.evalInfixComparison(statement, variable_table);
					// If the IF statement came out to be true, go inside that code block
					if (result == true)
						interpDepth += 4;
				}
				}

				lineNum ++;
			}
		}
		catch (ESPException e) {
			// Pass any ESP error to the error handler
			error_handler.outputErr(lineNum, e);
		}
		finally {
			userInput.close();
		}
	}

	// Gets user input and sets it to the specified index in the variable table
	private void inputInteger(int varIndex) throws VariableNameException {
		if (varIndex < 0 || varIndex > variable_table.length)
			throw new ArrayIndexOutOfBoundsException("Variable table index out of bounds: " + varIndex);
		
		Variable v = variable_table[varIndex];
		boolean isValid = false;
		System.out.println("Enter an integer number for variable: ");

		// Loop until user entered a valid integer
		while (!isValid) {
			if (userInput.hasNextInt()) {
				int val = userInput.nextInt();
				v.setValue(val);
				isValid = true;
			}
			else {
				// Clear input
				userInput.nextLine();
				System.out.println("Please enter a valid integer number: ");
			}
		}
	}
	
	/**
	 * Gets the value of a variable in the variable table
	 */
	public int getValueOf(char variable) throws UndefinedVariableException, VariableNameException {
		return variable_table[variable].getValue();
	}

	public static void main( String [ ] args ) {
		// args[0] will be the name of input file, for example text.esp
		System.out.println("The input file is " + args[0]);
		ESPInterpreter interp = new ESPInterpreter();

		interp.load(new File(args[0]));
		interp.execute();
		System.out.println("Done.");
	}

}
