package pk_Util;
import pk_XKBManager.AAj;
import pk_Util.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.net.MalformedURLException;

import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

/**
 * Displays the specific-complements (siblings) of a concept.
 *
 * @modified 2000.06.11
 * @since 2000.06.10
 * @author HoKoNoUmo
 */
public class ViewSpecificComplement extends JFrame
{
	static final long serialVersionUID = 21L;
	String						sbcpt;
	String						xcpt_sFormalID;
	String						cptGen;
	String						cptGenID;
	String						cptDiv;
	String						cptDivID;
	JList							jlistResults;				// List to display matches in.
	DefaultListModel	dlistModel;

	/**
	 * The constructor.
	 * @modified
	 * @since 2000.06.10
	 * @author HoKoNoUmo
	 */
	public ViewSpecificComplement(String name)
	{
		sbcpt=Util.getXCpt_sLastFileName(name);
		xcpt_sFormalID=Util.getXCpt_sFormalID(sbcpt);

		Extract ex=new Extract();

		//choose the generic.
		ex.extractGenerics(sbcpt);
		Vector gens=ex.vectorGen;
		if (gens.isEmpty())
		{
			JOptionPane.showMessageDialog(null, "There are NO specific-complements BECAUSE this sbcpt doesn't have a generic");
		}
		else {
			Object[] generics = gens.toArray();
			Object selectedGen = JOptionPane.showInputDialog(null,
						 "Choose the GENERIC on which you want to view the specific-complements",		//message
						 "Choose One of	 " +gens.size(),						//title
						 JOptionPane.INFORMATION_MESSAGE,						//message type
						 null,																			//icon
						 generics,																	//values to select
						 generics[0]);															//initial selection
			if (selectedGen!=null)
			{
				cptGen=(String)selectedGen;
				cptGenID=Util.getXCpt_sFormalID(cptGen);
				//choose the division.
				ex.extractDivision(cptGen);
				Vector divs=ex.vectorDivision;
				Object[] dv = divs.toArray();
				Object[] dvs = new Object[dv.length+1];
				dvs[0]="No Division";
				System.arraycopy(dv, 0, dvs, 1, dv.length);
				Object selectedValue = JOptionPane.showInputDialog(null,
							 "Choose the REFINO_SPECIFICdIVISION on which you want to view the specific-complements",	//message
							 "Choose One of	 " +dvs.length,							//title
							 JOptionPane.INFORMATION_MESSAGE,						//message type
							 null,																			//icon
							 dvs,																				//values to select
							 dvs[0]);																		//initial selection

				if (selectedValue!=null)
				{
					cptDiv=(String)selectedValue;
					if (cptDiv.indexOf("@")!=-1) cptDivID=Util.getXCpt_sFormalID(cptDiv);

					//find the specCompls.
					Vector vSC=new Vector(); //will hold the specific-complements.
					if (cptDiv.equals("No Division"))
					{
						ex.extractSpecifics(cptGen);
						vSC=ex.vectorSpe;
					}
					else {
						ex.extractSubDiv(cptGen,cptDiv);
						vSC=ex.vectorSubDiv;
					}
					if (!vSC.contains(sbcpt))
					{
						JOptionPane.showMessageDialog(null, "There are NO specific-complement cpts");
					}

					else {
						vSC.remove(sbcpt);
						setTitle("VIEW the SPECIFIC-COMPLEMENTS of (" +sbcpt +")");
						addWindowListener(new WindowAdapter()
						{
							public void windowClosing(WindowEvent we) {dispose();}
						});

						JPanel pNorth = new JPanel();
						pNorth.setBorder(BorderFactory.createEtchedBorder());
						JLabel lbDiv = new JLabel ("GENERICiNHERITOR="+cptGen
																			+",	 "
																			+"REFINO_SPECIFICdIVISION="+cptDiv);
						lbDiv.setFont (new Font ("serif", Font.PLAIN, 16));
						lbDiv.setForeground(Color.black);
						pNorth.add(lbDiv);

						JPanel pCenter = new JPanel(new BorderLayout());
						dlistModel = new DefaultListModel();
						for (int i=0; i<vSC.size(); i++)
						{
							String sc=(String)vSC.get(i);
							String xcpt_sName = Textual.createNormalName(sc);
							String id = Util.getXCpt_sFormalID(sc);
							String nodeName = xcpt_sName +" (" +id +")";
							dlistModel.addElement(nodeName);
						}
						jlistResults = new JList (dlistModel);
						JScrollPane scrollPane = new JScrollPane(jlistResults);
						scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
						pCenter.add("Center", scrollPane);

						JPanel pSouth = new JPanel();
						pSouth.setBorder(BorderFactory.createEtchedBorder());
						JLabel lb = new JLabel ("By clicking on a CONCEPT you see this concept on the AAj-VIEWER");
						lb.setFont (new Font ("serif", Font.PLAIN, 16));
						lb.setForeground(Color.black);
						pSouth.add(lb);

						getContentPane().add(pNorth, BorderLayout.NORTH);
						getContentPane().add(pCenter, BorderLayout.CENTER);
						getContentPane().add(pSouth, BorderLayout.SOUTH);

						MouseListener mouseListener = new MouseAdapter()
						{
							public void mouseClicked(MouseEvent me)
							{
								if (me.getClickCount() == 1)
								{
									JList list = (JList)me.getSource();
									int index = list.locationToIndex(me.getPoint());
									list.setSelectedIndex(index);
									String selection = (String)list.getSelectedValue();
									if (selection!=null)
									{
										String cptSel = selection.substring(selection.indexOf("(")+1, selection.indexOf(")"));
										AAj.display(cptSel);
										AAj.frameAAj.setVisible(true);
										AAj.frameAAj.toFront();
										AAj.frameAAj.requestFocus();
									}
								}
							}
						};
						jlistResults.addMouseListener(mouseListener);

						setIconImage( Util.getImage("resources/AAj_icon.gif") );
						setSize(new Dimension(576,374));
						setLocation(182,109);
						setVisible(true);
					}//there are specCompls
				}//div selected
				else {
					JOptionPane.showMessageDialog(null, "You MUST select a generic's-division");
				}
			}//gen selected.
			else {
				JOptionPane.showMessageDialog(null, "You MUST select a generic");
			}
		}//else gen exist.
	} //end ViewSpecificComplement

}