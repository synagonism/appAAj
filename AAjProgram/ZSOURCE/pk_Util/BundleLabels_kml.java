/**
 *
 * @modified 2006.11.13
 * @since 2006.11.13 (v00.02.00)
 * @author HoKoNoUmo
 */
package pk_Util;

import java.util.ListResourceBundle;

public class BundleLabels_kml
	extends ListResourceBundle {

	public Object[][] getContents() {
		return contents;
	}

	static final Object[][] contents = {
		/* COMMON-USED-LABELS: */

		/* ALL CAPITALS: */
		{"CANCEL",								"CANCEL"},
		{"DEFINITION",						"DEFINUFANO"},
		{"DEFINITION_ATTRIBUTE",	"ATTRIBUTE-DEFINITION"},
		{"DEFINITION_ATTRIBUTE_START","START-ATTRIBUTE-DEFINITION"},
		{"DEFINITION_ATTRIBUTE_END", 	"END-ATTRIBUTE-DEFINITION"},
		{"DEFINITION_PART",					"PART-DEFINITION"},
		{"DEFINITION_PART_START",		"START-PART-DEFINITION"},
		{"DEFINITION_PART_END",			"END-PART-DEFINITION"},
		{"DEFINITION_WHOLE",					"WHOLE-DEFINITION"},
		{"DEFINITION_WHOLE_START",		"START-WHOLE-DEFINITION"},
		{"DEFINITION_WHOLE_END", 		"END-WHOLE-DEFINITION"},
		{"DEFINITION_SPECIFIC",			"SPECIFIC-DEFINITION"},
		{"DEFINITION_SPECIFIC_START",	"START-SPECIFIC-DEFINITION"},
		{"DEFINITION_SPECIFIC_END",		"END-SPECIFIC-DEFINITION"},
		{"DEFINITION_GENERIC",				"GENERIC-DEFINITION"},
		{"DEFINITION_GENERIC_START",	"START-GENERIC-DEFINITION"},
		{"DEFINITION_GENERIC_END",		"END-GENERIC-DEFINITION"},
		{"DEFINITION_ENVIRONMENT",		"ENVIRONMENT-DEFINITION"},
		{"DEFINITION_ENVIRONMENT_START",	"START-ENVIRONMENT-DEFINITION"},
		{"DEFINITION_ENVIRONMENT_END",	"END-ENVIRONMENT-DEFINITION"},
		{"DEFINITION_SENSORY",				"SENSORY-DEFINITION"},
		{"REFINO",							"REFINO"},
		{"REFINO_ARGUMENT",		"ARGUMENT-RELATION"},
		{"REFINO_ATTRIBUTE",		"EO"},
		{"REFINO_DEFINITION",	"DEFINITION-RELATION"},
		{"REFINO_ENTITY",			"ENTITY-RELATION"},
		{"REFINO_ENVIRONMENT",	"REFINO_ENVIRONMENT"},
		{"REFINO_GENERIC",			"REFINO_GENERIC"},
		{"REFINO_NAME",				"NAME-RELATION"},
		{"REFINO_NONaTTRIBUTE","NON-ATTRIBUTE-RELATION"},
		{"REFINO_PART",				"REFINO_PART"},
		{"REFINO_PARTcOMPLEMENT","PARTIAL-COMPLEMENT--RELATION"},
		{"REFINO_SPECIFIC",		"SPECIFIFEANO"},
		{"REFINO_SPECIFICcOMPLEMENT","SPECIFIC-COMPLEMENT--RELATION"},
		{"REFINO_WHOLE",				"REFINO_WHOLE"},
		{"ENVIRONMENT",				"ENVIRONMENT"},
		{"ENVIRONMENTS",				"ENVIRONMENTS"},
		{"GENERIC",						"GENERIC"},
		{"GENERICS",						"GENERICS"},
		{"HELP",								"HELP"},
		{"FO_ETO_KONCOS_NAMO",			"FO_ETO_KONCOS_NAMO"},
		{"FO_ETO_TO_KONCOS_NAMO",	"TxCONCEPT-NAME"},
		{"TERMS",							"NAMERI"},
		{"NAME",								"NAMO"},
		{"NEW",								"NEW"},
		{"NO",									"NO"},
		{"OK",									"DO"},
		{"OLD",								"OLD"},
		{"PART",								"PARTO"},
		{"PARTcOMPLEMENT",	"PARTIAL-COMPLEMENT"},
		{"PARTS",							"PARTI"},
		{"SENSORIALbCONCEPT",		"SENSORIAL--B-CONCEPT"},
		{"SPECIFIC",						"SPECIFICO"},
		{"SPECIFICS",					"SPECIFICS"},
		{"SPECIFICcOMPLEMENT",	"SPECIFIC-COMPLEMENT"},
		{"WHOLE",							"WHOLE"},
		{"WHOLES",							"WHOLES"},
		{"YES",								"DO"},
		{"XMLcONCEPT",					"X-CONCEPT"},

		/* First Capital: */
		{"NounAdjectiveTx",		"NounAdjectiveTx"},
		{"NounAdverbTx",				"NounAdverbTx"},
		{"All",								"All"},
		{"Author",							"Author"},
		{"BWorldview",				"FoEkogoSimbo"},
		{"Cancel",							"Cancel"},
		{"ChooseLangToUpdate",	"Chose Lango to Update"},
		{"ChooseOne",					"Choose One"},
		{"Created",						"Created"},
		{"Conjunction_Tx",			"Conjunction_Tx"},
		{"Delete",							"Delete"},
		{"Deleted",						"Deleted"},
		{"Description",				"Description"},
		{"Down",								"Down"},
		{"English",						"English"},
		{"English2",						"English"},
		{"EngMainName",				"English-MainName"},
		{"Environment_Relation", "Environment Relation"},
		{"Esperanto",					"Esperanto"},
		{"FileXCpt",					"File-XCpt"},
		{"Find",								"Find"},
		{"FindRule",						"Find-Rule"},
		{"FormalName",					"Formal-Name"},
		{"FormalNumber",				"Formal-Number"},
		{"FormalTerm",					"Formal-Term"},
		{"FormalView",					"Formal-View"},
		{"FormalID",						"Formal-ID"},
		{"FullName",							"Full-Name"},
		{"Greek",							"Greek"},
		{"Greek2",							"Greek"},
		{"Help",								"Help"},
		{"Insert",							"Insert"},
		{"InternalXCpt",			"Internal-XCpt"},
		{"Komo",								"Komo"},
		{"Language",							"Lango"},
		{"Modify",							"Modify"},
		{"MostUsed",			"Most-used"},
		{"Name",								"Namo"},
		{"NameConceptLg",			"FoEtoKoncosNamo"},
		{"Name_NounAdjective",					"Name_NounAdjective"},
		{"TermTxNounAdjective",					"TermTxNounAdjective"},
		{"Name_NounAdverb",					"Name_NounAdverb"},
		{"TermTxNounAdverb",					"TermTxNounAdverb"},
		{"Name_Conjunction",						"Name_Conjunction"},
		{"NameTxConjunction",						"NameTxConjunction"},
		{"Term_TxConcept",						"Term_TxConcept"},
		{"TermsOfConcept_Tx",						"TermsOfConcept_Tx"},
		{"Concept_Tx",								"FoEtoToKonco"},
		{"Name_Short",				"Name_Short"},
		{"NameShortTx",				"NameShortTx"},
		{"Name_NounCase",						"Name_NounCase"},
		{"NameNounTx",						"NameNounTx"},
		{"TermTxNounCase",			"TermTxNounCase"},
		{"Name_Symbol",					"Name_Symbol"},
		{"Name_NounSpecialAdjective",	"Name_NounSpecialAdjective"},
		{"NamoSpecialoTxNounAdjective",	"NamoSpecialoTxNounAdjective"},
		{"Name_NounSpecialAdverb",	"Name_NounSpecialAdverb"},
		{"NamoSpecialoTxNounAdverb",	"NamoSpecialoTxNounAdverb"},
		{"Name_NounSpecialCase",		"Name_NounSpecialCase"},
		{"NamoSpecialoTxNoun",		"NamoSpecialoTxNoun"},
		{"Name_Verb",						"Name_Verb"},
		{"Name_sTypeOfLgConcept","Type-of-NameConceptLg"},
		{"Rel",									"Rel"},
		{"Relation",							"Relation"},
		{"Replace",							"Replace"},
		{"Rule",									"Rule"},
		{"SeeTextDefinition",		"See-Text-Definition"},
		{"SeeXmlDefinition",			"See-XML-Definition"},
		{"SpecificDivisionOn",		"Specific-division-on: "},
		{"SrSubWorldview",				"SrSubWorldview"},
		{"Statistics",						"Statistics"},
		{"Term",									"Namero"},
		{"TermTxVerb",						"TermTxVerb"},
		{"TxNoun",								"TxNoun"},
		{"TxNounSpecial",					"TxNounSpecial"},
		{"TypeOfCreationDefinition","Type-of-CreationDefinition"},
		{"TypeOfName",							"Type-of-Name"},
		{"UniqueName",						"Unique-Name"},
		{"Up",											"Up"},
		{"Update",									"Update"},
		{"Verb_Tx",								"FoEtoToRino"},
		{"XSubWorldview",				"XSubWorldview"},

		/* all smalls: */
		{"all",								"all"},
		{"analytic-definition","analytic-definition"},
		{"attribute",					"eo"},
		{"author",							"author"},
		{"creation-definition",	"creation-definition"},
		{"english",						"lango-anglo"},
		{"esperanto",					"lango-esperanto"},
		{"foEkoKoncosNamo",		"foEkoKoncosNamo"},
		{"generic",						"generic"},
		{"greek",							"lango-elado"},
		{"komo",								"lango-komo"},
		{"part",								"parto"},
		{"relator",						"relator"},
		{"sensory",						"sensory"},
		{"specific",						"specific"},
		{"txConjunctions",			"tx-conjunctions"},
		{"tx_nouns",						"foEtoToOVo"},
		{"tx_nounAdjectives",	"tx_nounAdjectives"},
		{"tx_adverbs",					"tx_adverbs"},
		{"txVerbs",						"foEtoToRinoVo"},
		{"whole",							"whole"},
		{"xSubWorldView",			"foEkogoSeoVudo"},
		{"xWorldView",					"foEkogoSeoSimbo"},

		/* MultiWord: */
		{"frasisSynMgt",	"(You can manage its synonyms, after concept-creation)"},
		{"MsgAddNewAtt", "Add a NEW concept, as ATTRIBUTE to:   "},
		{"MsgAddNewEnv", "Add a NEW xConcept, as ENVIRONMENT to:   "},
		{"MsgAddNewGeneric", "Add a NEW xConcept, as GENERIC to:   "},
		{"MsgAddNewPart", "Add a NEW xConcept, as PART to:   "},
		{"MsgAddNewSpecific", "Add a NEW xConcept, as SPECIFIC to:   "},
		{"MsgAddNewWhole", "Add a NEW xConcept, as WHOLE to:   "},
		{"MsgAddDefRel", "<html><center>Add definition's relations as: ( [Rel] {arg1} {([]{})} {arg3} ) <br/>Use any UNIQUE-NAME for the sr-cpts rel/arg"},
		{"MsgChooseSrWV",	"Choose Sensorial-Worldview"},
		{"MsgDefGive",			"You MUST give a DEFINITION"},
		{"MsgMainName", 		"You MUST give a Main-Name in english"},
		{"MsgPreferInternals", "(Prefer Internals when the definition is enough to create it)"},

		/* ALPHABETICALLY PER OBJECT: */

		/* ABOUT DIALOG: */
		{"About1",			"Programo-FoEkoSeoKonco-Javo"},
		{"About2",			"version: 00.02.00"},
		{"About3",			"created: 1997.05.12"},
		{"About4",			"autoro: Nikos Kasselouris"},
		{"About5",			"email: nikkas@otenet.gr"},
		{"About6",			"url: http://users.otenet.gr/~nikkas/"},

		/* FrameAddNewSpecific.java: */
		{"fansTitle",		"Add a NEW concept, as SPECIFIC to ("},

		/* FrameAddUnrelated.java: */
		{"fauTitle",			"Add a NEW UNRELATED concept"},

		/* RetrieveFrame_FindTxConceptTerms: */
		{"ffnTitle",				"Find the TERMS of a FO_ETO_TO_KONCOS_NAMO"},
		{"ffnMessage",			"Write the FO_ETO_KONCOS_NAMO of an English/Greek concept and choose a RULE to create its termTxCpt_sAll *OR* let the computer to find the appropriate"},
		{"ffnRuleForce",		"Force-Rule:"},
		{"ffnRule",				"Choose-Rule:"},
		{"ffnTxConceptTerms",		"TxConceptTerms:"},
		{"ffnFind",				"Find TxConceptTerms"},

		/* RetrieveFrame_KBInfo.java: */
		{"fkbiTitle",			"XKnowledgeBase Statistics"},

		/* FrameRenameCurrentCpt.java: */
		{"frccTitle",			"Rename Current-Concept"},
		{"frccNameEl",			"Greek-Name:"},
		{"frccNameEn",			"English-Name:"},
		{"frccType",				"type:"},

		/* FrameNameManagement.java: */
		{"fnmgtTitle",					"Names of Current-Concept Management"},
		{"fnmgtTmrRule",				"Term-rule:"},
		{"fnmgtCurrent1",			"current"},
		{"fnmgtCurrent2",			"current"},
		{"fnmgtDeletePermanent","Delete Permanent"},
		{"fnmgtTipInsert",			"Insert Name AFTER selected name"},
		{"fnmgtTipModify",			"Modify selected Name"},
		{"fnmgtTipDelete",			"Sets DELETION-DATE in selected Name"},
		{"fnmgtTipDeletePermanent",		"DELETES-PERMANENT the selected Name"},
		{"fnmgtTipOK",					"Update Lango-LgConcept-Names and Close the window"},
		{"fnmgtTipUpdate",			"Update names of CURRENT-LANGUAGE"},
		{"fnmgtMsgSelect",			"You MUST select a name!!!"},
		{"fnmgtMsgModify",			"Modifing rule or lg-concept's--name, old names are orphan!!!"},
		{"fnmgtMsgName",				"The MAIN-NAME of a sr-concept is its FIRST-Name-of-CaseNoun"},

		/* Frame_TxTerminalKomo_Mgt.java: */
		{"ftkmTitle",				"Management of Komo-TxTerminals"},
		{"ftkmLCreate",			"Create Komo-TxTerminals"},
		{"ftkmLUses",				"Uses:"},
		{"ftkmLSilFrs",			"First-Sil:"},
		{"ftkmLSilMid",			"Middle-Sil:"},
		{"ftkmLChoose",			"Choose:"},
		{"ftkmLSilNum",			"Sil:"},
		{"ftkmLPref",				"Prefix:"},
		{"ftkmLSuf",					"Suffix:"},
		{"ftkmLWrite",				"Write:"},
		{"ftkmCreate",				"Create TxTerminal"},
		{"ftkmCreateValidate",	"Create & Validate TxTerminal"},
		{"ftkmTxWord",		" New TxTerminal: "},
		//------------------------------------
		{"ftkmLValidate",		"Validate Komo-TxTerminal"},
		{"ftkmTxWord2",				"Write TxTerminal: "},
		{"ftkmValidate",			"Validate TxTerminal"},
		//------------------------------------
		{"ftkmError1",				"Prefix has TOO MANY syllables."},
		{"ftkmError2",				"Suffix has TOO MANY syllables."},
		{"ftkmError3",				"Choose MORE syllables."},

		/* AAj.java: */
		{"chooseLangOfSynToUpdate",		"Choose the LANGUAGE of termTxCpt_sAll you want to update"},
		/* MISC: */
		{"integrated",				"integrated"},
		{"nonIntegrated",		"NON-integrated"},
		{"viewer_sTextPaneTip",			"Double-Click on a word to FIND the corresponding concept"},
		{"titleLabel",				"AAj (Programo-FoEkoSeoKonco)"},
		/* VIEWER-SEARCH: */
		{"LabelFindBy",					"Find FOeKOsEOkONCO by: "},
		{"ButtonFindByRelated",	"Related"},
		/*	VIEWER-TREE: */
		{"PART:NAME",			"DO: NAMO"},
		{"parCmp",					"PART-KOMPLETEINO"},
		{"nih",						"PART: Non-Inherited:"},
		{"inh",						"PART: Inherited from: "},
		{"sib",						"FRATEPO"},
		{"specificCmp",		"SPECIFIC-KOMPLETEINO"},

	};
}