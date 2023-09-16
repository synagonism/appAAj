package pk_XKBEditor;

import pk_XKBManager.AAj;
import pk_Util.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 * It generalizes a SELECTED notInherited prt.
 *
 * Have a combobox to select the generic, whose prt will be the new sbcpt.
 * It creates a new file-xcpt.
 * In new, it inserts the generic as whole.
 * In new, it inserts the selected as spe.
 * In generic, it inserts the new as prt.
 * In selected, it inserts the new as gen IF the selected is a file-xcpt.
 * In current, it replaces the gen.
 * In gen's subs (except current), it inserts the new as inherited.
 *
 * @modified 2001.03.06
 * @since 2000.04.02
 * @author HoKoNoUmo
 */
public class FrameGeneralizePart extends JFrame
{
	static final long serialVersionUID = 21L;
	String sbcpt=null;				//The current sbcpt.
	String xcpt_sFormalID=null;
	String prt=null;				//The attribute we want to generalize.
	String attID=null;
	String kspNew=null;			//The generalized attribute.
	String kspNewID=null;
	String gen=null;				//The generic that holds the generalized attribute.
	String genID=null;

	/**
	 * @param acptID is the ID of the xcpt we will override the attribute.
	 * @param aattID
	 *	the ID of the prt we will override.	 This is also the gen
	 *	of the new.
	 * @modified
	 * @since 2000.03.30
	 * @author HoKoNoUmo
	 */
	public FrameGeneralizePart(String acptID, String aattID)
	{
		xcpt_sFormalID=acptID;
		sbcpt=Util.getXCpt_sLastFileName(acptID);
		attID=aattID;
		prt=Util.getXCpt_sLastFileName(aattID);
		setTitle("Generalize the attribute (" +prt +") of concept (" +sbcpt +")");

		//gens row.
		JLabel labelGen = new JLabel("Generic:");
		final Extract ex = new Extract();
		ex.extractGenerics(sbcpt);
		Vector vGen = ex.vectorGen;
		Object[] gens = vGen.toArray();
		final JComboBox cb2 = new JComboBox(gens);
		JLabel labelGenComment=new JLabel("	 (The generic that will hold the NEW attribute)");
	 //formal-name row.
		JLabel labelName = new JLabel("Formal-Name:");
		final JTextField fieldName = new JTextField(19);
		fieldName.setFont(new Font("serif", Font.BOLD, 14));
		JLabel labelMdb = new JLabel("		SrWorldview:");
		Object[] vd = AAj.trmapSrBrSubWorldview.keySet().toArray();
		final JComboBox cbox = new JComboBox(vd);
		cbox.setSelectedItem((Object)AAj.xcpt_sFormalID.substring(0, AAj.xcpt_sFormalID.indexOf("-")));
		//termTxCpt_sAll row.
		JLabel labelSyn = new JLabel("Synterms:");
		final JTextField fieldSyn = new JTextField(37);
		//termTxCpt_sAll comment row.
		JLabel labelSyn2 = new JLabel("(Write termTxCpt_sAll alphabetically and separate them by commas)");
		//definition row.
		JLabel labelDef = new JLabel("Definition:");
		final JTextArea areaDef = new JTextArea();
		JScrollPane scroller = new JScrollPane(){
			static final long serialVersionUID = 21L;
			public Dimension getPreferredSize() {
				return new Dimension(399,126);
			}
		};
		JViewport port = scroller.getViewport();
		areaDef.setFont(new Font("serif", Font.PLAIN, 18));
		areaDef.setLineWrap(true);
		areaDef.setWrapStyleWord(true);
		port.add(areaDef);

		//Create the Layouts.
		Box box = new Box(BoxLayout.Y_AXIS);
		JPanel paneGen = new JPanel();
		paneGen.add(labelGen);
		paneGen.add(cb2);
		paneGen.add(labelGenComment);
		JPanel paneName = new JPanel();
		paneName.add(labelName);
		paneName.add(fieldName);
		paneName.add(labelMdb);
		paneName.add(cbox);
		JPanel paneSyn = new JPanel();
		paneSyn.add(labelSyn);
		paneSyn.add(fieldSyn);
		JPanel paneSyn2 = new JPanel();
		paneSyn2.add(labelSyn2);
		JPanel paneDef = new JPanel();
		paneDef.add(labelDef);
		paneDef.add(scroller);

		box.add(paneGen);
		box.add(paneName);
		box.add(paneSyn);
		box.add(paneSyn2);
		box.add(paneDef);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		final JButton ok = new JButton("	OK	");
		buttonPane.add(ok);
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				gen=(String)cb2.getSelectedItem();
				gen=Util.getXCpt_sLastFileName(gen);
				genID=Util.getXCpt_sFormalID(gen);

				String sFormalName=fieldName.getText();
				String sMdb=(String) cbox.getSelectedItem();
				String sSyns=fieldSyn.getText();
				String sDef=areaDef.getText();

				if (sFormalName!=null && sDef!=null)
				{
					int lastXSWVNumber = Util.getNextFormalNumber(sMdb);
					Insert ins = new Insert();

					kspNew = sFormalName +"@" +sMdb +"-" +lastXSWVNumber +".xml";
					kspNewID=Util.getXCpt_sFormalID(kspNew);

					//create the new file.
					ins.old_createXCpt_FileWithDef_Txt(kspNew,												//fileName
//																		null,
//																		null,
//																sSyns,										//termTxCpt_sAll
																sDef);										//definition

					//in new, insert the generic as whole.
					ins.insertAttWholeInFile(kspNew,
																gen,											//FILENAME
																null,										//Generic
																AAj.kb_sAuthor);						//Author

					//in new, insert the attribute as spe.
					ins.insertAttSpecificInFile(kspNew,
																prt,											//FILENAME
																kspNew,										//Generic
																AAj.kb_sAuthor);						//Author

					//in generic, insert the new as attribute.
					ins.insertAttPartInFile(gen,
																kspNew,										//FILENAME
																"no",											//Generic
																AAj.kb_sAuthor);						//Author

					//in prt, insert the new as gen >>IF<< it is a file-xcpt.
					if (prt.indexOf("#")==-1)
					{
						ins.insertAttGenericInFile(prt,
																kspNew,										//FILENAME
																"no",											//Generic
																AAj.kb_sAuthor);						//Author
					}

					//in current, replace the gen xml-prt of prt.
					Vector<String> vectorReplacing = new Vector<String>();
					vectorReplacing.add(attID +"," +kspNewID);
					ins.replaceGeneric(sbcpt, vectorReplacing);

					//insert in gen's subs (except sbcpt), the new as inherited-notOverridden.
					Vector<String> filesToSearch = new Vector<String>();
					filesToSearch.add(gen);
					while(!filesToSearch.isEmpty())
					{
						String currentFileName=null;
						try {
							currentFileName = filesToSearch.firstElement();
						}
						catch (Exception e)
						{
							JOptionPane.showMessageDialog(null, "FrameGeneralizeAtt: Problem in " + currentFileName);
							continue;				// Try next file
						}
						ex.extractSpecifics(currentFileName);
						Vector subs = ex.vectorSpeID;
						for (int i=0; i<subs.size(); i++)
						{
							String fName=(String)subs.get(i);
							if (!fName.equals(xcpt_sFormalID))
							{
								if (fName.indexOf("#")==-1)//we insert only to file-xcpt
								{
									fName=Util.getXCpt_sLastFileName(fName);
									filesToSearch.add(fName);
									//insert the new
									ins.insertAttPartInFile(fName,
																kspNew,										//FILENAME
																kspNew,										//Generic
																AAj.kb_sAuthor);						//Author
								}
							}
						}
						filesToSearch.remove(0);
					}

					//display the new file.
					AAj.display(kspNew);
					if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(kspNew);
					dispose();
				}
			}
		});

		final JButton cancel = new JButton("Cancel");
		buttonPane.add(cancel);
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dispose();
			}
		});
		JButton jbHelp = new JButton(" help ");
		buttonPane.add (jbHelp);
		jbHelp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				AAj.display("it-48#6");
				AAj.frameAAj.toFront();
				AAj.frameAAj.requestFocus();
			}
		});

		//Put the box/buttons in another panel.
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(box, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.SOUTH);

		getContentPane().add(contentPane, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter()
		{
					public void windowClosing(WindowEvent e) {
							dispose();
					}
		});
		pack();
		Util.setFrameIcon(this);
		setLocation(126,126);
		setVisible(true);
	}
}