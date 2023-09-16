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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 * @modified 2001.07.17
 * @since 2000.02.06
 * @author HoKoNoUmo
 */
public class FrameAddUnrelated extends JFrame
{
	static final long serialVersionUID = 21L;

	public FrameAddUnrelated()
	{
		super(AAj.rbLabels.getString("fauTitle")); //"Add a NEW UNRELATED concept"

		//formal-word row.
		JLabel labelName = new JLabel(AAj.rbLabels.getString("FormalTerm")+": ");
		final JTextField fieldName = new JTextField(19);
		JLabel labelMdb = new JLabel("			 "+AAj.rbLabels.getString("BWorldview")+":");
		Object[] vd = AAj.trmapSrBrSubWorldview.keySet().toArray();
		final JComboBox cbox = new JComboBox(vd);
		cbox.setSelectedItem((Object)AAj.xcpt_sFormalID.substring(0, AAj.xcpt_sFormalID.indexOf("-")));
		//termTxCpt_sAll row.
		JLabel labelSyn = new JLabel(AAj.rbLabels.getString("frasisSynMgt"));//(You can manage its sysnonyms, after concept-creation)
		//definition row.
		JLabel labelDef = new JLabel(AAj.rbLabels.getString("DEFINITION") +":");
		final JTextArea areaDef = new JTextArea();
		JScrollPane scroller = new JScrollPane(){
			static final long serialVersionUID = 21L;
			public Dimension getPreferredSize() {
				return new Dimension(399,126);
			}
		};
		JViewport port = scroller.getViewport();
		areaDef.setFont(new Font("serif", Font.PLAIN, 16));
		areaDef.setLineWrap(true);
		areaDef.setWrapStyleWord(true);
		port.add(areaDef);

		//Create the Layouts.
		Box box = new Box(BoxLayout.Y_AXIS);

		JPanel namePane = new JPanel();
		namePane.setLayout(new FlowLayout());
		namePane.add(labelName);
		namePane.add(fieldName);
		namePane.add(labelMdb);
		namePane.add(cbox);

		JPanel synPane = new JPanel();
		synPane.setLayout(new FlowLayout());
		synPane.add(labelSyn);

		JPanel defPane = new JPanel();
		defPane.setLayout(new FlowLayout());
		defPane.add(labelDef);
		defPane.add(scroller);

		box.add(namePane);
		box.add(synPane);
		box.add(defPane);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		final JButton ok = new JButton(AAj.rbLabels.getString("OK"));
		buttonPane.add(ok);
		ok.addActionListener(new ActionListener()
		{
				public void actionPerformed(ActionEvent ae)
				{
					String sFormalName=fieldName.getText();
					String sMdb=(String) cbox.getSelectedItem();
					String sDef=areaDef.getText();

					if (sFormalName!=null && sDef!=null)
					{
						int lastXSWVNumber = Util.getNextFormalNumber(sMdb);
						String fileName = sFormalName +"@" +sMdb +"-" +lastXSWVNumber +".xml";

						Insert ins = new Insert();
						ins.old_createXCpt_FileWithDef_Txt(fileName,			//fileName
																		sDef);					//definition

						//display the new file in frame-editor
						AAj.display(fileName);
						if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(fileName);

						dispose();
					}
				}
			});

			final JButton cancel = new JButton(AAj.rbLabels.getString("CANCEL"));
			buttonPane.add(cancel);
			cancel.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae)
				{
					dispose();
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
