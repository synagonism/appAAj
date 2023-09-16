/*
 * FrameOverridePart.java - Overrides a part-attribute.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2005 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Generic Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
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
 * It overrides a SELECTED inherited-notOverridden att with a NEW
 * or EXISTING attribute.
 * (an attribute with 'id' and 'generic' xml-attributes the same)
 *
 * It creates a new file-xcpt, as overridden att of current.
 * It inserts in new, the gen-att as generic.
 * It inserts in new, the gen-att's pars as pars.
 * It inserts in new, the current as whole, because new is an att of current.
 * It deletes in current, the inherited-notOverrridden att, **ONLY** if we override for the first time.
 * It inserts in current, the new as inherited-overridden att.
 * In gen-att, inserts the new as specific. [2000.04.23]<p>
 *
 * Some times I need to override MANY times an attribute
 * eg 'partition's element' we must override as many times as the
 * elements of a partition. [2000.04.27]<p>
 *
 * TODO:
 * When we override an atribute we must override it in all its
 * specific-occurrences. 2000.06.05
 *
 * @modified 2001.03.06
 * @since 2000.03.30
 * @author HoKoNoUmo
 */
public class FrameOverridePart extends JFrame
{
	static final long serialVersionUID = 21L;
	String sbcpt=null;					//the current xConcept, the one that holds the att.
	String xcpt_sFormalID=null;
	String attGen=null;				//the generic-attribute, the one we overide
	String attGenID=null;
	String attNew=null;
	boolean typeInt=true;


	/**
	 * @param acptID is the ID of the xcpt we will override the attribute.
	 * @param aattID is the ID of the att we will override.	 This is also the attGen of the new.
	 * @param first is the string that shows if this is the **first** time we override the attribute or not.
	 * @modified
	 * @since 2000.03.30
	 * @author HoKoNoUmo
	 */
	public FrameOverridePart(String acptID, String aattID, String first)
	{
		final String firstOver=first;
		xcpt_sFormalID=acptID;
		sbcpt=Util.getXCpt_sLastFileName(acptID);
		attGenID=aattID;
		attGen=Util.getXCpt_sLastFileName(aattID);

		//ASK how to override.
		int	 result= JOptionPane.showConfirmDialog(null, "Override with a NEW (not EXISTING) concept?");
		if(result == JOptionPane.YES_OPTION)
		{
			setTitle("Override the attribute (" +attGen +") of concept (" +sbcpt +")");
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
			//internal/external row.
			ButtonGroup fileType = new ButtonGroup();
			final JRadioButton jrbuttonInt = new JRadioButton ("as internal");
			final JRadioButton jrbuttonFile = new JRadioButton ("as file");
			fileType.add(jrbuttonFile);
			fileType.add(jrbuttonInt);
			jrbuttonInt.setSelected(true);
			jrbuttonInt.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae) {
					if (jrbuttonInt.isSelected() == true)
						typeInt = true;
					else
						typeInt = false;
				}
			});
			jrbuttonFile.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent ae) {
					if (jrbuttonFile.isSelected() == true)
						typeInt = false;
					else
						typeInt = true;
				}
			});
			JLabel labelIntExt = new JLabel("(Prefer Internals when the definition is enough to define it)");

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
			JPanel paneIntExt = new JPanel();
			paneIntExt.setLayout(new FlowLayout());
			paneIntExt.add(jrbuttonInt);
			paneIntExt.add(jrbuttonFile);
			JPanel paneIntExt2 = new JPanel();
			paneIntExt2.setLayout(new FlowLayout());
			paneIntExt2.add(labelIntExt);

			box.add(paneName);
			box.add(paneSyn);
			box.add(paneSyn2);
			box.add(paneDef);
			box.add(paneIntExt);
			box.add(paneIntExt2);

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
					String sSyns=fieldSyn.getText();
					String sDef=areaDef.getText();

					if (sFormalName!=null && sDef!=null)
					{
						Insert ins = new Insert();

						if (!typeInt)
						{
							int lastXSWVNumber = Util.getNextFormalNumber(sMdb);
							attNew = sFormalName +"@" +sMdb +"-" +lastXSWVNumber +".xml";

							//create the new file.
							ins.old_createXCpt_FileWithDef_Txt(attNew,									//fileName
//																		null,
//																		null,
//																sSyns,									//termTxCpt_sAll
																sDef);									//definition

							//delete in current, the old att, **only** if we override
							//for the first time it, ie an inherited-notoverriden one.
							if (firstOver.equals("first"))
							{
								Remove rm = new Remove();
								rm.removeCPTEXT(sbcpt, attGenID);
							}

							//insert in current, the new as inherited-overridden.
							ins.insertAttPartInFile(sbcpt,
																attNew,									//FILENAME
																attGen,									//Generic
																AAj.kb_sAuthor);					//Author

							//insert in new, the current as whole.
							ins.insertAttWholeInFile(attNew,
																sbcpt,											//FILENAME
																null,										//Generic
																AAj.kb_sAuthor);						//Author

							//insert in new, the gen-att as generic.
							ins.insertAttGenericInFile(attNew,
																attGen,										//FILENAME
																attGen,										//Generic
																AAj.kb_sAuthor);						//Author

							//inserts in new, gen-att's pars as inherited to new.
							Extract ex = new Extract();
							ex.extractParts(attGenID);
							Vector vAtt = ex.vectorParID;
							for (int i=0; i<vAtt.size(); i++)
							{
								String attID=(String)vAtt.get(i);
								String att=Util.getXCpt_sLastFileName(attID);
								ins.insertAttPartInFile(attNew,
																att,											//FILENAME
																att,											//GENERIC
																AAj.kb_sAuthor);						//AUTHOR
							}

							//in gen-att, insert the new as spe.
							ins.insertAttSpecificInFile(attGen,
																attNew,										//FILENAME
																attGen,										//Generic
																AAj.kb_sAuthor);						//Author

							//insert in new, gen-att's who if exist as who to new.
		//					ex.extractWholes(attGenID);
		//					Vector vWho=ex.vectorWhoID;
		//					for (int i=0; i<vWho.size(); i++)
		//					{
		//						String whoID=(String)vWho.get(i);
		//						String who=Util.getXCpt_sLastFileName(whoID);
		//						ins.insertAttWholeInFile(attNew,
		//																who,											//FILENAME
		//																null,										//Generic
		//																Util.getCurrentDate(),		//Created
		//																Util.getCurrentDate(),		//LastMod
		//																AAj.kb_sAuthor);						//Author
		//					}

							//redisplay the current.
							AAj.display(sbcpt);
							if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(sbcpt);
							dispose();
						}

						else //make internal-xcpt.
						{
							String hid=xcpt_sFormalID;
							String hostFormalNumber=hid.substring(hid.indexOf("-")+1, hid.length());
							int nextFormalNumber_Internal=Util.getNextFormalNumber_Internal(hid);
							attNew = sFormalName +"@" +sMdb +"-" +hostFormalNumber +"#" +nextFormalNumber_Internal +".xml";

							//delete in current, the old att, **only** if we override
							//for the first time it, ie an inherited-notoverriden one.
							if (firstOver.equals("first"))
							{
								Remove rm = new Remove();
								rm.removeCPTEXT(sbcpt, attGenID);
							}

							//insert the new att.
							ins.createXCpt_InternalAsPart(sbcpt, attNew, attGen, sSyns, sDef);

							//redisplay host.
							AAj.display(sbcpt);
							if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(sbcpt);
							dispose();
						}
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
					AAj.display("it-48#5");
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

		else if(result == JOptionPane.NO_OPTION)//override with an existing sbcpt.
		{
			String attOverID = JOptionPane.showInputDialog(null, "Enter the ID (eg: it-1) of the existing concept");
			if(attOverID != null)
			{
				String attOver = Util.getXCpt_sLastFileName(attOverID);

				//delete in current, the old att.
				if (firstOver.equals("first"))
				{
					Remove rm = new Remove();
					rm.removeCPTEXT(sbcpt, attGenID);
				}
				//WE must remove and the existing att.

				//insert in current, the existing as inherited-overridden.
				Insert ins = new Insert();
				ins.insertAttPartInFile(sbcpt,
																attOver,									//FILENAME
																attGen,										//Generic
																AAj.kb_sAuthor);						//Author

				//insert in existing, the current as whole.
				ins.insertAttWholeInFile(attOver,
																sbcpt,											//FILENAME
																null,										//Generic
																AAj.kb_sAuthor);						//Author

				//insert in existing, the gen-att as generic.
				ins.insertAttGenericInFile(attOver,
																attGen,										//FILENAME
																attGen,									//Generic
																AAj.kb_sAuthor);					//Author

				//inserts in existing, gen-att's pars as inherited to new.
				Extract ex = new Extract();
				ex.extractParts(attGenID);
				Vector vAtt = ex.vectorParID;
				for (int i=0; i<vAtt.size(); i++)
				{
					String attID=(String)vAtt.get(i);
					String att=Util.getXCpt_sLastFileName(attID);
					ins.insertAttPartInFile(attOver,
																att,										//FILENAME
																att,										//GENERIC
																AAj.kb_sAuthor);					//AUTHOR
				}

				//redisplay the current.
				AAj.display(sbcpt);
				if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(sbcpt);
			}
		}
	}
}