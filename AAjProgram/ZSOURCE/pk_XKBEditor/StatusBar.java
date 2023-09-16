/**
 * @modified
 * @since 2000.02.27
 * StatusBar.java -
 * Copyright (C) 1999-2001 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * Hacked from Stephen Withall.
 */
package pk_XKBEditor;

import javax.swing.border.Border;
import javax.swing.border.BevelBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;

public class StatusBar extends JPanel
{
	static final long serialVersionUID = 21L;
		/** The area in which the main status text is displayed. */
		JTextField		StatusText				= new JTextField();

		/** If this status bar is called upon to display the progress of some
		 *	activity, it is displayed here. */
		JProgressBar	StatusProgressBar = new JProgressBar();

		/** The border used for normal components which have a border. */
		Border				StandardBorder		= null;

		public StatusBar()
		{
			super();

//				StandardBorder = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
//				setBorder(StandardBorder);

				setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

				StatusText.setEditable(false);
				StatusText.setBorder(StandardBorder);
				StatusText.setForeground(Color.black);
				StatusText.setBackground(Color.lightGray);
				StatusText.setFont(new Font("SansSerif", Font.PLAIN, 14));
				add(StatusText);
		}

		/**
		 *	@param	InputText	 The text to be displayed on the status bar
		 */
		public void setText(String	InputText)
		{
			StatusText.setText(InputText);
		}

		/** Add the progress bar to the status bar.
		 *
		 *	@return	 The progress bar
		 */
		public JProgressBar addProgressBar()
		{
			StatusProgressBar.setValue(0);
			add(StatusProgressBar);
			return(StatusProgressBar);
		}

		/** Remove the progress bar from the status bar.
		 */
		public void removeProgressBar()
		{
			remove(StatusProgressBar);
		}

		/** Get the progress bar.
		 *
		 *	@return	 The progress bar
		 */
		public JProgressBar getProgressBar()
		{
			return(StatusProgressBar);
		}

		/** Validate from the top of the containment treeStructure.
		 */
		public void validateContainerTreeStructure()
		{
			Container CurrentContainer = this;
			for (Container ParentContainer = getParent();
					 ParentContainer != null && ! ParentContainer.isValid();
						 ParentContainer = ParentContainer.getParent())
				{
					CurrentContainer = ParentContainer;
			}
			CurrentContainer.validate();
		}
}

