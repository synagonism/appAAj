package pk_XKBEditor;

import pk_XKBManager.AAj;
import pk_Util.Extract;
import pk_Util.Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Contains methods that remove elements of a sc-file.
 *
 * @modified
 * @since 2000.04.02
 */
public class Remove
{
	public String kb_sAuthor=AAj.kb_sAuthor;
	boolean inserted=false;

	// I need it to sort strings to sort concepts
	Collator collator = Collator.getInstance();

	/**
	 * Removes a XCPT from a sc.
	 *
	 * @param xcpt_sName is the filename/id of the xcpt we will remove the external.
	 * @param externalID is the id of the external sbcpt we want to remove.
	 * @modified
	 * @since 2000.04.02
	 * @author HoKoNoUmo
	 */
	public void removeCPTEXT(String xcpt_sName, String externalID)
	{
		String sbcpt = Util.getXCpt_sLastFileName(xcpt_sName);
		String path=Util.getXCpt_sFullDir(sbcpt) +File.separator;
		String fileIn = path +sbcpt;
		String fileOut = path +"tmp";

		try {
			FileReader reader =new FileReader(fileIn);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileOut);
			BufferedWriter bw =new BufferedWriter(writer);
			String ln = null;

			while ((ln = br.readLine()) != null)
			{
				// insert all lines before internal.
				if (!ln.startsWith("<XCPT"))
				{
					//set lastmodified.
					if (ln.startsWith("<XCONCEPT"))
					{
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}

				else //line STARTS with XCPT.
				{
					String ext=Util.getXmlAttribute(ln,"FRnAME");
					String extID=Util.getXCpt_sFormalID(ext);
					if(extID.equals(externalID))//this is the external we want.
					{
						continue;
					}
					else //we write the other external cpts.
					{
						bw.write(ln);
						bw.newLine();
						continue;
					}
				}
			}
			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Remove.removeCPTEXT: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		//rename files
		File f1 = new File(fileIn);
		f1.delete();
		File f2 = new File(fileOut);
		f2.renameTo(new File(fileIn));
	}

	/**
	 * Removes an internal-xcpt.
	 * @param intName is the filename/id of an INTERNAL-XCPT.
	 * @modified 2000.04.02
	 * @since 2000.02.17
	 * @author HoKoNoUmo
	 */
	public void removeCPTINT(String intName)
	{
		String intCpt = Util.getXCpt_sLastFileName(intName);
		//I suppose that xmlFileName exists.
		String hostFileName = Util.getSrCptInt_sHostFlName(intCpt);
		String path=Util.getXCpt_sFullDir(hostFileName) +File.separator;
		String fileIn = path +hostFileName;
		String fileOut = path +"tmp";

		try {
			FileReader reader =new FileReader(fileIn);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileOut);
			BufferedWriter bw =new BufferedWriter(writer);
			String ln = null;

			while ((ln = br.readLine()) != null)
			{
				// insert all lines before internal.
				if (!ln.startsWith("<INTxCPT"))
				{
					//set lastmodified.
					if (ln.startsWith("<XCONCEPT"))
					{
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}

				else //line STARTS with INTxCPT.
				{
					if(ln.indexOf("FRnAME=\""+intCpt)!=-1)//this is the internal we want.
					{
						// run while until reach end of internal.
						while (!ln.startsWith("</INTxCPT"))
						{
							ln=br.readLine();
						}
					}
					//we write the other internal lines.
					else {
						bw.write(ln);
						bw.newLine();
						continue;
					}
				}
			}
			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Remove.removeCPTINT: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		//rename files
		File f1 = new File(fileIn);
		f1.delete();
		File f2 = new File(fileOut);
		f2.renameTo(new File(fileIn));
	}

	/**
	 * @param xcpt_sName is the filename/id of a file/internal-xcpt.
	 * @modified 2000.04.03
	 * @since 1999.04.25
	 * @author HoKoNoUmo
	 */
	public void removeSynonym(String xcpt_sName, String syn)
	{
		String					sbcpt=Util.getXCpt_sLastFileName(xcpt_sName);
		String					xcpt_sDir = Util.getXCpt_sFullDir(sbcpt);
		String					fileIn=null;
		String					fileOut=null;
		FileReader			reader = null;
		BufferedReader	br = null;
		FileWriter			writer = null;
		BufferedWriter	bw = null;

		try {
			if (sbcpt.indexOf("#")==-1)	 //sbcpt is a 'file' one.
			{
				fileIn = xcpt_sDir +File.separator +sbcpt;
				fileOut = xcpt_sDir +File.separator +"tmp";
				reader =new FileReader(fileIn);
				br =new BufferedReader(reader);
				writer = new FileWriter(fileOut);
				bw =new BufferedWriter(writer);
				String ln = null;
				String ln2 = null;

				while ((ln = br.readLine()) != null)
				{
					// insert all lines before syn.
					if (!ln.startsWith("<REFINO_NAME"))
					{
						//set lastmodified.
						if (ln.startsWith("<XCONCEPT"))
						{
							bw.write(Util.setDateLastMod(ln));
							bw.newLine();
						}
						else {
							bw.write(ln);
							bw.newLine();
						}
						continue;
					}
					else //line STARTS with syn.
					{
						bw.write(ln);
						bw.newLine();
						ln=br.readLine();
						// run while until mach synonym.
						while (!ln.startsWith("</REFINO_NAME"))
						{
							//if LINE is empty or comment, write it
							if (!(ln.startsWith("<NAME")||ln.startsWith("</REFINO_NAME")))
							{
								bw.write(ln);
								ln=br.readLine();
								if (ln.startsWith("</REFINO_NAME")) break;
							}

							ln2=br.readLine();//read the text of name-element.

							if (ln2.startsWith(syn))//check to mach.
							{
								ln=br.readLine();//read </name
								ln=br.readLine();
								break;
							}
							else //write the existing syn.
							{
								bw.write(ln);
								bw.newLine();
								bw.write(ln2);
								bw.newLine();
								//read </name.
								ln=br.readLine();
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
						bw.write(ln);
						bw.newLine();
						continue;
					}
				}
				br.close();
				bw.close();
			}

			else //sbcpt is an internal-xcpt.
			{
				fileIn = xcpt_sDir +File.separator +Util.getSrCptInt_sHostFlName(sbcpt);
				fileOut = xcpt_sDir +File.separator +"tmp";
				reader =new FileReader(fileIn);
				br =new BufferedReader(reader);
				writer = new FileWriter(fileOut);
				bw =new BufferedWriter(writer);
				String ln = null;
				String ln2 = null;

				while ((ln = br.readLine()) != null)
				{
					// insert all lines before and after internal.
					if (ln.indexOf("FRnAME=\"" +sbcpt)==-1)
					{
					bw.write(ln);
						bw.newLine();
						continue;
					}
					else //This is the matched internal.
					{
						//set lastmodified.
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();
						ln=br.readLine(); //read syn/def
						//because we del syn, then <syn exists.

						if (ln.indexOf("<REFINO_DEFINITION")!=-1)//<def line
						{
							JOptionPane.showMessageDialog(null, "There is NOT synonym to delete");
						}
						else //line is <syn.
						{
							bw.write(ln); //<syn line.
							bw.newLine();
							ln=br.readLine();//<name or </syn line.

							while (ln.indexOf("</REFINO_NAME")==-1)//loop while not </syn.
							{
								//read the text of name-element.
								ln2=br.readLine();
								ln2.trim();
								if (ln2.startsWith("\t")) ln2=ln2.substring(1, ln2.length());//clear to compare.
								if (ln2.startsWith(syn))//check to mach.
								{
									ln=br.readLine();//read </name
									break;
								}
								else //write the existing syn.
								{
									bw.write(ln);//write <name
									bw.newLine();
									bw.write("\t"+ln2);
									bw.newLine();

									ln=br.readLine();//read </name.
									bw.write(ln);
									bw.newLine();
									ln=br.readLine();
								}
							}
						}
					}
				}
				br.close();
				bw.close();
			}
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Remove.removeSynonym: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		File f1 = new File(fileIn);//rename files.
		f1.delete();
		File f2 = new File(fileOut);
		f2.renameTo(new File(fileIn));

		Insert ins = new Insert();
		ins.updateTermFiles(sbcpt, "all");//we modified the termTxCpt_sAll then update them.
	}

}
