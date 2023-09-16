/*
 * Textual.java - Contains GLOBAL TEXTUAL utility methods and variables.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2006 Nikos Kasselouris
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
import java.util.*;
import java.util.regex.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * Contains GLOBAL
 * TEXTUAL utility methods and variables.<p>
 *
 * I got the textual-related methods from {@link Util Util}.
 * @modified 2006.02.12
 * @since 2006.02.12 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Textual
{
	public static String	AAj_sDir =
							System.getProperty("user.dir") + File.separator;
//	"C:\\java\\AAj\\WORKING" + File.separator;

	/** The log writer. */
	public static BufferedWriter			bwLog;


	/**
	 * CASES:<br>
	 * xxx'yyy		  >>> xxx's-yyy. (yyy attribute of xxx)<br>
	 * xxx.yyy		  >>> yyy-xxx. (yyy specific of xxx)<br>
	 * xxx-xxx		  >>> xxx-xxx.<br>
	 * xxx_xxx		  >>> xxx xxx.<br>
	 * xxx'and'yyy  >>> Relation of xxx to yyy.
	 *
	 * @param fileName  the filename of the xcpt.
	 * @return the Name of the xcpt from its filename by a function.
	 * @modified 2001.03.05
	 * @since 1999.04.20
	 * @author HoKoNoUmo
	 */
	public static String createNormalName(String fileName)
	{
		String normalName=null;
		String formalNameVerbal=null;
		if (fileName.indexOf("@")!=-1)
		{
			formalNameVerbal=fileName.substring(0, fileName.indexOf("@"));
		}
		else System.out.println("Textual.createNormalName(): " +fileName +" is not a FileName");

		if (formalNameVerbal.indexOf("'")!=-1)//we have a formal-name xxx'yyy or xxx'and'yyy
		{
			if (formalNameVerbal.indexOf("'")!=formalNameVerbal.lastIndexOf("'")) //2000.04.12
			{
				String part1=formalNameVerbal.substring(0, formalNameVerbal.indexOf("'"));
				String part2=formalNameVerbal.substring(formalNameVerbal.lastIndexOf("'")+1, formalNameVerbal.length());
				normalName="Relation of " +part1 +" to " +part2; //2000.04.19
//			  normalName=formalNameVerbal.replace('\'', ' ');
			}
			else //an environment-name.
			{
				normalName= formalNameVerbal.substring(0, formalNameVerbal.indexOf("'")) //2000.04.10
									+"'s-" +formalNameVerbal.substring(formalNameVerbal.indexOf("'")+1, formalNameVerbal.length());
			}
		}
		else if (formalNameVerbal.indexOf(".")>2) //we have a formal-name xxx.yyy
		{
			String firstName = formalNameVerbal.substring(formalNameVerbal.indexOf(".")+1, formalNameVerbal.length());
			String lastName = formalNameVerbal.substring(0, formalNameVerbal.indexOf("."));
			normalName= firstName +"-" +lastName;
		}
		else normalName=formalNameVerbal;

		if (normalName.indexOf("_")>1)
		{
			normalName=normalName.replace('_', ' ');
			return normalName;
		}
		else return normalName;
	}


	/**
	 * Returns the possible languages that a string belongs as array.
	 *
	 * @modified 2006.10.28
	 * @since 2006.10.28 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String[] findArrayOfLangOfTxPrimaryUnitStructure(String lkt)
	{
		Vector<String> vol = new Vector<String>();//vector of languages.

		String ln="";
		String fl= AAj_sDir +"AAjKB" +File.separator
					+"TERMINAL" +File.separator +"textual_strings.xml";

		try {
			FileInputStream fis = new FileInputStream(fl);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-16");
			BufferedReader br = new BufferedReader(isr);
			while ( (ln=br.readLine())!=null )
			{
				if (ln.startsWith("<TX_PRIMARY_UNIT_STRUCTURE")){
					String ls=Util.getXmlAttribute(ln,"STRING");
					String lng = ln.substring(27,30).toLowerCase();//wrkarnd
					if (lng.equals("eln"))
						lkt=lkt.toLowerCase(new Locale("el","GR"));
					lkt=lkt.toLowerCase();
					boolean found=true;

					for(int i=0; i<lkt.length(); i++)
					{
						String cs = String.valueOf(lkt.charAt(i));
						if (!ls.contains(cs)){
							found=false;
							break;
						}
					}

					if (found)
						vol.add(lng);
				}
			}
			br.close();
		}
		catch (EOFException e){
			System.out.println("Textual.findArrayOfLangOfTxPrimaryUnitStructure: EOFException");}
		catch (FileNotFoundException e)	{
			System.out.println("Textual.findArrayOfLangOfTxPrimaryUnitStructure: FileNotFoundException");}
		catch (IOException e){
			System.out.println("Textual.findArrayOfLangOfTxPrimaryUnitStructure: IOException");}

		//needs work: 3.x is tx_unit_structure??? ppp
//		if (isNumber(lkt))
//			return false;

		String aol[]=new String[vol.size()];
		aol=vol.toArray(aol);

		return aol;
	}


	/**
	 * Finds the Terms of a textual_concept of a concept.<br>
	 *
	 * ALGORITHM:<br>
	 *  1. extract the line with the FIRST lg-concept from the
	 *  nameino-element of the sr-concept-file.
	 *  2.
	 *
	 * @param uniqueNameOfXCpt A unique identifier of the xcpt.
	 * @param nmLgCpt	The lg-concept, for which we want its terms:
	 *  Name_Verb, Name_NounCase, ...
	 * @param lng	The language in which we want the terms.
	 * @modified 2006.02.04
	 * @since 2001.07.12
	 * @author HoKoNoUmo
	 */
	public static String[] getTermsOfTxConceptAsArrayIfXConcept(String uniqueNameOfXCpt,
												String nmLgCpt, String lng)
	{
		String nmrArray[] = {"-"};
		String termTxCpt_sAll = "";
		String ln;

		try {
			if (uniqueNameOfXCpt.indexOf("#")==-1)//a file-xConcept.
			{
				BufferedReader br = new BufferedReader(
						new FileReader(Util.getXCpt_sLastFullFileName(uniqueNameOfXCpt)));
				while ( (ln=br.readLine())!=null )
				{
					if (ln.startsWith("<" +lng +" "))
					{
						while ( !(ln=br.readLine()).startsWith("</"+lng) )
						{
							if (ln.indexOf("<"+nmLgCpt) != -1){
								termTxCpt_sAll=ln;
								break;
							}
						}
					}
				}
				br.close();
			}
			else //an internal-xConcept
			{
				String idLook=Util.getXCpt_sFormalID(uniqueNameOfXCpt);
				String hostIdName=Util.getSrCptInt_sHostID(uniqueNameOfXCpt);
//System.out.println(idLook);
//System.out.println(hostIdName);
				BufferedReader br = new BufferedReader (
					new FileReader(Util.getXCpt_sLastFullFileName(hostIdName)));
				while ( (ln=br.readLine())!=null )
				{
					if (ln.startsWith("<INTxCPT"))
					{
						String idFound=Util.getXCpt_sFormalID(Util.getXmlAttribute(ln,"FRnAME"));
						if (idLook.equals(idFound)) //This is the internal we are looking for.
						{
							while ( (ln=br.readLine())!=null )
							{
								if (ln.startsWith("<" +lng))
								{
									while ( !(ln=br.readLine()).startsWith("</"+lng) )
									{
										if (ln.startsWith("<"+nmLgCpt)){
											termTxCpt_sAll=ln;
											break;
										}
									}
								}
							}
						}
					}
				}
				br.close();
			}
		}
		catch (EOFException e){
			System.out.println("Textual.getTermsOfTxConceptAsArrayIfXConcept: EOFException");}
		catch (FileNotFoundException e)	{
			JOptionPane.showMessageDialog(null,
				"There is NO xmlfile to extract the lg-concept's--term: " +uniqueNameOfXCpt);}
		catch (IOException e){
			System.out.println("Textual.getTermsOfTxConceptAsArrayIfXConcept: IOException");
			System.out.println(e.toString());
		}

		if (!termTxCpt_sAll.equals("")){
			termTxCpt_sAll=getTermsOfTxConcept(Util.getXmlAttribute(termTxCpt_sAll,"TxEXP"),
														Util.getXmlAttribute(termTxCpt_sAll,"TRMrULE") );

			StringTokenizer parser = new StringTokenizer(termTxCpt_sAll, "#");
			int tokens=parser.countTokens();
			if (tokens==1)//tx_nouns, irregulars, english-words,
			{
				StringTokenizer parser2 = new StringTokenizer(termTxCpt_sAll, ",");
				int tokens2=parser2.countTokens();
				nmrArray=new String[tokens2];
				try {
					for(int i=0; i<tokens2; i++)
					{
						nmrArray[i]=(String)parser2.nextElement();
					}
				}
				catch (NoSuchElementException e) {
					System.out.println("Textual.getTermsOfTxConceptAsArrayIfXConcept: NoSuchElementException1");
				}
			}

			else {
				if (tokens==3)					  //greek tx_nounAdjective
					nmrArray=new String[24];  //3x8=24
				else if (tokens==6)			 //greek tx_nounAdjective
					nmrArray=new String[48];  //6x8=48
				else if (tokens==9)			 //greek tx_nounAdjective
					nmrArray=new String[72];  //9x8=72
				else if (tokens==7)			 //greek tx_verb 1 voice
					nmrArray=new String[27];  //6+1+6+6+6+1+1
				else if (tokens==14)			//greek tx_verb 2 voices
					nmrArray=new String[54];  //27x2=54

				int index=0;
				try {
					while(parser.hasMoreElements())
					{
						String tk1=(String)parser.nextElement();
						StringTokenizer parser2 = new StringTokenizer(tk1, ",");
						while(parser2.hasMoreElements())
						{
							String tk2=(String)parser2.nextElement();
							nmrArray[index]=tk2;
							index=index+1;
						}
					}
				} catch (NoSuchElementException e) {
				System.out.println("Textual.getTermsOfTxConceptAsArrayIfXConcept: NoSuchElementException2");
				}
			}
		}
		return nmrArray;
	}


	/**
	 * Returns the FIRST individual-tx_concept of an xcpt.
	 *
	 * @param uniqueNameOfXCpt A unique identifier of the xcpt.
	 * @param indTxCpt	The individual-tx_concept we want: nnCsNm01, nnAj01, ...
	 * @param lng	The language in which we want tx_cpt.
	 * @modified 2009.01.23
	 * @since 2009.01.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getTxConceptIndividualIfXConcept(String uniqueNameOfXCpt,
												String indTxCpt, String lng)
	{
		String tcpt="";//tx_cpt
		String trms[]={","};
		String lnTxCpt=""; //LineWithTxConcept

		if (lng.equals("eng"))
		{
			//1: get the expression of tx_cpt
			String ln="";
			String fileIn = Util.AAj_sDir +"AAjKB" +File.separator
										+"TERMINAL" +File.separator
										+"MapeinoTextualSemasial.eng.xml";
			try {
				FileReader fr= new FileReader(fileIn);
				BufferedReader br= new BufferedReader (fr);
				while ((ln=br.readLine()) != null)
				{
					if (ln.startsWith("<LGcPT=\""+indTxCpt)) //this is our line
					{
						lnTxCpt=ln;
						tcpt=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
				br.close();
			}
			catch (EOFException e){System.out.println("getTxConceptIndividualIfXConcept: EOFException");}
			catch (FileNotFoundException e){System.out.println("getTxConceptIndividualIfXConcept: FNFException");}
			catch (IOException e){System.out.println("getTxConceptIndividualIfXConcept: IOException");}

			//2: get the terms of tx_cpt
			if (indTxCpt.startsWith("nnCs")){
				trms=getTermsOfTxConceptAsArrayIfXConcept(uniqueNameOfXCpt,"Name_NounCase", "eng");
				//car,cars,car's,cars'
//				nnCsNm01	car	Nominative	smNn1	Generic,Sin
//				nnCsNm02	cars	Nominative	smNn2	Generic,Plu
//				nnCsNm03	the car	Nominative	smNn3	Individual,Sin
//				nnCsNm04	the cars	Nominative	smNn4	Individual,Plu
//				nnCsNm05	a car	Nominative	smNn5	Random,Sin
//				nnCsNm06	some cars	Nominative	smNn6	Random,Plu
//				nnCsPs01	car's	Possessive	smNn1	Generic,Sin
//				nnCsPs02	cars'	Possessive	smNn2	Generic,Plu
//				nnCsPs03	the car's	Possessive	smNn3	Individual,Sin
//				nnCsPs04	the cars'	Possessive	smNn4	Individual,Plu
//				nnCsPs05	a car's	Possessive	smNn5	Random,Sin
//				nnCsPs06	some cars'
				tcpt=tcpt.replaceAll("trmNnCsNm1",trms[0]);
				tcpt=tcpt.replaceAll("trmNnCsNm2",trms[1]);
				tcpt=tcpt.replaceAll("trmNnCsNm3",trms[2]);
				tcpt=tcpt.replaceAll("trmNnCsNm4",trms[3]);
			}
			else if (indTxCpt.startsWith("nnAj")){
				trms=getTermsOfTxConceptAsArrayIfXConcept(uniqueNameOfXCpt,"Name_NounAdjective", "eng");
				//conceptual, more conceptual, most conceptual
				tcpt=tcpt.replaceAll("trmNnAj1",trms[0]);
				tcpt=tcpt.replaceAll("trmNnAj2",trms[1]);
				tcpt=tcpt.replaceAll("trmNnAj3",trms[2]);
			}
			else if (indTxCpt.startsWith("nnAv")){
				trms=getTermsOfTxConceptAsArrayIfXConcept(uniqueNameOfXCpt,"Name_NounAdverb", "eng");
				//
				tcpt=tcpt.replaceAll("trmNnAv1",trms[0]);
				tcpt=tcpt.replaceAll("trmNnAv2",trms[1]);
				tcpt=tcpt.replaceAll("trmNnAv3",trms[2]);
			}
			else if (indTxCpt.startsWith("vrb")){
				trms=getTermsOfTxConceptAsArrayIfXConcept(uniqueNameOfXCpt,"Name_Verb", "eng");
				//name,names,named,naming,named
				tcpt=tcpt.replaceAll("trmVrb1",trms[0]);
				tcpt=tcpt.replaceAll("trmVrb2",trms[1]);
				tcpt=tcpt.replaceAll("trmVrb3",trms[2]);
				tcpt=tcpt.replaceAll("trmVrb4",trms[3]);
				tcpt=tcpt.replaceAll("trmVrb5",trms[4]);
			}
		}
		else if (lng.equals("eln"))
		{
			//toDo
		}
		//addLng

		return tcpt;
	}


	/**
	 * Finds the gender of the first noun of a sr-concept.
	 * If gender=-1 something went wrong.
	 *
	 * @param uniqueNameOfXCpt
	 * 		A unique-name of a file/internal-xcpt.
	 * @modified 2004.11.21
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String greekGetGender(String uniqueNameOfXCpt)
	{
		String n[]=getTermsOfTxConceptAsArrayIfXConcept(uniqueNameOfXCpt, "Name_NounCase", "eln");
		if (n[0].equals("ο"))
			return "mas";
		else if (n[0].equals("η"))
			return "fem";
		else if (n[0].equals("το"))
			return "neu";
		else
			return "-1";
	}


	/**
	 * Returns the terms of a lg-concept
	 * in the format: trm1,trm2#trm3,...<br>
	 * GIVEN: the name of the lg-concept and the term-rule.
	 *
	 * @modified 2001.06.12
	 * @since 2001.05.23
	 * @author HoKoNoUmo
	 */
	public static String getTermsOfTxConcept(String termTxCpt_sName, String rule)
	{
		String nmLgConcepts="";
		termTxCpt_sName=termTxCpt_sName.toLowerCase();

		//addLng
		if (rule.startsWith("rlElnTrmNnCs"))
		{
			TermTxNounCaseEln n = new TermTxNounCaseEln(termTxCpt_sName);
			nmLgConcepts=n.getTermsOfTxConceptIfRuleOnly(rule);
		}
		else if (rule.startsWith("rlElnTrmVbr"))
		{
			TermTxVerbEln v = new TermTxVerbEln(termTxCpt_sName);
			nmLgConcepts=v.getTermsOfTxConceptIfRuleOnly(rule);
		}
		else if (rule.startsWith("rlElnTrmAdj"))
		{
			TermTxNounAdjectiveEln aj = new TermTxNounAdjectiveEln(termTxCpt_sName);
			nmLgConcepts=aj.getTermsOfTxConceptIfRuleOnly(rule);
		}

//english words:
		else if (rule.startsWith("rlEngTrmNnCs"))
		{
			TermTxNounCaseEng n = new TermTxNounCaseEng(termTxCpt_sName);
			nmLgConcepts=n.getTermsOfTxConceptIfRuleOnly(rule);
		}
		else if (rule.startsWith("rlEngTrmVrb"))
		{
			TermTxVerbEng v = new TermTxVerbEng(termTxCpt_sName);
			nmLgConcepts=v.getTermsOfTxConceptIfRuleOnly(rule);
		}
		else if (rule.startsWith("rlEngTrmNnAj"))
		{
			TermTxNounAdjectiveEng aj = new TermTxNounAdjectiveEng(termTxCpt_sName);
			nmLgConcepts=aj.getTermsOfTxConceptIfRuleOnly(rule);
		}
		return nmLgConcepts;
	}


	/**
	 * Finds the termTxCpt_sAll of a word, GIVEN the rule used to create them!!
	 *
	 * @modified 2001.06.13
	 * @since 2001.06.12
	 * @author HoKoNoUmo
	 */
	public static String[] getTermsOfTxConceptAsArray(String word, String rule)
	{
		String nmrArray[]=null;
		String nmLgConcepts=getTermsOfTxConcept(word,rule);
		StringTokenizer parser = new StringTokenizer(nmLgConcepts, "#");
		int tokens=parser.countTokens();

		if (tokens==1)//tx_nouns, irregulars, english-words,
		{
			StringTokenizer parser2 = new StringTokenizer(nmLgConcepts, ",");
			int tokens2=parser2.countTokens();
			nmrArray=new String[tokens2];
			try {
				for(int i=0; i<tokens2; i++)
				{
						nmrArray[i]=(String)parser2.nextElement();
				}
			} catch (NoSuchElementException e) {
				System.out.println("Textual.getTermsOfTxConceptAsArray: NoSuchElementException1");
			}
		}

		else {
			if (tokens==3)					  //greek tx_nounAdjective
				nmrArray=new String[24];  //3x8=24
			else if (tokens==6)			 //greek tx_nounAdjective
				nmrArray=new String[48];  //6x8=48
			else if (tokens==9)			 //greek tx_nounAdjective
				nmrArray=new String[72];  //9x8=72
			else if (tokens==7)			 //greek tx_verb 1 voice
				nmrArray=new String[30];  //30x1=30
			else if (tokens==14)			//greek tx_verb 2 voices
				nmrArray=new String[60];  //30x2=60

			int index=0;
			try {
				while(parser.hasMoreElements())
				{
					String tk1=(String)parser.nextElement();
					StringTokenizer parser2 = new StringTokenizer(tk1, ",");
					while(parser.hasMoreElements())
					{
						nmrArray[index]=(String)parser2.nextElement();
						index=index+1;
					}
				}
			} catch (NoSuchElementException e) {
				System.out.println("Textual.getTermsOfTxConceptAsArray: NoSuchElementException2");
			}
		}
		return nmrArray;
	}


	/**
	 * Returns one or two letter code for logal_concepts.
	 *
	 * @modified 2004.11.22
	 * @since 2004.09.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String getShortNameOfLgConcept(String lgCpt)
	{
		lgCpt=lgCpt.toLowerCase();
		String sn="unknown";
		if (lgCpt.equals("tx_verb"))
			sn="v";
		else if (lgCpt.equals("tx_conjunction"))
			sn="c";
		else if (lgCpt.equals("tx_noun"))
			sn="n";
		else if (lgCpt.equals("tx_nounAdjective"))
			sn="an";
		else if (lgCpt.equals("tx_adverb"))
			sn="av";
		return sn;
	}


	/**
	 * Οι λέξεις τον, την, στον, στην, έναν, αυτήν, δεν, μην
	 * ΧΑΝΟΥΝ το τελικό ν μπροστά από λέξεις που αρχίζουν από:
	 * γ,β,δ,χ,φ,θ,μ,ν,λ,ρ,σ,ζ.
	 *
	 * @param str
	 * 		A 2 word string.
	 * @modified
	 * @since 2001.10.02
	 * @author HoKoNoUmo
	 */
	public static String greekRemoveLastN(String str)
	{
		String newstr="";
		String first=str.substring(0, str.indexOf(" "));
		String second=str.substring(str.indexOf(" ")+1,str.length());
		if (first.equals("τον") || first.equals("ΤΟΝ") ||
				first.equals("την") || first.equals("ΤΗΝ") ||
				first.equals("στον") || first.equals("ΣΤΟΝ") ||
				first.equals("στην") || first.equals("ΣΤΗΝ") ||
				first.equals("έναν") || first.equals("ΈΝΑΝ") || first.equals("ΕΝΑΝ") ||
				first.equals("αυτήν") || first.equals("ΑΥΤΉΝ") || first.equals("ΑΥΤΗΝ") ||
				first.equals("δεν") || first.equals("ΔΕΝ") ||
				first.equals("μην") || first.equals("ΜΗΝ") )
			if (second.startsWith("γ") || second.startsWith("Γ") ||
					second.startsWith("β") || second.startsWith("Β") ||
					second.startsWith("δ") || second.startsWith("Δ") ||
					second.startsWith("χ") || second.startsWith("Χ") ||
					second.startsWith("φ") || second.startsWith("Φ") ||
					second.startsWith("θ") || second.startsWith("Θ") ||
					second.startsWith("μ") || second.startsWith("Μ") ||
					second.startsWith("ν") || second.startsWith("Ν") ||
					second.startsWith("λ") || second.startsWith("Λ") ||
					second.startsWith("ρ") || second.startsWith("Ρ") ||
					second.startsWith("σ") || second.startsWith("Σ") ||
					second.startsWith("ζ") || second.startsWith("Ζ") )
			{
				newstr=first.substring(0,first.length()-1) +" " +second;
			}
		return newstr;
	}


	/**
	 * Removes tonos from a greek word.<p>
	 * I copied this method here, from Term_TxConcept class.
	 *
	 * @modified 2006.02.07
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public static String greekRemoveTonos(String word)
	{
		if (word.indexOf("ά")!=-1)
			return word.replace('ά','α');
		else if (word.indexOf("έ")!=-1)
			return word.replace('έ','ε');
		else if (word.indexOf("ή")!=-1)
			return word.replace('ή','η');
		else if (word.indexOf("ί")!=-1)
			return word.replace('ί','ι');
		else if (word.indexOf("ό")!=-1)
			return word.replace('ό','ο');
		else if (word.indexOf("ύ")!=-1)
			return word.replace('ύ','υ');
		else if (word.indexOf("ώ")!=-1)
			return word.replace('ώ','ω');
		else if (word.indexOf("ΰ")!=-1)
			return word.replace('ΰ','ϋ');
		else if (word.indexOf("ΐ")!=-1)
			return word.replace('ΐ','ϊ');
		else
			return word;
	}


	/**
	 * Returns the string: "char;uXXXX;int;unicode-name"
	 *
	 * @param chr	The input character as String.
	 * @modified 2006.02.12
	 * @since 2006.02.12 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String identifyCharacterUnicode(String chr)
	{
		String ln="";
		String fl= AAj_sDir +"AAjKB" +File.separator
					+"TERMINAL" +File.separator +"unicode.txt";
		try {
//			FileReader fr = new FileReader();
//			BufferedReader br = new BufferedReader(fr);
			FileInputStream fis = new FileInputStream(fl);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-16");
			BufferedReader br = new BufferedReader(isr);
			while ( (ln=br.readLine())!=null )
			{
				if (ln.startsWith(chr))
					return ln;
			}
			br.close();
		}
		catch (EOFException e){
			System.out.println("Textual.identifyCharacterUnicode: EOFException");}
		catch (FileNotFoundException e)	{
			System.out.println("Textual.identifyCharacterUnicode: FileNotFoundException");}
		catch (IOException e){
			System.out.println("Textual.identifyCharacterUnicode: IOException");}
		return ln;
	}


	/**
	 * Tests if a string is a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#foEtoPoStrukto">
	 * tx_unit_structure</a> of a language.<p>
	 *
	 * ALGORITHM: the method tests if all the letters of the tx_unit_structure
	 * belong to a specific language.<br>
	 * This is NOT correct.  It must know the used silaberos and check
	 * against them, because a sequence of letters of a language may not
	 * be used.<p>
	 *
	 * In the file "AAjKB/TERMINAL/textual_strings.txt" we define
	 * the letters of a language.<p>
	 *
	 * @param lng	The 3 letter code that denotes a language.
	 * 		If lng=all a tx_nonstopNode is tx_unit_structure if is tx_unit_structure at least in one
	 * 		language, not mixed letteros.
	 * @param lkt	The input tx_unit_structure.
	 * @modified 2006.02.19
	 * @since 2006.02.11 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean isTxPrimaryUnitStructureOfLang(String lng, String lkt)
	{
		if (lng.equals("eln"))
			lkt=lkt.toLowerCase(new Locale("el","GR"));
		lkt=lkt.toLowerCase();
		String els = "";
		String ens = "";
		String eps = "";
		boolean result=false;
		String ln="";
		String fl= AAj_sDir +"AAjKB" +File.separator
					+"TERMINAL" +File.separator +"textual_strings.xml";
		try {
			FileInputStream fis = new FileInputStream(fl);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-16");
			BufferedReader br = new BufferedReader(isr);
			while ( (ln=br.readLine())!=null )
			{
				if (ln.startsWith("<TX_PRIMARY_UNIT_STRUCTURE_eln"))
					els=Util.getXmlAttribute(ln,"STRING");
				else if (ln.startsWith("<TX_PRIMARY_UNIT_STRUCTURE_eng"))
					ens=Util.getXmlAttribute(ln,"STRING");
				else if (ln.startsWith("<TX_PRIMARY_UNIT_STRUCTURE_epo"))
					eps=Util.getXmlAttribute(ln,"STRING");
			}
			br.close();
		}
		catch (EOFException e){
			System.out.println("Textual.isTxPrimaryUnitStructureOfLang: EOFException");}
		catch (FileNotFoundException e)	{
			System.out.println("Textual.isTxPrimaryUnitStructureOfLang: FileNotFoundException");}
		catch (IOException e){
			System.out.println("Textual.isTxPrimaryUnitStructureOfLang: IOException");}

		//needs work: 3.x is tx_unit_structure???
		if (isNumber(lkt))
			return false;

		if (lng.equals("all"))
		{
			//addLng
			if (isTxPrimaryUnitStructureOfLang("eln",lkt))
				return true;
			else if (isTxPrimaryUnitStructureOfLang("eng",lkt))
				return true;
			else if (isTxPrimaryUnitStructureOfLang("epo",lkt))
				return true;
			else
				return false;
		}

		for(int i=0; i<lkt.length(); i++)
		{
			String cs = String.valueOf(lkt.charAt(i));
			if (lng.equals("eln"))
				result = els.contains(cs);
			else if (lng.equals("eng"))
				result = ens.contains(cs);
			else if (lng.equals("epo"))
				result = eps.contains(cs);
			if (!result)
				return result;
		}
		return result;
	}


	/**
	 * I'm gonna use this method to find if a string is english or greek.
	 *
	 * This method does not works if we want a string among european
	 * languages because they have same letters. 2004.08.13
	 * @modified 2006.02.23
	 * @since 2001.03.17
	 * @author HoKoNoUmo
	 */
	public static boolean isLetterEnglish(String s)
	{
		s=s.toLowerCase();
		if (s.equals("a")||
				s.equals("b")||
				s.equals("c")||
				s.equals("d")||
				s.equals("e")||
				s.equals("f")||
				s.equals("g")||
				s.equals("h")||
				s.equals("i")||
				s.equals("j")||
				s.equals("k")||
				s.equals("l")||
				s.equals("m")||
				s.equals("n")||
				s.equals("o")||
				s.equals("p")||
				s.equals("q")||
				s.equals("r")||
				s.equals("s")||
				s.equals("t")||
				s.equals("u")||
				s.equals("v")||
				s.equals("w")||
				s.equals("x")||
				s.equals("y")||
				s.equals("z") )
			return true;
		else
			return false;
	}


	/**
	 *
	 * @modified 2006.06.03
	 * @since 2006.02.12 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean isNumber(String tkn)
	{
		//english:		3.6 3,600.45 3600.45
		//greek: 		3,6 3.600,45 3600,45
		//.., with this algorithm it is a number!!!
		//Test it as a regular-expression.

/*
		if ( Pattern.matches("\\d+,\\d+", tkn) //1234,55
			|| )

*/
		String nbs = "0123456789.,";
		boolean result=true;

		for(int i=0; i<tkn.length(); i++)
		{
			Character c = new Character(tkn.charAt(i));
			String cs = c.toString();
			result = nbs.contains(cs);
			if (!result)
				return result;
		}
		return result;
	}


	/**
	 * Test if a java-char is a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango_ho.html#foEtoPoQoPunkto">
	 * punctuation</a> in a language.
	 *
	 * @modified 2006.02.22
	 * @since 2006.02.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean isCharTxPunctuation(char ch, String lng)
	{
		String pkt = String.valueOf(ch);
		String[] elp = null;
		String[] enp = null;
		String[] epp = null;
		boolean result=false;
		String ln="";
		String fl= AAj_sDir +"AAjKB" +File.separator
					+"TERMINAL" +File.separator +"textual_strings.xml";
		try {
			FileInputStream fis = new FileInputStream(fl);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-16");
			BufferedReader br = new BufferedReader(isr);
			while ( (ln=br.readLine())!=null )
			{
				if (ln.startsWith("JvCHAR_STOP_SECONDARY_eln"))
					elp=ln.substring(ln.indexOf(" ")).split(" ");
				else if (ln.startsWith("JvCHAR_STOP_SECONDARY_eng")){
					enp=ln.substring(ln.indexOf(" ")).split(" ");
				}
				else if (ln.startsWith("JvCHAR_STOP_SECONDARY_epo"))
					epp=ln.substring(ln.indexOf(" ")).split(" ");
			}
			br.close();
		}
		catch (EOFException e){
			System.out.println("Textual.isCharTxPunctuation: EOFException");}
		catch (FileNotFoundException e)	{
			System.out.println("Textual.isCharTxPunctuation: FileNotFoundException");}
		catch (IOException e){
			System.out.println("Textual.isCharTxPunctuation: IOException");}

		if (lng.equals("eln"))
			result = Util.arrayStringContains(elp, pkt);
		else if (lng.equals("eng"))
			result = Util.arrayStringContains(enp, pkt);
		else if (lng.equals("epo"))
			result = Util.arrayStringContains(epp, pkt);
		return result;
	}


	/**
	 * Tests if a java-char is tx_blankspace.
	 *
	 * @modified 2006.02.19
	 * @since 2006.02.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean isCharTxBlankspace(char ch)
	{
		if (ch==' '  ||
				ch=='\t' ||
				ch=='\n'
			 )
			return true;
		else
			return false;
	}


	/**
	 * Tests if a java-char is a secondary-stop-node (tx_punctuation or tx_blankspace)
	 * in a natural-language.
	 *
	 * @param	lng		A 3 letter language denoted string.
	 * @modified 2006.10.26
	 * @since 2004.08.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean isCharStopSecondary(char ch, String lng)
	{
		//tx_punctuations and tx_blankspaces
		//diferent langetros have diferent delimiters 2006.02.17
		String ln="";
		String enp="";
		String fl= AAj.AAj_sDir +"AAjKB" +File.separator
					+"AAjINDEXES" +File.separator +"textual_strings.xml";
		try {
			FileInputStream fis = new FileInputStream(fl);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-16");
			BufferedReader br = new BufferedReader(isr);
			while ( (ln=br.readLine())!=null )
			{
				if (ln.startsWith("JvCHAR_STOP_SECONDARY_"+lng)){
					enp=ln.substring(ln.indexOf(" "));
					break;
				}
			}
			br.close();
		}
		catch (EOFException e){
			System.out.println("Textual.isCharStopSecondary: EOFException");}
		catch (FileNotFoundException e)	{
			System.out.println("Textual.isCharStopSecondary: FileNotFoundException");}
		catch (IOException e){
			System.out.println("Textual.isCharStopSecondary: IOException");}

		//JvCHAR_STOP_SECONDARY_EN . ! : , ? ; ( ) [ ] ` ' " « »
		enp=enp +" \t\n"; //plus whitespace
		if (enp.contains(String.valueOf(ch)))
			return true;
		else
			return false;
	}


	/**
	 * Tests if a tx_secondary_nonstop_node string is a symbol-name.<p>
	 *
	 * TxSymbolName is a structure of tx_symbols.<p>
	 *
	 * In the file "AAjKB/TERMINAL/tx_symbol.txt" we store
	 * the tx_symbols.
	 *
	 * @modified 2006.10.28
	 * @since 2006.02.13 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static boolean isTxSymbolName(String noStopNd)
	{
		//String nbs = "#$%&*+/<=>...
		boolean result=false;
		String ln="";
		String fl= AAj_sDir +"AAjKB" +File.separator
					+"TERMINAL" +File.separator +"tx_symbol.txt";

		//if is NOT number¦lettero
		if (isNumber(noStopNd))
			return false;
//		else if (isTxPrimaryUnitStructureOfLang("all",noStopNd))
//			return false;
		else {
			for(int i=0; i<noStopNd.length(); i++)
			{
				Character c = new Character(noStopNd.charAt(i));
				String cs = c.toString();
				if (!isNumber(cs) )//&&!isTxPrimaryUnitStructureOfLang("all",cs))
				{
					try {
						FileInputStream fis = new FileInputStream(fl);
						InputStreamReader isr = new InputStreamReader(fis, "UTF-16");
						BufferedReader br = new BufferedReader(isr);
						while ( (ln=br.readLine())!=null )
						{
							if (ln.startsWith(cs))
								return true;
						}
						br.close();
					}
					catch (EOFException e){
						System.out.println("Textual.isTxSymbolName: EOFException");}
					catch (FileNotFoundException e)	{
						System.out.println("Textual.isTxSymbolName: FileNotFoundException");}
					catch (IOException e){
						System.out.println("Textual.isTxSymbolName: IOException");}
				}
			}
		}
		return result;
	}


	/**
	 * not in use.
	 *
	 * Checks if a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango_ho.html#foEtoPoStrukto">
	 * tx_unit_structure</a> is a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango_ho.html#word">
	 * word</a> of a language.<p>
	 *
	 * <b>OUTPUT</b>: a string with the languages the tx_unit_structure is word
	 * and in which file term or word it is found: eny;epd
	 *
	 * @modified 2006.02.24
	 * @since 2006.02.24 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String isWord(TxNode lk)
	{
		//check all the tx_unit_structures if they are words.
		String result="";
		String[] atype=null;
		if (lk.txnd_sType.indexOf(";")!=-1)
			atype=lk.txnd_sType.split(";");
		else
			atype[0]=lk.txnd_sType;
		String idx=lk.txnd_sExpression.substring(0,1);
		boolean found=false;
		BufferedReader br =null;
		String line;

		for(int j=0; j<atype.length; j++)
		{
			String lng = atype[j].substring(atype[j].indexOf(".")+1);//ela has 3 letters
			String fileTerminal=Util.AAj_sDir+"AAjKB" +File.separator
					+"TERMINAL" +File.separator
					+lng +File.separator +"terminal_"+lng+".xml";
			String fileTerm=Util.AAj_sDir+"AAjKB" +File.separator
					+"TERMINAL" +File.separator
					+lng +File.separator +"term_"+lng+"_"+idx+".xml";
			try {
				br = new BufferedReader(new FileReader(fileTerminal));
				//in the file of most-used-words a word only one time exists
				while ((line=br.readLine())!=null && !found){
					if (line.startsWith("<LgYUORDO")){
						if (line.indexOf("TxEXP=\""+lk.txnd_sExpression.toLowerCase()+"\"")!=-1){
							found=true;
							if (result.equals(""))
								result=lng+"y";
							else
								result=result+";"+lng+"y";
						}
					}
				}
				br.close();
			}
			catch (IOException ioe){
				System.out.println("Textual.isWord ioe: fileTerminal");
			}
			if (!found)
			{
				try {
					br = new BufferedReader(new FileReader(fileTerm));
					while ((line=br.readLine())!=null && !found){
						if (line.startsWith("<TRM")){
							if (line.indexOf("TxEXP=\""+lk.txnd_sExpression.toLowerCase()+"\"")!=-1){
								found=true;
								if (result.equals(""))
									result=lng+"d";
								else
									result=result+";"+lng+"d";
							}
						}
					}
					br.close();
				}
				catch (IOException ioe){
					System.out.println("Textual.isWord ioe: fileTerm");
				}
			}
		}
		return result;
	}


	/**
	 * not in use.
	 *
	 * Tests if a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango_ho.html#foEtoPoStrukto">
	 * tx_unit_structure</a> is a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango_ho.html#word">
	 * word</a> of a language.<p>
	 *
	 * <b>OUTPUT</b>: a string with the language the tx_unit_structure is word
	 * PLUS a letter denoting the file (term or tx_auxiliary) where
	 * it is found: end or eni
	 *
	 * @modified 2006.02.24
	 * @since 2006.02.24 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String isWordOf(String lng, TxNode lk)
	{
		String result="";
		String idx=lk.txnd_sExpression.substring(0,1);
		boolean found=false;
		BufferedReader br =null;
		String line;

		String fileTerminal=Util.AAj_sDir+"AAjKB" +File.separator
					+"TERMINAL" +File.separator
					+lng +File.separator +"terminal_"+lng+".xml";
		String fileTerm=Util.AAj_sDir+"AAjKB" +File.separator
					+"TERMINAL" +File.separator
					+lng +File.separator +"term_"+lng+"_"+idx+".xml";
		try {
			br = new BufferedReader(new FileReader(fileTerminal));
			//in the wordfile a word only one time exists
			while ((line=br.readLine())!=null && !found){
				if (line.startsWith("<LgYUORDO")){
					if (line.indexOf("TxEXP=\""+lk.txnd_sExpression.toLowerCase()+"\"")!=-1){
						found=true;
						result=lng+"i";
					}
				}
			}
			br.close();
		}
		catch (IOException ioe){
			System.out.println("Textual.isWord ioe: fileTerminal");
		}
		if (!found)
		{
			try {
				br = new BufferedReader(new FileReader(fileTerm));
				while ((line=br.readLine())!=null && !found){
					if (line.startsWith("<TRM")){
						if (line.indexOf("TxEXP=\""+lk.txnd_sExpression.toLowerCase()+"\"")!=-1){
							found=true;
							result=lng+"d";
						}
					}
				}
				br.close();
			}
			catch (IOException ioe){
				System.out.println("Textual.isWord ioe: fileTerm");
			}
		}
		return result;
	}


	/**
	 * Returns the input string with the first letter capital.
	 * @modified 2004.11.21
	 * @since 2001.02.28
	 * @author HoKoNoUmo
	 */
	public static String setFirstCapital(String frasis)
	{
		String fpart = frasis.substring(0,1);
		String lpart = frasis.substring(1, frasis.length());
		fpart = fpart.toUpperCase();
		return fpart+lpart;
	}


	/**
	 * Removes spaces and tabs from the end of a string.<p>
	 *
	 * I need this method to trim the main-tx_nonstopNodes.
	 * The program identifies main-tx_nonstopNodes only from the amount of \r\n.
	 * @modified
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public static String trimEnd(String str)
	{
		for(;;) {
			if (str.endsWith(" ") || str.endsWith("\t"))
				str=str.substring(0,str.length()-1);
			else
				return str;
		}
	}


}
