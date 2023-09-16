/*
 * Parser_Text.java - A TREE-BASED parser that inputs PLAIN-TEXT and outputs
 * 							 its TxNode (the treeStructure).
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2005 Nikos Kasselouris
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
package pk_Logo;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Hashtable;
import java.util.Vector;

import pk_Util.Util;

/**
 * A TREE-BASED parser that inputs PLAIN-TEXT and outputs its
 * java-TxNode (the treeStructure).<p>
 *
 * CODE:<code><br/>
 * Parser_Text parser = new Parser_Text(context, lang);<br/>
 * FileReader reader = new FileReader("filename.txt");<br/>
 * TxNode sctm = parser.parseFromReader(reader);</code><p>
 *
 * PRECONDITIONS:<br/>
 * - Blank-lines define the main-tx_nonstopNodes (tx_period_s_whole, tx_period, tx_period_s_part).<br/>
 * - Labels such: chapter, secter define subnodes.<p>
 *
 * The methodology of parsing will be: linear and parallel.<br/>
 * - linear: word after word.<br/>
 * - parallel: for each word we will find all possible knowledge.
 * 2004.08.05<p>
 *
 * @modified 2004.11.09
 * @since 2004.08.04 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Parser_Text
{

	/**
	 * A string that describes the brainual--sub-worldview of the input-text
	 * if we know it. This helps
	 * the parser to find where to search for words first.
	 */
	public String context;
	/** The output TxNode. */
	public TxNode txNdOutMost;
	/** The language of input text. */
	public String lang;

	/** Temporary-buffer, utility to get the next-tx_nonstopNode */
	protected StringBuffer tmpbuf = new StringBuffer();

	/**
	 * Character-read-back table.
	 * Holds the reader and its push-back character.
	 * If there is no such character, this value is '\u0000'.
	 */
	protected Hashtable<Reader,String> charReadBackTable = new Hashtable<Reader,String>();

	/** The current line-number in the source. */
	protected int lineNrSrc=1;
	/** The line-number in the source at the begining of the tx_nonstopNode. */
	protected int lineNrSrcBeg=1;



	/**
	 * Constructs the java object by inputing the context and the
	 * language of the input-text if we know them.
	 *
	 * @param cntxt	The brainual--sub-worldview the input-text denotes.
	 * @param lng	The language of the input-text.
	 * @modified 2004.08.08
	 * @since 2004.08.08 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Parser_Text(String cntxt, String lng)
	{
		context=cntxt;
		lang=lng;
	}


	/**
	 * Parses TEXT from a java.io.Reader and outputs its java-Text.
	 *
	 * @param reader
	 *   	The reader from which to retrieve the plain-text.
	 * @modified 2004.11.20
	 * @since 2004.08.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode parseFromReader(Reader reader) throws IOException
	{
		Util.log(null);
		Util.log(null);

		if (lang.equals("eng")){
			Util.log("START-PARSING: TEXTUAL-EXPRESSION-ENGLISH");
			Parser_TextToTxNode_Eng enp = new Parser_TextToTxNode_Eng(context);
			txNdOutMost = enp.parseFromReader(reader);
//			Parser_TextEng enp = new Parser_TextEng(context);
//			txNdOutMost = enp.parseFromReader(reader);
		}	else if (lang.equals("eln")){
//			Util.log("START-PARSING: GREEK-TEXT");
//			Parser_Text_Eln elp = new Parser_Text_Eln(context);
//			txNdOutMost = elp.parseFromReader(reader);
		}

		return txNdOutMost;
	}


	/**
	 * Parses TEXT from a String.
	 *
	 * @param string
	 *   The string from which to retrieve the data.
	 * @modified
	 * @since 2004.08.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode parseFromString(String string)
		throws IOException
	{
		txNdOutMost = this.parseFromReader(new StringReader(string));
		return txNdOutMost;
	}


	/**
	 * Parses TEXT from a String.
	 *
	 * @param string
	 *   The string from which to retrieve the data.
	 * @param offset
	 *   The first character in <code>string</code> to scan.
	 * @modified
	 * @since 2004.08.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode parseFromString(String string, int offset)
		throws IOException
	{
			return this.parseFromString(string.substring(offset));
	}


	/**
	 * Parses TEXT from a String.
	 *
	 * @param string
	 *   The string from which to retrieve the data.
	 * @param offset
	 *   The first character in <code>string</code> to scan.
	 * @param end
	 *   The character where to stop scanning.
	 *   This character is not scanned.
	 * @modified
	 * @since 2004.08.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode parseFromString(String string, int offset, int end)
		throws IOException
	{
		string = string.substring(offset, end);
		txNdOutMost = this.parseFromReader(new StringReader(string));
		return txNdOutMost;
	}


	/**
	 * Parses TEXT from a char array.
	 *
	 * @param input
	 *   The char array from which to retrieve the data.
	 * @param offset
	 *   The first character in <code>string</code> to scan.
	 * @param end
	 *   The character where to stop scanning.
	 *   This character is not scanned.
	 * @modified
	 * @since 2004.08.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode parseFromCharArray(char[] input, int  offset,
													int  end)
		throws IOException
	{
		Reader reader = new CharArrayReader(input, offset, end);
		txNdOutMost = this.parseFromReader(reader);
		return txNdOutMost;
	}


	/**
	 * <b>INPUT</b>: a Vector of TxNodes and an index in this vector.
	 * <b>OUTPUT</b>: the next tx_nonstop_node.
	 *
	 * IF null, we reached the end of the vector.
	 *
	 * @modified 2007.02.05
	 * @since 2007.02.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode getNextTxNonstopNode(Vector<TxNode> voc, int i)
	{
		int j = i+1;
		TxNode ch = voc.get(j);
		while(ch.txnd_sType.equals("TxPunctuation")
					|| ch.txnd_sType.equals("TxBlankspace")){
			j=j+1;
			if (j<voc.size()+1)
				ch = voc.get(j);
			else {
				ch = null;
				break;
			}
		}
		return ch;
	}


	/**
	 * Scans a character from a reader and returns it.
	 * If the returned character is '\u0000' we reached the end-of-source.
	 *
	 * @modified
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected char scanChar(Reader rdr) throws IOException
	{
		char ch;

		//first check if the character is already read
		if (charReadBackTable.containsKey(rdr) &&
				! (charReadBackTable.get(rdr)).equals("\u0000") ){
				ch = (charReadBackTable.get(rdr)).charAt(0);
				scanCharBack(rdr,'\u0000');
				return ch;
		} else {
			int i = rdr.read();
			if (i < 0) {
				return '\u0000';
			} else if (i == 10) { //10=0a=\n linefeed LF
				lineNrSrc += 1;
				return '\n';
			} else {
				return (char) i;
			}
		}
	}


	/**
	 * Pushes a character back to the read-back buffer-table.
	 *
	 * @param reader
	 * 		The reader which we want to push a character back.
	 * @param ch
	 * 		The character to push back.
	 * @modified
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected void scanCharBack(Reader reader, char ch)
	{
		charReadBackTable.put(reader,String.valueOf(ch));
	}


	/**
	 * Scans blankspaces (space, tab, new-line) from a reader and
	 * returns the <b>next</b>
	 * character following the whitespace, which pushes-back.
	 * The whitespace is apended to a stringbuffer.
	 * If the return character is '\u0000' we reached the end of the reader.
	 *
	 * @modified 2006.08.02
	 * @since 2006.02.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected char scanTxBlankspace(Reader reader, StringBuffer result)throws IOException
	{
		char ch;
		for (;;) {
			ch = scanChar(reader);
			switch (ch) {
				case ' ':
				case '\t':
				case '\n':
					result.append(ch);
					break;
				case '\r':
					break;
				case '\u0000':
					return ch;
				default:
					return ch;
			}
		}
	}


	/**
	 * Scans whitespace from a reader and returns the <b>next</b>
	 * character following the whitespace, which pushes-back.
	 *
	 * @modified 2006.08.02
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected char scanTxBlankspace(Reader reader)throws IOException
	{
		char ch;
		for (;;) {
			ch = scanChar(reader);
			switch (ch) {
				case ' ':
				case '\t':
				case '\r':
				case '\n':
					break;
				case '\u0000':
					return ch;
				default:
					return ch;
			}
		}
	}


	/**
	 * Scans the next PERIOD'S-WHOLE-NONSTOP-NODE, a tx_expression separated
	 * with blank lines (at least two '\n'), from a READER.<br/>
	 * <b>Instances</b>: tx_period_s_whole, tx_period, tx_period_s_part<p>
	 *
	 * The scanned tx_expression is appended to <code>result</code>.
	 *
	 * @return
	 * 		The character after '\n'.<br/>
	 * 		If the character is '\u0000' we reached the end-of-source.
	 * @modified
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected char scanTxNonstopnodeMain(Reader reader, StringBuffer result)throws IOException
	{
		char ch;
		for(;;) {
			ch = scanChar(reader);

			if (ch=='\u0000')
				return ch;
//on mac: \r
//on unix: \n
//on windows: \r\n
//java internal: \n
			else if (ch=='\n'){//a paragrapher OR a main-tx_nonstopNode.
				ch=scanChar(reader);//\n
				ch=scanChar(reader);
				if (ch=='\u0000')
					return ch;
				else if (ch=='\n'){ //another \n means main-tx_nonstopNode.
					ch=scanTxBlankspace(reader);
					return ch;
				}
				else //we are inside a paragrapher
					result.append(ch);
			}

			else
				result.append(ch);
		}
	}


	/**
	 * Searches all the tx_syntax of a terminal for a value
	 * in an attribute.
	 *
	 * @param t	The TERMINAL whose tx_syntax we want to check.
	 * @param att	The attribute of the tx_syntax we want to search.
	 * @param value	The value of the attribure of the tx_syntax
	 * 		we want to check.
	 * @modified 2008.11.15
	 * @since 2004.09.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected boolean checkTxSyntaxOfTerminalExactly(TxNode t,
																		String att,
																		String value)
	{
		String sks_a;
		for(String sks : t.txnd_s_VoSyntax)
		{
			sks_a = Util.getXmlAttribute(sks,att);
			//with startsWith method we can search for an tx_auxiliary, lg-concept's--term...
			if (sks_a.indexOf(value)!=-1)
				return true;
		}
		return false;
	}


	/**
	 * Searches all the tx_syntax of a terminal, if a value
	 * in an attribute starts with a specific value.
	 *
	 * @param t	The TERMINAL whose tx_syntax we want to check.
	 * @param att	The attribute of the tx_syntax we want to search.
	 * @param value	The value of the attribure of the tx_syntax
	 * 		we want to check.
	 * @modified 2008.11.15
	 * @since 2008.11.15 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected boolean checkTxSyntaxOfTerminalStartsWith(TxNode t,
																		String att,
																		String value)
	{
		String sks_a;
		for(String sks : t.txnd_s_VoSyntax)
		{
			sks_a = Util.getXmlAttribute(sks,att);
			if (sks_a.startsWith(value))
				return true;
		}
		return false;
	}


	/**
	 * Creates a parse exception, when the next characters read
	 * are not the characters that were expected.
	 *
	 * @param str
	 * 		The characters that was expected.
	 * @modified 2004.11.20
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected Exception_Logo_ParsingText throwExpectedInput(String str)
	{
		String msg = "Expected: " + str;
		return new Exception_Logo_ParsingText(lineNrSrc, msg);
	}


	/**
	 * Creates a parse exception, when a syntax error occured.
	 *
	 * @param cntxt
	 * 		A description of where the error occured.
	 * @modified 2004.11.20
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected Exception_Logo_ParsingText throwSyntaxError(String cntxt)
	{
		String msg = "Syntax error while parsing = " + cntxt;
		return new Exception_Logo_ParsingText(lineNrSrc, msg);
	}

}
