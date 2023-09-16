package pk_Util;
import pk_XKBManager.AAj;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

/**
 * It shows a list with the concepts visited.
 * @modified
 * @since 2000.03.29
 * @author HoKoNoUmo
 */
public class History extends JFrame
{
	static final long serialVersionUID = 21L;
	JList jlistHistory;				// List to display the concepts visited.

	/**
	 * The constructor.
	 * @modified
	 * @since 2000.03.29
	 * @author HoKoNoUmo
	 */
	public History()
	{
		super("XConcepts Visited");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we) {dispose();}
		});

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEtchedBorder());

		jlistHistory = new JList();
		setData();
		jlistHistory.setFixedCellHeight(15);
		jlistHistory.setSelectedIndex(0);
		JScrollPane scrollPane = new JScrollPane(jlistHistory);
//		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panel.add("Center", scrollPane);

		MouseListener mouseListener = new MouseAdapter()
		{
			public void mouseClicked(MouseEvent me)
			{
				if (me.getClickCount() == 1)
				{
					JList list = (JList)me.getSource();
					int index = list.locationToIndex(me.getPoint());
					list.setSelectedIndex(index);
					String selection = (String)list.getSelectedValue();
					if (selection!=null)
					{
						AAj.display(selection);
					}
					dispose();
				}
			}
		};
		jlistHistory.addMouseListener(mouseListener);

		getContentPane().add(panel, BorderLayout.CENTER);
		setIconImage( Util.getImage("resources/AAj_icon.gif") );
		setSize(new Dimension(259,426));
		setLocation(16,76);
		setVisible(true);
	} //end History

	/**
	 * @modified
	 * @since 2000.03.29
	 * @author HoKoNoUmo
	 */
	public void setData()
	{
		Object[] cpts = new Object[AAj.stack.size()];
		for (int i=0; i<AAj.stack.size(); i++)
		{
			cpts[AAj.stack.size()-(i+1)]=AAj.stack.elementAt(i);
		}
		jlistHistory.setListData(cpts);
	}

}