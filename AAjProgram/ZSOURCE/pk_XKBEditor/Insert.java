/*
 * Insert.java - Util methods.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2000 Nikos Kasselouris
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pk_XKBEditor;

import pk_XKBManager.AAj;
import pk_Logo.*;
import pk_Util.Extract;
import pk_Util.Util;
import pk_Util.LabelFactory;
import pk_Util.Textual;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.text.Collator;
import java.util.Map;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Contains methods to insert 'elements' in AAj's-files.
 *
 * @modified 2009.10.30
 * @since 2000.01.28 (v00.01.08)
 * @author HoKoNoUmo
 */
public class Insert
{
	private String		xcpt_sFrmlName; //I want to use in methods of this class. 2001.03.12
	private String		xcpt_sFileName;
	private String		xcpt_sAuthor=AAj.kb_sAuthor;
	private boolean inserted=false;
	private String		ln = null;
	// I need it to sort strings to sort concepts
	private Collator	collator = Collator.getInstance();


	/**
	 * Creates a file-xcpt.<p>
	 * <b>INPUT</b>: 1) the formal-name, 2) the xml-definition element and
	 * 3) the xml-english-main-name element of the xcpt we want to create.
	 *
	 * @modified 2009.10.01
	 * @since 2009.10.01 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public void createXCpt_File(String frName,
														String xDef,
														String xName)
	{
		try {
			String ffileName = Util.getXCpt_sFullDir(frName) +File.separator
												+frName +".xml";
			FileWriter fw = new FileWriter(ffileName);
			BufferedWriter bw = new BufferedWriter(fw);
			xcpt_sAuthor= Util.getXmlAttribute(xName, "AUTHOR");

			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.newLine();
			bw.newLine();
			bw.write("<XCONCEPT"
							+" LASTiNTfRnUMBER=\"0\""
							+" INTEGRATED=\"no\""
							+" LASTmOD=\"" +Util.getCurrentDate()
							+"\" CREATED=\"" +Util.getCurrentDate()
							+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			bw.newLine();

			bw.write("<REFINO_DEFINITION"
							+" CREATED=\"" +Util.getCurrentDate()
							+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			bw.write(xDef);
			bw.newLine();
			bw.write("</REFINO_DEFINITION>");
			bw.newLine();
			bw.newLine();

			bw.write("<REFINO_NAME"
							+" CREATED=\"" +Util.getCurrentDate()
							+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			bw.write("<eng"
							+" CREATED=\"" +Util.getCurrentDate()
							+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			bw.write(xName);
			bw.newLine();
			bw.write("</eng>");
			bw.newLine();
			bw.write("</REFINO_NAME>");
			bw.newLine();

			bw.write("</XCONCEPT>");
			bw.close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Insert.createXCpt_File: Problem in writing");
		}

		updateTermFiles(frName, "eng");
	}


	/**
	 * Create a new UNRELATED file-xcpt given its filename and definition.
	 *
	 * @param fileName
	 *	 the name of the file of the new xcpt (always in english).
	 * @param def the definition of the xcpt.
	 * @modified 2001.06.28
	 * @since 2000.02.07
	 * @author HoKoNoUmo
	 */
	public void old_createXCpt_FileWithDef_Txt(String fileName,
//														String nmLocale,
//														String nm,
//														String typeNew,
														String def)
	{
		try {
			String ffileName = Util.getXCpt_sFullDir(fileName) +File.separator +fileName;
			FileWriter fw = new FileWriter(ffileName);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.newLine();
			bw.newLine();
			bw.write("<XCONCEPT"
							+" LASTiNTfRnUMBER=\"0\""
							+" INTEGRATED=\"no\""
							+" LASTmOD=\"" +Util.getCurrentDate() +"\""
							+" CREATED=\"" +Util.getCurrentDate() +"\""
							+" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			bw.newLine();

			bw.write("<REFINO_DEFINITION"
							+" CREATED=\"" +Util.getCurrentDate() +"\""
							+" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			bw.write(def);
			bw.newLine();
			bw.write("</REFINO_DEFINITION>");
			bw.newLine();

			bw.write("</XCONCEPT>");
			bw.close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null,"Insert.old_createXCpt_FileWithDef_Txt: Problem in writing");
		}

		//write termTxCpt_sAll.
//		updateTermFiles(fileName, "all");
	}

	/**
	 * Creates a NEW file-XmlCpt given:
	 * @param kspNew The name for the xml-file.
	 * @param cptWhole The file-name of its whole-xcpt if any.
	 * @param cptGeneric The file-name of its generic-xcpt if any.
	 * @modified 2000.06.11
	 * @since 1999.02.21
	 * @author HoKoNoUmo
	 */
	public void createXCpt_FileWithAtt(String kspNew,
																String cptPart,
																String cptWhole,
																String cptGeneric,
																String cptSpecific,
																String cptEnvironment)
	{
		try {
			String fileName = Util.getXCpt_sFullDir(kspNew)+File.separator+kspNew;
			FileWriter fw = new FileWriter(fileName);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			bw.newLine();
			bw.newLine();
			bw.write("<XCONCEPT"
							+" LASTiNTfRnUMBER=\"0\""
							+" INTEGRATED=\"no\""
							+" LASTmOD=\"" +Util.getCurrentDate() +"\""
							+" CREATED=\"" +Util.getCurrentDate() +"\""
							+" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			bw.newLine();

			bw.write("<NAME"
							+" LASTmOD=\"" +Util.getCurrentDate() +"\""
							+" CREATED=\"" +Util.getCurrentDate() +"\""
							+" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			String xcpt_sName = Textual.createNormalName(kspNew);
			bw.write(xcpt_sName);
			bw.newLine();
			bw.write("</NAME>");
			bw.newLine();
			bw.newLine();

			bw.write("<REFINO_DEFINITION"
							+" CREATED=\"" +Util.getCurrentDate() +"\""
							+" AUTHOR=\"" +xcpt_sAuthor +"\">");
			bw.newLine();
			String result = JOptionPane.showInputDialog("Give the REFINO_DEFINITION for the concept " +kspNew);
			bw.write(result);
			bw.newLine();
			bw.write("</REFINO_DEFINITION>");
			bw.newLine();
			bw.newLine();

			if (cptPart!=null)
			{
				bw.write("<REFINO_PART"
								+" CREATED=\"" +Util.getCurrentDate() +"\""
								+" AUTHOR=\"" +xcpt_sAuthor +"\">");
				bw.newLine();
				bw.write("<XCPT"
								+" FRnAME=\"" +cptPart +"\""
								+" GENERICiNHERITOR=\"...\""
								+" CREATED=\"" +Util.getCurrentDate() +"\""
								+" AUTHOR=\"" +xcpt_sAuthor +"\"/>");
				bw.newLine();
				bw.write("</REFINO_PART>");
				bw.newLine();
				bw.newLine();
			}

			if (cptWhole!=null)
			{
				bw.write("<REFINO_WHOLE"
								+" CREATED=\"" +Util.getCurrentDate() +"\""
								+" AUTHOR=\"" +xcpt_sAuthor +"\">");
				bw.newLine();
				bw.write("<XCPT"
								+" FRnAME=\"" +cptWhole +"\""
								+" GENERICiNHERITOR=\"...\""
								+" CREATED=\"" +Util.getCurrentDate() +"\""
								+" AUTHOR=\"" +xcpt_sAuthor +"\"/>");
				bw.newLine();
				bw.write("</REFINO_WHOLE>");
				bw.newLine();
				bw.newLine();
			}

			if (cptGeneric!=null)
			{
				bw.write("<REFINO_GENERIC"
								+" CREATED=\"" +Util.getCurrentDate() +"\""
								+" AUTHOR=\"" +xcpt_sAuthor +"\">");
				bw.newLine();
				bw.write("<XCPT"
							+" FRnAME=\"" +cptGeneric +"\""
							+" GENERICiNHERITOR=\"\""
							+" CREATED=\"" +Util.getCurrentDate() +"\""
							+" AUTHOR=\"" +xcpt_sAuthor +"\"/>");
				bw.newLine();
				bw.write("</REFINO_GENERIC>");
				bw.newLine();
				bw.newLine();
			}

			if (cptSpecific!=null)
			{
				bw.write("<REFINO_SPECIFIC"
								+" CREATED=\"" +Util.getCurrentDate() +"\""
								+" AUTHOR=\"" +xcpt_sAuthor +"\">");
				bw.newLine();
				bw.write("<XCPT"
							+" FRnAME=\"" +cptSpecific +"\""
							+" GENERICiNHERITOR=\"\""
							+" CREATED=\"" +Util.getCurrentDate() +"\""
							+" AUTHOR=\"" +xcpt_sAuthor +"\"/>");
				bw.newLine();
				bw.write("</REFINO_SPECIFIC>");
				bw.newLine();
				bw.newLine();
			}

			if (cptEnvironment!=null)
			{
				bw.write("<REFINO_ENVIRONMENT"
								+" CREATED=\"" +Util.getCurrentDate() +"\""
								+" AUTHOR=\"" +xcpt_sAuthor +"\">");
				bw.newLine();
				bw.write("<XCPT"
							+" FRnAME=\"" +cptEnvironment +"\""
							+" GENERICiNHERITOR=\"...\""
							+" CREATED=\"" +Util.getCurrentDate() +"\""
							+" AUTHOR=\"" +xcpt_sAuthor +"\"/>");
				bw.newLine();
				bw.write("</REFINO_ENVIRONMENT>");
			}

			bw.newLine();
			bw.write("</XCONCEPT>");

			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,"Insert.createXCpt_FileWithAtt: Problem in writing");
		}
	}


	/**
	 * Creates an term-file, given the its full-path.
	 *
	 * @modified 2006.02.01
	 * @since 2006.02.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void createTermFile(String fileTrm)
	{
		try {
			FileWriter filew = new FileWriter(fileTrm);
			BufferedWriter w = new BufferedWriter(filew);
			w.write("<?XML version=\"1.0\"?>");
			w.newLine();
			w.write("<!DOCTYPE TRMNL [");
			w.newLine();
			w.write(" <!ELEMENT TRMNL EMPTY>");
			w.newLine();
			w.write(" <!ATTLIST TRMNL TxEXP CDATA #REQUIRED>");
			w.newLine();
			w.write(" <!ATTLIST TRMNL TRMNLtYPE CDATA #REQUIRED>");
			w.newLine();
			w.write(" <!ATTLIST TRMNL XCPT CDATA #REQUIRED>");
			w.newLine();
			w.write(" <!ATTLIST TRMNL NAME CDATA #IMPLIED>");
			w.newLine();
			w.write("]>");
			w.newLine();
			w.write("<!--");
			w.newLine();
			w.write(" * @since " +Util.getCurrentDate());
			w.newLine();
			w.write(" * @author " +AAj.xcpt_sAuthor);
			w.newLine();
			w.write(" * This file has been created by the machine.");
			w.newLine();
			w.write(" * Contains the LG-CONCEPT-TERMS of the stored xConcepts.");
			w.newLine();
			w.write(" * The xConcept-files contain ONLY the lgCptNames.");
			w.newLine();
			w.write("-->");
			w.newLine();
			w.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null,
				"Insert.createTermFile: IOException\n" +e.toString(),
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}
	}


	/**
	 * Inserts an EXISTING xcpt as ATTRIBUTE in a file-xcpt.
	 * I order them by FileName.
	 * ToDo: insert it before any "REFINO" element.
	 *
	 * @param uniqueNm	A unique-name of the file-xcpt.
	 * @param attUnNm	A unique-name of the attribute-xcpt.
	 * @param attGenInh	The "generic/inheritor" information of attribute.
	 * @modified 2009.10.02
	 * @since 2009.10.02 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public void insertAttAttributeInFile(String uniqueNm,
																		String attUnNm,
																		String attGenInh,
																		String attAut)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(uniqueNm);
		String fileNew = Util.getXCpt_sFullDir(uniqueNm) +File.separator
										+"tmpInsertAttribute";
		inserted=false;
		attUnNm= Util.getXCpt_sFormalName(attUnNm);

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);

			while ((ln = br.readLine()) != null)
			{
				// insert all lines before att.
				if (!ln.startsWith("<REFINO_ATTRIBUTE"))
				{
					//set integrated=no.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))//r_att is last element
					{
						bw.newLine();
						bw.write("<REFINO_ATTRIBUTE "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +attAut +"\">");
						bw.newLine();
						if (attGenInh==null){
							bw.write("<XCPT FRnAME=\"" +attUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +attAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +attUnNm
												+"\" GENERICiNHERITOR=\"" +attGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +attAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_ATTRIBUTE>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}
				else {//line STARTS with <ratt.
					//write <ratt
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("</REFINO_ATTRIBUTE")
							&& !ln.startsWith("<REFINO")) {
						String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
						if (!(collator.compare(attUnNm,existingFrNm)>0)){
							//write the new-att before existing
							if (attGenInh==null){
								bw.write("<XCPT FRnAME=\"" +attUnNm
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +attAut +"\"/>");
							} else {
								bw.write("<XCPT FRnAME=\"" +attUnNm
													+"\" GENERICiNHERITOR=\"" +attGenInh
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +attAut +"\"/>");
							}
							bw.newLine();

							//then we write the existing xcpt line.
							bw.write(ln);
							bw.newLine();
							inserted=true;
							break;
						}
						else //write the existing xcpt
						{
							//2 cases: xcpt and xcpt_int
							if (ln.startsWith("<INTxCPT"))
							{
								bw.newLine();
								//and all lines of internal
								while (!(ln=br.readLine()).startsWith("</INTxCPT")){
									bw.write(ln);
									bw.newLine();
									ln=br.readLine();
								}
								bw.write(ln); //the </xcpt
								bw.newLine();
								ln=br.readLine();
							} else {
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
					}

					if (inserted) {
						continue;
					} else {
						if (attGenInh==null){
							bw.write("<XCPT FRnAME=\"" +attUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +attAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +attUnNm
												+"\" GENERICiNHERITOR=\"" +attGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +attAut +"\"/>");
						}
						bw.newLine();

						// write </att and continue
						bw.write(ln);
						bw.newLine();
						inserted=true;
					}
				}
			}
			br.close();
			bw.close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Insert.insertAttAttributeInFile: IOException",
														"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}


	/**
	 * Inserts an EXISTING xcpt as ENTITY in a file-xcpt.
	 * I order them by FileName.
	 *
	 * @param uniqueNm	A unique-name of the file-xcpt.
	 * @modified 2009.10.02
	 * @since 2009.10.02 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public void insertAttEntityInFile(String uniqueNm,
																		String entUnNm,
																		String entGenInh,
																		String entAut)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(uniqueNm);
		String fileNew = Util.getXCpt_sFullDir(uniqueNm)
										+File.separator +"tmpInsertEntity";
		inserted=false;
		entUnNm= Util.getXCpt_sFormalName(entUnNm);

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);

			while ((ln = br.readLine()) != null)
			{
				// insert all lines before ent.
				if (!ln.startsWith("<REFINO_ENTITY"))
				{
					//set integrated=no.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && (ln.startsWith("<REFINO_ATTRIBUTE")
										||ln.startsWith("</XCONCEPT")))//r_ent is last element
					{
						bw.newLine();
						bw.write("<REFINO_ENTITY "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +entAut +"\">");
						bw.newLine();
						if (entGenInh==null){
							bw.write("<XCPT FRnAME=\"" +entUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +entAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +entUnNm
												+"\" GENERICiNHERITOR=\"" +entGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +entAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_ENTITY>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}
				else //line STARTS with <rent.
				{
					//write <rent
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("</REFINO_ENTITY"))
					{
						String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
						if (!(collator.compare(entUnNm,existingFrNm)>0))
						{
							//write the new-ent before existing
							if (entGenInh==null){
								bw.write("<XCPT FRnAME=\"" +entUnNm
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +entAut +"\"/>");
							} else {
								bw.write("<XCPT FRnAME=\"" +entUnNm
													+"\" GENERICiNHERITOR=\"" +entGenInh
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +entAut +"\"/>");
							}
							bw.newLine();

							//then we write the existing xcpt line.
							bw.write(ln);
							bw.newLine();
							inserted=true;
							break;
						}
						else //write the existing xcpt
						{
							//2 cases: xcpt and xcpt_int
							if (ln.startsWith("<INTxCPT"))
							{
								bw.newLine();
								//and all lines of internal
								while (!(ln=br.readLine()).startsWith("</INTxCPT")){
									bw.write(ln);
									bw.newLine();
									ln=br.readLine();
								}
								bw.write(ln); //the </xcpt
								bw.newLine();
								ln=br.readLine();
							} else {
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
					}

					if (inserted) {
						continue;
					}
					else {
						if (entGenInh==null){
							bw.write("<XCPT FRnAME=\"" +entUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +entAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +entUnNm
												+"\" GENERICiNHERITOR=\"" +entGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +entAut +"\"/>");
						}
						bw.newLine();

						// write </ent and continue
						bw.write(ln);
						bw.newLine();
						inserted=true;
					}
				}

			}
			br.close();
			bw.close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Insert.insertAttEntityInFile: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}

		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}


	/**
	 * Inserts an EXISTING xcpt as PART in a file-xcpt.
	 * I order them by FileName.
	 *
	 * @param uniqueNm	A unique-name of the file-xcpt.
	 * @param attUnNm	A unique-name of the part-xcpt.
	 * @param attGenInh	The "generic/inheritor" information of attribute.
	 * @modified 2009.11.04
	 * @author HoKoNoUmo
	 */
	public void insertAttPartInFile(String uniqueNm,
															String parUnNm,
															String parGenInh,
															String parAut)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(uniqueNm);
		String fileNew = Util.getXCpt_sFullDir(uniqueNm) +File.separator
										+"tmpInsertPart";
		inserted=false;

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);


			while ((ln = br.readLine()) != null)
			{
				// insert all lines before par.
				if (!ln.startsWith("<REFINO_PART"))
				{
					//set integrated=no.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && (ln.startsWith("<REFINO_WHOLE")||ln.startsWith("<REFINO_GENERIC")
																	||ln.startsWith("<REFINO_SPECIFIC")
																	||ln.startsWith("<REFINO_ENVIRONMENT")))
					{
						bw.write("<REFINO_PART "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (parGenInh==null){
							bw.write("<XCPT FRnAME=\"" +parUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +parAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +parUnNm
												+"\" GENERICiNHERITOR=\"" +parGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +parAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_PART>");
						bw.newLine();
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
						inserted=true;
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))
					{
						bw.newLine();
						bw.write("<REFINO_PART "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (parGenInh==null){
							bw.write("<XCPT FRnAME=\"" +parUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +parAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +parUnNm
												+"\" GENERICiNHERITOR=\"" +parGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +parAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_PART>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}

				else //line STARTS with <par.
				{
					bw.write(ln);//write <par.
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("</REFINO_PART"))
					{
						//order the xcpt/intXcpt lines.
						if (ln.startsWith("<XCPT")||ln.startsWith("<INTxCPT"))
						{
							String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
							if (!(collator.compare(parUnNm,existingFrNm)>0))
							{
								if (parGenInh==null){
									bw.write("<XCPT FRnAME=\"" +parUnNm
														+"\" CREATED=\"" +Util.getCurrentDate()
														+"\" AUTHOR=\"" +parAut +"\"/>");
								} else {
									bw.write("<XCPT FRnAME=\"" +parUnNm
														+"\" GENERICiNHERITOR=\"" +parGenInh
														+"\" CREATED=\"" +Util.getCurrentDate()
														+"\" AUTHOR=\"" +parAut +"\"/>");
								}
								bw.newLine();

								//then we write the existing xcpt line.
								bw.write(ln);
								bw.newLine();
								inserted=true;
								break;
							}
							else //write the existing line
							{
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
						else //it is an empty line or belongs to a intXcpt, write it.
						{
							//don't write empty lines.
							ln.trim();
							if (ln.length()!=0)
							{
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
							else {
								ln=br.readLine();
							}
						}
					}

					if (inserted) {
						continue;
					}
					else {
						if (parGenInh==null){
							bw.write("<XCPT FRnAME=\"" +parUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +parAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +parUnNm
												+"\" GENERICiNHERITOR=\"" +parGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +parAut +"\"/>");
						}
						bw.newLine();

						// write </par and continue
						bw.write(ln);
						bw.newLine();
						inserted=true;
						continue;
					}
				}
			}

			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Insert.insertAttPartInFile: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}

	/**
	 * Inserts an EXISTING xcpt as ENVIRONMENT in an xml-file,
	 * in ascending order by ID
	 * @param uniqueNm	A unique-name of the file-xcpt.
	 * @param envUnNm	A unique-name of the attribute-xcpt.
	 * @param envGenInh	The "generic/inheritor" information of attribute.
	 * @modified 2009.11.21
	 * @since 1999.02.22
	 * @author HoKoNoUmo
	 */
	public void insertAttEnvironmentInFile(String uniqueNm,
																		String envUnNm,
																		String envGenInh,
																		String envAut)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(uniqueNm);
		String fileNew = Util.getXCpt_sFullDir(uniqueNm) +File.separator
										+"tmpInsertEnvironment";
		inserted=false;

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);


			while ((ln = br.readLine()) != null)
			{
				// insert all lines before env.
				if (!ln.startsWith("<REFINO_ENVIRONMENT"))
				{
					//set integrated=no.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))
					{
						bw.newLine();
						bw.write("<REFINO_ENVIRONMENT "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (envGenInh==null){
							bw.write("<XCPT FRnAME=\"" +envUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +envAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +envUnNm
												+"\" GENERICiNHERITOR=\"" +envGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +envAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_ENVIRONMENT>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}
				else //line STARTS with <env.
				{
					//write <env
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("</REFINO_ENVIRONMENT"))
					{
						//if LINE is empty, write it
						if (!(ln.startsWith("<XCPT")||ln.startsWith("</REFINO_ENVIRONMENT")))
						{
							bw.newLine();
							ln=br.readLine();
							if (ln.startsWith("</REFINO_ENVIRONMENT")) break;
						}

						//check the order.
						String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
						if (!(collator.compare(envUnNm,existingFrNm)>0))
						{
							if (envGenInh==null){
								bw.write("<XCPT FRnAME=\"" +envUnNm
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +envAut +"\"/>");
							} else {
								bw.write("<XCPT FRnAME=\"" +envUnNm
													+"\" GENERICiNHERITOR=\"" +envGenInh
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +envAut +"\"/>");
							}
							bw.newLine();

							//then we write the existing xcpt line.
							bw.write(ln);
							bw.newLine();
							inserted=true;
							break;
						}
						else //write the existing xcpt
						{
							bw.write(ln);
							bw.newLine();
							ln=br.readLine();
						}

					}

					if (inserted) {
						continue;
					}
					else {
						if (envGenInh==null){
							bw.write("<XCPT FRnAME=\"" +envUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +envAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +envUnNm
												+"\" GENERICiNHERITOR=\"" +envGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +envAut +"\"/>");
						}
						bw.newLine();

						// write </env and continue
						bw.write(ln);
						bw.newLine();
						inserted=true;
					}
				}

			}
			br.close();
			bw.close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Insert.insertAttEnvironmentInFile: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}

	/**
	 * Creates an Internal-xcpt and inserts it as ATTRIBUTE, alphabetically,
	 * in a file-xcpt.
	 * <b>INPUT</b>: 1)a unique-name of the host-xcpt,<br/>
	 * 2) the internal's formal-name,<br/>
	 * 3) the internal's xml-definition element and<br/>
	 * 4) the internal's xml-english-main-name element.
	 *
	 * @modified
	 * @since 2009.10.07
	 * @author HoKoNoUmo
	 */
	public void createXCpt_InternalAsAttribute(String hostUniqNm,
																					String intFrNm,
																					String intXDef,
																					String intXName)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(hostUniqNm);
		String fileNew = Util.getXCpt_sFullDir(hostUniqNm)
										+File.separator +"tmpInternalAttribute";
		inserted=false;

		try	{
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);

			while ((ln = br.readLine()) != null)
			{
				// insert all lines before att.
				if (!ln.startsWith("<REFINO_ATTRIBUTE")) {
					//set integrated=no.
					if (ln.startsWith("<XCONCEPT")) {
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))
					{
						bw.newLine();
						bw.write("<REFINO_ATTRIBUTE"
											+"\" CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						writeInternal(bw, intFrNm, intXDef, intXName);
						bw.newLine();
						bw.write("</REFINO_ATTRIBUTE>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}	else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}

				else {//line STARTS with <ratt.
					//write <ratt
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("</REFINO_ATTRIBUTE"))
					{
						//order the xcpt/intXcpt lines.
						if (ln.startsWith("<XCPT")||ln.startsWith("<INTxCPT")) {
							String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
							if (!(collator.compare(intFrNm,existingFrNm)>0)) {
								writeInternal(bw, intFrNm, intXDef, intXName);
								bw.newLine();
								//then we write the existing xcpt line.
								bw.write(ln);
								bw.newLine();
								inserted=true;
								break;
							} else { //write the existing line
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
						else //it is an empty line or belongs to a intXcpt, write it.
						{
							//don't write empty lines.
							if (ln.length()!=0) {
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							} else {
								ln=br.readLine();
							}
						}
					}

					if (inserted) {
						continue;
					}
					else {
						writeInternal(bw, intFrNm, intXDef, intXName);
						bw.newLine();
						inserted=true;

						// write </par and continue
						bw.write(ln);
						bw.newLine();
					}
				} //end par case. Then continue to read/write all the other lines.
			}
			br.close();
			bw.close();
		} catch (IOException e)	{
			JOptionPane.showMessageDialog(null, "Insert.createXCpt_InternalAsAttribute: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
		updateTermFiles(intFrNm, "eng");
	}

	/**
	 * Inserts an Internal-xcpt as part, alphabetically, to an xmlfile.
	 * @modified 2000.06.11
	 * @since 2000.02.13
	 * @author HoKoNoUmo
	 */
	public void createXCpt_InternalAsPart(String xmlFile,
																				String intFLN,
																				String intGen,
																				String intSyns,
																				String intDef)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(xmlFile);
		String fileNew = Util.getXCpt_sFullDir(xmlFile) +File.separator +"tmpInternalPart";
		inserted=false;

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);


			while ((ln = br.readLine()) != null)
			{
				// insert all lines before par.
				if (!ln.startsWith("<REFINO_PART"))
				{
					//set integrated=no.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && (ln.startsWith("<REFINO_WHOLE")||ln.startsWith("<REFINO_GENERIC")||ln.startsWith("<REFINO_SPECIFIC")||ln.startsWith("<REFINO_ENVIRONMENT")))
					{
						bw.write("<REFINO_PART "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						writeInternal(bw, intFLN, intGen, intDef);
						bw.newLine();
						bw.write("</REFINO_PART>");
						bw.newLine();
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
						inserted=true;
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))
					{
						bw.newLine();
						bw.write("<REFINO_PART "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						writeInternal(bw, intFLN, intGen, intDef);
						bw.newLine();
						bw.write("</REFINO_PART>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}

				else //line STARTS with <par.
				{
					//write <par.
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("</REFINO_PART"))
					{
						//order the xcpt/intXcpt lines.
						if (ln.startsWith("<XCPT")||ln.startsWith("<INTxCPT"))
						{
							String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
							if (!(collator.compare(intFLN,existingFrNm)>0))
							{
								writeInternal(bw, intFLN, intGen, intDef);
								bw.newLine();
								//then we write the existing xcpt line.
								bw.write(ln);
								bw.newLine();
								inserted=true;
								break;
							}
								else //write the existing line
							{
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
						else //it is an empty line or belongs to a intXcpt, write it.
						{
							//don't write empty lines.
							if (ln.length()!=0)
							{
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
							else {
								ln=br.readLine();
							}
						}
					}

					if (inserted) {
						continue;
					}
					else {
							writeInternal(bw, intFLN, intGen, intDef);
						bw.newLine();
						inserted=true;

						// write </par and continue
						bw.write(ln);
						bw.newLine();
					}
				}//end par case. Then continue to read/write all the other lines.

			}
			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Insert.createXCpt_InternalAsPart: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
		updateTermFiles(intFLN, "all");
	}

	/**
	 * @modified 2001.07.17
	 * @since 2000.02.17
	 * @author HoKoNoUmo
	 */
	public void createXCpt_InternalAsSpecific(String xmlFile,
																				String intFLN,
																				String intDef)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(xmlFile);
		String fileNew = Util.getXCpt_sFullDir(xmlFile) +File.separator +"tmpInternalSpecific";
		inserted=false;

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);

			while ((ln = br.readLine()) != null)
			{
				// insert all lines before spe.
				if (!ln.startsWith("<REFINO_SPECIFIC"))
				{
					//set integrated=no.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && ln.startsWith("<REFINO_ENVIRONMENT"))
					{
						bw.write("<REFINO_SPECIFIC "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						writeInternal(bw, intFLN, AAj.xcpt_sFileName, intDef);
						bw.newLine();
						bw.write("</REFINO_SPECIFIC>");
						bw.newLine();
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
						inserted=true;
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))
					{
						bw.newLine();
						bw.write("<REFINO_SPECIFIC "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						writeInternal(bw, intFLN, AAj.xcpt_sFileName, intDef);
						bw.newLine();
						bw.write("</REFINO_SPECIFIC>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}

				else //line STARTS with <spe.
				{
					//write <spe.
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!(ln.startsWith("<REFINO_SPECIFICdIVISION")) && !ln.startsWith("</REFINO_SPECIFIC"))
					{
						//order the xcpt/intXcpt lines.
						if (ln.startsWith("<XCPT")||ln.startsWith("<INTxCPT"))
						{
							String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
							if (!(collator.compare(intFLN,existingFrNm)>0))
							{
								writeInternal(bw, intFLN, AAj.xcpt_sFileName, intDef);
								bw.newLine();
								//then we write the existing xcpt line.
								bw.write(ln);
								bw.newLine();
								inserted=true;
								break;
							}
								else //write the existing line
							{
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
						else //it is an empty line or belongs to a intXcpt, write it.
						{
							bw.write(ln);
							bw.newLine();
							ln=br.readLine();
						}
					}

					if (inserted) {
						continue;
					}
					else {
						writeInternal(bw, intFLN, AAj.xcpt_sFileName, intDef);
						bw.newLine();
						inserted=true;

						// write </spe and continue
						bw.write(ln);
						bw.newLine();
					}
				}//end par case. Then continue to read/write all the other lines.

			}
			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Insert.createXCpt_InternalAsSpecific: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
//		updateTermFiles(intFLN, "all");
	}

	/**
	 * Inserts an EXISTING GENERIC-xcpt in an xml-file, in ascending order by ID.
	 * @modified 2001.03.06
	 * @since 1999.02.21
	 * @author HoKoNoUmo
	 */
	public void insertAttGenericInFile(String uniqueNm,
																	String genUnNm,
																	String genGenInh,
																	String genAut)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(uniqueNm);
		String fileNew = Util.getXCpt_sFullDir(uniqueNm) +File.separator +"tmpInsertGeneric";
		inserted=false;

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);


			while ((ln = br.readLine()) != null)
			{

				// insert all lines before gen.
				if (!ln.startsWith("<REFINO_GENERIC"))
				{
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && (ln.startsWith("<REFINO_SPECIFIC")||ln.startsWith("<REFINO_ENVIRONMENT")))
					{
						bw.write("<REFINO_GENERIC "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (genGenInh==null){
							bw.write("<XCPT FRnAME=\"" +genUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +genAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +genUnNm
												+"\" GENERICiNHERITOR=\"" +genGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +genAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_GENERIC>");
						bw.newLine();
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
						inserted=true;
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))
					{
						bw.newLine();
						bw.write("<REFINO_GENERIC "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (genGenInh==null){
							bw.write("<XCPT FRnAME=\"" +genUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +genAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +genUnNm
												+"\" GENERICiNHERITOR=\"" +genGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +genAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_GENERIC>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}
				else //line STARTS with gen.
				{
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("</REFINO_GENERIC"))
					{
						//if LINE is empty, write it
						if (!(ln.startsWith("<XCPT")||ln.startsWith("</REFINO_GENERIC")))
						{
							bw.newLine();
							ln=br.readLine();
							if (ln.startsWith("</REFINO_GENERIC")) break;
						}

						//check the order.
						String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
						if (!(collator.compare(genUnNm,existingFrNm)>0))
						{
							if (genGenInh==null){
								bw.write("<XCPT FRnAME=\"" +genUnNm
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +genAut +"\"/>");
							} else {
								bw.write("<XCPT FRnAME=\"" +genUnNm
													+"\" GENERICiNHERITOR=\"" +genGenInh
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +genAut +"\"/>");
							}
							bw.newLine();

							//then we write the existing xcpt line.
							bw.write(ln);
							bw.newLine();
							inserted=true;
							break;
						}
						else //write the existing xcpt
						{
							bw.write(ln);
							bw.newLine();
							ln=br.readLine();
						}

					}

					if (inserted) {
						continue;
					}
					else {
						if (genGenInh==null){
							bw.write("<XCPT FRnAME=\"" +genUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +genAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +genUnNm
												+"\" GENERICiNHERITOR=\"" +genGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +genAut +"\"/>");
						}
						bw.newLine();

						// write </gen.
						bw.write(ln);
						bw.newLine();
						inserted=true;
						continue;
					}
				}

			}
			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Editor.insertAttGenericInFile: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}

	/**
	 * It creates a SPECIFIC-DIVISION of a generic concept,
	 * on a related concept.
	 * We must know the unique-name of the related xcpt.
	 *
	 * @param xcpt	The filename/id of the xcpt we will insert a specific-division.
	 * @modified 2009.08.30
	 * @since 2000.04.08
	 * @author HoKoNoUmo
	 */
	public void insertAttSpecificDivision(String xcpt)
	{
		String xcpt_sFormalID=Util.getXCpt_sFormalID(xcpt);
		String criterionID = JOptionPane.showInputDialog(
			"INSERT the ID of the RELATED-CONCEPT you will make the SPECIFIC-DIVISION");
		if (criterionID!=null)
		{
			criterionID=criterionID.toLowerCase();
			String crtFLN = Util.getXCpt_sLastFileName(criterionID);
			String fileOld = Util.getXCpt_sLastFullFileName(xcpt);
			String fileNew = Util.getXCpt_sFullDir(xcpt) +File.separator +"tmpSpecificDivision";

			try {
				FileReader reader =new FileReader(fileOld);
				BufferedReader br =new BufferedReader(reader);
				FileWriter writer = new FileWriter(fileNew);
				BufferedWriter bw =new BufferedWriter(writer);
				inserted=false;

				while ((ln = br.readLine()) != null)
				{
					// insert all lines before spe.
					if (!ln.startsWith("<REFINO_SPECIFIC"))
					{
						if (ln.startsWith("<XCONCEPT"))
						{
							String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
							String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
							bw.write(part1 +"INTEGRATED=\"no\" " +part2);
							bw.newLine();
						}
						else if (!inserted && ln.startsWith("<REFINO_ENVIRONMENT"))
						{
							bw.write("<REFINO_SPECIFIC "
												+"CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
							bw.newLine();
							bw.write("<REFINO_SPECIFICdIVISION "
											+"ATR=\"" +crtFLN
											+"\" CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
							bw.newLine();
							bw.write("</REFINO_SPECIFICdIVISION>");
							bw.newLine();
							bw.write("</REFINO_SPECIFIC>");
							bw.newLine();
							bw.newLine();
							bw.write(ln); //the NEXT line.
							bw.newLine();
							inserted=true;
						}
						else if (!inserted && ln.startsWith("</XCONCEPT"))
						{
							bw.newLine();
							bw.write("<REFINO_SPECIFIC "
												+"CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
							bw.newLine();
							bw.write("<REFINO_SPECIFICdIVISION "
											+"ATR=\"" +crtFLN
											+"\" CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
							bw.newLine();
							bw.write("</REFINO_SPECIFICdIVISION>");
							bw.newLine();
							bw.write("</REFINO_SPECIFIC>");
							bw.newLine();
							bw.write(ln); //the NEXT line.
							bw.newLine();
						}
						else {
							bw.write(ln);
							bw.newLine();
						}
						continue;
					}

					else //line STARTS with spe.
					{
						bw.write(ln); //write <spe line.
						bw.newLine();
						ln=br.readLine();

						// run while until reach newFrNm>existingFrNm
						while (!ln.startsWith("</REFINO_SPECIFIC"))
						{
							//order the xcpt/intXcpt lines.
							if (ln.startsWith("<REFINO_SPECIFICdIVISION"))
							{
								String existingATTR=ln.substring(ln.indexOf("ATR=")+5, ln.indexOf("\"", ln.indexOf("ATR=")+6));
								if (!(collator.compare(crtFLN,existingATTR)>0))
								{
									bw.write("<REFINO_SPECIFICdIVISION "
													+"ATR=\"" +crtFLN
													+"\" CREATED=\"" +Util.getCurrentDate()
													+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
									bw.newLine();
									bw.write("</REFINO_SPECIFICdIVISION>");
									bw.newLine();
									bw.newLine(); //an empty line before next division.
									bw.write(ln); //then we write the existing xcpt line.
									bw.newLine();
									inserted=true;
									break;
								}
								else //write existing
								{
									bw.write(ln);
									bw.newLine();
									ln=br.readLine();
								}
							}
							else //it is an empty line or belongs to a intXcpt, write it.
							{
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}

						if (inserted) {
							bw.write(ln); //write </spe line.
							bw.newLine();
							continue;
						}
						else {
							bw.newLine(); //before division.
							bw.write("<REFINO_SPECIFICdIVISION "
											+"ATR=\"" +crtFLN
											+"\" CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
							bw.newLine();
							bw.write("</REFINO_SPECIFICdIVISION>");
							bw.newLine();
							inserted=true;

							bw.write(ln); //write </spe line.
							bw.newLine();
							continue;
						}
					}//starts with spe.
				}

				br.close();
				bw.close();
			}
			catch (IOException e)
			{
				JOptionPane.showMessageDialog(null, "Insert.insertAttSpecificDivision: IOException",
																		"WARNING", JOptionPane.WARNING_MESSAGE);
			}
			Util.renameFile1AndDeleteFile2(fileNew, fileOld);

			//redisplay xcpt.
			AAj.display(xcpt_sFormalID);
			if (AAj.frameEditor!=null) AAj.frameEditor.insertFile(xcpt_sFormalID);
		}
	}

	/**
	 * Inserts an EXISTING SPECIFIC-xcpt in an xml-file.
	 * ToDo: Put first divisions, Then misc. Order by formal-names.
	 *
	 * I have to take into account the internal-concepts.
	 * @modified 2009.11.22
	 * @since 1999.02.22 (v00.01.06)
	 * @author HoKoNoUmo
	 */
	public void insertAttSpecificInFile(String uniqueNm,
																	String speUnNm,
																	String speGenInh,
																	String speAut)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(uniqueNm);
		String fileNew = Util.getXCpt_sFullDir(uniqueNm) +File.separator
										+"tmpInsertSpecific";

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);
			inserted=false;

			while ((ln = br.readLine()) != null) {

				// insert all lines before spe.
				if (!ln.startsWith("<REFINO_SPECIFIC")) {
					if (ln.startsWith("<XCONCEPT")) {
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && ln.startsWith("<REFINO_ENVIRONMENT")) {
						bw.write("<REFINO_SPECIFIC "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (speGenInh==null){
							bw.write("<XCPT FRnAME=\"" +speUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +speAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +speUnNm
												+"\" GENERICiNHERITOR=\"" +speGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +speAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_SPECIFIC>");
						bw.newLine();
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
						inserted=true;
					}
					else if (!inserted && ln.startsWith("</XCONCEPT")) {
						bw.newLine();
						bw.write("<REFINO_SPECIFIC "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (speGenInh==null){
							bw.write("<XCPT FRnAME=\"" +speUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +speAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +speUnNm
												+"\" GENERICiNHERITOR=\"" +speGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +speAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_SPECIFIC>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}

				else {//line STARTS with spe.
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					// run while until reach newFrNm>existingFrNm
					while (!ln.startsWith("<REFINO_SPECIFICdIVISION") &&
								 !ln.startsWith("</REFINO_SPECIFIC")) {
						//order the xcpt/intXcpt lines.
						if (ln.startsWith("<XCPT")||ln.startsWith("<INTxCPT")) {
							String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
							if (!(collator.compare(speUnNm,existingFrNm)>0)) {
								if (speGenInh==null){
									bw.write("<XCPT FRnAME=\"" +speUnNm
														+"\" CREATED=\"" +Util.getCurrentDate()
														+"\" AUTHOR=\"" +speAut +"\"/>");
								} else {
									bw.write("<XCPT FRnAME=\"" +speUnNm
														+"\" GENERICiNHERITOR=\"" +speGenInh
														+"\" CREATED=\"" +Util.getCurrentDate()
														+"\" AUTHOR=\"" +speAut +"\"/>");
								}
								bw.newLine();
								//then we write the existing xcpt line.
								bw.write(ln);
								bw.newLine();
								inserted=true;
								break;
							}
							else {//write the existing line
								bw.write(ln);
								bw.newLine();
								ln=br.readLine();
							}
						}
						else {//it is an empty line or belongs to a intXcpt, write it.
							bw.write(ln);
							bw.newLine();
							ln=br.readLine();
						}
					}

					if (inserted) {
						continue;
					}
					else {
						if (speGenInh==null){
							bw.write("<XCPT FRnAME=\"" +speUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +speAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +speUnNm
												+"\" GENERICiNHERITOR=\"" +speGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +speAut +"\"/>");
						}
						bw.newLine();
						inserted=true;

						// write </spe.
						if (ln.startsWith("<REFINO_SPECIFICdIVISION")){
							bw.newLine();
							bw.write(ln);
							bw.newLine();
							continue;
						}
						else {
							bw.write(ln);
							bw.newLine();
							continue;
						}
					}//not inserted
				}//starts with spe.

			}
			br.close();
			bw.close();
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Insert.insertAttSpecificInFile: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}

	/**
	 * Inserts an EXISTING WHOLE in an xml-file, at the end of existings wholes.
	 * @modified 2000.04.16
	 * @author HoKoNoUmo
	 */
	public void insertAttWholeInFile(String uniqueNm,
																String whoUnNm,
																String whoGenInh,
																String whoAut)
	{
		String fileOld = Util.getXCpt_sLastFullFileName(uniqueNm);
		String fileNew = Util.getXCpt_sFullDir(uniqueNm) +File.separator
										+"tmpInsertWhole";
		inserted=false;

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);

			while ((ln = br.readLine()) != null)
			{

				// insert all lines before.
				if (!ln.startsWith("<REFINO_WHOLE"))
				{
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("INTEGRATED="));
						String part2 = ln.substring(ln.indexOf("LASTmOD="),ln.length());
						bw.write(part1 +"INTEGRATED=\"no\" " +part2);
						bw.newLine();
					}
					else if (!inserted && (ln.startsWith("<REFINO_GENERIC")
										||ln.startsWith("<REFINO_SPECIFIC")||ln.startsWith("<REFINO_ENVIRONMENT")))
					{
						bw.write("<REFINO_WHOLE "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (whoGenInh==null){
							bw.write("<XCPT FRnAME=\"" +whoUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +whoAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +whoUnNm
												+"\" GENERICiNHERITOR=\"" +whoGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +whoAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_WHOLE>");
						bw.newLine();
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
						inserted=true;
					}
					else if (!inserted && ln.startsWith("</XCONCEPT"))
					{
						bw.newLine();
						bw.write("<REFINO_WHOLE "
											+"CREATED=\"" +Util.getCurrentDate()
											+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
						bw.newLine();
						if (whoGenInh==null){
							bw.write("<XCPT FRnAME=\"" +whoUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +whoAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +whoUnNm
												+"\" GENERICiNHERITOR=\"" +whoGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +whoAut +"\"/>");
						}
						bw.newLine();
						bw.write("</REFINO_WHOLE>");
						bw.newLine();
						bw.write(ln); //the NEXT line.
						bw.newLine();
					}
					else {
						bw.write(ln);
						bw.newLine();
					}
					continue;
				}

				else //line STARTS with who.
				{
					bw.write(ln);
					bw.newLine();
					ln=br.readLine();

					while (!ln.startsWith("</REFINO_WHOLE"))
					{
						bw.write(ln);
						bw.newLine();
						ln=br.readLine();
					}

					if (inserted) {
						continue;
					}
					else {
						if (whoGenInh==null){
							bw.write("<XCPT FRnAME=\"" +whoUnNm
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +whoAut +"\"/>");
						} else {
							bw.write("<XCPT FRnAME=\"" +whoUnNm
												+"\" GENERICiNHERITOR=\"" +whoGenInh
												+"\" CREATED=\"" +Util.getCurrentDate()
												+"\" AUTHOR=\"" +whoAut +"\"/>");
						}
						bw.newLine();

						bw.write(ln);// write </who.
						bw.newLine();
						inserted=true;
						continue;
					}
				}
			}
			br.close();
			bw.close();
		}
		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Insert.insertAttWholeInFile: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}


	/** NOT_USED
	 * Inserts new lg-concept's--names to an existing xcpt from a vector.
	 * We use this method in the {@link FrameNameManagement
	 * FrameNameManagement} class. 2001.06.28
	 *
	 * @param uniqueNameOfXCpt A unique-name of the file/internal xConcept.
	 * @param xcpt_sFileNameNew	The NEW file-name of the xcpt OR null.
	 * @param vnms The vector with all the lg-concept's--names of the xcpt
	 *		in xml-form and the lg-concept element.
	 * @modified 2008.12.06
	 * @since 1999.04.24
	 * @author HoKoNoUmo
	 */
	public void insertVectorXmlNameInSConcept(String uniqueNameOfXCpt,
													String xcpt_sFileNameNew, Vector vnms)
	{
		//I suppose that xmlFileName exists.
		String xcpt_sLFName=Util.getXCpt_sLastFileName(uniqueNameOfXCpt);
		String xcpt_sDir = Util.getXCpt_sFullDir(xcpt_sLFName);
		String fileOld=null;
		String fileNew=null;
		FileReader reader = null;
		BufferedReader br = null;
		FileWriter writer = null;
		BufferedWriter bw = null;
		if (xcpt_sFileNameNew==null) fileNew = xcpt_sDir +File.separator +"tmpVectorXmlName";
		else fileNew = xcpt_sDir +File.separator +xcpt_sFileNameNew;

		try {
			if (xcpt_sLFName.indexOf("#")==-1)	 //xcpt is a 'file' one.
			{
				fileOld = xcpt_sDir +File.separator +xcpt_sLFName;
				reader =new FileReader(fileOld);
				br =new BufferedReader(reader);
				writer = new FileWriter(fileNew);
				bw =new BufferedWriter(writer);

				while ((ln = br.readLine()) != null)
				{
					if (ln.startsWith("<XCONCEPT"))
					{
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();
					}
						else if (ln.startsWith("<REFINO_NAME"))
					{
							while (!ln.startsWith("</REFINO_NAME"))
						{
								ln=br.readLine();
							}
							//write the new termTxCpt_sAll.
						for (int i=0; i<vnms.size(); i++)
						{
							bw.write((String)vnms.elementAt(i));
							bw.newLine();
						}
						inserted=true;
						continue;
						}

					else if (ln.startsWith("<REFINO_DEFINITION")&&!inserted)
					{
						//there is no <synonym element.
						//write the new termTxCpt_sAll.
						for (int i=0; i<vnms.size(); i++)
						{
							bw.write((String)vnms.elementAt(i));
							bw.newLine();
						}
						bw.newLine(); //put an extra blank line
						bw.write(ln); //write the <def line.
						bw.newLine();
						inserted=true;
					}

					else {
						bw.write(ln);
						bw.newLine();
					}
				}
				br.close();
				bw.close();
			}

			else //xcpt is an internal xcpt.
			{
				fileOld = xcpt_sDir +File.separator
								+Util.getSrCptInt_sHostFlName(xcpt_sLFName);
				reader =new FileReader(fileOld);
				br =new BufferedReader(reader);
				writer = new FileWriter(fileNew);
				bw =new BufferedWriter(writer);

				while ((ln = br.readLine()) != null)
				{
					// insert all lines before and after internal.
					if (ln.indexOf("FRnAME=\"" +xcpt_sLFName)==-1)
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
						ln=br.readLine(); //ALWAYS <REFINO_NAME

						while (ln.indexOf("</REFINO_NAME")==-1)
						{
							ln=br.readLine();
						}
						//write the new termTxCpt_sAll.
						for (int i=0; i<vnms.size(); i++)
						{
							bw.write("\t"+vnms.elementAt(i));
							bw.newLine();
						}
						continue;
					}
				}
				br.close();
				bw.close();
			}
		}

		catch (IOException e)
		{
			JOptionPane.showMessageDialog(null, "Insert.insertXmlNameVectorInSConcept: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		//rename files
		File f1 = new File(fileOld);
		f1.delete();
		if (xcpt_sFileNameNew==null)
		{
			File f2 = new File(fileNew);
			f2.renameTo(new File(fileOld));
		}

		updateTermFiles(xcpt_sLFName, "all");//termTxCpt_sAll modified then update.
	}


	/**
	 * Inserts new lg-concept's--names in ONE language, in an existing xcpt
	 * from a vector in xml-format plus the lng element.
	 * We use this method in the {@link FrameNameManagement
	 * FrameNameManagement} class.<br/>
	 * The insertion preserves the order of languages: kml, eng, eln, epo
	 *
	 * @param uniqueNameOfXCpt A unique-name of the file/internal-xcpt.
	 * @param lng The language of the lg-concept's--names.
	 * @param vnms The vector with all the lg-concept's--names of the xcpt
	 *					in xml-format and the 'lng' element.
	 * @modified 2008.03.24
	 * @since 2008.03.24 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void insertVectorXmlNameLngInXConcept(String uniqueNameOfXCpt,
																				String lng, Vector vnms)
	{
		String xcpt_sLFName=Util.getXCpt_sLastFileName(uniqueNameOfXCpt);
		String xcpt_sDir = Util.getXCpt_sFullDir(uniqueNameOfXCpt);
		String fileOld=null;
		String fileNew=xcpt_sDir +File.separator +"tmpVectorXNameLng";
		FileReader reader = null;
		BufferedReader br = null;
		FileWriter writer = null;
		BufferedWriter bw = null;

		try {
			if (xcpt_sLFName.indexOf("#")==-1)	 //xcpt is a 'file' one.
			{
				fileOld = xcpt_sDir +File.separator +xcpt_sLFName;
				reader =new FileReader(fileOld);
				br =new BufferedReader(reader);
				writer = new FileWriter(fileNew);
				bw =new BufferedWriter(writer);

				while ((ln=br.readLine()) != null)
				{
					if (ln.startsWith("<XCONCEPT")||ln.startsWith("<REFINO_NAME")) {
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();
					}

					else if (ln.startsWith("<kml")){
						if (lng.equals("kml")){
							while (!(ln=br.readLine()).startsWith("</"+lng))
							{}
							for (int i=0; i<vnms.size(); i++) {
								bw.write((String)vnms.elementAt(i));
								bw.newLine();
							}
							inserted=true;
						}
						else {//insert <kml
							bw.write(ln);
							bw.newLine();
						}
					}

					else if (ln.startsWith("<eng")){
						if (lng.equals("kml")&&!inserted){
							for (int i=0; i<vnms.size(); i++) {
								bw.write((String)vnms.elementAt(i));
								bw.newLine();
							}
							inserted=true;
							bw.write(ln);//insert <eng
							bw.newLine();
						}
						else if (lng.equals("eng")){
							while (!(ln=br.readLine()).startsWith("</"+lng))
							{}
							for (int i=0; i<vnms.size(); i++) {
								bw.write((String)vnms.elementAt(i));
								bw.newLine();
							}
							inserted=true;
						}
						else {//insert <eng
							bw.write(ln);
							bw.newLine();
						}
					}

					else if (ln.startsWith("<eln")){
						if ((lng.equals("kml")&&!inserted)
							||(lng.equals("eng")&&!inserted)){
							for (int i=0; i<vnms.size(); i++) {
								bw.write((String)vnms.elementAt(i));
								bw.newLine();
							}
							inserted=true;
							bw.write(ln);//insert <eng
							bw.newLine();
						}
						else if (lng.equals("eln")){
							while (!(ln=br.readLine()).startsWith("</"+lng))
							{}
							for (int i=0; i<vnms.size(); i++) {
								bw.write((String)vnms.elementAt(i));
								bw.newLine();
							}
							inserted=true;
						}
						else {//insert <eng
							bw.write(ln);
							bw.newLine();
						}
					}

					else if (ln.startsWith("<epo")){
						if ((lng.equals("kml")&&!inserted)
							||(lng.equals("eng")&&!inserted)
							||(lng.equals("eln")&&!inserted)){
							for (int i=0; i<vnms.size(); i++) {
								bw.write((String)vnms.elementAt(i));
								bw.newLine();
							}
							inserted=true;
							bw.write(ln);//insert <epo
							bw.newLine();
						}
						else if (lng.equals("epo")){
							while (!(ln=br.readLine()).startsWith("</epo"))
							{}
							for (int i=0; i<vnms.size(); i++) {
								bw.write((String)vnms.elementAt(i));
								bw.newLine();
							}
							inserted=true;
						}
						else {//insert <epo
							bw.write(ln);
							bw.newLine();
						}
					}

					else if (ln.startsWith("</REFINO_NAME")&&!inserted){
						for (int i=0; i<vnms.size(); i++) {
							bw.write((String)vnms.elementAt(i));
							bw.newLine();
						}
						inserted=true;
						bw.write(ln);//insert </nam
						bw.newLine();
					}

					else {
						bw.write(ln);
						bw.newLine();
					}
				}
			}

			else //xcpt is an internal xcpt.
			{
				fileOld = xcpt_sDir +File.separator
								+Util.getSrCptInt_sHostFlName(xcpt_sLFName);
				reader =new FileReader(fileOld);
				br =new BufferedReader(reader);
				writer = new FileWriter(fileNew);
				bw =new BufferedWriter(writer);

				while ((ln = br.readLine()) != null)
				{
					// insert all lines before/after internal.
					if (ln.indexOf("FRnAME=\"" +xcpt_sLFName)==-1)
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
						ln=br.readLine(); //read <REFINO_NAME. ALWAYS the creation of xcpt creates syn element.2001.05.10
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();

						while ((ln = br.readLine()).indexOf("</REFINO_NAME")==-1)
						{
							if (ln.indexOf("<kml")!=-1){
								if (lng.equals("kml")){
									while ((ln=br.readLine()).indexOf("</"+lng)==-1)
									{}
									for (int i=0; i<vnms.size(); i++) {
										bw.write("\t"+(String)vnms.elementAt(i));
										bw.newLine();
									}
									inserted=true;
								}
								else {//insert <kml
									bw.write(ln);
									bw.newLine();
								}
							}

							else if (ln.indexOf("<eng")!=-1){
								if (lng.equals("kml")&&!inserted){
									for (int i=0; i<vnms.size(); i++) {
										bw.write("\t"+(String)vnms.elementAt(i));
										bw.newLine();
									}
									inserted=true;
									bw.write(ln);//insert <eng
									bw.newLine();
									break;
								}
								else if (lng.equals("eng")){
									while ((ln=br.readLine()).indexOf("</"+lng)==-1)
									{}
									for (int i=0; i<vnms.size(); i++) {
										bw.write("\t"+(String)vnms.elementAt(i));
										bw.newLine();
									}
									inserted=true;
									break;
								}
								else {//insert <eng
									bw.write(ln);
									bw.newLine();
								}
							}

							else if (ln.indexOf("<eln")!=-1){
								if ((lng.equals("kml")&&!inserted)
									||(lng.equals("eng")&&!inserted)){
									for (int i=0; i<vnms.size(); i++) {
										bw.write("\t"+(String)vnms.elementAt(i));
										bw.newLine();
									}
									inserted=true;
									bw.write(ln);//insert <eng
									bw.newLine();
								}
								else if (lng.equals("eln")){
									while ((ln=br.readLine()).indexOf("</"+lng)==-1)
									{}
									for (int i=0; i<vnms.size(); i++) {
										bw.write("\t"+(String)vnms.elementAt(i));
										bw.newLine();
									}
									inserted=true;
								}
								else {//insert <eng
									bw.write(ln);
									bw.newLine();
								}
							}

							else if (ln.indexOf("<epo")!=-1){
								if ((lng.equals("kml")&&!inserted)
									||(lng.equals("eng")&&!inserted)
									||(lng.equals("eln")&&!inserted)){
									for (int i=0; i<vnms.size(); i++) {
										bw.write("\t"+(String)vnms.elementAt(i));
										bw.newLine();
									}
									inserted=true;
									bw.write(ln);//insert <epo
									bw.newLine();
								}
								else if (lng.equals("epo")){
									while ((ln=br.readLine()).indexOf("</"+lng)==-1)
									{}
									for (int i=0; i<vnms.size(); i++) {
										bw.write("\t"+(String)vnms.elementAt(i));
										bw.newLine();
									}
									inserted=true;
								}
								else {//insert <epo
									bw.write(ln);
									bw.newLine();
								}
							}
						}

						if (!inserted){
							for (int i=0; i<vnms.size(); i++) {
								bw.write("\t"+(String)vnms.elementAt(i));
								bw.newLine();
							}
							bw.write(ln);//insert </nam
							bw.newLine();
						}
					}
				}
			}
			br.close();
			bw.close();
		}

		catch (IOException e) {
			JOptionPane.showMessageDialog(null,
				"Insert.insertVectorXmlNameLngInXConcept: IOException",
				"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);

		updateTermFiles(xcpt_sLFName, lng);//termTxCpt_sAll modified then update.
	}


	/**
	 * Inserts a LG-CONCEPT-TERM in an term-file.<br/>
	 *
	 * @param eks		The expresetro of the term.
	 * @param typeNew		The type of the lg-concept's--term
	 * @param xcpt_sFrmlName The formalName of the xcpt-denoted.
	 * @param termTxCpt_sName The lg-concept's--name of the lg-concept's--term.
	 * @param mu The mostused attribute of term.
	 * @param lng The language of the terms.
	 * @modified 2006.10.16
	 * @since 2006.02.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void insertTermInTermFile(String eks, String typeNew,
														String xcpt_sFrmlName, String termTxCpt_sName,
														String mu, String lng)
	{
		Util.log(eks+", "+typeNew);
		String fileNew = AAj.AAj_sDir +"tmpInsertTerm";
		String fileTrm="";
		if (mu.equals("1"))
			fileTrm=AAj.AAj_sDir +"AAjKB" +File.separator
							+"AAjINDEXES" +File.separator
							+lng +File.separator +"index_terminal_"+lng+".xml";
		else
			fileTrm=Util.getFullTermFile(eks, lng);
		File f = new File(fileTrm);
		if (!f.exists()) createTermFile(fileTrm);

		//addLng
		if (lng.equals("eln"))
			collator = Collator.getInstance(new Locale("el", "GR"));

		try {
			FileReader reader =new FileReader(fileTrm);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);
			inserted=false;

			while ((ln=br.readLine()) != null)
			{
				//first write the non TRM lines
				if (!ln.startsWith("<TRMNL"))
				{
					bw.write(ln);
					bw.newLine();
				}
				else //line starts with <TRMNL
				{
					String expFound=Util.getXmlAttribute(ln,"TxEXP");

					//CASE1: expFound<expNew ==> write found & read next line
					if (collator.compare(expFound,eks)<0)
					{
						bw.write(ln);
						bw.newLine();
						continue; //read next line
					}

					//CASE2: expFound=expNew
					else if (collator.compare(expFound,eks)==0)
					{
						String typeFound=Util.getXmlAttribute(ln,"TRMNLtYPE");
						String kspFound=Util.getXmlAttribute(ln,"XCPT");
						String fmlIDFound=Util.getXCpt_sFormalID(kspFound);
						String fmlIDNew=Util.getXCpt_sFormalID(xcpt_sFrmlName);
						String narFound=Util.getXmlAttribute(ln,"NAME");

						//CASE2a: expFound=expNew & tpFound<tpNew
						//					==> write found, read next line
						if (collator.compare(typeFound,typeNew)<0)//aa<xx
						{
							bw.write(ln);
							bw.newLine();
							continue;
						}
						//CASE2b: expFound=expNew & tpFound=tpNew ==>
						else if (collator.compare(typeFound,typeNew)==0)
						{
							//CASE2b1: expFound=expNew & tpFound=tpNew &
							//					idFound<idNew
							//					==> write found, read next line
							if (collator.compare(fmlIDFound,fmlIDNew)<0)//
							{
								bw.write(ln);
								bw.newLine();
								continue;
							}
							//CASE2b2: expFound=expNew & tpFound=tpNew &
							//					idFound=idNew
							//					==> write old, stop reading,	ppp
							//
							else if (collator.compare(fmlIDFound,fmlIDNew)==0)
							{
								System.out.println("TERM <" +eks +"> of " +xcpt_sFrmlName
												+" has same TYPE and ID with existing-type.");
								bw.write(ln);
								bw.newLine();
								inserted=true;
								break; //stop reading lines
							}
							//CASE2b3: expFound=expNew & tpFound=tpNew &
							//					idFound>idNew
							//					==> new, old, stop reading
							else if (collator.compare(fmlIDFound,fmlIDNew)>0)
							{
								bw.write("<TRMNL TxEXP=\""+eks
												+"\" TRMNLtYPE=\"" +typeNew
												+"\" XCPT=\"" +xcpt_sFrmlName
												+"\" NAME=\"" +termTxCpt_sName
												+"\"/>");
								bw.newLine();
								bw.write(ln); //inset the existing line
								bw.newLine();
								inserted=true;
								break;
							}
						}
						//CASE2c: expFound=expNew & tpFound>tpNew
						//					==> write new/found, stop reading
						else if (collator.compare(typeFound,typeNew)>0)
						{
//System.out.println(typeFound+">"+typeNew);//xxx>aaa, 7>1
							bw.write("<TRMNL TxEXP=\""+eks
											+"\" TRMNLtYPE=\"" +typeNew
											+"\" XCPT=\"" +xcpt_sFrmlName
											+"\" NAME=\"" +termTxCpt_sName
											+"\"/>");
							bw.newLine();
							bw.write(ln); //inset the existing line
							bw.newLine();
							inserted=true;
							break;//don't read other line.
						}
					}

					//CASE3: expFound-zzz>expNew-aaa
					//				==> write new/found, stop reading
					else if (collator.compare(expFound,eks)>0)
					{
						bw.write("<TRMNL TxEXP=\""+eks
										+"\" TRMNLtYPE=\"" +typeNew
										+"\" XCPT=\"" +xcpt_sFrmlName
										+"\" NAME=\"" +termTxCpt_sName
										+"\"/>");
						bw.newLine();
						bw.write(ln);//insert current line
						bw.newLine();
						inserted=true;
						break;
					}
				}
			}

			if (inserted==false)
			{
				bw.write("<TRMNL TxEXP=\""+eks
								+"\" TRMNLtYPE=\"" +typeNew
								+"\" XCPT=\"" +xcpt_sFrmlName
								+"\" NAME=\"" +termTxCpt_sName
								+"\"/>");
				bw.newLine();
			}
			else //inserted: read the rest lines and add.
			{
				while ((ln=br.readLine()) != null)//insert all other lines.
				{
					bw.write(ln);
					bw.newLine();
				}
			}
			br.close();
			bw.close();
		}
		catch (IOException e){
			System.out.println("Insert.insertTermInTermFile: IOException"
												+e.toString());
		}
		File f1 = new File(fileTrm);
		f1.delete();
		File f2 = new File(fileNew);
		f2.renameTo(new File(fileTrm));
	}


	/**
	 * Inserts alternative-terms (=same individuals)of the form
	 * trm1|trm2 in an term-file.<br/>
	 *
	 * @param eks The alternative-lg-concept's--terms of same type.
	 * @param nmrType The type of the terms.
	 * @param xcpt_sFrmlName The formalName of the xcpt denoted.
	 * @param termTxCpt_sName The first-term of these terms
	 * @param mu The mostused attribute of term.
	 * @param lng The language of the terms.
	 * @modified 2006.10.16
	 * @since 2006.02.07 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void insertAltTermsInTermFile(String eks, String nmrType,
				String xcpt_sFrmlName, String termTxCpt_sName, String mu, String lng)
	{
		if (eks.indexOf("|")==-1) //one tx_expression
		{
			insertTermInTermFile(eks,nmrType,xcpt_sFrmlName,termTxCpt_sName,mu,lng);
		}

		else //more than one same type tx_expressions
		{
			StringTokenizer st = new StringTokenizer(eks, "|");
			int t=st.countTokens();
			String b[]= new String[t];
			for(int i=0; i<t; i++)
				b[i]=st.nextToken();
			LabelFactory lf = new LabelFactory();
			String lbls="a";
			for (int j=0; j<b.length; j++)
			{
				lbls=lf.getNextLabel();
				insertTermInTermFile(b[j],nmrType+lbls,xcpt_sFrmlName,termTxCpt_sName,mu,lng);
			}
		}
	}


	/**
	 * Removes an internal-xConcept.
	 * Adds the empty internalNumber to LASTiNTfRnUMBER. ex: 8,3 or 8,3,5
	 * @param intFile is the 'filename/id' of the internal we want to remove.
	 * @modified 2000.05.15
	 * @since 2000.02.17
	 * @author HoKoNoUmo
	 */
	public void removeInternal(String intFile)
	{
		//I suppose that xmlFileName exists.
		String hostFileName = Util.getSrCptInt_sHostFlName(intFile);
		String path=Util.getXCpt_sFullDir(hostFileName) +File.separator;
		String fileOld = path +hostFileName;
		String fileNew = path +"tmpRemoveInternal";

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);

			while ((ln = br.readLine()) != null)
			{
				// insert all lines before internal.
				if (!ln.startsWith("<INTxCPT"))
				{
					//set lastmodified.
					if (ln.startsWith("<XCONCEPT"))
					{
						String part1 = ln.substring(0, ln.indexOf("\"",ln.indexOf("LASTiNTfRnUMBER=")+11));
						String part2 = ln.substring(ln.indexOf("INTEGRATED"), ln.indexOf("LASTmOD="));
						String part3 = ln.substring(ln.indexOf("CREATED="),ln.length());
						String fnn=Util.getXCpt_sFormalNumber(intFile);
						String fnNumber2=fnn.substring(fnn.indexOf("#")+1,fnn.length());
						bw.write(part1 +"," +fnNumber2 +"\" " +part2
										+"LASTmOD=\"" +Util.getCurrentDate() +"\" " +part3);
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
					if(ln.indexOf("FRnAME=\""+intFile)!=-1)//this is the internal we want.
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
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Insert.removeInternal: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}


	/**
	 * Removes ONE language-lg-concept's--name from the sr-concept-file and all its
	 * INDIVIDUALS from term-files.
	 *
	 * @param xmlNmLng	The xmlNameConceptLgLango we want to remove from sr-concept-file.
	 * @param uniqueNameOfXCpt	A unique-name of the xcpt denoted.
	 * @modified 2008.03.27
	 * @since 2008.03.27 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeNameConceptLgLango(String uniqueNameOfXCpt, String xmlNmLng, String lng)
	{
		//we must remove ONE language-lg-concept's--name
		String lgCpt = xmlNmLng.substring(0, xmlNmLng.indexOf(" "));//<namoNoun
		String eks=Util.getXmlAttribute(xmlNmLng, "TxEXP");
		String xcpt_sLFName = Util.getXCpt_sLastFileName(uniqueNameOfXCpt);
		String xcpt_sDir = Util.getXCpt_sFullDir(uniqueNameOfXCpt);
		String fileOld=null;
		String fileNew=xcpt_sDir +File.separator +"tmpRemoveName";
		FileReader reader = null;
		BufferedReader br = null;
		FileWriter writer = null;
		BufferedWriter bw = null;

		try {
			if (xcpt_sLFName.indexOf("#")==-1)	 //xcpt is a 'file' one.
			{
				fileOld = xcpt_sDir +File.separator +xcpt_sLFName;
				reader =new FileReader(fileOld);
				br =new BufferedReader(reader);
				writer = new FileWriter(fileNew);
				bw =new BufferedWriter(writer);

				while ((ln=br.readLine()) != null)
				{
					if (ln.startsWith("<XCONCEPT")||ln.startsWith("<REFINO_NAME"))
					{
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();
						continue;
					}

					else if (ln.startsWith("<"+lng))
					{
						String lnLng=Util.setDateLastMod(ln);
						int nmrN=0;
//						bw.write(Util.setDateLastMod(ln));//write <lng
//						bw.newLine();
						while (!(ln=br.readLine()).startsWith("</"+lng))
						{
							//if notfound, write
							if (ln.startsWith(lgCpt)
								&& ln.indexOf("TxEXP=\""+eks+"\"")!=-1){}
							else {
								if (nmrN==0){
									bw.write(lnLng);
									bw.newLine();
									bw.write(ln);
									bw.newLine();
									nmrN=nmrN+1;
								}
								else {
									bw.write(ln);
									bw.newLine();
									nmrN=nmrN+1;
								}
							}
						}
						if (nmrN!=0){
							bw.write(ln);//write </lng
							bw.newLine();
						}
						continue;
					}

					else {
						bw.write(ln);
						bw.newLine();
					}
				}
				br.close();
				bw.close();
			}

			else //xcpt is an internal xcpt.
			{
				fileOld = xcpt_sDir +File.separator +Util.getSrCptInt_sHostFlName(xcpt_sLFName);
				reader =new FileReader(fileOld);
				br =new BufferedReader(reader);
				writer = new FileWriter(fileNew);
				bw =new BufferedWriter(writer);

				while ((ln = br.readLine()) != null)
				{
					// insert all lines before/after internal.
					if (ln.indexOf("FRnAME=\"" +xcpt_sLFName)==-1)
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
						ln=br.readLine(); //read <REFINO_NAME. ALWAYS the creation of xcpt creates syn element.2001.05.10
						bw.write(Util.setDateLastMod(ln));
						bw.newLine();

						//read and write next lines until <lng
						while ((ln = br.readLine()).indexOf("<"+lng)==-1)
						{
							bw.write(ln);
							bw.newLine();
						}

						//only read next lines until </lng
						while ((ln = br.readLine()).indexOf("</"+lng)==-1)
						{
							//if notfound, write
							if (ln.startsWith(lgCpt)
								&& ln.indexOf("TxEXP=\""+eks+"\"")!=-1){}
							else {
								bw.write(ln);
								bw.newLine();
							}
						}
						bw.write(ln);//write </lng
						bw.newLine();
						continue;
					}
				}
				br.close();
				bw.close();
			}
		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Insert.removeNameConceptLgLango: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
		//toDo: remove instances from term-files
		if (lng.equals("kml")){
			//removeTermFromTermFilesKml(uniqueNameOfXCpt, xmlNmLng);
		} else if (lng.equals("eng")) {
			removeTermFromTermFilesEng(uniqueNameOfXCpt, xmlNmLng);
		} else if (lng.equals("eln")) {
			removeTermFromTermFilesEln(uniqueNameOfXCpt, xmlNmLng);
		} else if (lng.equals("epo")){
			//removeTermFromTermFilesEpo(uniqueNameOfXCpt, xmlNmLng);
		}
		//addLng
	}


	/**
	 *
	 * @modified 2008.03.31
	 * @since 2008.03.31 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeTermFromTermFilesEng(String uniqueNameOfXCpt, String xmlNmLng)
	{
		xcpt_sFrmlName=Util.getXCpt_sFormalName(uniqueNameOfXCpt);

		String eks=Util.getXmlAttribute(xmlNmLng, "TxEXP");
		String rl=Util.getXmlAttribute(xmlNmLng, "TRMrULE");
		String mu=Util.getXmlAttribute(xmlNmLng, "MOSTuSED");

		String ix = "a"; //the first leter of lg-concept's--term.
		String idxd=AAj.AAj_sDir +"AAjKB" +File.separator
							+"AAjINDEXES" +File.separator
							+"eng" +File.separator +"index_term_eng_";
		String fileTrm=idxd;

		if (xmlNmLng.startsWith("<Name_NounCase"))
		{
			TermTxNounCaseEng nen=new TermTxNounCaseEng(eks);
			String nmrs = nen.getTermsOfTxConceptIfRuleOnly(rl);
			//The nameNoun is a string of 4 coma delimited termTxCpt_sAll.
			//We index the first 2, because the pozesiveros
			//can be found from nominatives. 2006.02.02
			String a[] = nmrs.split(",");
			for(int n=0; n<2; n++)
			{
				if (!a[n].startsWith("-"))
					removeNmrAltFromFileTerm(a[n], "trmNnCsNm"+String.valueOf(n+1),
															xcpt_sFrmlName, "eng", mu);
			}
		}
		else if (xmlNmLng.startsWith("<Name_NounAdjective"))
		{
			//xConcept: <Name_NounAdjective TxEXP="Concept" TRMrULE="rlEngTrmNnAj11"
			//dez:<TRM TxEXP="concept" XCPT=\""hknu.meta-1" TYPE="trmNnAj3"/>
			//termTxCpt_sAll: 3 cases
			TermTxNounAdjectiveEng nen = new TermTxNounAdjectiveEng(eks);
			String nmrs = nen.getTermsOfTxConceptIfRuleOnly(rl);
			String a[] = nmrs.split(",");
			for(int n=0; n<3; n++)
			{
				if (!a[n].startsWith("-"))
					removeNmrAltFromFileTerm(a[n], "trmNnAj"+String.valueOf(n+1),
															xcpt_sFrmlName, "eng", mu);
			}
		}
		else if (xmlNmLng.startsWith("<Name_NounAdverb"))
		{
			//termTxCpt_sAll: 1.
			removeNmrFromFileTerm(eks, "nmAdv1", xcpt_sFrmlName, "eng", mu);
		}
		else if (xmlNmLng.startsWith("<Name_Verb"))
		{
			//xConcept: <Name_Verb TxEXP="Concept" TRMrULE="rlEngTrmVrb11"
			//dez:<TRM TxEXP="concept" XCPT=\""hknu.meta-1" TYPE="trmNnCsNm1"/>
			//termTxCpt_sAll: 5 cases
			TermTxVerbEng ven=new TermTxVerbEng(eks);
			String nmrs = ven.getTermsOfTxConceptIfRuleOnly(rl);
			String a[] = nmrs.split(",");
			for(int n=0; n<5; n++)
			{
				if (!a[n].startsWith("-"))
					removeNmrAltFromFileTerm(a[n], "trmVrb"+String.valueOf(n+1),
															xcpt_sFrmlName, "eng", mu);
			}
		}
		else if (xmlNmLng.startsWith("<Name_Conjunction"))
			removeNmrFromFileTerm(eks, "trmCnj",xcpt_sFrmlName, "eng", mu);
		else if (xmlNmLng.startsWith("<Name_Short"))
			removeNmrFromFileTerm(eks, "nmLtr",xcpt_sFrmlName, "eng", mu);
		else if (xmlNmLng.startsWith("<Name_Symbol"))
			removeNmrFromFileTerm(eks, "nmSbr",xcpt_sFrmlName, "eng", mu);
		else if (xmlNmLng.startsWith("<Name_NounSpecialCase"))
		{
			//ppp I changed this 2006.10.07
			//english tx_special_nouns are special-expressions
			//they are indexed in the terminal files.
		}
		else
			JOptionPane.showMessageDialog(null,"Insert.removeTermFromTermFilesEng: unknown lg-concept's--term");
	}


	/**
	 *
	 * @modified 2008.04.06
	 * @since 2008.04.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeTermFromTermFilesEln(String uniqueNameOfXCpt, String xmlNmLng)
	{
		xcpt_sFrmlName=Util.getXCpt_sFormalName(uniqueNameOfXCpt);

		String eks=Util.getXmlAttribute(xmlNmLng, "TxEXP");
		String rl=Util.getXmlAttribute(xmlNmLng, "TRMrULE");
		String mu=Util.getXmlAttribute(xmlNmLng, "MOSTuSED");

		if (xmlNmLng.startsWith("<Name_NounCase"))
		{
			TermTxNounCaseEln nen=new TermTxNounCaseEln(eks);
			String nmrs = nen.getTermsOfTxConceptIfRuleOnly(rl);
			//The nameNoun is a string of >>>8<<< coma delimited termTxCpt_sAll.
			//The first shows the gender.
			String a[] = nmrs.split(",");

			if (a[0].equals("ο"))
			{
				for(int n=1; n<8; n++)
				{
					if (!a[n].startsWith("-"))
						removeNmrAltFromFileTerm(a[n], "trmNnCsA"+String.valueOf(n),
																xcpt_sFrmlName, "eln", mu);
				}
			}
			else if (a[0].equals("η"))
			{
				//we don't index the 4=vokativero which is always equal to 1
				for(int n=1; n<8; n++)
				{
					if (!a[n].startsWith("-")&&n!=4)
					//ALL feminines have the 4=1
					//to save space we don't write.
						removeNmrAltFromFileTerm(a[n], "trmNnCb"+String.valueOf(n),
																xcpt_sFrmlName, "eln", mu);
				}
			}
			else if (a[0].equals("το"))
			{
				//we don't index the 3,4,7
				for(int n=1; n<7; n++)
				{
					if (!a[n].startsWith("-")&&n!=3&&n!=4)
					//ALL neuters have 3,4=1 and 7=5
					//to save space we don't write.
						removeNmrAltFromFileTerm(a[n], "trmNnCc"+String.valueOf(n),
																xcpt_sFrmlName, "eln", mu);
				}
			}
		}
		else if (xmlNmLng.startsWith("<Name_NounAdjective"))
		{
				//adjective-terms: 17x3=51 cases
				TermTxNounAdjectiveEln nen = new TermTxNounAdjectiveEln(eks);
				String nmrs = nen.getTermsOfTxConceptIfRuleOnly(rl);
				//1a#1b#1c#2a#2b#2c#3a#3b#3c
				String g[] = nmrs.split("#");
				String g1[] = g[0].split(",");
				String g2[] = g[1].split(",");
				String g3[] = g[2].split(",");
				String g4[] = g[3].split(",");
				String g5[] = g[4].split(",");
				String g6[] = g[5].split(",");
				String g7[] = g[6].split(",");
				String g8[] = g[7].split(",");
				String g9[] = g[8].split(",");
				for(int n=1; n<8; n++)//masculines
				{
					if (!g1[n].startsWith("-"))
						removeNmrAltFromFileTerm(g1[n],"trmNnAj1a"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
					if (!g4[n].startsWith("-"))
						removeNmrAltFromFileTerm(g4[n],"trmNnAj2a"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
					if (!g7[n].startsWith("-"))
						removeNmrAltFromFileTerm(g7[n],"trmNnAj3a"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
				}
				for(int n=1; n<8; n++)//feminines
				{
					if (!g2[n].startsWith("-")&&n!=4)
						removeNmrAltFromFileTerm(g2[n],"trmNnAj1b"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
					if (!g5[n].startsWith("-")&&n!=4)
						removeNmrAltFromFileTerm(g5[n],"trmNnAj2b"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
					if (!g8[n].startsWith("-")&&n!=4)
						removeNmrAltFromFileTerm(g8[n],"trmNnAj3b"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
				}
				for(int n=1; n<7; n++)//neuters
				{
					if (!g3[n].startsWith("-")&&n!=3&&n!=4)
						removeNmrAltFromFileTerm(g3[n],"trmNnAj1c"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
					if (!g6[n].startsWith("-")&&n!=3&&n!=4)
						removeNmrAltFromFileTerm(g6[n],"trmNnAj2c"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
					if (!g9[n].startsWith("-")&&n!=3&&n!=4)
						removeNmrAltFromFileTerm(g9[n],"trmNnAj3c"+String.valueOf(n),
																			xcpt_sFrmlName,"eln",mu);
				}
		}
		else if (xmlNmLng.startsWith("<Name_NounAdverb"))
		{
			//termTxCpt_sAll: 1.
//			removeNmrAltFromFileTerm(eks, "nmAdv1",
//													xcpt_sFrmlName, "eng", mu);
		}
		else if (xmlNmLng.startsWith("<Name_Verb"))
		{
			//termTxCpt_sAll: 27 or 54 cases
			TermTxVerbEln ven=new TermTxVerbEln(eks);
			String nmrs = ven.getCommaTxConceptTermsIfRuleOnly(rl);
			String a[] = nmrs.split(",");
			if (a.length==54)
			{
				for(int n=0; n<54; n++)
				{
					if (!a[n].startsWith("-"))
						removeNmrAltFromFileTerm(a[n],"trmVrb"+String.valueOf(n+1),
																			xcpt_sFrmlName,"eln",mu);
				}
			}
			else if (a.length==27)
			{
				if (a[0].toLowerCase().endsWith("ω"))
				{
					for(int n=0; n<27; n++)
					{
						if (!a[n].startsWith("-"))
							removeNmrAltFromFileTerm(a[n],"trmVrb"+String.valueOf(n+1),
																				xcpt_sFrmlName,"eln",mu);
					}
				}
				else {
					for(int n=0; n<27; n++)
					{
						if (!a[n].startsWith("-"))
							removeNmrAltFromFileTerm(a[n],"trmVrb"+String.valueOf(n+28),
																				xcpt_sFrmlName,"eln",mu);
					}
				}
			}
			else
				JOptionPane.showMessageDialog(null,
					"Insert.updateTermFilesEln: tx_verb with no 27 or 54 terms1!!!",
					"Warning", JOptionPane.WARNING_MESSAGE);
		}
		else if (xmlNmLng.startsWith("<Name_Conjunction"))
			removeNmrFromFileTerm(eks, "trmCnj",xcpt_sFrmlName, "eln", "1");
		else if (xmlNmLng.startsWith("<Name_Short"))
			removeNmrFromFileTerm(eks, "nmLtr",xcpt_sFrmlName, "eln", mu);
		else if (xmlNmLng.startsWith("<Name_Symbol"))
			removeNmrFromFileTerm(eks, "nmSbr",xcpt_sFrmlName, "eln", mu);
		else if (xmlNmLng.startsWith("<Name_NounSpecialCase"))
		{
			//ppp I changed this 2006.10.07
			//english tx_special_nounss are special-expressions
			//they are indexed in the terminal files.
		}
		else
			JOptionPane.showMessageDialog(null,"Insert.removeTermFromTermFilesEln: unknown lg-concept's--term");
	}


	/**
	 *
	 * @param eks The alternative-lg-concept's--terms of same type.
	 * @param nmrType The type of the terms.
	 * @param xcpt_sFrmlName The formalName of the xcpt denoted.
	 * @param lng The language of the terms.
	 * @param mu The mostused attribute of term.
	 * @modified 2008.04.06
	 * @since 2008.04.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeNmrAltFromFileTerm(String eks, String nmrType,
				String xcpt_sFrmlName, String lng, String mu)
	{
		if (eks.indexOf("|")==-1) //one term
		{
			removeNmrFromFileTerm(eks,nmrType,xcpt_sFrmlName,lng,mu);
		}
		else //alternative-terms
		{
			StringTokenizer st = new StringTokenizer(eks, "|");
			int t=st.countTokens();
			String b[]= new String[t];
			for(int i=0; i<t; i++)
				b[i]=st.nextToken();
			LabelFactory lf = new LabelFactory();
			String lbls="a";
			for (int j=0; j<b.length; j++)
			{
				lbls=lf.getNextLabel();
				removeNmrFromFileTerm(b[j],nmrType+lbls,xcpt_sFrmlName,lng,mu);
			}
		}
	}


	/**
	 * Removes ONE term from term-files.
	 *
	 * @modified 2008.03.31
	 * @since 2008.03.31 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeNmrFromFileTerm(String eks,
																String tmType,
																String uniqueNameOfXCpt,
																String lng,
																String mu)//mostUsed
	{
		Util.log(eks+", "+tmType);
		//1)
		//<TRM TxEXP="hierarchy-node" TRMNLtYPE="trmNnCsNm1"
		//XCPT="BConcept@hknu.meta-1@" NAME="hierarchy-node"/>
		//2)
		//<TRM TxEXP="hierarchy-node">
		//<SYNTAX TxEXP="hierarchy-node" TRMNLtYPE.. XCPT...

		String xcpt_sfmlID=Util.getXCpt_sFormalID(uniqueNameOfXCpt);
		String ln2="";
		String fileNew = AAj.AAj_sDir +"tmpRemoveNmr";
		String tmrFile = "";
		if (mu.equals("1"))
			tmrFile=AAj.AAj_sDir +"AAjKB" +File.separator
							+"AAjINDEXES" +File.separator
							+lng +File.separator +"index_terminal_"+lng+".xml";
		else
			tmrFile=Util.getFullTermFile(eks, lng);
		try {
			FileReader reader =new FileReader(tmrFile);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);
			inserted=false;

			while ((ln = br.readLine()) != null)
			{
				if (!ln.startsWith("<TRM"))
				{
					bw.write(ln);
					bw.newLine();
				}
				else
				{
					if (ln.indexOf("TxEXP=\""+eks+"\"")!=-1){//found related term
						if (!ln.endsWith("/>")){ //has sintakseros
							Vector<String> vtmp = new Vector<String>();
							vtmp.add(ln);
							while (!(ln=br.readLine()).startsWith("</TRM")){
								if (ln.indexOf(tmType)!=-1
									&& ln.indexOf(xcpt_sfmlID)!=-1){}
								else
									vtmp.add(ln);
							}
							if (vtmp.size()!=1){
								for (String t : vtmp){
									bw.write(t);
									bw.newLine();
								}
								bw.write(ln);//</nmr
								bw.newLine();
							}
						}
						else { //one line term //toDo: later remove this case
							if (ln.indexOf(tmType)!=-1
									&& ln.indexOf(xcpt_sfmlID)!=-1){
								//do nothing
							} else {
								bw.write(ln);
								bw.newLine();
							}
						}
					}
					else { //nmr notrelated, write it.
						bw.write(ln);
						bw.newLine();
					}
				}
			}
			br.close();
			bw.close();
		}
		catch (IOException e) {
			System.out.println("Insert.removeNmrFromFileTerm: IOException"
												+e.toString());
		}
		Util.renameFile1AndDeleteFile2(fileNew, tmrFile);
	}


	/**
	 * Replace the 'GENERICiNHERITOR=' data of an xcpt/intXcpt.
	 *
	 * @param xcpt is the ID/FileName of the (file) xcpt in which we gonna make modifications.
	 * @param idGen is a Vector that contains the ids of the pairs id/gen xml-pars
	 * (separated by commas) we will modify.
	 * @modified 2000.03.21
	 * @since 2000.03.20
	 * @author HoKoNoUmo
	 */
	public void replaceGeneric(String xcpt, Vector idGen)
	{
		//put on map for easy searching.
		Map<String,String> mapID=new HashMap<String,String>();
		for (int i=0; i<idGen.size(); i++)
		{
			String el = (String)idGen.get(i);
			String key = el.substring(0,el.indexOf(","));
			String value = el.substring(el.indexOf(",")+1,el.length());
			mapID.put(key, value);
		}
		String fileOld = Util.getXCpt_sLastFullFileName(xcpt);
		String fileNew = Util.getXCpt_sFullDir(xcpt) +File.separator +"tmpReplaceGeneric";

		try {
			FileReader reader =new FileReader(fileOld);
			BufferedReader br =new BufferedReader(reader);
			FileWriter writer = new FileWriter(fileNew);
			BufferedWriter bw =new BufferedWriter(writer);


			while ((ln = br.readLine()) != null)
			{
				if (!ln.startsWith("<XCPT") && !ln.startsWith("<INTxCPT"))
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

				else //line STARTS with <XCPT/INT
				{
					if (!mapID.isEmpty())
					{
						String existingFrNm=Util.getXmlAttribute(ln,"FRnAME");
						existingFrNm=Util.getXCpt_sFormalID(existingFrNm);
						if (mapID.containsKey(existingFrNm))
						{
							String part1 = ln.substring(0, ln.indexOf("GENERICiNHERITOR="));
							String part3 = ln.substring(ln.indexOf("CREATED="),ln.length());
							String gen=Util.getXCpt_sLastFileName(mapID.get(existingFrNm));
							bw.write(part1 +"GENERICiNHERITOR=\"" + gen
											+"\" LASTmOD=\"" +Util.getCurrentDate() +"\" " +part3);
							bw.newLine();
							mapID.remove(existingFrNm);
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
			JOptionPane.showMessageDialog(null, "Insert.replaceGeneric: IOException",
																	"WARNING", JOptionPane.WARNING_MESSAGE);
		}
		Util.renameFile1AndDeleteFile2(fileNew, fileOld);
	}


	/**
	 * Updates the term-files with the terms of a sr-concept
	 * of a concrete language or "all" languages.
	 *
	 * @param uniqueNameOfXCpt	A unique-name of a file/internal-xcpt.
	 * @param lng		The lng/all language we want to update.
	 * @modified 2006.02.06
	 * @since 2006.01.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void updateTermFiles(String uniqueNameOfXCpt, String lng)
	{
		xcpt_sFrmlName=Util.getXCpt_sFormalName(uniqueNameOfXCpt);

		if (!lng.equalsIgnoreCase("all"))
		{
			lng=lng.toLowerCase();
			if (lng.equals("eln"))
				updateTermFilesEln(xcpt_sFrmlName);
			else if (lng.equals("eng"))
				updateTermFilesEng(xcpt_sFrmlName);
			else if (lng.equals("epo")){
				//updateTermFilesEp(xcpt_sFrmlName);
			}
			else if (lng.equals("kml")){
				//updateTermFilesEp(xcpt_sFrmlName);
			}
			//addLng
		}
		else //lng=all, we have to do the same procedure to all lng files.
		{
			updateTermFilesEln(xcpt_sFrmlName);
			updateTermFilesEng(xcpt_sFrmlName);
			//updateTermFilesEp(xcpt_sFrmlName);
		}
	}


	/**
	 * Updates the GREEK term-files with the terms of a sr-concept.<br/>
	 * <b>INPUT</b>: A unique-name of a file/internal-xcpt.
	 *
	 * @modified 2006.02.06
	 * @since 2006.02.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void updateTermFilesEln(String uniqueNameOfXCpt)
	{
		xcpt_sFrmlName=Util.getXCpt_sFormalName(uniqueNameOfXCpt);
		Vector<String> vnms=null; //will hold the term lines.
		Extract		ex = new Extract();
		vnms = ex.extractLgConceptNames(uniqueNameOfXCpt, "eln");

		//FOR every line in vector:
		//1) find lg-concept's--terms.
		//2) for each one update index files.
		for (int i=0; i<vnms.size(); i++)
		{
			String lnNmr = vnms.elementAt(i);
			String eks=Util.getXmlAttribute(lnNmr,"TxEXP");
			String rl= Util.getXmlAttribute(lnNmr,"TRMrULE");
			String mu= Util.getXmlAttribute(lnNmr,"MOSTuSED");
			if (lnNmr.startsWith("<Name_NounCase"))
			{
				TermTxNounCaseEln nen=new TermTxNounCaseEln(eks);
				String nmrs = nen.getTermsOfTxConceptIfRuleOnly(rl);
				//The nameNoun is a string of >>>8<<< coma delimited termTxCpt_sAll.
				//The first shows the gender.
				String a[] = nmrs.split(",");

				if (a[0].equals("ο"))
				{
					for(int n=1; n<8; n++)
					{
						if (!a[n].startsWith("-"))
							insertAltTermsInTermFile(a[n],"trmNnCsA"+String.valueOf(n),
																				xcpt_sFrmlName,a[1],mu,"eln");
					}
				}
				else if (a[0].equals("η"))
				{
					//we don't index the 4=vokativero which is always equal to 1
					for(int n=1; n<8; n++)
					{
						if (!a[n].startsWith("-")&&n!=4)
						//ALL feminines have the 4=1
						//to save space we don't write.
							insertAltTermsInTermFile(a[n],"trmNnCb"+String.valueOf(n),
																				xcpt_sFrmlName,a[1],mu,"eln");
					}
				}
				else if (a[0].equals("το"))
				{
					//we don't index the 3,4,7
					for(int n=1; n<7; n++)
					{
						if (!a[n].startsWith("-")&&n!=3&&n!=4)
						//ALL neuters have 3,4=1 and 7=5
						//to save space we don't write.
							insertAltTermsInTermFile(a[n],"trmNnCc"+String.valueOf(n),
																				xcpt_sFrmlName,a[1],mu,"eln");
					}
				}
			}
			else if (lnNmr.startsWith("<Name_NounAdjective"))
			{
				//adjective-terms: 17x3=51 cases
				TermTxNounAdjectiveEln nen = new TermTxNounAdjectiveEln(eks);
				String nmrs = nen.getTermsOfTxConceptIfRuleOnly(rl);
				//1a#1b#1c#2a#2b#2c#3a#3b#3c
				String g[] = nmrs.split("#");
				String g1[] = g[0].split(",");
				String g2[] = g[1].split(",");
				String g3[] = g[2].split(",");
				String g4[] = g[3].split(",");
				String g5[] = g[4].split(",");
				String g6[] = g[5].split(",");
				String g7[] = g[6].split(",");
				String g8[] = g[7].split(",");
				String g9[] = g[8].split(",");
				for(int n=1; n<8; n++)//masculines
				{
					if (!g1[n].startsWith("-"))
						insertAltTermsInTermFile(g1[n],"trmNnAj1a"+String.valueOf(n),
																			xcpt_sFrmlName,g1[1],mu,"eln");
					if (!g4[n].startsWith("-"))
						insertAltTermsInTermFile(g4[n],"trmNnAj2a"+String.valueOf(n),
																			xcpt_sFrmlName,g4[1],mu,"eln");
					if (!g7[n].startsWith("-"))
						insertAltTermsInTermFile(g7[n],"trmNnAj3a"+String.valueOf(n),
																			xcpt_sFrmlName,g7[1],mu,"eln");
				}
				for(int n=1; n<8; n++)//feminines
				{
					if (!g2[n].startsWith("-")&&n!=4)
						insertAltTermsInTermFile(g2[n],"trmNnAj1b"+String.valueOf(n),
																			xcpt_sFrmlName,g1[1],mu,"eln");
					if (!g5[n].startsWith("-")&&n!=4)
						insertAltTermsInTermFile(g5[n],"trmNnAj2b"+String.valueOf(n),
																			xcpt_sFrmlName,g4[1],mu,"eln");
					if (!g8[n].startsWith("-")&&n!=4)
						insertAltTermsInTermFile(g8[n],"trmNnAj3b"+String.valueOf(n),
																			xcpt_sFrmlName,g7[1],mu,"eln");
				}
				for(int n=1; n<7; n++)//neuters
				{
					if (!g3[n].startsWith("-")&&n!=3&&n!=4)
						insertAltTermsInTermFile(g3[n],"trmNnAj1c"+String.valueOf(n),
																			xcpt_sFrmlName,g1[1],mu,"eln");
					if (!g6[n].startsWith("-")&&n!=3&&n!=4)
						insertAltTermsInTermFile(g6[n],"trmNnAj2c"+String.valueOf(n),
																			xcpt_sFrmlName,g4[1],mu,"eln");
					if (!g9[n].startsWith("-")&&n!=3&&n!=4)
						insertAltTermsInTermFile(g9[n],"trmNnAj3c"+String.valueOf(n),
																			xcpt_sFrmlName,g7[1],mu,"eln");
				}
			}
			else if (lnNmr.startsWith("<Name_NounAdverb"))
			{
				//termTxCpt_sAll: 3
			}
			else if (lnNmr.startsWith("<Name_Verb"))
			{
				//termTxCpt_sAll: 27 or 54 cases
				TermTxVerbEln ven=new TermTxVerbEln(eks);
				String nmrs = ven.getCommaTxConceptTermsIfRuleOnly(rl);
				String a[] = nmrs.split(",");
				if (a.length==54)
				{
					for(int n=0; n<54; n++)
					{
						if (!a[n].startsWith("-"))
							insertAltTermsInTermFile(a[n],"trmVrb"+String.valueOf(n+1),
																				xcpt_sFrmlName,a[0],mu,"eln");
					}
				}
				else if (a.length==27)
				{
					if (a[0].toLowerCase().endsWith("ω"))
					{
						for(int n=0; n<27; n++)
						{
							if (!a[n].startsWith("-"))
								insertAltTermsInTermFile(a[n],"trmVrb"+String.valueOf(n+1),
																					xcpt_sFrmlName,a[0],mu,"eln");
						}
					}
					else {
						for(int n=0; n<27; n++)
						{
							if (!a[n].startsWith("-"))
								insertAltTermsInTermFile(a[n],"trmVrb"+String.valueOf(n+28),
																					xcpt_sFrmlName,a[0],mu,"eln");
						}
					}
				}
				else
					JOptionPane.showMessageDialog(null,
						"Insert.updateTermFilesEln: tx_verb with no 27 or 54 terms1!!!",
						"Warning", JOptionPane.WARNING_MESSAGE);
			}
			else if (lnNmr.startsWith("<Name_Short")){
				insertTermInTermFile(eks,"nmLtr",xcpt_sFrmlName,"",mu,"eln");
			}
			else if (lnNmr.startsWith("<Name_Symbol")){
				insertTermInTermFile(eks,"nmSbr",xcpt_sFrmlName,"",mu,"eln");
			}
			else if (lnNmr.startsWith("<Name_Conjunction"))
			{
				//they are indexed in the terminal files.
				insertTermInTermFile(eks,"trmCnj",xcpt_sFrmlName,"","1","eln");
			}
			else if (lnNmr.startsWith("<Name_NounSpecialCase"))
			{
				//english tx_special_nounss are special-expressions
				//they are indexed in the terminal files.
			}
		}

	}


	/**
	 * Updates the ENGLISH term-files with the terms of a sr-concept.<br/>
	 * <b>INPUT</b>: A unique-name of a file/internal-xcpt.
	 *
	 * @modified 2006.02.07
	 * @since 2006.01.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void updateTermFilesEng(String uniqueNameOfXCpt)
	{
		xcpt_sFrmlName=Util.getXCpt_sFormalName(uniqueNameOfXCpt);
		Vector<String> vnms=null; //will hold the term lines.

		Extract ex = new Extract();
		vnms = ex.extractLgConceptNames(uniqueNameOfXCpt, "eng");
		//the vector has and the <lng lines.

		//FOR every line in vector:
		//1) find terms1 or tx_expressions:
		//2) for each one update index files.
		for (int i=0; i<vnms.size(); i++)
		{
			String lnNmr = vnms.elementAt(i);
			String eks=Util.getXmlAttribute(lnNmr,"TxEXP");
			String rl=Util.getXmlAttribute(lnNmr,"TRMrULE");
			String mu=Util.getXmlAttribute(lnNmr,"MOSTuSED");
			if (lnNmr.startsWith("<Name_NounCase"))
			{
				TermTxNounCaseEng nen=new TermTxNounCaseEng(eks);
				String nmrs = nen.getTermsOfTxConceptIfRuleOnly(rl);
				//The nameNoun is a string of 4 coma delimited termTxCpt_sAll.
				//We index the first 2, because the pozesiveros
				//can be found from nominatives. 2006.02.02
				String a[] = nmrs.split(",");
				for(int n=0; n<2; n++)
				{
					if (!a[n].startsWith("-"))
						insertAltTermsInTermFile(a[n],"trmNnCsNm"+String.valueOf(n+1),
																			xcpt_sFrmlName,a[0],mu,"eng");
				}
			}
			else if (lnNmr.startsWith("<Name_NounAdjective"))
			{
				//xConcept: <Name_NounAdjective TxEXP="Concept" TRMrULE="rlEngTrmNnAj11"
				//dez:<TRM TxEXP="concept" XCPT=\""hknu.meta-1" TYPE="trmNnAj3"/>
				//termTxCpt_sAll: 3 cases
				TermTxNounAdjectiveEng nen = new TermTxNounAdjectiveEng(eks);
				String nmrs = nen.getTermsOfTxConceptIfRuleOnly(rl);
				String a[] = nmrs.split(",");
				for(int n=0; n<3; n++)
				{
					if (!a[n].startsWith("-"))
						insertAltTermsInTermFile(a[n],"trmNnAj"+String.valueOf(n+1),
																			xcpt_sFrmlName,a[0],mu,"eng");
				}
			}
			else if (lnNmr.startsWith("<Name_NounAdverb"))
			{
				//termTxCpt_sAll: 1.
				//xConcept: <Name_NounAdverb TxEXP="conceptually" NORULE
				//dez: <TRM TxEXP="conceptually" XCPT=\""hknu.meta-1" TYPE="trmNnAv1"/>
				insertTermInTermFile(eks,"nmAdv1",xcpt_sFrmlName,eks,mu,"eng");
			}
			else if (lnNmr.startsWith("<Name_Verb"))
			{
				//xConcept: <Name_Verb TxEXP="Concept" TRMrULE="rlEngTrmVrb11"
				//dez:<TRM TxEXP="concept" XCPT=\""hknu.meta-1" TYPE="trmNnCsNm1"/>
				//termTxCpt_sAll: 5 cases
				TermTxVerbEng ven=new TermTxVerbEng(eks);
				String nmrs = ven.getTermsOfTxConceptIfRuleOnly(rl);
				String a[] = nmrs.split(",");
				for(int n=0; n<5; n++)
				{
					if (!a[n].startsWith("-"))
						insertAltTermsInTermFile(a[n],"trmVrb"+String.valueOf(n+1),
																			xcpt_sFrmlName,a[0],mu,"eng");
				}
			}
			else if (lnNmr.startsWith("<Name_Short")
						||lnNmr.startsWith("<Name_Symbol"))
			{
				//xConcept: <Name_Short TxEXP="xcpt"
				//ONE case
				if (!eks.startsWith("-"))
				{
					if (lnNmr.startsWith("<Name_Short"))
						insertTermInTermFile(eks,"nmLtr",
																		xcpt_sFrmlName,"",mu,"eng");
					else if (lnNmr.startsWith("<Name_Symbol"))
						insertTermInTermFile(eks,"nmSbr",
																		xcpt_sFrmlName,"",mu,"eng");
				}
			}
			else if (lnNmr.startsWith("<Name_Conjunction"))
			{
				//they are indexed in the terminal files.
				insertTermInTermFile(eks,"trmCnj",xcpt_sFrmlName,"","1","eng");
			}
			else if (lnNmr.startsWith("<Name_NounSpecialCase"))
			{
				//ppp I changed this 2006.10.07
				//english tx_special_nounss are special-expressions
				//they are indexed in the terminal files.
			}
		}
	}


	/**
	 * Writes an Internal to a BufferedWriter.
	 * We use this method to avoid repeating the next code.<br/>
	 * <b>INPUT</b>: 1) the BufferedWriter, 2) the formal-name
	 * 3) the xml-definition element and 4) xml-eng-main-name
	 * element of the internal.
	 *
	 * @modified 2009.10.07
	 * @since 2000.02.17
	 * @author HoKoNoUmo
	 */
	public void writeInternal(BufferedWriter bw, String intFrNm,
													String intXDef, String intXName)
													throws IOException
	{
		xcpt_sAuthor= Util.getXmlAttribute(intXName, "AUTHOR");
		bw.write("<INTxCPT FRnAME=\"" +intFrNm
							+"\" CREATED=\"" +Util.getCurrentDate()
							+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
		bw.newLine();

		//write definition-relation.
		bw.write("<REFINO_DEFINITION"
							+" CREATED=\"" +Util.getCurrentDate()
							+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
		bw.newLine();
		bw.write(intXDef);
		bw.newLine();
		bw.write("</REFINO_DEFINITION>");
		bw.newLine();

		//write name-relation
		bw.write("<REFINO_NAME"
						+" CREATED=\"" +Util.getCurrentDate()
						+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
		bw.newLine();
		bw.write("<eng"
						+" CREATED=\"" +Util.getCurrentDate()
						+"\" AUTHOR=\"" +xcpt_sAuthor +"\">");
		bw.newLine();
		bw.write(intXName);
		bw.newLine();
		bw.write("</eng>");
		bw.newLine();
		bw.write("</REFINO_NAME>");
		bw.newLine();
		bw.write("</INTxCPT>");
	}


}