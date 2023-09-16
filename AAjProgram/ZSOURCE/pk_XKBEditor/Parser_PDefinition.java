/*
 * Parser_PDefinition.java - A string-parser that parses parethesis-definitions.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2009 Nikos Kasselouris
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA	02111-1307, USA.
 */
package pk_XKBEditor;

import java.io.IOException;
import java.io.StringReader;
import javax.swing.JOptionPane;


/**
 * A string-parser that parses parethesis-definitions
 * and maps them to xml-definitions.<p>
 *
 * CODE:<code><br/>
 * Parser_PDefinition parser = new Parser_PDefinition(pDef);<br/>
 * String xDef = parser.xDefinition;</code><p>
 *
 * @modified
 * @since 2009.10.08 (v00.02.01)
 * @author HoKoNoUmo
 */
public class Parser_PDefinition
{
//	<REFINO FRnAME="un1">
//	<XCPT FRnAME="un2"/>
//	<REFINO FRnAME="un3">
//	<XCPT FRnAME="un4"/>
//	<XCPT FRnAME="un5"/>
//	</REFINO>
//	</REFINO>
//	([un1] (un2) ([un3](un4)(un5)))


	/** The xml-definition created after parsing the
	 * parethesis-definition.
	 */
	public String xDefinition="";

	/**
	 * Holds a character read, but NOT understood.
	 * If there is no such character, this field is '\u0000'.
	 */
	private char charAlreadyRead;

	/** Holds the parenthesis-string we want to parse.*/
	private StringReader reader;

	/**
	 * Holds line number in the source we are reading.*/
	private int sourceLineNr=1;


	/**
	 * Creates and initializes a parser.<br/>
	 *
	 * @modified 2009.10.12
	 * @since 2009.10.08 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public Parser_PDefinition(String pDef) throws IOException {
		charAlreadyRead = '\u0000';
		reader = new StringReader(pDef);
		sourceLineNr = 1;
		//ALWAYS we read 1 argument.
		char ch = scanWhitespace();
		if (ch == '\u0003')
			return;//we reached end of text
		else if (ch != '(')
			throw throwExpectedInput("(");
		else {
			scanArgument();
		}
	}


	/**
	 * Scans a parenthesis-definition.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - The first "(" has already been read.<br/>
	 * - The argument != null
	 *
	 * @modified
	 * @since 2009.10.10 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	protected void scanArgument() throws IOException {
		char ch = scanWhitespace();
		if (ch != '['){
			//we reached an arg with only a unique-name
			scanCharBack(ch);
			String unm = scanUniqueName();
			ch = scanWhitespace();
			if (ch != ')')
					throw throwExpectedInput(")");
			//make xml-element
			xDefinition = xDefinition
				+"\n<XCPT FRnAME=\""+unm+"\"/>";
		} else {
			String rl = scanRelation();
			//make xml-element
			xDefinition = xDefinition
				+"\n<RELATION FRnAME=\""+rl+"\"/>";
			//one or more args after a relation
			while ((ch=scanWhitespace()) != ')'){
				if (ch != '(')
						throw throwExpectedInput("(");
				scanArgument();
			}
			//found ) end of argument
			xDefinition = xDefinition
				+"\n</RELATION>";

		}
	}


	/**
	 * Scans a unique-name (a formal-name or special-names)
	 * of xcpts from the current reader.<p>
	 *
	 * @return The unique-name as a java-string.
	 * @modified 2009.10.12
	 * @since 2009.10.12 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	protected String scanUniqueName() throws IOException {
		StringBuffer result = new StringBuffer();
		char ch=' ';
		for (;;) {
			ch = scanChar();
			//BConcept@hknu.symb-7#1@
			//stops when finds  a non-unique-name-character.
			if (		 ((ch >= 'A')	&& (ch <= 'Z'))
					|| ((ch >= 'a') && (ch <= 'z'))
					|| ((ch >= '0') && (ch <= '9'))
					|| (ch == '_')
					|| (ch == '.')
					|| (ch == '-')
					|| (ch == '@')
					|| (ch == '#')) {
					result.append(ch);
			} else
				break;
		}
		scanCharBack(ch);
		return result.toString();
	}


	/**
	 * Returns the unique-name of a relation.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - The first "[" has already been read.<br/>
	 * - The relation != null
	 *
	 * @modified 2009.10.10
	 * @since 2009.10.10 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	protected String scanRelation() throws IOException {
		char ch = scanWhitespace();
		scanCharBack(ch);
		String nm = scanUniqueName();
		ch = scanWhitespace();
		if (ch != ']')
			throw throwExpectedInput("]");
		return nm;
	}


	/**
	 * This method scans whitespace from the current reader.
	 *
	 * @return The next character following the whitespace.
	 */
	protected char scanWhitespace() throws IOException {
		for (;;) {
			char ch = scanChar();
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
	 * Reads a character from a reader.
	 *
	 * @modified 2009.10.10
	 * @since 2009.10.10 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	protected char scanChar() throws IOException {
		//first check if the character is already read
		if (charAlreadyRead != '\u0000') {
			char ch = charAlreadyRead;
			charAlreadyRead = '\u0000';
			return ch;
		} else {
			int i = reader.read();
//System.out.println("read: "+(char)i);
			if (i < 0) {
				return '\u0003';//end of text
			} else if (i == 10) {
				sourceLineNr += 1;
				return '\n';
			} else {
				return (char) i;
			}
		}
	}


	/**
	 * Holds the character we have already read, from previous action.
	 *
	 * @modified
	 * @since 2009.10.12 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	protected void scanCharBack(char ch) {
		charAlreadyRead = ch;
	}


	/**
	 * Creates a parse exception for when a syntax error occured.
	 *
	 * @param context The context in which the error occured.
	 * @modified 2009.10.12
	 * @since 2009.10.12 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	protected Exception_ParsingPDefinition throwSyntaxError(String context)
	{
		String msg = "Syntax error while parsing " + context;
		return new Exception_ParsingPDefinition(sourceLineNr, msg);
	}


	/**
	 * Creates a parse exception for when the next character read is not
	 * the character that was expected.<p>
	 *
	 * @param charSet	The string of characters that was expected.
	 * @modified 2009.10.12
	 * @since 2009.10.12 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	protected Exception_ParsingPDefinition throwExpectedInput(String charSet)
	{
		String msg = "Expected: " + charSet;
		return new Exception_ParsingPDefinition(sourceLineNr, msg);
	}


}
