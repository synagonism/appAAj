package pk_Util;

import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * This class COUNTS ALL the PART xcpts of a concept.
 *
 * I'm counting the parts of current AND the parts of them.
// * I'm counting the attributes of current AND the specifics of these pars.
// * I'm NOT counting the pars of these pars AS I'm doing in 'view all pars' function.
 *
 * We must run this thread first to see how it takes to find all pars
 * ADD then to run the 'view all pars chain' function wich we can not stop.
 *
 * @modified 2000.06.09
 * @since 2000.03.13
 * @author HoKoNoUmo
 *
 */
public class ThreadCountAllPar extends Thread
{
	static boolean abortThread=false;
	Vector<String> parsFound=new Vector<String>();       // Specifics found.
	Vector<String> filesToSearch=new Vector<String>(); 	// the files I must search for pars.
	String	xcpt_sFormalID=null;
	JButton	jbFind=null;
	JButton jbStop=null;
	JTextField jtField=null;

  /**
   * @modified
   * @since 2000.03.13
   * @author HoKoNoUmo
   */
  public ThreadCountAllPar(String cid, JButton findBtn, JButton stopBtn, JTextField jtf)
  {
  	abortThread=false;
  	xcpt_sFormalID=cid;
  	jbFind=findBtn;
  	jbStop=stopBtn;
  	jtField=jtf;
  }

	/**
	 * @modified 2000.06.09
	 * @since 2000.03.13 (v00.01.09)
	 * @author HoKoNoUmo
	 */
	public void run()
	{
		//find the first-level pars.
		Extract ex = new Extract();
		ex.extractParts(xcpt_sFormalID);
		Vector<String> pars = ex.vectorParID;
		for (int i=0; i<pars.size(); i++)
		{
			if (abortThread) break;
			filesToSearch.add(pars.get(i));
			parsFound.add(pars.get(i));
		}

		//for the first-level-pars, we find their subs.
		while(!filesToSearch.isEmpty() && !abortThread)
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "ThreadCountAllPar: Problem in " + currentFileName);
				continue;       // Try next file
			}

			//extract pars.
			ex.extractParts(currentFileName);
			pars = ex.vectorParID;//all (only notinherited and overiden inherited).
			for (int i=0; i<pars.size(); i++)
			{
				if (abortThread) break;
				String fName=pars.get(i);
 				if (parsFound.contains(fName))
 				{
 					parsFound.add(fName);
 				}
 				else
 				{
					filesToSearch.add(fName);
					parsFound.add(fName);
				}
			}

/*
			ex.extractSpecifics(currentFileName);
			Vector subs = ex.vectorSpeID;
			for (int i=0; i<subs.size(); i++)
			{
				if (abortThread) break;
				String subID=subs.get(i);
				ex.extractWholes(subID);
				Vector whos=ex.vectorWhoID;
				if (whos.contains(xcpt_sFormalID) && whos.size()<2)
				{
					filesToSearch.add(subID);
					parsFound.add(subID);
				}
			}
*/

			filesToSearch.remove(0);
		}

		//enable buttons
		jbFind.setEnabled(true);
		jbStop.setEnabled(false);
		jtField.setText(String.valueOf(parsFound.size()));
	}//end run.

}