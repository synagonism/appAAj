/*
 * RetrieveFrame_Translation.java - Maps Text to Semasia and vice versa.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2004 Nikos Kasselouris
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
package pk_XKBManager;

import pk_Util.*;
import pk_SSemasia.*;
import pk_Logo.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.IOException;

import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Maps Text to Semasia and vice versa.
 *
 * @modified 2004.11.15
 * @since 2001.09.22 (v00.02.00)
 * @author HoKoNoUmo
 */
public class RetrieveFrame_Translation extends JFrame
{
	static final long serialVersionUID = 21L;
	JButton 		btnTranslate;
	JButton		btnClearIn, btnClearOut;

	JComboBox 	cboxIn;
	JComboBox 	cboxOut;
	JComboBox 	jcbLangIn;
	JComboBox 	jcbLangOut;

	JTextArea		areaIn, areaOut;


	/**
	 * The constructor.
	 *
	 * @modified
	 * @since 2001.09.22
	 * @author HoKoNoUmo
	 */
	public RetrieveFrame_Translation()
	{
		super("SrSemasia & Text Mapping");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {dispose();}
		});

		Box box = new Box(BoxLayout.Y_AXIS);

		//input panel
		JPanel panelIn = new JPanel();
		panelIn.setBorder(new TitledBorder(LineBorder.createBlackLineBorder()," Input ",TitledBorder.LEFT,TitledBorder.TOP));
		areaIn = new JTextArea();
		JScrollPane scroller = new JScrollPane(){
			static final long serialVersionUID = 21L;
			public Dimension getPreferredSize() {
				return new Dimension(759,99);
			}
		};
		areaIn.setText("Concept, is called (every tree structure node of a "
									+"b-conceptual--sub-worldview).");
/*
		areaIn.setText("έννοια ονομάζεται κάθε....");
		areaIn.setText("Concept is called every hierarchy-node of a concept-model.");
		areaIn.setText("<SSMA LABEL=\"ex1\" LNG=\"eng\">");
		areaIn.append("\n  <!-- Concept is called every node of a concept-model -->");
		areaIn.append("\n  <SmSentence LABEL=\"s1\">");
		areaIn.append("\n    <SmVerb LABEL=\"x1\" TRM=\"Naming@hknu.symb-17\" SMaTTR=\"ind,pre,ins,pass,iper,ning,aff,sin,3\"/>");
		areaIn.append("\n    <SmNoun LABEL=\"x2\" TRM=\"Person@hknu.symb-12\" INSTANCE=\"nikkas\" SMaTTR=\"individual,sin\"/>");
		areaIn.append("\n    <SmNoun LABEL=\"x3\" TRM=\"BConcept@hknu.meta-1\" SMaTTR=\"generic,sin\"/>");
		areaIn.append("\n    <SmNounStructure LABEL=\"x4\">");
		areaIn.append("\n      <SmNoun LABEL=\"a\" TRM=\"SubWorldview_conceptual@hknu.symb-6\" SMaTTR=\"random,sin\"/>");
		areaIn.append("\n      <SmNounStructure LABEL=\"b\">");
		areaIn.append("\n        <SmNoun LABEL=\"b1\" TRM=\"Tree-network_s_node@hknu.symb-11\" SMaTTR=\"random,sin\"/>");
		areaIn.append("\n        <SmSpecialNoun LABEL=\"b2\" TRM=\"Quantity@hknu.symb-18\" SMaTTR=\"idef.every,any\"/>");
		areaIn.append("\n        <SmConjunction TRM=\"att.int.property.mo\" ARGS=\"b1,b2\"/>");
		areaIn.append("\n      </SmNounStructure>");
		areaIn.append("\n      <SmConjunction TRM=\"att.internal.mo\" ARGS=\"a,b\"/>");
		areaIn.append("\n    </SmNounStructure>");
		areaIn.append("\n    <SmConjunction TRM=\"sm_subjecteino\" ARGS=\"x1,x3\"/>");
		areaIn.append("\n    <SmConjunction TRM=\"sm_agenteino\" ARGS=\"x1,x2\"/>");
		areaIn.append("\n    <SmConjunction TRM=\"sm_verm.lg_object\" ARGS=\"x1,x4\"/>");
		areaIn.append("\n  </SmSentence>");
		areaIn.append("\n</SSMA>");
*/
		JViewport port = scroller.getViewport();
		areaIn.setFont(new Font("arial", Font.PLAIN, 14));
		areaIn.setTabSize(5);
		port.add(areaIn);
		panelIn.add(scroller);

		//output panel
		JPanel panelOut = new JPanel();
//    panelOut.setBorder(BorderFactory.createEtchedBorder());
		panelOut.setBorder(new TitledBorder(LineBorder.createBlackLineBorder()," Output ",TitledBorder.LEFT,TitledBorder.TOP));
//    panelOut.setBackground(Color.blue);
		areaOut = new JTextArea();
		JScrollPane scroller2 = new JScrollPane(){
			static final long serialVersionUID = 21L;
			public Dimension getPreferredSize() {
				return new Dimension(759,499);
			}
		};
		JViewport port2 = scroller2.getViewport();
		areaOut.setFont(new Font("arial", Font.PLAIN, 14));
		areaOut.setTabSize(5);
		port2.add(areaOut);
		panelOut.add(scroller2);

		//parameter panel.
		JPanel panelPar = new JPanel();
		JLabel labelIn = new JLabel("INPUT:");
		labelIn.setFont(new Font("serif", Font.BOLD, 14));
		final Object[] cbdIn = {"SrSemasia","Text"};
		cboxIn = new JComboBox(cbdIn);
		cboxIn.setSelectedItem(cbdIn[1]);
		JLabel labelLangIn = new JLabel("Language");
		labelLangIn.setFont(new Font("serif", Font.BOLD, 12));
		final Object[] cbdLang = {"English","Greek"};
		jcbLangIn = new JComboBox(cbdLang);
		jcbLangIn.setSelectedItem(cbdLang[0]);

		JLabel labelM = new JLabel("                ");
		labelM.setFont(new Font("serif", Font.BOLD, 14));

		JLabel labelOut = new JLabel("OUTPUT:");
		labelOut.setFont(new Font("serif", Font.BOLD, 14));
		cboxOut = new JComboBox(cbdIn);
		cboxOut.setSelectedItem(cbdIn[1]);
		JLabel labelLangOut = new JLabel("Language");
		labelLangOut.setFont(new Font("serif", Font.BOLD, 12));
		jcbLangOut = new JComboBox(cbdLang);
		jcbLangOut.setSelectedItem(cbdLang[0]);

		panelPar.add(labelIn);
		panelPar.add(cboxIn);
		panelPar.add(labelLangIn);
		panelPar.add(jcbLangIn);
		panelPar.add(labelM);
		panelPar.add(labelOut);
		panelPar.add(cboxOut);
		panelPar.add(labelLangOut);
		panelPar.add(jcbLangOut);

		//button panel.
		JPanel panelBtn = new JPanel();
		btnTranslate = new JButton("Translate");
		btnTranslate.setFont(new Font("serif", Font.BOLD, 14));
		btnTranslate.addActionListener(new ActionTrans());
		btnClearIn = new JButton("Clear Input");
		btnClearIn.setFont(new Font("serif", Font.BOLD, 14));
		btnClearIn.addActionListener(new ActionListener()
		{
			public void	actionPerformed(ActionEvent	ae)
			{
				areaIn.setText("");
			}
		});
		btnClearOut = new JButton("Clear Output");
		btnClearOut.setFont(new Font("serif", Font.BOLD, 14));
		btnClearOut.addActionListener(new ActionListener()
		{
			public void	actionPerformed(ActionEvent	ae)
			{
				areaOut.setText("");
			}
		});
		panelBtn.add(btnTranslate);
		panelBtn.add(btnClearIn);
		panelBtn.add(btnClearOut);

		box.add(panelIn);
		box.add(panelOut);
		box.add(panelPar);
		box.add(panelBtn);
		JPanel paneMain = new JPanel();
		paneMain.setBorder(BorderFactory.createEtchedBorder());
		paneMain.setLayout(new BorderLayout());
		paneMain.add(box, BorderLayout.CENTER);

		// and get the GUI components onto our content pane
		getContentPane().add(paneMain, BorderLayout.CENTER);
		Util.setFrameIcon(this);
		setSize(new Dimension(800,800));
		setLocation(59,19);
		setVisible(true);
	}//end RetrieveFrame_Translation


	/**
	 * @modified 2006.09.28
	 * @since 2001.09.22
	 * @author HoKoNoUmo
	 */
	class ActionTrans extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			String input = areaIn.getText();
			StringWriter writer = new StringWriter();

			if (((String)cboxIn.getSelectedItem()).equals("Text")){
				if (((String)jcbLangIn.getSelectedItem()).equals("English")){
					if (((String)cboxOut.getSelectedItem()).equals("Text")){
						if (((String)jcbLangOut.getSelectedItem()).equals("English")){
							//TEXT,eng ---> TEXTNODE,eng
							Parser_Text tparser = new Parser_TextToTxNode_Eng("hknu.symb");
							TxNode jTxNd=null;
							try {
								jTxNd = tparser.parseFromString(input);
							} catch (IOException eio) {
								System.out.println(eio.toString());
							}
							areaOut.append(jTxNd.toString());
/*
							//TEXT,eng --> jTEXT --> jSrSemasia --> TEXT,eng
							Parser_Text tparser = new Parser_Text("hknu.symb","eng");
							SSmNode jSSm=null;
							try {
								jSSm = tparser.parseFromString(input);
							} catch (IOException eio) {
								System.out.println(eio.toString());
							}
							Maper_SSemasiaToText mvv = new Maper_SSemasiaToText(jSSm,"eng",writer);
*/
						} else if (((String)jcbLangOut.getSelectedItem()).equals("Greek")){
							//TEXT,eng-->jTEXT-->jSEMASIA,eng-->jSEMASIA,eln-->TEXT,eln
							Parser_Text tparser = new Parser_Text("hknu.symb","eng");
							SSmNode jSSm=null;
//							try {
//								jSSm = tparser.parseFromString(input);
//							} catch (IOException eio) {
//								System.out.println(eio.toString());
//							}
//							Maper_SSemasiaToSSemasia mvv = new Maper_SSemasiaToSSemasia(jSSm,"eln");
//							Maper_SSemasiaToText mmt = new Maper_SSemasiaToText(mvv.jSSmOut,"eln",writer);
						}
					} else if (((String)cboxOut.getSelectedItem()).equals("SrSemasia")){
						if (((String)jcbLangOut.getSelectedItem()).equals("English")){
							//TEXT,eng --> SEMASIA,eng
							Parser_Text tparser = new Parser_Text("hknu.symb","eng");
							SSmNode jSSm=null;
//							try {
//								jSSm = tparser.parseFromString(input);
//							} catch (IOException eio) {
//								System.out.println(eio.toString());
//							}
//							try {
//								jSSm.mapToCharNode(writer);
//							} catch (IOException ioe) {
//								System.out.println("RetrieveFrame_Translation: IOException");
//							}
						} else if (((String)jcbLangOut.getSelectedItem()).equals("Greek")){
							//TEXT,eng --> jTEXT --> jSEMASIA,eng --> SEMASIA,eln
							Parser_Text tparser = new Parser_Text("hknu.symb","eng");
							SSmNode jSSm=null;
//							try {
//								jSSm = tparser.parseFromString(input);
//							} catch (IOException eio) {
//								System.out.println(eio.toString());
//							}
//							Maper_SSemasiaToSSemasia mvv = new Maper_SSemasiaToSSemasia(jSSm,"eln",writer);
						}
					}
				} else if (((String)jcbLangIn.getSelectedItem()).equals("Greek")){
					if (((String)cboxOut.getSelectedItem()).equals("Textual")){
						if (((String)jcbLangOut.getSelectedItem()).equals("English")){
							//TEXT,eln --> TEXT,eng
						} else if (((String)jcbLangOut.getSelectedItem()).equals("Greek")){
							//TEXT,eln --> TEXT,eln
							Parser_Text tparser = new Parser_Text("hknu.symb","eln");
							SSmNode jSSm=null;
//							try {
//								jSSm = tparser.parseFromString(input);
//							} catch (IOException eio) {
//								System.out.println(eio.toString());
//							}
//							Maper_SSemasiaToText mvv = new Maper_SSemasiaToText(jSSm,"eln",writer);
						}
					} else if (((String)cboxOut.getSelectedItem()).equals("SrSemasia")){
						if (((String)jcbLangOut.getSelectedItem()).equals("English")){
							//TEXT,eln --> SEMASIA,eng
						} else if (((String)jcbLangOut.getSelectedItem()).equals("Greek")){
							//TEXT,eln --> SEMASIA,eln
							Parser_Text tparser = new Parser_Text("hknu.symb","eln");
							SSmNode jSSm=null;
//							try {
//								jSSm = tparser.parseFromString(input);
//								jSSm.mapToCharNode(writer);
//							} catch (IOException eio) {
//								System.out.println("RetrieveFrame_Translation: IOException");
//							}
						}
					}
				}
			}

			else if (((String)cboxIn.getSelectedItem()).equals("SrSemasia"))
			{
				if (((String)jcbLangIn.getSelectedItem()).equals("English")){
					if (((String)cboxOut.getSelectedItem()).equals("Textual")){
						if (((String)jcbLangOut.getSelectedItem()).equals("English")){
							//SEMASIA,eng --> TEXT,eng
							Parser_SSemasia mparser = new Parser_SSemasia();
							SSmNode jSSm = mparser.parseFromString(input);
							Maper_SSemasiaToText mmt = new Maper_SSemasiaToText(jSSm, "eng", writer);
						} else if (((String)jcbLangOut.getSelectedItem()).equals("Greek")){
							//SEMASIA,eng --> SEMASIA,eln --> TEXT,eln
							Parser_SSemasia mparser = new Parser_SSemasia();
							SSmNode jSSm = mparser.parseFromString(input);
							Maper_SSemasiaToSSemasia mvv = new Maper_SSemasiaToSSemasia(jSSm,"eln");
							Maper_SSemasiaToText mmt = new Maper_SSemasiaToText(mvv.jSSmOut,"eln",writer);
						}
					} else if (((String)cboxOut.getSelectedItem()).equals("SrSemasia")){
						if (((String)jcbLangOut.getSelectedItem()).equals("English")){
							//SEMASIA,eng --> SEMASIA,eng
							Parser_SSemasia mparser = new Parser_SSemasia();
							SSmNode jSSm = mparser.parseFromString(input);
							try {
								jSSm.mapToCharNode(writer);
							} catch (IOException ioe) {
								System.out.println("RetrieveFrame_Translation: IOException");
							}
						} else if (((String)jcbLangOut.getSelectedItem()).equals("Greek")){
							//SEMASIA,eng --> SEMASIA,eln
							Parser_SSemasia mparser = new Parser_SSemasia();
							SSmNode jSSm = mparser.parseFromString(input);
							Maper_SSemasiaToSSemasia mvv = new Maper_SSemasiaToSSemasia(jSSm,"eln",writer);
						}
					}
				} else if (((String)jcbLangIn.getSelectedItem()).equals("Greek")){
					if (((String)cboxOut.getSelectedItem()).equals("Textual")){
						if (((String)jcbLangOut.getSelectedItem()).equals("English")){
							//SEMASIA,eln --> SEMASIA,eng --> TEXT,eng
							Parser_SSemasia mparser = new Parser_SSemasia();
							SSmNode jSSm = mparser.parseFromString(input);
							Maper_SSemasiaToSSemasia mvv = new Maper_SSemasiaToSSemasia(jSSm,"eng");
							Maper_SSemasiaToText mmt = new Maper_SSemasiaToText(mvv.jSSmOut, "eng", writer);
						} else if (((String)jcbLangOut.getSelectedItem()).equals("Greek")){
							//SEMASIA,eln --> TEXT,eln
							Parser_SSemasia mparser = new Parser_SSemasia();
							SSmNode jSSm = mparser.parseFromString(input);
							Maper_SSemasiaToText mmt = new Maper_SSemasiaToText(jSSm, "eln", writer);
						}
					} else if (((String)cboxOut.getSelectedItem()).equals("SrSemasia")){
						if (((String)jcbLangOut.getSelectedItem()).equals("English")){
							//SEMASIA,eln --> SEMASIA,eng
							Parser_SSemasia mparser = new Parser_SSemasia();
							SSmNode jSSm = mparser.parseFromString(input);
							Maper_SSemasiaToSSemasia mvv = new Maper_SSemasiaToSSemasia(jSSm,"eng",writer);
						} else if (((String)jcbLangOut.getSelectedItem()).equals("Greek")){
							//SEMASIA,eln --> SEMASIA,eln
							Parser_SSemasia mparser = new Parser_SSemasia();
							SSmNode jSSm = mparser.parseFromString(input);
							try {
								jSSm.mapToCharNode(writer);
							} catch (IOException ioe) {
								System.out.println("RetrieveFrame_Translation: IOException");
							}
						}
					}
				}
			}

			try	{
				writer.close();
			}	catch (IOException ioe)	{
				areaOut.append("\nWriter Close exception");
				return;
			}

			//all ok, then write the output
			areaOut.append("\n\n" +writer.toString());
		}
	}


}
