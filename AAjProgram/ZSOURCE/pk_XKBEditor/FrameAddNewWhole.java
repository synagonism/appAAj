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
 * The program creates the new xml-file.
 * Then it inserts in current, the new as whole.
 * add inserts in new, the current as att.
 *
 * Finally, it adds in all current's subs, the new as whole. 2000.03.18
 *
 * @modified 2000.04.19
 * @since 2000.02.15
 * @author HoKoNoUmo
 */
public class FrameAddNewWhole extends JFrame
{
	static final long serialVersionUID = 21L;
	String	sbcpt=null;

	public FrameAddNewWhole(String acpt)
	{
		super("Add a NEW concept, as WHOLE to (" +acpt +")");
		sbcpt=Util.getXCpt_sLastFileName(acpt);

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
		final JTextField fieldSyn = new JTextField(36);
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
		JPanel paneName = new JPanel();
		paneName.setLayout(new FlowLayout());
		paneName.add(labelName);
		paneName.add(fieldName);
		paneName.add(labelMdb);
		paneName.add(cbox);
		JPanel paneSyn = new JPanel();
		paneSyn.setLayout(new FlowLayout());
		paneSyn.add(labelSyn);
		paneSyn.add(fieldSyn);
		JPanel paneSyn2 = new JPanel();
		paneSyn2.setLayout(new FlowLayout());
		paneSyn2.add(labelSyn2);
		JPanel paneDef = new JPanel();
		paneDef.setLayout(new FlowLayout());
		paneDef.add(labelDef);
		paneDef.add(scroller);

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
				String sFormalName=fieldName.getText();
				String sMdb=(String) cbox.getSelectedItem();
				String newSyns=fieldSyn.getText();
				String newDef=areaDef.getText();

				if (sFormalName!=null && newDef!=null)
				{
					int lastXSWVNumber = Util.getNextFormalNumber(sMdb);
					Insert ins = new Insert();
					String newFileName=null;

					newFileName = sFormalName +"@" +sMdb +"-" +lastXSWVNumber +".xml";
					//create the new file.
					ins.old_createXCpt_FileWithDef_Txt(newFileName,										//fileName
//																		null,
//																		null,
//																newSyns,										//termTxCpt_sAll
																newDef);										//definition
					//inserts the new as whole to current.
					ins.insertAttWholeInFile(sbcpt,
																newFileName,								//FILENAME
																"no",												//Generic
																AAj.kb_sAuthor);							//Author
					//insert the current as attribute to new.
					ins.insertAttPartInFile(newFileName,
																sbcpt,												//FILENAME
																"no",												//Generic. The new has no gen-sbcpt.
																AAj.kb_sAuthor);							//Author

					//insert new as whole to all subs of current.
					Vector<String> filesToSearch = new Vector<String>();
					filesToSearch.add(sbcpt);
					while(!filesToSearch.isEmpty())
					{
						String currentFileName=null;
						try {
							currentFileName = filesToSearch.firstElement();
						}
						catch (Exception e)
						{
							JOptionPane.showMessageDialog(null, "FrameAddNewWho: Problem in " + currentFileName);
							continue;				// Try next file
						}
						Extract ex = new Extract();
						ex.extractSpecifics(currentFileName);
						Vector subs = ex.vectorSpeID;
						for (int i=0; i<subs.size(); i++)
						{
							String fName=(String)subs.get(i);
							if (fName.indexOf("#")==-1)//we insert only to file-xcpt
							{
								fName=Util.getXCpt_sLastFileName(fName);
								filesToSearch.add(fName);
								//insert the new
								ins.insertAttWholeInFile(fName,
																			newFileName,						//FILENAME
																			"no",										//Generic
																			AAj.kb_sAuthor);					//Author
							}
						}
						filesToSearch.remove(0);
					}

					//display the new file in frame-editor
					AAj.display(newFileName);
					if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(newFileName);
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
				AAj.display("it-48#2");
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
			static final long serialVersionUID = 21L;
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