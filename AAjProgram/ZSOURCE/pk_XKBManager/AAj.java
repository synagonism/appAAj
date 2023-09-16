/*
 * AAj.java - The application.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 1997 Nikos Kasselouris
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pk_XKBManager;

import pk_Html.HtmlMgr;
import pk_Util.*;
import pk_XKBEditor.*;
import org.xml.sax.SAXException;
import org.xml.sax.InputSource;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.util.Date;
import javax.swing.*;
//import javax.swing.JOptionPane;
import javax.swing.event.*;
import javax.swing.table.TableColumn;
import javax.swing.tree.*;
import javax.swing.text.*;

/**
 * The application. Creates the UserInterface and displays
 * an AAj--sensorial-b-concept (an xml file).
 *
 * @modified 2009.08.08
 * @since	1997.05.12 (v00.00.01)
 * @author HoKoNoUmo
 */
public class AAj extends JPanel
{
	static final long serialVersionUID = 21L;
	//the FINAL edition IN a version will hold the ending date.
	private String									strVersion = "00.02.02 (2010.01.02-2010.06.20)";
	private JMenuBar								jmbMenubar;
	private JTextField							jtfFindByUniqueName;
	private JTextField							jtfFindByFrTerm;
//	private JPanel									jpanelName;	/* concept name	*/
//	private JSplitPane							splitPane; 	/*	editor and toc	*/
	private Hashtable<String,Action>	htCommands = new Hashtable<String,Action>();

	public RetrieveFrame_AskAAj				frameAsk=null;
	public RetrieveFrame_Translation	frameTrans=null;
	public FindByXmlFiles					frameFindByXmlFiles=null;
	public FindByName							frameFindByName=null;
	public FindByRelated						frameFindByRelated=null;
	public ViewAllLevels						frameAllLevels=null;
	public ViewAllGenLevels				frameAllGens=null;
	public ViewAllSubLevels				frameAllSpes=null;
	public ViewAllParLevels				frameAllPars=null;
	public ViewAllWhoLevels				frameAllWhos=null;
	public History									frameHistory=null;
	public ViewSpecificComplement		frameSpecificCompl=null;
	/** I have put this variable in Util class in order to test
	 * parts of the program. */
	public static String						AAj_sDir=Util.AAj_sDir;//has and separator

	public static JFrame						frameAAj;
	public static Editor						frameEditor=null;
	public static Locale						locale;
	public static String						lng="eng";//holds the displayed language in 3letters
	public static ResourceBundle 		rbLabels;
	public static ResourceBundle 		rbMenus;
	/** I need it to read one internal-xcpt. */
	public static boolean					blReadInt = false;
	/* concept related */
	/** The name of the File of the displayed-xConcept: Sensorial-b-concept@hknu.it-1.xml */
	public static String						xcpt_sFileName;
	/** The Formal-Name of the displayed-xConcept: Hard-Disk@hknu.it-1 */
	public static String						xcpt_sFormalName;	//2001.03.08
	/** The ID of the displayed-xConcept: hknu.it-1 */
	public static String						xcpt_sFormalID;
	/** The directory of the displayed-xConcept: c:\java\AAj\working\AAjKB\infotech. */
	public static String						xcpt_sDir;
	/** The last INTERNAL-ID of the displayed-xConcept. */
	public static String						xcpt_sLastIntID;
	/** The file-name of the host IF the displayed-xConcept is internal. */
	public static String						xcpt_sHostFile;
	/** The ID of the host IF the displayed-xConcept is internal. */
	public static String						xcpt_sHostID;
	/** Holds the name of the displayed-xConcept. */
	public static JLabel						xcpt_sLblNameCpt;
	public static JLabel						xcpt_sLblSrWorldview;
	public static JLabel						xcpt_sLblSrSubWorldview;
	/** The author of the displayed-xConcept. */
	public static String						xcpt_sAuthor;
	public static String						xcpt_sCreated;
	public static String						xcpt_sModified;
	public static JLabel						xcpt_sLblIntegrated=new JLabel();
	public static Stack<String>			stack=new Stack<String>();	//for history of concepts.
	/** The current author of the XKnowledgeBase. */
	public static String						kb_sAuthor;
	//When we change the name of a concept, then we must change its
	//formalName because the majority of users must be viewer and not authors. 2009.06.11
	//when we display a sbcpt, this varialble contains the ids of the xcpts
	//with old names.
	public static Vector<String>		vectorOldNamesID=new Vector<String>();
	//Counters for attributes
	public static int							intInh, intNInh, intName,intPar,intParInh,
		intParNInh,intWho,intGen,intSpe,intDiv,intEnv,intArg;
	/** key= XBrainWorldview-shortName<br/>
	 *  value= Array(description-directory(FullName). */
	public static Map<String,String[]> trmapSrBrWorldview=new TreeMap<String,String[]>();
	/** key= XBrainSubWorldview-shortName (hknu.it)<br/>
	 *  value= Array(description-directory(FullName)-lastID-NoInternals). */
	public static Map<String,String[]> trmapSrBrSubWorldview=new TreeMap<String,String[]>();
	/** key= XBrainSubWorldview (hknu.it)<br/>
	 *  value= treeset with its emptyFrNUMBERs */
	public static Map<String,TreeSet<String>>	 tmapEmptyID=
									new TreeMap<String,TreeSet<String>>();	//vd-TreeSet
	/* variables:	toc */
	public static JTree										viewer_sToC;
	public static DefaultMutableTreeNode		root = new DefaultMutableTreeNode("XMLcONCEPT");
	public static DefaultTreeModel					tocModel = new DefaultTreeModel(root);
	public static DefaultMutableTreeNode		tocNodePartName = null; //lgCptNames node
	public static DefaultMutableTreeNode		tocNodeNoun = null; //nouns
	public static DefaultMutableTreeNode		tocNodeVrb = null;
	public static DefaultMutableTreeNode		tocNodeAdj = null;
	public static DefaultMutableTreeNode		tocNodeAdv = null;
	public static DefaultMutableTreeNode		tocNodeDef = null;
	public static DefaultMutableTreeNode		tocNodePart = null;
//	public static DefaultMutableTreeNode		tocNodeNih = null;//toDo: to abolish inherited|nonInherited nodes 2009.06.08
//	public static DefaultMutableTreeNode		tocNodeInh = null;
	public static DefaultMutableTreeNode		tocNodeWho = null;
	public static DefaultMutableTreeNode		tocNodeParCmp = null; //partial-complements
	public static DefaultMutableTreeNode		tocNodeGen = null;
	public static DefaultMutableTreeNode		tocNodeSpeCmp = null; //specific-complements
	public static DefaultMutableTreeNode		tocNodeSpe = null;
	public static DefaultMutableTreeNode		tocNodeSpeDiv = null; //divizoSpesifepto
	public static DefaultMutableTreeNode		tocNodeEnv = null; //envieinos
	public static DefaultMutableTreeNode		tocNodeEnvCpt = null;
	public static DefaultMutableTreeNode		tocNodeEnvRel = null;
	public static DefaultMutableTreeNode		tocNodeEnt = null; //2009.09.09
	public static DefaultMutableTreeNode		tocNodeAtt = null; //2007.10.11
	public static DefaultMutableTreeNode		tocNodeAttRel = null;
	public static DefaultMutableTreeNode		tocNodeNonAtt = null; //2009.11.09
	public static DefaultMutableTreeNode		tocNodeNonAttRel = null; //2009.11.09
	public static DefaultMutableTreeNode		tocNodeArg = null; //2009.10.25
	public static DefaultMutableTreeNode		tocNodeRef = null; //2009.11.12
	public static DefaultMutableTreeNode		tocNodeRefRel = null; //2009.11.12
	public static Map<String,DefaultMutableTreeNode> inheritorsFound=new HashMap<String,DefaultMutableTreeNode>(); //holds the inherited-sbcpt for the xcpt's toc.
	public static MouseListener 						mouseListenerTree=null;
	/* variables:	editor */
	public static StyleContext							stlCxt = new StyleContext();
	public static DefaultStyledDocument 		doc = new DefaultStyledDocument(stlCxt);
	public static JTextPane								viewer_sTextPane= new JTextPane(doc);
	/* BLACK bold letters	*/
	public static Style									s0 = stlCxt.addStyle(null,	null);
	static {
		StyleConstants.setForeground(s0, Color.black);
		StyleConstants.setFontFamily(s0, "Palatino Linotype");
		StyleConstants.setFontSize(s0, 14);
		StyleConstants.setBold(s0, true);}
		//StyleConstants.setLeftIndent(s0, 20);}
	/* RED bold underlined letters */
	public static Style									 s1 = stlCxt.addStyle(null,	null);
	static {
		StyleConstants.setForeground(s1, Color.red);
		StyleConstants.setFontFamily(s1, "sansserif");
		StyleConstants.setFontSize(s1, 18);
		StyleConstants.setBold(s1, true);
		StyleConstants.setUnderline(s1,	true);}
	/* BLUE bold letters */
	public static Style									 s2 = stlCxt.addStyle(null,	null);
	static {
		StyleConstants.setForeground(s2, Color.blue);
		StyleConstants.setFontFamily(s2, "sansserif");
		StyleConstants.setFontSize(s2, 18);
		StyleConstants.setBold(s2, true);}
	/* magenta bold letters	*/
	public static Style									 s3 = stlCxt.addStyle(null,	null);
	static {
		StyleConstants.setForeground(s3, Color.magenta);
		StyleConstants.setFontFamily(s3, "sansserif");
		StyleConstants.setFontSize(s3, 16);
		StyleConstants.setBold(s3, true);}
	// caret positions
	public static Map<String,String>		hmapCaret=new HashMap<String,String>();
	public pk_XKBEditor.StatusBar				statusBar = new pk_XKBEditor.StatusBar();

	Action[] defaultActions=
	{
		//SYSTEM MENU
		new ActionExit(),

		//MENU BUILD-KNOWLEDGEBASE
		new ActionBuildChangeSubWorldview(),
		new ActionEditor(),								//Opens the editor with current-sbcpt.
		//integration commands
		new ActionIntegrationPart(),
		new ActionIntegrationWhole(),
		new ActionIntegrationGeneric(),
		new ActionIntegrationSpecific(),
		new ActionIntegrationEnvironment(),
		new ActionIntegrationTotal(),
		//add concepts commands:
		new ActionAddNewPart(),			 //Adds NEW attribute-sbcpt to crt-sbcpt.
		new ActionAddNewWhole(),
		new ActionAddNewGeneric(),
		new ActionAddNewSpecific(),
		new ActionAddNewEnvironment(),
		new ActionAddNewAttribute(),
		new ActionAddAttPart(),						//adds EXISTING attribute
		new ActionAddAttWhole(),
		new ActionAddAttGeneric(),
		new ActionAddAttSpecific(),
		new ActionAddAttEnvironment(),
		new ActionAddUnrelatedConcept(),		//Adds new Concept unrelated to current with id-number=last+1	or empty.
		//delete commands:
		new ActionDelConcept(),						 //Delete the current sbcpt.
		//del related	(att,	who, gen...)
		new ActionDelConceptUnrelated(),
		//part commands.
		new ActionPartOverriding(),
		new ActionPartGenericization(),
		new ActionPartMakeInherited(),
		//generic commands:
		new ActionAddDivisionSpecific(),
		//Term commads:
		new ActionNameMgt(),
		new ActionLgConceptTermUpdate(),
		new ActionLgTerminalKomoMgt(),
		//Brainual-worldview commands:
		new ActionAddSrWorldview(),
		new ActionAddSrSubWorldview(),
		new ActionKBSetAuthor(),						//Set the vd author.
		new ActionSaveVariables(),

		//MENU RETRIEVE-KNOWLEDGEBASE
		new ActionBack1(),
		new ActionBack2(),
		new ActionRefresh(),
		//separator
		new ActionGetSrCptInfo(),
		new ActionViewAll(),
		new ActionViewAllPars(),
		new ActionViewAllWhos(),
		new ActionViewAllGens(),
		new ActionViewAllSpes(),					//View the toc with ALL specifics of current.
		//
		new ActionViewSpecificComplement(),
//		new ViewPartialComplAction(),
		//
		new ActionOpen(),
		new ActionFindByName(),					//searches lng/term.index.xml for	&lt;name&gt; elements.
		new ActionFindByRelated(),				//searches related sbcpt files.
		new ActionFindByUniqueName(),				//searches lng/term.index.xml for an id.
		new ActionFindByFormalName(),
		new ActionFindSelected(),				//searches the selected text on viewer_sTextPane.
		new ActionFindByXmlFiles(),				//searches the xcpt-files.
		new ActionFindByFileList(),				//searches the list of sbcpt-files for ids/formal-names.
		new ActionFindTermsOfName(),			//finds the FORMS of an xcpt's synonym.
		//
		new ActionAskAAj(),
		new ActionTranslation(),
		//
		new ActionKBGetInfo(),
		new ActionKBGetAuthor(),						//Get the current	'author'.
		//
		new ActionEnglish(),
		new ActionGreek(),
		new ActionEsperanto(),
		new ActionKomo(),

		//
		new ActionHelpHtml(),
	};

	//misc variables.
	Font fontSSp12 = new Font("sansserif", Font.PLAIN, 12);
	Font fontSSp14 = new Font("sansserif", Font.PLAIN, 14);
	Font fontSSp16 = new Font("sansserif", Font.PLAIN, 16);
	Font fontSSp18 = new Font("sansserif", Font.PLAIN, 18);
	Font fontssb14 = new Font("sansserif", Font.BOLD,	14);
	Font fontssb16 = new Font("sansserif", Font.BOLD,	16);

	Font fontPLp14 = new Font("Palatino Linotype", Font.PLAIN, 14);
	// I need it when I dispose AAj frames.
	MouseListener mlText = new MouseListenerText();

	//The vector bellow will hold ther RELATIONS of the current-sbcpt.
	//For economy reasons I'm using only ids.
	//Also I fill
	public static Vector<String> vectorParID=null;			//holds the ids of part concepts.
	public static Vector<String> vectorParFileID=null;	//holds the ids of 'file'	attribute of sbcpt's attributes.
	public static Vector<String> vectorParGenID=null;	 	//holds the ids of 'generic' attribute of sbcpt's attributes.
	public static Vector<String> vectorWhoID=null;			//holds the ids of who.
	public static Vector<String> vectorGenID=null;			//holds the ids of gen.
	public static Vector<String> vectorSpeID=null;			//holds the ids of spe.
	public static Vector<String> vectorEnvID=null;			//holds the ids of env.


//************************************************************************
//************************************************************************

	/**
	 * Create the main frame with the user interface.
	 *
	 * @modified 2005.09.08
	 * @since	1997.05.12
	 * @author HoKoNoUmo
	 */
	public static void main(String[] args)
	{
		//create the log writer;
		log("start");
		log("PROGRAM-DIRECTORY: "+AAj_sDir);

		/*
		//first ask for a natural-language.
		Object[] langs = {"English", "Greek (Ελληνικά)"};
		Object selectedValue = JOptionPane.showInputDialog(null,
					"Choose a USER-INTERFACE-LANGUAGE",								//message
					"Choose One",																			//title
					JOptionPane.INFORMATION_MESSAGE,									//message type
					new ImageIcon("resources/AAj_icon.gif"),					//icon
					langs,																						//values to select
					langs[0]);																				//initial selection
		if (selectedValue!=null)
		{
			if (selectedValue.equals("English")) locale = new Locale("en", "US");
			else if	(selectedValue.equals("Greek (Ελληνικά)")) locale = new Locale("el", "GR");
			else locale = new Locale("en", "US");
		}
		else System.exit(1);
		*/
		locale = new Locale("en", "US"); //2005.09.05
		lng="eng";

		rbLabels = ResourceBundle.getBundle("pk_Util.BundleLabels",	locale);
		rbMenus = ResourceBundle.getBundle("pk_Util.BundleMenus",	locale);

		frameAAj = new JFrame();
		frameAAj.setTitle(rbLabels.getString("titleLabel"));
		frameAAj.setBackground(Color.lightGray);
		frameAAj.getContentPane().setLayout(new BorderLayout());
		frameAAj.getContentPane().add("Center", new AAj());
		Util.setFrameIcon(frameAAj);

		display(args[0]);

		frameAAj.addWindowListener(new FrameCloser());
		frameAAj.pack();
		frameAAj.setSize(new Dimension(859, 500));
		frameAAj.setLocation(10,0);
		frameAAj.setVisible(true);
//		frame.dispose();
	}


	/**
	 * Constructor.
	 * @modified 1999.04.10
	 * @author HoKoNoUmo
	 */
	public AAj()
	{
		//JPanel will use a double buffer.
		super(true);
		setBorder(BorderFactory.createEtchedBorder());
		setLayout(new BorderLayout());

		// initialize variables.
		readVariables();

		try	{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}	catch	(Exception exc)	{
			System.err.println("Error loading L&F: " + exc);
		}

		/* commands	*/
		Action[] actions = TextAction.augmentList(viewer_sTextPane.getActions(), defaultActions);
		for	(int i = 0;	i	<	actions.length;	i++) {
			Action a = actions[i];
			htCommands.put((String)a.getValue(Action.NAME),	a);
		}

		/* JMenuBar on NORTH Layout	*/
		jmbMenubar = new JMenuBar();
		jmbMenubar.setFont(fontSSp18);
		makeMenuProgram(jmbMenubar);
		makeMenuRetrieve(jmbMenubar);
		makeMenuBuild(jmbMenubar);
		makeMenuLango(jmbMenubar);
		makeMenuHelp(jmbMenubar);
		add("North", jmbMenubar);

		/* create NamePanel for concept-name	*/
		JLabel jlblSrWorldview = new JLabel(AAj.rbLabels.getString("xWorldView")+":",JLabel.RIGHT);
		jlblSrWorldview.setFont(fontSSp16);
		jlblSrWorldview.setForeground(Color.blue);
		JLabel jlblSrSubWorldview = new JLabel(AAj.rbLabels.getString("xSubWorldView")+":",JLabel.RIGHT);
		jlblSrSubWorldview.setFont(fontSSp16);
		jlblSrSubWorldview.setForeground(Color.blue);
		JLabel jlblNameCpt = new JLabel(rbLabels.getString("foEkoKoncosNamo")+":", JLabel.RIGHT);
		jlblNameCpt.setFont(fontSSp16);
		jlblNameCpt.setForeground(Color.blue);
		xcpt_sLblSrWorldview=new JLabel();
		xcpt_sLblSrWorldview.setFont(new Font("sansserif", Font.BOLD,	16));
//		xcpt_sLblSrWorldview.setText("HoKoNoUmo");//toDo
		xcpt_sLblSrSubWorldview=new JLabel();
		xcpt_sLblSrSubWorldview.setFont(new Font("sansserif", Font.BOLD,	16));
//		sbcpts_LabelSrSubWorldview.setText("simbano");
		xcpt_sLblNameCpt=new JLabel();
		xcpt_sLblNameCpt.setFont(new Font("sansserif", Font.BOLD,	18));
//		xcpt_sLblNameCpt.setForeground(Color.black);
		xcpt_sLblIntegrated.setFont(fontSSp14);

		JPanel jpanelNmSouth = new JPanel();
		jpanelNmSouth.setBackground(Color.cyan);
		jpanelNmSouth.add(jlblSrWorldview);
		jpanelNmSouth.add(xcpt_sLblSrWorldview);
		JLabel keni = new JLabel("");
		keni.setPreferredSize(new Dimension(17, 5));
		jpanelNmSouth.add(keni);
		jpanelNmSouth.add(jlblSrSubWorldview);
		jpanelNmSouth.add(xcpt_sLblSrSubWorldview);
		JPanel jpanelNameNorth = new JPanel();
		jpanelNameNorth.setBackground(Color.cyan);
		jpanelNameNorth.add(jlblNameCpt);
		jpanelNameNorth.add(xcpt_sLblNameCpt);
		jpanelNameNorth.add(xcpt_sLblIntegrated);
		JPanel jpanelName = new JPanel(new BorderLayout());
		jpanelName.setBackground(Color.cyan);
		jpanelName.setBorder(BorderFactory.createEtchedBorder());
		jpanelName.add(jpanelNameNorth, BorderLayout.NORTH);
		jpanelName.add(jpanelNmSouth, BorderLayout.SOUTH);
		jpanelName.setPreferredSize(new Dimension(859,59));

		/* make splitPane panel for viewer's-textPane and toc	*/
		createTOC();	/* Create the JTreeModel.	*/
		viewer_sTextPane.addMouseListener(mlText);
		viewer_sTextPane.setToolTipText(rbLabels.getString("viewer_sTextPaneTip"));
		JScrollPane  treeView, textView;
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent (treeView = new JScrollPane (viewer_sToC));
		splitPane.setRightComponent	(textView = new JScrollPane	(viewer_sTextPane));
		splitPane.setDividerLocation (259);
		splitPane.setContinuousLayout(true);

		/* jpanelSearch */
		JPanel jpanelSearch = new JPanel();
		jpanelSearch.setBackground(Color.blue);
		jpanelSearch.setBorder(BorderFactory.createEtchedBorder());
		JLabel labelFindBy = new JLabel(rbLabels.getString("LabelFindBy"),Label.LEFT);
		labelFindBy.setForeground(Color.white);
		labelFindBy.setFont(fontssb16);
		jpanelSearch.add(labelFindBy);
		JLabel empty = new JLabel("");
		empty.setPreferredSize(new Dimension(17, 10));
		jpanelSearch.add(empty);
		JButton btnFindByName = new JButton(rbLabels.getString("Term"));
		btnFindByName.setFont(fontSSp14);
		btnFindByName.setForeground(Color.black);
		btnFindByName.setBackground(Color.white);
		btnFindByName.addActionListener(new ActionFindByName());
		jpanelSearch.add(btnFindByName);
		JLabel empty2 = new JLabel("");
		empty2.setPreferredSize(new Dimension(17,	10));
		jpanelSearch.add(empty2);
		JButton btnFindByRelated = new JButton(rbLabels.getString("ButtonFindByRelated"));
		btnFindByRelated.setFont(fontSSp14);
		btnFindByRelated.setForeground(Color.black);
		btnFindByRelated.setBackground(Color.white);
		btnFindByRelated.addActionListener(new ActionFindByRelated());
		jpanelSearch.add(btnFindByRelated);
		JLabel labelFindByUniqueName = new JLabel(rbLabels.getString("UniqueName")+":");
		labelFindByUniqueName.setForeground(Color.white);
		labelFindByUniqueName.setFont(fontSSp14);
		jpanelSearch.add(labelFindByUniqueName);
		jtfFindByUniqueName = new JTextField(9);
		jtfFindByUniqueName.setBackground(Color.white);
		jtfFindByUniqueName.setFont(fontssb14);
		jtfFindByUniqueName.setPreferredSize(new Dimension(126,	30));
		jtfFindByUniqueName.addActionListener(new ActionFindByUniqueName());
		jtfFindByUniqueName.setActionCommand("jtfFindByUniqueName");
		jpanelSearch.add(jtfFindByUniqueName);
		JLabel labelFindByFRName = new JLabel(rbLabels.getString("FormalTerm")+":");
		labelFindByFRName.setForeground(Color.white);
		labelFindByFRName.setFont(fontSSp14);
//		jpanelSearch.add(labelFindByFRName);
		jtfFindByFrTerm = new JTextField(9);
		jtfFindByFrTerm.setBackground(Color.white);
		jtfFindByFrTerm.setFont(fontssb14);
		jtfFindByFrTerm.setPreferredSize(new Dimension(126,	30));
		jtfFindByFrTerm.addActionListener(new ActionFindByFormalName());
		jtfFindByFrTerm.setActionCommand("jtfFindByFrTerm");
//		jpanelSearch.add(jtfFindByFrTerm);
// since formal-term is the first-noun there is no need for this search.
// 2008.10.06
		JLabel labelSrWV = new JLabel("MsgChooseSrWV",Label.RIGHT);
		labelSrWV.setForeground(Color.white);
		labelSrWV.setFont(fontssb14);
		jpanelSearch.add(labelSrWV);
		//toDo 2009.03.28

		/* AAj JPanel for conceptname,	(toc-viewer's-textPane), tfield, */
		JPanel jpanelAAj = new JPanel();
//		jpanelAAj.setBorder(BorderFactory.createEtchedBorder());
		jpanelAAj.setLayout(new BorderLayout());
		jpanelAAj.add("North", jpanelName);
		jpanelAAj.add("Center", splitPane);
		jpanelAAj.add("South", jpanelSearch);

		/* jtulbar */
		JToolBar jtulbar = new JToolBar();
		jtulbar.setFont(fontSSp12);
		jtulbar.add(createTool("jmiRetrieveBack1"));
		jtulbar.add(createTool("jmiRetrieveBack2"));
		jtulbar.add(createTool("refresh"));
		jtulbar.add(createTool("open"));
		jtulbar.add(Box.createHorizontalStrut(9));
		jtulbar.add(createTool("editor"));
		jtulbar.add(Box.createHorizontalStrut(9));
		jtulbar.add(createTool("findName"));
		jtulbar.add(createTool("findSelected"));
		jtulbar.add(createTool("copy-to-clipboard"));
		jtulbar.add(Box.createHorizontalGlue());
//		jtulbar.addSeparator();

		/* center2 JPanel for TOOLBAR and jpanelAAj */
		JPanel jpanelToolbarAAj = new JPanel();
		jpanelToolbarAAj.setLayout(new BorderLayout());
		jpanelToolbarAAj.add("North",	jtulbar);
		jpanelToolbarAAj.add("Center", jpanelAAj);

		/* status bar	*/
		statusBar.setText(strVersion);
//		statusBar.add(new JLabel(strVersion));
		/* add center and south areas on AAj */
		add("Center",	jpanelToolbarAAj);
		add("South", statusBar);
		/*
				AAj has on c=jpanelToolbarAAj, s=statusBar,
				jpanelToolbarAAj:	n=jtulbar, c=AAjpanel
				AAjpanel: n=jpanelName, c=splitPane,	s=jpanelSearch
				jpanelName=	concept name
				splitPane= toc, viewer_sTextPane,
		*/
	}


	//************************************************************************
	// INITIALIZATION Methods
	//************************************************************************
	/**
	 * Initializes the variables(properties) from the
	 * AAjDir/resources/variables.txt file
	 *
	 * @modified 2008.04.26
	 * @since	1999
	 * @author HoKoNoUmo
	 */
	public void readVariables()
	{
		try {
			String directory = AAj_sDir + "resources";
			String fileName = directory	+	File.separator +"variables.txt";
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);
			String ln = null;
			StringTokenizer tokenizer = null;

			while	((ln = br.readLine())	!= null)
			{
				ln.trim();

				//the current/last author of the knowledge-base
				if (ln.startsWith("kb_sAuthor"))
					kb_sAuthor=ln.substring(ln.indexOf("=")+1, ln.length());

				//the worldviews of the knowledge-base
				else if	(ln.startsWith("XBrainWorldview="))
				{
					ln=ln.substring(ln.indexOf("=")+1, ln.length());
					tokenizer= new StringTokenizer(ln, ";");
					String[] v = new String[2];
					String key = tokenizer.nextToken();	//shortName
					v[0] = tokenizer.nextToken();				//description
					v[1] = tokenizer.nextToken();				//directory(fullName)
					trmapSrBrWorldview.put(key, v);
				}

				//the VIEWS of all worldviews
				else if	(ln.startsWith("XBrainSubWorldview="))
				{
					ln=ln.substring(ln.indexOf("=")+1, ln.length());
					tokenizer= new StringTokenizer(ln, ";");
					String[] a = new String[4];
					String key = tokenizer.nextToken();	//shortName
					a[0] = tokenizer.nextToken();				//description
					a[1] = tokenizer.nextToken();				//directory(fullName)
					a[2] = tokenizer.nextToken();				//last id
					a[3] = tokenizer.nextToken();				//internals
					trmapSrBrSubWorldview.put(key,	a);
				}

				//the emptyFrNUMBERs of all views
				else if	(ln.startsWith("emptyFrNUMBER"))
				{
					ln=ln.substring(ln.indexOf("@")+1, ln.length());
					String vd = ln.substring(0,	ln.indexOf("="));
					String ids=ln.substring(ln.indexOf("=")+2, ln.length()-1);
					ids=ids.replace(',', ' ');
					tokenizer = new StringTokenizer(ids);
					TreeSet<String> values = new TreeSet<String>();
					while (tokenizer.hasMoreTokens())
					{
						values.add(tokenizer.nextToken());
					}
					tmapEmptyID.put(vd,	values);
				}

			}
			br.close();
		}
		catch	(IOException e){
			JOptionPane.showMessageDialog(null,"AAj.readVariables:	File variables.txt NOT found");
		}
	}


	/**
	 * Save back the variables to file resources/variables.txt<p>
	 *
	 * IF the program will end ubnormally THEN this function doesn't works
	 * and we are loosing the ids of the last sbcpts we created. 2000.04.24
	 *
	 * @modified 2008.04.26
	 * @since	1999
	 * @author HoKoNoUmo
	 */
	public static void saveVariables()
	{
		log("close");

		try {
			String directory = AAj_sDir +"resources";
			String fileName = directory	+	File.separator +"variables.txt";
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("kb_sAuthor=" +kb_sAuthor);
			bw.newLine();

			bw.newLine();
			bw.write("#XBRAINWORLDVIEW= SHORTNAME; DESCRIPTION;	DIRECTORY(FullName);");
			bw.newLine();
			for (Iterator i = trmapSrBrWorldview.entrySet().iterator();	i.hasNext(); )
			{
				Map.Entry e = (Map.Entry) i.next();
				String[] v = (String[])	e.getValue();
				bw.write("XBrainWorldview=" +e.getKey() +";" +v[0] +";" +v[1] +";");
				bw.newLine();
			}

			bw.newLine();
			bw.write("#XBRAINSUBWORLDVIEW= SHORTNAME; DESCRIPTION;	DIRECTORY(FullName); LAST-ID; INTERNALS;");
			bw.newLine();
			for (Iterator i = trmapSrBrSubWorldview.entrySet().iterator();	i.hasNext(); )
			{
				Map.Entry e = (Map.Entry) i.next();
				String[] a = (String[])	e.getValue();
				bw.write("XBrainSubWorldview=" +e.getKey() +";" +a[0] +";" +a[1] +";" +a[2] +";" +a[3] +";");
				bw.newLine();
			}

			bw.newLine();
			bw.write("#EMPTY-FrNUMBERS: emptyFrNUMBER@hknu.it=[14, 19]");
			bw.newLine();
			for (Iterator i = tmapEmptyID.entrySet().iterator(); i.hasNext();	)
			{
				Map.Entry e = (Map.Entry) i.next();
				TreeSet v = (TreeSet) e.getValue();
				if (!v.isEmpty())
				{
					bw.write("emptyFrNUMBER@" +e.getKey()	+"=" +v.toString());
					bw.newLine();
				}
			}

			bw.close();
		}
		catch	(IOException e)
		{
			JOptionPane.showMessageDialog(null,"AAj.saveVariables:	Problem in writing");
		}
	}


	/**
	 * Displays an AAj-SConcept.
	 *
	 * @param sbcptID A unique-name of the xcpt displayed.
	 * @modified 2004.07.03
	 * @since	1999.04.25
	 * @author HoKoNoUmo
	 */
	public static void display(String sbcptID)
	{
		xcpt_sFormalID=Util.getXCpt_sFormalID(sbcptID);//eg.	it-1 or it-26#1.
		xcpt_sFileName=Util.getXCpt_sLastFileName(xcpt_sFormalID);	//eg.	Entity@hknu.symb-26.xml
		xcpt_sFormalName=Util.getXCpt_sFormalName(xcpt_sFileName);

		viewer_sTextPane.setStyledDocument(doc = new DefaultStyledDocument(stlCxt));
		setNewTocContent();

		//for back action fead stack.
		String lastSrCpt=null;
		try	{lastSrCpt = stack.peek();}//look at the lg_object without removing.
		catch	(EmptyStackException e)	{lastSrCpt = "null";}
		if (!lastSrCpt.equals(xcpt_sFileName))	stack.push(xcpt_sFileName);

		//initialize
		inheritorsFound=new HashMap<String,DefaultMutableTreeNode>();
		xcpt_sLblNameCpt.setText("");	//if termTxCpt_sAll empty, display nothing.
		//initialize the counters of sbcpt's statistics.
		intName=intPar=intParInh=intParNInh=intWho=intGen=intSpe=intDiv=intEnv=intArg=0;
		hmapCaret=new HashMap<String,String>();
		vectorOldNamesID=new Vector<String>();
		//clear the vectors that contain the corelatons of current-sbcpt.
		vectorParID=vectorParFileID=vectorParGenID=vectorWhoID=vectorGenID=
								vectorSpeID=vectorEnvID=new Vector<String>();

		//count the gens.
		Extract ex = new Extract();
		try	{ex.extractSpecifics(sbcptID);}
		catch	(NullPointerException npe){}
		if (!ex.vectorSpeID.isEmpty())
		{
			vectorSpeID = ex.vectorSpeID;
			intSpe=vectorSpeID.size();
		}
		//display the same sbcpt in editor.
		if (frameEditor!=null	&& frameEditor.isVisible())
		{
			//when we insert from editor,	we don't do it double.
			if (!frameEditor.sbcpt.equals(sbcptID))frameEditor.insertFile(sbcptID);
		}

		//display a	FILE-XCPT.
		if (sbcptID.indexOf("#")==-1)
		{
			blReadInt=false;
			xcpt_sDir = Util.getXCpt_sFullDir(xcpt_sFileName);
			String fileName = Util.getXCpt_sFullDir(xcpt_sFileName) +File.separator +xcpt_sFileName;
			try {
				Parser_XCpt parser = new Parser_XCpt();
//				FileReader reader = new FileReader(fileName);
				FileInputStream fis = new FileInputStream(fileName);
				InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
				BufferedReader reader = new BufferedReader(isr);
				parser.parseFromReader(reader, fileName);
				reader.close();
			}
			catch	(IOException e){
				JOptionPane.showMessageDialog(null,"AAj.display: Parser_XCpt");
			}
			viewer_sTextPane.setCaretPosition(doc.getStartPosition().getOffset());
			//set the last names for kptext-fln and generic-attributes
			//ppp
//			if (!vectorOldNamesID.isEmpty()) Util.setLastCptNames(xcpt_sFormalID, vectorOldNamesID);
		}

		//display an INTERNAL-XCPT.
		else {
			blReadInt=true;
			xcpt_sHostID=Util.getSrCptInt_sHostID(xcpt_sFormalID);//eg. it-1.
			xcpt_sHostFile=Util.getXCpt_sLastFileName(xcpt_sHostID);//eg."Sensorial-b-concept@hknu.it-1.xml"
			String hostFFile=Util.getXCpt_sLastFullFileName(xcpt_sHostID);
			xcpt_sDir = Util.getXCpt_sFullDir(xcpt_sHostFile); //eg. c:\java\...\infotech.

			try {
				Parser_XCpt parser = new Parser_XCpt();
				FileReader reader = new FileReader(hostFFile);
				parser.parseFromReader(reader, hostFFile);
				reader.close();
			}
			catch	(IOException e){
				JOptionPane.showMessageDialog(null,"AAj.display: Parser_XCpt");
			}
			viewer_sTextPane.setCaretPosition(doc.getStartPosition().getOffset());
			//set the last names for kptext-fln and generic-attributes
//			if (!vectorOldNamesID.isEmpty()) Util.setLastCptNames(kptHostID, vectorOldNamesID);
		}
	}


	/**
	 * Creates the toc with the standard nodes.
	 * @modified 2009.04.16
	 * @author HoKoNoUmo
	 */
	void createTOC()
	{
		tocNodeDef = new DefaultMutableTreeNode(rbLabels.getString("REFINO_DEFINITION"));
		tocNodePartName = new DefaultMutableTreeNode(rbLabels.getString("REFINO_NAME"));
//		tocNodeWho = new DefaultMutableTreeNode(rbLabels.getString("REFINO_WHOLE"));
//		tocNodePart = new DefaultMutableTreeNode(rbLabels.getString("REFINO_PART"));
//		tocNodeParCmp = new DefaultMutableTreeNode(rbLabels.getString("REFINO_PARTcOMPLEMENT"));
//		tocNodeGen = new DefaultMutableTreeNode(rbLabels.getString("REFINO_GENERIC"));
//		tocNodeSpe = new DefaultMutableTreeNode(rbLabels.getString("REFINO_SPECIFIC"));
//		tocNodeSpeCmp = new DefaultMutableTreeNode(rbLabels.getString("REFINO_SPECIFICcOMPLEMENT"));
//		tocNodeEnv = new DefaultMutableTreeNode(rbLabels.getString("REFINO_ENVIRONMENT"));
//		tocNodeEnt = new DefaultMutableTreeNode(rbLabels.getString("REFINO_ENTITY"));
//		tocNodeAtt = new DefaultMutableTreeNode(rbLabels.getString("REFINO_ATTRIBUTE"));
		root = new DefaultMutableTreeNode(rbLabels.getString("XMLcONCEPT"));
		tocModel = new DefaultTreeModel(root);

		tocModel.insertNodeInto(tocNodeDef,	root,	0);
		tocModel.insertNodeInto(tocNodePartName,	root,	1);
//		tocModel.insertNodeInto(tocNodeWho,	root,	2);
//		tocModel.insertNodeInto(tocNodePart,	root,	3);
//		tocModel.insertNodeInto(tocNodeParCmp,	root,	4);
//		tocModel.insertNodeInto(tocNodeGen,	root,	5);
//		tocModel.insertNodeInto(tocNodeSpe,	root,	6);
//		tocModel.insertNodeInto(tocNodeSpeCmp,	root,	7);
//		tocModel.insertNodeInto(tocNodeEnv,	root,	8);
//		tocModel.insertNodeInto(tocNodeEnt,	root,	9);
//		tocModel.insertNodeInto(tocNodeAtt,	root,	10);
		/* final JTree */
		viewer_sToC = new JTree(tocModel);
		viewer_sToC.setRootVisible(true);
		viewer_sToC.setShowsRootHandles(true);
		viewer_sToC.setRowHeight(17);
		DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer)	viewer_sToC.getCellRenderer();
		tcr.setClosedIcon(null);
		tcr.setOpenIcon(null);
		tcr.setLeafIcon(null);
//		ToolTipManager.sharedInstance().registerComponent(viewer_sToC);
//		viewer_sToC.setCellRenderer(new ViewerTreeCellRenderer());
		mouseListenerTree=new MouseListenerTree();
		viewer_sToC.addMouseListener(mouseListenerTree);
	}

	/**
	 * Sets NEW info on the existing toc.
	 * @modified 2009.04.16
	 * @author HoKoNoUmo
	 */
	public static void setNewTocContent()
	{
		tocNodeDef = new DefaultMutableTreeNode(rbLabels.getString("REFINO_DEFINITION"));
		tocNodePartName = new DefaultMutableTreeNode(rbLabels.getString("REFINO_NAME"));
		tocNodeNoun = null;
		tocNodeVrb = null;
		tocNodeAdj = null;
		tocNodeAdv = null;
		tocNodeWho = null;
		tocNodePart = null;
		tocNodeGen = null;
		tocNodeSpe = null;
		tocNodeEnv = null;
		tocNodeEnvCpt=null;
		tocNodeEnvRel=null;
		tocNodeEnt = null;
		tocNodeAtt = null;
		tocNodeAttRel=null;
		tocNodeNonAtt = null;
		tocNodeNonAttRel=null;
		tocNodeArg=null;
		tocNodeRef=null;
		tocNodeRefRel=null;
		root = new DefaultMutableTreeNode(rbLabels.getString("XMLcONCEPT"));
		tocModel = new DefaultTreeModel(root);

		tocModel.insertNodeInto(tocNodeDef,	root,	0);
		tocModel.insertNodeInto(tocNodePartName,	root,	1);
//		tocModel.insertNodeInto(tocNodeWho,	root,	2);
//		tocModel.insertNodeInto(tocNodePart,	root,	3);
//		tocModel.insertNodeInto(tocNodeParCmp,	root,	4);
//		tocModel.insertNodeInto(tocNodeGen,	root,	5);
//		tocModel.insertNodeInto(tocNodeSpe,	root,	6);
//		tocModel.insertNodeInto(tocNodeSpeCmp,	root,	7);
//		tocModel.insertNodeInto(tocNodeEnv,	root,	8);
//		tocModel.insertNodeInto(tocNodeEnt,	root,	9);
//		tocModel.insertNodeInto(tocNodeAtt,	root,	10);

		//we don't create new toc.
		viewer_sToC.setModel(tocModel);
		viewer_sToC.setRootVisible(true);
		viewer_sToC.setShowsRootHandles(true);
		viewer_sToC.setRowHeight(17);
		DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer)	viewer_sToC.getCellRenderer();
		tcr.setClosedIcon(null);
		tcr.setOpenIcon(null);
		tcr.setLeafIcon(null);
		viewer_sToC.removeMouseListener(mouseListenerTree);
		mouseListenerTree=new MouseListenerTree();
		viewer_sToC.addMouseListener(mouseListenerTree);
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	static DefaultMutableTreeNode createNewNode(String name)
	{
		return new DefaultMutableTreeNode(name);
	}


	//************************************************************************
	// Menu creation Methods
	//************************************************************************

	/**
	 * @modified 1999.05.10
	 * @since	1999.05.10
	 * @author HoKoNoUmo
	 */
	void addMenuItem(JMenu menu, String cmd)
	{
		JMenuItem mi = new JMenuItem(rbMenus.getString(cmd + "MI"));
		mi.setFont(fontSSp12);
//		URL url = this.getClass().getResource("../resources/"	+cmd +".gif");
//		URL url=null;
//		try	{url = new URL(AAj_sDir +"resources"	+File.separator	+cmd +".gif");}
//		catch	(MalformedURLException e){System.out.println(e.toString());}
//		Image img=null;
//		try	{img=	Util.getImage("../resources/"+cmd+".gif",	AAj.class);}
//		catch	(Error e){}
//		if (img	!= null)
//		{
//			mi.setHorizontalTextPosition(JButton.RIGHT);
//			mi.setIcon(new ImageIcon(img));
//		}

//		ImageIcon ii=new ImageIcon(AAj_sDir+cmd+".gif");
//		if (ii !=	null)
//		{
//			mi.setHorizontalTextPosition(JButton.RIGHT);
//			mi.setIcon(ii);
//		}
//	mi.setActionCommand(cmd);
		Action a = htCommands.get(cmd);
		if (a	!= null)
		{
			mi.addActionListener(a);
			mi.addMouseListener(new MouseAction());
//		mi.addMouseListener(new MouseAction());
//		a.addPropertyChangeListener(createActionChangeListener(mi));
			mi.setEnabled(a.isEnabled());
		}
		else {
			mi.setEnabled(false);
		}
		menu.add(mi);
	}

	/**
	 * Create-newtool-button.
	 * @author HoKoNoUmo
	 */
	Component createTool(String tool)
	{
//		URL url = this.getClass().getResource("../resources/"+tool+".gif");
//		if (url	==null)	url=this.getClass().getResource("../resources/AAj.gif");
//		Image img=null;
//		try	{
//		img= Util.getImage("../resources/"+tool+".gif",	AAj.class);
//		}
//		catch	(Error e){img = Util.getImage("../resources/AAj.gif", AAj.class);}
//		JButton b = new JButton(new ImageIcon(img))


//		ImageIcon ii=new ImageIcon(Util.getImage("resources/"+tool+".gif", AAj.class));
//		JButton b=new JButton(ii)

//		URL url = this.getClass().getResource("resources/"+tool+".gif");
//		JButton b = new JButton(new ImageIcon(url))

		ImageIcon ii=new ImageIcon("resources/"+tool+".gif");
		JButton b=new JButton(ii)
		{	static final long serialVersionUID = 21L;
			public float getAlignmentY() {
			return	0.5f;	}	 };
		b.setFont(fontSSp12);
		b.setRequestFocusEnabled(false);
		b.setMargin(new Insets(1,1,1,1));
		String bs = rbMenus.getString(tool+"MI");
		if (bs !=	null){
			Action a = htCommands.get(tool);
			if (a	!= null) {
				b.setActionCommand(bs);
				b.addActionListener(a);
			}	else {			b.setEnabled(false);	}
			b.setToolTipText(rbMenus.getString(tool+"Tip"));
		}
		return b;
	}

	/**
	 * @modified 2009.05.30
	 * @author HoKoNoUmo
	 */
	void makeMenuProgram	(JMenuBar mb)
	{
		URL url = null;
		JMenu systemMenu = new JMenu(rbMenus.getString("systemMenu"));
		systemMenu.setFont(fontSSp14);
		systemMenu.addMouseListener(new MouseAction());

		JMenuItem aboutMI = new JMenuItem(rbMenus.getString("aboutMI"));
		aboutMI.addActionListener(new ActionAbout(frameAAj, "About AAj"));
		aboutMI.setFont(fontSSp12);
		aboutMI.addMouseListener(new MouseAction());
		systemMenu.add(aboutMI);

		systemMenu.addSeparator();
		addMenuItem(systemMenu,	"exit");
		mb.add(systemMenu);
	}


	/**
	 * @modified 2009.05.30
	 * @author HoKoNoUmo
	 */
	void makeMenuBuild (JMenuBar mb)
	{
		URL url = null;
		JMenu jMenuBuild = new JMenu(rbMenus.getString("menuBuild"));
		jMenuBuild.setFont(fontSSp14);
		jMenuBuild.addMouseListener(new MouseAction());

//		jMenuBuild.addSeparator();
		JMenu jMenuIntegrate=new JMenu(rbMenus.getString("menuIntegrate"));
		jMenuIntegrate.setFont(fontSSp12);
		jMenuIntegrate.addMouseListener(new MouseAction());
		jMenuBuild.add(jMenuIntegrate);
		addMenuItem(jMenuIntegrate, "totalIntegration");
		jMenuIntegrate.addSeparator();
		addMenuItem(jMenuIntegrate, "parIntegration");
		addMenuItem(jMenuIntegrate, "whoIntegration");
		addMenuItem(jMenuIntegrate, "genIntegration");
		addMenuItem(jMenuIntegrate, "subIntegration");
		addMenuItem(jMenuIntegrate, "envIntegration");

		addMenuItem(jMenuBuild, "editor");

		//CurrentConcept's names
//		jMenuBuild.addSeparator();
		addMenuItem(jMenuBuild, "nameMgt");
		addMenuItem(jMenuBuild, "updateTermFiles");
		//CurrentConcept's parts
//		jMenuBuild.addSeparator();
		addMenuItem(jMenuBuild, "parOverride");
		addMenuItem(jMenuBuild, "parGeneralize");
		addMenuItem(jMenuBuild, "parMakeInherited");
//		jMenuBuild.addSeparator();
		addMenuItem(jMenuBuild, "jmiBuildChangeSubWorldview");

		jMenuBuild.addSeparator();
		JMenu addCptMenu = new JMenu(rbMenus.getString("addCptMenu"));
		addCptMenu.setFont(fontSSp12);
		addCptMenu.addMouseListener(new MouseAction());
		jMenuBuild.add(addCptMenu);
		//add NEW XCPT nenu
		JMenu addNewMenu = new JMenu(rbMenus.getString("addNewMenu"));
		addNewMenu.setFont(fontSSp12);
		addNewMenu.addMouseListener(new MouseAction());
		addCptMenu.add(addNewMenu);
		addMenuItem(addNewMenu,	"addNewPar");
		addMenuItem(addNewMenu,	"addNewWho");
		addMenuItem(addNewMenu,	"addNewGen");
		addMenuItem(addNewMenu,	"addNewSpe");
		addMenuItem(addNewMenu,	"addNewEnv");
		addMenuItem(addNewMenu,	"addNewAtr");
		//add EXISTING XCPT menu
		JMenu addExistingMenu = new JMenu(rbMenus.getString("addExistingMenu"));
		addExistingMenu.setFont(fontSSp12);
		addExistingMenu.addMouseListener(new MouseAction());
		addCptMenu.add(addExistingMenu);
		addMenuItem(addExistingMenu, "addPar");
		addMenuItem(addExistingMenu, "addWho");
		addMenuItem(addExistingMenu, "addGen");
		addMenuItem(addExistingMenu, "menuAddAttSpecific");
		addMenuItem(addExistingMenu, "addEnv");
		//
		addMenuItem(addCptMenu,	"addUnrelated");

		//del sbcpt menu
		JMenu delCptMenu = new JMenu(rbMenus.getString("delCptMenu"));
		delCptMenu.setFont(fontSSp12);
		delCptMenu.addMouseListener(new MouseAction());
		jMenuBuild.add(delCptMenu);
		//
		addMenuItem(delCptMenu,	"delCrtCpt");	//delete current sbcpt.
		//del part menu
		JMenu delRelatedMenu = new JMenu(rbMenus.getString("delRelatedMenu"));
		delRelatedMenu.setFont(fontSSp12);
		delRelatedMenu.addMouseListener(new MouseAction());
		delCptMenu.add(delRelatedMenu);
		addMenuItem(delRelatedMenu,	"delPar");
		addMenuItem(delRelatedMenu,	"delWho");
		addMenuItem(delRelatedMenu,	"delGen");
		addMenuItem(delRelatedMenu,	"delSub");
		addMenuItem(delRelatedMenu,	"delDim");
		addMenuItem(delRelatedMenu,	"delEnv");
		//del unrelated
		addMenuItem(delCptMenu,	"delUnrelated");

		addMenuItem(jMenuBuild, "jmiAddDivisionSpecific");

		jMenuBuild.addSeparator();
		addMenuItem(jMenuBuild, "tmrKmlMgt");
		addMenuItem(jMenuBuild, "addSrWorldview");
		addMenuItem(jMenuBuild, "addSrSubWorldview");
		addMenuItem(jMenuBuild, "setKBAuthor");
		addMenuItem(jMenuBuild, "saveVariables");

		mb.add(jMenuBuild);
	}

	/**
	 * @modified 2004.04.21
	 * @author HoKoNoUmo
	 */
	void makeMenuRetrieve	(JMenuBar mb){
		JMenu jMenuRetrieve = new JMenu(rbMenus.getString("menuRetrieve"));
		jMenuRetrieve.setFont(fontSSp14);
		jMenuRetrieve.addMouseListener(new MouseAction());

		addMenuItem(jMenuRetrieve,	"jmiRetrieveBack1");
		addMenuItem(jMenuRetrieve,	"jmiRetrieveBack2");
		addMenuItem(jMenuRetrieve,	"refresh");

		jMenuRetrieve.addSeparator();
		addMenuItem(jMenuRetrieve,	"getSrCptInfo");
		addMenuItem(jMenuRetrieve,	"viewAll");
		addMenuItem(jMenuRetrieve,	"viewAllWhos");
		addMenuItem(jMenuRetrieve,	"viewAllPars");
		addMenuItem(jMenuRetrieve,	"viewAllGens");
		addMenuItem(jMenuRetrieve,	"viewAllSpes");

		jMenuRetrieve.addSeparator();
		addMenuItem(jMenuRetrieve,	"viewSpecificCompl");

		jMenuRetrieve.addSeparator();
		JMenu findMenu=new JMenu(rbMenus.getString("findMenu"));
		findMenu.setFont(fontSSp12);
		findMenu.addMouseListener(new MouseAction());
		jMenuRetrieve.add(findMenu);
		addMenuItem(findMenu,	"findName");
		addMenuItem(findMenu,	"findRelated");
		addMenuItem(findMenu,	"findID");
		addMenuItem(findMenu,	"findFormalName");
		addMenuItem(findMenu,	"findSelected");
		addMenuItem(findMenu,	"open");
		addMenuItem(findMenu,	"findXmlFiles");
		addMenuItem(findMenu,	"findFileList");

		addMenuItem(jMenuRetrieve,	"findTxConceptTerms");

		jMenuRetrieve.addSeparator();
		addMenuItem(jMenuRetrieve,	"askAAj");
		addMenuItem(jMenuRetrieve,	"trans");

		jMenuRetrieve.addSeparator();
		addMenuItem(jMenuRetrieve,	"getKBInfo");
		addMenuItem(jMenuRetrieve,	"getKBAuthor");

		mb.add(jMenuRetrieve);
	}


	/**
	 *
	 * @modified 2008.04.08
	 * @since 2008.04.08 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	void makeMenuLango	(JMenuBar mb){
		JMenu menuLango = new JMenu(rbMenus.getString("langMenu"));
		menuLango.setFont(fontSSp14);
		menuLango.addMouseListener(new MouseAction());

		addMenuItem(menuLango,	"english");
		addMenuItem(menuLango,	"greek");
		addMenuItem(menuLango,	"esperanto");
		addMenuItem(menuLango,	"komo");

		mb.add(menuLango);
	}


	/**
	 * @modified 2000.11.12
	 * @author HoKoNoUmo
	 */
	void makeMenuHelp	(JMenuBar mb)
	{
		JMenu menuHelp = new JMenu(rbMenus.getString("menuHelp"));
		menuHelp.setFont(fontSSp14);
		menuHelp.addMouseListener(new MouseAction());

		addMenuItem(menuHelp,	"helpHtml");

		menuHelp.addSeparator();
		JMenuItem contentAAjMI = new JMenuItem(rbMenus.getString("contentAAjMI"));
		contentAAjMI.setFont(fontSSp12);
		contentAAjMI.addMouseListener(new MouseAction());
		contentAAjMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)	{
				display("AAj@hknu.it-14.xml");
			}
		});
		menuHelp.add(contentAAjMI);
		JMenuItem partsMI = new JMenuItem(rbMenus.getString("partsMI"));
		partsMI.setFont(fontSSp12);
		partsMI.addMouseListener(new MouseAction());
		partsMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)	{
				frameAllPars = new ViewAllParLevels("it-14");
			}
		});
		menuHelp.add(partsMI);
		JMenuItem functionsMI = new JMenuItem(rbMenus.getString("functionsMI"));
		functionsMI.setFont(fontSSp12);
		functionsMI.addMouseListener(new MouseAction());
		functionsMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae)	{
				frameAllSpes = new ViewAllSubLevels("it-44");
			}
		});
		menuHelp.add(functionsMI);

		mb.add(menuHelp);
	}

	//************************************************************************
	// UTIL METHODS
	//************************************************************************

	/**
	 * @modified 1999.02.27
	 * @since	1999.02.27
	 * @author HoKoNoUmo
	 */
	public Frame getFrame()
	{
		return frameAAj;
	}//end getFrame.


	/**
	 * @modified 2004.08.13
	 * @since 2004.08.13 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static void log(String message)
	{
		Util.log(message);
	}


	//************************************************************************
	// INNER CLASSES
	//************************************************************************


	/**
	 * Changes the sub-worldview of current sr-concept.<br/>
	 * All the PARTS of an xcpt belong to the same sub-worldview.
	 *
	 * @modified 2009.05.30
	 * @since 2009.05.30 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionBuildChangeSubWorldview extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionBuildChangeSubWorldview()
		{
			super("jmiBuildChangeSubWorldview");
		}
		public void actionPerformed(ActionEvent e)
		{
			//toDo
		}
	}

	/**
	 * Displays a	Window about the program
	 */
	class ActionAbout extends Dialog
		implements WindowListener,ActionListener
	{
		static final long serialVersionUID = 21L;
		ActionAbout(Frame AAj,	String title){
			super(AAj,	title, true);
			setLayout(new GridLayout(6,1));
			add(new JLabel(rbLabels.getString("About1"), JLabel.CENTER));
			add(new JLabel(strVersion,	JLabel.CENTER));
			add(new JLabel(rbLabels.getString("About3"), JLabel.CENTER));
			add(new JLabel(rbLabels.getString("About4"), JLabel.CENTER));
			add(new JLabel(rbLabels.getString("About5"), JLabel.CENTER));
			add(new JLabel(rbLabels.getString("About6"), JLabel.CENTER));
			pack();
			setLocation(300,200);
			addWindowListener(this);
		}
		public void actionPerformed(ActionEvent e){
			setVisible(true);
		}
		public void windowOpened(WindowEvent e)	{}
		public void windowClosing(WindowEvent e) {dispose();}
		public void windowClosed(WindowEvent e)	{}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
	}

	/**
	 * Add an EXISTING-XCPT as attribute to ***CURRENT-xcpt***.
	 * @modified 2000.03.24
	 * @since	1999.04.23
	 * @author HoKoNoUmo
	 */
	class ActionAddAttPart extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddAttPart()
		{
			super("addPar");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddAttPart faa = new FrameAddAttPart(xcpt_sFileName);
		}
	}

	/**
	 * Adds an EXISTING ENVIRONMENT-XCPT to CURRENT-xcpt.
	 * @modified 1999.04.23
	 * @since	1999.04.23
	 * @author HoKoNoUmo
	 */
	class ActionAddAttEnvironment extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddAttEnvironment()
		{
			super("addEnv");
		}
		public void actionPerformed(ActionEvent e)
		{
			String result = JOptionPane.showInputDialog("INSERT the ID "
																			+"(vd-number)	for this ENVIRONMENT-XCPT");
			if (result!=null)
			{
				String sFileName = Util.getXCpt_sLastFileName(result);
				Extract ex = new Extract();
				ex.extractGenerics(result);
				Vector<String> vgn = ex.vectorGenID;
				String gen=null;
				if (!vgn.isEmpty())	gen=vgn.get(0);
				else gen="no";

				Insert ins = new Insert();
				ins.insertAttEnvironmentInFile(xcpt_sFileName,
																		sFileName,								//FILENAME
																		gen,											//Generic
																		kb_sAuthor);								//Author

				//display the new file in frame-editor
				if (frameEditor!=null) frameEditor.insertFile(sFileName);
			}
		}
	}

	/**
	 * Adds an EXISTING GENERIC-sbcpt to CURRENT-xcpt.
	 * @modified 2000.03.28
	 * @since	1999.04.23
	 * @author HoKoNoUmo
	 */
	class ActionAddAttGeneric extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddAttGeneric()
		{
			super("addGen");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddAttGeneric fag = new FrameAddAttGeneric(xcpt_sFileName);
		}
	}

	/**
	 *
	 * @modified 2009.09.27
	 * @since 2009.08.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionAddNewAttribute extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddNewAttribute()
		{
			super("addNewAtr");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddNewAttribute fana = new FrameAddNewAttribute();
		}
	}


	/**
	 * Add a NEW-XCPT as NON-INHERITED-attribute to	***CURRENT-xcpt***.
	 * @modified 2000.03.24
	 * @since	1999.02.20
	 * @author HoKoNoUmo
	 */
	class ActionAddNewPart extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddNewPart()
		{
			super("addNewPar");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddNewPart fana = new FrameAddNewPart();
		}
	}

	/**
	 * IF current is an lg_object THEN it adds an ENVIRONMENT-sbcpt
	 * (a RELATION to another	'lg_object').<br/>
	 * It asks for the file-name of the other lg_object.<br/>
	 * It creates the xml-file for the new concept.<br/>
	 * It inserts the	'objects'	in the new corelaton.<br/>
	 * It sets as ENVIRONMENT-sbcpt the new-sbcpt to its 'objects'.<br/>
	 *
	 * @modified 2000.03.24
	 * @since	1999.02.22
	 * @author HoKoNoUmo
	 */
	class ActionAddNewEnvironment extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddNewEnvironment()
		{
			super("addNewEnv");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddNewEnvironment fane = new FrameAddNewEnvironment(xcpt_sFileName);
		}
	}

	/**
	 * Adds a	GENERIC to CURRENT.
	 * Creates the xml-file for this concept with the current-sbcpt as specific.
	 * @modified 2000.03.24
	 * @since	1999.02.21
	 * @author HoKoNoUmo
	 */
	class ActionAddNewGeneric extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddNewGeneric()
		{
			super("addNewGen");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddNewGeneric fana = new FrameAddNewGeneric(xcpt_sFileName);
		}
	}


	/**
	 * Adds a	new SPECIFIC-sbcpt to CURRENT-xcpt.
	 * Creates the new xml-file for the specific-sbcpt.
	 * Sets the current-sbcpt as Generic to new concept.
	 * @modified 2000.02.17
	 * @since	1999.02.22
	 * @author HoKoNoUmo
	 */
	class ActionAddNewSpecific extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddNewSpecific()
		{
			super("addNewSpe");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddNewSpecific fans = new FrameAddNewSpecific();
		}
	}

	/**
	 * Inserts a WHOLE-sbcpt in CURRENT-xcpt,
	 * Creates the new xml-file with attribute-sbcpt the current-sbcpt.
	 * @modified 2000.03.24
	 * @since	1999.02.21
	 * @author HoKoNoUmo
	 */
	class ActionAddNewWhole extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddNewWhole()
		{
			super("addNewWho");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddNewWhole fanw = new FrameAddNewWhole(xcpt_sFileName);
		}
	}


	/**
	 *
	 * @modified 2006.11.17
	 * @since 2006.11.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionAddSrWorldview extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddSrWorldview()
		{
			super("addSrWorldview");
		}
		public void actionPerformed(ActionEvent e)
		{
			//ppp
			FrameAddSrWorldview facb = new FrameAddSrWorldview();
		}
	}

	/**
	 * Adds a	new subo SrWorldview.
	 * @modified 2000.01.28
	 * @since	1999.04.10
	 * @author HoKoNoUmo
	 */
	class ActionAddSrSubWorldview extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddSrSubWorldview()
		{
			super("addSrSubWorldview");
		}
		public void actionPerformed(ActionEvent e)
		{
			//toDo
			FrameAddSrWorldview facb = new FrameAddSrWorldview();
		}
	}

	/**
	 * This function adds a SPECIFIC-DIVISION to a generic-sbcpt on one
	 * of its related-concepts.
	 * @modified 2000.04.10
	 * @since	2000.04.08
	 * @author HoKoNoUmo
	 */
	class ActionAddDivisionSpecific extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddDivisionSpecific()
		{
			super("jmiAddDivisionSpecific");
		}
		public void actionPerformed(ActionEvent e)
		{
			Insert ins = new Insert();
			ins.insertAttSpecificDivision(xcpt_sFormalID);
		}
	}

	/**
	 * It asks for the ID of the existing SPECIFIC.
	 * It inserts in current,	the existing as specific.
	 * It inserts in existing, the current as generic IF it is not.
	 * It inserts in existing, the current's pars IF they are not.
	 * It inserts in existing, the current's wholes IF they are not.
	 * @modified 2000.03.29
	 * @since	1999.04.23
	 * @author HoKoNoUmo
	 */
	class ActionAddAttSpecific extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddAttSpecific()
		{
			super("menuAddAttSpecific");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddAttSpecific fas = new FrameAddAttSpecific(xcpt_sFileName);
		}
	}

	/**
	 * It asks for the ID of the existing whole.
	 * It inserts in current,	the existing as whole.
	 * It inserts in existing, the current as att IF already there is not.
	 * It inserts in current's subs, the existing as whole IF already there is not.
	 * @modified 2000.03.27
	 * @since	1999.04.23
	 * @author HoKoNoUmo
	 */
	class ActionAddAttWhole extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddAttWhole()
		{
			super("addWho");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddAttWhole faw = new FrameAddAttWhole(xcpt_sFileName);
		}
	}

	/**
	 * @modified
	 * @since	2000.02.03
	 * @author HoKoNoUmo
	 */
	class ActionAddUnrelatedConcept extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAddUnrelatedConcept()
		{
			super	("addUnrelated");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameAddUnrelated fau = new FrameAddUnrelated();
		}
	}

	/**
	 * @modified
	 * @since	2000.03.06
	 * @author HoKoNoUmo
	 */
	class ActionAskAAj extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionAskAAj()
		{
			super("askAAj");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (frameAsk==null)
			{
				frameAsk = new RetrieveFrame_AskAAj();
			}
			else {
				frameAsk.setVisible(true);
				frameAsk.toFront();
				frameAsk.requestFocus();
			}
		}
	}

	/**
	 * @modified
	 * @since	2001.09.22
	 * @author HoKoNoUmo
	 */
	class ActionTranslation extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionTranslation()
		{
			super("trans");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (frameTrans==null)
			{
				frameTrans = new RetrieveFrame_Translation();
			}
			else {
				frameTrans.setVisible(true);
				frameTrans.toFront();
				frameTrans.requestFocus();
			}
		}
	}

	/**
	 * @modified
	 * @since	2000.05.28
	 * @author HoKoNoUmo
	 */
	class ActionBack1 extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionBack1()
		{
			super	("jmiRetrieveBack1");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (stack.size() > 1)	//if it is 1,	this is the first concept
			{
				stack.pop();
				xcpt_sFileName =  stack.pop();
				display(xcpt_sFileName);
			}
		}
	}

	/**
	 * @modified 2000.03.29
	 * @author HoKoNoUmo
	 */
	class ActionBack2 extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionBack2()
		{
			super	("jmiRetrieveBack2");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (frameHistory==null)
			{
				frameHistory = new History();
			}
			else {
				frameHistory.setVisible(true);
				frameHistory.toFront();
				frameHistory.requestFocus();
				frameHistory.setData();
			}
		}
	}

	/**
	 * Deletes the current sbcpt.	Shows its corelatons with others.
	 * @modified
	 * @since	2000.02.15
	 * @author HoKoNoUmo
	 */
	class ActionDelConcept extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionDelConcept()
		{
			super("delCrtCpt");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameDelConcept fdc = new FrameDelConcept(xcpt_sFileName);
		}
	}

	/**
	 * @modified 1999.04.30
	 * @since	1999.04.30
	 * @author HoKoNoUmo
	 */
	class ActionDelConceptUnrelated extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionDelConceptUnrelated	()
		{
			super("delUnrelated");
		}

		public void actionPerformed(ActionEvent e)
		{
//		if (frameFind==null)
//		{
//			frameFind = new SearchXml();
//		}
//		else
//		{
//			frameFind.setVisible(true);
//			frameFind.toFront();
//			frameFind.requestFocus();
//		}

		}
	}	//end of ActionDelConceptUnrelated

	/**
	 * Opens the editor with the current xmlfile.
	 */
	class ActionEditor extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionEditor ()
		{
			super("editor");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (frameEditor==null)
			{
				frameEditor = new Editor(xcpt_sFileName);
			}
			else {
				frameEditor.insertFile(xcpt_sFileName);
				frameEditor.setVisible(true);
				frameEditor.toFront();
				frameEditor.requestFocus();
			}
		}
	}

	/**
	 * Handles exit command
	 */
	class ActionExit extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionExit()
		{
			super("exit");
		}
		public void actionPerformed(ActionEvent e){
			saveVariables();
			System.exit(-1);
		}
	}

	/**
	 * @modified
	 * @since	2000.03.05
	 * @author HoKoNoUmo
	 */
	class ActionFindByFormalName extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionFindByFormalName ()
		{
			super("findFormalName");
		}
		public void actionPerformed(ActionEvent e)
		{
			String id=null;
			//we can get the ID from the textfield or the menu.
			if (e.getActionCommand().equals("jtfFindByFrTerm"))	id = jtfFindByFrTerm.getText();
			else id=JOptionPane.showInputDialog("WRITE the FORMAL-NAME you are searching for:");
			if (id!=null)
			{
				id=id.toLowerCase();
				FindByFormalName fbfn = new FindByFormalName(id);
			}
		}
	}

	/**
	 * @modified
	 * @since	2000.03.05
	 * @author HoKoNoUmo
	 */
	class ActionFindByUniqueName extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionFindByUniqueName ()
		{
			super("findID");
		}
		public void actionPerformed(ActionEvent e)
		{
			String id=null;
			//we can get the ID from the textfield or the menu.
			if (e.getActionCommand().equals("jtfFindByUniqueName"))
			{
				id = jtfFindByUniqueName.getText();
				id = Util.getXCpt_sFormalID(id);
			}
			else id=JOptionPane.showInputDialog("WRITE the UniqueName you are searching for");
			if (id!=null)	{FindByFormalID fbid = new FindByFormalID(id);}
		}
	}

	/**
	 * Search for a	sbcpt-file by id or FileName.
	 * @modified 2000.03.05
	 * @author HoKoNoUmo
	 */
	class ActionFindByFileList extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionFindByFileList()
		{
			super("findFileList");
		}
		public void actionPerformed(ActionEvent e)
		{
			String idfn = JOptionPane.showInputDialog("WRITE the ID/FORMAL-NAME you are searching for:");
			if (idfn!=null)
			{
				FindByFileList fbfl = new FindByFileList(idfn);
			}
		}
	}

	/**
	 * Searchs the name	elements in xml-files for a	name.
	 * @modified 1999.03.10
	 * @since	1999.03.10
	 * @author HoKoNoUmo
	 */
	class ActionFindByName extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionFindByName ()
		{
			super("findName");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (frameFindByName==null)
			{
				frameFindByName = new FindByName();
			}
			else {
				frameFindByName.setVisible(true);
				frameFindByName.toFront();
				frameFindByName.requestFocus();
			}
		}
	}

	/**
	 * @modified
	 * @since	2000.03.04
	 * @author HoKoNoUmo
	 */
	class ActionFindByRelated extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionFindByRelated	()
		{
			super("findRelated");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (frameFindByRelated==null)
			{
				frameFindByRelated = new FindByRelated();
			}
			else {
				frameFindByRelated.setVisible(true);
				frameFindByRelated.toFront();
				frameFindByRelated.requestFocus();
			}
		}
	}	//end of ActionFindByRelated

	/**
	 * @modified
	 * @since	2000.03.05
	 * @author HoKoNoUmo
	 */
	class ActionFindByXmlFiles extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionFindByXmlFiles()
		{
			super("findXmlFiles");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (frameFindByXmlFiles==null)
			{
				frameFindByXmlFiles=new FindByXmlFiles();
			}
			else {
				frameFindByXmlFiles.setVisible(true);
				frameFindByXmlFiles.toFront();
				frameFindByXmlFiles.requestFocus();
			}
		}
	}

	/**
	 * Finds the concept with name the selected text on the viewer's-textPane.
	 * @modified
	 * @since	2000.01.25
	 * @author HoKoNoUmo
	 */
	class ActionFindSelected extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionFindSelected ()
		{
			super("findSelected");
		}
		public void actionPerformed(ActionEvent e)
		{
			//put the selected-text on input-textfield
			String ss = viewer_sTextPane.getSelectedText();
			if (frameFindByName==null)
			{
				frameFindByName = new FindByName();
				frameFindByName.jtfInput.setText(ss);
				//select the current knowledge-base
				String vd = xcpt_sFormalID.substring(0, xcpt_sFormalID.indexOf("-"));
				frameFindByName.jlistMdb.setSelectedValue((Object)vd, true);
			}
			else {
				frameFindByName.jtfInput.setText(ss);
				//select the current knowledge-base
				String vd = xcpt_sFormalID.substring(0, xcpt_sFormalID.indexOf("-"));
				frameFindByName.jlistMdb.setSelectedValue((Object)vd, true);
				frameFindByName.setVisible(true);
				frameFindByName.toFront();
				frameFindByName.requestFocus();
			}
		}
	}

	/**
	 * Finds the TERMS of a LgConcept's name.
	 * @modified 2001.05.14
	 * @since	2001.03.16
	 * @author HoKoNoUmo
	 */
	class ActionFindTermsOfName extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionFindTermsOfName()
		{
			super("findTxConceptTerms");
		}
		public void actionPerformed(ActionEvent e)
		{
			RetrieveFrame_FindTxConceptTerms ffsf = new RetrieveFrame_FindTxConceptTerms(null);
		}
	}

	/**
	 * It generalizes a	SELECTED notInherited att.
	 *
	 * Have a	combobox to select the generic,	whose att will be the new sbcpt.
	 * It creates a	new file-xcpt.
	 * In new, it inserts the generic as whole.
	 * In new, it inserts the selected as spe.
	 * In generic, it inserts the new as att.
	 * In selected,	it inserts the new as gen IF the selected is a file-xcpt.
	 * In current, it replaces the gen.
	 * In gen's subs (except current), it inserts the new as inherited.
	 * @modified
	 * @since	2000.04.03
	 * @author HoKoNoUmo
	 */
	class ActionPartGenericization extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionPartGenericization()
		{
			super("parGeneralize");
		}
		public void actionPerformed(ActionEvent e)
		{
			Object nd = viewer_sToC.getLastSelectedPathComponent();
			if (nd!=null)
			{
				String node = nd.toString();
				if (node.indexOf("(")!=-1)
				{
					String nodeID=node.substring(node.indexOf("(")+1,node.indexOf(")"));
					Extract ex = new Extract();
					ex.extractParts(xcpt_sFileName);
					Vector<String> vattni= ex.vectorParNotInhID;//holds the NonInherited att.
					if (!vattni.contains(nodeID))
					{
						String nodeFileName = Util.getXCpt_sLastFileName(nodeID);
						JOptionPane.showMessageDialog(null,	"("	+nodeFileName	+")	is NOT a NonInherited attribute. Select one of them.");
					}
					else {
						FrameGeneralizePart fga = new FrameGeneralizePart(xcpt_sFormalID, nodeID);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,	"You must select a NonInherited attribute. One with NO generic.");
				}
			}
			else {
				JOptionPane.showMessageDialog(null,	"You must select a NonInherited attribute. One with NO generic.");
			}
		}
	}

	/**
	 * @modified 2000.03.24
	 * @since	1999.04.29
	 * @author HoKoNoUmo
	 */
	class ActionGetSrCptInfo extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionGetSrCptInfo()
		{
			super("getSrCptInfo");
		}
		public void actionPerformed(ActionEvent e)
		{
			GetSrCptInfo gci = new GetSrCptInfo(xcpt_sFileName);
		}
	}

	/**
	 * @modified
	 * @since	2000.02.02
	 * @author HoKoNoUmo
	 */
	class ActionKBGetAuthor extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionKBGetAuthor()
		{
			super("getKBAuthor");
		}
		public void actionPerformed(ActionEvent e)
		{
			JOptionPane.showMessageDialog(null,	"The current AUTHOR is \"" +kb_sAuthor +"\"");
		}
	}

	/**
	 * Displays info of XKnowledgeBase: existing SrWorldviews/SrSubWorldviews.
	 *
	 * @modified 1999.04.10
	 * @since	1999.04.08
	 * @author HoKoNoUmo
	 */
	class ActionKBGetInfo extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionKBGetInfo()
		{
			super("getKBInfo");
		}
		public void actionPerformed(ActionEvent e)
		{
			RetrieveFrame_KBInfo fkbi = new RetrieveFrame_KBInfo();
/*
			final JFrame frameTable = new JFrame("XKnowledgeBase Statistics");
			String[] colussnd_s_Names = {"#", "ShortName","Description","Directory",
															"Last-ID","Empty-IDs","File-Cpts","Internals","Total-Cpts"};
			int lastRow=trmapSrBrSubWorldview.size()+1;
			Object[][] data = new Object[lastRow][9];

			int sumFiles=0;		 	//counter for all file-xcpts.
			int sumInternals=0;	//counter for all internal-xcpts.
			int sumTotals=0;			//counter for all sbcpts.

			Iterator it = trmapSrBrSubWorldview.entrySet().iterator();
			for (int i=0; i<trmapSrBrSubWorldview.size(); i++)
			{
				Map.Entry et = (Map.Entry) it.next();
				String[] a = (String[]) et.getValue();
				String key = et.getKey();
				//lust id number.
				int lastFormalNumber = Integer.parseInt(a[2]);
				//number of empty ids.
				TreeSet<String> set = tmapEmptyID.get(key);
				int ids;
				if (set!=null) ids=set.size();
				else ids=0;
				String stids = String.valueOf(ids);
				//number of file-concepts.
				int n = lastFormalNumber-ids;
				String stn = String.valueOf(n);
				//internals
				int n2 = Integer.parseInt(a[3]);
				//total
				int n3 = n+n2;
				String stn3 = String.valueOf(n3);
				//sum
				sumFiles=sumFiles+n;
				sumInternals=sumInternals+n2;
				sumTotals=sumTotals+n3;
				data[i][0]=String.valueOf(i+1);	//number of vd
				data[i][1]=key;								 //name
				data[i][2]=a[0];								//description
				data[i][3]=a[1];								//directory
				data[i][4]=a[2];								//last-id
				data[i][5]=stids;							 //empty-ids
				data[i][6]=stn;								 //file-xcpts
				data[i][7]=a[3];								//internals
				data[i][8]=stn3;								//totals
			}
			data[lastRow-1][0]="";
			data[lastRow-1][1]="";
			data[lastRow-1][2]="";
			data[lastRow-1][3]="";
			data[lastRow-1][4]="";
			data[lastRow-1][5]="SUM";
			data[lastRow-1][6]=String.valueOf(sumFiles);
			data[lastRow-1][7]=String.valueOf(sumInternals);
			data[lastRow-1][8]=String.valueOf(sumTotals);

			JTable table = new JTable(data,	colussnd_s_Names);
//			table.setPreferredScrollableViewportSize(new Dimension(749,	196));
			//set column widths.
			TableColumn column = null;
			column = table.getColumnModel().getColumn(0);
			column.setPreferredWidth(19);
			column = table.getColumnModel().getColumn(1);
			column.setPreferredWidth(133);
			column = table.getColumnModel().getColumn(2);
			column.setPreferredWidth(200);
			column = table.getColumnModel().getColumn(3);
			column.setPreferredWidth(150);
			column = table.getColumnModel().getColumn(4);
			column.setPreferredWidth(70);
			column = table.getColumnModel().getColumn(5);
			column.setPreferredWidth(70);
			column = table.getColumnModel().getColumn(6);
			column.setPreferredWidth(70);
			column = table.getColumnModel().getColumn(7);
			column.setPreferredWidth(70);
			column = table.getColumnModel().getColumn(8);
			column.setPreferredWidth(70);
			table.setCellSelectionEnabled(false);
			table.setColumnSelectionAllowed(false);
			table.setRowSelectionAllowed(false);
			JScrollPane scrollPane = new JScrollPane(table, 20, 30);

			frameTable.getContentPane().add(scrollPane,	BorderLayout.CENTER);
			Util.setFrameIcon(frameTable);
			frameTable.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
								frameTable.dispose();
						}
				});
			frameTable.pack();
			frameTable.setLocation(0,126);
			frameTable.setVisible(true);
*/
		}
	}


	/**
	 * Display the viewer in English User-Interface language.
	 * @modified 2009.08.05
	 * @author HoKoNoUmo
	 */
	class ActionEnglish extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionEnglish()
		{
			super("english");
		}
		public void actionPerformed(ActionEvent e) {
			viewer_sTextPane.removeMouseListener(mlText);

			frameAAj.dispose();
			locale=	new Locale("en", "US");
			lng="eng";
			rbLabels = ResourceBundle.getBundle("pk_Util.BundleLabels",	locale);
			rbMenus = ResourceBundle.getBundle("pk_Util.BundleMenus",	locale);
			frameAAj = new JFrame();
			frameAAj.setFont(fontSSp12);
			frameAAj.setTitle(rbLabels.getString("titleLabel"));	/*program title*/
			frameAAj.setBackground(Color.lightGray);
			frameAAj.getContentPane().setLayout(new BorderLayout());
			frameAAj.getContentPane().add("Center", new AAj());
			Util.setFrameIcon(frameAAj);

			display(xcpt_sFileName);
			frameAAj.addWindowListener(new FrameCloser());
			frameAAj.pack();
			frameAAj.setSize(800, 600);
			frameAAj.setVisible(true);
		}
	}


	/**
	 * Displays the viewer in Greek User-Interface language.
	 * @modified 2000.11.12
	 * @since
	 * @author HoKoNoUmo
	 */
	class ActionGreek extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionGreek()
		{
			super("greek");
		}

		public void actionPerformed(ActionEvent e)
		{
			viewer_sTextPane.removeMouseListener(mlText);

			frameAAj.dispose();
			locale=	new Locale("el", "GR");
			lng="eln";
			rbLabels = ResourceBundle.getBundle("pk_Util.BundleLabels",	locale);
			rbMenus = ResourceBundle.getBundle("pk_Util.BundleMenus",	locale);
			frameAAj = new JFrame();
			frameAAj.setFont(fontSSp12);
			frameAAj.setLocale(locale);
			frameAAj.setTitle(rbLabels.getString("titleLabel")); //program title.
			frameAAj.setBackground(Color.lightGray);
			frameAAj.getContentPane().setLayout(new BorderLayout());
			frameAAj.getContentPane().add("Center", new AAj());
			Util.setFrameIcon(frameAAj);
			display(xcpt_sFileName);
			frameAAj.addWindowListener(new FrameCloser());
			frameAAj.pack();
			frameAAj.setSize(800, 600);
			frameAAj.setVisible(true);
		}
	}


	/**
	 *
	 * @modified 2008.04.19
	 * @since 2008.04.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionEsperanto extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionEsperanto()
		{
			super("esperanto");
		}

		public void actionPerformed(ActionEvent e)
		{
			viewer_sTextPane.removeMouseListener(mlText);

			frameAAj.dispose();
			locale=	new Locale("epo");
			lng="epo";
			rbLabels = ResourceBundle.getBundle("pk_Util.BundleLabels",	locale);
			rbMenus = ResourceBundle.getBundle("pk_Util.BundleMenus",	locale);
			frameAAj = new JFrame();
			frameAAj.setFont(fontSSp12);
			frameAAj.setLocale(locale);
			frameAAj.setTitle(rbLabels.getString("titleLabel")); //program title.
			frameAAj.setBackground(Color.lightGray);
			frameAAj.getContentPane().setLayout(new BorderLayout());
			frameAAj.getContentPane().add("Center", new AAj());
			Util.setFrameIcon(frameAAj);
			display(xcpt_sFileName);
			frameAAj.addWindowListener(new FrameCloser());
			frameAAj.pack();
			frameAAj.setSize(800, 600);
			frameAAj.setVisible(true);
		}
	}


	/**
	 *
	 * @modified 2006.11.14
	 * @since 2006.11.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionKomo extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionKomo()
		{
			super("komo");
		}

		public void actionPerformed(ActionEvent e)
		{
			viewer_sTextPane.removeMouseListener(mlText);

			frameAAj.dispose();
			locale=	new Locale("kml");
			lng="kml";
			rbLabels = ResourceBundle.getBundle("pk_Util.BundleLabels",	locale);
			rbMenus = ResourceBundle.getBundle("pk_Util.BundleMenus",	locale);
			frameAAj = new JFrame();
			frameAAj.setFont(fontSSp12);
			frameAAj.setLocale(locale);
			frameAAj.setTitle(rbLabels.getString("titleLabel")); //program title.
			frameAAj.setBackground(Color.lightGray);
			frameAAj.getContentPane().setLayout(new BorderLayout());
			frameAAj.getContentPane().add("Center", new AAj());
			Util.setFrameIcon(frameAAj);
			display(xcpt_sFileName);
			frameAAj.addWindowListener(new FrameCloser());
			frameAAj.pack();
			frameAAj.setSize(800, 600);
			frameAAj.setVisible(true);
		}
	}


	/**
	 * @modified 2001.03.05
	 * @since	2000.02.29
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationEnvironment extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationEnvironment()
		{
			super("envIntegration");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.environmentIntegration(xcpt_sFileName);
		}
	}

	/**
	 * @modified 2001.03.05
	 * @since	2000.02.28
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationGeneric extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationGeneric()
		{
			super("genIntegration");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.genericIntegration(xcpt_sFileName);
		}
	}	//End of class ActionIntegrationGeneric

	/**
	 * @modified 2001.03.05
	 * @since	2000.02.28
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationPart extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationPart()
		{
			super("parIntegration");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.partIntegration(xcpt_sFileName);
		}
	}

	/**
	 * SPECIFIC-INTEGRATION:
	 * IF current has as SPECIFIC xcpt-y
	 * THEN the attributes of current are attributes of sbcpt-y.
	 * [nikos, 1999.01.17]
	 *
	 * IF current has as SPECIFIC xcpt-y
	 * THEN sbcpt-y has as GENERIC current
	 * [nikos, 1999.02.22]
	 *
	 * @modified 2001.03.05
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationSpecific extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationSpecific()
		{
			super("subIntegration");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.specificIntegration(xcpt_sFileName);
		}
	}

	/**
	 * Performs ALL kinds of integration in CURRENT-xcpt.<p>
	 * IF all these integrations pass, then put the value of the 'integrated'	attribute	'yes'.
	 * @modified 1999.04.18
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationTotal extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationTotal()
		{
			super("totalIntegration");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.totalIntegration(xcpt_sFileName);
		}
	}

	/**
	 * @modified 2001.03.05
	 * @since	2000.02.28
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationWhole extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationWhole()
		{
			super("whoIntegration");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.wholeIntegration(xcpt_sFileName);
		}
	}


	/**
	 * @modified
	 * @since	2000.04.10
	 * @author HoKoNoUmo
	 */
	class ActionPartMakeInherited extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionPartMakeInherited()
		{
			super("parMakeInherited");
		}
		public void actionPerformed(ActionEvent e)
		{
			Object nd = viewer_sToC.getLastSelectedPathComponent();
			if (nd!=null)
			{
				String node = nd.toString();
				if (node.indexOf("(")!=-1)
				{
					String nodeID=node.substring(node.indexOf("(")+1,node.indexOf(")"));
					Extract ex = new Extract();
					ex.extractParts(xcpt_sFileName);
					Vector<String> vattni= ex.vectorParNotInhID;//holds the NonInherited att.
					if (!vattni.contains(nodeID))
					{
						String nodeFileName = Util.getXCpt_sLastFileName(nodeID);
						JOptionPane.showMessageDialog(null,	"("	+nodeFileName	+")	is NOT a NonInherited attribute. Select one of them.");
					}
					else {
						FrameMakeInherited fmi = new FrameMakeInherited(xcpt_sFormalID, nodeID);
					}
				}
				else {
					JOptionPane.showMessageDialog(null,	"You must select a NonInherited attribute. One with NO generic.");
				}
			}
			else {
				JOptionPane.showMessageDialog(null,	"You must select a NonInherited attribute. One with NO generic.");
			}
		}
	}

	/**
	 * Open a	concept, by its file
	 */
	class ActionOpen extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionOpen()
		{
			super("open");
		}
		public void actionPerformed(ActionEvent e)
		{
			FileDialog fileDialog = new FileDialog(frameAAj);
			fileDialog.setMode(FileDialog.LOAD);
			fileDialog.setDirectory(xcpt_sDir);
			fileDialog.setVisible(true);
			String file = fileDialog.getFile();
			if (file ==	null)	{		 return;			 }
			File f = new File(fileDialog.getDirectory(), file);
			if (f.exists())	{
				//display the new xml-file.
				xcpt_sFileName = file;
				display(xcpt_sFileName);
			}
		}
	}

	/**
	 * It overrides a	SELECTED Inherited-NotOverridden att, with a new or an existing.
	 * (an attribute with	'id' and 'generic' xml-attributes the same)
	 *
	 * It creates a	 new file-xcpt.
	 * It inserts in new,	the inherited's gen as generic.
	 * It inserts in new,	the inherited's gen pars.
	 * It inserts in new,	the inherited's gen whole.
	 * It inserts in current,	the new as inherited-overridden att.
	 * @modified
	 * @since	20000.03.30
	 * @author HoKoNoUmo
	 */
	class ActionPartOverriding extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionPartOverriding()
		{
			super("parOverride");
		}
		public void actionPerformed(ActionEvent e)
		{
			Object nd = viewer_sToC.getLastSelectedPathComponent();
			if (nd!=null)
			{
				String node = nd.toString();
				if (node.indexOf("(")!=-1)
				{
					String nodeID=node.substring(node.indexOf("(")+1,node.indexOf(")"));
					Extract ex = new Extract();
					ex.extractParts(xcpt_sFileName);
					Vector<String> vattino=	ex.vectorParInhNotOverID;	//holds the inherited-notOverridden att.
					Vector<String> vattio= ex.vectorParInhOverID;		 //holds the inherited-Overrridden att.

					if (vattino.contains(nodeID))
					{
						FrameOverridePart foa = new FrameOverridePart(xcpt_sFormalID, nodeID, "first");
					}
					else if	(vattio.contains(nodeID))
					{
						ex.extractGenerics(nodeID);
						String genID=ex.stringGen;
						FrameOverridePart foa = new FrameOverridePart(xcpt_sFormalID, genID,	"no");
					}
					else {
						String nodeFileName = Util.getXCpt_sLastFileName(nodeID);
						JOptionPane.showMessageDialog(null,	"("	+nodeFileName	+")	is NOT an Inherited attribute. Select one of them.");
					}
				}
				else {
					JOptionPane.showMessageDialog(null,	"You must select an Inherited attribute. One that its generic is the same with it.");
				}
			}
			else {
				JOptionPane.showMessageDialog(null,	"You must select an Inherited attribute. One that its generic is the same with it.");
			}
		}
	}

	/**
	 * Read again the current-sbcpt.
	 */
	class ActionRefresh extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionRefresh()
		{
			super("refresh");
		}
		public void actionPerformed(ActionEvent e)
		{
			display(xcpt_sFileName);
		}
	}

	/**
	 * I need this action to save variables before the end of the program.
	 * Otherwise,	when the programes closes abnormally,	I'm loosing the last changes in these variables.
	 * @modified
	 * @since	2000.05.24
	 * @author HoKoNoUmo
	 */
	class ActionSaveVariables extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionSaveVariables()
		{
			super("saveVariables");
		}
		public void actionPerformed(ActionEvent e)
		{
			saveVariables();
		}
	}

	/**
	 * @modified
	 * @since	2000.02.03
	 * @author HoKoNoUmo
	 */
	class ActionKBSetAuthor extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionKBSetAuthor()
		{
			super("setKBAuthor");
		}
		public void actionPerformed(ActionEvent e)
		{
			String result = JOptionPane.showInputDialog("The OLD KnoweledgeBase-Author is	\""	+kb_sAuthor	+"\""
																									+"\n  Insert the NEW Author");
			if (result!=null)
			{
				kb_sAuthor=result;
			}
		}
	}

	/**
	 * @modified 2008.03.30
	 * @since	2001.05.5
	 * @author HoKoNoUmo
	 */
	class ActionNameMgt extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionNameMgt()
		{
			super("nameMgt");
		}
		public void actionPerformed(ActionEvent e)
		{
			FrameNameManagement fnmgt = new FrameNameManagement(xcpt_sFileName);
		}
	}


	/**
	 * This action updates the terms of current-xConcept in
	 * the term.index.xml files.<br/>
	 * First asks from the user the language of terms he
	 * wants to update.
	 *
	 * @modified 2006.02.04
	 * @since	2000.02.19
	 * @author HoKoNoUmo
	 */
	class ActionLgConceptTermUpdate extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionLgConceptTermUpdate()
		{
			super("updateTermFiles");
		}
		public void actionPerformed(ActionEvent e)
		{
			Object[] lang = {rbLabels.getString("all"),
											rbLabels.getString("English2"),
											rbLabels.getString("Greek2"),
											rbLabels.getString("Esperanto"),
											rbLabels.getString("Komo")};
			Object selectedValue = JOptionPane.showInputDialog(null,
					rbLabels.getString("ChooseLangToUpdate"),				//Choose the LANGUAGE of termTxCpt_sAll you want to update
					rbLabels.getString("ChooseOne"),								//title
					JOptionPane.INFORMATION_MESSAGE,							//message type
					new ImageIcon("resources/window_icon.gif"),		//icon
					lang,																				//values to select
					lang[0]);																		//initial selection
			if (selectedValue!=null)
			{
				String lango = "";
				if	(selectedValue.equals(rbLabels.getString("English2")))
					lango="eng";
				else if	(selectedValue.equals(rbLabels.getString("Greek2")))
					lango="eln";
				else if	(selectedValue.equals(rbLabels.getString("Esperanto")))
					lango="epo";
				else if	(selectedValue.equals(rbLabels.getString("Komo")))
					lango="kml";//addLng
				else
					lango="all";
				Insert ins = new Insert();
				ins.updateTermFiles(xcpt_sFileName,	lango);
			}
		}
	}

	/**
	 * @modified
	 * @since	2000.06.09
	 * @author HoKoNoUmo
	 */
	class ActionViewAll extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionViewAll	()
		{
			super("viewAll");
		}

		public void actionPerformed(ActionEvent e)
		{
			frameAllLevels = new ViewAllLevels(xcpt_sFileName);
		}
	}

	/**
	 * @modified
	 * @since	2000.03.11
	 * @author HoKoNoUmo
	 */
	class ActionViewAllPars extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionViewAllPars	()
		{
			super("viewAllPars");
		}

		public void actionPerformed(ActionEvent e)
		{
			//maybe I	need more than one of this frame.
			frameAllPars = new ViewAllParLevels(xcpt_sFileName);
		}
	}

	/**
	 * @modified
	 * @since	2000.03.10
	 * @author HoKoNoUmo
	 */
	class ActionViewAllGens extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionViewAllGens	()
		{
			super("viewAllGens");
		}

		public void actionPerformed(ActionEvent e)
		{
			//maybe I	need more than one of this frame.
			frameAllGens = new ViewAllGenLevels(xcpt_sFileName);
		}
	}

	/**
	 * Creates a treeStructure with ALL the specifics of current xConcept.
	 * @modified 1999.05.08
	 * @since	1999.04.30
	 * @author HoKoNoUmo
	 */
	class ActionViewAllSpes extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionViewAllSpes	()
		{
			super("viewAllSpes");
		}

		public void actionPerformed(ActionEvent e)
		{
			//maybe I	need more than one of this frame.
			frameAllSpes = new ViewAllSubLevels(xcpt_sFileName);
		}
	}

	/**
	 * @modified
	 * @since	2000.03.11
	 * @author HoKoNoUmo
	 */
	class ActionViewAllWhos extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionViewAllWhos	()
		{
			super("viewAllWhos");
		}

		public void actionPerformed(ActionEvent e)
		{
			//maybe I	need more than one of this frame.
			frameAllWhos = new ViewAllWhoLevels(xcpt_sFileName);
		}
	}

	/**
	 * @modified
	 * @since	2000.06.10
	 * @author HoKoNoUmo
	 */
	class ActionViewSpecificComplement extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionViewSpecificComplement	()
		{
			super("viewSpecificCompl");
		}
		public void actionPerformed(ActionEvent e)
		{
			frameSpecificCompl = new ViewSpecificComplement(xcpt_sFileName);
		}
	}


	/**
	 *
	 * @modified 2006.11.17
	 * @since 2006.11.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionLgTerminalKomoMgt extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionLgTerminalKomoMgt()
		{
			super("tmrKmlMgt");
		}
		public void actionPerformed(ActionEvent e)
		{
			Frame_TxTerminalKomo_Mgt ftkm = new Frame_TxTerminalKomo_Mgt();
		}
	}


	/**
	 * Opens the HtmlMgr, to display the help-system in html-format.
	 *
	 * @modified 2010.06.19
	 * @since 2010.06.19 (v00.02.02)
	 * @author HoKoNoUmo
	 */
	class ActionHelpHtml extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionHelpHtml() {
			super("helpHtml");
		}
		public void actionPerformed(ActionEvent e)
		{
			HtmlMgr hm = new HtmlMgr();
		}
	}


	/**
	 * Handles windowclosing command
	 */
	static class FrameCloser extends WindowAdapter
	{
		static final long serialVersionUID = 21L;
		public void windowClosing(WindowEvent e) {
			saveVariables();
			System.exit(0);
		}
	}

	/**
	 * @modified 2000.06.04
	 * @since	2000.06.03
	 * @author HoKoNoUmo
	 */
	class MouseAction extends MouseAdapter
	{
		static final long serialVersionUID = 21L;
		public void mouseEntered(MouseEvent e)
		{
			String obj = e.getSource().toString();
			String ssnd_s_Name = obj.substring(obj.lastIndexOf("=")+1,	obj.length()-1);

			//SYSTEM-MENU
			if (ssnd_s_Name.equals(rbMenus.getString("systemMenu")))
				statusBar.setText(rbMenus.getString("systemHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("exitMI")))
				statusBar.setText(rbMenus.getString("exitHelp"));

			//BUILD-MENU
			else if	(ssnd_s_Name.equals(rbMenus.getString("menuBuild")))
				statusBar.setText(rbMenus.getString("buildHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("editorMI")))
				statusBar.setText(rbMenus.getString("editorHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("menuIntegrate")))
				statusBar.setText(rbMenus.getString("integrateHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("totalIntegrationMI")))
				statusBar.setText(rbMenus.getString("totalIntegrationHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("parIntegrationMI")))
				statusBar.setText(rbMenus.getString("parIntegrationHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("whoIntegrationMI")))
				statusBar.setText(rbMenus.getString("whoIntegrationHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("genIntegrationMI")))
				statusBar.setText(rbMenus.getString("genIntegrationHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("subIntegrationMI")))
				statusBar.setText(rbMenus.getString("subIntegrationHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("jmiBuildChangeSubWorldviewMI")))
				statusBar.setText(rbMenus.getString("jmiBuildChangeSubWorldviewHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("addCptMenu")))
				statusBar.setText(rbMenus.getString("addCptHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addNewMenu")))
				statusBar.setText(rbMenus.getString("addNewHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addNewParMI")))
				statusBar.setText(rbMenus.getString("addNewParHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addNewWhoMI")))
				statusBar.setText(rbMenus.getString("addNewWhoHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addNewGenMI")))
				statusBar.setText(rbMenus.getString("addNewGenHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addNewSpeMI")))
				statusBar.setText(rbMenus.getString("addNewSpeHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addNewEnvMI")))
				statusBar.setText(rbMenus.getString("addNewEnvHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addNewAtrMI")))
				statusBar.setText(rbMenus.getString("addNewAtrHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addExistingMenu")))
				statusBar.setText(rbMenus.getString("addExistingHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addParMI")))
				statusBar.setText(rbMenus.getString("addParHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addWhoMI")))
				statusBar.setText(rbMenus.getString("addWhoHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addGenMI")))
				statusBar.setText(rbMenus.getString("addGenHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("menuAddAttSpecificMI")))
				statusBar.setText(rbMenus.getString("menuAddAttSpecificHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addEnvMI")))
				statusBar.setText(rbMenus.getString("addEnvHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addUnrelatedMI")))
				statusBar.setText(rbMenus.getString("addUnrelatedHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("delCptMenu")))
				statusBar.setText(rbMenus.getString("delCptHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("delCrtCptMI")))
				statusBar.setText(rbMenus.getString("delCrtCptHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("delRelatedMenu")))
				statusBar.setText(rbMenus.getString("delRelatedHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("delParMI")))
				statusBar.setText(rbMenus.getString("delParHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("delWhoMI")))
				statusBar.setText(rbMenus.getString("delWhoHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("delGenMI")))
				statusBar.setText(rbMenus.getString("delGenHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("delSubMI")))
				statusBar.setText(rbMenus.getString("delSubHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("delEnvMI")))
				statusBar.setText(rbMenus.getString("delEnvHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("delUnrelatedMI")))
				statusBar.setText(rbMenus.getString("delUnrelatedHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("parOverrideMI")))
				statusBar.setText(rbMenus.getString("parOverrideHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("parGeneralizeMI")))
				statusBar.setText(rbMenus.getString("parGeneralizeHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("parMakeInheritedMI")))
				statusBar.setText(rbMenus.getString("parMakeInheritedHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("jmiAddDivisionSpecificMI")))
				statusBar.setText(rbMenus.getString("jmiAddDivisionSpecificHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("nameMgtMI")))
				statusBar.setText(rbMenus.getString("nameMgtHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("updateTermFilesMI")))
				statusBar.setText(rbMenus.getString("updateTermFilesHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("tmrKmlMgtMI")))
				statusBar.setText(rbMenus.getString("tmrKmlMgtHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("addSrWorldviewMI")))
				statusBar.setText(rbMenus.getString("addSrWorldviewHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("addSrSubWorldviewMI")))
				statusBar.setText(rbMenus.getString("addSrSubWorldviewHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("setKBAuthorMI")))
				statusBar.setText(rbMenus.getString("setKBAuthorHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("saveVariablesMI")))
				statusBar.setText(rbMenus.getString("saveVariablesHelp"));

			//RETRIEVE-MENU
			else if	(ssnd_s_Name.equals(rbMenus.getString("menuRetrieve")))
				statusBar.setText(rbMenus.getString("retrieveHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("jmiRetrieveBack1MI")))
				statusBar.setText(rbMenus.getString("jmiRetrieveBack1Help"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("jmiRetrieveBack2MI")))
				statusBar.setText(rbMenus.getString("jmiRetrieveBack2Help"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("refreshMI")))
				statusBar.setText(rbMenus.getString("refreshHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("getSrCptInfoMI")))
				statusBar.setText(rbMenus.getString("getSrCptInfoHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("viewAllMI")))
				statusBar.setText(rbMenus.getString("viewAllHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("viewAllSpesMI")))
				statusBar.setText(rbMenus.getString("viewAllSpesHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("viewAllGensMI")))
				statusBar.setText(rbMenus.getString("viewAllGensHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("viewAllParsMI")))
				statusBar.setText(rbMenus.getString("viewAllParsHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("viewAllWhosMI")))
				statusBar.setText(rbMenus.getString("viewAllWhosHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("viewSpecificComplMI")))
				statusBar.setText(rbMenus.getString("viewSpecificComplHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("findMenu")))
				statusBar.setText(rbMenus.getString("findHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("findNameMI")))
				statusBar.setText(rbMenus.getString("findNameHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("findRelatedMI")))
				statusBar.setText(rbMenus.getString("findRelatedHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("findIDMI")))
				statusBar.setText(rbMenus.getString("findIDHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("findFormalNameMI")))
				statusBar.setText(rbMenus.getString("findFormalNameHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("openMI")))
				statusBar.setText(rbMenus.getString("openHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("findSelectedMI")))
				statusBar.setText(rbMenus.getString("findSelectedHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("findXmlFilesMI")))
				statusBar.setText(rbMenus.getString("findXmlFilesHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("findFileListMI")))
				statusBar.setText(rbMenus.getString("findFileListHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("findTxConceptTermsMI")))
				statusBar.setText(rbMenus.getString("findTxConceptTermsHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("askAAjMI")))
				statusBar.setText(rbMenus.getString("askAAjHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("transMI")))
				statusBar.setText(rbMenus.getString("transHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("getKBInfoMI")))
				statusBar.setText(rbMenus.getString("getKBInfoHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("getKBAuthorMI")))
				statusBar.setText(rbMenus.getString("getKBAuthorHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("langMenu")))
				statusBar.setText(rbMenus.getString("langHelp"));

			//HELP-MENU
			else if	(ssnd_s_Name.equals(rbMenus.getString("menuHelp")))
				statusBar.setText(rbMenus.getString("helpHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("contentAAjMI")))
				statusBar.setText(rbMenus.getString("contentAAjHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("partsMI")))
				statusBar.setText(rbMenus.getString("partsHelp"));
			else if	(ssnd_s_Name.equals(rbMenus.getString("functionsMI")))
				statusBar.setText(rbMenus.getString("functionsHelp"));

			else if	(ssnd_s_Name.equals(rbMenus.getString("aboutMI")))
				statusBar.setText(rbMenus.getString("aboutHelp"));

			else
				statusBar.setText(strVersion);
		}
		public void mouseExited(MouseEvent e)
		{
			statusBar.setText(strVersion);
		}
	}

	/**
	 * By double-clicking on a 'name'	comes the window that searches for this item.
	 * @modified
	 * @since	2000.01.23
	 * @author HoKoNoUmo
	 */
	class MouseListenerText extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			if (e.getClickCount()	== 2)
			{
				//put the selected-text on input-textfield
				String ss = viewer_sTextPane.getSelectedText();
				if (frameFindByName==null)
				{
					frameFindByName = new FindByName();
					frameFindByName.jtfInput.setText(ss);
					//select the current knowledge-base
					String vd = xcpt_sFormalID.substring(0, xcpt_sFormalID.indexOf("-"));
					frameFindByName.jlistMdb.setSelectedValue((Object)vd, true);
				}
				else {
					frameFindByName.jtfInput.setText(ss);
					//select the current knowledge-base
					String vd = xcpt_sFormalID.substring(0, xcpt_sFormalID.indexOf("-"));
					frameFindByName.jlistMdb.setSelectedValue((Object)vd, true);
					frameFindByName.setVisible(true);
					frameFindByName.toFront();
					frameFindByName.requestFocus();
				}
			}
		}
	}

	/**
	 * It handles mouse events on the TREE
	 * @modified 2009.10.25
	 * @author HoKoNoUmo
	 */
	static class MouseListenerTree extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			int selRow = viewer_sToC.getRowForLocation(e.getX(),	e.getY());
			TreePath selPath = viewer_sToC.getPathForLocation(e.getX(), e.getY());
			String lastNode = null;
			String nodeID = null;

			if(selRow!=-1	&& selPath!=null)	{
				lastNode=selPath.getLastPathComponent().toString();
				int pos=0;

				if (lastNode.indexOf("@")>1)
				{
					nodeID=lastNode.substring(lastNode.indexOf("@")+1, lastNode.lastIndexOf("@"));

					if(e.getClickCount() ==	1)
					{
						if (hmapCaret.containsKey(nodeID))
						{
							String stPos = hmapCaret.get(nodeID);
							pos = Integer.parseInt(stPos);
							viewer_sTextPane.setCaretPosition(pos);
						}
					}

					else if(e.getClickCount()	== 2)
					{
						display(nodeID);
					}
				}

				else {
					String stPos = hmapCaret.get(lastNode);
					if (stPos!=null){
						pos = Integer.parseInt(stPos);
						viewer_sTextPane.setCaretPosition(pos);//+22);//wrkarnd
					}
				}
			}
		}
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	class StatusBar extends Container
	{
		static final long serialVersionUID = 21L;
		public StatusBar() {
			super();
			setLayout(new BoxLayout(this,	BoxLayout.X_AXIS));
		}
		public void paint(Graphics g)	{
			super.paint(g);
		}
	}

}