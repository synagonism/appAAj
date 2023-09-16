package pk_SSemasia;


import javax.swing.JOptionPane;
/**
 * A Exception_SSemasia_Operating is thrown when an error occures
 * while operating a java-SSMA.
 * @modified 2004.07.18
 * @since 2004.06.14 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Exception_SSemasia_Operating
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
	public Exception_SSemasia_Operating(String message)
	{
		super(message);
		JOptionPane.showMessageDialog(null, message);
	}

}
