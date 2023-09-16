/*
 * Util.java - Contains GLOBAL utility methods and variables.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2004 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Generic Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	 See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA	02111-1307, USA.
 */

package pk_Util;

import pk_XKBManager.AAj;
import pk_Logo.*;
import pk_SSemasia.*;

import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.Collator;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.TreeSet;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Contains GLOBAL utility methods and variables.
 *
 * @modified 2009.10.27
 * @since 2000.01.29 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Util
{
	//wrkarnd
	public static String	AAj_sDir = System.getProperty("user.dir") + File.separator;
//"g:\\file1\\AAjWORKING\\AAjProgram" + File.separator;
//								System.getProperty("user.dir") + File.separator;
//	"g:\\file1\\AAjWORKING" + File.separator;
//

	/** The log writer. */
	public static BufferedWriter			bwLog;


	/**
	 *
	 * @modified 2006.10.29
	 * @since 2006.10.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean areTwoArrayStringEqual(String[] a1, String[] a2)
	{
		if (a1.length!=a2.length)
			return false;
		else {
			for (int i=0; i<a1.length; i++)
			{
				if (!a1[i].equals(a2[i]))
					return false;
			}
			return true;
		}
	}


	/**
	 * Test if an array of strings contains a string.
	 *
	 * @modified 2006.02.20
	 * @since 2006.02.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean arrayStringContains(String[] a, String key)
	{
		for (int i=0; i<a.length; i++)
		{
			if (a[i].equals(key))
				return true;
		}
		return false;
	}


	/**
	 * Copy a file to another file.
	 * @param fileFrom and fileTo are full-path strings.
	 * @modified
	 * @since 2000.01.29
	 * @author HoKoNoUmo
	 */
	public static void copyFile(String fileFrom, String fileTo)
	{
		try {
			FileReader reader =new FileReader(fileFrom);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer= new FileWriter(fileTo);
			BufferedWriter bw =new BufferedWriter(writer);
			String ln= null;
			while ((ln= br.readLine()) != null)
			{
				bw.write(ln);
				bw.newLine();
			}
			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Util.copyFile: e.toString()",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
	}

	/**
	 * <b>INPUT</b>: an xcpt's MAIN-NAME.<br/>
	 * <b>OUTPUT</b>: the formal-term of the xcpt.<br/>
	 * - Entity.<br/>
	 * - Entity's-attribute.<br/>
	 * - Entity_individual.
	 *
	 * @modified 2009.09.30
	 * @since 2009.09.30 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public static String createFormalTerm(String mainNameOfXCpt)
	{
		if (mainNameOfXCpt.indexOf(" ")!=-1){
			//ask for specific.
			String result;
			result= JOptionPane.showInputDialog(null,"If the name ("+mainNameOfXCpt +") denotes a specific concept, "
															+"\neg <Individual entity>, THEN rewrite the name in the form:"
															+"\ngeneric # specific eg entity # individual"
															+"\nELSE press cancel");
			if (result!=null){
				String[] parts= result.split("#");
				mainNameOfXCpt=parts[0].trim() +"_" +parts[1].trim();
			}
		}
		//replace space with hyphen
		mainNameOfXCpt=mainNameOfXCpt.replace(' ','-');
		//make first capital
		mainNameOfXCpt=Textual.setFirstCapital(mainNameOfXCpt);
		return mainNameOfXCpt;
	}


	/**
	 * Returns the first name of the concept plus "(FormalID)", which
	 * we use as a ToC node.
	 *
 	 * @param uniqueNameOfXCpt A unique-name of a file/internal-xcpt.
	 * @modified 2009.11.03
	 * @since 2000.04.15
	 * @author HoKoNoUmo
	 */
	public static String createNodeName(String uniqueNameOfXCpt)
	{
		String xcpt_sName= getXCpt_sName(uniqueNameOfXCpt, AAj.lng);
		xcpt_sName= xcpt_sName.replace(' ','-');
		String xcpt_sFormalID= getXCpt_sFormalID(uniqueNameOfXCpt);
		return xcpt_sName +" @" +xcpt_sFormalID +"@";
	}


	/**
	 * Extracts from a vector-of-lines the first "refino" xelement.
	 * This can contain other "refino" elements.
	 * The first line, is the refino line.
	 *
	 * @modified 2009.11.18
	 * @since 2009.11.16 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public static Vector<String> extractFirstRefino(Vector<String> vol)
	{
		Vector<String> vor= new Vector<String>();
		String ln= "";
		ln= vol.get(0);
		vor.add(ln);
		for (int i= 1; i< vol.size(); i++) {
			ln= vol.get(i);
			if (ln.startsWith("<XCPT")){
				vor.add(ln);
			} else if (ln.startsWith("<REFINO")){
				Vector<String> rfn= new Vector<String>();
				for (int j= i; j < vol.size(); j++) {
					ln= vol.get(j);
					rfn.add(ln);
				}
				Vector<String> rfn2= extractFirstRefino(rfn);
				vor.addAll(rfn2);
				i= i+rfn2.size()-1;
			} else if (ln.startsWith("</REFINO")) {
				vor.add(ln);
				break;
			}
		}
		return vor;
	}


	/**
	 * From a vector of lines, extracts the refino-xelement that begines at
	 * given index.
	 *
	 * @modified 2009.11.18
	 * @since 2009.11.16 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public static Vector<String> extractRefinoAtIndex(Vector<String> vol, int i)
	{
		Vector<String> vor= new Vector<String>();
		String ln= "";
		ln= vol.get(i);
		vor.add(ln);
		for (i= i+1; i< vol.size(); i++) {
			ln= vol.get(i);
			if (ln.startsWith("<XCPT")){
				vor.add(ln);
			} else if (ln.startsWith("<REFINO")){
				Vector<String> rfn= new Vector<String>();
				for (int j= i; j < vol.size(); j++) {
					ln= vol.get(j);
					rfn.add(ln);
				}
				Vector<String> rfn2= extractRefinoAtIndex(rfn,0);
//System.out.println("===");
//printVectorOfString(rfn2);
				vor.addAll(rfn2);
				i= i+rfn2.size()-1;
			} else if (ln.startsWith("</REFINO")){
				vor.add(ln);
				break;
			}
		}
		return vor;
	}


	/**
	 * Returns the NAME of an xcpt (= the name of its first NOUN).
	 *
	 * @param uniqueNameOfXCpt A unique-name of a file/internal-xcpt.
	 * @param lng	The language in which we want to get the name: eln,eng,...
	 * @modified
	 * @since 2001.03.08
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sName(String uniqueNameOfXCpt, String lng)
	{
		String xcpt_sName=null;
		String line;
		String lngElm="<" +lng +" ";

		if (uniqueNameOfXCpt.indexOf("#")==-1)//a file-xcpt.
		{
			try {
				BufferedReader br= new BufferedReader(new FileReader(getXCpt_sLastFullFileName(uniqueNameOfXCpt)));
				while ((line=br.readLine()) !=null)
				{
					if (line.startsWith(lngElm))
					{
						//read the next <Name_NounCase line.
						line=br.readLine();
						xcpt_sName=getXmlAttribute(line,"TxEXP");
						break;
					}
				}
				br.close();
			}
			catch (EOFException e){System.out.println("Util.getXCpt_sName: EOFException");}
			catch (FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(null, "Util.getXCpt_sName: There is NO xmlfile"
																						+" to extract the NAME : " +uniqueNameOfXCpt);
			}
			catch (IOException e){System.out.println("Util.getXCpt_sName: IOException");}
		}

		else //we have an internal-xcpt.
		{
			String idName=getXCpt_sFormalID(uniqueNameOfXCpt);
			String intName=null;

			try {
				String hostIdName=getSrCptInt_sHostID(idName);
				BufferedReader br= new BufferedReader (new FileReader(getXCpt_sLastFullFileName(hostIdName)));
				while ( (line=br.readLine())!=null )
				{
					if (line.startsWith("<INTxCPT"))
					{
						String idExist=getXmlAttribute(line, "FRnAME");
						String ideid= getXCpt_sFormalID(idExist);

						if (idName.equals(ideid)) //This is the internal we are looking for.
						{
							while ( line.indexOf("</REFINO_NAME")==-1 )
							{
								if (line.indexOf("<" +lng +" ")!=-1)//we reached the locale.
								{
									//read next <tx_noun line.
									line=br.readLine();
									intName=getXmlAttribute(line, "TxEXP");
								}
								if (intName!=null) break;
								line=br.readLine();
							}
						}
					}
					if (intName!=null) break;
				}
				br.close();
			}
			catch (EOFException e)					{System.out.println("getSrCptInt_sName: eofe 488");}
			catch (FileNotFoundException e){System.out.println("getSrCptInt_sName: fnfe 489");}
			catch (IOException e)					{System.out.println("getSrCptInt_sName: ioe 490");}

			return intName;
		}
		return xcpt_sName;
	}


	/**
	 * The FileName of an xcpt: "BConcept@hknu.meta-1@.xml".<br/>
	 * Returns the last fileName of the xcpt without ".xml".
	 *
	 * @param uniqueNameOfXCpt A unique-name of a file/internal-xcpt.
	 * @modified 2001.03.08
	 * @since 2000.03.13
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalName(String uniqueNameOfXCpt)
	{
		String fileName= getXCpt_sLastFileName(uniqueNameOfXCpt);
		return fileName.substring(0,fileName.length()-4);
	}


	/**
	 * Returns the full directory of an xcpt:
	 * \AAjHome\AAjKB\HoKoNoUmo\Symban
	 *
	 * @param uniqueNameOfXCpt		A unique-name of a file/internal-xcpt.
	 * @modified 2009.05.28
	 * @since 1999.03.22
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFullDir(String uniqueNameOfXCpt)
	{
		if (uniqueNameOfXCpt.equals("none"))		{
			return "none";
		}
		else		{
			String directory= AAj_sDir +"AAjKB" +File.separator
						+getXCpt_sFormalWorldviewFull(uniqueNameOfXCpt) +File.separator
						+getXCpt_sFormalSubWorldviewFull(uniqueNameOfXCpt);
			return directory;
		}
	}


	/**
	 * Returns the LAST file-name a an xcpt.<br/>
	 * <b>GIVEN</b>: the x-concept's fileName reflects the last xcpt's name.
	 * In the knowledge-base there are old formalNames.
	 *
	 * @param uniqueNameOfXCpt	A unique-name OF a file/internal-xcpt.
	 * @modified 2009.11.16
	 * @since 1999.03.23
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sLastFileName(String uniqueNameOfXCpt)
	{
		//toDo: The LastFileName to be the first noun...
		if (uniqueNameOfXCpt.equals("none"))		{
			return "none";
		}
		else	 if (uniqueNameOfXCpt.equals("sensory"))		{
			return "sensory";
		}
		else		{
			String directory;
			String frID=getXCpt_sFormalID(uniqueNameOfXCpt);
			directory= AAj_sDir +"AAjKB" +File.separator
									+getXCpt_sFormalWorldviewFull(uniqueNameOfXCpt) +File.separator
									+getXCpt_sFormalSubWorldviewFull(uniqueNameOfXCpt);//
			File fcptDir= new File (directory);

			if (frID.indexOf("#")!=-1) //frID is an internal-id.
			{
				String hostID=getSrCptInt_sHostID(frID);
				FilterID flt= new FilterID(hostID);
				String[] fileName= fcptDir.list(flt);
				if (fileName[0] != null) //host file exists.
				{
					Extract ex= new Extract();
					return ex.extractIntFileName(frID);
				}
				else {
					JOptionPane.showMessageDialog(null,
					"Util.getXCpt_sLastFileName: There is NO file for interanl: " +frID);
					return null;
				}
			}
			else//xcpt is a FILE one.
			{
				FilterID flt= new FilterID(frID);
				String[] fileName= null;
				fileName=fcptDir.list(flt);
				if (fileName != null) {
					try {
						String fn= fileName[0];
					} catch (Exception e) {
						System.out.println("xcpt >" +frID +"< NOT found");
					}
					return fileName[0];
				}
				else {
					JOptionPane.showMessageDialog(null,
						"Util.getXCpt_sLastFileName: There is NO file for "+frID);
					return null;
				}
			}
		}
	}

	/**
	 * Returns the LAST full file-name of an xcpt (the name and the
	 * directory).
	 *
	 * @param uniqueNameOfXCpt is the old file-name or id of a FILE-XCPT only.
	 * @modified 2001.05.14
	 * @since 1999.03.22
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sLastFullFileName(String uniqueNameOfXCpt)
	{
		String directory=getXCpt_sFullDir(uniqueNameOfXCpt);
		String fn=getXCpt_sLastFileName(uniqueNameOfXCpt);
		return directory +File.separator +fn;
	}


	/**
	 * Returns the formal-term of an xcpt (the formal-name
	 * without the formal-ID): "Concept".
	 *
	 * @param uniqueNameOfXCpt		A unique-name of a file/internal-xcpt.
	 * @modified 2001.07.10
	 * @since 2001.03.08
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalTerm(String uniqueNameOfXCpt)
	{
		String flName=getXCpt_sLastFileName(uniqueNameOfXCpt);
		return flName.substring(0,flName.indexOf("@"));
	}

	/**
	 * Returns the formal-ID of an xcpt: "hknu.meta-1"
	 *
	 * @param uniqueNameOfXCpt
	 *		A unique-name of a file/internal-xcpt.
	 * @modified 2009.10.23
	 * @since 2000.02.18
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalID(String uniqueNameOfXCpt)
	{
		if (uniqueNameOfXCpt.equals("none"))
		{
			return uniqueNameOfXCpt;
		}
		else if (uniqueNameOfXCpt.indexOf("@")!=-1)
		{
			if (uniqueNameOfXCpt.endsWith("xml")) //name is a filename
				return uniqueNameOfXCpt.substring(uniqueNameOfXCpt.indexOf("@")+1, uniqueNameOfXCpt.length()-5);
			else if (uniqueNameOfXCpt.endsWith("@"))
				return uniqueNameOfXCpt.substring(uniqueNameOfXCpt.indexOf("@")+1, uniqueNameOfXCpt.length()-1);
			else //name is a formalName
				return uniqueNameOfXCpt.substring(uniqueNameOfXCpt.indexOf("@")+1, uniqueNameOfXCpt.length());
		}
		else //name is an id.
		{
			return uniqueNameOfXCpt;
		}
	}

	/**
	 * Returns the formal-number (as string) of an xcpt: "1".
	 *
	 * @param uniqueNameOfXCpt	A unique-name of file/internal-xcpt.
	 * @modified 2000.03.11
	 * @since 2000.02.18
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalNumber(String uniqueNameOfXCpt)
	{
		String number= null;
		String vs= getXCpt_sFormalID(uniqueNameOfXCpt);//hknu.symb-1
		number=vs.substring(vs.lastIndexOf("-")+1, vs.length());
		return number;
	}


	/**
	 * Returns the formal-view of an xcpt: "hknu.symb".
	 *
	 * @param uniqueNameOfXCpt		A unique-name of a file/internal-xcpt.
	 * @modified 2000.03.11
	 * @since 2000.02.18
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalView(String uniqueNameOfXCpt)
	{
		String xfrv=null;
		if (uniqueNameOfXCpt.equals("none"))
		{
			xfrv="none";
		}
		else if (uniqueNameOfXCpt.indexOf("@")!=-1)
		{
			xfrv=uniqueNameOfXCpt.substring(uniqueNameOfXCpt.indexOf("@")+1, uniqueNameOfXCpt.lastIndexOf("-"));
		}
		else //name is id.
		{
			xfrv=uniqueNameOfXCpt.substring(0, uniqueNameOfXCpt.indexOf("-"));
		}
		return xfrv;
	}


	/**
	 * Returns the formal-worldview of xcpt, eg: "hknu"
	 *
	 * @param uniqueNameOfXCpt		A unique-name of a file/internal-xcpt.
	 * @modified 2008.03.24
	 * @since 2008.03.24 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalWorldview(String uniqueNameOfXCpt)
	{
		String vs= getXCpt_sFormalView(uniqueNameOfXCpt);//hknu.symb
		return vs.substring(0,vs.lastIndexOf("."));//hknu
	}


	/**
	 * Returns the full-name of a formal-worldview of an xcpt: "HoKoNoUmo"
	 *
	 * @param uniqueNameOfXCpt		A unique-name of a file/internal-xcpt.
	 * @modified 2009.05.28
	 * @since 2009.05.28 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalWorldviewFull(String uniqueNameOfXCpt)
	{
		String ffwv="";
		String fv= getXCpt_sFormalView(uniqueNameOfXCpt);//hknu.symb
		String fwv= fv.substring(0,fv.lastIndexOf("."));//hknu
		if (AAj.trmapSrBrWorldview==null){
			if (fwv.equals("hknu"))
				ffwv="HoKoNoUmo";
		} else {
			for (Iterator i=AAj.trmapSrBrWorldview.keySet().iterator(); i.hasNext(); )
			{
				if (i.next().equals(fwv))
				{
					String[] ar= AAj.trmapSrBrWorldview.get(fwv);
					ffwv=ar[1];//directory=fullName.
					break;
				}
			}
		}
		return ffwv;
	}


	/**
	 * Returns the formal-subWorldview of an xcpt: "symb".
	 *
	 * @param uniqueNameOfXCpt A unique-name of an x-concept.
	 * @modified 2008.03.24
	 * @since 2008.03.24 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalSubWorldview(String uniqueNameOfXCpt)
	{
		String vs= getXCpt_sFormalView(uniqueNameOfXCpt);//hknu.symb
		return vs.substring(vs.indexOf(".")+1,vs.length());//symb
	}


	/**
	 * Returns the full-name (not the short) of a formal-subWorldview
	 * of a sensorial-b-concept.<br/>
	 * I created this method in order to run the program
	 * without the main class.
	 *
	 * @param uniqueNameOfXCpt A unique-name of an x-concept.
	 * @modified 2009.05.28
	 * @since 2004.06.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getXCpt_sFormalSubWorldviewFull(String uniqueNameOfXCpt)
	{
		//if AAj.trmapSrBrSubWorldview is empty
		//trmapSrBrSubWorldview contains this info 2008.04.26 nnn
		String vd= getXCpt_sFormalView(uniqueNameOfXCpt);
		if (vd.endsWith(".it"))
			return "InfoTech";
		else if (vd.endsWith(".meta"))
			return "MetaBConcept";
		else if (vd.endsWith(".symb"))
			return "Symban";
		else {
			JOptionPane.showMessageDialog(null,
				"Util.getXCpt_sFormalSubWorldviewFull: There is NO directory for: " +uniqueNameOfXCpt);
			return "unknown";
		}
	}


	/**
	 * @param intNameFlFrId is an id/filename.
	 * @modified 2001.05.14
	 * @since 2000.02.16
	 * @author HoKoNoUmo
	 */
	public static String getSrCptInt_sHostFlName(String intNameFlFrId)
	{
		String hostID=getSrCptInt_sHostID(intNameFlFrId);
		return getXCpt_sLastFileName(hostID);
	}


	/**
	 * Returns the hostID given the unique-name of an internal-xConcept.
	 *
	 * @modified 2006.02.04
	 * @since 2000.02.12
	 * @author HoKoNoUmo
	 */
	public static String getSrCptInt_sHostID(String uniqueNameOfXCpt)
	{
		String intName=uniqueNameOfXCpt;
		String hostID;
		if (intName.indexOf("@")!=-1)//intName is a filename.
		{
			hostID= intName.substring(intName.indexOf("@")+1, intName.indexOf("#"));
		}
		else//intName is an id.
		{
			hostID=intName.substring(0, intName.indexOf("#"));
		}
		return hostID;
	}


	/**
	 * Returns a string of current date in form 2000.01.29
	 * @modified
	 * @since 2000.01.29
	 * @author HoKoNoUmo
	 */
	public static String getCurrentDate()
	{
		SimpleDateFormat formatter= new SimpleDateFormat ("yyyy.MM.dd");
		Date currentTime= new Date();
		String stringDate= formatter.format(currentTime);
		return stringDate;
	}


	/**
	 * <b>INPUT</b>: a t_word and a 3letter-language.<br/>
	 * <b>OUTPUT</b>: the full-path of the term-file to search
	 * for this t_word.
	 *
	 * @modified 2008.03.12
	 * @since 2008.03.12 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getFullTermFile(String yrd, String lng)
	{
		//addLng
		//When I 'll create a new index, I must come here and add it.
		String[] arEng={"a","b","c","d","e","f",
										"g","h","i","j","k","l","m","n","o","p",
										"q","r","s","t","u","v","w","x","y","z"};
		String[] arKml={"a","e","i","o","u",
										"p","b","f","v","q","w","s","z","c","j",
										"t","d","k","g","h","y","m","n","l","r"};
		String[] arEln={" "," "," "," ",
										" "," "," "," "," "," "," "," "," "," ",
										" "," "," "," "," "," "," "," "," "," "};

		String index="a";

		if (lng.equals("kml")){
			index= "a";
			for (String s : arKml){
				if (yrd.startsWith(s)){
					index= s;
					break;
				}
			}
		}
		else if (lng.equals("eng")){
			index= "a";
			for (String s : arEng){
				if (yrd.startsWith(s)){
					index= s;
					break;
				}
			}
		}
		else if (lng.equals("eln")){
			index= " ";
			yrd=yrd.toLowerCase();
			yrd=Textual.greekRemoveTonos(yrd);
			for (String s : arEln){
				if (yrd.startsWith(s)){
					index= s;
					break;
				}
			}
		}
		//addLng

		String fileTerm= AAj_sDir +"AAjKB" +File.separator
					+"AAjINDEXES" +File.separator
					+lng +File.separator +"index_term_"+lng+"_"+index+".xml";
		return fileTerm;
	}


	/**
	 * @modified 2001.03.17
	 * @since 2000.01.30
	 * @author HoKoNoUmo
	 */
	public static Image getImage(String path)
	{
//		return Toolkit.getDefaultToolkit().getImage(clss.getResource(path));
		return Toolkit.getDefaultToolkit().getImage(path);
	}


	/**
	 * <b>INPUT</b>: the english name of a language OR its 2-leter iso code 639-1.<br/>
	 * <b>OUTPUT</b>: the 3-leter ISO codes 639-2.<p>
	 *
	 * If not found, "noLango3" is returned.
	 *
	 * @modified 2006.11.14
	 * @since 2006.11.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getLango3(String lng)
	{
		lng= lng.toLowerCase();
		String line=null;
		String lango3File=Util.AAj_sDir+"AAjKB" +File.separator
					+"AAjINDEXES" +File.separator+"lango3.txt";
		try {
			BufferedReader br= new BufferedReader(new FileReader(lango3File));
			while ((line=br.readLine())!=null){
				if (line.startsWith(lng)){
					String[] ar= line.split(";");
					br.close();
					return ar[1];
				}
			}
		}
		catch (IOException ioe){
			System.out.println("pk_Util.getLango3 ioe: " +lng);
		}
		return lng+";noLango3";
	}


	/**
	 * <b>INPUT</b>: the normal-name of a language, as the program
	 * presents it in language ComboBoxes.<br/>
	 * <b>OUTPUT</b>: the 3-leter ISO codes 639-2.<p>
	 *
	 * @modified 2009.09.29
	 * @since 2009.09.29 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public static String getLango3FromNormal(String lang)
	{
		String lng= "noLng";
		if (lang.equals(AAj.rbLabels.getString("English2")))
			lng="eng";
		else if (lang.equals(AAj.rbLabels.getString("Greek2")))
			lng="eln";
		else if (lang.equals(AAj.rbLabels.getString("Esperanto")))
			lng="epo";
		else if (lang.equals(AAj.rbLabels.getString("Komo")))
			lng="kml";
		//addLng
		return lng;
	}


	/**
	 * <b>INPUT</b>: the 3-leter ISO codes 639-2.<br/>
	 * <b>OUTPUT</b>: the english name of a language.<p>
	 *
	 * If not found, "noLango3" is returned.
	 *
	 * @modified 2009.08.04
	 * @since 2009.08.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getLango3toNormal(String lng)
	{
		lng= lng.toLowerCase();
		String line=null;
		String lango3File=Util.AAj_sDir+"AAjKB" +File.separator
					+"AAjINDEXES" +File.separator+"lango3toNormal.txt";
		try {
			BufferedReader br= new BufferedReader(new FileReader(lango3File));
			while ((line=br.readLine())!=null){
				if (line.startsWith(lng+";")){
					String[] ar= line.split(";");
					br.close();
					return ar[1];
				}
			}
		}
		catch (IOException ioe){
			System.out.println("pk_Util.getLango3toNormal ioe: " +lng);
		}
		return lng+";noLango3";
	}


	/**
	 * Returns the name of an xcpt the formal-name of which
	 * an xElement contains.
	 * The name is the name of its first noun.
	 *
	 * @param lng	The language in which we want to get the name: eln,eng,...
	 * @modified 2009.11.04
	 * @since 2009.11.02 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public static String getNameOfConceptInXElement(String xElm, String lng)
	{
		String cpt= getXmlAttribute(xElm, "FRnAME");
		String name= getXCpt_sName(cpt, lng);
		name= name.replace(' ','-'); //2009.11.04
		return name;
	}


	/**
	 * Returns the NEXT formal-number to create a new xcpt in the
	 * given formal-view (ex: hknu.symb).
	 *
	 * @modified 2000.02.08
	 * @since 1999.04.21
	 * @author HoKoNoUmo
	 */
	public static int getNextFormalNumber(String frV)
	{
		int lastFormalNumber=0;

		//first check if there is any empty id-numbers.
		TreeSet<String> set= AAj.tmapEmptyID.get(frV);
		if (set!=null)
		{
			if (!set.isEmpty())
			{
				String n= set.first();
				lastFormalNumber=Integer.parseInt(n);
				//remove the id-number if it was an emptyFrNUMBER.
				set.remove(set.first());
				AAj.tmapEmptyID.put(frV,set);
				if (AAj.frameEditor!=null)AAj.frameEditor.tmapEmptyID=AAj.tmapEmptyID;
			}
		}
		else {
			for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
			{
				if (i.next().equals(frV))
				{
					String[] ar= AAj.trmapSrBrSubWorldview.get(frV);
					lastFormalNumber= Integer.parseInt(ar[2])+1;
					//increase the last id.
					ar[2]=String.valueOf(lastFormalNumber);
					AAj.trmapSrBrSubWorldview.put(frV, ar);
					if (AAj.frameEditor!=null)AAj.frameEditor.trmapSrBrSubWorldview=AAj.trmapSrBrSubWorldview;
					break;
				}
			}
		}
		return lastFormalNumber;
	}


	/**
	 * Returns the NEXT formal-number for an internal
	 * AND saves back the new last-internal-id.
	 * @param xcpt is filename/id of a file-xcpt.
	 * @modified 2000.05.15
	 * @since 2000.05.11
	 * @author HoKoNoUmo
	 */
	public static int getNextFormalNumber_Internal(String xcpt)
	{
		int nextIntID=0;
		String fileIn= Util.getXCpt_sLastFullFileName(xcpt);
		String fileOut= Util.getXCpt_sFullDir(xcpt) +File.separator +"tmp";
		try {
			FileReader reader =new FileReader(fileIn);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer= new FileWriter(fileOut);
			BufferedWriter bw =new BufferedWriter(writer);
			String ln= null;

			while ((ln= br.readLine()) != null)
			{
				if (ln.startsWith("<XCONCEPT"))
				{
					String part1= ln.substring(0, ln.indexOf("LASTiNTfRnUMBER="));
					String part2= ln.substring(ln.indexOf("INTEGRATED="),ln.length());
					String liid=ln.substring(ln.indexOf("LASTiNTfRnUMBER=")+11,
																	ln.indexOf("\"",ln.indexOf("LASTiNTfRnUMBER=")+12));
					String lastid=null;

					if (liid.indexOf(",")!=-1) //there are empty ids
					{
						if (liid.indexOf(",")==liid.lastIndexOf(",")) //eg 8,3
						{
							lastid=liid.substring(liid.indexOf(",")+1,liid.length());
							nextIntID=Integer.parseInt(lastid);
							bw.write(part1 +"LASTiNTfRnUMBER=\"" +liid.substring(0,liid.indexOf(","))
												+"\" " +part2);
							bw.newLine();
						}
						else //there are more than one empty id, eg 8,3,4,6
						{
							lastid=liid.substring(liid.indexOf(","),liid.indexOf(",",liid.indexOf(",")+1) );
							nextIntID=Integer.parseInt(lastid);
							bw.write(part1 +"LASTiNTfRnUMBER=\""	+liid.substring(0,liid.indexOf(","))
												+liid.substring(liid.indexOf(",",liid.indexOf(",")+1),liid.length())
												+"\" " +part2);
							bw.newLine();
						}
					}
					else //there are no empty ids.
					{
						nextIntID=Integer.parseInt(liid)+1;
						bw.write(part1 +"LASTiNTfRnUMBER=\"" +String.valueOf(nextIntID) +"\" " +part2);
						bw.newLine();
					}
					break;
				}
				else {
					bw.write(ln);
					bw.newLine();
				}
			}

			while ((ln= br.readLine()) != null)
			{
				bw.write(ln);
				bw.newLine();
			}

			br.close();
			bw.close();
		}
		catch (IOException eio)
		{
			JOptionPane.showMessageDialog(null, "Util.getNextFormalNumber_Internal",
																"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		//rename files
		File f1= new File(fileIn);
		f1.delete();
		File f2= new File(fileOut);
		f2.renameTo(new File(fileIn));

		//increase the number of internals in the knowledge-base.
		String xfrv= Util.getXCpt_sFormalView(xcpt);
		for (Iterator i=AAj.trmapSrBrSubWorldview.keySet().iterator(); i.hasNext(); )
		{
			if (i.next().equals(xfrv))
			{
				String[] ar= AAj.trmapSrBrSubWorldview.get(xfrv);
				ar[3]=String.valueOf(Integer.parseInt(ar[3])+1);
				AAj.trmapSrBrSubWorldview.put(xfrv, ar);
				if (AAj.frameEditor!=null)AAj.frameEditor.trmapSrBrSubWorldview=AAj.trmapSrBrSubWorldview;
				break;
			}
		}

		return nextIntID;
	}//method getNextFormalNumber_Internal.


	/**
	 * @param part
	 *		The part we want to find its number in a delimited-string.
	 * @param ds
	 *		The delimited-string (delimited with comma)
	 *		of which we want to find the order of a part.
	 * @modified 2004.09.02
	 * @since 2004.09.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static int getNumberOfDelimitedString(String part, String ds)
	{
		int n=0;
		int index=0;
		try {
			StringTokenizer parser= new StringTokenizer(ds, ",");
			while(parser.hasMoreElements())
			{
				index=index+1;
				if ((parser.nextElement()).equals(part)){
					n=index;
					break;
				}
			}
		} catch (NoSuchElementException e) {
			System.out.println("Util.getNumberOfDelimitedString: NoSuchElementException");
		}
		return n;
	}


	/**
	 * Creates a vector with elements the lines of a string.
	 *
	 * @modified 2009.01.15
	 * @since 2009.01.15 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static Vector<String> getVectorOfLinesOfString(String str)
	{
		Vector<String> voln= new Vector<String>();
		BufferedReader br= new BufferedReader(new StringReader(str));
		String ln="";
		try {
			while ( (ln=br.readLine())!=null )
			{
				voln.addElement(ln);
			}
			br.close();
		} catch (IOException e){
			System.out.println(e.toString());
		}
		return voln;
	}

	/**
	 * Returns the value of an xml-attribute from a string.<br/>
	 * IF the string does NOT contain this attribute, THEN "none"
	 * is returned.
	 *
	 * @param str
	 *		The string from which we want to extract the xml-attribute.
	 * @param attName
	 *		The name of the xml-attribute whose VALUE we want to find.
	 * @modified 2006.02.21
	 * @since 2001.03.13
	 * @author HoKoNoUmo
	 */
	public static String getXmlAttribute(String str, String attName)
	{
		String attribute="";
		try {
			if (str.indexOf(attName+"=")==-1)
				attribute="none";
			else {
				int b= str.indexOf(attName+"=")+attName.length()+2;
				int e= str.indexOf("\"",b);
				int e2= str.indexOf("\"",e+1);

				if ('\\'==str.charAt(e-1)) //the string contains \"
					attribute=str.substring(b, e2);
				else
					attribute=str.substring(b, e);
			}
		}
		catch (StringIndexOutOfBoundsException e) {
			log("Util.getXmlAttribute: substringException"
					+"\nstring=" +str +"\nattribute=" +attName);
		}
		return attribute;
	}


	/**
	 * Returns true if kpt1 is an attribute of kpt2.
	 *
	 * @modified 2006.11.23
	 * @since 2004.10.30 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean isAtriboOf(String idfier1, String idfier2)
	{
		String kns1ID=getXCpt_sFormalID(idfier1);
		String kns2ID=getXCpt_sFormalID(idfier2);
		if (isSpecificOf(kns1ID,kns2ID))
			return true;
		else if (isSpecificOf(kns2ID,kns1ID))
			return true;
		else if (isPartOf(kns1ID,kns2ID))
			return true;
		else if (isPartOf(kns2ID,kns1ID))
			return true;
		else if (isEnvironmentOf(kns1ID,kns2ID))
			return true;
		else
			return false;
	}


	/**
	 * Returns true if kns1 is an envio of kns2.
	 *
	 * @modified 2006.11.23
	 * @since 2006.11.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	 public static boolean isEnvironmentOf(String idfier1, String idfier2)
	{
		//ppp to be done.
		String kns1ID=getXCpt_sFormalID(idfier1);
		String kns2ID=getXCpt_sFormalID(idfier2);
		Vector<String> vWho= new Vector<String>(); //will hold ALL the whos.
		boolean yes=true;
		boolean no=false;
		Extract ex= new Extract();
		ex.extractWholes(kns1ID);
		if(!ex.vectorWhoID.isEmpty()) vWho=ex.vectorWhoID;

		while(!vWho.isEmpty())
		{
			if (vWho.contains(kns2ID)) return yes;

			//remove the first element of vg and append its gens.
			String who= vWho.remove(0);
			ex.extractWholes(who);
			if(!ex.vectorWhoID.isEmpty()) vWho.addAll(ex.vectorWhoID);
		}

		return no;//because there is no whos or no one meets the criteria.
	}


	/**
	 * check if kns1 is part of kns2?
	 * eg: is 'athens' part of 'greece'?
	 * in other words:
	 * is greece a whole in the chain of wholes of athens?
	 * @modified
	 * @since 2000.03.07
	 * @author HoKoNoUmo
	 */
	public static boolean isPartOf(String idfier1, String idfier2)
	{
		String kns1ID=getXCpt_sFormalID(idfier1);
		String kns2ID=getXCpt_sFormalID(idfier2);
		Vector<String> vWho= new Vector<String>(); //will hold ALL the whos.
		boolean yes=true;
		boolean no=false;
		Extract ex= new Extract();
		ex.extractWholes(kns1ID);
		if(!ex.vectorWhoID.isEmpty()) vWho=ex.vectorWhoID;

		while(!vWho.isEmpty())
		{
			if (vWho.contains(kns2ID)) return yes;

			//remove the first element of vg and append its gens.
			String who= vWho.remove(0);
			ex.extractWholes(who);
			if(!ex.vectorWhoID.isEmpty()) vWho.addAll(ex.vectorWhoID);
		}//loop

		return no;//because there is no whos or no one meets the criteria.
	}


	/**
	 * Check if kpt1 IS kpt2. eg is 'everest' a 'mountain'?
	 * in other words:
	 * is kpt2 contained in any gen of kpt1?
	 * (or is kpt1 a spe of kpt2?)
	 * @param idfier1
	 *		A unique-name of a file/internal kpt1.
	 * @modified 2006.11.23
	 * @since 2000.03.01
	 * @author HoKoNoUmo
	 */
	public static boolean isSpecificOf(String idfier1, String idfier2)
	{
		String kns1ID=getXCpt_sFormalID(idfier1);
		String kns2ID=getXCpt_sFormalID(idfier2);
		Vector<String> vg= new Vector<String>(); //will hold all the founding gens.
		boolean yes=true;
		boolean no=false;
		Extract ex= new Extract();
		ex.extractGenerics(kns1ID);
		if(!ex.vectorGenID.isEmpty()) vg=ex.vectorGenID;

		while(!vg.isEmpty())
		{
			if (vg.contains(kns2ID)) return yes;

			//remove the first element of vg and append its gens.
			String gen= vg.remove(0);
			ex.extractGenerics(gen);
			if(!ex.vectorGenID.isEmpty()) vg.addAll(ex.vectorGenID);
		}

		return no;//because there is no gens or no one meets the criteria.
	}


	/**
	 * @param message
	 *		The message we want to write in log-file
	 *		(log.txt at program's directory).
	 *		If message=start, it creates the log-file.
	 *		If message=close, it closes the file.
	 *		If message=null, writes a new line.
	 * @modified 2004.08.13
	 * @since 2004.08.13 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static void log(String message)
	{
		//if message=="no log" do nothing 2004.11.17
		if (message!=null){
			if (message.equals("start")){
				try{
					Util.bwLog=new BufferedWriter(new FileWriter(AAj_sDir+"log.txt"));
				}catch (IOException ioe){
					System.out.println("Util.log: problem in creating log-file");
				}
			}
			else if (message.equals("close")){
				try{
					Util.bwLog.close();
				}catch (IOException ioe){
					System.out.println("Util.log: problem in closing log-file");
				}
			}
			else {
				try{
					bwLog.newLine();
					bwLog.write(message);
					bwLog.flush();
				}catch (IOException ioe){
					System.out.println("Util.log: problem writing in log-file");
				}
			}
		}
		else {
			try{
				bwLog.newLine();
				bwLog.flush();
			}catch (IOException ioe){
				System.out.println("Util.log: problem writing in log-file");
			}
		}
	}


	/**
	 * @modified 2004.09.23
	 * @since 2004.09.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static void logArrayString(String[] ar)
	{
		try{
			for(int i=0; i<ar.length; i++)
			{
				bwLog.write(i+"="+ar[i]+", ");
			}
			bwLog.flush();
		}catch (IOException ioe){
			System.out.println("Util.logArrayString: problem writing in log-file");
		}
	}


	/**
	 *
	 * @modified 2006.10.19
	 * @since 2006.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static void logVectorString(Vector<String> v)
	{
		try {
			for(String s : v)
			{
				log(s);
			}
			bwLog.flush();
		}catch (IOException ioe){
			System.out.println("Util.logVectorString: problem writing in log-file");
		}
	}


	/**
	 * <b>INPUT</b>: a xml-SSemasia and the output language.<br/>
	 * <b>OUTPUT</b>: its Text in the language given.
	 * @modified 2006.01.08
	 * @since 2001.09.30
	 * @author HoKoNoUmo
	 */
	public static String mapXSSemasiaToText(String xSSm, String lng)
		//throws Exception_Logo_GeneratingText, Exception_SSemasia_Operating
	{
		Parser_SSemasia parser= new Parser_SSemasia();
		SSmNode jSSm= parser.parseFromString(xSSm);
		StringWriter wr= new StringWriter();
		Maper_SSemasiaToSSemasia mvv= new Maper_SSemasiaToSSemasia(jSSm,lng);
//Util.log(mvv.jSSmOut.toString());
		Maper_SSemasiaToText mm= new Maper_SSemasiaToText(mvv.jSSmOut, lng, wr);
		return wr.toString();
	}

	/**
	 * Prints on sout an array of strings.
	 *
	 * @modified 2006.10.19
	 * @since 2004.09.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static void printArrayString(String[] a)
	{
		for (int i=0; i<a.length; i++)
			System.out.println(a[i]);
	}


	/**
	 * Prints on sout a vector of strings.
	 *
	 * @modified 2009.01.23
	 * @since 2009.01.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static void printVectorOfString(Vector<String> v)
	{
		for (String l : v)
			System.out.println(l);
	}


	/**
	 * Sorts an array of strings.<br/>
	 * GIVEN: the array and the language of strings (3 letter code).
	 *
	 * @modified 2006.02.08
	 * @since 2006.02.08 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String[] sortArrayString(String[] a, String lng)
	{
		Collator collator= Collator.getInstance();
		if (lng.toLowerCase().equals("eln"))
			collator= Collator.getInstance(new Locale("el", "GR"));

		String tmp;
		for (int i=0; i<a.length; i++)
		{
			for (int j=i+1; j<a.length; j++)
			{
				// Compare elements of the array two at a time.
				if (collator.compare(a[i], a[j]) > 0 )
				{
					// Swap a[i] and a[j]
					tmp= a[i];
					a[i]= a[j];
					a[j]= tmp;
				}
			}
		}
		return a;
	}


	/**
	 * Renames file-A with the name of file-B AND deletes file-B.<br/>
	 * Precodition: the names of the files must contain and the full
	 * directories.
	 *
	 * @modified 2009.11.03
	 * @since 2000.01.29
	 * @author HoKoNoUmo
	 */
	public static void renameFile1AndDeleteFile2(String aFile, String bFile)
	{
		//aFile= tmpInsert...
		File f2= new File(bFile);
		f2.delete();
		File f1= new File(aFile);
		f1.renameTo(new File(bFile));
	}

	/**
	 * @modified
	 * @since 2000.02.01
	 * @author HoKoNoUmo
	 */
	public static void setFrameIcon(Frame fr)
	{
		fr.setIconImage( Util.getImage("resources/AAj_icon.gif") );
	}

	/**
	 * TODO: DON'T USE. It creates as garbage the 'tmp' file. 2001.05.12
	 *				NOW the generic att is: gen/inheritor, to fix the code. 2000.11.13
	 *
	 * @param xcpt is the frID/FileName of the (file) xcpt in which we gonna make modifications.
	 * @param oldNamesID is a Vector that contains the ids of the xcpts with old-names.
	 * @modified
	 * @since 2000.05.29
	 * @author HoKoNoUmo
	 */
	public static void setLastCptNames(String xcpt, Vector oldNamesID)
	{
		String fileIn= Util.getXCpt_sLastFullFileName(xcpt);
		String fileOut= Util.getXCpt_sFullDir(xcpt) +File.separator +"tmp";

		try {
			FileReader reader =new FileReader(fileIn);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer= new FileWriter(fileOut);
			BufferedWriter bw =new BufferedWriter(writer);
			String ln= null;

			while ((ln= br.readLine()) != null)
			{
				if (!ln.startsWith("<XCPT") && !ln.startsWith("<INTxCPT") && !ln.startsWith("<REFINO_SPECIFICdIVISION"))
				{
					if (ln.startsWith("<XCONCEPT"))//set lastmodified.
					{
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
				}

				else //line STARTS with <XCPT/INTxCPT/REFINO_SPECIFICdIVISION
				{
					if (ln.startsWith("<XCPT")) //check fln/generic
					{
						String existFLN=Util.getXmlAttribute(ln,"FRnAME");
						existFLN=Util.getXCpt_sFormalID(existFLN);
						String existGEN=Util.getXmlAttribute(ln,"REFINO_GENERIC");
						existGEN=Util.getXCpt_sFormalID(existGEN);
						if (oldNamesID.contains(existFLN) && oldNamesID.contains(existGEN)) //replace both
						{
							String part2= ln.substring(ln.indexOf("CREATED="),ln.length());
							String fln=Util.getXCpt_sLastFileName(existFLN);
							String gen=Util.getXCpt_sLastFileName(existGEN);
							bw.write("<XCPT FRnAME=\"" +fln
											+"\" GENERICiNHERITOR=\"" + gen
											+"\" LASTmOD=\"" +Util.getCurrentDate() +"\" " +part2);
							bw.newLine();
						}
						else if (oldNamesID.contains(existFLN)) //replace fln
						{
							String part1= ln.substring(ln.indexOf("GENERICiNHERITOR="), ln.indexOf("LASTmOD="));
							String part2= ln.substring(ln.indexOf("CREATED="),ln.length());
							String fln=Util.getXCpt_sLastFileName(existFLN);
							bw.write("<XCPT FRnAME=\"" +fln +"\" "
											+part1 +"LASTmOD=\"" +Util.getCurrentDate() +"\" "
											+part2);
							bw.newLine();
						}
						else if (oldNamesID.contains(existGEN)) //replace gen
						{
							String part1= ln.substring(0, ln.indexOf("GENERICiNHERITOR="));
							String part3= ln.substring(ln.indexOf("CREATED="),ln.length());
							String gen=Util.getXCpt_sLastFileName(existGEN);
							bw.write(part1 +"GENERICiNHERITOR=\"" + gen
											+"\" LASTmOD=\"" +Util.getCurrentDate() +"\" " +part3);
							bw.newLine();
						}
						else //write the line
						{
							bw.write(ln);
							bw.newLine();
						}
					}


					else if (ln.startsWith("<INTxCPT")) //check generic
					{
						String existGEN=ln.substring(ln.indexOf("GENERICiNHERITOR=")+9,ln.indexOf("\"",ln.indexOf("GENERICiNHERITOR=")+10));
						existGEN=Util.getXCpt_sFormalID(existGEN);

						if (oldNamesID.contains(existGEN)) //replace gen
						{
							String part1= ln.substring(0, ln.indexOf("GENERICiNHERITOR="));
							String part3= ln.substring(ln.indexOf("CREATED="),ln.length());
							String gen=Util.getXCpt_sLastFileName(existGEN);
							bw.write(part1 +"GENERICiNHERITOR=\"" + gen
											+"\" LASTmOD=\"" +Util.getCurrentDate() +"\" "	 +part3);
							bw.newLine();
						}
						else //write the line
						{
							bw.write(ln);
							bw.newLine();
						}
					}


					else if (ln.startsWith("<REFINO_SPECIFICdIVISION")) //check crt
					{
						String existCRT=ln.substring(ln.indexOf("ATR=")+5,ln.indexOf("\"",ln.indexOf("ATR=")+6));
						existCRT=Util.getXCpt_sFormalID(existCRT);
						if (oldNamesID.contains(existCRT)) //replace crt
						{
							String part2= ln.substring(ln.indexOf("CREATED="),ln.length());
							String crt=Util.getXCpt_sLastFileName(existCRT);
							bw.write("<REFINO_SPECIFICdIVISION ATR=\"" +crt +"\" LASTmOD=\"" +Util.getCurrentDate()
											+"\" " +part2);
							bw.newLine();
						}
						else //write the line
						{
							bw.write(ln);
							bw.newLine();
						}
					}


					else {
						bw.write(ln);
						bw.newLine();
					}
				}
			}
			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Util.setLastCptNames: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		File f1= new File(fileIn);//rename files.
		f1.delete();
		File f2= new File(fileOut);
		f2.renameTo(new File(fileIn));
	}


	/**
	 * Sets CurrentDate in "DELETED" xml-attribute.<br/>
	 * GIVEN: "CREATED" attr follows.
	 *
	 * @modified 2008.03.28
	 * @since 2008.03.25 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String setDateDeleted(String ln)
	{
		if (ln.indexOf("DELETED=")!=-1){
			String part1= ln.substring(0, ln.indexOf("DELETED="));
			String part2= ln.substring(ln.indexOf("CREATED="),ln.length());
			return part1 +"DELETED=\"" +Util.getCurrentDate() +"\" " +part2;
		}
		else {
			String part1= ln.substring(0, ln.indexOf("CREATED="));
			String part2= ln.substring(ln.indexOf("CREATED="),ln.length());
			return part1 +"DELETED=\"" +Util.getCurrentDate() +"\" " +part2;
		}
	}


	/**
	 * Sets CurrentDate in "LASTmOD" xml-attribute.<br/>
	 * GIVEN: "CREATED" attr follows.
	 *
	 * @modified 2008.03.28
	 * @since 2008.03.24 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String setDateLastMod(String ln)
	{
		if (ln.indexOf("LASTmOD=")!=-1){
			String part1= ln.substring(0, ln.indexOf("LASTmOD="));
			String part2= ln.substring(ln.indexOf("CREATED="),ln.length());
			return part1 +"LASTmOD=\"" +Util.getCurrentDate() +"\" " +part2;
		}
		else {
			String part1= ln.substring(0, ln.indexOf("CREATED="));
			String part2= ln.substring(ln.indexOf("CREATED="),ln.length());
			return part1 +"LASTmOD=\"" +Util.getCurrentDate() +"\" " +part2;
		}
	}

}
