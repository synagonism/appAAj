/*
 * Maper_SSemasiaToSSemasia_Eng.java - Generates the english SSemasia of a SSemasia.
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
package pk_SSemasia;

import java.io.*;
import pk_Util.Util;

/**
 * It takes a jSSemasia and maps it to
 * an ENGLISH jSSemasia.
 * @modified
 * @since 2004.11.21 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_SSemasiaToSSemasia_Eng
{
	/** The jSSemasia we want to map to english-jSSemasia */
	public SSmNode jSsmaIn;
	/** The output jSSemasia */
	public SSmNode jSSmOut;

	/**
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_SSemasiaToSSemasia_Eng (SSmNode mm)
	{
		jSsmaIn=mm;
	}


	/**
	 * Generates the english-jSSemasia of a jSSemasia.
	 * @modified
	 * @since 2004.11.21
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSSemasia()
	{
		if (!jSsmaIn.getName().equals("SSMA")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eng: It is not a SSMA.");
		}

		jSsmaIn.setAttribute("LNG", "eng");

		for (int i=0; i<jSsmaIn.ssnd_s_Children.size(); i++)
		{
			SSmNode	child = mapSSmNode(jSsmaIn.ssnd_s_Children.get(i),null);
		}
		jSSmOut=jSsmaIn;
		return jSSmOut;
	}


	/**
	 * Returns a SmSentenceStructure in the specificed-language.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmSentenceStructure(SSmNode mss, String syntax)
	{
		if ( ! mss.getName().equals("SmSentenceStructure")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eng: It is not a SmSentenceStructure.");
		}
		return mss;
	}


	/**
	 * Returns a SmSentence in the specificed-language.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmSentence(SSmNode ssmnd, String syntax)
	{
		if ( ! ssmnd.getName().equals("SmSentence")){
			throw new Exception_SSemasia_Generating(
				"Maper_SSemasiaToSSemasia_Eng.mapSmSentence: It is not a SmSentence.");
		}

		//here we will change the syntax of the tx_verb.
		//#S:I #V:love #O:Mary
		//#S:Η Μαίρη #V:αρέσει #σε: μένα. = μου αρέσει η Μαίρη.
		//#S:Εγώ #V:αγαπώ #O:τη Μαίρη.
		//We must know the syntax of every verb in any language. 2004.11.21

		//if syntax of greek tx_verb is the same
		//else change the constituents.
		for (int i=0; i<ssmnd.ssnd_s_Children.size(); i++)
		{
			SSmNode	child = mapSSmNode(ssmnd.ssnd_s_Children.get(i),null);
		}

		return ssmnd;
	}


	/**
	 * Returns a SmVerb in the specificed-language.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmVerb (SSmNode svrb)
	{
		if ( ! svrb.getName().equals("SmVerb")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eng: It is not a SmVerb.");
		}

		//#S:I #V:love #O:Mary
		//#S:Η Μαίρη #V:αρέσει #σε: μένα. = μου αρέσει η Μαίρη.
		//#S:Εγώ #V:αγαπώ #O:τη Μαίρη.
		//We must know the syntax of every verb in any language. 2004.11.21

		//they are the same.
		return svrb;
	}


	/**
	 * Returns a SmConjunction in the specificed-language.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmConjunction(SSmNode mcr)
	{
		if ( ! mcr.getName().equals("SmConjunction")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eng: It is not a SmConjunction.");
		}
		return mcr;
	}


	/**
	 * Returns a SmNounStructure in the specificed-language.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmNounStructure(SSmNode mns, String syntax)
	{
		if ( ! mns.getName().equals("SmNounStructure")) {
			throw new Exception_SSemasia_Generating(
				"Maper_SSemasiaToSSemasia_Eng: It is not a SmNounStructure.");
		}

		for (int i=0; i<mns.ssnd_s_Children.size(); i++)
		{
			SSmNode	child = mapSSmNode(mns.ssnd_s_Children.get(i),null);
		}
		//Util.log("\ttx_expression of SmNounStructure "+lbl +" = "+logal);
		return mns;
	}


	/**
	 * Returns a SmNoun in the specificed-language.<br/>
	 *
	 * &lt;SmNoun LABEL="b1" TRM="Tree-network's-node@hknu.symb-11.xml" SMaTTR="individual,sin" /&gt;
	 * @param syntax
	 *		It takes the values: nominative, possesiveCase.
	 * 		lg_agent, lg_object, lg_subject,
	 * 		subjComplement, tx_nounAdjective.
	 * 		lg_subject2 (for indefinite with a) 2004.08.03
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmNoun(SSmNode mnr, String syntax)
	{
		String des=mnr.getAttribute("XCPT");
		if ( ! mnr.getName().equals("SmNoun")) {
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eng: "
						+des +" it is not a SmNoun.");
		}

		String desr=mnr.getAttribute("XCPT");
		String attr=mnr.getAttribute("SMaTTR");

		//we must REMOVE the gender in the value of attribute
		String attrNew = attr.substring(0,attr.lastIndexOf(","));
		mnr.setAttribute("SMaTTR",attrNew);

		//Util.log("\tlogal of SmNoun "+des+"= "+logal);
		return mnr;
	}


	/**
	 * Returns a smSpecialNoun in the specificed-language.<br/>
	 * &lt;SmSpecialNoun LABEL="b2" TRM="Quantity@hknu.symb-18@" SMaTTR="idef.any,any" /&gt;
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmSpecialNoun(SSmNode mpnr, String syntax)
	{
		if ( ! mpnr.getName().startsWith("SmSpecial")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eng: It is not a SmSpecialNoun-node.");
		}

		//Util.log("\tlogal of SmSpecialNoun "+sbcpt+"= "+logal);
		return mpnr;
	}


	/**
	 * Returns a SSmNode in the specificed-language.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSSmNode(SSmNode mn, String syntax)
	{
		SSmNode grmn=null;
		if (mn.getName().equals("SmSentenceStructure"))
			grmn=mapSmSentenceStructure(mn, syntax);
		else if (mn.getName().equals("SmParagraph")
					 ||mn.getName().equals("SmSupersentence")){
			for (int i=0; i<mn.ssnd_s_Children.size(); i++)
			{
				grmn = mapSSmNode(mn.ssnd_s_Children.get(i),null);
			}
		}
		else if (mn.getName().equals("SmSentence"))
			grmn=mapSmSentence(mn, syntax);
		else if (mn.getName().equals("SmNounStructure"))
			grmn=mapSmNounStructure(mn, syntax);
		else if (mn.getName().equals("SmVerb"))
			grmn=mapSmVerb(mn);
		else if (mn.getName().equals("SmNoun"))
			grmn=mapSmNoun(mn, syntax);
		else if (mn.getName().equals("SmConjunction"))
			grmn=mapSmConjunction(mn);
		else if (mn.getName().startsWith("SmSpecial"))
			grmn=mapSmSpecialNoun(mn, syntax);
		else
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eng.mapSSmNode: It is not a jFoEdoNode.");
		return grmn;
	}


}
