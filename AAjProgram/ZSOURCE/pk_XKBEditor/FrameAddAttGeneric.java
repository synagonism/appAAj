package pk_XKBEditor;

import pk_XKBManager.AAj;
import pk_Util.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * It asks for the ID of the existing generic.
 * It inserts in current, the existing as generic.
 * It inserts in existing, the current as spe IF already there is not.
 * It inserts in current, the existing's pars IF already they are not.
 * It inserts in current's subs, the existing's pars IF already they are not.
 * IF existing has wholes THEN it inserts it in current and its subs if it is not.
 * @modified 2001.03.06
 * @since 2000.03.27
 * @author HoKoNoUmo
 */
public class FrameAddAttGeneric extends JFrame
{
	static final long serialVersionUID = 21L;
	String sbcpt=null;
	String xcpt_sFormalID=null;
	String cptGen=null; //the gen fileName of current.

	public FrameAddAttGeneric(String acpt)
	{
		super("Add an EXISTING concept, as GENERIC to (" +acpt +")");
		sbcpt=Util.getXCpt_sLastFileName(acpt);
		xcpt_sFormalID=Util.getXCpt_sFormalID(sbcpt);

		//Create the labels and tFields
		JLabel labelName = new JLabel("ID of existing-sbcpt:");
		final JTextField fieldName = new JTextField(26);
		fieldName.setFont(new Font("serif", Font.BOLD, 16));
		JLabel labelEx = new JLabel("(example: it-26)");

		//Create the Layouts.
		JPanel paneLabel = new JPanel();
		paneLabel.add(labelName);
		paneLabel.add(fieldName);
		paneLabel.add(labelEx);

		JPanel paneButton = new JPanel();
		paneButton.setLayout(new FlowLayout());
		final JButton ok = new JButton("	OK	");
		paneButton.add(ok);
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String existingID=fieldName.getText();
				existingID=existingID.toLowerCase();
				if (existingID!=null)
				{
					String existingFileName = Util.getXCpt_sLastFileName(existingID);

					//find the existing's gen.
					Extract ex = new Extract();
					ex.extractGenerics(existingID);
					String existGen=ex.stringGen;
					//find the existing's wholes.
					ex.extractWholes(existingID);
					Vector vexistWho = ex.vectorWhoID;

					Insert ins = new Insert();
					//Insert in current, the existing as GENERIC.
					ins.insertAttGenericInFile(sbcpt,
																		existingFileName,					//FILENAME
																		existGen,									//Generic
																		AAj.kb_sAuthor);						//Author

					//Insert in existing, the current as SPECIFIC IF already there is not.
					ex.extractSpecifics(existingID);
					Vector vSub=ex.vectorSpeID;
					if (!vSub.contains(xcpt_sFormalID))
					{
						//find current's gen.
						ex.extractGenerics(xcpt_sFormalID);
						cptGen=ex.stringGen;
						ins.insertAttSpecificInFile(existingFileName,
																		sbcpt,											//FILENAME
																		cptGen,										//Generic
																		AAj.kb_sAuthor);						//Author
					}

					//insert in current, the existing's att IF already they are not.
					ex.extractParts(existingID);
					Vector vexistAtt = ex.vectorParID;
					ex.extractParts(xcpt_sFormalID);
					Vector vcptAtt = ex.vectorParID;
					for (int i=0; i<vexistAtt.size(); i++)
					{
						String attExist=(String)vexistAtt.get(i);
						String attExistID=Util.getXCpt_sLastFileName(attExist);
						if (!vcptAtt.contains(attExist))
						{
							ins.insertAttPartInFile(sbcpt,
																		attExistID,								//FILENAME
																		attExistID,								//GENERIC
																		AAj.kb_sAuthor);						//AUTHOR
						}
					}

					//insert in current, the existing's wholes IF they are not.
					ex.extractWholes(xcpt_sFormalID);
					Vector vcptWho = ex.vectorWhoID;
					for (int i=0; i<vexistWho.size(); i++)
					{
						String existWho=(String)vexistWho.get(i);
						String existWhoID=Util.getXCpt_sLastFileName(existWho);
						if (!vcptWho.contains(existWho))
						{
							ins.insertAttWholeInFile(sbcpt,
																		existWhoID,								//FILENAME
																		null,										//GENERIC
																		AAj.kb_sAuthor);						//AUTHOR
						}
					}

					//It inserts in current's subs, the existing's ATTS IF already they are not.
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
							JOptionPane.showMessageDialog(null, "FrameAddGen: Problem in " + currentFileName);
							continue;				// Try next file
						}
						ex.extractSpecifics(currentFileName);
						Vector subs = ex.vectorSpeID;
						for (int i=0; i<subs.size(); i++)
						{
							String subID=(String)subs.get(i);
							if (subID.indexOf("#")==-1)//we insert only to file-xcpt
							{
								String subFileName=Util.getXCpt_sLastFileName(subID);
								filesToSearch.add(subFileName);

								//insert in spe, the existing's pars if already they are not.
								for (int j=0; j<vexistAtt.size(); j++)
								{
									String attExist=(String)vexistAtt.get(j);
									String attExistID=Util.getXCpt_sLastFileName(attExist);
									ex.extractParts(subID);
									Vector subAtt=ex.vectorParID;
									if (!subAtt.contains(attExist))
									{
										ins.insertAttPartInFile(subFileName,
																					attExistID,								//FILENAME
																					attExistID,								//GENERIC
																					AAj.kb_sAuthor);						//AUTHOR
									}
								}

								//insert in spe, the existing's wholes if they are not.
								for (int j=0; j<vexistWho.size(); j++)
								{
									String existWho=(String)vexistWho.get(j);
									String existWhoID=Util.getXCpt_sLastFileName(existWho);
									if (!vcptWho.contains(existWho))
									{
										ins.insertAttWholeInFile(subFileName,
																					existWhoID,								//FILENAME
																					null,										//GENERIC
																					AAj.kb_sAuthor);						//AUTHOR
									}
								}
							}
						}
						filesToSearch.remove(0);
					}

					//display the new file in frame-editor
					AAj.display(AAj.xcpt_sFileName);
					if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(AAj.xcpt_sFileName);
					dispose();
				}
			}
		});
		final JButton cancel = new JButton("Cancel");
		paneButton.add(cancel);
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dispose();
			}
		});
		JButton jbHelp = new JButton("Help");
		paneButton.add (jbHelp);
		jbHelp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				AAj.display("it-48#3");
				AAj.frameAAj.toFront();
				AAj.frameAAj.requestFocus();
			}
		});

		//Put the panels in another panel, labels on left, text fields on right
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(paneLabel, BorderLayout.CENTER);
		contentPane.add(paneButton, BorderLayout.SOUTH);

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