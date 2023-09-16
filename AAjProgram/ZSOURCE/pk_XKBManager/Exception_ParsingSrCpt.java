package pk_XKBManager;

/**
 * An Exception_ParsingSrCpt is thrown when an error occures while
 * parsing an XML-CPT.
 */
public class Exception_ParsingSrCpt
		extends RuntimeException
{
static final long serialVersionUID = 21L;

		/**
		 * Creates an exception.
		 *
		 * @param name
		 *		The name of the element where the error is located.
		 * @param message
		 *		A message describing what went wrong.
		 *
		 */
		public Exception_ParsingSrCpt(String name,String message){
				super("XML Parse Exception during parsing of "
							+ name + " element"
							+ ": " + message);
		}


		/**
		 * Creates an exception.
		 *
		 * @param name		The name of the element where the error is located.
		 * @param lineNr	The number of the line in the input.
		 * @param message A message describing what went wrong.
		 *
		 */
		public Exception_ParsingSrCpt(String name,
														 int		lineNr,
														 String message)
		{
				super("XML Parse Exception during parsing of "
							+ name + " element"
							+ " at line " + lineNr + ": " + message);
		}


}
