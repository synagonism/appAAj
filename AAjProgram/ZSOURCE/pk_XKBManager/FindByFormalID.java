package pk_XKBManager;

import pk_Util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * This class searches for a concept by ID.
 * Searches the lng/term.index.xml files.
 * @modified
 * @since 2000.03.04
 * @author HoKoNoUmo
 */
public class FindByFormalID
{
	String searchingID=null;
	String searchingNumber=null;

  /**
   * The constructor.
   * @modified
   * @since 2000.03.04
   * @author HoKoNoUmo
   */
  public FindByFormalID(String id)
  {
  	searchingID=id;
  	searchingNumber=Util.getXCpt_sFormalNumber(id);

		String vd = id.substring(0, id.indexOf("-"));
		String kbFileName = vd +"'termTxCpt_sAll.xml";
		String mmDir = Util.getXCpt_sFullDir(vd +"-1");
		String kbFullFileName=mmDir +File.separator +kbFileName;
		File fl = new File(kbFullFileName);
		if (fl.exists())
		{
			searchFile(kbFullFileName);
		}
		else JOptionPane.showMessageDialog(null, "There is NO vd-file for this ID");
	}

	/**
	 * Read ONE lng/term.index.xml file and searches the &lt;name&gt; xml elements.
	 * @param kbFileName is a fullpath file.
	 * @modified
	 * @since 2000.03.04
	 * @author HoKoNoUmo
	 */
	final void searchFile (String kbFullFileName)
	{
		String 					line;          			// Raw line read in.
		String 					xcpt_sFileName=null;		// the fileName of a concept.
		String					cptNumber=null;				// the integer of a concept.
		BufferedReader 	br =null;
		boolean					found=false;

		try {
			br = new BufferedReader(new FileReader(kbFullFileName));

			while ((line=br.readLine())!=null)
			{
				if (line.startsWith("<NAME"))
				{
					xcpt_sFileName=line.substring(line.indexOf("FN=")+4, line.indexOf("\"",line.indexOf("FN=")+5));
					cptNumber=line.substring(line.indexOf("NBR=")+5, line.indexOf("\"",line.indexOf("NBR=")+6));
					if (cptNumber.equals(searchingNumber))
					{
						AAj.display(xcpt_sFileName);
						found=true;
						break;
					}
				}
			}
			br.close();
			if (!found) JOptionPane.showMessageDialog(null, "There is NO file for this ID");
		}
		catch (FileNotFoundException fnfe)
		{
			System.out.println("FindByFormalID.searchFile: fnfe: " +kbFullFileName);
		}
		catch (IOException ioe)
		{
			JOptionPane.showMessageDialog(null, "FindByFormalID.searchFile(): IOException: on " +kbFullFileName);
		}
	}//END of searchFile

}