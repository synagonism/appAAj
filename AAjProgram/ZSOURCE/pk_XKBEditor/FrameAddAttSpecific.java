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
 * It asks for the ID of the existing SPECIFIC.
 * It inserts in current, the existing as specific.
 * It inserts in existing, the current as generic IF it is not.
 * It inserts in existing, the current's pars IF they are not.
 * It inserts in existing, the current's wholes IF they are not.
 *
 * @modified 2001.03.06
 * @since 2000.03.29
 * @author HoKoNoUmo
 */
public class FrameAddAttSpecific extends JFrame
{
	static final long serialVersionUID = 21L;
	String sbcpt=null;
	String xcpt_sFormalID=null;
	String cptGen=null; //the gen fileName of current.

	public FrameAddAttSpecific(String acpt)
	{
		super("Add an EXISTING concept, as SPECIFIC to (" +acpt +")");
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
				String existID=fieldName.getText();
				if (existID!=null)
				{
					String existFileName = Util.getXCpt_sLastFileName(existID);
					Insert ins = new Insert();
					Extract ex = new Extract();
					ex.extractGenerics(sbcpt);
					cptGen=ex.stringGen;
					ex.extractGenerics(existID);
					Vector vexistGen=ex.vectorGenID;
					String existGen=ex.stringGen;

					//insert in current, the existing as spe.
					ins.insertAttSpecificInFile(sbcpt,
																		existFileName,					//FILENAME
																		existGen,								//Generic
																		AAj.kb_sAuthor);					//Author

					//Insert in existing, the current as GENERIC IF already it is not.
					if (!vexistGen.contains(xcpt_sFormalID))
					{
						ins.insertAttGenericInFile(existFileName,
																		sbcpt,										//FILENAME
																		cptGen,									//Generic
																		AAj.kb_sAuthor);					//Author
					}

					//Insert in existing, the current's pars IF they are not.
					ex.extractParts(sbcpt);
					Vector vcptAtt = ex.vectorParID;
					ex.extractParts(existID);
					Vector vexistAtt=ex.vectorParID;
					for (int i=0; i<vcptAtt.size(); i++)
					{
						String att=(String)vcptAtt.get(i);
						String attFLN=Util.getXCpt_sLastFileName(att);
						if (!vexistAtt.contains(att))
						{
							ins.insertAttPartInFile(existFileName,
																			attFLN,									//FILENAME
																			attFLN,									//GENERIC
																			AAj.kb_sAuthor);					//AUTHOR
						}
					}

					//Insert in existing, the current's wholes IF they are not.
					ex.extractWholes(sbcpt);
					Vector vcptWho=ex.vectorWhoID;
					ex.extractWholes(existID);
					Vector vexistWho=ex.vectorWhoID;
					for (int i=0; i<vcptWho.size(); i++)
					{
						String who=(String)vcptWho.get(i);
						String whoFLN=Util.getXCpt_sLastFileName(who);
						if (!vexistWho.contains(who))
						{
							ins.insertAttWholeInFile(existFileName,
																	whoFLN,										//FILENAME
																	null,										//Generic
																	AAj.kb_sAuthor);						//Author
						}
					}

					//redisplay the current.
					AAj.display(AAj.xcpt_sFileName);
					if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(AAj.xcpt_sFileName);
					dispose();
				}
				else //null existID
				{
					JOptionPane.showMessageDialog(null,"You MUST enter the ID of the existing concept");
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