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
 * It creates the new xml-file.
 * It inserts in current, the new as gen.
 * It inserts in new, the current as spe.
 *
 * to do:
 * The program asks which current-pars to generalize. (file-xcpt because have subs)
 * These pars are becoming inherited-overridden in current.
 *
 * Do you want the current's att	...................
 * to become generic's att .....................
 * yes, no, yestoall, notoall
 *
 * @modified 2000.03.21
 * @since 2000.03.17
 * @author HoKoNoUmo
 */
public class FrameAddNewGeneric extends JFrame
{
	static final long serialVersionUID = 21L;
	String	sbcpt=null;

	public FrameAddNewGeneric(String acpt)
	{
		super("Add a NEW concept, as GENERIC to (" +acpt +")");
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
		areaDef.setFont(new Font("serif", Font.BOLD, 18));
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
				String sMdb= (String)cbox.getSelectedItem();
				String sSyns=fieldSyn.getText();
				String sDef=areaDef.getText();

				if (sFormalName!=null && sDef!=null)
				{
					int lastXSWVNumber = Util.getNextFormalNumber(sMdb);
					Insert ins = new Insert();
					String newFileName=null;
					newFileName = sFormalName +"@" +sMdb +"-" +lastXSWVNumber +".xml";

					//create the new file.
					ins.old_createXCpt_FileWithDef_Txt(newFileName,										//fileName
//																		null,
//																		null,
//														sSyns,													//termTxCpt_sAll
														sDef);													//definition
					//insert in current, the new as gen.
					ins.insertAttGenericInFile(sbcpt,
																	newFileName,							//FILENAME
																	"no",											//Generic
																	AAj.kb_sAuthor);						//Author
					//insert in new, the current as spe.
					ins.insertAttSpecificInFile(newFileName,
																	sbcpt,											//FILENAME
																	newFileName,							//Generic
																	AAj.kb_sAuthor);						//Author
					//dispose after created the new gen.
					dispose();

					//generalize current's att.
					Extract ex = new Extract();
					ex.extractParts(sbcpt);
					Vector<String> vattNotInh = ex.vectorParNotInh;
					Vector<String> vectorReplacing=new Vector<String>();//will containe the att we will modify.
					for (int i=0; i<vattNotInh.size(); i++)
					{
						String attID=vattNotInh.get(i);
						String attFileName=Util.getXCpt_sLastFileName(attID);
						//ask for generalization
						int confirm = JOptionPane.showConfirmDialog(null,
									"Do you want to GENERICIZE the att (" +attFileName +")",
									"Choose One", JOptionPane.YES_NO_OPTION);
						if(confirm == JOptionPane.YES_OPTION)
						{
							//ask for file-name
							String result = JOptionPane.showInputDialog("INSERT the (FormalName@MM) "
																					+"for the GENERIC of att ("+attFileName +")");
							if (result!=null)
							{
								String mm2 = result.substring(result.indexOf("@")+1, result.length());
								int nextMMID = Util.getNextFormalNumber(mm2);
								String genFileName = result +"-" +nextMMID +".xml";
								String genID=Util.getXCpt_sFormalID(genFileName);
								//create file for the generalized.
								//the generalized have whole the new.
								//the generalized have spe the att.
								ins.createXCpt_FileWithAtt(genFileName, null, newFileName, null, attFileName, null);

								//the new have att the generalized.
								ins.insertAttPartInFile(newFileName,genFileName,"no",AAj.kb_sAuthor);

								//do current's-att >> inherited-overridden.
								vectorReplacing.add(attID +"," +genID);
								//the att file-xcpt have as gen the generalized
								if (attID.indexOf("#")==-1)
								{
									ins.insertAttGenericInFile(attFileName,genFileName,"no",
														AAj.kb_sAuthor);
								}
							}
						}
					}//end for att loop.

					//set in current, new gen values in generalized pars.
					ins.replaceGeneric(sbcpt, vectorReplacing);

					//display the new file in frame-editor
					AAj.display(newFileName);
					if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(newFileName);
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
		JButton jbHelp = new JButton(" Help ");
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