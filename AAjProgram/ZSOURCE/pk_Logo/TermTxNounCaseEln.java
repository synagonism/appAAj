/**
 * TermTxNounCaseEln.java - The greek-tx_noun class.
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
import pk_Util.Textual;

import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Contains the lg-concept's--term-rules of greek tx_nouns.
 * @modified 2004.09.07
 * @since 2001.03.11 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TermTxNounCaseEln
	extends Term_TxConcept
{
	int tonos=-1;
	String part1="",part2="";			//the parts of a multi-word tx_noun.
	String[] nms1=null,nms2=null; //the termTxCpt_sAll of the parts of a multi-word.

	/**
	 * @modified 2001.03.12
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public TermTxNounCaseEln (String frm, String rl)
	{
		super(frm, "termTxNoun_Case", "eln");
		termTxCpt_sRule=rl;
	}


	/**
	 * @modified
	 * @since 2001.03.24
	 * @author HoKoNoUmo
	 */
	public TermTxNounCaseEln (String frm)
	{
		this(frm, null);
	}


	/**
	 * Returns all the tx-cpt's-terms of the tx-noun as: nm1,nm2,....<br/>
	 * ALGORITHM:<br/>
	 *		- The program unsing the <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango/term-rule_eln.html#ruleNameNnCs">
	 *		termTxCpt_sName-rules</a> finds the <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango/term-rule_eln.html#ruleTermNnC">
	 *		tx_noun-creating-rules</a>.<br/>
	 *		- If it needs more information, asks it from the user.
	 *
	 * @modified 2001.05.23
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));
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

//-ου:
			else if (termTxCpt_sName.endsWith("ού"))
			{
				//η αλεπού,αλεπούς,αλεπούδες,αλεπούδων
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2d1");
			}

// -ι:
			else if (termTxCpt_sName.endsWith("ή")||termTxCpt_sName.endsWith("η"))
			{
				int	 result= JOptionPane.showConfirmDialog(null,
					"Με κατάληξη -η έχουμε 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΟΥΣΙΑΣΤΙΚΩΝ Θηλυκών."
					+"\nΑρχαιόκλιτα (σκέψη,σκέψεων) ή Νεόκλιτα (ψυχή,ψυχών)."
					+"\nΝα κλιθεί σαν αρχαιόκλιτο;","Να κλιθεί σαν ΑΡΧΑΙΟΚΛΙΤΟ;",JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION)
				{
					//η σκέψη,σκέψης,σκέψεις,σκέψεων
					//η δύναμη,δύναμης,δυνάμεις,δυνάμεων
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2b1");
				}
				else //ΔΕΝ επηρεάζεται από την αρχαία
				{
//					if (tonos==3)//2001.05.20
//					{
//						//η όμοφρη
//						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2b3");
//					}
//					else
//					{
						//η ψυχή,ψυχής,ψυχές,ψυχών
						//η νίκη,νίκης,νίκες,νικών
						//η ζάχαρη
						termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2b2");
//					}
				}
			}

			else if (termTxCpt_sName.endsWith("ί")||termTxCpt_sName.endsWith("ι"))
			{
				//το παιδί,παιδιού,παιδιά,παιδιών
				//το τραγούδι,τραγουδιού,τραγούδια,τραγουδιών
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs3b1");
			}

// -ο
			else if (termTxCpt_sName.endsWith("ιμο"))
			{
				//το δέσιμο,δεσίματος,δεσίματα,δεσιμάτων
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs3c1");
			}
			else if (termTxCpt_sName.endsWith("ο")||termTxCpt_sName.endsWith("ό"))//2001.05.24
			{
				//το βουνό,βουνού,βουνά,βουνών
				//το πεύκο
				//το σίδερο,σίδερου,σίδερα,σίδερων
				//το πρόσωπο,προσώπου
				int	 result= JOptionPane.showConfirmDialog(null,
					"Με κατάληξη -ο έχουμε 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΟΥΣΙΑΣΤΙΚΩΝ ουδετέρων."
					+"\nΝα διατηρεί τον τόνο του (βουνό/πεύκο/σίδερο) ή να τον ανεβάζει (πρόσωπο,προσώπου)."
					+"\nΝα διατηρεί τον ΤΟΝΟ του;","Να διατηρεί τον τόνο του;",JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION)
				{
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs3c2");
				}
				else termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs3c3");
			}

			else if (termTxCpt_sName.endsWith("\u03ce")||termTxCpt_sName.endsWith("\u03c9"))//ώ,ω
			{
				//η Αργυρώ
				//η Φρόσω
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2e1");
			}

// -ς
			// -ας
			else if (termTxCpt_sName.endsWith("άς") || termTxCpt_sName.endsWith("ας")
							||termTxCpt_sName.endsWith("άσ") || termTxCpt_sName.endsWith("ασ") )
			{
				//μπορεί να είναι αρσενικό ή ουδέτερο.
				int	 result= JOptionPane.showConfirmDialog(null,
					"Κατάληξη -ας έχουν και αρσενικά (πχ ο σφουγγαράς,αγώνας,φύλακας) και ουδέτερα (πχ το κρέας)."
					+"\nΝα κλιθεί σαν Αρσενικό;","Να κλιθεί σαν ΑΡΣΕΝΙΚΟ;",JOptionPane.YES_NO_OPTION);
				if(result == JOptionPane.YES_OPTION)
				{
					//ο σφουγγαράς,σφουγγαρά,σφουγγαράδες,σφουγγαράδων
					//ο αγώνας,αγώνα,αγώνες,αγώνων
					//ο φύλακας,φύλακα,φύλακες,φυλάκων
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs1a1");
				}
				else {
					//το κρέας,κρέατος,κρέατα,κρεάτων
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs3d1");
				}
			}

			//αρσενικά σε -ες
			else if (termTxCpt_sName.endsWith("ές")||termTxCpt_sName.endsWith("έσ"))
			{
				//ο καφές,καφέ,καφέδες,καφέδων
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs1b1");
			}

			//αρσενικά σε -ης:
			else if (termTxCpt_sName.endsWith("ής")||termTxCpt_sName.endsWith("ης")
						||termTxCpt_sName.endsWith("ήσ")||termTxCpt_sName.endsWith("ησ"))
			{
				Object[] options = {"1) να κλιθεί σαν ΙΣΟΣΥΛΛΑΒΟ όπως ναύτης,ναύτες",
														"2) να κλιθεί σαν ΑΝΙΣΟΣΥΛΛΑΒΟ όπως καφετζής,καφετζήδες",
														"3) να κλιθεί σαν ΔΙΠΛΟ όπως πραματευτής,πραματευτές|πραματευτάδες"};
				Object selectedValue = JOptionPane.showInputDialog(null,
									"Με κατάληξη -ης υπάρχουν 3 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ-ΟΥΣΙΑΣΤΙΚΩΝ",	//message
									"Επέλεξε ΕΝΑ",																	//title
									JOptionPane.INFORMATION_MESSAGE,								//message type
									null,																						//icon
									options,																				//values to select
									options[0]);																		//initial selection
				if (((String)selectedValue).startsWith("1)"))
				{
					//ισοσύλλαβα
					//ο νικητής,νικητή,νικητές,νικητών
					//ο ναύτης,ναύτη,ναύτες,ναυτών.
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs1c1");
				}
				else if (((String)selectedValue).startsWith("2)"))
				{
					//ανισοσύλαβα
					//ο νοικοκύρης,νοικοκύρη,νοικοκύρηδες,νοικοκύρηδων
					//ο φούρναρης,φούρναρη,φουρνάρηδες,φουρνάρηδων
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs1c2");
				}
				else if (((String)selectedValue).startsWith("3)"))
				{
					//διπλό
					//ο πραματευτής,πραματευτή,πραματευτές/άδες,πραματευτών/τάδων
					//ο αφέντης
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs1c3");
				}
			}//αρσενικά σε ης

			// -ος
			else if (termTxCpt_sName.endsWith("ός") || termTxCpt_sName.endsWith("ος")
							||termTxCpt_sName.endsWith("όσ") || termTxCpt_sName.endsWith("οσ") )
			{
				Object[] options = {
														"1) να κλιθεί σαν ΑΡΣΕΝΙΚΟ (πχ ο ουρανός/δρόμος/καλόγερος-καλόγερου)",
														"2) να κλιθεί σαν ΑΡΣΕΝΙΚΟ (πχ ο κίνδυνος-κινδύνου)",
														"3) να κλιθεί σαν ΘΗΛΥΚΟ (πχ η διάμετρος)",
														"4) να κλιθεί σαν ΟΥΔΕΤΕΡΟ (πχ το γεγονός/μέρος/έδαφος"};
				Object selectedValue = JOptionPane.showInputDialog(null,
									"Δώσε επιπρόσθετη πληροφορία"
									+"\nΜε κατάληξη -ος υπάρχουν αρσενικά, Θηλυκά και ουδέτερα",//message
									"Επέλεξε ΕΝΑ",														//title
									JOptionPane.INFORMATION_MESSAGE,					//message type
									null,																			//icon
									options,																	//values to select
									options[0]);															//initial selection
				if (((String)selectedValue).startsWith("1)"))
				{
					//ο ουρανός,ουρανού,ουρανό,ουρανοί,ουρανών,ουρανούς
					//ο δρόμος,δρόμου,δρόμο,δρόμοι,δρόμων,δρόμους
					//ο καλόγερος,καλόγερου
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs1d1");
				}
				if (((String)selectedValue).startsWith("2)"))
				{
					//ο κίνδυνος,κινδύνου
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs1d2");
				}
				else if (((String)selectedValue).startsWith("3)"))
				{
					//αρχαιόκλητο: η διάμετρος,διαμέτρου,διάμετρο,διάμετροι,διαμέτρων,διαμέτρους
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs2c1");
				}
				else if (((String)selectedValue).startsWith("4)"))
				{
					//το γεγονός,γεγονότος,γεγονότα,γεγονότων
					//το μέρος,μέρους,μέρη,μερών
					//το έδαφος,εδάφους,εδάφη,εδαφών
					termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs3d2");
				}
			}

			//αρσενικά σε -ους
			else if (termTxCpt_sName.endsWith("ούς")||termTxCpt_sName.endsWith("ούσ"))
			{
				//ο παππούς,παππού,παππούδες,παππούδων
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 3);
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs1e1");
			}

			else if (termTxCpt_sName.endsWith("ως")||termTxCpt_sName.endsWith("ωσ")
							||termTxCpt_sName.endsWith("ώς")||termTxCpt_sName.endsWith("ώσ"))
			{
				//το φώς,φωτός,φώτα,φώτων
				//το καθεστώς,καθεστώτος,καθεστώτα,καθεστώτων
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmNnCs3d3");
			}

			//unknown case
			else {
				termTxCpt_sAll=termTxCpt_sName;
			}
		}

		return termTxCpt_sAll;
	}


	/**
	 * @modified
	 * @since 2001.04.12
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfIrregular()
	{
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
		else if (mfform.equals("γάμα"))
		{
			termTxCpt_sAll="γάμα";
		}
		else if (mfform.equals("γιώτα"))
		{
			termTxCpt_sAll="γιώτα";
		}
		else if (mfform.equals("δέλτα"))
		{
			termTxCpt_sAll="δέλτα";
		}
		else if (mfform.equals("έψιλον"))
		{
			termTxCpt_sAll="έψιλον";
		}
		else if (mfform.equals("ζήτα"))
		{
			termTxCpt_sAll="ζήτα";
		}
		else if (mfform.equals("ήτα"))
		{
			termTxCpt_sAll="ήτα";
		}
		else if (mfform.equals("θήτα"))
		{
			termTxCpt_sAll="θήτα";
		}
		else if (mfform.equals("κάπα"))
		{
			termTxCpt_sAll="κάπα";
		}
		else if (mfform.equals("λάμδα"))
		{
			termTxCpt_sAll="λάμδα";
		}
		else if (mfform.equals("μι"))
		{
			termTxCpt_sAll="μι";
		}
		else if (mfform.equals("νι"))
		{
			termTxCpt_sAll="νι";
		}
		else if (mfform.equals("ξι"))
		{
			termTxCpt_sAll="ξι";
		}
		else if (mfform.equals("όμικρον"))
		{
			termTxCpt_sAll="όμικρον";
		}
		else if (mfform.equals("πι"))
		{
			termTxCpt_sAll="πι";
		}
		else if (mfform.equals("ρο"))
		{
			termTxCpt_sAll="ρο";
		}
		else if (mfform.equals("σίγμα"))
		{
			termTxCpt_sAll="σίγμα";
		}
		else if (mfform.equals("ταυ"))
		{
			termTxCpt_sAll="ταυ";
		}
		else if (mfform.equals("ύψιλον"))
		{
			termTxCpt_sAll="ύψιλον";
		}
		else if (mfform.equals("φι"))
		{
			termTxCpt_sAll="φι";
		}
		else if (mfform.equals("χι"))
		{
			termTxCpt_sAll="χι";
		}
		else if (mfform.equals("ψι"))
		{
			termTxCpt_sAll="ψι";
		}
		else if (mfform.equals("ωμέγα"))
		{
			termTxCpt_sAll="ωμέγα";
		}
		else if (mfform.equals("βήτα"))
		{
			termTxCpt_sAll="βήτα";
		}
		return termTxCpt_sAll;
	}//getTermsOfTxConceptIfIrregular.

	/**
	 * @modified
	 * @since 2001.04.12
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
	}//getTermsOfTxConceptIfRule.

	/**
	 * Find the different tx-cpt's-terms of the tx-noun given its 'type'.
	 * @modified 2004.11.06
	 * @since 2001.03.23
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly(String rule)
	{
		termTxCpt_sRule=rule;
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));

//ΑΡΣΕΝΙΚΑ

		if (termTxCpt_sRule.equals("rlElnTrmNnCs1a1"))
		{
			tonos=greekTonosFind(termTxCpt_sName);
			if (tonos==1)
			{
					//ο σφουγγαράς,σφουγγαρά,σφουγγαράδες,σφουγγαράδων
					note="Κλίθηκε με τον rlElnTrmNnCs1a1(ο σφουγγαράς): αρσενικό -ας οξύτονο.";
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					termTxCpt_sAll=	"ο,"
								+stem +"άς,"
								+stem +"ά,"
								+stem +"ά,"
								+stem +"ά,"
								+stem +"άδες,"
								+stem +"άδων,"
								+stem +"άδες";
			}
			else if (tonos==2)
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
						termTxCpt_sAll=	"ο,"
								+stem +"ας,"
								+stem +"α,"
								+stem +"α,"
								+stem +"α,"
								+stem +"ες,"
								+stem2 +"ών,"
								+stem +"ες";
					}
					else {
						note="Κλίθηκε με τον rlElnTrmNnCs1a1(ο αγώνας): αρσενικό -ας παροξύτονο.";
						termTxCpt_sAll=	"ο,"
								+stem +"ας,"
								+stem +"α,"
								+stem +"α,"
								+stem +"α,"
								+stem +"ες,"
								+stem +"ων,"
								+stem +"ες";
					}
			}
			else if (tonos==3)
			{
				//μάστορας στον πληθυντικό κυρίως μαστόροι,μαστόρων, μαστόρους όπως (άγγελος) 2001.04.13
				//ο φύλακας,φύλακα,φύλακες,φυλάκων
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
				stem2=greekTonosIncrease(stem);
				note="Κλίθηκε με τον rlElnTrmNnCs1a1(ο φύλακας): αρσενικό -ας προπαροξύτονο.";
				termTxCpt_sAll=	"ο,"
							+stem +"ας,"
							+stem +"α,"
							+stem +"α,"
							+stem +"α,"
							+stem +"ες,"
							+stem2 +"ων,"
							+stem +"ες";
			}
		}

		//αρσενικά σε -ες
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1b1"))
		{
				//ο καφές,καφέ,καφέδες,καφέδων
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
				note="Κλίθηκε με τον rlElnTrmNnCs1b1(ο καφές): αρσενικό -ες.";
				termTxCpt_sAll=	"ο,"
							+stem +"ές,"
							+stem +"έ,"
							+stem +"έ,"
							+stem +"έ,"
							+stem +"έδες,"
							+stem +"έδων,"
							+stem +"έδες";
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1c1"))
		{
					tonos=greekTonosFind(termTxCpt_sName);
					//ισοσύλλαβα
					if (tonos==1)
					{
						//ο νικητής,νικητή,νικητές,νικητών
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs1c1(ο νικητής): αρσενικό -ης οξύτονο.";
						termTxCpt_sAll=	"ο,"
							+stem +"ής,"
							+stem +"ή,"
							+stem +"ή,"
							+stem +"ή,"
							+stem +"ές,"
							+stem +"ών,"
							+stem +"ές";
					}
					else if (tonos==2)
					{
						//ο ναύτης,ναύτη,ναύτες,ναυτών.
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs1c1(ο ναύτης): αρσενικό -ης παροξύτονο.";
						termTxCpt_sAll=	"ο,"
							+stem +"ης,"
							+stem +"η,"
							+stem +"η,"
							+stem +"η,"
							+stem +"ες,"
							+greekTonosRemove(stem) +"ών,"
							+stem +"ες";
					}
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1c1b"))
		{
						//ισοσύλλαβα
						//ο σταχτής
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs1c1b(ο σταχτής): ΕΠΙΘΕΤΟ αρσενικό -ής οξύτονο.";
						termTxCpt_sAll=	"ο,"
							+stem +"ής,"
							+stem +"ή|"+stem +"ιού,"
							+stem +"ή,"
							+stem +"ή,"
							+stem +"ιοί,"
							+stem +"ιών,"
							+stem +"ιούς";
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1c1c"))//2001.05.17
		{
			tonos=greekTonosFind(termTxCpt_sName);
			if (tonos==1)
			{
				//η συνεχής
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs1c1c(ο συνεχής): ΕΠΙΘΕΤΟ αρσενικό -ής (ανώμαλο).";
				termTxCpt_sAll=	"ο,"
							+stem +"ής,"
							+stem +"ούς,"
							+stem +"ή,"
							+stem +"ή|"+stem +"ής,"
							+stem +"είς,"
							+stem +"ών,"
							+stem +"είς";
			}
			if (tonos==2)
			{
						//ο ελώδης
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs2c1c(ο ελώδης): ΕΠΙΘΕΤΟ αρσενικό -ής (ανώμαλο).";
						termTxCpt_sAll=	"ο,"
							+stem +"ης,"
							+stem +"ους,"
							+stem +"η,"
							+stem +"η|"+stem +"ης,"
							+stem +"εις,"
							+greekTonosRemove(stem) +"ών," //πλήρης,πλήρων? η γραμμ λέει πληρών 2001.05.26
							+stem +"εις";
			}
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1c2"))
		{
					tonos=greekTonosFind(termTxCpt_sName);
					//ανισοσύλαβα
					if (tonos==1)
					{
						//ο καφετζής,καφετζήδες
						//ο πραματευτής,πραματευτάδες
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs1c2(ο πραματευτής): αρσενικό -ης οξύτονο ανισοσύλλαβο.";
						if (termTxCpt_sName.endsWith("τής"))
						{
							termTxCpt_sAll=	"ο,"
								+stem +"ής,"
								+stem +"ή,"
								+stem +"ή,"
								+stem +"ή,"
								+stem +"άδες,"
								+stem +"άδων,"
								+stem +"άδες";
						}
						else {
						note="Κλίθηκε με τον rlElnTrmNnCs1c2(ο καφετζής): αρσενικό -ης οξύτονο ανισοσύλλαβο.";
							termTxCpt_sAll=	"ο,"
								+stem +"ής,"
								+stem +"ή,"
								+stem +"ή,"
								+stem +"ή,"
								+stem +"ήδες,"
								+stem +"ήδων,"
								+stem +"ήδες";
						}
					}
					if (tonos==2)
					{
						//ο νοικοκύρης,νοικοκύρη,νοικοκύρηδες,νοικοκύρηδων
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs1c2(ο νοικοκύρης): αρσενικό -ης παροξύτονο.";
						termTxCpt_sAll=	"ο,"
							+stem +"ης,"
							+stem +"η,"
							+stem +"η,"
							+stem +"η,"
							+stem +"ηδες,"
							+stem +"ηδων,"
							+stem +"ηδες";
					}
					else if (tonos==3)
					{
						//ο φούρναρης,φούρναρη,φουρνάρηδες,φουρνάρηδων
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs1c2(ο φούρναρης): αρσενικό -ης προπαροξύτονο.";
						stem2=greekTonosIncrease(stem);
						termTxCpt_sAll=	"ο,"
							+stem +"ης,"
							+stem +"η,"
							+stem +"η,"
							+stem +"η,"
							+stem2 +"ηδες,"
							+stem2 +"ηδων,"
							+stem2 +"ηδες";
					}
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1c3"))
		{
					tonos=greekTonosFind(termTxCpt_sName);
					//διπλό
					if (tonos==1)
					{
						//ο πραματευτής,πραματευτή,πραματευτές/άδες,πραματευτών/τάδων
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs1c3(ο πραματευτής): αρσενικό -ης οξύτονο διπλό.";
						termTxCpt_sAll=	"ο,"
							+stem +"ής,"
							+stem +"ή,"
							+stem +"ή,"
							+stem +"ή,"
							+stem +"ές|" +stem +"άδες,"
							+stem +"ών|" +stem +"άδων,"
							+stem +"ές|" +stem +"άδες";
					}
					else if (tonos==2)
					{
						//ο αφέντης
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						stem2=greekTonosRemove(stem);
						note="Κλίθηκε με τον rlElnTrmNnCs1c3(ο αφέντης): αρσενικό -ης παροξύτονο διπλό.";
						termTxCpt_sAll=	"ο,"
							+stem +"ης,"
							+stem +"η,"
							+stem +"η,"
							+stem +"η,"
							+stem +"ες|" +stem2 +"άδες,"
							+stem2 +"ών|" +stem2 +"άδων,"
							+stem +"ες|" +stem2 +"άδες";
					}
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1c4"))//2001.05.17
		{
				//ο βαθύς
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
				note="Κλίθηκε με τον rlElnTrmNnCs1c4(ο βαθύς): επίθετο αρσενικό -ύς.";
				termTxCpt_sAll=	"ο,"
							+stem +"ύς,"
							+stem +"ύ|"+stem +"ιού,"
							+stem +"ύ,"
							+stem +"ύ,"
							+stem +"ιοί,"
							+stem +"ιών,"
							+stem +"ιούς";
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1d1"))
		{
			tonos=greekTonosFind(termTxCpt_sName);
			if (tonos==1)
			{
					//ο ουρανός,ουρανού,ουρανό,ουρανοί,ουρανών,ουρανούς
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs1d1(ο ουρανός): αρσενικό -ος οξύτονο.";
					termTxCpt_sAll=	"ο,"
							+stem +"ός,"
							+stem +"ού,"
							+stem +"ό,"
							+stem +"έ,"
							+stem +"οί,"
							+stem +"ών,"
							+stem +"ούς";
			}
			else if (tonos==2)
			{
					//ο δρόμος,δρόμου,δρόμο,δρόμοι,δρόμων,δρόμους
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs1d1(ο δρόμος): αρσενικό -ος παροξύτονο.";
					termTxCpt_sAll=	"ο,"
							+stem +"ος,"
							+stem +"ου,"
							+stem +"ο,"
							+stem +"ε,"
							+stem +"οι,"
							+stem +"ων,"
							+stem +"ους";
			}
			else if (tonos==3)
			{
					//ο αντίλαλος αντίλαλου, αντίλαλο, αντίλαλοι, αντίλαλων, αντίλαλους
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs1d1(ο καλόγερος): αρσενικό -ος προπαροξύτονο με γεν. προπαροξύτονη.";
					termTxCpt_sAll=	"ο,"
							+stem +"ος,"
							+stem +"ου,"
							+stem +"ο,"
							+stem +"ε,"
							+stem +"οι,"
							+stem +"ων,"
							+stem +"ους";
			}
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1d2"))
		{
					//ο κίνδυνος,κινδύνου,κίνδυνο,κίνδυνοι,κινδύνων,κινδύνους
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					stem2=greekTonosIncrease(stem);
					note="Κλίθηκε με τον rlElnTrmNnCs1d2(ο κίνδυνος): αρσενικό -ος προπαροξύτονο με γεν. παροξύτονη.";
					termTxCpt_sAll=	"ο,"
							+stem +"ος,"
							+stem2 +"ου,"
							+stem +"ο,"
							+stem +"ε,"
							+stem +"οι,"
							+stem2 +"ων,"
							+stem2 +"ους";
		}

		//αρσενικά σε -ους
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs1e1"))
		{
				//ο παππούς,παππού,παππούδες,παππούδων
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 3);
				note="Κλίθηκε με τον rlElnTrmNnCs1e1(ο παππούς): αρσενικό -ούς.";
				termTxCpt_sAll=	"ο,"
							+stem +"ούς,"
							+stem +"ού,"
							+stem +"ού,"
							+stem +"ού,"
							+stem +"ούδες,"
							+stem +"ούδων,"
							+stem +"ούδες";
		}

//*********************************************************************
//ΘΗΛΥΚΑ
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2a1"))
		{
					//η καρδιά,καρδιάς,καρδιές,καρδιών
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
					note="Κλίθηκε με τον rlElnTrmNnCs2a1(η καρδιά): θηλυκό -α ισοσύλλαβο.";
					termTxCpt_sAll=	"η,"
							+stem +"ά,"
							+stem +"άς,"
							+stem +"ά,"
							+stem +"ά,"
							+stem +"ές,"
							+stem +"ών,"
							+stem +"ές";
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2a1b"))//2001.05.17
		{
					//η γλυκιά,γλυκιάς
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs2a1b(η γλυκιά): ΕΠΙΘΕΤΟ θηλυκό -ιά ισοσύλλαβο.";
					termTxCpt_sAll=	"η,"
							+stem +"ιά,"
							+stem +"ιάς,"
							+stem +"ιά,"
							+stem +"ιά,"
							+stem +"ές,"
							+stem +"ών,"
							+stem +"ές";
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2a2"))
		{
					//η γιαγιά,γιαγιάς,γιαγιάδες,γιαγιάδων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
					note="Κλίθηκε με τον rlElnTrmNnCs2a2(η γιαγιά): θηλυκό -α ανισοσύλλαβο.";
					termTxCpt_sAll=	"η,"
							+stem +"ά,"
							+stem +"άς,"
							+stem +"ά,"
							+stem +"ά,"
							+stem +"άδες,"
							+stem +"άδων,"
							+stem +"άδες";
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2a3"))
		{
					//η θάλασσα
					//η ώρα,ώρας,ώρες,ωρών
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
					note="Κλίθηκε με τον rlElnTrmNnCs2a3(η ώρα): θηλυκό -α γεν.πληθ.οξύτονη.";
					termTxCpt_sAll=	"η,"
							+stem +"α,"
							+stem +"ας,"
							+stem +"α,"
							+stem +"α,"
							+stem +"ες,"
							+greekTonosRemove(stem) +"ών,"
							+stem +"ες";
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2a4"))
		{
					tonos=greekTonosFind(termTxCpt_sName);
					if (tonos==2)
					{
						//η ελπίδα,ελπίδας,ελπίδες,ελπίδων
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						note="Κλίθηκε με τον rlElnTrmNnCs2a4(η ελπίδα): θηλυκό -α παροξύτονο με γεν.πληθ.παροξύτονη.";
						termTxCpt_sAll=	"η,"
							+stem +"α,"
							+stem +"ας,"
							+stem +"α,"
							+stem +"α,"
							+stem +"ες,"
							+stem +"ων,"
							+stem +"ες";
					}
					else if (tonos==3)
					{
						//η οντότητα,οντότητας,οντότητες,οντοτήτων
						note="Κλίθηκε με τον rlElnTrmNnCs2a4(η οντότητα): θηλυκό -α προπαροξύτονο με γεν.πληθ.παροξύτονη.";
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						termTxCpt_sAll=	"η,"
							+stem +"α,"
							+stem +"ας,"
							+stem +"α,"
							+stem +"α,"
							+stem +"ες,"
							+greekTonosIncrease(stem) +"ων,"
							+stem +"ες";
					}
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2a5")) //2001.05.17
		{
						//η πλούσια,πλούσιας
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						note="Κλίθηκε με τον rlElnTrmNnCs2a5(η πλούσια): θηλυκό ΕΠΙΘΕΤΟ -α προπαροξ με γεν.πληθ. προπαροξ.";
						termTxCpt_sAll=	"η,"
							+stem +"α,"
							+stem +"ας,"
							+stem +"α,"
							+stem +"α,"
							+stem +"ες,"
							+stem +"ων,"
							+stem +"ες";
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2a5b")) //2001.05.27
		{
						//η φρέσκια (επίθετο)
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs2a5b(η φρέσκια): θηλυκό ΕΠΙΘΕΤΟ -α προπαροξ με γεν.πληθ. προπαροξ.";
						termTxCpt_sAll=	"η,"
							+stem +"ια,"
							+stem +"ιας,"
							+stem +"ια,"
							+stem +"ια,"
							+stem +"ες,"
							+stem +"ων,"
							+stem +"ες";
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2b1"))
		{
					tonos=greekTonosFind(termTxCpt_sName);
					if (tonos==2)
					{
						//η σκέψη,σκέψης,σκέψεις,σκέψεων
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						note="Κλίθηκε με τον rlElnTrmNnCs2b1(η σκέψη): θηλυκό -η παροξύτονο αρχαιόκλιτο.";
						termTxCpt_sAll=	"η,"
							+stem +"η,"
							+stem +"ης|"+stem +"εως,"
							+stem +"η,"
							+stem +"η,"
							+stem +"εις,"
							+stem +"εων,"
							+stem +"εις";
					}
					else if (tonos==3)
					{
						//η δύναμη,δύναμης,δυνάμεις,δυνάμεων
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						stem2=greekTonosIncrease(stem);
						note="Κλίθηκε με τον rlElnTrmNnCs2b1(η δύναμη): θηλυκό -η προπαροξύτονο αρχαιόκλιτο.";
						termTxCpt_sAll=	"η,"
							+stem +"η,"
							+stem +"ης|"+stem2 +"εως,"
							+stem +"η,"
							+stem +"η,"
							+stem2 +"εις,"
							+stem2 +"εων,"
							+stem2 +"εις";
					}
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2b2"))
		{
					tonos=greekTonosFind(termTxCpt_sName);
					if (tonos==1)
					{
						//η ψυχή,ψυχής,ψυχές,ψυχών
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						note="Κλίθηκε με τον rlElnTrmNnCs2b2(η ψυχή): θηλυκό -η οξύτονο νεόκλιτο.";
						termTxCpt_sAll=	"η,"
							+stem +"ή,"
							+stem +"ής,"
							+stem +"ή,"
							+stem +"ή,"
							+stem +"ές,"
							+stem +"ών,"
							+stem +"ές";
					}
					else if (tonos==2)
					{
						//η νίκη,νίκης,νίκες,νικών
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						note="Κλίθηκε με τον rlElnTrmNnCs2b2(η νίκη): θηλυκό -η παροξύτονο νεόκλιτο.";
						termTxCpt_sAll=	"η,"
							+stem +"η,"
							+stem +"ης,"
							+stem +"η,"
							+stem +"η,"
							+stem +"ες,"
							+greekTonosRemove(stem) +"ών|-,"
							+stem +"ες";
					}
					else if (tonos==3)//2001.05.25
					{
						//η ζάχαρη
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						note="Κλίθηκε με τον rlElnTrmNnCs2b2(η ζάχαρη): θηλυκό -η προπαροξύτονο νεόκλιτο.";
						termTxCpt_sAll=	"η,"
							+stem +"η,"
							+stem +"ης,"
							+stem +"η,"
							+stem +"η,"
							+stem +"ες,"
							+"-,"
							+stem +"ες";
					}
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2b3")) //2001.05.17
		{
						//η άσπρη,άσπρης,άσπρη,άσπρη,άσπρες,άσπρων,άσπρες,άσπρες
						//η όμορφη,όμορφης...
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
						note="Κλίθηκε με τον rlElnTrmNnCs2b3(η άσπρη/όμορφη): επίθετο θηλυκό -η παροξύτονο, διατηρεί τόνο.";
						termTxCpt_sAll=	"η,"
							+stem +"η,"
							+stem +"ης,"
							+stem +"η,"
							+stem +"η,"
							+stem +"ες,"
							+stem +"ων,"
							+stem +"ες";
		}

//θηλυκά σε -ς:
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2c1"))
		{
			tonos=greekTonosFind(termTxCpt_sName);
			if (tonos==3)
			{
				//αρχαιόκλιτο: η διάμετρος,διαμέτρου,διάμετρο,διάμετροι,διαμέτρων,διαμέτρους
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
				stem2=greekTonosIncrease(stem);
				note="Κλίθηκε με τον rlElnTrmNnCs2c1(η διάμετρος): θηλυκό -ος προπαροξύτονο αρχαιόκλιτο.";
				termTxCpt_sAll=	"η,"
							+stem +"ος,"
							+stem2 +"ου,"
							+stem +"ο,"
							+stem +"ο,"
							+stem +"οι|"+stem +"ες,"
							+stem2 +"ων,"
							+stem2 +"ους|"+stem +"ες";
			}
			if (tonos==2)
			{
				//αρχαιόκλιτο: η διχοτόμος,διχοτόμου
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
				note="Κλίθηκε με τον rlElnTrmNnCs2c1(η διχοτόμος): θηλυκό -ος παροξύτονο αρχαιόκλιτο.";
				termTxCpt_sAll=	"η,"
							+stem +"ος,"
							+stem +"ου,"
							+stem +"ο,"
							+stem +"ο,"
							+stem +"οι|"+stem +"ες,"
							+stem +"ων,"
							+stem +"ους|"+stem +"ες";
			}
			else {
				//αρχαιόκλιτο: η κιβωτός,κιβωτού,
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
				note="Κλίθηκε με τον rlElnTrmNnCs2c1(η κιβωτός): θηλυκό -ος οξύτονο αρχαιόκλιτο.";
				termTxCpt_sAll=	"η,"
							+stem +"ός,"
							+stem +"ού,"
							+stem +"ό,"
							+stem +"ό,"
							+stem +"οί|"+stem +"ές,"
							+stem +"ών,"
							+stem +"ούς|"+stem +"ές";
			}
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2c2"))
		{
			tonos=greekTonosFind(termTxCpt_sName);
			if (tonos==1)
			{
						//η συνεχής
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs2c2(η συνεχής): ΕΠΙΘΕΤΟ θηλυκό -ής (ανώμαλο).";
						termTxCpt_sAll=	"η,"
							+stem +"ής,"
							+stem +"ούς,"
							+stem +"ή,"
							+stem +"ής,"
							+stem +"είς,"
							+stem +"ών,"
							+stem +"είς";
			}
			if (tonos==2)
			{
						//η ελώδης
						stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
						note="Κλίθηκε με τον rlElnTrmNnCs2c2(η ελώδης): ΕΠΙΘΕΤΟ θηλυκό -ής (ανώμαλο).";
						termTxCpt_sAll=	"η,"
							+stem +"ης,"
							+stem +"ους,"
							+stem +"η,"
							+stem +"ης,"
							+stem +"εις,"
							+greekTonosRemove(stem) +"ών,"
							+stem +"εις";
			}
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2d1"))
		{
				//η αλεπού,αλεπούς,αλεπούδες,αλεπούδων
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
				note="Κλίθηκε με τον rlElnTrmNnCs2d1(η αλεπού): θηλυκό -ού.";
				termTxCpt_sAll=	"η,"
							+stem +"ού,"
							+stem +"ούς,"
							+stem +"ού,"
							+stem +"ού,"
							+stem +"ούδες,"
							+stem +"ούδων,"
							+stem +"ούδες";
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs2e1"))
		{
			tonos=greekTonosFind(termTxCpt_sName);
			if (tonos==1)
			{
				//η Αργυρώ
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
				note="Κλίθηκε με τον rlElnTrmNnCs2e1(η Αργυρώ): θηλυκό -ω οξύτονο.";
				termTxCpt_sAll=	"η,"
							+stem +"ώ,"
							+stem +"ώς,"
							+stem +"ώ,"
							+stem +"ώ,"
							+"-,"
							+"-,"
							+"-";
			}
			else if (tonos==2)
			{
				//η Φρόσω
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
				note="Κλίθηκε με τον rlElnTrmNnCs2e1(η Φρόσω): θηλυκό -ω παροξύτονο.";
				termTxCpt_sAll=	"η,"
							+stem +"ω,"
							+stem +"ως,"
							+stem +"ω,"
							+stem +"ω,"
							+"-,"
							+"-,"
							+"-";
			}
		}

//*********************************************************************
//ΟΥΔΕΤΕΡΑ

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3a1"))
		{
				tonos=greekTonosFind(termTxCpt_sName);
				if (tonos==2)
				{
					//το κύμα,κύματος,κύματα,κυμάτων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs3a1(το κύμα): ουδέτερο -μα παροξύτονο.";
					termTxCpt_sAll=	"το,"
							+stem +"μα,"
							+stem +"ματος,"
							+stem +"μα,"
							+stem +"μα,"
							+stem +"ματα,"
							+greekTonosRemove(stem) +"μάτων,"
							+stem +"ματα";
				}
				else if (tonos==3)
				{
					//το όνομα,ονόματος,ονόματα,ονομάτων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);	//όνο-
					stem2=greekTonosIncrease(stem);					//ονό-
					stem3=greekTonosRemove(stem);						//ονο-
					note="Κλίθηκε με τον rlElnTrmNnCs3a1(το όνομα): ουδέτερο -μα προπαροξύτονο.";
					termTxCpt_sAll=	"το,"
							+stem +"μα,"
							+stem2 +"ματος,"
							+stem +"μα,"
							+stem +"μα,"
							+stem2 +"ματα,"
							+stem3 +"μάτων,"
							+stem2 +"ματα";
				}
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3b1"))
		{
			tonos=greekTonosFind(termTxCpt_sName);
			if (tonos==1)
			{
				//το παιδί,παιδιού,παιδιά,παιδιών
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
				note="Κλίθηκε με τον rlElnTrmNnCs3b1(το παιδί): ουδέτερο -ι οξύτονο.";
				termTxCpt_sAll=	"το,"
							+stem +"ί,"
							+stem +"ιού,"
							+stem +"ί,"
							+stem +"ί,"
							+stem +"ιά,"
							+stem +"ιών,"
							+stem +"ιά";
			}
			else if (tonos==2)
			{
				//το τραγούδι,τραγουδιού,τραγούδια,τραγουδιών
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
				note="Κλίθηκε με τον rlElnTrmNnCs3b1(το τραγούδι): ουδέτερο -ι παροξύτονο.";
				termTxCpt_sAll=	"το,"
							+stem +"ι,"
							+greekTonosRemove(stem) +"ιού,"
							+stem +"ι,"
							+stem +"ι,"
							+stem +"ια,"
							+greekTonosRemove(stem) +"ιών,"
							+stem +"ια";
			}
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3b2"))//2001.05.17
		{
					//το βαθύ
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs3b2(το βαθύ): επίθετο ουδέτερο -ύ.";
					termTxCpt_sAll=	"το,"
							+stem +"ύ,"
							+stem +"ύ|"+stem +"ιού,"
							+stem +"ύ,"
							+stem +"ύ,"
							+stem +"ιά,"
							+stem +"ιών,"
							+stem +"ιά";
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3c1"))
		{
					//το δέσιμο,δεσίματος,δεσίματα,δεσιμάτων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 3);
					stem2=greekTonosRemove(stem);
					note="Κλίθηκε με τον rlElnTrmNnCs3c1(το δέσιμο): ουδέτερο -ιμο.";
					termTxCpt_sAll=	"το,"
							+stem +"ιμο,"
							+stem2 +"ίματος,"
							+stem +"ιμο,"
							+stem +"ιμο,"
							+stem2 +"ίματα,"
							+stem2 +"ιμάτων,"
							+stem2 +"ίματα";
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3c2"))
		{
			tonos=greekTonosFind(termTxCpt_sName);
			if (tonos==1)
			{
				//το βουνό,βουνού,βουνά,βουνών
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
				note="Κλίθηκε με τον rlElnTrmNnCs3c2(το βουνό): ουδέτερο -ο οξύτονο.";
				termTxCpt_sAll=	"το,"
							+stem +"ό,"
							+stem +"ού,"
							+stem +"ό,"
							+stem +"ό,"
							+stem +"ά,"
							+stem +"ών,"
							+stem +"ά";
			}
			else {
					//το πεύκο
					//το σίδερο,σίδερου,σίδερα,σίδερων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
					note="Κλίθηκε με τον rlElnTrmNnCs3c2(το πεύκο/σίδερο): ουδέτερο -ο παροξύτονο/προπαροξ.";
					termTxCpt_sAll=	"το,"
							+stem +"ο,"
							+stem +"ου,"
							+stem +"ο,"
							+stem +"ο,"
							+stem +"α,"
							+stem +"ων,"
							+stem +"α";
			}
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3c3"))//2001.05.24
		{
					//το πρόσωπο
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
					stem2=greekTonosIncrease(stem);
					note="Κλίθηκε με τον rlElnTrmNnCs3c3(το πρόσωπο): ουδέτερο -ο προπαροξ. που αλλάζει τόνο";
					termTxCpt_sAll=	"το,"
							+stem +"ο,"
							+stem2 +"ου,"
							+stem +"ο,"
							+stem +"ο,"
							+stem +"α,"
							+stem2 +"ων,"
							+stem +"α";
		}

//επίθετα σε -ς:
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3d1"))
		{
				//το κρέας,κρέατος,κρέατα,κρεάτων
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
				note="Κλίθηκε με τον rlElnTrmNnCs3d1(το κρέας): ουδέτερο -ας.";
				termTxCpt_sAll=	"το,"
							+stem +"ας,"
							+stem +"ατος,"
							+stem +"ας,"
							+stem +"ας,"
							+stem +"ατα,"
							+greekTonosRemove(stem) +"άτων,"
							+stem +"ατα";
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3d2"))
		{
				tonos=greekTonosFind(termTxCpt_sName);
				if (tonos==1)
				{
					//το γεγονός,γεγονότος,γεγονότα,γεγονότων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs3d2(το γεγονός): ουδέτερο -ος οξύτονο.";
					termTxCpt_sAll=	"το,"
							+stem +"ός,"
							+stem +"ότος,"
							+stem +"ός,"
							+stem +"ός,"
							+stem +"ότα,"
							+stem +"ότων,"
							+stem +"ότα";
				}
				else if (tonos==2)
				{
					//το μέρος,μέρους,μέρη,μερών
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs3d2(το μέρος): ουδέτερο -ος παροξύτονο.";
					termTxCpt_sAll=	"το,"
							+stem +"ος,"
							+stem +"ους,"
							+stem +"ος,"
							+stem +"ος,"
							+stem +"η,"
							+greekTonosRemove(stem) +"ών,"
							+stem +"η";
				}
				else if (tonos==3)
				{
					//το έδαφος,εδάφους,εδάφη,εδαφών
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					stem2=greekTonosIncrease(stem);
					note="Κλίθηκε με τον rlElnTrmNnCs3d2(το έδαφος): ουδέτερο -ος προπαροξύτονο.";
					termTxCpt_sAll=	"το,"
							+stem +"ος,"
							+stem2 +"ους,"
							+stem +"ος,"
							+stem +"ος,"
							+stem2 +"η,"
							+greekTonosRemove(stem) +"ών,"
							+stem2 +"η";
				}
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3d3"))
		{
				if (findNumberOfGreekVowels(termTxCpt_sName)==1)
				{
					//το φώς,φωτός,φώτα,φώτων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs3d3(το φως): ουδέτερο -ως μονοσύλλαβο.";
					termTxCpt_sAll=	"το,"
							+stem +"ως,"
							+stem +"ωτός,"
							+stem +"ως,"
							+stem +"ως,"
							+stem +"ώτα,"
							+stem +"ώτων,"
							+stem +"ώτα";
				}
				else {
					//το καθεστώς,καθεστώτος,καθεστώτα,καθεστώτων
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs3d3(το καθεστώς): ουδέτερο -ως μή μονοσύλλαβο.";
					termTxCpt_sAll=	"το,"
							+stem +"ώς,"
							+stem +"ώτος,"
							+stem +"ώς,"
							+stem +"ώς,"
							+stem +"ώτα,"
							+stem +"ώτων,"
							+stem +"ώτα";
				}
		}
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3d4"))//2001.05.17
		{
				tonos=greekTonosFind(termTxCpt_sName);
				if (tonos==1)
				{
					//το συνεχές
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs3d4(το συνεχές): ΕΠΙΘΕΤΟ ουδέτερο -ές.";
					termTxCpt_sAll=	"το,"
							+stem +"ές,"
							+stem +"ούς,"
							+stem +"ές,"
							+stem +"ές,"
							+stem +"ή,"
							+stem +"ών,"
							+stem +"ή";
				}
				else if (tonos==2)
				{
					//το ελώδες
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
					note="Κλίθηκε με τον rlElnTrmNnCs3d4(το ελώδες): ΕΠΙΘΕΤΟ ουδέτερο -ες.";
					termTxCpt_sAll=	"το,"
							+stem +"ες,"
							+stem +"ους,"
							+stem +"ες,"
							+stem +"ες,"
							+stem +"η,"
							+greekTonosRemove(stem) +"ών," //σύμφωνα με τη γραμματική. Αλλα στο 'πλήρες' μάλλον 'πλήρων' 2001.05.26
							+stem +"η";
				}
		}

		else if (termTxCpt_sRule.equals("rlElnTrmNnCs3e1"))//2001.05.20
		{
					//το βαθύ
					stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
					note="Κλίθηκε με τον rlElnTrmNnCs3e1(το βαθύ): ΕΠΙΘΕΤΟ ουδέτερο -ύ.";
					termTxCpt_sAll=	"το,"
							+stem +"ύ,"
							+stem +"ύ|"+stem +"ιού,"
							+stem +"ύ,"
							+stem +"ύ,"
							+stem +"ιά,"
							+stem +"ιών,"
							+stem +"ιά";
		}

//ΑΝΩΜΑΛΑ
		else if (termTxCpt_sRule.equals("rlElnTrmNnCs4a1")) //2001.05.13
		{
			termTxCpt_sAll=getTermsOfTxConceptIfIrregular();
		}

//*********************************************************************
//ΠΟΛΥΛΕΚΤΙΚΑ-ΧΑΡΑΚΤΗΡΙΣΤΙΚΩΝ
		else if (termTxCpt_sRule.startsWith("rlElnTrmNnCs5"))
		{
			//ο ορισμός έννοιας|εννοιών (rlElnTrmNnCs1d1)
			part1=termTxCpt_sName.substring(0,termTxCpt_sName.indexOf(" "));
			part2=termTxCpt_sName.substring(termTxCpt_sName.indexOf(" ")+1,termTxCpt_sName.length());
			String rl=termTxCpt_sRule.substring(12,termTxCpt_sRule.length());//ppp
			nms1=Textual.getTermsOfTxConceptAsArray(part1,"rlElnTrmNnCs"+rl);
			termTxCpt_sAll= nms1[0] +" " +part2 +","
						+nms1[1] +" " +part2 +","
						+nms1[2] +" " +part2 +","
						+nms1[3] +" " +part2 +","
						+nms1[4] +" " +part2 +","
						+nms1[5] +" " +part2 +","
						+nms1[6] +" " +part2 +","
						+nms1[7] +" " +part2;
		}

//ΠΟΛΥΛΕΚΤΙΚΑ ΕΙΔΙΚΩΝ
		else if (termTxCpt_sRule.startsWith("rlElnTrmNnCs6")) //2001.09.22
		{
			//η γενική έννοια
			part1=termTxCpt_sName.substring(0,termTxCpt_sName.indexOf(" "));
			part2=termTxCpt_sName.substring(termTxCpt_sName.indexOf(" ")+1,termTxCpt_sName.length());
			String rl1=termTxCpt_sRule.substring(12,termTxCpt_sRule.indexOf("-"));//ppp
			String rl2=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("-")+1,termTxCpt_sRule.length());
			nms1=Textual.getTermsOfTxConceptAsArray(part1,"rlElnTrmNnCs"+rl1);
			nms2=Textual.getTermsOfTxConceptAsArray(part2,"rlElnTrmNnCs"+rl2);
			termTxCpt_sAll= nms1[0] +"-" +nms2[0].substring(nms2[0].indexOf(" ")+1,nms2[0].length()) +","
						+nms1[1] +"-" +nms2[1] +","
						+nms1[2] +"-" +nms2[2] +","
						+nms1[3] +"-" +nms2[3] +","
						+nms1[4] +"-" +nms2[4] +","
						+nms1[5] +"-" +nms2[5] +","
						+nms1[6] +"-" +nms2[6] +","
						+nms1[7] +"-" +nms2[7];
		}

		else if (termTxCpt_sRule.equals("")||termTxCpt_sRule.equals("none"))
		{
			note="χωρίς κανόνας-κλίσης.";
			termTxCpt_sAll=termTxCpt_sName+",-";
		}

//unknown case
		else {
			note="Unkown term's-rule.";
			termTxCpt_sAll=termTxCpt_sName+",-";
		}

		return termTxCpt_sAll;
	}//end getTermsOfTxConceptIfRuleOnly.


}

