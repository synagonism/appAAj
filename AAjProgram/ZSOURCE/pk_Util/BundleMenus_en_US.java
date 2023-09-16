/**
 * @modified 2003.11.19
 * @since 2003.11.19|1999.02.19 (v00.02.00)
 * @author HoKoNoUmo
 */
package pk_Util;

import java.util.ListResourceBundle;

public class BundleMenus_en_US extends ListResourceBundle {


	public Object[][] getContents() {
		return contents;
	}

	static final Object[][] contents = {
		/* */
		/* SYSTEM MENU */
		/* */
		{"systemMenu",				"Program"},
		{"systemHelp",				"Functions Related to AAj"},
		{"aboutMI",					"About Program/Author"},
		{"aboutHelp",				"VIEW information about the creation of the Program and its Author"},
		/* separator */
		{"exitMI",						"Exit"},
		{"exitHelp",					"Program Exit"},

		/* */
		/* BUILD-KNOWLEDGEbASE MENU */
		/* */
		{"menuBuild",				"Build-XKnowledgeBase"},
		{"buildHelp",				"Functions Related to XKnowledgeBase Building"},
		{"editorMI",					"Edit Source of XConcept"},
		{"editorHelp",				"A simple XML-Editor for xcpts"},
		{"editorTip",				"Edit Source of xcpt"},

		/* separator */
		{"menuIntegrate",				"Integrate Current XConcept"},
		{"integrateHelp",				"Make CONSISTENT the current-xcpt's relations with its attributes"},
		{"totalIntegrationMI",		"Total Integration"},
		{"totalIntegrationHelp",	"Do ALL types of integrations"},
		{"parIntegrationMI",			"Part Integration"},
		{"parIntegrationHelp",		"Check if PARTS of current-xcpt have as WHOLE current-xcpt"},
		{"whoIntegrationMI",			"Whole Integration"},
		{"whoIntegrationHelp",		"Check if WHOLE-xcpts have as PARTS current-xcpt ..."},
		{"genIntegrationMI",			"Generic Integration"},
		{"genIntegrationHelp",		"Check if all parts of GENERIC-xcpt are parts of current-xcpt ..."},
		{"sibIntegrationMI",			"Sibling Integration"},
		{"subIntegrationMI",			"Specific Integration"},
		{"subIntegrationHelp",		"Check if SPECIFICS have all PARTS of current-xcpt ..."},
		{"envIntegrationMI",			"Environment Integration"},
		{"envIntegrationHelp",		"Check if ENVIRONMENT-xcpts have in their environment current-xcpt ..."},

		{"jmiBuildChangeSubWorldviewMI", "Change SubWorldView"},
		{"jmiBuildChangeSubWorldviewHelp", "Change sub-worldview of current-xcpt"},

		/* separator: add xcpts */
		{"addCptMenu",						"Add XConcept"},
		{"addCptHelp",						"Add NEW/EXISTING xcpt to current xcpt"},
		{"addNewMenu",						"Add NEW XConcept to Current"},
		{"addNewHelp",						"Add NEW xcpt Related to current xcpt"},
		{"addNewParMI",					"As PART"},
		{"addNewParHelp",				"Add NEW xcpt as PART of current xcpt"},
		{"addNewParTip",					"Add NEW xcpt as PART"},
		{"addNewWhoMI",					"As WHOLE"},
		{"addNewWhoHelp",				"Add NEW xcpt as WHOLE to current xcpt"},
		{"addNewWhoTip",					"Add NEW xcpt as WHOLE"},
		{"addNewGenMI",					"As GENERIC"},
		{"addNewGenHelp",				"Add NEW xcpt as GENERIC to current xcpt"},
		{"addNewGenTip",					"Add NEW xcpt as GENERIC"},
		{"addNewSibMI",					"As SIBLING"},
		{"addNewSibTip",					"Add NEW xcpt as SIBLING"},
		{"addNewSpeMI",					"As SPECIFIC"},
		{"addNewSpeHelp",				"Add NEW xcpt as SPECIFIC to current xcpt"},
		{"addNewSpeTip",					"Add NEW xcpt as SPECIFIC"},
		{"addNewEnvMI",					"As ENVIRONMENT"},
		{"addNewEnvHelp",				"Add NEW xcpt as ENVIRONMENT to current xcpt"},
		{"addNewEnvTip",					"Add NEW xcpt as ENVIRONMENT"},
		{"addNewAtrMI",					"As ATTRIBUTE"},
		{"addNewAtrHelp",				"Add NEW xcpt as ATTRIBUTE to current xcpt"},
		{"addNewAtrTip",					"Add NEW xcpt as ATTRIBUTE"},
		/* */
		{"addExistingMenu",			"Add EXISTING XConcept to Current"},
		{"addExistingHelp",			"Add EXISTING X-CONCEPT to current xcpt"},
		{"addParMI",							"As PART"},
		{"addParHelp",						"Add EXISTING X-CONCEPT as PART to current xcpt"},
		{"addParTip",						"As PART"},
		{"addWhoMI",							"As WHOLE"},
		{"addWhoHelp",						"Add EXISTING X-CONCEPT as WHOLE to current xcpt"},
		{"addWhoTip",						"As WHOLE"},
		{"addGenMI",							"As GENERIC"},
		{"addGenHelp",						"Add EXISTING X-CONCEPT as GENERIC to current xcpt"},
		{"addGenTip",						"As GENERIC"},
		{"addSibMI",							"As SIBLING"},
		{"addSibTip",						"As SIBLING"},
		{"menuAddAttSpecificMI",							"As SPECIFIC"},
		{"menuAddAttSpecificHelp",						"Add EXISTING XCONCEPT as SPECIFIC to current xcpt"},
		{"menuAddAttSpecificTip",						"As SPECIFIC"},
		{"addEnvMI",							"As ENVIRONMENT"},
		{"addEnvHelp",						"Add EXISTING X-CONCEPT as ENVIRONMENT to current xcpt"},
		{"addEnvTip",						"As ENVIRONMENT"},
		{"addUnrelatedMI",				"Add UNRELATED XConcept"},
		{"addUnrelatedHelp",			"Add xcpt UNRELATED to current xcpt"},
		{"addUnrelatedTip",			"Add UNRELATED xcpt"},
		/* */
		{"delCptMenu",						"Delete XConcept"},
		{"delCptHelp",						"DELETE xcpt from Worldview"},

		{"delCrtCptMI",					"Delete XConcept"},
		{"delCrtCptHelp",				"DELETE current-xcpt"},
		{"delCrtCptTip",					"Delete xcpt"},
		{"delRelatedMenu",				"Delete Related XConcept"},
		{"delRelatedHelp",				"DELETE related-xcpt to current"},
		{"delParMI",							"Delete PART"},
		{"delParHelp",						"DELETE PART of current xcpt"},
		{"delParTip",						"Delete PART"},
		{"delWhoMI",							"Delete WHOLE"},
		{"delWhoHelp",						"DELETE WHOLE of current xcpt"},
		{"delWhoTip",						"Delete WHOLE"},
		{"delGenMI",							"Delete GENERIC"},
		{"delGenHelp",						"DELETE GENERIC of current xcpt"},
		{"delGenTip",						"Delete GENERIC"},
		{"delSibMI",							"Delete SIBLING"},
		{"delSibTip",						"Delete SIBLING"},
		{"delSubMI",							"Delete SPECIFIC"},
		{"delSubHelp",						"DELETE SPECIFIC of current xcpt"},
		{"delSubTip",						"Delete SPECIFIC"},
		{"delDimMI",							"Delete DIMENSION"},
		{"delDimTip",						"Delete DIMENSION"},
		{"delEnvMI",							"Delete ENVIRONMENT"},
		{"delEnvHelp",						"DELETE ENVIRONMENT of current xcpt"},
		{"delEnvTip",						"Delete ENVIRONMENT"},
		{"delUnrelatedMI",				"Delete UNRELATED XConcept to Current"},
		{"delUnrelatedHelp",			"DELETE xcpt UNRELATED to current-xcpt"},
		{"delUnrelatedTip",			"Delete UNRELATED xcpt to current"},

		/* separator: part commands */
		{"parOverrideMI",				"Override Selected Part"},
		{"parOverrideHelp",			"Specialize Inherited Part-xcpt"},
		{"parGeneralizeMI",			"Generalize Selected Part"},
		{"parGeneralizeHelp",		"Make Selected-Part-xcpt, Part-xcpt of Current's-Generic"},
		{"parMakeInheritedMI",		"Make Part Inherited"},
		{"parMakeInheritedHelp",	"Make Selected-Part-xcpt, inherited of EXISTING part-xcpt of Current's-Generic"},

		/* separator */
		{"jmiAddDivisionSpecificMI",		"Add Specific-Division ON Attribute"},
		{"jmiAddDivisionSpecificHelp",	"Add Set-of-Specifics that include ALL Concrete-xcpts of current xcpt"},

		/* separator */
		{"nameMgtMI",						"Manage Names"},
		{"nameMgtHelp",					"Insert/Modify/Delete the NAMES of current-xcpt or RENAME it"},
		{"updateTermFilesMI",		"Update LgConcept-Terms"},
		{"updateTermFilesHelp",	"Update LgConceptTerms of current-xcpt in term-files"},
		{"tmrKmlMgtMI",					"Manage Komo-Terms"},
		{"tmrKmlMgtHelp",				"Manage(Create/Validate) terms, lg-concept-auxiliaries, interjenctions, phatic-words"},

		/* separator */
		{"addSrWorldviewMI",			"Create Sensorial-Worldview"},
		{"addSrWorldviewHelp",		"ADD new sensorial-b-worldview"},
		{"addSrSubWorldviewMI",		"Create Sensorial-SubWorldview"},
		{"addSrSubWorldviewHelp",	"ADD new sensorial-b--sub-worldview part of current Worldview"},
		{"setKBAuthorMI",				"Set Author of XKnowledgeBase"},
		{"setKBAuthorHelp",			"Set new Author for XKnowledgeBase"},
		{"saveVariablesMI",			"Save Variables"},
		{"saveVariablesHelp",		"Save last values of AAj-Variables in case of abnormal-exit"},

		{"copy-to-clipboardMI",	"Copy"},
		{"copy-to-clipboardTip",	"Copy to clipboard"},


		/* */
		/* RETRIEVE-KNOWLEDGEbASE MENU */
		/* */
		{"menuRetrieve",					"Retrieve-XKnowledgeBase"},
		{"retrieveHelp",					"Functions Related to XKnowledgeBase-Retrieving"},
		{"jmiRetrieveBack1MI",							"Back"},
		{"jmiRetrieveBack1Help",						"Display Previous xcpt"},
		{"jmiRetrieveBack1Tip",							"Go to Previous xcpt"},
		{"jmiRetrieveBack2MI",							"History"},
		{"jmiRetrieveBack2Help",						"View list with visited-xcpts"},
		{"jmiRetrieveBack2Tip",							"History"},
		{"refreshMI",						"Refresh"},
		{"refreshHelp",					"Redisplay Current-xcpt"},
		{"refreshTip",						"View xcpt after editing its source"},

		/* separator */
		{"getSrCptInfoMI",				"Information of Current XConcept"},
		{"getSrCptInfoHelp",			"View Information (statistics and more) about Current-xcpt"},
		{"viewAllMI",						"View All Tree-Structures"},
		{"viewAllHelp",					"View Tree with all LEVELS of RELATED-xcpts"},
		{"viewAllSpesMI",				"View Specific-Tree-Structure"},
		{"viewAllSpesHelp",			"View Tree with ALL specifics of current xcpt"},
		{"viewAllGensMI",				"View Generic-Tree-Structure"},
		{"viewAllGensHelp",			"View Tree with ALL generics of current xcpt"},
		{"viewAllParsMI",				"View Part-Tree-Structure"},
		{"viewAllParsHelp",			"View Tree with ALL parts of current xcpt"},
		{"viewAllWhosMI",				"View Whole-Tree-Structure"},
		{"viewAllWhosHelp",			"View Tree with ALL wholes of current xcpt"},
		{"viewSpecificComplMI",	"View Specific-Complements"},
		{"viewSpecificComplHelp","VIEW SPECIFIC-COMPLEMENTS (siblings) of current-xcpt on generic's-division or generic only."},

		/* separator */
		{"findMenu",							"Find XConcept"},
		{"findHelp",							"Choose method to FIND xcpt"},
		{"findNameMI",						"Find XConcept by Name"},
		{"findNameHelp",					"FIND xcpt by searching TERMS (lng/term.index.xml files)"},
		{"findNameTip",					"Find xcpt by name/synonym"},
		{"findRelatedMI",				"Find XConcept by Related XConcepts"},
		{"findRelatedHelp",			"FIND xcpt by searching RELATED-xcpts (xcpt.xml files)"},
		{"findRelatedTip",				"Find xcpt by Related ones "},
		{"findIDMI",							"Find XConcept by formalID"},
		{"findIDHelp",						"FIND xcpt by searching formalIDs (lng/term.index.xml files)"},
		{"findIDTip",						"Find xcpt by formalID"},
		{"findFormalNameMI",			"Find XConcept by Formal-Name"},
		{"findFormalNameHelp",		"FIND xcpt by searching FORMAL-NAMES (lng/term.index.xml files)"},
		{"findFormalNameTip",		"Find xcpt by Formal-Name"},
		{"openMI",								"Open XConcept File"},
		{"openHelp",							"Find xcpt xml-file from file-list"},
		{"openTip",							"Open xml-file xcpt"},
		{"findSelectedMI",				"Find Selected XConcept"},
		{"findSelectedHelp",			"FIND SELECTED-XCONCEPT by searching names/termTxCpt_sAll (lng/term.index.xml files)"},
		{"findSelectedTip",			"Find Selected xcpt"},
		{"findXmlFilesMI",				"Find XConcept by Searching Xml-Files"},
		{"findXmlFilesHelp",			"FIND xcpt by searching xcpt.xml files for names/termTxCpt_sAll"},
		{"findXmlFilesTip",			"Find xcpt by searching Xml-Files"},
		{"findFileListMI",				"Find XConcept by formalID/FormalName File-Lists"},
		{"findFileListHelp",			"FIND xcpt by searching formalIDs/formalNames in xcpt.xml file lists"},
		{"findFileListTip",			"Find xcpt by formalID/FormalName File-Lists"},
		{"findTxConceptTermsMI",	"Find TxConcept-Terms of TxConcept-Name"},
		{"findTxConceptTermsHelp","FIND TxCONCEPT-TERMS of TxConcept-Name"},

		/* separator */
		{"askAAjMI",							"Ask AAj a Question"},
		{"askAAjHelp",						"ASK a question: (Is A a B?) or (Is A part of B?)"},
		{"askAAjTip",						"Ask AAj question"},
		{"transMI",							"Translate"},
		{"transHelp",						"Conceptual-Information and Logos Translation"},

		/* separator */
		{"getKBInfoMI",					"XKnowledgeBase Statistics"},
		{"getKBInfoHelp",				"VIEW statistics of XKnowledgeBase"},
		{"getKBAuthorMI",				"XKnowledgeBase Author"},
		{"getKBAuthorHelp",			"VIEW XKnowledgeBase AUTHOR"},

		/* separator */
		{"langMenu",							"Language"},
		{"langHelp",							"CHOOSE Human-Language to work with program and its Knowledge"},
		{"englishMI",						"English Language"},
		{"greekMI",							"Greek Language"},
		{"esperantoMI",					"Esperanto Language"},
		{"komoMI",								"Komo Language"},

		/* */
		/* HELP MENU */
		/* */
		{"menuHelp",						"Help"},
		{"helpHelp",						"Help on Program-Use and on its Knowledge-Formalisms (The xcpt Approach)"},
		{"helpHtmlMI",					"Help in Html"},
		{"helpHtmlHelp",				"VIEW the help system of the program, in HTML format."},
		/* separator */
		{"contentAAjMI",				"What is AAj"},
		{"contentAAjHelp",			"VIEW what is AAj"},
		{"partsMI",						"AAj Parts Tree"},
		{"partsHelp",					"VIEW PARTS of AAj"},
		{"functionsMI",				"AAj Functions Tree"},
		{"functionsHelp",			"VIEW FUNCTIONS of AAj"},
	};
}
