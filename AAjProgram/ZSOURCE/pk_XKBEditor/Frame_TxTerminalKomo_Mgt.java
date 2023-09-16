/*
 * Frame_TxTerminalKomo_Mgt.java - Komo word creation methods.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2006 Nikos Kasselouris
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

import pk_XKBManager.RetrieveFrame_FindTxConceptTerms;
import pk_XKBManager.AAj;
import pk_Util.Util;
import pk_Util.Extract;

import java.awt.BorderLayout;
import java.awt.Choice;
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
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.TreeSet;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 * Frame_TxTerminalKomo_Mgt.java - Create/Validate Komo-TextualTerminalUnit
 * (= lg-concept's--terms, lg-concept-auxiliaries, iterjections, phatic-words).
 *
 * @modified 2006.11.16
 * @since 2006.11.16 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Frame_TxTerminalKomo_Mgt extends JFrame
{
	static final long serialVersionUID = 21L;

	Choice					choiceSilFrs;
	Choice					choiceSilMid;

	Choice					choiceSilNumber;
//	Choice					choicePrefR;
	Choice					choiceSufR;

	JTextField			fieldPref;
	JTextField			fieldSuf;

	JButton				btCreate;
	JButton				btCreateValidate;
	JTextField			fieldTxWord;
	String					word="";

	String[] 			arSilFrs; 		//first-silabetros
	String[] 			arSilMid; 		//middle-silabetros
	String[] arFonetro={"a","e","i","o","u"}; //to choose a random
	String[] arSimfonetro={"p","f","q","t","s","c","k","h","m","r",
												"b","v","w","d","z","j","g","y","n","l"};

	//VALIDATE
	JTextField			fieldTxWord2;
	JButton				btValidate;
	JTextArea			areaInfo;

	//tx_suffix;leso;WITHOUT;RESERVED. Denotes WITHOUT.
	//leso;RESERVED. Denotes WITHOUT.
	Vector<String> vos = new Vector<String>();
	//leso=WITHOUT
	TreeSet<String> trsetSuf = new TreeSet<String>();

	//tx_prefix;yo;FIRSTNAME;HOMO
	//yo;FIRSTNAME
	Vector<String> vop = new Vector<String>();
	//yo=FIRSTNAME
	TreeSet<String> trsetPre = new TreeSet<String>();

	/**
	 *
	 * @modified 2006.11.16
	 * @since 2006.11.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Frame_TxTerminalKomo_Mgt()
	{
		super(AAj.rbLabels.getString("ftkmTitle"));

		readTxWordKomoInfo();//from TxWordKomo.txt

		//CREATE - PANEL:
		JPanel panelNorth = new JPanel();
		panelNorth.setBorder(BorderFactory.createEtchedBorder());
		panelNorth.setBackground(Color.cyan);
		JLabel labelCreate=new JLabel(AAj.rbLabels.getString("ftkmLCreate"));
		labelCreate.setFont(new Font("SansSerif", Font.BOLD, 18));
		panelNorth.add(labelCreate);

		//panel-choice1 row: uses: first, Middle silabatros
		JPanel panelChoice1 = new JPanel();
		JLabel labelUses = new JLabel(AAj.rbLabels.getString("ftkmLUses"),JLabel.LEFT);
		labelUses.setFont(new Font("SansSerif", Font.BOLD, 18));
		JLabel labelSilFrs = new JLabel(AAj.rbLabels.getString("ftkmLSilFrs"),JLabel.RIGHT);
		labelSilFrs.setFont(new Font("SansSerif", Font.BOLD, 14));
		choiceSilFrs = new Choice();
		choiceSilFrs.setFont(new Font("SansSerif", Font.BOLD, 14));
		choiceSilFrs.setBackground(Color.white);
		for (String s : arSilFrs)
			choiceSilFrs.add(s);
		JLabel labelSilMid = new JLabel(AAj.rbLabels.getString("ftkmLSilMid"),JLabel.RIGHT);
		labelSilMid.setFont(new Font("SansSerif", Font.BOLD, 14));
		choiceSilMid = new Choice();
		choiceSilMid.setFont(new Font("SansSerif", Font.BOLD, 14));
		choiceSilMid.setBackground(Color.white);
		for (String s : arSilMid)
			choiceSilMid.add(s);
		panelChoice1.add(labelUses);
		panelChoice1.add(labelSilFrs);
		panelChoice1.add(choiceSilFrs);
		panelChoice1.add(labelSilMid);
		panelChoice1.add(choiceSilMid);

		//panel-choice2 row: choose number of silabetros, pref, suf
		JPanel panelChoice2 = new JPanel();
		JLabel labelChoose = new JLabel(AAj.rbLabels.getString("ftkmLChoose"),JLabel.LEFT);
		labelChoose.setFont(new Font("SansSerif", Font.BOLD, 18));
		JLabel labelSilNum = new JLabel(AAj.rbLabels.getString("ftkmLSilNum"),JLabel.RIGHT);
		labelSilNum.setFont(new Font("SansSerif", Font.BOLD, 14));
		choiceSilNumber = new Choice();
		choiceSilNumber.setFont(new Font("SansSerif", Font.BOLD, 18));
		choiceSilNumber.setBackground(Color.white);
		choiceSilNumber.add("1");
		choiceSilNumber.add("2");
		choiceSilNumber.add("3");
		choiceSilNumber.add("4");
		choiceSilNumber.add("5");
		choiceSilNumber.add("6");
		choiceSilNumber.add("7");
		choiceSilNumber.add("     ");//to increase the width of choice.
		choiceSilNumber.select("3");
		JLabel labelPrefiksetroR = new JLabel(AAj.rbLabels.getString("ftkmLPref"),JLabel.RIGHT);
		labelPrefiksetroR.setFont(new Font("SansSerif", Font.BOLD, 14));
//		choicePrefR = new Choice();
//		choicePrefR.setFont(new Font("SansSerif", Font.BOLD, 14));
//		choicePrefR.setBackground(Color.white);
//		choicePrefR.add("");
//		for (String s : trsetPre)
//			choicePrefR.add(s);
		JLabel labelSufiksetroR = new JLabel(AAj.rbLabels.getString("ftkmLSuf"),JLabel.RIGHT);
		labelSufiksetroR.setFont(new Font("SansSerif", Font.BOLD, 14));
		choiceSufR = new Choice();
		choiceSufR.setFont(new Font("SansSerif", Font.BOLD, 14));
		choiceSufR.setBackground(Color.white);
		choiceSufR.add("");
		for (String s : trsetSuf)
			choiceSufR.add(s);
		panelChoice2.add(labelChoose);
		panelChoice2.add(labelSilNum);
		panelChoice2.add(choiceSilNumber);
		panelChoice2.add(labelPrefiksetroR);
//		panelChoice2.add(choicePrefR);
		panelChoice2.add(labelSufiksetroR);
		panelChoice2.add(choiceSufR);

		//panel-choice3 row: write tx_prefix, tx_suffix
		JPanel panelChoice3 = new JPanel();
		JLabel labelWrite = new JLabel(AAj.rbLabels.getString("ftkmLWrite"),JLabel.LEFT);
		labelWrite.setFont(new Font("SansSerif", Font.BOLD, 18));
		JLabel labelPref = new JLabel(AAj.rbLabels.getString("ftkmLPref"),JLabel.RIGHT);
		labelPref.setFont(new Font("SansSerif", Font.BOLD, 14));
		fieldPref = new JTextField(7);
		fieldPref.addActionListener(new ActionCreateValidate());
		fieldPref.setFont(new Font("SansSerif", Font.BOLD, 14));
		JLabel labelSuf = new JLabel(AAj.rbLabels.getString("ftkmLSuf"),JLabel.RIGHT);
		labelSuf.setFont(new Font("SansSerif", Font.BOLD, 14));
		fieldSuf = new JTextField(7);
		fieldSuf.addActionListener(new ActionCreateValidate());
		fieldSuf.setFont(new Font("SansSerif", Font.BOLD, 14));
		panelChoice3.add(labelWrite);
		panelChoice3.add(labelPref);
		panelChoice3.add(fieldPref);
		panelChoice3.add(labelSuf);
		panelChoice3.add(fieldSuf);

		//panel choice:
		JPanel panelChoice = new JPanel(new BorderLayout());
		panelChoice.add(panelChoice1, BorderLayout.NORTH);
		panelChoice.add(panelChoice2, BorderLayout.CENTER);
		panelChoice.add(panelChoice3, BorderLayout.SOUTH);

		JPanel panelJButton = new JPanel();
		btCreate = new JButton(AAj.rbLabels.getString("ftkmCreate"));
		btCreate.setFont(new Font("SansSerif", Font.BOLD, 16));
		btCreate.addActionListener(new ActionCreate());
		btCreateValidate = new JButton(AAj.rbLabels.getString("ftkmCreateValidate"));
		btCreateValidate.setFont(new Font("SansSerif", Font.BOLD, 16));
		btCreateValidate.addActionListener(new ActionCreateValidate());
		panelJButton.add(btCreate);
		panelJButton.add(btCreateValidate);

		JPanel panelTxWord = new JPanel();
		JLabel labelTxWord = new JLabel(AAj.rbLabels.getString("ftkmTxWord"),JLabel.RIGHT);
		labelTxWord.setFont(new Font("SansSerif", Font.BOLD, 18));
		fieldTxWord = new JTextField(22);
		fieldTxWord.setBackground(Color.lightGray);
		fieldTxWord.setFont(new Font("SansSerif", Font.BOLD, 22));
		fieldTxWord.setForeground(Color.blue);
		panelTxWord.add(labelTxWord);
		panelTxWord.add(fieldTxWord);

		JPanel panelCenter = new JPanel(new BorderLayout());
//		panelSouth.setBorder(BorderFactory.createEtchedBorder());
		panelCenter.add(panelChoice, BorderLayout.NORTH);
		panelCenter.add(panelJButton, BorderLayout.CENTER);
		panelCenter.add(panelTxWord, BorderLayout.SOUTH);

		JPanel panelSouth = new JPanel();
		JLabel labelEmpty = new JLabel("         ");
		labelEmpty.setFont(new Font("SansSerif", Font.BOLD, 9));
		panelSouth.add(labelEmpty);

		JPanel panelCreate = new JPanel(new BorderLayout());
//		panelCreate.setBorder(BorderFactory.createEtchedBorder());
		panelCreate.add(panelNorth, BorderLayout.NORTH);
		panelCreate.add(panelCenter, BorderLayout.CENTER);
		panelCreate.add(panelSouth, BorderLayout.SOUTH);

		//VALIDATE - PANEL:
		JPanel panelNorth2 = new JPanel();
		panelNorth2.setBorder(BorderFactory.createEtchedBorder());
		panelNorth2.setBackground(Color.cyan);
		JLabel labelValidate=new JLabel(AAj.rbLabels.getString("ftkmLValidate"));
		labelValidate.setFont(new Font("SansSerif", Font.BOLD, 18));
		panelNorth2.add(labelValidate);

		JPanel panelTxWord2 = new JPanel();
		JLabel labelTxWord2 = new JLabel(AAj.rbLabels.getString("ftkmTxWord2"),JLabel.RIGHT);
		labelTxWord2.setFont(new Font("SansSerif", Font.BOLD, 18));
		fieldTxWord2 = new JTextField(22);
		fieldTxWord2.setBackground(Color.lightGray);
		fieldTxWord2.setFont(new Font("SansSerif", Font.BOLD, 22));
		fieldTxWord2.setForeground(Color.blue);
		fieldTxWord2.addActionListener(new ActionValidate());
		panelTxWord2.add(labelTxWord2);
		panelTxWord2.add(fieldTxWord2);

		JPanel panelJButton2 = new JPanel();
		btValidate = new JButton(AAj.rbLabels.getString("ftkmValidate"));
		btValidate.setFont(new Font("SansSerif", Font.BOLD, 16));
		btValidate.addActionListener(new ActionValidate());
		panelJButton2.add(btValidate);

		JPanel panelInfo = new JPanel();
//		panelInfo.setBorder(BorderFactory.createEtchedBorder());
//    panelInfo.setBackground(Color.blue);
		areaInfo = new JTextArea();
		JScrollPane scroller = new JScrollPane(){
			static final long serialVersionUID = 21L;
			public Dimension getPreferredSize() {
				return new Dimension(579,279);
			}
		};
		JViewport port = scroller.getViewport();
		areaInfo.setFont(new Font("arial", Font.PLAIN, 14));
		areaInfo.setTabSize(5);
		port.add(areaInfo);
		panelInfo.add(scroller);

		JPanel panelCenter2 = new JPanel(new BorderLayout());
//		panelSouth2.setBorder(BorderFactory.createEtchedBorder());
		panelCenter2.add(panelTxWord2, BorderLayout.NORTH);
		panelCenter2.add(panelJButton2, BorderLayout.CENTER);

		JPanel panelValidate = new JPanel(new BorderLayout());
//		panelValidate.setBorder(BorderFactory.createEtchedBorder());
		panelValidate.add(panelNorth2, BorderLayout.NORTH);
		panelValidate.add(panelCenter2, BorderLayout.CENTER);
		panelValidate.add(panelInfo, BorderLayout.SOUTH);

		Box panelMain = new Box(BoxLayout.Y_AXIS);
		panelMain.add(panelCreate);
		panelMain.add(panelValidate);

		getContentPane().add(panelMain, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		pack();
		Util.setFrameIcon(this);
		setLocation(226,0);
		setSize(new Dimension(759, 709));
		setVisible(true);
	}


	/**
	 *
	 * @modified 2006.11.16
	 * @since 2006.11.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionCreate extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			create();
		}
	}


	/**
	 *
	 * @modified 2006.11.17
	 * @since 2006.11.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionValidate extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			word = fieldTxWord2.getText().toLowerCase();
			validate(word);
		}
	}


	/**
	 *
	 * @modified 2006.11.18
	 * @since 2006.11.18 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	class ActionCreateValidate extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			create();
			word = fieldTxWord2.getText().toLowerCase();
			validate(word);
		}
	}


	/**
	 *
	 * @modified 2008.03.12
	 * @since 2008.03.12 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void create()
	{
		String pfks=""; //tx_prefix written/chosen
		String sfks="";

		String s1="";
		String s2="";
		String s3="";
		String s4="";
		String s5="";
		String s6="";
		String s7="";
		String s8="";

		String csi = "";
//		csi = choicePrefR.getSelectedItem();
//		if (!csi.equals(""))
//			pfks=csi.substring(0,csi.indexOf("=")).toLowerCase();
		//written comes first than selected
		if (!fieldPref.getText().equals(""))
			pfks=fieldPref.getText();
		csi = choiceSufR.getSelectedItem();
		if (!csi.equals(""))
			sfks=csi.substring(0,csi.indexOf("=")).toLowerCase();
		if (!fieldSuf.getText().equals(""))
			sfks=fieldSuf.getText();

		s1=peakSilabetro(1);
		s2=peakSilabetro(2);
		s3=peakSilabetro(2);
		s4=peakSilabetro(2);
		s5=peakSilabetro(2);
		s6=peakSilabetro(2);
		s7=peakSilabetro(2);
		s8=peakSilabetro(2);

		//***************************************************************
		if (choiceSilNumber.getSelectedItem().equals("1"))
		{
			//always peak
			setTxWord(s1);
		}
		//****************************************************************
		else if (choiceSilNumber.getSelectedItem().equals("2"))
		{
			if (pfks.equals("") && sfks.equals(""))
				setTxWord(s1+s2);
			else if (!pfks.equals("") && sfks.equals(""))
				if (	findSilabetroNumber(pfks)>1)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError1"));
				else
					setTxWord(pfks+s2);
			else if (pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(sfks)>1)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError2"));
				else
					setTxWord(s1+sfks);
			else if (!pfks.equals("") && !sfks.equals(""))
				fieldTxWord.setText(AAj.rbLabels.getString("ftkmError3"));
		}
		//*****************************************************************
		else if (choiceSilNumber.getSelectedItem().equals("3"))
		{
			if (pfks.equals("") && sfks.equals(""))
				setTxWord(s1+s2+s3);

			else if (!pfks.equals("") && sfks.equals(""))
				if (	findSilabetroNumber(pfks)>2)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError1"));
				else
					if (	findSilabetroNumber(pfks)==1)
						setTxWord(pfks+s2+s3);
					else
						setTxWord(pfks+s3);

			else if (pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(sfks)>2)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError2"));
				else
					if (	findSilabetroNumber(sfks)==1)
						setTxWord(s1+s2+sfks);
					else
						setTxWord(s1+sfks);

			else if (!pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(pfks)+findSilabetroNumber(sfks)>2)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError3"));
				else
					setTxWord(pfks+s2+sfks);
		}
		//*****************************************************************
		else if (choiceSilNumber.getSelectedItem().equals("4"))
		{
			if (pfks.equals("") && sfks.equals(""))
				setTxWord(s1+s2+s3+s4);

			else if (!pfks.equals("") && sfks.equals(""))
				if (	findSilabetroNumber(pfks)>3)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError1"));
				else
					if (	findSilabetroNumber(pfks)==1)
						setTxWord(pfks+s2+s3+s4);
					else if (	findSilabetroNumber(pfks)==2)
						setTxWord(pfks+s3+s4);
					else
						setTxWord(pfks+s4);

			else if (pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(sfks)>3)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError2"));
				else
					if (	findSilabetroNumber(sfks)==1)
						setTxWord(s1+s2+s3+sfks);
					else if (	findSilabetroNumber(sfks)==2)
						setTxWord(s1+s2+sfks);
					else
						setTxWord(s1+sfks);

			else if (!pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(pfks)+findSilabetroNumber(sfks)>3)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError3"));
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==2)
					setTxWord(pfks+s2+sfks);
				else if (findSilabetroNumber(pfks)==2 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+sfks);
				else
					setTxWord(pfks+s2+s3+sfks);
		}
		//*****************************************************************
		else if (choiceSilNumber.getSelectedItem().equals("5"))
		{
			if (pfks.equals("") && sfks.equals(""))
				setTxWord(s1+s2+s3+s4+s5);

			else if (!pfks.equals("") && sfks.equals(""))
				if (	findSilabetroNumber(pfks)>4)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError1"));
				else
					if (	findSilabetroNumber(pfks)==1)
						setTxWord(pfks+s2+s3+s4+s5);
					else if (	findSilabetroNumber(pfks)==2)
						setTxWord(pfks+s3+s4+s5);
					else if (	findSilabetroNumber(pfks)==3)
						setTxWord(pfks+s4+s5);
					else
						setTxWord(pfks+s5);

			else if (pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(sfks)>4)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError2"));
				else
					if (	findSilabetroNumber(sfks)==1)
						setTxWord(s1+s2+s3+s4+sfks);
					else if (	findSilabetroNumber(sfks)==2)
						setTxWord(s1+s2+s3+sfks);
					else if (	findSilabetroNumber(sfks)==3)
						setTxWord(s1+s2+sfks);
					else
						setTxWord(s1+sfks);

			else if (!pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(pfks)+findSilabetroNumber(sfks)>4)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError3"));
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+s4+sfks);
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==2)
					setTxWord(pfks+s2+s3+sfks);
				else if (findSilabetroNumber(pfks)==2 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+sfks);
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==3)
					setTxWord(pfks+s2+sfks);
				else if (findSilabetroNumber(pfks)==3 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+sfks);
				else
					setTxWord(pfks+s2+sfks);
		}
		//*****************************************************************
		else if (choiceSilNumber.getSelectedItem().equals("6"))
		{
			if (pfks.equals("") && sfks.equals(""))
				setTxWord(s1+s2+s3+s4+s5+s6);

			else if (!pfks.equals("") && sfks.equals(""))
				if (	findSilabetroNumber(pfks)>5)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError1"));
				else
					if (	findSilabetroNumber(pfks)==1)
						setTxWord(pfks+s2+s3+s4+s5+s6);
					else if (	findSilabetroNumber(pfks)==2)
						setTxWord(pfks+s3+s4+s5+s6);
					else if (	findSilabetroNumber(pfks)==3)
						setTxWord(pfks+s4+s5+s6);
					else if (	findSilabetroNumber(pfks)==4)
						setTxWord(pfks+s5+s6);
					else
						setTxWord(pfks+s6);

			else if (pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(sfks)>5)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError2"));
				else
					if (	findSilabetroNumber(sfks)==1)
						setTxWord(s1+s2+s3+s4+s5+sfks);
					else if (	findSilabetroNumber(sfks)==2)
						setTxWord(s1+s2+s3+s4+sfks);
					else if (	findSilabetroNumber(sfks)==3)
						setTxWord(s1+s2+s3+sfks);
					else if (	findSilabetroNumber(sfks)==4)
						setTxWord(s1+s2+sfks);
					else
						setTxWord(s1+sfks);

			else if (!pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(pfks)+findSilabetroNumber(sfks)>4)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError3"));
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+s4+s5+sfks);
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==2)
					setTxWord(pfks+s2+s3+s4+sfks);
				else if (findSilabetroNumber(pfks)==2 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+s4+sfks);
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==3)
					setTxWord(pfks+s2+s3+sfks);
				else if (findSilabetroNumber(pfks)==3 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+sfks);
				else if (findSilabetroNumber(pfks)==2 &&
								findSilabetroNumber(sfks)==2)
					setTxWord(pfks+s2+s3+sfks);
				else
					setTxWord(pfks+s2+sfks);//1+4=4+1
		}
		//*****************************************************************
		else if (choiceSilNumber.getSelectedItem().equals("7"))
		{
			if (pfks.equals("") && sfks.equals(""))
				setTxWord(s1+s2+s3+s4+s5+s6+s7);

			else if (!pfks.equals("") && sfks.equals(""))
				if (	findSilabetroNumber(pfks)>6)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError1"));
				else
					if (	findSilabetroNumber(pfks)==1)
						setTxWord(pfks+s2+s3+s4+s5+s6+s7);
					else if (	findSilabetroNumber(pfks)==2)
						setTxWord(pfks+s3+s4+s5+s6+s7);
					else if (	findSilabetroNumber(pfks)==3)
						setTxWord(pfks+s4+s5+s6+s7);
					else if (	findSilabetroNumber(pfks)==4)
						setTxWord(pfks+s5+s6+s7);
					else if (	findSilabetroNumber(pfks)==5)
						setTxWord(pfks+s6+s7);
					else
						setTxWord(pfks+s7);

			else if (pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(sfks)>6)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError2"));
				else
					if (	findSilabetroNumber(sfks)==1)
						setTxWord(s1+s2+s3+s4+s5+s6+sfks);
					else if (	findSilabetroNumber(sfks)==2)
						setTxWord(s1+s2+s3+s4+s5+sfks);
					else if (	findSilabetroNumber(sfks)==3)
						setTxWord(s1+s2+s3+s4+sfks);
					else if (	findSilabetroNumber(sfks)==4)
						setTxWord(s1+s2+s3+sfks);
					else if (	findSilabetroNumber(sfks)==5)
						setTxWord(s1+s2+sfks);
					else
						setTxWord(s1+sfks);

			else if (!pfks.equals("") && !sfks.equals(""))
				if (findSilabetroNumber(pfks)+findSilabetroNumber(sfks)>4)
					fieldTxWord.setText(AAj.rbLabels.getString("ftkmError3"));
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+s4+s5+s6+sfks);
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==2)
					setTxWord(pfks+s2+s3+s4+s5+sfks);
				else if (findSilabetroNumber(pfks)==2 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+s4+s5+sfks);
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==3)
					setTxWord(pfks+s2+s3+s4+sfks);
				else if (findSilabetroNumber(pfks)==3 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+s4+sfks);
				else if (findSilabetroNumber(pfks)==2 &&
								findSilabetroNumber(sfks)==2)
					setTxWord(pfks+s2+s3+s4+sfks);
				else if (findSilabetroNumber(pfks)==1 &&
								findSilabetroNumber(sfks)==4)
					setTxWord(pfks+s2+s3+sfks);
				else if (findSilabetroNumber(pfks)==4 &&
								findSilabetroNumber(sfks)==1)
					setTxWord(pfks+s2+s3+sfks);
				else
					setTxWord(pfks+s2+sfks);//1+5=5+1=2+3=3+2
		}
	}


	/**
	 * It finds if a t_unit_structure can be a komo t_word:<br/>
	 * 1) It finds if uses a reserved tx_prefix.<br/>
	 * 2) It finds if uses a reserved tx_suffix.<br/>
	 * 3) It finds if the tx_unit_structure is already a komo word.<br/>
	 * 4) It finds if it has valid silabetros.
	 *
	 * @modified 2008.03.12
	 * @since 2008.03.12 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void validate(String t_unit_structure)
	{
			String[] ar;
			areaInfo.setText("");

			//1. check tx_prefixs
			for (String s : vop){
				ar = s.split(";");
				if (t_unit_structure.startsWith(ar[0])){
					areaInfo.append("-"+ar[0]
						+" (TxPREFIX): RESERVED denotes "+ar[1]
						+"\n");
				}
			}

			//2. check tx_suffixs.
			//Displays all reserved-tx_suffixs that match the ending
			//of the t_unit_structure from big to small.
			for (String s : vos){
				ar = s.split(";");
				if (t_unit_structure.endsWith(ar[0])){
					areaInfo.append("\n\n-"+ar[0]+"  (TxSUFFIX):  ");
					String[] ar2 = ar[1].split("#");
					areaInfo.append(ar2[0]);
					for(int i=1; i<ar2.length; i++)
					{
						areaInfo.append("\n\t"+ar2[i]);
					}
				}
				//Displays and all tx_suffixs end with 2 last diktetros
				//of the t_unit_structure
				if (ar[0].endsWith(t_unit_structure.substring(t_unit_structure.length()-2)))
					if (!t_unit_structure.endsWith(ar[0]))
						areaInfo.append("\n  -"+ar[0]+"  (tx_suffix-related).  "
														+ar[1]);
			}

			//3. check if it is a termero.
			BufferedReader br=null;
			String line=null;
			boolean found=false;
			//check if it is an lg-concept's--auxiliary.
			String fileTerminal=Util.AAj_sDir+"AAjKB" +File.separator
						+"AAjINDEXES" +File.separator
						+"kml" +File.separator +"index_terminal_kml.xml";
			try {
				br = new BufferedReader(new FileReader(fileTerminal));
				while ((line=br.readLine())!=null && !found){
					if (line.startsWith("<TRMNL")){
						line = Util.getXmlAttribute(line,"TxEXP");
						if (line.equals(t_unit_structure)){
							found=true;
							areaInfo.append("\n\n=>"+t_unit_structure
							+" is ALREADY a komo LG-CONCEPT-AUXILIARY. Find another one.");
							break;
						}
					}
					else if (line.startsWith("<IJR")){
						line = Util.getXmlAttribute(line,"TxEXP");
						if (line.equals(t_unit_structure)){
							found=true;
							areaInfo.append("\n\n=>"+t_unit_structure
							+" is ALREADY a komo INTERJECTERO. Find another one.");
							break;
						}
					}
					else if (line.startsWith("<FTR")){
						line = Util.getXmlAttribute(line,"TxEXP");
						if (line.equals(t_unit_structure)){
							found=true;
							areaInfo.append("\n\n=>"+t_unit_structure
							+" is ALREADY a komo FATERO. Find another one.");
							break;
						}
					}
					//Term-noun-conjunction-verb
					else if (line.startsWith("<TRMNL")){
						line = Util.getXmlAttribute(line,"TxEXP");
						if (line.equals(t_unit_structure)){
							found=true;
							areaInfo.append("\n\n=>"+t_unit_structure
							+" is ALREADY a komo TERM. Find another one.");
							break;
						}
					}
				}
				br.close();
			}
			catch (IOException ioe){
				System.out.println("FrameYKM.validate ioe: fileTerminal");
			}

			if (!found){
				//check if it is a term
				String nmf = Util.getFullTermFile(t_unit_structure,"kml");
				File fileTerm = new File(nmf);
				if (fileTerm.exists()) {
					try {
						br = new BufferedReader(new FileReader(nmf));
						while ((line=br.readLine())!=null){
							line = Util.getXmlAttribute(line,"TxEXP");
							if (line.equals(t_unit_structure)){
								found=true;
								areaInfo.append("\n\n"+t_unit_structure
								+" is ALREADY a komo t_word. Find another one.");
								break;
							}
						}
						if (!found)
							areaInfo.append("\n\n" +t_unit_structure
						+" is NOT a komo t_word.");
					}
					catch (IOException ioe){
						System.out.println("FYKManagement ioe: " +fileTerm);
					}
				}
				else {
					areaInfo.append("\n\n" +t_unit_structure +" is NOT a komo t_word.");
				}
			}

			//4. check silabetros
			String arslb[] = findSilabetrosAsArray(t_unit_structure);
			String rp = arslb[0].replaceAll("[aeiou]","?");
			if (!Util.arrayStringContains(arSilFrs, rp))
				areaInfo.append("\n\n" +t_unit_structure +" has NOT a valid FIRST silabetro: "
														+arslb[0]);
			for (int j = 1; j < arslb.length; j ++){
				rp = arslb[j].replaceAll("[aeiou]","?");
				if (!Util.arrayStringContains(arSilMid, rp))
					areaInfo.append("\n\n" +t_unit_structure +" has NOT a valid MIDDLE silabetro: "
													+arslb[j]);
			}
	}


	/**
	 * Every fonetro is the end of a silabetro.
	 *
	 * @modified 2006.11.21
	 * @since 2006.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String[] findSilabetrosAsArray(String lktr){
		String ssl=findSilabetrosAsString(lktr);
		return ssl.split("-");
	}


	/**
	 * <b>INPUT</b>: a t_unit_structure.<br/>
	 * <b>OUTPUT</b>: a string with the silabetros: "si-la-be-tro"
	 *
	 * @modified 2006.11.21
	 * @since 2006.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String findSilabetrosAsString(String lktr){
		String sil="";
		String sout="";
		int e = lktr.length();
		for (int i=0; i<e; i++){
			String c = lktr.substring(0,1);
			lktr = lktr.substring(1);
			if (isFonetro(c)){
				sil=sil+c;
				sout=sout+"-"+sil;
				sil="";
			}
			else
				sil=sil+c;
		}
		if (!sil.equals(""))
			sout=sout+sil;
		return sout.substring(1);
	}


	/**
	 *
	 * @modified 2006.11.21
	 * @since 2006.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int findSilabetroNumber(String t_unit_structure){
		String[] sil = findSilabetrosAsArray(t_unit_structure);
		return sil.length;
	}


	/**
	 *
	 * @modified 2006.11.16
	 * @since 2006.11.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isFonetro(char c)
	{
		if (c=='a' ||
				c=='e' ||
				c=='i' ||
				c=='o' ||
				c=='u' )
			return true;
		else
			return false;
	}


	/**
	 *
	 * @modified 2006.11.20
	 * @since 2006.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isFonetro(String c)
	{
		if (c.equals("a") ||
				c.equals("e") ||
				c.equals("i") ||
				c.equals("o") ||
				c.equals("u") ||
				c.equals("?"))//2006.11.22
			return true;
		else
			return false;
	}


	/**
	 * <b>INPUT</b>: integer 1 or 2. Denotes from which array of silabetros
	 * to peak: 1=First-Silabetro, 2=Middle-Silabetro.<p>
	 *
	 * Komo does not have different end-silabetros. All worderos end
	 * with a fonero (verbs loose the last -o of its nouns).
	 *
	 * @modified 2006.11.17
	 * @since 2006.11.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String peakSilabetro(int j)
	{
		String ss="";
		if (j==1){
			ss=arSilFrs[roll(arSilFrs)];
		}
		else if (j==2){
			ss=arSilMid[roll(arSilMid)];
		}
		if (ss.indexOf("?")!=-1)
			ss=ss.replace('?',arFonetro[roll(arFonetro)].charAt(0));
		if (ss.indexOf("!")!=-1)
			ss=ss.replace('!',arSimfonetro[roll(arSimfonetro)].charAt(0));
		return ss;
	}


	/**
	 * <b>INPUT</b>: t_unit_structure1, t_unit_structure2.<br/>
	 * <b>OUTPUT</b>: one new t_unit_structure.<br/>
	 *
	 * METHOD:<br/>
	 * 1) IF the merge creates an accepted silabetro
	 * (permissible combination), leave it as it is.<br/>
	 * 2) DOES permissible TRANSORMATIONS:<br/>
	 * 	bt ==> mt<br/>
	 * 	sm ==> zm<br/>
	 * 3) Otherwise, adds -o.
	 *
	 * @modified 2006.11.17
	 * @since 2006.11.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mergeTUnitStructures(String lx1, String lx2)
	{
		String out="";
		String[] arLx1S=findSilabetrosAsArray(lx1);//array of lx1-silabetros
		String lx1SL=arLx1S[arLx1S.length-1];//the last-silabetro of lx1
		String lx1SL2 = lx1SL.replaceAll("[aeiou]","?");//with ? as fonero
		String lx1KLast = ""; //last-klastero of last syllable of lx1
		if (lx1SL2.startsWith("?"))
			if (!lx1SL2.endsWith("?")) lx1KLast=lx1SL2.substring(lx1SL2.indexOf("?")+1);

		String[] arLx2S=findSilabetrosAsArray(lx2);//array of lx2-silabetros
		String lx2SF=arLx2S[0];//the first-silabetro of lx2
		String lx2SF2 = lx2SF.replaceAll("[aeiou]","?");//with ? as fonero
		String lx2KFirst = ""; //first-klastero of first syllable of lx2
		if (!lx2SF2.startsWith("?"))
			if (!lx2SF2.endsWith("?")) lx2KFirst=lx2SF2.substring(0,lx2SF2.indexOf("?"));

		String kNew = lx1KLast+lx2KFirst;


		//CASE1: t_unit_structure1 ends with fonetro
		if (lx1KLast.equals("")){
			return lx1+lx2; //sto+lango==>stolango
			//we asume that the second t_unit_structure is valid.
		}

		//CASE2: TRANSFORMATIONS
		else {
			//bt ==> mt,
			//sm ==> zm<
			if (kNew.startsWith("bt"))
				return lx1.substring(0,lx1.length()-1)+"m"+lx2;
			if (kNew.startsWith("sm"))
				return lx1.substring(0,lx1.length()-1)+"z"+lx2;


			//CASE3: ACCEPTED KLASTETRO ==> leave it
			if (Util.arrayStringContains(arSilMid, kNew+"?"))
				return lx1+lx2;
			else
			//CASE4: NON ACCEPTED LASTERO ==> add "o"
				return lx1+"o"+lx2;
		}

		//onom-ero ==> onom-ant-ero ==> onomantero 2006.12.02
		//verb-ero ==> verb-al(o)-ero ==> verb's-argument 2006.12.02
		//emoc-epto ==> emoc-no-epto ==> emoconoepto 2006.12.02
		//brain-ufino ==> brain-no-ufino ==> brainoufino 2006.12.02

		//IF we use and tx_prefixs we decrease too much the number
		//of words we can create. 2006.11.25
	}


	/**
	 *
	 * @modified 2006.11.17
	 * @since 2006.11.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void readTxWordKomoInfo(){
		String line;
		String[] ar;
		Vector<String> vsf = new Vector<String>();//vector of first-silab
		Vector<String> vsm = new Vector<String>();
		String fileIn = AAj.AAj_sDir +"AAjKB" +File.separator
								+"AAjINDEXES" +File.separator
								+"kml_affix.txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileIn));
			while ((line=br.readLine())!=null) {
				if (line.startsWith("silFrs")) {
					ar = line.split(";");
					vsf.add(ar[1]);
				}
				else if (line.startsWith("silMid")) {
					ar = line.split(";");
					vsm.add(ar[1]);
				}
				else if (line.startsWith("tx_suffix")) {
					if (line.indexOf("RESERVED")!=-1){
						ar = line.split(";");
						if (ar.length<3)
							System.out.println("==> TxWordKomo.txt: less than 3 (;) "+line);
						vos.add(ar[1]+";"+ar[3]);
						//tx_suffix;leso;WITHOUT;RESERVED. Denotes WITHOUT.
						//leso;RESERVED. Denotes WITHOUT.
						trsetSuf.add(ar[1]+"="+ar[2]);
						//leso=WITHOUT
					}
				}
				else if (line.startsWith("tx_prefix")) {
					ar = line.split(";");
					trsetPre.add(ar[1]+"="+ar[2]);
					vop.add(ar[1]+";"+ar[2]);//the denoted
				}
			}
			br.close();
		}
		catch (FileNotFoundException fnfe)	{
			JOptionPane.showMessageDialog(null,
				"Frame_TxTerminalKomo_Mgt: " +fileIn,
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		catch (IOException e)	{
			System.out.println(e.toString());
		}

		arSilFrs = new String[vsf.size()];
		arSilFrs = vsf.toArray(arSilFrs);
		arSilMid = new String[vsm.size()];
		for(int j=0; j<vsm.size(); j++)
		{
			arSilMid[j]=vsm.get(j);
		}

		//the tx_prefixs and tx_suffixs must have
		//VALID silabetros.

	}


	/**
	 * Peeks an element of an array.
	 * @modified 2006.11.16
	 * @since 2006.11.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int roll(String[] ar)
	{
//      Random rd = new Random();
//      value=rd.nextInt(501);
		int value=0;
		double p = Math.random();
		double sum = 0;
		for (int i = 0; i < ar.length; i++)
		{
			if (sum < p & p <= sum + (1.0/ar.length)) value = i;
			sum = sum + (1.0/ar.length);
		}
//System.out.println(value +", arraylength=" +ar.length);//+s1[0] +","+s2 +","+s3 +","+sfks);
		return value;
	}


	/**
	 *
	 * @modified 2006.11.21
	 * @since 2006.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean startsWithFonetro(String lktr)
	{
		if (lktr.startsWith("a") ||
				lktr.startsWith("e") ||
				lktr.startsWith("i") ||
				lktr.startsWith("o") ||
				lktr.startsWith("u") )
			return true;
		else
			return false;
	}

	/**
	 * Set t_word on textfields.
	 *
	 * @modified 2008.03.15
	 * @since 2008.03.15 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void setTxWord(String yd)
	{
		fieldTxWord.setText(findSilabetrosAsString(yd));
		fieldTxWord2.setText(yd);
	}

}

