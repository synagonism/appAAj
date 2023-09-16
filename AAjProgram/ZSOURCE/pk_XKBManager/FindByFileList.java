package pk_XKBManager;

import pk_Util.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 * This class searches for a concept by ID/FormalName.
 * Searches the names of xml-files in their directories file-lists.
 *
 * @modified
 * @since 2000.03.05
 * @author HoKoNoUmo
 */
public class FindByFileList
{

	/**
	 * The constructor.
	 * @param uniqueNameOfXCpt an id or a formal-name of a concept.
	 * @modified
	 * @since 2000.03.04
	 * @author HoKoNoUmo
	 */
	public FindByFileList(String uniqueNameOfXCpt)
	{
			String xCpt = uniqueNameOfXCpt;
			File fcptDir=null;
			FilterID flt=null;
			String[] fileName=new String[2];
			boolean found=false;

			if (xCpt.indexOf("-")>1)
			{
				//we are searching for an ID or name.
				String mm2 = xCpt.substring(0, xCpt.indexOf("-"));
				String vd = mm2.toLowerCase();

				if (AAj.trmapSrBrSubWorldview.containsKey(vd)) //xCpt is an id
				{
					String[] ar = AAj.trmapSrBrSubWorldview.get(vd);
					String directory = System.getProperty("user.dir")
														+File.separator
														+"AAjKB" +File.separator
					+Util.getXCpt_sFormalWorldview(uniqueNameOfXCpt) +File.separator
														+AAj.lng
														+File.separator +ar[1];
					fcptDir = new File (directory);
					flt = new FilterID(xCpt);
					try {
						fileName = fcptDir.list(flt);
						if (fileName[0] != null)
						{
							found=true;
							AAj.display(fileName[0]);
						}
					}
					catch (Exception elist)
					{
						JOptionPane.showMessageDialog(null, "There is NOT file for this ID");
					}
				}

				else //name with -.
				{
					//we are searching for a name.
					FilterName flt2 = new FilterName(xCpt+"@");//to search ONLY for whole names
					try {
						//search first 'hknu.symb'
						fcptDir = new File(Util.getXCpt_sFullDir("hknu.meta-1"));
						fileName = fcptDir.list(flt2);
						if (fileName[0] != null)
						{
							found=true;
							AAj.display(fileName[0]);
						}
					}
					catch (Exception e3)
					{
						try {
							fcptDir = new File(Util.getXCpt_sFullDir("it-1"));
							fileName = fcptDir.list(flt2);
							if (fileName[0] != null)
							{
								found=true;
								AAj.display(fileName[0]);
							}
						}
						catch (Exception e4)
						{
							try {
								fcptDir = new File(Util.getXCpt_sFullDir("itsoft-1"));
								fileName = fcptDir.list(flt2);
								if (fileName[0] != null)
								{
									found=true;
									AAj.display(fileName[0]);
								}
							}
							catch (Exception e5)
							{
								try {
									fcptDir = new File(Util.getXCpt_sFullDir("society-1"));
									fileName = fcptDir.list(flt2);
									if (fileName[0] != null)
									{
										found=true;
										AAj.display(fileName[0]);
									}
								}
								catch (Exception e6)
								{
									//search all kn and break
									for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
									{
										String key =(String) i.next();
										if (!key.equals("hknu.symb") ||!key.equals("it")
													||!key.equals("itsoft") ||!key.equals("society"))
										{
											try {
												fcptDir = new File(Util.getXCpt_sFullDir(key+"-1"));
												fileName = fcptDir.list(flt2);
												if (fileName[0] != null)
												{
													found=true;
													AAj.display(fileName[0]);
													break;
												}
											}
											catch (Exception e7) {}
										}
									}
								}
							}
						}
					}
					if (!found) JOptionPane.showMessageDialog(null, "There is NOT file for this concept");
				}
			}
			else //name without -.
			{
				//we are searching for a name.
				FilterName flt2 = new FilterName(xCpt+"@");//to search ONLY for whole names
				try {
					//search first 'hknu.symb'
					fcptDir = new File(Util.getXCpt_sFullDir("hknu.meta-1"));
					fileName = fcptDir.list(flt2);
					if (fileName[0] != null)
					{
						found=true;
						AAj.display(fileName[0]);
					}
				}
				catch (Exception e3)
				{
					try {
						fcptDir = new File(Util.getXCpt_sFullDir("it-1"));
						fileName = fcptDir.list(flt2);
						if (fileName[0] != null)
						{
							found=true;
							AAj.display(fileName[0]);
						}
					}
					catch (Exception e4)
					{
						try {
							fcptDir = new File(Util.getXCpt_sFullDir("itsoft-1"));
							fileName = fcptDir.list(flt2);
							if (fileName[0] != null)
							{
								found=true;
								AAj.display(fileName[0]);
							}
						}
						catch (Exception e5)
						{
							try {
								fcptDir = new File(Util.getXCpt_sFullDir("society-1"));
								fileName = fcptDir.list(flt2);
								if (fileName[0] != null)
								{
									found=true;
									AAj.display(fileName[0]);
								}
							}
							catch (Exception e6)
							{
								//search all kn and break
								for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
								{
									String key =(String) i.next();
									if (!key.equals("hknu.symb") ||!key.equals("it")
												||!key.equals("itsoft") ||!key.equals("society"))
									{
										try {
											fcptDir = new File(Util.getXCpt_sFullDir(key+"-1"));
											fileName = fcptDir.list(flt2);
											if (fileName[0] != null)
											{
												found=true;
												AAj.display(fileName[0]);
												break;
											}
										}
										catch (Exception e7) {}
									}
								}
							}
						}
					}
				}
				if (!found) JOptionPane.showMessageDialog(null, "There is NOT file for this concept");
			}
	}//END of constructor.

}