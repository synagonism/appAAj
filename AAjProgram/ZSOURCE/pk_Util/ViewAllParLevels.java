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

/**
 * This class creates a TREE with all PARTS of a concept.<p>
 *
 * PARTS OF A CONCEPT I mean the first-level pars
 * add recursivly
 * the pars of them and their subs that are ABSTRACT (not cocrete) IF the xcpt is an abstract one.
 * [2000.04.10]
 * eg for the "SBCS" sbcpt we show only 'generic' pars, NOT for AAj or fvSBCS pars.
 * id: the one that have NOT as whole a concrete sbcpt.<p>
 *
 * By clicking on a node you view that concept on the 'viewer'.
 *
 * @modified 2000.06.08
 * @since 2000.03.11
 * @author HoKoNoUmo
 */
public class ViewAllParLevels extends JFrame
{
	static final long serialVersionUID = 21L;

	Vector<String> filesToSearch=new Vector<String>();		// Container for files-names to search for pars & gens.
	Vector<String> repetitives=new Vector<String>();			// Container for repetitive files, source for vicious circles.
	Hashtable<String,DefaultMutableTreeNode> parsFound=new Hashtable<String,DefaultMutableTreeNode>();		// File-Names of ALL attributes.
	Vector	<String> vectorParsFound=new Vector<String>();	// Holds repetitives pars found
	JTree			tree=null;
	Object[]	xcpt_sDir = null;

	/**
	 * The constructor.
	 *
	 * @param sbcpt is the filename/id of the xcpt we want to see all its pars.
	 */
	public ViewAllParLevels(String sbcpt)
	{
		sbcpt=Util.getXCpt_sLastFileName(sbcpt);
		String xcpt_sFormalID=Util.getXCpt_sFormalID(sbcpt);
		String cptNormalName=Textual.createNormalName(sbcpt);
		setTitle("View ALL PARTS of Concept (" +cptNormalName +")");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});

		JPanel pNorth = new JPanel();
		pNorth.setBorder(BorderFactory.createEtchedBorder());
		JLabel label1 = new JLabel ("The TOTAL number of PARTS is:	");
		label1.setFont (new Font ("serif", Font.PLAIN, 16));
		label1.setForeground(Color.black);
		pNorth.add (label1);
		JLabel label2 = new JLabel ();
		label2.setFont (new Font ("serif", Font.PLAIN, 18));
		label2.setForeground(Color.black);
		pNorth.add (label2);

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
		DefaultMutableTreeNode root = createNode(sbcpt);
		DefaultTreeModel treeModel = new DefaultTreeModel(root);

		//find all gens of sbcpt
//		ex.extractAllGen(xcpt_sFormalID);
//		Vector vAllGen=ex.vectorAllGenID;
//		vAllGen.add(xcpt_sFormalID);

		Extract ex = new Extract();
		ex.extractParts(sbcpt);
		Vector pars = ex.vectorParID;
		for (int i=0; i<pars.size(); i++)
		{
			String fNameID=(String)pars.get(i);
			String fName=Util.getXCpt_sLastFileName(fNameID);
			filesToSearch.add(fName);
			//insert it into tree.
			DefaultMutableTreeNode nodeNew = createNode(fName);
			parsFound.put(fName, nodeNew);
			vectorParsFound.add(fName);
			treeModel.insertNodeInto(nodeNew, root, root.getChildCount());
		}

		//now search pars&subs
		while(!filesToSearch.isEmpty())
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "ViewAllParLevels: Problem in " + currentFileName);
				continue;				// Try next file
			}

			//extract pars.
			ex.extractParts(currentFileName);
			pars = ex.vectorParID;
			DefaultMutableTreeNode nodeToInsert = parsFound.get(currentFileName);
			for (int i=0; i<pars.size(); i++)
			{
				String fNameID=(String)pars.get(i);
				if (fNameID!=null)
				{
					String fName=Util.getXCpt_sLastFileName(fNameID);

					if (parsFound.containsKey(fName))
					{
					repetitives.add(fName +"		of	>>>		" +currentFileName);
						DefaultMutableTreeNode nodeNew = createNode(fName);
						vectorParsFound.add(fName);
						treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
					}
					else {
						filesToSearch.add(fName);
						//insert it into tree.
						DefaultMutableTreeNode nodeNew = createNode(fName);
						parsFound.put(fName, nodeNew);
						vectorParsFound.add(fName);
						treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
					}
				}
			}

/*
			//extract subs
			ex.extractSpecifics(currentFileName);
			Vector subs = ex.vectorSpeID;
			nodeToInsert = (DefaultMutableTreeNode)parsFound.get(currentFileName);
			for (int i=0; i<subs.size(); i++)
			{
				String subID=(String)subs.get(i);
				String fName=Util.getXCpt_sLastFileName(subID);
				if (fName!=null)
				{
					//add for search only the ones with whole only the xcpt.
					//TODO: we must check if the whole of the subs is 'partof' the xcpt. 2000.05.20
					ex.extractWholes(subID);
					Vector whos=ex.vectorWhoID;
//					if (vAllGen.containsAll(whos))// && whos.size()<2)
					if (whos.contains(xcpt_sFormalID))
					{

						if (parsFound.containsKey(fName))
						{
							repetitives.add(fName +"		of	>>>		" +currentFileName);
						}
//					if (subID.equals(currentFileName))
//					{
//						repetitives.add(fName +"		of	>>>		" +currentFileName);
//					}
						else {
							filesToSearch.add(fName);
							//insert it into tree.
							DefaultMutableTreeNode nodeNew = createNode(fName);
							parsFound.put(fName, nodeNew);
							treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
						}
					}
				}
			}
*/
			filesToSearch.remove(0);
		}

		showRepetitives();

		//I subtract the current concept.
		label2.setText(String.valueOf(vectorParsFound.size()));
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
		JLabel label3 = new JLabel ("By clicking on a SC you see this concept on the AAj-VIEWER");
		label3.setFont (new Font ("serif", Font.PLAIN, 16));
		label3.setForeground(Color.black);
		pSouth.add(label3);

		// and get the GUI components onto our content pane.
		getContentPane().add(pNorth, BorderLayout.NORTH);
		getContentPane().add(pCenter, BorderLayout.CENTER);
		getContentPane().add(pSouth, BorderLayout.SOUTH);

		Util.setFrameIcon(this);
		setSize(new Dimension(676,374));
		setLocation(126,126);
		setVisible(true);
	} //end ViewAllParLevels

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

	public void showRepetitives()
	{
		JFrame frame = new JFrame("Check the Repetitive Parts");
//		frame.addWindowListener(new WindowAdapter()
//		{
//			public void windowClosing(WindowEvent we) {dispose();}
//		});

		JList list = new JList(repetitives);
		list.setFixedCellHeight(15);
		JScrollPane scrollPane = new JScrollPane(list);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		Util.setFrameIcon(frame);
		frame.setLocation(50, 50);
		frame.pack();
		frame.setVisible(true);
	}
}
