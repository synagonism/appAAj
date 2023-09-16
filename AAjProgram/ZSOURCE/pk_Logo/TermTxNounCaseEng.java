/**
 * TermTxNounCaseEng.java - The english-tx_noun class.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2001 Nikos Kasselouris
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
 * Contains the lg-concept's--term-rules of english tx-nouns.<p>
 *
 * The tx-cpt's-terms are returned in a string like: termTxCpt_sAll="form1,form2".<p>
 *
 * I use the IRREGULARS from WordNet 1.6. 2001.11.27
 * @modified 2006.01.31
 * @since 2001.03.12 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TermTxNounCaseEng
	extends Term_TxConcept
{
	String		part1="",part2="",part3="";		//the parts of a multi-word tx_noun.
	String[]	trms1=null,trms2=null;	//the termTxCpt_sAll of the parts of a multi-word.

	/**
	 * @modified 2004.07.25
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public TermTxNounCaseEng (String frm, String rl)
	{
		super(frm, "termTxNoun_Case", "eng");
		termTxCpt_sRule=rl;
	}

	/**
	 * @modified
	 * @since 2001.03.22
	 * @author HoKoNoUmo
	 */
	public TermTxNounCaseEng(String frm)
	{
		this(frm, null);
	}



	/**
	 * Finds the tx-cpt's-terms of a tx-noun, given its name.
	 * @modified 2006.02.05
	 * @since 2001.04.13
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		termTxCpt_sAll="";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();
		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
 //1) -es.
			if (	 termTxCpt_sName.endsWith("ch")
					|| termTxCpt_sName.endsWith("sh")
					|| termTxCpt_sName.endsWith("o") //2001.11.28
					//|| termTxCpt_sName.endsWith("s")
					|| termTxCpt_sName.endsWith("x")
				 )
			{
				//tor-ch,torches. 2001.03.14
				//bru-sh,brushes. 2001.03.14
				//tomat-o,tomatoes. 2001.03.14
				//bu-s,buses. 2001.03.14
				//bo-x,boxes
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnCs12");
			}

			else if ( termTxCpt_sName.endsWith("s")	 )
			{
				//bu-s,buses,bus's|bus',buses'.
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnCs13");
			}

	//2) f/fe
			else if (termTxCpt_sName.endsWith("f")||termTxCpt_sName.endsWith("fe"))
			{
				//lea-f,leaves
				//kni-fe,knives
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnCs21");
			}

	//3) y
			else if (termTxCpt_sName.endsWith("y")) //2001.06.04
			{
				//bo-y,bo-ys
				//entit-y,entit-ies
				if (isLetterVowelEnglish(getLastLettersIfSuffix(termTxCpt_sName,2).charAt(0)) )
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnCs11");
				else
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnCs22");
			}

			// >> s.
			else {
				//car,cars
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnCs11");
			}
		}
		return termTxCpt_sAll;
	}


	/**
	 * IF a word has a regular AND an irregular type, I don't write it. 2001.11.27
	 * @modified 2001.11.27
	 * @since 2001.04.13
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfIrregular()
	{
//we can extract them from file AAjKB/irregularNounEn.txt. 2003.11.23

//1) end in ch|sh|o|s|x end doesn't have plural -es
//Irregular is the word with -o -oes. 2001.11.28
//2) end in f|fe and NOT -ves
//3) end in y and not ys|ies
//uy ---> uies
//5) different termTxCpt_sAll.
//6) are same.

		note="it is irregular.";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();
		String line;
		String fin = AAj.AAj_sDir +"AAjKB" +File.separator
								+"AAjINDEXES" +File.separator
								+"eng" +File.separator
								+"iregularEngTxNoun.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fin));
			while ((line=br.readLine())!=null)
			{
				if (line.startsWith(termTxCpt_sName+","))
				{
					termTxCpt_sAll=line;
					break;
				}
			}
			br.close();
		}
		catch (FileNotFoundException fnfe)
		{
			JOptionPane.showMessageDialog(null,
				"TermTxNounCaseEng.getTermsOfTxConceptIfIrregular: FileNotFound \n iregularEngTxNoun.txt",
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,
				"TermTxNounCaseEng.getTermsOfTxConceptIfIrregular: IOException\n" +e.toString(),
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		return termTxCpt_sAll;
	}


	/**
	 * Finds the tx-cpt's-terms of a tx-noun, given its term's-rule,
	 * BUT first checks if it is irregular.
	 *
	 * @modified
	 * @since 2001.05.13
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRule(String rule)
	{
		termTxCpt_sAll="";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();

		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(rule);
		}

		return termTxCpt_sAll;
	}


	/**
	 * Finds the tx-cpt's-terms of a tx-noun, given its term's-rule.<p>
	 * PREREQUISITE:
	 *
	 * @modified 2009.09.29
	 * @since 2001.05.13
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly(String rule)
	{
		termTxCpt_sRule=rule;
		termTxCpt_sName=termTxCpt_sName.toLowerCase();
		stem=termTxCpt_sName;
		termTxCpt_sAll="";

// -s.
		if (termTxCpt_sRule.equals("rlEngTrmNnCs11"))
		{
			//car,cars,car's,cars'
			note="Rule rlEngTrmNnCs11: regular tx_nouns, make the plural with -s.";
			termTxCpt_sAll=	stem +","
							+stem +"s,"
							+stem +"'s,"
							+stem +"s'";
		}

//-es
		else if (termTxCpt_sRule.equals("rlEngTrmNnCs12"))
		{
			//tor-ch,torches. 2001.03.14
			//bru-sh,brushes. 2001.03.14
			//tomat-o,tomatoes. 2001.03.14
			//bo-x,boxes
			note="Rule rlEngTrmNnCs12: the tx-nouns that end with -ch|sh|o|x, "
					+"\n\tmake the plural with -es.";
			termTxCpt_sAll=	stem +","
						+stem +"es,"
						+stem +"'s,"
						+stem +"es'";
		}

		else if (termTxCpt_sRule.equals("rlEngTrmNnCs13"))
		{
			//bus,buses,bus's|bus',buses'
			note="Rule rlEngTrmNnCs13: the tx-nouns that end with -s, "
					+"\n\tmake the plural with -es.";
			termTxCpt_sAll=	stem +","
						+stem +"es,"
						+stem +"'s|" +stem +"',"
						+stem +"es'";
		}

		else if (termTxCpt_sRule.equals("rlEngTrmNnCs21"))
		{
			//lea-f,leaves
			//kni-fe,knives
			if ( termTxCpt_sName.endsWith("f") )
			{
				note="Rule rlEngTrmNnCs21: the tx-nouns that end with -f, \n\tmake the plural with -ves.";
				stem=getFirstLettersIfSuffix(termTxCpt_sName.toLowerCase(), 1);
				termTxCpt_sAll=	stem +"f,"
								+stem +"ves,"
								+stem +"f's,"
								+stem +"ves'";
			}
			else//ends with "fe"
			{
				note="Rule rlEngTrmNnCs21: the tx-nouns that end with -fe, \n\tmake the plural with -ves.";
				stem=getFirstLettersIfSuffix(termTxCpt_sName.toLowerCase(), 2);
				termTxCpt_sAll=	stem +"fe,"
								+stem +"ves,"
								+stem +"fe's,"
								+stem +"ves'";
			}
		}

		else if (termTxCpt_sRule.equals("rlEngTrmNnCs22"))
		{
			//bo-y,bo-ys
			//entit-y,entit-ies
			stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			note="Rule rlEngTrmNnCs22: the tx-nouns that end with -y with previous consonant, \n\tmake the plural with -ies.";
			termTxCpt_sAll=	stem +"y,"
							+stem +"ies,"
							+stem +"y's,"
							+stem +"ies'";
		}

//irregular
		else if (termTxCpt_sRule.equals("rlEngTrmNnCs31"))
		{
			termTxCpt_sAll=getTermsOfTxConceptIfIrregular();
		}
		else if (termTxCpt_sRule.equals("rlEngTrmNnCs32"))
		//2001.06.29 irregular: same
		//I need this rule to find the tx-cpt's-terms without searching
		//the irregulars.
		{
			note="Rule rlEngTrmNnCs32: irregular, same";
			if (termTxCpt_sName.endsWith("s"))
				termTxCpt_sAll=termTxCpt_sName+","
									+termTxCpt_sName+","
									+termTxCpt_sName+"'s|"+termTxCpt_sName+"',"
									+termTxCpt_sName +"'";
			else
				termTxCpt_sAll=termTxCpt_sName+","
									+termTxCpt_sName+","
									+termTxCpt_sName+"'s,"
									+termTxCpt_sName +"'s";
		}
		else if (termTxCpt_sRule.equals("rlEngTrmNnCs33"))
		//irregular: only singular 2006.02.01
		{
			note="Rule rlEngTrmNnCs32: irregular, only singular";
			if (termTxCpt_sName.endsWith("s"))
				termTxCpt_sAll=termTxCpt_sName+",-,"
								 +termTxCpt_sName+"'s|"+termTxCpt_sName+"',-";
			else
				termTxCpt_sAll=termTxCpt_sName+",-,"
								 +termTxCpt_sName+"'s,-";
		}
		else if (termTxCpt_sRule.equals("rlEngTrmNnCs34"))
		//irregular: only plural 2006.02.01
		{
			note="Rule rlEngTrmNnCs32: irregular, only plural";
			termTxCpt_sAll="-,"
								+termTxCpt_sName
								+",-,"
								+termTxCpt_sName+"'";
		}

//POLYLECTIC NAMES:
//MultiWord-Change-First
		else if (termTxCpt_sRule.startsWith("rlEngTrmNnCs4a"))
		{
			note="Rule rlEngTrmNnCs4a: multiword, change first word";
			//definition of a concept, definitions of a concept.
			//part1 = the first word
			part1=termTxCpt_sName.substring(0,termTxCpt_sName.indexOf(" "));
			part2=termTxCpt_sName.substring(termTxCpt_sName.indexOf(" ")+1);
			String rl=termTxCpt_sRule.substring(termTxCpt_sRule.length()-2);
			trms1=Textual.getTermsOfTxConceptAsArray(part1,"rlEngTrmNnCs"+rl);
			note="Rule "+termTxCpt_sRule+": multiWord-change-first";
			termTxCpt_sAll= trms1[0] +" " +part2 +","
						+trms1[1] +" " +part2
						+",-,-";
		}
//IF changes only the last word, THEN we use the ONE_WORD rules
/*
		else if (termTxCpt_sRule.startsWith("rlEngTrmNnCs4b"))
		{
			//concept's definition, concept's definitions,
			part1=termTxCpt_sName.substring(0,termTxCpt_sName.lastIndexOf(" "));
			part2=termTxCpt_sName.substring(termTxCpt_sName.lastIndexOf(" ")+1,termTxCpt_sName.length());
			String rl=termTxCpt_sRule.substring(5,termTxCpt_sRule.length());
			trms2=Textual.getTermsOfTxConceptAsArray(part2,"rlEngTrmNnCs"+rl);
			termTxCpt_sAll= part1 +"-" +trms2[0] +","
						+part1 +"-" +trms2[1];
		}

//POLYLECTIC SPECIFIC
		else if (termTxCpt_sRule.startsWith("rlEngTrmNnCs5"))
		{
			//case: generic-concept,generic-concepts.
			//case: concept-and-referent corelaton, concept-and-referent corelatons,
			//the firs-form can be: 'generic concept' or 'generic-concept' 2001.09.25
			part1=termTxCpt_sName.substring(0,termTxCpt_sName.lastIndexOf(" ")); //wrkarnd
			part2=termTxCpt_sName.substring(termTxCpt_sName.lastIndexOf(" ")+1,termTxCpt_sName.length());
			String rl=termTxCpt_sRule.substring(12,termTxCpt_sRule.length());//wrkarnd
			trms2=Textual.getTermsOfTxConceptAsArray(part2,"rlEngTrmNnCs"+rl);
//			if (trms2.length==2)
//			{
				termTxCpt_sAll= part1 +" " +trms2[0] +","
							+part1 +" " +trms2[1];
//			}
//			else	//one form. 2001.06.29
//				termTxCpt_sAll= part1 +" " +trms2[0];
		}
*/

//MultiWord-Change-LAST
		else if (termTxCpt_sRule.startsWith("rlEngTrmNnCs4b"))
		{
			//definition of a concept, definitions of a concept.
			//part2 = the last word
			note="Rule rlEngTrmNnCs4b: multiword, change last word";
			part1=termTxCpt_sName.substring(0,termTxCpt_sName.lastIndexOf(" "));
			part2=termTxCpt_sName.substring(termTxCpt_sName.lastIndexOf(" ")+1);
			String rl=termTxCpt_sRule.substring(termTxCpt_sRule.length()-2);
			trms2=Textual.getTermsOfTxConceptAsArray(part2,"rlEngTrmNnCs"+rl);
			note="Rule "+termTxCpt_sRule+": multiWord-change-last";
			termTxCpt_sAll=part1 +" " +trms2[0] +","
						+part1 +" " +trms2[1]
						+",-,-";
		}

//MultiWord-Change-BOTH
		else if (termTxCpt_sRule.startsWith("rlEngTrmNnCs4c"))
		{
			//process or relation, processes or relations
			note="Rule rlEngTrmNnCs4a"
					+termTxCpt_sRule.substring(termTxCpt_sRule.length()-4)
					+": multiword, change first and last words";
			part1=termTxCpt_sName.substring(0,termTxCpt_sName.indexOf(" "));
			part2=termTxCpt_sName.substring(termTxCpt_sName.indexOf(" "),termTxCpt_sName.lastIndexOf(" ")+1);
			part3=termTxCpt_sName.substring(termTxCpt_sName.lastIndexOf(" ")+1);
			String rl=termTxCpt_sRule.substring(termTxCpt_sRule.length()-4, termTxCpt_sRule.length()-2);
			String rl2=termTxCpt_sRule.substring(termTxCpt_sRule.length()-2);
			trms1=Textual.getTermsOfTxConceptAsArray(part1,"rlEngTrmNnCs"+rl);
			trms2=Textual.getTermsOfTxConceptAsArray(part3,"rlEngTrmNnCs"+rl2);
			termTxCpt_sAll= trms1[0] +part2 +trms2[0] +","
						+trms1[1] +part2 +trms2[1] +",-,-";
		}

		else if (termTxCpt_sRule.equals("")||termTxCpt_sRule.equals("none"))
		{
			note="no term's-rule.";
			termTxCpt_sAll=termTxCpt_sName+",-,-,-";
		}

//unknown case
		else {
			note="unkown term's-rule.";
			termTxCpt_sAll=termTxCpt_sName+",-,-,-";
		}

		return termTxCpt_sAll;
	}

}