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
import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

/**
 * Shows the corelatons of current sbcpt and Deletes it.
 * We *MUST* display the xcpt in order to delet it, because we use info about its corelatons.
 *
 * @modified 2000.06.11
 * @since 2000.02.15
 * @author HoKoNoUmo
 */
public class FrameDelConcept extends JFrame
{
	static final long serialVersionUID = 21L;

	/**
	 * @param acpt is filename/id of a file/internal-xcpt.
	 */
	public FrameDelConcept(String acpt)
	{
		super("DELETE the current xConcept?");
		final String sbcpt=Util.getXCpt_sLastFileName(acpt);
		final String xcpt_sFormalID=Util.getXCpt_sFormalID(sbcpt);

		//careful row.
		JLabel labelCareful = new JLabel("BE CAREFUL");
		labelCareful.setFont(new Font("serif", Font.BOLD, 14));
		//sum row.
		Extract ex = new Extract();
		ex.extractSpecifics(sbcpt);
		Vector subs = ex.vectorSpe;
		int intSpe = subs.size();
		int sum = AAj.intPar+AAj.intWho+AAj.intGen+intSpe+AAj.intEnv;
		JLabel labelSum = new JLabel("The current xConcept is related with >>" +sum +"<< concepts");
		labelSum.setFont(new Font("serif", Font.PLAIN, 20));
		//specific row.
		JLabel att = new JLabel(AAj.intPar +"	 Attribute,");
		JLabel who = new JLabel(AAj.intWho +"	 Whole,");
		JLabel gen = new JLabel(AAj.intGen +"	 Generic,");
		JLabel spe = new JLabel(intSpe +"	 Specific,");
		JLabel env = new JLabel(AAj.intEnv +"	 Envrinoment XConcepts");
		//ask row.
		JLabel labelAsk = new JLabel("Do you want to DELETE it?");
		labelAsk.setFont(new Font("serif", Font.BOLD, 14));

		//Create the Layouts.
		Box box = new Box(BoxLayout.Y_AXIS);
		JPanel paneCareful = new JPanel();
		paneCareful.setLayout(new FlowLayout());
		paneCareful.add(labelCareful);
		JPanel paneSum = new JPanel();
		paneSum.setLayout(new FlowLayout());
		paneSum.add(labelSum);
		JPanel paneSpecific = new JPanel();
		paneSpecific.setLayout(new FlowLayout());
		paneSpecific.add(att);
		paneSpecific.add(who);
		paneSpecific.add(gen);
		paneSpecific.add(spe);
		paneSpecific.add(env);
		JPanel paneAsk = new JPanel();
		paneAsk.setLayout(new FlowLayout());
		paneAsk.add(labelAsk);

		box.add(paneCareful);
		box.add(paneSum);
		box.add(paneSpecific);
		box.add(paneAsk);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		final JButton ok = new JButton("	YES	 ");
		buttonPane.add(ok);
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Insert ins = new Insert();

				if (sbcpt.indexOf("#")!=-1)//it is an internal.
				{
					//remove internal AND add put the empty internal-number at LASTiNTfRnUMBER xml attribute.
					ins.removeInternal(sbcpt);

					//decrease the number of internals
					String vd = Util.getXCpt_sFormalView(sbcpt);
					for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
					{
						if (i.next().equals(vd))
						{
							String[] ar = AAj.trmapSrBrSubWorldview.get(vd);
							ar[3]=String.valueOf(Integer.parseInt(ar[3])-1);
							AAj.trmapSrBrSubWorldview.put(vd, ar);
							if (AAj.frameEditor!=null)AAj.frameEditor.trmapSrBrSubWorldview=AAj.trmapSrBrSubWorldview;
							break;
						}
					}
				}
				else //sbcpt is a file-xcpt.
				{
					//del the concept
					String fileName = Util.getXCpt_sLastFullFileName(sbcpt);
					File f1 = new File(fileName);
					f1.delete();
					//add formal-number to emptyid
					//if it is the last sbcpt decrease the lastid else put the id in emptyid.
					String FormalNumber=xcpt_sFormalID.substring(xcpt_sFormalID.indexOf("-")+1, xcpt_sFormalID.length());
					int idNumber = Integer.parseInt(FormalNumber);
					String vd = xcpt_sFormalID.substring(0, xcpt_sFormalID.indexOf("-"));
					String[] ar = AAj.trmapSrBrSubWorldview.get(vd);
					int lastFormalNumber = Integer.parseInt(ar[2]);
					if (idNumber==lastFormalNumber)
					{
						ar[2]=String.valueOf(Integer.parseInt(ar[2])-1);
						AAj.trmapSrBrSubWorldview.put(vd,ar);
						if (AAj.frameEditor!=null)AAj.frameEditor.trmapSrBrSubWorldview=AAj.trmapSrBrSubWorldview;
					}
					else {
						TreeSet<String> set = AAj.tmapEmptyID.get(vd);
						if (set==null) set=new TreeSet<String>();
						set.add(Util.getXCpt_sFormalNumber(sbcpt));
						AAj.tmapEmptyID.put(vd,set);
						if (AAj.frameEditor!=null)AAj.frameEditor.tmapEmptyID=AAj.tmapEmptyID;
					}
				}
				//ins.updateNamesRemove(sbcpt);

				//display previous or help
				if (AAj.stack.size()>1) // if it is 1, this is the first concept.
				{
					AAj.stack.pop();
					String sbcpt =  AAj.stack.pop();
					AAj.display(sbcpt);
				}
				else {
					AAj.display("AAj@hknu.it-14.xml");
				}
				dispose();
			}
		});

		final JButton cancel = new JButton(" NO ");
		buttonPane.add(cancel);
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dispose();
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
}