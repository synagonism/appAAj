/**
 * TermTxVerbEln.java - The greek-textual-verb class.
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
 *
 */
package pk_Logo;

import pk_Util.Util;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Contains the lg-concept's--term-rules of greek tx_verbs.<p>
 *
 * The greek tx-verb has 54 terms. 2004.11.03<p>
 *
 * <b>ΠΟΛΥΛΕΚΤΙΚΟΤΗΤΑ</b>: Η νέα ελληνική σε μεγάλο βαθμό
 * τα πολυλεκτικά-ρήματα τα κλίνει
 * σαν μονολεκτικά: προ-έβαλα > πρόβαλα. παρα-έβλεπα > παράβλεπα.<br/>
 * Ο κανόνας αυτός όμως είναι ΓΕΜΑΤΟΣ εξαιρέσεις μιας και η επίδραση
 * της αρχαίας (λόγω καθαρεύουσας) είναι ακόμα μεγάλη στη γλώσσα μας πχ:
 * απο-κλείω > απ-έκλεισα,
 *
 * @modified 2004.11.03
 * @since 2001.03.16 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TermTxVerbEln
	extends Term_TxConcept
{
	String prefix="";
	//Before checking for irregulars, remove the first tx_adverb/prefosition if exist.


	/**
	 * @modified 2004.09.16
	 * @since 2001.03.16 (v00.02.00)
	 * @author HoKoNoUmo
	 *
	 */
	public TermTxVerbEln (String nm, String rl)
	{
		super(nm, "term_TxVerb", "eln");
		termTxCpt_sRule=rl;
	}

	/**
	 * @modified 2004.09.16
	 * @since 2001.03.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TermTxVerbEln(String nm)
	{
		this(nm, null);
	}


	/**
	 * This method contains the rules that find the termTxCpt_sAll of a tx_verb
	 * GIVEN the FIRST termTxCpt_sName eg (τρέχω, κοιμάμαι).
	 *
	 * @return	All tx-cpt's-terms of the tx-verb as: form1|form2,form3,form4;form4,form5...
	 *					ΕΝΕΡΓΗΤΙΚΗ-ΦΩΝΗ:
	 *					1) Ενεστώτας Οριστική
	 *					2) Ενεστώτας Προστακτική
	 *					3) Μετοχή
	 *					4) Παρατατικός
	 *					5) Αόριστος Οριστική
	 *					6) Αόριστος Υποτακτική
	 *					7) Αόριστος Προστακτική
	 *					ΠΑΘΗΤΙΚΗ ΦΩΝΗ:
	 *					8) Ενεστώτας Οριστική
	 *					9) Ενεστώτας Προστακτική
	 *					10)Παρατατικός
	 *					11)Αόριστος Οριστική
	 *					12)Αόριστος Υποτακτική
	 *					13)Αόριστος Προστακτική
	 *					14)Μετοχή
	 * @modified 2001.04.12
	 * @since 2001.03.16
	 * @author HoKoNoUmo
	 */
	public String findTermsOfTxConceptIfName()
	{
		String s="",s2="",s3="",s4="",s5="",s6="";
		String termTxCpt_sAll="";
		String termTxCpt_sRule="", ruleCode="";

		termTxCpt_sName=termTxCpt_sName.toLowerCase();//always I work on lowercase letters. 2001.03.18
		//first check if it is irregular
		termTxCpt_sAll=getTermsOfTxConceptIfIrregular();

		if (termTxCpt_sAll.length()==0)
		{

//*********************************************************************
//καταλήξεις -νω

//-αίνω/-αίνομαι
			if (termTxCpt_sName.endsWith("αίνω")||(termTxCpt_sName.endsWith("αίνομαι")))
			{
				Object[] options = {
									"1) με αόριστο -α-ήθηκα/-ημένος,		(rlElnTrmVbr112a): καταλαβαίνω",
									"2) με αόριστο -ηκα/-ασμένος,				(rlElnTrmVbr241b): ανεβαίνω",
									"3) με αόριστο -ανα-άθηκα/-αμένος,	(rlElnTrmVbr421a): βουβαίνω",
									"4) με αόριστο -ανα-άνθηκα/-ασμένος, (rlElnTrmVbr422a): λευκ-αίνω",
									"5) με αόριστο -υνα-ύθηκα/-υμένος,	(rlElnTrmVbr442a): ακριβαίνω",
									"6) με αόριστο -αξα-άχτηκα/-αγμένος,(rlElnTrmVbr521a): βυζαίνω",
									"7) με αόριστο -ασα-άθηκα/-αμένος,	(rlElnTrmVbr721a): αποσταίνω",
									"8) με αόριστο -ασα-άστηκα/-ασμένος,(rlElnTrmVbr821a): χορταίνω",
									"9) με αόριστο -ησα-ήθηκα/-ημένος,	(rlElnTrmVbr741a): ανασταίνω",
									"10) με αόριστο -υσα-ύθηκα/-υμένος,	 (rlElnTrmVbr747a): αρταίνω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -αίνω/-αίνομαι κλίνονται με 10 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ",	//message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("5)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("6)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("7)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("8)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("9)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("10)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}
//-έλνω-έλνομαι:
			else if (termTxCpt_sName.endsWith("έλνω")||(termTxCpt_sName.endsWith("έλνομαι")))
			{
				Object[] options = {
									"1) με αόριστο -αλα-άλθηκα/-αλμένος, (rlElnTrmVbr321a): ψέλνω",
									"2) με αόριστο -ειλα-άλθηκα/-αλμένος, (rlElnTrmVbr341a): στέλνω",
									"3) με αόριστο -ειλα-έλθηκα/-ελμένος, (rlElnTrmVbr342a): παραγγέλνω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -έλνω/-έλνομαι κλίνονται με 3 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}
//-έρνω/-έρνομαι
			else if (termTxCpt_sName.endsWith("έρνω")||(termTxCpt_sName.endsWith("έρνομαι")))
			{
				Object[] options = {
									"1) με αόριστο -αρα-άρθηκα/-αρμένος, (rlElnTrmVbr621a): γδέρνω",
									"2) με αόριστο -ειρα-άρθηκα/-αρμένος, (rlElnTrmVbr641a): δέρνω",
									"3) με αόριστο -ερα-έρθηκα/-ερμένος, (rlElnTrmVbr642a): φέρνω",
									"4) με αόριστο -υρα-ύρθηκα/-υρμένος, (rlElnTrmVbr643a): σέρνω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -έρνω/-έρνομαι κλίνονται με 4 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}
//-άνω-άνομαι
			else if (termTxCpt_sName.endsWith("άνω")||termTxCpt_sName.endsWith("άνομαι") )
			{
				Object[] options = {
									"1) με αόριστο -αξα-άχτηκα/-αγμένος, (rlElnTrmVbr512a): φτ'-άνω",
									"2) με αόριστο -ασα-άθηκα/-αμένος, (rlElnTrmVbr711a): χ-άνω",
									"3) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr811a): π'-άνω",
									"4) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr742a): αυξάνω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -άνω/-άνομαι κλίνονται με 4 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}
//-ένω-ένομαι
			else if (termTxCpt_sName.endsWith("ένω")||termTxCpt_sName.endsWith("ένομαι") )
			{
				Object[] options = {
									"1) με αόριστο -εινα-είθηκα/-ειμένος, (rlElnTrmVbr441a): μένω",
									"2) με αόριστο -εσα-έθηκα/-εμένος, (rlElnTrmVbr711a): δένω",
									"3) με αόριστο -υνα-ύθηκα/-υμένος, (rlElnTrmVbr443a): πλένω, ξεπλένω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -ένω/-ένομαι κλίνονται με 3 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//-'ι'νω
			else if ( termTxCpt_sName.endsWith("είνω")||termTxCpt_sName.endsWith("είνομαι")
							||termTxCpt_sName.endsWith("ήνω")||termTxCpt_sName.endsWith("ήνομαι")
							||termTxCpt_sName.endsWith("ίνω")||termTxCpt_sName.endsWith("ίνομαι")
							||termTxCpt_sName.endsWith("ύνω")||termTxCpt_sName.endsWith("ύνομαι") )
			{
				Object[] options = {
									"1) με αόριστο -α-θηκα/-μένος, (rlElnTrmVbr113a): οξύν-ω",
									"2) με αόριστο -να-θηκα/-μένος, (rlElnTrmVbr411a): κρί-νω",
									"3) με αόριστο -σα-θηκα/-μένος, (rlElnTrmVbr711a): στή-νω,λύ-νω",
									"4) με αόριστο -σα-στηκα/-σμένος, (rlElnTrmVbr811a): κλεί-νω,σβή-νω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -'ί'νω/-'ί'νομαι κλίνονται με 4 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//-ώνω:
			else if (termTxCpt_sName.endsWith("ώνω")||termTxCpt_sName.endsWith("ώνομαι") )
			{
				Object[] options = {
									"1) με αόριστο -σα-θηκα/-μένος, (rlElnTrmVbr711a): απλώνω",
									"2) με αόριστο -σα-στηκα/-σμένος, (rlElnTrmVbr811a): ζώνω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -ώνω/-ώνομαι κλίνονται με 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//-χνω: ψάχνω/έψαξα
			else if (termTxCpt_sName.endsWith("χνω")||termTxCpt_sName.endsWith("χνομαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr511a");

//*********************************************************************
//-ούγω: (άκουγα)άκουσα
			else if (termTxCpt_sName.endsWith("ούγω")||termTxCpt_sName.endsWith("ούγομαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr812a");

//-αίγω/αίγομαι|αίω/αίομαι: κλαίω
//και φταίω/αιξα
			else if (termTxCpt_sName.endsWith("αίγω")||termTxCpt_sName.endsWith("αίγομαι")
						||termTxCpt_sName.endsWith("αίω")||termTxCpt_sName.endsWith("αίομαι") )
			{
				Object[] options = {
									"1) με αόριστο -αιξα-αίχτηκα/-αιγμένος, (rlElnTrmVbr513a): φταίω",
									"2) με αόριστο -αψα-άφτηκα/-αμένος, (rlElnTrmVbr921a): κλαίω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -αίω/-αίομαι(γ) κλίνονται με 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ",	//message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//λαρυγγικόληκτα: ξα/χτηκα
			else if (termTxCpt_sName.endsWith("γγω")||termTxCpt_sName.endsWith("γγομαι")
						||termTxCpt_sName.endsWith("σσω")||termTxCpt_sName.endsWith("σσομαι")
						||termTxCpt_sName.endsWith("ττω")||termTxCpt_sName.endsWith("ττομαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr511a");

//χειλικόληκτα: ψα/φτηκα
			else if ( termTxCpt_sName.endsWith("πτω")||termTxCpt_sName.endsWith("φτω")
							||termTxCpt_sName.endsWith("πτομαι")||termTxCpt_sName.endsWith("φτoμαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr913a");

//βω-βομαι
			else if ( termTxCpt_sName.endsWith("βω")||termTxCpt_sName.endsWith("βομαι") )
			{
					Object[] options = {
											"1) με αόριστο -ψα-φτηκα/-μμένος, (rlElnTrmVbr911a): κόβω",
											"2) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr825a):σέβομαι",
											};
					Object selectedValue = JOptionPane.showInputDialog(null,
								"Δώσε επιπρόσθετη πληροφορία"
								+"\nΤα ρήματα σε -βω/-βομαι κλίνονται με 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
								"Επέλεξε ΕΝΑ",																	//title
								JOptionPane.QUESTION_MESSAGE,									//message type
								null,																						//icon
								options,																				//values to select
								options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//χειλικόληκτα πω|φω: ψα/φτηκα
			else if ( termTxCpt_sName.endsWith("πω")||termTxCpt_sName.endsWith("πομαι")
							||termTxCpt_sName.endsWith("φω")||termTxCpt_sName.endsWith("φομαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr911a");

//γω-χω: ξα/χτηκα
			else if ( termTxCpt_sName.endsWith("γω")||termTxCpt_sName.endsWith("χω")
							||termTxCpt_sName.endsWith("γομαι")||termTxCpt_sName.endsWith("χομαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr512a");

//-ζω: σα/στηκα ή ξα/χτηκα
			else if (termTxCpt_sName.endsWith("ζω")||termTxCpt_sName.endsWith("ζομαι") )
			{
					Object[] options = {"1) με αόριστο -σα/-στηκα/-σμένος, (rlElnTrmVbr811a): αγκαλ'άζω",
															"2) με άριστο -ξα/-χτηκα/-γμένος, (rlElnTrmVbr512a): βουλ'άζω"};
					Object selectedValue = JOptionPane.showInputDialog(null,
								"Δώσε επιπρόσθετη πληροφορία"
								+"\nΤα ρήματα σε -ζω/-ζομαι κλίνονται με 2 κανόνες κλίσης", //message
								"Επέλεξε ΕΝΑ",																	//title
								JOptionPane.QUESTION_MESSAGE,									//message type
								null,																						//icon
								options,																				//values to select
								options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//-θω: σα/στηκα
			else if (termTxCpt_sName.endsWith("θω")||termTxCpt_sName.endsWith("θομαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr811a");

//-κω:
			else if (termTxCpt_sName.endsWith("κω")||termTxCpt_sName.endsWith("κομαι") )
			{
					Object[] options = {
									"1) με αόριστο -ξα-χτηκα/-γμένος, (rlElnTrmVbr511a): διδάσκω",
									"2) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr746a): βόσκω",
									"3) με αόριστο -ξα-χτηκα/-γμένος, (rlElnTrmVbr512a): πλέκω",
									"4) με αόριστο -ω/-α ελλιπής, (rlElnTrmVbr111b): ανήκω",
									};
					Object selectedValue = JOptionPane.showInputDialog(null,
								"Δώσε επιπρόσθετη πληροφορία"
								+"\nΤα ρήματα σε -κω/-κομαι κλίνονται με 4 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
								"Επέλεξε ΕΝΑ",																	//title
								JOptionPane.QUESTION_MESSAGE,									//message type
								null,																						//icon
								options,																				//values to select
								options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//-λω
			else if (termTxCpt_sName.endsWith("λω")||termTxCpt_sName.endsWith("λομαι") )
			{
					Object[] options = {
									"1) με αόριστο -α, 'ελιπής' (rlElnTrmVbr111b): βάλ-ω",
									"2) με αόριστο -ειλα-έλθηκα/-ελμένος, (rlElnTrmVbr343a): αγγ-έλλω",
									};
					Object selectedValue = JOptionPane.showInputDialog(null,
								"Δώσε επιπρόσθετη πληροφορία"
								+"\nΤα ρήματα σε -λω/-λομαι κλίνονται με 2 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
								"Επέλεξε ΕΝΑ",																	//title
								JOptionPane.QUESTION_MESSAGE,									//message type
								null,																						//icon
								options,																				//values to select
								options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//φωνηεντόληκτα

//-άω
			else if (termTxCpt_sName.endsWith("άω") )
			{
				//ρωτά σε ποιά τάξη: γελώ-γελάς
				Object[] options = {
									"1) με αόριστο -αξα-άχτηκα/-αγμένος, (rlElnTrmVbr522a): πετ-άω",
									"2) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr823a): γελ-άω",
									"3) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr822a): περ-νάω",
									"4) με αόριστο -εσα-έθηκα/-εμένος, (rlElnTrmVbr731a): βαρ-άω",
									"5) με αόριστο -ηξα-ήχτηκα/-ηγμένος, (rlElnTrmVbr541a): τραβ-άω",
									"6) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr743a): αγαπ-άω",
									"7) με αόριστο -ησα-ήστηκα/-ησμένος, (rlElnTrmVbr841a): ζουλ-άω",
									"8) με αόριστο -υσα-ύστηκα/-υσμένος, (rlElnTrmVbr844a): μεθ-άω",
									"9) με αόριστο -αξα-άχτηκα/-αγμένος, (rlElnTrmVbr513a): φυλ-άω&φυλάγω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
								"Δώσε επιπρόσθετη πληροφορία"
								+"\nΤα ρήματα σε -άω (άγω|ώ) κλίνονται με 9 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ",	//message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("5)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("6)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("7)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("8)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("9)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//-έω: υσα/υστηκα
			else if (termTxCpt_sName.endsWith("έω")||termTxCpt_sName.endsWith("έομαι") )
			{
				Object[] options = {
									"1) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr746a): δέ-ομαι",
									"2) με αόριστο -υσα-ύστηκα/-υσμένος, (rlElnTrmVbr845a): πλέ-ω",
									"3) με αόριστο -ξα-χτηκα/-γμένος, (rlElnTrmVbr513a): αρμέ-ω&αρμέ-γω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -έω/-έομαι κλίνονται με 3 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}
//-ούω: (άκουγα)άκουσα
			else if (termTxCpt_sName.endsWith("ούω")||termTxCpt_sName.endsWith("ούομαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr812a");
//-ύω: (ίδρυα)σα/στηκα
			else if (termTxCpt_sName.endsWith("ύω")||termTxCpt_sName.endsWith("ύομαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr712a");

//*********************************************************************
//-άμαι/άσαι: λύπησα/ήθηκα
			else if (termTxCpt_sName.endsWith("άμαι") )
				termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr745a");

//ιέμαι:
			else if (termTxCpt_sName.endsWith("ιέμαι") )
			{
				//ρωτά σε ποιά τάξη: γελώ-γελάς
				Object[] options = {
									"1) με αόριστο -αξα-άχτηκα/-αγμένος, (rlElnTrmVbr522a): πετάω",
									"2) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr823a): γελάω",
									"3) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr822a): περνάω",
									"4) με αόριστο -εσα-έθηκα/-εμένος, (rlElnTrmVbr731a): βαράω",
									"5) με αόριστο -ηξα-ήχτηκα/-ηγμένος, (rlElnTrmVbr541a): τραβάω",
									"6) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr743a): αγαπάω",
									"7) με αόριστο -ησα-ήστηκα/-ησμένος, (rlElnTrmVbr841a): ζουλάω",
									"8) με αόριστο -υσα-ύστηκα/-υσμένος, (rlElnTrmVbr844a): μεθάω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
								"Δώσε επιπρόσθετη πληροφορία"
								+"\nΤα ρήματα σε -άω&ώ/-ιέμαι κλίνονται με 8 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("5)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("6)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("7)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("8)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//-ούμαι/είσαι:
			else if (termTxCpt_sName.endsWith("ούμαι") )
			{
				Object[] options = {
									"1) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr824a): διαθλώ,-ούμαι-είσαι",
									"2) με αόριστο -εσα-έθηκα/-εμένος, (rlElnTrmVbr732a): αφαιρώ,-ούμαι-είσαι",
									"3) με αόριστο -εσα-έστηκα/-εσμένος, (rlElnTrmVbr832a): καλώ,-ούμαι-είσαι",
									"4) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr744a): θεωρώ,-ούμαι-είσαι",
									"5) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr745a): λυπώ,-ούμαι-άσαι",
									"6) με αόριστο -ησα-ήστηκα/-ησμένος, (rlElnTrmVbr842a): -ούμαι-είσαι",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -ώ/-ούμαι κλίνονται με 6 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ",	//message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("5)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("6)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//-ώ|ω μονοσύλλαβο
			else if (termTxCpt_sName.endsWith("ώ")||(termTxCpt_sName.endsWith("ω")&&findNumberOfGreekVowels(termTxCpt_sName)==1) )//σπω
			{
				Object[] options = {
									"1) με αόριστο -αξα-άχτηκα/-αγμένος, (rlElnTrmVbr522a): πετάω-άς",
									"2) με αόριστο -ηξα-ήχτηκα/-ηγμένος, (rlElnTrmVbr541a): τραβάω-άς",
									"3) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr823a): γελάω-άς",
									"4) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr822a): περνάω-άς",
									"5) με αόριστο -ασα-άστηκα/-ασμένος, (rlElnTrmVbr824a): διαθλώ-είς,-ούμαι-είσαι",
									"6) με αόριστο -εσα-έθηκα/-εμένος, (rlElnTrmVbr731a): βαράω-άς",
									"7) με αόριστο -εσα-έθηκα/-εμένος, (rlElnTrmVbr732a): αφαιρώ-είς,-ούμαι-είσαι",
									"8) με αόριστο -εσα-έστηκα/-εσμένος, (rlElnTrmVbr832a): καλώ-είς,-ούμαι-είσαι",
									"9) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr743a): αγαπάω-άς",
									"10) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr744a): θεωρώ-είς,-ούμαι-είσαι",
									"11) με αόριστο -ησα-ήθηκα/-ημένος, (rlElnTrmVbr744a): λυπώ-είς,-ούμαι-άσαι",
									"12) με αόριστο -ησα-ήστηκα/-ησμένος, (rlElnTrmVbr841a): ζουλάω-άς",
									"13) με αόριστο -ησα-ήστηκα/-ησμένος, (rlElnTrmVbr842a):-ώ-είς,-ούμαι-είσαι",
									"14) με αόριστο -υσα-ύστηκα/-υσμένος, (rlElnTrmVbr844a): μεθάω-άς",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"Δώσε επιπρόσθετη πληροφορία"
							+"\nΤα ρήματα σε -ώ (-ιέμαι|-ούμαι κλίνονται με 14 ΚΑΝΟΝΕΣ-ΚΛΙΣΗΣ", //message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("5)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("6)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("7)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("8)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("9)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("10)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("11)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("12)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("13)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("14)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

//όλα τα άλλα σε -ω ???
//με τον ελλιπή τρέμω/έτρεμα
//ή σα/θηκα, σα/στηκα
			else if (termTxCpt_sName.endsWith("ω")||termTxCpt_sName.endsWith("ομαι") )
			{
				Object[] options = {
									"1) με αόριστο -α ελλιπής, (rlElnTrmVbr111b): τρέμ-ω",
									"2) με αόριστο -α-θηκα/-μένος, (rlElnTrmVbr113a): οξύν-ω",
									"3) με αόριστο -σα-θηκα/-μένος, (rlElnTrmVbr712a): ιδρύ-ω",
									"4) με αόριστο -σα-στηκα/-σμένος, (rlElnTrmVbr813a): αποκλεί-ω",
									"5) με αόριστο -σα-στηκα/-σμένος, (rlElnTrmVbr812a): ακού-'γ'ω",
									"6) με αόριστο -ισα/-ισμένος, (rlElnTrmVbr843b): σαλτάρ-ω",
									};
				Object selectedValue = JOptionPane.showInputDialog(null,
							"ΠΡΟΣΟΧΗ: ρήμα σε -ω/-ομαι που ΔΕΝ υπάρχει άλλος κανόνας-ενεστώτα ισχυρότερος να προηγηθεί."
							+"\nΠως θέλει να κλιθεί;",	//message
							"Επέλεξε ΕΝΑ",																	//title
							JOptionPane.QUESTION_MESSAGE,									//message type
							null,																						//icon
							options,																				//values to select
							options[0]);																		//initial selection
				if (selectedValue!=null)
				{
					termTxCpt_sRule=(String) selectedValue;
					ruleCode=termTxCpt_sRule.substring(termTxCpt_sRule.indexOf("(")+1,termTxCpt_sRule.lastIndexOf(")"));
				}
				if (termTxCpt_sRule.startsWith("1)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("2)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("3)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("4)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("5)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
				if (termTxCpt_sRule.startsWith("6)")) termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly(ruleCode);
			}

			else {
				note="ΑΓΝΩΣΤΗ ΚΑΤΑΛΗΞΗ ΕΝΕΣΤΩΤΑ";
			}
		}//it is not irregular.

		return termTxCpt_sAll;
	}//enf of findTermsOfTxConceptIfName.


	/**
	 * I need this method to check for irregularity before searching by term's-rule or not.
	 *
	 * Εδώ τοποθετώ και τα ρήματα που έχουν 1 φωνή.
	 * Στην πρώτη σειρά θα τοποθετώ την αιτία ανωμαλίας ως εξής: "ανώμαλο - ...: "	2001.03.26
	 * @modified 2004.11.03
	 * @since 2001.03.25 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfIrregular()
	{
		String termTxCpt_sAll="";

		//Μάλον όποιο ρήμα διατηρεί ΕΣΩΤΕΡΙΚΗ-ΑΥΞΗΣΗ πρέπει να το κρατήσω σαν εξαίρεση
		//και όλα τα άλλα τα σύνθετα να κλίνονται σαν μονολεκτικά. 2001.04.09
		//Πρέπει όμως να προστθέσω στις εξαιρέσεις και τα σύνθετά τους.

		//Ξεχωρίζω το πρώτο συνθετικό για να ψάχνει στα ανώμαλα χωρίς αυτό.
		//Ετσι γράφουμε ΜΙΑ φορά το ανώμαλο ρήμα, και έχουμε τους τύπους του με όλα τα σύνθετά του.
		//αν-αιρώ, αν-αγγέλω
		//αφ-αιρώ
		//δι-αιρώ
		//εξ-αιρώ
		//επι-δρώ
		//καθ-αιρώ
		//κατ-αγγέλω
		//συν-αιρώ
//		if (termTxCpt_sName.startsWith("κακο")) prefix="κακο";
//		else if (termTxCpt_sName.startsWith("καλο")) prefix="καλο";
//		//Τα σύνθετα με 'παρα', χάνουν την αύξηση. παραέφαγα > παράφαγα.
//		else if (termTxCpt_sName.startsWith("παρα")) prefix="παρα";
//		else if (termTxCpt_sName.startsWith("πολυ")) prefix="πολυ";

		//lg_conceptName = monowordName.
//		String lg_conceptName=getLastLettersIfPrefix(termTxCpt_sName,prefix.length());
		String lg_conceptName=termTxCpt_sName;

		if (lg_conceptName.equals("απονέμω")||lg_conceptName.equals("απονεμω"))
		{
			note="'ανώμαλο': κλίνεται ανάλογα με το μ-ένω/έμ-εινα.";
			termTxCpt_sAll=
				"απονέμω,απονέμεις,απονέμει,απονέμουμε,απονέμετε,απονέμουν#"
			 +"απονέμε#"
			 +"απόνεμα,απόνεμες,απόνεμε,απονέμαμε,απονέματε,απόνεμαν|απονέμανε#"
			 +"απόνειμα|απένειμα,...#"
			 +"απονείμω,απονείμεις,απονείμει,απονείμουμε|απονείμομε,απονείμετε,απονείμουν|απονείμουνε#"
			 +"απονείμε#"
			 +"απονέμοντας|απονείοντας#"

			 +"απονέμομαι,απονέμεσαι,απονέμεται,απονεμόμαστε,απονέμεστε,απονέμονται#"
			 +"απονέμου#"
			 +"απονεμόμουνα,μενόσουν,μενόταν|μενότανε,μενόμαστε,μενόσαστε,μένονταν|μενόντανε#"
			 +"απονεμήθηκα,μείθηκες,μείθηκε,μαθήκαμε,μαθήκατε,μείθηκαν|μαθήκανε#"
			 +"απονεμειθώ,μειθείς,μειθεί,μειθοείμε,μειθείτε,μειθοείν|μειθούνε#"
			 +"απονείμου#"
			 +"απονεμειμένος";
		}
		else if (lg_conceptName.equals("αρέσω")||lg_conceptName.equals("αρεσω"))
		{
			// αρέσω,(άρεσα|άρεζα),άρεσα,-,-
			//κλείνετε σαν το ιδρύ-ω/ίδρυ-α/ίδρυ-σα, αλλά επειδή
			//το ο χαρακτήρας είναι (σ) δεν μπορεί να ξεχωρίσει παρατατικό/αρόριστο
			//και γιαυτό προσθέτει το ζ
			note="ανώμαλο: αρέσω,άρεζα/άρεσα,αρεστός.";
			termTxCpt_sAll=
							 "αρέσω,αρέσεις,αρέσει,αρέσουμε,αρέσετε,αρέσουν|αρέσουνε#"
							+"άρεζε#"
							+"άρεζα,άρεζες,άρεζε,αρέζαμε,αρέζατε,άρεζαν#"
							+"άρεσα,άρεσες,άρεσε,αρέσαμε,αρέσατε,άρεσαν#"
							+"αρέσω,αρέσεις,αρέσει,αρέσουμε,αρέσετε,αρέσουν|αρέσουνε#"
							+"άρεσε#"
							+"αρεστός#"

							+"αρέσκομαι#"
							+"αρέσκου#"
							+"αρεσκόμουν#"
							+"-,-,-,-,-#"
							+"αρεστώ,αρεστείς#"
							+"-#"
							+"αρεστός";
		}
		else if (lg_conceptName.equals("αφήνω")||lg_conceptName.equals("αφηνω"))
		{
			note="ανώμαλο του rlElnTrmVbr711a (στήνω): αντί αφήθηκα > αφέθηκα, άφησα & αφήκα.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr711a");
		}
//β
		else if (lg_conceptName.equals("βάζω")||lg_conceptName.equals("βαζω"))
		{
			note="ανώμαλο σε σχέση με τα ρήματα με ενεστώτα -ζω: που έχουν αόριστο -ξα/-σα";
			termTxCpt_sAll="βάζω,έβαλα,βάλθηκα,βαλμένος";
		}
		else if (lg_conceptName.equals("βάλλω")||lg_conceptName.equals("βαλλω")
						||lg_conceptName.equals("βάλω")||lg_conceptName.equals("βαλω"))
		{
			note = "ανώμαλο (προβάλλω|προβάλω|προέβαλλα|προέβαλα)(προ,προσ,ανα)";
			termTxCpt_sAll="βάλλω,έβαλα,βλήθηκα,βλημένος";
		}
		else if (lg_conceptName.equals("βγάζω")||lg_conceptName.equals("βγαζω"))
		{
			note="ανώμαλο: ";
			termTxCpt_sAll="βγάζω,έβγαλα,βγάλθηκα,βγαλμένος";
		}
		else if (lg_conceptName.equals("βγαίνω")||lg_conceptName.equals("βγαινω"))
		{
			note="ανώμαλο του rlElnTrmVbr241b: έχει μετοχή αντί βγασμένος > βγαλμένος.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr241b");
		}
		else if (lg_conceptName.equals("βλέπω")||lg_conceptName.equals("βλεπω"))
		{
			note="ανώμαλο: ";
			termTxCpt_sAll="βλέπω,είδα|ανάβλεψα|πρόβλεψα,ειδώθηκα|ιδωθώ,ιδωμένος";
		}
		else if (lg_conceptName.equals("βρέχω")||lg_conceptName.equals("βρεχω"))
		{
			note="ανώμαλο του rlElnTrmVbr512a (προσέχ-ω): βρέχτηκα και βράχηκα|βράχτηκα, βρεγμένος&βρεμένος.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr512a");
		}
		else if (lg_conceptName.equals("βρίσκω")||lg_conceptName.equals("βρισκω"))
		{
			note="ανώμαλο: ";
			termTxCpt_sAll="βρίσκω,έβρισκα,βρήκα|ήβρα,βρέθηκα,-";
		}
//γ
		else if (lg_conceptName.equals("γίνομαι")||lg_conceptName.equals("γινομαι"))
		{
			note="ανώμαλο: ";
			termTxCpt_sAll="γίνομαι,έγινα,γίνηκα(γενώ),γινωμένος";
		}
		else if (lg_conceptName.equals("διαβαίνω")||lg_conceptName.equals("διαβαίνω"))
		{
			note="ανώμαλο: δεν έχει μετοχή.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr511b");
		}
		else if (lg_conceptName.equals("διαρρέω")||lg_conceptName.equals("διαρρέω"))
		{
			// διαρρέω,διέρρευσα,-,-
			note="ανώμαλο: ";
			termTxCpt_sAll="διαρρέω,διέρρευσα,-,-";
		}
		else if (lg_conceptName.equals("δίνω")||lg_conceptName.equals("δίνω"))
		{
			// δίνω,έδωσα|έδωκα(δώσε),δόθηκα,δοσμένος|δομένος
			note="ανώμαλο: ";
			termTxCpt_sAll="δίνω,έδωσα|έδωκα(δώσε),δόθηκα,δοσμένος|δομένος";
		}
		else if (lg_conceptName.equals("είμαι")||lg_conceptName.equals("είμαι"))
		{
			note="ανώμαλο: Χρησιμοποιείται ως βοηθητικό στην κλίση άλλων.";
			termTxCpt_sAll=
				 "είμαι,είσαι,είναι,είμαστε,είστε,είναι#"
				+"-#"
				+"ήμουν,ήσουν,ήταν,ήμαστε,ήσαστε,ήταν#"
				+"ήμουν,ήσουν,ήταν,ήμαστε,ήσαστε,ήταν#"
				+"-,-,-,-,-#"
				+"-#"
				+"όντας";
		}
		else if (lg_conceptName.equals("επαινώ")||lg_conceptName.equals("επαινώ"))
		{
			// επαινώ|παινώ|παινεύω,επαίνεσα|παίνεψα,επαινέθηκα|παινεύτηκα,παινεμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="επαινώ|παινώ|παινεύω,επαίνεσα|παίνεψα,επαινέθηκα|παινεύτηκα,παινεμένος";
		}
		else if (lg_conceptName.equals("επιλέγω")||lg_conceptName.equals("επιλέγομαι"))
		{
			note="ανώμαλο: διατηρεί την εσωτερική αύξηση (επέλεξα).\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr512a");
		}
		else if (lg_conceptName.equals("επιστρατεύω")||lg_conceptName.equals("επιστρατεύω"))
		{
			note="ανώμαλο - έχει αόριστο (σα) ενώ τα αύω/εύω σε (ψα).\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr912a");
		}
		else if (lg_conceptName.equals("έρχομαι")||lg_conceptName.equals("ερχομαι"))
		{
			note="ανώμαλο του κανόνα: προσέχω/πρόσεξα/προσέχτηκα.";
			termTxCpt_sAll="έρχομαι,ήρθα,-,-";
		}
		else if (lg_conceptName.equals("εύχομαι")||lg_conceptName.equals("ευχομαι"))
		{
			note="ανώμαλο του κανόνα: προσέχω/πρόσεξα/προσέχτηκα.";
			termTxCpt_sAll="εύχομαι,-,ευχήθηκα,-";
		}
		else if (lg_conceptName.equals("έχω")||lg_conceptName.equals("έχω"))
		{
			note="ανώμαλο και βοηθητικό.";
			termTxCpt_sAll=
				"έχω,έχεις,έχει,έχουμε|έχομε,έχετε,έχουν#"
				+"έχε#"
				+"είχα,είχες,είχε,είχαμε,είχατε,έχαν#"
				+"είχα,είχες,είχε,είχαμε,είχατε,έχαν#"
				+"είχα,είχες,είχε,είχαμε,είχατε,έχαν#"
				+"-#"
				+"έχοντας";
		}
//ζ
//θ
		else if (lg_conceptName.equals("θέλω")||lg_conceptName.equals("θελω"))
		{
			// θέλω,θέλησα,-,θελημένος
			// αύξηση ή.
			note="ανώμαλο:";
			termTxCpt_sAll="θέλω,ήθελα,θέλησα,-,θελημένος";
		}
		else if (lg_conceptName.equals("θέτω")||lg_conceptName.equals("θέτω"))
		{
			// θέτω,έθεσα,(τέθηκα),θεμένος(απο,κατα,αποσυν,...)
			note="ανώμαλο: ";
			termTxCpt_sAll="θέτω,έθεσα,(τέθηκα),θεμένος(απο,κατα,αποσυν,...)";
		}
//κ
		else if (lg_conceptName.equals("κάθομαι")||lg_conceptName.equals("κάθομαι"))
		{
			// κάθομαι|καθίζω,κάθισα,-,καθισμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="κάθομαι|καθίζω,κάθισα,-,καθισμένος";
		}
		else if (lg_conceptName.equals("καίω")||lg_conceptName.equals("καίγω"))
		{
			// καίω,έκαψα,κάηκα,καμένος
			note="ανώμαλο του rlElnTrmVbr921a: αντί κάφτηκα > κάηκα.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr921a");
		}
		else if (lg_conceptName.equals("κάνω")||lg_conceptName.equals("κάνω"))
		{
			// κάνω,(έκανα),έκαμα(έκανα),-,καμωμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="κάνω,(έκανα),έκαμα(έκανα),-,καμωμένος";
		}
		else if (lg_conceptName.equals("κείτομαι")||lg_conceptName.equals("κειτομαι"))
		{
			termTxCpt_sAll="κείτομαι";
		}
		else if (lg_conceptName.equals("κόβω")||lg_conceptName.equals("κόβoμαι")) //2001.03.26
		{
			note="ανώμαλο αντί κόφτηκα > κόπηκα.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr911a");
		}
		else if (lg_conceptName.equals("κοιμούμαι")||lg_conceptName.equals("κοιμάμαι"))
		{
			note="ανώμαλο του rlElnTrmVbr745: η μετοχή από κοιμημένος > κοιμισμένος.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr745c");
		}
//λ
		else if (lg_conceptName.equals("λαβαίνω")||lg_conceptName.equals("λαβαινω"))
		{
			note="Ελλειπτικό: Χρησιμοποιούμε μόνο την Ενεργητική-Φωνή του με σημασία 'παραλαβαίνω'."
						+"\nΓια να εκφράσουμε την παθητική-διάθεση χρησιμοποιούμαι το συνώνυμό του 'παραλαβαίνω'.";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr112b");
		}
		else if (lg_conceptName.equals("λέω")||lg_conceptName.equals("λέγω"))
		{
			// λέω|λέγω,είπα(255),ειπώθηκα,ειπωμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="λέω|λέγω,είπα(255),ειπώθηκα,ειπωμένος";
		}
//μ
		else if (lg_conceptName.equals("μαθαίνω")||lg_conceptName.equals("μαθαίνω"))
		{
			note="ανώμαλο: ";
			termTxCpt_sAll="μαθαίνω,έμαθα,μαθεύτηκα,μαθημένος(κ10)";
		}
		else if (lg_conceptName.equals("μάχομαι")||lg_conceptName.equals("μαχομαι"))
		{
			note="ανώμαλο του κανόνα: προσέχω/πρόσεξα/προσέχτηκα.";
			termTxCpt_sAll="μάχομαι,πολέμησα";
		}
		else if (lg_conceptName.equals("μολύνω")||lg_conceptName.equals("μολύνω"))
		{
			note="ανώμαλο του rlElnTrmVbr113a: αντί μολυμμένος > μολυσμένος.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr113a");
		}
//ν
		else if (lg_conceptName.equals("ντρέπομαι")||lg_conceptName.equals("ντρέπομαι"))
		{
			// ντρέπομαι,-,ντράπηκα(259),-
			note="ανώμαλο: ";
			termTxCpt_sAll="ντρέπομαι,-,ντράπηκα(259),-";
		}
		else if (lg_conceptName.equals("ξέρω")||lg_conceptName.equals("ξέρω"))
		{
			// ξέρω,ήξερα
			// αύξηση ή.
			note="ανώμαλο: ";
			termTxCpt_sAll="ξέρω,ήξερα";
		}
		else if (lg_conceptName.equals("ξύνω")||lg_conceptName.equals("ξύνω"))
		{
			note="ανώμαλο (61)";
			termTxCpt_sAll="ξύνω,έξυσα,ξύστηκα|ξύθηκα,";
		}
		else if (lg_conceptName.equals("παίρνω")||lg_conceptName.equals("παίρνω"))
		{
			// παίρνω,πήρα,πάρθηκα,παρμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="παίρνω,πήρα,πάρθηκα,παρμένος";
		}
//		else if (lg_conceptName.equals("παρασταίνω")||lg_conceptName.equals("παρασταίνω"))
//		{
//			note="ανώμαλο του rlElnTrmVbr741a: αντί παραστήθηκα >παραστάθηκα";
//			termTxCpt_sAll="παρασταίνω,παράστησα,παραστάθηκα,παραστημένος";
//		}
		else if (lg_conceptName.equals("πέφτω")||lg_conceptName.equals("πέφτω"))
		{
			// πέφτω,έπεσα,-,πεσμένος
			// ανώμαλο ως χειλικόληκτο.
			note="ανώμαλο: ";
			termTxCpt_sAll="πέφτω,έπεσα,-,πεσμένος";
		}
		else if (lg_conceptName.equals("πηγαίνω")||lg_conceptName.equals("πηγαίνω"))
		{
			note="ανώμαλο: ";
			termTxCpt_sAll="πηγαίνω|πάω,πήγα(255),-,πηγεμένος(κ10)";
		}
		else if (lg_conceptName.equals("πίνω")||lg_conceptName.equals("πίνω"))
		{
			note="ανώμαλο: ";
			termTxCpt_sAll="πίνω,έπινα,ήπια,πιώθηκα,πιωμένος";
		}
		else if (lg_conceptName.equals("πνέω")||lg_conceptName.equals("πνέω"))
		{
			// πνέω,έπνευσα,εμ|πνεύστηκα,εμ|πνευσμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="πνέω,έπνευσα,εμ|πνεύστηκα,εμ|πνευσμένος";
		}
		else if (lg_conceptName.equals("πνίγω")||lg_conceptName.equals("πνιγω")) //2001.03.26
		{
			note="ανώμαλο στον παθ-αόρ, αντί πνίχτηκα > πνίγηκα και πνιχτώ > πνιγώ.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr512a");
		}
		else if (lg_conceptName.equals("σπω")||lg_conceptName.equals("σπω"))
		{
			// σπω,(σπάζω,σπάνω,σπάω),έσπασα,(απο)σπάστηκα,σπασμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="σπω,(σπάζω,σπάνω,σπάω),έσπασα,(απο)σπάστηκα,σπασμένος";
		}
		else if (lg_conceptName.equals("στέκομαι")||lg_conceptName.equals("στέκομαι"))
		{
			// στέκομαι|στέκω,-,στάθηκα(στάσου,σταθείτε),-
			note="ανώμαλο: ";
			termTxCpt_sAll="στέκομαι|στέκω,-,στάθηκα(στάσου,σταθείτε),-";
		}
		else if (lg_conceptName.equals("στρέφω")||lg_conceptName.equals("στρέφω"))
		{
			// στρέφω,έστρεφα,στράφηκα(259),στραμμένος(κατα|στρεμμένος)
			note="ανώμαλο: ";
			termTxCpt_sAll="στρέφω,έστρεφα,στράφηκα(259),στραμμένος(κατα|στρεμμένος)";
		}
		else if (lg_conceptName.equals("τείνω")||lg_conceptName.equals("τείνω"))
		{
			// τείνω,έτεινα,(απο)τάθηκα,-
			note="ανώμαλο: ";
			termTxCpt_sAll="τείνω,έτεινα,(απο)τάθηκα,-";
		}
		else if (lg_conceptName.equals("τρέπω")||lg_conceptName.equals("τρέπω"))
		{
			// τρέπω,έτρεψα,τράπηκα(259),-τραμμένος
			note="ανώμαλο, ";
			termTxCpt_sAll="καν. έτρεψα/τρέφτηκα (259): τρέπω,έτρεψα,τράπηκα,-τραμμένος";
		}
		else if (lg_conceptName.equals("τρέφω")||lg_conceptName.equals("τρέφω"))
		{
			// τρέφω|θρέψω,έθρεψα,τράφηκα|θράφηκα(259),θρεμμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="τρέφω|θρέψω,έθρεψα,τράφηκα|θράφηκα(259),θρεμμένος";
		}
		else if (lg_conceptName.equals("τρέχω")||lg_conceptName.equals("τρέχω"))
		{
			// τρέχω,(τρέχα,τρεχάτε),έτρεξα,-,-
			note="ανώμαλο: ";
			termTxCpt_sAll="τρέχω,(τρέχα,τρεχάτε),έτρεξα,-,-";
		}
		else if (lg_conceptName.equals("τρώω")||lg_conceptName.equals("τρώγω")||lg_conceptName.equals("τρώγομαι"))
		{
			// τρώω|τρώγω,έφαγα(255),φαγώθηκα,φαγωμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="τρώω|τρώγω,έφαγα(255),φαγώθηκα,φαγωμένος";
		}
		else if (lg_conceptName.equals("υπάρχω")||lg_conceptName.equals("υπαρχω"))
		{
			note="ανώμαλο του (rlElnTrmVbr512a:προσέχω) υπάρχω,υπήρξα.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr512a");
		}
		else if (lg_conceptName.equals("υπόσχομαι")||lg_conceptName.equals("υποσχομαι"))
		{
			note="ανώμαλο του κανόνα: προσέχω/πρόσεξα/προσέχτηκα.";
			termTxCpt_sAll="υπόσχομαι,υποσχέθηκα,-,υποσχεμένος";
		}
		else if (lg_conceptName.equals("φαίνομαι")||lg_conceptName.equals("φαίνομαι"))
		{
			// φαίνομαι,-,φάνηκα(259),(κακοφανισμένος)
			note="ανώμαλο: ";
			termTxCpt_sAll="φαίνομαι,-,φάνηκα(259),(κακοφανισμένος)";
		}
		else if (lg_conceptName.equals("φεύγω")||lg_conceptName.equals("φεύγω"))
		{
			note="ανώμαλο του rlElnTrmVbr512a (ανοί-γω): φεύγω,(φεύγε,φεύγα),έφυγα.\\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr512b");
		}
		else if (lg_conceptName.equals("φθείρω")||lg_conceptName.equals("φθείρω"))
		{
			// φθείρω,έφθειρα,φθάρηκα(267),φθαρμένος
			note="ανώμαλο: ";
			termTxCpt_sAll="φθείρω,έφθειρα,φθάρηκα(267),φθαρμένος";
		}
		else if (lg_conceptName.equals("φοβούμαι")||lg_conceptName.equals("φοβάμαι"))
		{
			note="ανώμαλο του rlElnTrmVbr745c: η μετοχή του φοβ-ημένος > φοβισμένος.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr745c");
		}
		else if (lg_conceptName.equals("φταίω")||lg_conceptName.equals("φταιω"))
		{
			note="Μόνο ενεργητικό (εκφράζει κατάσταση). \n\tΕπίσης οι τύποι με (γ) έχουν εκλύψει ήδη απο το αρχαίο 'πταίω'.\n\t";
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly("rlElnTrmVbr513b");
		}
		else if (lg_conceptName.equals("χαίρομαι")||lg_conceptName.equals("χαίρω")||lg_conceptName.equals("χαίρομαι")||lg_conceptName.equals("χαίρω"))
		{
			// χαίρομαι,(χαρούμενος),-,χάρηκα(259),-
			note="ανώμαλο: ";
			termTxCpt_sAll="χαίρομαι,(χαρούμενος),-,χάρηκα(259),-";
		}

		//άν είναι πολυλεκτικό με επίρημα, βάλε στην αρχή κάθε τύπου το επίρρημα.
//		if (termTxCpt_sAll.length()!=0 && prefix.length()>0)
//		{
//			StringTokenizer parser = new StringTokenizer(termTxCpt_sAll, ";");
//			termTxCpt_sAll="";
//			while (parser.hasMoreTokens())
//			{
//				String st=(String)parser.nextElement();
//				StringTokenizer parser2 = new StringTokenizer(st, ",");
//				while (parser2.hasMoreTokens())
//				{
//					String type=prefix+parser2.nextElement();
//					if (type.startsWith("παραέ") )
//						type="παρά" +getLastLettersIfPrefix(type,5); //2001.04.02
//					termTxCpt_sAll=termTxCpt_sAll +type +",";
//				}
//				termTxCpt_sAll=termTxCpt_sAll+";";
//			}
//		}

		return termTxCpt_sAll;
	}//getTermsOfTxConceptIfIrregular.


	/**
	 * I call this from JComboBox with the rules.
	 * First check if the termTxCpt_sName is irregular.
	 * @modified 2004.11.04
	 * @since 2001.03.25 (v00.02.00)
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
	}


	/**
	 * I'll use this method also to FORCE a word to use a term's-rule
	 * (eg if I want to see where are the irregularities). 2001.03.27
	 * @modified 2004.11.04
	 * @since 2001.03.24 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly(String rule)
	{
		termTxCpt_sRule=rule;
		String s="",s2="",s3="",s4="",s5="",s6="";
		String termTxCpt_sAll="", nm2="";
		boolean afksisi;
		termTxCpt_sName=termTxCpt_sName.toLowerCase();

		//ξεχωρίζω τα επιρρήματα που διατηρούν την αύξηση:
		//Αν και καμιά φορά χρησιμοποιούμε και τους δύο τύπους κακοέπλεκα/κακόπλεκα
		//Θεωρώ ότι βασικά η ελληνική-γλώσσα τα θεωρεί μια λέξη.
		//Το πρόγραμμα έχει τη δυνατότητα να παρουσιάζει και εσωτερική αύξηση. 2001.04.09
//		if (termTxCpt_sName.startsWith("κακο")) prefix="κακο";				//κακοπλέκω/κακοέπλεκα
//		else if (termTxCpt_sName.startsWith("καλο")) prefix="καλο";		//καλοπλέκω/καλοέπλεκα
//		//Τα σύνθετα με 'παρα', χάνουν την αύξηση. παραέφαγα > παράφαγα.
//		else if (termTxCpt_sName.startsWith("παρα")) prefix="παρα";
//		else if (termTxCpt_sName.startsWith("πολυ")) prefix="πολυ";		//πολυπλέκω/πολυέπλεκα
//		termTxCpt_sName=getLastLettersIfPrefix(termTxCpt_sName,prefix.length());

//1)-α-θηκα

		//-ω/-α
		//τρέμ-ω, έτρεμ-α
		if (termTxCpt_sRule.equals("rlElnTrmVbr111b") )
		{
			//Ελλειπής.	 Η αρχική κατασκευή ρήματος με σχηματισμό ενεστώτα, αορίστου.
			s=getFirstLettersIfSuffix(termTxCpt_sName,1);	//τρέμ-
			s2=greekTonosRemove(s);							//τρεμ-

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				note=note+"Κλίθηκε με τον ΕΛΛΙΠΗ rlElnTrmVbr111b: -ω/-α, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
				+s+"ε#"
				+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε,έ" +s2+"αν|"+s+"ανε#"
				+"-,-,-,-,-#"
				+"-,-,-,-,-#"
				+"-#"
				+s+"οντας";
			}
			else {
				//σαλπάρ-ω,σάλπαρ-α
				s3=greekTonosDecrease(s);					//σάλπαρ-
				note=note+"Κλίθηκε με τον ΕΛΛΙΠΗ rlElnTrmVbr111b: -ω/-α, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
				+s3+"ε#"
				+"-,-,-,-,-#"
				+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
				+"-,-,-,-,-#"
				+"-#"
				+s+"οντας";
			}
		}

		//-αίνω-αίνομαι/-α-θηκα/-ημένος
		//καταλαβ-αίνω-αίνομαι/κατάλαβ-α-ήθηκα/-ημένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr112a")||termTxCpt_sRule.equals("rlElnTrmVbr112b")||termTxCpt_sRule.equals("rlElnTrmVbr112c") )
		{
			if (termTxCpt_sName.endsWith("νω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);			//καταλαβ-
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);			//καταλαβ-

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//λαβ-αίνω/έλαβ-α
				s2=greekTonosSetOnFirst(s);				//λάβ-
				if (termTxCpt_sRule.equals("rlElnTrmVbr112a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr112a: -αίνω-αίνομαι/-α-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr112b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr112b: -αίνω-αίνομαι/-α-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s2+"αινε#"
				+s2+"αινα," +s2+"αινες," +s2+"αινε," +s+"αίναμε," +s+"αίνατε," +s2+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"α," +"έ"+s+"ες," +"έ"+s+"ε," +s2+"αμε," +s2+"ατε,έ" +s+"αν|"+"ε"+s2+"ανε#"
				+s2+"ω," +s2+"εις," +s2+"ει," +s2+"ουμε|" +s2+"ομε," +s2+"ετε," +s2+"ουν|"+s2+"ουνε#"
				+s2+"ε#"
				+s+"αίνοντας#";
			}
			else {
				//καταλαβ-αίνω/κατάλαβ-α
				s2=greekTonosSetOnFirst(s);				//καταλάβ-
				s3=greekTonosDecrease(s2);				//κατάλαβ-
				if (termTxCpt_sRule.equals("rlElnTrmVbr112a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr112a: -αίνω-αίνομαι/-α-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr112b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr112b: -αίνω-αίνομαι/-α-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s2+"αινε#"
				+s2+"αινα," +s2+"αινες," +s2+"αινε," +s+"αίναμε," +s+"αίνατε," +s2+"αιναν|"+s+"αίνανε#"
				+s3+"α," +s3+"ες," +s3+"ε," +s2+"αμε," +s2+"ατε," +s3+"αν|"+s+"ανε#"
				+s2+"ω," +s2+"εις," +s2+"ει," +s2+"ουμε|" +s2+"ομε," +s2+"ετε," +s2+"ουν|"+s2+"ουνε#"
				+s3+"ε#"
				+s+"αίνοντας#";
			}
			nm2=
			 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
			+s+"αίνου#"
			+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"νόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
			+s+"ήθηκα," +s+"ήθηκες," +s+"ήθηκε," +s+"ηθήκαμε," +s+"ηθήκατε," +s+"ήθηκαν|"+s+"ηθήκανε#"
			+s+"ηθώ," +s+"ηθείς," +s+"ηθεί," +s+"ηθούμε," +s+"ηθείτε," +s+"ηθούν#"
			+s+"ήσου#"
			+s+"ημένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr112c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr112c: -αίνω-αίνομαι/-α-ήθηκα/-ημένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr112a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ω-ομαι/-α-θηκα/μένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr113a")||termTxCpt_sRule.equals("rlElnTrmVbr113b")||termTxCpt_sRule.equals("rlElnTrmVbr113c") )
		{
			//οξύν-ω-ομαι/-α-θηκα/μένος
			//Λίγα ρήματα σε -υνω ακολουθούν αυτόν τον κανόνα.
			//Φαίνεται στον παθητικό αόριστο 'οξύν-θηκα' (κρατούν το ν).
			//Επίσης στην παθητική-μετοχή ο ν γίνεται μ οξυνμένος > οξυμμένος.
			if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);			//οξύν-
			else //-ομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);			//οξύν-

			afksisi=!isLetterVowelGreek(s.charAt(0))
							&& findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//μή υπαρκτό: λύν-ω-ομαι/έλυν-α-λύνθηκα/λυμ-μένος
				s2=greekTonosRemove(s); //λυν-
				if (termTxCpt_sRule.equals("rlElnTrmVbr113a"))
					note=note+"Κλίθηκε με τον προβληματικό (έχει παρατατικό και αόριστο ίδιους) rlElnTrmVbr113a:"
										+"\n\t -ω-ομαι/α-θηκα/μένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον προβληματικό (έχει παρατατικό και αόριστο ίδιους) rlElnTrmVbr113b:"
										+"\n\t -ω-ομαι/α-θηκα/μένος, αύξ=ναί, φωνή=ε&π";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν#"
				+s2+"ε#"
				+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε,έ" +s2+"αν#"
				+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε,έ" +s2+"αν#"
				+s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν#"
				+s2+"ε#"
				+s+"οντας#";
			}
			else {
				//οξύν-ω/όξυν-α
				s2=greekTonosDecrease(s); //όξυν-
				s3=greekTonosRemove(s2);	//οξυν-
				if (termTxCpt_sRule.equals("rlElnTrmVbr113a"))
					note=note+"Κλίθηκε με τον προβληματικό (έχει παρατατικό και αόριστο ίδιους) rlElnTrmVbr113a:"
										+"\n\t -ω-ομαι/α-θηκα/μένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον προβληματικό (έχει παρατατικό και αόριστο ίδιους) rlElnTrmVbr113b:"
										+"\n\t -ω-ομαι/α-θηκα/μένος, αύξ=όχι, φωνή=ε&π";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν#"
				+s2+"ε#"
				+s2+"α," +s2+"ες," +s2+"ε," +s+"αμε," +s+"ατε," +s2+"αν#"
				+s2+"α," +s2+"ες," +s2+"ε," +s+"αμε," +s+"ατε," +s2+"αν#"
				+s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν#"
				+s2+"ε#"
				+s+"οντας#";
			}

			//διευθύν-ομαι/διευθύν-θηκα
			s3=greekTonosRemove(s);		//διευθυν-
			nm2=
			 s+"ομαι," +s+"εσαι," +s+"εται," +s3+"όμαστε," +s+"εστε," +s+"ονται#"
			+s+"ου#"
			+s3+"όμουνα," +s3+"όσουν," +s3+"όταν," +s3+"όμαστε," +s3+"όσαστε," +s+"ονταν#"
			+s+"θηκα," +s+"θηκες," +s+"θηκε," +s3+"θήκαμε," +s3+"θήκατε," +s+"θηκαν#"
			+s3+"θώ," +s3+"θείς," +s3+"θεί," +s3+"θούμε," +s3+"θείτε," +s3+"θούν#"
			+s+"σου#"
			+getFirstLettersIfSuffix(s3,1)+"μμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr113c") )
			{
				note=note+"Κλίθηκε με τον προβληματικό (έχει παρατατικό και αόριστο ίδιους) rlElnTrmVbr113c:"
									+"\n\t -ω-ομαι/α-θηκα/μένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr113a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

//2)-κα-θηκα

		//-ηκα/-αίνω/-ασμένος
		//ανεβαίνω/ανέβηκα
		else if (termTxCpt_sRule.equals("rlElnTrmVbr241b") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//μπ-αίνω/μπ-ήκα
				note=note+"Κλίθηκε με τον rlElnTrmVbr241b: -αίνω/-ήκα/-ασμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|" +s+"αίνανε#"
				+s+"ήκα," +s+"ήκες," +s+"ήκε," +s+"ήκαμε," +s+"ήκατε," +s+"ήκαν|"+s+"ήκανε#"
				+s+"ω," +s+"εις," +s+"ει," +s+"ούμε," +s+"είτε," +s+"ουν|"+s+"ούνε#"
				+s+"ες|" +"έ"+s+"α#"
				+s+"αίνοντας#";
			}
			else {
				//ανεβ-αίνω/ανέβ-ηκα/ανεβ-ασμένος
				s3=greekTonosSetOnFirst(s);
				note=note+"Κλίθηκε με τον rlElnTrmVbr241b: -αίνω/-ήκα/-ασμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"ηκα," +s3+"ηκες," +s3+"ηκε," +s+"ήκαμε," +s+"ήκατε," +s3+"ηκαν|"+s3+"ήκανε#"
				+s+"ώ," +s+"είς," +s+"εί," +s+"ούμε," +s+"είτε," +s+"ούν|"+s+"ούνε#"
				+s3+"α#"
				+s+"αίνοντας#";
			}
		}

//3)-λα-λθηκα

		//-έλνω-έλνομαι/-αλα-άλθηκα/-αλμένος
		//ψ-έλνω-έλνομαι/έ-ψ-αλα-άλθηκα/ψ-αλμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr321a")||termTxCpt_sRule.equals("rlElnTrmVbr321b")||termTxCpt_sRule.equals("rlElnTrmVbr321c") )
		{
			if (termTxCpt_sName.endsWith("έλνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//ψ-έλνω/έ-ψ-αλα
				if (termTxCpt_sRule.equals("rlElnTrmVbr321a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr321a: -έλνω-έλνομαι/-αλα-άλθηκα/-αλμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr321b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr321b: -έλνω-έλνομαι/-αλα-άλθηκα/-αλμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"έλνω," +s+"έλνεις," +s+"έλνει," +s+"έλνουμε|" +s+"έλνομε," +s+"έλνετε," +s+"έλνουν|"+s+"έλνουνε#"
				+s+"έλνε#"
				+"έ"+s+"ελνα," +"έ"+s+"ελνες," +"έ"+s+"ελνε," +s+"έλναμε," +s+"έλνατε," +"έ"+s+"ελναν|"+s+"έλνανε#"
				+"έ"+s+"αλα," +"έ"+s+"αλες," +"έ"+s+"αλε," +s+"άλαμε," +s+"άλατε,έ" +s+"αλαν|"+"ε"+s+"άλανε|"+s+"άλανε#"
				+s+"άλω," +s+"άλεις," +s+"άλει," +s+"άλουμε|" +s+"άλομε," +s+"άλετε," +s+"άλουν|"+s+"άλουνε#"
				+s+"άλε#"
				+s+"έλνοντας,"+s+"άλοντας," +s+"αλμένος#";
			}
			else {
				//ψιλοψ-έλνω/ψιλόψ-αλα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr321a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr321a: -έλνω-έλνομαι/-αλα-άλθηκα/-αλμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr321b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr321b: -έλνω-έλνομαι/-αλα-άλθηκα/-αλμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"έλνω," +s+"έλνεις," +s+"έλνει," +s+"έλνουμε|" +s+"έλνομε," +s+"έλνετε," +s+"έλνουν|"+s+"έλνουνε#"
				+s2+"ελνε#"
				+s2+"ελνα," +s2+"ελνες," +s2+"ελνε," +s+"έλναμε," +s+"έλνατε," +s2+"ελναν|"+s+"έλνανε#"
				+s2+"αλα," +s2+"αλες," +s2+"αλε," +s+"άλαμε," +s+"άλατε," +s2+"αλαν|"+s+"άλανε#"
				+s+"άλω," +s+"άλεις," +s+"άλει," +s+"άλουμε|" +s+"άλομε," +s+"άλετε," +s+"άλουν|"+s+"άλουνε#"
				+s2+"αλε#"
				+s+"έλνοντας|"+s+"άλοντας#";
			}

			nm2=
			 s+"έλνομαι," +s+"έλνεσαι," +s+"έλνεται," +s+"ελνόμαστε," +s+"έλνεστε," +s+"έλνονται#"
			+s+"έλνου#"
			+s+"ελνόμουνα," +s+"ελνόσουν," +s+"ελνόταν|"+s+"ελνότανε," +s+"ελνόμαστε," +s+"νόσαστε," +s+"έλνονταν|"+s+"ελνόντανε#"
			+s+"άλθηκα," +s+"άλθηκες," +s+"άλθηκε," +s+"αλθήκαμε," +s+"αλθήκατε," +s+"άλθηκαν|"+s+"αλθήκανε#"
			+s+"αλθώ," +s+"αλθείς," +s+"αλθεί," +s+"αλθούμε," +s+"αλθείτε," +s+"αλθούν#"
			+s+"άλσου#"
			+s+"αλμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr321c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr321c: -έλνω-έλνομαι/-αλα-άλθηκα/-αλμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr321a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-έλνω-έλνομαι/-ειλα-άλθηκα/-αλμένος
		//στ-έλνω-έλνομαι/έ-στ-ειλα-άλθηκα/στ-αλμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr341a")||termTxCpt_sRule.equals("rlElnTrmVbr341b")||termTxCpt_sRule.equals("rlElnTrmVbr341c") )
		{
			if (termTxCpt_sName.endsWith("έλνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//στέλνω/έστειλα
				if (termTxCpt_sRule.equals("rlElnTrmVbr341a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr341a: -έλνω-έλνομαι/-ειλα-άλθηκα/-αλμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr341b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr341b: -έλνω-έλνομαι/-ειλα-άλθηκα/-αλμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"έλνω," +s+"έλνεις," +s+"έλνει," +s+"έλνουμε|" +s+"έλνομε," +s+"έλνετε," +s+"έλνουν|"+s+"έλνουνε#"
				+s+"έλνε#"
				+"έ"+s+"ελνα," +"έ"+s+"ελνες," +"έ"+s+"ελνε," +s+"έλναμε," +s+"έλνατε," +"έ"+s+"ελναν|"+s+"έλνανε#"
				+"έ"+s+"ειλα," +"έ"+s+"ειλες," +"έ"+s+"ειλε," +s+"είλαμε," +s+"είλατε,έ" +s+"ειλαν|"+"ε"+s+"είλανε|"+s+"είλανε#"
				+s+"είλω," +s+"είλεις," +s+"είλει," +s+"είλουμε|" +s+"είλομε," +s+"είλετε," +s+"είλουν|"+s+"είλουνε#"
				+s+"είλε#"
				+s+"έλνοντας|"+s+"είλοντας#";
			}
			else {
				//παραστ-έλνω/παράστ-ειλα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr341a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr341a: -έλνω-έλνομαι/-ειλα-άλθηκα/-αλμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr341b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr341b: -έλνω-έλνομαι/-ειλα-άλθηκα/-αλμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"έλνω," +s+"έλνεις," +s+"έλνει," +s+"έλνουμε|" +s+"έλνομε," +s+"έλνετε," +s+"έλνουν|"+s+"έλνουνε#"
				+s2+"ελνε#"
				+s2+"ελνα," +s2+"ελνες," +s2+"ελνε," +s+"έλναμε," +s+"έλνατε," +s2+"ελναν|"+s+"έλνανε#"
				+s2+"ειλα," +s2+"ειλες," +s2+"ειλε," +s+"είλαμε," +s+"είλατε," +s2+"ειλαν|"+s+"είλανε#"
				+s+"είλω," +s+"είλεις," +s+"είλει," +s+"είλουμε|" +s+"είλομε," +s+"είλετε," +s+"είλουν|"+s+"είλουνε#"
				+s2+"ειλε#"
				+s+"έλνοντας|"+s+"είλοντας#";
			}
			nm2=
			 s+"έλνομαι," +s+"έλνεσαι," +s+"έλνεται," +s+"ελνόμαστε," +s+"έλνεστε," +s+"έλνονται#"
			+s+"έλνου#"
			+s+"ελνόμουνα," +s+"ελνόσουν," +s+"ελνόταν|"+s+"ελνότανε," +s+"ελνόμαστε," +s+"νόσαστε," +s+"έλνονταν|"+s+"ελνόντανε#"
			+s+"άλθηκα," +s+"άλθηκες," +s+"άλθηκε," +s+"αλθήκαμε," +s+"αλθήκατε," +s+"άλθηκαν|"+s+"αλθήκανε#"
			+s+"αλθώ," +s+"αλθείς," +s+"αλθεί," +s+"αλθούμε," +s+"αλθείτε," +s+"αλθούν#"
			+s+"άλσου#"
			+s+"αλμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr341c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr341c: -έλνω-έλνομαι/-ειλα-άλθηκα/-αλμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr341a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-έλνω-έλνομαι/-ειλα-έλθηκα/-ελμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr342a")||termTxCpt_sRule.equals("rlElnTrmVbr342b")||termTxCpt_sRule.equals("rlElnTrmVbr342c") )
		{
			//παραγγ-έλνω-έλνομαι/έ-γγ-ειλα-έλθηκα/στ-ελμένος
			if (termTxCpt_sName.endsWith("έλνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//γέλνω/έγειλα (μή υπαρκτό)
				if (termTxCpt_sRule.equals("rlElnTrmVbr342a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr342a: -έλνω-έλνομαι/-ειλα-έλθηκα/-ελμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr342b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr342b: -έλνω-έλνομαι/-ειλα-έλθηκα/-ελμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"έλνω," +s+"έλνεις," +s+"έλνει," +s+"έλνουμε|" +s+"έλνομε," +s+"έλνετε," +s+"έλνουν|"+s+"έλνουνε#"
				+s+"έλνε#"
				+"έ"+s+"ελνα," +"έ"+s+"ελνες," +"έ"+s+"ελνε," +s+"έλναμε," +s+"έλνατε," +"έ"+s+"ελναν|"+s+"έλνανε#"
				+"έ"+s+"ειλα," +"έ"+s+"ειλες," +"έ"+s+"ειλε," +s+"είλαμε," +s+"είλατε,έ" +s+"ειλαν|"+"ε"+s+"είλανε|"+s+"είλανε#"
				+s+"είλω," +s+"είλεις," +s+"είλει," +s+"είλουμε|" +s+"είλομε," +s+"είλετε," +s+"είλουν|"+s+"είλουνε#"
				+s+"είλε#"
				+s+"έλνοντας|"+s+"είλοντας#";
			}
			else {
				//παραγγ-έλνω/παράγγ-ειλα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr342a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr342a: -έλνω-έλνομαι/-ειλα-έλθηκα/-ελμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr342b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr342b: -έλνω-έλνομαι/-ειλα-έλθηκα/-ελμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"έλνω," +s+"έλνεις," +s+"έλνει," +s+"έλνουμε|" +s+"έλνομε," +s+"έλνετε," +s+"έλνουν|"+s+"έλνουνε#"
				+s2+"ελνε#"
				+s2+"ελνα," +s2+"ελνες," +s2+"ελνε," +s+"έλναμε," +s+"έλνατε," +s2+"ελναν|"+s+"έλνανε#"
				+s2+"ειλα," +s2+"ειλες," +s2+"ειλε," +s+"είλαμε," +s+"είλατε," +s2+"ειλαν|"+s+"είλανε#"
				+s+"είλω," +s+"είλεις," +s+"είλει," +s+"είλουμε|" +s+"είλομε," +s+"είλετε," +s+"είλουν|"+s+"είλουνε#"
				+s2+"ειλε#"
				+s+"έλνοντας|"+s+"είλοντας#";
			}
			nm2=
			 s+"έλνομαι," +s+"έλνεσαι," +s+"έλνεται," +s+"ελνόμαστε," +s+"έλνεστε," +s+"έλνονται#"
			+s+"έλνου#"
			+s+"ελνόμουνα," +s+"ελνόσουν," +s+"ελνόταν|"+s+"ελνότανε," +s+"ελνόμαστε," +s+"νόσαστε," +s+"έλνονταν|"+s+"ελνόντανε#"
			+s+"έλθηκα," +s+"έλθηκες," +s+"έλθηκε," +s+"ελθήκαμε," +s+"ελθήκατε," +s+"έλθηκαν|"+s+"ελθήκανε#"
			+s+"ελθώ," +s+"ελθείς," +s+"ελθεί," +s+"ελθούμε," +s+"ελθείτε," +s+"ελθούν#"
			+s+"έλσου#"
			+s+"ελμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr342c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr342c: -έλνω-έλνομαι/-ειλα-έλθηκα/-ελμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr342a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-έλλω-έλλομαι/-ειλα-έλθηκα/-ελμένος
		//αγγέλλω/άγγειλα
		else if (termTxCpt_sRule.equals("rlElnTrmVbr343a")||termTxCpt_sRule.equals("rlElnTrmVbr343b")||termTxCpt_sRule.equals("rlElnTrmVbr343c") )
		{
			if (termTxCpt_sName.endsWith("έλλω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else if (termTxCpt_sName.endsWith("έλλομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//τέλλω/έτειλα (μή υπαρκτό)
				if (termTxCpt_sRule.equals("rlElnTrmVbr343a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr343a: -έλλω-έλλομαι/-ειλα-έλθηκα/-ελμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr343b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr343b: -έλλω-έλλομαι/-ειλα-έλθηκα/-ελμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"έλλω," +s+"έλλεις," +s+"έλλει," +s+"έλλουμε|" +s+"έλλομε," +s+"έλλετε," +s+"έλλουν|"+s+"έλλουνε#"
				+s+"έλλε#"
				+"έ"+s+"ελλα," +"έ"+s+"ελλες," +"έ"+s+"ελλε," +s+"έλλαμε," +s+"έλλατε," +"έ"+s+"ελλαν|"+s+"έλλανε#"
				+"έ"+s+"ειλα," +"έ"+s+"ειλες," +"έ"+s+"ειλε," +s+"είλαμε," +s+"είλατε,έ" +s+"ειλαν|"+"ε"+s+"είλανε|"+s+"είλανε#"
				+s+"είλω," +s+"είλεις," +s+"είλει," +s+"είλουμε|" +s+"είλομε," +s+"είλετε," +s+"είλουν|"+s+"είλουνε#"
				+s+"είλε#"
				+s+"έλλοντας|"+s+"είλοντας#";
			}
			else {
				//παραγγ-έλλω/παράγγ-ειλα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr343a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr343a: -έλλω-έλλομαι/-ειλα-έλθηκα/-ελμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr343b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr343b: -έλλω-έλλομαι/-ειλα-έλθηκα/-ελμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"έλλω," +s+"έλλεις," +s+"έλλει," +s+"έλλουμε|" +s+"έλλομε," +s+"έλλετε," +s+"έλλουν|"+s+"έλλουνε#"
				+s2+"ελλε#"
				+s2+"ελλα," +s2+"ελλες," +s2+"ελλε," +s+"έλλαμε," +s+"έλλατε," +s2+"ελλαν|"+s+"έλλανε#"
				+s2+"ειλα," +s2+"ειλες," +s2+"ειλε," +s+"είλαμε," +s+"είλατε," +s2+"ειλαν|"+s+"είλανε#"
				+s+"είλω," +s+"είλεις," +s+"είλει," +s+"είλουμε|" +s+"είλομε," +s+"είλετε," +s+"είλουν|"+s+"είλουνε#"
				+s2+"ειλε#"
				+s+"έλλοντας|"+s+"είλοντας#";
			}
			nm2=
			 s+"έλλομαι," +s+"έλλεσαι," +s+"έλλεται," +s+"ελλόμαστε," +s+"έλλεστε," +s+"έλλονται#"
			+s+"έλλου#"
			+s+"ελλόμουνα," +s+"ελλόσουν," +s+"ελλόταν|"+s+"ελλότανε," +s+"ελλόμαστε," +s+"νόσαστε," +s+"έλλονταν|"+s+"ελλόντανε#"
			+s+"έλθηκα," +s+"έλθηκες," +s+"έλθηκε," +s+"ελθήκαμε," +s+"ελθήκατε," +s+"έλθηκαν|"+s+"ελθήκανε#"
			+s+"ελθώ," +s+"ελθείς," +s+"ελθεί," +s+"ελθούμε," +s+"ελθείτε," +s+"ελθούν#"
			+s+"έλσου#"
			+s+"ελμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr343c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr343c: -έλλω-έλλομαι/-ειλα-έλθηκα/-ελμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr343a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

//4)-να-θηκα

		//-να-θηκα/-νω-νομαι/-μένος
		//κρίνω/κρίνομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr411a")||termTxCpt_sRule.equals("rlElnTrmVbr411b")||termTxCpt_sRule.equals("rlElnTrmVbr411c") )
		{
			if (termTxCpt_sName.endsWith("νω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("νομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//κρί-νω-νομαι
				if (termTxCpt_sRule.equals("rlElnTrmVbr411a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr411a: -νω-νομαι/-να-θηκα/-μένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr411b: -νω-νομαι/-να-θηκα/-μένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
					 s+"νω," +s+"νεις," +s+"νει," +s+"νουμε|" +s+"νομε," +s+"νετε," +s+"νουν|"+s+"νουνε#"
					+s+"νε#"
					+"έ"+s2+"να," +"έ"+s2+"νες," +"έ"+s2+"νε," +s+"ναμε," +s+"νατε,έ" +s2+"ναν|"+s+"νανε#"
					+"έ"+s2+"να," +"έ"+s2+"νες," +"έ"+s2+"νε," +s+"ναμε," +s+"νατε,έ" +s2+"ναν|"+s+"νανε#"
					+s+"νω," +s+"νεις," +s+"νει," +s+"νουμε|" +s+"νομε," +s+"νετε," +s+"νουν|"+s+"νουνε#"
					+s+"νε#"
					+s+"νοντας#";
			}
			else {
				//δαγκά-νω/δάγκα-να
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr411a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr411a: -νω-νομαι/-να-θηκα/-μένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr411b: -νω-νομαι/-να-θηκα/-μένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
					 s+"νω," +s+"νεις," +s+"νει," +s+"νουμε|" +s+"νομε," +s+"νετε," +s+"νουν|"+s+"νουνε#"
					+s3+"νε#"
					+s3+"να," +s3+"νες," +s3+"νε," +s+"ναμε," +s+"νατε," +s3+"ναν|"+s+"νανε#"
					+s3+"να," +s3+"νες," +s3+"νε," +s+"ναμε," +s+"νατε," +s3+"ναν|"+s+"νανε#"
					+s+"νω," +s+"νεις," +s+"νει," +s+"νουμε|" +s+"νομε," +s+"νετε," +s+"νουν|"+s+"νουνε#"
					+s3+"νε#"
					+s+"νοντας#";
			}
			nm2=
			 s+"νομαι," +s+"νεσαι," +s+"νεται," +s2+"νόμαστε," +s+"νεστε," +s+"νονται#"
			+s+"νου," +s+"νεστε#"
			+s2+"νόμουνα," +s2+"νόσουν," +s2+"νόταν|"+s2+"νότανε," +s2+"νόμαστε," +s2+"νόσαστε," +s+"νονταν|"+s+"νόντανε#"
			+s+"θηκα," +s+"θηκες," +s+"θηκε," +s2+"θήκαμε," +s2+"θήκατε," +s+"θηκαν|"+s+"θήκανε#"
			+s2+"θώ," +s2+"θείς," +s2+"θεί," +s2+"θούμε," +s2+"θείτε," +s2+"θούν|"+s2+"θούνε#"
			+s+"νου," +s2+"θείτε#"
			+s2+"μένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr411c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr411c: -νω-νομαι/-να-θηκα/-μένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr411a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ανα-άθηκα/-αίνω-αίνομαι
		//βουβαίνω/βούβανα
		else if (termTxCpt_sRule.equals("rlElnTrmVbr421a")||termTxCpt_sRule.equals("rlElnTrmVbr421b")||termTxCpt_sRule.equals("rlElnTrmVbr421c") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-ομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-αίνω/έχ-ανα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr421a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr421a: -αίνω-αίνομαι/-ανα-άθηκα/-αμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr421b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr421b: -αίνω-αίνομαι/-ανα-άθηκα/-αμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"ανα," +"έ"+s+"ανες," +"έ"+s+"ανε," +s+"άναμε," +s+"άνατε,έ" +s+"αναν|"+s+"άνανε#"
				+s+"άνω," +s+"άνεις," +s+"άνει," +s+"άνουμε|" +s+"άνομε," +s+"άνετε," +s+"άνουν|"+s+"άνουνε#"
				+s+"άνε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			else {
				//βουβαίνω/βούβανα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr421a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr421a: -αίνω-αίνομαι/-ανα-άθηκα/-αμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr421b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr421b: -αίνω-αίνομαι/-ανα-άθηκα/-αμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"ανα," +s3+"ανες," +s3+"ανε," +s+"άναμε," +s+"άνατε," +s3+"αναν#"
				+s+"άνω," +s+"άνεις," +s+"άνει," +s+"άνουμε|" +s+"άνομε," +s+"άνετε," +s+"άνουν|"+s+"άνουνε#"
				+s3+"ανε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			nm2=
				 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
				+s+"αίνου#"
				+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"αινόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
				+s+"άθηκα," +s+"άθηκες," +s+"άθηκε," +s+"αθήκαμε," +s+"αθήκατε," +s+"άθηκαν|"+s+"αθήκανε#"
				+s+"αθώ," +s+"αθείς," +s+"αθεί," +s+"αθούμε," +s+"αθείτε," +s+"αθούν|"+s+"αθούνε#"
				+s+"άνου#"
				+s+"αμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr421c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr421c: -αίνω-αίνομαι/-να-θηκα/-αμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr421a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ανα-άνθηκα/-αίνω-αίνομαι/-σμένος
		//λευκαίνω/λευκαίνομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr422a")||termTxCpt_sRule.equals("rlElnTrmVbr422b")||termTxCpt_sRule.equals("rlElnTrmVbr422c") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else if (termTxCpt_sName.endsWith("αίνομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-αίνω/έχ-ανα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr422a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr422a: -αίνω-αίνομαι/-ανα-άνθηκα/-ασμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr422b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr422b: -αίνω-αίνομαι/-ανα-άνθηκα/-ασμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"ανα," +"έ"+s+"ανες," +"έ"+s+"ανε," +s+"άναμε," +s+"άνατε,έ" +s+"αναν|"+s+"άνανε#"
				+s+"άνω," +s+"άνεις," +s+"άνει," +s+"άνουμε|" +s+"άνομε," +s+"άνετε," +s+"άνουν|"+s+"άνουνε#"
				+s+"άνε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			else {
				//λευκαίνω/λευκαίνομαι
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr422a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr422a: -αίνω-αίνομαι/-ανα-άνθηκα/-ασμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr422b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr422b: -αίνω-αίνομαι/-ανα-άνθηκα/-ασμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"ανα," +s3+"ανες," +s3+"ανε," +s+"άναμε," +s+"άνατε," +s3+"αναν#"
				+s+"άνω," +s+"άνεις," +s+"άνει," +s+"άνουμε|" +s+"άνομε," +s+"άνετε," +s+"άνουν|"+s+"άνουνε#"
				+s3+"ανε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			nm2=
				 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
				+s+"αίνου#"
				+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"αινόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
				+s+"άνθηκα," +s+"άνθηκες," +s+"άνθηκε," +s+"ανθήκαμε," +s+"ανθήκατε," +s+"άνθηκαν|"+s+"ανθήκανε#"
				+s+"ανθώ," +s+"ανθείς," +s+"ανθεί," +s+"ανθούμε," +s+"ανθείτε," +s+"ανθούν|"+s+"ανθούνε#"
				+s+"άνου#"
				+s+"ασμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr422c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr422c: -αίνω-αίνομαι/-να-νθηκα/-ασμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr422a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-εινα-είθηκα/-ένω-ένομαι/-ειμένος
		//μένω/έμεινα
		else if (termTxCpt_sRule.equals("rlElnTrmVbr441a")||termTxCpt_sRule.equals("rlElnTrmVbr441b")||termTxCpt_sRule.equals("rlElnTrmVbr441c") )
		{
			if (termTxCpt_sName.endsWith("ένω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,3);
			else if (termTxCpt_sName.endsWith("ένομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,6);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//μ-ένω/έμ-εινα
				if (termTxCpt_sRule.equals("rlElnTrmVbr441a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr441a: -ένω-ένομαι/-εινα-είθηκα/-ειμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr441b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr441b: -ένω-ένομαι/-εινα-είθηκα/-ειμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ένω," +s+"ένεις," +s+"ένει," +s+"ένουμε|" +s+"ένομε," +s+"ένετε," +s+"ένουν|"+s+"ένουνε#"
				+s+"ένε#"
				+"έ"+s+"ενα," +"έ"+s+"ενες," +"έ"+s+"ενε," +s+"έναμε," +s+"ένατε,έ" +s+"εναν|"+s+"ένανε#"
				+"έ"+s+"εινα," +"έ"+s+"εινες," +"έ"+s+"εινε," +s+"είναμε," +s+"είνατε,έ" +s+"αναν|"+s+"είνανε#"
				+s+"είνω," +s+"είνεις," +s+"είνει," +s+"είνουμε|" +s+"είνομε," +s+"είνετε," +s+"είνουν|"+s+"είνουνε#"
				+s+"είνε#"
				+s+"ένοντας|" +s+"είνοντας#";
			}
			else {
				//παραμ-ένω/παράμ-εινα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr441a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr441a: -ένω-ένομαι/-εινα-είθηκα/-ειμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr441b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr441b: -ένω-ένομαι/-εινα-είθηκα/-ειμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ένω," +s+"ένεις," +s+"ένει," +s+"ένουμε|" +s+"ένομε," +s+"ένετε," +s+"ένουν|"+s+"ένουνε#"
				+s3+"ενε#"
				+s3+"ενα," +s3+"ενες," +s3+"ενε," +s+"έναμε," +s+"ένατε," +s3+"εναν|"+s+"ένανε#"
				+s3+"εινα," +s3+"εινες," +s3+"εινε," +s+"είναμε," +s+"είνατε," +s3+"ειναν#"
				+s+"είνω," +s+"είνεις," +s+"είνει," +s+"είνουμε|" +s+"είνομε," +s+"είνετε," +s+"είνουν|"+s+"είνουνε#"
				+s3+"εινε#"
				+s+"ένοντας|" +s+"είνοντας#";
			}
			nm2=
				 s+"ένομαι," +s+"ένεσαι," +s+"ένεται," +s+"ενόμαστε," +s+"ένεστε," +s+"ένονται#"
				+s+"ένου#"
				+s+"ενόμουνα," +s+"ενόσουν," +s+"ενόταν|"+s+"ενότανε," +s+"ενόμαστε," +s+"ενόσαστε," +s+"ένονταν|"+s+"ενόντανε#"
				+s+"είθηκα," +s+"είθηκες," +s+"είθηκε," +s+"αθήκαμε," +s+"αθήκατε," +s+"είθηκαν|"+s+"αθήκανε#"
				+s+"ειθώ," +s+"ειθείς," +s+"ειθεί," +s+"ειθοείμε," +s+"ειθείτε," +s+"ειθοείν|"+s+"ειθούνε#"
				+s+"είνου#"
				+s+"ειμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr441c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr441c: -ένω-ένομαι/-εινα-είθηκα/-υμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr441a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-υνα-ύθηκα/-αίνω-αίνομαι/-υμένος
		//ακρυβ-αίνω/ακρύβ-υνα
		else if (termTxCpt_sRule.equals("rlElnTrmVbr442a")||termTxCpt_sRule.equals("rlElnTrmVbr442b")||termTxCpt_sRule.equals("rlElnTrmVbr442c") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-αίνομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-αίνω/έχ-υνα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr442a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr442a: -αίνω-αίνομαι/-υνα-ύθηκα/-υμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr442b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr442b: -αίνω-αίνομαι/-υνα-ύθηκα/-υμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"υνα," +"έ"+s+"υνες," +"έ"+s+"υνε," +s+"ύναμε," +s+"ύνατε,έ" +s+"αναν|"+s+"ύνανε#"
				+s+"ύνω," +s+"ύνεις," +s+"ύνει," +s+"ύνουμε|" +s+"ύνομε," +s+"ύνετε," +s+"ύνουν|"+s+"ύνουνε#"
				+s+"ύνε#"
				+s+"αίνοντας|" +s+"ύνοντας#";
			}
			else {
				//ακριβ-αίνω/ακρίβ-υνα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr442a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr442a: -αίνω-αίνομαι/-υνα-ύθηκα/-υμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr442b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr442b: -αίνω-αίνομαι/-υνα-ύθηκα/-υμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"υνα," +s3+"υνες," +s3+"υνε," +s+"ύναμε," +s+"ύνατε," +s3+"υναν#"
				+s+"ύνω," +s+"ύνεις," +s+"ύνει," +s+"ύνουμε|" +s+"ύνομε," +s+"ύνετε," +s+"ύνουν|"+s+"ύνουνε#"
				+s3+"υνε#"
				+s+"αίνοντας|" +s+"ύνοντας#";
			}
			nm2=
				 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
				+s+"αίνου," +s+"αίνεστε#"
				+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"αινόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
				+s+"ύθηκα," +s+"ύθηκες," +s+"ύθηκε," +s+"αθήκαμε," +s+"αθήκατε," +s+"ύθηκαν|"+s+"αθήκανε#"
				+s+"υθώ," +s+"υθείς," +s+"υθεί," +s+"υθούμε," +s+"υθείτε," +s+"υθούν|"+s+"υθούνε#"
				+s+"ύνου," +s+"υθείτε#"
				+s+"υμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr442c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr442c: -αίνω-αίνομαι/-υνα-ύθηκα/-υμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr442a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-υνα-ύθηκα/-ένω-ένομαι/-υμένος
		//πλένω/πλένομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr443a")||termTxCpt_sRule.equals("rlElnTrmVbr443b")||termTxCpt_sRule.equals("rlElnTrmVbr443c") )
		{
			if (termTxCpt_sName.endsWith("ένω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,3);
			else //-ένομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,6);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//πλ-ένω/έπλ-υνα
				if (termTxCpt_sRule.equals("rlElnTrmVbr443a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr443a: -ένω-ένομαι/-υνα-ύθηκα/-υμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr443b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr443b: -ένω-ένομαι/-υνα-ύθηκα/-υμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ένω," +s+"ένεις," +s+"ένει," +s+"ένουμε|" +s+"ένομε," +s+"ένετε," +s+"ένουν|"+s+"ένουνε#"
				+s+"ένε#"
				+"έ"+s+"ενα," +"έ"+s+"ενες," +"έ"+s+"ενε," +s+"έναμε," +s+"ένατε,έ" +s+"εναν|"+s+"ένανε#"
				+"έ"+s+"υνα," +"έ"+s+"υνες," +"έ"+s+"υνε," +s+"ύναμε," +s+"ύνατε,έ" +s+"αναν|"+s+"ύνανε#"
				+s+"ύνω," +s+"ύνεις," +s+"ύνει," +s+"ύνουμε|" +s+"ύνομε," +s+"ύνετε," +s+"ύνουν|"+s+"ύνουνε#"
				+s+"ύνε#"
				+s+"ένοντας|" +s+"ύνοντας#";
			}
			else {
				//ξεπλ-ένω/ξέπλ-υνα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr443a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr443a: -ένω-ένομαι/-υνα-ύθηκα/-υμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr443b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr443b: -ένω-ένομαι/-υνα-ύθηκα/-υμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ένω," +s+"ένεις," +s+"ένει," +s+"ένουμε|" +s+"ένομε," +s+"ένετε," +s+"ένουν|"+s+"ένουνε#"
				+s3+"ενε#"
				+s3+"ενα," +s3+"ενες," +s3+"ενε," +s+"έναμε," +s+"ένατε," +s3+"εναν|"+s+"ένανε#"
				+s3+"υνα," +s3+"υνες," +s3+"υνε," +s+"ύναμε," +s+"ύνατε," +s3+"υναν#"
				+s+"ύνω," +s+"ύνεις," +s+"ύνει," +s+"ύνουμε|" +s+"ύνομε," +s+"ύνετε," +s+"ύνουν|"+s+"ύνουνε#"
				+s3+"υνε#"
				+s+"ένοντας|" +s+"ύνοντας#";
			}
			nm2=
				 s+"ένομαι," +s+"ένεσαι," +s+"ένεται," +s+"ενόμαστε," +s+"ένεστε," +s+"ένονται#"
				+s+"ένου#"
				+s+"ενόμουνα," +s+"ενόσουν," +s+"ενόταν|"+s+"ενότανε," +s+"ενόμαστε," +s+"ενόσαστε," +s+"ένονταν|"+s+"ενόντανε#"
				+s+"ύθηκα," +s+"ύθηκες," +s+"ύθηκε," +s+"αθήκαμε," +s+"αθήκατε," +s+"ύθηκαν|"+s+"αθήκανε#"
				+s+"υθώ," +s+"υθείς," +s+"υθεί," +s+"υθούμε," +s+"υθείτε," +s+"υθούν|"+s+"υθούνε#"
				+s+"ύνου#"
				+s+"υμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr443c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr443c: -ένω-ένομαι/-υνα-ύθηκα/-υμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr443a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

//5)-ξα-χτηκα

		//-γγω-γγομαι/-ξα-χτηκα/-γμένος
		//-γγω-σκω-σσω-ττω-χνω
		else if (termTxCpt_sRule.equals("rlElnTrmVbr511a")||termTxCpt_sRule.equals("rlElnTrmVbr511b")||termTxCpt_sRule.equals("rlElnTrmVbr511c") )
		{
			//η κατάληξη χωρίς το ω
			String c="";
			if (termTxCpt_sName.endsWith("ω"))
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,3);			//διδά-
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-3,termTxCpt_sName.length()-1);
			}
			else //-ομαι
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,6);			//διδά-
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-6,termTxCpt_sName.length()-4);
			}
			s2=greekTonosRemove(s);								//διδα-

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//σφί-γγω/έσφι-ξα
				if (termTxCpt_sRule.equals("rlElnTrmVbr511a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr511a: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr511b: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s+c+"ε#"
				+"έ"+s2+c+"α," +"έ"+s2+c+"ες," +"έ"+s2+c+"ε," +s+c+"αμε," +s+c+"ατε,έ" +s2+c+"αν#"
				+"έ"+s2+"ξα," +"έ"+s2+"ξες," +"έ"+s2+"ξε," +s+"ξαμε," +s+"ξατε,έ" +s2+"ξαν#"
				+s+"ξω," +s+"ξεις," +s+"ξει," +s+"ξουμε|" +s+"ξομε," +s+"ξετε," +s+"ξουν|"+s+"ξουνε#"
				+s+"ξε#"
				+s+c+"οντας#";
			}
			else {
				//διδά-σκω/δίδα-ξα
				s3=greekTonosDecrease(s);				//δίδα-
				if (termTxCpt_sRule.equals("rlElnTrmVbr511a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr511a: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr511b: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s3+c+"ε#"
				+s3+c+"α," +s3+c+"ες," +s3+c+"ε," +s+c+"αμε," +s+c+"ατε," +s3+c+"αν|"+s+c+"ανε#"
				+s3+"ξα," +s3+"ξες," +s3+"ξε," +s+"ξαμε," +s+"ξατε," +s3+"ξαν#"
				+s+"ξω," +s+"ξεις," +s+"ξει," +s+"ξουμε|" +s+"ξομε," +s+"ξετε," +s+"ξουν|"+s+"ξουνε#"
				+s3+"ξε#"
				+s+c+"οντας#";
			}
			//σφί-γγομαι/σφί-χτηκα, διδά-σκομαι/διδά-χτηκα
			nm2=
				 s+c+"ομαι," +s+c+"εσαι," +s+c+"εται," +s2+c+"όμαστε" +s+c+"εστε," +s+c+"ονται#"
				+s+c+"ου#"
				+s2+c+"όμουνα," +s2+c+"όσουν," +s2+c+"όταν|"+s2+c+"ότανε," +s2+c+"όμαστε," +s2+c+"όσαστε," +s+c+"ονταν|"+s2+c+"όντανε#"
				+s+"χτηκα," +s+"χτηκες," +s+"χτηκε," +s2+"χτήκαμε," +s2+"χτήκατε," +s+"χτηκαν|"+s2+"χτήκανε#"
				+s2+"χτώ," +s2+"χτείς," +s2+"χτεί," +s2+"χτούμε," +s2+"χτείτε," +s2+"χτούν|"+s2+"χτούνε#"
				+s+"ξου#"
				+s2+"γμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr511c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr511c: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr511a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//πλέ-κω-κομαι/έπλε-ξα-πλέχτηκα/πλεγμένος
		//-νω-κω-γω-χω
		else if (termTxCpt_sRule.equals("rlElnTrmVbr512a")||termTxCpt_sRule.equals("rlElnTrmVbr512b")||termTxCpt_sRule.equals("rlElnTrmVbr512c") )
		{
			//η κατάληξη χωρίς το ω
			String c="";
			if (termTxCpt_sName.endsWith("ω"))
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);			//διδά-
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-2,termTxCpt_sName.length()-1);
			}
			else //-ομαι
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);			//διδά-
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-5,termTxCpt_sName.length()-4);
			}
			s2=greekTonosRemove(s);								//διδα-

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//πλέ-κω/έπλε-ξα
				if (termTxCpt_sRule.equals("rlElnTrmVbr512a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr512a: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr512b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr512b: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s+c+"ε#"
				+"έ"+s2+c+"α," +"έ"+s2+c+"ες," +"έ"+s2+c+"ε," +s+c+"αμε," +s+c+"ατε,έ" +s2+c+"αν#"
				+"έ"+s2+"ξα," +"έ"+s2+"ξες," +"έ"+s2+"ξε," +s+"ξαμε," +s+"ξατε,έ" +s2+"ξαν#"
				+s+"ξω," +s+"ξεις," +s+"ξει," +s+"ξουμε|" +s+"ξομε," +s+"ξετε," +s+"ξουν|"+s+"ξουνε#"
				+s+"ξε#"
				+s+c+"οντας#";
			}
			else {
				//προπλέ-κω/πρόπλε-ξα
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr512a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr512a: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr512b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr512b: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s3+c+"ε#"
				+s3+c+"α," +s3+c+"ες," +s3+c+"ε," +s+c+"αμε," +s+c+"ατε," +s3+c+"αν|"+s+c+"ανε#"
				+s3+"ξα," +s3+"ξες," +s3+"ξε," +s+"ξαμε," +s+"ξατε," +s3+"ξαν#"
				+s+"ξω," +s+"ξεις," +s+"ξει," +s+"ξουμε|" +s+"ξομε," +s+"ξετε," +s+"ξουν|"+s+"ξουνε#"
				+s3+"ξε#"
				+s+c+"οντας#";
			}
			//πλέ-κομαι/πλέ-χτηκα
			nm2=
				 s+c+"ομαι," +s+c+"εσαι," +s+c+"εται," +s2+c+"όμαστε," +s+c+"εστε," +s+c+"ονται#"
				+s+c+"ου," +s+c+"εστε#"
				+s2+c+"όμουνα," +s2+c+"όσουν," +s2+c+"όταν|"+s2+c+"ότανε," +s2+c+"όμαστε," +s2+c+"όσαστε," +s+c+"ονταν|"+s2+c+"όντανε#"
				+s+"χτηκα," +s+"χτηκες," +s+"χτηκε," +s2+"χτήκαμε," +s2+"χτήκατε," +s+"χτηκαν|"+s2+"χτήκανε#"
				+s2+"χτώ," +s2+"χτείς," +s2+"χτεί," +s2+"χτούμε," +s2+"χτείτε," +s2+"χτούν|"+s2+"χτούνε#"
				+s+"ξου," +s2+"χτείτε#"
				+s2+"γμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr512c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr512c: -"+c+"ω-"+c+"ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr512a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//(γ)ω-(γ)ομαι/-ξα-χτηκα/-γμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr513a")||termTxCpt_sRule.equals("rlElnTrmVbr513b")||termTxCpt_sRule.equals("rlElnTrmVbr513c") )
		{
			if (termTxCpt_sName.endsWith("γω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("γομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);
			else if (termTxCpt_sName.endsWith("ομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			s2=greekTonosRemove(s);								//διδα-

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//φλά-ω-γομαι
				if (termTxCpt_sRule.equals("rlElnTrmVbr513a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr513a: -(γ)ω-(γ)ομαι/-ξα-χτηκα/-γμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr513b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr513b: -(γ)ω-(γ)ομαι/-ξα-χτηκα/-γμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"γω," +s+"ς|"+s+"γεις," +s+"ει|"+s+"γει," +s+"με," +s+"τε," +s+"ν|"+s+"νε#"
				+s+"ε|"+s+"γε#"
				+"έ"+s2+"α|"+"έ"+s2+"γα," +"έ"+s2+"ες|"+"έ"+s2+"γες," +"έ"+s2+"ε|"+"έ"+s2+"γε," +s+"αμε|"+s+"γαμε," +s+"ατε|"+s+"γατε," +"έ"+s2+"αν|"+"έ"+s2+"γαν#"
				+"έ"+s2+"ξα," +"έ"+s2+"ξες," +"έ"+s2+"ξε," +s+"ξαμε," +s+"ξατε,έ" +s2+"ξαν#"
				+s+"ξω," +s+"ξεις," +s+"ξει," +s+"ξουμε|" +s+"ξομε," +s+"ξετε," +s+"ξουν|"+s+"ξουνε#"
				+s+"ξε#"
				+s+"οντας|"+s+"γοντας#";
			}
			else {
				//φυλά-ω/φύλα-ξα
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr513a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr513a: -(γ)ω-(γ)ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr513b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr513b: -(γ)ω-(γ)ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"γω," +s+"ς|"+s+"γεις," +s+"ει|"+s+"γει," +s+"με," +s+"τε," +s+"ν|"+s+"νε#"
				+s3+"ε|"+s3+"γε#"
				+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
				+s3+"ξα," +s3+"ξες," +s3+"ξε," +s+"ξαμε," +s+"ξατε," +s3+"ξαν#"
				+s+"ξω," +s+"ξεις," +s+"ξει," +s+"ξουμε|" +s+"ξομε," +s+"ξετε," +s+"ξουν|"+s+"ξουνε#"
				+s3+"ξε#"
				+s+"οντας|"+s+"γοντας#";
			}
			//φυλά(γ)ομαι/φυλάχτηκα
			nm2=
				 s+"ομαι|"+s+"γομαι," +s+"εσαι|"+s+"γεσαι," +s+"εται|"+s+"γεται," +s2+"όμαστε|"+s2+"γόμαστε," +s+"εστε|"+s+"γεστε," +s+"ονται|"+s+"γονται#"
				+s+"ου|"+s+"γου#"
				+s2+"όμουνα|"+s2+"γόμουνα," +s2+"όσουν|"+s2+"γόσουν," +s2+"όταν|"+s2+"γόταν|" +s2+"ότανε|"+s2+"γότανε," +s2+"όμαστε|"+s2+"γόμαστε," +s2+"όσαστε|"+s2+"γόσαστε," +s+"ονταν|"+s+"γονταν|"+s2+"όντανε|"+s2+"όντανε#"
				+s+"χτηκα," +s+"χτηκες," +s+"χτηκε," +s2+"χτήκαμε," +s2+"χτήκατε," +s+"χτηκαν|"+s2+"χτήκανε#"
				+s2+"χτώ," +s2+"χτείς," +s2+"χτεί," +s2+"χτούμε," +s2+"χτείτε," +s2+"χτούν|"+s2+"χτούνε#"
				+s+"ξου#"
				+s2+"γμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr513c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr513c: -(γ)ω-(γ)ομαι/-ξα-χτηκα/-γμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr513a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-άξα-άχτηκα/-αίνω-αίνομαι
		//βυζαίνω-βυζαίνομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr521a")||termTxCpt_sRule.equals("rlElnTrmVbr521b")||termTxCpt_sRule.equals("rlElnTrmVbr521c") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else if (termTxCpt_sName.endsWith("αίνομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-αίνω/έχ-αξα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr521a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr521a: -αίνω-αίνομαι/-αξα-άχτηκα/-αγμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr521b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr521b: -αίνω-αίνομαι/-αξα-άχτηκα/-αγμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"αξα," +"έ"+s+"αξες," +"έ"+s+"αξε," +s+"άξαμε," +s+"άξατε,έ" +s+"αξαν|"+s+"άξανε#"
				+s+"άξω," +s+"άξεις," +s+"άξει," +s+"άξουμε|" +s+"άξομε," +s+"άξετε," +s+"άξουν|"+s+"άξουνε#"
				+s+"άξε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			else {
				//βυζ-αίνω/βύζ-αξα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr521a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr521a: -αίνω-αίνομαι/-αξα-άχτηκα/-αγμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr521b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr521b: -αίνω-αίνομαι/-αξα-άχτηκα/-αγμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"αξα," +s3+"αξες," +s3+"αξε," +s+"άξαμε," +s+"άξατε," +s3+"αξαν#"
				+s+"άξω," +s+"άξεις," +s+"άξει," +s+"άξουμε|" +s+"άξομε," +s+"άξετε," +s+"άξουν|"+s+"άξουνε#"
				+s3+"αξε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			//βυζ-αίνομαι/βυζ-άχτηκα
			nm2=
				 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
				+s+"αίνου#"
				+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"αινόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
				+s+"άχτηκα," +s+"άχτηκες," +s+"άχτηκε," +s+"αχτήκαμε," +s+"αχτήκατε," +s+"άχτηκαν|"+s+"αχτήκανε#"
				+s+"αχτώ," +s+"αχτείς," +s+"αχτεί," +s+"αχτούμε," +s+"αχτείτε," +s+"αχτούν|"+s+"αχτούνε#"
				+s+"άξου#"
				+s+"αγμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr521c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr521c: -αίνω-αίνομαι/-αξα-άχτηκα/-αγμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr521a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-άξα-άχτηκα/-ώ&άω-ιέμαι/-αγμένος
		//πετάω-πετιέμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr522a")||termTxCpt_sRule.equals("rlElnTrmVbr522b")||termTxCpt_sRule.equals("rlElnTrmVbr522c") )
		{
			if (termTxCpt_sName.endsWith("άω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else //-ιέμαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-άω|ώ-έχαξα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr522a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr522a: -ώ&άω-ιέμαι/-αξα-άχτηκα/-αγμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr522b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr522b: -ώ&άω-ιέμαι/-αξα-άχτηκα/-αγμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s+"ά#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"αξα," +"έ"+s+"αξες," +"έ"+s+"αξε," +s+"άξαμε," +s+"άξατε," +"έ"+s+"αξαν#"
				+s+"άξω," +s+"άξεις," +s+"άξει," +s+"άξουμε|"+s+"άξομε," +s+"άξετε," +s+"άξουν|"+s+"άξουνε#"
				+s+"άξε#"
				+s+"ώντας#";
			}
			else {
				//πετ-άω|ώ/πέτ-αξα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr522a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr522a: -ώ&άω-ιέμαι/-αξα-άχτηκα/-αγμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr522b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr522b: -ώ&άω-ιέμαι/-αξα-άχτηκα/-αγμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s2+"α#"
				+s+"ούσα|"+s2+"αγα," +s+"ούσες|"+s2+"αγες," +s+"ούσε|"+s2+"αγε," +s+"ούσαμε|"+s+"άγαμε," +s+"ούσατε|"+s+"άγατε," +s+"ούσαν|"+s+"ούσανε|"+s+"άγανε#"
				+s2+"αξα," +s2+"αξες," +s2+"αξε," +s+"άξαμε," +s+"άξατε," +s2+"αξαν#"
				+s+"άξω," +s+"άξεις," +s+"άξει," +s+"άξουμε|"+s+"άξομε," +s+"άξετε," +s+"άξουν|"+s+"άξουνε#"
				+s2+"αξε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ιέμαι," +s+"ιέσαι," +s+"ιέται," +s+"ιόμαστε," +s+"ιέστε," +s+"ιούνται#"
				+"-#"
				+s+"ιόμουνα," +s+"ιόσουν," +s+"ιόταν|"+s+"ιότανε," +s+"ιόμαστε," +s+"ιόσαστε," +s+"ιόνταν|"+s+"ιόντανε#"
				+s+"άχτηκα," +s+"άχτηκες," +s+"άχτηκε," +s+"αχτήκαμε," +s+"αχτήκατε," +s+"άχτηκαν|"+s+"αχτήκανε#"
				+s+"αχτώ," +s+"αχτείς," +s+"αχτεί," +s+"αχτούμε," +s+"αχτείτε," +s+"αχτούν#"
				+s+"άξου#"
				+s+"αγμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr522c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr522c: -ώ&άω-ιέμαι/-αξα-άχτηκα/-αγμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr522a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ηξα-ήχτηκα/-ώ&άω-ιέμαι/-ηγμένος
		//πηδάω-πηδιέμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr541a")||termTxCpt_sRule.equals("rlElnTrmVbr541b")||termTxCpt_sRule.equals("rlElnTrmVbr541c") )
		{
			if (termTxCpt_sName.endsWith("άω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else //-ιέμαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-άω|ώ-έχηξα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr541a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr541a: -ώ&άω-ιέμαι/-ηξα-ήχτηκα/-αγμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr541b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr541b: -ώ&άω-ιέμαι/-ηξα-ήχτηκα/-αγμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s+"ά#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"ηξα," +"έ"+s+"ηξες," +"έ"+s+"ηξε," +s+"ήξαμε," +s+"ήξατε," +"έ"+s+"ηξαν#"
				+s+"ήξω," +s+"ήξεις," +s+"ήξει," +s+"ήξουμε|"+s+"ήξομε," +s+"ήξετε," +s+"ήξουν|"+s+"ήξουνε#"
				+s+"ήξε#"
				+s+"ώντας#";
			}
			else {
				//πετ-άω|ώ/πέτ-ηξα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr541a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr541a: -ώ&άω-ιέμαι/-ηξα-ήχτηκα/-αγμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr541b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr541b: -ώ&άω-ιέμαι/-ηξα-ήχτηκα/-αγμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s2+"α#"
				+s+"ούσα|"+s2+"αγα," +s+"ούσες|"+s2+"αγες," +s+"ούσε|"+s2+"αγε," +s+"ούσαμε|"+s+"άγαμε," +s+"ούσατε|"+s+"άγατε," +s+"ούσαν|"+s+"ούσανε|"+s+"άγανε#"
				+s2+"ηξα," +s2+"ηξες," +s2+"ηξε," +s+"ήξαμε," +s+"ήξατε," +s2+"ηξαν#"
				+s+"ήξω," +s+"ήξεις," +s+"ήξει," +s+"ήξουμε|"+s+"ήξομε," +s+"ήξετε," +s+"ήξουν|"+s+"ήξουνε#"
				+s2+"ηξε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ιέμαι," +s+"ιέσαι," +s+"ιέται," +s+"ιόμαστε," +s+"ιέστε," +s+"ιούνται#"
				+"-#"
				+s+"ιόμουνα," +s+"ιόσουν," +s+"ιόταν|"+s+"ιότανε," +s+"ιόμαστε," +s+"ιόσαστε," +s+"ιόνταν|"+s+"ιόντανε#"
				+s+"ήχτηκα," +s+"ήχτηκες," +s+"ήχτηκε," +s+"ηχτήκαμε," +s+"ηχτήκατε," +s+"ήχτηκαν|"+s+"ηχτήκανε#"
				+s+"ηχτώ," +s+"ηχτείς," +s+"ηχτεί," +s+"ηχτούμε," +s+"ηχτείτε," +s+"ηχτούν#"
				+s+"ήξου#"
				+s+"ηγμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr541c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr541c: -ώ&άω-ιέμαι/-ηξα-ήχτηκα/-αγμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr541a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

//6)-ρα-ρθηκα

		//-έρνω-έρνομαι/-αρα-άρθηκα/-αρμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr621a")||termTxCpt_sRule.equals("rlElnTrmVbr621b")||termTxCpt_sRule.equals("rlElnTrmVbr621c") )
		{
			//γδ-έρνω-έρνομαι/έ-γδ-ειρα-άρθηκα/γδ-αρμένος
			if (termTxCpt_sName.endsWith("έρνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//γδ-έρνω/έ-γδ-ειρα
				if (termTxCpt_sRule.equals("rlElnTrmVbr621a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr621a: -έρνω-έρνομαι/-αρα-άρθηκα/-αρμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr621b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr621b: -έρνω-έρνομαι/-αρα-άρθηκα/-αρμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"έρνω," +s+"έρνεις," +s+"έρνει," +s+"έρνουμε|" +s+"έρνομε," +s+"έρνετε," +s+"έρνουν|"+s+"έρνουνε#"
				+s+"έρνε#"
				+"έ"+s+"ερνα," +"έ"+s+"ερνες," +"έ"+s+"ερνε," +s+"έρναμε," +s+"έρνατε," +"έ"+s+"ερναν|"+s+"έρνανε#"
				+"έ"+s+"αρα," +"έ"+s+"αρες," +"έ"+s+"αρε," +s+"άραμε," +s+"άρατε,έ" +s+"αραν|"+"ε"+s+"άρανε|"+s+"άρανε#"
				+s+"άρω," +s+"άρεις," +s+"άρει," +s+"άρουμε|" +s+"άρομε," +s+"άρετε," +s+"άρουν|"+s+"άρουνε#"
				+s+"άρε#"
				+s+"έρνοντας|"+s+"άροντας#";
			}
			else {
				//παραγδ-έρνω/παράγδ-αρα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr621a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr621a: -έρνω-έρνομαι/-αρα-άρθηκα/-αρμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr621b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr621b: -έρνω-έρνομαι/-αρα-άρθηκα/-αρμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"έρνω," +s+"έρνεις," +s+"έρνει," +s+"έρνουμε|" +s+"έρνομε," +s+"έρνετε," +s+"έρνουν|"+s+"έρνουνε#"
				+s2+"ερνε#"
				+s2+"ερνα," +s2+"ερνες," +s2+"ερνε," +s+"έρναμε," +s+"έρνατε," +s2+"ερναν|"+s+"έρνανε#"
				+s2+"αρα," +s2+"αρες," +s2+"αρε," +s+"άραμε," +s+"άρατε," +s2+"αραν|"+s+"άρανε#"
				+s+"άρω," +s+"άρεις," +s+"άρει," +s+"άρουμε|" +s+"άρομε," +s+"άρετε," +s+"άρουν|"+s+"άρουνε#"
				+s2+"αρε#"
				+s+"έρνοντας|"+s+"άροντας#";
			}
			nm2=
			 s+"έρνομαι," +s+"έρνεσαι," +s+"έρνεται," +s+"ερνόμαστε," +s+"έρνεστε," +s+"έρνονται#"
			+s+"έρνου#"
			+s+"ερνόμουνα," +s+"ερνόσουν," +s+"ερνόταν|"+s+"ερνότανε," +s+"ερνόμαστε," +s+"νόσαστε," +s+"έρνονταν|"+s+"ερνόντανε#"
			+s+"άρθηκα," +s+"άρθηκες," +s+"άρθηκε," +s+"αρθήκαμε," +s+"αρθήκατε," +s+"άρθηκαν|"+s+"αρθήκανε#"
			+s+"αρθώ," +s+"αρθείς," +s+"αρθεί," +s+"αρθούμε," +s+"αρθείτε," +s+"αρθούν#"
			+s+"άρσου#"
			+s+"αρμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr621c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr621c: -έρνω-έρνομαι/-αρα-άρθηκα/-αρμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr621a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-έρνω-έρνομαι/-ειρα-άρθηκα/-αρμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr641a")||termTxCpt_sRule.equals("rlElnTrmVbr641b")||termTxCpt_sRule.equals("rlElnTrmVbr641c") )
		{
			//σπ-έρνω-έρνομαι/έ-σπ-ειρα-άρθηκα/σπ-αρμένος
			if (termTxCpt_sName.endsWith("έρνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);			//σπ-
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);			//σπ-

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//σπ-έρνω/έ-σπ-ειρα
				if (termTxCpt_sRule.equals("rlElnTrmVbr641a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr641a: -έρνω-έρνομαι/-ειρα-άρθηκα/-αρμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr641b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr641b: -έρνω-έρνομαι/-ειρα-άρθηκα/-αρμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"έρνω," +s+"έρνεις," +s+"έρνει," +s+"έρνουμε|" +s+"έρνομε," +s+"έρνετε," +s+"έρνουν|"+s+"έρνουνε#"
				+s+"έρνε#"
				+"έ"+s+"ερνα," +"έ"+s+"ερνες," +"έ"+s+"ερνε," +s+"έρναμε," +s+"έρνατε," +"έ"+s+"ερναν|"+s+"έρνανε#"
				+"έ"+s+"ειρα," +"έ"+s+"ειρες," +"έ"+s+"ειρε," +s+"είραμε," +s+"είρατε,έ" +s+"ειραν|"+"ε"+s+"είρανε|"+s+"είρανε#"
				+s+"είρω," +s+"είρεις," +s+"είρει," +s+"είρουμε|" +s+"είρομε," +s+"είρετε," +s+"είρουν|"+s+"είρουνε#"
				+s+"είρε#"
				+s+"έρνοντας|"+s+"είροντας#";
			}
			else {
				//βολοδ-έρνω/βολόδ-ειρα
				s2=greekTonosSetOnFirst(s);				//βολόδ-
				if (termTxCpt_sRule.equals("rlElnTrmVbr641a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr641a: -έρνω-έρνομαι/-ειρα-άρθηκα/-αρμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr641b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr641b: -έρνω-έρνομαι/-ειρα-άρθηκα/-αρμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"έρνω," +s+"έρνεις," +s+"έρνει," +s+"έρνουμε|" +s+"έρνομε," +s+"έρνετε," +s+"έρνουν|"+s+"έρνουνε#"
				+s2+"ερνε#"
				+s2+"ερνα," +s2+"ερνες," +s2+"ερνε," +s+"έρναμε," +s+"έρνατε," +s2+"ερναν|"+s+"έρνανε#"
				+s2+"ειρα," +s2+"ειρες," +s2+"ειρε," +s+"είραμε," +s+"είρατε," +s2+"ειραν|"+s+"είρανε#"
				+s+"είρω," +s+"είρεις," +s+"είρει," +s+"είρουμε|" +s+"είρομε," +s+"είρετε," +s+"είρουν|"+s+"είρουνε#"
				+s2+"ειρε#"
				+s+"έρνοντας|"+s+"είροντας#";
			}
			nm2=
			 s+"έρνομαι," +s+"έρνεσαι," +s+"έρνεται," +s+"ερνόμαστε," +s+"έρνεστε," +s+"έρνονται#"
			+s+"έρνου#"
			+s+"ερνόμουνα," +s+"ερνόσουν," +s+"ερνόταν|"+s+"ερνότανε," +s+"ερνόμαστε," +s+"ερνόσαστε," +s+"έρνονταν|"+s+"ερνόντανε#"
			+s+"άρθηκα," +s+"άρθηκες," +s+"άρθηκε," +s+"αρθήκαμε," +s+"αρθήκατε," +s+"άρθηκαν|"+s+"αρθήκανε#"
			+s+"αρθώ," +s+"αρθείς," +s+"αρθεί," +s+"αρθούμε," +s+"αρθείτε," +s+"αρθούν#"
			+s+"άρσου#"
			+s+"αρμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr641c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr641c: -έρνω-έρνομαι/-ειρα-άρθηκα/-αρμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr641a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-έρνω-έρνομαι/-ερα-έρθηκα/-ερμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr642a")||termTxCpt_sRule.equals("rlElnTrmVbr642b")||termTxCpt_sRule.equals("rlElnTrmVbr642c") )
		{
			//φέρνω-έρνομαι/έφ-ερα-έρθηκα/φ-ερμένος
			if (termTxCpt_sName.endsWith("έρνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//φέρνω-έφερα
				if (termTxCpt_sRule.equals("rlElnTrmVbr642a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr642a: -έρνω-έρνομαι/-ερα-έρθηκα/-ερμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr642b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr642b: -έρνω-έρνομαι/-ερα-έρθηκα/-ερμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"έρνω," +s+"έρνεις," +s+"έρνει," +s+"έρνουμε|" +s+"έρνομε," +s+"έρνετε," +s+"έρνουν|"+s+"έρνουνε#"
				+s+"έρνε#"
				+"έ"+s+"ερνα," +"έ"+s+"ερνες," +"έ"+s+"ερνε," +s+"έρναμε," +s+"έρνατε," +"έ"+s+"ερναν|"+s+"έρνανε#"
				+"έ"+s+"ερα," +"έ"+s+"ερες," +"έ"+s+"ερε," +s+"έραμε," +s+"έρατε,έ" +s+"εραν|"+"ε"+s+"έρανε|"+s+"έρανε#"
				+s+"έρω," +s+"έρεις," +s+"έρει," +s+"έρουμε|" +s+"έρομε," +s+"έρετε," +s+"έρουν|"+s+"έρουνε#"
				+s+"έρε#"
				+s+"έρνοντας|"+s+"έροντας#";
			}
			else {
				//γυροφέρνω/γυρόφερα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr642a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr642a: -έρνω-έρνομαι/-ερα-έρθηκα/-ερμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr642b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr642b: -έρνω-έρνομαι/-ερα-έρθηκα/-ερμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"έρνω," +s+"έρνεις," +s+"έρνει," +s+"έρνουμε|" +s+"έρνομε," +s+"έρνετε," +s+"έρνουν|"+s+"έρνουνε#"
				+s2+"ερνε#"
				+s2+"ερνα," +s2+"ερνες," +s2+"ερνε," +s+"έρναμε," +s+"έρνατε," +s2+"ερναν|"+s+"έρνανε#"
				+s2+"ερα," +s2+"ερες," +s2+"ερε," +s+"έραμε," +s+"έρατε," +s2+"εραν|"+s+"έρανε#"
				+s+"έρω," +s+"έρεις," +s+"έρει," +s+"έρουμε|" +s+"έρομε," +s+"έρετε," +s+"έρουν|"+s+"έρουνε#"
				+s2+"ερε#"
				+s+"έρνοντας|"+s+"έροντας#";
			}
			nm2=
			 s+"έρνομαι," +s+"έρνεσαι," +s+"έρνεται," +s+"ερνόμαστε," +s+"έρνεστε," +s+"έρνονται#"
			+s+"έρνου#"
			+s+"ερνόμουνα," +s+"ερνόσουν," +s+"ερνόταν|"+s+"ερνότανε," +s+"ερνόμαστε," +s+"νόσαστε," +s+"έρνονταν|"+s+"ερνόντανε#"
			+s+"έρθηκα," +s+"έρθηκες," +s+"έρθηκε," +s+"αρθήκαμε," +s+"αρθήκατε," +s+"έρθηκαν|"+s+"αρθήκανε#"
			+s+"ερθώ," +s+"ερθείς," +s+"ερθεί," +s+"ερθούμε," +s+"ερθείτε," +s+"ερθούν#"
			+s+"έρσου#"
			+s+"ερμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr642c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr642c: -έρνω-έρνομαι/-ερα-έρθηκα/-ερμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr642a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-έρνω-έρνομαι/-υρα-ύρθηκα/-υρμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr643a")||termTxCpt_sRule.equals("rlElnTrmVbr643b")||termTxCpt_sRule.equals("rlElnTrmVbr643c") )
		{
			//σ-έρνω-έρνομαι/έ-σ-υρα-ύρθηκα/σ-υρμένος
			if (termTxCpt_sName.endsWith("έρνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//σ-έρνω/έ-σ-υρα
				if (termTxCpt_sRule.equals("rlElnTrmVbr643a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr643a: -έρνω-έρνομαι/-υρα-ύρθηκα/-υρμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr643b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr643b: -έρνω-έρνομαι/-υρα-ύρθηκα/-υρμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"έρνω," +s+"έρνεις," +s+"έρνει," +s+"έρνουμε|" +s+"έρνομε," +s+"έρνετε," +s+"έρνουν|"+s+"έρνουνε#"
				+s+"έρνε#"
				+"έ"+s+"ερνα," +"έ"+s+"ερνες," +"έ"+s+"ερνε," +s+"έρναμε," +s+"έρνατε," +"έ"+s+"ερναν|"+s+"έρνανε#"
				+"έ"+s+"υρα," +"έ"+s+"υρες," +"έ"+s+"υρε," +s+"ύραμε," +s+"ύρατε,έ" +s+"υραν|"+"ε"+s+"ύρανε|"+s+"ύρανε#"
				+s+"ύρω," +s+"ύρεις," +s+"ύρει," +s+"ύρουμε|" +s+"ύρομε," +s+"ύρετε," +s+"ύρουν|"+s+"ύρουνε#"
				+s+"ύρε#"
				+s+"έρνοντας|"+s+"ύροντας#";
			}
			else {
				//παραγδ-έρνω/παρύγδ-αρα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr643a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr643a: -έρνω-έρνομαι/-υρα-ύρθηκα/-υρμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr643b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr643b: -έρνω-έρνομαι/-υρα-ύρθηκα/-υρμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"έρνω," +s+"έρνεις," +s+"έρνει," +s+"έρνουμε|" +s+"έρνομε," +s+"έρνετε," +s+"έρνουν|"+s+"έρνουνε#"
				+s2+"ερνε#"
				+s2+"ερνα," +s2+"ερνες," +s2+"ερνε," +s+"έρναμε," +s+"έρνατε," +s2+"ερναν|"+s+"έρνανε#"
				+s2+"υρα," +s2+"υρες," +s2+"υρε," +s+"ύραμε," +s+"ύρατε," +s2+"υραν|"+s+"ύρανε#"
				+s+"ύρω," +s+"ύρεις," +s+"ύρει," +s+"ύρουμε|" +s+"ύρομε," +s+"ύρετε," +s+"ύρουν|"+s+"ύρουνε#"
				+s2+"υρε#"
				+s+"έρνοντας|"+s+"ύροντας#";
			}
			nm2=
			 s+"έρνομαι," +s+"έρνεσαι," +s+"έρνεται," +s+"ερνόμαστε," +s+"έρνεστε," +s+"έρνονται#"
			+s+"έρνου#"
			+s+"ερνόμουνα," +s+"ερνόσουν," +s+"ερνόταν|"+s+"ερνότανε," +s+"ερνόμαστε," +s+"νόσαστε," +s+"έρνονταν|"+s+"ερνόντανε#"
			+s+"ύρθηκα," +s+"ύρθηκες," +s+"ύρθηκε," +s+"υρθήκαμε," +s+"υρθήκατε," +s+"ύρθηκαν|"+s+"υρθήκανε#"
			+s+"υρθώ," +s+"υρθείς," +s+"υρθεί," +s+"υρθούμε," +s+"υρθείτε," +s+"υρθούν#"
			+s+"ύρσου#"
			+s+"υρμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr643c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr643c: -έρνω-έρνομαι/-υρα-ύρθηκα/-υρμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr643a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

//7)-σα-θηκα

		//-σα-θηκα/-νω-νομαι/-μένος
		//λύ-νω/λύ-νομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr711a")||termTxCpt_sRule.equals("rlElnTrmVbr711b")||termTxCpt_sRule.equals("rlElnTrmVbr711c") )
		{
			if (termTxCpt_sName.endsWith("νω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else //-νομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//λύ-νω-νομαι/έλυ-να
				if (termTxCpt_sRule.equals("rlElnTrmVbr711a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr711a: -νω-νομαι/-σα-θηκα/-μένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr711b: -νω-νομαι/-σα-θηκα/-μένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
					 s+"νω," +s+"νεις," +s+"νει," +s+"νουμε|" +s+"νομε," +s+"νετε," +s+"νουν|"+s+"νουνε#"
					+s+"νε#"
					+"έ"+s2+"να," +"έ"+s2+"νες," +"έ"+s2+"νε," +s+"ναμε," +s+"νατε,έ" +s2+"ναν|"+s+"νανε#"
					+"έ"+s2+"σα," +"έ"+s2+"σες," +"έ"+s2+"σε," +s+"σαμε," +s+"σατε,έ" +s2+"σαν|"+s+"σανε#"
					+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
					+s+"σε#"
					+s+"νοντας#";
			}
			else {
				//δαγκά-νω/δάγκα-σα
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr711a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr711a: -νω-νομαι/-σα-θηκα/-μένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr711b: -νω-νομαι/-σα-θηκα/-μένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
					 s+"νω," +s+"νεις," +s+"νει," +s+"νουμε|" +s+"νομε," +s+"νετε," +s+"νουν|"+s+"νουνε#"
					+s3+"νε#"
					+s3+"να," +s3+"νες," +s3+"νε," +s+"ναμε," +s+"νατε," +s3+"ναν|"+s+"νανε#"
					+s3+"σα," +s3+"σες," +s3+"σε," +s+"σαμε," +s+"σατε," +s3+"σαν|"+s+"σανε#"
					+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
					+s3+"σε"
					+s+"νοντας#";
			}
			nm2=
			 s+"νομαι," +s+"νεσαι," +s+"νεται," +s2+"νόμαστε," +s+"νεστε," +s+"νονται#"
			+s+"νου#"
			+s2+"νόμουνα," +s2+"νόσουν," +s2+"νόταν|"+s2+"νότανε," +s2+"νόμαστε," +s2+"νόσαστε," +s+"νονταν|"+s+"νόντανε#"
			+s+"θηκα," +s+"θηκες," +s+"θηκε," +s2+"θήκαμε," +s2+"θήκατε," +s+"θηκαν|"+s+"θήκανε#"
			+s2+"θώ," +s2+"θείς," +s2+"θεί," +s2+"θούμε," +s2+"θείτε," +s2+"θούν|"+s2+"θούνε#"
			+s+"σου#"
			+s2+"μένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr711c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr711c: -νω-νομαι/-σα-θηκα/-μένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr711a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-σα-θηκα/-ω-ομαι/-μένος
		//ιδρύω/ιδρύομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr712a")||termTxCpt_sRule.equals("rlElnTrmVbr712b")||termTxCpt_sRule.equals("rlElnTrmVbr712c") )
		{
			if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else //-ομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//λύ-ω/έλυ-σα
				if (termTxCpt_sRule.equals("rlElnTrmVbr712a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr712a: -ω-ομαι/-σα-θηκα/-μένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr712b: -ω-ομαι/-σα-θηκα/-μένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s+"ε#"
					+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε,έ" +s2+"αν|"+s+"ανε#"
					+"έ"+s2+"σα," +"έ"+s2+"σες," +"έ"+s2+"σε," +s+"σαμε," +s+"σατε,έ" +s2+"σαν|"+s+"σανε#"
					+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
					+s+"σε#"
					+s+"οντας#";
			}
			else {
				//ιδρύ-ω/ίδρυ-σα
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr712a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr712a: -ω-ομαι/-σα-θηκα/-μένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr712b: -ω-ομαι/-σα-θηκα/-μένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s3+"ε#"
					+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
					+s3+"σα," +s3+"σες," +s3+"σε," +s+"σαμε," +s+"σατε," +s3+"σαν|"+s+"σανε#"
					+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
					+s3+"σε#"
					+s+"οντας#";
			}
			nm2=
			 s+"ομαι," +s+"εσαι," +s+"εται," +s2+"όμαστε," +s+"εστε," +s+"ονται#"
			+s+"ου#"
			+s2+"όμουνα," +s2+"όσουν," +s2+"όταν|"+s2+"ότανε," +s2+"όμαστε," +s2+"όσαστε," +s+"ονταν|"+s+"όντανε#"
			+s+"θηκα," +s+"θηκες," +s+"θηκε," +s2+"θήκαμε," +s2+"θήκατε," +s+"θηκαν|"+s+"θήκανε#"
			+s2+"θώ," +s2+"θείς," +s2+"θεί," +s2+"θούμε," +s2+"θείτε," +s2+"θούν|"+s2+"θούνε#"
			+s+"σου#"
			+s2+"μένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr712c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr712c: -ω-ομαι/-σα-θηκα/-μένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr712a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-εσα-έθηκα/-ώ&άω-ιέμαι/-εμένος
		//βαράω/βαριέμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr731a")||termTxCpt_sRule.equals("rlElnTrmVbr731b")||termTxCpt_sRule.equals("rlElnTrmVbr731c") )
		{
			if (termTxCpt_sName.endsWith("άω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else //-ιέμαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//π-άω-έπ-εσα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr731a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr731a: -ώ&άω-ιέμαι/-εσα-έθηκα/-εμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr731b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr731b: -ώ&άω-ιέμαι/-εσα-έθηκα/-εμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"άω," +s+"ας," +s+"α|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ουν|"+s+"αν|"+s+"άνε#"
				+s+"ά#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"εσα," +"έ"+s+"εσες," +"έ"+s+"εσε," +s+"έσαμε," +s+"έσατε," +"έ"+s+"εσαν#"
				+s+"έσω," +s+"έσεις," +s+"έσει," +s+"έσουμε|"+s+"έσομε," +s+"έσετε," +s+"έσουν|"+s+"έσουνε#"
				+s+"έσε#"
				+s+"ώντας#";
			}
			else {
				//βαρ-άω|ώ/βάρ-εσα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr731a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr731a: -ώ&άω-ιέμαι/-εσα-έθηκα/-εμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr731b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr731b: -ώ&άω-ιέμαι/-εσα-έθηκα/-εμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s2+"α#"
				+s+"ούσα|"+s2+"αγα," +s+"ούσες|"+s2+"αγες," +s+"ούσε|"+s2+"αγε," +s+"ούσαμε|"+s+"άγαμε," +s+"ούσατε|"+s+"άγατε," +s+"ούσαν|"+s+"ούσανε|"+s+"άγανε#"
				+s2+"εσα," +s2+"εσες," +s2+"εσε," +s+"έσαμε," +s+"έσατε," +s2+"εσαν#"
				+s+"έσω," +s+"έσεις," +s+"έσει," +s+"έσουμε|"+s+"έσομε," +s+"έσετε," +s+"έσουν|"+s+"έσουνε#"
				+s2+"εσε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ιέμαι," +s+"ιέσαι," +s+"ιέται," +s+"ιόμαστε," +s+"ιέστε," +s+"ιούνται#"
				+"-#"
				+s+"ιόμουνα," +s+"ιόσουν," +s+"ιόταν|"+s+"ιότανε," +s+"ιόμαστε," +s+"ιόσαστε," +s+"ιόνταν|"+s+"ιόντανε#"
				+s+"έθηκα," +s+"έθηκες," +s+"έθηκε," +s+"εθήκαμε," +s+"εθήκατε," +s+"έθηκαν|"+s+"εθήκανε#"
				+s+"εθώ," +s+"εθείς," +s+"εθεί," +s+"εθούμε," +s+"εθείτε," +s+"εθούν#"
				+s+"έσου#"
				+s+"εμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr731c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr731c: -ώ&άω-ιέμαι/-εσα-έθηκα/-εμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr731a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-εσα-έθηκα/-ώ-ούμαι/-εμένος
		//αφαιρώ/αφαιρούμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr732a")||termTxCpt_sRule.equals("rlElnTrmVbr732b")||termTxCpt_sRule.equals("rlElnTrmVbr732c") )
		{
			if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ούμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//ζ-ώ/έ-ζ-εσα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr732a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr732a: -ώ-ούμαι/-εσα-έθηκα/-εμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr732b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr732b: -ώ-ούμαι/-εσα-έθηκα/-εμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ούμε," +s+"είτε," +s+"ούν|"+s+"ούνε#"
				+s+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"εσα," +"έ"+s+"εσες," +"έ"+s+"εσε," +s+"έσαμε," +s+"έσατε," +"έ"+s+"εσαν#"
				+s+"έσω," +s+"έσεις," +s+"έσει," +s+"έσουμε|"+s+"έσομε," +s+"έσετε," +s+"έσουν|"+s+"έσουνε#"
				+s+"έσε#"
				+s+"ώντας#";
			}
			else {
				//αφαιρ-ώ/αφαίρ-εσα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr732a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr732a: -ώ-ούμαι/-εσα-έθηκα/-εμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr732b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr732b: -ώ-ούμαι/-εσα-έθηκα/-εμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ," +s+"είς," +s+"εί," +s+"ούμε," +s+"είτε," +s+"ούν|"+s+"ούνε#"
				+s2+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+s2+"εσα," +s2+"εσες," +s2+"εσε," +s+"έσαμε," +s+"έσατε," +s2+"εσαν#"
				+s+"έσω," +s+"έσεις," +s+"έσει," +s+"έσουμε|"+s+"έσομε," +s+"έσετε," +s+"έσουν|"+s+"έσουνε#"
				+s2+"εσε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ούμαι," +s+"είσαι," +s+"είται," +s+"ούμαστε," +s+"είστε," +s+"ούνται#"
				+"-#"
				//το λεξικό-ρημάτων Παγουλάτου έχει μόνο τον τύπο -ούμουν.
				//Θεωρώ ότι αυτός αρχα'ί'ζει. Το γλωσσικό μου αίσθημα θεωρεί πρωτεύον τον -ιόμουνα. 2001.04.09
				+s+"ούμουν|"+s+"ιόμουνα," +s+"ούσουν," +s+"ούνταν|"+s+"ούντανε," +s+"ούμαστε," +s+"ούσαστε," +s+"ούνταν|"+s+"ούντανε#"
				+s+"έθηκα," +s+"έθηκες," +s+"έθηκε," +s+"εθήκαμε," +s+"εθήκατε," +s+"έθηκαν|"+s+"εθήκανε#"
				+s+"εθώ," +s+"εθείς," +s+"εθεί," +s+"εθούμε," +s+"εθείτε," +s+"εθούν#"
				+s+"έσου#"
				+s+"εμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr732c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr732c: -ώ-ούμαι/-εσα-έθηκα/-εμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr732a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ησα-ήθηκα/-αίνω-αίνομαι
		//ανασταίνω/ανασταίνομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr741a")||termTxCpt_sRule.equals("rlElnTrmVbr741b")||termTxCpt_sRule.equals("rlElnTrmVbr741c") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else //-αίνομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-αίνω/έχ-ησα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr741a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr741a: -αίνω-αίνομαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr741b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr741b: -αίνω-αίνομαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"ησα," +"έ"+s+"ησες," +"έ"+s+"ησε," +s+"ήσαμε," +s+"ήσατε,έ" +s+"ησαν|"+s+"ήσανε#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|" +s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s+"ήσε#"
				+s+"αίνοντας#";
			}
			else {
				//ανασταίνω/ανάστησα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr741a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr741a: -αίνω-αίνομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr741b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr741b: -αίνω-αίνομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"ησα," +s3+"ησες," +s3+"ησε," +s+"ήσαμε," +s+"ήσατε," +s3+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|" +s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s3+"ησε#"
				+s+"αίνοντας#";
			}
			nm2=
				 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
				+s+"αίνου#"
				+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"αινόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
				+s+"ήθηκα," +s+"ήθηκες," +s+"ήθηκε," +s+"ηθήκαμε," +s+"ηθήκατε," +s+"ήθηκαν|"+s+"ηθήκανε#"
				+s+"ηθώ," +s+"ηθείς," +s+"ηθεί," +s+"ηθούμε," +s+"ηθείτε," +s+"ηθούν|"+s+"ηθούνε#"
				+s+"ήσου#"
				+s+"ημένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr741c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr741c: -αίνω-αίνομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr741a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ησα-ήθηκα/-άνω-άνομαι
		//αυξάνω/αυξάνομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr742a")||termTxCpt_sRule.equals("rlElnTrmVbr742b")||termTxCpt_sRule.equals("rlElnTrmVbr742c") )
		{
			if (termTxCpt_sName.endsWith("άνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,3);
			else //-άνομαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,6);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-άνω/έχ-ανα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr742a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr742a: -άνω-άνομαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr742b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr742b: -άνω-άνομαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"άνω," +s+"άνεις," +s+"άνει," +s+"άνουμε|" +s+"άνομε," +s+"άνετε," +s+"άνουν|"+s+"άνουνε#"
				+s+"άνε#"
				+"έ"+s+"ανα," +"έ"+s+"ανες," +"έ"+s+"ανε," +s+"άναμε," +s+"άνατε,έ" +s+"αναν|"+s+"άνανε#"
				+"έ"+s+"ησα," +"έ"+s+"ησες," +"έ"+s+"ησε," +s+"ήσαμε," +s+"ήσατε,έ" +s+"ησαν|"+s+"ήσανε#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|" +s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s+"ήσε#"
				+s+"άνοντας#";
			}
			else {
				//αυξάνω/αυξάνομαι
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr742a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr742a: -άνω-άνομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr742b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr742b: -άνω-άνομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"άνω," +s+"άνεις," +s+"άνει," +s+"άνουμε|" +s+"άνομε," +s+"άνετε," +s+"άνουν|"+s+"άνουνε#"
				+s3+"ανε#"
				+s3+"ανα," +s3+"ανες," +s3+"ανε," +s+"άναμε," +s+"άνατε," +s3+"αναν|"+s+"άνανε#"
				+s3+"ησα," +s3+"ησες," +s3+"ησε," +s+"ήσαμε," +s+"ήσατε," +s3+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|" +s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s3+"ησε#"
				+s+"άνοντας#";
			}
			nm2=
				 s+"άνομαι," +s+"άνεσαι," +s+"άνεται," +s+"ανόμαστε," +s+"άνεστε," +s+"άνονται#"
				+s+"άνου#"
				+s+"ανόμουνα," +s+"ανόσουν," +s+"ανόταν|"+s+"ανότανε," +s+"ανόμαστε," +s+"ανόσαστε," +s+"άνονταν|"+s+"ανόντανε#"
				+s+"ήθηκα," +s+"ήθηκες," +s+"ήθηκε," +s+"ηθήκαμε," +s+"ηθήκατε," +s+"ήθηκαν|"+s+"ηθήκανε#"
				+s+"ηθώ," +s+"ηθείς," +s+"ηθεί," +s+"ηθούμε," +s+"ηθείτε," +s+"ηθούν|"+s+"ηθούνε#"
				+s+"ήσου#"
				+s+"ημένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr742c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr742c: -άνω-άνομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr742a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ησα-ήθηκα/-ώ&άω-ιέμαι/-ημένος
		//αγαπάω/αγαπιέμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr743a")||termTxCpt_sRule.equals("rlElnTrmVbr743b")||termTxCpt_sRule.equals("rlElnTrmVbr743c") )
		{
			if (termTxCpt_sName.endsWith("άω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else //-ιέμαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//π-άω-έπ-ησα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr743a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr743a: -ώ&άω-ιέμαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr743b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr743b: -ώ&άω-ιέμαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"άω," +s+"ας," +s+"α|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ουν|"+s+"αν|"+s+"άνε#"
				+s+"ά#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"ησα," +"έ"+s+"ησες," +"έ"+s+"ησε," +s+"ήσαμε," +s+"ήσατε," +"έ"+s+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s+"ήσε#"
				+s+"ώντας#";
			}
			else {
				//αγαπάω/αγάπησα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr743a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr743a: -ώ&άω-ιέμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr743b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr743b: -ώ&άω-ιέμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s2+"α#"
				+s+"ούσα|"+s2+"αγα," +s+"ούσες|"+s2+"αγες," +s+"ούσε|"+s2+"αγε," +s+"ούσαμε|"+s+"άγαμε," +s+"ούσατε|"+s+"άγατε," +s+"ούσαν|"+s+"ούσανε|"+s+"άγανε#"
				+s2+"ησα," +s2+"ησες," +s2+"ησε," +s+"ήσαμε," +s+"ήσατε," +s2+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s2+"ησε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ιέμαι," +s+"ιέσαι," +s+"ιέται," +s+"ιόμαστε," +s+"ιέστε," +s+"ιούνται#"
				+"-#"
				+s+"ιόμουνα," +s+"ιόσουν," +s+"ιόταν|"+s+"ιότανε," +s+"ιόμαστε," +s+"ιόσαστε," +s+"ιόνταν|"+s+"ιόντανε#"
				+s+"ήθηκα," +s+"ήθηκες," +s+"ήθηκε," +s+"ηθήκαμε," +s+"ηθήκατε," +s+"ήθηκαν|"+s+"ηθήκανε#"
				+s+"ηθώ," +s+"ηθείς," +s+"ηθεί," +s+"ηθούμε," +s+"ηθείτε," +s+"ηθούν#"
				+s+"ήσου#"
				+s+"ημένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr743c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr743c: -ώ&άω-ιέμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr743a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ησα-ήθηκα/-ώ-ούμαι/-ημένος
		//θεωρώ/θεωρούμαι(θεωρείσαι)
		else if (termTxCpt_sRule.equals("rlElnTrmVbr744a")||termTxCpt_sRule.equals("rlElnTrmVbr744b")||termTxCpt_sRule.equals("rlElnTrmVbr744c") )
		{
			if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else //-ούμαι
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//ζω/έζησα
				if (termTxCpt_sRule.equals("rlElnTrmVbr744a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr744a: -ώ-ούμαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr744b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr744b: -ώ-ούμαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ούμε," +s+"είτε," +s+"ουν|"+s+"αν|"+s+"ούνε#"
				+s+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"ησα," +"έ"+s+"ησες," +"έ"+s+"ησε," +s+"ήσαμε," +s+"ήσατε," +"έ"+s+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s+"ήσε#"
				+s+"ώντας#";
			}
			else {
				//θεωρώ/θεωρούμαι
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr744a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr744a: -ώ-ούμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr744b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr744b: -ώ-ούμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ," +s+"είς," +s+"εί," +s+"ούμε," +s+"είτε," +s+"ούν|"+s+"ούνε#"
				+s2+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+s2+"ησα," +s2+"ησες," +s2+"ησε," +s+"ήσαμε," +s+"ήσατε," +s2+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s2+"ησε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ούμαι," +s+"είσαι," +s+"είται," +s+"ούμαστε," +s+"είστε," +s+"ούνται#"
				+"-#"
				+s+"ούμουν|"+s+"ιόμουνα," +s+"ούσουν," +s+"ούνταν|"+s+"ούντανε," +s+"ούμαστε," +s+"ούσαστε," +s+"ούνταν|"+s+"ούντανε#"
				+s+"ήθηκα," +s+"ήθηκες," +s+"ήθηκε," +s+"ηθήκαμε," +s+"ηθήκατε," +s+"ήθηκαν|"+s+"ηθήκανε#"
				+s+"ηθώ," +s+"ηθείς," +s+"ηθεί," +s+"ηθούμε," +s+"ηθείτε," +s+"ηθούν#"
				+s+"ήσου#"
				+s+"ημένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr744c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr744c: -ώ-ούμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr744a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}


//8)-σα-στηκα
		else if ( termTxCpt_sRule.equals("rlElnTrmVbr745a")||termTxCpt_sRule.equals("rlElnTrmVbr745b")||termTxCpt_sRule.equals("rlElnTrmVbr745c")
						||termTxCpt_sRule.equals("rlElnTrmVbr746a")||termTxCpt_sRule.equals("rlElnTrmVbr746b")||termTxCpt_sRule.equals("rlElnTrmVbr746c")
						||termTxCpt_sRule.equals("rlElnTrmVbr747a")||termTxCpt_sRule.equals("rlElnTrmVbr747b")||termTxCpt_sRule.equals("rlElnTrmVbr747c")
						||termTxCpt_sRule.equals("rlElnTrmVbr721a")||termTxCpt_sRule.equals("rlElnTrmVbr721b")||termTxCpt_sRule.equals("rlElnTrmVbr721c")
						||termTxCpt_sRule.equals("rlElnTrmVbr811a")||termTxCpt_sRule.equals("rlElnTrmVbr811b")||termTxCpt_sRule.equals("rlElnTrmVbr811c")
						||termTxCpt_sRule.equals("rlElnTrmVbr812a")||termTxCpt_sRule.equals("rlElnTrmVbr812b")||termTxCpt_sRule.equals("rlElnTrmVbr812c")
						||termTxCpt_sRule.equals("rlElnTrmVbr813a")||termTxCpt_sRule.equals("rlElnTrmVbr813b")||termTxCpt_sRule.equals("rlElnTrmVbr813c")
						||termTxCpt_sRule.equals("rlElnTrmVbr821a")||termTxCpt_sRule.equals("rlElnTrmVbr821b")||termTxCpt_sRule.equals("rlElnTrmVbr821c")
						||termTxCpt_sRule.equals("rlElnTrmVbr822a")||termTxCpt_sRule.equals("rlElnTrmVbr822b")||termTxCpt_sRule.equals("rlElnTrmVbr822c")
						||termTxCpt_sRule.equals("rlElnTrmVbr823a")||termTxCpt_sRule.equals("rlElnTrmVbr823b")||termTxCpt_sRule.equals("rlElnTrmVbr823c")
						||termTxCpt_sRule.equals("rlElnTrmVbr824a")||termTxCpt_sRule.equals("rlElnTrmVbr824b")||termTxCpt_sRule.equals("rlElnTrmVbr824c")
						||termTxCpt_sRule.equals("rlElnTrmVbr825a")||termTxCpt_sRule.equals("rlElnTrmVbr825b")||termTxCpt_sRule.equals("rlElnTrmVbr825c")
						||termTxCpt_sRule.equals("rlElnTrmVbr832a")||termTxCpt_sRule.equals("rlElnTrmVbr832b")||termTxCpt_sRule.equals("rlElnTrmVbr832c")
						||termTxCpt_sRule.equals("rlElnTrmVbr841a")||termTxCpt_sRule.equals("rlElnTrmVbr841b")||termTxCpt_sRule.equals("rlElnTrmVbr841c")
						||termTxCpt_sRule.equals("rlElnTrmVbr842a")||termTxCpt_sRule.equals("rlElnTrmVbr842b")||termTxCpt_sRule.equals("rlElnTrmVbr842c")
						||termTxCpt_sRule.equals("rlElnTrmVbr843b")
						||termTxCpt_sRule.equals("rlElnTrmVbr844a")||termTxCpt_sRule.equals("rlElnTrmVbr844b")||termTxCpt_sRule.equals("rlElnTrmVbr844c")
						||termTxCpt_sRule.equals("rlElnTrmVbr845a")||termTxCpt_sRule.equals("rlElnTrmVbr845b")||termTxCpt_sRule.equals("rlElnTrmVbr845c")
						)
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly2(termTxCpt_sRule);


//9) -ψα-φτηκα
		else if ( termTxCpt_sRule.equals("rlElnTrmVbr921a")||termTxCpt_sRule.equals("rlElnTrmVbr921b")||termTxCpt_sRule.equals("rlElnTrmVbr921c")
						||termTxCpt_sRule.equals("rlElnTrmVbr912a")||termTxCpt_sRule.equals("rlElnTrmVbr912b")||termTxCpt_sRule.equals("rlElnTrmVbr912c")
						||termTxCpt_sRule.equals("rlElnTrmVbr911a")||termTxCpt_sRule.equals("rlElnTrmVbr911b")||termTxCpt_sRule.equals("rlElnTrmVbr911c")
						||termTxCpt_sRule.equals("rlElnTrmVbr913a")||termTxCpt_sRule.equals("rlElnTrmVbr913b")||termTxCpt_sRule.equals("rlElnTrmVbr913c")
						)
			termTxCpt_sAll=getTermsOfTxConceptIfRuleOnly2(termTxCpt_sRule);

		else if (termTxCpt_sRule.equals("")||termTxCpt_sRule.equals("none"))
		{
			note="χωρίς κανόνας-κλίσης.";
			termTxCpt_sAll=termTxCpt_sName+",-";
		}

		else {
			//not a know type. Insert them manually.
			String	result = JOptionPane.showInputDialog(null,
												"Το πρόγραμμα ΔΕΝ έχει αυτόν τον κανόνα : "+termTxCpt_sRule
												+"\nΓράψε τις μορφές μόνος σου!!!");
			if(result != null)
			{
				termTxCpt_sAll=result;
			}
		}

		//άν είναι πολυλεκτικό με επίρημα, βάλε στην αρχή κάθε τύπου το επίρρημα.
//		if (prefix.length()>0)
//		{
//			StringTokenizer parser = new StringTokenizer(termTxCpt_sAll, "#");
//			termTxCpt_sAll="";
//			while (parser.hasMoreTokens())
//			{
//				String st=(String)parser.nextElement();
//				StringTokenizer parser2 = new StringTokenizer(st, ",");
//				while (parser2.hasMoreTokens())
//				{
//					String type=prefix+parser2.nextElement();
//					if (type.startsWith("παραέ") )
//						type="παρά" +getLastLettersIfPrefix(type,6); //2001.04.02 "έ-"
//					termTxCpt_sAll=termTxCpt_sAll +type +",";
//				}
//				termTxCpt_sAll=termTxCpt_sAll+"#";
//			}
//		}

		return termTxCpt_sAll;
	}//getTermsOfTxConceptIfRuleOnly.


	/**
	 * I created this method because an error occured in previous method:
	 * too much bytes for a method.!!!
	 * @modified 2004.11.03
	 * @since 2001.04.08 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getTermsOfTxConceptIfRuleOnly2(String termTxCpt_sRule)
	{
		String s="",s2="",s3="",s4="",s5="",s6="";
		String termTxCpt_sAll="", nm2="";
		boolean afksisi;

		//-ησα-ήθηκα/-ώ-ούμαι/-ημένος
		//λυπώ/λυπούμαι(λυπάσαι)
		if (termTxCpt_sRule.equals("rlElnTrmVbr745a")||termTxCpt_sRule.equals("rlElnTrmVbr745b")||termTxCpt_sRule.equals("rlElnTrmVbr745c") )
		{
			if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ούμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);
			else if (termTxCpt_sName.endsWith("άμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//ζω/έζησα
				if (termTxCpt_sRule.equals("rlElnTrmVbr745a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr745a: -ώ-ούμαι&άμαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr745b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr745b: -ώ-ούμαι&άμαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ούμε," +s+"είτε," +s+"ουν|"+s+"αν|"+s+"ούνε#"
				+s+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"ησα," +"έ"+s+"ησες," +"έ"+s+"ησε," +s+"ήσαμε," +s+"ήσατε," +"έ"+s+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s+"ήσε#"
				+s+"ώντας#";
			}
			else {
				//λυπώ/λυπάμαι
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr745a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr745a: -ώ-ούμαι&άμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr745b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr745b: -ώ-ούμαι&άμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ," +s+"είς," +s+"εί," +s+"ούμε," +s+"είτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s2+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+s2+"ησα," +s2+"ησες," +s2+"ησε," +s+"ήσαμε," +s+"ήσατε," +s2+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s2+"ησε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ούμαι|"+s+"άμαι," +s+"άσαι," +s+"άται," +s+"ούμαστε," +s+"άστε," +s+"ούνται#"
				+"-#"
				+s+"όμουνα|"+s+"όμουνα," +s+"όσουν," +s+"όταν|"+s+"ότανε," +s+"όμαστε," +s+"όσαστε," +s+"όνταν|"+s+"όντανε|"+s+"ούνταν|"+s+"ούντανε|"+s+"όντουσαν#"
				+s+"ήθηκα," +s+"ήθηκες," +s+"ήθηκε," +s+"ηθήκαμε," +s+"ηθήκατε," +s+"ήθηκαν|"+s+"ηθήκανε#"
				+s+"ηθώ," +s+"ηθείς," +s+"ηθεί," +s+"ηθούμε," +s+"ηθείτε," +s+"ηθούν#"
				+s+"ήσου#"
				+s+"ημένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr745c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr745c: -ώ-ούμαι&άμαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr745a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ησα-ήθηκα/-ω-ομαι/-μένος
		//δέω/δέομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr746a")||termTxCpt_sRule.equals("rlElnTrmVbr746b")||termTxCpt_sRule.equals("rlElnTrmVbr746c") )
		{
			if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//δέ-ω/έ-δε-α/δέ-ησα
				if (termTxCpt_sRule.equals("rlElnTrmVbr746a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr746a: -ω-ομαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr746b: -ω-ομαι/-ησα-ήθηκα/-ημένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s+"ε#"
					+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε,έ" +s2+"αν|"+s+"ανε#"
					+s+"ησα," +s+"ησες," +s+"ησε," +s2+"ήσαμε," +s2+"ήσατε," +s+"ησαν|"+s2+"ήσανε#"
					+s2+"ήσω," +s2+"ήσεις," +s2+"ήσει," +s2+"ήσουμε|" +s2+"ήσομε," +s2+"ήσετε," +s2+"ήσουν|"+s2+"ήσουνε#"
					+s+"ησε#"
					+s+"οντας#";
			}
			else {
				//παραδέομαι
				if (termTxCpt_sRule.equals("rlElnTrmVbr746a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr746a: -ω-ομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr746b: -ω-ομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=ε";
				s3=greekTonosDecrease(s);
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s3+"ε#"
					+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
					+s+"ησα," +s+"ησες," +s+"ησε," +s2+"ήσαμε," +s2+"ήσατε," +s+"ησαν|"+s2+"ήσανε#"
					+s2+"ήσω," +s2+"ήσεις," +s2+"ήσει," +s2+"ήσουμε|" +s2+"ήσομε," +s2+"ήσετε," +s2+"ήσουν|"+s2+"ήσουνε#"
					+s+"ησε#"
					+s+"οντας#";
			}
			nm2=
			 s+"ομαι," +s+"εσαι," +s+"εται," +s2+"όμαστε," +s+"εστε," +s+"ονται#"
			+s+"ου#"
			+s2+"όμουνα," +s2+"όσουν," +s2+"όταν|"+s2+"ότανε," +s2+"όμαστε," +s2+"όσαστε," +s+"ονταν|"+s+"όντανε#"
			+s2+"ήθηκα," +s2+"ήθηκες," +s2+"ήθηκε," +s2+"ηθήκαμε," +s2+"ηθήκατε," +s2+"ήθηκαν|"+s2+"ηθήκανε#"
			+s2+"ηθώ," +s2+"ηθείς," +s2+"ηθεί," +s2+"ηθούμε," +s2+"ηθείτε," +s2+"ηθούν|"+s2+"ηθούνε#"
			+s2+"ήσου#"
			+s2+"ημένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr746c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr746c: -ω-ομαι/-ησα-ήθηκα/-ημένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr746a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-υσα-ύθηκα/-αίνω-αίνομαι
		//αρταίνω/αρταίνομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr747a")||termTxCpt_sRule.equals("rlElnTrmVbr747b")||termTxCpt_sRule.equals("rlElnTrmVbr747c") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else if (termTxCpt_sName.endsWith("αίνομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-αίνω/έχ-υσα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr747a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr747a: -αίνω-αίνομαι/-υσα-ύθηκα/-υμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr747b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr747b: -αίνω-αίνομαι/-υσα-ύθηκα/-υμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"υσα," +"έ"+s+"υσες," +"έ"+s+"υσε," +s+"ύσαμε," +s+"ύσατε,έ" +s+"υσαν|"+s+"ύσανε#"
				+s+"ύσω," +s+"ύσεις," +s+"ύσει," +s+"ύσουμε|" +s+"ύσομε," +s+"ύσετε," +s+"ύσουν|"+s+"ύσουνε#"
				+s+"ύσε#"
				+s+"αίνοντας#";
			}
			else {
				//ανασταίνω/ανάστυσα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr747a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr747a: -αίνω-αίνομαι/-υσα-ύθηκα/-υμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr747b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr747b: -αίνω-αίνομαι/-υσα-ύθηκα/-υμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"υσα," +s3+"υσες," +s3+"υσε," +s+"ύσαμε," +s+"ύσατε," +s3+"υσαν#"
				+s+"ύσω," +s+"ύσεις," +s+"ύσει," +s+"ύσουμε|" +s+"ύσομε," +s+"ύσετε," +s+"ύσουν|"+s+"ύσουνε#"
				+s3+"υσε#"
				+s+"αίνοντας#";
			}
			nm2=
				 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
				+s+"αίνου#"
				+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"αινόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
				+s+"ύθηκα," +s+"ύθηκες," +s+"ύθηκε," +s+"υθήκαμε," +s+"υθήκατε," +s+"ύθηκαν|"+s+"υθήκανε#"
				+s+"υθώ," +s+"υθείς," +s+"υθεί," +s+"υθούμε," +s+"υθείτε," +s+"υθούν|"+s+"υθούνε#"
				+s+"ύσου#"
				+s+"υμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr747c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr747c: -αίνω-αίνομαι/-υσα-ύθηκα/-υμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr747a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ασα-άθηκα/-αίνω-αίνομαι/-αμένος
		//αποσταίνω
		else if (termTxCpt_sRule.equals("rlElnTrmVbr721a")||termTxCpt_sRule.equals("rlElnTrmVbr721b")||termTxCpt_sRule.equals("rlElnTrmVbr721c") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else if (termTxCpt_sName.endsWith("αίνομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-αίνω/έχ-ασα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr721a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr721a: -αίνω-αίνομαι/-ασα-άθηκα/-αμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr721b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr721b: -αίνω-αίνομαι/-ασα-άθηκα/-αμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"ασα," +"έ"+s+"ασες," +"έ"+s+"ασε," +s+"άσαμε," +s+"άσατε,έ" +s+"ασαν|"+s+"άσανε#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|" +s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s+"άσε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			else {
				//χορτ-αίνω/χόρτ-ασα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr721a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr721a: -αίνω-αίνομαι/-ασα-άθηκα/-αμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr721b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr721b: -αίνω-αίνομαι/-ασα-άθηκα/-αμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"ασα," +s3+"ασες," +s3+"ασε," +s+"άσαμε," +s+"άσατε," +s3+"ασαν#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|" +s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s3+"ασε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			nm2=
				 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
				+s+"αίνου#"
				+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"αινόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
				+s+"άθηκα," +s+"άθηκες," +s+"άθηκε," +s+"αθήκαμε," +s+"αθήκατε," +s+"άθηκαν|"+s+"αθήκανε#"
				+s+"αθώ," +s+"αθείς," +s+"αθεί," +s+"αθούμε," +s+"αθείτε," +s+"αθούν|"+s+"αθούνε#"
				+s+"άσου#"
				+s+"αμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr721c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr721c: -αίνω-αίνομαι/-ασα-άθηκα/-αμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr721a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}


//8)-σα-στηκα

		//-θω-θομαι/-σα-στηκα/-σμένος
		//-νω
		//πλάθω/πλάθομαι, κλίνω/κλίνομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr811a")||termTxCpt_sRule.equals("rlElnTrmVbr811b")||termTxCpt_sRule.equals("rlElnTrmVbr811c") )
		{
			//η κατάληξη χωρίς το ω
			String c="";
			if (termTxCpt_sName.endsWith("ω"))
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-2,termTxCpt_sName.length()-1);
			}
			else if (termTxCpt_sName.endsWith("ομαι"))
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-5,termTxCpt_sName.length()-4);
			}
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//κλί-νω/έκλισα
				if (termTxCpt_sRule.equals("rlElnTrmVbr811a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr811a: -"+c+"ω-"+c+"ομαι/-σα-στηκα/-σμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr811b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr811b: -"+c+"ω-"+c+"ομαι/-σα-στηκα/-σμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s+c+"ε#"
				+"έ"+s2+c+"α," +"έ"+s2+c+"ες," +"έ"+s2+c+"ε," +s+c+"αμε," +s+c+"ατε,έ" +s2+c+"αν#"
				+"έ"+s2+"σα," +"έ"+s2+"σες," +"έ"+s2+"σε," +s+"σαμε," +s+"σατε,έ" +s2+"σαν#"
				+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
				+s+"σε#"
				+s+c+"οντας#";
			}
			else {
				//καλοπιάνω/καλοπιάνομαι
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr811a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr811a: -"+c+"ω-"+c+"ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr811b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr811b: -"+c+"ω-"+c+"ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s3+c+"ε#"
				+s3+c+"α," +s3+c+"ες," +s3+c+"ε," +s+c+"αμε," +s+c+"ατε," +s3+c+"αν|"+s+c+"ανε#"
				+s3+"σα," +s3+"σες," +s3+"σε," +s+"σαμε," +s+"σατε," +s3+"σαν#"
				+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
				+s3+"σε#"
				+s+c+"οντας#";
			}
			nm2=
				 s+c+"ομαι," +s+c+"εσαι," +s+c+"εται," +s2+c+"όμαστε," +s+c+"εστε," +s+c+"ονται#"
				+s+c+"ου#"
				+s2+c+"όμουνα," +s2+c+"όσουν," +s2+c+"όταν|"+s2+c+"ότανε," +s2+c+"όμαστε," +s2+c+"όσαστε," +s+c+"ονταν|"+s2+c+"όντανε#"
				+s+"στηκα," +s+"στηκες," +s+"στηκε," +s2+"στήκαμε," +s2+"στήκατε," +s+"στηκαν|"+s2+"στήκανε#"
				+s2+"στώ," +s2+"στείς," +s2+"στεί," +s2+"στούμε," +s2+"στείτε," +s2+"στούν|"+s2+"στούνε#"
				+s+"σου#"
				+s2+"σμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr811c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr811c: -"+c+"ω-"+c+"ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr811a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//(γ)ω-(γ)ομαι/-σα-στηκα/-σμένος
		//ακούω&ακούγω
		else if (termTxCpt_sRule.equals("rlElnTrmVbr812a")||termTxCpt_sRule.equals("rlElnTrmVbr812b")||termTxCpt_sRule.equals("rlElnTrmVbr812c") )
		{
			if (termTxCpt_sName.endsWith("γω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("γομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);
			else if (termTxCpt_sName.endsWith("ομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//φλά-ω-γομαι
				if (termTxCpt_sRule.equals("rlElnTrmVbr812a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr812a: -(γ)ω-(γ)ομαι/-σα-στηκα/-σμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr812b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr812b: -(γ)ω-(γ)ομαι/-σα-στηκα/-σμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"γω," +s+"ς|"+s+"γεις," +s+"ει|"+s+"γει," +s+"με," +s+"τε," +s+"ν|"+s+"νε#"
				+s+"ε|"+s+"γε#"
				+"έ"+s2+"α|"+"έ"+s2+"γα," +"έ"+s2+"ες|"+"έ"+s2+"γες," +"έ"+s2+"ε|"+"έ"+s2+"γε," +s+"αμε|"+s+"γαμε," +s+"ατε|"+s+"γατε," +"έ"+s2+"αν|"+"έ"+s2+"γαν#"
				+"έ"+s2+"σα," +"έ"+s2+"σες," +"έ"+s2+"σε," +s+"σαμε," +s+"σατε,έ" +s2+"σαν#"
				+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
				+s+"σε#"
				+s+"οντας|"+s+"γοντας#";
			}
			else {
				//ακούω/άκουσα
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr812a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr812a: -(γ)ω-(γ)ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr812b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr812b: -(γ)ω-(γ)ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"γω," +s+"ς|"+s+"γεις," +s+"ει|"+s+"γει," +s+"με," +s+"τε," +s+"ν|"+s+"νε#"
				+s3+"ε|"+s3+"γε#"
				+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
				+s3+"σα," +s3+"σες," +s3+"σε," +s+"σαμε," +s+"σατε," +s3+"σαν#"
				+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
				+s3+"σε#"
				+s+"οντας|"+s+"γοντας#";
			}
			nm2=
				 s+"ομαι|"+s+"γομαι," +s+"εσαι|"+s+"γεσαι," +s+"εται|"+s+"γεται," +s2+"όμαστε|"+s2+"γόμαστε," +s+"εστε|"+s+"γεστε," +s+"ονται|"+s+"γονται#"
				+s+"ου|"+s+"γου#"
				+s2+"όμουνα|"+s2+"γόμουνα," +s2+"όσουν|"+s2+"γόσουν," +s2+"όταν|"+s2+"γόταν|" +s2+"ότανε|"+s2+"γότανε," +s2+"όμαστε|"+s2+"γόμαστε," +s2+"όσαστε|"+s2+"γόσαστε," +s+"ονταν|"+s+"γονταν|"+s2+"όντανε|"+s2+"όντανε#"
				+s+"στηκα," +s+"στηκες," +s+"στηκε," +s2+"στήκαμε," +s2+"στήκατε," +s+"στηκαν|"+s2+"στήκανε#"
				+s2+"στώ," +s2+"στείς," +s2+"στεί," +s2+"στούμε," +s2+"στείτε," +s2+"στούν|"+s2+"στούνε#"
				+s+"σου#"
				+s2+"σμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr812c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr812c: -(γ)ω-(γ)ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr812a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ω-ομαι/-σα-στηκα/-σμένος
		//αποκλεί-ω
		else if (termTxCpt_sRule.equals("rlElnTrmVbr813a")||termTxCpt_sRule.equals("rlElnTrmVbr813b")||termTxCpt_sRule.equals("rlElnTrmVbr813c") )
		{
			if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//κλεί-ω/έκλει-σα
				if (termTxCpt_sRule.equals("rlElnTrmVbr813a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr813a: -ω-ομαι/-σα-στηκα/-σμένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr813b: -ω-ομαι/-σα-στηκα/-σμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s+"ε#"
					+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε,έ" +s2+"αν|"+s+"ανε#"
					+"έ"+s2+"σα," +"έ"+s2+"σες," +"έ"+s2+"σε," +s+"σαμε," +s+"σατε,έ" +s2+"σαν|"+s+"σανε#"
					+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
					+s+"σε#"
					+s+"οντας#";
			}
			else {
				//αποκλείω/απόκλει-σα
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr813a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr813a: -ω-ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr813b: -ω-ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s3+"ε#"
					+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
					+s3+"σα," +s3+"σες," +s3+"σε," +s+"σαμε," +s+"σατε," +s3+"σαν|"+s+"σανε#"
					+s+"σω," +s+"σεις," +s+"σει," +s+"σουμε|" +s+"σομε," +s+"σετε," +s+"σουν|"+s+"σουνε#"
					+s3+"σε#"
					+s+"οντας#";
			}
			nm2=
			 s+"ομαι," +s+"εσαι," +s+"εται," +s2+"όμαστε," +s+"εστε," +s+"ονται#"
			+s+"ου#"
			+s2+"όμουνα," +s2+"όσουν," +s2+"όταν|"+s2+"ότανε," +s2+"όμαστε," +s2+"όσαστε," +s+"ονταν|"+s+"όντανε#"
			+s+"στηκα," +s+"στηκες," +s+"στηκε," +s2+"στήκαμε," +s2+"στήκατε," +s+"στηκαν|"+s+"στήκανε#"
			+s2+"στώ," +s2+"στείς," +s2+"στεί," +s2+"στούμε," +s2+"στείτε," +s2+"στούν|"+s2+"στούνε#"
			+s+"σου#"
			+s2+"σμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr813c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr813c: -ω-ομαι/-σα-στηκα/-σμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr813a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ασα-άστηκα/-αίνω-αίνομαι
		//χορταίνω/χορταίνομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr821a")||termTxCpt_sRule.equals("rlElnTrmVbr821b")||termTxCpt_sRule.equals("rlElnTrmVbr821c") )
		{
			if (termTxCpt_sName.endsWith("αίνω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else if (termTxCpt_sName.endsWith("αίνομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,7);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-αίνω/έχ-ασα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr821a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr821a: -αίνω-αίνομαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr821b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr821b: -αίνω-αίνομαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s+"αίνε#"
				+"έ"+s+"αινα," +"έ"+s+"αινες," +"έ"+s+"αινε," +s+"αίναμε," +s+"αίνατε,έ" +s+"αιναν|"+s+"αίνανε#"
				+"έ"+s+"ασα," +"έ"+s+"ασες," +"έ"+s+"ασε," +s+"άσαμε," +s+"άσατε,έ" +s+"ασαν|"+s+"άσανε#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|" +s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s+"άσε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			else {
				//χορτ-αίνω/χόρτ-ασα
				s3=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr821a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr821a: -αίνω-αίνομαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr821b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr821b: -αίνω-αίνομαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίνω," +s+"αίνεις," +s+"αίνει," +s+"αίνουμε|" +s+"αίνομε," +s+"αίνετε," +s+"αίνουν|"+s+"αίνουνε#"
				+s3+"αινε#"
				+s3+"αινα," +s3+"αινες," +s3+"αινε," +s+"αίναμε," +s+"αίνατε," +s3+"αιναν|"+s+"αίνανε#"
				+s3+"ασα," +s3+"ασες," +s3+"ασε," +s+"άσαμε," +s+"άσατε," +s3+"ασαν#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|" +s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s3+"ασε#"
				+s+"αίνοντας|" +s+"άνοντας#";
			}
			nm2=
				 s+"αίνομαι," +s+"αίνεσαι," +s+"αίνεται," +s+"αινόμαστε," +s+"αίνεστε," +s+"αίνονται#"
				+s+"αίνου#"
				+s+"αινόμουνα," +s+"αινόσουν," +s+"αινόταν|"+s+"αινότανε," +s+"αινόμαστε," +s+"αινόσαστε," +s+"αίνονταν|"+s+"αινόντανε#"
				+s+"άστηκα," +s+"άστηκες," +s+"άστηκε," +s+"αστήκαμε," +s+"αστήκατε," +s+"άστηκαν|"+s+"αστήκανε#"
				+s+"αστώ," +s+"αστείς," +s+"αστεί," +s+"αστούμε," +s+"αστείτε," +s+"αστούν|"+s+"αστούνε#"
				+s+"άσου#"
				+s+"ασμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr821c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr821c: -αίνω-αίνομαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr821a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ασα-άστηκα/-ώ&άω-ιέμαι/-ασμένος
		//γελάω/γελιέμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr823a")||termTxCpt_sRule.equals("rlElnTrmVbr823b")||termTxCpt_sRule.equals("rlElnTrmVbr823c") )
		{
			if (termTxCpt_sName.endsWith("άω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ιέμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-άω-έχ-ασα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr823a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr823a: -ώ&άω-ιέμαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr823b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr823b: -ώ&άω-ιέμαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"άω," +s+"ας," +s+"α|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ουν|"+s+"αν|"+s+"άνε#"
				+s+"ά#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"ασα," +"έ"+s+"ασες," +"έ"+s+"ασε," +s+"άσαμε," +s+"άσατε," +"έ"+s+"ασαν#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|"+s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s+"άσε#"
				+s+"ώντας#";
			}
			else {
				//γελάω/γέλασα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr823a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr823a: -ώ&άω-ιέμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr823b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr823b: -ώ&άω-ιέμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s2+"α#"
				+s+"ούσα|"+s2+"αγα," +s+"ούσες|"+s2+"αγες," +s+"ούσε|"+s2+"αγε," +s+"ούσαμε|"+s+"άγαμε," +s+"ούσατε|"+s+"άγατε," +s+"ούσαν|"+s+"ούσανε|"+s+"άγανε#"
				+s2+"ασα," +s2+"ασες," +s2+"ασε," +s+"άσαμε," +s+"άσατε," +s2+"ασαν#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|"+s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s2+"ασε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ιέμαι," +s+"ιέσαι," +s+"ιέται," +s+"ιόμαστε," +s+"ιέστε," +s+"ιούνται#"
				+"-#"
				+s+"ιόμουνα," +s+"ιόσουν," +s+"ιόταν|"+s+"ιότανε," +s+"ιόμαστε," +s+"ιόσαστε," +s+"ιόνταν|"+s+"ιόντανε#"
				+s+"άστηκα," +s+"άστηκες," +s+"άστηκε," +s+"αστήκαμε," +s+"αστήκατε," +s+"άστηκαν|"+s+"αστήκανε#"
				+s+"αστώ," +s+"αστείς," +s+"αστεί," +s+"αστούμε," +s+"αστείτε," +s+"αστούν#"
				+s+"άσου#"
				+s+"ασμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr823c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr823c: -ώ&άω-ιέμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr823a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ασα-άστηκα/-νώ&νάω-νιέμαι/-ασμένος
		//περνάω/περνιέμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr822a")||termTxCpt_sRule.equals("rlElnTrmVbr822b")||termTxCpt_sRule.equals("rlElnTrmVbr822c") )
		{
			if (termTxCpt_sName.endsWith("νάω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,3);
			else if (termTxCpt_sName.endsWith("νώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("νιέμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,6);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//χ-νάω-έχ-ασα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr822a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr822a: -νώ&νάω-νιέμαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr822b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr822b: -νώ&νάω-νιέμαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"νω|"+s+"νάω," +s+"νας," +s+"να|"+s+"νάει," +s+"νούμε|"+s+"νάμε," +s+"νάτε," +s+"νουν|"+s+"ναν|"+s+"νάνε#"
				+s+"ά#"
				+s+"νούσα," +s+"νούσες," +s+"νούσε," +s+"νούσαμε," +s+"νούσατε," +s+"νούσαν|"+s+"νούσανε#"
				+"έ"+s+"ασα," +"έ"+s+"ασες," +"έ"+s+"ασε," +s+"άσαμε," +s+"άσατε," +"έ"+s+"ασαν#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|"+s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s+"άσε#"
				+s+"νώντας#";
			}
			else {
				//περ-νάω/πέρ-ασα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr822a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr822a: -νώ&νάω-νιέμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr822b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr822b: -νώ&νάω-νιέμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"νώ|"+s+"νάω," +s+"νάς," +s+"νά|"+s+"νάει," +s+"νούμε|"+s+"νάμε," +s+"νάτε," +s+"νούν|"+s+"νάν|"+s+"νάνε#"
				+s2+"να#"
				+s+"νούσα|"+s2+"ναγα," +s+"νούσες|"+s2+"ναγες," +s+"νούσε|"+s2+"ναγε," +s+"νούσαμε|"+s+"νάγαμε," +s+"νούσατε|"+s+"νάγατε," +s+"νούσαν|"+s+"νούσανε|"+s+"νάγανε#"
				+s2+"ασα," +s2+"ασες," +s2+"ασε," +s+"άσαμε," +s+"άσατε," +s2+"ασαν#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|"+s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s2+"ασε#"
				+s+"νώντας#";
			}
			nm2=
				 s+"νιέμαι," +s+"νιέσαι," +s+"νιέται," +s+"νιόμαστε," +s+"νιέστε," +s+"νιούνται#"
				+"-#"
				+s+"νιόμουνα," +s+"νιόσουν," +s+"νιόταν|"+s+"νιότανε," +s+"νιόμαστε," +s+"νιόσαστε," +s+"νιόνταν|"+s+"νιόντανε#"
				+s+"άστηκα," +s+"άστηκες," +s+"άστηκε," +s+"αστήκαμε," +s+"αστήκατε," +s+"άστηκαν|"+s+"αστήκανε#"
				+s+"αστώ," +s+"αστείς," +s+"αστεί," +s+"αστούμε," +s+"αστείτε," +s+"αστούν#"
				+s+"άσου#"
				+s+"ασμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr822c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr822c: -ώ&άω-ιέμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr822a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ασα-άστηκα/-ώ-ούμαι/-ασμένος
		//διαθλώ/διαθλούμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr824a")||termTxCpt_sRule.equals("rlElnTrmVbr824b")||termTxCpt_sRule.equals("rlElnTrmVbr824c") )
		{
			if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ούμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//ρ-ω/έρ-ασα
				if (termTxCpt_sRule.equals("rlElnTrmVbr824a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr824a: -ώ-ούμαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr824b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr824b: -ώ-ούμαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ούμε," +s+"είτε," +s+"ουν|"+s+"αν|"+s+"ούνε#"
				+s+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"ασα," +"έ"+s+"ασες," +"έ"+s+"ασε," +s+"άσαμε," +s+"άσατε," +"έ"+s+"ασαν#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|"+s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s+"άσε#"
				+s+"ώντας#";
			}
			else {
				//καλώ/καλούμαι
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr824a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr824a: -ώ-ούμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr824b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr824b: -ώ-ούμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ," +s+"είς," +s+"εί," +s+"ούμε," +s+"είτε," +s+"ούν|"+s+"ούνε#"
				+s2+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+s2+"ασα," +s2+"ασες," +s2+"ασε," +s+"άσαμε," +s+"άσατε," +s2+"ασαν#"
				+s+"άσω," +s+"άσεις," +s+"άσει," +s+"άσουμε|"+s+"άσομε," +s+"άσετε," +s+"άσουν|"+s+"άσουνε#"
				+s2+"ασε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ούμαι," +s+"είσαι," +s+"είται," +s+"ούμαστε," +s+"είστε," +s+"ούνται#"
				+"-#"
				+s+"όμουνα|"+s+"όμουν," +s+"ούσουν," +s+"ούνταν|"+s+"ούντανε," +s+"ούμαστε," +s+"ούσαστε," +s+"ούνταν|"+s+"ούντανε#"
				+s+"άστηκα," +s+"άστηκες," +s+"άστηκε," +s+"αστήκαμε," +s+"αστήκατε," +s+"άστηκαν|"+s+"αστήκανε#"
				+s+"αστώ," +s+"αστείς," +s+"αστεί," +s+"αστούμε," +s+"αστείτε," +s+"αστούν#"
				+s+"άσου#"
				+s+"ασμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr824c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr824c: -ώ-ούμαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr824a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ω-ομαι/-ασα-άστηκα/-ασμένος
		//σέβομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr825a")||termTxCpt_sRule.equals("rlElnTrmVbr825b")||termTxCpt_sRule.equals("rlElnTrmVbr825c") )
		{
			if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//σεβ-ω/σεβ-ασα
				if (termTxCpt_sRule.equals("rlElnTrmVbr825a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr825a: -ω-ομαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr825b: -ω-ομαι/-ασα-άστηκα/-ασμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s+"ε#"
					+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε,έ" +s2+"αν|"+s+"ανε#"
					+s+"ασα," +s+"ασες," +s+"ασε," +s2+"άσαμε," +s2+"άσατε," +s+"ασαν|"+s2+"άσανε#"
					+s2+"άσω," +s2+"άσεις," +s2+"άσει," +s2+"άσουμε|" +s2+"άσομε," +s2+"άσετε," +s2+"άσουν|"+s2+"άσουνε#"
					+s+"ασε#"
					+s+"οντας#";
			}
			else {
				//παρασέβομαι
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr825a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr825a: -ω-ομαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr825b: -ω-ομαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s3+"ε#"
					+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
					+s+"ασα," +s+"ασες," +s+"ασε," +s2+"άσαμε," +s2+"άσατε," +s+"ασαν|"+s2+"άσανε#"
					+s2+"άσω," +s2+"άσεις," +s2+"άσει," +s2+"άσουμε|" +s2+"άσομε," +s2+"άσετε," +s2+"άσουν|"+s2+"άσουνε#"
					+s+"ασε#"
					+s+"οντας#";
			}
			nm2=
			 s+"ομαι," +s+"εσαι," +s+"εται," +s2+"όμαστε," +s+"εστε," +s+"ονται#"
			+s+"ου#"
			+s2+"όμουνα," +s2+"όσουν," +s2+"όταν|"+s2+"ότανε," +s2+"όμαστε," +s2+"όσαστε," +s+"ονταν|"+s+"όντανε#"
			+s2+"άστηκα," +s2+"άστηκες," +s2+"άστηκε," +s2+"αστήκαμε," +s2+"αστήκατε," +s2+"άστηκαν|"+s2+"αστήκανε#"
			+s2+"αστώ," +s2+"αστείς," +s2+"αστεί," +s2+"αστούμε," +s2+"αστείτε," +s2+"αστούν|"+s2+"αστούνε#"
			+s2+"άσου#"
			+s2+"ασμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr825c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr825c: -ω-ομαι/-ασα-άστηκα/-ασμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr825a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-εσα-έστηκα/-ώ-ούμαι/-εσμένος
		//καλώ/καλούμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr832a")||termTxCpt_sRule.equals("rlElnTrmVbr832b")||termTxCpt_sRule.equals("rlElnTrmVbr832c") )
		{
			if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ούμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//ρ-ω/έρ-εσα
				if (termTxCpt_sRule.equals("rlElnTrmVbr832a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr832a: -ώ-ούμαι/-εσα-έστηκα/-εσμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr832b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr832b: -ώ-ούμαι/-εσα-έστηκα/-εσμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ούμε," +s+"είτε," +s+"ουν|"+s+"αν|"+s+"ούνε#"
				+s+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"εσα," +"έ"+s+"εσες," +"έ"+s+"εσε," +s+"έσαμε," +s+"έσατε," +"έ"+s+"εσαν#"
				+s+"έσω," +s+"έσεις," +s+"έσει," +s+"έσουμε|"+s+"έσομε," +s+"έσετε," +s+"έσουν|"+s+"έσουνε#"
				+s+"έσε#"
				+s+"ώντας#";
			}
			else {
				//καλώ/καλούμαι
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr832a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr832a: -ώ-ούμαι/-εσα-έστηκα/-εσμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr832b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr832b: -ώ-ούμαι/-εσα-έστηκα/-εσμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ," +s+"είς," +s+"εί," +s+"ούμε," +s+"είτε," +s+"ούν|"+s+"ούνε#"
				+s2+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+s2+"εσα," +s2+"εσες," +s2+"εσε," +s+"έσαμε," +s+"έσατε," +s2+"εσαν#"
				+s+"έσω," +s+"έσεις," +s+"έσει," +s+"έσουμε|"+s+"έσομε," +s+"έσετε," +s+"έσουν|"+s+"έσουνε#"
				+s2+"εσε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ούμαι," +s+"είσαι," +s+"είται," +s+"ούμαστε," +s+"είστε," +s+"ούνται#"
				+"-#"
				+s+"ούμουν," +s+"ούσουν," +s+"ούνταν|"+s+"ούντανε," +s+"ούμαστε," +s+"ούσαστε," +s+"ούνταν|"+s+"ούντανε#"
				+s+"έστηκα," +s+"έστηκες," +s+"έστηκε," +s+"εστήκαμε," +s+"εστήκατε," +s+"έστηκαν|"+s+"εστήκανε#"
				+s+"εστώ," +s+"εστείς," +s+"εστεί," +s+"εστούμε," +s+"εστείτε," +s+"εστούν#"
				+s+"έσου#"
				+s+"εσμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr832c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr832c: -ώ-ούμαι/-εσα-έστηκα/-εσμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr832a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ησα-ήστηκα/-ώ&άω-ιέμαι/-ησμένος
		//ζουλώ/ζουλιέμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr841a")||termTxCpt_sRule.equals("rlElnTrmVbr841b")||termTxCpt_sRule.equals("rlElnTrmVbr841c") )
		{
			if (termTxCpt_sName.endsWith("άω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ιέμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//ζ-άω-έζ-ησα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr841a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr841a: -ώ&άω-ιέμαι/-ησα-ήστηκα/-ησμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr841b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr841b: -ώ&άω-ιέμαι/-ησα-ήστηκα/-ησμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"άω," +s+"ας," +s+"α|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ουν|"+s+"αν|"+s+"άνε#"
				+s+"ά#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"ησα," +"έ"+s+"ησες," +"έ"+s+"ησε," +s+"ήσαμε," +s+"ήσατε," +"έ"+s+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s+"ήσε#"
				+s+"ώντας#";
			}
			else {
				//ζουλάω/ζούλησα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr841a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr841a: -ώ&άω-ιέμαι/-ησα-ήστηκα/-ησμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr841b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr841b: -ώ&άω-ιέμαι/-ησα-ήστηκα/-ησμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s2+"α#"
				+s+"ούσα|"+s2+"αγα," +s+"ούσες|"+s2+"αγες," +s+"ούσε|"+s2+"αγε," +s+"ούσαμε|"+s+"άγαμε," +s+"ούσατε|"+s+"άγατε," +s+"ούσαν|"+s+"ούσανε|"+s+"άγανε#"
				+s2+"ησα," +s2+"ησες," +s2+"ησε," +s+"ήσαμε," +s+"ήσατε," +s2+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s2+"ησε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ιέμαι," +s+"ιέσαι," +s+"ιέται," +s+"ιόμαστε," +s+"ιέστε," +s+"ιούνται#"
				+"-#"
				+s+"ιόμουνα," +s+"ιόσουν," +s+"ιόταν|"+s+"ιότανε," +s+"ιόμαστε," +s+"ιόσαστε," +s+"ιόνταν|"+s+"ιόντανε#"
				+s+"ήστηκα," +s+"ήστηκες," +s+"ήστηκε," +s+"ηστήκαμε," +s+"ηστήκατε," +s+"ήστηκαν|"+s+"ηστήκανε#"
				+s+"ηστώ," +s+"ηστείς," +s+"ηστεί," +s+"ηστούμε," +s+"ηστείτε," +s+"ηστούν#"
				+s+"ήσου#"
				+s+"ησμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr841c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr841c: -ώ&άω-ιέμαι/-ησα-ήστηκα/-ησμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr841a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ησα-ήστηκα/-ώ-ούμαι/-ησμένος
		//διαλαλώ/διαλαλούμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr842a")||termTxCpt_sRule.equals("rlElnTrmVbr842b")||termTxCpt_sRule.equals("rlElnTrmVbr842c") )
		{
			if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ούμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//ζ-ω/έζ-ησα
				if (termTxCpt_sRule.equals("rlElnTrmVbr842a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr842a: -ώ-ούμαι/-ησα-ήστηκα/-ησμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr842b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr842b: -ώ-ούμαι/-ησα-ήστηκα/-ησμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ούμε," +s+"είτε," +s+"ουν|"+s+"αν|"+s+"ούνε#"
				+s+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"ησα," +"έ"+s+"ησες," +"έ"+s+"ησε," +s+"ήσαμε," +s+"ήσατε," +"έ"+s+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s+"ήσε#"
				+s+"ώντας#";
			}
			else {
				//διαλαλ-άω/διαλάλ-ησα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr842a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr842a: -ώ-ούμαι/-ησα-ήστηκα/-ησμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr842b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr842b: -ώ-ούμαι/-ησα-ήστηκα/-ησμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ," +s+"είς," +s+"εί," +s+"ούμε," +s+"είτε," +s+"ούν|"+s+"ούνε#"
				+s2+"ει#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+s2+"ησα," +s2+"ησες," +s2+"ησε," +s+"ήσαμε," +s+"ήσατε," +s2+"ησαν#"
				+s+"ήσω," +s+"ήσεις," +s+"ήσει," +s+"ήσουμε|"+s+"ήσομε," +s+"ήσετε," +s+"ήσουν|"+s+"ήσουνε#"
				+s2+"ησε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ούμαι," +s+"είσαι," +s+"είται," +s+"ούμαστε," +s+"είστε," +s+"ούνται#"
				+"-#"
				+s+"ούμουν," +s+"ούσουν," +s+"ούνταν|"+s+"ούντανε," +s+"ούμαστε," +s+"ούσαστε," +s+"ούνταν|"+s+"ούντανε#"
				+s+"ήστηκα," +s+"ήστηκες," +s+"ήστηκε," +s+"ηστήκαμε," +s+"ηστήκατε," +s+"ήστηκαν|"+s+"ηστήκανε#"
				+s+"ηστώ," +s+"ηστείς," +s+"ηστεί," +s+"ηστούμε," +s+"ηστείτε," +s+"ηστούν#"
				+s+"ήσου#"
				+s+"ησμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr842c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr842c: -ώ-ούμαι/-ησα-ήστηκα/-ησμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr842a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ισα&-α/-ω/-ισμένος
		//σαλτάρω
		else if (termTxCpt_sRule.equals("rlElnTrmVbr843b") )
		{
			if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else
				note="Λάθος κατάληξη ενεστώτα.\n\t";
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//τάρ-ω/τάρ-ισα&έταρ-α
				note=note+"Κλίθηκε με τον rlElnTrmVbr843b: -ω/-ισα&-α/-ισμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ουμε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
				+s+"ε#"
				+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε," +"έ"+s2+"αν|"+s+"ανε#"
				+s+"ισα|"+"έ"+s2+"α," +s+"ισες|"+"έ"+s2+"ες," +s+"ισε|"+"έ"+s2+"ε," +s2+"ίσαμε|"+s+"αμε," +s2+"ίσατε|"+s+"ατε," +s+"ισαν|"+s2+"ίσανε|"+"έ"+s2+"αν|"+s+"ανε#"
				+s2+"ίσω," +s2+"ίσεις," +s2+"ίσει," +s2+"ίσουμε|"+s2+"ίσομε," +s2+"ίσετε," +s2+"ίσουν|"+s2+"ίσουνε#"
				+s+"ισε#"
				+s+"οντας#";
			}
			else {
				//σαλτάρ-ω/σαλτάρ-ισα
				s3=greekTonosDecrease(s);
				note=note+"Κλίθηκε με τον rlElnTrmVbr843b: -ω/-ισα&-α/-ισμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω," +s+"εις," +s+"ει," +s+"ουμε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
				+s3+"ε#"
				+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
				+s+"ισα|"+s3+"α," +s+"ισες,"+s3+"ες," +s+"ισε|"+s3+"ε," +s2+"ίσαμε|"+s+"αμε," +s2+"ίσατε|"+s+"ατε," +s+"ισαν|"+s2+"ίσανε|"+s3+"αν|"+s+"ανε#"
				+s2+"ίσω," +s2+"ίσεις," +s2+"ίσει," +s2+"ίσουμε|"+s2+"ίσομε," +s2+"ίσετε," +s2+"ίσουν|"+s2+"ίσουνε#"
				+s+"ισε#"
				+s+"οντας#";
			}
		}

		//-υσα-ύθηκα/-ω-ομαι/-υσμένος
		//πλέω/πλέομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr845a")||termTxCpt_sRule.equals("rlElnTrmVbr845b")||termTxCpt_sRule.equals("rlElnTrmVbr845c") )
		{
			if (termTxCpt_sName.endsWith("ω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//πλέω/έπλευσα
				if (termTxCpt_sRule.equals("rlElnTrmVbr845a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr845a: -ω-ομαι/-υσα-ύστηκα/-υσμένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr845b: -ω-ομαι/-υσα-ύστηκα/-υσμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s+"ε#"
					+"έ"+s2+"α," +"έ"+s2+"ες," +"έ"+s2+"ε," +s+"αμε," +s+"ατε,έ" +s2+"αν|"+s+"ανε#"
					+"έ"+s2+"υσα," +"έ"+s2+"υσες," +"έ"+s2+"υσε," +s2+"ύσαμε," +s2+"ύσατε,έ" +s2+"υσαν|"+s2+"ύσανε#"
					+s2+"ύσω," +s2+"ύσεις," +s2+"ύσει," +s2+"ύσουμε|" +s2+"ύσομε," +s2+"ύσετε," +s2+"ύσουν|"+s2+"ύσουνε#"
					+s2+"ύσε#"
					+s+"οντας#";
			}
			else {
				//παραπλέ-ω/παράπλε-υσα
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr845a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr845a: -ω-ομαι/-υσα-ύστηκα/-υσμένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr845b: -ω-ομαι/-υσα-ύστηκα/-υσμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
					 s+"ω," +s+"εις," +s+"ει," +s+"ουμε|" +s+"ομε," +s+"ετε," +s+"ουν|"+s+"ουνε#"
					+s3+"ε#"
					+s3+"α," +s3+"ες," +s3+"ε," +s+"αμε," +s+"ατε," +s3+"αν|"+s+"ανε#"
					+s3+"υσα," +s3+"υσες," +s3+"υσε," +s+"ύσαμε," +s+"ύσατε," +s3+"υσαν|"+s+"ύσανε#"
					+s2+"ύσω," +s2+"ύσεις," +s2+"ύσει," +s2+"ύσουμε|" +s2+"ύσομε," +s2+"ύσετε," +s2+"ύσουν|"+s+"ύσουνε#"
					+s3+"υσε#"
					+s+"οντας#";
			}
			nm2=
			 s+"ομαι," +s+"εσαι," +s+"εται," +s2+"όμαστε," +s+"εστε," +s+"ονται#"
			+s+"ου#"
			+s2+"όμουνα," +s2+"όσουν," +s2+"όταν|"+s2+"ότανε," +s2+"όμαστε," +s2+"όσαστε," +s+"ονταν|"+s+"όντανε#"
			+s2+"ύστηκα," +s2+"ύστηκες," +s2+"ύστηκε," +s2+"υστήκαμε," +s2+"υστήκατε," +s2+"ύστηκαν|"+s2+"υστήκανε#"
			+s2+"υστώ," +s2+"υστείς," +s2+"υστεί," +s2+"υστούμε," +s2+"υστείτε," +s2+"υστούν|"+s2+"υστούνε#"
			+s2+"ύσου#"
			+s2+"υσμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr845c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr845c: -ω-ομαι/-υσα-ύστηκα/-υσμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr845a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-υσα-ύστηκα/-ώ&άω-ιέμαι/-υσμένος
		//μεθάω/μεθιέμαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr844a")||termTxCpt_sRule.equals("rlElnTrmVbr844b")||termTxCpt_sRule.equals("rlElnTrmVbr844c") )
		{
			if (termTxCpt_sName.endsWith("άω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ώ"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,1);
			else if (termTxCpt_sName.endsWith("ιέμαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<1;
			if (afksisi)
			{
				//ζ-άω-έζ-υσα (ανύπαρκτο)
				if (termTxCpt_sRule.equals("rlElnTrmVbr844a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr844a: -ώ&άω-ιέμαι/-υσα-ύστηκα/-υσμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr844b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr844b: -ώ&άω-ιέμαι/-υσα-ύστηκα/-υσμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"ω|"+s+"άω," +s+"ας," +s+"α|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ουν|"+s+"αν|"+s+"άνε#"
				+s+"ά#"
				+s+"ούσα," +s+"ούσες," +s+"ούσε," +s+"ούσαμε," +s+"ούσατε," +s+"ούσαν|"+s+"ούσανε#"
				+"έ"+s+"υσα," +"έ"+s+"υσες," +"έ"+s+"υσε," +s+"ύσαμε," +s+"ύσατε," +"έ"+s+"υσαν#"
				+s+"ύσω," +s+"ύσεις," +s+"ύσει," +s+"ύσουμε|"+s+"ύσομε," +s+"ύσετε," +s+"ύσουν|"+s+"ύσουνε#"
				+s+"ύσε#"
				+s+"ώντας#";
			}
			else {
				//ζουλάω/ζούλησα
				s2=greekTonosSetOnFirst(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr844a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr844a: -ώ&άω-ιέμαι/-υσα-ύστηκα/-υσμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr844b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr844b: -ώ&άω-ιέμαι/-υσα-ύστηκα/-υσμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"ώ|"+s+"άω," +s+"άς," +s+"ά|"+s+"άει," +s+"ούμε|"+s+"άμε," +s+"άτε," +s+"ούν|"+s+"άν|"+s+"άνε#"
				+s2+"α#"
				+s+"ούσα|"+s2+"αγα," +s+"ούσες|"+s2+"αγες," +s+"ούσε|"+s2+"αγε," +s+"ούσαμε|"+s+"άγαμε," +s+"ούσατε|"+s+"άγατε," +s+"ούσαν|"+s+"ούσανε|"+s+"άγανε#"
				+s2+"υσα," +s2+"υσες," +s2+"υσε," +s+"ύσαμε," +s+"ύσατε," +s2+"υσαν#"
				+s+"ύσω," +s+"ύσεις," +s+"ύσει," +s+"ύσουμε|"+s+"ύσομε," +s+"ύσετε," +s+"ύσουν|"+s+"ύσουνε#"
				+s2+"υσε#"
				+s+"ώντας#";
			}
			nm2=
				 s+"ιέμαι," +s+"ιέσαι," +s+"ιέται," +s+"ιόμαστε," +s+"ιέστε," +s+"ιούνται#"
				+"-#"
				+s+"ιόμουνα," +s+"ιόσουν," +s+"ιόταν|"+s+"ιότανε," +s+"ιόμαστε," +s+"ιόσαστε," +s+"ιόνταν|"+s+"ιόντανε#"
				+s+"ύστηκα," +s+"ύστηκες," +s+"ύστηκε," +s+"υστήκαμε," +s+"υστήκατε," +s+"ύστηκαν|"+s+"υστήκανε#"
				+s+"υστώ," +s+"υστείς," +s+"υστεί," +s+"υστούμε," +s+"υστείτε," +s+"υστούν#"
				+s+"ύσου#"
				+s+"υσμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr844c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr844c: -ώ&άω-ιέμαι/-υσα-ύστηκα/-υσμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr844a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

//9)-ψα-φτηκα

		//αί(γ)ω-αί(γ)ομαι/-αψα-άφτηκα/-αμένος
		else if (termTxCpt_sRule.equals("rlElnTrmVbr921a")||termTxCpt_sRule.equals("rlElnTrmVbr921b")||termTxCpt_sRule.equals("rlElnTrmVbr921c") )
		{
			//κλ-αίω/κλ-αίγομαι
			if (termTxCpt_sName.endsWith("αίγω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,4);
			else if (termTxCpt_sName.endsWith("αίω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,3);
			else if (termTxCpt_sName.endsWith("αίγομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,6);
			else if (termTxCpt_sName.endsWith("αίομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,6);
//			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//κλ-αίω/έκλ-αψα
				if (termTxCpt_sRule.equals("rlElnTrmVbr921a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr921a: -αί(γ)ω-αί(γ)ομαι/-αψα-άφτηκα/-αμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr921b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr921b: -αί(γ)ω-αί(γ)ομαι/-αψα-άφτηκα/-αμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίω|"+s+"αίγω," +s+"αις|"+s+"αίγεις," +s+"αίει|"+s+"αίγει," +s+"αίμε," +s+"αίτε," +s+"αιν|"+s+"αίνε#"
				+s+"αίε|"+s+"αίγε#"
				+"έ"+s+"αια|"+"έ"+s+"αιγα," +"έ"+s+"αιες|"+"έ"+s+"αιγες," +"έ"+s+"αιε|"+"έ"+s+"αιγε," +s+"αίαμε|"+s+"αίγαμε," +s+"αίατε|"+s+"αίγατε," +"έ"+s+"αιαν|"+"έ"+s+"αιγαν|"+s+"αίγανε#"
				+"έ"+s+"αψα," +"έ"+s+"αψες," +"έ"+s+"αψε," +s+"άψαμε," +s+"άψατε,έ" +s+"αψαν|"+s+"άψανε#"
				+s+"άψω," +s+"άψεις," +s+"άψει," +s+"άψουμε|" +s+"άψομε," +s+"άψετε," +s+"άψουν|"+s+"άψουνε#"
				+s+"άψε#"
				+s+"αίοντας|"+s+"αίγοντας#";
			}
			else {
				//παρακλ-αίω/παράκλ-αψα
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr921a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr921a: -αί(γ)ω-αί(γ)ομαι/-αψα-άφτηκα/-αμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr921b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr921b: -αί(γ)ω-αί(γ)ομαι/-αψα-άφτηκα/-αμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+"αίω|"+s+"αίγω," +s+"αίς|"+s+"αίγεις," +s+"αίει|"+s+"αίγει," +s+"αίμε," +s+"αίτε," +s+"αίν|"+s+"αίνε#"
				+s3+"αιε|"+s3+"αιγε#"
				+s3+"αια|"+s3+"αιγα," +s3+"αιες|"+s3+"αιγες," +s3+"αιε|"+s3+"αιγε," +s+"αίαμε|"+s+"αίγαμε," +s+"αίατε|"+s+"αίγατε," +s3+"αιαν|"+s3+"αιγαν|"+s+"αίγανε#"
				+s3+"αψα," +s3+"αψες," +s3+"αψε," +s+"άψαμε," +s+"άψατε," +s3+"ψαν|"+s+"άψανε#"
				+s+"άψω," +s+"άψεις," +s+"άψει," +s+"άψουμε|" +s+"άψομε," +s+"άψετε," +s+"άψουν|"+s+"άψουνε#"
				+s+"άψε#"
				+s+"αίοντας|"+s+"αίγοντας#";
			}
			nm2=
				 s+"αίομαι|"+s+"αίγομαι," +s+"αίεσαι|"+s+"αίγεσαι," +s+"αίεται|"+s+"αίγεται," +s+"αιόμαστε|"+s+"αιγόμαστε," +s+"εστε|"+s+"γεστε," +s+"ονται|"+s+"γονται#"
				+s+"αίου|"+s+"αίγου#"
				+s+"αιόμουνα|"+s+"αιγόμουνα," +s+"αιόσουν|"+s+"αιγόσουν," +s+"αιόταν|"+s+"αιγόταν|" +s+"αιότανε|"+s+"αιγότανε," +s+"αιόμαστε|"+s+"αιγόμαστε," +s+"αιόσαστε|"+s+"αιγόσαστε," +s+"ονταν|"+s+"γονταν|"+s+"αιόντανε|"+s+"αιόντανε#"
				+s+"άφτηκα," +s+"άφτηκες," +s+"άφτηκε," +s+"αφτήκαμε," +s+"αφτήκατε," +s+"άφτηκαν|"+s+"αφτήκανε#"
				+s+"αφτώ," +s+"αφτείς," +s+"αφτεί," +s+"αφτούμε," +s+"αφτείτε," +s+"αφτούν|"+s+"αφτούνε#"
				+s+"άψου#"
				+s+"αμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr921c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr921c: -αί(γ)ω-αί(γ)ομαι/-αψα-άφτηκα/-αμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr921a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-ψα-ύτηκα/-ύω-ύομαι/-υμένος
		//αναπα-ύω/αναπα-ύομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr912a")||termTxCpt_sRule.equals("rlElnTrmVbr912b")||termTxCpt_sRule.equals("rlElnTrmVbr912c") )
		{
			if (termTxCpt_sName.endsWith("ύω"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
			else if (termTxCpt_sName.endsWith("ύομαι"))
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);
			s2=greekTonosSetOnFirst(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//παύω/παύομαι
				if (termTxCpt_sRule.equals("rlElnTrmVbr912a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr912a: -ύω-ύομαι/-ψα-ύτηκα/-υμένος, αύξ=ναί, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr912b: -ύω-ύομαι/-ψα-ύτηκα/-υμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
					 s+"ύω," +s+"ύεις," +s+"ύει," +s+"ύουμε|" +s+"ύομε," +s+"ύετε," +s+"ύουν|"+s+"ύουνε#"
					+s+"ύε#"
					+"έ"+s+"υα," +"έ"+s+"υες," +"έ"+s+"υε," +s+"ύαμε," +s+"ύατε,έ" +s+"υαν|"+s+"ύανε#"
					+"έ"+s+"ψα," +"έ"+s+"ψες," +"έ"+s+"ψε," +s2+"ψαμε," +s2+"ψατε,έ" +s+"ψαν|"+s2+"ψανε#"
					+s2+"ψω," +s2+"ψεις," +s2+"ψει," +s2+"ψουμε|" +s2+"ψομε," +s2+"ψετε," +s2+"ψουν|"+s2+"ψουνε#"
					+s2+"ψε#"
					+s+"ύοντας#";
			}
			else {
				//αναπα-ύω/ανάπα-ψα
				s3=greekTonosDecrease(s2);
				if (termTxCpt_sRule.equals("rlElnTrmVbr912a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr912a: -ύω-ύομαι/-ψα-ύτηκα/-υμένος, αύξ=όχι, φωνή=ε&π";
				else
					note=note+"Κλίθηκε με τον rlElnTrmVbr912b: -ύω-ύομαι/-ψα-ύτηκα/-υμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
					 s+"ύω," +s+"ύεις," +s+"ύει," +s+"ύουμε|" +s+"ύομε," +s+"ύετε," +s+"ύουν|"+s+"ύουνε#"
					+s3+"υε#"
					+s3+"υα," +s3+"υες," +s3+"υε," +s+"ύαμε," +s+"ύατε," +s3+"υαν|"+s+"ύανε#"
					+s3+"ψα," +s3+"ψες," +s3+"ψε," +s2+"ψαμε," +s2+"ψατε," +s3+"ψαν|"+s2+"ψανε#"
					+s2+"ψω," +s2+"ψεις," +s2+"ψει," +s2+"ψουμε|" +s2+"ψομε," +s2+"ψετε," +s2+"ψουν|"+s2+"ψουνε#"
					+s2+"ψε#"
					+s+"ύοντας#";
			}
			nm2=
			 s+"ύομαι," +s+"ύεσαι," +s+"ύεται," +s+"υόμαστε," +s+"ύεστε," +s+"ύονται#"
			+s+"ύου#"
			+s+"υόμουνα," +s+"υόσουν," +s+"υόταν|"+s+"υότανε," +s+"υόμαστε," +s+"υόσαστε," +s+"ύονταν|"+s+"υόντανε#"
			+s+"ύτηκα," +s+"ύτηκες," +s+"ύτηκε," +s+"υτήκαμε," +s+"υτήκατε," +s+"ύτηκαν|"+s+"υτήκανε#"
			+s+"υτώ," +s+"υτείς," +s+"υτεί," +s+"υτούμε," +s+"υτείτε," +s+"υτούν|"+s+"υτούνε#"
			+s+"ύου#"
			+s+"υμένος";

			if (termTxCpt_sRule.equals("rlElnTrmVbr912c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr912c: -ύω-ύομαι/-ψα-ύτηκα/-υμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr912a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-βω-βομαι/-ψα-φτηκα/-μμένος
		//-πω-φω
		//ανάβω/ανάβομαι, λάμπω, γράφω
		else if (termTxCpt_sRule.equals("rlElnTrmVbr911a")||termTxCpt_sRule.equals("rlElnTrmVbr911b")||termTxCpt_sRule.equals("rlElnTrmVbr911c") )
		{
			//η κατάληξη χωρίς το ω
			String c="";
			if (termTxCpt_sName.endsWith("ω"))
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,2);
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-2,termTxCpt_sName.length()-1);
			}
			else if (termTxCpt_sName.endsWith("μαι"))
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,5);
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-5,termTxCpt_sName.length()-4);
			}
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//κόβω/έκοψα
				if (termTxCpt_sRule.equals("rlElnTrmVbr911a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr911a: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr911b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr911b: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s+c+"ε#"
				+"έ"+s2+c+"α," +"έ"+s2+c+"ες," +"έ"+s2+c+"ε," +s+c+"αμε," +s+c+"ατε,έ" +s2+c+"αν#"
				+"έ"+s2+"ψα," +"έ"+s2+"ψες," +"έ"+s2+"ψε," +s+"ψαμε," +s+"ψατε,έ" +s2+"ψαν#"
				+s+"ψω," +s+"ψεις," +s+"ψει," +s+"ψουμε|" +s+"ψομε," +s+"ψετε," +s+"ψουν|"+s+"ψουνε#"
				+s+"ψε#"
				+s+c+"οντας#";
			}
			else {
				//ανάβω/ανάβομαι
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr911a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr911a: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr911b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr911b: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s3+c+"ε#"
				+s3+c+"α," +s3+c+"ες," +s3+c+"ε," +s+c+"αμε," +s+c+"ατε," +s3+c+"αν|"+s+c+"ανε#"
				+s3+"ψα," +s3+"ψες," +s3+"ψε," +s+"ψαμε," +s+"ψατε," +s3+"ψαν#"
				+s+"ψω," +s+"ψεις," +s+"ψει," +s+"ψουμε|" +s+"ψομε," +s+"ψετε," +s+"ψουν|"+s+"ψουνε#"
				+s3+"ψε#"
				+s+c+"οντας#";
			}
			nm2=
				 s+c+"ομαι," +s+c+"εσαι," +s+c+"εται," +s2+c+"όμαστε," +s+c+"εστε," +s+c+"ονται#"
				+s+c+"ου#"
				+s2+c+"όμουνα," +s2+c+"όσουν," +s2+c+"όταν|"+s2+c+"ότανε," +s2+c+"όμαστε," +s2+c+"όσαστε," +s+c+"ονταν|"+s2+c+"όντανε#"
				+s+"φτηκα," +s+"φτηκες," +s+"φτηκε," +s2+"φτήκαμε," +s2+"φτήκατε," +s+"φτηκαν|"+s2+"φτήκανε#"
				+s2+"φτώ," +s2+"φτείς," +s2+"φτεί," +s2+"φτούμε," +s2+"φτείτε," +s2+"φτούν|"+s2+"φτούνε#"
				+s+"ψου#"
				+s2+"μμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr911c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr911c: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr911a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		//-πτω-πτομαι/-ψα-φτηκα/-μμένος
		//-φτω
		//ανακαλύπτω/ανακαλύπτομαι
		else if (termTxCpt_sRule.equals("rlElnTrmVbr913a")||termTxCpt_sRule.equals("rlElnTrmVbr913b")||termTxCpt_sRule.equals("rlElnTrmVbr913c") )
		{
			//η κατάληξη χωρίς το ω
			String c="";
			if (termTxCpt_sName.endsWith("ω"))
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,3);
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-3,termTxCpt_sName.length()-1);
			}
			else if (termTxCpt_sName.endsWith("μαι"))
			{
				s=getFirstLettersIfSuffix(termTxCpt_sName,6);
				c=termTxCpt_sName.substring(termTxCpt_sName.length()-6,termTxCpt_sName.length()-4);
			}
			s2=greekTonosRemove(s);

			afksisi=!isLetterVowelGreek(s.charAt(0)) && findNumberOfGreekVowels(s)<2;
			if (afksisi)
			{
				//κόπτω/έκοψα
				if (termTxCpt_sRule.equals("rlElnTrmVbr913a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr913a: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=ναί, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr913b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr913b: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=ναί, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s+c+"ε#"
				+"έ"+s2+c+"α," +"έ"+s2+c+"ες," +"έ"+s2+c+"ε," +s+c+"αμε," +s+c+"ατε,έ" +s2+c+"αν#"
				+"έ"+s2+"ψα," +"έ"+s2+"ψες," +"έ"+s2+"ψε," +s+"ψαμε," +s+"ψατε,έ" +s2+"ψαν#"
				+s+"ψω," +s+"ψεις," +s+"ψει," +s+"ψουμε|" +s+"ψομε," +s+"ψετε," +s+"ψουν|"+s+"ψουνε#"
				+s+"ψε#"
				+s+c+"οντας#";
			}
			else {
				//ανακαλύπτω/ανακαλύπτομαι
				s3=greekTonosDecrease(s);
				if (termTxCpt_sRule.equals("rlElnTrmVbr913a"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr913a: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=όχι, φωνή=ε&π";
				else if (termTxCpt_sRule.equals("rlElnTrmVbr913b"))
					note=note+"Κλίθηκε με τον rlElnTrmVbr913b: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=όχι, φωνή=ε";
				termTxCpt_sAll=
				 s+c+"ω," +s+c+"εις," +s+c+"ει," +s+c+"ουμε|" +s+c+"ομε," +s+c+"ετε," +s+c+"ουν|"+s+c+"ουνε#"
				+s3+c+"ε#"
				+s3+c+"α," +s3+c+"ες," +s3+c+"ε," +s+c+"αμε," +s+c+"ατε," +s3+c+"αν|"+s+c+"ανε#"
				+s3+"ψα," +s3+"ψες," +s3+"ψε," +s+"ψαμε," +s+"ψατε," +s3+"ψαν#"
				+s+"ψω," +s+"ψεις," +s+"ψει," +s+"ψουμε|" +s+"ψομε," +s+"ψετε," +s+"ψουν|"+s+"ψουνε#"
				+s3+"ψε#"
				+s+c+"οντας#";
			}
			nm2=
				 s+c+"ομαι," +s+c+"εσαι," +s+c+"εται," +s2+c+"όμαστε," +s+c+"εστε," +s+c+"ονται#"
				+s+c+"ου#"
				+s2+c+"όμουνα," +s2+c+"όσουν," +s2+c+"όταν|"+s2+c+"ότανε," +s2+c+"όμαστε," +s2+c+"όσαστε," +s+c+"ονταν|"+s2+c+"όντανε#"
				+s+"φτηκα," +s+"φτηκες," +s+"φτηκε," +s2+"φτήκαμε," +s2+"φτήκατε," +s+"φτηκαν|"+s2+"φτήκανε#"
				+s2+"φτώ," +s2+"φτείς," +s2+"φτεί," +s2+"φτούμε," +s2+"φτείτε," +s2+"φτούν|"+s2+"φτούνε#"
				+s+"ψου#"
				+s2+"μμένος";
			if (termTxCpt_sRule.equals("rlElnTrmVbr913c") )
			{
				note=note+"Κλίθηκε με τον rlElnTrmVbr913c: -"+c+"ω-"+c+"ομαι/-ψα-φτηκα/-μμένος, αύξ=όχι, φωνή=π";
				termTxCpt_sAll=nm2;
			}
			else if (termTxCpt_sRule.equals("rlElnTrmVbr913a"))
			{
				termTxCpt_sAll=termTxCpt_sAll+nm2;
			}
		}

		return termTxCpt_sAll;
	}


	/**
	 * Returns the termTxCpt_sAll in the form "nm1,nm2,...nm52",
	 * given a term's-rule.<br/>
	 * Case1: 26 active OR passive voice<br/>
	 * Case2: 54 active AND passive voice
	 *
	 * @modified 2006.02.07
	 * @since 2006.02.07 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getCommaTxConceptTermsIfRuleOnly(String termTxCpt_sRule)
	{
		termTxCpt_sAll=	getTermsOfTxConceptIfRuleOnly(termTxCpt_sRule);

		String n[]= termTxCpt_sAll.split("#");
		String n1[]=null;
		String n2;
		String n3[]=null;
		String n4[]=null;
		String n5[]=null;
		String n6;
		String n7;
		String n8[]=null;
		String n9;
		String n10[]=null;
		String n11[]=null;
		String n12[]=null;
		String n13;
		String n14;
		if (n.length==14){
			n1=n[0].split(",");
			n2=n[1];
			n3=n[2].split(",");
			n4=n[3].split(",");
			n5=n[4].split(",");
			n6=n[5];
			n7=n[6];
			n8=n[7].split(",");
			n9=n[8];
			n10=n[9].split(",");
			n11=n[10].split(",");
			n12=n[11].split(",");
			n13=n[12];
			n14=n[13];
			termTxCpt_sAll =
						 n1[0]+","+n1[1]+","+n1[2]+","+n1[3]+","+n1[4]+","+n1[5]
				+","+n2
				+","+n3[0]+","+n3[1]+","+n3[2]+","+n3[3]+","+n3[4]+","+n3[5]
				+","+n4[0]+","+n4[1]+","+n4[2]+","+n4[3]+","+n4[4]+","+n4[5]
				+","+n5[0]+","+n5[1]+","+n5[2]+","+n5[3]+","+n5[4]+","+n5[5]
				+","+n6
				+","+n7
				+","+n8[0]+","+n8[1]+","+n8[2]+","+n8[3]+","+n8[4]+","+n8[5]
				+","+n9
				+","+n10[0]+","+n10[1]+","+n10[2]+","+n10[3]+","+n10[4]+","+n10[5]
				+","+n11[0]+","+n11[1]+","+n11[2]+","+n11[3]+","+n11[4]+","+n11[5]
				+","+n12[0]+","+n12[1]+","+n12[2]+","+n12[3]+","+n12[4]+","+n12[5]
				+","+n13
				+","+n14;
		}

		else if (n.length==7){
			n1=n[0].split(",");
			n2=n[1];
			n3=n[2].split(",");
			n4=n[3].split(",");
			n5=n[4].split(",");
			n6=n[5];
			n7=n[6];
			termTxCpt_sAll =
						 n1[0]+","+n1[1]+","+n1[2]+","+n1[3]+","+n1[4]+","+n1[5]
				+","+n2
				+","+n3[0]+","+n3[1]+","+n3[2]+","+n3[3]+","+n3[4]+","+n3[5]
				+","+n4[0]+","+n4[1]+","+n4[2]+","+n4[3]+","+n4[4]+","+n4[5]
				+","+n5[0]+","+n5[1]+","+n5[2]+","+n5[3]+","+n5[4]+","+n5[5]
				+","+n6
				+","+n7;
		}
		return termTxCpt_sAll;
	}

}
