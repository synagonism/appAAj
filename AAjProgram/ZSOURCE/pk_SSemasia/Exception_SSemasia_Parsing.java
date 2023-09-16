package pk_SSemasia;


import javax.swing.JOptionPane;
/**
 * A Exception_SSemasia_Parsing is thrown when an error occures
 * while parsing an XML-SSMA.
 * @modified 2004.09.08
 * @since 2004.06.14 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Exception_SSemasia_Parsing
	extends RuntimeException
{
	static final long serialVersionUID = 21L;


	/**
	 * Creates an exception.
	 *
	 * @param name
	 * 		The name of the element where the error is located.
	 * @param message
	 * 		A message describing what went wrong.
	 *
	 */
	public Exception_SSemasia_Parsing(String name,String message)
	{
		super("XML Parse Exception during parsing of "
			  + name + " element"
			  + ": " + message);
		JOptionPane.showMessageDialog(null, "XML Parse Exception during parsing of "
			  + name + " element"
			  + ": " + message);
	}


	/**
	 * Creates an exception.
	 *
	 * @param name	The name of the element where the error is located.
	 * @param lineNr  The number of the line in the input.
	 * @param message A message describing what went wrong.
	 *
	 */
	public Exception_SSemasia_Parsing(String name, int	lineNr,
																 String message)
	{
		super("XML Parse Exception during parsing of "
			  + name + " element"
			  + " at line " + lineNr + ": " + message);
		JOptionPane.showMessageDialog(null, "XML Parse Exception during parsing of "
			  + name + " element"
			  + " at line " + lineNr + ": " + message);
	}

}
