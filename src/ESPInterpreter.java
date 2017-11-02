import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ESPInterpreter {
	private Variable [] variable_table;
	
	public ESPInterpreter() { //This is just an example, not a complete constructor
		variable_table = new Variable['z'];
		for (int i = 0; i < 'z'; i++) {
			variable_table[ i ] = new Variable();
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
		} catch (UndefinedVariableException E) {
			System.out.println("error: variable " +c +" is not defined.");
		}
		
		
	}

	// Executes the given file
	public void execute(File f) {
		// Attempt to open the file
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(f));	

			// Go through line by line, check for errors
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}
			
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
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Returns true if the given char 'v' exists
	 * in the variable table
	 */
	public boolean existsInVariableTable(char v) {
		if ()
	}
	
	public static void main( String [ ] args ) {
		// args[0] will be the name of input file, for example text.esp
		System.out.println("The input file is " + args[0]);
		ESPInterpreter interp = new ESPInterpreter();
		interp.execute(new File(args[0]));
		System.out.println("Done.");
	}

}
