/**
 * @modified 2003.11.19
 * @since 2003.11.19|1999.02.19 (v00.02.00)
 * @author HoKoNoUmo
 */
package pk_Util;

import java.util.ListResourceBundle;

public class BundleMenus_el_GR
	extends ListResourceBundle {

	public Object[][] getContents() {
 		return contents;
	}

	static final Object[][] contents = {
		/* */
		/* SYSTEM MENU */
		/* */
		{"systemMenu",			"Πρόγραμμα"},
		{"systemHelp",			"Functions Related to AAj"},
		{"aboutMI",				"Σχετικά με το Πρόγραμμα/Κατασκευαστή"},
		{"aboutHelp",			"VIEW information about the creation of the Program and its Author"},
		/*separator */
		{"exitMI",					"Έξοδος"},
		{"exitHelp",				"Program Exit"},

		/* */
		/* BUILD-KNOWLEDGEbASE MENU */
		/* */
		{"menuBuild",			"Κατασκευή-ΒάσηςΓνώσεων"},
		/*jMenuBuild=Καταχώρηση-Γνώσης */
		{"buildHelp",			"Λειτουργίες σχετικές με τη δημιουργία Εννοιακού-Μοντέλου"},
		{"editorMI",				"Τροποποίησε την Παρούσα Έννοια"},
		{"editorHelp",			"A simple XML-Editor for the sensorial-b-concepts"},
		{"editorTip",			"Αλλαξε τον κώδικα της παρούσας έννοιας"},

		/* separator */
		{"menuIntegrate",				"Ολοκλήρωση"},
		{"integrateHelp",				"Make CONSISTENT the current-xcpt's relations with its attributes"},
		{"totalIntegrationMI",		"Συνολική Ολοκλήρωση"},
		{"totalIntegrationHelp",	"Do ALL the types of integrations"},
		{"parIntegrationMI",			"Μερών Ολοκλήρωση"},
		{"parIntegrationHelp",		"Check if the PARTS of current-xcpt have as WHOLE the current-xcpt"},
		{"whoIntegrationMI",			"Όλων Ολοκλήρωση"},
		{"whoIntegrationHelp",		"Check if the WHOLE-cpts have as PARTS the current-xcpt ..."},
		{"genIntegrationMI",			"Γενικών Ολοκλήρωση"},
		{"genIntegrationHelp",		"Check if all the parts of a GENERIC-xcpt are parts of the current-xcpt ..."},
		{"sibIntegrationMI",			"Αδελφών Ολοκλήρωση"},
		{"subIntegrationMI",			"Ειδικών Ολοκλήρωση"},
		{"subIntegrationHelp",		"Check if the SPECIFICS have all the PARTS of the current-xcpt ..."},
		{"envIntegrationMI",			"Περιβάλλοντος Ολοκλήρωση"},
		{"envIntegrationHelp",		"Check if the ENVIRONMENT-XCPTS have in their environment the current-xcpt ..."},

		{"jmiBuildChangeSubWorldviewMI", "Change SubWorldView"},
		{"jmiBuildChangeSubWorldviewHelp", "Change sub-worldview of current-xcpt"},

		/* separator */
		{"addCptMenu",						"Πρόσθεσε Έννοια"},
		{"addCptHelp",						"Add a NEW/EXISTING Concept to Current-xcpt"},
		{"addNewMenu",						"ΝΕΑ στην παρούσα"},
		{"addNewHelp",						"Add a NEW-XCPT Related to Current-xcpt"},
		{"addNewParMI",						"Πρόσθεσε ΝΕΑ Έννοια Μέρος"},
		{"addNewParHelp",					"Add a NEW-XCPT as PART of the Current-xcpt"},
		{"addNewParTip",					"Πρόσθεσε ΝΕΑ Έννοια Μέρος"},
		{"addNewWhoMI",						"Πρόσθεσε ΝΕΑ Έννοια Όλο"},
		{"addNewWhoHelp",					"Add a NEW-XCPT as WHOLE to the Current-xcpt"},
		{"addNewWhoTip",					"Πρόσθεσε ΝΕΑ Έννοια Όλο"},
		{"addNewGenMI",						"Πρόσθεσε ΝΕΑ Γενική Έννοια"},
		{"addNewGenHelp",					"Add a NEW-XCPT as GENERIC to the Current-xcpt"},
		{"addNewGenTip",					"Πρόσθεσε ΝΕΑ Γενική Έννοια"},
		{"addNewSibMI",						"Πρόσθεσε ΝΕΑ Αδελφή Έννοια"},
		{"addNewSibTip",					"Πρόσθεσε ΝΕΑ Αδελφή Έννοια"},
		{"addNewSpeMI",						"Πρόσθεσε ΝΕΑ Ειδική Έννοια"},
		{"addNewSpeHelp",					"Add a NEW-XCPT as SPECIFIC to the Current-xcpt"},
		{"addNewSpeTip",					"Πρόσθεσε ΝΕΑ Ειδική Έννοια"},
		{"addNewEnvMI",						"Πρόσθεσε ΝΕΑ Σχέση"},
		{"addNewEnvHelp",					"Add a NEW-XCPT as ENVIRONMENT to the Current-xcpt"},
		{"addNewEnvTip",					"Πρόσθεσε ΝΕΑ Σχέση"},
		{"addNewAtrMI",					"As ATTRIBUTE"},
		{"addNewAtrHelp",				"Add NEW sr-concept as ATTRIBUTE to current sr-concept"},
		{"addNewAtrTip",					"Add NEW sr-concept as ATTRIBUTE"},
		/* */
		{"addExistingMenu",			"ΥΠΑΡΧΟΥΣΑ στην παρούσα"},
		{"addExistingHelp",			"Add EXISTING-XCPT to Current-xcpt"},
		{"addParMI",						"Πρόσθεσε Έννοια Μέρος"},
		{"addParHelp",					"Add EXISTING-XCPT as PART to Current-xcpt"},
		{"addParTip",						"Πρόσθεσε Έννοια Μέρος"},
		{"addWhoMI",						"Πρόσθεσε Έννοια Όλο"},
		{"addWhoHelp",					"Add EXISTING-XCPT as WHOLE to Current-xcpt"},
		{"addWhoTip",						"Πρόσθεσε Έννοια Όλο"},
		{"addGenMI",						"Πρόσθεσε Γενική Έννοια"},
		{"addGenHelp",					"Add EXISTING-XCPT as GENERIC to Current-xcpt"},
		{"addGenTip",						"Πρόσθεσε Γενική Έννοια"},
		{"addSibMI",						"Πρόσθεσε Αδελφή Έννοια"},
		{"addSibTip",						"Πρόσθεσε Αδελφή Έννοια"},
		{"menuAddAttSpecificMI",						"Πρόσθεσε Ειδική Έννοια"},
		{"menuAddAttSpecificHelp",					"Add EXISTING-XCPT as SPECIFIC to Current-xcpt"},
		{"menuAddAttSpecificTip",						"Πρόσθεσε Ειδική Έννοια"},
		{"addEnvMI",						"Πρόσθεσε Σχέση"},
		{"addEnvHelp",					"Add EXISTING-XCPT as ENVIRONMENT to Current-xcpt"},
		{"addEnvTip",						"Πρόσθεσε Σχέση"},
		{"addUnrelatedMI",			"ΑΣΧΕΤΗ με την παρούσα"},
		{"addUnrelatedHelp",		"Add UNRELATED-XCPT to Current-xcpt"},
		{"addUnrelatedTip",			"Πρόσθεσε ΑΣΧΕΤΗ-ΕΝΝΟΙΑ με την παρούσα"},
		/* */
		{"delCptMenu",					"ΔΙΕΓΡΑΨΕ Έννοια"},
		{"delCptHelp",					"DELETE a Concept from a SrΚοσμοθερώρηση"},

		{"delCrtCptMI",					"Την ΠΑΡΟΥΣΑ Ένοια"},
		{"delCrtCptHelp",				"DELETE the Current-Cpt"},
		{"delCrtCptTip",				"ΔΙΕΓΡΑΨΕ την παρούσα ένοια"},

		{"delRelatedMenu",			"ΣΧΕΤΙΖΟΜΕΝΗ Έννοια"},
		{"delRelatedHelp",			"DELETE Related-xcpt to Current"},
		{"delParMI",						"Διέγραψε Έννοια ΧΑΡΑΚΤΗΡΙΣΤΙΚΟ"},
		{"delParHelp",					"DELETE a PART of the Current-xcpt"},
		{"delParTip",						"Διέγραψε Έννοια ΧΑΡΑΚΤΗΡΙΣΤΙΚΟ"},
		{"delWhoMI",						"Διέγραψε ΟΛΟΤΗΤΑ Έννοια"},
		{"delWhoHelp",					"DELETE a WHOLE of the Current-xcpt"},
		{"delWhoTip",						"Διέγραψε ΟΛΟΤΗΤΑ Έννοια"},
		{"delGenMI",						"Διέγραψε ΓΕΝΙΚΗ Έννοια"},
		{"delGenHelp",					"DELETE a GENERIC of the Current-xcpt"},
		{"delGenTip",						"Διέγραψε ΓΕΝΙΚΗ Έννοια"},
		{"delSibMI",						"Διέγραψε ΑΔΕΛΦΗ Έννοια"},
		{"delSibTip",						"Διέγραψε ΑΔΕΛΦΗ Έννοια"},
		{"delSubMI",						"Διέγραψε ΜΕΡΙΚΗ Έννοια"},
		{"delSubHelp",					"DELETE a SPECIFIC of the Current-xcpt"},
		{"delSubTip",						"Διέγραψε ΜΕΡΙΚΗ Έννοια"},
		{"delDimMI",						"Διέγραψε ΔΙΑΜΕΡΙΣΜΟ"},
		{"delDimTip",						"Διέγραψε ΔΙΜΕΡΙΣΜΟ"},
		{"delEnvMI",						"Διέγραψε ΠΕΡΙΒΑΛΛΟΝΤΟΣ Έννοια"},
		{"delEnvHelp",					"DELETE an ENVIRONMENT of the Current-xcpt"},
		{"delEnvTip",						"Διέγραψε ΠΕΡΙΒΑΛΛΟΝΤΟΣ Έννοια"},

		{"delUnrelatedMI",				"ΑΣΧΕΤΗ με την παρούσα"},
		{"delUnrelatedHelp",			"DELETE an xcpt UNRELATED to current-xcpt"},
		{"delUnrelatedTip",				"Διέγραψε Έννοια ΑΣΧΕΤΗ με την παρούσα"},

		/* separator: Μερών commands */
		{"parOverrideMI",					"Συγκεκριμενοποίησε Το Επιλεγμένο Μέρος"},
		{"parOverrideHelp",				"Specialize an Inherited Part-xcpt"},
		{"parGeneralizeMI",				"Γενίκευσε Το Επιλεγμένο Μέρος"},
		{"parGeneralizeHelp",			"Make the Selected-Part-xcpt, a Part-xcpt of Current's-Generic"},
		{"parMakeInheritedMI",			"Κάνε Μέρος Κληρονομημένο"},
		{"parMakeInheritedHelp",		"Make the Selected-Part-xcpt, inherited of an EXISTING part-xcpt of Current's-Generic"},

		/* separator */
		{"jmiAddDivisionSpecificMI",					"Πρόσθεσε Διαίρεση-Ειδικών ως προς ένα Χαρακτηριστικό"},
		{"jmiAddDivisionSpecificHelp",				"Add a Set-of-Specifics that include ALL the Concrete-cpts of Current-xcpt"},

		/* separator */
		{"nameMgtMI",							"Διαχείριση Ονομάτων"},
		{"nameMgtHelp",						"Πρόσθεση/Αντικατάσταση/Διαγραφή ονομάτων της παρούσας-έννοιας ή Αλλαγή του ονόματός της"},
		{"updateTermFilesMI",			"Ενημέρωση Όρων"},
		{"updateTermFilesHelp",		"Rewrite the LgConceptTerms of the Current-xcpt in term.index.xml files"},
		{"tmrKmlMgtMI",						"Διαχείριση Όρων της Κόμο"},
		{"tmrKmlMgtHelp",					"Manage(Create/Validate) Komo-Terms"},

		/* separator */
		{"addSrWorldviewMI",				"Πρόσθεσε Κοσμοθερώρηση"},
		{"addSrWorldviewHelp",			"Πρόσθεσε ΑισθητήΚοσμοθερώρηση"},
		{"addSrSubWorldviewMI",		"Πρόσθεσε ΥποΚοσμοθεώρηση"},
		{"addSrSubWorldviewHelp",	"Πρόσθεσε ΑισθητήΥποΚοσμοθερώρηση"},
		{"setKBAuthorMI",					"Αλλαξε Συγγραφέα Βάσης"},
		{"setKBAuthorHelp",				"Όρισε Καινούργιο Συγγραφέα για τη Βάση Γνώσεων"},
		{"saveVariablesMI",				"Αποθήκευση Μεταβλητών"},
		{"saveVariablesHelp",			"Save the last values of AAj-Variables in case of an abnormal-exit"},

		{"copy-to-clipboardMI",		"Αντιγραφή"},
		{"copy-to-clipboardTip",		"Αντιγραφή στο Σημειωματάριο"},

		/* */
		/* RETRIEVE MENU */
		/* */
		{"menuRetrieve",						"Ανάκτηση-ΒάσηςΓνώσεων"},
		{"retrieveHelp",						"Λειτουργίες σχετικές με την Ανάκτηση ΑισθητώνΚοσμοθεωρήσεων"},
		{"jmiRetrieveBack1MI",			"Προηγούμενη Έννοια"},
		{"jmiRetrieveBack1Help",		"Display the Previous Concept"},
		{"jmiRetrieveBack1Tip",		"Δές την Προηγούμενη Έννοια"},
		{"jmiRetrieveBack2MI",			"Προηγούμενες Έννοιες"},
		{"jmiRetrieveBack2Help",		"View the list with the visited-cpts"},
		{"jmiRetrieveBack2Tip",		"Δές την Προηγούμενες Έννοιες"},
		{"refreshMI",							"Ανανέωση"},
		{"refreshHelp",						"Redisplay the Current-xcpt"},
		{"refreshTip",							"Δές ξανά την έννοια μετά την τροποποίηση του κώδικά της"},

		/* separator */
		{"getSrCptInfoMI",					"Έννοιας Πληροφορίες"},
		{"getSrCptInfoHelp",				"View Information (statistics and more) about the Current-xcpt"},
		{"viewAllMI",							"Δές όλες τις Δομές-Δένδρου"},
		{"viewAllHelp",						"Δές τις οργανώσεις-ιεραρχίας της παρούσας έννοιας"},
		{"viewAllSpesMI",					"Δες Δομή-Δένδρου ΕΙΔΙΚΩΝ"},
		{"viewAllSpesHelp",				"Δες τις ειδικές-έννοιες της παρούσας και αυτών τις ειδικές κ.ο.κ."},
		{"viewAllSpesTip",					"Δες τις ειδικές-έννοιες της παρούσας και αυτών τις ειδικές κ.ο.κ."},
		{"viewAllGensMI",					"Δες Δομή-Δένδρου ΓΕΝΙΚΩΝ"},
		{"viewAllGensHelp",				"Δες τις γενικές-έννοιες της παρούσας και αυτών τις γενικές κ.ο.κ."},
		{"viewAllParsMI",					"Δες Δομή-Δένδρου ΜΕΡΩΝ"},
		{"viewAllParsHelp",				"Δες τις έννοιες-μέρους της παρούσας και αυτών τα μέρη κ.ο.κ."},
		{"viewAllWhosMI",					"Δες Δομή-Δένδρου ΟΛΩΝ"},
		{"viewAllWhosHelp",				"Δες τις έννοιες-όλου της παρούσας και αυτών τις ολικές κ.ο.κ."},
		{"viewSpecificComplMI",		"Δες τις Συμπληρωματικές-Είδους"},
		{"viewSpecificComplHelp",	"VIEW the SPECIFIC-COMPLEMENT of current-xcpt on a generic's-division or generic only."},

		/* separator */
		{"findMenu",							"Βρές Έννοια"},
		{"findHelp",							"Choose a method to FIND a Concept"},
		{"findNameMI",						"Βρές Έννοια από όνομα/συνώνυμα"},
		{"findNameHelp",					"FIND a Concept by searching TERMS (lng/term.index.xml files)"},
		{"findNameTip",						"Βρές Έννοια από όνομα/συνώνυμα"},
		{"findRelatedMI",					"Βρές Έννοια από Σχετικές-Έννοιες"},
		{"findRelatedHelp",				"FIND a Concept by searching RELATED-XCPTS (xcpt.xml files)"},
		{"findRelatedTip",				"Βρές Έννοια από Σχετικές-Έννοιες"},
		{"findIDMI",							"Βρές Έννοια από τον Κωδικό"},
		{"findIDHelp",						"FIND a Concept by searching IDs (lng/term.index.xml files)"},
		{"findIDTip",							"Βρές Έννοια από τον Κωδικό"},
		{"findFormalNameMI",			"Βρές Έννοια από το Τυπικό-Όνομα"},
		{"findFormalNameHelp",		"FIND a Concept by searching FORMAL-NAMES (lng/term.index.xml files)"},
		{"findFormalNameTip",			"Βρές Έννοια από το Τυπικό-Όνομα"},
		{"openMI",								"Ανοιξε Αρχείο-Έννοιας"},
		{"openHelp",							"Find a sensorial-b-concept xml-file from the file-list"},
		{"openTip",								"Ανοιξε μια Καινούργια Έννοια"},
		{"findSelectedMI",				"Βρές την Επιλεγμένη Έννοια"},
		{"findSelectedHelp",			"FIND the SELECTED-XCPT by searching names/termTxCpt_sAll (lng/term.index.xml files)"},
		{"findSelectedTip",				"Βρές την Επιλεγμένη Έννοια"},
		{"findXmlFilesMI",				"Βρές Έννοια ψάχνοντας τα xml-Αρχεία"},
		{"findXmlFilesHelp",			"FIND a Concept by searching the xcpt.xml files for names/termTxCpt_sAll"},
		{"findXmlFilesTip",				"Βρές Έννοια ψάχνοντας τα Αρχεία"},
		{"findFileListMI",				"Βρές Έννοια από Κωδ/Τυπ-Ονόματα"},
		{"findFileListHelp",			"FIND a Concept by searching IDs/FORMALNAMES in xcpt.xml file lists"},
		{"findFileListTip",				"Βρές Έννοια από τους Κωδ/Τυπ-Ον της Λίστας των αρχείων"},
		{"findTxConceptTermsMI",					"Βρές τους ΦΟΡΜΕΡΣ ενός Νέιμερ"},
		{"findTxConceptTermsHelp",				"Βρές τις ΜΟΡΦΕΣ της κλίσης μιας ΛΕΞΗΣ"},

		{"askAAjMI",				"Ρώτα το AAj"},
		{"askAAjHelp",			"ASK a question (Is A a B?, Is A part of B?)"},
		{"askAAjTip",			"Ρώτα το AAj"},
		{"transMI",					"Μετέφρασε"},
		{"transHelp",				"Μετάφραση Εννοιακής-Πληροφορίας και Λόγου"},

		{"getKBInfoMI",			"Δες Πληροφορία για τη Βάση"},
		{"getKBInfoHelp",		"VIEW concept-statistics of XKnowledgeBase"},
		{"getKBAuthorMI",				"Συγγραφέας Βάσης"},
		{"getKBAuthorHelp",			"VIEW the current AUTHOR"},

		{"langMenu",				"Γλώσσα"},
		{"langHelp",				"CHOOSE the Human-Language to work with the program and its Knowledge"},
		{"englishMI",			"Αγγλική Γλώσσα"},
		{"greekMI",				"Ελληνική Γλώσσα"},
		{"esperantoMI",		"Εσπεράντο Γλώσσα"},
		{"komoMI",					"Κοινή Γλώσσα"},


		/* */
		/* HELP MENU */
		/* */
		{"menuHelp",				"Βοήθεια"},
		{"helpHelp",				"Help on Program-Use and on its Knowledge-Formalisms (The Sensorial-BConcept Approach)"},
		{"helpHtmlMI",					"Help in Html"},
		{"helpHtmlHelp",				"VIEW the help system of the program, in HTML format."},
		/* separator */
		{"contentAAjMI",		"AAj"},
		{"contentAAjHelp",	"VIEW the AAj-CONCEPT"},
		{"partsMI",				"AAj Μέρη σε δενδροδιάγραμμα"},
		{"partsHelp",			"VIEW the PARTS of the AAj-xcpt"},
		{"functionsMI",		"AAj Λειτουργίες σε δενδροδιάγραμμα"},
		{"functionsHelp",	"VIEW the FUNCTIONS of the AAj-xcpt"},
	};
}
