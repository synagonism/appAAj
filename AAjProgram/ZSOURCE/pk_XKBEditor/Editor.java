/**
 * @modified 2000.06.11
 * @since 1999.02.08
 * Editor.java - The EDITOR. Edits the AAj-knowledge-bases.
 * Copyright (C) 1999-2001 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * TO DO:
 * I'll try to put here the 'EDITING FUNCTIONS'.	This way I'll keep the viewer (AAj)
 * small and then as an applet anybody can read the AAj-mms.
 * [1999.04.21]
 */
package pk_XKBEditor;

import pk_XKBManager.FindByName;
import pk_XKBManager.AAj;
import pk_Util.*;

//import org.gjt.sp.jedit.syntax.*;
//import org.gjt.sp.jedit.textarea.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;

import java.net.URL;

import java.text.Collator;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.UIManager;

import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import javax.swing.text.TextAction;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class Editor extends JFrame
{
	static final long serialVersionUID = 21L;

	/** Variables related with the xcpt we display. */
	public String										sbcpt;
	public String										xcpt_sFormalID;
	public String										xcpt_sDir; //the full directory.
	public String										xcpt_sHostFile;
	/** in order to build a new concept.
	 * We must save this info, and get it back every time we start
	 the program. */
	public String										author;
	public static Map								trmapSrBrSubWorldview; //brainual-worldview info.
	public static Map<String,TreeSet<String>> tmapEmptyID; //empty (ids without a concept) id-numbers.

	/** the text-component */
	public JTextArea							textArea = new JTextArea();

	public	JSplitPane							splitPane;
	private Hashtable<String,Action>	commands;
	private JMenuBar							menubar;
	private JToolBar							toolbar;

	/** I made statusBar static to have access from all other concepts */
	public static StatusBar			statusBar = new StatusBar();
	public Stack<String>					stack =	 new Stack<String>(); // for history of concepts.
	protected FileDialog					fileDialog;
	public			 int								confirm; //for confirm dialogs

	/** Listener for the edits on the current document.	 */
	protected UndoableEditListener	undoHandler = new UndoHandler();
	/** UndoManager that we add edits to. */
	protected UndoManager						undo = new UndoManager();

	/** Suffix applied to the key used in resource file lookups for an image.	 */
	public static final String			imageSuffix = "Image";
	/** Suffix applied to the key used in resource file lookups for a label.	 */
	public static final String			menuitemSuffix = "MI";
	/** Suffix applied to the key used in resource file lookups for an action.	*/
	public static final String			actionSuffix = "Action";
	/** Suffix applied to the key used in resource file lookups for tooltip text.		*/
	public static final String			tipSuffix = "Tooltip";

	private UndoAction							undoAction = new UndoAction();
	private RedoAction							redoAction = new RedoAction();

	/** Actions defined by the Editor class	 */
	private Action[]								defaultActions =
	{
		//file menu
		new ActionOpen(),
//	new NewAction(),
		new SaveAction(),
		new PreviousAction(),
		new ActionRefresh(),
		new ViewerAction(),
		new ActionExit(),

		//find menu
		new FindAction(),
		new Find2Action(),
		new ViewSubsTreeAction(),

		//edit menu
		redoAction,
		undoAction,
		new WrapAction(),

		//integration menu
		new ActionIntegrationTotal(),
		new PartAction(),
		new ActionIntegrationWhole(),
		new IntegrationGenericAction(),
		new ActionIntegrationSpecific(),
		new ActionIntegrationEnvironment(),

		//configure menu

		//help menu
	};

	private static ResourceBundle		resources;
	static
	{
			try {
				resources = ResourceBundle.getBundle("pk_XKBEditor/resources.Editor", Locale.US);
			}
			catch (MissingResourceException mre)
			{
				System.err.println("pk_XKBEditor/resources/Editor.properties not found");
				System.exit(1);
			}
	}

	// INTEGRATION VARIABLES
	/** Contains the attributes of the current concept */
	public Vector										vectorPar;
	public Vector										vectorParID;
	/** Contains the whole-concepts of the current concept */
	public Vector										vectorWho;
	/** Contains the generic-concepts of the current concept. */
	public Vector										vectorGen;
	public Vector										vectorGenID;
	/** Contains the specific-concepts of the current concept. */
// public Vector										vectorSpe;
	/** Contains ALL the spe-sbcpts in all dimensions only onces. */
	public Vector										vectorSubAll;
	/** Contains the dimensions of the current concept. */
// public Vector										vectorDim;
	/** Contains the environment-sbcpts of the current concept. */
	public Vector										vectorEnv;

	/** I need it to sort strings to sort concepts */
	Collator collator = Collator.getInstance();
	Font fontssp14 = new Font("sansserif", Font.PLAIN, 14);
	Font fontssp16 = new Font("sansserif", Font.PLAIN, 16);
	Font fontssp18 = new Font("sansserif", Font.PLAIN, 18);


	/**
	 * @modified 2000.02.21
	 * @author HoKoNoUmo
	 */
	public Editor(String xmlFile)
	{
		setBackground(Color.lightGray);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Util.getImage("resources/AAj_icon.gif"));
		addWindowListener(new ActionExit());

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception exc) {
			System.err.println("Editor: Error loading L&F: " + exc);
		}

		// install the command table
		commands = new Hashtable<String,Action>();
		Action[] actions = TextAction.augmentList(textArea.getActions(), defaultActions);
		for (int i = 0; i < actions.length; i++) {
//			Action a = defaultActions[i]; //only the one I have;
			Action a = actions[i];
			commands.put((String)a.getValue(Action.NAME), a);
		}

		// put textArea in a scroller
		JScrollPane scroller = new JScrollPane();
		JViewport port = scroller.getViewport();
		port.add(textArea);

		//create the menus.
		menubar = new JMenuBar();
		menubar.setFont(fontssp18);
		makeFileMenu(menubar);
		makeEditMenu(menubar);
		makeInsertMenu(menubar);
		makeDeleteMenu(menubar);
		makeIntegrate(menubar);
		makeSearchMenu(menubar);
		makeConfigureMenu(menubar);
		makeHelpMenu(menubar);

		// create a panel to hold the textarea.
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add("North",createToolbar());
		panel.add("Center", scroller);

		// **** INSERT content into the splitpane components ****
		insertFile(xmlFile);

		// put components on frame
		getContentPane().add("North", menubar);
		getContentPane().add("Center", panel);
		getContentPane().add("South", statusBar);

		setSize(new Dimension(699,559));
		setLocation(100,0);
		setVisible(true);
	}

	/**
	 * Creates a new treePane and inserts it into the Left component of
	 * splitpane.
	 * Also inserts the file into the doc of the right textarea.
	 *
	 * I'm using this method to DISPLAY NEW-FILES in editor.
	 * @modified 1999.03.31
	 * @author HoKoNoUmo
	 */
	public void insertFile(String file)
	{
		//I suppose that 'file' is the last file-name for this concept.
		//I need this in renaming concepts where I have 2 file names with the same id.
		//Because I'm using this method to display new files, I must check for
		//the last sbcpt-file-name before the use of this method. [1999.04.25]

		// DO first initializations.
		Frame frame = getFrame();
		frame.setTitle(resources.getString("Title") + "  (" +file +")");
		sbcpt = file;
		xcpt_sFormalID = file.substring(file.indexOf("@")+1, file.length()-4);
		xcpt_sDir = Util.getXCpt_sFullDir(file);
		stack.push(file); // for back action
		author = AAj.kb_sAuthor;
		trmapSrBrSubWorldview = AAj.trmapSrBrSubWorldview;
		tmapEmptyID = AAj.tmapEmptyID;

		if (file.indexOf("#")!=-1)//in case of internal-xcpt.
		{
			xcpt_sHostFile=Util.getSrCptInt_sHostFlName(file);
			// Inserts file into JTextArea's doc.
			insertFileToJTextArea(xcpt_sHostFile);
		}
		else {
			// Inserts file into JTextArea's doc.
			insertFileToJTextArea(file);
		}
	}

	/**
	 * Inserts a file into editor(JTextArea) doc.
	 * @modified 1999.03.23
	 * @author HoKoNoUmo
	 */
	public void insertFileToJTextArea(String fileName)
	{
		textArea.setFont(new Font("serif", Font.BOLD, 14));
//		textArea.setDocument(new SyntaxDocument());
		textArea.setDocument(new PlainDocument());
		textArea.setTabSize(4);
		textArea.setEditable(true);
//		textArea.setLineWrap(true);
//		textArea.setWrapStyleWord(true);

		// put content on jtextarea document
		File file = new File(xcpt_sDir, fileName);
		try {
			Reader in = new FileReader(file);
			char[] buff = new char[4096];
			int nch;
			while ((nch = in.read(buff, 0, buff.length)) != -1)
			{
				textArea.getDocument().insertString(textArea.getDocument().getLength(),
																						new String(buff, 0, nch),
																						null);
			}
		} catch (FileNotFoundException ef) {
				System.err.println("Editor: fnfe");
		} catch (IOException eio) {
				System.err.println("Editor: ioe");
		} catch (BadLocationException ebl) {
				System.err.println("Editor: ble");
		}

		textArea.setCaretPosition(textArea.getDocument().getStartPosition().getOffset());
		//Colorizing ta;
//		String clazz = "org.gjt.sp.jedit.syntax.XMLTokenMarker";
//		try
//		{
//			Class cls;
//			ClassLoader loader = getClass().getClassLoader();
//			if (loader == null)
//				cls = Class.forName(clazz);
//			else
//				cls = loader.loadClass(clazz);
//			textArea.setTokenMarker((TokenMarker) cls.newInstance());
//		} catch(Exception e) { }
//		textArea.repaint();
//
//		org.gjt.sp.jedit.textarea.TextAreaPainter painter = textArea.getPainter();
//		painter.setLineHighlightEnabled(true);
//		painter.setEOLMarkersPainted(true);
//		painter.setBracketHighlightColor(Color.red);
//		painter.setFont(new Font("serif", Font.PLAIN, 16));

		// Add this as a listener for undoable edits.
		textArea.getDocument().addUndoableEditListener(undoHandler);
	}

	/**
	 * Fetch the textArea contained in this panel
	 */
	protected JTextArea getEditor()
	{
		return textArea;
	}

	/**
	 * Find the hosting frame, for the file-chooser dialog.
	 */
	protected Frame getFrame()
	{
		Frame frame = this;
		return frame;
	}

	/**
	 * @modified 1999.05.13
	 * @since 1999.05.08
	 * @author HoKoNoUmo
	 */
	void makeFileMenu (JMenuBar mb)
	{
		JMenu fileMenu = new JMenu(resources.getString("fileMenu"));
		fileMenu.addMouseListener(new MouseCommand());
		fileMenu.setFont(fontssp14);

		addMenuItem(fileMenu, "open");
		addMenuItem(fileMenu, "save");
		fileMenu.addSeparator();
		addMenuItem(fileMenu, "previous");
		addMenuItem(fileMenu, "refresh");
		addMenuItem(fileMenu, "viewer");
		fileMenu.addSeparator();
		addMenuItem(fileMenu, "exit");

		mb.add(fileMenu);
	}

	/**
	 * @modified 1999.05.13
	 * @since 1999.05.08
	 * @author HoKoNoUmo
	 */
	void makeSearchMenu(JMenuBar mb)
	{
		JMenu findMenu = new JMenu(resources.getString("findMenu"));
		findMenu.addMouseListener(new MouseCommand());
		findMenu.setFont(fontssp14);

		addMenuItem(findMenu, "find");	//searches the names/termTxCpt_sAll.
		addMenuItem(findMenu, "find2"); //searches the ids/formal-names.
		addMenuItem(findMenu, "viewSubsTree");

		mb.add(findMenu);
	}

	/**
	 * @modified 1999.05.08
	 * @since 1999.05.08
	 * @author HoKoNoUmo
	 */
	void makeEditMenu(JMenuBar mb)
	{
		JMenu editMenu = new JMenu(resources.getString("editMenu"));
		editMenu.addMouseListener(new MouseCommand());
		editMenu.setFont(fontssp14);

		addMenuItem(editMenu, "cut");
		addMenuItem(editMenu, "copy");
		addMenuItem(editMenu, "paste");
		editMenu.addSeparator();
		addMenuItem(editMenu, "undo");
		addMenuItem(editMenu, "redo");

		editMenu.addSeparator();
		JCheckBoxMenuItem cbmi = new JCheckBoxMenuItem(getResourceString("wrap" +menuitemSuffix));
		cbmi.setFont(fontssp14);
		URL url = getResource("wrap" + imageSuffix);
		if (url != null)
		{
			cbmi.setHorizontalTextPosition(JButton.RIGHT);
			cbmi.setIcon(new ImageIcon(url));
		}
		String astr = getResourceString("wrap" + actionSuffix);
		if (astr == null)
		{
			astr = "wrap";
		}
		cbmi.setActionCommand(astr);
		Action a = commands.get(astr);
		if (a != null)
		{
			cbmi.addActionListener(a);
			cbmi.addMouseListener(new MouseCommand());
			a.addPropertyChangeListener(createActionChangeListener(cbmi));
			cbmi.setEnabled(a.isEnabled());
		}
		else {
			cbmi.setEnabled(false);
		}
		cbmi.setSelected(false);
		editMenu.add(cbmi);

		mb.add(editMenu);
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	void makeInsertMenu(JMenuBar mb)
	{
		JMenu insertMenu = new JMenu(resources.getString("insertMenu"));
		insertMenu.addMouseListener(new MouseCommand());
		insertMenu.setFont(fontssp14);

		mb.add(insertMenu);
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	void makeDeleteMenu(JMenuBar mb)
	{
		JMenu deleteMenu = new JMenu(resources.getString("deleteMenu"));
		deleteMenu.addMouseListener(new MouseCommand());
		deleteMenu.setFont(fontssp14);

		mb.add(deleteMenu);
	}

	/**
	 * @modified 1999.05.08
	 * @since 1999.05.08
	 * @author HoKoNoUmo
	 */
	void makeIntegrate(JMenuBar mb)
	{
		JMenu integrationMenu = new JMenu(resources.getString("integrationMenu"));
		integrationMenu.addMouseListener(new MouseCommand());
		integrationMenu.setFont(fontssp14);

		addMenuItem(integrationMenu, "total");
		integrationMenu.addSeparator();
		addMenuItem(integrationMenu, "part");
		addMenuItem(integrationMenu, "whole");
		addMenuItem(integrationMenu, "generic");
		addMenuItem(integrationMenu, "specific");
		addMenuItem(integrationMenu, "environment");

		mb.add(integrationMenu);
	}

	/**
	 * @modified 1999.05.08
	 * @since 1999.05.08
	 * @author HoKoNoUmo
	 */
	void makeConfigureMenu(JMenuBar mb)
	{
		JMenu configureMenu = new JMenu(resources.getString("configureMenu"));
		configureMenu.addMouseListener(new MouseCommand());
		configureMenu.setFont(fontssp14);

		addMenuItem(configureMenu, "getAuthor");
		addMenuItem(configureMenu, "getKBInfo");
		addMenuItem(configureMenu, "getSrCptStat");
		configureMenu.addSeparator();
		addMenuItem(configureMenu, "addSrSubWorldview");
		addMenuItem(configureMenu, "setAuthor");
		configureMenu.addSeparator();

		JMenu langMenu = new JMenu(resources.getString("langMenu"));
		langMenu.setFont(fontssp14);
		configureMenu.add(langMenu);
		addMenuItem(langMenu, "english");
		addMenuItem(langMenu, "greek");

		mb.add(configureMenu);
	}

	/**
	 * @modified 2000.02.27
	 * @since 1999.05.08
	 * @author HoKoNoUmo
	 */
	void makeHelpMenu(JMenuBar mb)
	{
		JMenu menuHelp = new JMenu(resources.getString("menuHelp"));
		menuHelp.addMouseListener(new MouseCommand());
		menuHelp.setFont(fontssp14);

		JMenuItem contentsMI = new JMenuItem(resources.getString("contentsMI"));
		contentsMI.setFont(fontssp14);
		contentsMI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				AAj.display("it-48#1");
				AAj.frameAAj.setVisible(true);
				AAj.frameAAj.toFront();
				AAj.frameAAj.requestFocus();
			}
		});
		menuHelp.add(contentsMI);

		mb.add(menuHelp);
	}

	/**
	 * @modified 1999.05.08
	 * @since 1999.05.08
	 * @author HoKoNoUmo
	 */
	void addMenuItem(JMenu menu, String cmd)
	{
		JMenuItem mi = new JMenuItem(getResourceString(cmd + menuitemSuffix));
		mi.setFont(fontssp14);
		URL url = getResource(cmd + imageSuffix);
		if (url != null)
		{
			mi.setHorizontalTextPosition(JButton.RIGHT);
			mi.setIcon(new ImageIcon(url));
		}
		//cut/copy/paste have different action-names.
		String astr = getResourceString(cmd + actionSuffix);
		if (astr == null)
		{
			astr = cmd;
		}
		mi.setActionCommand(astr);
		Action a = commands.get(astr);
		if (a != null)
		{
			mi.addActionListener(a);
			mi.addMouseListener(new MouseCommand());
			a.addPropertyChangeListener(createActionChangeListener(mi));
			mi.setEnabled(a.isEnabled());
		}
		else {
			mi.setEnabled(false);
		}
		menu.add(mi);
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	protected String getResourceString(String nm)
	{
		String str;
		try {
			str = resources.getString(nm);
		} catch (MissingResourceException mre) {
			str = null;
//			System.err.println("Editor.getResourceString: mre " +nm);
		}
		return str;
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	protected URL getResource(String key)
	{
		String name = getResourceString(key);
		if (name != null) {
			URL url = this.getClass().getResource(name);
			return url;
		}
		return null;
	}

	/**
	 * Create the toolbar.	By default this reads the
	 * resource file for the definition of the toolbar.
	 */
	private Component createToolbar()
	{
		toolbar = new JToolBar();
		String[] toolKeys = tokenize(getResourceString("toolbar"));
		for (int i = 0; i < toolKeys.length; i++) {
			if (toolKeys[i].equals("-")) {
				toolbar.add(Box.createHorizontalStrut(5));
			} else {
			toolbar.add(createTool(toolKeys[i]));
			}
		}
		toolbar.add(Box.createHorizontalGlue());
		return toolbar;
	}

	/**
	 * Hook through which every toolbar item is created.
	 */
	protected Component createTool(String key)
	{
		return createToolbarButton(key);
	}

	/**
	 * Create a button to go inside of the toolbar.	 By default this
	 * will load an image resource.	 The image filename is relative to
	 * the classpath (including the '.' directory if its a part of the
	 * classpath), and may either be in a JAR file or a separate file.
	 *
	 * @param key The key in the resource file to serve as the basis
	 *	of lookups.
	 */
	protected JButton createToolbarButton(String key)
	{
		URL url = getResource(key + imageSuffix);
		JButton b = new JButton(new ImageIcon(url)) {
			static final long serialVersionUID = 21L;
			public float getAlignmentY() { return 0.5f; }
		};
		b.setRequestFocusEnabled(false);
		b.setMargin(new Insets(1,1,1,1));

		String astr = getResourceString(key + actionSuffix);
		if (astr == null) {
			astr = key;
		}
		Action a = commands.get(astr);
		if (a != null) {
			b.setActionCommand(astr);
			b.addActionListener(a);
		} else {
			b.setEnabled(false);
		}
		String tip = getResourceString(key + tipSuffix);
		if (tip != null) {
			b.setToolTipText(tip);
		}
		return b;
	}

	/**
	 * Take the given string and chop it up into a series
	 * of strings on whitespace boundries.	This is useful
	 * for trying to get an array of strings out of the
	 * resource file.
	 */
	protected String[] tokenize(String input)
	{
		Vector<String> v = new Vector<String>();
		StringTokenizer t = new StringTokenizer(input);
		String cmd[];

		while (t.hasMoreTokens())
			v.addElement(t.nextToken());
		cmd = new String[v.size()];
		for (int i = 0; i < cmd.length; i++)
			cmd[i] = v.elementAt(i);

		return cmd;
	}

	/**
	 * @modified
	 * @since
	 */
	protected PropertyChangeListener createActionChangeListener(JMenuItem b)
	{
		return new ActionChangedListener(b);
	}

	//************************************************************************
	// UTILITY METHODS
	//************************************************************************

	/**
	 * Set the AUTHOR who uses the system.
	 */
	public void setAuthor(String name)
	{
		author=name;
		AAj.kb_sAuthor=name;
	}

	/**
	 * Get the AUTHOR who uses the system.
	 */
	public String getAuthor()
	{
		String name=author;
		return name;
	}

	//************************************************************************
	//INNER CLASSES ALPHABETICALLY
	//************************************************************************

	/**
	 * @modified
	 * @since
	 *
	 */
	private class ActionChangedListener implements PropertyChangeListener
	{
		JMenuItem menuItem;

		ActionChangedListener(JMenuItem mi) {
					super();
					this.menuItem = mi;
		}

		public void propertyChange(PropertyChangeEvent e) {
					String propertyName = e.getPropertyName();
					if (e.getPropertyName().equals(Action.NAME)) {
							String text = (String)e.getNewValue();
							menuItem.setText(text);
					} else if (propertyName.equals("enabled")) {
							Boolean enabledState = (Boolean) e.getNewValue();
							menuItem.setEnabled(enabledState.booleanValue());
					}
		}
	}

	/**
	 * IF current is a corelaton (Relation@hknu.symb-1)
	 * THEN the objects of its environment must have the current in its environment.
	 * [1999.02.28]
	 *
	 * ENVIRONMENT-INTEGRATION:
	 * IF sbcpt-x has as ENVIRONMENT-sbcpt sbcpt-y with 'lg_object' sbcpt-z.
	 * THEN sbcpt-z has as environment, sbcpt-y.
	 * AND sbcpt-x and sbcpt-z are environment-sbcpts of sbcpt-y.
	 * [1999.02.22]
	 *
	 * @modified 1999.04.18
	 * @since 1999.02.23
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationEnvironment extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationEnvironment()
		{
			super("environment");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.environmentIntegration(sbcpt);
		}
	}

	/**
	 * @modified 2000.02.19
	 * @since
	 * @author HoKoNoUmo
	 */
	class ActionExit	extends AbstractAction implements	 WindowListener
	{
		static final long serialVersionUID = 21L;
		//toDo: create a modal-dialog that ask to update or not termTxCpt_sAll.
		ActionExit() {
			super("exit");
		}

		public void actionPerformed(ActionEvent ae)
		{
			Object[] options = {"Discard-File",
													"Save-File Only",
													"Save-File & Update-Synterms",
													"Update-Synterms Only"};
			Object selectedValue = JOptionPane.showInputDialog(null,
							"Choose what you want to do",							//message
							"Choose One",															//title
							JOptionPane.INFORMATION_MESSAGE,					//message type
							null,																			//icon
							options,																	//values to select
							options[0]);															//initial selection
			if (selectedValue.equals("Discard-File"))
			{
				dispose();
			}
			else if (selectedValue.equals("Save-File Only"))
			{
				SaveAction sa = new SaveAction();
				sa.actionPerformed(ae);
				dispose();
			}
			else if (selectedValue.equals("Save-File & Update-Synterms"))
			{
			SaveAction sa = new SaveAction();
			sa.actionPerformed(ae);
			Insert ins = new Insert();
			ins.updateTermFiles(sbcpt, "all");
					dispose();
			}
			else if (selectedValue.equals("Update-Synterms Only"))
			{
			Insert ins = new Insert();
			ins.updateTermFiles(sbcpt, "all");
					dispose();
			}
		}

		public void windowOpened(WindowEvent e) {}
		public void windowClosing(WindowEvent e)
		{
			Object[] options = {"Discard-File",
													"Save-File Only",
													"Save-File & Update-Synterms",
													"Update-Synterms Only"};
			Object selectedValue = JOptionPane.showInputDialog(null,
							"Choose what you want to do",							//message
							"Choose One",															//title
							JOptionPane.INFORMATION_MESSAGE,					//message type
							null,																			//icon
							options,																	//values to select
							options[0]);															//initial selection
			if (selectedValue!=null)
			{
				if (selectedValue.equals("Discard-File"))
				{
					dispose();
				}
				else if (selectedValue.equals("Save-File Only"))
				{
					SaveAction sa = new SaveAction();
					sa.windowClosing(e);
					dispose();
				}
				else if (selectedValue.equals("Save-File & Update-Synterms"))
				{
					SaveAction sa = new SaveAction();
					sa.windowClosing(e);
					Insert ins = new Insert();
					ins.updateTermFiles(sbcpt, "all");
					dispose();
				}
				else if (selectedValue.equals("Update-Synterms Only"))
				{
					Insert ins = new Insert();
					ins.updateTermFiles(sbcpt, "all");
					dispose();
				}
			}
		}
		public void windowClosed(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}

	}

	/**
	 * Searchs the &lt;name&gt; elements in xml-files for a name.
	 * @modified 1999.03.10
	 * @since 1999.03.10
	 * @author HoKoNoUmo
	 */
	class FindAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		FindAction ()
		{
			super("find");
		}
		public void actionPerformed(ActionEvent e)
		{
			FindByName frameFind = new FindByName();
		}
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	class Find2Action extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		Find2Action ()
		{
			super("find2");
		}
		public void actionPerformed(ActionEvent e)
		{
			String strCpt = JOptionPane.showInputDialog("WRITE the ID or the FORMAL-NAME you are searching for");
			File xcpt_sDir=null;
			FilterID flt=null;
			String[] fileName=new String[2];
			boolean found=false;

			if (strCpt.indexOf("-")>1)
			{
				//we are searching for an ID or name.
				String mm2 = strCpt.substring(0, strCpt.indexOf("-"));
				String vd = mm2.toLowerCase();

				if (trmapSrBrSubWorldview.containsKey(vd)) //strCpt is an id
				{
					String[] ar = (String[])trmapSrBrSubWorldview.get(vd);
					String directory = System.getProperty("user.dir")
														+File.separator
														+ar[1];
					xcpt_sDir = new File (directory);
					flt = new FilterID(strCpt);
					try {
						fileName = xcpt_sDir.list(flt);
						if (fileName[0] != null)
						{
							found=true;
							sbcpt= fileName[0];
							AAj.display(sbcpt);
						}
					}
					catch (Exception elist)
					{
						JOptionPane.showMessageDialog(null, "Editor: There is NOT file for this ID");
					}
				}
				else //name with -.
				{
					//we are searching for a name.
					FilterName flt2 = new FilterName(strCpt+"@");//to search ONLY for whole names
					try {
						//search first 'hknu.symb'
						xcpt_sDir = new File(Util.getXCpt_sFullDir("hknu.meta-1"));
						fileName = xcpt_sDir.list(flt2);
						if (fileName[0] != null)
						{
							found=true;
							sbcpt= fileName[0];
							AAj.display(sbcpt);
						}
					}
					catch (Exception e3)
					{
						try {
							xcpt_sDir = new File(Util.getXCpt_sFullDir("it-1"));
							fileName = xcpt_sDir.list(flt2);
							if (fileName[0] != null)
							{
								found=true;
								sbcpt= fileName[0];
								AAj.display(sbcpt);
							}
						}
						catch (Exception e4)
						{
							try {
								xcpt_sDir = new File(Util.getXCpt_sFullDir("itsoft-1"));
								fileName = xcpt_sDir.list(flt2);
								if (fileName[0] != null)
								{
									found=true;
									sbcpt= fileName[0];
									AAj.display(sbcpt);
								}
							}
							catch (Exception e5)
							{
								try {
									xcpt_sDir = new File(Util.getXCpt_sFullDir("society-1"));
									fileName = xcpt_sDir.list(flt2);
									if (fileName[0] != null)
									{
										found=true;
										sbcpt= fileName[0];
										AAj.display(sbcpt);
									}
								}
								catch (Exception e6)
								{
									//search all kn and break
									for (Iterator i=trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
									{
										String key = (String)i.next();
										if (!key.equals("hknu.symb") ||!key.equals("it")
													||!key.equals("itsoft") ||!key.equals("society"))
										{
											try {
												xcpt_sDir = new File(Util.getXCpt_sFullDir(key+"-1"));
												fileName = xcpt_sDir.list(flt2);
												if (fileName[0] != null)
												{
													found=true;
													sbcpt= fileName[0];
													AAj.display(sbcpt);
													break;
												}
											}
											catch (Exception e7) {}
										}
									}
								}
							}
						}
					}
					if (!found) JOptionPane.showMessageDialog(null, "There is NOT file for this concept");
				}
			}
			else //name without -.
			{
				//we are searching for a name.
				FilterName flt2 = new FilterName(strCpt+"@");//to search ONLY for whole names
				try {
					//search first 'hknu.symb'
					xcpt_sDir = new File(Util.getXCpt_sFullDir("hknu.meta-1"));
					fileName = xcpt_sDir.list(flt2);
					if (fileName[0] != null)
					{
						found=true;
						sbcpt= fileName[0];
						AAj.display(sbcpt);
					}
				}
				catch (Exception e3)
				{
					try {
						xcpt_sDir = new File(Util.getXCpt_sFullDir("it-1"));
						fileName = xcpt_sDir.list(flt2);
						if (fileName[0] != null)
						{
							found=true;
							sbcpt= fileName[0];
							AAj.display(sbcpt);
						}
					}
					catch (Exception e4)
					{
						try {
							xcpt_sDir = new File(Util.getXCpt_sFullDir("itsoft-1"));
							fileName = xcpt_sDir.list(flt2);
							if (fileName[0] != null)
							{
								found=true;
								sbcpt= fileName[0];
								AAj.display(sbcpt);
							}
						}
						catch (Exception e5)
						{
							try {
								xcpt_sDir = new File(Util.getXCpt_sFullDir("society-1"));
								fileName = xcpt_sDir.list(flt2);
								if (fileName[0] != null)
								{
									found=true;
									sbcpt= fileName[0];
									AAj.display(sbcpt);
								}
							}
							catch (Exception e6)
							{
								//search all kn and break
								for (Iterator i=trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
								{
									String key = (String)i.next();
									if (!key.equals("hknu.symb") ||!key.equals("it")
												||!key.equals("itsoft") ||!key.equals("society"))
									{
										try {
											xcpt_sDir = new File(Util.getXCpt_sFullDir(key+"-1"));
											fileName = xcpt_sDir.list(flt2);
											if (fileName[0] != null)
											{
												found=true;
												sbcpt= fileName[0];
												AAj.display(sbcpt);
												break;
											}
										}
										catch (Exception e7) {}
									}
								}
							}
						}
					}
				}
				if (!found) JOptionPane.showMessageDialog(null, "There is NOT file for this concept");
			}

		}
	}

	/**
	 * DO GENERIC-INTEGRATION:
	 * IF current has as GENERIC sbcpt-y
	 * THEN the PARTS of sbcpt-y are attributes of current.
	 * [nikos, 1999.01.17]
	 *
	 * IF current has as GENERIC sbcpt-y
	 * THEN sbcpt-y has as SPECIFIC current.
	 * [1999.02.28]
	 * @modified 2000.02.27
	 * @author HoKoNoUmo
	 */
	class IntegrationGenericAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		IntegrationGenericAction()
		{
			super("generic");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.genericIntegration(sbcpt);
		}
	} //End of class IntegrationGenericAction

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	public class MouseCommand extends MouseAdapter
	{
		public void mouseEntered(MouseEvent e)
		{
			String obj = e.getSource().toString();
			String ssnd_s_Name = obj.substring(obj.lastIndexOf("=")+1, obj.length()-1);
			if (ssnd_s_Name.equals("File"))
				statusBar.setText(" ");
			else if (ssnd_s_Name.equals("New"))
				statusBar.setText("Create a new 'empty' concept.");
			else if (ssnd_s_Name.equals("Open"))
				statusBar.setText("");
			else if (ssnd_s_Name.equals("Save"))
				statusBar.setText(" ");
			else if (ssnd_s_Name.equals("Exit"))
				statusBar.setText(" ");

			else if (ssnd_s_Name.equals("Edit"))
				statusBar.setText(" ");
			else if (ssnd_s_Name.equals("Refresh"))
				statusBar.setText("Redisplays the Current-sbcpt");
			else if (ssnd_s_Name.equals("Previous"))
				statusBar.setText("Displays the PREVIOUS concept");
			else if (ssnd_s_Name.equals("Cut"))
				statusBar.setText(" ");

			else if (ssnd_s_Name.equals("Integration"))
				statusBar.setText("Do a consistent whole the CURRENT-xcpt with the knowledge-base.");
			else if (ssnd_s_Name.equals("Total Integration"))
				statusBar.setText("Do ALL the types of integrations.");
			else if (ssnd_s_Name.equals("Part Integration"))
				statusBar.setText("Check if the PARTS of current-sbcpt have as WHOLE the current-sbcpt");
			else if (ssnd_s_Name.equals("Whole Integration"))
				statusBar.setText("Check if the WHOLE cpts have as ATTRIBUTE the current-sbcpt.");
			else if (ssnd_s_Name.equals("Generic Integration"))
				statusBar.setText("Check if all the pars of a GENERIC-sbcpt are pars of the current-sbcpt.");
			else if (ssnd_s_Name.equals("Specific Integration"))
				statusBar.setText("Check if the SPECIFIC have all the pars of the current-sbcpt");
			else
				statusBar.setText(" ");
		}
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	class NewAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		NewAction() {
			super("new");
		}
		NewAction(String nm) {
			super(nm);
		}

		public void actionPerformed(ActionEvent e)
		{
			String fName= "empty@hknu.symb-0.xml";
			insertFile(fName);
		}
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	class ActionOpen extends NewAction
	{
		static final long serialVersionUID = 21L;
		ActionOpen()
		{
			super("open");
		}
		public void actionPerformed(ActionEvent e)
		{
			Frame frame = getFrame();
			if (fileDialog == null) {
				fileDialog = new FileDialog(frame);
			}
			fileDialog.setMode(FileDialog.LOAD);
			fileDialog.setDirectory(xcpt_sDir);
			fileDialog.setVisible(true);

			String file = fileDialog.getFile();
			if (file == null) {
				return;
			}
			File f = new File(fileDialog.getDirectory(), file);

			// Put the file into splitpane components.
			if (f.exists())
			{
				insertFile(file);
				if (!AAj.xcpt_sFileName.equals(sbcpt)) AAj.display(sbcpt);
			}
		}
	}

	/**
	 * Performs *** PART INTEGRATION *** of the current xConcept.
	 * IF sbcpt-x has as ATTRIBUTE sbcpt-y
	 * THEN sbcpt-y has as WHOLE sbcpt-x.
	 * @modified 1999.04.15
	 * @author HoKoNoUmo
	 */
	class PartAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		PartAction()
		{
			super("part");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.partIntegration(sbcpt);
		}
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	class PreviousAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		PreviousAction() {
			super("previous");
		}

		public void actionPerformed(ActionEvent e)
		{
			if (stack.size() > 1 ) { //if it is 1, this is the first concept.
				stack.pop();
				sbcpt = stack.pop();

				insertFile(sbcpt);
				if (!AAj.xcpt_sFileName.equals(sbcpt)) AAj.display(sbcpt);
			}
		}
	}

	/**
	 * @modified
	 * @since
	 *
	 */
	class RedoAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public RedoAction() {
			super("redo");
			setEnabled(false);
		}
		public void actionPerformed(ActionEvent e) {
		try {
			undo.redo();
		} catch (CannotRedoException ex) {
			System.out.println("Unable to redo: " + ex);
			ex.printStackTrace();
		}
		update();
		undoAction.update();
		}

		protected void update() {
			if(undo.canRedo()) {
				setEnabled(true);
				putValue(Action.NAME, undo.getRedoPresentationName());
			}
			else {
				setEnabled(false);
				putValue(Action.NAME, "redo");
			}
		}
	}

	/**
	 * @modified
	 * @since
	 * @author HoKoNoUmo
	 */
	class ActionRefresh extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionRefresh() {
			super("refresh");
		}
		public void actionPerformed(ActionEvent e)
		{
			insertFile(sbcpt);
			stack.pop(); /* because after parsing we have 2 times the same concept */
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
	 * @modified 2000.02.28
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationSpecific extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationSpecific()
		{
			super("specific");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.specificIntegration(sbcpt);
		}
	}

	/**
	 * Performs ALL kinds of integration in CURRENT-xcpt.
	 * IF all these integrations pass, then put the value of the 'integrated' attribute 'yes'.
	 * @modified 1999.04.18
	 * @author HoKoNoUmo
	 */
	public class ActionIntegrationTotal extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationTotal()
		{
			super("total");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.totalIntegration(sbcpt);
		}
	}

	/**
	 * @modified 2000.03.01
	 * @since
	 * @author HoKoNoUmo
	 */
	class SaveAction	extends NewAction implements WindowListener
	{
		static final long serialVersionUID = 21L;
		SaveAction() {
			super("save");
		}

		public void actionPerformed(ActionEvent e) {save();}
		public void windowOpened(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {save();}
		public void windowClosed(WindowEvent e) {}
		public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}
		public void windowActivated(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}

		public void save()
		{
			Frame frame = getFrame();
			if (fileDialog == null) {
				fileDialog = new FileDialog(frame);
			}
			fileDialog.setMode(FileDialog.SAVE);
			fileDialog.setDirectory(xcpt_sDir);
			if (sbcpt != null)
			if (sbcpt.indexOf("#")==-1) fileDialog.setFile(sbcpt);
			else fileDialog.setFile(xcpt_sHostFile);//because sbcpt is an internal.
			fileDialog.setVisible(true);

			String file = fileDialog.getFile();	 //asks for a file
			if (file == null) {			return;				}
			File f = new File(xcpt_sDir, file);

			try {
			FileWriter saveWriter = new FileWriter(f);
			Document doc = getEditor().getDocument();
							String text = doc.getText(
												doc.getStartPosition().getOffset(),
												doc.getLength());
			saveWriter.write(text);
			saveWriter.close();
			}
			catch (BadLocationException ble) {System.err.println("Editor: BadLocationException");}
			catch (IOException io) { System.err.println("Editor: IOException");}
		}
	}

	/**
	 * Puts a new value for the variable author
	 */
	class SetAuthorAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		SetAuthorAction()
		{
			super("setAuthor");
		}
		public void actionPerformed(ActionEvent e)
		{
			String result;
			result = JOptionPane.showInputDialog(null,"The value of the AUTHOR variable is \""
																	+author +"\".	 Please ENTER a new value.");
			if (result!=null)
			{
				author = result;
			}
		}
	}

	/**
	 * @modified
	 * @since
	 *
	 */
	class UndoAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public UndoAction() {
			super("undo");
			setEnabled(false);
		}
		public void actionPerformed(ActionEvent e) {
			try {
				undo.undo();
			} catch (CannotUndoException ex) {
				System.out.println("Editor: Unable to undo");
				ex.printStackTrace();
			}
				update();
				redoAction.update();
		}

		protected void update() {
		if(undo.canUndo()) {
			setEnabled(true);
			putValue(Action.NAME, undo.getUndoPresentationName());
		}
		else {
			setEnabled(false);
			putValue(Action.NAME, "undo");
		}
		}
	}

	/**
	 * @modified
	 * @since
	 *
	 */
	class UndoHandler implements UndoableEditListener
	{
		/**
		* Messaged when the Document has created an edit, the edit is
		* added to <code>undo</code>, an instance of UndoManager.
		*/
		public void undoableEditHappened(UndoableEditEvent e) {
			undo.addEdit(e.getEdit());
			undoAction.update();
			redoAction.update();
		}
	}

	/**
	 * @modified
	 * @since 2000.02.25 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ViewerAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ViewerAction()
		{
			super("viewer");
		}
		public void actionPerformed(ActionEvent e)
		{
//			AAj.display(sbcpt); //viewer & editor always display same file.
			AAj.frameAAj.setVisible(true);
			AAj.frameAAj.toFront();
			AAj.frameAAj.requestFocus();
		}
	}

	/**
	 * Performs WHOLE integration of the current sbcpt.
	 * IF current has as WHOLE sbcpt-y
	 * THEN sbcpt-y has as ATTRIBUTE current.
	 * @modified 2000.02.26
	 * @author HoKoNoUmo
	 */
	class ActionIntegrationWhole extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ActionIntegrationWhole()
		{
			super("whole");
		}
		public void actionPerformed(ActionEvent e)
		{
			Integration ingr = new Integration();
			ingr.wholeIntegration(sbcpt);
		}
	}

	/**
	 * @modified
	 * @since 2000jan
	 * @author HoKoNoUmo
	 */
	class WrapAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		WrapAction ()
		{
			super("wrap");
		}
		public void actionPerformed(ActionEvent e)
		{
			JCheckBoxMenuItem cbox = (JCheckBoxMenuItem)e.getSource();
			if(cbox.isSelected())
			{
				textArea.setLineWrap(true);
				textArea.setWrapStyleWord(true);
			}
			else {
				textArea.setLineWrap(false);
			}
		}
	}

	/**
	 * @modified 1999.05.13
	 * @since 1999.05.13
	 * @author HoKoNoUmo
	 */
	class ViewSubsTreeAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		ViewSubsTreeAction ()
		{
			super("viewSubsTree");
		}

		public void actionPerformed(ActionEvent e)
		{
			ViewAllSubLevels frameSubsTree = new ViewAllSubLevels(sbcpt);
		}
	}

}
