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
 * a) IF current is an OBJECT THEN it adds a RELATION of current-lg_object to another-lg_object.
 * It asks for the file-name of the other lg_object.
 * It creates the xml-file for the new concept.
 * It inserts in new, the 'objects' as environment.
 * It inserts in objects, the new as environment.
 *
 * b) IF current is a RELATION THEN adds an lg_object. [toDo 2000.03.29]
 *
 * @modified 2000.03.29
 * @since 2000.03.24
 * @author HoKoNoUmo
 */
public class FrameAddNewEnvironment extends JFrame
{
	static final long serialVersionUID = 21L;
	String	sbcpt=null;

	public FrameAddNewEnvironment(String acpt)
	{
		super("Add a NEW concept, as ENVIRONMENT to (" +acpt +")");
		sbcpt=Util.getXCpt_sLastFileName(acpt);

		//related-lg_object row.
		JLabel labelObject = new JLabel("Related-Object:");
		final JTextField fieldObject = new JTextField(26);
		fieldObject.setFont(new Font("serif", Font.BOLD, 14));
		JLabel labelObject2 = new JLabel("(Write the FileName)");
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
		JPanel paneObject = new JPanel();
		paneObject.add(labelObject);
		paneObject.add(fieldObject);
		paneObject.add(labelObject2);
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

		box.add(paneObject);
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
				if (!Util.isSpecificOf(Util.getXCpt_sFormalID(sbcpt),"hknu.meta-1"))
				{
					String sFormalName=fieldName.getText();
					String sMdb=(String) cbox.getSelectedItem();
					String sSyns=fieldSyn.getText();
					String sDef=areaDef.getText();

					if (sFormalName!=null && sDef!=null)
					{
						int lastXSWVNumber = Util.getNextFormalNumber(sMdb);
						Insert ins = new Insert();
						String newFileName=null;
						newFileName = sFormalName +"@" +sMdb +"-" +lastXSWVNumber +".xml";

						//create the new file.
						ins.old_createXCpt_FileWithDef_Txt(newFileName,												//fileName
//																		null,
//																		null,
//															sSyns,															//termTxCpt_sAll
															sDef);															//definition

						//insert the 'objects' in new.
						ins.insertAttEnvironmentInFile(newFileName,
																				sbcpt,											//FILENAME
																				null,										//Generic
																				AAj.kb_sAuthor);						//Author
						String lg_object=fieldObject.getText();
						ins.insertAttEnvironmentInFile(newFileName,
																				lg_object,										//FILENAME
																				null,										//Generic
																				AAj.kb_sAuthor);						//Author

						//insert the corelaton in objects.
						ins.insertAttEnvironmentInFile(lg_object,
																				newFileName,							//FILENAME
																				null,										//Generic
																				AAj.kb_sAuthor);						//Author
						ins.insertAttEnvironmentInFile(sbcpt,
																				newFileName,							//FILENAME
																				null,										//Generic
																				AAj.kb_sAuthor);						//Author

						//inserts the current as generic to new.
						ins.insertAttGenericInFile(newFileName,
																				"Relation@hknu.symb-16.xml",		//FILENAME
																				null,										//Generic
																				AAj.kb_sAuthor);						//Author

						//display the new file in frame-editor
						AAj.display(newFileName);
						if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(newFileName);
					}
					else //the current is a corelaton.
					{
						JOptionPane.showMessageDialog(null,"We add 'corelatons' only to 'objects'");
					}
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