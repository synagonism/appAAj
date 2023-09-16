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
 * It asks for the ID of the existing whole.
 * It inserts in current, the existing as whole.
 * It inserts in existing, the current as att IF already there is not.
 * It inserts in current's subs, the existing as whole IF already there is not.
 * @modified
 * @since 2000.03.27
 * @author HoKoNoUmo
 */
public class FrameAddAttWhole extends JFrame
{
	static final long serialVersionUID = 21L;
	String sbcpt=null;
	String xcpt_sFormalID=null;
	String cptGen=null; //the gen fileName of current.

	public FrameAddAttWhole(String acpt)
	{
		super("Add an EXISTING concept, as WHOLE to (" +acpt +")");
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
					String gen=ex.stringGen;
					Insert ins = new Insert();

					//Insert in current, the existing as whole.
					ins.insertAttWholeInFile(sbcpt,
																		existingFileName,					//FILENAME
																		gen,											//Generic
																		AAj.kb_sAuthor);						//Author

					//Insert in existing, the current as att IF already there is not.
					ex.extractParts(existingID);
					Vector vAtt=ex.vectorParID;
					if (!vAtt.contains(xcpt_sFormalID))
					{
						//find current's gen.
						ex.extractGenerics(xcpt_sFormalID);
						cptGen=ex.stringGen;
						ins.insertAttPartInFile(existingFileName,
																		sbcpt,											//FILENAME
																		cptGen,										//Generic
																		AAj.kb_sAuthor);						//Author
					}

					//It inserts in current's subs, the existing as whole IF already there is not.
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
							JOptionPane.showMessageDialog(null, "FrameAddAtt: Problem in " + currentFileName);
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
								//insert the existing if already there is not.
								ex.extractWholes(subID);
								Vector subWho=ex.vectorWhoID;
								if (!subWho.contains(existingID))
								{
									ins.insertAttWholeInFile(subFileName,
																		existingFileName,				//FILENAME
																		gen,										//Generic
																		AAj.kb_sAuthor);					//Author
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