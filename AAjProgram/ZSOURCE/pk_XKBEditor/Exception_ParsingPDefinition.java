package pk_XKBEditor;

import javax.swing.JOptionPane;

/**
 * An Exception_ParsingPDefinition is thrown when an error occures while
 * parsing a parenthesis-definition.
 *
 * @modified 2009.10.12
 * @since 2009.10.10 (v00.02.01)
 * @author HoKoNoUmo
 */
 public class Exception_ParsingPDefinition extends RuntimeException {
	static final long serialVersionUID = 21L;

	/**
	 * Creates an exception.
	 *
	 * @param lineNr	The number of the line in the input.
	 * @param message A message describing what went wrong.
	 */
	public Exception_ParsingPDefinition(int lineNr, String message)	{
		super("Parse Exception during parsing of parenthesis-definition"
					+ " at line " + lineNr + ": " + message);
		JOptionPane.showMessageDialog(null, "Parse Exception during parsing of parenthesis-definition"
					+ " at line " + lineNr + ": " + message);
	}

}
