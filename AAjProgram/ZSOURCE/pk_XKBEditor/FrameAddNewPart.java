package pk_XKBEditor;

import pk_XKBManager.AAj;
import pk_XKBManager.Maper_XDefinitionToText;
import pk_XKBManager.RetrieveFrame_FindTxConceptTerms;
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
import java.io.IOException;
import java.util.TreeSet;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
 * It creates a new xcpt.<br/>
 * In CURRENT it inserts the new as PART.<br/>
 * In NEW it inserts the current as WHOLE.<p>
 *
 * IF current has specifics, THEN it inserts the new as
 * inherited-nonOverridden-att in all specifics. [2000.03.17]
 *
 * @modified 2009.11.04
 * @since 2000.02.13
 * @author HoKoNoUmo
 */
public class FrameAddNewPart extends FrameAddNewXConcept
{
	static final long serialVersionUID = 21L;


	/**
	 * The constructor.
	 */
	public FrameAddNewPart()
	{
		super(AAj.rbLabels.getString("MsgAddNewPart") +AAj.xcpt_sFormalName);
		createLayout();
	}


	/**
	 * It creates a new xcpt.
	 * In NEW it inserts the current as ENTITY.<p>
	 * In CURRENT it inserts the new as ATTRIBUTE.
	 *
	 * IF current has specifics, THEN it inserts the new as
	 * inherited-nonOverridden-att in all specifics.
	 *
	 * @modified 2009.09.28
	 * @since 2009.09.28 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionOK extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			String engMName=jtfEngMainName.getText();
			if (engMName.equals("")){
				JOptionPane.showMessageDialog(null, AAj.rbLabels.getString("MsgMainName"));
			}
			else {
				String newFrTerm= Util.createFormalTerm(engMName);
				String newFrView= (String)jcbFrView.getSelectedItem();

				Insert ins = new Insert();
				String newFrName=null;

				if (!typeInt)
				{
					int lastFrNumber = Util.getNextFormalNumber(newFrView);
					newFrName = newFrTerm +"@" +newFrView
											+"-" +lastFrNumber +"@";
					//create the new file.
					ins.createXCpt_File(newFrName, createXmlDefinition(), createXmlNounCase());
					//insert in CURRENT, the new as part.
					ins.insertAttPartInFile(currentFrName,
																newFrName,								//FRnAME
																null,										//Gen/Inh
																jtfAuthor.getText());		//Author
					//insert in NEW, current as whole.
					ins.insertAttWholeInFile(newFrName,
																currentFrName,						//Att
																null,										//Gen/Inh
																jtfAuthor.getText());		//Author


					//display the new file
					AAj.display(newFrName);
					if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(newFrName);
					dispose();
				}

				else //make internal xcpt.
				{
					String hid=Util.getXCpt_sFormalID(currentFrName);
					String hostFormalNumber=Util.getXCpt_sFormalNumber(currentFrName);
					int nextFormalNumber_Internal=Util.getNextFormalNumber_Internal(hid);
					newFrName = newFrTerm +"@" +newFrView +"-"
										+hostFormalNumber +"#" +nextFormalNumber_Internal +"@";
					ins.createXCpt_InternalAsAttribute(currentFrName, newFrName,
												createXmlDefinition(), createXmlNounCase());
					//redisplay host.
					AAj.display(currentFrName);
					dispose();
				}

				//insert in CURRENT's subs, the new-xcpt as att-inherited-nonOverridden.
				//inherited = has gen/inh
				//nonOver = frNm equals gen
				Vector<String> filesToSearch = new Vector<String>();
				filesToSearch.add(currentFrName);
				while(!filesToSearch.isEmpty())
				{
					String subFrName=null;
					try	{
						subFrName = filesToSearch.firstElement();
					}
					catch (Exception ex)	{
						JOptionPane.showMessageDialog(null, "FrameAddNewAtt: Problem in " + subFrName);
						continue;				// Try next file
					}
					Extract ex = new Extract();
					ex.extractSpecifics(subFrName);
					Vector subs = ex.vectorSpeID;
					for (int i=0; i<subs.size(); i++)
					{
						String frName=(String)subs.get(i);
						if (frName.indexOf("#")==-1)//we insert only to file-xcpt
						{
							frName=Util.getXCpt_sFormalName(frName);
							filesToSearch.add(frName);
							//insert the new
							ins.insertAttAttributeInFile(frName,
																	newFrName,								//FRnAME
																	newFrName+"/"+currentFrName,//Gen/Inh
																	jtfAuthor.getText());		//Author
						}
					}
					filesToSearch.remove(0);
				}
			}//only if we have a name
		}
	}


}