/**
 * This class creates a TREE with all Levels of
 * PARTS/WHOLES/GENERICS/SPECIFICS/ENVIRONMENTS of a concept.<p>
 *
 * By clicking on a node you view that concept on the 'viewer'.
 *
 * @modified
 * @since 2000.06.09
 * @author HoKoNoUmo
 */

package pk_Util;

import pk_XKBManager.AAj;
import pk_Util.Util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JViewport;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class ViewAllLevels extends JFrame
{
	static final long serialVersionUID = 21L;

	Vector<String> filesToSearch=new Vector<String>();		// Container for files-names to search for pars & gens.
	Hashtable<String,DefaultMutableTreeNode> attsFound=new Hashtable<String,DefaultMutableTreeNode>();		// File-Names of ALL attributes.
	JTree			tree=null;
	Object[]	xcpt_sDir = null;
	Vector<String> vectorParsFound=new Vector<String>();	// Holds repetitives pars found
	int				whosNumber=0;									// Will hold the number of who cpts.
	int				gensNumber=0;
	int				subsNumber=0;

	/**
	 * The constructor.
	 *
	 * @param sbcpt is the filename/id of the xcpt we want to see all its pars.
	 */
	public ViewAllLevels(String sbcpt)
	{
		sbcpt=Util.getXCpt_sLastFileName(sbcpt);
		String xcpt_sFormalID=Util.getXCpt_sFormalID(sbcpt);
		String cptNormalName=Textual.createNormalName(sbcpt);
		setTitle("View all LEVELS of RELATED-CONCEPTS of the Concept (" +cptNormalName +")");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});

		JPanel pNorth = new JPanel();
		pNorth.setBorder(BorderFactory.createEtchedBorder());
		JLabel label1 = new JLabel ("PARTS = ");
		label1.setFont (new Font ("serif", Font.PLAIN, 16));
		label1.setForeground(Color.black);
		pNorth.add (label1);
		JLabel label2 = new JLabel ();
		label2.setFont (new Font ("serif", Font.PLAIN, 18));
		label2.setForeground(Color.black);
		pNorth.add (label2);

		JLabel label3 = new JLabel ("	 WHOLES = ");
		label3.setFont (new Font ("serif", Font.PLAIN, 16));
		label3.setForeground(Color.black);
		pNorth.add (label3);
		JLabel label4 = new JLabel ();
		label4.setFont (new Font ("serif", Font.PLAIN, 18));
		label4.setForeground(Color.black);
		pNorth.add (label4);

		JLabel label5 = new JLabel ("	 GENERICS = ");
		label5.setFont (new Font ("serif", Font.PLAIN, 16));
		label5.setForeground(Color.black);
		pNorth.add (label5);
		JLabel label6 = new JLabel ();
		label6.setFont (new Font ("serif", Font.PLAIN, 18));
		label6.setForeground(Color.black);
		pNorth.add (label6);

		JLabel label7 = new JLabel ("	 SPECIFICS = ");
		label7.setFont (new Font ("serif", Font.PLAIN, 16));
		label7.setForeground(Color.black);
		pNorth.add (label7);
		JLabel label8 = new JLabel ();
		label8.setFont (new Font ("serif", Font.PLAIN, 18));
		label8.setForeground(Color.black);
		pNorth.add (label8);

		MouseListener mouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				int selRow = tree.getRowForLocation(me.getX(), me.getY() );
				TreePath selPath = tree.getPathForLocation(me.getX(), me.getY());
				String lastNode = null;
				String nodeID = null;
				if(selRow != -1 && selPath != null)
				{
					lastNode=selPath.getLastPathComponent().toString();
					if (lastNode.indexOf("(")>1)
					{
						nodeID=lastNode.substring(lastNode.indexOf("(")+1, lastNode.lastIndexOf(")"));
						if(me.getClickCount() == 1)
						{
							AAj.display(nodeID);
						}
					}
				}//selRow
			}
		};
		JPanel pCenter = new JPanel(new BorderLayout());
		//create tree
		DefaultMutableTreeNode root = createNode(sbcpt);
		DefaultTreeModel treeModel = new DefaultTreeModel(root);
		DefaultMutableTreeNode nodePar=new	DefaultMutableTreeNode("PARTS");
		DefaultMutableTreeNode nodeWho=new	DefaultMutableTreeNode("WHOLES");
		DefaultMutableTreeNode nodeGen=new	DefaultMutableTreeNode("GENERICS");
		DefaultMutableTreeNode nodeSub=new	DefaultMutableTreeNode("SPECIFICS");
		treeModel.insertNodeInto(nodePar, root, root.getChildCount());
		treeModel.insertNodeInto(nodeWho, root, root.getChildCount());
		treeModel.insertNodeInto(nodeGen, root, root.getChildCount());
		treeModel.insertNodeInto(nodeSub, root, root.getChildCount());

		Extract ex = new Extract();


		//a) find the par atts.
		ex.extractParts(sbcpt);
		Vector pars = ex.vectorParID;
		for (int i=0; i<pars.size(); i++)
		{
			String fNameID=(String)pars.get(i);
			if (!fNameID.equals("none")){
				String fName=Util.getXCpt_sLastFileName(fNameID);
				filesToSearch.add(fName);
				//insert it into tree.
				DefaultMutableTreeNode nodeNew = createNode(fName);
				attsFound.put(fName, nodeNew);
				vectorParsFound.add(fName);
				treeModel.insertNodeInto(nodeNew, nodePar, nodePar.getChildCount());
			}
		}
		//now search pars
		while(!filesToSearch.isEmpty())
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "ViewAllLevels: Problem in " + currentFileName);
				continue;				// Try next file
			}
			ex.extractParts(currentFileName);
			pars = ex.vectorParID;
			DefaultMutableTreeNode nodeToInsert = attsFound.get(currentFileName);
			for (int i=0; i<pars.size(); i++)
			{
				String fNameID=(String)pars.get(i);
				if (fNameID!=null)
				{
					String fName=Util.getXCpt_sLastFileName(fNameID);
					if (attsFound.containsKey(fName))
					{
						DefaultMutableTreeNode nodeNew = createNode(fName);
						vectorParsFound.add(fName);
						treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
					}
					else {
						filesToSearch.add(fName);
						//insert it into tree.
						DefaultMutableTreeNode nodeNew = createNode(fName);
						attsFound.put(fName, nodeNew);
						vectorParsFound.add(fName);
						treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
					}
				}
			}
			filesToSearch.remove(0);
		}


		//b) find the who atts.
		filesToSearch=new Vector<String>();
		attsFound=new Hashtable<String,DefaultMutableTreeNode>();
		ex.extractWholes(sbcpt);
		Vector whos = ex.vectorWhoID;
		for (int i=0; i<whos.size(); i++)
		{
			String fNameID=(String)whos.get(i);
			String fName=Util.getXCpt_sLastFileName(fNameID);
			filesToSearch.add(fName);
			DefaultMutableTreeNode nodeNew = createNode(fName);
			attsFound.put(fName, nodeNew);
			treeModel.insertNodeInto(nodeNew, nodeWho, nodeWho.getChildCount());
		}
		while(!filesToSearch.isEmpty())
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "ViewAllLevels: Problem in " + currentFileName);
				continue;				// Try next file
			}
			ex.extractWholes(currentFileName);
			whos = ex.vectorWhoID;
			DefaultMutableTreeNode nodeToInsert = attsFound.get(currentFileName);
			for (int i=0; i<whos.size(); i++)
			{
				String fName=(String)whos.get(i);
				if (!fName.equals("none")){
					fName=Util.getXCpt_sLastFileName(fName);
					filesToSearch.add(fName);
					//insert it into tree.
					DefaultMutableTreeNode nodeNew = createNode(fName);
					attsFound.put(fName, nodeNew);
					treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
				}
			}
			filesToSearch.remove(0);
		}
		whosNumber=attsFound.size();


		//c) find the gen atts.
		filesToSearch=new Vector<String>();
		attsFound=new Hashtable<String,DefaultMutableTreeNode>();
		ex.extractGenerics(sbcpt);
		Vector gens = ex.vectorGenID;
		for (int i=0; i<gens.size(); i++)
		{
			String fNameID=(String)gens.get(i);
			if (!fNameID.equals("none")){
				String fName=Util.getXCpt_sLastFileName(fNameID);
				filesToSearch.add(fName);
				DefaultMutableTreeNode nodeNew = createNode(fName);
				attsFound.put(fName, nodeNew);
				treeModel.insertNodeInto(nodeNew, nodeGen, nodeGen.getChildCount());
			}
		}
		while(!filesToSearch.isEmpty())
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "ViewAllLevels: Problem in " + currentFileName);
				continue;				// Try next file
			}
			ex.extractGenerics(currentFileName);
			gens = ex.vectorGenID;
			DefaultMutableTreeNode nodeToInsert = attsFound.get(currentFileName);
			for (int i=0; i<gens.size(); i++)
			{
				String fName=(String)gens.get(i);
				if (!fName.equals("none")){
					fName=Util.getXCpt_sLastFileName(fName);
					filesToSearch.add(fName);
					//insert it into tree.
					DefaultMutableTreeNode nodeNew = createNode(fName);
					attsFound.put(fName, nodeNew);
					treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
				}
			}
			filesToSearch.remove(0);
		}
		gensNumber=attsFound.size();

		//d) find the spe atts.
		filesToSearch=new Vector<String>();
		attsFound=new Hashtable<String,DefaultMutableTreeNode>();
		ex.extractSpecifics(sbcpt);
		Vector subs = ex.vectorSpeID;
		for (int i=0; i<subs.size(); i++)
		{
			String fNameID=(String)subs.get(i);
			String fName=Util.getXCpt_sLastFileName(fNameID);
			filesToSearch.add(fName);
			DefaultMutableTreeNode nodeNew = createNode(fName);
			attsFound.put(fName, nodeNew);
			treeModel.insertNodeInto(nodeNew, nodeSub, nodeSub.getChildCount());
		}
		while(!filesToSearch.isEmpty())
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "ViewAllLevels: Problem in " + currentFileName);
				continue;				// Try next file
			}
			ex.extractSpecifics(currentFileName);
			subs = ex.vectorSpeID;
			DefaultMutableTreeNode nodeToInsert = attsFound.get(currentFileName);

			for (int i=0; i<subs.size(); i++)
			{
				String fName=(String)subs.get(i);
				fName=Util.getXCpt_sLastFileName(fName);
				filesToSearch.add(fName);
				//insert it into tree.
				DefaultMutableTreeNode nodeNew = createNode(fName);
				attsFound.put(fName, nodeNew);
				treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
			}
			filesToSearch.remove(0);
		}
		subsNumber=attsFound.size();

		//I subtract the current concept.
		label2.setText(String.valueOf(vectorParsFound.size()));
		label4.setText(String.valueOf(whosNumber));
		label6.setText(String.valueOf(gensNumber));
		label8.setText(String.valueOf(subsNumber));

		tree = new JTree(treeModel);
		tree.setFont(new Font("serif",Font.PLAIN,16));
		tree.setRootVisible(true);
		tree.setShowsRootHandles(true);
		tree.setRowHeight(17);
		DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer) tree.getCellRenderer();
		tcr.setClosedIcon(null);
		tcr.setOpenIcon(null);
		tcr.setLeafIcon(null);
		tree.addMouseListener(mouseListener);
		JScrollPane scrollPane = new JScrollPane();
		JViewport vp = scrollPane.getViewport();
		vp.add(tree);
		pCenter.add("Center", scrollPane);

		JPanel pSouth = new JPanel();
		pSouth.setBorder(BorderFactory.createEtchedBorder());
		JLabel label9 = new JLabel ("By clicking on a SC you see this concept on the AAj-VIEWER");
		label9.setFont (new Font ("serif", Font.PLAIN, 16));
		label9.setForeground(Color.black);
		pSouth.add(label9);

		// and get the GUI components onto our content pane.
		getContentPane().add(pNorth, BorderLayout.NORTH);
		getContentPane().add(pCenter, BorderLayout.CENTER);
		getContentPane().add(pSouth, BorderLayout.SOUTH);

		Util.setFrameIcon(this);
		setSize(new Dimension(676,374));
		setLocation(126,126);
		setVisible(true);
	} //end ViewAllLevels

	/**
	 * Takes a file-name and returns a node (normalname (id)).
	 * @modified 1999.05.01
	 * @since 1999.05.01
	 * @author HoKoNoUmo
	 */
	public DefaultMutableTreeNode createNode(String fName)
	{
		String xcpt_sName = Textual.createNormalName(fName);
		String id = fName.substring(fName.indexOf("@")+1, fName.length()-4);
		String nodeName = xcpt_sName +" (" +id +")";
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
		return node;
	}

}
