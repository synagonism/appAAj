package pk_XKBManager;

import pk_Util.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
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
import javax.swing.ImageIcon;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * This class ASKS the questions:
 * a) is A a B?
 * b) is A part of B?
 *
 * @modified 2000.03.09
 * @since 2000.03.06
 * @author HoKoNoUmo
 */
public class RetrieveFrame_AskAAj extends JFrame
{
	static final long serialVersionUID = 21L;
	JButton				jbtnAsk1, jbtnAsk2; // GUI buttons (toDo: add help button)
	JButton				jbtnHelp1, jbtnHelp2;
	JButton				jbtnFind1A, jbtnFind1B;
	JButton				jbtnFind2A, jbtnFind2B;
	JButton				jbtnStop1;
	JButton				jbtnStop2;

	JTextField		jtfInput1A;					// TextField used to enter text for concept A in QA.
	JTextField		jtfInput1B;					// TextField used to enter text for concept B in QA.
	JTextField		jtfInput2A;					// TextField used to enter text for concept A in QB.
	JTextField		jtfInput2B;					// TextField used to enter text for concept B in QB.

	JComboBox			jcbMdb;							// ComboBox to hold the Brainual-worldview to search.
	JCheckBox			jchboxCaseSens;			// CheckBox for ConceptA of Question1 case.
	JCheckBox			jchboxMatchName;		// CheckBox for ConceptA of Question1 whole/part name.
	JCheckBox			jchboxMatchWord;		// CheckBox for ConceptA of Question1 whole/part word.

	boolean				caseSens	= false;	 // Flag to indicate if we need to match case.
	boolean				matchWord = false;	 // Flag to indicate if we need to match whole word.
	boolean				matchName = false;	 // Flag to indicate if we need to match whole name.

	String				mmToSearch = null;		// Holds the MMases to search.
	String				cpt1A;								// Holds the cptA of question1.
	String				cpt1B;
	String				cpt2A;
	String				cpt2B;
	String				cpt1AID;							// Holds the ID for cptA of question1.
	String				cpt1BID;
	String				cpt2AID;
	String				cpt2BID;
	String				cptToFind;						// To hold 1A,1B,2A,2B values to know which sbcpt we are searching for.
	Vector	<String>	vectorFound = new Vector<String>(); // Holds the xcpt we find after a search.

	RetrieveFrame_AskAAj			objAskAAj=null;						//I need this lg_object in FindNameThread class.
	AskAAjThread threadAsk = null;					// Search thread.
	boolean				abortThread = false;

	/**
	 * The constructor.
	 * @modified
	 * @since 2000.03.06
	 * @author HoKoNoUmo
	 */
	public RetrieveFrame_AskAAj()
	{
		super("ASK AAj");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {dispose();}
		});
		objAskAAj=this;

		Box box = new Box(BoxLayout.Y_AXIS);

		JPanel panelQ1 = new JPanel();
		panelQ1.setBorder(new TitledBorder(LineBorder.createBlackLineBorder()," question 1 ",TitledBorder.LEFT,TitledBorder.TOP));
		Box boxL1 = new Box(BoxLayout.Y_AXIS);
		JPanel panelQ1Q = new JPanel();
		JLabel lis1 = new JLabel("Is ");
		lis1.setFont (new Font ("serif", Font.BOLD, 14));
		jtfInput1A = new JTextField(12);
		jtfInput1A.setFont (new Font ("serif", Font.BOLD, 14));
		jtfInput1A.setActionCommand("input1A");
		JLabel la = new JLabel(" a ");
		la.setFont (new Font ("serif", Font.BOLD, 14));
		jtfInput1B = new JTextField(12);
		jtfInput1B.setFont (new Font ("serif", Font.BOLD, 14));
		jtfInput1B.setActionCommand("input1B");
		JLabel lq1 = new JLabel(" ? ");
		lq1.setFont (new Font ("serif", Font.BOLD, 14));
		panelQ1Q.add(lis1);
		panelQ1Q.add(jtfInput1A);
		panelQ1Q.add(la);
		panelQ1Q.add(jtfInput1B);
		panelQ1Q.add(lq1);
		JPanel panelEx1 = new JPanel();
		JLabel lex1 = new JLabel("(eg: Is 'Everest' a 'Mountain'?)");
		lex1.setFont (new Font ("serif", Font.PLAIN, 14));
		panelEx1.add(lex1);
		JPanel panelQ1B = new JPanel();
		jbtnFind1A = new JButton("Find Concept 1A");
		jbtnFind1A.setFont (new Font ("serif", Font.PLAIN, 14));
		jbtnFind1A.setActionCommand("find1A");
		panelQ1B.add(jbtnFind1A);
		jbtnStop1 = new JButton("Stop");
		jbtnStop1.setFont (new Font ("serif", Font.PLAIN, 14));
		jbtnStop1.setEnabled(false);
		panelQ1B.add(jbtnStop1);
		jbtnFind1B = new JButton("Find Concept 1B");
		jbtnFind1B.setFont (new Font ("serif", Font.PLAIN, 14));
		jbtnFind1B.setActionCommand("find1B");
		panelQ1B.add(jbtnFind1B);
		boxL1.add(panelQ1Q);
		boxL1.add(panelEx1);
		boxL1.add(panelQ1B);
		Box boxR1 = new Box(BoxLayout.Y_AXIS);
		jbtnAsk1 = new JButton("Ask");
		jbtnAsk1.setFont (new Font ("serif", Font.BOLD, 14));
		jbtnAsk1.setActionCommand("ask1");
		jbtnHelp1 = new JButton("Help");
		jbtnHelp1.setFont (new Font ("serif", Font.BOLD, 14));
		JPanel pAsk1 = new JPanel();
		JPanel pHelp1 = new JPanel();
		pAsk1.add(jbtnAsk1);
		pHelp1.add(jbtnHelp1);
		boxR1.add(pAsk1);
		boxR1.add(pHelp1);
		panelQ1.add(boxL1);
		panelQ1.add(boxR1);

		JPanel panelPar = new JPanel();
		panelPar.setBorder(new TitledBorder(LineBorder.createBlackLineBorder()," SET sbcpt parameters before searching!!! ",TitledBorder.LEFT,TitledBorder.TOP));
		//mms contains vd and all.
		Object[] vd = AAj.trmapSrBrSubWorldview.keySet().toArray();
		Object[] mms = new Object[vd.length+1];
		mms[0]="ALL";
		System.arraycopy(vd, 0, mms, 1, vd.length);
		jcbMdb = new JComboBox();
		for (int i = 0; i < mms.length; i++)
		{
			jcbMdb.addItem((String) mms[i]);
		}
		panelPar.add(jcbMdb);
		JLabel lkb1a = new JLabel("SBSubWorldview",Label.LEFT);
		lkb1a.setFont (new Font ("serif", Font.PLAIN, 14));
		panelPar.add(lkb1a);
		jchboxCaseSens = new JCheckBox ("case sensitive");
		panelPar.add (jchboxCaseSens);
		jchboxMatchWord = new JCheckBox ("whole word");
		panelPar.add (jchboxMatchWord);
		jchboxMatchName = new JCheckBox ("whole name");
		panelPar.add (jchboxMatchName);

		JPanel panelQ2 = new JPanel();
		panelQ2.setBorder(new TitledBorder(LineBorder.createBlackLineBorder()," question 2 ",TitledBorder.LEFT,TitledBorder.TOP));
		Box boxL2 = new Box(BoxLayout.Y_AXIS);
		JPanel panelQ2Q = new JPanel();
		JLabel lis2 = new JLabel("Is ");
		lis2.setFont (new Font ("serif", Font.BOLD, 14));
		jtfInput2A = new JTextField(12);
		jtfInput2A.setFont (new Font ("serif", Font.BOLD, 14));
		jtfInput2A.setActionCommand("input2A");
		JLabel lpart = new JLabel(" part of ");
		lpart.setFont (new Font ("serif", Font.BOLD, 14));
		jtfInput2B = new JTextField(12);
		jtfInput2B.setFont (new Font ("serif", Font.BOLD, 14));
		jtfInput2B.setActionCommand("input2B");
		JLabel lq2 = new JLabel(" ? ");
		lq2.setFont (new Font ("serif", Font.BOLD, 14));
		panelQ2Q.add(lis2);
		panelQ2Q.add(jtfInput2A);
		panelQ2Q.add(lpart);
		panelQ2Q.add(jtfInput2B);
		panelQ2Q.add(lq2);
		JPanel panelEx2 = new JPanel();
		JLabel lex2 = new JLabel("(eg: Is 'Athens' part of 'Greece'?)");
		lex2.setFont (new Font ("serif", Font.PLAIN, 14));
		panelEx2.add(lex2);
		JPanel panelQ2B = new JPanel();
		jbtnFind2A = new JButton("Find Concept 2A");
		jbtnFind2A.setFont (new Font ("serif", Font.PLAIN, 14));
		jbtnFind2A.setActionCommand("find2A");
		panelQ2B.add(jbtnFind2A);
		jbtnStop2 = new JButton("Stop");
		jbtnStop2.setFont (new Font ("serif", Font.PLAIN, 14));
		jbtnStop2.setEnabled(false);
		panelQ2B.add(jbtnStop2);
		jbtnFind2B = new JButton("Find Concept 2B");
		jbtnFind2B.setFont (new Font ("serif", Font.PLAIN, 14));
		jbtnFind2B.setActionCommand("find2B");
		panelQ2B.add(jbtnFind2B);
		boxL2.add(panelQ2Q);
		boxL2.add(panelEx2);
		boxL2.add(panelQ2B);
		Box boxR2 = new Box(BoxLayout.Y_AXIS);
		jbtnAsk2 = new JButton("Ask");
		jbtnAsk2.setFont (new Font ("serif", Font.BOLD, 14));
		jbtnHelp2 = new JButton("Help");
		jbtnHelp2.setFont (new Font ("serif", Font.BOLD, 14));
		JPanel pAsk2 = new JPanel();
		JPanel pHelp2 = new JPanel();
		pAsk2.add(jbtnAsk2);
		pHelp2.add(jbtnHelp2);
		boxR2.add(pAsk2);
		boxR2.add(pHelp2);
		panelQ2.add(boxL2);
		panelQ2.add(boxR2);

		box.add(panelQ1);
		box.add(panelPar);
		box.add(panelQ2);

		// and get the GUI components onto our content pane
		getContentPane().add(box, BorderLayout.CENTER);

		jbtnAsk1.addActionListener(new AskAction());
		jbtnAsk2.addActionListener(new AskAction());

		jtfInput1A.addActionListener(new FindAction());
		jtfInput1B.addActionListener(new FindAction());
		jtfInput2A.addActionListener(new FindAction());
		jtfInput2B.addActionListener(new FindAction());
		jbtnFind1A.addActionListener(new FindAction());
		jbtnFind1B.addActionListener(new FindAction());
		jbtnFind2A.addActionListener(new FindAction());
		jbtnFind2B.addActionListener(new FindAction());

		jbtnStop1.addActionListener(new StopAction());
		jbtnStop2.addActionListener(new StopAction());

		jbtnHelp1.addActionListener(new HelpAction());
		jbtnHelp2.addActionListener(new HelpAction());

		jchboxCaseSens.addActionListener(new CaseAction());
		jchboxMatchName.addActionListener(new MatchNameAction());
		jchboxMatchWord.addActionListener(new MatchWordAction());

		setIconImage( Util.getImage("resources/AAj_icon.gif") );
		setSize(new Dimension(609,374));
		setLocation(172,76);
		setVisible(true);
	}//end RetrieveFrame_AskAAj

	/**
	 * @modified
	 * @since 2000.03.08
	 * @author HoKoNoUmo
	 */
	void addToVectorFound (String xcpt_sFileName, String xcpt_sName)
	{
		String cptFID = Util.getXCpt_sFormalID(xcpt_sFileName);
		vectorFound.addElement(xcpt_sName +" (" +cptFID	 +")");
	}

	/**
	 * @modified
	 * @since 2000.03.08
	 * @author HoKoNoUmo
	 */
	void enableButtons()
	{
		if (cptToFind.equals("1A"))
		{
			jbtnFind1A.setEnabled(true);
			jbtnStop1.setEnabled(false);
		}
		else if (cptToFind.equals("1B"))
		{
			jbtnFind1B.setEnabled(true);
			jbtnStop1.setEnabled(false);
		}
		else if (cptToFind.equals("2A"))
		{
			jbtnFind2A.setEnabled(true);
			jbtnStop2.setEnabled(false);
		}
		else if (cptToFind.equals("2B"))
		{
			jbtnFind2B.setEnabled(true);
			jbtnStop2.setEnabled(false);
		}
	}

	/**
	 * @modified
	 * @since 2000.03.08
	 * @author HoKoNoUmo
	 */
	void disableButtons()
	{
		if (cptToFind.equals("1A"))
		{
			jbtnFind1A.setEnabled(false);
			jbtnStop1.setEnabled(true);
		}
		else if (cptToFind.equals("1B"))
		{
			jbtnFind1B.setEnabled(false);
			jbtnStop1.setEnabled(true);
		}
		else if (cptToFind.equals("2A"))
		{
			jbtnFind2A.setEnabled(false);
			jbtnStop2.setEnabled(true);
		}
		else if (cptToFind.equals("2B"))
		{
			jbtnFind2B.setEnabled(false);
			jbtnStop2.setEnabled(true);
		}
	}

	//*********************************************************************
	/**
	 * @modified 2000.03.09
	 * @since 2000.03.07
	 * @author HoKoNoUmo
	 */
	class AskAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			//We can do the question without searching IF we know the ids.
			cpt1A=jtfInput1A.getText();
			if(cpt1A.indexOf("(")!=-1) cpt1AID=cpt1A.substring(cpt1A.indexOf("(")+1,cpt1A.indexOf(")"));
			cpt1B=jtfInput1B.getText();
			if(cpt1B.indexOf("(")!=-1) cpt1BID=cpt1B.substring(cpt1B.indexOf("(")+1,cpt1B.indexOf(")"));
			cpt2A=jtfInput2A.getText();
			if(cpt2A.indexOf("(")!=-1) cpt2AID=cpt2A.substring(cpt2A.indexOf("(")+1,cpt2A.indexOf(")"));
			cpt2B=jtfInput2B.getText();
			if(cpt2B.indexOf("(")!=-1) cpt2BID=cpt2B.substring(cpt2B.indexOf("(")+1,cpt2B.indexOf(")"));

			if (e.getActionCommand().equals("ask1"))
			{
				if(cpt1AID!=null || cpt1BID!=null)
				{
					if (cpt1AID.length()>1 || cpt1BID.length()>1)
					{
						boolean bisa=Util.isSpecificOf(cpt1AID, cpt1BID);
						if (bisa)
							JOptionPane.showMessageDialog(null, ">>YES<< " +cpt1A +" is a " +cpt1B);
						else
							JOptionPane.showMessageDialog(null, ">>NO<< " +cpt1A +" is NOT a " +cpt1B);
					}
				}
				else JOptionPane.showMessageDialog(null, "There is NO cpts to do the question");
			}

			else //is A partOf B?
			{
				if(cpt2AID!=null || cpt2BID!=null)
				{
					if (cpt2AID.length()>1 || cpt2BID.length()>1)
					{
						boolean bispo=Util.isPartOf(cpt2AID, cpt2BID);
						if (bispo)
							JOptionPane.showMessageDialog(null, ">>YES<< " +cpt2A +" is part of " +cpt2B);
						else
							JOptionPane.showMessageDialog(null, ">>NO<< " +cpt2A +" is NOT part of " +cpt2B);
					}
				}
				else JOptionPane.showMessageDialog(null, "There is NO cpts to do the question");
			}
		}
	}

	//*********************************************************************
	/**
	 * @modified 2000.03.09
	 * @since 2000.03.07
	 * @author HoKoNoUmo
	 */
	class CaseAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			if (jchboxCaseSens.isSelected()==true)
				caseSens = true;
			else
				caseSens = false;
		}
	}

	//*********************************************************************
	/**
	 * @modified 2000.03.10
	 * @since 2000.03.09
	 * @author HoKoNoUmo
	 */
	class FindAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			mmToSearch=(String)jcbMdb.getSelectedItem();
			if (e.getActionCommand().equals("find1A") || e.getActionCommand().equals("input1A"))
			{
				cptToFind="1A";
				cpt1A=jtfInput1A.getText();
				cpt1A.trim();
				if (cpt1A.length()>1)
				{
					disableButtons();
					abortThread=false; //we can make a search.
					threadAsk = new AskAAjThread(objAskAAj, cpt1A);
					threadAsk.start();
				}
			}
			else if (e.getActionCommand().equals("find1B") || e.getActionCommand().equals("input1B"))
			{
				cptToFind="1B";
				cpt1B=jtfInput1B.getText();
				cpt1B.trim();
				if (cpt1B.length()>1)
				{
					disableButtons();
					abortThread=false; //we can make a search.
					threadAsk = new AskAAjThread(objAskAAj, cpt1B);
					threadAsk.start();
				}
			}
			else if (e.getActionCommand().equals("find2A") || e.getActionCommand().equals("input2A"))
			{
				cptToFind="2A";
				cpt2A=jtfInput2A.getText();
				cpt2A.trim();
				if (cpt2A.length()>1)
				{
					disableButtons();
					abortThread=false; //we can make a search.
					threadAsk = new AskAAjThread(objAskAAj, cpt2A);
					threadAsk.start();
				}
			}
			else if (e.getActionCommand().equals("find2B") || e.getActionCommand().equals("input2B"))
			{
				cptToFind="2B";
				cpt2B=jtfInput2B.getText();
				cpt2B.trim();
				if (cpt2B.length()>1)
				{
					disableButtons();
					abortThread=false; //we can make a search.
					threadAsk = new AskAAjThread(objAskAAj, cpt2B);
					threadAsk.start();
				}
			}
		}
	}

	//*********************************************************************
	/**
	 * @modified 2000.03.09
	 * @since 2000.03.07
	 * @author HoKoNoUmo
	 */
	class MatchNameAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent ae)
		{
			if (jchboxMatchName.isSelected() == true)
				matchName = true;
			else
				matchName = false;
		}
	}

	//*********************************************************************
	/**
	 * @modified 2000.03.14
	 * @since 2000.03.08
	 * @author HoKoNoUmo
	 */
	class HelpAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent ae)
		{
			try {
				AAj.display("it-49#1");
			}
			catch (Exception e){};
			AAj.frameAAj.toFront();
			AAj.frameAAj.requestFocus();
		}
	}

	//*********************************************************************
	/**
	 * @modified
	 * @since 2000.03.07
	 * @author HoKoNoUmo
	 */
	class MatchWordAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent ae)
		{
			if (jchboxMatchWord.isSelected() == true)
				matchWord = true;
			else
				matchWord = false;
		}
	}

	//*********************************************************************
	/**
	 * @modified
	 * @since 2000.03.08
	 * @author HoKoNoUmo
	 */
	class StopAction extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			// Abort button pressed - stop the thread
			if (threadAsk != null) abortThread=true;
			threadAsk = null;

			// Enable buttons for another search
			enableButtons();
		}
	}
}

//*********************************************************************
//*********************************************************************
/**
 * @modified
 * @since 2000.03.08
 * @author HoKoNoUmo
 */
class AskAAjThread extends Thread
{
	RetrieveFrame_AskAAj		appl;													//we need this to use its variables.
	String			startCptFile=null;						// The name of file of the start concept.
	String			textToFind=null;							// String to find
	Vector	<String>		filesToSearch=new Vector<String>();
	boolean			matched=false;								//flag if we found a hit on a concept.

	/**
	 * Constructor
	 */
	AskAAjThread (RetrieveFrame_AskAAj application, String text)
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
				String vd =(String) i.next(); //key = the vd.
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
			if (appl.cptToFind.equals("1A"))
			{
				cptsArray = appl.vectorFound.toArray();
				selectedValue = JOptionPane.showInputDialog(null,
								"Choose CONCEPT-1A",												//message
								"Choose One",															//title
								JOptionPane.INFORMATION_MESSAGE,					//message type
								null,																			//icon
								cptsArray,																//values to select
								cptsArray[0]);														//initial selection
				if (selectedValue!=null)
				{
					appl.jtfInput1A.setText((String)selectedValue);
					appl.cpt1A = (String)selectedValue;
					appl.cpt1AID=appl.cpt1A.substring(appl.cpt1A.indexOf("(")+1,appl.cpt1A.indexOf(")"));
				}
				else JOptionPane.showMessageDialog(null, "No concept-1A selected");
			}
			else if (appl.cptToFind.equals("1B"))
			{
				cptsArray = appl.vectorFound.toArray();
				selectedValue = JOptionPane.showInputDialog(null,
								"Choose CONCEPT-1B",												//message
								"Choose One",															//title
								JOptionPane.INFORMATION_MESSAGE,					//message type
								null,																			//icon
								cptsArray,																//values to select
								cptsArray[0]);														//initial selection
				if (selectedValue!=null)
				{
					appl.jtfInput1B.setText((String)selectedValue);
					appl.cpt1B = (String)selectedValue;
					appl.cpt1BID=appl.cpt1B.substring(appl.cpt1B.indexOf("(")+1,appl.cpt1B.indexOf(")"));
				}
				else JOptionPane.showMessageDialog(null, "No concept-1B selected");
			}

			else if (appl.cptToFind.equals("2A"))
			{
				cptsArray = appl.vectorFound.toArray();
				selectedValue = JOptionPane.showInputDialog(null,
								"Choose CONCEPT-2A",												//message
								"Choose One",															//title
								JOptionPane.INFORMATION_MESSAGE,					//message type
								null,																			//icon
								cptsArray,																//values to select
								cptsArray[0]);														//initial selection
				if (selectedValue!=null)
				{
					appl.jtfInput2A.setText((String)selectedValue);
					appl.cpt2A = (String)selectedValue;
					appl.cpt2AID=appl.cpt2A.substring(appl.cpt2A.indexOf("(")+1,appl.cpt2A.indexOf(")"));
				}
				else JOptionPane.showMessageDialog(null, "No concept-2A selected");
			}

			else if (appl.cptToFind.equals("2B"))
			{
				cptsArray = appl.vectorFound.toArray();
				selectedValue = JOptionPane.showInputDialog(null,
								"Choose CONCEPT-2B",												//message
								"Choose One",															//title
								JOptionPane.INFORMATION_MESSAGE,					//message type
								null,																			//icon
								cptsArray,																//values to select
								cptsArray[0]);														//initial selection
				if (selectedValue!=null)
				{
					appl.jtfInput2B.setText((String)selectedValue);
					appl.cpt2B = (String)selectedValue;
					appl.cpt2BID=appl.cpt2B.substring(appl.cpt2B.indexOf("(")+1,appl.cpt2B.indexOf(")"));
				}
				else JOptionPane.showMessageDialog(null, "No concept-2B selected");
			}

		}
		else JOptionPane.showMessageDialog(null, "NO concept found, give another try");
	}

	/**
	 * Reads ONE lng/term.index.xml-file and searches the &lt;name&gt; xml elements.
	 * @param kbFileName is a fullpath file.
	 * @modified 1999.03.29
	 * @since 1999.03.07
	 * @author HoKoNoUmo
	 */
	final void searchFile (String kbFullFileName)
	{
		String				line;								// Raw line read in.
		String				xcpt_sFileName=null;		// the fileName of a concept.
		String					xcpt_sName=null;
		String					cptSyn=null;
		BufferedReader br =null;

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
			System.out.println("AskAAjThread.searchFile: fnfe: " +kbFullFileName);
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "AskAAjThread.searchFile(): IOException: on " +kbFullFileName);
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