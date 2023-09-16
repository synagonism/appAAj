/**
 *
 * @modified 2006.11.13
 * @since 2006.11.13 (v00.02.00)
 * @author HoKoNoUmo
 */
package pk_Util;

import java.util.ListResourceBundle;

public class BundleMenus_epo extends ListResourceBundle {


	public Object[][] getContents() {
 		return contents;
	}

	static final Object[][] contents = {
		/* */
		/* SYSTEM MENU */
		/* */
		{"systemMenu",			"Programo"},
		{"systemHelp",			"Funkcioj parenca al AAj"},
		{"aboutMI",				"Je la programo/a\u016dtoro"},
		{"aboutHelp",			"rigardi informon je de la programo kaj it a\u016dtoro la fondi\u011don"},
		/* separator */
		{"exitMI",					"Eliru"},
		{"exitHelp",				"Elirejo de la programo"},

		/* */
		/* BUILD-KNOWLEDGEbASE MENU */
		/* */
		{"menuBuild",			"Konstruu-KonaBaso"},
		{"buildHelp",			"Funkcioj Related to XKnowledgeBase Building"},
		{"editorMI",				"Edit the Source of xConcept"},
		{"editorHelp",			"A simple XML-Editor for the sensorial-b-concepts"},
		{"editorTip",			"Edit the Source of xConcept"},

		/* separator */
		{"menuIntegrate",				"Integrate Current-xcpt"},
		{"integrateHelp",				"Make CONSISTENT the current-xcpt's relations with its attributes"},
		{"totalIntegrationMI",		"Total Integration"},
		{"totalIntegrationHelp",	"Do ALL the types of integrations"},
		{"parIntegrationMI",			"Parto Integration"},
		{"parIntegrationHelp",		"Check if the PARTS of current-xcpt have as WHOLE the current-xcpt"},
		{"whoIntegrationMI",			"Whole Integration"},
		{"whoIntegrationHelp",		"Check if the WHOLE-cpts have as PARTS the current--x-cpt ..."},
		{"genIntegrationMI",			"Generiko Integration"},
		{"genIntegrationHelp",		"Check if all the parts of a GENERIC-xcpt are parts of the current-xcpt ..."},
		{"sibIntegrationMI",			"Sibling Integration"},
		{"subIntegrationMI",			"Specifiko Integration"},
		{"subIntegrationHelp",		"Check if the SPECIFICS have all the PARTS of the current-xcpt ..."},
		{"envIntegrationMI",			"Environment Integration"},
		{"envIntegrationHelp",		"Check if the ENVIRONMENT-XCPTS have in their environment the current-xcpt ..."},

		{"jmiBuildChangeSubWorldviewMI", "Change SubWorldView"},
		{"jmiBuildChangeSubWorldviewHelp", "Change sub-worldview of current-xcpt"},

		/* separator: add concepts */
		{"addCptMenu",					"Add XConcept"},
		{"addCptHelp",						"Add a NEW/EXISTING XConcept to Current-xcpt"},
		{"addNewMenu",					"Add NEW-CONCEPT to current"},
		{"addNewHelp",					"Add a NEW-XCPT Related to Current-xcpt"},
		{"addNewParMI",				"As PART"},
		{"addNewParHelp",			"Add a NEW-XCPT as PART of the Current-xcpt"},
		{"addNewParTip",				"Add NEW-CONCEPT as PART"},
		{"addNewWhoMI",			"As WHOLE"},
		{"addNewWhoHelp",		"Add a NEW-XCPT as WHOLE to the Current-xcpt"},
		{"addNewWhoTip",			"Add NEW-CONCEPT as WHOLE"},
		{"addNewGenMI",				"As GENERIC"},
		{"addNewGenHelp",		"Add a NEW-XCPT as GENERIC to the Current-xcpt"},
		{"addNewGenTip",			"Add NEW-CONCEPT as GENERIC"},
		{"addNewSibMI",				"As SIBLING"},
		{"addNewSibTip",				"Add NEW-CONCEPT as SIBLING"},
		{"addNewSpeMI",				"As SPECIFIC"},
		{"addNewSpeHelp",		"Add a NEW-XCPT as SPECIFIC to the Current-xcpt"},
		{"addNewSpeTip",			"Add NEW-CONCEPT as SPECIFIC"},
		{"addNewEnvMI",				"As ENVIRONMENT"},
		{"addNewEnvHelp",			"Add a NEW-XCPT as ENVIRONMENT to the Current-xcpt"},
		{"addNewEnvTip",				"Add NEW-CONCEPT as ENVIRONMENT"},
		{"addNewAtrMI",					"As ATTRIBUTE"},
		{"addNewAtrHelp",				"Add NEW sr-concept as ATTRIBUTE to current sr-concept"},
		{"addNewAtrTip",					"Add NEW sr-concept as ATTRIBUTE"},
		/* */
		{"addExistingMenu",		"Add EXISTING-CONCEPT to current"},
		{"addExistingHelp",		"Add EXISTING-XCPT to Current-xcpt"},
		{"addParMI",						"As PART"},
		{"addParHelp",					"Add EXISTING-XCPT as PART to Current-xcpt"},
		{"addParTip",					"As PART"},
		{"addWhoMI",						"As WHOLE"},
		{"addWhoHelp",					"Add EXISTING-XCPT as WHOLE to Current-xcpt"},
		{"addWhoTip",					"As WHOLE"},
		{"addGenMI",			"As GENERIC"},
		{"addGenHelp",			"Add EXISTING-XCPT as GENERIC to Current-xcpt"},
		{"addGenTip",			"As GENERIC"},
		{"addSibMI",			"As SIBLING"},
		{"addSibTip",			"As SIBLING"},
		{"menuAddAttSpecificMI",			"As SPECIFIC"},
		{"menuAddAttSpecificHelp",			"Add EXISTING-XCPT as SPECIFIC to Current-xcpt"},
		{"menuAddAttSpecificTip",			"As SPECIFIC"},
		{"addEnvMI",			"As ENVIRONMENT"},
		{"addEnvHelp",			"Add EXISTING-XCPT as ENVIRONMENT to Current-xcpt"},
		{"addEnvTip",			"As ENVIRONMENT"},
		{"addUnrelatedMI",			"Add UNRELATED-CONCEPT"},
		{"addUnrelatedHelp",	"Add UNRELATED-XCPT to Current-xcpt"},
		{"addUnrelatedTip",		"Add UNRELATED-CONCEPT"},
		/* */
		{"delCptMenu",			"Delete XConcept"},
		{"delCptHelp",			"DELETE XConcept from a SrWorldview"},

		{"delCrtCptMI",			"Delete the XConcept"},
		{"delCrtCptHelp",			"DELETE the Current-xcpt"},
		{"delCrtCptTip",			"Delete the XConcept"},
		{"delRelatedMenu",			"Delete Related XConcept"},
		{"delRelatedHelp",			"DELETE Related-xcpt to Current"},
		{"delParMI",			"Delete PART"},
		{"delParHelp",			"DELETE a PART of the Current-xcpt"},
		{"delParTip",			"Delete PART"},
		{"delWhoMI",			"Delete WHOLE"},
		{"delWhoHelp",			"DELETE a WHOLE of the Current-xcpt"},
		{"delWhoTip",			"Delete WHOLE"},
		{"delGenMI",			"Delete GENERIC"},
		{"delGenHelp",			"DELETE a GENERIC of the Current-xcpt"},
		{"delGenTip",			"Delete GENERIC"},
		{"delSibMI",			"Delete SIBLING"},
		{"delSibTip",			"Delete SIBLING"},
		{"delSubMI",			"Delete SPECIFIC"},
		{"delSubHelp",			"DELETE a SPECIFIC of the Current-xcpt"},
		{"delSubTip",			"Delete SPECIFIC"},
		{"delDimMI",			"Delete DIMENSION"},
		{"delDimTip",			"Delete DIMENSION"},
		{"delEnvMI",			"Delete ENVIRONMENT"},
		{"delEnvHelp",			"DELETE an ENVIRONMENT of the Current-xcpt"},
		{"delEnvTip",			"Delete ENVIRONMENT"},
		{"delUnrelatedMI",			"Delete UNRELATED concept to current"},
		{"delUnrelatedHelp",			"DELETE an xcpt UNRELATED to current-xcpt"},
		{"delUnrelatedTip",			"Delete UNRELATED concept to current"},

		/* separator: part commands */
		{"parOverrideMI",				"Override the Selected Parto"},
		{"parOverrideHelp",			"Specialize an Inherited Parto-xcpt"},
		{"parGeneralizeMI",			"Generalize the Selected Parto"},
		{"parGeneralizeHelp",		"Make the Selected-Parto-xcpt, a Parto-xcpt of Current's-Generiko"},
		{"parMakeInheritedMI",	"Make a Parto Inherited"},
		{"parMakeInheritedHelp","Make the Selected-Parto-xcpt, inherited of an EXISTING part-xcpt of Current's-Generiko"},

		/* separator */
		{"jmiAddDivisionSpecificMI",				"Add Specifiko-Division ON an Attribute"},
		{"jmiAddDivisionSpecificHelp",			"Add a Set-of-Specifics that include ALL the Concrete-cpts of Current-xcpt"},

		/* separator */
		{"nameMgtMI",						"Manage Names"},
		{"nameMgtHelp",					"Insert/Replace/Delete the NAMES of the Current-XCPT or RENAME it"},
		{"updateTermFilesMI",		"Update LgConceptTerms"},
		{"updateTermFilesHelp",	"Update the LgConceptTerms of the Current-xConcept in the term-file"},
		{"tmrKmlMgtMI",					"Manage Komo-Terms"},
		{"tmrKmlMgtHelp",				"Manage(Create/Validate) Komo-Terms"},

		/* separator */
		{"addSrWorldviewMI",			"Create Sensorial-Worldview"},
		{"addSrWorldviewHelp",		"ADD sensorial-Brainual-worldview"},
		{"addSrSubWorldviewMI",	"Create SrSubWorldview"},
		{"addSrSubWorldviewHelp","ADD a new SrSubWorldview part of current XKnowledgeBase"},
		{"setKBAuthorMI",				"Set Author of XKnowledgeBase"},
		{"setKBAuthorHelp",			"Set a new Author for the system of AAj-SrWorldviews"},
		{"saveVariablesMI",			"Save The Variables"},
		{"saveVariablesHelp",		"Save the last values of AAj-Variables in case of an abnormal-exit"},

		{"copy-to-clipboardMI",	"Copy"},
		{"copy-to-clipboardTip",	"Copy to clipboard"},


		/* */
		/* RETRIEVE-KNOWLEDGEbASE MENU */
		/* */
		{"menuRetrieve",					"Vidu-KonaBaso"},
		{"retrieveHelp",					"Funkcioj Related to XKnowledgeBase-Retrieving"},
		{"jmiRetrieveBack1MI",							"Back"},
		{"jmiRetrieveBack1Help",						"Display the Previous XConcept"},
		{"jmiRetrieveBack1Tip",							"Go to Previous XConcept"},
		{"jmiRetrieveBack2MI",							"History"},
		{"jmiRetrieveBack2Help",						"View the list with the visited-cpts"},
		{"jmiRetrieveBack2Tip",							"History"},
		{"refreshMI",						"Refresh"},
		{"refreshHelp",					"Redisplay the Current-xcpt"},
		{"refreshTip",						"View the concept after editing its source"},

		/* separator */
		{"getSrCptInfoMI",					"Current XConcept Information"},
		{"getSrCptInfoHelp",				"View Information (statistics and more) about the Current-xcpt"},
		{"viewAllMI",						"View All Tree-Structures"},
		{"viewAllHelp",					"View a Tree with all LEVELS of RELATED-XCPTS"},
		{"viewAllSpesMI",				"View Specifiko-Tree-Structure"},
		{"viewAllSpesHelp",			"View a Tree with ALL specifics of the Current-xcpt"},
		{"viewAllGensMI",				"View Generiko-Tree-Structure"},
		{"viewAllGensHelp",			"View a Tree with ALL generics of the Current-xcpt"},
		{"viewAllParsMI",				"View Parto-Tree-Structure"},
		{"viewAllParsHelp",			"View a Tree with ALL parts of the Current-xcpt"},
		{"viewAllWhosMI",				"View Whole-Tree-Structure"},
		{"viewAllWhosHelp",			"View a Tree with ALL wholes of the Current-xcpt"},
		{"viewSpecificComplMI",	"View Specifiko-Complements"},
		{"viewSpecificComplHelp","VIEW the SPECIFIC-COMPLEMENTS (siblings) of the current-xcpt on a generic's-division or generic only."},

		/* separator */
		{"findMenu",							"Find XConcept"},
		{"findHelp",							"Choose a method to FIND XConcept"},
		{"findNameMI",						"Find XConcept by name/synonym"},
		{"findNameHelp",					"FIND XConcept by searching TERMS (lng/term.index.xml files)"},
		{"findNameTip",					"Find XConcept by name/synonym"},
		{"findRelatedMI",				"Find XConcept by Related ones"},
		{"findRelatedHelp",			"FIND XConcept by searching RELATED-XCPTS (xcpt.xml files)"},
		{"findRelatedTip",				"Find XConcept by Related ones "},
		{"findIDMI",							"Find XConcept by ID"},
		{"findIDHelp",						"FIND XConcept by searching IDs (lng/term.index.xml files)"},
		{"findIDTip",						"Find XConcept by ID"},
		{"findFormalNameMI",			"Find XConcept by Formal-Name"},
		{"findFormalNameHelp",		"FIND XConcept by searching FORMAL-NAMES (lng/term.index.xml files)"},
		{"findFormalNameTip",		"Find XConcept by Formal-Name"},
		{"openMI",								"Open a SC File"},
		{"openHelp",							"Find a Structured-XConcept xml-file from the file-list"},
		{"openTip",							"Open a New XConcept"},
		{"findSelectedMI",				"Find the Selected concept"},
		{"findSelectedHelp",			"FIND the SELECTED-XCPT by searching names/termTxCpt_sAll (lng/term.index.xml files)"},
		{"findSelectedTip",			"Find the Selected concept"},
		{"findXmlFilesMI",				"Find XConcept by searching Xml-Files"},
		{"findXmlFilesHelp",			"FIND XConcept by searching the xcpt.xml files for names/termTxCpt_sAll"},
		{"findXmlFilesTip",			"Find XConcept by searching Xml-Files"},
		{"findFileListMI",				"Find XConcept by ID/FormalName File-Lists"},
		{"findFileListHelp",			"FIND XConcept by searching IDs/FORMALNAMES in xcpt.xml file lists"},
		{"findFileListTip",			"Find XConcept by ID/FormalName File-Lists"},
		{"findTxConceptTermsMI",		"Find TxCONCEPT-TERMS of a TxConcept-Name"},
		{"findTxConceptTermsHelp",	"FIND the TxCONCEPT-TERMS of a TxConcept-Name"},

		/* separator */
		{"askAAjMI",							"Ask AAj a question"},
		{"askAAjHelp",						"ASK a question (Is A a B?, Is A part of B?)"},
		{"askAAjTip",						"Ask AAj a question"},
		{"transMI",							"Translate"},
		{"transHelp",						"Conceptual-Information and Logos Translation"},

		/* separator */
		{"getKBInfoMI",			"XKnowledgeBase statistics"},
		{"getKBInfoHelp",		"VIEW concept-statistics of XKnowledgeBase"},
		{"getKBAuthorMI",				"XKnowledgeBase Author"},
		{"getKBAuthorHelp",			"VIEW the current AAj-Knowledge AUTHOR"},

		/* separator */
		{"langMenu",						"Lango"},
		{"langHelp",						"CHOOSE the Natural-Lango to work with the program and its Knowledge"},
		{"englishMI",					"Angla Lango"},
		{"greekMI",						"Greka Lango"},
		{"esperantoMI",				"Esperanta Lango"},
		{"komoMI",							"Koma Lango"},

		/* */
		/* HELP MENU */
		/* */
		{"menuHelp",						"Helpo"},
		{"helpHelp",						"Help on Program-Use and on its Knowledge-Formalisms (The Sensorial-BConcept Approach)"},
		{"helpHtmlMI",					"Help in Html"},
		{"helpHtmlHelp",				"VIEW the help system of the program, in HTML format."},
		/* separator */
		{"contentAAjMI",				"What is the AAj"},
		{"contentAAjHelp",			"VIEW what is the AAj"},
		{"partsMI",						"AAj Parts Tree"},
		{"partsHelp",					"VIEW the PARTS of the AAj"},
		{"functionsMI",				"AAj Funkcioj Tree"},
		{"functionsHelp",			"VIEW the FUNCTIONS of the AAj"},
	};
}
