package pk_XKBEditor;

import pk_XKBManager.AAj;
import pk_Util.Util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Creates a New SrWorldview.
 * TODO: This function must create the directory in real terms.
 *
 * @modified 2001.03.08
 * @since 1999.04.10
 * @author HoKoNoUmo
 */
public class FrameAddSrWorldview extends JFrame
{
	static final long serialVersionUID = 21L;

	public FrameAddSrWorldview()
	{
		super("Add a NEW SrWorldview");

		//Create the labels and tFields
		JLabel labelName = new JLabel("Name (short)");
		JLabel labelDescription = new JLabel("Description");
		JLabel labelDirectory = new JLabel("Directory");
		final JTextField fieldName = new JTextField(26);
		final JTextField fieldDescription = new JTextField(26);
		final JTextField fieldDirectory = new JTextField(26);

		//Create the Layouts.
		JPanel labelPane = new JPanel();
		labelPane.setLayout(new GridLayout(0, 1));
		labelPane.add(labelName);
		labelPane.add(labelDescription);
		labelPane.add(labelDirectory);

		JPanel fieldPane = new JPanel();
		fieldPane.setLayout(new GridLayout(0, 1));
		fieldPane.add(fieldName);
		fieldPane.add(fieldDescription);
		fieldPane.add(fieldDirectory);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout());
		final JButton ok = new JButton("	OK	");
		buttonPane.add(ok);
		ok.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				String[] a = new String[3];
				String key = fieldName.getText();
				a[0] = fieldDescription.getText();
				a[1] = fieldDirectory.getText();
				a[2] = String.valueOf(0);
				if (AAj.trmapSrBrSubWorldview.containsKey(key))
				{
					JOptionPane.showMessageDialog(null,
						"KnowledgeBase already exists, USE another one",
						"Warning", JOptionPane.WARNING_MESSAGE);
				}
				else {
					if (key.equals(""))
					{
						dispose();
					}
					else {
						AAj.trmapSrBrSubWorldview.put(key, a);
						dispose();
					}
				}
			}
		});
		final JButton cancel = new JButton("Cancel");
		buttonPane.add(cancel);
		cancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dispose();
			}
		});

		//Put the panels in another panel, labels on left, text fields on right
		JPanel contentPane = new JPanel();
		contentPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
		contentPane.setLayout(new BorderLayout());
		contentPane.add(labelPane, BorderLayout.CENTER);
		contentPane.add(fieldPane, BorderLayout.EAST);
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
