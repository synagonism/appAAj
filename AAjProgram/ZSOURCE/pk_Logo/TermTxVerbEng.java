/**
 * TermTxVerbEng.java - The english-tx_verb class.
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
 * Contains the lg-concept's--term-rules of english tx_verbs.<p>
 *
 * We need 5 termTxCpt_sAll to create all english instance-tx_verbs:<p><code>
 * trmVrb1) present=							write|advise,<br/>
 * trmVrb2) present third person=	writes|advises.<br/>
 * trmVrb3) past participle=			written|advised,<br/>
 * trmVrb4) present participle=		writing|advising,<br/>
 * trmVrb5) past=								wrote|advised,</code>
 *
 * @modified 2008.12.16
 * @since 2001.03.14 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TermTxVerbEng
	extends Term_TxConcept
{


	/**
	 * @modified 2004.09.05
	 * @since 2001.03.14
	 * @author HoKoNoUmo
	 */
	public TermTxVerbEng (String nam, String rl)
	{
		super(nam, "term_TxVerb", "eng");
		termTxCpt_sRule=rl;
	}

	/**
	 * @modified 2004.09.05
	 * @since 2001.03.16
	 * @author HoKoNoUmo
	 */
	public TermTxVerbEng(String nam)
	{
		this(nam, null);
	}


	/**
	 * @modified 2004.09.08
	 * @since 2004.09.08 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TermTxVerbEng ()
	{
		super("tx_verb", "eng");
	}



	/**
	 * @modified 2001.05.24
	 * @since 2001.04.13
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		termTxCpt_sName=termTxCpt_sName.toLowerCase();

		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
// -PP
				if (isLetterConsonantEnglish(termTxCpt_sName.charAt( termTxCpt_sName.length()-1 ))
							&& termTxCpt_sName.charAt(termTxCpt_sName.length()-1)!='x'
							&& isLetterVowelEnglish( termTxCpt_sName.charAt(termTxCpt_sName.length()-2) ) )
			{
				//ends with consonant with previous vowel with accent.
				//open,open-ed
				//stop,stop-ped,stopping,stops
				//ask for accent.
				int	 result= JOptionPane.showConfirmDialog(null,
				"Is the ACCENT on last syllable?",
				"Choose One",JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION)
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmVrb12");
				}
				else if(result == JOptionPane.NO_OPTION)
				{
					if (termTxCpt_sName.endsWith("l"))
					{
						//travel,travelled|traveled,travelling|traveling,travels
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmVrb13");
					}
					else {
						//climb,climbed,climbing,climbs
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmVrb11");
					}
				}
			}

// -E
			else if (termTxCpt_sName.endsWith("e"))
			{
				//like,liked,liking,likes
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmVrb21");
			}

// -ES.
			else if (	 termTxCpt_sName.endsWith("ch")
							|| termTxCpt_sName.endsWith("sh")
							|| termTxCpt_sName.endsWith("o")
							|| termTxCpt_sName.endsWith("ss")
							|| termTxCpt_sName.endsWith("x")
							)
			{
				//ch,sh,o,ss,x
				//miss,misses
				//watch,watches
				//finish,finishes
				//go,goes
				//mix,mixes
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmVrb14");
			}

// -Y
			else if (termTxCpt_sName.endsWith("y"))
			{
				//study,studied,studying,studies
				//play,played,playing,plays
				stem=getFirstLettersIfSuffix(termTxCpt_sName.toLowerCase(), 1);
				if (!isLetterVowelEnglish(getLastLettersIfSuffix(stem,1).charAt(0)) )
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmVrb22");
				}
				else termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmVrb11");
			}

// REGULAR.
			else {
				//climb,climbed,climbing,climbs
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlEngTrmVrb11");
			}
		}
		return termTxCpt_sAll;
	}//enf of findTermsOfTxConceptIfName


	/**
	 * @modified 2006.02.01
	 * @since 2001.04.13
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfIrregular()
	{
		note="it is irregular.";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();
		String line;
		String fin = AAj.AAj_sDir +"AAjKB" +File.separator
								+"AAjINDEXES" +File.separator
								+"eng" +File.separator
								+"iregularEngTxVerb.txt";
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
				"TermTxVerbEng.getTermsOfTxConceptIfIrregular: FileNotFound \n tx_nonstopNode/iregularEngTxVerb.txt",
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,
				"TermTxVerbEng.getTermsOfTxConceptIfIrregular: IOException\n" +e.toString(),
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		return termTxCpt_sAll;
	}


	/**
	 * @modified 2004.09.05
	 * @since 2001.05.24
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRule(String termTxCpt_sRule)
	{
		termTxCpt_sName=termTxCpt_sName.toLowerCase();

		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(termTxCpt_sRule);
		}

		return termTxCpt_sAll;
	}


	/**
	 * @modified 2004.09.05
	 * @since 2001.05.23
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly(String rule)
	{
		termTxCpt_sRule=rule;
		termTxCpt_sName=termTxCpt_sName.toLowerCase();
		stem=termTxCpt_sName;

//-ed-ing-s
		if (termTxCpt_sRule.equals("rlEngTrmVrb11"))
		{
				//climb,climbed,climbing,climbs
				note="rlEngTrmVrb11: regular.";
				termTxCpt_sAll=	stem +","
								+stem +"s,"
								+stem +"ed,"
								+stem +"ing,"
								+stem +"ed";
		}

// -PP
		else if (termTxCpt_sRule.equals("rlEngTrmVrb12"))
		{
				//ends with consonant with previous vowel with accent.
				//sit,swim, NOT open.
				//stop,stopped,stopping,stops
				//ask for accent.
				String lc=getLastLettersIfSuffix(stem,1);
				note="rlEngTrmVrb12: verbs that end with consonant with previous vowel and accent on last syllable,"
							+"\n\tdouble the last consonant.";
				termTxCpt_sAll=	stem +","
								+stem +"s,"
								+stem +lc +"ed,"
								+stem +lc +"ing,"
								+stem +lc +"ed";
		}

		else if (termTxCpt_sRule.equals("rlEngTrmVrb13"))
		{
				//travel,travelled|traveled,travelling|traveling,travels
				String lc=getLastLettersIfSuffix(stem,1);
				note="rlEngTrmVrb13: ends with consonant l with previous vowel and accent NOT on last syllable.";
				termTxCpt_sAll=	stem +","
								+stem +"s,"
								+stem +"led|" +stem +"ed,"
								+stem +"ling|" +stem +"ing,"
								+stem +"led|" +stem +"ed";
		}

		else if (termTxCpt_sRule.equals("rlEngTrmVrb14"))
		{
				//ch,sh,o,ss,x
				//miss,misses
				//watch,watches
				//finish,finishes
				//go,goes
				//mix,mixes
				note="rlEngTrmVrb14: tx_verbs that end with -ch|sh|o|ss|x, use -es instead of -s.";
				termTxCpt_sAll=	stem +","
								+stem +"es,"
								+stem +"ed,"
								+stem +"ing,"
								+stem +"ed";
		}

// -E
		else if (termTxCpt_sRule.equals("rlEngTrmVrb21"))
		{
				//like,liked,liking,likes
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
				String lc=getLastLettersIfSuffix(stem,1);
				note="rlEngTrmVrb21: ends with -e.";
				termTxCpt_sAll=	stem +"e,"
								+stem +"es,"
								+stem +"ed,"
								+stem +"ing,"
								+stem +"ed";
		}

// -Y
		else if (termTxCpt_sRule.equals("rlEngTrmVrb22"))
		{
				//study,studied,studying,studies
				//play,played,playing,plays
				stem=getFirstLettersIfSuffix(termTxCpt_sName.toLowerCase(), 1);
				note="rlEngTrmVrb22: ends with -y with previous consonant.";
				termTxCpt_sAll=	stem +"y,"
								+stem +"ies,"
								+stem +"ied,"
								+stem +"ying,"
								+stem +"ied";
		}

//irregular
		else if (termTxCpt_sRule.equals("rlEngTrmVrb31"))
		{
			termTxCpt_sAll=getTermsOfTxConceptIfIrregular();
		}

		else if (termTxCpt_sRule.equals("")||termTxCpt_sRule.equals("none"))
		{
			note="no term's-rule.";
			termTxCpt_sAll=termTxCpt_sName+",-,-,-,-";
		}

//unknown case
		else {
			note="unkown term's-rule.";
			termTxCpt_sAll=termTxCpt_sName+",-,-,-,-";
		}

		return termTxCpt_sAll;
	}

}
