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
import javax.swing.JTextField;

/**
 * It makes a SELECTED Non-Inherited att, Inherited-Overridden.
 * We suppose that already exists its generic-att.
 * It is similar to 'attribute generalization' but now we don't create the generic-att.
 * In this case, this exists.
 *
 * In generic-att, it inserts the selected as spe.
 * In selected, it inserts the generic-att as gen IF the selected is a file-xcpt.
 * In current, it replaces the gen of selected.
 *
 * @modified
 * @since 2000.04.10
 * @author HoKoNoUmo
 */
public class FrameMakeInherited extends JFrame
{
	static final long serialVersionUID = 21L;
	String sbcpt=null;				//The current sbcpt.
	String xcpt_sFormalID=null;
	String att=null;				//The attribute we want to make inherited from a generic-sbcpt.
	String attID=null;
	String attGen=null;			//The generic-attribute of a generic-concept.
	String attGenID=null;

  /**
   * @param acptID is the ID of the xcpt we will change one of its attributes.
   * @param aattID is the ID of the att we want to make inherited.
   * @modified
   * @since 2000.03.30
   * @author HoKoNoUmo
   */
  public FrameMakeInherited(String acptID, String aattID)
  {
  	xcpt_sFormalID=acptID;
		sbcpt=Util.getXCpt_sLastFileName(acptID);
		attID=aattID;
		att=Util.getXCpt_sLastFileName(aattID);
		setTitle("Make attribute INHERITED");


    //Create the left components
    JLabel labelAttGen = new JLabel("Generic-Attribute (ID) = ",JLabel.RIGHT);
		labelAttGen.setFont(new Font("serif", Font.PLAIN, 16));
    JLabel labelCpt = new JLabel("Current-Concept = ",JLabel.RIGHT);
		labelCpt.setFont(new Font("serif", Font.PLAIN, 16));
    JLabel labelAtt = new JLabel("NonInherited-Attribute = ",JLabel.RIGHT);
		labelAtt.setFont(new Font("serif", Font.PLAIN, 16));

    //create the right components.
    final JTextField fieldAttGen = new JTextField(16);
		fieldAttGen.setFont(new Font("serif", Font.BOLD, 14));
    JTextField fieldCpt = new JTextField(16);
		fieldCpt.setFont(new Font("serif", Font.BOLD, 14));
		fieldCpt.setText(sbcpt);
    JTextField fieldAtt = new JTextField(16);
		fieldAtt.setFont(new Font("serif", Font.BOLD, 14));
		fieldAtt.setText(att);

    JPanel paneLeft = new JPanel();
    paneLeft.setLayout(new GridLayout(0, 1));
    paneLeft.add(labelAttGen);
    paneLeft.add(labelCpt);
    paneLeft.add(labelAtt);
    JPanel paneRight = new JPanel();
    paneRight.setLayout(new GridLayout(0, 1));
    paneRight.add(fieldAttGen);
    paneRight.add(fieldCpt);
    paneRight.add(fieldAtt);
    JPanel paneRelations = new JPanel();
    paneRelations.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));
    paneRelations.setLayout(new BorderLayout());
    paneRelations.add(paneLeft, BorderLayout.WEST);
    paneRelations.add(paneRight, BorderLayout.EAST);

    //north for label and buttons.
    JPanel paneAsk = new JPanel();
    JLabel labelAsk = new JLabel("Make it Inherited?");
		labelAsk.setFont(new Font("serif", Font.BOLD, 18));
    paneAsk.add(labelAsk);
		//buttons
    JPanel paneButton = new JPanel();
    final JButton ok = new JButton("  Yes  ");
    paneButton.add(ok);
    ok.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent ae)
    	{
				attGenID = fieldAttGen.getText();
				attGenID=attGenID.toLowerCase();
				attGen=Util.getXCpt_sLastFileName(attGenID);

				//in gen-att, insert the attribute as spe.
				Insert ins = new Insert();
				ins.insertAttSpecificInFile(attGen,
															att,											//FILENAME
															attGen, 									//Generic
															AAj.kb_sAuthor);						//Author

				//in att, insert the new as gen >>IF<< it is a file-xcpt.
				if (att.indexOf("#")==-1)
				{
					//find gen of the gen-att.
					Extract ex = new Extract();
					ex.extractGenerics(attGenID);
					String gen = ex.stringGen;
					ins.insertAttGenericInFile(att,
															attGen,										//FILENAME
															gen,	 										//Generic
															AAj.kb_sAuthor);						//Author
				}

				//in current, replace the gen xml-att of att.
				Vector<String> vectorReplacing = new Vector<String>();
				vectorReplacing.add(attID +"," +attGenID);
				ins.replaceGeneric(sbcpt, vectorReplacing);

				//redisplay the current.
				AAj.display(sbcpt);
				if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(sbcpt);
				dispose();
    	}
    });

    final JButton cancel = new JButton("  No  ");
    paneButton.add(cancel);
    cancel.addActionListener(new ActionListener()
    {
    	public void actionPerformed(ActionEvent ae)
    	{
    		dispose();
    	}
    });
		JButton jbHelp = new JButton(" Help ");
		paneButton.add (jbHelp);
		jbHelp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				AAj.display("it-48#6");
				AAj.frameAAj.toFront();
				AAj.frameAAj.requestFocus();
			}
		});

    JPanel paneSouth = new JPanel(new BorderLayout());
    paneSouth.add(paneAsk, BorderLayout.NORTH);
    paneSouth.add(paneButton, BorderLayout.SOUTH);

    //Put the box/buttons in another panel.
    JPanel contentPane = new JPanel(new BorderLayout());
    contentPane.add(paneRelations, BorderLayout.CENTER);
    contentPane.add(paneSouth, BorderLayout.SOUTH);

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
}