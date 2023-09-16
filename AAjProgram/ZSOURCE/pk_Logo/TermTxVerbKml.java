/**
 * TermTxVerbKml.java - The esperanto-ttermVerb class.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2008 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * This program is free software; you can redistribute it and/or
 * modified it under the terms of the GNU Generic Public License
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
package pk_Logo;

import pk_XKBManager.AAj;
import pk_Util.Util;
import pk_Util.Textual;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * The tx-cpt's-terms are returned in a string like:
 * termTxCpt_sAll="individual1,individual2".<p>
 *
 * @modified
 * @since 2008.04.24 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TermTxVerbKml
	extends Term_TxConcept
{
	String		part1="",part2="";		//the parts of a multi-word TermTxVerb.
	String[]	nms1=null,nms2=null;	//the termTxCpt_sAll of the parts of a multi-word.

	/**
	 * @modified
	 * @since 2008.04.24
	 * @author HoKoNoUmo
	 */
	public TermTxVerbKml (String name, String rule)
	{
		super(name, "term_TxVerb", "epo");
		termTxCpt_sRule=rule;
	}

	/**
	 * @modified
	 * @since 2008.04.21
	 * @author HoKoNoUmo
	 */
	public TermTxVerbKml(String name)
	{
		this(name, null);
	}



	/**
	 * Finds the termTxCpt_sAll of a TermTxVerb, given its termTxCpt_sName.
	 * @modified 2008.04.23
	 * @since 2008.04.21
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		termTxCpt_sAll="";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();
		stem=termTxCpt_sName;

//skributan, 	aoristic
//skributen, 	past
//skributin, 	present
//skributon,	future
		stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
		termTxCpt_sAll=	stem+"an,"
									+stem+"en,"
									+stem+"in,"
									+stem+"on";

		return termTxCpt_sAll;
	}


}