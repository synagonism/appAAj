/**
 * TermTxNounCaseEla.java - The ancient greek case-noun class.
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

import pk_Util.Util;

import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Contains the lg-concept's--term-rules of ancient-greek tx_nouns.
 * @modified 2004.07.25
 * @since 2001.06.08 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TermTxNounCaseEla
	extends Term_TxConcept
{
		int tonos=-1;


	/**
	 * @modified 2004.07.25
	 * @since 2001.06.08
	 * @author HoKoNoUmo
	 */
	public TermTxNounCaseEla (String nm, String rl)
	{
		super(nm, "termTxNoun_Case", "ela");
		termTxCpt_sRule=rl;
	}


	/**
	 * @modified
	 * @since 2001.06.08
	 * @author HoKoNoUmo
	 */
	public TermTxNounCaseEla (String frm)
	{
		this(frm, null);
	}

//*********************************************************************
// ALPHABETICALLY
//*********************************************************************

	/**
	 * @return	All termTxCpt_sAll of the tx-noun as: form1,form2,form3,form4.
	 *					The program finds the type of tx_noun.
	 *					If it needs more information, asks it from the user.
	 * @modified
	 * @since 2001.06.08
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));
		String stem="",stem2="",stem3="";
		String termTxCpt_sAll="";
		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{

// -α
			if (termTxCpt_sName.endsWith("\u03ac"))//ά
			{
					//χρειάζεται επιπρόσθετη πληροφορία. Είναι ισοσύλλαβο ή όχι;
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -ά έχουμε 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΟΥΣΙΑΣΤΙΚΩΝ"
						+"\nΙσοσύλλαβη (καρδιά,καρδιές) και ανισοσύλλαβη (γιαγιά,γιαγιάδες)."
						+"\nΝα κλιθεί σαν ΙΣΟΣΥΛΛΑΒΟ;","Να κλιθεί σαν ΙΣΟΣΥΛΛΑΒΟ;",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
					{
						//η καρδιά,καρδιάς,καρδιές,καρδιών
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2a1");
					}
					else {
						//η γιαγιά,γιαγιάς,γιαγιάδες,γιαγιάδων
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2a2");
					}
			}
			else if (termTxCpt_sName.endsWith("α"))
			{
				Object[] options = {
						"1) να κλιθεί σαν θηλυκό με τη γενική-πληθ. να τονίζεται στη λήγουσα (ώρα,ωρών)",
						"2) να κλιθεί σαν θηλυκό με τη γενική-πληθ. να ΜΗΝ τονίζεται στη λήγουσα (ελπίδα,οντότητα)",
						"3) να κλιθεί σαν ουδέτερο (κύμα,όνομα)"};
				Object selectedValue = JOptionPane.showInputDialog(null,
									"Με κατάληξη -α υπάρχουν 3 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΟΥΣΙΑΣΤΙΚΩΝ", //message
									"Επέλεξε ΕΝΑ",																	//title
									JOptionPane.INFORMATION_MESSAGE,								//message type
									null,																						//icon
									options,																				//values to select
									options[0]);																		//initial selection
				if (((String)selectedValue).startsWith("1)"))
				{
						//η θάλασσα
						//η ώρα,ώρας,ώρες,ωρών
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2a3");
				}
				else if (((String)selectedValue).startsWith("2)"))
				{
						//η ελπίδα,ελπίδας,ελπίδες,ελπίδων
						//η οντότητα,οντότητας,οντότητες,οντοτήτων
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2a4");
				}
				else if (((String)selectedValue).startsWith("3)"))
				{
						//το κύμα,κύματος,κύματα,κυμάτων
						//το όνομα,ονόματος,ονόματα,ονομάτων
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs3a1");
				}
			}

		}

		return termTxCpt_sAll;
	}


	/**
	 * @modified
	 * @since 2001.06.08
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfIrregular()
	{
		String termTxCpt_sAll="";
		String mfform=termTxCpt_sName;
		note="είναι ανώμαλο ουσιαστικό.";

//ΑΝΩΜΑΛΑ ΑΛΦΑΒΗΤΙΚΑ:
		if (mfform.equals("άλφα"))
		{
			termTxCpt_sAll="άλφα";
		}
		else if (mfform.equals("βήτα"))
		{
			termTxCpt_sAll="βήτα";
		}
		return termTxCpt_sAll;
	}//getTermsOfTxConceptIfIrregular.

	/**
	 * @modified
	 * @since 2001.06.08
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRule(String termTxCpt_sRule)
	{
		String termTxCpt_sAll="";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();

		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(termTxCpt_sRule);
		}

		return termTxCpt_sAll;
	}//getTermsOfTxConceptIfRule.

	/**
	 * Find the different tx-cpt's-terms of the tx-noun given its 'type'.
	 * @modified
	 * @since 2001.06.08
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly(String rule)
	{
		termTxCpt_sRule=rule;
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));
		String stem="",stem2="",stem3="";
		String termTxCpt_sAll="";

//ΑΡΣΕΝΙΚΑ
		if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj1a1"))
		{
			//ο τιτάν-τιτάανος
			//ο κλητήρ-κλητήηρος
			//ο χειμών-χειμώωνος
			//ο ιχώρ-ιχώωρος
			//ο έλλην-έλληνος
			if (greekTonosFind(termTxCpt_sName)==1)
			{
					note="Κλίθηκε με τον rlElnTrmNnCs1a1(ο σφουγγαράς): αρσενικό -ας οξύτονο.";
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					termTxCpt_sAll=	"ο "
								+stem +"άς,"
								+stem +"ά,"
								+stem +"ά,"
								+stem +"ά,"
								+stem +"άδες,"
								+stem +"άδων,"
								+stem +"άδες,"
								+stem +"άδες";
			}
			else if (greekTonosFind(termTxCpt_sName)==2)
			{
					//ο αγώνας,αγώνα,αγώνες,αγώνων
					//ΕΞΑΙΡΕΣΗ: τα δισύλαβα και αυτά που τελειώνουν σε -ίας,
					//					τονίζουν τη γεν-πληθ στη λήγουσα.
					//					ταμίας,ταμία,ταμίες,ταμιών
					//					μήνας,μήνα,μήνες,μηνών.
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					stem2=greekTonosRemove(stem);
					if (	 findNumberOfGreekVowels(termTxCpt_sName)==2
							|| termTxCpt_sName.endsWith("ίας") )
					{
						note="Κλίθηκε με τον rlElnTrmNnCs1a1(ο ταμίας): αρσενικό -ας παροξύτονο δισύλλαβο σε -ίας.";
						termTxCpt_sAll=	"ο "
								+stem +"ας,"
								+stem +"α,"
								+stem +"α,"
								+stem +"α,"
								+stem +"ες,"
								+stem2 +"ών,"
								+stem +"ες,"
								+stem +"ες";
					}
					else {
						note="Κλίθηκε με τον rlElnTrmNnCs1a1(ο αγώνας): αρσενικό -ας παροξύτονο.";
						termTxCpt_sAll=	"ο "
								+stem +"ας,"
								+stem +"α,"
								+stem +"α,"
								+stem +"α,"
								+stem +"ες,"
								+stem +"ων,"
								+stem +"ες,"
								+stem +"ες";
					}
			}
			else if (greekTonosFind(termTxCpt_sName)==3)
			{
				//μάστορας στον πληθυντικό κυρίως μαστόροι,μαστόρων, μαστόρους όπως (άγγελος) 2001.04.13
					//ο φύλακας,φύλακα,φύλακες,φυλάκων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					stem2=greekTonosIncrease(stem);
					note="Κλίθηκε με τον rlElnTrmNnCs1a1(ο φύλακας): αρσενικό -ας προπαροξύτονο.";
					termTxCpt_sAll=	"ο "
								+stem +"ας,"
								+stem +"α,"
								+stem +"α,"
								+stem +"α,"
								+stem +"ες,"
								+stem2 +"ων,"
								+stem +"ες,"
								+stem +"ες";
			}
		}


//ΑΝΩΜΑΛΑ
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmNnCs4a1")) //2001.05.13
		{
			termTxCpt_sAll=getTermsOfTxConceptIfIrregular();
		}

		else if (termTxCpt_sRule.equals("")||termTxCpt_sRule.equals("none"))
		{
			note="χωρίς κανόνα-κλίσης.";
			termTxCpt_sAll=termTxCpt_sName+",-";
		}

		//unknown case
		else {
			termTxCpt_sAll=termTxCpt_sName+",-";
		}

		return termTxCpt_sAll;
	}//end getTermsOfTxConceptIfRuleOnly.

//*********************************************************************
// BUILDING OPERATIONS
//*********************************************************************


}

