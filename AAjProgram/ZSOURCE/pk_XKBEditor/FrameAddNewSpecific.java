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
 * It creates a new file-xcpt.<br/>
 * In CURRENT it inserts the new as SPECIFIC.<br/>
 * In NEW it inserts the current as GENERIC.<p>
 *
 * Sets the GENERIC-ATTRIBUTES of current, inherited-attributes of the new.<p>
 *
 * IF the definition is enough to describe the concept, THEN
 * makes the new internal.
 *
 * @modified 2009.11.19
 * @since 2000.02.17 (v00.01.08)
 * @author HoKoNoUmo
 */
 public class FrameAddNewSpecific extends FrameAddNewXConcept
{
	static final long serialVersionUID = 21L;


	/**
	 * The constructor.
	 */
	public FrameAddNewSpecific()
	{
		super(AAj.rbLabels.getString("MsgAddNewSpecific") +AAj.xcpt_sFormalName);
		createLayout();
	}


	/**
	 * It creates a new file-xcpt.<br/>
	 * In CURRENT it inserts the new as SPECIFIC.<br/>
	 * In NEW it inserts the current as GENERIC.<p>
	 *
	 * Sets the generic-attributes of current, inherited-attributes of the new.<p>
	 *
	 * IF the definition is enouph to describe the concept, THEN
	 * makes the new internal.
	 *
	 * @modified 2009.11.19
	 * @since 2009.11.19 (v00.02.01)
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

				if (!typeInt) {
					int lastFrNumber = Util.getNextFormalNumber(newFrView);
					newFrName = newFrTerm +"@" +newFrView
											+"-" +lastFrNumber +"@";
					//create the new file.
					ins.createXCpt_File(newFrName, createXmlDefinition(), createXmlNounCase());
					//insert in CURRENT, the new as SPECIFIC.
					ins.insertAttSpecificInFile(currentFrName,
																newFrName,								//FRnAME
																null,										//Gen/Inh
																jtfAuthor.getText());		//Author
					//insert in NEW, current as GENERIC.
					ins.insertAttGenericInFile(newFrName,
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

				//in NEW inserts the attributes of CURRENT
				//as inherited-nonOverridden.
				//inherited = has gen/inh
				//nonOver = frNm equals gen
				Extract ex = new Extract();
				ex.extractParts(currentFrName);
				Vector vAtt = ex.vectorParID;
				for (int i=0; i<vAtt.size(); i++)
				{
					String att=(String)vAtt.get(i);
					String attID=Util.getXCpt_sLastFileName(att);
					ins.insertAttPartInFile(newFrName,
																attID,									 //FILENAME
																attID+"/"+currentFrName,//GENERIC/INHERITOR 2001.07.17
																jtfAuthor.getText());	 //AUTHOR
				}

				//insert current's who if exist as who to new.
				ex.extractWholes(currentFrName);
				Vector vWho=ex.vectorWhoID;
				for (int i=0; i<vWho.size(); i++)
				{
					String who=(String)vWho.get(i);
					String whoID=Util.getXCpt_sLastFileName(who);
					ins.insertAttWholeInFile(newFrName,
															whoID,										//FILENAME
															whoID+"/"+currentFrName,	//Generic/Inheritor
															jtfAuthor.getText());		//Author
				}

				//insert current's env if exist as who to new.
				ex.extractEnvironments(currentFrName);
				Vector vEnv=ex.vectorEnvID;
				for (int i=0; i<vEnv.size(); i++)
				{
					String env=(String)vEnv.get(i);
					String envID=Util.getXCpt_sLastFileName(env);
					ins.insertAttWholeInFile(newFrName,
															envID,										//FILENAME
															envID+"/"+currentFrName,	//Generic/Inheritor
															jtfAuthor.getText());		//Author
				}
			}
		}
	}


}