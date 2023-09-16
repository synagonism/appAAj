/**
 * TermTxNounAdverbEln.java - The greek-tx_adverb class.
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
 * Contains the lg-concept's--term-rules of greek tx_adverbs.
 * @modified 2004.09.07
 * @since 2001.05.30 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TermTxNounAdverbEln
	extends Term_TxConcept
{
		int tonos=-1;

	/**
	 * @modified 2004.07.25
	 * @since 2001.05.17
	 * @author HoKoNoUmo
	 */
	public TermTxNounAdverbEln (String nm, String rl)
	{
		super(nm, "termTxNoun_Adverb", "eln");
		termTxCpt_sRule=rl;
	}


	/**
	 * @modified
	 * @since 2001.05.17
	 * @author HoKoNoUmo
	 */
	public TermTxNounAdverbEln (String frm)
	{
		this(frm, null);
	}


	/**
	 * @modified
	 * @since 2001.05.30
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));
		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{
		}

		return termTxCpt_sAll;
	}


	/**
	 * @modified
	 * @since 2001.05.30
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfIrregular()
	{
		String mfform=termTxCpt_sName.toLowerCase(new Locale("el","GR"));
		note="είναι ανώμαλο επίρρημα.";

//ΑΝΩΜΑΛΑ ΑΛΦΑΒΗΤΙΚΑ:
		if (mfform.endsWith("αρχύτερα"))
		{
			note=note +" Έχει μόνο συγκριτικό.";
			termTxCpt_sAll="-,αρχύτερα,-";
		}
		else if (mfform.equals("γρήγορα")||mfform.equals("το γρηγορότερο"))
		{
			note=note +" Δεν έχει συγκριτικό βαθμό.";
			termTxCpt_sAll="γρήγορα,-,το γρηγορότερο";
		}
		else if (mfform.equals("εμπρός")||mfform.equals("εμπρόσ")
			||mfform.equals("μπρος")||mfform.equals("μπροσ")
			||mfform.equals("μπροστύτερα"))
		{
			termTxCpt_sAll="εμπρός|μπρός,μπροστύτερα,-";
		}
		else if (mfform.equals("καλά")||mfform.equals("καλύτερα")
			||mfform.equals("άριστα"))
		{
			termTxCpt_sAll="καλά,καλύτερα|πιο καλά,άριστα|πολύ καλά";
		}
		else if (mfform.equals("λίγο")||mfform.equals("λιγότερο")
			||mfform.equals("ελάχιστα"))
		{
			termTxCpt_sAll="λίγο,λιγότερο,πολύ λίγο|ελάχιστα";
		}
		else if (mfform.equals("νωρίς")||mfform.equals("νωρίσ")
			||mfform.equals("νωρίτερα"))
		{
			note=note +" Δεν έχει υπερθετικό βαθμό.";
			termTxCpt_sAll="νωρίς,νωρίτερα,-";
		}
		else if (mfform.equals("πολύ")||mfform.equals("περισσότερο")
						||mfform.equals("πιότερο")||mfform.equals("πάρα πολύ"))
		{
			termTxCpt_sAll="πολύ,περισσότερο|πιότερο,πάρα πολύ";
		}
		else if (mfform.equals("πρώτα")||mfform.equals("πρωτύτερα"))
		{
			note=note +" Δεν έχει υπερθετικό βαθμό.";
			termTxCpt_sAll="πρώτα,πρωτύτερα,-,";
		}
		else if (mfform.equals("ύστερα")||mfform.equals("υστερότερα"))
		{
			note=note +" Δεν έχει υπερθετικό βαθμό.";
			termTxCpt_sAll="ύστερα,υστερότερα,-";
		}
		return termTxCpt_sAll;
	}

	/**
	 * @modified
	 * @since 2001.05.30
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
	 * Finds the different tx-concept's-terms of a tx_adverb given its 'type'.
	 * @modified
	 * @since 2001.05.30
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly(String rule)
	{
		termTxCpt_sRule=rule;
		termTxCpt_sName=termTxCpt_sName.toLowerCase(new Locale("el","GR"));

//παραθετικά:
		if (termTxCpt_sRule.equalsIgnoreCase("elava1"))
		{
			//τα προερχόμενα από επίθετα -ος σχηματίζουν παραθετικά σε -ότερα-ότατα.
			if (termTxCpt_sName.endsWith("ά")||termTxCpt_sName.endsWith("α"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else if (termTxCpt_sName.endsWith("ότερα")||termTxCpt_sName.endsWith("ότατα"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -α-ότερα-ότατα"
										+" για να κλιθεί με τον κανόνα elava1");
			}
			note="Κλίθηκε με τον elava1: Τα επιρρήματα των επιθέτων σε -ος-η|α-ο σχηματίζουν παραθετικά"
					+"\n						όπως και επίθετα, μονολεκτικά σε -ότερα/-ότατα ή περιφραστικά.";
			if (greekTonosFind(termTxCpt_sName)==1)
					termTxCpt_sAll= stem+"ά," +stem+"ότερα|πιο "+stem+"ά," +stem+"ότατα|πολύ "+stem+"ά";
			else if (greekTonosFind(termTxCpt_sName)==2||greekTonosFind(termTxCpt_sName)==3)
					termTxCpt_sAll= stem+"α," +stem+"ότερα|πιο "+stem+"α," +stem+"ότατα|πολύ "+stem+"α";
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("elava2"))
		{
			if (termTxCpt_sName.endsWith("ά"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
			else if (termTxCpt_sName.endsWith("ύτερα")||termTxCpt_sName.endsWith("ότατα"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 5);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ά-ύτερα-ύτατα"
										+" για να κλιθεί με τον κανόνα elava2");
			}
			note="Κλίθηκε με τον elava2: Τα επιρρήματα των επιθέτων σε -ύς-ιά-ύ σχηματίζουν παραθετικά"
					+"\n						όπως και επίθετα, μονολεκτικά σε -ύτερα/-ύτατα ή περιφραστικά.";
			termTxCpt_sAll= stem+"ά," +stem+"ύτερα|πιο "+stem+"ά," +stem+"ύτατα|πολύ "+stem+"ά";
		}

		else if (termTxCpt_sRule.equalsIgnoreCase("elava3"))
		{
			if (termTxCpt_sName.endsWith("ώς")||termTxCpt_sName.endsWith("ώσ"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
			else if (termTxCpt_sName.endsWith("έστερα")||termTxCpt_sName.endsWith("έστατα"))
				stem=getFirstLettersIfSuffix(termTxCpt_sName, 6);
			else {
				stem="θεμα";
				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ώς-έστερα-έστατα"
										+" για να κλιθεί με τον κανόνα elava3");
			}
			note="Κλίθηκε με τον elava3: Τα επιρρήματα των επιθέτων σε -ης-ης-ες σχηματίζουν παραθετικά"
					+"\n						όπως και επίθετα, μονολεκτικά σε -έστερα/-έστατα ή περιφραστικά.";
			termTxCpt_sAll= stem+"ώς," +stem+"έστερα|πιο "+stem+"ώς," +stem+"έστατα|πολύ "+stem+"ώς";
		}

//περιφραστικά παραθετικά:
		else if (termTxCpt_sRule.equalsIgnoreCase("elavb1"))
		{
//			if (termTxCpt_sName.endsWith("ος")||termTxCpt_sName.endsWith("οσ")||termTxCpt_sName.endsWith("ός")||termTxCpt_sName.endsWith("όσ"))
//				stem=getFirstLettersIfSuffix(termTxCpt_sName, 2);
//			else if (termTxCpt_sName.endsWith("η")||termTxCpt_sName.endsWith("ή")||termTxCpt_sName.endsWith("ο")||termTxCpt_sName.endsWith("ό"))
//				stem=getFirstLettersIfSuffix(termTxCpt_sName, 1);
//			else
//			{
//				stem="θεμα";
//				JOptionPane.showMessageDialog(null, "Η λέξη ΠΡΕΠΕΙ να τελειώνει σε -ος-η-ο "
//										+"για να κλιθεί με τον κανόνα rlElnTrmAdj11d");
//			}
			note="Κλίθηκε με τον elavb1: επιρρήματα με περιφραστικά παραθετικά.";
			termTxCpt_sAll= termTxCpt_sName+", πιο " +termTxCpt_sName +", πολύ " +termTxCpt_sName;
		}

//επιρρήματα χωρίς παραθετικά:
		else if (termTxCpt_sRule.equalsIgnoreCase("elavc1"))
		{
			note="Κλίθηκε με τον elavc1: επιρρήματα χωρίς παραθετικά.";
			termTxCpt_sAll = termTxCpt_sName;
		}

//ΑΝΩΜΑΛΑ
		else if (termTxCpt_sRule.equalsIgnoreCase("elavd1"))
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

}
