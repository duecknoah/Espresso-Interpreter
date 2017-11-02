
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
	
	public static void main( String [ ] args ) {
		// args[0] will be the name of input file, for example text.esp
		System.out.println("The input file is" + args[0]);
		ESPInterpreter sample = new ESPInterpreter();
		sample.ShowHowToUseVariableClass();
	}

}
