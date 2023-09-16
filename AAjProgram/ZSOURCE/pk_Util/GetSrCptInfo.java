package pk_Util;

import pk_XKBManager.AAj;
import pk_Util.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @modified 2000.06.25
 * @since 2000.03.13
 * @author HoKoNoUmo
 */
public class GetSrCptInfo extends JFrame
{
	static final long serialVersionUID = 21L;
	ThreadCountAllSub threadSub=null;
	ThreadCountAllGen threadGen=null;
	ThreadCountAllPar threadPar=null;
	ThreadCountAllWho threadWho=null;
	String						xcpt_sFormalID=null;

	JButton 					jbSubAllFind=null;
	JButton 					jbGenAllFind=null;
	JButton 					jbAttAllFind=null;
	JButton 					jbWhoAllFind=null;
	JButton 					jbSumAllFind=null;
	JButton 					jbSubAllStop=null;
	JButton 					jbGenAllStop=null;
	JButton 					jbAttAllStop=null;
	JButton 					jbWhoAllStop=null;

	JTextField 				fieldSum=null;
	JTextField 				fieldEnv=null;
	JTextField 				fieldSubAll=null;
	JTextField 				fieldGenAll=null;
	JTextField 				fieldAttAll=null;
	JTextField 				fieldWhoAll=null;
	JTextField 				fieldSumAll=null;


	/**
	 * @modified 2009.08.04
	 * @since 2000.03.13
	 * @author HoKoNoUmo
	 */
	public GetSrCptInfo(String sbcpt)
	{
		super("Information on SensorialConcept (" +Textual.createNormalName(sbcpt) +")");
		xcpt_sFormalID=Util.getXCpt_sFormalID(sbcpt);

		JLabel labelFRN = new JLabel("Formal-Name =",JLabel.RIGHT);
		labelFRN.setFont(new Font("serif", Font.BOLD, 16));
		JLabel labelFRN2 = new JLabel(Util.getXCpt_sFormalName(sbcpt),JLabel.CENTER);
		labelFRN2.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelE = new JLabel("       ");
		JLabel labelID = new JLabel("FormalID =",JLabel.RIGHT);
		labelID.setFont(new Font("serif", Font.BOLD, 16));
		JLabel labelID2 = new JLabel(Util.getXCpt_sFormalID(sbcpt),JLabel.CENTER);
		labelID2.setFont(new Font("serif", Font.PLAIN, 16));
		//author row.
		JLabel labelAuthor = new JLabel("Author =");
		labelAuthor.setFont(new Font("serif", Font.BOLD, 16));
		JLabel labelAuthor2 = new JLabel(AAj.xcpt_sAuthor);
		labelAuthor2.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelCreated = new JLabel("Created: =");
		labelCreated.setFont(new Font("serif", Font.BOLD, 16));
		JLabel labelCreated2 = new JLabel(AAj.xcpt_sCreated);
		labelCreated2.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelModified = new JLabel("LastModified =");
		labelModified.setFont(new Font("serif", Font.BOLD, 16));
		JLabel labelModified2 = new JLabel(AAj.xcpt_sModified);
		labelModified2.setFont(new Font("serif", Font.PLAIN, 16));

		//Create the left components
		JLabel labelDesg = new JLabel("Number of NAMES = ",JLabel.RIGHT);
		//toDo: "All-terms" 2009.06.10
		labelDesg.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelAtt = new JLabel("Number of PARTS = ",JLabel.RIGHT);
		labelAtt.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelWho = new JLabel("Number of WHOLES = ",JLabel.RIGHT);
		labelWho.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelGen = new JLabel("Number of GENERICS = ",JLabel.RIGHT);
		labelGen.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelSub = new JLabel("Number of SPECIFICS = ",JLabel.RIGHT);
		labelSub.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelDim = new JLabel("Number of DIVISIONS = ",JLabel.RIGHT);
		labelDim.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelEnv = new JLabel("Number of ENVIRONMENTS = ",JLabel.RIGHT);
		labelEnv.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelSum = new JLabel("SUM of ATTRIBUTES = ",JLabel.RIGHT);
		labelSum.setFont(new Font("serif", Font.BOLD, 16));
		JLabel labelAttAll = new JLabel("Number of ***ALL*** PARTS = ",JLabel.RIGHT);
		labelAttAll.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelWhoAll = new JLabel("Number of ***ALL*** WHOLES = ",JLabel.RIGHT);
		labelWhoAll.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelGenAll = new JLabel("Number of ***ALL*** GENERICS = ",JLabel.RIGHT);
		labelGenAll.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelSubAll = new JLabel("Number of ***ALL*** SPECIFICS = ",JLabel.RIGHT);
		labelSubAll.setFont(new Font("serif", Font.PLAIN, 16));
		JLabel labelSumAll = new JLabel("SUM of ALL ATTRIBUTES = ",JLabel.RIGHT);
		labelSumAll.setFont(new Font("serif", Font.BOLD, 16));

		//create the center components.
		JTextField fieldSyn = new JTextField(4);
		fieldSyn.setFont(new Font("serif", Font.BOLD, 14));
		fieldSyn.setHorizontalAlignment(JTextField.CENTER);
		JTextField fieldAtt = new JTextField(4);
		fieldAtt.setFont(new Font("serif", Font.BOLD, 14));
		fieldAtt.setHorizontalAlignment(JTextField.CENTER);
		JTextField fieldWho = new JTextField(4);
		fieldWho.setFont(new Font("serif", Font.BOLD, 14));
		fieldWho.setHorizontalAlignment(JTextField.CENTER);
		JTextField fieldGen = new JTextField(4);
		fieldGen.setFont(new Font("serif", Font.BOLD, 14));
		fieldGen.setHorizontalAlignment(JTextField.CENTER);
		JTextField fieldSub = new JTextField(4);
		fieldSub.setFont(new Font("serif", Font.BOLD, 14));
		fieldSub.setHorizontalAlignment(JTextField.CENTER);
		JTextField fieldDim = new JTextField(4);
		fieldDim.setFont(new Font("serif", Font.BOLD, 14));
		fieldDim.setHorizontalAlignment(JTextField.CENTER);
		fieldEnv = new JTextField(4);
		fieldEnv.setFont(new Font("serif", Font.BOLD, 14));
		fieldEnv.setHorizontalAlignment(JTextField.CENTER);
		fieldSum = new JTextField(4);
		fieldSum.setFont(new Font("serif", Font.BOLD, 16));
		fieldSum.setHorizontalAlignment(JTextField.CENTER);
		fieldAttAll = new JTextField(4);
		fieldAttAll.setFont(new Font("serif", Font.BOLD, 14));
		fieldAttAll.setHorizontalAlignment(JTextField.CENTER);
		fieldWhoAll = new JTextField(4);
		fieldWhoAll.setFont(new Font("serif", Font.BOLD, 14));
		fieldWhoAll.setHorizontalAlignment(JTextField.CENTER);
		fieldGenAll = new JTextField(4);
		fieldGenAll.setFont(new Font("serif", Font.BOLD, 14));
		fieldGenAll.setHorizontalAlignment(JTextField.CENTER);
		fieldSubAll = new JTextField(4);
		fieldSubAll.setFont(new Font("serif", Font.BOLD, 14));
		fieldSubAll.setHorizontalAlignment(JTextField.CENTER);
		fieldSumAll = new JTextField(4);
		fieldSumAll.setFont(new Font("serif", Font.BOLD, 14));
		fieldSumAll.setHorizontalAlignment(JTextField.CENTER);

		//create the right components
		JLabel labelE1 = new JLabel(" ");
		JLabel labelE2 = new JLabel(" ");
		//toDo: on every attribute we can show with a shash the inherited ones.
		//ex. PARTS: 3/1 (= total=3, inherited=1, nonInherited=2) 2009.06.10
		JLabel labelAttNih = new JLabel("Non-Inherited="+String.valueOf(AAj.intParNInh));
		labelAttNih.setFont(new Font("serif", Font.PLAIN, 12));
		JLabel labelAttInh = new JLabel("Inherited="+String.valueOf(AAj.intParInh));
		labelAttInh.setFont(new Font("serif", Font.PLAIN, 12));
		JLabel labelE4 = new JLabel(" ");
		JLabel labelE5 = new JLabel(" ");
		JButton jbHelp = new JButton("Help");
		jbHelp.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				AAj.display("it-49#2");
				AAj.frameAAj.toFront();
				AAj.frameAAj.requestFocus();
			}
		});
		JLabel labelE7 = new JLabel(" ");
		JLabel labelE8 = new JLabel(" ");
		JLabel labelE9 = new JLabel(" ");
		JLabel labelE10 = new JLabel(" ");
		JLabel labelE11 = new JLabel(" ");
		jbAttAllFind = new JButton("find");
		jbAttAllFind.setActionCommand("findPar");
		jbAttAllFind.addActionListener(new FindAction());
		jbWhoAllFind = new JButton("find");
		jbWhoAllFind.setActionCommand("findWho");
		jbWhoAllFind.addActionListener(new FindAction());
		jbGenAllFind = new JButton("find");
		jbGenAllFind.setActionCommand("findGen");
		jbGenAllFind.addActionListener(new FindAction());
		jbSubAllFind = new JButton("find");
		jbSubAllFind.setActionCommand("findSub");
		jbSubAllFind.addActionListener(new FindAction());
		jbSumAllFind = new JButton("find");
		jbSumAllFind.setActionCommand("findSum");
		jbSumAllFind.addActionListener(new FindAction());

		jbAttAllStop = new JButton("stop");
		jbAttAllStop.setEnabled(false);
		jbAttAllStop.setActionCommand("stopPar");
		jbAttAllStop.addActionListener(new StopAction());
		jbWhoAllStop = new JButton("stop");
		jbWhoAllStop.setEnabled(false);
		jbWhoAllStop.setActionCommand("stopWho");
		jbWhoAllStop.addActionListener(new StopAction());
		jbGenAllStop = new JButton("stop");
		jbGenAllStop.setEnabled(false);
		jbGenAllStop.setActionCommand("stopGen");
		jbGenAllStop.addActionListener(new StopAction());
		jbSubAllStop = new JButton("stop");
		jbSubAllStop.setEnabled(false);
		jbSubAllStop.setActionCommand("stopSub");
		jbSubAllStop.addActionListener(new StopAction());

		//Create the Layouts.
		JPanel paneNorth = new JPanel(new BorderLayout());
		paneNorth.setBorder(BorderFactory.createEtchedBorder());
		JPanel paneNorth1 = new JPanel();
		paneNorth1.add(labelFRN);
		paneNorth1.add(labelFRN2);
		paneNorth1.add(labelE);
		paneNorth1.add(labelID);
		paneNorth1.add(labelID2);
		JPanel paneNorth2 = new JPanel();
		paneNorth2.add(labelAuthor);
		paneNorth2.add(labelAuthor2);
		paneNorth2.add(labelCreated);
		paneNorth2.add(labelCreated2);
		paneNorth2.add(labelModified);
		paneNorth2.add(labelModified2);
		paneNorth.add(paneNorth1, BorderLayout.NORTH);
		paneNorth.add(paneNorth2, BorderLayout.SOUTH);
		paneNorth1.setBackground(Color.cyan);
		paneNorth2.setBackground(Color.cyan);

		JPanel paneLeft = new JPanel();
		paneLeft.setLayout(new GridLayout(0, 1));
		paneLeft.add(labelDesg);
		paneLeft.add(labelAtt);
		paneLeft.add(labelWho);
		paneLeft.add(labelGen);
		paneLeft.add(labelSub);
		paneLeft.add(labelDim);
		paneLeft.add(labelEnv);
		paneLeft.add(labelSum);
		paneLeft.add(labelAttAll);
		paneLeft.add(labelWhoAll);
		paneLeft.add(labelGenAll);
		paneLeft.add(labelSubAll);
		paneLeft.add(labelSumAll);

		JPanel paneCenter = new JPanel();
		paneCenter.setLayout(new GridLayout(0, 1));
		paneCenter.add(fieldSyn);
		paneCenter.add(fieldAtt);
		paneCenter.add(fieldWho);
		paneCenter.add(fieldGen);
		paneCenter.add(fieldSub);
		paneCenter.add(fieldDim);
		paneCenter.add(fieldEnv);
		paneCenter.add(fieldSum);
		paneCenter.add(fieldAttAll);
		paneCenter.add(fieldWhoAll);
		paneCenter.add(fieldGenAll);
		paneCenter.add(fieldSubAll);
		paneCenter.add(fieldSumAll);

		JPanel paneRight = new JPanel();
		paneRight.setLayout(new GridLayout(0, 1));
		paneRight.add(labelE1); 	//syn
		JPanel pAttI = new JPanel();
		pAttI.add(labelAttNih);
		pAttI.add(labelAttInh);
		paneRight.add(pAttI); 		//att
		paneRight.add(labelE4); 	//who
		paneRight.add(labelE5);		//gen
		JPanel pHelp = new JPanel();
		pHelp.add(jbHelp);
		paneRight.add(pHelp);			//spe
		paneRight.add(labelE8);		//dim
		paneRight.add(labelE9);		//env
		paneRight.add(labelE10);	//sum
		JPanel pAtt = new JPanel();
		pAtt.add(jbAttAllFind);
		pAtt.add(jbAttAllStop);
		paneRight.add(pAtt);
		JPanel pWho = new JPanel();
		pWho.add(jbWhoAllFind);
		pWho.add(jbWhoAllStop);
		paneRight.add(pWho);
		JPanel pGen = new JPanel();
		pGen.add(jbGenAllFind);
		pGen.add(jbGenAllStop);
		paneRight.add(pGen);
		JPanel pSub = new JPanel();
		pSub.add(jbSubAllFind);
		pSub.add(jbSubAllStop);
		paneRight.add(pSub);
		JPanel pSumAll = new JPanel();
		pSumAll.add(jbSumAllFind);
		paneRight.add(pSumAll);

		//set the numbers.
		fieldSyn.setText(String.valueOf(AAj.intName));
		fieldAtt.setText(String.valueOf(AAj.intPar));
		fieldWho.setText(String.valueOf(AAj.intWho));
		fieldGen.setText(String.valueOf(AAj.intGen));
		fieldSub.setText(String.valueOf(AAj.intSpe));
		fieldDim.setText(String.valueOf(AAj.intDiv));
		fieldEnv.setText(String.valueOf(AAj.intEnv));
		int sum = AAj.intPar +AAj.intWho +AAj.intGen +AAj.intSpe +AAj.intEnv;
		fieldSum.setText(String.valueOf(sum));
//    CountSubAll csub = new CountSubAll(sbcpt);
//    Vector subsAll = csub.subsFound;
//    fieldSubAll.setText(String.valueOf(subsAll.size()));

		//Put the panels in another panel, labels on left, text fields on right
		JPanel paneRelations = new JPanel();
		paneRelations.setBorder(BorderFactory.createEmptyBorder(2, 20, 2, 20));
		paneRelations.setLayout(new BorderLayout());
		paneRelations.add(paneLeft, BorderLayout.WEST);
		paneRelations.add(paneCenter, BorderLayout.CENTER);
		paneRelations.add(paneRight, BorderLayout.EAST);

		JPanel paneMain = new JPanel();
		paneMain.setBorder(BorderFactory.createEtchedBorder());
		paneMain.setLayout(new BorderLayout());
		paneMain.add(paneNorth, BorderLayout.NORTH);
		paneMain.add(paneRelations, BorderLayout.CENTER);
		//I can put in a scroll if it is very large.

		getContentPane().add(paneMain, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});

		pack();
		Util.setFrameIcon(this);
//    setLocation(126,126);
		setSize(new Dimension(559, 559));
		setVisible(true);
	}

	/**
	 * @modified
	 * @since 2000.03.13
	 * @author HoKoNoUmo
	 */
	class FindAction extends AbstractAction
	{
		static final long serialVersionUID = 43L;
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("findSub"))
			{
				jbSubAllFind.setEnabled(false);
				jbSubAllStop.setEnabled(true);
				fieldSubAll.setText("");
				threadSub = new ThreadCountAllSub(xcpt_sFormalID, jbSubAllFind, jbSubAllStop, fieldSubAll);
				//String cid, JButton findBtn, JButton stopBtn, JTextField jtf
				threadSub.start();
			}
			if (e.getActionCommand().equals("findGen"))
			{
				jbGenAllFind.setEnabled(false);
				jbGenAllStop.setEnabled(true);
				fieldGenAll.setText("");
				threadGen = new ThreadCountAllGen(xcpt_sFormalID, jbGenAllFind, jbGenAllStop, fieldGenAll);
				//String cid, JButton findBtn, JButton stopBtn, JTextField jtf
				threadGen.start();
			}
			if (e.getActionCommand().equals("findPar"))
			{
				jbAttAllFind.setEnabled(false);
				jbAttAllStop.setEnabled(true);
				fieldAttAll.setText("");
				threadPar = new ThreadCountAllPar(xcpt_sFormalID, jbAttAllFind, jbAttAllStop, fieldAttAll);
				//String cid, JButton findBtn, JButton stopBtn, JTextField jtf
				threadPar.start();
			}
			if (e.getActionCommand().equals("findWho"))
			{
				jbWhoAllFind.setEnabled(false);
				jbWhoAllStop.setEnabled(true);
				fieldWhoAll.setText("");
				threadWho = new ThreadCountAllWho(xcpt_sFormalID, jbWhoAllFind, jbWhoAllStop, fieldWhoAll);
				//String cid, JButton findBtn, JButton stopBtn, JTextField jtf
				threadWho.start();
			}
			if (e.getActionCommand().equals("findSum"))
			{
				fieldSumAll.setText("");
				String strEnv=fieldEnv.getText();
				String strSubAll=fieldSubAll.getText();
				String strGenAll=fieldGenAll.getText();
				String strAttAll=fieldAttAll.getText();
				String strWhoAll=fieldWhoAll.getText();
				int sum= Integer.parseInt(strEnv)
								+Integer.parseInt(strSubAll)
								+Integer.parseInt(strGenAll)
								+Integer.parseInt(strAttAll)
								+Integer.parseInt(strWhoAll);
				fieldSumAll.setText(String.valueOf(sum));
			}
		}
	}

	/**
	 * @modified 2000.06.09
	 * @since 2000.03.13
	 * @author HoKoNoUmo
	 */
	class StopAction extends AbstractAction
	{
		static final long serialVersionUID = 44L;
		public void actionPerformed(ActionEvent e)
		{
			if (e.getActionCommand().equals("stopSub"))
			{
				if (threadSub != null) ThreadCountAllSub.abortThread=true;
				threadSub = null;
			}
			if (e.getActionCommand().equals("stopGen"))
			{
				if (threadGen != null) ThreadCountAllGen.abortThread=true;
				threadGen = null;
			}
			if (e.getActionCommand().equals("stopPar"))
			{
				if (threadPar != null) ThreadCountAllPar.abortThread=true;
				threadPar = null;
			}
			if (e.getActionCommand().equals("stopWho"))
			{
				if (threadWho != null) ThreadCountAllWho.abortThread=true;
				threadWho = null;
			}
		}
	}

}