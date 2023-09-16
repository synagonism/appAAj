/**
 * @modified 2009.06.21
 * @since 2001.05.03
 * FrameNameManagement.java - Inserts/Deletes names of current xConcept.
 * Copyright (C) 2001 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 */
package pk_XKBEditor;

import pk_XKBManager.RetrieveFrame_FindTxConceptTerms;
import pk_XKBManager.AAj;
import pk_Util.Util;
import pk_Util.Extract;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class FrameNameManagement extends JFrame
{
	static final long serialVersionUID = 21L;
	final String	xcpt_sFileName;
	final String	xcpt_sFormalName;
	final String	xcpt_sFormalID;

	String 			lng=AAj.lng;
	boolean			modified=false;

	JButton			jbtInsert;
	JButton			jbtModify;
	JButton			jbtUpdate;
	JButton			jbtDelete;
	//toDo
	JButton			jbtDeletePermanent;
	JButton			jbtUp;
	JButton			jbtDown;

	JLabel				jlblLang;
	JLabel				jlblTypeOfTxConceptName;
	JLabel				jlblLgConceptName;
	JLabel				jlblRule;
	JLabel				jlblDeleted;
	JLabel				jlblCreated;
	JLabel				jlblAuthor;
	JLabel				jlblNameMsg;

	JComboBox		jcbLang;
	JComboBox		jcbTypeOfNameInLng;
	JTextField		jtfTxConceptName;
	//toDo: make a choice with the rules.
	JTextField		jtfRule;
	JTextField		jtfDeleted;
	JTextField		jtfCreated;
	JTextField		jtfAuthor;
	JCheckBox		jchMostUsed;

	JList						jlsName;
	Vector	<String>		vctNameOfXmlSCpt; //Holds the lg-concept's--names of a sr-concept-file.
	Vector	<String>		vctNameOfPresentation;	//Holds the lg-concept's--names of list

	/**
	 * <b>INPUT</b>: the file-name of a sr-concept.<br/>
	 * PROCESS: displays the lg-concept's--names of a choosen-language at a time,
	 * which we manage.
	 *
	 * @modified 2001.06.28
	 * @since 2001.05.5
	 * @author HoKoNoUmo
	 */
	public FrameNameManagement(String kspFileN)
	{
		super(AAj.rbLabels.getString("fnmgtTitle"));
		xcpt_sFileName=kspFileN;
		xcpt_sFormalName=Util.getXCpt_sFormalName(xcpt_sFileName);
		xcpt_sFormalID=Util.getXCpt_sFormalID(xcpt_sFileName);

		jlblLang = new JLabel(AAj.rbLabels.getString("Language")+":",JLabel.RIGHT);
		jlblTypeOfTxConceptName = new JLabel(AAj.rbLabels.getString("TypeOfName")+":",JLabel.RIGHT);
		jlblLgConceptName = new JLabel(AAj.rbLabels.getString("Name")+":",JLabel.RIGHT);
		jlblRule = new JLabel(AAj.rbLabels.getString("fnmgtTmrRule"),JLabel.RIGHT);
		jlblDeleted = new JLabel(AAj.rbLabels.getString("Deleted")+":",JLabel.RIGHT);
		jlblCreated = new JLabel(AAj.rbLabels.getString("Created")+":",JLabel.RIGHT);
		jlblAuthor = new JLabel(AAj.rbLabels.getString("Author")+":",JLabel.RIGHT);

		jcbLang = new JComboBox();
		jcbLang.addItem(AAj.rbLabels.getString("English2"));
		jcbLang.addItem(AAj.rbLabels.getString("Greek2"));
		jcbLang.addItem(AAj.rbLabels.getString("Esperanto"));
		jcbLang.addItem(AAj.rbLabels.getString("Komo"));
		//addLng
		if (AAj.lng.equals("eng"))
			jcbLang.setSelectedIndex(0);
		else if (AAj.lng.equals("eln"))
			jcbLang.setSelectedIndex(1);
		else if (AAj.lng.equals("epo"))
			jcbLang.setSelectedIndex(2);
		else if (AAj.lng.equals("kml"))
			jcbLang.setSelectedIndex(3);
		jcbLang.addActionListener(new ActionDisplay());
		jcbTypeOfNameInLng = new JComboBox();
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_NounCase"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_NounAdjective"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_NounAdverb"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_Verb"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_Conjunction"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_Short"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_Symbol"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_NounSpecialCase"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_NounSpecialAdjective"));
		jcbTypeOfNameInLng.addItem(AAj.rbLabels.getString("Name_NounSpecialAdverb"));

		jtfTxConceptName = new JTextField(17);
		jtfRule = new JTextField(17);
		jtfDeleted = new JTextField(17);
		jtfCreated = new JTextField(17);
		jtfAuthor = new JTextField(17);
		jchMostUsed = new JCheckBox(AAj.rbLabels.getString("MostUsed"));

		final JButton jbtFindSpeType = new JButton(AAj.rbLabels.getString("Find"));
		jbtFindSpeType.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				RetrieveFrame_FindTxConceptTerms ffsf = new RetrieveFrame_FindTxConceptTerms(jtfTxConceptName.getText());
			}
		});
		final JButton jbtModified = new JButton(AAj.rbLabels.getString("fnmgtCurrent2"));
		jbtModified.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				jtfDeleted.setText(Util.getCurrentDate());
			}
		});
		final JButton jbtCreated = new JButton(AAj.rbLabels.getString("fnmgtCurrent2"));
		jbtCreated.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				jtfCreated.setText(Util.getCurrentDate());
			}
		});
		final JButton jbtAuthor = new JButton(AAj.rbLabels.getString("fnmgtCurrent1"));
		jbtAuthor.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				jtfAuthor.setText(AAj.kb_sAuthor);
			}
		});
		jbtInsert = new JButton(AAj.rbLabels.getString("Insert"));
		jbtInsert.addActionListener(new ActionInsert());
		jbtInsert.setToolTipText(AAj.rbLabels.getString("fnmgtTipInsert"));
		jbtModify = new JButton(AAj.rbLabels.getString("Modify"));
		jbtModify.addActionListener(new ActionModify());
		jbtModify.setToolTipText(AAj.rbLabels.getString("fnmgtTipModify"));
		jbtDelete = new JButton(AAj.rbLabels.getString("Delete"));
		jbtDelete.addActionListener(new ActionDelete());
		jbtDelete.setToolTipText(AAj.rbLabels.getString("fnmgtTipDelete"));
		jbtDeletePermanent = new JButton(AAj.rbLabels.getString("fnmgtDeletePermanent"));
		jbtDeletePermanent.addActionListener(new ActionDeletePermanent());
		jbtDeletePermanent.setToolTipText(AAj.rbLabels.getString("fnmgtTipDeletePermanent"));
		jbtUpdate = new JButton(AAj.rbLabels.getString("Update"));
		jbtUpdate.addActionListener(new ActionUpdate());
		jbtUpdate.setToolTipText(AAj.rbLabels.getString("fnmgtTipUpdate"));
		jbtUp = new JButton(AAj.rbLabels.getString("Up"));
		jbtUp.addActionListener(new ActionUp());
		jbtDown = new JButton(AAj.rbLabels.getString("Down"));
		jbtDown.addActionListener(new ActionDown());
		final JButton jbtOK = new JButton(AAj.rbLabels.getString("OK"));
		jbtOK.setToolTipText(AAj.rbLabels.getString("fnmgtTipOK"));
		jbtOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				updateNameConceptLgLango();
				AAj.display(xcpt_sFileName);
				if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(xcpt_sFileName);
				dispose();
			}
		});

		final JButton jbtCancel = new JButton(AAj.rbLabels.getString("CANCEL"));
		jbtCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dispose();
			}
		});

		Extract ex = new Extract();
		vctNameOfXmlSCpt = ex.extractLgConceptNames(xcpt_sFileName, AAj.lng);
		makeVectorOfPresentation(vctNameOfXmlSCpt);
		if (!vctNameOfXmlSCpt.isEmpty()){
			String sel = vctNameOfXmlSCpt.elementAt(1);
			selectNameInJComboBoxWithNameInLng(sel);
			jtfTxConceptName.setText(Util.getXmlAttribute(sel,"TxEXP"));
			jtfRule.setText(Util.getXmlAttribute(sel,"TRMrULE"));
			jtfDeleted.setText(Util.getXmlAttribute(sel,"DELETED"));
			jtfCreated.setText(Util.getXmlAttribute(sel,"CREATED"));
			jtfAuthor.setText(Util.getXmlAttribute(sel,"AUTHOR"));
		}
		else {
			jcbTypeOfNameInLng.setSelectedIndex(0);
			jtfTxConceptName.setText("");
			jtfRule.setText("");
			jtfDeleted.setText("");
			jtfCreated.setText("");
			jtfAuthor.setText("");
		}
		jlsName = new JList(vctNameOfPresentation);
		JScrollPane scrollPane = new JScrollPane(jlsName);

		MouseListener mouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				if (me.getClickCount() == 1)
				{
					JList list = (JList)me.getSource();
					int index = list.locationToIndex(me.getPoint());//from 0,1,2,...
					if (index!=-1)
					{
						list.setSelectedIndex(index);
						//on the SAME index, search vctNameOfXmlSCpt
						String sel = vctNameOfXmlSCpt.elementAt(index+1);//vctNameOfPresentation does not have <lng line
						if (sel!=null)
						{
							//select Term_TxConcept
							selectNameInJComboBoxWithNameInLng(sel);
							jtfTxConceptName.setText(Util.getXmlAttribute(sel,"TxEXP"));
							jtfRule.setText(Util.getXmlAttribute(sel,"TRMrULE"));
							jtfDeleted.setText(Util.getXmlAttribute(sel,"DELETED"));
							jtfCreated.setText(Util.getXmlAttribute(sel,"CREATED"));
							jtfAuthor.setText(Util.getXmlAttribute(sel,"AUTHOR"));
							String amu=Util.getXmlAttribute(sel,"MOSTuSED");
							boolean mu=true;
							if (amu.equals("none")||amu.equals(""))
								mu=false;
							jchMostUsed.setSelected(mu);
						}
						else {
							jcbTypeOfNameInLng.setSelectedIndex(0);
							jtfTxConceptName.setText("");
							jtfRule.setText("");
							jtfDeleted.setText("");
							jtfCreated.setText("");
							jtfAuthor.setText("");
							jchMostUsed.setSelected(false);
						}
					}
				}
			}
		};
		jlsName.addMouseListener(mouseListener);
		jlsName.setFont(new Font("sansserif", Font.BOLD, 14));

		//Create the Layouts.
		JPanel pnlNorthTitle = new JPanel();
		pnlNorthTitle.setBorder(BorderFactory.createEtchedBorder());
		pnlNorthTitle.setBackground(Color.cyan);
		JLabel labelCpt=new JLabel(xcpt_sFormalName);
		labelCpt.setFont(new Font("sansserif", Font.BOLD, 18));
		pnlNorthTitle.add(labelCpt);

		JPanel paneLeft = new JPanel(new GridLayout(8,1,1,0));
		paneLeft.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		paneLeft.add(jlblLang);
		paneLeft.add(jlblTypeOfTxConceptName);
		paneLeft.add(jlblLgConceptName);
		paneLeft.add(jlblRule);
		paneLeft.add(jlblDeleted);
		paneLeft.add(jlblCreated);
		paneLeft.add(jlblAuthor);
		JPanel paneMiddle = new JPanel(new GridLayout(8,1,1,0));
		paneMiddle.add(jcbLang);
		paneMiddle.add(jcbTypeOfNameInLng);
		paneMiddle.add(jtfTxConceptName);
		paneMiddle.add(jtfRule);
		paneMiddle.add(jtfDeleted);
		paneMiddle.add(jtfCreated);
		paneMiddle.add(jtfAuthor);
		paneMiddle.add(jchMostUsed);
		JPanel paneRight = new JPanel(new GridLayout(8,1,1,0));
		paneRight.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		paneRight.add(new JLabel(" "));
		paneRight.add(new JLabel(" "));
		paneRight.add(new JLabel(" "));
		paneRight.add(jbtFindSpeType);
		paneRight.add(jbtModified);
		paneRight.add(jbtCreated);
		paneRight.add(jbtAuthor);
		paneRight.add(new JLabel(" "));
		JPanel paneCenter1 = new JPanel(new BorderLayout());
		paneCenter1.setBorder(BorderFactory.createEmptyBorder(2, 5, 10, 15));
		paneCenter1.add(paneLeft, BorderLayout.WEST);
		paneCenter1.add(paneMiddle, BorderLayout.CENTER);
		paneCenter1.add(paneRight, BorderLayout.EAST);
		JPanel paneCenter2 = new JPanel(new GridLayout(1,1));
		paneCenter2.add(scrollPane);
		JPanel pnlCenterNoInsert = new JPanel(new BorderLayout());
		pnlCenterNoInsert.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));
		pnlCenterNoInsert.add(paneCenter1, BorderLayout.NORTH);
		pnlCenterNoInsert.add(paneCenter2, BorderLayout.CENTER);

		JPanel pnlEastInsert = new JPanel(new GridLayout(0, 1,10,10));
		pnlEastInsert.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 20));
		pnlEastInsert.add(new JLabel(" "));
		pnlEastInsert.add(jbtInsert);
		pnlEastInsert.add(jbtModify);
		pnlEastInsert.add(jbtDelete);
		pnlEastInsert.add(jbtDeletePermanent);
		pnlEastInsert.add(jbtUpdate);
		pnlEastInsert.add(new JLabel(" "));
		pnlEastInsert.add(new JLabel(" "));
		pnlEastInsert.add(new JLabel(" "));
		pnlEastInsert.add(jbtUp);
		pnlEastInsert.add(jbtDown);
		pnlEastInsert.add(new JLabel(" "));
		pnlEastInsert.add(new JLabel(" "));

		JPanel paneSouth1 = new JPanel();
		jlblNameMsg = new JLabel(AAj.rbLabels.getString("fnmgtMsgName"));
		jlblNameMsg.setForeground(Color.red);
		paneSouth1.add(jlblNameMsg);
		JPanel paneSouth2 = new JPanel();
		paneSouth2.add(jbtOK);
		paneSouth2.add(jbtCancel);
		Box pnlSouthBoxOk = new Box(BoxLayout.Y_AXIS);
		pnlSouthBoxOk.add(paneSouth1);
		pnlSouthBoxOk.add(paneSouth2);

		JPanel jpnlMain = new JPanel(new BorderLayout());
		jpnlMain.setBorder(BorderFactory.createEtchedBorder());
		jpnlMain.add(pnlNorthTitle, BorderLayout.NORTH);
		jpnlMain.add(pnlCenterNoInsert, BorderLayout.CENTER);
		jpnlMain.add(pnlEastInsert, BorderLayout.EAST);
		jpnlMain.add(pnlSouthBoxOk, BorderLayout.SOUTH);

		getContentPane().add(jpnlMain, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		pack();
		Util.setFrameIcon(this);
		setLocation(226,0);
		setSize(new Dimension(659, 559));
		setVisible(true);
	}


	/**
	 * <b>INPUT</b>: a vector with the lg-concept's--name lines of a
	 * sr-concept-file (plus <lng lines).<br/>
	 * PROCESS: creates a mapping-vector to display in this Frame.
	 *
	 * @modified 2009.06.13
	 * @since 2001.05.08
	 * @author HoKoNoUmo
	 */
	void makeVectorOfPresentation(Vector<String> v)
	{
		vctNameOfPresentation=new Vector<String>(v.size());

		for (int i=0; i<v.size(); i++)
		{
			String lns = v.elementAt(i);
			String lnl = "";
			if (lns.indexOf("Name")!=-1){
				lnl=AAj.rbLabels.getString(lns.substring(lns.indexOf("Name"),lns.indexOf(" TxEXP")))
						+":  "
						+Util.getXmlAttribute(lns,"TxEXP");
				vctNameOfPresentation.add(lnl);
			}
		}
	}


	/**
	 * Inserts the new lg-concept's--names of the displayed language
	 * in the xConcept file.
	 *
	 * @modified 2008.03.24
	 * @since 2008.03.24 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	void updateNameConceptLgLango()
	{
		Insert ins = new Insert();
		ins.insertVectorXmlNameLngInXConcept(xcpt_sFileName, lng, vctNameOfXmlSCpt);
	}


	/**
	 * Selects the appropriate name in the combobox with the types of
	 * names in each language, GIVEN the types of names used in the
	 * xml-sensorial-b-concepts.
	 *
	 * @modified 2008.04.06
	 * @since 2008.04.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	void selectNameInJComboBoxWithNameInLng(String sel)
	{
			if (sel.startsWith("<Name_NounCase"))
				jcbTypeOfNameInLng.setSelectedIndex(0);
			else if (sel.startsWith("<Name_NounAdjective"))
				jcbTypeOfNameInLng.setSelectedIndex(1);
			else if (sel.startsWith("<Name_NounAdverb"))
				jcbTypeOfNameInLng.setSelectedIndex(2);
			else if (sel.startsWith("<Name_Verb"))
				jcbTypeOfNameInLng.setSelectedIndex(3);
			else if (sel.startsWith("<Name_Conjunction"))
				jcbTypeOfNameInLng.setSelectedIndex(4);
			else if (sel.startsWith("<Name_Short"))
				jcbTypeOfNameInLng.setSelectedIndex(5);
			else if (sel.startsWith("<Name_Symbol"))
				jcbTypeOfNameInLng.setSelectedIndex(6);
			else if (sel.startsWith("<Name_NounSpecialCase"))
				jcbTypeOfNameInLng.setSelectedIndex(7);
			else if (sel.startsWith("<Name_NounSpecialAdjective"))
				jcbTypeOfNameInLng.setSelectedIndex(8);
			else if (sel.startsWith("<Name_NounSpecialAdverb"))
				jcbTypeOfNameInLng.setSelectedIndex(9);
	}


	/**
	 * Finds the type of name used in xml-sr-concpets,
	 * GIVEN the index of the selected name in the comboBox with the types of
	 * names in each language.
	 *
	 * @modified 2009.06.21
	 * @since 2009.06.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	String getNameInSrCptIfIndexOfJComboBoxWithNameInLng(int i)
	{
		String ton="";
		switch (i) {
			case 0:  ton="Name_NounCase"; break;
			case 1:  ton="Name_NounAdjective"; break;
			case 2:  ton="Name_NounAdverb"; break;
			case 3:  ton="Name_Verb"; break;
			case 4:  ton="Name_Conjunction"; break;
			case 5:  ton="Name_Short"; break;
			case 6:  ton="Name_Symbol"; break;
			case 7:  ton="Name_NounSpecialCase"; break;
			case 8:  ton="Name_NounSpecialAdjective"; break;
			case 9:  ton="Name_NounSpecialAdverb"; break;
		}
		return ton;
	}


	/**
	 * Displays the lg-concept's--names of the selected language.
	 *
	 * @modified 2008.03.23
	 * @since 2008.03.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionDisplay extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			if (modified){
				int confirm = JOptionPane.showConfirmDialog(null,
							"The previous NameConceptLgLango have been modified. Do you want to UPDATE them?",
							"Choose One", JOptionPane.YES_NO_OPTION);
				if(confirm == JOptionPane.YES_OPTION)
					updateNameConceptLgLango();
				modified=false;
			}

			String sl = (String)jcbLang.getSelectedItem();

			if (sl.equals(AAj.rbLabels.getString("English2")))
				lng="eng";
			else if (sl.equals(AAj.rbLabels.getString("Greek2")))
				lng="eln";
			else if (sl.equals(AAj.rbLabels.getString("Esperanto")))
				lng="epo";
			else if (sl.equals(AAj.rbLabels.getString("Komo")))
				lng="kml";
			//addLng

			Extract ex = new Extract();
			vctNameOfXmlSCpt = ex.extractLgConceptNames(xcpt_sFileName, lng);//
			makeVectorOfPresentation(vctNameOfXmlSCpt);
			jlsName.setListData(vctNameOfPresentation);

			if (!vctNameOfXmlSCpt.isEmpty()){
				String sel = vctNameOfXmlSCpt.elementAt(1);
				if (sel.startsWith("<Name_NounCase"))
					jcbTypeOfNameInLng.setSelectedIndex(0);
				else if (sel.startsWith("<Name_NounAdjective"))
					jcbTypeOfNameInLng.setSelectedIndex(1);
				else if (sel.startsWith("<Name_NounAdverb"))
					jcbTypeOfNameInLng.setSelectedIndex(2);
				else if (sel.startsWith("<Name_Verb"))
					jcbTypeOfNameInLng.setSelectedIndex(3);
				else if (sel.startsWith("<Name_Conjunction"))
					jcbTypeOfNameInLng.setSelectedIndex(4);
				else if (sel.startsWith("<Name_Short"))
					jcbTypeOfNameInLng.setSelectedIndex(5);
				else if (sel.startsWith("<Name_Symbol"))
					jcbTypeOfNameInLng.setSelectedIndex(6);
				else if (sel.startsWith("<Name_NounSpecialCase"))
					jcbTypeOfNameInLng.setSelectedIndex(7);
				else if (sel.startsWith("<Name_NounSpecialAdjective"))
					jcbTypeOfNameInLng.setSelectedIndex(8);
				else if (sel.startsWith("<Name_NounSpecialAdverb"))
					jcbTypeOfNameInLng.setSelectedIndex(9);
				jtfTxConceptName.setText(Util.getXmlAttribute(sel,"TxEXP"));
				jtfRule.setText(Util.getXmlAttribute(sel,"TRMrULE"));
				jtfDeleted.setText(Util.getXmlAttribute(sel,"DELETED"));
				jtfCreated.setText(Util.getXmlAttribute(sel,"CREATED"));
				jtfAuthor.setText(Util.getXmlAttribute(sel,"AUTHOR"));
				String amu=Util.getXmlAttribute(sel,"MOSTuSED");
				boolean mu=true;
				if (amu.equals("none")||amu.equals(""))
					mu=false;
				jchMostUsed.setSelected(mu);
			}
			else {
				jcbTypeOfNameInLng.setSelectedIndex(0);
				jtfTxConceptName.setText("");
				jtfRule.setText("");
				jtfDeleted.setText("");
				jtfCreated.setText("");
				jtfAuthor.setText("");
				jchMostUsed.setSelected(false);
			}
		}
	}


	/**
	 * Inserts a lg-concept's--name we created, AFTER the SELECTED
	 * lg-concept's--name in the list.
	 *
	 * @modified 2008.12.04
	 * @since 2001.05.06
	 * @author HoKoNoUmo
	 */
	class ActionInsert extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			String lti=""; //lineToInsert.
			String nmLgCpt = getNameInSrCptIfIndexOfJComboBoxWithNameInLng(jcbTypeOfNameInLng.getSelectedIndex());
			//ppp problem with displayed names.

			//IF vector is empty, add the <lng </lng elements.
			if (vctNameOfXmlSCpt.isEmpty()) {
				vctNameOfXmlSCpt.add("<"+lng +" LASTmOD=\""
												+"\" CREATED=\"" +jtfCreated.getText()
												+"\" AUTHOR=\"" +jtfAuthor.getText() +"\">");
				if (jchMostUsed.isSelected()== true)
					lti="<" +nmLgCpt +" TxEXP=\"" +jtfTxConceptName.getText()
							+"\" MOSTuSED=\"1"
							+"\" TRMrULE=\"" +jtfRule.getText()
							+"\" CREATED=\"" +jtfCreated.getText()
							+"\" AUTHOR=\"" +jtfAuthor.getText() +"\"/>";
				else
					lti="<" +nmLgCpt +" TxEXP=\"" +jtfTxConceptName.getText()
							+"\" TRMrULE=\"" +jtfRule.getText()
							+"\" CREATED=\"" +jtfCreated.getText()
							+"\" AUTHOR=\"" +jtfAuthor.getText() +"\"/>";
				vctNameOfXmlSCpt.add(lti);
				vctNameOfXmlSCpt.add("</"+lng+">");
			}
			else {
				int index = jlsName.getSelectedIndex();
				if (index!=-1)
				{
					if (jchMostUsed.isSelected()== true)
						lti="<" +nmLgCpt +" TxEXP=\"" +jtfTxConceptName.getText()
								+"\" MOSTuSED=\"1"
								+"\" TRMrULE=\"" +jtfRule.getText()
								+"\" CREATED=\"" +jtfCreated.getText()
								+"\" AUTHOR=\"" +jtfAuthor.getText() +"\"/>";
					else
						lti="<" +nmLgCpt +" TxEXP=\"" +jtfTxConceptName.getText()
								+"\" TRMrULE=\"" +jtfRule.getText()
								+"\" CREATED=\"" +jtfCreated.getText()
								+"\" AUTHOR=\"" +jtfAuthor.getText() +"\"/>";
					vctNameOfXmlSCpt.add(index+2,lti);
				}
				else {
					JOptionPane.showMessageDialog(null,
											AAj.rbLabels.getString("fnmgtMsgSelect"));
				}
			}
			makeVectorOfPresentation(vctNameOfXmlSCpt);
			jlsName.setListData(vctNameOfPresentation);
			modified=true;
		}
	}

	/**
	 * Updates the lg-concept's--names of displayed-language both
	 * in sr-concept-file and in term-files.
	 *
	 * @modified 2001.05.14
	 * @since 2001.05.09
	 * @author HoKoNoUmo
	 */
	class ActionUpdate extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			updateNameConceptLgLango();
		}
	}


	/**
	 * By deleting a lg-concept's--name, we just set the deletion-date.
	 *
	 * @modified 2001.05.14
	 * @since 2001.05.09
	 * @author HoKoNoUmo
	 */
	class ActionDelete extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			int index = jlsName.getSelectedIndex();
			if (index!=-1)
			{
				String sel = (String)jlsName.getSelectedValue();
				String nmrLine = vctNameOfXmlSCpt.get(index+1);
				String lngLine = vctNameOfXmlSCpt.get(0);
				nmrLine=Util.setDateDeleted(nmrLine);
				lngLine=Util.setDateLastMod(lngLine);
				vctNameOfXmlSCpt.set(0, lngLine);
				vctNameOfXmlSCpt.set(index+1, nmrLine);

				makeVectorOfPresentation(vctNameOfXmlSCpt);
				jlsName.setListData(vctNameOfPresentation);
				jtfDeleted.setText(Util.getXmlAttribute(nmrLine,"DELETED"));
				modified=true;
			}
			else {
				JOptionPane.showMessageDialog(null,
										AAj.rbLabels.getString("fnmgtMsgSelect"));
			}
		}
	}


	/**
	 * ATTENTION: if you modify the rule or the first-lg-concept's--name,
	 * you have to manange and the previous name-of--b-concept.
	 *
	 * @modified 2008.03.30
	 * @since 2008.03.30 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionModify extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			int index = jlsName.getSelectedIndex();
			if (index!=-1)
			{
				JOptionPane.showMessageDialog(null,
										AAj.rbLabels.getString("fnmgtMsgModify"));
				String sel = (String)jlsName.getSelectedValue();
				String nmrLine = vctNameOfXmlSCpt.get(index+1);

				String lngLine = vctNameOfXmlSCpt.get(0);
				lngLine=Util.setDateLastMod(lngLine);
				vctNameOfXmlSCpt.set(0, lngLine);

				if (jchMostUsed.isSelected()== true)
					nmrLine="<" +getNameInSrCptIfIndexOfJComboBoxWithNameInLng(jcbTypeOfNameInLng.getSelectedIndex())
							+" TxEXP=\"" +jtfTxConceptName.getText()
							+"\" MOSTuSED=\"1"
							+"\" TRMrULE=\"" +jtfRule.getText()
							+"\" DELETED=\"" +jtfDeleted.getText()
							+"\" CREATED=\"" +jtfCreated.getText()
							+"\" AUTHOR=\"" +jtfAuthor.getText() +"\"/>";
				else
					nmrLine="<" +getNameInSrCptIfIndexOfJComboBoxWithNameInLng(jcbTypeOfNameInLng.getSelectedIndex())
							+" TxEXP=\"" +jtfTxConceptName.getText()
							+"\" TRMrULE=\"" +jtfRule.getText()
							+"\" DELETED=\"" +jtfDeleted.getText()
							+"\" CREATED=\"" +jtfCreated.getText()
							+"\" AUTHOR=\"" +jtfAuthor.getText() +"\"/>";
				//toDo: nmrLine=Util.removeEmptyAttributesTerm(nmrLine);
				vctNameOfXmlSCpt.set(index+1, nmrLine);

				makeVectorOfPresentation(vctNameOfXmlSCpt);
				jlsName.setListData(vctNameOfPresentation);
				jtfDeleted.setText(Util.getXmlAttribute(nmrLine,"DELETED"));
				modified=true;
			}
			else {
				JOptionPane.showMessageDialog(null,
										AAj.rbLabels.getString("fnmgtMsgSelect"));
			}
		}
	}


	/**
	 * Delete permanent this lg-concept's--term from sr-concept-file
	 * and term-files.
	 *
	 * @modified 2008.03.27
	 * @since 2008.03.27 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionDeletePermanent extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			int index = jlsName.getSelectedIndex();
			if (index!=-1)
			{
				String xmlNmLng = vctNameOfXmlSCpt.remove(index+1);

				makeVectorOfPresentation(vctNameOfXmlSCpt);
				jlsName.setListData(vctNameOfPresentation);
				if (!vctNameOfXmlSCpt.isEmpty()){
					String sel = vctNameOfXmlSCpt.elementAt(1);
					selectNameInJComboBoxWithNameInLng(sel);
					jtfTxConceptName.setText(Util.getXmlAttribute(sel,"TxEXP"));
					jtfRule.setText(Util.getXmlAttribute(sel,"TRMrULE"));
					jtfDeleted.setText(Util.getXmlAttribute(sel,"DELETED"));
					jtfCreated.setText(Util.getXmlAttribute(sel,"CREATED"));
					jtfAuthor.setText(Util.getXmlAttribute(sel,"AUTHOR"));
				}
				else {
					jcbTypeOfNameInLng.setSelectedIndex(0);
					jtfTxConceptName.setText("");
					jtfRule.setText("");
					jtfDeleted.setText("");
					jtfCreated.setText("");
					jtfAuthor.setText("");
				}

				//remove from sr-concept-file and term-files.
				Insert ins = new Insert();
				ins.removeNameConceptLgLango(xcpt_sFormalID, xmlNmLng, lng);
			}
			else {
				JOptionPane.showMessageDialog(null,
										AAj.rbLabels.getString("fnmgtMsgSelect"));
			}
		}
	}


	/**
	 * @modified 2001.05.14
	 * @since 2001.05.09
	 * @author HoKoNoUmo
	 */
	class ActionUp extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			int index = jlsName.getSelectedIndex();
			if (index!=-1)
			{
				//toDo: if you are changing the first-noun
				//you are changing the name
				//do you want to change its filename?
				Object obj = vctNameOfXmlSCpt.remove(index+1);
				vctNameOfXmlSCpt.add(index,(String)obj);

				//modify <lng lines
				String lgl = vctNameOfXmlSCpt.get(0);
				lgl = Util.setDateLastMod(lgl);
				vctNameOfXmlSCpt.set(0,lgl);

				makeVectorOfPresentation(vctNameOfXmlSCpt);
				jlsName.setListData(vctNameOfPresentation);
				modified=true;
			}
			else {
				JOptionPane.showMessageDialog(null,
										AAj.rbLabels.getString("fnmgtMsgSelect"));
			}
		}
	}


	/**
	 * @modified 2001.05.14
	 * @since 2001.05.09
	 * @author HoKoNoUmo
	 */
	class ActionDown extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			int index = jlsName.getSelectedIndex();
			if (index!=-1)
			{
				//toDo: if you are changing the first-noun
				//you are changing the name
				//do you want to change its filename?
				Object obj = vctNameOfXmlSCpt.remove(index+1);
				vctNameOfXmlSCpt.add(index+2,(String)obj);

				//modify <lng lines
				String lgl = vctNameOfXmlSCpt.get(0);
				lgl = Util.setDateLastMod(lgl);
				vctNameOfXmlSCpt.set(0,lgl);

				makeVectorOfPresentation(vctNameOfXmlSCpt);
				jlsName.setListData(vctNameOfPresentation);
				modified=true;
			}
			else {
				JOptionPane.showMessageDialog(null,
										AAj.rbLabels.getString("fnmgtMsgSelect"));
			}
		}
	}

}

