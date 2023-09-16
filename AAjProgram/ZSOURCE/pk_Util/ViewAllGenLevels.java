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
import java.util.Map;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
 * This class creates a TREE with all GENERICS of a concept.
 * By double-clicking on a node you view that concept on the 'viewer'.
 *
 * @modified 2000.03.11
 * @since 2000.03.10
 * @author HoKoNoUmo
 */
public class ViewAllGenLevels extends JFrame
{
	static final long serialVersionUID = 21L;

	Vector<String> filesToSearch=new Vector<String>();		// Container for files-names to search for gens.
	Map<String,DefaultMutableTreeNode> gensFound=new HashMap<String,DefaultMutableTreeNode>();					// File-Names of ALL specifics.

	JTree tree=null;

	Object[] xcpt_sDir = null;

	/**
	 * @modified
	 * @since 2000.03.10
	 * @author HoKoNoUmo
	 */
	public ViewAllGenLevels(String sbcpt)
	{
		sbcpt=Util.getXCpt_sLastFileName(sbcpt);
		String cptNormalName=Textual.createNormalName(sbcpt);
		setTitle("View ALL GENERICS of Concept (" +cptNormalName +")");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {
				dispose();
			}
		});

		JPanel pNorth = new JPanel();
		pNorth.setBorder(BorderFactory.createEtchedBorder());
		JLabel label1 = new JLabel ("The TOTAL number of generics is:	 ");
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

		//insert data into treeModel.
		filesToSearch.add(sbcpt);
		gensFound.put(sbcpt, root);
		while(!filesToSearch.isEmpty())
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "ViewAllGenLevels: Problem in " + currentFileName);
				continue;				// Try next file
			}
			Extract ex = new Extract();
			ex.extractGenerics(currentFileName);
			Vector gens = ex.vectorGenID;
			DefaultMutableTreeNode nodeToInsert = gensFound.get(currentFileName);

			for (int i=0; i<gens.size(); i++)
			{
				String fName=(String)gens.get(i);
				if (!fName.equals("none")){
					fName=Util.getXCpt_sLastFileName(fName);
					filesToSearch.add(fName);
					//insert it into tree.
					DefaultMutableTreeNode nodeNew = createNode(fName);
					gensFound.put(fName, nodeNew);
					treeModel.insertNodeInto(nodeNew, nodeToInsert, nodeToInsert.getChildCount());
				}
			}
			filesToSearch.remove(0);
		}
		//I subtract the current concept.
		label2.setText(String.valueOf(gensFound.size()-1));
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
	} //end ViewAllGenLevels

	/**
	 * Takes a file-name and returns a node (normalname (id)).
	 * @modified 1999.05.01
	 * @since 1999.05.01
	 * @author HoKoNoUmo
	 */
	public DefaultMutableTreeNode createNode(String fName)
	{
		String xcpt_sName = Textual.createNormalName(fName);
		String id = Util.getXCpt_sFormalID(fName);
		String nodeName = xcpt_sName +" (" +id +")";
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(nodeName);
		return node;
	}

}
