/*
 * Maper_SSemasiaToSSemasia_Eln.java - Generates the greek-SSemasia of
 * another SSemasia.
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
import pk_Util.Textual;

/**
 * It takes a jSSemasia and maps it to a GREEK jSSemasia.
 * @modified
 * @since 2004.11.20 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_SSemasiaToSSemasia_Eln
{
	/** The jSSemasia we want to map to greek-jSSemasia */
	public SSmNode jSsmaIn;
	/** The output jSSemasia */
	public SSmNode jSSmOut;

	/**
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_SSemasiaToSSemasia_Eln (SSmNode mm)
	{
		jSsmaIn=mm;
	}


	/**
	 * Generates the greek-jSSemasia of a jSSemasia.
	 * @modified
	 * @since 2004.11.21
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSSemasia()
	{
		if (!jSsmaIn.getName().equals("SSMA")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eln: It is not a SSMA.");
		}

		jSsmaIn.setAttribute("LNG", "eln");

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
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmSentenceStructure(SSmNode mss, String syntax)
	{
		if ( ! mss.getName().equals("SmSentenceStructure")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eln: It is not a SmSentenceStructure.");
		}
		return mss;
	}


	/**
	 * Returns a SmSentence in the specificed-language.
	 * @modified
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmSentence(SSmNode ssmnd, String syntax)
	{
		if ( ! ssmnd.getName().equals("SmSentence")){
			throw new Exception_SSemasia_Generating(
				"Maper_SSemasiaToSSemasia_Eln.mapSmSentence: It is not a SmSentence.");
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
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmVerb (SSmNode svrb)
	{
		if ( ! svrb.getName().equals("SmVerb")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eln: It is not a SmVerb.");
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
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmConjunction(SSmNode mcr)
	{
		if ( ! mcr.getName().equals("SmConjunction")){
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eln: It is not a SmConjunction.");
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
				"Maper_SSemasiaToSSemasia_Eln: It is not a SmNounStructure.");
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
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSmNoun(SSmNode snno, String syntax)
	{
		String des=snno.getAttribute("XCPT");
		if ( ! snno.getName().equals("SmNoun")) {
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eln: "
						+des +" it is not a SmNoun.");
		}

		String desr=snno.getAttribute("XCPT");
		String attr=snno.getAttribute("SMaTTR");

		//we must add the gender in the value of attributes
		String gender = Textual.greekGetGender(desr);
		//gender=mas,fem,neu
		attr=attr+","+gender;
		Util.log("\tmtx_noun = "+des +", attribute = "+attr);
		snno.setAttribute("SMaTTR",attr);

		//Util.log("\ttx_expression of SmNoun "+des+"= "+logal);
		return snno;
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
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eln: It is not a SmSpecialNoun-node.");
		}

		//Util.log("\ttx_expression of SmSpecialNoun "+sbcpt+"= "+logal);
		return mpnr;
	}


	/**
	 * Returns a SSmNode in the specificed-language.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapSSmNode(SSmNode ndm, String syntax)
	{
		SSmNode grmn=null;
		if (ndm.getName().equals("SmSentenceStructure"))
			grmn=mapSmSentenceStructure(ndm, syntax);
		else if (ndm.getName().equals("SmParagraph")
					 ||ndm.getName().equals("SmSupersentence")){
			for (int i=0; i<ndm.ssnd_s_Children.size(); i++)
			{
				grmn = mapSSmNode(ndm.ssnd_s_Children.get(i),null);
			}
		}
		else if (ndm.getName().equals("SmSentence"))
			grmn=mapSmSentence(ndm, syntax);
		else if (ndm.getName().equals("SmNounStructure"))
			grmn=mapSmNounStructure(ndm, syntax);
		else if (ndm.getName().equals("SmVerb"))
			grmn=mapSmVerb(ndm);
		else if (ndm.getName().equals("SmNoun"))
			grmn=mapSmNoun(ndm, syntax);
		else if (ndm.getName().equals("SmConjunction"))
			grmn=mapSmConjunction(ndm);
		else if (ndm.getName().startsWith("SmSpecial"))
			grmn=mapSmSpecialNoun(ndm, syntax);
		else
			throw new Exception_SSemasia_Generating("Maper_SSemasiaToSSemasia_Eln.mapSSmNode: It is not a jFoEdoNode.");
		return grmn;
	}


}
