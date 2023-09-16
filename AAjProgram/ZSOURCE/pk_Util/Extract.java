package pk_Util;

import pk_XKBManager.AAj;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * This Class will hold ALL the extraction methods.
 *
 * @modified 2001.06.28
 * @since 2000.02.21
 * @author HoKoNoUmo
 */
public class Extract
{
	/* We need it to refer to sbcpts. */
	String xcpt_sFormalID;

	//I will create these vectors when I will call the corresponding methods.
	/** Contains the IDs of the PARTS cpts of an xml-sc. */
	public Vector<String>		vectorPar = null;						//holds the filenames of ALL pars.
	public Vector<String>		vectorParFile = null;				//holds the pars that are file-cpts.
	public Vector<String>		vectorParGen = null;					//Holds the generics of Inherited pars.
	public Vector<String>		vectorParInh = null;					//Holds the inherited, have a value on gen.
	public Vector<String>		vectorParInhOver = null;			//nmID =/ gen
	public Vector<String>		vectorParInhNotOver = null;		//nmID = gen
	public Vector<String>		vectorParNotInh = null;			//gen=no.

	public Vector<String>		vectorParID = null;					//holds the ids of All pars.
	public Vector<String>		vectorParFileID = null;
	public Vector<String>		vectorParGenID = null;
	public Vector<String>		vectorParInhID = null;
	public Vector<String>		vectorParInhGenID = null;		//holds the ids of inherited WITH its gens as "inhid,genid"
	public Vector<String>		vectorParInhOverID = null;
	public Vector<String>		vectorParInhNotOverID = null;
	public Vector<String>		vectorParNotInhID = null;
	/** Contains the IDs of the ENVIRONMENT cpts of an xml-sc. */
	public Vector<String>		vectorEnv = null;
	public Vector<String>		vectorEnvID = null;
	/** Contains the IDs of the GENERIC cpts of an xml-sc. */
	public Vector<String>		vectorGen = null;
	public Vector<String>		vectorGenID = null;
	public String		stringGen = null; //contains the lastFileName of first gen OR 'no'
	public Vector<String>		vectorAllGenID=null;

	/** Contains the IDs of the SPECIFIC cpts of an xml-sc. */
	public Vector<String>		vectorSpe = null;
	public Vector<String>		vectorSpeID = null;
	/** Contains the IDs of the WHOLE cpts of an xml-sc. */
	public Vector<String>		vectorWho = null;
	public Vector<String>		vectorWhoID = null;

	/** Contains the IDs of the SPECIFIC cpts of an xml-sc's-division. */
	public Vector<String>		vectorSubDiv = null;
	public Vector<String>		vectorSubDivID = null;
	/** Contains the FileNames/IDs of the DIVISIONS of an xml-sc. */
	public Vector<String>		vectorDivision = null;
	public Vector<String>		vectorDivisionID = null;

	//I'm using this to hold the def/name data.
	public ByteArrayOutputStream baos = null;

	String line;
	Vector<String> filesToSearch=new Vector<String>();	// the files I must search for a sbcpt.

	/**
	 * Find all the generics of an xcpt and puts them on the
	 * vector vectorAllGenID.
	 *
	 * @param name is a unique-designator of a file/internal-xcpt.
	 * @modified
	 * @since 2000.04.11
	 * @author HoKoNoUmo
	 */
	public void extractAllGen(String name)
	{
		String xcpt_sFormalID=Util.getXCpt_sFormalID(name);
		vectorAllGenID=new Vector<String>();
		filesToSearch.add(xcpt_sFormalID);
		while(!filesToSearch.isEmpty())
		{
			String currentFileName=null;
			try {
				currentFileName = filesToSearch.firstElement();
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "extractAllGen: Problem in " + currentFileName);
				continue;				// Try next file
			}
			extractGenerics(currentFileName);
			Vector<String> gens = vectorGenID;
			for (int i=0; i<gens.size(); i++)
			{
				filesToSearch.add(gens.get(i));
				vectorAllGenID.add(gens.get(i));
			}
			filesToSearch.remove(0);
		}
	}

	/**
	 * Extract the part-XConcepts (XCPT/INT) of a file-xcpt.
	 * An internal doesn't have parts.
	 *
	 * @param fileName is a unique-designator of a file/internal-xcpt.
	 * @modified 2000.06.05
	 * @since 1999.02.07
	 * @author HoKoNoUmo
	 */
	public void extractParts (String fileName)
	{
		vectorPar = new Vector<String>();
		vectorParID = new Vector<String>();
		vectorParFile = new Vector<String>();
		vectorParFileID = new Vector<String>();
		vectorParGen = new Vector<String>();
		vectorParGenID = new Vector<String>();
		vectorParInh = new Vector<String>();					//have a value on gen.
		vectorParInhID = new Vector<String>();
		vectorParInhGenID = new Vector<String>();
		vectorParInhOver = new Vector<String>();			//nmID # gen
		vectorParInhOverID = new Vector<String>();
		vectorParInhNotOver = new Vector<String>();		//nmID = gen
		vectorParInhNotOverID = new Vector<String>();
		vectorParNotInh = new Vector<String>();				//gen=no.
		vectorParNotInhID = new Vector<String>();

		if (fileName.indexOf("#")==-1)//the xcpt is not an internal
		{
			try {
				BufferedReader br=new BufferedReader(new FileReader(Util.getXCpt_sLastFullFileName(fileName)));
				while ((line=br.readLine()) !=null)
				{
					if (line.startsWith("<REFINO_PART"))
					{
						while (!line.startsWith("</REFINO_PART"))
						{
							if (line.startsWith("<XCPT") || line.startsWith("<INTxCPT"))
							{
								String cptFLN=Util.getXmlAttribute(line,"FRnAME");
								String cptGen=Util.getXmlAttribute(line,"REFINO_GENERIC");
								//all att.
								vectorPar.add(cptFLN);
								vectorParID.add(Util.getXCpt_sFormalID(cptFLN));

								//inherited - NonInherited
								if (cptGen==null)
								{
									vectorParNotInh.add(cptFLN);
									vectorParNotInhID.add(Util.getXCpt_sFormalID(cptFLN));
									if (cptFLN.indexOf("#")==-1)//the notInternals are file-cpts.
									{
										vectorParFile.add(cptFLN);
										vectorParFileID.add(Util.getXCpt_sFormalID(cptFLN));
									}
								}
								else //gen != no
								{
									vectorParGen.add(cptGen);
									vectorParGenID.add(Util.getXCpt_sFormalID(cptGen));
									vectorParInh.add(cptFLN);
									vectorParInhID.add(Util.getXCpt_sFormalID(cptFLN));
									vectorParInhGenID.add(Util.getXCpt_sFormalID(cptFLN) +"," +Util.getXCpt_sFormalID(cptGen));
									//inherited overridden-NotOverridden
									if(Util.getXCpt_sFormalID(cptFLN).equals(Util.getXCpt_sFormalID(cptGen)))
									{
										vectorParInhNotOver.add(cptFLN);
										vectorParInhNotOverID.add(Util.getXCpt_sFormalID(cptFLN));
									}
									else//gen != fln
									{
										vectorParInhOver.add(cptFLN);
										vectorParInhOverID.add(Util.getXCpt_sFormalID(cptFLN));
										//The inherited-Overridden are file-cpts.
										vectorParFile.add(cptFLN);
										vectorParFileID.add(Util.getXCpt_sFormalID(cptFLN));
									}
								}
							}

							line=br.readLine();
						}
						break;
					}
				}
				br.close();
			}
			catch (EOFException e)					{System.out.println("ExtractAttID: EOFException");}
			catch (FileNotFoundException e) {System.out.println("ExtractAttID: FNFException");}
			catch (IOException e)						{System.out.println("ExtractAttID: IOException");}
		}
	}//End of ExtractAtt


	/**
	 * Extract the TEXT of the definition element of an xml-file
	 *
	 * @param name a unique-designator of a file/internal-xcpt.
	 * @modified 2000.03.12
	 * @since 1999.01.09
	 * @author HoKoNoUmo
	 */
	public void extractDef (String name)
	{
		baos = new ByteArrayOutputStream();
		String xcpt_sFormalID =	Util.getXCpt_sFormalID(name);

		if (xcpt_sFormalID.indexOf("#")==-1)//it is a file-xcpt.
		{
			try {
				BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(xcpt_sFormalID)));
				/* bw will contain all the output information. */
				BufferedWriter	bw = new BufferedWriter	 (new PrintWriter (baos));

				while ((line=br.readLine()) !=null)
				{
					if (line.startsWith("<REFINO_DEFINITION"))
					{
						line=br.readLine();
						while (!line.startsWith("</REFINO_DEFINITION"))
						{
//								line.trim();
								if (line.startsWith("\t")) line=line.substring(1,line.length());
								bw.write("\t" +line);
								bw.newLine();
								line=br.readLine();
						}
						break;
					}
				} // end of while
				br.close();
				bw.close();
			}
			catch (EOFException e){System.out.println("Extract.extractDef: EOFException");}
			catch (FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(null, "There is NOT file to extract REFINO_DEFINITION for " +name);
			}
			catch (IOException e){System.out.println("Extract.extractDef: IOException");}
		}

		else //it is an internal-xcpt.
		{
			String hc = Util.getSrCptInt_sHostFlName(name);
			String hcID = Util.getXCpt_sFormalID(hc);
			try {
				BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(hcID)));
				/* bw will contain all the output information. */
				BufferedWriter	bw = new BufferedWriter	 (new PrintWriter (baos));

				while ((line=br.readLine()) !=null)
				{
					if (line.startsWith("<INTxCPT"))
					{
						String cptIntFile=Util.getXmlAttribute(line,"FRnAME");
						String cptIntID= Util.getXCpt_sFormalID(cptIntFile);
						if (cptIntID.equals(xcpt_sFormalID))
						{
							while ((line=br.readLine()) !=null)//pass the syn lines.
							{
								if (line.indexOf("<REFINO_DEFINITION")!=-1) break;
							}
							line=br.readLine();//first line of def.
							while (line.indexOf("</REFINO_DEFINITION")==-1)
							{
//								line.trim();
//								if (line.startsWith("\t")) line=line.substring(1,line.length());
								bw.write(line);
								bw.newLine();
								line=br.readLine();
							}
							break;
						}
					}
				} // end of while
				br.close();
				bw.close();
			}
			catch (EOFException e){System.out.println("Extract.extractDef: EOFException");}
			catch (FileNotFoundException e)
			{
				JOptionPane.showMessageDialog(null, "There is NOT file to extract REFINO_DEFINITION for " +name);
			}
			catch (IOException e){System.out.println("Extract.extractDef: IOException");}
		}
	}

	/**
	 * @param name is a unique-designator of a file/internal-xcpt.
	 * @modified
	 * @since 2000.06.10
	 * @author HoKoNoUmo
	 */
	public void extractDivision (String name)
	{
		vectorDivision = new Vector<String>();
		vectorDivisionID = new Vector<String>();

		if (name.indexOf("#")==-1) //an internal doesn't have subs.
		{
			try {
				BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(name)));

				while ((line=br.readLine())!=null)
				{
					if (line.startsWith("<REFINO_SPECIFIC"))
					{
						while (!line.startsWith("</REFINO_SPECIFIC"))
						{
							if (line.startsWith("<REFINO_SPECIFICdIVISION"))
							{
								String sbcpt=line.substring(line.indexOf("ATR=")+5,
																					line.indexOf("\"",line.indexOf("ATR=")+6));
								sbcpt = Util.getXCpt_sLastFileName(sbcpt);
								vectorDivision.add(sbcpt);
								String xcpt_sFormalID= Util.getXCpt_sFormalID(sbcpt);
								vectorDivisionID.add(xcpt_sFormalID);
							}
							line=br.readLine();
						}
						break;
					}
				} // end of while
				br.close();
			}
			catch (EOFException e)					{System.out.println("ExtractDivision: EOFException");}
			catch (FileNotFoundException e){System.out.println("ExtractDivision: FNFException");}
			catch (IOException e)					{System.out.println("ExtractDivision: IOException");}
		}
	}

	/**
	 * Extract the ENVIRONMENT-XConcepts of a file-xcpt.
	 * An internal doesn't have pars.
	 * Returns a vector with the IDs and
	 * a vector with the filenames of these concepts.
	 * @modified 2000.02.24
	 * @since 1999.02.23
	 * @author HoKoNoUmo
	 */
	public void extractEnvironments(String fileName)
	{
		vectorEnv = new Vector<String>();
		vectorEnvID = new Vector<String>();

		if (fileName.indexOf("#")==-1)//the xcpt is not an internal
		{
			try {
				BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(fileName)));

				while ((line=br.readLine()) !=null)
				{
					if (line.startsWith("<REFINO_ENVIRONMENT"))
					{
						while (!line.startsWith("</REFINO_ENVIRONMENT"))
						{
							if (line.startsWith("<XCPT"))
							{
								String xcpt_sFormalID=Util.getXmlAttribute(line,"FRnAME");
								vectorEnv.add(xcpt_sFormalID);

								String cptIDID= Util.getXCpt_sFormalID(xcpt_sFormalID);
								vectorEnvID.add(cptIDID);
							}
							line=br.readLine();
						}
						break;
					}
				} // end of while
				br.close();
			}
			catch (EOFException e)					{System.out.println("ExtractEnv: EOFException");}
			catch (FileNotFoundException e) {System.out.println("ExtractEnv: FNFException");}
			catch (IOException e)						{System.out.println("ExtractEnv: IOException");}
		}
	}//End of extractEnvironments

	/**
	 * Extract the GENERIC-XConcepts of a file-xcpt.
	 * Returns a vector with the IDs and
	 * a vector with the filenames of these concepts.<p>
	 *
	 * An internal-xcpt has its gen on its gen xml-attribute.
	 *
	 * @param fileName is a unique-designator of a file/internal-xcpt.
	 * @modified 2000.03.27
	 * @since 1999.02.10
	 * @author HoKoNoUmo
	 */
	public void extractGenerics(String fileName)
	{
		vectorGen = new Vector<String>();
		vectorGenID = new Vector<String>();

		if (!fileName.equals("none")){
			if (fileName.indexOf("#")==-1)//the xcpt is not an internal
			{
				try {
					BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(fileName)));

					while ((line=br.readLine()) !=null)
					{
						if (line.startsWith("<REFINO_GENERIC"))
						{
							while (!line.startsWith("</REFINO_GENERIC"))
							{
								if (line.startsWith("<XCPT"))
								{
									String xcpt_sFormalID=Util.getXmlAttribute(line,"FRnAME");
									vectorGen.add(xcpt_sFormalID);
									String cptIDID= Util.getXCpt_sFormalID(xcpt_sFormalID);
									vectorGenID.add(cptIDID);
								}
								line=br.readLine();
							}
							break;
						}
					} // end of while
					br.close();
				}
				catch (EOFException e)					{System.out.println("ExtractGen: EOFException");}
				catch (FileNotFoundException e) {System.out.println("ExtractGen: FNFException (" +fileName +")");}
				catch (IOException e)						{System.out.println("ExtractGen: IOException");}
			}

			else //sbcpt IS An internal.
			{
				String hc = Util.getSrCptInt_sHostFlName(Util.getXCpt_sLastFileName(fileName));
				String hcID = Util.getXCpt_sFormalID(hc);
				String xcpt_sFormalID=Util.getXCpt_sFormalID(fileName);

				try {
					BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(hcID)));

					while ((line=br.readLine()) !=null)
					{
						if (line.startsWith("<INTxCPT"))
						{
							String cptIntFile=Util.getXmlAttribute(line,"FRnAME");
							String cptIntID= Util.getXCpt_sFormalID(cptIntFile);
							if (cptIntID.equals(xcpt_sFormalID))
							{
								String cptIntGen=Util.getXmlAttribute(line,"REFINO_GENERIC");
								if (cptIntGen!=null)
								{
									String cptIntGenID=Util.getXCpt_sFormalID(cptIntGen);
									vectorGen.add(cptIntGen);
									vectorGenID.add(cptIntGenID);
									break;
								}
							}
						}
						else {
							continue;
						}
					} // end of while
					br.close();
				}
				catch (EOFException e)					{System.out.println("ExtractGen: EOFException");}
				catch (FileNotFoundException e) {System.out.println("ExtractGen: FNFException");}
				catch (IOException e)						{System.out.println("ExtractGen: IOException");}
			}
		}

		//put value on stringGen.
		if (!vectorGenID.isEmpty())
		{
			stringGen=vectorGen.get(0);
			stringGen=Util.getXCpt_sLastFileName(stringGen);
		}
		else stringGen="no";
	}

	/**
	 * Extract the 'file-name' of an internal-xcpt.
	 *
	 * @param formalID the formalID of an internal-xcpt (hknu.symb-21#1).
	 * @modified 2009.01.19
	 * @since 2000.02.12
	 * @author HoKoNoUmo
	 */
	public String extractIntFileName (String formalID)
	{
		String	 intFileName="";
		try {
			String hostIdName=Util.getSrCptInt_sHostID(formalID);
			BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(hostIdName)));

			while ((line=br.readLine()) !=null)
			{
				if (line.startsWith("<INTxCPT"))
				{
					String idExist=Util.getXmlAttribute(line,"FRnAME");
					String ideid = idExist.substring(idExist.indexOf("@")+1, idExist.length()-4);
					if (formalID.equals(ideid))
					{
						intFileName=idExist;
						break;
					}
					else continue;
				}
			}
			br.close();
		}
		catch (EOFException e)
			{System.out.println("ExtractIntID: EOFException");}
		catch (FileNotFoundException e)
			{System.out.println("ExtractIntID: FNFException");}
		catch (IOException e)
			{System.out.println("ExtractIntID: IOException");}
		return intFileName;
	}

	/**
	 * Extract the Name-elements of a file-xcpt.
	 *
	 * @param uniqueNameOfXCpt	A unique-name of a file/internal-xcpt.
	 * @param nameType
	 *  The type of name: NameNone, Name_NounCase, Name_NounAdjective,
	 *  Name_NounAdverb, Name_Verb, NameConcjunction, ...
	 * @param lng	The 3letter language-code of the Name-element.
	 * @modified 2009.01.19
	 * @since 2009.01.19
	 * @author HoKoNoUmo
	 */
	public Vector<String> extractName (String uniqueNameOfXCpt, String nameType, String lng)
	{
		Vector<String> von = new Vector<String>();//VectorOfNames
		try {
			BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(uniqueNameOfXCpt)));
//ppp on internal-xcpts.
			while ((line=br.readLine()) !=null)
			{
				if (line.startsWith("<"+lng))
				{
					while (!(line=br.readLine()).startsWith("</"+lng))
					{
						if (line.startsWith("<"+nameType))
							von.add(line);
					}
					break;
				}
			}
			br.close();
		}
		catch (EOFException e)
			{System.out.println("ExtractName: EOFException");}
		catch (FileNotFoundException e)
		{
			JOptionPane.showMessageDialog(null, "There is NOT file to extract NAME for " +uniqueNameOfXCpt);
		}
		catch (IOException e)
			{System.out.println("ExtractName: IOException");}
		return von;
	}


	/**
	 * Extract All the SPECIFICS of a file-xcpt.
	 * Returns a vector with the IDs and
	 * a vector with the LAST filenames of these concepts.
	 *
	 * @param fileName is a unique-designator of a file/internal-xcpt.
	 * @modified 2004.04.09
	 * @since 1999.02.28
	 * @author HoKoNoUmo
	 */
	public void extractSpecifics (String fileName)
	{
		vectorSpe = new Vector<String>();
		vectorSpeID = new Vector<String>();

		if (fileName.indexOf("#")==-1) //an internal doesn't have subs.
		{
			try {
				BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(fileName)));

				while ((line=br.readLine())!=null)
				{
					if (line.startsWith("<REFINO_SPECIFIC"))
					{
						while (!line.startsWith("</REFINO_SPECIFIC"))
						{
							if (line.startsWith("<XCPT"))
							{
								String xcpt_sFormalID=Util.getXmlAttribute(line,"FRnAME");
								//we will extract the last file-names of these concepts.
								xcpt_sFormalID = Util.getXCpt_sLastFileName(xcpt_sFormalID);
								if (!vectorSpe.contains(xcpt_sFormalID))
								{
									vectorSpe.add(xcpt_sFormalID);
									String cptIDID= Util.getXCpt_sFormalID(xcpt_sFormalID);
									vectorSpeID.add(cptIDID);
								}
							}//if cptext

							else if (line.startsWith("<INTxCPT"))
							{
								String cptIntFile=Util.getXmlAttribute(line,"FRnAME");
								String cptIntID= Util.getXCpt_sFormalID(cptIntFile);
								if (!vectorSpe.contains(cptIntFile))
								{
									vectorSpe.add(cptIntFile);
									vectorSpeID.add(cptIntID);
								}
							}
							line=br.readLine();
						}
						break;
					}
				}
				br.close();
			}
			catch (EOFException e)	{System.out.println("ExtractSpe: EOFException");}
			catch (FileNotFoundException e) {System.out.println("ExtractSpe: FNFException");}
			catch (IOException e)	{System.out.println("ExtractSpe: IOException");}
		}
	}

	/**
	 * Extracts the specifics on a Division.
	 * @param name is a unique-designator of a file/internal-xcpt.
	 * @param div is an nmID/fileName of an xcpt's-division.
	 * @modified
	 * @since 2000.06.10
	 * @author HoKoNoUmo
	 */
	public void extractSubDiv(String name, String div)
	{
		vectorSubDiv = new Vector<String>();
		vectorSubDivID = new Vector<String>();
		String divID=Util.getXCpt_sFormalID(div);

		if (name.indexOf("#")==-1) //an internal doesn't have subs.
		{
			try {
				BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(name)));

				while ((line=br.readLine())!=null)
				{
					if (line.startsWith("<REFINO_SPECIFICdIVISION"))
					{
						if (line.indexOf(divID)!=-1) //we found the division we want.
						{
							while (!line.startsWith("</REFINO_SPECIFICdIVISION"))
							{
								if (line.startsWith("<XCPT"))
								{
									String sbcpt=Util.getXmlAttribute(line,"FRnAME");
									sbcpt = Util.getXCpt_sLastFileName(sbcpt);
									vectorSubDiv.add(sbcpt);
									String xcpt_sFormalID= Util.getXCpt_sFormalID(sbcpt);
									vectorSubDivID.add(xcpt_sFormalID);
								}
								else if (line.startsWith("<INTxCPT"))
								{
									String cptInt=Util.getXmlAttribute(line,"FRnAME");
									String cptIntID= Util.getXCpt_sFormalID(cptInt);
									vectorSubDiv.add(cptInt);
									vectorSubDivID.add(cptIntID);
								}
								line=br.readLine();
							}
						}
					}
					if (line.startsWith("</REFINO_SPECIFIC")) break;
				} // end of while
				br.close();
			}
			catch (EOFException e)					{System.out.println("ExtractSubDiv: EOFException");}
			catch (FileNotFoundException e) {System.out.println("ExtractSubDiv: FNFException");}
			catch (IOException e)						{System.out.println("ExtractSubDiv: IOException");}
		}
	}

	/**
	 * Extracts the LINES of the REFINO_NAME-element of a file/internal
	 * xConcept.<br/>
	 * Returns a vector with these lines plus lng-elements.<br/>
	 * If lang=all AND REFINO_NAME-element. 2001.06.28<br/>
	 * If REFINO_NAME or lng doesn't exists, the vector is empty. 2001.06.28
	 *
	 * @param uniqueNameOfXCpt	A unique-name of a file/internal-xcpt.
	 * @param lang		The language on which we want to extract names:
	 * 				eln/eng/.../all.
	 * @modified 2008.12.07
	 * @since 1999.04.25
	 * @author HoKoNoUmo
	 */
	public Vector<String> extractLgConceptNames (String uniqueNameOfXCpt, String lang)
	{
		lang=lang.toLowerCase();
		xcpt_sFormalID=Util.getXCpt_sFormalID(uniqueNameOfXCpt);
		Vector<String> vectorNames = new Vector<String>();
		String ln;
		FileReader fr=null;
		BufferedReader br=null;

		try {
			if (xcpt_sFormalID.indexOf("#")==-1) //it is a file-xcpt.
			{
				fr = new FileReader(Util.getXCpt_sLastFullFileName(xcpt_sFormalID));
				br = new BufferedReader (fr);
				while ((ln=br.readLine()) != null)
				{
					if (ln.startsWith("<REFINO_NAME"))
					{
						if (lang.equals("all"))
						{
							vectorNames.add(ln); //add <nam
							ln=br.readLine();
							while (!ln.startsWith("</REFINO_NAME"))
							{
								ln.trim();
								vectorNames.add(ln);
								ln=br.readLine();
							}
							vectorNames.add(ln); //add </nam
							break;
						}

						else //read ONE lang.
						{
							ln=br.readLine();//the lang line.
							//maybe there is no lang line.
							while ( !ln.startsWith("</REFINO_NAME") )
							{
								if ( ln.startsWith("<"+lang) )
								{
									vectorNames.add(ln);//add the <lang line.
									while ( !(ln=br.readLine()).startsWith("</"+lang) )
									{
										ln.trim();
										vectorNames.add(ln);
									}
									vectorNames.add(ln);//add the </lang line.
								}
								ln=br.readLine();
							}
							break;
						}
					}
				}
			}

			else //it is an internal-xcpt.
			{
				String hfn = Util.getSrCptInt_sHostFlName(xcpt_sFormalID);
				fr = new FileReader(Util.getXCpt_sLastFullFileName(hfn));
				br = new BufferedReader(fr);

				while ((ln=br.readLine())!=null)
				{
					if (ln.startsWith("<INTxCPT"))
					{
						if (Pattern.matches("<INTxCPT FRnAME=\"[A-Za-z0-9'-_ ]+@"
																+xcpt_sFormalID+".*", ln))
						{
							ln=br.readLine();//read <nameino
							if (lang.equalsIgnoreCase("all"))
							{
								while (ln.startsWith("\t")) ln=ln.substring(1);
								vectorNames.add(ln);//add <nam line.
								while (ln.indexOf("</REFINO_NAME")==-1)
								{
									while (ln.startsWith("\t")) ln=ln.substring(1);
									ln.trim();
									vectorNames.add(ln);
									ln=br.readLine();
								}
								while (ln.startsWith("\t")) ln=ln.substring(1);
								vectorNames.add(ln);//add </nam line.
								break;
							}

							else //read ONE lang.
							{
								ln=br.readLine();//the lang line.
								//maybe there is no lang line.
								while ( ln.indexOf("</REFINO_NAME")==-1 )
								{
									if ( ln.indexOf("<"+lang)!=-1 )
									{
										while (ln.startsWith("\t")) ln=ln.substring(1);
										vectorNames.add(ln);//add the <lang line.
										while ( ln.indexOf("</"+lang)==-1 )
										{
											ln=br.readLine();
											while (ln.startsWith("\t")) ln=ln.substring(1);
											ln.trim();
											vectorNames.add(ln);
										}
										break;
									}
									ln=br.readLine();
								}
							}
						}
						break;
					}
				}
			}
			br.close();
		}
		catch (EOFException e){System.out.println("Extract.extractLgConceptNames: EOFException");}
		catch (FileNotFoundException e)	{
			JOptionPane.showMessageDialog(null, "There is NO file to extract TERMS for " +Util.getXCpt_sLastFileName(xcpt_sFormalID));
		}
		catch (IOException e){System.out.println("Extract.extractLgConceptNames: IOException");}
		return vectorNames;
	}

	/**
	 * @param name is a unique-designator of a file/internal-xcpt.
	 * @modified 2009.12.10
	 * @since
	 * @author HoKoNoUmo
	 */
	public void extractWholes (String name)
	{
		vectorWho = new Vector<String>();
		vectorWhoID = new Vector<String>();

		if (name.indexOf("#")==-1)//the xcpt is not an internal
		{
			try {
				BufferedReader br = new BufferedReader (new FileReader(Util.getXCpt_sLastFullFileName(name)));

				while ((line=br.readLine()) !=null)
				{
					if (line.startsWith("<REFINO_WHOLE"))
					{
						while (!line.startsWith("</REFINO_WHOLE"))
						{
							if (line.startsWith("<XCPT"))
							{
								String xcpt_sFormalID=Util.getXmlAttribute(line,"FRnAME");
								vectorWho.add(xcpt_sFormalID);
								String cptIDID = Util.getXCpt_sFormalID(xcpt_sFormalID);
								vectorWhoID.add(cptIDID);
							}
							line=br.readLine();
						}
						break;
					}
				}
				br.close();
			}
			catch (EOFException e)					{System.out.println("ExtractWho: EOFException");}
			catch (FileNotFoundException e) {System.out.println("ExtractWho: FNFException");}
			catch (IOException e)						{System.out.println("ExtractWho: IOException");}
		}

		else	//sbcpt IS An internal.
		{
			//IF it is an att-internal THEN its host is its whole.
			//BUT if it is spe-internal then its whole is its host whole.
			String hc = Util.getSrCptInt_sHostFlName(name);
			String hcID = Util.getXCpt_sFormalID(hc);
			String xcpt_sFormalID=Util.getXCpt_sFormalID(name);
			Vector<String> vhcWho=new Vector<String>();
			Vector<String> vhcWhoID=new Vector<String>();
			boolean found=false;

			try {
				BufferedReader br=new BufferedReader(new FileReader(Util.getXCpt_sLastFullFileName(hcID)));
				while ((line=br.readLine()) !=null)
				{

					if (line.startsWith("<REFINO_PART")) //whole=host
					{
						while (!line.startsWith("</REFINO_PART"))
						{
							if (line.startsWith("<INTxCPT"))
							{
								String cptIntFile=Util.getXmlAttribute(line,"FRnAME");
								String cptIntID= Util.getXCpt_sFormalID(cptIntFile);
								if (cptIntID.equals(xcpt_sFormalID))
								{
									vectorWho.add(hc);
									vectorWhoID.add(hcID);
									found=true;
									break;
								}
							}
							line=br.readLine();
						}
						if (found)break;
					}

					if (line.startsWith("<REFINO_WHOLE")) //find the whole host.
					{
						while (!line.startsWith("</REFINO_WHOLE"))
						{
							if (line.startsWith("<XCPT"))
							{
								String cptWFile=Util.getXmlAttribute(line,"FRnAME");
								vhcWho.add(cptWFile);
								String cptWID = Util.getXCpt_sFormalID(cptWFile);
								vhcWhoID.add(cptWID);
							}
							line=br.readLine();
						}
					}

					if (line.startsWith("<REFINO_SPECIFIC")) //whole=host whole.
					{
						while (!line.startsWith("</REFINO_SPECIFIC"))
						{
							if (line.startsWith("<INTxCPT"))
							{
								String cptIntFile=Util.getXmlAttribute(line,"FRnAME");
								String cptIntID= Util.getXCpt_sFormalID(cptIntFile);
								if (cptIntID.equals(xcpt_sFormalID))
								{
									vectorWho=vhcWho;
									vectorWhoID=vhcWhoID;
									found=true;
									break;
								}
							}
							line=br.readLine();
						}
						if (found) break;
					}

				}
				br.close();
			}
			catch (EOFException e)					{System.out.println("ExtractWho: EOFException");}
			catch (FileNotFoundException e)	{System.out.println("ExtractWho: FNFException");}
			catch (IOException e)					{System.out.println("ExtractWho: IOException");}
		}
	}

}
