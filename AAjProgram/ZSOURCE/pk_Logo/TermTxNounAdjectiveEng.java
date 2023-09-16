/**
 * TermTxNounAdjectiveEng.java - The english-tx_nounAdjective class.
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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Contains the rules of a english tx-nounAdjectives.
 *
 * @modified 2004.07.25
 * @since 2001.05.27 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TermTxNounAdjectiveEng
	extends Term_TxConcept
{


	/**
	 * @modified 2004.09.07
	 * @since 2001.05.27
	 * @author HoKoNoUmo
	 */
	public TermTxNounAdjectiveEng (String nam, String rl)
	{
		super(nam, "termTxNoun_Adjective", "eng");
		termTxCpt_sRule=rl;
	}


	/**
	 * @modified 2004.09.07
	 * @since 2001.05.27
	 * @author HoKoNoUmo
	 */
	public TermTxNounAdjectiveEng (String nam)
	{
		this(nam, null);
	}


	/**
	 * @modified 2001.06.09
	 * @since 2001.05.27
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		termTxCpt_sAll="";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();
		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
			//We need extra information
			Object[] options = {
					"1) has ONE syllable (LOOONG vowels=2 syllables)",
					"2) has TWO syllable",
					"3) has MORE than TWO syllables"};
			Object selectedValue = JOptionPane.showInputDialog(null,
								"We need the number of syllables",					//message
								"Choose One",																//title
								JOptionPane.INFORMATION_MESSAGE,						//message type
								null,																			//icon
								options,																	//values to select
								options[2]);																//initial selection
			if (((String)selectedValue).startsWith("1)"))
			{
					if (isLetterConsonantEnglish(termTxCpt_sName.charAt( termTxCpt_sName.length()-1 ))
							&& isLetterVowelEnglish( termTxCpt_sName.charAt(termTxCpt_sName.length()-2) ) )
					//fat,fat-ter,fat-test
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnAj12");
				else
					//dark,dark-er,dark-est
					//wise,wise-r,wise-st
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnAj11");
			}
			else if (((String)selectedValue).startsWith("2)"))
			{
				if (termTxCpt_sName.endsWith("y")) //happy,happier,happiest
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnAj21");
				else //nnn Is it correct? 2006.02.05
					//cool,cool-er,cool-est.
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnAj11");
			}
			else if (((String)selectedValue).startsWith("3)"))
			{
				//careful,more-careful,most-careful
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmNnAj31");
			}
		}

		return termTxCpt_sAll;
	}


	/**
	 * @modified 2008.04.05
	 * @since 2001.05.27
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfIrregular()
	{
		note="It is an irregular tx_nounAdjective.";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();
		String line;
		String fin = AAj.AAj_sDir +"AAjKB" +File.separator
								+"AAjINDEXES" +File.separator
								+"eng" +File.separator
								+"iregularEngTxNounAdjective.txt";
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
				"TermTxNounAdjectiveEng.getTermsOfTxConceptIfIrregular: FileNotFound \n tx_nonstopNode/iregularEngTxNounAdjective.txt",
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,
				"TermTxNounAdjectiveEng.getTermsOfTxConceptIfIrregular: IOException\n" +e.toString(),
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		return termTxCpt_sAll;
	}

	/**
	 * @modified 2001.06.09
	 * @since 2001.05.27
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRule(String rule)
	{
		termTxCpt_sName=termTxCpt_sName.toLowerCase();

		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(rule);
		}

		return termTxCpt_sAll;
	}

	/**
	 * Finds the different tx-concept's-terms of an tx_nounAdjective given its termTxCpt_sRule.
	 * @modified 2001.06.09
	 * @since 2001.05.27
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly(String rule)
	{
		termTxCpt_sRule=rule;
		termTxCpt_sName=termTxCpt_sName.toLowerCase();

//1) adding sufix:
		if (termTxCpt_sRule.equals("rlEngTrmNnAj11"))
		{
			if (termTxCpt_sName.endsWith("e"))
			{
				note="Rule rlEngTrmNnAj11: wise,wise-r,wise-st.";
				termTxCpt_sAll= termTxCpt_sName+"," +termTxCpt_sName+"r,"
						+termTxCpt_sName+"st";
			}
			else {
				note="Rule rlEngTrmNnAj11: dark,dark-er,dark-est.";
				termTxCpt_sAll= termTxCpt_sName+"," +termTxCpt_sName+"er," +termTxCpt_sName+"est";
			}
		}

		else if (termTxCpt_sRule.equals("rlEngTrmNnAj12"))
		{
			String lc=getLastLettersIfSuffix(termTxCpt_sName,1);
			note="Rule rlEngTrmNnAj12: fat,fat-ter,fat-test.";
			termTxCpt_sAll= termTxCpt_sName +","
						+termTxCpt_sName +lc +"er,"
						+termTxCpt_sName +lc +"est";
		}

//2) changing suffix:
		else if (termTxCpt_sRule.equals("rlEngTrmNnAj21"))
		{
			stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			note="Rule rlEngTrmNnAj21: happ-y,happ-ier,happ-iest.";
			termTxCpt_sAll= stem +"y,"
						+stem +"ier,"
						+stem +"yiest";
		}

//3) adding words:
		else if (termTxCpt_sRule.equals("rlEngTrmNnAj31"))
		{
			note="Rule rlEngTrmNnAj31: careful, more careful, most careful.";
			termTxCpt_sAll= termTxCpt_sName +","
						+"more " +termTxCpt_sName +","
						+"most " +termTxCpt_sName;
		}

//4) irregular:
		else if (termTxCpt_sRule.equals("rlEngTrmNnAj41"))
		{
			note="Rule rlEngTrmNnAj41: irregular.";
			termTxCpt_sAll=getTermsOfTxConceptIfIrregular();
		}
		//5) without degrees:
		else if (termTxCpt_sRule.equals("rlEngTrmNnAj42"))
		{
			note="Rule rlEngTrmNnAj42: irregular: no degrees of comparison (eg round).";
			termTxCpt_sAll=termTxCpt_sName +",-,-";
		}

		else if (termTxCpt_sRule.equals("")||termTxCpt_sRule.equals("none"))
		{
			note="without rule.";
			termTxCpt_sAll=termTxCpt_sName+",-,-";
		}

//unknown case
		else {
			note="UNKNOWN term's-rule.";
			termTxCpt_sAll=termTxCpt_sName +",?,?";
		}

		return termTxCpt_sAll;
	}//end getTermsOfTxConceptIfRuleOnly.

}


