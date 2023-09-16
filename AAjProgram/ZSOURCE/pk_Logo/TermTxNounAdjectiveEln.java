/**
 * TermTxNounAdjectiveEln.java - The greek-tx_nounAdjective class.
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
import pk_Util.Textual;

import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Contains the lg-concept's--term-rules of greek tx_nounAdjectives.
 * @modified 2004.07.25
 * @since 2001.05.17
 * @author HoKoNoUmo
 */
public class TermTxNounAdjectiveEln
	extends Term_TxConcept
{
		int tonos=-1;

	/**
	 * @modified 2004.07.25
	 * @since 2001.05.17
	 * @author HoKoNoUmo
	 */
	public TermTxNounAdjectiveEln (String nm, String rl)
	{
		super(nm, "termTxNoun_Adjective", "eln");
		termTxCpt_sRule=rl;
	}


	/**
	 * @modified
	 * @since 2001.05.17
	 * @author HoKoNoUmo
	 */
	public TermTxNounAdjectiveEln (String nm)
	{
		this(nm, null);
	}


	/**
	 * Returns all the tx-concept's-terms of the tx-nounAdjective as: nm1,nm2,....<br/>
	 * ALGORITHM:<br/>
	 *		- The program unsing the <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango/term-rule_eln.html#ruleNameNnCs">
	 *		termTxCpt_sName-rules</a> finds the <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango/term-rule_eln.html#ruleTermNnC">
	 *		tx_noun-creating-rules</a>.<br/>
	 *		- If it needs more information, asks it from the user.
	 *
	 * @modified 2001.05.27
	 * @since 2001.05.17
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));
		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{

// -α
			if (termTxCpt_sName.endsWith("α"))
			{
					//χρειάζεται επιπρόσθετη πληροφορία.
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -α (θηλυκό) η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
						+"\nΜε αρσενικό -ος (ωραίος-ωραία-ωραίο) και με -ης (ζηλιάρης-ζηλιάρα-ζηλιάρικο)."
						+"\nΝα κλιθεί σαν το 'ωραίος';","Να κλιθεί σαν το 'ωραίος';",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
					{
						//ωραίος-ωραία-ωραίο
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12");
					}
					else {
						//ζηλιάρης-ζηλιάρα-ζηλιάρικο
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj24");
					}
			}
			else if (termTxCpt_sName.endsWith("ά"))
			{
				Object[] options = {
								"1) να κλιθεί σαν το γλυκός-γλυκιά-γλυκό (rlElnTrmAdj12)",
								"2) να κλιθεί σαν το βαθύς-βαθιά-βαθύ (rlElnTrmAdj21)",
								"3) να κλιθεί σαν το σταχτής-σταχτιά-σταχτί (rlElnTrmAdj22)",
														};
				Object selectedValue = JOptionPane.showInputDialog(null,
									"Δώσε επιπρόσθετη πληροφορία"
									+"\nΜε κατάληξη -ά (θηλυκό) η γλώσσα έχει 3 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ", //message
									"Επέλεξε ΕΝΑ",																	//title
									JOptionPane.INFORMATION_MESSAGE,								//message type
									null,																						//icon
									options,																				//values to select
									options[0]);																		//initial selection
				if (((String)selectedValue).startsWith("1)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12");
				}
				else if (((String)selectedValue).startsWith("2)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj21");
				}
				else if (((String)selectedValue).startsWith("3)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj22");
				}
			}

//-ι:
			//-η
			else if (termTxCpt_sName.endsWith("έστερη")||termTxCpt_sName.endsWith("έστατη"))
			{
			int result= JOptionPane.showConfirmDialog(null,
				"Με κατάληξη -έστερη/έστατη (θηλυκού) η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
				+"\nΟξύτονο 'ανώμαλο' (συνεχής-συνεχής-συνεχές) και"
				+"\nΠαροξύτονο 'ανώμαλο' (ελώδης-ελώδης-ελώδες)."
				+"\nΝα κλιθεί σαν το 'συνεχής';","Να κλιθεί σαν το 'συνεχής';",JOptionPane.YES_NO_OPTION);
			if(result == JOptionPane.YES_OPTION)
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23a");
			else
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23b");
		}
			else if (termTxCpt_sName.endsWith("ότερη")||termTxCpt_sName.endsWith("ότατη"))
			{
			Object[] options = {
						"1) να κλιθεί σαν το δυνατός-δυνατή-δυνατό		(rlElnTrmAdj11a)",
						"2) να κλιθεί σαν το άσπρος-άσπρη-άσπρο				(rlElnTrmAdj11b)",
						"3) να κλιθεί σαν το όμορφος-όμορφη-όμορφο		(rlElnTrmAdj11c)",
						"4) να κλιθεί σαν το γλυκός-γλυκιά-γλυκό			(rlElnTrmAdj12a)",
						"5) να κλιθεί σαν το ωραίος-ωραία-ωραίο				(rlElnTrmAdj12b)",
						"6) να κλιθεί σαν το πλούσιος-πλούσια-πλούσιο (rlElnTrmAdj12c)",
													};
			Object selectedValue = JOptionPane.showInputDialog(null,
								"Δώσε επιπρόσθετη πληροφορία"
								+"\nΜε κατάληξη -ότερη/ότατη η γλώσσα έχει 6 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ",	//message
								"Επέλεξε ΕΝΑ",																	//title
								JOptionPane.INFORMATION_MESSAGE,								//message type
								null,																						//icon
								options,																				//values to select
								options[0]);																		//initial selection
				if (((String)selectedValue).startsWith("1)"))
				{
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11a");
					}
				else if (((String)selectedValue).startsWith("2)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11b");
					}
				else if (((String)selectedValue).startsWith("3)"))
				{
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11c");
					}
				else if (((String)selectedValue).startsWith("4)"))
				{
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12a");
					}
				else if (((String)selectedValue).startsWith("5)"))
				{
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12b");
					}
				else if (((String)selectedValue).startsWith("6)"))
				{
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12c");
					}
				}
			else if (termTxCpt_sName.endsWith("ύτερη")||termTxCpt_sName.endsWith("ύτατη"))
				{
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj21");
			}
				else if (termTxCpt_sName.endsWith("ή")||termTxCpt_sName.endsWith("η"))
			{
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11");
			}
			//-ι
				else if (termTxCpt_sName.endsWith("ί"))
			{
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj22");
			}
			//-υ
				else if (termTxCpt_sName.endsWith("ού"))
			{
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj31");
			}
				else if (termTxCpt_sName.endsWith("ύ"))
			{
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj21");
			}

//-ο:
			else if (termTxCpt_sName.endsWith("άδικο"))
			{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj31");
			}
			else if (termTxCpt_sName.endsWith("ικο"))
			{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj24");
			}
			else if (termTxCpt_sName.endsWith("έστερο")||termTxCpt_sName.endsWith("έστατο"))
			{
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -έστερο/έστατο (ουδέτερο) η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
						+"\nΟξύτονο 'ανώμαλο' (συνεχής-συνεχής-συνεχές) και"
						+"\nΠαροξύτονο 'ανώμαλο' (ελώδης-ελώδης-ελώδες)."
						+"\nΝα κλιθεί σαν το 'συνεχής';","Να κλιθεί σαν το 'συνεχής';",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23a");
					else
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23b");
			}
			else if (termTxCpt_sName.endsWith("ότερο")||termTxCpt_sName.endsWith("ότατο"))
			{
				Object[] options = {
														"1) να κλιθεί σαν το δυνατός-δυνατή-δυνατό		(rlElnTrmAdj11a)",
														"2) να κλιθεί σαν το άσπρος-άσπρη-άσπρο				(rlElnTrmAdj11b)",
														"3) να κλιθεί σαν το όμορφος-όμορφη-όμορφο		(rlElnTrmAdj11c)",
														"4) να κλιθεί σαν το γλυκός-γλυκιά-γλυκό			(rlElnTrmAdj12a)",
														"5) να κλιθεί σαν το ωραίος-ωραία-ωραίο				(rlElnTrmAdj12b)",
														"6) να κλιθεί σαν το πλούσιος-πλούσια-πλούσιο (rlElnTrmAdj12c)",
														};
				Object selectedValue = JOptionPane.showInputDialog(null,
									"Δώσε επιπρόσθετη πληροφορία"
									+"\nΜε κατάληξη -ότερο/ότατο η γλώσσα έχει 6 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ",	//message
									"Επέλεξε ΕΝΑ",																	//title
									JOptionPane.INFORMATION_MESSAGE,								//message type
									null,																						//icon
									options,																				//values to select
									options[0]);																		//initial selection
				if (((String)selectedValue).startsWith("1)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11a");
				}
				else if (((String)selectedValue).startsWith("2)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11b");
				}
				else if (((String)selectedValue).startsWith("3)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11c");
				}
				else if (((String)selectedValue).startsWith("4)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12a");
				}
				else if (((String)selectedValue).startsWith("5)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12b");
				}
				else if (((String)selectedValue).startsWith("6)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12c");
				}
			}
			else if (termTxCpt_sName.endsWith("ύτερο")||termTxCpt_sName.endsWith("ύτατο"))
			{
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj21");
			}
			else if (termTxCpt_sName.endsWith("ό"))
			{
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -ό (ουδέτερο) η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
						+"\nΜε θηλυκό -ή (δυνατός-δυνατή-δυνατό) και με -ά (γλυκός-γλυκιά-γλυκό)."
						+"\nΝα κλιθεί σαν το 'δυνατός';","Να κλιθεί σαν το 'δυνατός';",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
					{
						//δυνατός-δυνατή-δυνατό
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11");
					}
					else {
						//γλυκός-γλυκιά-γλυκό
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12");
					}
			}
			else if (termTxCpt_sName.endsWith("ο"))
			{
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -ο (ουδέτερο) η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
						+"\nΜε θηλυκό -η (άσπρος-άσπρη-άσπρο/όμορφος-όμορφη-όμορφο) και"
						+"\nΜε θυλυκό -ά (ωραίος-ωραία-ωραίο/πλούσιος-πλούσια-πλούσιο)."
						+"\nΝα κλιθεί σαν το 'άσπρος/όμορφος';","Να κλιθεί σαν το 'άσπρος/όμορφος';",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
					{
						//δυνατός-δυνατή-δυνατό
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11");
					}
					else {
						//γλυκός-γλυκιά-γλυκό
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12");
					}
			}

//-ς:
			else if (termTxCpt_sName.endsWith("άς")||termTxCpt_sName.endsWith("άσ"))
			{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj31");
			}
			else if (termTxCpt_sName.endsWith("ές")||termTxCpt_sName.endsWith("έσ"))
			{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23");
			}
			else if (termTxCpt_sName.endsWith("ης")||termTxCpt_sName.endsWith("ησ"))
			{
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -ης η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
						+"\nΑνισοσύλλαβα όπως το ζηλιάρης-ζηλιάρα-ζηλιάρικο και 'ανώμαλα' όπως ελώδης-ελώδης-ελώδες."
						+"\nΝα κλιθεί σαν το 'ζηλιάρης';","Να κλιθεί σαν το 'ζηλιάρης';",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
					{
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj24");
					}
					else {
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23");
					}
			}
			else if (termTxCpt_sName.endsWith("ής")||termTxCpt_sName.endsWith("ήσ"))
			{
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -ής (αρσενικό) η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
						+"\nΜε θηλυκό -ιά (σταχτής-σταχτιά-σταχτί) και με θυλυκό -ής (συνεχής-συνεχής-συνεχές)."
						+"\nΝα κλιθεί σαν το 'σταχτής';","Να κλιθεί σαν το 'σταχτής';",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
					{
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj22");
					}
					else {
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23");
					}
			}
			else if (termTxCpt_sName.endsWith("ύς")||termTxCpt_sName.endsWith("ύσ"))
			{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj21");
			}
			else if (termTxCpt_sName.endsWith("έστερος")||termTxCpt_sName.endsWith("έστεροσ")
							||termTxCpt_sName.endsWith("έστατος")||termTxCpt_sName.endsWith("έστατοσ"))
			{
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -έστερος/έστατος (αρσενικό) η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
						+"\nΟξύτονο 'ανώμαλο' (συνεχής-συνεχής-συνεχές) και"
						+"\nΠαροξύτονο 'ανώμαλο' (ελώδης-ελώδης-ελώδες)."
						+"\nΝα κλιθεί σαν το 'συνεχής';","Να κλιθεί σαν το 'συνεχής';",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23a");
					else
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj23b");
			}
			else if (termTxCpt_sName.endsWith("ότερος")||termTxCpt_sName.endsWith("ότεροσ")
							||termTxCpt_sName.endsWith("ότατος")||termTxCpt_sName.endsWith("ότατοσ"))
			{
				Object[] options = {
														"1) να κλιθεί σαν το δυνατός-δυνατή-δυνατό		(rlElnTrmAdj11a)",
														"2) να κλιθεί σαν το άσπρος-άσπρη-άσπρο				(rlElnTrmAdj11b)",
														"3) να κλιθεί σαν το όμορφος-όμορφη-όμορφο		(rlElnTrmAdj11c)",
														"4) να κλιθεί σαν το γλυκός-γλυκιά-γλυκό			(rlElnTrmAdj12a)",
														"5) να κλιθεί σαν το ωραίος-ωραία-ωραίο				(rlElnTrmAdj12b)",
														"6) να κλιθεί σαν το πλούσιος-πλούσια-πλούσιο (rlElnTrmAdj12c)",
														};
				Object selectedValue = JOptionPane.showInputDialog(null,
									"Δώσε επιπρόσθετη πληροφορία"
									+"\nΜε κατάληξη -ότερος/ότατος η γλώσσα έχει 6 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ",	//message
									"Επέλεξε ΕΝΑ",																	//title
									JOptionPane.INFORMATION_MESSAGE,								//message type
									null,																						//icon
									options,																				//values to select
									options[0]);																		//initial selection
				if (((String)selectedValue).startsWith("1)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11a");
				}
				else if (((String)selectedValue).startsWith("2)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11b");
				}
				else if (((String)selectedValue).startsWith("3)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11c");
				}
				else if (((String)selectedValue).startsWith("4)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12a");
				}
				else if (((String)selectedValue).startsWith("5)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12b");
				}
				else if (((String)selectedValue).startsWith("6)"))
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12c");
				}
			}
			else if (termTxCpt_sName.endsWith("ύτερος")||termTxCpt_sName.endsWith("ύτεροσ")
							||termTxCpt_sName.endsWith("ύτατος")||termTxCpt_sName.endsWith("ύτατοσ"))
			{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj21");
			}
			else if (termTxCpt_sName.endsWith("ός")||termTxCpt_sName.endsWith("όσ")||termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ"))
			{
					int	 result= JOptionPane.showConfirmDialog(null,
						"Με κατάληξη -ος (αρσενικό) η γλώσσα έχει 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΕΠΙΘΕΤΩΝ."
						+"\nΜε θηλυκό -η (δυνατός-δυνατή-δυνατό/άσπρος-άσπρη-άσπρο/όμορφος-όμορφη-όμορφο) και"
						+"\nΜε θυλυκό -α (γλυκός-γλυκιά-γλυκό/ωραίος-ωραία-ωραίο/πλούσιος-πλούσια-πλούσιο)."
						+"\nΝα κλιθεί σαν το 'δυνατός/άσπρος/όμορφος';","Να κλιθεί σαν το 'δυνατός/άσπρος/όμορφος';",JOptionPane.YES_NO_OPTION);
					if(result == JOptionPane.YES_OPTION)
					{
						//δυνατός-δυνατή-δυνατό
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj11");
					}
					else {
						//γλυκός-γλυκιά-γλυκό
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmAdj12");
					}
			}

//unknown case
			else {
				note="άγνωστη κατάληξη";
				termTxCpt_sAll=termTxCpt_sName;
			}
		}

		return termTxCpt_sAll;
	}


	/**
	 * Returns the tx-concept's-terms of irregulars.
	 * @modified 2001.05.24
	 * @since 2001.05.18
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfIrregular()
	{
		String mfform=termTxCpt_sName.toLowerCase(new Locale("el","GR"));
		note="είναι ανώμαλο επίθετο.";

//ΑΝΩΜΑΛΑ ΑΛΦΑΒΗΤΙΚΑ:
		if (mfform.endsWith("μενος")||mfform.endsWith("μένος")
			||mfform.endsWith("μενη")||mfform.endsWith("μένη")
			||mfform.endsWith("μενο")||mfform.endsWith("μένο"))
		{
			note=note +"\n						Οι παθητικές μετοχές σχηματίζουν τα παραθετικά τους ΜΟΝΟ περιφραστικά."
								+"\n						πιο, ο πιο, πολύ.";
			termTxCpt_sAll=Textual.getTermsOfTxConcept(mfform,"rlElnTrmAdj11d");
		}

		else if (mfform.equals("ανώτερος")||mfform.equals("ανώτατος")
						||mfform.equals("ανώτεροσ")||mfform.equals("ανώτατοσ")
						||mfform.equals("ανώτερη")||mfform.equals("ανώτατη")
						||mfform.equals("ανώτερο")||mfform.equals("ανώτατο"))
		{
			note=note +" Δεν έχει θετικό βαθμό.";
			termTxCpt_sAll=Textual.getTermsOfTxConcept("ανώτερος","rlElnTrmAdj11d") +"#"
						+Textual.getTermsOfTxConcept("ανώτατος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("απλός")||mfform.equals("απλόσ")
			||mfform.equals("απλή")||mfform.equals("απλό")
			||mfform.equals("απλούστερος")||mfform.equals("απλούστεροσ")
			||mfform.equals("απλούστερη")||mfform.equals("απλούστερο")
			||mfform.equals("απλούστατος")||mfform.equals("απλούστατοσ")
			||mfform.equals("απλούστατη")||mfform.equals("απλούστατο"))
		{
			termTxCpt_sAll=Textual.getTermsOfTxConcept("απλός","rlElnTrmAdj11d")	+"#"
					+Textual.getTermsOfTxConcept("απλούστερος","rlElnTrmAdj11d") +"#"
					+Textual.getTermsOfTxConcept("απλούστατος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("γέρος")||mfform.equals("γέροσ")
			||mfform.equals("γριά")||mfform.equals("γέρικο")
			||mfform.equals("γεροντότερος")||mfform.equals("γεροντότεροσ")
			||mfform.equals("γεροντότερη")||mfform.equals("γεροντότερο"))
		{
			termTxCpt_sAll=Textual.getTermsOfTxConcept("γέρος","rlElnTrmNnCs1d1") +"#"
					+Textual.getTermsOfTxConcept("γριά","rlElnTrmNnCs2a1") +"#"
					+Textual.getTermsOfTxConcept("γέρικο","rlElnTrmNnCs3c2") +"#"
					+Textual.getTermsOfTxConcept("γεροντότερος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("κακός")||mfform.equals("κακόσ")
			||mfform.equals("κακιά")||mfform.equals("κακό")
			||mfform.equals("χειρότερος")||mfform.equals("χειρότεροσ")
			||mfform.equals("χειρότερη")||mfform.equals("χειρότερο"))
		{
			termTxCpt_sAll=Textual.getTermsOfTxConcept("κακός","rlElnTrmNnCs1d1") +"#"
					+Textual.getTermsOfTxConcept("κακιά","rlElnTrmNnCs2a1b") +"#"
					+Textual.getTermsOfTxConcept("κακό","rlElnTrmNnCs3c2") +"#"
					+Textual.getTermsOfTxConcept("χειρότερος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("καλός")||mfform.equals("καλόσ")
			||mfform.equals("καλή")||mfform.equals("καλό")
			||mfform.equals("καλύτερος")||mfform.equals("καλύτεροσ")
			||mfform.equals("καλύτερη")||mfform.equals("καλύτερο")
			||mfform.equals("άριστος")||mfform.equals("άριστοσ")
			||mfform.equals("άριστη")||mfform.equals("άριστο"))
		{
			termTxCpt_sAll=Textual.getTermsOfTxConcept("καλός","rlElnTrmAdj11d")	+"#"
					+Textual.getTermsOfTxConcept("καλύτερος","rlElnTrmAdj11d") +"#"
					+Textual.getTermsOfTxConcept("άριστος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("κατώτερος")||mfform.equals("κατώτατος")
						||mfform.equals("κατώτεροσ")||mfform.equals("κατώτατοσ")
						||mfform.equals("κατώτερη")||mfform.equals("κατώτατη")
						||mfform.equals("κατώτερο")||mfform.equals("κατώτατο"))
		{
			note=note +" Δεν έχει θετικό βαθμό.";
			termTxCpt_sAll=Textual.getTermsOfTxConcept("κατώτερος","rlElnTrmAdj11d")	+"#"
						+Textual.getTermsOfTxConcept("κατώτατος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("λίγος")||mfform.equals("λίγοσ")
			||mfform.equals("λίγη")||mfform.equals("λίγο")
			||mfform.equals("λιγότερος")||mfform.equals("λιγότεροσ")
			||mfform.equals("λιγότερη")||mfform.equals("λιγότερο")
			||mfform.equals("ελάχιστος")||mfform.equals("ελάχιστοσ")
			||mfform.equals("ελάχιστη")||mfform.equals("ελάχιστο"))
		{
			termTxCpt_sAll=Textual.getTermsOfTxConcept("λίγος","rlElnTrmAdj11d")	+"#"
					+Textual.getTermsOfTxConcept("λιγότερος","rlElnTrmAdj11d") +"#"
					+Textual.getTermsOfTxConcept("ελάχιστος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("μεγάλος")||mfform.equals("μεγάλοσ")
			||mfform.equals("μεγάλη")||mfform.equals("μεγάλο")
			||mfform.equals("μεγαλύτερος")||mfform.equals("μεγαλύτεροσ")
			||mfform.equals("μεγαλύτερη")||mfform.equals("μεγαλύτερο")
			||mfform.equals("μέγιστος")||mfform.equals("μέγιστοσ")
			||mfform.equals("μέγιστη")||mfform.equals("μέγιστο"))
		{
			termTxCpt_sAll=Textual.getTermsOfTxConcept("μεγάλος","rlElnTrmAdj11d")	+"#"
					+Textual.getTermsOfTxConcept("μεγαλύτερος","rlElnTrmAdj11d") +"#"
					+Textual.getTermsOfTxConcept("μέγιστος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("μεταγενέστερος")||mfform.equals("μεταγενέστεροσ")
					||mfform.equals("μεταγενέστερη")||mfform.equals("μεταγενέστερο"))
		{
			note=note +" Δεν έχει θετικό και υπερθετικό βαθμό.";
			termTxCpt_sAll=Textual.getTermsOfTxConcept("μεταγενέστερος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("μικρός")||mfform.equals("μικρόσ")
			||mfform.equals("μικρή")||mfform.equals("μικρό")
			||mfform.equals("μικρότερος")||mfform.equals("μικρότεροσ")
			||mfform.equals("μικρότερη")||mfform.equals("μικρότερο")
			||mfform.equals("ελάχιστος")||mfform.equals("ελάχιστοσ")
			||mfform.equals("ελάχιστη")||mfform.equals("ελάχιστο"))
		{
			termTxCpt_sAll=Textual.getTermsOfTxConcept("μικρός","rlElnTrmAdj11d") +"#"
					+Textual.getTermsOfTxConcept("μικρότερος","rlElnTrmAdj11d")	+"#"
					+Textual.getTermsOfTxConcept("ελάχιστος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("πολύς")||mfform.equals("πολύσ")
			||mfform.equals("πολλή")||mfform.equals("πολύ")
			||mfform.equals("περισσότερος")||mfform.equals("περισσότεροσ")
			||mfform.equals("περισσότερη")||mfform.equals("περισσότερο")
			||mfform.equals("πιότερος")||mfform.equals("πιότεροσ")
			||mfform.equals("πιότερη")||mfform.equals("πιότερο")
			)//2001.05.22
		{
//			note=note +"\n\tπαραθετικά: περισσότερος(σπάνια πιότερος), -.";
			termTxCpt_sAll="ο πολύς,-,πολύ,-,πολλοί,πολλών,πολλούς,πολλοί"
					+"#η πολλή,πολλής,πολλή,-,πολλές,πολλών,πολλές,πολλές"
					+"#το πολύ,-,πολύ,-,πολλά,πολλών,πολλά,πολλά"		+"#"
					+Textual.getTermsOfTxConcept("περισσότερος","rlElnTrmAdj11d")		+"#"
					+Textual.getTermsOfTxConcept("πιότερος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("προγενέστερος")||mfform.equals("προγενέστεροσ")
					||mfform.equals("προγενέστερη")||mfform.equals("προγενέστερο"))
		{
			note=note +" Δεν έχει θετικό και υπερθετικό βαθμό.";
			termTxCpt_sAll=Textual.getTermsOfTxConcept("προγενέστερος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("προτιμότερος")||mfform.equals("προτιμότεροσ")
					||mfform.equals("προτιμότερη")||mfform.equals("προτιμότερο"))
		{
			note=note +" Δεν έχει θετικό και υπερθετικό βαθμό.";
			termTxCpt_sAll=Textual.getTermsOfTxConcept("προτιμότερος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("πρωτύτερος")||mfform.equals("πρωτύτεροσ")
					||mfform.equals("πρωτύτερη")||mfform.equals("πρωτύτερο"))
		{
			note=note +" Δεν έχει θετικό και υπερθετικό βαθμό.";
			termTxCpt_sAll=Textual.getTermsOfTxConcept("πρωτύτερος","rlElnTrmAdj11d");
		}
		else if (mfform.equals("υπέρτερος")||mfform.equals("υπέρτατος")
					||mfform.equals("υπέρτερη")||mfform.equals("υπέρτατη")
					||mfform.equals("υπέρτερο")||mfform.equals("υπέρτατο"))
		{
			note=note +" Δεν έχει θετικό βαθμό.";
			termTxCpt_sAll=Textual.getTermsOfTxConcept("υπέρτερος","rlElnTrmAdj11d")	+"#"
						+Textual.getTermsOfTxConcept("υπέρτατος","rlElnTrmAdj11d");
		}
		return termTxCpt_sAll;
	}

	/**
	 * @modified
	 * @since 2001.05.18
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRule(String rule)
	{
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));
		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(rule);
		}

		return termTxCpt_sAll;
	}

	/**
	 * Finds the tx-concept's-terms of an tx_nounAdjective given its <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango/term-rule_eln.html#ruleTrmNnAj">
	 * lg-concept's--term-creating-rule</a>.
	 *
	 * @modified 2001.05.24
	 * @since 2001.05.18
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly(String rule)
	{
		termTxCpt_sRule=rule;
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));

//ΑΡΣΕΝΙΚΑ ΣΕ -ΟΣ:
		if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj11"))
		{
			//υποθέτουμε ότι ο ΠΡΩΤΟΣ-ΤΥΠΟΣ είναι 'θετικού-βαθμου'.
			if (termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ")||termTxCpt_sName.endsWith("ός")||termTxCpt_sName.endsWith("όσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("η")||termTxCpt_sName.endsWith("ή")||termTxCpt_sName.endsWith("ο")||termTxCpt_sName.endsWith("ό"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ος-η-ο για να κλιθεί με τον κανόνα rlElnTrmAdj11");
			}
			stem2=greekTonosRemove(stem);
			if (greekTonosFind(termTxCpt_sName)==1)
			{
					note="Κλίθηκε με τον rlElnTrmAdj11 (δυνατός-δυνατή-δυνατό)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
					termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ός","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ή","rlElnTrmNnCs2b2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ό","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατο","rlElnTrmNnCs3c2");
			}
			else if (greekTonosFind(termTxCpt_sName)==2||greekTonosFind(termTxCpt_sName)==3)
			{
					note="Κλίθηκε με τον rlElnTrmAdj11 (άσπρος-άσπρη-άσπρο/όμορφος-όμορφη-όμορφο)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
					termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"η","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem+"ο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατο","rlElnTrmNnCs3c2");
			}
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj11a"))
		{
			if (termTxCpt_sName.endsWith("ότερος")||termTxCpt_sName.endsWith("ότεροσ")
							||termTxCpt_sName.endsWith("ότατος")||termTxCpt_sName.endsWith("ότατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ότερη")||termTxCpt_sName.endsWith("ότερο")
							||termTxCpt_sName.endsWith("ότατη")||termTxCpt_sName.endsWith("ότατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else if (termTxCpt_sName.endsWith("ός")||termTxCpt_sName.endsWith("όσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("ή")||termTxCpt_sName.endsWith("ό"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ός-ή-ό-ότερος-ότατος "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj11a");
			}
			note="Κλίθηκε με τον rlElnTrmAdj11a (δυνατός-δυνατή-δυνατό)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			termTxCpt_sAll=		 Textual.getTermsOfTxConcept(stem+"ός","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ή","rlElnTrmNnCs2b2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ό","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατο","rlElnTrmNnCs3c2");
		}
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj11b"))
		{
			if (termTxCpt_sName.endsWith("ότερος")||termTxCpt_sName.endsWith("ότεροσ")
							||termTxCpt_sName.endsWith("ότατος")||termTxCpt_sName.endsWith("ότατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ότερη")||termTxCpt_sName.endsWith("ότερο")
							||termTxCpt_sName.endsWith("ότατη")||termTxCpt_sName.endsWith("ότατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else if (termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("η")||termTxCpt_sName.endsWith("ο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ος-η-ο-ότερος-ότατος "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj11b");
			}
			stem2=greekTonosRemove(stem);
			stem3=greekTonosSetOnFirst(stem2);
			note="Κλίθηκε με τον rlElnTrmAdj11b (άσπρος-άσπρη-άσπρο)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			termTxCpt_sAll=		 Textual.getTermsOfTxConcept(stem3+"ος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem3+"η","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem3+"ο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατο","rlElnTrmNnCs3c2");
		}
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj11c"))
		{
			if (termTxCpt_sName.endsWith("ότερος")||termTxCpt_sName.endsWith("ότεροσ")
							||termTxCpt_sName.endsWith("ότατος")||termTxCpt_sName.endsWith("ότατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ότερη")||termTxCpt_sName.endsWith("ότερο")
							||termTxCpt_sName.endsWith("ότατη")||termTxCpt_sName.endsWith("ότατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else if (termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("η")||termTxCpt_sName.endsWith("ο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ος-η-ο-ότερος-ότατος "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj11c");
			}
			stem2=greekTonosRemove(stem);
			stem3=greekTonosSetOnSecond(stem2);
			note="Κλίθηκε με τον rlElnTrmAdj11c (όμορφος-όμορφη-όμορφο)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			termTxCpt_sAll=		 Textual.getTermsOfTxConcept(stem3+"ος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem3+"η","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem3+"ο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατο","rlElnTrmNnCs3c2");
		}
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj11d"))
		{
			//υποθέτουμε ότι ο ΠΡΩΤΟΣ-ΤΥΠΟΣ είναι 'θετικού-βαθμου'.
			//ΚΑΙ παράγει μόνο το θετικό-βαθμό (μας χρειάζεται στο σχηματισμό ανώμαλων παραθετικών).
			if (termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ")||termTxCpt_sName.endsWith("ός")||termTxCpt_sName.endsWith("όσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("η")||termTxCpt_sName.endsWith("ή")||termTxCpt_sName.endsWith("ο")||termTxCpt_sName.endsWith("ό"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ος-η-ο "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj11d");
			}
			if (greekTonosFind(termTxCpt_sName)==1)
			{
					note="Κλίθηκε με τον rlElnTrmAdj11d (δυνατός-δυνατή-δυνατό).";
					termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ός","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ή","rlElnTrmNnCs2b2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ό","rlElnTrmNnCs3c2");
			}
			else if (greekTonosFind(termTxCpt_sName)==2||greekTonosFind(termTxCpt_sName)==3)
			{
					note="Κλίθηκε με τον rlElnTrmAdj11d (άσπρος-άσπρη-άσπρο/όμορφος-όμορφη-όμορφο).";
					termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"η","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem+"ο","rlElnTrmNnCs3c2");
			}
		}

//ος-α-ο
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj12a"))
		{
			if (termTxCpt_sName.endsWith("ότερος")||termTxCpt_sName.endsWith("ότεροσ")
							||termTxCpt_sName.endsWith("ότατος")||termTxCpt_sName.endsWith("ότατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ότερη")||termTxCpt_sName.endsWith("ότερο")
							||termTxCpt_sName.endsWith("ότατη")||termTxCpt_sName.endsWith("ότατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else if (termTxCpt_sName.endsWith("ός")||termTxCpt_sName.endsWith("όσ")||termTxCpt_sName.endsWith("ιά"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("ό"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ός-ά-ό-ότερος-ότατος "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj12a");
			}
			note="Κλίθηκε με τον rlElnTrmAdj12a (γλυκός-γλυκιά-γλυκό)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			if (stem.endsWith("κ")||stem.endsWith("χ")||stem.endsWith("ν")||stem.endsWith("θ"))
				termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem+"ός","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ιά","rlElnTrmNnCs2a1b") +"#";
			else
				termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem+"ός","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ά","rlElnTrmNnCs2a1") +"#";//γηρεός-γηρεά-γηρεό
			termTxCpt_sAll = termTxCpt_sAll
								+Textual.getTermsOfTxConcept(stem+"ό","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότερο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem+"ότατο","rlElnTrmNnCs3c2");
		}
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj12b"))
		{
			if (termTxCpt_sName.endsWith("ότερος")||termTxCpt_sName.endsWith("ότεροσ")
							||termTxCpt_sName.endsWith("ότατος")||termTxCpt_sName.endsWith("ότατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ότερη")||termTxCpt_sName.endsWith("ότερο")
							||termTxCpt_sName.endsWith("ότατη")||termTxCpt_sName.endsWith("ότατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else if (termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("ο")||termTxCpt_sName.endsWith("α"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ος-α-ο-ότερος-ότατος "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj12b");
			}
			stem2=greekTonosRemove(stem);
			stem3=greekTonosSetOnFirst(stem2);
			note="Κλίθηκε με τον rlElnTrmAdj12b (ωραίος-ωραία-ωραίο)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			if (stem.endsWith("κ")||stem.endsWith("χ")||stem.endsWith("ν")||stem.endsWith("θ"))
				termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem3+"ος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem3+"ια","rlElnTrmNnCs2a5b") +"#";//φρέσκια-φρέσκες
			else
				termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem3+"ος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem3+"α","rlElnTrmNnCs2a4") +"#";
			termTxCpt_sAll = termTxCpt_sAll
								+Textual.getTermsOfTxConcept(stem3+"ο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατο","rlElnTrmNnCs3c2");
		}
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj12c"))
		{
			if (termTxCpt_sName.endsWith("ότερος")||termTxCpt_sName.endsWith("ότεροσ")
							||termTxCpt_sName.endsWith("ότατος")||termTxCpt_sName.endsWith("ότατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ότερη")||termTxCpt_sName.endsWith("ότερο")
							||termTxCpt_sName.endsWith("ότατη")||termTxCpt_sName.endsWith("ότατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else if (termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("ο")||termTxCpt_sName.endsWith("α"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ος-α-ο-ότερος-ότατος "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj12c");
			}
			stem2=greekTonosRemove(stem);
			stem3=greekTonosSetOnSecond(stem2);
			note="Κλίθηκε με τον rlElnTrmAdj12c (πλούσιος-πλούσια-πλούσιο)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			if (stem.endsWith("κ")||stem.endsWith("χ")||stem.endsWith("ν")||stem.endsWith("θ"))
				termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem3+"ος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem3+"ια","rlElnTrmNnCs2a5b") +"#";//κατάφρεσκια-κατάφρεσκες
			else
				termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem3+"ος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem3+"α","rlElnTrmNnCs2a4") +"#";
			termTxCpt_sAll = termTxCpt_sAll
								+Textual.getTermsOfTxConcept(stem+"ο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότερο","rlElnTrmNnCs3c2") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατος","rlElnTrmNnCs1d1") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατη","rlElnTrmNnCs2b3") +"#"
								+Textual.getTermsOfTxConcept(stem2+"ότατο","rlElnTrmNnCs3c2");
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj12"))
		{
			//υποθέτουμε ότι ο ΠΡΩΤΟΣ-ΤΥΠΟΣ είναι 'θετικού-βαθμου'.
			if (termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ")
							||termTxCpt_sName.endsWith("ός")||termTxCpt_sName.endsWith("όσ")||termTxCpt_sName.endsWith("ά"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("α")||termTxCpt_sName.endsWith("ο")||termTxCpt_sName.endsWith("ό"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ος-α-ο για να κλιθεί με τον κανόνα rlElnTrmAdj12");
			}
			stem2=greekTonosRemove(stem);
			if (greekTonosFind(termTxCpt_sName)==1)
			{
					note="Κλίθηκε με τον rlElnTrmAdj12 (γλυκός-γλυκιά-γλυκό)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
				if (stem.endsWith("κ")||stem.endsWith("χ")||stem.endsWith("ν")||stem.endsWith("θ"))
					termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem+"ός","rlElnTrmNnCs1d1") +"#"
									+Textual.getTermsOfTxConcept(stem+"ιά","rlElnTrmNnCs2a1b") +"#";
				else
					termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem+"ός","rlElnTrmNnCs1d1") +"#"
									+Textual.getTermsOfTxConcept(stem+"ά","rlElnTrmNnCs2a1") +"#";//γηρεός-γηρεά-γηρεό
				termTxCpt_sAll = termTxCpt_sAll
									+Textual.getTermsOfTxConcept(stem+"ό","rlElnTrmNnCs3c2") +"#"
									+Textual.getTermsOfTxConcept(stem+"ότερος","rlElnTrmNnCs1d1") +"#"
									+Textual.getTermsOfTxConcept(stem+"ότερη","rlElnTrmNnCs2b3") +"#"
									+Textual.getTermsOfTxConcept(stem+"ότερο","rlElnTrmNnCs3c2") +"#"
									+Textual.getTermsOfTxConcept(stem+"ότατος","rlElnTrmNnCs1d1") +"#"
									+Textual.getTermsOfTxConcept(stem+"ότατη","rlElnTrmNnCs2b3") +"#"
									+Textual.getTermsOfTxConcept(stem+"ότατο","rlElnTrmNnCs3c2");
			}
			else if (greekTonosFind(termTxCpt_sName)==2)
			{
					note="Κλίθηκε με τον rlElnTrmAdj12 (ωραίος-ωραία-ωραίο)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
					if (stem.endsWith("κ")||stem.endsWith("χ")||stem.endsWith("ν")||stem.endsWith("θ"))
						termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem+"ος","rlElnTrmNnCs1d1") +"#"
										+Textual.getTermsOfTxConcept(stem+"ια","rlElnTrmNnCs2a5b") +"#";//φρέσκια-φρέσκες
					else
						termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem+"ος","rlElnTrmNnCs1d1") +"#"
										+Textual.getTermsOfTxConcept(stem+"α","rlElnTrmNnCs2a4") +"#";
					termTxCpt_sAll = termTxCpt_sAll
										+Textual.getTermsOfTxConcept(stem+"ο","rlElnTrmNnCs3c2") +"#"
										+Textual.getTermsOfTxConcept(stem2+"ότερος","rlElnTrmNnCs1d1") +"#"
										+Textual.getTermsOfTxConcept(stem2+"ότερη","rlElnTrmNnCs2b3") +"#"
										+Textual.getTermsOfTxConcept(stem2+"ότερο","rlElnTrmNnCs3c2") +"#"
										+Textual.getTermsOfTxConcept(stem2+"ότατος","rlElnTrmNnCs1d1") +"#"
										+Textual.getTermsOfTxConcept(stem2+"ότατη","rlElnTrmNnCs2b3") +"#"
										+Textual.getTermsOfTxConcept(stem2+"ότατο","rlElnTrmNnCs3c2");
			}
			else if (greekTonosFind(termTxCpt_sName)==3)
			{
					note="Κλίθηκε με τον rlElnTrmAdj12 (πλούσιος-πλούσια-πλούσιο)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
				if (stem.endsWith("κ")||stem.endsWith("χ")||stem.endsWith("ν")||stem.endsWith("θ"))
					termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem+"ος","rlElnTrmNnCs1d1") +"#"
									+Textual.getTermsOfTxConcept(stem+"ια","rlElnTrmNnCs2a5b") +"#";//κατάφρεσκια-κατάφρεσκες
				else
					termTxCpt_sAll=	 Textual.getTermsOfTxConcept(stem+"ος","rlElnTrmNnCs1d1") +"#"
									+Textual.getTermsOfTxConcept(stem+"α","rlElnTrmNnCs2a4") +"#";
				termTxCpt_sAll = termTxCpt_sAll
									+Textual.getTermsOfTxConcept(stem+"ο","rlElnTrmNnCs3c2") +"#"
									+Textual.getTermsOfTxConcept(stem2+"ότερος","rlElnTrmNnCs1d1") +"#"
									+Textual.getTermsOfTxConcept(stem2+"ότερη","rlElnTrmNnCs2b3") +"#"
									+Textual.getTermsOfTxConcept(stem2+"ότερο","rlElnTrmNnCs3c2") +"#"
									+Textual.getTermsOfTxConcept(stem2+"ότατος","rlElnTrmNnCs1d1") +"#"
									+Textual.getTermsOfTxConcept(stem2+"ότατη","rlElnTrmNnCs2b3") +"#"
									+Textual.getTermsOfTxConcept(stem2+"ότατο","rlElnTrmNnCs3c2");
			}
		}

//ΑΡΣΕΝΙΚΑ ΣΕ -ΙΣ:
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj21"))
		{
			if (termTxCpt_sName.endsWith("ύς")||termTxCpt_sName.endsWith("ά"))//2001.05.21
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("ύ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else if (termTxCpt_sName.endsWith("ύτερος")||termTxCpt_sName.endsWith("ύτεροσ")
							||termTxCpt_sName.endsWith("ύτατος")||termTxCpt_sName.endsWith("ύτατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ύτερη")||termTxCpt_sName.endsWith("ύτερο")
							||termTxCpt_sName.endsWith("ύτατη")||termTxCpt_sName.endsWith("ύτατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ύς-ιά-ύ για να κλιθεί με τον κανόνα rlElnTrmAdj21");
			}
			note="Κλίθηκε με τον rlElnTrmAdj21 (βαθύς-βαθιά-βαθύ)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ύς","rlElnTrmNnCs1c4") +"#"
						+Textual.getTermsOfTxConcept(stem+"ιά","rlElnTrmNnCs2a1") +"#"
						+Textual.getTermsOfTxConcept(stem+"ύ","rlElnTrmNnCs3e1") +"#"
						+Textual.getTermsOfTxConcept(stem+"ύτερος","rlElnTrmNnCs1d1") +"#"
						+Textual.getTermsOfTxConcept(stem+"ύτερη","rlElnTrmNnCs2b3") +"#"
						+Textual.getTermsOfTxConcept(stem+"ύτερο","rlElnTrmNnCs3c2") +"#"
						+Textual.getTermsOfTxConcept(stem+"ύτατος","rlElnTrmNnCs1d1") +"#"
						+Textual.getTermsOfTxConcept(stem+"ύτατη","rlElnTrmNnCs2b3") +"#"
						+Textual.getTermsOfTxConcept(stem+"ύτατο","rlElnTrmNnCs3c2");
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj22"))
		{
			if (termTxCpt_sName.endsWith("ής")||termTxCpt_sName.endsWith("ιά")||termTxCpt_sName.endsWith("ήσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("ί"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ής-ιά-ί για να κλιθεί με τον κανόνα rlElnTrmAdj22");
			}
			note="Κλίθηκε με τον rlElnTrmAdj22 (σταχτής-σταχτιά-σταχτί)."
					+"\n						Παραθετικά: σταχτής, πιο σταχτής, ο πιο σταχτής, πολύ σταχτής.";
			termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ής","rlElnTrmNnCs1c1b") +"#"
						+Textual.getTermsOfTxConcept(stem+"ιά","rlElnTrmNnCs2a1") +"#"
						+Textual.getTermsOfTxConcept(stem+"ί","rlElnTrmNnCs3b1");
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj23a"))
		{
			if (termTxCpt_sName.endsWith("έστερος")||termTxCpt_sName.endsWith("έστεροσ")
							||termTxCpt_sName.endsWith("έστατος")||termTxCpt_sName.endsWith("έστατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 7);
			else if (termTxCpt_sName.endsWith("έστερη")||termTxCpt_sName.endsWith("έστερο")
							||termTxCpt_sName.endsWith("έστατη")||termTxCpt_sName.endsWith("έστατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ής")||termTxCpt_sName.endsWith("ές")||termTxCpt_sName.endsWith("ήσ")||termTxCpt_sName.endsWith("έσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ής-ές-έστερος-έστατος "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj23a");
			}
			note="Κλίθηκε με τον rlElnTrmAdj23a (συνεχής-συνεχής-συνεχές)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ής","rlElnTrmNnCs1c1c") +"#"
						+Textual.getTermsOfTxConcept(stem+"ής","rlElnTrmNnCs2c2") +"#"
						+Textual.getTermsOfTxConcept(stem+"ές","rlElnTrmNnCs3d4") +"#"
						+Textual.getTermsOfTxConcept(stem+"έστερος","rlElnTrmNnCs1d1") +"#"
						+Textual.getTermsOfTxConcept(stem+"έστερη","rlElnTrmNnCs2b3") +"#"
						+Textual.getTermsOfTxConcept(stem+"έστερο","rlElnTrmNnCs3c2") +"#"
						+Textual.getTermsOfTxConcept(stem+"έστατος","rlElnTrmNnCs1d1") +"#"
						+Textual.getTermsOfTxConcept(stem+"έστατη","rlElnTrmNnCs2b3") +"#"
						+Textual.getTermsOfTxConcept(stem+"έστατο","rlElnTrmNnCs3c2");
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj23b"))
		{
			if (termTxCpt_sName.endsWith("έστερος")||termTxCpt_sName.endsWith("έστεροσ")
							||termTxCpt_sName.endsWith("έστατος")||termTxCpt_sName.endsWith("έστατοσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 7);
			else if (termTxCpt_sName.endsWith("έστερη")||termTxCpt_sName.endsWith("έστερο")
							||termTxCpt_sName.endsWith("έστατη")||termTxCpt_sName.endsWith("έστατο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else if (termTxCpt_sName.endsWith("ης")||termTxCpt_sName.endsWith("ες")||termTxCpt_sName.endsWith("ησ")||termTxCpt_sName.endsWith("εσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ης-ες-έστερος-έστατος "
										+"για να κλιθεί με τον κανόνα rlElnTrmAdj23b");
			}
			stem2=greekTonosRemove(stem);
			note="Κλίθηκε με τον rlElnTrmAdj23b (ελώδης-ελώδης-ελώδες)."
					+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
			termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ης","rlElnTrmNnCs1c1c") +"#"
						+Textual.getTermsOfTxConcept(stem+"ης","rlElnTrmNnCs2c2") +"#"
						+Textual.getTermsOfTxConcept(stem+"ες","rlElnTrmNnCs3d4") +"#"
						+Textual.getTermsOfTxConcept(stem2+"έστερος","rlElnTrmNnCs1d1") +"#"
						+Textual.getTermsOfTxConcept(stem2+"έστερη","rlElnTrmNnCs2b3") +"#"
						+Textual.getTermsOfTxConcept(stem2+"έστερο","rlElnTrmNnCs3c2") +"#"
						+Textual.getTermsOfTxConcept(stem2+"έστατος","rlElnTrmNnCs1d1") +"#"
						+Textual.getTermsOfTxConcept(stem2+"έστατη","rlElnTrmNnCs2b3") +"#"
						+Textual.getTermsOfTxConcept(stem2+"έστατο","rlElnTrmNnCs3c2");
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj23"))
		{
			//μόνο για ΠΡΩΤΟ-ΤΥΠΟ θετικού-βαθμού
			if (termTxCpt_sName.endsWith("ής")||termTxCpt_sName.endsWith("ές")||termTxCpt_sName.endsWith("ήσ")||termTxCpt_sName.endsWith("έσ")
				||termTxCpt_sName.endsWith("ης")||termTxCpt_sName.endsWith("ες")||termTxCpt_sName.endsWith("ησ")||termTxCpt_sName.endsWith("εσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ης-ες για να κλιθεί με τον κανόνα rlElnTrmAdj23");
			}
			if (greekTonosFind(termTxCpt_sName)==1)
			{
				note="Κλίθηκε με τον rlElnTrmAdj23 (συνεχής-συνεχής-συνεχές)."
						+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
				termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ής","rlElnTrmNnCs1c1c") +"#"
							+Textual.getTermsOfTxConcept(stem+"ής","rlElnTrmNnCs2c2") +"#"
							+Textual.getTermsOfTxConcept(stem+"ές","rlElnTrmNnCs3d4") +"#"
							+Textual.getTermsOfTxConcept(stem+"έστερος","rlElnTrmNnCs1d1") +"#"
							+Textual.getTermsOfTxConcept(stem+"έστερη","rlElnTrmNnCs2b3") +"#"
							+Textual.getTermsOfTxConcept(stem+"έστερο","rlElnTrmNnCs3c2") +"#"
							+Textual.getTermsOfTxConcept(stem+"έστατος","rlElnTrmNnCs1d1") +"#"
							+Textual.getTermsOfTxConcept(stem+"έστατη","rlElnTrmNnCs2b3") +"#"
							+Textual.getTermsOfTxConcept(stem+"έστατο","rlElnTrmNnCs3c2");
			}
			else if (greekTonosFind(termTxCpt_sName)==2)
			{
				stem2=greekTonosRemove(stem);
				note="Κλίθηκε με τον rlElnTrmAdj23 (ελώδης-ελώδης-ελώδες)."
						+"\n						Παραθετικά και περιφραστικά: πιο, ο πιο, πολύ.";
				termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ης","rlElnTrmNnCs1c1c") +"#"
							+Textual.getTermsOfTxConcept(stem+"ης","rlElnTrmNnCs2c2") +"#"
							+Textual.getTermsOfTxConcept(stem+"ες","rlElnTrmNnCs3d4") +"#"
							+Textual.getTermsOfTxConcept(stem2+"έστερος","rlElnTrmNnCs1d1") +"#"
							+Textual.getTermsOfTxConcept(stem2+"έστερη","rlElnTrmNnCs2b3") +"#"
							+Textual.getTermsOfTxConcept(stem2+"έστερο","rlElnTrmNnCs3c2") +"#"
							+Textual.getTermsOfTxConcept(stem2+"έστατος","rlElnTrmNnCs1d1") +"#"
							+Textual.getTermsOfTxConcept(stem2+"έστατη","rlElnTrmNnCs2b3") +"#"
							+Textual.getTermsOfTxConcept(stem2+"έστατο","rlElnTrmNnCs3c2");
			}
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj24"))
		{
			if (termTxCpt_sName.endsWith("ης")||termTxCpt_sName.endsWith("ησ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("α"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else if (termTxCpt_sName.endsWith("ικο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 3);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ης-α-ικο για να κλιθεί με τον κανόνα rlElnTrmAdj24");
			}
			note="Κλίθηκε με τον rlElnTrmAdj24 (ζηλιάρης-ζηλιάρα-ζηλιάρικο)."
					+"\n						Παραθετικά περιφραστικά: πιο, ο πιο, πολύ.";
			termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"ης","rlElnTrmNnCs1c2") +"#"
						+Textual.getTermsOfTxConcept(stem+"α","rlElnTrmNnCs2a4") +"#"
						+Textual.getTermsOfTxConcept(stem+"ικο","rlElnTrmNnCs3c2");
		}

//3) αρσενικά σε -ας:
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj31"))
		{
			if (termTxCpt_sName.endsWith("άς")||termTxCpt_sName.endsWith("άσ")||termTxCpt_sName.endsWith("ού"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("άδικο"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -άς-ού-άδικο για να κλιθεί με τον κανόνα rlElnTrmAdj31");
			}
			note="Κλίθηκε με τον rlElnTrmAdj31 (λογάς-λογού-λογάδικο)."
					+"\n						Παραθετικά: λογάς, πιο λογάς, ο πιο λογάς, πολύ λογάς.";
			termTxCpt_sAll= Textual.getTermsOfTxConcept(stem+"άς","rlElnTrmNnCs1a1") +"#"
						+Textual.getTermsOfTxConcept(stem+"ού","rlElnTrmNnCs2d1") +"#"
						+Textual.getTermsOfTxConcept(stem+"άδικο","rlElnTrmNnCs3c2");
		}

//4) ΧΩΡΙΣ ΠΑΡΑΘΕΤΙΚΑ:

//5) ΑΝΩΜΑΛΑ:
		else if (termTxCpt_sRule.equalsIgnoreCase("rlElnTrmAdj51"))
		{
			termTxCpt_sAll=getTermsOfTxConceptIfIrregular();
		}

		else if (termTxCpt_sRule.equals("")||termTxCpt_sRule.equals("none"))
		{
			note="χωρίς κανόνας-κλίσης.";
			termTxCpt_sAll=termTxCpt_sName+",-";
		}

//unknown case
		else {
			note="άγνωστος κανόνας-κλίσης.";
			termTxCpt_sAll=termTxCpt_sName+",-";
		}

		return termTxCpt_sAll;
	}


	/**
	 * Returns the UNIQUE tx-concept's-terms in the form "nm1,nm2,...nm52",
	 * given a term's-rule.<br/>
	 * ALL tx-concept's-terms are: (8+8+8)x3 = 72<br/>
	 * UNIQUE tx-concept's-terms are: (7+6+4)x3 = 51
	 *
	 * @modified 2006.02.07
	 * @since 2006.02.07 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getUniqueTxConceptTermsIfRuleOnly(String termTxCpt_sRule)
	{
		termTxCpt_sAll=	getTermsOfTxConceptIfRuleOnly(termTxCpt_sRule);
//ο,δυνατός,δυνατού,δυνατό,δυνατέ,δυνατοί,δυνατών,δυνατούς
//#η,δυνατή,δυνατής,δυνατή,δυνατή,δυνατές,δυνατών,δυνατές
//#το,δυνατό,δυνατού,δυνατό,δυνατό,δυνατά,δυνατών,δυνατά
//#ο,δυνατότερος,δυνατότερου,δυνατότερο,δυνατότερε,δυνατότεροι,δυνατότερων,δυνατότερους

		String g[]= termTxCpt_sAll.split("#");
		String g1[] = g[0].split(",");
		String g2[] = g[1].split(",");
		String g3[] = g[2].split(",");
		String g4[] = g[3].split(",");
		String g5[] = g[4].split(",");
		String g6[] = g[5].split(",");
		String g7[] = g[6].split(",");
		String g8[] = g[7].split(",");
		String g9[] = g[8].split(",");
		termTxCpt_sAll = g1[1]+","+g1[2]+","+g1[3]+","+g1[4]+","+g1[5]+","+g1[6]+","+g1[7]
				  +","+g2[1]+","+g2[2]+","+g2[3]+","+g2[5]+","+g2[6]+","+g2[7]
					+","+g3[1]+","+g3[2]+","+g3[5]+","+g3[6]

					+","+g4[1]+","+g4[2]+","+g4[3]+","+g4[4]+","+g4[5]+","+g4[6]+","+g4[7]
					+","+g5[1]+","+g5[2]+","+g5[3]+","+g5[5]+","+g5[6]+","+g5[7]
					+","+g6[1]+","+g6[2]+","+g6[5]+","+g6[6]

					+","+g7[1]+","+g7[2]+","+g7[3]+","+g7[4]+","+g7[5]+","+g7[6]+","+g7[7]
					+","+g8[1]+","+g8[2]+","+g8[3]+","+g8[5]+","+g8[6]+","+g8[7]
					+","+g9[1]+","+g9[2]+","+g9[5]+","+g9[6];
		return termTxCpt_sAll;
	}

}
