/*
 * Parser_XCpt.java - An event-based xml-parser that parses AAj's-xcpts.
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA	02111-1307, USA.
 */
package pk_XKBManager;

import java.awt.Color;
import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.EmptyStackException;
import java.util.Vector;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.JOptionPane;
import javax.swing.tree.DefaultMutableTreeNode;

import pk_Util.Extract;
import pk_Util.Util;
import pk_Util.Textual;
import pk_Logo.Exception_Logo_GeneratingText;
import pk_SSemasia.Exception_SSemasia_Operating;

/**
 * An EVENT-BASED xml-PARSER that parses AAj's-xcpts.
 * Also, it is an event-PROCESSOR that presents the xConcepts.<p>
 *
 * CODE:<code><br/>
 * Parser_XCpt parser = new Parser_XCpt();<br/>
 * FileReader reader = new FileReader("filename.xml");<br/>
 * parser.parseFromReader(reader, fileName);</code><p>
 *
 * The parser-code is hacked from Marc De Scheemaecker's NanoXML 2 Lite.
 *
 * @modified 2005.09.08
 * @since 2004.07.02 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Parser_XCpt
{
	private Stack<String> stackParent = new Stack<String>();
	private String nameParent = null;
	private String nameParent2 = "noparent2";
	private String nameParent3 = "noparent3";
	private boolean rightInt=false;
	private String fileName="";

	/**
	 * Character read too much.
	 * This character provides push-back functionality to the input reader
	 * without having to use a PushbackReader.
	 * If there is no such character, this field is '\u0000'.
	 */
	private char charReadTooMuch;

	/**
	 * The reader provided by the caller of the parse method.
	 *
	 * <b>Invariants:</b><br/>
	 *	- The field is not <code>null</code> while the parse method
	 *		is running.
	 *
	 */
	private Reader reader;

	/**
	 * The current line number in the source content.
	 *
	 * <b>Invariants:</b><br/>
	 *	- sourceLineNr &gt;0 while the parse method is running.
	 *
	 */
	private int sourceLineNr=1;


	/**
	 * Creates and initializes a parser.
	 */
	public Parser_XCpt()
	{
	}


	/**
	 * Reads one XML element from a java.io.Reader and parses it.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 *	 - <code>reader != null</code><br/>
	 *	 - <code>reader</code> is not closed<p>
	 *
	 * <b>Postconditions:</b><br/>
	 *	 - the state of the receiver is updated to reflect the XML
	 *		 element parsed from the reader<br/>
	 *	 - the reader points to the first character following the last
	 *		 '&gt;' character of the XML element
	 *
	 * @param reader
	 *	 The reader from which to retrieve the XML data.
	 * @param flName	The name of the file of the xcpt.
	 * @throws java.io.IOException
	 *	 If an error occured while reading the input.
	 * @throws Exception_ParsingSrCpt
	 *	 If an error occured while parsing the read data.
	 */
	public void parseFromReader(Reader reader, String flName)
			throws IOException, Exception_ParsingSrCpt
	{
		charReadTooMuch = '\u0000';
		this.reader = reader;
		fileName = Util.getXCpt_sLastFileName(flName);
		sourceLineNr = 1;

		Util.log(null);
		Util.log("START-PARSING: AAj-SCONCEPT:");

		for (;;){
			char ch = scanWhitespace();
			if (ch == '\u0003')
				return;//we reached end of text
			else if (ch != '<') {
				throw throwExpectedInput("<");
			}
			ch = scanChar();
			if (ch == '?') {
				skipXmlDeclaration();
			}else if (ch=='!'){
				skipComment();
			}else {
				scanCharBack(ch);
				stackParent.push("noparent");
				stackParent.push("noparent2");
				stackParent.push("noparent3");
				scanElement();
			}
		}
	}


	/**
	 * Reads one XML element from a String and parses it.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 *	 - <code>string != null</code><br/>
	 *	 - <code>string.length() &gt; 0</code><p>
	 *
	 * <b>Postconditions:</b><br/>
	 *	 - the state of the receiver is updated to reflect the XML element
	 *		 parsed from the reader
	 *
	 * @throws Exception_ParsingSrCpt
	 *	 If an error occured while parsing the string.
	 */
	public void parseFromString(String string)
			throws Exception_ParsingSrCpt
	{
			try {
				this.parseFromReader(new StringReader(string),null);
			} catch (IOException e) {
					// Java exception handling suxx
			}
	}


	/**
	 * Reads one XML element from a String and parses it.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>string != null</code><br/>
	 * - <code>offset &lt; string.length()</code><br/>
	 * - <code>offset &gt;= 0</code><p>
	 *
	 * <b>Postconditions:</b><br/>
	 * - the state of the receiver is updated to reflect the XML element
	 *	 parsed from the reader
	 *
	 * @param offset
	 *	 The first character in <code>string</code> to scan.
	 * @throws Exception_ParsingSrCpt
	 *	 If an error occured while parsing the string.
	 */
	public void parseFromString(String string, int offset)
			throws Exception_ParsingSrCpt
	{
			this.parseFromString(string.substring(offset));
	}


	/**
	 * Reads one XML element from a String and parses it.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>string != null</code><br/>
	 * - <code>end &lt;= string.length()</code><br/>
	 * - <code>offset &lt; end</code><br/>
	 * - <code>offset &gt;= 0</code><p>
	 *
	 * <b>Postconditions:</b><br/>
	 * - the state of the receiver is updated to reflect the XML element
	 *	 parsed from the reader
	 *
	 * @param offset
	 *	 The first character in <code>string</code> to scan.
	 * @param end
	 *	 The character where to stop scanning.
	 *	 This character is not scanned.
	 * @throws Exception_ParsingSrCpt
	 *	 If an error occured while parsing the string.
	 */
	public void parseFromString(String string,
													int offset,
													int end)
			throws Exception_ParsingSrCpt
	{
			string = string.substring(offset, end);
			try {
					this.parseFromReader(new StringReader(string),null);
			} catch (IOException e) {
					// Java exception handling suxx
			}
	}


	/**
	 * Reads one XML element from a char array and parses it.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 *	 - <code>input != null</code><br/>
	 *	 - <code>end &lt;= input.length</code><br/>
	 *	 - <code>offset &lt; end</code><br/>
	 *	 - <code>offset &gt;= 0</code><p>
	 *
	 * <b>Postconditions:</b><br/>
	 *	- the state of the receiver is updated to reflect the XML element
	 *		parsed from the reader
	 *
	 * @param offset
	 *	 The first character in <code>string</code> to scan.
	 * @param end
	 *	 The character where to stop scanning.<br/>
	 *	 This character is not scanned.
	 * @throws Exception_ParsingSrCpt
	 *	 If an error occured while parsing the string.
	 */
	public void parseFromCharArray(char[] input,
																int offset,
																int end)
			throws Exception_ParsingSrCpt
	{
			try {
					Reader reader = new CharArrayReader(input, offset, end);
					this.parseFromReader(reader,null);
			} catch (IOException e) {
					// This exception will never happen.
			}
	}


	/**
	 * Returns the text-content of the element.<p>
	 *
	 * <b>Precoditions:</b><br/>
	 * - the content does NOT contain the "&lt;" character.
	 *
	 * @modified 2004.07.04
	 * @since 2004.07.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected String scanContentText()
		throws IOException
	{
		StringBuffer data = new StringBuffer();
		for (;;) {
			char ch = scanChar();
			if (ch == '<') {
				scanCharBack(ch);
				return data.toString();
			} else {
				data.append(ch);
			}
		}
	}


	/**
	 * Scans a txt-XML-element.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - The first &lt; has already been read.<br/>
	 *
	 * @modified 2004.11.17
	 * @since 2004.07.02 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected void scanElement() throws IOException {
		StringBuffer buf = new StringBuffer();
		String nameElement = scanIdentifier();
		Hashtable<String,String> attributes = new Hashtable<String,String>();
		String strTContent="";
		char ch;
		nameParent = stackParent.peek();
		//Util.log("\tcpt-element: "+nameElement +", parent: "+nameParent);

		//these elements-with their children are processed separately
		//in content-method.
		if (nameElement.equals("SSMA")){
			strTContent=scanSSMA();
			processElementContent(nameParent, strTContent, null);
			return;//Begin another scanElement.
		}
		if (nameElement.equals("DEFINITION_GENERIC")){
			strTContent=scanDefinitionGeneric();
			processElementContent("DEFINITION_GENERIC", strTContent, nameParent);
			return;//Begin another scanElement.
		}
		if (nameElement.equals("DEFINITION_PART")){
			strTContent=scanDefinitionPart();
			processElementContent("DEFINITION_PART", strTContent, nameParent);
			return;//Begin another scanElement.
		}
		if (nameElement.equals("DEFINITION_RELATOR")){
			strTContent=scanDefinitionRelator();
			processElementContent("DEFINITION_RELATOR", strTContent, nameParent);
			return;//Begin another scanElement.
		}

		ch = scanWhitespace();
		while ((ch != '>') && (ch != '/')) {
			//found attributes
			scanCharBack(ch);
			String key = scanIdentifier();
			ch = scanWhitespace();
			if (ch != '=') {
				throw throwExpectedInput("=");
			}
			scanCharBack(scanWhitespace());
			attributes.put(key, scanDelimitedString());
			ch = scanWhitespace();
		}
		//done with attributes, then process the start of element.
		try {
			String p = stackParent.pop();//remove parent
			nameParent2 = stackParent.pop();//remove parent2
			nameParent3 = stackParent.peek();//read parent3
			stackParent.push(nameParent2);
			stackParent.push(p);
		} catch (EmptyStackException ese) {
			System.out.println(ese.toString());
		}
		processElementStart(nameElement, attributes, nameParent, nameParent2, nameParent3);

		//found / or >, empty-element OR subelements OR text-content
		//inside empty-element
		if (ch == '/') {//empty-element
			ch = scanChar();
			if (ch != '>') {
					throw throwExpectedInput(">");
			}
			processElementEnd(nameElement, nameParent);
		}

		else { //inside element's-content: subelements OR text-content OR comments
			ch = scanWhitespace();//< or letter

			if (ch != '<'){ //found text-content
				scanCharBack(ch);
				strTContent=scanContentText();
				processElementContent(nameElement,strTContent,nameParent);

				//inside: end-tag of element
				ch = scanWhitespace(); //THE < after content
				ch = scanWhitespace(); //THE /
				if (! scanLiteral(nameElement)) {
						throw throwExpectedInput(nameElement);
				}
				//Util.log("\t"+ch+nameElement);
				ch = scanWhitespace(); //THE >
				processElementEnd(nameElement, nameParent);
			}

			else { //ch==< inside subelement or comment
				stackParent.push(nameElement);
				ch = scanChar();
				while (ch != '/') {
					if (ch == '!') {
						skipComment();
					} else {
						scanCharBack(ch);
						scanElement();
					}
					ch = scanWhitespace();
					if (ch != '<' ){
						throw throwExpectedInput("<");
					}
					ch = scanChar();
				}

				//inside end-tag
				stackParent.pop();
				scanCharBack(scanWhitespace());
				if (! scanLiteral(nameElement)) {
						throw throwExpectedInput(nameElement);
				}
				Util.log("\t/"+nameElement);
				scanWhitespace();//the >
				processElementEnd(nameElement, nameParent);
			}
		}
	}


	/**
	 * Scans an unique-name from the current reader.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>result != null</code><br/>
	 * - The next character read from the reader is a valid first
	 *	 character of an XML unique-name.<p>
	 *
	 * <b>Postconditions:</b><br/>
	 * - The next character read from the reader won't be an unique-name
	 *	 character.
	 *
	 * @return The unique-name as a java-string.
	 * @author HoKoNoUmo
	 */
	protected String scanIdentifier()
			throws IOException
	{
			StringBuffer result = new StringBuffer();
			for (;;) {
					char ch = scanChar();
					//stops when finds not a "character".
					if (		((ch < 'A') || (ch > 'Z'))
								&& ((ch < 'a') || (ch > 'z'))
								&& ((ch < '0') || (ch > '9'))
								&& (ch != '_')
								&& (ch != '.')
								&& (ch != ':')
								&& (ch != '-')
								&& (ch <= '\u007E')) {
						scanCharBack(ch);
						return result.toString();
					}
					result.append(ch);
			}
	}


	/**
	 * Scans the data for literal text.<br/>
	 * Scanning stops when a character does not match or after
	 * the complete text has been checked, whichever comes first.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>literal != null</code>
	 *
	 * @param literal The literal to check.
	 */
	protected boolean scanLiteral(String literal)
			throws IOException
	{
			int length = literal.length();
			for (int i = 0; i < length; i += 1) {
					if (scanChar() != literal.charAt(i)) {
							return false;
					}
			}
			return true;
	}


	/**
	 *
	 * @modified 2009.11.02
	 * @since 2009.11.02 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	 protected String scanDefinitionGeneric()
		throws IOException
	{
		StringBuffer result = new StringBuffer();
		result.append("<DEFINITION_GENERIC");// we have read it.

		char c=scanChar();
		String sr;
		while (true){
			sr = result.toString();
			if (sr.endsWith("/DEFINITION_GENERIC")){
				break;
			}else{
				result.append(c);
				c=scanChar();
			}
		}
		result.append(c);//the last >.
		return result.toString();
	}

	/**
	 * Scans from reader the DEFINITION_PART element
	 * and returns it as String.<p>
	 *
	 * <b>Precodition:</b><br/>
	 * - The "&lt;DEFINITION_PART" has already been read.
	 *
	 * @modified 2008.10.16
	 * @since 2008.10.16 (v00.02.00)
	 * @author HokoYono
	 */
	protected String scanDefinitionPart()
		throws IOException
	{
		StringBuffer result = new StringBuffer();
		result.append("<DEFINITION_PART");// we have read it.

		char c=scanChar();
		String sr;
		while (true){
			sr = result.toString();
			if (sr.endsWith("/DEFINITION_PART")){
				break;
			}else{
				result.append(c);
				c=scanChar();
			}
		}
		result.append(c);//the last >.
		return result.toString();
	}

	/**
	 * Scans from reader the DEFINITION_RELATOR element
	 * and returns it as String.<p>
	 *
	 * <b>Precodition:</b><br/>
	 * - The "&lt;DEFINITION_RELATOR" has already been read.
	 *
	 * @modified 2009.11.12
	 * @since 2009.11.12 (v00.02.01)
	 * @author HokoYono
	 */
	protected String scanDefinitionRelator()
		throws IOException
	{
		StringBuffer result = new StringBuffer();
		result.append("<DEFINITION_RELATOR");// we have read it.

		char c=scanChar();
		String sr;
		while (true){
			sr = result.toString();
			if (sr.endsWith("/DEFINITION_RELATOR")){
				break;
			}else{
				result.append(c);
				c=scanChar();
			}
		}
		result.append(c);//the last >.
		return result.toString();
	}

	/**
	 * Scans from reader the SSMA (sensorial-semasia-subWorldview) element
	 * and returns it as String.<p>
	 *
	 * <b>Precodition:</b><br/>
	 * - The "&lt;SSMA" has already been read.
	 *
	 * @modified 2006.06.02
	 * @since 2004.07.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected String scanSSMA()
		throws IOException
	{
		StringBuffer result = new StringBuffer();
		result.append("<SSMA");// we have read it.

		char c=scanChar();
		String sr;
		while (true){
			sr = result.toString();
			if (sr.endsWith("/SSMA")){
				break;
			}else{
				result.append(c);
				c=scanChar();
			}
		}
/*
		char c1=scanChar();
		char c2=scanChar();
		char c3=scanChar();
		char c4=scanChar();
		char c5=scanChar();
		while (true){
			//wrkarnd
			if (c1=='/' && c2=='S' && c3=='S' && c4=='M' && c5=='A') { //SSMA!!!!!!!!
				break;
			}else{
				result.append(c1);
				c1=c2;
				c2=c3;
				c3=c4;
				c4=c5;
				c5=scanChar();
			}
		}
		c1=scanChar();//the last >.
		result.append("/SSMA>");
*/

		result.append(c);//the last >.

		return result.toString();
	}


	/**
	 * This method scans a delimited string from the current reader.
	 * The scanned string without delimiters is returned.
	 */
	protected String scanDelimitedString()
			throws IOException
	{
			StringBuffer sb = new StringBuffer();
			char delimiter = scanChar();
			if (delimiter != '"') {
					throw throwExpectedInput("\"");
			}
			for (;;) {
					char ch = scanChar();
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
	protected char scanWhitespace()
			throws IOException
	{
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
	 * This method scans whitespace from the current reader.
	 * The scanned whitespace is appended to <code>result</code>.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>result != null</code>
	 *
	 * @return The next character following the whitespace.
	 */
	protected char scanWhitespace(StringBuffer result)
			throws IOException
	{
		for (;;) {
			char ch = scanChar();
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
	 * Skips a comment: &lt;!-- data --&gt;<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - The first "&lt;!" has already been read.
	 */
	protected void skipComment()
			throws IOException
	{
			scanChar();
			if (scanChar() != '-') {
					throw throwExpectedInput("-, in <!--");
			}
			int dashesToRead = 2;
			while (dashesToRead > 0) {
					char ch = scanChar();
					if (ch == '-') {
							dashesToRead -= 1;
					} else {
							dashesToRead = 2;
					}
			}
			if (scanChar() != '>') {
					throw throwExpectedInput(">");
			}
	}


	/**
	 * Skips the xml-declaration &lt;?xml version... ?&gt;.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 *	- The first &lt;? has already been read.<br/>
	 *	- <code>bracketLevel >= 0</code>
	 */
	protected void skipXmlDeclaration()
			throws IOException
	{
			char ch = scanChar();
			while (ch!='?') {
					ch = scanChar();
			}
			if (scanChar() != '>') {
					throw throwExpectedInput(">");
			}
	}


	/**
	 * Reads a character from a reader.
	 */
	protected char scanChar()
			throws IOException
	{
			//first check if the character is already read
			if (charReadTooMuch != '\u0000') {
					char ch = charReadTooMuch;
					charReadTooMuch = '\u0000';
					return ch;

			} else {
					int i = this.reader.read();
					if (i < 0) {
						return '\u0003';//end of text
//						throw this.throwUnexpectedEndOfData();
					} else if (i == 10) {
							sourceLineNr += 1;
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
			charReadTooMuch = ch;
	}


	/**
	 * Creates a parse exception for when an invalid value is given to a
	 * method.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>name != null</code>
	 * - <code>value != null</code>
	 *
	 * @param name	The name of the entity.
	 * @param value The value of the entity.
	 */
	protected Exception_ParsingSrCpt throwInvalidValue(String name,
																								String value)
	{
			String msg = "Attribute \"" + name + "\" does not contain a valid "
								 + "value (\"" + value + "\")";
			return new Exception_ParsingSrCpt(name, sourceLineNr, msg);
	}


	/**
	 * Creates a parse exception for when the end of the data input has been
	 * reached.
	 */
	protected Exception_ParsingSrCpt throwUnexpectedEndOfData()
	{
			String msg = "Unexpected end of data reached";
			return new Exception_ParsingSrCpt("parsing xConcept", sourceLineNr, msg);
	}


	/**
	 * Creates a parse exception for when a syntax error occured.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>context != null</code><br/>
	 * - <code>context.length() &gt; 0</code>
	 *
	 * @param context The context in which the error occured.
	 */
	protected Exception_ParsingSrCpt throwSyntaxError(String context)
	{
			String msg = "Syntax error while parsing " + context;
			return new Exception_ParsingSrCpt("parsing bmsc", sourceLineNr, msg);
	}


	/**
	 * Creates a parse exception for when the next character read is not
	 * the character that was expected.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 * - <code>charSet != null</code><br/>
	 * - <code>charSet.length() &gt; 0</code>
	 *
	 * @param charSet
	 *	The set of characters (in human readable form) that was expected.
	 */
	protected Exception_ParsingSrCpt throwExpectedInput(String charSet)
	{
			String msg = "Expected: " + charSet;
			return new Exception_ParsingSrCpt("parsing xConcept", sourceLineNr, msg);
	}


//************************************************************************
// ELEMENT HANDLERS
//************************************************************************

	/**
	 * At element's START we do the followings:<br/>
	 * - at XCONCEPT:<b>
	 * - at Name_NounCase: <b>
	 * - at Name_Short: <b>
	 * - at REFINO_DEFINITION: <b>
	 * - at ANALYTIC: on file-xcpts, we write the "analytic-definition"
	 * in the attributes-area.<b>
	 * - at REFINO_PART: <b>
	 * - at XCPT: <b>
	 * - at INTxCPT: <b>
	 * - at REFINO_SPECIFICdIVISION: we add the "attribute" on which we create
	 * the division on toc.<b>
	 *
	 * @modified 2004.07.02
	 * @since 2004.07.02 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected void processElementStart(String elName,
																		Hashtable atts,
																		String parent, String parent2, String parent3)
	{
		if (elName.equals("XCONCEPT")){//at start
			if (!AAj.blReadInt){
				String integrated = (String)atts.get("INTEGRATED");
				String xcpt_sFormalID = Util.getXCpt_sFormalID(fileName);
				String xcpt_sFormalNumber = Util.getXCpt_sFormalNumber(fileName);
				//toDo: find FullNames
				AAj.xcpt_sLblSrWorldview.setText(Util.getXCpt_sFormalWorldviewFull(fileName)
						+" (" +Util.getXCpt_sFormalWorldview(fileName)+"),");
				String fv=Util.getXCpt_sFormalView(fileName);
				String swv=Util.getXCpt_sFormalSubWorldview(fileName);
				String fswv="";
				for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
				{
					if (i.next().equals(fv))
					{
						String[] ar = AAj.trmapSrBrSubWorldview.get(fv);
						fswv=ar[1];//directory.
						fswv=fswv.substring(fswv.lastIndexOf("/")+1);
						break;
					}
				}
				AAj.xcpt_sLblSrSubWorldview.setText(fswv+" ("+swv+")");
				if (integrated.equals("yes")){
					AAj.xcpt_sLblIntegrated.setText("(#" +xcpt_sFormalNumber +", "
						+AAj.rbLabels.getString("integrated") +", "
						+AAj.rbLabels.getString(Util.getLango3toNormal(AAj.lng)) +")");
					AAj.xcpt_sLblIntegrated.setForeground(Color.red);
				} else {
					AAj.xcpt_sLblIntegrated.setText("(#" +xcpt_sFormalNumber +", "
						+AAj.rbLabels.getString("nonIntegrated") +", "
						+AAj.rbLabels.getString(Util.getLango3toNormal(AAj.lng)) +")");
					AAj.xcpt_sLblIntegrated.setForeground(Color.red);
				}
				AAj.xcpt_sAuthor=(String)atts.get("AUTHOR");
				AAj.xcpt_sCreated=(String)atts.get("CREATED");
				AAj.xcpt_sModified=(String)atts.get("LASTmOD");
			}
		}

		else if (elName.equals("REFINO_DEFINITION")){ //at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				writeTextInDoc(AAj.rbLabels.getString("REFINO_DEFINITION"),AAj.s1);
				writeTextInDoc(":",AAj.s0);
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_DEFINITION"), String.valueOf(AAj.doc.getLength()));
			}
		}
		else if (elName.startsWith("DEFINITION_GENERIC")){//at start
			if (!AAj.blReadInt){
				String def="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText("<"+elName,AAj.lng,false);
				def = mdtt.txtDefinition;
				String cr= (String)atts.get("CREATION");
				String title= "";
				if (cr!=null){
					title = "\n" +AAj.rbLabels.getString("creation-definition")+"::"
						+AAj.rbLabels.getString("generic")+": ";
				} else {
					title = "\n"+AAj.rbLabels.getString("generic")+": ";
				}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), title, AAj.s2);
				}
				catch (BadLocationException eble){}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), def, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHDefGenEnd: ble");
				}
			}
		}
/*
		else if (elName.equals("DEFINITION_GENERIC_END")){//at start
			if (!AAj.blReadInt){
				String def="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText("<"+elName,AAj.lng,true);
				def = mdtt.txtDefinition;
				String cr= (String)atts.get("CREATION");
				String title= "";
				if (cr!=null){
					title = "\n" +AAj.rbLabels.getString("creation-definition")+"::"
						+AAj.rbLabels.getString("generic")+": ";
				} else {
					title = "\n"+AAj.rbLabels.getString("generic")+": ";
				}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), title, AAj.s2);
				}
				catch (BadLocationException eble){}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), def, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHDefGenEnd: ble");
				}
			}
		}
		else if (elName.equals("DEFINITION_SPECIFIC_START")){//at start
			if (!AAj.blReadInt){
				String def="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText("<"+elName,AAj.lng,true);
				def = mdtt.txtDefinition;
				String cr= (String)atts.get("CREATION");
				String title= "";
				if (cr!=null){
					title = "\n" +AAj.rbLabels.getString("creation-definition")+"::"
						+AAj.rbLabels.getString("specific")+": ";
				} else {
					title = "\n"+AAj.rbLabels.getString("specific")+": ";
				}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), title, AAj.s2);
				}
				catch (BadLocationException eble){}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), def, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHDefSpeStart: ble");
				}
			}
		}
*/
		else if (elName.equals("DEFINITION_ATTRIBUTE_START")){//at start
			if (!AAj.blReadInt){
				String def="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText("<"+elName,AAj.lng);
				def = mdtt.txtDefinition;
				//check if this is a creation-definition:
				String cr= (String)atts.get("CREATION");
				String title= "";
				if (cr!=null){
					title = "\n" +AAj.rbLabels.getString("creation-definition")+"::"
						+AAj.rbLabels.getString("attribute")+": ";
				} else {
					title = "\n"+AAj.rbLabels.getString("attribute")+": ";
				}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), title, AAj.s2);
				}
				catch (BadLocationException eble){}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), def, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHDefAttStart: ble");
				}
			}
		}

		else if (elName.equals("REFINO_NAME")){ //at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				writeTextInDoc("\n\n",AAj.s0);
				writeTextInDoc(AAj.rbLabels.getString("REFINO_NAME"),AAj.s1);
				writeTextInDoc(":",AAj.s0);
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_NAME"), String.valueOf(AAj.doc.getLength()));
			}
		}
		else if (elName.equals("kml")){ //at start
			if (!AAj.blReadInt && parent3.equals("noparent3")){
				StringWriter swriter = new StringWriter();
				swriter.write("\n" +AAj.rbLabels.getString("Komo") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s2);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHkml: ble");}
				catch (IOException ioe){System.out.println("EHkml: ioe");}
			}
		}
		else if (elName.equals("eng")){ //at start
			if (!AAj.blReadInt && parent3.equals("noparent3")){
				StringWriter swriter = new StringWriter();
				swriter.write("\n" +AAj.rbLabels.getString("English") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s2);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHEnglish: ble");}
				catch (IOException ioe){System.out.println("EHEnglish: ioe");}
			}
		}
		else if (elName.equals("eln")){ //at start
			if (!AAj.blReadInt && parent3.equals("noparent3")){
				StringWriter swriter = new StringWriter();
				swriter.write("\n" +AAj.rbLabels.getString("Greek") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s2);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHGreek: ble");}
				catch (IOException ioe){System.out.println("EHGreek: ioe");}
			}
		}
		else if (elName.equals("epo")){ //at start
			if (!AAj.blReadInt && parent3.equals("noparent3")){
				StringWriter swriter = new StringWriter();
				swriter.write("\n" +AAj.rbLabels.getString("Esperanto") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s2);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHEsperanto: ble");}
				catch (IOException ioe){System.out.println("EHEsperanto: ioe");}
			}
		}
		else if (elName.equals("Name_NounCase")){//at start
			// if we don't read internal AND parent3=XCONCEPT, display it.
			// >>>AND<<< in the correct internal.
			if (!AAj.blReadInt && parent3.equals("XCONCEPT")){
				//the parent-element and the AAj-lang must be the same.
				String tx_nounTerm = (String)atts.get("TxEXP");
				tx_nounTerm= tx_nounTerm.replace(' ','-');
				if (AAj.lng.equals(parent.toLowerCase())){
					//increase the name counter.
					AAj.intName=AAj.intName+1;
					//put the tx-noun on 'tx_nouns' tree-node.
					if (AAj.tocNodeNoun==null){
						//We use the FIRST 'tx_noun' for the concept's name. 2001.03.06
						AAj.xcpt_sLblNameCpt.setText(tx_nounTerm);
						AAj.tocNodeNoun = new DefaultMutableTreeNode(AAj.rbLabels.getString("tx_nouns"));
						AAj.tocModel.insertNodeInto(AAj.tocNodeNoun, AAj.tocNodePartName, AAj.tocNodePartName.getChildCount());
						AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(tx_nounTerm),
														AAj.tocNodeNoun, AAj.tocNodeNoun.getChildCount());
					} else
						AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(tx_nounTerm),
														AAj.tocNodeNoun, AAj.tocNodeNoun.getChildCount());
				}
				StringWriter swriter = new StringWriter();
				swriter.write("\n  * " +AAj.rbLabels.getString("TxNoun") +": "+tx_nounTerm);
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s0);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHNameNounCase: ble");}
				catch (IOException ioe){System.out.println("EHNameNounCase: ioe");}
			} else if (AAj.blReadInt && rightInt && parent3.equals("INTxCPT")){
				//display it, IF parent3=INTxCPT
				//AND it is the right internal
//				if (AAj.lng.equals(parent.toLowerCase())){
					//increase the name counter.
					AAj.intName=AAj.intName+1;
					String tx_nounTerm = (String)atts.get("TxEXP");
					tx_nounTerm= tx_nounTerm.replace(' ','-');
					//put the tx-noun on 'tx_nouns' tree-node.
					if (AAj.tocNodeNoun==null){
						//We use the FIRST 'tx_noun' for the concept's name. 2001.03.06
						AAj.xcpt_sLblNameCpt.setText(tx_nounTerm);
						AAj.tocNodeNoun = new DefaultMutableTreeNode(AAj.rbLabels.getString("tx_nouns"));
						AAj.tocModel.insertNodeInto(AAj.tocNodeNoun, AAj.tocNodePartName, AAj.tocNodePartName.getChildCount());
						AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(tx_nounTerm),
														AAj.tocNodeNoun, AAj.tocNodeNoun.getChildCount());
					} else
						AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(tx_nounTerm),
														AAj.tocNodeNoun, AAj.tocNodeNoun.getChildCount());
//				}
			}
		}
		else if (elName.equals("Name_Verb")){//at start
			if (!AAj.blReadInt && parent3.equals("XCONCEPT")){
//				if (AAj.lng.equals(parent.toLowerCase())){
					//increase the name counter.
					AAj.intName=AAj.intName+1;
					String tx_verbName = (String)atts.get("TxEXP");
					//put the tx_verb on 'tx_verbs' tree-node.
					if (AAj.tocNodeVrb==null){
						AAj.tocNodeVrb = new DefaultMutableTreeNode(AAj.rbLabels.getString("txVerbs"));
						AAj.tocModel.insertNodeInto(AAj.tocNodeVrb, AAj.tocNodePartName, AAj.tocNodePartName.getChildCount());
						AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(tx_verbName),
														AAj.tocNodeVrb, AAj.tocNodeVrb.getChildCount());
					} else AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(tx_verbName),
														AAj.tocNodeVrb, AAj.tocNodeVrb.getChildCount());
//				}
			}
		}
		else if (elName.equals("Name_NounAdjective")){//at start
			if (!AAj.blReadInt && parent3.equals("XCONCEPT")){
				String adjTerm = (String)atts.get("TxEXP");
				if (AAj.lng.equals(parent.toLowerCase())){
					//increase the name counter.
					AAj.intName=AAj.intName+1;
					//put the tx-nounAdjective on 'tx_nounAdjectives' tree-node.
					if (AAj.tocNodeAdj==null){
						AAj.tocNodeAdj = new DefaultMutableTreeNode(AAj.rbLabels.getString("tx_nounAdjectives"));
						AAj.tocModel.insertNodeInto(AAj.tocNodeAdj, AAj.tocNodePartName, AAj.tocNodePartName.getChildCount());
						AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(adjTerm),
																AAj.tocNodeAdj, AAj.tocNodeAdj.getChildCount());
					}
					else AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(adjTerm),
																AAj.tocNodeAdj, AAj.tocNodeAdj.getChildCount());
				}
				StringWriter swriter = new StringWriter();
				swriter.write("\n  * " +AAj.rbLabels.getString("NounAdjectiveTx") +": "+adjTerm);
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s0);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHNameNounCase: ble");}
				catch (IOException ioe){System.out.println("EHNameNounCase: ioe");}
			}
		}
		else if (elName.equals("Name_NounAdverb")){//at start
			if (!AAj.blReadInt && parent3.equals("XCONCEPT")){
				String advName = (String)atts.get("TxEXP");
				if (AAj.lng.equals(parent.toLowerCase())){
					//increase the name counter.
					AAj.intName=AAj.intName+1;
					//put the tx_adverb on 'tx_nounAdverbes' tree-node.
					if (AAj.tocNodeAdv==null){
						AAj.tocNodeAdv = new DefaultMutableTreeNode(AAj.rbLabels.getString("tx_adverbs"));
						AAj.tocModel.insertNodeInto(AAj.tocNodeAdv, AAj.tocNodePartName, AAj.tocNodePartName.getChildCount());
						AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(advName),
																	AAj.tocNodeAdv, AAj.tocNodeAdv.getChildCount());
					}
					else AAj.tocModel.insertNodeInto(new DefaultMutableTreeNode(advName),
																	AAj.tocNodeAdv, AAj.tocNodeAdv.getChildCount());
				}
				StringWriter swriter = new StringWriter();
				swriter.write("\n  * " +AAj.rbLabels.getString("NounAdverbTx") +": "+advName);
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s0);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHNameNounAdverb: ble");}
				catch (IOException ioe){System.out.println("EHNameNounAdverb: ioe");}
			}
		}
		else if(elName.equals("Name_Short")){ //at start
			if (!AAj.blReadInt && parent3.equals("XCONCEPT")){
				String shortName = (String)atts.get("TxEXP");
				if (AAj.lng.equals(parent.toLowerCase())){
					//increase the name counter.
					AAj.intName=AAj.intName+1;
					if (shortName!=null)
						AAj.xcpt_sLblNameCpt.setText(AAj.xcpt_sLblNameCpt.getText()
																			+" (" +shortName +")");
				}
			}
		}

		else if (elName.equals("REFINO_WHOLE")){ //at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeWho = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_WHOLE"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeWho, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_WHOLE") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHWholefino: ble");}
				catch (IOException ioe){System.out.println("EHWholefino: ioe");}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_WHOLE"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if(elName.equals("REFINO_PART")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodePart = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_PART"));
				AAj.tocModel.insertNodeInto(AAj.tocNodePart, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_PART") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),
						swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_PART"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if(elName.equals("REFINO_PARTcOMPLEMENT")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeParCmp = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_PARTcOMPLEMENT"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeParCmp, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("PARTcOMPLEMENT") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),
						swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("PARTcOMPLEMENT"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if(elName.equals("REFINO_GENERIC")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeGen = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_GENERIC"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeGen, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_GENERIC") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),
						swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_GENERIC"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if (elName.equals("REFINO_SPECIFIC")){ //at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeSpe = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_SPECIFIC"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeSpe, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_SPECIFIC") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHSpecifefino: ble");}
				catch (IOException ioe){System.out.println("EHSpecifefino: ioe");}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_SPECIFIC"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if (elName.equals("REFINO_SPECIFICdIVISION")){//at start
			//internals does not contain divisions.
			if (!AAj.blReadInt && parent3.equals("noparent3")){
				// Sets the node on viewer-tree: "Classification on: criterion (fid)"
				String flName = (String)atts.get("ATR");
				String lflName = Util.getXCpt_sLastFileName(flName);
				if (!flName.equals(lflName))
					AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
				String xcpt_sName = Util.createNodeName(flName);
				AAj.intDiv=AAj.intDiv+1;
				AAj.tocNodeSpeDiv = new DefaultMutableTreeNode(AAj.rbLabels.getString("SpecificDivisionOn") +xcpt_sName);
				AAj.tocModel.insertNodeInto(AAj.tocNodeSpeDiv, AAj.tocNodeSpe, AAj.tocNodeSpe.getChildCount());

				StringWriter swriter = new StringWriter();
				swriter.write("\n" +AAj.rbLabels.getString("SpecificDivisionOn") +xcpt_sName +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s2);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHDivizospecefefino: ble");}
				catch (IOException ioe){System.out.println("EHDivizospecefefino: ioe");}
			}
		}

		else if(elName.equals("REFINO_SPECIFICcOMPLEMENT")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeSpeCmp = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_SPECIFICcOMPLEMENT"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeSpeCmp, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("SPECIFICcOMPLEMENT") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),
						swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("SPECIFICcOMPLEMENT"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if (elName.equals("REFINO_ENVIRONMENT")){ //at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeEnv = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_ENVIRONMENT"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeEnv, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_ENVIRONMENT") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHWholefino: ble");}
				catch (IOException ioe){System.out.println("EHWholefino: ioe");}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_ENVIRONMENT"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if (elName.equals("REFINO")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeRef = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeRef, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO"), String.valueOf(AAj.doc.getLength()));
			} else if (!AAj.blReadInt && parent3.equals("noparent3")){
				// Sets the node on viewer-tree: "Classification on: criterion (fid)"
				String flName = (String)atts.get("FRnAME");
				String lflName = Util.getXCpt_sLastFileName(flName);
				if (!flName.equals(lflName))
					AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
				String xcpt_sName = Util.createNodeName(flName);
//				AAj.intDiv=AAj.intDiv+1;
				if (parent.equals("REFINO")){
					AAj.tocNodeRefRel = new DefaultMutableTreeNode(AAj.rbLabels.getString("Rel") +"::" +xcpt_sName);
					AAj.tocModel.insertNodeInto(AAj.tocNodeRefRel, AAj.tocNodeRef, AAj.tocNodeRef.getChildCount());
				} else if (parent.equals("REFINO_ATTRIBUTE")){
					AAj.tocNodeAttRel = new DefaultMutableTreeNode(AAj.rbLabels.getString("Rel") +"::" +xcpt_sName);
					AAj.tocModel.insertNodeInto(AAj.tocNodeAttRel, AAj.tocNodeAtt, AAj.tocNodeAtt.getChildCount());
				} else if (parent.equals("REFINO_NONaTTRIBUTE")){
					AAj.tocNodeNonAttRel = new DefaultMutableTreeNode(AAj.rbLabels.getString("Rel") +"::" +xcpt_sName);
					AAj.tocModel.insertNodeInto(AAj.tocNodeNonAttRel, AAj.tocNodeNonAtt, AAj.tocNodeNonAtt.getChildCount());
				} else {
					AAj.tocNodeEnvRel = new DefaultMutableTreeNode(AAj.rbLabels.getString("Rel") +"::" +xcpt_sName);
					AAj.tocModel.insertNodeInto(AAj.tocNodeEnvRel, AAj.tocNodeEnv, AAj.tocNodeEnv.getChildCount());
				}
				StringWriter swriter = new StringWriter();
				swriter.write("\n" +AAj.rbLabels.getString("Relation") +":: " +xcpt_sName +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s2);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHRefino: ble");}
				catch (IOException ioe){System.out.println("EHRefino: ioe");}
			}
		}

		else if(elName.equals("REFINO_ENTITY")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeEnt = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_ENTITY"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeEnt, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_ENTITY") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_ENTITY"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if(elName.equals("REFINO_ATTRIBUTE")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeAtt = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_ATTRIBUTE"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeAtt, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_ATTRIBUTE") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_ATTRIBUTE"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if(elName.equals("REFINO_NONaTTRIBUTE")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeNonAtt = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_NONaTTRIBUTE"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeNonAtt, AAj.root, AAj.root.getChildCount());
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_NONaTTRIBUTE") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_NONaTTRIBUTE"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if (elName.equals("REFINO_ARGUMENT")){//at start
			if (!AAj.blReadInt && parent.equals("XCONCEPT")){
				// Put the node on viewer's-toc
				AAj.tocNodeArg = new DefaultMutableTreeNode(AAj.rbLabels.getString("REFINO_ARGUMENT"));
				AAj.tocModel.insertNodeInto(AAj.tocNodeArg, AAj.root, AAj.root.getChildCount());
				// Put the node on viewer's-textPane
				StringWriter swriter = new StringWriter();
				swriter.write("\n\n" +AAj.rbLabels.getString("REFINO_ARGUMENT") +":");
				try {
					AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException eble){}
				catch (IOException ioe){}
				//store position
				AAj.hmapCaret.put(AAj.rbLabels.getString("REFINO_ARGUMENT"), String.valueOf(AAj.doc.getLength()));
			}
		}

		else if (elName.equals("XCPT")){//at start
			if (!AAj.blReadInt){

				if (parent.equals("REFINO_PART")){//parent of xcpt
					//toDo: Display the part-definition of extSrCpt. 2009.06.02
					//found a cptext inside a part of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					String att = Util.getXCpt_sLastFileName(flName);
					String attID = Util.getXCpt_sFormalID(att);
					if (!flName.equals(att))
						AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(att));
					String nodeName = Util.createNodeName(att);
					AAj.intPar=AAj.intPar+1;
					//the REFINO_GENERIC element-attribute denotes if it is inherited or not.

					//insert the node in the tree depending on its 'generic' value.
					String genValue = (String)atts.get("REFINO_GENERIC");
					String genCpt=null;				//the generic of this internal-xcpt.
					String inheritor=null;			//the inheritor of this internal-xcpt.
					String inheritorID=null;

					if (genValue==null)
					{
						//A NonInherited found
						AAj.intParNInh=AAj.intParNInh+1;
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
						AAj.tocModel.insertNodeInto(node, AAj.tocNodePart, AAj.tocNodePart.getChildCount());
					}
					else {
						//an Inherited found
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName+"(inh)");
						if (genValue.indexOf("/")!=-1)
						{
							AAj.intParInh=AAj.intParInh+1;
							AAj.tocModel.insertNodeInto(node, AAj.tocNodePart, AAj.tocNodePart.getChildCount());
						}
						else //iheritor is missing, isert as NonInherited
						{
							JOptionPane.showMessageDialog(null,"Part (" +flName +") is an iherited-part WITHOUT inheritor!!!");
							AAj.intParNInh=AAj.intParNInh+1;
							AAj.tocModel.insertNodeInto(node, AAj.tocNodePart, AAj.tocNodePart.getChildCount());
						}
					}

					StringWriter swriter = new StringWriter();
					swriter.write("\n" +Util.getXCpt_sName(att,AAj.lng));
					//store position
					AAj.hmapCaret.put(attID, String.valueOf(AAj.doc.getLength()));
					try {
						AAj.doc.insertString(AAj.doc.getLength(), swriter.toString(), AAj.s2);
						swriter.close();}
					catch (BadLocationException eble){}
					catch (IOException ioe){}
					//write the generic if exists. 2000.02.09
					if (genCpt!=null)
					{
						//toDo: I have to fix the Util.setLastCptNames 2000.11.13
			//			String lgName = Util.getXCpt_sLastFileName(genCpt);
			//			if (!genCpt.equals(lgName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lgName));
						String genName = Util.getXCpt_sName(genCpt,AAj.lng);
						StringWriter swriter3 = new StringWriter();
						swriter3.write(" (gen=" +genName +")");
						try {
							AAj.doc.insertString(AAj.doc.getLength(), swriter3.toString(), AAj.s0);
							swriter3.close();}
						catch (BadLocationException ble){System.out.println("EHCptextPar: ble 117");}
						catch (IOException ioe){System.out.println("EHCptextPar: ioe 118");}
					}
					// write the definition of this attribute
					Extract def = new Extract();
					try {
						def.extractDef(att);
					}
					catch (Exception ex)
					{
						JOptionPane.showMessageDialog(null, "EHCptextPar: There is NOT file for the ATTRIBUTE-sbcpt " +att);
					}
					String strDef = ":\n" +def.baos.toString();
					StringWriter swriter2 = new StringWriter();
					swriter2.write(strDef);
					try {
						AAj.doc.insertString(AAj.doc.getLength(), swriter2.toString(), AAj.s0);
						swriter2.close();}
					catch (BadLocationException ble){System.out.println("EHCptextPar: ble 137");}
					catch (IOException ioe){System.out.println("EHCptextPar: ioe 138");}
				}

				else if (parent.equals("REFINO_WHOLE")){//parent of xcpt
					//found a cptext inside a whole of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					String nodeName = "none";
					if (!flName.equals("none")){
						//set the LastFileName in displayed-sbcpt xml-file.
						String lflName = Util.getXCpt_sLastFileName(flName);
						if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
						nodeName = Util.createNodeName(flName);
					}
					AAj.intWho=AAj.intWho+1;
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeWho, AAj.tocNodeWho.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}

				else if (parent.equals("REFINO_PARTcOMPLEMENT")){//parent of xcpt
					//found a cptext inside a PartialComplement of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					String nodeName = "none";
					if (!flName.equals("none")){
						//set the LastFileName in displayed-sbcpt xml-file.
						String lflName = Util.getXCpt_sLastFileName(flName);
						if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
						nodeName = Util.createNodeName(flName);
					}
//					AAj.intGen=AAj.intGen+1;
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeParCmp, AAj.tocNodeParCmp.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}

				else if (parent.equals("REFINO_GENERIC")){//parent of xcpt
					//found a cptext inside a generic of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					String nodeName = "none";
					if (!flName.equals("none")){
						//set the LastFileName in displayed-sbcpt xml-file.
						String lflName = Util.getXCpt_sLastFileName(flName);
						if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
						nodeName = Util.createNodeName(flName);
					}
					AAj.intGen=AAj.intGen+1;
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeGen, AAj.tocNodeGen.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}

				else if (parent.equals("REFINO_SPECIFIC")){//parent of xcpt
					//found a cptext inside a specific of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					//set the LastFileName in displayed-sbcpt xml-file.
					String lflName = Util.getXCpt_sLastFileName(flName);
					if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
					String nodeName = Util.createNodeName(flName);
					//we count specifics separately.
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeSpe, AAj.tocNodeSpe.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}
				else if (parent.equals("REFINO_SPECIFICdIVISION")){//parent of xcpt
					//found a cptext inside a division of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					//set the LastFileName in displayed-sbcpt xml-file.
					String lflName = Util.getXCpt_sLastFileName(flName);
					if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
					String nodeName = Util.createNodeName(flName);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeSpeDiv, AAj.tocNodeSpeDiv.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}

				else if (parent.equals("REFINO_SPECIFICcOMPLEMENT")){//parent of xcpt
					//found a cptext inside a SpecicComplement of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					String nodeName = "none";
					if (!flName.equals("none")){
						//set the LastFileName in displayed-sbcpt xml-file.
						String lflName = Util.getXCpt_sLastFileName(flName);
						if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
						nodeName = Util.createNodeName(flName);
					}
//					AAj.intGen=AAj.intGen+1;
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeSpeCmp, AAj.tocNodeSpeCmp.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}

				else if (parent.equals("REFINO_ENVIRONMENT")){//parent of xcpt
					//found a cptext inside an environment of a file-xcpt
					String sbcpt = (String)atts.get("FRnAME");
					String xcpt_sFormalID = Util.getXCpt_sFormalID(sbcpt);
					String lcpt = Util.getXCpt_sLastFileName(sbcpt);
					if (!sbcpt.equals(lcpt)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lcpt));
					String nodeName = Util.createNodeName(sbcpt);
					AAj.intEnv=AAj.intEnv+1;
					AAj.tocNodeEnvCpt = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(AAj.tocNodeEnvCpt, AAj.tocNodeEnv, AAj.tocNodeEnv.getChildCount());
					//IF env-xcpt is a relation, display its env-args on viewer-tree.

					if (Util.isSpecificOf(xcpt_sFormalID, "hknu.symb-16"))
					{
						Extract ex = new Extract();
						ex.extractEnvironments(xcpt_sFormalID);
						Vector vcptEnvID=ex.vectorEnvID;
						for (int i=0; i<vcptEnvID.size(); i++)
						{
							String cptEnvID=(String)vcptEnvID.get(i);
							String cptEnvNodeName = Util.createNodeName(cptEnvID);
							DefaultMutableTreeNode node = new DefaultMutableTreeNode(cptEnvNodeName);
							AAj.tocModel.insertNodeInto(node, AAj.tocNodeEnvCpt, AAj.tocNodeEnvCpt.getChildCount());
						}
					}
					writeCptAttrInDocInBlack(nodeName);
				}

				else if (parent.equals("REFINO")){//parent of xcpt
					//found a cptext inside an environment|attribute-relation of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					String lflName = Util.getXCpt_sLastFileName(flName);
					if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
					String nodeName = Util.createNodeName(flName);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					if (parent2.equals("REFINO")){
						AAj.tocModel.insertNodeInto(node, AAj.tocNodeRefRel, AAj.tocNodeRefRel.getChildCount());
						writeCptAttrInDocInBlack(nodeName);
					} else if (parent2.equals("REFINO_ATTRIBUTE")){
						AAj.tocModel.insertNodeInto(node, AAj.tocNodeAttRel, AAj.tocNodeAttRel.getChildCount());
						writeCptAttrInDocInBlack(nodeName);
					} else if (parent2.equals("REFINO_NONaTTRIBUTE")){
						AAj.tocModel.insertNodeInto(node, AAj.tocNodeNonAttRel, AAj.tocNodeNonAttRel.getChildCount());
						writeCptAttrInDocInBlack(nodeName);
					} else {
						AAj.tocModel.insertNodeInto(node, AAj.tocNodeEnvRel, AAj.tocNodeEnvRel.getChildCount());
						writeCptAttrInDocInBlack(nodeName);
					}
				}

				else if (parent.equals("REFINO_ENTITY")){//parent of xcpt
					//found a cptext inside an Entity-Relation of a file-xcpt
					String flName = (String)atts.get("FRnAME");
					String nodeName = "none";
					if (!flName.equals("none")){
						//set the LastFileName in displayed-sbcpt xml-file.
						String lflName = Util.getXCpt_sLastFileName(flName);
						if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
						nodeName = Util.createNodeName(flName);
					}
//					AAj.intGen=AAj.intGen+1;
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeEnt, AAj.tocNodeEnt.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}

				else if (parent.equals("REFINO_ATTRIBUTE")){//parent of xcpt
					String flName = (String)atts.get("FRnAME");
					String lflName = Util.getXCpt_sLastFileName(flName);
					if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
					String nodeName = Util.createNodeName(flName);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeAtt, AAj.tocNodeAtt.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}

				else if (parent.equals("REFINO_ARGUMENT")){//parent of xcpt
					String flName = (String)atts.get("FRnAME");
					String lflName = Util.getXCpt_sLastFileName(flName);
					if (!flName.equals(lflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lflName));
					String nodeName = Util.createNodeName(flName);
					AAj.intArg=AAj.intArg+1;
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeArg, AAj.tocNodeArg.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}
			}
		}

		else if (elName.equals("INTxCPT")){//at start
			String flName = (String)atts.get("FRnAME");
			String xcpt_sFormalID = Util.getXCpt_sFormalID(flName);
			String xcpt_sFormalNumber = Util.getXCpt_sFormalNumber(flName);

			if (xcpt_sFormalID.equals(AAj.xcpt_sFormalID))
			{
				//we found the internal we want to display as ONE sbcpt.
				rightInt=true;//only when this is true we read termTxCpt_sAll.
				if (parent.equals("REFINO_PART")){//at start/int
					//set the author.
					AAj.xcpt_sAuthor=(String)atts.get("AUTHOR");
					AAj.xcpt_sCreated=(String)atts.get("CREATED");
					AAj.xcpt_sModified=(String)atts.get("LASTmOD");

					//put the host on the tree as WHOLE-XCPT.
					AAj.intWho=AAj.intWho+1;						//number of whole.
					String hostID=Util.getSrCptInt_sHostID(flName);		//eg. it-1.
					String nodeName = Util.createNodeName(hostID);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeWho, AAj.tocNodeWho.getChildCount());

					//put gen-xml-att on tree GENERIC-XCPT.
					String genValue = (String)atts.get("REFINO_GENERIC");
					String genCpt=null;				//the generic of this internal-xcpt.
					if (genValue!=null)
					{
						if (genValue.indexOf("/")!=-1)
						{
							genCpt=genValue.substring(0, genValue.indexOf("/"));
							AAj.intGen=AAj.intGen+1;

		// check for the last file-name.
		//					String gflName = Util.getXCpt_sLastFileName(gen);
		//					if (!gen.equals(gflName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(gflName));

							node = new	DefaultMutableTreeNode(Util.createNodeName(genCpt));
							AAj.tocModel.insertNodeInto(node, AAj.tocNodeGen, AAj.tocNodeGen.getChildCount());
						}
						else //iheritor is missing, isert in NonInherited
						{
							JOptionPane.showMessageDialog(null,"Internal-Part (" +flName +") is an iherited WITHOUT inheritor!!!");
							genCpt=genValue;
							AAj.intGen=AAj.intGen+1;
							node = new	DefaultMutableTreeNode(Util.createNodeName(genCpt));
							AAj.tocModel.insertNodeInto(node, AAj.tocNodeGen, AAj.tocNodeGen.getChildCount());
						}
					}
					//put ID on title. An internal is integrated by deafault.
					AAj.xcpt_sLblIntegrated.setText("(#" +xcpt_sFormalNumber +", " +AAj.rbLabels.getString("integrated"));
					AAj.xcpt_sLblIntegrated.setForeground(Color.red);
				}
				else if (parent.equals("REFINO_SPECIFIC")){//at start/int
					//found INTxCPT as specific of a file-xcpt.
					//put it on tree.
					//we found an internal we want to display as one sbcpt.
					String xcpt_sName = Util.createNodeName(flName);
					String fid = Util.getXCpt_sFormalID(flName);
					//set the author.
					AAj.xcpt_sAuthor=(String)atts.get("AUTHOR");
					AAj.xcpt_sCreated=(String)atts.get("CREATED");
					AAj.xcpt_sModified=(String)atts.get("LASTmOD");

					//put the host on the tree as gen.
					AAj.intGen=AAj.intGen+1;//number of whole.
					String hostID=Util.getSrCptInt_sHostID(fid);//eg. it-1.
					String hostFile=Util.getXCpt_sLastFileName(hostID);//eg."Sensorial-b-concept@hknu.it-1.xml"
					String nodeName = Util.createNodeName(hostFile);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeGen, AAj.tocNodeGen.getChildCount());

					//put the host's whole on the tree as who.
					Extract ex = new Extract();
					ex.extractWholes(hostFile);
					Vector vWho= ex.vectorWhoID;
					for (int i=0; i<vWho.size(); i++)
					{
						String whoID=(String)vWho.get(i);
						nodeName = Util.createNodeName(whoID);
						node = new	DefaultMutableTreeNode(nodeName);
						AAj.tocModel.insertNodeInto(node, AAj.tocNodeWho, AAj.tocNodeWho.getChildCount());
						AAj.intWho=AAj.intWho+1;
					}
					//put ID on title. An internal is integrated by deafault.
					AAj.xcpt_sLblIntegrated.setText("(#" +xcpt_sFormalNumber +", " +AAj.rbLabels.getString("integrated"));
					AAj.xcpt_sLblIntegrated.setForeground(Color.red);
				}
			}
			else {
				//display the internal as ATTRIBUTE of an xcpt.
				if (parent.equals("REFINO_PART")){//at start/int/attr
					//display the int as attribute of another
					String xcpt_sName = Util.getXCpt_sName(flName,AAj.lng);
					String fid = Util.getXCpt_sFormalID(flName);
					//store position
					AAj.hmapCaret.put(fid, String.valueOf(AAj.doc.getLength()));
					//increase the 'parts' counter.
					AAj.intPar=AAj.intPar+1;
					//create the node to insert.
					String nodeName = xcpt_sName +" (" +fid +")";
//					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					//insert the node in the tree depending on its 'generic' value.
					String genValue = (String)atts.get("REFINO_GENERIC");
					String genCpt=null;				//the generic of this internal-xcpt.
					String inheritor=null;		//the inheritor of this internal-xcpt.
					String inheritorID=null;

					if (genValue==null)
					{
						//A NonInherited found
						AAj.intParNInh=AAj.intParNInh+1;
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
						AAj.tocModel.insertNodeInto(node, AAj.tocNodePart, AAj.tocNodePart.getChildCount());
					}
					else {
						//an Inherited found
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName+"(inh)");
						if (genValue.indexOf("/")!=-1)
						{
							AAj.intParInh=AAj.intParInh+1;
							AAj.tocModel.insertNodeInto(node, AAj.tocNodePart, AAj.tocNodePart.getChildCount());
						}
						else //iheritor is missing, isert as NonInherited
						{
							JOptionPane.showMessageDialog(null,"Part (" +flName +") is an iherited-part WITHOUT inheritor!!!");
							AAj.intParNInh=AAj.intParNInh+1;
							AAj.tocModel.insertNodeInto(node, AAj.tocNodePart, AAj.tocNodePart.getChildCount());
						}
					}

					StringWriter swriter = new StringWriter();
					swriter.write("\n" +xcpt_sName);
					try {
						AAj.doc.insertString(AAj.doc.getLength(), swriter.toString(), AAj.s2);
						swriter.close(); }
					catch (BadLocationException eble){}
					catch (IOException ioe){}
					//write the generic if exists. 2000.02.09
					if (genCpt!=null)
					{
			//			String lgName = Util.getXCpt_sLastFileName(genCpt);
			//			if (!genCpt.equals(lgName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lgName));
						String genName = Util.getXCpt_sName(genCpt,AAj.lng);
						StringWriter swriter2 = new StringWriter();
						swriter2.write(" (gen=" +genName +"):");
						try {
							AAj.doc.insertString(AAj.doc.getLength(), swriter2.toString(), AAj.s0);
							swriter2.close(); }
						catch (BadLocationException eble){}
						catch (IOException ioe){}
					}
				}
				else if (parent.equals("REFINO_SPECIFIC")){//at start/int/attr
					String xcpt_sName = Textual.createNormalName(flName);
					String fid = flName.substring(flName.indexOf("@")+1, flName.length()-4);
			//		String gen = getAttribute("REFINO_GENERIC", atts);
			//		String lgName = Util.getXCpt_sLastFileName(gen);
			//		if (!gen.equals(lgName)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(lgName));
					String nodeName = xcpt_sName +" (" +fid +")";
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					//insert the node in the tree depending on its 'generic' value.
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeSpe, AAj.tocNodeSpe.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}
				else if (parent.equals("REFINO_ATTRIBUTE")){//at start/int/attr
					String xcpt_sName = Textual.createNormalName(flName);
					String fid = flName.substring(flName.indexOf("@")+1, flName.length()-4);
					String nodeName = xcpt_sName +" (" +fid +")";
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
					//insert the node in the tree depending on its 'generic' value.
					AAj.tocModel.insertNodeInto(node, AAj.tocNodeAtt, AAj.tocNodeAtt.getChildCount());
					writeCptAttrInDocInBlack(nodeName);
				}
			}
		}
	}


	/**
	 * At element's CONTENT we do the followings:<br/>
	 * a) REFINO_DEFINITION:
	 *
	 * @modified 2006.01.08
	 * @since 2004.07.02 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected void processElementContent(String elName, String cntnt, String parent)
	{
		if (elName.equals("REFINO_DEFINITION")){

			if (AAj.blReadInt && rightInt && parent.equals("INTxCPT")){
				StringWriter swriter = new StringWriter();
				swriter.write(AAj.rbLabels.getString("REFINO_DEFINITION") +":\n");
				try {
					AAj.doc.insertString(AAj.doc.getLength(), swriter.toString(), AAj.s1);
					swriter.close(); }
				catch (BadLocationException ble){System.out.println("EHintDefinition: ble-35");}
				catch (IOException ioe){System.out.println("EHintDefinition: ioe-36");}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), cntnt, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHintDefinition: ble");
				}
			}
			else {
				//found text-content in DEF-ELEMENT,
				//isert it.
				//not for internals
				if (parent.equals("XCONCEPT") && !AAj.blReadInt){
					StringWriter swriter = new StringWriter();
					swriter.write(cntnt);
					swriter.write("\n");
					try {
						AAj.doc.insertString(AAj.doc.getLength(), swriter.toString(), AAj.s0);
						swriter.close(); }
					catch (BadLocationException eble){}
					catch (IOException ioe){}
				}
			}
		}

		else if (elName.equals("ANALYTIC")){//at content
			if (!AAj.blReadInt){
				//found scmm element
				//translate ssemasia to text.
				String def="";
				def= Util.mapXSSemasiaToText(cntnt,AAj.lng);
				def=": " +def;
				try {
					AAj.doc.insertString(AAj.doc.getLength(), def, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHDefAnalytic: ble 65");
				}
			}
		}

		else if (elName.equals("DEFINITION_GENERIC")){//at content
			if (!AAj.blReadInt){
				String def="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText(cntnt,AAj.lng,false);
				def = mdtt.txtDefinition;
				String cr= Util.getXmlAttribute(cntnt, "CREATION");
				String title= "";
				if (cr!=null){
					title = "\n" +AAj.rbLabels.getString("creation-definition")+"::"
						+AAj.rbLabels.getString("generic")+": ";
				} else {
					title = "\n"+AAj.rbLabels.getString("generic")+": ";
				}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), title, AAj.s2);
				}
				catch (BadLocationException eble){}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), def, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHDefGen: ble");
				}
			}
		}
		else if (elName.equals("DEFINITION_PART")){//at content of element
			if (!AAj.blReadInt){
				String def="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText(cntnt,AAj.lng,false);
				def = mdtt.txtDefinition;
//				def="\n" +cntnt;
				String title = "\n"+AAj.rbLabels.getString("part")+": ";
				try {
					AAj.doc.insertString(AAj.doc.getLength(), title, AAj.s2);
				}
				catch (BadLocationException eble){}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), def, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHDefPart: ble");
				}
			}
		}
		else if (elName.equals("DEFINITION_RELATOR")){//at content of element
			if (!AAj.blReadInt){
				String def="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText(cntnt,AAj.lng);
				def = mdtt.txtDefinition;
				String cr= Util.getXmlAttribute(cntnt, "CREATION");
				String title= "";
				if (cr!=null){
					title = "\n" +AAj.rbLabels.getString("creation-definition")+"::"
						+AAj.rbLabels.getString("relator")+": ";
				} else {
					title = "\n"+AAj.rbLabels.getString("relator")+": ";
				}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), title, AAj.s2);
				}
				catch (BadLocationException eble){}
				try {
					AAj.doc.insertString(AAj.doc.getLength(), def, AAj.s0);
				} catch (BadLocationException ble){
					System.out.println("EHDefRelator: ble");
				}
			}
		}



	}


	/**
	 * At element's END we do the followings:<br/>
	 * a) XCONCEPT: we expand the row with parts at toc.<br/>
	 * b) INTxCPT: we display it as internal.
	 *
	 * @modified 2009.11.02
	 * @since 2004.07.02 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected void processElementEnd(String elName, String parent)
	{
		if (elName.equals("XCONCEPT")){
			//expand the part node of toc
//			AAj.viewer_sToC.expandRow(4);
		}

		else if (elName.equals("INTxCPT")){
			// we display an internal of a SPECIFIC | PART as one sbcpt.
			if (AAj.blReadInt && rightInt){
				if (parent.equals("REFINO_SPECIFIC")){
					//insert parts label
					StringWriter swriter3 = new StringWriter();
					swriter3.write("PARTS:");
					try {
						AAj.doc.insertString(AAj.doc.getLength(), swriter3.toString(), AAj.s1);
						swriter3.close(); }
					catch (BadLocationException eble){}
					catch (IOException ioe){}

					//put the host-pars on tree as att.
					Extract ex = new Extract();
					ex.extractParts(AAj.xcpt_sHostID);
					Vector vPar = ex.vectorParID;
					for (int i=0; i<vPar.size(); i++)
					{
						String parID=(String)vPar.get(i);
						String parFLN = Util.getXCpt_sLastFileName(parID);
						if (!parID.equals(parFLN)) AAj.vectorOldNamesID.add(Util.getXCpt_sFormalID(parFLN));
						String parName = Textual.createNormalName(parFLN);
						String nodeName = parName +" (" +parID +")";
						AAj.intPar=AAj.intPar+1;

						//>>ALL<< host-att are inherited.
						DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName+"(inh)");
						AAj.intParNInh=AAj.intParNInh+1;
						AAj.tocModel.insertNodeInto(node, AAj.tocNodePart, AAj.tocNodePart.getChildCount());

						//display the defs
						StringWriter swriter = new StringWriter();
						swriter.write("\n	 " +parName +" (gen=" +parID +")");
						//store position
						AAj.hmapCaret.put(parID, String.valueOf(AAj.doc.getLength()));
						try {
							AAj.doc.insertString(AAj.doc.getLength(), swriter.toString(), AAj.s2);
							swriter.close();}
						catch (BadLocationException eble){}
						catch (IOException ioe){}

						Extract def = new Extract();
						try{def.extractDef(parID);}
						catch (Exception exc){JOptionPane.showMessageDialog(null, "EHintSub: There is NOT file for the ATTRIBUTE-sbcpt " +parID);}
						String strDef = ":\n" +def.baos.toString();
						StringWriter swriter2 = new StringWriter();
						swriter2.write(strDef);
						try {
							AAj.doc.insertString(AAj.doc.getLength(), swriter2.toString(), AAj.s0);
							swriter2.close();}
						catch (BadLocationException eble){}
						catch (IOException ioe){}
					}
				}
				rightInt=false;
			}
		}

	}


	/**
	 * Inserts the name of an attribute-xConcept in the viewer's doc.
	 *
	 * @modified 2009.05.03
	 * @since 2009.04.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected void writeCptAttrInDocInBlack(String nodeName)
	{
		//toDo: handle inherited sbcpts:
		writeTextInDoc("\n  * " +nodeName, AAj.s0);
/*
		StringWriter swriter = new StringWriter();
		swriter.write("\n  * " +nodeName);
		try {
			AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), AAj.s0);
			swriter.close(); }
		catch (BadLocationException ble){System.out.println("EHSCptExt: ble");}
		catch (IOException ioe){System.out.println("EHSCptExt: ioe");}
*/
	}


	/**
	 * Inserts text, with a Style in viewer's doc.
	 * @modified 2009.05.03
	 * @since 2009.05.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected void writeTextInDoc(String txt, Style stl)
	{
		StringWriter swriter = new StringWriter();
		swriter.write(txt);
		try {
			AAj.doc.insertString(AAj.doc.getLength(),swriter.toString(), stl);
			swriter.close(); }
		catch (BadLocationException ble){
			System.out.println("EHWriteTextInDoc: ble");
		}
		catch (IOException ioe){
			System.out.println("EHWriteTextInDoc: ioe");
		}
	}
}
