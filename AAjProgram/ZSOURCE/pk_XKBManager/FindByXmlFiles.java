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

/**
 * This class searches the &lt;name&gt;/<termTxCpt_sAll> elements of the XML-FILES (my concepts).
 *
 * It searches ALL the files
 * or
 * It can start from a concept and begin searching its specifics/parts
 * we recursively find. So we can significantly reduce the amount of concepts to search.
 *
 * I can have the option to search only the names not the termTxCpt_sAll. [1999.03.28]
 *
 * @modified 2000.06.20
 * @since 1999.03.07
 * @author HoKoNoUmo
 */
public class FindByXmlFiles extends JFrame
{
	static final long serialVersionUID = 21L;
	JButton search, clear, abort;						// GUI buttons

	public JTextField jtfieldInput;					// TextField used to enter search text in
	JTextField				jtfieldStart;
	JTextField				jtfieldStatus;				// TextField used to display search status

	public JList			jlistMdb;							// Holds the Knowledge Bases.
	final JList				jlistResults;					// List to display matches in.
	DefaultListModel	dlistModel;
	JRadioButton			jrButtonCaseSens;
	JRadioButton			jrButtonCaseInsens;

	JCheckBox					jchboxPar;
	JCheckBox					jchboxSub;
	JCheckBox					jchboxMatchWord;
	JCheckBox					jchboxMatchName;

	Color		colorBg=Color.lightGray;			 // Background color.
	Color		colorFg=Color.black;					 // Foreground color.

	FindByXmlFiles objSearchXml=null;						//I need this lg_object in FindByXmlFilesThread class.
	FindByXmlFilesThread threadSF = null;							// Search thread.
	boolean abortThread = true;

	public boolean matchCase = false;				// Flag to indicate if we need to match case.
	public boolean matchWord = false;				// Flag to indicate if we need to match whole word.
	public boolean matchName = false;				// Flag to indicate if we need to match whole name.
	public boolean searchPar	= false;
	public boolean searchSub	= false;

	Vector<String> filesVisited=new Vector<String>();				// Files that have been visited
	Vector<String> filesMatch=new Vector<String>();					// Files that contain the search word

	Object[] xcpt_sDir = null;


	//************************************************************************
	public static void main(String args[])
	{
		new FindByXmlFiles();
	}

	//************************************************************************
	public FindByXmlFiles()
	{
		super("Search the names/termTxCpt_sAll of the Xml concept-files");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});

		objSearchXml=this;

		JPanel pNorth = new JPanel(new BorderLayout());
		pNorth.setBorder(BorderFactory.createEtchedBorder());
		// Left box that holds the list of knowledge bases.
		Box boxL = new Box(BoxLayout.Y_AXIS);
		JLabel lab5 = new JLabel ("choose a MM:");
		lab5.setFont (new Font ("sansserif", Font.PLAIN, 12));
		lab5.setForeground(Color.black);
		boxL.add(lab5);
		Object[] vd = AAj.trmapSrBrSubWorldview.keySet().toArray();
		Object[] mms = new Object[vd.length+1];
		mms[0]="ALL";
		System.arraycopy(vd, 0, mms, 1, vd.length);
		jlistMdb = new JList(mms);
		jlistMdb.setFixedCellHeight(15);
		jlistMdb.setSelectedIndex(0);
		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(new Dimension(59, 74));
		JViewport vp2 = sp.getViewport();
		vp2.add(jlistMdb);
		boxL.add(sp);
		//box that contains the search fields.
		Box boxR = new Box(BoxLayout.Y_AXIS);
		JPanel pbox1 = new JPanel();
		jchboxPar = new JCheckBox ("parts");
		pbox1.add(jchboxPar);
		jchboxSub = new JCheckBox ("specifics");
		pbox1.add(jchboxSub);
		JPanel pbox2 = new JPanel();
		JLabel lab3 = new JLabel ("START from (id/FormalName): ");
		lab3.setFont (new Font ("sansserif", Font.PLAIN, 12));
		lab3.setForeground(Color.black);
		pbox2.add(lab3);
		jtfieldStart = new JTextField ("",16);
		pbox2.add(jtfieldStart);
		JLabel lab4 = new JLabel ("and search its: ");
		lab4.setFont (new Font ("sansserif", Font.PLAIN, 12));
		lab4.setForeground(Color.black);
		pbox2.add(lab4);
		JPanel pbox3 = new JPanel();
		search = new JButton (" search ");
		search.setFont (new Font ("sansserif", Font.BOLD, 14));
		pbox3.add (search);
		clear = new JButton (" clear ");
		clear.setFont (new Font ("sansserif", Font.BOLD, 14));
		pbox3.add (clear);
		abort = new JButton (" stop ");
		abort.setFont (new Font ("sansserif", Font.BOLD, 14));
		abort.setEnabled(false);
		pbox3.add (abort);
		pbox3.setForeground (colorFg);
		pbox3.setBackground (colorBg);
		JPanel pbox4 = new JPanel();
		JLabel lab = new JLabel ("SEARCH for: ");
		lab.setFont (new Font ("sansserif", Font.PLAIN, 12));
		lab.setForeground(Color.black);
		pbox4.add (lab);
		jtfieldInput = new JTextField ("",26);
		jtfieldInput.setBackground (Color.white);
		pbox4.add (jtfieldInput);
		boxR.add(pbox4);
		boxR.add(pbox3);
		boxR.add(pbox2);
		boxR.add(pbox1);
		pNorth.add("West", boxL);
		pNorth.add("Center", boxR);

		JPanel pCenter = new JPanel(new BorderLayout());
//		pCenter.setBorder(BorderFactory.createEtchedBorder());
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
		jtfieldStatus = new JTextField ("",14);
		jtfieldStatus.setEditable (false);
		jtfieldStatus.setBackground (Color.lightGray);
		pSouth.add (jtfieldStatus);
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

		jtfieldInput.addActionListener(new SearchCommand());
		jtfieldStart.addActionListener(new SearchCommand());
		search.addActionListener(new SearchCommand());

		clear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				// Clear button pressed - clear all the fields and return
				jtfieldInput.setText("");
				jtfieldStatus.setText("");

				// Clear radio buttons
				jrButtonCaseSens.setSelected(false);
				jrButtonCaseInsens.setSelected(true);
				jchboxMatchWord.setSelected(false);

				if (dlistModel.size() > 0)
					dlistModel.clear();
				threadSF = null;
			}
		});

		abort.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				// Abort button pressed - stop the thread
				if (threadSF != null) abortThread=false;
				threadSF = null;
				// Enable buttons for another search
				enableButtons();
				abort.setEnabled(false);
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

		jchboxPar.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				if (jchboxPar.isSelected() == true)
					searchPar = true;
				else
					searchPar = false;
			}
		});

		jchboxSub.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {
				if (jchboxSub.isSelected() == true)
					searchSub = true;
				else
					searchSub = false;
			}
		});

		setIconImage( Util.getImage("resources/AAj_icon.gif") );
		setSize(new Dimension(576,374));
		setLocation(222,126);
		setVisible(true);
	} //end FindByXmlFiles

	//************************************************************************
	/**
	 * enable use of buttons in GUI
	 */
	public void enableButtons ()
	{
		search.setEnabled(true);
		clear.setEnabled(true);
	}

	//************************************************************************
	/**
	 * Disable use of buttons in GUI
	 */
	final void disableButtons ()
	{
		search.setEnabled(false);
		clear.setEnabled(false);
	}

	//************************************************************************
	/**
	 * returns the number of XmlFiles that the search thread has visited
	 */
	public int getTotalFiles ()
	{
		return filesVisited.size();
	}

	//************************************************************************
	/**
	 * Adds the concept we found to list.
	 * @param ns name or synonym.
	 */
	public void addToList (String file, String line, String xcpt_sName, String ns)
	{
		String xcpt_sFormalID = file.substring(file.indexOf("@")+1, file.length()-4);

		dlistModel.addElement("CONCEPT: " +xcpt_sName +" (" +xcpt_sFormalID	 +"), "
																			+"			 " +ns +": " +line);
		filesMatch.addElement(file);
	}

	//************************************************************************
	//************************************************************************
	/**
	 * Handles case radiobuttons
	 */
	public class CaseCommand extends AbstractAction {
		static final long serialVersionUID = 21L;
		CaseCommand()
		{
			super("case");
		}
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("first"))
				matchCase = false;
			else
				matchCase = true;
		}
	}

	//************************************************************************
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
			String text = jtfieldInput.getText();

			// Make sure ther's somthing to search for
			if (text.length() != 0)
			{
				// New search so clear the GUI out
				if (!dlistModel.isEmpty())	dlistModel.clear();
				disableButtons ();
				abort.setEnabled(true);
				jtfieldStatus.setText("");
				// Clear out previous search data
				filesVisited.removeAllElements();
				filesMatch.removeAllElements();

				// We're off - start the search thread
				xcpt_sDir = jlistMdb.getSelectedValues();
				abortThread=true; //we can make a search.
				threadSF = new FindByXmlFilesThread(objSearchXml, text);
				threadSF.start();
			}
		}
	}

}


//************************************************************************
//************************************************************************
//************************************************************************
/**
 * This thread performs the search. It searches all the files.
 * or it begins a search at a file and then searches all its specifics/parts.
 */
class FindByXmlFilesThread extends Thread
{
	FindByXmlFiles	appl;
	String			startCptFile=null;						// The name of file of the start concept.
	String			textToFind=null;							// String to find
	int					hitsFound = 0;								// No of occurrences of search string
	Vector<String>	filesToSearch=new Vector<String>();

	//************************************************************************
	/**
	 * Constructor
	 */
	FindByXmlFilesThread (FindByXmlFiles application, String text)
	{
		appl=application;
		textToFind = text;
	}

	//************************************************************************
	public void run()
	{
		startSearch();

		appl.enableButtons();
		appl.abort.setEnabled(false);
		appl.jtfieldStatus.setText("Matches=" +hitsFound +", Searched=" +appl.getTotalFiles());
	}

	//************************************************************************
	/**
	 * @modified 1999.03.28
	 * @since 1999mar
	 * @author nikas
	 */
	final void startSearch()
	{
		String currentFileName=null;		 // Xml File currently being searched
		String startCpt=null;
		try {
			startCpt=appl.jtfieldStart.getText();
		}
		catch (NullPointerException npe){}

		//IF start-sbcpt null, searches ALL the files.
		if (startCpt.length()<2)
		{
			//nothing on start tfield
			if (appl.xcpt_sDir[0].equals("ALL"))
			{
				//it is better to search the hknu.symb/it vd and then the others.
				for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
				{
					String key = (String)i.next();
					File file = new File (Util.getXCpt_sFullDir(key+"-1"));
					FilterID flt = new FilterID("");
					String[] arrayFiles=null;
					try {arrayFiles = file.list(flt);}
					catch (Exception el) {}
					if (arrayFiles!=null)
					{
						while (appl.abortThread)
						{
							for(int j=0; j<arrayFiles.length; j++)
							{
								try {
									currentFileName = arrayFiles[j];
								}
								catch (Exception e)
								{
									JOptionPane.showMessageDialog(null, "FindByXmlFilesThread.StartSearch: File= "
																												+currentFileName);
									continue;				// Try next page
								}
								if (!appl.abortThread) break;
								if (!currentFileName.endsWith("'termTxCpt_sAll.xml"))
								{
									searchFile(currentFileName);
									appl.filesVisited.addElement(currentFileName);
								}
							}
							break;
						}
					}
				}
			}
			else //some mms are selected.
			{
				for (int i=0; i<appl.xcpt_sDir.length; i++)
				{
					File file = new File (Util.getXCpt_sFullDir(appl.xcpt_sDir[i] +"-1"));
					FilterID flt = new FilterID("");
					String[] arrayFiles=null;
					try {arrayFiles = file.list(flt);}
					catch (Exception el) {}
					while (appl.abortThread)
					{
						for(int j=0; j<arrayFiles.length; j++)
						{
							try {
								currentFileName = arrayFiles[j];
							}
							catch (Exception e)
							{
								JOptionPane.showMessageDialog(null, "StartSearch(): Exception: " + e + " File= " + currentFileName);
								continue;				// Try next page
							}
							if (!appl.abortThread) break;
							if (!currentFileName.endsWith("'termTxCpt_sAll.xml"))
							{
								searchFile(currentFileName);
								appl.filesVisited.addElement(currentFileName);
							}
						}
						break;
					}
				}
			}
		}

		//IF start-sbcpt in NOT null, searches the vector filesToSearch.
		if (startCpt.length()>2)
		{
			File xcpt_sDir=null;
			FilterID flt=null;
			String[] fileName=new String[2];
			boolean found=false;

			if (startCpt.indexOf("-")>1)
			{
				//we are searching for an ID OR filename with -.
				String mm2 = startCpt.substring(0, startCpt.indexOf("-"));
				String vd = mm2.toLowerCase();

				if (AAj.trmapSrBrSubWorldview.containsKey(vd)) //startCpt is an id.
				{
					xcpt_sDir = new File (Util.getXCpt_sFullDir(startCpt));
					flt = new FilterID(startCpt);
					try {
						fileName = xcpt_sDir.list(flt);
						if (fileName[0] != null)
						{
							found=true;
							filesToSearch.addElement(fileName[0]);
							startCptFile = fileName[0];
						}
					}
					catch (Exception elist)
					{
						JOptionPane.showMessageDialog(null, "There is NOT file for this ID");
					}
				}
				else //name with -.
				{
					//we are searching for a name.
					FilterName flt2 = new FilterName(startCpt+"@");//to search ONLY for whole names
					try {
						//search first 'hknu.symb'
						xcpt_sDir = new File(Util.getXCpt_sFullDir("hknu.meta-1"));
						fileName = xcpt_sDir.list(flt2);
						if (fileName[0] != null)
						{
							found=true;
							filesToSearch.addElement(fileName[0]);
							startCptFile = fileName[0];
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
								filesToSearch.addElement(fileName[0]);
								startCptFile = fileName[0];
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
									filesToSearch.addElement(fileName[0]);
									startCptFile = fileName[0];
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
										filesToSearch.addElement(fileName[0]);
										startCptFile = fileName[0];
									}
								}
								catch (Exception e6)
								{
									//search all kn and break
									for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
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
													filesToSearch.addElement(fileName[0]);
													startCptFile = fileName[0];
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
				FilterName flt2 = new FilterName(startCpt+"@");//to search ONLY for whole names
				try {
					//search first 'hknu.symb'
					xcpt_sDir = new File(Util.getXCpt_sFullDir("hknu.meta-1"));
					fileName = xcpt_sDir.list(flt2);
					if (fileName[0] != null)
					{
						found=true;
						filesToSearch.addElement(fileName[0]);
						startCptFile = fileName[0];
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
							filesToSearch.addElement(fileName[0]);
							startCptFile = fileName[0];
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
								filesToSearch.addElement(fileName[0]);
								startCptFile = fileName[0];
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
									filesToSearch.addElement(fileName[0]);
									startCptFile = fileName[0];
								}
							}
							catch (Exception e6)
							{
								//search all kn and break
								for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
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
												filesToSearch.addElement(fileName[0]);
												startCptFile = fileName[0];
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

		while(!filesToSearch.isEmpty() && appl.abortThread)
		{
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "FindByXmlFilesThread.StartSearch: File= " + currentFileName);
				continue;				// Try next file
			}
				if (!currentFileName.endsWith("'termTxCpt_sAll.xml"))
				{
					searchFile(currentFileName);
					appl.filesVisited.addElement(currentFileName);
				}
				filesToSearch.remove(0);
		}
	}

	//************************************************************************
	/**
	 * Read ONE file and searches the &lt;name&gt; xml elements.
	 * Also reads the attributes/specifics if asked, and add these concepts
	 * to vector filesToSearch.
	 * @modified 1999.03.29
	 * @since 1999.03.07
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
			System.out.println("FindByXmlFilesThread.searchFile: fnfe: " +fileName);
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

					if (appl.searchPar==true && startCptFile.equals(fileName))
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

					else if (appl.searchPar==true && !startCptFile.equals(fileName))
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
					if ((startCptFile.equals(fileName) && appl.searchPar==true)
							&& appl.searchSub==false)
					{
						br.close();
						break;
					}

					else if ( appl.searchSub==true
										||(appl.searchPar==true && appl.searchSub==true)
										||(appl.searchPar==true && !startCptFile.equals(fileName))
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


	//************************************************************************
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
			if (appl.matchCase)
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
			// NOT matchCase
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
	}

}
