package pk_XKBManager;

import pk_Util.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * This class searches for a concept by Formal-Name.
 * Searches the lng/term.index.xml files.
 *
 * @modified
 * @since 2000.03.05
 * @author HoKoNoUmo
 */
public class FindByFormalName extends JFrame
{
	static final long serialVersionUID = 21L;
	String searchingFRN=null;
	JButton jbtnOK, jbtnCancel;					// GUI buttons (toDo: add help button)
	public JList			jlistMdb;						// Holds the Knowledge Bases.
	DefaultListModel	dlistModel;
	Object[] xcpt_sDir = null;								// Holds the directories to search.
	boolean found = false;

	/**
	 * The constructor.
	 * @param formalName is the Formal-Name we are searching for.
	 * @modified
	 * @since 2000.03.04
	 * @author HoKoNoUmo
	 */
	public FindByFormalName(String formalName)
	{
		super("Find FormalName (" +formalName +")");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {dispose();}
		});
		searchingFRN=formalName;

		JPanel panelCenter = new JPanel(new BorderLayout());
		//ASK what vd to search.
		Object[] vd = AAj.trmapSrBrSubWorldview.keySet().toArray();
		Object[] mms = new Object[vd.length+1];
		mms[0]="ALL";
		System.arraycopy(vd, 0, mms, 1, vd.length); //mms contains and all.
		jlistMdb = new JList(mms);
		jlistMdb.setFixedCellHeight(15);
		jlistMdb.setSelectedIndex(0);
		JScrollPane sp = new JScrollPane();
		sp.setPreferredSize(new Dimension(109, 109));
		JViewport vp2 = sp.getViewport();
		vp2.add(jlistMdb);
		panelCenter.setBorder(new TitledBorder(LineBorder.createBlackLineBorder()," choose some SrWorldviews ",TitledBorder.CENTER,TitledBorder.TOP));
		panelCenter.add("Center", sp);

		JPanel panelSouth = new JPanel();
		jbtnOK = new JButton (" OK ");
		jbtnOK.setFont (new Font ("sansserif", Font.BOLD, 14));
		panelSouth.add (jbtnOK);
		jbtnCancel = new JButton (" Cancel ");
		jbtnCancel.setFont (new Font ("sansserif", Font.BOLD, 14));
		panelSouth.add (jbtnCancel);

		jbtnCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) {dispose();}
		});

		jbtnOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				xcpt_sDir = jlistMdb.getSelectedValues();
				if (xcpt_sDir[0].equals("ALL"))
				{
					//it is better to search the hknu.symb/it vd and then the others.
					//here we can put priorities.
					for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
					{
						String knb =(String) i.next(); //key = the vd.
						String kbFileName = knb +"'termTxCpt_sAll.xml";
						String mmDir = Util.getXCpt_sFullDir(knb +"-1");
						String kbFullFileName=mmDir +File.separator +kbFileName;
						File fl = new File(kbFullFileName);
						if (fl.exists())
						{
							searchFile(kbFullFileName);
						}
					}
				}

				else //some mms are selected.
				{
					for (int i=0; i<xcpt_sDir.length; i++)
					{
						String knb = (String)xcpt_sDir[i];
						String kbFileName = knb +"'termTxCpt_sAll.xml";
						String mmDir = Util.getXCpt_sFullDir(knb +"-1");
						String kbFullFileName=mmDir +File.separator +kbFileName;
						File fl = new File(kbFullFileName);
						if (fl.exists())
						{
							searchFile(kbFullFileName);
						}
					}
				}
				if (!found) JOptionPane.showMessageDialog(null, "There is NO concept with this FormalName");
				dispose();
			}
		});
		jbtnOK.setSelected(true);

		getContentPane().add(panelCenter, BorderLayout.CENTER);
		getContentPane().add(panelSouth, BorderLayout.SOUTH);
		setIconImage( Util.getImage("resources/AAj_icon.gif") );
		setSize(new Dimension(319,199));
		setLocation(222,126);
		setVisible(true);
	} //end FindByFormalName

	/**
	 * Read ONE lng/term.index.xml file and searches the &lt;name&gt; xml elements.
	 * @param kbFileName is a fullpath file.
	 * @modified
	 * @since 2000.03.05
	 * @author HoKoNoUmo
	 */
	final void searchFile (String kbFullFileName)
	{
		String					line;								// Raw line read in.
		String					xcpt_sFileName=null;		// the fileName of a concept.
		String					xcpt_sFrmlName=null;
		BufferedReader	br =null;

		try {
			br = new BufferedReader(new FileReader(kbFullFileName));

			while ((line=br.readLine())!=null)
			{
				if (line.startsWith("<NAME"))
				{
					xcpt_sFileName=line.substring(line.indexOf("FN=")+4, line.indexOf("\"",line.indexOf("FN=")+5));
					String cptFRN=xcpt_sFileName.substring(0, xcpt_sFileName.indexOf("@"));
					xcpt_sFrmlName=cptFRN.toLowerCase();
					if (searchingFRN.equals(xcpt_sFrmlName))
					{
						AAj.display(xcpt_sFileName);
						found=true;
						break;
					}
				}
			}
			br.close();
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("FindByFormalName.searchFile: fnfe: " +kbFullFileName);
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "FindByFormalName.searchFile(): IOException: on " +kbFullFileName);
		}
	}//END of searchFile

}//END of class FindByFormalName.


