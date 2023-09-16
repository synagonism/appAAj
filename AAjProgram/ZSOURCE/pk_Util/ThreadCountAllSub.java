package pk_Util;

import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class COUNTS ALL the specific-concepts of a concept.
 *
 * We must run this thread first to see how it takes to find all subs
 * ADD then to run the 'view all subs chain' function wich we can not stop.
 *
 * @modified 2000.06.09
 * @since 2000.03.13
 * @author HoKoNoUmo
 *
 */
public class ThreadCountAllSub extends Thread
{
	static boolean abortThread=false;
	Vector<String> subsFound=new Vector<String>();       // Specifics found.
	Vector<String> filesToSearch=new Vector<String>(); 	// the files I must search for subs.
	String	xcpt_sFormalID=null;
	JButton	jbFind=null;
	JButton jbStop=null;
	JTextField jtField=null;

  /**
   * @modified 2000.06.09
   * @since 2000.03.13 (v00.01.09)
   * @author HoKoNoUmo
   */
  public ThreadCountAllSub(String cid, JButton findBtn, JButton stopBtn, JTextField jtf)
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
				JOptionPane.showMessageDialog(null, "ThreadCountAllSub: Problem in " + currentFileName);
				continue;       // Try next file
			}
			Extract ex = new Extract();
			ex.extractSpecifics(currentFileName);
			Vector<String> subs = ex.vectorSpeID;

			for (int i=0; i<subs.size(); i++)
			{
				if (abortThread) break;
				filesToSearch.add(subs.get(i));
				subsFound.add(subs.get(i));
			}
			filesToSearch.remove(0);
		}

		//enable buttons
		jbFind.setEnabled(true);
		jbStop.setEnabled(false);
		jtField.setText(String.valueOf(subsFound.size()));
	}//end run.

}