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
 * In CURRENT it inserts the new as ATTRIBUTE.<br/>
 * In NEW it inserts the current as ENTITY.<p>
 *
 * IF current has specifics, THEN it inserts the new as
 * inherited-nonOverridden-att in all specifics.
 *
 * @modified 2009.10.26
 * @since 2009.09.26
 * @author HoKoNoUmo
 */
public class FrameAddNewAttribute extends JFrame
{
	static final long serialVersionUID = 21L;
	boolean typeInt=false;

	String xDef="";
	String currentFrName=null;

	JButton jbtOk;

	JCheckBox jchMostUsed;

	final JComboBox jcbDefType;
	JComboBox jcbLang;
	JComboBox jcbFrView;

	final JTextField jtfAuthor;
	final JTextField jtfDescription;
	final JTextField jtfEngMainName;
	JTextField jtfRule;

	JTextArea areaDef;

	/**
	 * The constructor.
	 */
	public FrameAddNewAttribute()
	{
		super(AAj.rbLabels.getString("MsgAddNewAtt") +AAj.xcpt_sFormalName);
		currentFrName=Util.getXCpt_sFormalName(AAj.xcpt_sFormalName);

		//row: Author.
		JLabel jlbAuthor = new JLabel(AAj.rbLabels.getString("Author")+":");
		jtfAuthor = new JTextField(29);
//		jtfAuthor.setFont(new Font("serif", Font.BOLD, 14));
		jtfAuthor.setText(AAj.kb_sAuthor);
		JLabel jlbFrView = new JLabel(AAj.rbLabels.getString("FormalView")+":");
		Object[] vd = AAj.trmapSrBrSubWorldview.keySet().toArray();
		jcbFrView = new JComboBox(vd);
		jcbFrView.setSelectedItem((Object)AAj.xcpt_sFormalID.substring(0, AAj.xcpt_sFormalID.indexOf("-")));

		//row: EngMainTerm.
		JLabel jlbEngMainName = new JLabel(AAj.rbLabels.getString("EngMainName")+":");
		jtfEngMainName = new JTextField(29);
		jchMostUsed = new JCheckBox(AAj.rbLabels.getString("MostUsed"));
		JLabel jlbRuleOfName = new JLabel(AAj.rbLabels.getString("Rule")+":");
		jtfRule = new JTextField(12);
		final JButton jbtFindRule = new JButton(AAj.rbLabels.getString("FindRule"));
		jbtFindRule.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				RetrieveFrame_FindTxConceptTerms ffsf = new RetrieveFrame_FindTxConceptTerms(jtfEngMainName.getText());
			}
		});

		//row: Description
		JLabel jlbDescription = new JLabel(AAj.rbLabels.getString("Description")+":");
		jtfDescription = new JTextField(49);
		JLabel jlbLang = new JLabel(AAj.rbLabels.getString("Language")+":");
		jcbLang = new JComboBox();
		jcbLang.addItem(AAj.rbLabels.getString("English2"));
		jcbLang.addItem(AAj.rbLabels.getString("Greek2"));
		jcbLang.addItem(AAj.rbLabels.getString("Esperanto"));
		jcbLang.addItem(AAj.rbLabels.getString("Komo"));
		jcbLang.setSelectedIndex(0);

		//row: definition-type
		JLabel jlbDefType = new JLabel(AAj.rbLabels.getString("TypeOfCreationDefinition")+":");
		Object[] defType = {AAj.rbLabels.getString("DEFINITION_ATTRIBUTE"),
										AAj.rbLabels.getString("DEFINITION_ATTRIBUTE_START"),
										AAj.rbLabels.getString("DEFINITION_ATTRIBUTE_END"),
										AAj.rbLabels.getString("DEFINITION_PART"),
										AAj.rbLabels.getString("DEFINITION_PART_START"),
										AAj.rbLabels.getString("DEFINITION_PART_END"),
										AAj.rbLabels.getString("DEFINITION_WHOLE"),
										AAj.rbLabels.getString("DEFINITION_WHOLE_START"),
										AAj.rbLabels.getString("DEFINITION_WHOLE_END"),
										AAj.rbLabels.getString("DEFINITION_SPECIFIC"),
										AAj.rbLabels.getString("DEFINITION_SPECIFIC_START"),
										AAj.rbLabels.getString("DEFINITION_SPECIFIC_END"),
										AAj.rbLabels.getString("DEFINITION_GENERIC"),
										AAj.rbLabels.getString("DEFINITION_GENERIC_START"),
										AAj.rbLabels.getString("DEFINITION_GENERIC_END"),
										AAj.rbLabels.getString("DEFINITION_ENVIRONMENT"),
										AAj.rbLabels.getString("DEFINITION_ENVIRONMENT_START"),
										AAj.rbLabels.getString("DEFINITION_ENVIRONMENT_END")};
		jcbDefType = new JComboBox(defType);
		//definition row.
		JLabel jlbMessageOfRelations = new JLabel(AAj.rbLabels.getString("MsgAddDefRel"));
		areaDef = new JTextArea();
		JScrollPane scrollerRelations = new JScrollPane(){
		static final long serialVersionUID = 21L;
			public Dimension getPreferredSize() {
				return new Dimension(753,159);
			}
		};
		JViewport port = scrollerRelations.getViewport();
		areaDef.setFont(new Font("serif", Font.PLAIN, 16));
//		areaDef.setLineWrap(true);
		areaDef.setTabSize(2);
		areaDef.setWrapStyleWord(true);
		port.add(areaDef);

		//internal/external row.
		ButtonGroup fileType = new ButtonGroup();
		final JRadioButton jrbuttonInt = new JRadioButton (AAj.rbLabels.getString("InternalXCpt"));
		final JRadioButton jrbuttonFile = new JRadioButton (AAj.rbLabels.getString("FileXCpt"));
		fileType.add(jrbuttonFile);
		fileType.add(jrbuttonInt);
		jrbuttonFile.setSelected(true);
		jrbuttonInt.addActionListener(new ActionListener() {
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
		JLabel jlbIntExt = new JLabel(AAj.rbLabels.getString("MsgPreferInternals"));

		//Create the Layouts.
		Box panelCenterBox = new Box(BoxLayout.Y_AXIS);
		JPanel panelAuthor = new JPanel();
		panelAuthor.setLayout(new FlowLayout());
		panelAuthor.add(jlbAuthor);
		panelAuthor.add(jtfAuthor);
		panelAuthor.add(jlbFrView);
		panelAuthor.add(jcbFrView);

		JPanel panelMainName = new JPanel(new FlowLayout());
		panelMainName.add(jlbEngMainName);
		panelMainName.add(jtfEngMainName);
		panelMainName.add(jchMostUsed);
		panelMainName.add(jlbRuleOfName);
		panelMainName.add(jtfRule);
		panelMainName.add(jbtFindRule);

		JPanel panelDescription = new JPanel(new FlowLayout());
		panelDescription.add(jlbDescription);
		panelDescription.add(jtfDescription);
		panelDescription.add(jlbLang);
		panelDescription.add(jcbLang);

		JPanel panelDefType = new JPanel(new FlowLayout());
		panelDefType.add(jlbDefType);
		panelDefType.add(jcbDefType);

		JPanel panelDefMessage = new JPanel(new FlowLayout());
		panelDefMessage.add(jlbMessageOfRelations);

		JPanel panelDefRelations = new JPanel(new FlowLayout());
		panelDefRelations.add(scrollerRelations);

		JPanel panelIntExt = new JPanel();
		panelIntExt.setLayout(new FlowLayout());
		panelIntExt.add(jrbuttonInt);
		panelIntExt.add(jrbuttonFile);
		JPanel panelIntExtMessage = new JPanel();
		panelIntExtMessage.setLayout(new FlowLayout());
		panelIntExtMessage.add(jlbIntExt);

		panelCenterBox.add(panelAuthor);
		panelCenterBox.add(panelMainName);
		panelCenterBox.add(panelDescription);
		panelCenterBox.add(panelDefType);
		panelCenterBox.add(panelDefMessage);
		panelCenterBox.add(panelDefRelations);
		panelCenterBox.add(panelIntExt);
		panelCenterBox.add(panelIntExtMessage);

		JPanel panelSouthButton = new JPanel(new FlowLayout());
		JButton jbtSeeXml = new JButton(AAj.rbLabels.getString("SeeXmlDefinition"));
		jbtSeeXml.addActionListener(new ActionSeeXmlDefinition());
		panelSouthButton.add(jbtSeeXml);
		JButton jbtSeeText = new JButton(AAj.rbLabels.getString("SeeTextDefinition"));
		jbtSeeText.addActionListener(new ActionSeeTextDefinition());
		panelSouthButton.add(jbtSeeText);

		jbtOk = new JButton(AAj.rbLabels.getString("OK"));
		jbtOk.addActionListener(new ActionOK());
		panelSouthButton.add(jbtOk);

		final JButton jbtCancel = new JButton(AAj.rbLabels.getString("Cancel"));
		panelSouthButton.add(jbtCancel);
		jbtCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				dispose();
			}
		});

		JButton jbtHelp = new JButton(AAj.rbLabels.getString("Help"));
		panelSouthButton.add (jbtHelp);
		jbtHelp.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
//				AAj.display("it-48#2");
//				AAj.frameAAj.toFront();
//				AAj.frameAAj.requestFocus();
			}
		});

		//Put the panelCenterBox/button-panel in main-panel.
		JPanel jpnlMain = new JPanel();
		jpnlMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		jpnlMain.setLayout(new BorderLayout());
		jpnlMain.add(panelCenterBox, BorderLayout.CENTER);
		jpnlMain.add(panelSouthButton, BorderLayout.SOUTH);

		getContentPane().add(jpnlMain, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		pack();
		Util.setFrameIcon(this);
		setLocation(126,126);
		setVisible(true);
	}


	/**
	 * Adds the EngMainName and Description xml-elements to the xml-definitions.
	 *
	 * @param xd	The xml-definition on which we going to add elements.
	 * @modified 2009.10.26
	 * @since 2009.10.11 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	void addEngMainNameAddDescription(String xd){
		xDef= xd +"\n<EngMainName TxEXP=\""+jtfEngMainName.getText();
		//ALWAYS the author of def, gives an english name.
		String rl= jtfRule.getText();
		if (!rl.equals("")){
			if (jchMostUsed.isSelected()== true){
				xDef=xDef+"\" MOSTuSED=\"1\" TRMrULE=\""+jtfRule.getText()+"\"/>";
			} else {
				xDef=xDef+"\" TRMrULE=\""+jtfRule.getText()+"\"/>";
			}
		} else {
			if (jchMostUsed.isSelected()== true){
				xDef=xDef+"\" MOSTuSED=\"1\"/>";
			} else {
				xDef=xDef+"\"/>";
			}
		}
		//description-line
		//a description could be added later from another author
		if (!jtfDescription.getText().equals("")){
			xDef=xDef+"\n<Description_"+Util.getLango3FromNormal((String)jcbLang.getSelectedItem())
					+" CREATED=\""+Util.getCurrentDate() +"\" AUTHOR=\""+jtfAuthor.getText() +"\">"
					+"\n"+jtfDescription.getText()
					+"\n</Description_"+Util.getLango3FromNormal((String)jcbLang.getSelectedItem())+">";
		}
	}


	/**
	 * Creates the XmlDefinition from the information gaved by author.
	 *
	 * @modified 2009.10.12
	 * @since 2009.09.29 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	String createXmlDefinition(){

		String defType = (String)jcbDefType.getSelectedItem();

		//ONLY attribute-def here

		String pDef=areaDef.getText();
		if (defType.equals(AAj.rbLabels.getString("DEFINITION_ATTRIBUTE"))){
			if (pDef.equals(""))
				JOptionPane.showMessageDialog(null, AAj.rbLabels.getString("MsgDefGive"));
			else {
				xDef="<DEFINITION_ATTRIBUTE CREATION=\"y\" CREATED=\""+Util.getCurrentDate()+"\""
					+" AUTHOR=\""+jtfAuthor.getText() +"\">";
				addEngMainNameAddDescription(xDef);
				Parser_PDefinition parser=null;
				try {
					parser = new Parser_PDefinition(pDef);
				} catch (IOException e) {
					System.out.println(e.toString());
				}
				String xd = parser.xDefinition;
				xDef=xDef +xd;
				xDef=xDef+"\n</DEFINITION_ATTRIBUTE>";
			}
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_ATTRIBUTE_START"))){
//<DEFINITION_ATTRIBUTE_START CREATION="y" CREATED="2009.09.26" AUTHOR="HoKoNoUmo">
//<EngMainName TxEXP="Entity's attribute" TRMrULE="rlEngTrmNnCs4b11" CREATED="2009.08.30" AUTHOR="HoKoNoUmo"/>
//<Description_eng CREATED="2009.09.26" AUTHOR="HoKoNoUmo">
//From the "structure-of-entity" we get and the start-concepts of "attribute" and "relation".
//</Description_eng>
//</DEFINITION_ATTRIBUTE_START>
			xDef="<DEFINITION_ATTRIBUTE_START CREATION=\"y\" CREATED=\""+Util.getCurrentDate()+"\""
					+" AUTHOR=\""+jtfAuthor.getText() +"\">";
			//main-name-line
			addEngMainNameAddDescription(xDef);
			xDef=xDef+"\n</DEFINITION_ATTRIBUTE_START>";
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_ATTRIBUTE_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_PART"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_PART_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_PART_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_WHOLE"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_WHOLE_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_WHOLE_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_SPECIFIC"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_SPECIFIC_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_SPECIFIC_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_GENERIC"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_GENERIC_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_GENERIC_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_ENVIRONMENT"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_ENVIRONMENT_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_ENVIRONMENT_END"))){
		}

		return xDef;
	}


	/**
	 * Creates the xml-element of the english main-name as appears in
	 * the refino_name element of xcpt.
	 *
	 * @modified 2009.10.01
	 * @since 2009.10.01 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	String createXmlNounCase(){
		//<Name_NounCase TxEXP="Entity" MOSTuSED="1" TRMrULE="rlEngTrmNnCs22"
		//CREATED="2000.11.12" AUTHOR="HoKoNoUmo"/>
		String mName= jtfEngMainName.getText();
		String xMainName="";
		xMainName="<Name_NounCase TxEXP=\""+mName;
		String rl= jtfRule.getText();
		if (!rl.equals("")){
			if (jchMostUsed.isSelected()== true){
				xMainName=xMainName+"\" MOSTuSED=\"1\" TRMrULE=\""+rl
				+"\" CREATED=\""+Util.getCurrentDate()+"\""
				+" AUTHOR=\""+jtfAuthor.getText() +"\"/>";
			} else {
				xMainName=xMainName+"\" TRMrULE=\""+rl
				+"\" CREATED=\""+Util.getCurrentDate()+"\""
				+" AUTHOR=\""+jtfAuthor.getText() +"\"/>";
			}
		} else {
			if (jchMostUsed.isSelected()== true){
				xMainName=xMainName+"\" MOSTuSED=\"1"
				+"\" CREATED=\""+Util.getCurrentDate()+"\""
				+" AUTHOR=\""+jtfAuthor.getText() +"\"/>";
			} else {
				xMainName=xMainName
				+"\" CREATED=\""+Util.getCurrentDate()+"\""
				+" AUTHOR=\""+jtfAuthor.getText() +"\"/>";
			}
		}
		return xMainName;
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
					//insert in CURRENT, the new as attribute.
					ins.insertAttAttributeInFile(currentFrName,
																newFrName,								//FRnAME
																null,										//Gen/Inh
																jtfAuthor.getText());		//Author
					//insert in NEW, current as entity.
					ins.insertAttEntityInFile(newFrName,
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


	/**
	 * Displays the TEXT of the definition, in current language.
	 *
	 * @modified 2009.09.28
	 * @since 2009.09.28 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionSeeTextDefinition extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			String engMName=jtfEngMainName.getText();
			if (engMName.equals("")){
				JOptionPane.showMessageDialog(null, AAj.rbLabels.getString("MsgMainName"));
			}
			else {
				String xmlDef = createXmlDefinition();
				String txtDef="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText(xmlDef,AAj.lng,false);//false=don't read from text
				txtDef = mdtt.txtDefinition;

				JOptionPane.showMessageDialog(null, txtDef);
			}
		}
	}


	/**
	 * Displays the creation-definition in XML format.
	 *
	 * @modified 2009.09.30
	 * @since 2009.09.30 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionSeeXmlDefinition extends AbstractAction
	{
		static final long serialVersionUID = 21L;
		public void actionPerformed(ActionEvent e)
		{
			String engMName=jtfEngMainName.getText();
			if (engMName.equals("")){
				JOptionPane.showMessageDialog(null, AAj.rbLabels.getString("MsgMainName"));
			}
			else {
				String xmlDef = createXmlDefinition();
				JOptionPane.showMessageDialog(null, xmlDef);
			}
		}
	}


}