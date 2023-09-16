package pk_Util;

import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class COUNTS ALL the whole-concepts of a concept.<p>
 *
 * We must run this thread first to see how it takes to find all whos
 * ADD then to run the 'view all whos chain' function wich we can not stop.
 *
 * @modified 2000.06.09
 * @since 2000.03.13
 * @author HoKoNoUmo
 */
public class ThreadCountAllWho extends Thread
{
	static boolean abortThread=false;
	Vector<String> whosFound=new Vector<String>();			 // Specifics found.
	Vector<String> filesToSearch=new Vector<String>();	// the files I must search for whos.
	String	xcpt_sFormalID=null;
	JButton jbFind=null;
	JButton jbStop=null;
	JTextField jtField=null;

	/**
	 * @modified 2000.06.09
	 * @since 2000.03.13 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public ThreadCountAllWho(String cid, JButton findBtn, JButton stopBtn, JTextField jtf)
	{
		abortThread=false;
		xcpt_sFormalID=cid;
		jbFind=findBtn;
		jbStop=stopBtn;
		jtField=jtf;
	}

	/**
	 * @modified
	 * @since 2000.03.13
	 * @author HoKoNoUmo
	 */
	public void run()
	{
		filesToSearch.add(xcpt_sFormalID);

		while(!filesToSearch.isEmpty() && !abortThread)
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "ThreadCountAllWho: Problem in " + currentFileName);
				continue;				// Try next file
			}
			Extract ex = new Extract();
			ex.extractWholes(currentFileName);
			Vector<String> whos = ex.vectorWhoID;

			for (int i=0; i<whos.size(); i++)
			{
				if (abortThread) break;
				filesToSearch.add(whos.get(i));
				whosFound.add(whos.get(i));
			}
			filesToSearch.remove(0);
		}

		//enable buttons
		jbFind.setEnabled(true);
		jbStop.setEnabled(false);
		jtField.setText(String.valueOf(whosFound.size()));
	}//end run.

}