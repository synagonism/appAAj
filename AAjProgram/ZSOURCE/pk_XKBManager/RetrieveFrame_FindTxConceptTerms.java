/*
 * RetrieveFrame_FindTxConceptTerms.java - Finds the TxConcept-Terms of a TxConcept-Name.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2006 Nikos Kasselouris
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package pk_XKBManager;

import pk_XKBManager.AAj;
import pk_Util.Util;
import pk_Util.Textual;
import pk_Logo.*;
import pk_SSemasia.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 * Finds the TxConcept-Terms of a TxConcept-Name.
 *
 * @modified 2006.01.28
 * @since 2001.03.16 (v00.02.00)
 * @author HoKoNoUmo
 */
 public class RetrieveFrame_FindTxConceptTerms extends JFrame
{
	static final long serialVersionUID = 21L;
	final JTextField jtfTxName;
	final JTextField fieldRule;
	final JComboBox cboxRule;
	final JComboBox jcbLang;
	final JTextArea areaForms;

	/**
	 * @modified 2001.11.28
	 * @since
	 * @author HoKoNoUmo
	 */
	public RetrieveFrame_FindTxConceptTerms(String word)
	{
		super(AAj.rbLabels.getString("ffnTitle")); //Find the Different-TERMS of a LgYUORDO (used/unused || monolectic/multi-word)

		//word row.
		JLabel labelWord = new JLabel(AAj.rbLabels.getString("FO_ETO_TO_KONCOS_NAMO"));//LgYUORDO:
		labelWord.setFont(new Font("serif", Font.BOLD, 16));
		jtfTxName = new JTextField(16);
		jtfTxName.setText(word);
		jtfTxName.setForeground(Color.red);
		jtfTxName.setFont(new Font("serif", Font.BOLD, 16));
		JLabel labelRuleWrite = new JLabel(AAj.rbLabels.getString("ffnRuleForce"));//Rule:
		labelRuleWrite.setFont(new Font("serif", Font.BOLD, 16));
		fieldRule = new JTextField(9);
		fieldRule.setFont(new Font("sansserif", Font.BOLD, 12));
		final JButton btFind = new JButton(AAj.rbLabels.getString("ffnFind"));//Find-Forms
		btFind.setFont(new Font("serif", Font.BOLD, 16));

		//message row.
		JLabel labelMessage = new JLabel(AAj.rbLabels.getString("ffnMessage"));//Write a word and choose a rule or nothing.
		labelMessage.setFont(new Font("serif", Font.PLAIN, 12));

		//rule row
		JLabel labelRule = new JLabel(AAj.rbLabels.getString("ffnRule"));//English rules.
		labelRule.setFont(new Font("serif", Font.BOLD, 14));
		final Object[] cbtEng = {
					"none",
					"Case-TxNoun",
					"Adjective-TxNoun",
					"Adverb-TxNoun",
					"TxVerb",
					" ",
					"Case-TxNoun pl -s, regular		(rlEngTrmNnCs11): car/cars",
					"Case-TxNoun pl -es ends -ch|sh|s|x (rlEngTrmNnCs12): torch,brush,bus,box",
					"Case-TxNoun pl -es ends -s: (rlEngTrmNnCs13): bus,buses,bus's|bus',buses'",
					"Case-TxNoun pl -ves ends -f|fe		(rlEngTrmNnCs21): leaf/leaves,knife/knives",
					"Case-TxNoun pl -ies ends -y&cons	 (rlEngTrmNnCs22): entity/entities,boy/boys",
					"Case-TxNoun irregular-diferent (rlEngTrmNnCs31): man, men, man's, men's",
					"Case-TxNoun irregular-same(rlEngTrmNnCs32):",
					"Case-TxNoun irregular-only singular(rlEngTrmNnCs33):",
					"Case-TxNoun irregular-only plural(rlEngTrmNnCs34):",
					"Case-TxNoun multiWord-change-FIRST (rlEngTrmNnCs4a11): car of xxx, cars of xxx",
					"Case-TxNoun multiWord-change-first (rlEngTrmNnCs4a12): torch of xxx, torches of xxx",
					"Case-TxNoun multiWord-change-first (rlEngTrmNnCs4a13): bus of xxx, buses of xxx",
					"Case-TxNoun multiWord-change-first (rlEngTrmNnCs4a21): leaf of xxx, leaves of xxx",
					"Case-TxNoun multiWord-change-first (rlEngTrmNnCs4a22): entity of xxx, entities of xxx",
					"Case-TxNoun multiWord-change-LAST (rlEngTrmNnCs4b11): entity's part, entity's parts",
					"Case-TxNoun multiWord-change-BOTH (rlEngTrmNnCs4c1111): car or home, cars or homes",
					"Case-TxNoun multiWord-change-both (rlEngTrmNnCs4c1211): torch or home, torches or homes",
					" ",
					"TxVerb -ed-ing-s regular							(rlEngTrmVrb11): climb,climbed,climbing,climbs",
					"TxVerb -xed-xing-s cons-vowel-accent (rlEngTrmVrb12): stop,stopped,stopping,stops",
					"TxVerb -led-ling-s ends with l				(rlEngTrmVrb13): travel,travelled|traveled,travelling|traveling,travels",
					"TxVerb -ed-ing-es ends -ch|sh|o|ss|x (rlEngTrmVrb14): miss,watch,finish,go,mix",
					"TxVerb -e-ed-ing-es ends -e					(rlEngTrmVrb21): like,liked,liking,likes",
					"TxVerb -y-ied-ying-ies ends -y&cons	(rlEngTrmVrb22): study,studied,studying,studies",
					" ",
					"Adjective-TxNoun -er-est					 (rlEngTrmNnAj11): dark,dark-er,dark-est/wise,wise-r,wise-st",
					"Adjective-TxNoun -xer-xest				 (rlEngTrmNnAj12): fat,fat-ter,fat-test",
					"Adjective-TxNoun -y-ier-iest			 (rlEngTrmNnAj21): happ-y,happ-ier,happ-iest",
					"Adjective-TxNoun more-most				 (rlEngTrmNnAj31): careful,more-careful,most-careful",
					"Adjective-TxNoun irregular				 (rlEngTrmNnAj41): good,better,best",
					"Adjective-TxNoun no degrees 			 (rlEngTrmNnAj42): ",
					};
		final Object[] cbtEln = {
					"κανένα",
					"Ουσιαστικό",
					"Ρήμα",
					"Επίθετο",
					" ",
					"αρσενικό -ας οξ/παροξ/προπαροξ (rlElnTrmNnCs1a1):σφουγγαράς,αγώνας,ταμίας,φύλακας",
					"αρσενικό -ές										(rlElnTrmNnCs1b1):καφές",
					"αρσενικό -ης ισοσύλλαβο				(rlElnTrmNnCs1c1):νικητής,ναύτης",
					"αρσενικό -ης ανισοσύλλαβο			(rlElnTrmNnCs1c2):καφετζής,νοικοκύρης,φούρναρης",
					"αρσενικό -ης διπλό							(rlElnTrmNnCs1c3):αφέντης,πραματευτής",
					"αρσενικό -ος οξ/παροξ/προπαροξ (rlElnTrmNnCs1d1):ουρανός,δρόμος,καλόγερος-καλόγερου",
					"αρσενικό -ος προπαρξ ανεβ τόνο (rlElnTrmNnCs1d2):κίνδυνος-κινδύνου",
					"αρσενικό -ούς όπως παππούς			(rlElnTrmNnCs1e1):παππούς",
					"θηλυκό -ά ισοσύλλαβο						(rlElnTrmNnCs2a1):καρδιά",
					"θηλυκό -ά ανισοσύλλαβο					(rlElnTrmNnCs2a2):γιαγιά",
					"θηλυκό -α γεν.πληθ. οξύτονη		(rlElnTrmNnCs2a3):ώρα,θάλασσα",
					"θηλυκό -α γεν.πληθ. παροξύτ.		(rlElnTrmNnCs2a4):ελπίδα,οντότητα",
					"θηλυκό -η νεόκλιτο οξ/παροξ		(rlElnTrmNnCs2b1):ψυχή,νίκη",
					"θηλυκό -η αρχαιόκλιτο παροξ/προπξ (rlElnTrmNnCs2b2):σκέψη,δύναμη",
					"θηλυκό -ος											(rlElnTrmNnCs2c1):διάμετρος",
					"θηλυκό -ού											(rlElnTrmNnCs2d1):αλεπού",
					"θηλυκό -ω οξ/παροξ							(rlElnTrmNnCs2e1):Αργυρώ,Φρόσω",
					"ουδέτερο -μα παροξ/προπαροξ		(rlElnTrmNnCs3a1):κύμα,όνομα",
					"ουδέτερο -ι οξ/παροξ						(rlElnTrmNnCs3b1):παιδί,τραγούδι",
					"ουδέτερο -ιμο									(rlElnTrmNnCs3c1):δέσιμο",
					"ουδέτερο -ο οξ/παροξ/προπαροξ	(rlElnTrmNnCs3c2):βουνό,πεύκο,σίδερο",
					"ουδέτερο -ας										(rlElnTrmNnCs3d1):κρέας",
					"ουδέτερο -ος οξ/παροξ/προπαροξ (rlElnTrmNnCs3d2):γεγονός,μέρος,έδαφος",
					"ουδέτερο -ώς										(rlElnTrmNnCs3d3):καθεστώς,φώς",
					" ",
					"ΚΑΝΟΝΕΣ ΡΗΜΑΤΩΝ ΑΛΦΑΒΗΤΙΚΑ ΩΣ ΠΡΟΣ ΤΟΝ ΑΟΡΙΣΤΟ",
					"						 1) -α",
					"-α/-ω ελλειπής,										 (rlElnTrmVbr111b):τρέμ-ω",
					"-α-ήθηκα/-αίνω-αίνομαι/-ημένος,		 (rlElnTrmVbr112a):καταλαβαίν-ω",
					"-α-θηκα/-ω-ομαι/-μένος,						 (rlElnTrmVbr113a):οξύν-ω",
					"						 2) -κα",
					"-ηκα/-αίνω/-αμένος,								 (rlElnTrmVbr241b):ανεβ-αίνω",
					"						 3) -λα",
					"-αλα-άλθηκα/-έλνω-έλνομαι/-αλμένος, (rlElnTrmVbr321a):ψ-έλνω",
					"-ειλα-άλθηκα/-έλνω-έλνομαι/-αλμένος,(rlElnTrmVbr341a):στ-έλνω",
					"-ειλα-έλθηκα/-έλνω-έλνομαι/-ελμένος,(rlElnTrmVbr342a):παραγγ-έλνω",
					"-ειλα-έλθηκα/-έλλω-έλλομαι/-ελμένος,(rlElnTrmVbr343a):αγγ-έλλω",
					"						 4) -να",
					"-να-θηκα/-νω-νομαι/-μένος,					 (rlElnTrmVbr411a):κρί-νω",
					"-ανα-άθηκα/-αίνω-αίνομαι/-αμένος,	 (rlElnTrmVbr421a):βουβ-αίνω",
					"-ανα-άνθηκα/-αίνω-αίνομαι/-ασμένος, (rlElnTrmVbr422a):λευκ-αίνω",
					"-εινα-είθηκα/-ένω-ένομαι/-ειμένος,	 (rlElnTrmVbr441a):μ-ένω",
					"-υνα-ύθηκα/-αίνω-αίνομαι/-υμένος,	 (rlElnTrmVbr442a):ακριβ-αίνω",
					"-υνα-ύθηκα/-ένω-ένομαι/-υμένος,		 (rlElnTrmVbr443a):πλ-ένω",
					"						 5) -ξα",
					"-ξα-χτηκα/-γγ|σκ|σσ|ττ|χνω-ομαι/γμένος,(rlElnTrmVbr511a):σφί-γγω,διδάσκω",
					"-ξα-χτηκα/-γ|κ|χω-γομαι/-γμένος,		 (rlElnTrmVbr512a):πνί-γω,πλέκω",
					"-ξα-χτηκα/-[γ]ω-[γ]ομαι/-γμένος,		 (rlElnTrmVbr513a):φυλά-[γ]ω",
					"-αξα-άχτηκα/-αίνω-αίνομαι/-αγμένος, (rlElnTrmVbr521a):βυζ-αίνω",
					"-αξα-άχτηκα/-ώ&άω-ιέμαι/-αγμένος,	 (rlElnTrmVbr522a):πετ-άω",
//										"-αξα-άχτηκα/-ώ-ούμαι&άμαι/-αγμένος, (rlElnTrmVbr523a)",
//										"-εξα-έχτηκα/-ώ&άω-ιέμαι/-εγμένος,	 (rlElnTrmVbr531a)",
//										"-εξα-έχτηκα/-ώ-ούμαι&άμαι/-εγμένος, (rlElnTrmVbr532a)",
					"-ηξα-ήχτηκα/-ώ&άω-ιέμαι/-ηγμένος,	 (rlElnTrmVbr541a):πηδ-άω",
//										"-ηξα-ήχτηκα/-ώ-ούμαι&άμαι/-ηγμένος, (rlElnTrmVbr542a)",
					"						 6) -ρα",
					"-αρα-άρθηκα/-έρνω-έρνομαι/-αρμένος, (rlElnTrmVbr621a):γδ-έρνω",
					"-ειρα-άρθηκα/-έρνω-έρνομαι/-αρμένος,(rlElnTrmVbr641a):δ-έρνω",
					"-ερα-έρθηκα/-έρνω-έρνομαι/-ερμένος, (rlElnTrmVbr642a):φ-έρνω",
					"-υρα-ύρθηκα/-έρνω-έρνομαι/-υρμένος, (rlElnTrmVbr643a):σ-έρνω",
					"						 7) -σα/-θηκα",
					"-σα-θηκα/-νω-νομαι/-μένος,					 (rlElnTrmVbr711a):δαγκά-νω",
					"-σα-θηκα/-ω-ομαι/-μένος,						 (rlElnTrmVbr712a):ιδρύ-ω",
					"-ασα-άθηκα/-αίνω-αίνομαι/-αμένος,	 (rlElnTrmVbr711a):αποστ-αίνω",
					"-ασα-άθηκα/-ανω-ανομαι/-αμένος,		 (rlElnTrmVbr711a):δαγκ-άνω",
//										"-ασα-άθηκα/-ώ&άω-ιέμαι/-αμένος,		 (rlElnTrmVbr722a)",
//										"-ασα-άθηκα/-ώ-ούμαι&άμαι/-αμένος,	 (rlElnTrmVbr723a)",
					"-εσα-έθηκα/-ώ&άω-ιέμαι/-εμένος,		 (rlElnTrmVbr731a):βαρ-άω",
					"-εσα-έθηκα/-ώ-ούμαι/-εμένος,				 (rlElnTrmVbr732a):αναιρ-ώ",
					"-ησα-ήθηκα/-αίνω-αίνομαι/-ημένος,	 (rlElnTrmVbr741a):αναστ-αίνω",
					"-ησα-ήθηκα/-άνω-άνομαι/-ημένος,		 (rlElnTrmVbr742a):αυξ-άνω",
					"-ησα-ήθηκα/-ήνω-ήνομαι/-ημένος,		 (rlElnTrmVbr711a):στ-ήνω",
					"-ησα-ήθηκα/-ώ&άω-ιέμαι/-ημένος,		 (rlElnTrmVbr743a):αγαπ-άω",
					"-ησα-ήθηκα/-ώ-ούμαι/-ημένος,				 (rlElnTrmVbr744a):θεωρ-ώ",
					"-ησα-ήθηκα/-ώ-ούμαι&άμαι/-ημένος,	 (rlElnTrmVbr745a):λυπώ,λυπ-άμαι",
					"-ησα-ήθηκα/-ω-ομαι/-ημένος,				 (rlElnTrmVbr746a):δέ-ομαι",
					"-υσα-ύθηκα/-αίνω-αίνομαι/-υμένος,	 (rlElnTrmVbr747a):αρτ-αίνω",
					"						 8) -σα/-στηκα",
					"-σα-στηκα/-θ|νω-θομαι/-σμένος,			 (rlElnTrmVbr811a):πλά-θω,κλί-νω",
					"-σα-στηκα/-[γ]ω-ομαι/-σμένος,			 (rlElnTrmVbr812a):ακού-[γ]ω",
					"-σα-στηκα/-ω-ομαι/-σμένος,					 (rlElnTrmVbr813a):αποκλεί-ω",
					"-ασα-άστηκα/-αίνω-αίνομαι/-ασμένος, (rlElnTrmVbr821a):χορτ-αίνω",
					"-ασα-άστηκα/-άνω-άνομαι/-σμένος,		 (rlElnTrmVbr811a):φτ-άνω",
					"-ασα-άστηκα/-νώ&νάω-νιέμαι/-ασμένος,(rlElnTrmVbr822a):περ-νάω",
					"-ασα-άστηκα/-ώ&άω-ιέμαι/-ασμένος,	 (rlElnTrmVbr823a):γελ-άω",
					"-ασα-άστηκα/-ώ-ούμαι/-ασμένος,			 (rlElnTrmVbr824a):διαθλ-ώ",
//										"-εσα-έστηκα/-ώ&άω-ιέμαι/-εσμένος,	 (rlElnTrmVbr831a)",
					"-εσα-έστηκα/-ώ-ούμαι&άμαι/-εσμένος, (rlElnTrmVbr832a):καλ-ώ",
					"-ησα-ήστηκα/-ώ&άω-ιέμαι/-ησμένος,	 (rlElnTrmVbr841a):ζουλ-άω",
					"-ησα-ήστηκα/-ώ-ούμαι/-ησμένος,			 (rlElnTrmVbr842a):διαλαλ-ώ",
					"-ισα&-α/-ω/-ισμένος,								 (rlElnTrmVbr843b):σαλτάρ-ω",
					"-υσα-ύστηκα/-άω&ώ-ιέμαι/-υσμένος,	 (rlElnTrmVbr845a):μεθ-άω",
					"-υσα-ύστηκα/-ω-ομαι/-υσμένος,			 (rlElnTrmVbr845a):πλέ-ω",
					"						 9) -ψα",
					"-ψα-φτηκα/-β|π|φω-βομαι/-μμένος,		 (rlElnTrmVbr911a):ανά-βω,λάμπω,γράφω",
					"-ψα-ύτηκα/-ύω-ύομαι/-υμένος,				 (rlElnTrmVbr912a):γιατρε-ύω",
					"-ψα-φτηκα/-πτ|φτω-πτομαι/-μμένος,	 (rlElnTrmVbr913a):καλύ-πτω,αστρά-φτω",
					"-αψα-άφτηκα/[γ]-αίω-αίομαι/-αμένος, (rlElnTrmVbr921a):κλ-αίω",
					" ",
					"επίθετο -ος-η-ο οξ/παρξ/προπαρξ (rlElnTrmAdj11):δυνατός-ή-ό,άσπρος-η-ο,όμορφος-η-ο",
					"επίθετο -ος-α-ο οξ/παρξ/προπαρξ (rlElnTrmAdj12):γλυκός-ά-ό,ωραίος-α-ο,πλούσιος-α-ο",
					"επίθετο -ύς-ιά-ύ								 (rlElnTrmAdj21):βαθύς-βαθιά-βαθύ",
					"επίθετο -ής-ιά-ί								 (rlElnTrmAdj22):σταχτής-σταχτιά-σταχτί",
					"επίθετο -ής-ής-ές							 (rlElnTrmAdj23):συνεχής-συνεχής-συνεχές",
					"επίθετο -ης-α-ικο							 (rlElnTrmAdj24):ζηλιάρης-ζηλιάρα-ζηλιάρικο",
					"επίθετο -άς-ού-άδικο						 (rlElnTrmAdj31):λογάς-λογού-λογάδικο",
					};
		final Object[] cbtEpo = {
					"none",
					"CaseTxNoun",
					"TxVerb",
					"AdjectiveTxNoun",
					"AdverbTxNoun",
					};
		final Object[] cbtKml = {
					"none",
					"CaseTxNoun",
					"TxVerb",
					};
		cboxRule = new JComboBox(cbtEln);
		cboxRule.setFont(new Font("monospaced", Font.BOLD, 12));
		cboxRule.setPreferredSize(new Dimension(474,22));
	//		cboxRule.setSelectedItem((Object)"δένω/έδεσα,ομαι/θηκα					 (elv1a1a1)");

		JLabel labelLang = new JLabel(AAj.rbLabels.getString("Language")+": ");//Language:
		labelLang.setFont(new Font("serif", Font.BOLD, 14));
		Object[] cbtLang = {
												AAj.rbLabels.getString("English2"),
												AAj.rbLabels.getString("Greek2"),
												AAj.rbLabels.getString("Esperanto"),
												AAj.rbLabels.getString("Komo"),
											 };
		jcbLang = new JComboBox(cbtLang);
		DefaultComboBoxModel dcbm = new DefaultComboBoxModel(cbtEng);
		//addLng
		if (AAj.lng.equals("eng")){
			jcbLang.setSelectedIndex(0);
			dcbm = new DefaultComboBoxModel(cbtEng);
		} 	else if (AAj.lng.equals("eln")){
			jcbLang.setSelectedIndex(1);
			dcbm = new DefaultComboBoxModel(cbtEln);
		} else if (AAj.lng.equals("epo")){
			jcbLang.setSelectedIndex(2);
			dcbm = new DefaultComboBoxModel(cbtEpo);
		} else if (AAj.lng.equals("kml")){
			jcbLang.setSelectedIndex(3);
			dcbm = new DefaultComboBoxModel(cbtKml);
		}
		cboxRule.setModel(dcbm);
//		jcbLang.setSelectedItem(AAj.rbLabels.getString("Greek2"));
		jcbLang.setFont(new Font("serif", Font.BOLD, 14));
		jcbLang.setPreferredSize(new Dimension(89,22));
		jcbLang.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				DefaultComboBoxModel dcbm;
				String lango=(String)jcbLang.getSelectedItem();
				if (lango.equals(AAj.rbLabels.getString("English2")) )
					dcbm = new DefaultComboBoxModel(cbtEng);
				else if (lango.equals(AAj.rbLabels.getString("Greek2")) )
					dcbm = new DefaultComboBoxModel(cbtEln);
				else if (lango.equals(AAj.rbLabels.getString("Esperanto")) )
					dcbm = new DefaultComboBoxModel(cbtEpo);
				else
					dcbm = new DefaultComboBoxModel(cbtKml);
				cboxRule.setModel(dcbm);
			}
		});

		//nmLgConcepts row.
		JLabel labelForms = new JLabel(AAj.rbLabels.getString("TermsOfConcept_Tx")+":");
		labelForms.setFont(new Font("serif", Font.BOLD, 16));
		areaForms = new JTextArea();
		JScrollPane scroller = new JScrollPane(){
			static final long serialVersionUID = 21L;
			public Dimension getPreferredSize() {
					return new Dimension(659,399);
			}
		};
		JViewport port = scroller.getViewport();
//		areaForms.setFont(new Font("arial", Font.PLAIN, 14));
		areaForms.setFont(new Font("monospaced", Font.BOLD, 14));
		areaForms.setTabSize(5);
		port.add(areaForms);

		//Create the Layouts.
		Box box = new Box(BoxLayout.Y_AXIS);

		JPanel paneWord = new JPanel();
		paneWord.setLayout(new FlowLayout());
		paneWord.add(labelWord);
		paneWord.add(jtfTxName);
		jtfTxName.addActionListener(new ActionFindTxConceptTerms());
		paneWord.add(labelRuleWrite);
		paneWord.add(fieldRule);
		fieldRule.addActionListener(new ActionFindTxConceptTerms());
		paneWord.add(new JLabel("				"));
		paneWord.add(btFind);
		btFind.addActionListener(new ActionFindTxConceptTerms());

		JPanel paneMessage = new JPanel();
		paneMessage.setLayout(new FlowLayout());
		paneMessage.add(labelMessage);

		JPanel paneRules = new JPanel();
		paneRules.setLayout(new FlowLayout());
		paneRules.add(labelRule);
		paneRules.add(cboxRule);
	//		paneRules.add(new JLabel(" "));
		paneRules.add(labelLang);
		paneRules.add(jcbLang);

		JPanel paneForms = new JPanel();
		paneForms.setLayout(new FlowLayout());
		paneForms.add(labelForms);
		paneForms.add(scroller);

		JPanel paneAuthor = new JPanel(new GridLayout());
		JLabel labelAuthor=new JLabel("Nikos Kasselouris: users.otenet.gr/~nikkas/",JLabel.RIGHT);
		labelAuthor.setFont(new Font("serif", Font.PLAIN, 14));
		paneAuthor.add(labelAuthor);

		box.add(paneWord);
		box.add(paneMessage);
		box.add(paneRules);
		box.add(paneForms);
		box.add(paneAuthor);

		//Put the box/buttons in another panel.
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(1, 5, 1, 35));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(box, BorderLayout.CENTER);

		getContentPane().add(contentPane, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});
		pack();
		Util.setFrameIcon(this);
		setLocation(9,1);
		setVisible(true);
	}

	/**
	 * @modified 2008.04.21
	 * @since 2001.04.04
	 * @author HoKoNoUmo
	 */
	class ActionFindTxConceptTerms extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			String wrd="";
			wrd=jtfTxName.getText();
			wrd=wrd.toLowerCase();
			//the rule to use.
			String rule="";
			String nmLgConcepts="";
			String ruleOnly="";
			ruleOnly=fieldRule.getText();
			String lango=(String)jcbLang.getSelectedItem();
			rule=(String)cboxRule.getSelectedItem();

			//do nothing on null wrd
			if (wrd.length()>0)
			{
				if (lango.equals(AAj.rbLabels.getString("English2")) )
				{
					if ( !Textual.isLetterEnglish(wrd.substring(0,1)) )
					JOptionPane.showMessageDialog(null,
						"You MUST write an English-word!");

					if (ruleOnly.length()==0)
					{
						//in case of "none"
						if (rule.equals("none"))
						{
							Object[] options = {
										"CaseTxNoun",
										"TxVerb",
										"AdjectiveTxNoun",
										"AdverbTxNoun"
							};
							Object selectedValue = JOptionPane.showInputDialog(null,
							"Choose the Part-of-Speech on which you want to see the possible nmLgConcepts",							//message
							"Choose One",															//title
							JOptionPane.INFORMATION_MESSAGE,					//message type
							null,																			//icon
							options,																	//values to select
							options[0]);															//initial selection
							if (selectedValue.equals("CaseTxNoun"))
							{
								rule="CaseTxNoun";
							}
							else if (selectedValue.equals("TxVerb"))
							{
								rule="TxVerb";
							}
							else if (selectedValue.equals("AdjectiveTxNoun"))
							{
								rule="AdjectiveTxNoun";
							}
							else if (selectedValue.equals("AdverbTxNoun"))
							{
							}
						}

						if (rule.equals("CaseTxNoun"))
						{
							TermTxNounCaseEng n = new TermTxNounCaseEng(wrd);
							nmLgConcepts=n.findTermsOfTxConceptIfName();
							displayTxNounCaseEng(n.note,nmLgConcepts);
						}
						else if (rule.equals("TxVerb"))
						{
							TermTxVerbEng v = new TermTxVerbEng(wrd);
							nmLgConcepts=v.findTermsOfTxConceptIfName();
							displayTxVerbEng(v.note,nmLgConcepts);
						}
						else if (rule.equals("AdjectiveTxNoun"))
						{
							TermTxNounAdjectiveEng a = new TermTxNounAdjectiveEng(wrd);
							nmLgConcepts=a.findTermsOfTxConceptIfName();
							displayTxNounAdjectiveEng(a.note,nmLgConcepts);
						}
						else if (rule.equals("AdverbTxNoun"))
						{
						}

						else //a specific rule eg rlEngTrmNnCs11
						{
							rule=rule.substring(rule.indexOf("(")+1,rule.indexOf(")") );

							if (rule.startsWith("rlEngTrmNnCs"))
							{
								TermTxNounCaseEng n = new TermTxNounCaseEng(wrd,rule);
								nmLgConcepts=n.getTermsOfTxConceptIfRule(rule);
								displayTxNounCaseEng(n.note,nmLgConcepts);
							}
							else if (rule.startsWith("rlEngTrmVrb"))//this is a tx_verb.
							{
								TermTxVerbEng v = new TermTxVerbEng(wrd,rule);
								nmLgConcepts=v.getTermsOfTxConceptIfRule(rule);
								displayTxVerbEng(v.note,nmLgConcepts);
							}
							else if (rule.startsWith("rlEngTrmNnAj"))
							{
								TermTxNounAdjectiveEng aj = new TermTxNounAdjectiveEng(wrd,rule);
								nmLgConcepts=aj.getTermsOfTxConceptIfRule(rule);
								displayTxNounAdjectiveEng(aj.note,nmLgConcepts);
							}
						}
					}

					else //we are forced to use a rule: DON'T check irregulars.
					{
						if (ruleOnly.startsWith("rlEngTrmNnCs"))
						{
							TermTxNounCaseEng n = new TermTxNounCaseEng(wrd,ruleOnly);
							nmLgConcepts=n.getTermsOfTxConceptIfRuleOnly(ruleOnly);
							displayTxNounCaseEng(n.note,nmLgConcepts);
						}
						else if (ruleOnly.startsWith("rlEngTrmVrb"))
						{
							TermTxVerbEng v = new TermTxVerbEng(wrd,ruleOnly);
							nmLgConcepts=v.getTermsOfTxConceptIfRuleOnly(ruleOnly);
							displayTxVerbEng(v.note,nmLgConcepts);
						}
						else if (ruleOnly.startsWith("rlEngTrmNnAj"))
						{
							TermTxNounAdjectiveEng aj = new TermTxNounAdjectiveEng(wrd,rule);
							nmLgConcepts=aj.getTermsOfTxConceptIfRuleOnly(rule);
							displayTxNounAdjectiveEng(aj.note,nmLgConcepts);
						}
					}
				}


				else if (lango.equals(AAj.rbLabels.getString("Greek2")) )
				{
					if (ruleOnly.length()==0)
					{
						//ΔΕΝ ΕΔΩΣΕ ΚΑΝΟΝΑ:
						if (rule.equals("κανένα"))
						{
							//ρήμα - ουσιαστικό
							if (wrd.endsWith("ώ")||wrd.endsWith("ω"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με κατάληξη -ω έχουμε κύρια ΡΗΜΑΤΑ (δένω,λυπώ) αλλά και ΘΗΛΥΚΑ (Φρόσω,Αργυρώ)."
									+"\nΝα κλιθεί σαν Ρήμα;","Να κλιθεί σαν ΡΗΜΑ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION)
									rule="TxVerb";
								else
									rule="CaseTxNoun";
							}
							//ρήμα
							else if (wrd.endsWith("μαι"))
								rule="TxVerb";
							//ουσιαστικό που προηγείται
							else if (wrd.endsWith("μα")||wrd.endsWith("ούς")||wrd.endsWith("ούσ")
											||wrd.endsWith("ιμο"))
								rule="CaseTxNoun";
							//επίθετο
							else if (wrd.endsWith("έστερη")||wrd.endsWith("έστατη")
											||wrd.endsWith("ότερη")||wrd.endsWith("ότατη")
											||wrd.endsWith("ύτατη")||wrd.endsWith("ύτατη")
											||wrd.endsWith("ικο")
											||wrd.endsWith("έστερο")||wrd.endsWith("έστατο")
											||wrd.endsWith("ότερο")||wrd.endsWith("ότατο")
											||wrd.endsWith("ύτερο")||wrd.endsWith("ύτατο")
											||wrd.endsWith("ύς")
											||wrd.endsWith("έστερος")||wrd.endsWith("έστατος")
											||wrd.endsWith("ότερος")||wrd.endsWith("ότατος")
											||wrd.endsWith("ύτερος")||wrd.endsWith("ύτατος")
											)
								rule="AdjectiveTxNoun";

							//επίθετο - ουσιαστικό
							else if (wrd.endsWith("ά")||wrd.endsWith("α"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -α η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ η καρδιά/θάλασσα) "
									+"\nΚΑΙ επίθετα (πχ γλυκός-γλυκιά-γλυκό/ωραίος-ωραία-ωραίο)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							else if (wrd.endsWith("ού"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -ού η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ η αλεπού) "
									+"\nΚΑΙ επίθετα (πχ λογάς-λογού-λογάδικο)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							else if (wrd.endsWith("ή")||wrd.endsWith("η"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -η η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ η ψυχή/δύναμη) "
									+"\nΚΑΙ επίθετα (πχ δυνατός-δυνατή-δυνατό/άσπρος-άσπρη-άσπρο)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							else if (wrd.endsWith("ί"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -ί η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ το παιδί) "
									+"\nΚΑΙ επίθετα (πχ σταχτής-σταχτιά-σταχτί)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							else if (wrd.endsWith("ό")||wrd.endsWith("ο"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -ο η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ το βουνό/το πεύκο) "
									+"\nΚΑΙ επίθετα (πχ γλυκός-γλυκιά-γλυκό/όμορφος-όμορφη-όμορφο)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							else if (wrd.endsWith("άς"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -άς η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ ο σφουγγαράς) "
									+"\nΚΑΙ επίθετα (πχ λογάς-λογού-λογάδικο)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							else if (wrd.endsWith("ές"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -ές η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ ο καφές) "
									+"\nΚΑΙ επίθετα (πχ συνεχής-συνεχής-συνεχές)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							else if (wrd.endsWith("ής")||wrd.endsWith("ης"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -ης η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ ο νικητής/ναύτης) "
									+"\nΚΑΙ επίθετα (πχ σταχτής-σταχτιά-σταχτί/ζηλιάρης-ζηλιάρα-ζηλιάρικο)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							else if (wrd.endsWith("ός")||wrd.endsWith("ος"))
							{
								int	 result= JOptionPane.showConfirmDialog(null,
									"Με αυτή την κατάληξη -ος η γλώσσα έχει ΚΑΙ ουσιαστικά (πχ ο ουρανός/δρόμος) "
									+"\nΚΑΙ επίθετα (πχ δυνατός-δυνατή-δυνατό/άσπρος-άσπρη-άσπρο)."
									+"\nΝα κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;","Να κλιθεί σαν ΟΥΣΙΑΣΤΙΚΟ;",JOptionPane.YES_NO_OPTION);
								if(result == JOptionPane.YES_OPTION) rule="CaseTxNoun";
								else	rule="AdjectiveTxNoun";
							}
							//επίθετο
							else if (
											wrd.endsWith("ύ")
											)
								rule="AdjectiveTxNoun";
							//ουσιαστικό
							else
								rule="CaseTxNoun";

							if (rule.equals("CaseTxNoun"))
							{
								TermTxNounCaseEln n = new TermTxNounCaseEln(wrd);
								nmLgConcepts=n.findTermsOfTxConceptIfName();
								displayTxNounCaseEln(n.note,nmLgConcepts);
							}
							else if (rule.equals("TxVerb"))
							{
								TermTxVerbEln v = new TermTxVerbEln(wrd);
								nmLgConcepts=v.findTermsOfTxConceptIfName();
								displayTxVerbEln(wrd,v.note,nmLgConcepts);
							}
							else if (rule.equals("AdjectiveTxNoun"))
							{
								TermTxNounAdjectiveEln a = new TermTxNounAdjectiveEln(wrd);
								nmLgConcepts=a.findTermsOfTxConceptIfName();
								displayTxNounAdjectiveEln(a.note,nmLgConcepts);
							}
						}

						//ΔΙΑΛΕΞΕ ΚΑΝΟΝΑ-ΚΛΙΣΗΣ:
						else if (rule.equals("Ουσιαστικό"))
						{
							TermTxNounCaseEln n = new TermTxNounCaseEln(wrd);
							nmLgConcepts=n.findTermsOfTxConceptIfName();
							displayTxNounCaseEln(n.note,nmLgConcepts);
						}
						else if (rule.equals("Ρήμα"))
						{
							TermTxVerbEln v = new TermTxVerbEln(wrd);
							nmLgConcepts=v.findTermsOfTxConceptIfName();
							displayTxVerbEln(wrd,v.note,nmLgConcepts);
						}
						else if (rule.equals("Επίθετο"))
						{
							TermTxNounAdjectiveEln a = new TermTxNounAdjectiveEln(wrd);
							nmLgConcepts=a.findTermsOfTxConceptIfName();
							displayTxNounAdjectiveEln(a.note,nmLgConcepts);
						}
						else {
							rule=rule.substring(rule.indexOf("(")+1,rule.indexOf(")") );
							if (rule.startsWith("rlElnTrmNnCs"))
							{
								TermTxNounCaseEln n = new TermTxNounCaseEln(wrd,rule);
								nmLgConcepts=n.getTermsOfTxConceptIfRule(rule);
								displayTxNounCaseEln(n.note,nmLgConcepts);
							}
							else if (rule.startsWith("rlElnTrmNvr"))//this is a tx_verb.
							{
								TermTxVerbEln v = new TermTxVerbEln(wrd,rule);
								nmLgConcepts=v.getTermsOfTxConceptIfRule(rule);
								displayTxVerbEln(wrd,v.note,nmLgConcepts);
							}
							else if (rule.startsWith("rlElnTrmAdj"))
							{
								TermTxNounAdjectiveEln a = new TermTxNounAdjectiveEln(wrd,rule);
								nmLgConcepts=a.getTermsOfTxConceptIfRuleOnly(rule);
								displayTxNounAdjectiveEln(a.note,nmLgConcepts);
							}
						}
					}

					else //we are forced to use a rule:
					{
						if (ruleOnly.startsWith("rlElnTrmNnCs"))
						{
							TermTxNounCaseEln n = new TermTxNounCaseEln(wrd,ruleOnly);
							nmLgConcepts=n.getTermsOfTxConceptIfRuleOnly(ruleOnly);
							displayTxNounCaseEln(n.note,nmLgConcepts);
						}
						else if (ruleOnly.startsWith("rlElnTrmVbr"))
						{
							TermTxVerbEln v = new TermTxVerbEln(wrd,ruleOnly);
							nmLgConcepts=v.getTermsOfTxConceptIfRuleOnly(ruleOnly);
							displayTxVerbEln(wrd,v.note,nmLgConcepts);
						}
						else if (ruleOnly.startsWith("rlElnTrmAdj"))//2001.05.26
						{
							TermTxNounAdjectiveEln a = new TermTxNounAdjectiveEln(wrd,ruleOnly);
							nmLgConcepts=a.getTermsOfTxConceptIfRuleOnly(ruleOnly);
							displayTxNounAdjectiveEln(a.note,nmLgConcepts);
						}
					}
				}

				else if (lango.equals(AAj.rbLabels.getString("Esperanto")) )
				{
					if (!ruleOnly.equals(""))
					{
						if (ruleOnly.equals("CaseTxNoun")){
							TermTxNounCaseEpo n = new TermTxNounCaseEpo(wrd);
							nmLgConcepts=n.findTermsOfTxConceptIfName();
							displayTxNounCase(nmLgConcepts);
						}
						else if (ruleOnly.equals("TxVerb")){
							TermTxVerbEpo v = new TermTxVerbEpo(wrd);
							nmLgConcepts=v.findTermsOfTxConceptIfName();
							displayTxVerb(nmLgConcepts);
						}
						else if (ruleOnly.equals("AdjectiveTxNoun")){
							TermTxNounAdjectiveEpo a = new TermTxNounAdjectiveEpo(wrd);
							nmLgConcepts=a.findTermsOfTxConceptIfName();
							displayTxNounAdjective(nmLgConcepts);
						}
						else if (ruleOnly.equals("AdverbTxNoun")){
							TermTxNounAdverbEpo n = new TermTxNounAdverbEpo(wrd);
							nmLgConcepts=n.findTermsOfTxConceptIfName();
							displayTxNounAdverb(nmLgConcepts);
						}
						else {
							JOptionPane.showMessageDialog(null,
								"The rules are: CaseTxNoun, TxVerb, TxNounAdjective, TxNounAdverb.");
						}
					}

					else {
						if (rule.equals("none")){
							//noun
							if (wrd.endsWith("o"))
							{
								TermTxNounCaseEpo n = new TermTxNounCaseEpo(wrd);
								nmLgConcepts=n.findTermsOfTxConceptIfName();
								displayTxNounCase(nmLgConcepts);
							}
							//verb
							else if (wrd.endsWith("i"))
							{
								TermTxVerbEpo n = new TermTxVerbEpo(wrd);
								nmLgConcepts=n.findTermsOfTxConceptIfName();
								displayTxVerb(nmLgConcepts);
							}
							//adjective
							else if (wrd.endsWith("a"))
							{
								TermTxNounAdjectiveEpo n = new TermTxNounAdjectiveEpo(wrd);
								nmLgConcepts=n.findTermsOfTxConceptIfName();
								displayTxNounAdjective(nmLgConcepts);
							}
							//adverb
							else if (wrd.endsWith("e"))
							{
								TermTxNounAdverbEpo n = new TermTxNounAdverbEpo(wrd);
								nmLgConcepts=n.findTermsOfTxConceptIfName();
								displayTxNounAdverb(nmLgConcepts);
							}
						}
						if (rule.equals("CaseTxNoun"))
						{
							if ( !wrd.endsWith("o") )
							JOptionPane.showMessageDialog(null,
								"ALL esperanto GENERIC-NOUNS end with -o.");
							TermTxNounCaseEpo n = new TermTxNounCaseEpo(wrd);
							nmLgConcepts=n.findTermsOfTxConceptIfName();
							displayTxNounCase(nmLgConcepts);
						}
						else if (rule.equals("TxVerb"))
						{
							if ( !wrd.endsWith("i") )
							JOptionPane.showMessageDialog(null,
								"ALL esperanto GENERIC-VERBS end with -i.");
							TermTxVerbEpo v = new TermTxVerbEpo(wrd);
							nmLgConcepts=v.findTermsOfTxConceptIfName();
							displayTxVerb(nmLgConcepts);
						}
						else if (rule.equals("AdjectiveTxNoun"))
						{
							if ( !wrd.endsWith("a") )
							JOptionPane.showMessageDialog(null,
								"ALL esperanto GENERIC-ADJECTIVES end with -a.");
							TermTxNounAdjectiveEpo a = new TermTxNounAdjectiveEpo(wrd);
							nmLgConcepts=a.findTermsOfTxConceptIfName();
							displayTxNounAdjective(nmLgConcepts);
						}
						else if (rule.equals("AdverbTxNoun"))
						{
							if ( !wrd.endsWith("e") )
							JOptionPane.showMessageDialog(null,
								"ALL esperanto DERIVED-GENERIC-ADVERBS end with -e.");
							TermTxNounAdverbEpo n = new TermTxNounAdverbEpo(wrd);
							nmLgConcepts=n.findTermsOfTxConceptIfName();
							displayTxNounAdverb(nmLgConcepts);
						}
					}
				}

				else if (lango.equals(AAj.rbLabels.getString("Komo")) )
				{
					if (!ruleOnly.equals(""))
					{
						if (ruleOnly.equals("CaseTxNoun")){
							TermTxNounCaseKml n = new TermTxNounCaseKml(wrd);
							nmLgConcepts=n.findTermsOfTxConceptIfName();
							displayTxNounCase(nmLgConcepts);
						}
						else if (ruleOnly.equals("TxVerb")){
							TermTxVerbKml v = new TermTxVerbKml(wrd);
							nmLgConcepts=v.findTermsOfTxConceptIfName();
							displayTxVerb(nmLgConcepts);
						}
						else {
							JOptionPane.showMessageDialog(null,
								"The rules are: CaseTxNoun, TxVerb.");
						}
					}

					else {
						if (rule.equals("none")){
							//noun
							if (wrd.endsWith("o"))
							{
								TermTxNounCaseKml n = new TermTxNounCaseKml(wrd);
								nmLgConcepts=n.findTermsOfTxConceptIfName();
								displayTxNounCase(nmLgConcepts);
							}
							//verb
							else if (wrd.endsWith("an"))
							{
								TermTxVerbKml n = new TermTxVerbKml(wrd);
								nmLgConcepts=n.findTermsOfTxConceptIfName();
								displayTxVerb(nmLgConcepts);
							}
							else {
								JOptionPane.showMessageDialog(null,
									"The komo GENERIC-NOUNS|VERBS end with -o|an.");
							}
						}
						if (rule.equals("CaseTxNoun"))
						{
							if ( !wrd.endsWith("o") )
							JOptionPane.showMessageDialog(null,
								"ALL komo GENERIC-NOUNS end with -o.");
							TermTxNounCaseKml n = new TermTxNounCaseKml(wrd);
							nmLgConcepts=n.findTermsOfTxConceptIfName();
							displayTxNounCase(nmLgConcepts);
						}
						else if (rule.equals("TxVerb"))
						{
							if ( !wrd.endsWith("an") )
							JOptionPane.showMessageDialog(null,
								"ALL komo GENERIC-VERBS end with -an.");
							TermTxVerbKml v = new TermTxVerbKml(wrd);
							nmLgConcepts=v.findTermsOfTxConceptIfName();
							displayTxVerb(nmLgConcepts);
						}
					}
				}
			}
		}
	}


	/**
	 *
	 * @modified 2008.04.23
	 * @since 2008.04.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void displayTxNounCase(String terms)
	{
		areaForms.setText("The TERMS of this case--tx-noun are:\n");

		String n[] = terms.split(",");
		for(int i=0; i<n.length; i++)
			areaForms.append("\n\t" +n[i]);
	}


	/**
	 * Displays the greek tx_nounCase that denotes the CONCRETE
	 * langoemo-tx_nounCase.
	 *
	 * @modified 2006.02.06
	 * @since 2001.04.13
	 * @author HoKoNoUmo
	 */
	public void displayTxNounCaseEln(String note, String trms)
	{
		//the termTxCpt_sAll are 7 + the article.
		String an[] = trms.split(",");

		if (note.length()!=0)
		{
			areaForms.setText("ΠΑΡΑΤΗΡΗΣΗ: "+note);
			areaForms.append("\nΟι ΤΥΠΟΙ αυτού του ουσιαστικού είναι:");
		}
		else areaForms.setText("Οι ΤΥΠΟΙ αυτού του ουσιαστικού είναι:\n");
		if (an[0].equals("ο"))
		{
			areaForms.append("\n\tο\t" +an[1]);
			areaForms.append("\n\tτου\t" +an[2]);
			areaForms.append("\n\tτον\t" +an[3]);
			areaForms.append("\n\t\t" +an[4]);
			areaForms.append("\n\tοι\t" +an[5]);
			areaForms.append("\n\tτων\t" +an[6]);
			areaForms.append("\n\tτους\t" +an[7]);
			areaForms.append("\n\t\t" +an[5]);
		}
		else if (an[0].equals("η"))
		{
			areaForms.append("\n\tη\t" +an[1]);
			areaForms.append("\n\tτης\t" +an[2]);
			areaForms.append("\n\tτην\t" +an[3]);
			areaForms.append("\n\t\t" +an[4]);
			areaForms.append("\n\tοι\t" +an[5]);
			areaForms.append("\n\tτων\t" +an[6]);
			areaForms.append("\n\tτις\t" +an[7]);
			areaForms.append("\n\t\t" +an[5]);
		}
		else if (an[0].equals("το"))
		{
			areaForms.append("\n\tτο\t" +an[1]);
			areaForms.append("\n\tτου\t" +an[2]);
			areaForms.append("\n\tτο\t" +an[3]);
			areaForms.append("\n\t\t" +an[4]);
			areaForms.append("\n\tτα\t" +an[5]);
			areaForms.append("\n\tτων\t" +an[6]);
			areaForms.append("\n\tτα\t" +an[7]);
			areaForms.append("\n\t\t" +an[5]);
		}
	}

	/**
	 * Displays the termTxCpt_sAll of an english tx-nounCase.
	 * @modified 2008.12.02
	 * @since 2001.04.13
	 * @author HoKoNoUmo
	 */
	public void displayTxNounCaseEng(String note, String trms)
	{
		//all tx_nounCases have 4 termTxCpt_sAll.
		if (note.length()!=0)
		{
			areaForms.setText("NOTE: "+note);
			areaForms.append("\nThe TERMS of this case--tx-noun are:");
		}
		else areaForms.setText("The TERMS of this case--tx-noun are:\n");

		String an[] = trms.split(",");
		areaForms.append("\n\t" +an[0]);
		areaForms.append("\n\t" +an[1]);
		areaForms.append("\n\t" +an[2]);
		areaForms.append("\n\t" +an[3]);
	}


	/**
	 *
	 * @modified 2008.04.23
	 * @since 2008.04.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void displayTxVerb(String terms)
	{
		areaForms.setText("The TERMS of this tx_verb are:\n");

		String n[] = terms.split(",");
		for(int i=0; i<n.length; i++)
			areaForms.append("\n\t" +n[i]);
	}


	/**
	 * @modified
	 * @since 2001.04.04
	 * @author HoKoNoUmo
	 */
	public void displayTxVerbEln(String word, String note, String trms)
	{
		String wrd=word;
		String nmLgConcepts=trms;

		StringTokenizer parser = new StringTokenizer(nmLgConcepts, "#");
		int tokens=parser.countTokens();
		try {
			if (note.length()!=0) areaForms.setText("ΠΑΡΑΤΗΡΗΣΗ: "+note);
			else areaForms.setText("Οι διαφορετικοί ΤΥΠΟΙ-ΚΛΙΣΗΣ είναι:");
			//on different rules we can set different labels:
			if ( tokens==14 )
			{
				areaForms.append("\nΕΝΕΡΓΗΤΙΚΗ-ΦΩΝΗ:");
				areaForms.append("\nΕνεστ-Οριστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΕνεστ-Προστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΠαρατατικός: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόρισ-Οριστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόριστ-Υποτ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόρισ-Προστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΜετοχή:      ");
				areaForms.append((String)parser.nextElement());

//									areaForms.append("\n");
				areaForms.append("\nΠΑΘΗΤΙΚΗ-ΦΩΝΗ:");
				areaForms.append("\nΕνεστ-Οριστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΕνεστ-Προστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΠαρατατικός: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόρισ-Οριστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόριστ-Υποτ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόρισ-Προστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΜετοχή:	      ");
				areaForms.append((String)parser.nextElement());
			}

			else if ( tokens==7 )
			{
				if ( wrd.endsWith("ω")||wrd.endsWith("ώ") )
					areaForms.append("\nΕΝΕΡΓΗΤΙΚΗ-ΦΩΝΗ:");
				else
					areaForms.append("\nΠΑΘΗΤΙΚΗ-ΦΩΝΗ:");
				areaForms.append("\nΕνεστ-Οριστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΕνεστ-Προστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΠαρατατικός: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόρισ-Οριστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόριστ-Υποτ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΑόρισ-Προστ: ");
				areaForms.append((String)parser.nextElement());
				areaForms.append("\nΜετοχή:      ");
				areaForms.append((String)parser.nextElement());
			}
			else {
				//one line.
				areaForms.append("\n" +nmLgConcepts);
			}
		}
		catch (NoSuchElementException nsee)
		{
			System.out.println("EXCEPTION: in display tx_verb");
		}
	}


	/**
	 * @modified
	 * @since 2001.04.13
	 * @author HoKoNoUmo
	 */
	public void displayTxVerbEng(String note, String trms)
	{
		StringTokenizer parser = new StringTokenizer(trms, ",");

		try {
			if (note.length()!=0)
			{
				areaForms.setText("NOTE: "+note);
				areaForms.append("\nThe DIFFERENT-TERMS of this tx_verb are:");
			}
			else areaForms.setText("he DIFFERENT-TERMS of this tx_verb are:\n");

			while (parser.hasMoreTokens())
			{
				areaForms.append("\n\t" +(String)parser.nextElement());
			}
		}
		catch (NoSuchElementException nsee)
		{
			System.out.println("EXCEPTION: in display english-tx_verb");
		}
	}


	/**
	 *
	 * @modified 2008.04.24
	 * @since 2008.04.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void displayTxNounAdjective(String terms)
	{
		areaForms.setText("The TERMS of this adjective are:\n");

		String n[] = terms.split(",");
		for(int i=0; i<n.length; i++)
			areaForms.append("\n\t" +n[i]);
	}


	/**
	 * @modified 2001.05.21
	 * @since 2001.05.18
	 * @author HoKoNoUmo
	 */
	public void displayTxNounAdjectiveEln(String note, String trms)
	{
		String nmLgConcepts=trms;
		StringTokenizer parser = new StringTokenizer(nmLgConcepts, "#");
		int tokens=parser.countTokens();
		String nmrMal="",nmrFem="",nmrNeu="",trm4="",trm5="",trm6="",trm7="",trm8="",trm9="";
		if (note.length()!=0) areaForms.setText("ΠΑΡΑΤΗΡΗΣΗ: "+note);
		else areaForms.setText("Οι ΤΥΠΟΙ του είναι:");

		if (tokens==3)
		{
			nmrMal = (String)parser.nextElement();
			nmrFem = (String)parser.nextElement();
			nmrNeu = (String)parser.nextElement();
			displayTxNounAdjectiveOneParathetiko(nmrMal,nmrFem,nmrNeu);
		}
		else if (tokens==6)
		{
			nmrMal = (String)parser.nextElement();
			nmrFem = (String)parser.nextElement();
			nmrNeu = (String)parser.nextElement();
			displayTxNounAdjectiveOneParathetiko(nmrMal,nmrFem,nmrNeu);
			areaForms.append("\n");
			trm4 = (String)parser.nextElement();
			trm5 = (String)parser.nextElement();
			trm6 = (String)parser.nextElement();
			displayTxNounAdjectiveOneParathetiko(trm4,trm5,trm6);
		}
		else if (tokens==9)
		{
			nmrMal = (String)parser.nextElement();
			nmrFem = (String)parser.nextElement();
			nmrNeu = (String)parser.nextElement();
			displayTxNounAdjectiveOneParathetiko(nmrMal,nmrFem,nmrNeu);
			areaForms.append("\n");
			trm4 = (String)parser.nextElement();
			trm5 = (String)parser.nextElement();
			trm6 = (String)parser.nextElement();
			displayTxNounAdjectiveOneParathetiko(trm4,trm5,trm6);
			areaForms.append("\n");
			trm7 = (String)parser.nextElement();
			trm8 = (String)parser.nextElement();
			trm9 = (String)parser.nextElement();
			displayTxNounAdjectiveOneParathetiko(trm7,trm8,trm9);
		}
	}


	/**
	 * Displays the termTxCpt_sAll of an english tx-nounAdjective.
	 * @modified 2006.02.05
	 * @since 2001.05.27
	 * @author HoKoNoUmo
	 */
	public void displayTxNounAdjectiveEng(String note, String trms)
	{
		//all tx_nounAdjectives have 3 termTxCpt_sAll
		if (note.length()!=0)
		{
			areaForms.setText("NOTE: "+note);
			areaForms.append("\nThe TERMS of this tx_nounAdjective are:");
		}
		else areaForms.setText("The TERMS of this tx_nounAdjective are:\n");

		String an[] = trms.split(",");
		areaForms.append("\n\t" +an[0]);
		areaForms.append("\n\t" +an[1]);
		areaForms.append("\n\t" +an[2]);
	}


	/**
	 * Displays one degree of a greek adnauner in three genders.
	 *
	 * @param nmrMal	The string with the male terms.
	 * @param nmrFem	The string with the female terms
	 * @param nmrNeu	The string with the neuter terms
	 * @modified 2008.04.22
	 * @since 2001.05.24
	 * @author HoKoNoUmo
	 */
	public void displayTxNounAdjectiveOneParathetiko(String nmrMal,
													String nmrFem, String nmrNeu)
	{
		String[] cases = new String[8];
		String an1[] = nmrMal.split(",");
		//find max length
		cases[0]="\n\tο    " +an1[1];
		int max=cases[0].length();
		cases[1]="\n\tτου  " +an1[2];
		if (cases[1].length()>max) max=cases[1].length();
		cases[2]="\n\tτον  " +an1[3];
		if (cases[2].length()>max) max=cases[2].length();
		cases[3]="\n\t     " +an1[4];
		if (cases[3].length()>max) max=cases[3].length();
		cases[4]="\n\tοι   " +an1[5];
		if (cases[4].length()>max) max=cases[4].length();
		cases[5]="\n\tτων  " +an1[6];
		if (cases[5].length()>max) max=cases[5].length();
		cases[6]="\n\tτους " +an1[7];
		if (cases[6].length()>max) max=cases[6].length();
		cases[7]="\n\t     " +an1[5];
		if (cases[7].length()>max) max=cases[7].length();
		String an2[] = nmrFem.split(",");
		cases[0]=cases[0] +"," +setSpaces(max+1-cases[0].length()) +"η   " +an2[1];
		int max2=cases[0].length();
		cases[1]=cases[1] +"," +setSpaces(max+1-cases[1].length()) +"της " +an2[2];
		if (cases[1].length()>max2) max2=cases[1].length();
		cases[2]=cases[2] +"," +setSpaces(max+1-cases[2].length()) +"την " +an2[3];
		if (cases[2].length()>max2) max2=cases[2].length();
		cases[3]=cases[3] +"," +setSpaces(max+1-cases[3].length()) +"    " +an2[4];
		if (cases[3].length()>max2) max2=cases[3].length();
		cases[4]=cases[4] +"," +setSpaces(max+1-cases[4].length()) +"οι  " +an2[5];
		if (cases[4].length()>max2) max2=cases[4].length();
		cases[5]=cases[5] +"," +setSpaces(max+1-cases[5].length()) +"των " +an2[6];
		if (cases[5].length()>max2) max2=cases[5].length();
		cases[6]=cases[6] +"," +setSpaces(max+1-cases[6].length()) +"τις " +an2[7];
		if (cases[6].length()>max2) max2=cases[6].length();
		cases[7]=cases[7] +"," +setSpaces(max+1-cases[7].length()) +"    " +an2[5];
		if (cases[7].length()>max2) max2=cases[7].length();
		String an3[] = nmrNeu.split(",");
		cases[0]=cases[0] +"," +setSpaces(max2+1-cases[0].length()) +"το  " +an3[1];
		cases[1]=cases[1] +"," +setSpaces(max2+1-cases[1].length()) +"του " +an3[2];
		cases[2]=cases[2] +"," +setSpaces(max2+1-cases[2].length()) +"το  " +an3[3];
		cases[3]=cases[3] +"," +setSpaces(max2+1-cases[3].length()) +"    " +an3[4];
		cases[4]=cases[4] +"," +setSpaces(max2+1-cases[4].length()) +"τα  " +an3[5];
		cases[5]=cases[5] +"," +setSpaces(max2+1-cases[5].length()) +"των " +an3[6];
		cases[6]=cases[6] +"," +setSpaces(max2+1-cases[6].length()) +"τα  " +an3[7];
		cases[7]=cases[7] +"," +setSpaces(max2+1-cases[7].length()) +"    " +an3[5];
		try {
			for(int i = 0; i < 8; i++) areaForms.append(cases[i]);
		}
		catch (NoSuchElementException nsee)
		{
			System.out.println("EXCEPTION: in display tx_nounAdjective");
		}
		areaForms.setCaretPosition(1);
	}


	/**
	 *
	 * @modified 2008.04.23
	 * @since 2008.04.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void displayTxNounAdverb(String terms)
	{
		areaForms.setText("The TERMS of this tx_adverb are:\n");

		String n[] = terms.split(",");
		for(int i=0; i<n.length; i++)
			areaForms.append("\n\t" +n[i]);
	}


	/**
	 * @modified
	 * @since 2001.05.20
	 * @author HoKoNoUmo
	 */
	public String setSpaces(int max)
	{
		String space = "";
		for (int j=0; j<max; j++)
			space=space +" ";
		return space;
	}

}