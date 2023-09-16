package pk_XKBManager;

import pk_Util.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.net.MalformedURLException;

import java.util.Iterator;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * This class searches for a &lt;name&gt;/&lt;synonym&gt; of a sc.
 * Searches the lng/term.index.xml files.
 *
 * @modified 2000.03.11
 * @since 2000.03.02 (v00.02.00)
 * @author HoKoNoUmo
 */
public class FindByName extends JFrame
{
	static final long serialVersionUID = 21L;
	JButton jbtnSearch, jbtnClear, jbtnAbort; // GUI buttons (toDo: add help button)

	JTextField				jtfInput;						// TextField used to enter search text in
	JTextField				jtfStatus;					// TextField used to display search status

	JList							jlistMdb;						// Holds the Knowledge Bases.
	JList							jlistResults;				// List to display matches in.
	DefaultListModel	dlistModel;
	JRadioButton			jrButtonCaseSens;
	JRadioButton			jrButtonCaseInsens;

	JCheckBox					jchboxNames;
	JCheckBox					jchboxSyns;
	JCheckBox					jchboxMatchWord;
	JCheckBox					jchboxMatchName;

	Color		colorBg=Color.lightGray;			// Background color.
	Color		colorFg=Color.black;					// Foreground color.

	FindByName objFindByName=null;				//I need this lg_object in FindNameThread class.
	FindByNameThread threadFByN = null;		// Search thread.
	boolean abortThread = false;

	boolean matchCase = false;		 // Flag to indicate if we need to match case.
	boolean matchWord = false;		 // Flag to indicate if we need to match whole word.
	boolean matchName = false;		 // Flag to indicate if we need to match whole name.
	boolean searchName = true;			// Flag to search names or not.
	boolean searchSyn = true;			 // Flag to search termTxCpt_sAll or not.

	Vector<String> filesVisited=new Vector<String>();			// Files that have been visited
	Vector<String> cptsMatch=new Vector<String>();				// XConcepts that contain the search-word (internal or not).
	int		hitsFound = 0;									// Number of occurrences of search string

	Object[] xcpt_sDir = null;								// Holds the directories to search.

	/**
	 * The constructor.
	 * @modified
	 * @since 2000.03.02
	 * @author HoKoNoUmo
	 */
	public FindByName()
	{
		super("What is X? (or Find a Concept by TERM)");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {dispose();}
		});
		objFindByName=this;

		JPanel pNorth = new JPanel(new BorderLayout());
		pNorth.setBorder(BorderFactory.createEtchedBorder());

		Object[] vd = AAj.trmapSrBrSubWorldview.keySet().toArray();
		Object[] mms = new Object[vd.length+1];
		mms[0]="ALL";
		System.arraycopy(vd, 0, mms, 1, vd.length);
		jlistMdb = new JList(mms);
		jlistMdb.setFixedCellHeight(15);
		jlistMdb.setSelectedIndex(0);
		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(new Dimension(109, 109));
		JViewport vp2 = sp.getViewport();
		vp2.add(jlistMdb);
		JPanel pboxL = new JPanel(new BorderLayout());
		pboxL.setBorder(new TitledBorder(LineBorder.createBlackLineBorder()," SrSubWorldview ",TitledBorder.CENTER,TitledBorder.BOTTOM));
		pboxL.add("Center", sp);

		//box that contains the search fields.
		Box boxR = new Box(BoxLayout.Y_AXIS);
		JPanel pbox1 = new JPanel();
		jchboxNames = new JCheckBox ("names");
		jchboxNames.setSelected (true);
		pbox1.add(jchboxNames);
		jchboxSyns = new JCheckBox ("termTxCpt_sAll");
		jchboxSyns.setSelected (true);
		pbox1.add(jchboxSyns);
		JPanel pbox3 = new JPanel();
		jbtnSearch = new JButton (" search ");
		jbtnSearch.setFont (new Font ("sansserif", Font.BOLD, 14));
		pbox3.add (jbtnSearch);
		jbtnClear = new JButton (" clear ");
		jbtnClear.setFont (new Font ("sansserif", Font.BOLD, 14));
		pbox3.add (jbtnClear);
		jbtnAbort = new JButton (" stop ");
		jbtnAbort.setFont (new Font ("sansserif", Font.BOLD, 14));
		jbtnAbort.setEnabled(false);
		pbox3.add (jbtnAbort);
		JButton jbHelp = new JButton(" help ");
		jbHelp.setFont (new Font ("sansserif", Font.BOLD, 14));
		pbox3.add (jbHelp);
		jbHelp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				AAj.display("it-49#4");
				AAj.frameAAj.toFront();
				AAj.frameAAj.requestFocus();
			}
		});
		pbox3.setForeground (colorFg);
		pbox3.setBackground (colorBg);
		JPanel pbox4 = new JPanel();
		JLabel lab = new JLabel ("SEARCH for: ");
		lab.setFont (new Font ("sansserif", Font.PLAIN, 12));
		lab.setForeground(Color.black);
		pbox4.add (lab);
		jtfInput = new JTextField ("",26);
		jtfInput.setFont (new Font ("sansserif", Font.BOLD, 12));
		jtfInput.setBackground (Color.white);
		pbox4.add (jtfInput);
		boxR.add(pbox4);
		boxR.add(pbox3);
		boxR.add(pbox1);

		pNorth.add("West", pboxL);
		pNorth.add("Center", boxR);

		JPanel pCenter = new JPanel(new BorderLayout());
		dlistModel = new DefaultListModel();
		jlistResults = new JList (dlistModel);
		JScrollPane scrollPane = new JScrollPane(jlistResults);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		pCenter.add("Center", scrollPane);

		JPanel pSouth = new JPanel();
		pSouth.setBorder(BorderFactory.createEtchedBorder());
		// This textfield shows the status of the search to provide
		// some feedback to the user. The page count is displayed.
		jtfStatus = new JTextField ("",14);
		jtfStatus.setEditable (false);
		jtfStatus.setBackground (Color.lightGray);
		pSouth.add (jtfStatus);
		JLabel lab2 = new JLabel ("case: ");
		lab2.setFont (new Font ("sansserif", Font.PLAIN, 12));
		lab2.setForeground(Color.black);
		pSouth.add(lab2);
		ButtonGroup caseSense = new ButtonGroup();
		jrButtonCaseInsens = new JRadioButton ("in-sensitive");
		jrButtonCaseInsens.setActionCommand("first");
		pSouth.add (jrButtonCaseInsens);
		jrButtonCaseSens = new JRadioButton ("sensitive" );
		jrButtonCaseSens.setActionCommand("second");
		pSouth.add (jrButtonCaseSens);
		caseSense.add (jrButtonCaseInsens);
		caseSense.add (jrButtonCaseSens);
		jrButtonCaseInsens.setSelected (true);
		jchboxMatchWord = new JCheckBox ("whole word");
		pSouth.add (jchboxMatchWord);
		jchboxMatchName = new JCheckBox ("whole name");
		pSouth.add (jchboxMatchName);
		pSouth.setForeground (colorFg);
		pSouth.setBackground (colorBg);

		// and get the GUI components onto our content pane
		getContentPane().add(pNorth, BorderLayout.NORTH);
		getContentPane().add(pCenter, BorderLayout.CENTER);
		getContentPane().add(pSouth, BorderLayout.SOUTH);

		jtfInput.addActionListener(new SearchCommand());
		jbtnSearch.addActionListener(new SearchCommand());

		jbtnClear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				// Clear button pressed - clear all the fields and return
				jtfInput.setText("");
				jtfStatus.setText("");

				// Clear buttons
				jchboxNames.setSelected (true);
				jchboxSyns.setSelected (true);
				jrButtonCaseSens.setSelected(false);
				jrButtonCaseInsens.setSelected(true);
				jchboxMatchWord.setSelected(false);
				jchboxMatchName.setSelected(false);

				if (dlistModel.size() > 0)
					dlistModel.clear();
				threadFByN = null;
			}
		});

		jbtnAbort.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				// Abort button pressed - stop the thread
				if (threadFByN != null) abortThread=true;
				threadFByN = null;
				// Enable buttons for another search
				enableButtons();
				jbtnAbort.setEnabled(false);
			}
		});

		MouseListener mouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				if (me.getClickCount() == 1)
				{
					JList list = (JList)me.getSource();
					int index = list.locationToIndex(me.getPoint());
					list.setSelectedIndex(index);
					String selection = (String)list.getSelectedValue();
					if (selection!=null)
					{
						String cptSel = selection.substring(selection.indexOf("(")+1, selection.indexOf(")"));
						AAj.display(cptSel);
						AAj.frameAAj.setVisible(true);
						AAj.frameAAj.toFront();
						AAj.frameAAj.requestFocus();
					}
				}
			}
		};
		jlistResults.addMouseListener(mouseListener);

		jrButtonCaseInsens.addActionListener(new CaseCommand());
		jrButtonCaseSens.addActionListener(new CaseCommand());

		jchboxMatchWord.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				if (jchboxMatchWord.isSelected() == true)
					matchWord = true;
				else
					matchWord = false;
			}
		});

		jchboxMatchName.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				if (jchboxMatchName.isSelected() == true)
					matchName = true;
				else
					matchName = false;
			}
		});

		jchboxNames.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				if (jchboxNames.isSelected() == true)
					searchName = true;
				else
					searchName = false;
			}
		});

		jchboxSyns.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				if (jchboxSyns.isSelected() == true)
					searchSyn = true;
				else
					searchSyn = false;
			}
		});

		setIconImage( Util.getImage("resources/AAj_icon.gif") );
		setSize(new Dimension(576,374));
		setLocation(182,109);
		setVisible(true);
	} //end FindByName

	/**
	 * enable use of buttons in GUI
	 */
	public void enableButtons ()
	{
		jbtnSearch.setEnabled(true);
		jbtnClear.setEnabled(true);
	}

	/**
	 * Disable use of buttons in GUI
	 */
	final void disableButtons ()
	{
		jbtnSearch.setEnabled(false);
		jbtnClear.setEnabled(false);
	}

	/**
	 * returns the number of XmlFiles that the search thread has visited
	 */
	public int getTotalFiles ()
	{
		return filesVisited.size();
	}

	/**
	 * Adds the concept we found to list.
	 * @param termTxCpt_sAll ALL the termTxCpt_sAll of a concept that match.
	 */
	public void addToList (String xcpt_sFileName, String termTxCpt_sAll, String xcpt_sName)
	{
		String xcpt_sFormalID = Util.getXCpt_sFormalID(xcpt_sFileName);
		if (!termTxCpt_sAll.equals(""))
		{
			dlistModel.addElement("CONCEPT: " +xcpt_sName +" (" +xcpt_sFormalID	 +"),				"
													+"TERMS: " +termTxCpt_sAll);
		}
		else {
			dlistModel.addElement("CONCEPT: " +xcpt_sName +" (" +xcpt_sFormalID	 +")");
		}
		cptsMatch.addElement(xcpt_sFileName);
		hitsFound++;
	}

	//************************************************************************
	/**
	 * Handles case radiobuttons
	 */
	public class CaseCommand extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("first"))
				matchCase = false;
			else
				matchCase = true;
		}
	}

	//************************************************************************
	/**
	 * Handles search
	 */
	public class SearchCommand extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		SearchCommand()
		{
			super("search");
		}
		public void actionPerformed(ActionEvent e)
		{
			// Search button pressed - read in search text entered
			String text = jtfInput.getText();
//we can remove '-' from text to avoid some situations that some put them and others not.
			text=text.replace('-', ' ');

			// Make sure ther's somthing to search for
			if (text.length() != 0)
			{
				// New search so clear the GUI out
				if (!dlistModel.isEmpty())	dlistModel.clear();
				hitsFound = 0;
				disableButtons ();
				jbtnAbort.setEnabled(true);
				jtfStatus.setText("");
				// Clear out previous search data
				filesVisited.removeAllElements();
				cptsMatch.removeAllElements();

				// We're off - start the search thread
				xcpt_sDir = jlistMdb.getSelectedValues();
				abortThread=false; //we can make a search.
				threadFByN = new FindByNameThread(objFindByName, text);
				threadFByN.start();
			}
		}
	}

}

//************************************************************************
//************************************************************************
/**
 * This thread performs the search.
 * It searches the lng/term.index.xml files.
 * @modified 2000.03.03
 * @since 2000.03.02
 * @author HoKoNoUmo
 */
class FindByNameThread extends Thread
{
	FindByName	appl;													//we need this to use its variables.
	String			startCptFile=null;						// The name of file of the start concept.
	String			textToFind=null;							// String to find
	Vector			filesToSearch=new Vector();
	boolean			matched=false;								//flag if we found a hit on a concept.

	/**
	 * Constructor
	 */
	FindByNameThread (FindByName application, String text)
	{
		appl=application;
		textToFind = text;
	}

	/**
	 * @modified
	 * @author HoKoNoUmo
	 */
	public void run()
	{
		if (appl.xcpt_sDir[0].equals("ALL"))
		{
			//it is better to search the hknu.symb/it vd and then the others.
			//here we can put priorities.
			for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
			{
				String vd =(String) i.next(); //key = the vd.
				if (appl.abortThread) break;
				String kbFileName = vd +"'termTxCpt_sAll.xml";
				String mmDir = Util.getXCpt_sFullDir(vd +"-1");
				String kbFullFileName=mmDir +File.separator +kbFileName;
				File fl = new File(kbFullFileName);
				if (fl.exists())
				{
					searchFile(kbFullFileName);
					appl.filesVisited.addElement(kbFileName);
				}
			}
		}

		else //some mms are selected.
		{
			for (int i=0; i<appl.xcpt_sDir.length; i++)
			{
				String vd = (String)appl.xcpt_sDir[i];
				if (appl.abortThread) break;
				String kbFileName = vd +"'termTxCpt_sAll.xml";
				String mmDir = Util.getXCpt_sFullDir(vd +"-1");
				String kbFullFileName=mmDir +File.separator +kbFileName;
				File fl = new File(kbFullFileName);
				if (fl.exists())
				{
					searchFile(kbFullFileName);
					appl.filesVisited.addElement(kbFileName);
				}
			}
		}
		appl.enableButtons(); //after searching, enable search/clear buttons.
		appl.jbtnAbort.setEnabled(false);
		appl.jtfStatus.setText("Matches=" +appl.hitsFound +", Searched=" +appl.getTotalFiles());
	}

	/**
	 * Read ONE lng/term.index.xml file and searches the &lt;name&gt; xml elements.
	 * @param kbFileName is a fullpath file.
	 * @modified 1999.03.29
	 * @since 1999.03.07
	 * @author HoKoNoUmo
	 */
	final void searchFile (String kbFullFileName)
	{
		String					line;								// Raw line read in.
		String					xcpt_sFileName=null;		// the fileName of a concept.
		String					xcpt_sName=null;
		String					cptSyn=null;
		String					matchedSyns="";		// holds ALL termTxCpt_sAll that match.
		BufferedReader	br =null;

		try {
			br = new BufferedReader(new FileReader(kbFullFileName));

			if (!appl.searchName && !appl.searchSyn)
						JOptionPane.showMessageDialog(null, "FindByName: check at least ONE. The name or the synonym.");

			while ((line=br.readLine())!=null && !appl.abortThread)
			{
				if (line.startsWith("<NAME"))
				{
					xcpt_sFileName=line.substring(line.indexOf("FN=")+4, line.indexOf("\"",line.indexOf("FN=")+5));
					xcpt_sName=line.substring(line.indexOf("NN=")+4, line.lastIndexOf("\""));
				}

				else if (line.startsWith("<SYN"))
				{
					if (appl.searchSyn)
					{
						cptSyn=line.substring(line.indexOf("NN=")+4, line.lastIndexOf("\""));
						if (checkMatch(cptSyn)) matchedSyns=matchedSyns +cptSyn +", ";
					}
				}

				else if (line.startsWith("</NAME"))//Add to List
				{
					if (appl.searchName)
					{
						if (appl.searchSyn)
						{
							if (matched) appl.addToList (xcpt_sFileName, matchedSyns, xcpt_sName);
							else if (checkMatch(xcpt_sName)) appl.addToList (xcpt_sFileName, matchedSyns, xcpt_sName);
						}
						else//searchName && !searchSyn
						{
							if (checkMatch(xcpt_sName)) appl.addToList (xcpt_sFileName, matchedSyns, xcpt_sName);
						}
					}
					else//!searchName
					{
						if (appl.searchSyn)
						{
							if (matched) appl.addToList(xcpt_sFileName, matchedSyns, xcpt_sName);
						}
						// else if !searchName && !searchSyn do nothing
					}
					matched=false;//for the next concept.
					matchedSyns="";
				}

			}
			br.close();
		}
		catch (FileNotFoundException fnfe){
			System.out.println("FindByNameThread.searchFile: fnfe: " +kbFullFileName);
		}
		catch (IOException ioe){
			JOptionPane.showMessageDialog(null, "FindByNameThread.searchFile(): IOException: on " +kbFullFileName);
		}
	}//END of searchFile


	/**
	 * Scan a name/synonym to see if the search string is present.
	 * @param inputLine
	 *		The name or synonym to check.
	 */
	final boolean checkMatch (String inputLine)
	{
		inputLine=inputLine.replace('-', ' ');
		if (inputLine.length() > 0)
		{
			// Check if case-sensitive search
			if (appl.matchCase)
			{
				// check to mach name
				if (appl.matchName)
				{
					if (inputLine.indexOf (textToFind) != -1
								 && inputLine.length() == textToFind.length() )
					{
						return matched=true;// Found it!
					}
				}
				// NOT match name
				else {
					// Check if attempting to match whole word
					if (appl.matchWord)
					{
						if (inputLine.indexOf (" " + textToFind + " ") != -1
							 ||	 inputLine.indexOf(textToFind +" ") ==0
							 ||	 (inputLine.indexOf (textToFind) != -1
									 && inputLine.length() == textToFind.length())
							 ||	 (inputLine.indexOf (" " + textToFind) != -1
									 && textToFind.charAt(textToFind.length()-1)
											== inputLine.charAt(inputLine.length()-1)))
						{
							return matched=true;// Found it!
						}
					}
					// not matchWord
					else if (inputLine.indexOf (textToFind) != -1)
					{
						return matched=true;// Found it!
					}
				}
			}
			// NOT matchCase
			else {
				String lower1 = inputLine.toLowerCase();
				String lower2 = textToFind.toLowerCase();

				// check to mach name
				if (appl.matchName)
				{
					if (lower1.indexOf (lower2) != -1
								 && lower1.length() == lower2.length() )
					{
						return matched=true;// Found it!
					}
				}
				// NOT match name
				else {
					// Check if attempting to match whole word
					if (appl.matchWord)
					{
						if (lower1.indexOf (" " + lower2 + " ") != -1
								||(lower1.indexOf (lower2) != -1 && lower1.length() == lower2.length())
								||lower1.indexOf(lower2 +" ") ==0
								||(lower1.indexOf (" " + lower2) != -1
									&& lower2.charAt(lower2.length()-1) == lower1.charAt(lower1.length()-1)))
						{
							return matched=true;// Found it!
						}
					}
					else if (lower1.indexOf (lower2) != -1)
					{
						return matched=true;// Found it!
					}
				} //end matching whole word.
			}
		}
		return matched;
	} // end checkMatch
}


