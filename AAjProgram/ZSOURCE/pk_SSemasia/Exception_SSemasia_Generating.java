package pk_SSemasia;

import javax.swing.JOptionPane;

/**
 * A Exception_SSemasia_Generating is thrown when an error occures
 * while generating a SBCSSemasia.
 * @modified 2004.09.08
 * @since 2004.09.08 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Exception_SSemasia_Generating
	extends RuntimeException
{
	static final long serialVersionUID = 21L;

	/**
	 * Creates an exception.
	 *
	 * @param message
	 * 		A message describing what went wrong.
	 *
	 */
	public Exception_SSemasia_Generating(String message)
	{
		super(message);
		JOptionPane.showMessageDialog(null, message);
	}

}
