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
 * It holds common functionality we need when we add new xConcepts
 * to current-xcpt.
 *
 * @modified 2009.11.04
 * @since 2009.11.04 (v00.02.01)
 * @author HoKoNoUmo
 */
public class FrameAddNewXConcept extends JFrame
{
	static final long serialVersionUID= 42L;
	public boolean typeInt=false;

	public String xDef="";
	public String currentFrName=null;

	public JLabel jlbAuthor;
	public JLabel jlbFrView;
	public JLabel jlbEngMainName;
	public JLabel jlbRuleOfName;
	public JLabel jlbDescription;
	public JLabel jlbLang;
	public JLabel jlbDefType;
	public JLabel jlbMessageOfRelations;
	public JLabel jlbIntExt;

	public JButton jbtFindRule;
	public JButton jbtOk;
	public JButton jbtCancel;
	public JButton jbtSeeXml;
	public JButton jbtSeeText;
	public JButton jbtHelp;

	public JCheckBox jchMostUsed;

	public JComboBox jcbDefType;
	public JComboBox jcbLang;
	public JComboBox jcbFrView;

	public JTextField jtfAuthor;
	public JTextField jtfDescription;
	public JTextField jtfEngMainName;
	public JTextField jtfRule;

	public JTextArea areaDef;

	public JScrollPane jscrPane;

	public JViewport jvPort;

	public ButtonGroup bgFileType;
	public JRadioButton jrbuttonInt;
	public JRadioButton jrbuttonFile;

	/**
	 * The constructor.
	 */
	public FrameAddNewXConcept(String title)
	{
		super(title);
	}

	/**
	 *
	 * @modified 2009.11.04
	 * @since 2009.11.04 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public void createLayout(){
		//row: Author.
		jlbAuthor= new JLabel(AAj.rbLabels.getString("Author")+":");
		jtfAuthor= new JTextField(29);
//		jtfAuthor.setFont(new Font("serif", Font.BOLD, 14));
		jtfAuthor.setText(AAj.kb_sAuthor);
		jlbFrView= new JLabel(AAj.rbLabels.getString("FormalView")+":");
		Object[] vd= AAj.trmapSrBrSubWorldview.keySet().toArray();
		jcbFrView= new JComboBox(vd);
		jcbFrView.setSelectedItem((Object)AAj.xcpt_sFormalID.substring(0, AAj.xcpt_sFormalID.indexOf("-")));

		//row: EngMainTerm.
		jlbEngMainName= new JLabel(AAj.rbLabels.getString("EngMainName")+":");
		jtfEngMainName= new JTextField(29);
		jchMostUsed= new JCheckBox(AAj.rbLabels.getString("MostUsed"));
		jlbRuleOfName= new JLabel(AAj.rbLabels.getString("Rule")+":");
		jtfRule= new JTextField(12);
		jbtFindRule= new JButton(AAj.rbLabels.getString("FindRule"));
		jbtFindRule.addActionListener(new ActionFindRule());

		//row: Description
		jlbDescription= new JLabel(AAj.rbLabels.getString("Description")+":");
		jtfDescription= new JTextField(49);
		jlbLang= new JLabel(AAj.rbLabels.getString("Language")+":");
		jcbLang= new JComboBox();
		jcbLang.addItem(AAj.rbLabels.getString("English2"));
		jcbLang.addItem(AAj.rbLabels.getString("Greek2"));
		jcbLang.addItem(AAj.rbLabels.getString("Esperanto"));
		jcbLang.addItem(AAj.rbLabels.getString("Komo"));
		jcbLang.setSelectedIndex(0);

		//row: definition-type
		jlbDefType= new JLabel(AAj.rbLabels.getString("TypeOfCreationDefinition")+":");
		Object[] defType= {AAj.rbLabels.getString("DEFINITION_ATTRIBUTE"),
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
		jcbDefType= new JComboBox(defType);
		//definition row.
		jlbMessageOfRelations= new JLabel(AAj.rbLabels.getString("MsgAddDefRel"));
		areaDef= new JTextArea();
		jscrPane= new JScrollPane();
		jscrPane.setPreferredSize(new Dimension(753,159));
		jvPort= jscrPane.getViewport();
		areaDef.setFont(new Font("serif", Font.PLAIN, 16));
//		areaDef.setLineWrap(true);
		areaDef.setTabSize(2);
		areaDef.setWrapStyleWord(true);
		jvPort.add(areaDef);

		//internal/external row.
		bgFileType= new ButtonGroup();
		jrbuttonInt= new JRadioButton (AAj.rbLabels.getString("InternalXCpt"));
		jrbuttonFile= new JRadioButton (AAj.rbLabels.getString("FileXCpt"));
		bgFileType.add(jrbuttonFile);
		bgFileType.add(jrbuttonInt);
		jrbuttonFile.setSelected(true);
		jrbuttonInt.addActionListener(new ActionButtonInt());
		jrbuttonFile.addActionListener(new ActionButtonFile());
		jlbIntExt= new JLabel(AAj.rbLabels.getString("MsgPreferInternals"));

		//Create the Layouts.
		Box panelCenterBox= new Box(BoxLayout.Y_AXIS);
		JPanel panelAuthor= new JPanel();
		panelAuthor.setLayout(new FlowLayout());
		panelAuthor.add(jlbAuthor);
		panelAuthor.add(jtfAuthor);
		panelAuthor.add(jlbFrView);
		panelAuthor.add(jcbFrView);

		JPanel panelMainName= new JPanel(new FlowLayout());
		panelMainName.add(jlbEngMainName);
		panelMainName.add(jtfEngMainName);
		panelMainName.add(jchMostUsed);
		panelMainName.add(jlbRuleOfName);
		panelMainName.add(jtfRule);
		panelMainName.add(jbtFindRule);

		JPanel panelDescription= new JPanel(new FlowLayout());
		panelDescription.add(jlbDescription);
		panelDescription.add(jtfDescription);
		panelDescription.add(jlbLang);
		panelDescription.add(jcbLang);

		JPanel panelDefType= new JPanel(new FlowLayout());
		panelDefType.add(jlbDefType);
		panelDefType.add(jcbDefType);

		JPanel panelDefMessage= new JPanel(new FlowLayout());
		panelDefMessage.add(jlbMessageOfRelations);

		JPanel panelDefRelations= new JPanel(new FlowLayout());
		panelDefRelations.add(jscrPane);

		JPanel panelIntExt= new JPanel();
		panelIntExt.setLayout(new FlowLayout());
		panelIntExt.add(jrbuttonInt);
		panelIntExt.add(jrbuttonFile);
		JPanel panelIntExtMessage= new JPanel();
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

		JPanel panelSouthButton= new JPanel(new FlowLayout());
		jbtSeeXml= new JButton(AAj.rbLabels.getString("SeeXmlDefinition"));
		jbtSeeXml.addActionListener(new ActionSeeXmlDefinition());
		panelSouthButton.add(jbtSeeXml);
		jbtSeeText= new JButton(AAj.rbLabels.getString("SeeTextDefinition"));
		jbtSeeText.addActionListener(new ActionSeeTextDefinition());
		panelSouthButton.add(jbtSeeText);

		jbtOk= new JButton(AAj.rbLabels.getString("OK"));
		jbtOk.addActionListener(new ActionOK());
		panelSouthButton.add(jbtOk);

		jbtCancel= new JButton(AAj.rbLabels.getString("Cancel"));
		jbtCancel.addActionListener(new ActionCancel());
		panelSouthButton.add(jbtCancel);

		JButton jbtHelp= new JButton(AAj.rbLabels.getString("Help"));
		jbtHelp.addActionListener(new ActionHelp());
		panelSouthButton.add (jbtHelp);

		//Put the panelCenterBox/button-panel in main-panel.
		JPanel jpnlMain= new JPanel();
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

		String defType= (String)jcbDefType.getSelectedItem();

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
					parser= new Parser_PDefinition(pDef);
				} catch (IOException e) {
					System.out.println(e.toString());
				}
				String xd= parser.xDefinition;
				xDef=xDef +xd;
				xDef=xDef+"\n</DEFINITION_ATTRIBUTE>";
			}
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_ATTRIBUTE_DESCRIPTION"))){
			xDef="<DEFINITION_ATTRIBUTE_DESCRIPTION CREATION=\"y\" CREATED=\""+Util.getCurrentDate()
					+"\" AUTHOR=\""+jtfAuthor.getText() +"\">";
			addEngMainNameAddDescription(xDef);
			xDef=xDef+"\n</DEFINITION_ATTRIBUTE_DESCRIPTION>";
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
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_PART_DESCRIPTION"))){
			xDef="<DEFINITION_PART_DESCRIPTION CREATION=\"y\" CREATED=\""+Util.getCurrentDate()
					+"\" AUTHOR=\""+jtfAuthor.getText() +"\">";
			addEngMainNameAddDescription(xDef);
			xDef=xDef+"\n</DEFINITION_PART_DESCRIPTION>";
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_PART_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_PART_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_WHOLE"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_WHOLE_DESCRIPTION"))){
			xDef="<DEFINITION_WHOLE_DESCRIPTION CREATION=\"y\" CREATED=\""+Util.getCurrentDate()
					+"\" AUTHOR=\""+jtfAuthor.getText() +"\">";
			addEngMainNameAddDescription(xDef);
			xDef=xDef+"\n</DEFINITION_WHOLE_DESCRIPTION>";
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_WHOLE_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_WHOLE_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_SPECIFIC"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_SPECIFIC_DESCRIPTION"))){
			xDef="<DEFINITION_SPECIFIC_DESCRIPTION CREATION=\"y\" CREATED=\""+Util.getCurrentDate()
					+"\" AUTHOR=\""+jtfAuthor.getText() +"\">";
			addEngMainNameAddDescription(xDef);
			xDef=xDef+"\n</DEFINITION_SPECIFIC_DESCRIPTION>";
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_SPECIFIC_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_SPECIFIC_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_GENERIC"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_GENERIC_DESCRIPTION"))){
			xDef="<DEFINITION_GENERIC_DESCRIPTION CREATION=\"y\" CREATED=\""+Util.getCurrentDate()
					+"\" AUTHOR=\""+jtfAuthor.getText() +"\">";
			addEngMainNameAddDescription(xDef);
			xDef=xDef+"\n</DEFINITION_GENERIC_DESCRIPTION>";
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_GENERIC_START"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_GENERIC_END"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_ENVIRONMENT"))){
		}
		else if (defType.equals(AAj.rbLabels.getString("DEFINITION_ENVIRONMENT_DESCRIPTION"))){
			xDef="<DEFINITION_ENVIRONMENT_DESCRIPTION CREATION=\"y\" CREATED=\""+Util.getCurrentDate()
					+"\" AUTHOR=\""+jtfAuthor.getText() +"\">";
			addEngMainNameAddDescription(xDef);
			xDef=xDef+"\n</DEFINITION_ENVIRONMENT_DESCRIPTION>";
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
	 *
	 * @modified 2009.11.04
	 * @since 2009.11.04 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionButtonFile extends AbstractAction
	{
		static final long serialVersionUID= 42L;
		public void actionPerformed(ActionEvent e)
		{
			if (jrbuttonFile.isSelected() == true)
				typeInt= false;
			else
				typeInt= true;
		}
	}


	/**
	 *
	 * @modified 2009.11.04
	 * @since 2009.11.04 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionButtonInt extends AbstractAction
	{
		static final long serialVersionUID= 42L;
		public void actionPerformed(ActionEvent e)
		{
			if (jrbuttonInt.isSelected() == true)
				typeInt= true;
			else
				typeInt= false;
		}
	}


	/**
	 *
	 * @modified 2009.11.04
	 * @since 2009.11.04 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionCancel extends AbstractAction
	{
		static final long serialVersionUID= 42L;
		public void actionPerformed(ActionEvent e)
		{
		}
	}


	/**
	 *
	 * @modified 2009.11.04
	 * @since 2009.11.04 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionFindRule extends AbstractAction
	{
		static final long serialVersionUID= 42L;
		public void actionPerformed(ActionEvent e)
		{
			RetrieveFrame_FindTxConceptTerms ffsf = new RetrieveFrame_FindTxConceptTerms(jtfEngMainName.getText());
		}
	}


	/**
	 *
	 * @modified 2009.11.04
	 * @since 2009.11.04 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionHelp extends AbstractAction
	{
		static final long serialVersionUID= 42L;
		public void actionPerformed(ActionEvent e)
		{
//				AAj.display("it-48#2");
//				AAj.frameAAj.toFront();
//				AAj.frameAAj.requestFocus();
		}
	}


	/**
	 * It does nothing. Specifics does it.
	 *
	 * @modified 2009.11.05
	 * @since 2009.09.28 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	class ActionOK extends AbstractAction
	{
		static final long serialVersionUID= 42L;
		public void actionPerformed(ActionEvent e){}
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
		static final long serialVersionUID= 42L;
		public void actionPerformed(ActionEvent e)
		{
			String engMName=jtfEngMainName.getText();
			if (engMName.equals("")){
				JOptionPane.showMessageDialog(null, AAj.rbLabels.getString("MsgMainName"));
			}
			else {
				String xmlDef= createXmlDefinition();
				String txtDef="";
				Maper_XDefinitionToText mdtt =
						new Maper_XDefinitionToText(xmlDef,AAj.lng,false);//false=don't read from text
				txtDef= mdtt.txtDefinition;

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
		static final long serialVersionUID= 42L;
		public void actionPerformed(ActionEvent e)
		{
			String engMName=jtfEngMainName.getText();
			if (engMName.equals("")){
				JOptionPane.showMessageDialog(null, AAj.rbLabels.getString("MsgMainName"));
			}
			else {
				String xmlDef= createXmlDefinition();
				JOptionPane.showMessageDialog(null, xmlDef);
			}
		}
	}


}