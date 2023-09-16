package pk_XKBManager;

import pk_Util.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
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
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 * This class searches RELATED sc.
 *
 * @modified 2000.03.12
 * @since 2000.03.04
 * @author HoKoNoUmo
 */
public class FindByRelated extends JFrame
{
	static final long serialVersionUID = 21L;
	JButton jbSearch, jbClear, jbStop, jbHelp;
	JButton jbFindA, jbFindB;
	JButton jbStopA, jbStopB;

	JTextField				jtfInput;							// TextField used to enter search text in
	JTextField				jtfInputA;						//Holds the xcpt_sFormalID which subs we are searching.
	JTextField				jtfInputB;						//Holds the xcpt_sFormalID which pars we are searching.
	JTextField				jtfStatus;						// TextField used to display search status

	JList							jlistResults;					// List to display matches in.
	DefaultListModel	dlistModel;

	JComboBox					jcbMdb;							// ComboBox to hold the SrSubWorldview to search.
	JCheckBox					jchboxCaseSens;			// CheckBox for ConceptA of Question1 case.
	JCheckBox					jchboxMatchWord;
	JCheckBox					jchboxMatchName;

	Color		colorBg=Color.lightGray;			 // Background color.
	Color		colorFg=Color.black;					 // Foreground color.

	FindByRelated objFBRelated=null;				//I need this lg_object in FindByRelatedTread class.
	FindByRelatedTread threadFBR = null;		// Search thread.
	boolean abortThread = false;
	FindByRelatedStartThread threadFBRStart = null;						// Search thread.

	boolean				caseSens	= false;				// Flag to indicate if we need to match case.
	boolean				matchWord = false;				// Flag to indicate if we need to match whole word.
	boolean				matchName = false;				// Flag to indicate if we need to match whole name.
	boolean				searchAtt = false;
	boolean				searchSub = false;

	Vector<String> filesVisited=new Vector<String>();				// Files that have been visited
	Vector<String> filesMatch=new Vector<String>();					// Files that contain the search word

	String				mmToSearch = null;				// Holds the MMases to search.
	String				cptA;											// Holds the cptA of question1.
	String				cptB;
	String				cptAID;										// Holds the ID for cptA of question1.
	String				cptBID;
	String				cptToFind;								// To hold A,B,C values to know which sbcpt we are searching for.
	Vector	<String>			vectorFound = new Vector<String>(); // Holds the xcpt we find after a search.

	/**
	 * @modified 2000.03.14
	 * @since 2000.03.04
	 * @author HoKoNoUmo
	 */
	public FindByRelated()
	{
		super("Find a Concept by searching RELATED concepts");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});
		objFBRelated=this;

		JPanel pNorth = new JPanel(new BorderLayout());
		pNorth.setBorder(BorderFactory.createEtchedBorder());
		//box that contains the search fields.
		Box box = new Box(BoxLayout.Y_AXIS);
		JPanel panel1 = new JPanel();
		jbSearch = new JButton (" search ");
		jbSearch.setFont (new Font ("sansserif", Font.BOLD, 14));
		panel1.add (jbSearch);
		jbClear = new JButton (" clear ");
		jbClear.setFont (new Font ("sansserif", Font.BOLD, 14));
		panel1.add (jbClear);
		jbStop = new JButton (" stop ");
		jbStop.setFont (new Font ("sansserif", Font.BOLD, 14));
		jbStop.setEnabled(false);
		jbStop.setActionCommand("stop");
		panel1.add (jbStop);
		jbHelp = new JButton (" help ");
		jbHelp.setFont (new Font ("sansserif", Font.BOLD, 14));
		panel1.add (jbHelp);
		panel1.setForeground (colorFg);
		panel1.setBackground (colorBg);
		JPanel panel2 = new JPanel();
		JLabel lab3 = new JLabel (" (eg: find 'Athens' which is part of 'Greece')");
		lab3.setFont (new Font ("sansserif", Font.PLAIN, 12));
		lab3.setForeground(Color.black);
		panel2.add (lab3);
		JPanel panel3 = new JPanel();
		JLabel lab2 = new JLabel ("Which is PART OF ");
		lab2.setFont (new Font ("sansserif", Font.BOLD, 12));
		lab2.setForeground(Color.black);
		panel3.add (lab2);
		jtfInputB = new JTextField ("",14);
		jtfInputB.setActionCommand("inputB");
		panel3.add(jtfInputB);
		jbFindB = new JButton (" find ");
		jbFindB.setActionCommand("findB");
		panel3.add(jbFindB);
		jbStopB = new JButton (" stop ");
		jbStopB.setEnabled(false);
		panel3.add(jbStopB);
		JPanel panel4 = new JPanel();
		JLabel lab5 = new JLabel (" (eg: find 'Everest' which is a 'mountain')");
		lab5.setFont (new Font ("sansserif", Font.PLAIN, 12));
		lab5.setForeground(Color.black);
		panel4.add (lab5);
		JPanel panel5 = new JPanel();
		JLabel lab4 = new JLabel ("Which IS A ");
		lab4.setFont (new Font ("sansserif", Font.BOLD, 12));
		lab4.setForeground(Color.black);
		panel5.add (lab4);
		jtfInputA = new JTextField ("",14);
		jtfInputA.setActionCommand("inputA");
		panel5.add(jtfInputA);
		jbFindA = new JButton (" find ");
		jbFindA.setActionCommand("findA");
		panel5.add(jbFindA);
		jbStopA = new JButton (" stop ");
		jbStopA.setEnabled(false);
		panel5.add(jbStopA);
		JPanel panel6 = new JPanel();
		JLabel lab6 = new JLabel ("SEARCH for: ");
		lab6.setFont (new Font ("sansserif", Font.BOLD, 12));
		lab6.setForeground(Color.black);
		panel6.add (lab6);
		jtfInput = new JTextField ("",20);
		jtfInput.setFont (new Font ("sansserif", Font.BOLD, 14));
		jtfInput.setBackground (Color.white);
		panel6.add(jtfInput);
		box.add(panel6);
		box.add(panel5);
		box.add(panel4);
		box.add(panel3);
		box.add(panel2);
		box.add(panel1);
		pNorth.add("Center", box);

		JPanel pCenter = new JPanel(new BorderLayout());
		dlistModel = new DefaultListModel();
		jlistResults = new JList (dlistModel);
		JScrollPane scrollPane = new JScrollPane();
		JViewport vp = scrollPane.getViewport();
		vp.add(jlistResults);
		pCenter.add("Center", scrollPane);

		JPanel pSouth = new JPanel();
		pSouth.setBorder(BorderFactory.createEtchedBorder());
		// This textfield shows the status of the search to provide
		// some feedback to the user. The page count is displayed.
		jtfStatus = new JTextField ("",14);
		jtfStatus.setEditable (false);
		jtfStatus.setBackground (Color.lightGray);
		pSouth.add (jtfStatus);
		//mms contains vd and all.
		Object[] vd = AAj.trmapSrBrSubWorldview.keySet().toArray();
		Object[] mms = new Object[vd.length+1];
		mms[0]="ALL";
		System.arraycopy(vd, 0, mms, 1, vd.length);
		jcbMdb = new JComboBox();
		for (int i = 0; i < mms.length; i++)
		{
			jcbMdb.addItem( mms[i]);
		}
		pSouth.add(jcbMdb);
		JLabel lmm = new JLabel("SBSubWorldview",Label.LEFT);
		lmm.setFont (new Font ("serif", Font.PLAIN, 14));
		pSouth.add(lmm);
		jchboxCaseSens = new JCheckBox ("case sensitive");
		pSouth.add(jchboxCaseSens);
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

		//Actions
		jtfInput.addActionListener(new SearchAction());
		jbSearch.addActionListener(new SearchAction());
		jtfInputA.addActionListener(new FindAction());
		jtfInputB.addActionListener(new FindAction());
		jbFindA.addActionListener(new FindAction());
		jbFindB.addActionListener(new FindAction());
		jbStop.addActionListener(new StopAction());
		jbStopA.addActionListener(new StopAction());
		jbStopB.addActionListener(new StopAction());

		jbClear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				// Clear button pressed - clear all the fields and return
				jtfInput.setText("");
				jtfInputA.setText("");
				jtfInputB.setText("");
				jtfStatus.setText("");

				// Clear radio buttons
				jchboxCaseSens.setSelected(false);
				jchboxMatchWord.setSelected(false);
				jchboxMatchName.setSelected(false);

				if (dlistModel.size() > 0)
					dlistModel.clear();
				threadFBR = null;
			}
		});

		jbHelp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				AAj.display("it-49#3");
				AAj.frameAAj.toFront();
				AAj.frameAAj.requestFocus();
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
					if (index < filesMatch.size())
					{
						String kspFile = filesMatch.elementAt(index);
						// Display the concept on viewer.
						String lastSrCptName = Util.getXCpt_sLastFileName(kspFile);
						AAj.display(lastSrCptName);
					}
				}
			}
		};
		jlistResults.addMouseListener(mouseListener);

		jchboxCaseSens.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (jchboxCaseSens.isSelected() == true)
					caseSens = true;
				else
					caseSens = false;
			}
		});
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

		setIconImage( Util.getImage("resources/AAj_icon.gif") );
		setSize(new Dimension(599,399));
		setLocation(199,116);
		setVisible(true);
	}

	/**
	 * @modified
	 * @since 2000.03.12
	 * @author HoKoNoUmo
	 */
	void addToVectorFound (String xcpt_sFileName, String xcpt_sName)
	{
		String cptFID = Util.getXCpt_sFormalID(xcpt_sFileName);
		vectorFound.addElement(xcpt_sName +" (" +cptFID		+")");
	}

	/**
	 * @modified 2000.03.12
	 * @author HoKoNoUmo
	 */
	public void enableButtons ()
	{
		if (cptToFind.equals("A"))
		{
			jbFindA.setEnabled(true);
			jbStopA.setEnabled(false);
		}
		else if (cptToFind.equals("B"))
		{
			jbFindB.setEnabled(true);
			jbStopB.setEnabled(false);
		}
		else if (cptToFind.equals("C"))
		{
			jbSearch.setEnabled(true);
			jbClear.setEnabled(true);
			jbStop.setEnabled(false);
		}
	}

	/**
	 * @modified 2000.03.12
	 * @author HoKoNoUmo
	 */
	final void disableButtons ()
	{
		if (cptToFind.equals("A"))
		{
			jbFindA.setEnabled(false);
			jbStopA.setEnabled(true);
		}
		else if (cptToFind.equals("B"))
		{
			jbFindB.setEnabled(false);
			jbStopB.setEnabled(true);
		}
		else if (cptToFind.equals("C"))
		{
			jbSearch.setEnabled(false);
			jbClear.setEnabled(false);
			jbStop.setEnabled(true);
		}
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
	 * @param ns name or synonym.
	 */
	public void addToList (String file, String line, String xcpt_sName, String ns)
	{
		String xcpt_sFormalID = file.substring(file.indexOf("@")+1, file.length()-4);

		dlistModel.addElement("CONCEPT: " +xcpt_sName +" (" +xcpt_sFormalID		+"), "
																			+"			 " +ns +": " +line);
		filesMatch.addElement(file);
	}

	//*********************************************************************
	/**
	 * @modified
	 * @since 2000.03.12
	 * @author HoKoNoUmo
	 */
	class FindAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			mmToSearch=(String)jcbMdb.getSelectedItem();
			if (e.getActionCommand().equals("findA") || e.getActionCommand().equals("inputA"))
			{
				cptToFind="A";
				cptA=jtfInputA.getText();
				cptA.trim();
				if (cptA.length()>1)
				{
					disableButtons();
					abortThread=false; //we can make a search.
					threadFBRStart = new FindByRelatedStartThread(objFBRelated, cptA);
					threadFBRStart.start();
				}
			}
			else if (e.getActionCommand().equals("findB") || e.getActionCommand().equals("inputB"))
			{
				cptToFind="B";
				cptB=jtfInputB.getText();
				cptB.trim();
				if (cptB.length()>1)
				{
					disableButtons();
					abortThread=false; //we can make a search.
					threadFBRStart = new FindByRelatedStartThread(objFBRelated, cptB);
					threadFBRStart.start();
				}
			}
		}
	}

	//************************************************************************
	/**
	 * @modified 2000.03.12
	 * @author HoKoNoUmo
	 */
	class SearchAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			// Search button pressed - read in search text entered
			String text = jtfInput.getText();
			text=text.replace('-', ' ');
			cptToFind="C";

			// Make sure ther's somthing to search for
			if (text.length() != 0)
			{
				// New search so clear the GUI out
				if (!dlistModel.isEmpty())	dlistModel.clear();
				disableButtons();
				jtfStatus.setText("");
				// Clear out previous search data
				filesVisited.removeAllElements();
				filesMatch.removeAllElements();

				// We're off - start the search thread
				abortThread=false; //we can make a search.
				threadFBR = new FindByRelatedTread(objFBRelated, text);
				threadFBR.start();
			}
		}
	}

	//*********************************************************************
	/**
	 * @modified
	 * @since 2000.03.12
	 * @author HoKoNoUmo
	 */
	class StopAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("stop"))
			{
				// Abort button pressed - stop the thread
				if (threadFBR != null) abortThread=true;
				threadFBR = null;
				// Enable buttons for another search
//				enableButtons();
			}
			else {
				// Abort button pressed - stop the thread
				if (threadFBRStart != null) abortThread=true;
				threadFBRStart = null;
				// Enable buttons for another search
//				enableButtons();
			}
		}
	}

}


//************************************************************************
//************************************************************************
/**
 * This thread performs the search.
 * It begins searching a file and then searches all its specifics/parts.
 */
class FindByRelatedTread extends Thread
{
	FindByRelated appl;
	String				startCptFile=null;						// The name of file of the start concept.
	String				textToFind=null;							// String to find
	int						hitsFound = 0;								// No of occurrences of search string
	Vector<String>	filesToSearch=new Vector<String>();

	/**
	 * Constructor
	 */
	FindByRelatedTread (FindByRelated application, String text)
	{
		appl=application;
		textToFind = text;
	}

	/**
	 * @modified
	 * @since 2000.03.04
	 * @author HoKoNoUmo
	 */
	public void run()
	{
		startSearch();
		appl.enableButtons();
		appl.jtfStatus.setText("Matches=" +hitsFound +", Searched=" +appl.getTotalFiles());
	}

	/**
	 * @modified
	 * @since 2000.03.04
	 * @author HoKoNoUmo
	 */
	final void startSearch()
	{
		String currentFileName=null;	// Xml File currently being searched
		String startGen=null;					//Holds the gen, the subs of which we will search.
		String startWho=null;					//Holds the who, the pars of which we will search.
		File xcpt_sDir=null;
		FilterID flt=null;
		String[] fileName=new String[2];

		try {
			startGen=appl.jtfInputA.getText();
			if (startGen.length()>2) startGen=startGen.substring(startGen.indexOf("(")+1, startGen.indexOf(")"));
			startWho=appl.jtfInputB.getText();
			if (startWho.length()>2) startWho=startWho.substring(startWho.indexOf("(")+1, startWho.indexOf(")"));
		}
		catch (NullPointerException npe){JOptionPane.showMessageDialog(null, "You MUST put a conceptID to start searching from");}

		//IF start-sbcpt in NOT null, searches the vector filesToSearch.
		if (startGen.length()>2)
		{
			appl.searchSub=true; appl.searchAtt=false; //we are searching first the subs.

			String mm2 = startGen.substring(0, startGen.indexOf("-"));
			String vd = mm2.toLowerCase();
			xcpt_sDir = new File (Util.getXCpt_sFullDir(startGen));
			flt = new FilterID(startGen);
			try {
				fileName = xcpt_sDir.list(flt);
				if (fileName[0] != null)
				{
					filesToSearch.addElement(fileName[0]);
					startCptFile = fileName[0];
				}
			}
			catch (Exception elist)
			{
				JOptionPane.showMessageDialog(null, "There is NOT file for this ID");
			}

			while(!filesToSearch.isEmpty() && !appl.abortThread)
			{
				try {
					currentFileName = filesToSearch.firstElement();
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "FindByRelatedTread.StartSearch: File= " + currentFileName);
					continue;				// Try next file
				}
				searchFile(currentFileName);
				appl.filesVisited.addElement(currentFileName);
				filesToSearch.remove(0);
			}
		}

		if (startWho.length()>2)
		{
			appl.searchAtt=true; appl.searchSub=false;

			String mm2 = startWho.substring(0, startWho.indexOf("-"));
			String vd = mm2.toLowerCase();
			xcpt_sDir = new File (Util.getXCpt_sFullDir(startWho));
			flt = new FilterID(startWho);
			try {
				fileName = xcpt_sDir.list(flt);
				if (fileName[0] != null)
				{
					filesToSearch.addElement(fileName[0]);
					startCptFile = fileName[0];
				}
			}
			catch (Exception elist)
			{
				JOptionPane.showMessageDialog(null, "There is NOT file for this ID");
			}

			while(!filesToSearch.isEmpty() && !appl.abortThread)
			{
				try {
					currentFileName = filesToSearch.firstElement();
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "FindByRelatedTread.StartSearch: File= " + currentFileName);
					continue;				// Try next file
				}
				searchFile(currentFileName);
				appl.filesVisited.addElement(currentFileName);
				filesToSearch.remove(0);
			}
		}
	}

	/**
	 * Reads ONE file and searches the &lt;name&gt; xml elements.
	 * Also reads attributes/specifics, and add these concepts
	 * to vector filesToSearch.
	 * @modified
	 * @since 2000.03.04
	 * @author HoKoNoUmo
	 */
	final void searchFile (String fileName)
	{
		String					line;						// Raw line read in.
		String					xcpt_sName=null;		// the first &lt;name&gt; is the name of the concept.
		int							intNameCount=0; // counts the name/termTxCpt_sAll of a concept.
		BufferedReader	br=null;				// holds the file we search.

		//we search for the xcpt-id not for filename.
		try {
			br = new BufferedReader(new FileReader(Util.getXCpt_sLastFullFileName(fileName)));
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("FindByRelatedTread.searchFile: fnfe: " +fileName);
		}

		try {
			while ((line=br.readLine()) != null)
			{
				if (line.indexOf("<NAME")!=-1)
				{
					intNameCount++;
					line = br.readLine(); //sbcpt's name
					// assume every synonym on ONE line.
					if (intNameCount==1)
					{
						xcpt_sName=line;
						// check for match
						checkMatch (line, fileName, xcpt_sName, "NAME");
					}
					if (intNameCount>1)
					{
						checkMatch (line, fileName, xcpt_sName, "REFINO_NAME");
					}
					line=br.readLine();
				}

				else if (line.indexOf("</REFINO_NAME")!=-1 || line.indexOf("<REFINO_DEFINITION")!=-1)
				{
					if (startCptFile==null)
					{
						br.close();
						break;
					}
					else continue;
				}

				//LINE begins with <attributes.
				else if (line.indexOf("<REFINO_PART")!=-1)
				{
					if (appl.searchAtt==true && startCptFile.equals(fileName))
					{
						//This is the start concept, extract only the attributes.
						while(line.indexOf("</REFINO_PART")==-1)
						{
							line=br.readLine();
							if (line.startsWith("<XCPT"))
							{
								String sbcpt=Util.getXmlAttribute(line,"FRnAME");
								String gnr=Util.getXmlAttribute(line,"REFINO_GENERIC");
								//add only file-cpts.
								if (gnr.equals("no"))
								{
									if (sbcpt.indexOf("#")==-1)
										if (!appl.filesVisited.contains(sbcpt)) filesToSearch.addElement(sbcpt);
								}
								else {
									if (!Util.getXCpt_sFormalID(sbcpt).equals(Util.getXCpt_sFormalID(gnr)))
										if (!appl.filesVisited.contains(sbcpt)) filesToSearch.addElement(sbcpt);
								}
							}
						}

						if (appl.searchSub==true)
						{
							line=br.readLine();
							continue;
						}
						else {
							br.close();
							break;
						}
					}

					else if (appl.searchAtt==true && !startCptFile.equals(fileName))
					{
						//this is NOT the start sbcpt, extract att and spe
						while(line.indexOf("</REFINO_PART")==-1)
						{
							line=br.readLine();
							if (line.startsWith("<XCPT"))
							{
								String sbcpt=Util.getXmlAttribute(line,"FRnAME");
								String gnr=Util.getXmlAttribute(line,"REFINO_GENERIC");
								//add only file-cpts.
								if (gnr.equals("no"))
								{
									if (sbcpt.indexOf("#")==-1)
										if (!appl.filesVisited.contains(sbcpt)) filesToSearch.addElement(sbcpt);
								}
								else {
									if (!Util.getXCpt_sFormalID(sbcpt).equals(Util.getXCpt_sFormalID(gnr)))
										if (!appl.filesVisited.contains(sbcpt)) filesToSearch.addElement(sbcpt);
								}
							}
						}
						line=br.readLine();
						continue; //in order to extract subs.
					}
					else if (appl.searchSub==true)
					{
						continue;
					}
					else {
						br.close();
						break;
					}
				}

				//this is a <specific line.
				else if (line.indexOf("<REFINO_SPECIFIC")!=-1)
				{
					if ((startCptFile.equals(fileName) && appl.searchAtt==true)
							&& appl.searchSub==false)
					{
						br.close();
						break;
					}

					else if ( appl.searchSub==true
										||(appl.searchAtt==true && appl.searchSub==true)
										||(appl.searchAtt==true && !startCptFile.equals(fileName))
									)
					{
						//extract spe
						while(line.indexOf("</REFINO_SPECIFIC")==-1)
						{
							line=br.readLine();
							if (line.startsWith("<XCPT"))
							{
								String sbcpt=Util.getXmlAttribute(line,"FRnAME");
								String gnr=Util.getXmlAttribute(line,"REFINO_GENERIC");
								//add only file-cpts.
								if (gnr.equals("no"))
								{
									if (sbcpt.indexOf("#")==-1)
										if (!appl.filesVisited.contains(sbcpt)) filesToSearch.addElement(sbcpt);
								}
								else {
									if (!Util.getXCpt_sFormalID(sbcpt).equals(Util.getXCpt_sFormalID(gnr)))
										if (!appl.filesVisited.contains(sbcpt)) filesToSearch.addElement(sbcpt);
								}
							}
						}
						br.close();
						break;
					}

					else {
						br.close();
						break;
					}
				}

				else if (line.indexOf("</XCONCEPT")!=-1)
				{
					br.close();
					break;
				}

				else continue;

			}
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "searchFile(): IOException: on concept: " +fileName);
		}
	} //END of searchFile

	/**
	 * Scan a line of text to see if the search string is present.
	 * If so then add the line to the list of matches.
	 * @param ns name or synonym of a concept
	 */
	final void checkMatch (String inputLine, String fileName, String xcpt_sName, String ns)
	{
		String searchLine = inputLine;

		if (searchLine.length() > 0)
		{
			// Check if case-sensitive search
			if (appl.caseSens)
			{
				// check to mach name
				if (appl.matchName)
				{
					if (searchLine.indexOf (textToFind) != -1
								 && searchLine.length() == textToFind.length() )
					{
						// Found it! Display the match
						appl.addToList (fileName, searchLine, xcpt_sName, ns);
						hitsFound++;
					}
				}
				// NOT match name
				else {
					// Check if attempting to match whole word
					if (appl.matchWord)
					{
						if (searchLine.indexOf (" " + textToFind + " ") != -1
							 ||	 searchLine.indexOf(textToFind +" ") ==0
							 ||	 (searchLine.indexOf (textToFind) != -1
									 && searchLine.length() == textToFind.length())
							 ||	 (searchLine.indexOf (" " + textToFind) != -1
									 && textToFind.charAt(textToFind.length()-1)
											== searchLine.charAt(searchLine.length()-1)))
						{
							// Found it! Display the match
							appl.addToList (fileName, searchLine, xcpt_sName, ns);
							hitsFound++;
						}
					}
					// not matchWord
					else if (searchLine.indexOf (textToFind) != -1)
					{
						// Found it! Display the match
						appl.addToList (fileName, searchLine, xcpt_sName, ns);
						hitsFound++;
					}
				}
			}
			// NOT caseSens
			else {
				String lower1 = searchLine.toLowerCase();
				String lower2 = textToFind.toLowerCase();

				// check to mach name
				if (appl.matchName)
				{
					if (lower1.indexOf (lower2) != -1
								 && lower1.length() == lower2.length() )
					{
						// Found it! Display the match
						appl.addToList (fileName, searchLine, xcpt_sName, ns);
						hitsFound++;
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
							// Found it! Display the match
							appl.addToList (fileName, searchLine, xcpt_sName, ns);
							hitsFound++;
						}
					}
					else if (lower1.indexOf (lower2) != -1)
					{
						// Found it! Display the match
						appl.addToList (fileName, searchLine, xcpt_sName, ns);
						hitsFound++;
					}
				} //end matching whole word.
			}
		}
	} // end checkMatch
}

/**
 * @modified
 * @since 2000.03.12
 * @author HoKoNoUmo
 */
class FindByRelatedStartThread extends Thread
{
	FindByRelated appl;													//we need this to use its variables.
	String					startCptFile=null;						// The name of file of the start concept.
	String				textToFind=null;							// String to find
	Vector<String>	filesToSearch=new Vector<String>();
	boolean				matched=false;								//flag if we found a hit on a concept.

	/**
	 * Constructor
	 */
	FindByRelatedStartThread (FindByRelated application, String text)
	{
		appl=application;
		textToFind = text;
	}

	/**
	 * @modified 2000.03.09
	 * @author HoKoNoUmo
	 */
	public void run()
	{
		Object[] cptsArray = null;
		Object selectedValue = null;
		appl.vectorFound=new Vector<String>();

		if (appl.mmToSearch.equals("ALL"))
		{
			//it is better to search the hknu.symb/it vd and then the others.
			//here we can put priorities.
			for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
			{
				String vd = (String)i.next(); //key = the vd.
				if (appl.abortThread) break;
				String kbFileName = vd +"'termTxCpt_sAll.xml";
				String mmDir = Util.getXCpt_sFullDir(vd +"-1");
				String kbFullFileName=mmDir +File.separator +kbFileName;
				File fl = new File(kbFullFileName);
				if (fl.exists())
				{
					searchFile(kbFullFileName);
				}
			}
		}

		else //ONE vd are selected.
		{
			String vd = appl.mmToSearch;
			String kbFileName = vd +"'termTxCpt_sAll.xml";
			String mmDir = Util.getXCpt_sFullDir(vd +"-1");
			String kbFullFileName=mmDir +File.separator +kbFileName;
			File fl = new File(kbFullFileName);
			if (fl.exists())
			{
				searchFile(kbFullFileName);
			}
		}

		appl.enableButtons(); //after searching, enable findCpt buttons.

		if (!appl.vectorFound.isEmpty())
		{
			if (appl.cptToFind.equals("A"))
			{
				cptsArray = appl.vectorFound.toArray();
				selectedValue = JOptionPane.showInputDialog(null,
								"Choose CONCEPT-A",												//message
								"Choose One",															//title
								JOptionPane.INFORMATION_MESSAGE,					//message type
								null,																			//icon
								cptsArray,																//values to select
								cptsArray[0]);														//initial selection
				if (selectedValue!=null)
				{
					appl.jtfInputA.setText((String)selectedValue);
					appl.cptA = (String)selectedValue;
					appl.cptAID=appl.cptA.substring(appl.cptA.indexOf("(")+1,appl.cptA.indexOf(")"));
				}
				else JOptionPane.showMessageDialog(null, "No concept-A selected");
			}
			else if (appl.cptToFind.equals("B"))
			{
				cptsArray = appl.vectorFound.toArray();
				selectedValue = JOptionPane.showInputDialog(null,
								"Choose CONCEPT-B",												//message
								"Choose One",															//title
								JOptionPane.INFORMATION_MESSAGE,					//message type
								null,																			//icon
								cptsArray,																//values to select
								cptsArray[0]);														//initial selection
				if (selectedValue!=null)
				{
					appl.jtfInputB.setText((String)selectedValue);
					appl.cptB = (String)selectedValue;
					appl.cptBID=appl.cptB.substring(appl.cptB.indexOf("(")+1,appl.cptB.indexOf(")"));
				}
				else JOptionPane.showMessageDialog(null, "No concept-B selected");
			}
		}
		else JOptionPane.showMessageDialog(null, "NO concept found, give another try");
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
		BufferedReader	br =null;

		try {
			br = new BufferedReader(new FileReader(kbFullFileName));

			while ((line=br.readLine())!=null && !appl.abortThread)
			{
				if (line.startsWith("<NAME"))
				{
					xcpt_sFileName=line.substring(line.indexOf("FN=")+4, line.indexOf("\"",line.indexOf("FN=")+5));
					xcpt_sName=line.substring(line.indexOf("NN=")+4, line.lastIndexOf("\""));
				}

				else if (line.startsWith("<SYN"))
				{
					cptSyn=line.substring(line.indexOf("NN=")+4, line.lastIndexOf("\""));
					matched=checkMatch(cptSyn);
				}

				else if (line.startsWith("</NAME"))//Add to List
				{
					if (matched ||checkMatch(xcpt_sName))
						appl.addToVectorFound (xcpt_sFileName, xcpt_sName);

					matched=false;//for the next concept.
				}

			}
			br.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("FindByRelatedStartThread.searchFile: fnfe: " +kbFullFileName);
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "FindByRelatedStartThread.searchFile(): IOException: on " +kbFullFileName);
		}
	}//END of searchFile

	/**
	 * Scan a name/synonym to see if the search string is present.
	 * @param inputLine the name or synonym to check.
	 */
	final boolean checkMatch (String inputLine)
	{
		if (inputLine.length() > 0)
		{
			// Check if case-sensitive search
			if (appl.caseSens)
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

				else// NOT match name
				{
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
			// NOT caseSens
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
