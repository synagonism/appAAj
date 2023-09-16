/*
 * Parser_SSemasia.java - A TREE-BASED xml-parser of an xml-SSemasia.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2004 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Generic Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package pk_SSemasia;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Vector;

import pk_Util.Util;

/**
 * A TREE-BASED xml-parser which
 * TAKES an xml-SSemasia
 * and CREATES a java-SSemasia (the tree).<p>
 *
 * The code is:<code><br/>
 * Parser_SSemasia parser = new Parser_SSemasia();<br/>
 * FileReader reader = new FileReader("filename.xml");<br/>
 * SSmNode jSSemasia = parser.parseFromReader(reader);</code><p>
 *
 * The code is hacked from Marc De Scheemaecker's NanoXML 2 Lite.
 *
 * @modified 2004.07.06
 * @since 2004.06.14 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Parser_SSemasia
{

	/**
	 * The java-xml-element that the parser will create.
	 *
	 * @modified 2004.06.14
	 * @since 2004.06.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode outermostElement = new SSmNode("SSMA", null);


	/**
	 * Character read too much.<br/>
	 * This character provides push-back functionality to the input
	 * reader without having to use a PushbackReader.<b>
	 * If there is no such character, this field is '\u0000'.
	 */
	private char charReadTooMuch;


	/**
	 * The reader provided by the caller of the parse method.
	 */
	private Reader reader;


	/**
	 * The current line number in the source content.
	 */
	private int sourceLineNr=1;


	/**
	 * Parses an XML-SSemasia from a java.io.Reader.
	 *
	 * @param reader
	 *   The reader from which to retrieve the XML data.
	 * @throws java.io.IOException
	 *   If an error occured while reading the input.
	 * @modified 2004.11.17
	 * @since 2004.06.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode parseFromReader(Reader reader) throws IOException
	{
		this.charReadTooMuch = '\u0000';
		this.reader = reader;
		this.sourceLineNr = 1;

		Util.log(null);
		Util.log(null);
		Util.log("START-PARSING: SSemasiaChar");

		for (;;){
			char ch = this.scanWhitespace();
			if (ch == '\u0003')
				return outermostElement;//we reached end of text
			else if (ch != '<') {
					throw this.throwExpectedInput("<");
			}
			ch = this.scanChar();
			if (ch == '?') {
					this.skipXmlDeclaration();
			}else if (ch=='!'){
					this.skipComment();
			}else {
					this.scanCharBack(ch);
					this.scanElement(outermostElement);
					return outermostElement;
			}
		}
	}


	/**
	 * Parses an XML-SSemasia from a String.
	 *
	 * @param string
	 *   The string from which to retrieve the XML data.
	 * @modified 2004.11.17
	 * @since 2004.06.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode parseFromString(String string)
			throws Exception_SSemasia_Parsing
	{
			try {
				outermostElement = this.parseFromReader(new StringReader(string));
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			return outermostElement;
	}


	/**
	 * Parses an XML-SSemasia from a String.
	 *
	 * @param string
	 *   The String from which to retrieve the XML data.
	 * @param offset
	 *   The first character in <code>string</code> to scan.
	 * @modified 2004.11.17
	 * @since 2004.06.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode parseFromString(String string, int offset)
	{
			return this.parseFromString(string.substring(offset));
	}


	/**
	 * Parses an XML-SSemasia from a String.
	 *
	 * @param string
	 *   The String from which to retrieve the XML data.
	 * @param offset
	 *   The first character in <code>string</code> to scan.
	 * @param end
	 *   The character where to stop scanning.
	 *   This character is not scanned.
	 * @modified 2004.11.17
	 * @since 2004.06.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode parseFromString(String string,int offset,int end)
	{
			string = string.substring(offset, end);
			try {
				outermostElement = this.parseFromReader(new StringReader(string));
			} catch (IOException e) {
			}
			return outermostElement;
	}


	/**
	 * Parses an XML-SSemasia from a char-array.
	 *
	 * @param input
	 *   The char-array from which to retrieve the XML data.
	 * @param offset
	 *   The first character in <code>string</code> to scan.
	 * @param end
	 *   The character where to stop scanning.
	 *   This character is not scanned.
	 * @modified 2004.11.17
	 * @since 2004.06.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode parseFromCharArray(char[] input,int offset,int end)
	{
		try {
			Reader reader = new CharArrayReader(input, offset, end);
			outermostElement = this.parseFromReader(reader);
		} catch (IOException e) {
			// This exception will never happen.
		}
		return outermostElement;
	}


	/**
	 * Scans the data for literal text.<br/>
	 * Scanning stops when a character does not match or after
	 * the complete text has been checked, whichever comes first.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>literal != null</code>
	 *
	 * @param literal	The literal to check.
	 */
	protected boolean scanLiteral(String literal)	throws IOException
	{
		int length = literal.length();
		for (int i = 0; i < length; i += 1) {
			if (this.scanChar() != literal.charAt(i)) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Scans a char-XML-element.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - The first &lt; has already been read.<br/>
	 * - <code>elt != null</code>
	 *
	 * @param elt The java-element that will contain the result.
	 * @modified 2004.11.19
	 * @since 2004.07.02 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected void scanElement(SSmNode elt)
			throws IOException
	{
		StringBuffer buf = new StringBuffer();
		String name = scanIdentifier();
		elt.setName(name);
		char ch = this.scanWhitespace();
		String logValue="";

		while ((ch != '>') && (ch != '/')) {
			//found attributes
			this.scanCharBack(ch);
			String key = scanIdentifier();
			ch = this.scanWhitespace();
			if (ch != '=') {
					throw this.throwExpectedInput("=");
			}
			this.scanCharBack(this.scanWhitespace());
			String value = scanString();
			elt.setAttribute(key, value);
			ch = this.scanWhitespace();
			if (key.equals("XCPT"))
				logValue=value;
		}
		Util.log("\tscan-element: "+name +": "+logValue);
		//scan-element: SmVerb: Naming@hknu.symb-17

		//found / or >, empty-element OR subelements
		if (ch == '/') {
			ch = this.scanChar();
			if (ch != '>') {
				throw this.throwExpectedInput(">");
			}
			return;
		}

		//found >, subelements found.
		ch = this.scanWhitespace();
		if (ch != '<')
		{
			throw this.throwExpectedInput("<");
		}

		ch = this.scanChar();
		while (ch != '/') {
			if (ch == '!') {
				this.skipComment();
			} else {
				this.scanCharBack(ch);
				SSmNode child = new SSmNode(null, elt);
				this.scanElement(child);
				try {
					elt.addChild(child);
				} catch (Exception_SSemasia_Operating eo){}
			}
			ch = this.scanWhitespace();
			if (ch != '<') {
				throw this.throwExpectedInput("<");
			}
			ch = this.scanChar();
		}

		this.scanCharBack(this.scanWhitespace());
		if (! this.scanLiteral(name)) {
			throw this.throwExpectedInput(name);
		}
		if (this.scanWhitespace() != '>') {
			throw this.throwExpectedInput(">");
		}
	}


	/**
	 * Scans an unique-term from the current reader.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>result != null</code><br/>
	 * - The next character read from the reader is a valid first
	 *   character of an XML unique-term.<p>
	 *
	 * <b>Postconditions:</b><br/>
	 * - The next character read from the reader won't be an unique-term
	 *   character.
	 *
	 * @return The unique-term as a java-string.
	 * @author HoKoNoUmo
	 */
	protected String scanIdentifier() throws IOException
	{
			StringBuffer result = new StringBuffer();
			for (;;) {
					char ch = this.scanChar();
					//stops when finds not a "character".
					if (((ch < 'A') || (ch > 'Z')) && ((ch < 'a') || (ch > 'z'))
							&& ((ch < '0') || (ch > '9')) && (ch != '_') && (ch != '.')
							&& (ch != ':') && (ch != '-') && (ch <= '\u007E')) {
							this.scanCharBack(ch);
							return result.toString();
					}
					result.append(ch);
			}
	}


	/**
	 * This method scans a delimited string from the current reader.
	 * The scanned string without delimiters is returned.
	 *
	 */
	protected String scanString()
			throws IOException
	{
			StringBuffer sb = new StringBuffer();
			char delimiter = this.scanChar();
			if (delimiter != '"') {
					throw this.throwExpectedInput("\"");
			}
			for (;;) {
					char ch = this.scanChar();
					if (ch == delimiter) {
							return sb.toString();
					} else {
							sb.append(ch);
					}
			}
	}


	/**
	 * This method scans whitespace from the current reader.
	 *
	 * @return The next character following the whitespace.
	 */
	protected char scanWhitespace()	throws IOException
	{
		for (;;) {
			char ch = this.scanChar();
			switch (ch) {
				case ' ':
				case '\t':
				case '\n':
				case '\r':
					break;
				default:
					return ch;
			}
		}
	}


	/**
	 * This method scans whitespace from the current reader AND
	 * appendeds it to a string-buffer.
	 *
	 * @return The next character following the whitespace.
	 * @param result	The string-buffer that gets the whitespace.
	 * <b>Preconditions:</b> <code>result != null</code>
	 */
	protected char scanWhitespace(StringBuffer result) throws IOException
	{
		for (;;) {
			char ch = this.scanChar();
			switch (ch) {
				case ' ':
				case '\t':
				case '\n':
					result.append(ch);
					break;
				case '\r':
					break;
				default:
					return ch;
			}
		}
	}


	/**
	 * Skips a comment &lt;!-- data --&gt;<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - The first "&lt;!" has already been read.
	 */
	protected void skipComment()
			throws IOException
	{
			this.scanChar();
			if (this.scanChar() != '-') {
					throw this.throwExpectedInput("-, in <!--");
			}
			int dashesToRead = 2;
			while (dashesToRead > 0) {
					char ch = this.scanChar();
					if (ch == '-') {
							dashesToRead -= 1;
					} else {
							dashesToRead = 2;
					}
			}
			if (this.scanChar() != '>') {
					throw this.throwExpectedInput(">");
			}
	}


	/**
	 * Skips the xml-declaration &lt;?xml version... ?&gt;.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 *  - The first &lt;? has already been read.<br/>
	 */
	protected void skipXmlDeclaration()
			throws IOException
	{
			char ch = this.scanChar();
			while (ch!='?') {
					ch = this.scanChar();
			}
			if (this.scanChar() != '>') {
					throw this.throwExpectedInput(">");
			}
	}


	/**
	 * Reads a character from a reader.
	 */
	protected char scanChar()
			throws IOException
	{
			//first check if the character is already read
			if (this.charReadTooMuch != '\u0000') {
					char ch = this.charReadTooMuch;
					this.charReadTooMuch = '\u0000';
					return ch;

			} else {
					int i = this.reader.read();
					if (i < 0) {
						return '\u0003';
//							throw this.throwUnexpectedEndOfData();
					} else if (i == 10) {
							this.sourceLineNr += 1;
							return '\n';
					} else {
							return (char) i;
					}
			}
	}


	/**
	 * Pushes a character back to the read-back buffer.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - The read-back buffer is empty.<br/>
	 * - <code>ch != '\u0000'</code>
	 *
	 * @param ch The character to push back.
	 */
	protected void scanCharBack(char ch)
	{
		this.charReadTooMuch = ch;
	}


	/**
	 * Creates a parse exception IF an invalid value is given to a
	 * method.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>name != null</code>
	 * - <code>value != null</code>
	 *
	 * @param name  The name of the entity.
	 * @param value The value of the entity.
	 */
	protected Exception_SSemasia_Parsing throwInvalidValue(String name,String value)
	{
			String msg = "Attribute \"" + name + "\" does not contain a valid "
								 + "value (\"" + value + "\")";
			return new Exception_SSemasia_Parsing(outermostElement.getName(), this.sourceLineNr, msg);
	}


	/**
	 * Creates a parse exception IF the end of the data input has been
	 * reached.
	 */
	protected Exception_SSemasia_Parsing throwUnexpectedEndOfData()
	{
			String msg = "Unexpected end of data reached";
			return new Exception_SSemasia_Parsing(outermostElement.getName(), this.sourceLineNr, msg);
	}


	/**
	 * Creates a parse exception IF a syntax error occured.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>context != null</code><br/>
	 * - <code>context.length() &gt; 0</code>
	 *
	 * @param context The context in which the error occured.
	 */
	protected Exception_SSemasia_Parsing throwSyntaxError(String context)
	{
			String msg = "Syntax error while parsing " + context;
			return new Exception_SSemasia_Parsing(outermostElement.getName(), this.sourceLineNr, msg);
	}


	/**
	 * Creates a parse exception IF the next character read is not
	 * the character that was expected.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>charSet != null</code><br/>
	 * - <code>charSet.length() > 0</code>
	 *
	 * @param charSet
	 *  The set of characters (in human readable form) that was expected.
	 */
	protected Exception_SSemasia_Parsing throwExpectedInput(String charSet)
	{
			String msg = "Expected: " + charSet;
			return new Exception_SSemasia_Parsing(outermostElement.getName(), this.sourceLineNr, msg);
	}


}
