/*
 * Maper_SSemasiaToText_Eln.java - Generates the greekText of a SSemasia.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2005 Nikos Kasselouris
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
package pk_SSemasia;

import java.io.*;
import pk_XKBManager.AAj;
import pk_Util.Util;
import pk_Util.Textual;
import pk_Logo.Exception_Logo_GeneratingText;

/**
 * It takes a jSSemasia and maps its coresponding Greek-Text.
 * @modified
 * @since 2004.11.01 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_SSemasiaToText_Eln
{
	/** The SSemasia we want to map to greek-text (subWorldview) */
	SSmNode jSSm;

	/**
	 * @modified
	 * @since 2004.11.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_SSemasiaToText_Eln (SSmNode ndm)
	{
		jSSm=ndm;
	}


	/**
	 * Generates the TEXT of a jSSemasia.
	 * @modified
	 * @since 2004.11.01
	 * @author HoKoNoUmo
	 */
	public String mapSSemasia()
	{
		if (!jSSm.getName().equals("SSMA")){
			throw new Exception_Logo_GeneratingText("Maper_SSemasiaToText_Eln: It is not a SSMA.");
		}

		SSmNode	childArgNodes[]=jSSm.getChildArgNodes();
		SSmNode	childCorelNodes[]=jSSm.getChildConjNodes();
		int		numChildCorelNodes=childCorelNodes.length;
		String	logal="";

		if (numChildCorelNodes<1)
		{
			logal=mapSSmNode(childArgNodes[0],"independent");
		}
		else //one or more childCorelNodes.
		{
			for (int rel=0; rel<numChildCorelNodes; rel++)
			{
				SSmNode	corelNode=childCorelNodes[rel];
				String	corelDesignator=corelNode.getAttrXCPT();
				SSmNode	arguments[]=corelNode.getArgNodes();

				if (corelDesignator.equals("sequence")){
					for (int arg=0; arg<arguments.length; arg++)
					{
						String fr = mapSSmNode(arguments[arg], "independent");
						logal = Textual.setFirstCapital(fr) +".	";//a tx_period at the end of a frasis.
					}
				}

				else if (corelDesignator.equals("ifthen")){
					String fr1 = mapSSmNode(arguments[0], "independent");
					String fr2 = mapSSmNode(arguments[1], "independent");
						logal = "If " +fr1 +", " +fr2 +".	 ";
				}
			}
		}
		return logal;
	}


	/**
	 * Returns the TEXT of a SmParagraph.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSmParagraph(SSmNode mns, String syntax)
	{
		if ( ! mns.getName().equals("SmParagraph")) {
			throw new Exception_Logo_GeneratingText(
				"Maper_SSemasiaToText_Eng: It is not a SmNounStructure.");
		}
		String logal="";
		return logal;
	}


	/**
	 * Returns the TEXT of a SmSupersentence.
	 * @modified 2005.09.18
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSmSupersentence(SSmNode jmfr, String syntax)
	{
		if ( ! jmfr.getName().equals("SmSupersentence")) {
			throw new Exception_Logo_GeneratingText(
				"Maper_SSemasiaToText_Eng: It is not a SmSupersentence.");
		}

		String logal="";
		String lgConj="";
		String lbl=jmfr.getAttribute("LABEL");

		SSmNode	childCorel[]=jSSm.getChildConjNodes();
		if (childCorel.length==0){
			//no sm_conjunction, then contains one sm_sentence
			logal=mapSmSentence(jmfr.ssnd_s_Children.get(0),"independent");
		} else {
			for (int c=0; c<childCorel.length; c++)
			{
				SSmNode	corel=childCorel[c];
				SSmNode args[]=corel.getArgNodes();

				//creates the logal according to its ssconjunctions.
				//get syntax of sm_conjunction.
				String sx[]=getSyntaxOfSmConjunction(corel);

				if (sx[2].equals("none")||sx[2].equals("")){
					lgConj = mapSSmNode(args[Integer.parseInt(sx[0])],sx[1])
							+" "+mapSSmNode(args[Integer.parseInt(sx[3])],sx[4]);
				}
				else {
					lgConj = mapSSmNode(args[Integer.parseInt(sx[0])],sx[1])
							+" "+sx[2]
							+" "+mapSSmNode(args[Integer.parseInt(sx[3])],sx[4]);
				}
				logal= logal +" " +lgConj;
			}
		}
		//we must consider and the syntax of this node. 2004.08.03
		Util.log("\ttx_expression of SmSupersentence "+lbl +" = "+logal);
		return logal;
	}


	/**
	 * Returns the TEXT of a SmSentence.
	 * @modified 2004.11.05
	 * @since 2004.11.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSmSentence(SSmNode ssmnd, String syntax)
	{
		if ( ! ssmnd.getName().equals("SmSentence")){
			throw new Exception_Logo_GeneratingText(
				"SmMaperSmSentenceToLogoEn: It is not a SmSentence.");
		}
		String	logal="";							//the logal to be returned.
		String	lg_subject;
		String	lg_object;
		String	lg_agent;							//in case of passive-sm_verm.
		String	lg_subjectcomplement;	 //in case of a corelaton-sm_verm.

		SSmNode sm_verm=ssmnd.getChildWithName("SmVerb");
		SSmNode sm_subject=null;
		SSmNode sm_object=null;
		SSmNode sm_agent=null;

		SSmNode smConj = ssmnd.getChildWithAttrXCPT("sm_subjecteino");
		SSmNode args[] = smConj.getArgNodes();
		sm_subject=args[1];

		smConj = ssmnd.getChildWithAttrXCPT("sm_objecteino");
		if (smConj!=null){
			args = smConj.getArgNodes();
			sm_object=args[1];
		}

		smConj = ssmnd.getChildWithAttrXCPT("sm_agenteino");
		if (smConj!=null){
			args = smConj.getArgNodes();
			sm_agent=args[1];
		}

		SSmNode restSmConjs[] = new SSmNode[9];
		int p=0; //a counter.
		SSmNode relArray[] = ssmnd.getChildConjNodes();
		for (int r = 0; r < relArray.length; r++){
			if ( !(relArray[r].getAttrXCPT().equals("sm_subjecteino"))
					&& !(relArray[r].getAttrXCPT().equals("sm_objecteino"))
					&& !(relArray[r].getAttrXCPT().equals("sm_objecteino2"))
					&& !(relArray[r].getAttrXCPT().equals("sm_agenteino"))
					&& !(relArray[r].getAttrXCPT().equals("sm_subjectcomplementeino"))				)
			{
				restSmConjs[p] = relArray[r];
				p++;
			}
		}

		if (syntax.equals("independent")){
			logal=mapSmVerb(sm_verm);

			//A) if sm_verm is a RELATION-sm_verm, map: s-v-sc
			//B) if sm_verm is a DOING-sm_verm:
			//b1) if it is active map: s-v-o-ar.
			//b2) if it is passive map: s-p-ag-ar.

			lg_subject=mapSSmNode(sm_subject, "nominative");
			logal=lg_subject +" " +logal;

			if (Util.isSpecificOf(sm_verm.getAttribute("XCPT"),"hknu.symb-16"))
			{
				//return lg_subject +v +lg_subjectcomplement + arguments.
				lg_subjectcomplement=mapSSmNode(sm_object, "subjcomplement");
				logal=logal +" " +lg_subjectcomplement;
			}
			else //we have a DOIN-sm_verm
			{
				Util.log("\tsm_verm doing");
				//add lg_object
				if (sm_object!=null){
					lg_object=mapSSmNode(sm_object, "accusative");
					logal=logal +" " +lg_object;
				}
				//add lg_agent:
				if (sm_agent!=null){
					lg_agent=mapSSmNode(sm_agent, "lg_agent");
					if (!lg_agent.equals(""))
						logal=logal +" " +lg_agent;
				}
			}

			//add tx_verb-complements.
			for (int i=0; i<restSmConjs.length; i++)
			{
				if (restSmConjs[i]!=null)
				{
					String pal="";
					SSmNode vcomplement=null;
					SSmNode ars[] = restSmConjs[i].getArgNodes();
					vcomplement = ars[1];
					pal=mapSSmNode(vcomplement, restSmConjs[i].getAttrXCPT());
					logal = logal +" " +pal;
				}
			}

			Util.log("\ttx_expression of SmSentence independent = " +logal);
			logal=logal +".";
		}
		else if (syntax.equals("lg_subject")){
		}
		else if (syntax.equals("lg_object")){
		}
		else if (syntax.equals("subjcomplement")){
		}
		else
			throw new Exception_Logo_GeneratingText(
						"SmMaperSmSentenceToLogoEn.map: It is not a valid syntax.");


		return logal;
	}


	/**
	 * Returns the TEXT of a SmVerb.
	 * @modified 2004.11.04
	 * @since 2004.11.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSmVerb (SSmNode svrb)
	{
		if ( ! svrb.getName().equals("SmVerb")){
			throw new Exception_Logo_GeneratingText("Maper_SSemasiaToText_Eln: It is not a SmVerb.");
		}

		String logal="";
		String sbcpt=svrb.getAttribute("XCPT");
		String attr=svrb.getAttribute("SMaTTR");
		String[] termTxCpt_sAll=Textual.getTermsOfTxConceptAsArrayIfXConcept(sbcpt,"Name_Verb","eln");

		//The greek tx_verb has 54 termTxCpt_sAll (27+27):

		String ln="";
		String fileIn = Util.AAj_sDir +"AAjKB" +File.separator
									+"AAjINDEXES" +File.separator
									+"MapeinoSemasialTextual.eln.xml";
		try {
			FileReader fr= new FileReader(fileIn);
			BufferedReader br= new BufferedReader (fr);
			while ((ln=br.readLine()) != null)
			{
				if (ln.indexOf(attr)!=-1) //this is our line
				{
					logal=Util.getXmlAttribute(ln,"TxEXP");
					break;
				}
			}
			br.close();
		}
		catch (EOFException e){System.out.println("Maper_SSemasiaToText_Eln: EOFException");}
		catch (FileNotFoundException e){System.out.println("Maper_SSemasiaToText_Eln: FNFException");}
		catch (IOException e){System.out.println("Maper_SSemasiaToText_Eln: IOException");}

		logal=logal.replace(',',' ');
		for(int i=1; i<10; i++)
		{
			if (termTxCpt_sAll[i-1].indexOf("|")!=-1){
				String arn[]=termTxCpt_sAll[i-1].split("|");
				logal=logal.replaceAll("trmVrb0"+i+"\\b",arn[0]);
			} else
				logal=logal.replaceAll("trmVrb0"+i+"\\b",termTxCpt_sAll[i-1]);
		}
		for(int i=10; i<55; i++)
		{
			if (termTxCpt_sAll[i-1].indexOf("|")!=-1){
				String arn[]=termTxCpt_sAll[i-1].split("|");
				logal=logal.replaceAll("trmVrb"+i+"\\b",arn[0]);
			} else
				logal=logal.replaceAll("trmVrb"+i+"\\b",termTxCpt_sAll[i-1]);
		}
		Util.log("\ttx_expression of SmVerb "+sbcpt+" = "+logal);
		return logal;
	}


	/**
	 * Returns the TEXT of a SmConjunction.
	 * @modified
	 * @since 2004.11.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSmConjunction(SSmNode ndm)
	{
		if ( ! ndm.getName().equals("SmConjunction")){
			throw new Exception_Logo_GeneratingText("Maper_SSemasiaToText_Eln: It is not a SmConjunction.");
		}
		return "...";
	}


	/**
	 * Returns the TEXT of a SmNounStructure.
	 * @modified
	 * @since 2004.11.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSmNounStructure(SSmNode mns, String syntax)
	{
		if ( ! mns.getName().equals("SmNounStructure")) {
			throw new Exception_Logo_GeneratingText(
				"Maper_SSemasiaToText_Eln: It is not a SmNounStructure.");
		}

		String logal="";
		String lbl=mns.getAttribute("LABEL");
		//every mnstructure has one sm_conjunction. 2004.06.21
		SSmNode corel = mns.getChildWithName("SmConjunction");
		SSmNode args[]=corel.getArgNodes();

		//creates the logal according to its ssconjunctions.
		//get syntax of sm_conjunction.
		String sx[]=getSyntaxOfSmConjunction(corel);

		if (sx[2].equals("none")||sx[2].equals("")){
			logal = mapSSmNode(args[Integer.parseInt(sx[0])],sx[1])
					+" "+mapSSmNode(args[Integer.parseInt(sx[3])],sx[4]);
		}
		else {
			logal = mapSSmNode(args[Integer.parseInt(sx[0])],sx[1])
					+" "+sx[2]
					+" "+mapSSmNode(args[Integer.parseInt(sx[3])],sx[4]);
		}

		//after tx_special_noun we don't put auxiliaries 2004.10.05
		logal=logal.replaceAll("any a","any");
		logal=logal.replaceAll("each a","each");
		logal=logal.replaceAll("κάθε ένας","κάθε");
		logal=logal.replaceAll("κάθε μια","κάθε");
		logal=logal.replaceAll("κάθε ένα","κάθε");


		//we must consider and the syntax of this node. 2004.08.03
		Util.log("\ttx_expression of SmNounStructure "+lbl +" = "+logal);
		return logal;
	}


	/**
	 * Returns the TEXT of a SmNoun.<br/>
	 *
	 * &lt;SmNoun LABEL="b1" TRM="Tree-network's-node@hknu.symb-11.xml" SMaTTR="individual,sin" /&gt;
	 * @param syntax
	 *		It takes the values: nominative, possesiveCase.
	 *		lg_agent, lg_object, lg_subject,
	 *		subjComplement, tx_nounAdjective.
	 *		lg_subject2 (for indefinite with a) 2004.08.03
	 * @modified
	 * @since 2004.11.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSmNoun(SSmNode mnr, String syntax)
	{
		String des=mnr.getAttribute("XCPT");
		if ( ! mnr.getName().equals("SmNoun")) {
			throw new Exception_Logo_GeneratingText("Maper_SSemasiaToText_Eln: "
						+des +" it is not a SmNoun.");
		}

		String logal="";
		String inst=mnr.getAttribute("INSTANCE");
		String attr=mnr.getAttribute("SMaTTR");

		String termTxCpt_sAll[]=Textual.getTermsOfTxConceptAsArrayIfXConcept(des,"Name_NounCase","eln");
		String ln="";
		String fileIn = Util.AAj_sDir +"AAjKB" +File.separator
									+"AAjINDEXES" +File.separator
									+"MapeinoSemasialTextual.eln.xml";
		try {
			FileReader fr= new FileReader(fileIn);
			BufferedReader br= new BufferedReader (fr);
			while ((ln=br.readLine()) != null)
			{
				if (syntax.equals("nominative")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf("LGcPT=\"nn")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
				else if (syntax.equals("genitive")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf("LGcPT=\"ng")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
				else if (syntax.equals("accusative")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf("LGcPT=\"na")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
				else if (syntax.equals("vocative")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf("LGcPT=\"nv")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
			}
			br.close();
		}
		catch (EOFException e){System.out.println("Maper_SSemasiaToText_Eln: EOFException");}
		catch (FileNotFoundException e){System.out.println("Maper_SSemasiaToText_Eln: FNFException");}
		catch (IOException e){System.out.println("Maper_SSemasiaToText_Eln: IOException");}

		logal=logal.replace(',',' ');
		//the greek-tx_noun has 7 termTxCpt_sAll.
		//the first element in the array is the gender.
		String nms[]= new String[8];
		for(int i=0; i<8; i++)
		{
			if (termTxCpt_sAll[i].indexOf("|")!=-1){
				String ar[]=termTxCpt_sAll[i].split("|");
				nms[i]=ar[0];
			} else
				nms[i]=termTxCpt_sAll[i];
		}
		logal=logal.replaceAll("trmNnCsNm1",nms[1]);	//ο δρόμος
		logal=logal.replaceAll("trmNnCsNm2",nms[5]); //οι δρόμοι
		logal=logal.replaceAll("trmNnCsNm3",nms[2]); //του δρόμου
		logal=logal.replaceAll("trmNnCsNm4",nms[6]); //των δρομων
		logal=logal.replaceAll("trmNnCsNm5",nms[3]); //τον δρόμο
		logal=logal.replaceAll("trmNnCsNm6",nms[7]); //τους δρομους
		logal=logal.replaceAll("trmNnCsNm7",nms[4]); //δρόμε

		if (syntax.equals("lg_agent")){
			if (inst.equals("nikkas"))
				logal="";
			else
				//the author of the cptbase is assumed and he is not presented in logal.
				logal="από " +logal;
		}

		//Proper-names have no "the"

		Util.log("\ttx_expression of SmNoun "+des+"= "+logal);
		return logal;
	}


	/**
	 * Returns the TEXT of a SmSpecialNoun.<br/>
	 * &lt;SmSpecialNoun LABEL="b2" TRM="Quantity@hknu.symb-18@" SMaTTR="idef.any,any" /&gt;
	 * @modified
	 * @since 2004.11.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSmSpecialNoun(SSmNode mpnr, String syntax)
	{
		if ( ! mpnr.getName().startsWith("SmSpecial")){
			throw new Exception_Logo_GeneratingText("Maper_SSemasiaToText_Eln: It is not a SmSpecialNoun-node.");
		}

		String logal="";
		String sbcpt=mpnr.getAttribute("XCPT");
		String attr=mpnr.getAttribute("SMaTTR");
		String ln="";
		String fileIn = Util.AAj_sDir +"AAjKB" +File.separator
									+"AAjINDEXES" +File.separator
									+"MapeinoSemasialTextual.eln.xml";
		try {
			FileReader fr= new FileReader(fileIn);
			BufferedReader br= new BufferedReader (fr);
			while ((ln=br.readLine()) != null)
			{
				if (syntax.equals("nominative")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf(sbcpt)!=-1
							&& ln.indexOf("SPECIAL_NOMINATIVE")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
				else if (syntax.equals("accusative")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf(sbcpt)!=-1
							&& ln.indexOf("SPECIAL_ACCUSATIVE")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
				else if (syntax.equals("possesiveCase")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf(sbcpt)!=-1
							&& ln.indexOf("SPECIAL_POSSESSIVE")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
				else if (syntax.equals("tx_nounAdjective")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf(sbcpt)!=-1
							&& ln.indexOf("SPECIAL_ADJECTIVE")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
				else if (syntax.equals("tx_adverb")){
					if (ln.indexOf(attr)!=-1
							&& ln.indexOf(sbcpt)!=-1
							&& ln.indexOf("SPECIAL_ADVERB")!=-1) //this is our line
					{
						logal=Util.getXmlAttribute(ln,"TxEXP");
						break;
					}
				}
			}
			br.close();
		}
		catch (EOFException e){System.out.println("Maper_SSemasiaToText_Eln: EOFException");}
		catch (FileNotFoundException e){System.out.println("Maper_SSemasiaToText_Eln: FNFException");}
		catch (IOException e){System.out.println("Maper_SSemasiaToText_Eln: IOException");}

		Util.log("\ttx_expression of SmSpecialNoun "+sbcpt+"= "+logal);
		return logal;
	}


	/**
	 * Returns the TEXT of a FoEdoNode.
	 * @modified
	 * @since 2004.11.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapSSmNode(SSmNode jmn, String syntax)
	{
		String logal="";
		if (jmn.getName().equals("SmParagraph"))
			logal=mapSmParagraph(jmn, syntax);
		else if (jmn.getName().equals("SmSupersentence"))
			logal=mapSmSupersentence(jmn, syntax);
		else if (jmn.getName().equals("SmSentence"))
			logal=mapSmSentence(jmn, syntax);
		else if (jmn.getName().equals("SmNounStructure"))
			logal=mapSmNounStructure(jmn, syntax);
		else if (jmn.getName().equals("SmVerb"))
			logal=mapSmVerb(jmn);
		else if (jmn.getName().equals("SmNoun"))
			logal=mapSmNoun(jmn, syntax);
		else if (jmn.getName().equals("SmConjunction"))
			logal=mapSmConjunction(jmn);
		else if (jmn.getName().startsWith("SmSpecial"))
			logal=mapSmSpecialNoun(jmn, syntax);
		else
			throw new Exception_Logo_GeneratingText("Maper_SSemasiaToText_Eln: It is not a SSMA-node.");
		return logal;
	}


	/**
	 * Returns the syntax as a string of five parts.<p>
	 *
	 * &lt;SmCONJUNCTION TRM="att.internal.nounemo" ARGS="1,2" XCPT=""
	 * CONJUNCTION="of" TXeXP1_B="2,nominative" TxEXP="of" TXeXP1_A="1,nominative" EXMPL="the engine of the car" /&gt;<p>
	 *
	 * The syntax of a sm_conjunction is an array that contains:<br/>
	 * 1. the number of FIRST argument, we express in the logal.<br/>
	 * 2. the syntax-expression of this argument (nominative, tx_nounAdjective..)<br/>
	 * 3. the expression of the sm_conjunction.<br/>
	 * 4. the number of SECOND argument.<br/>
	 * 5. the syntax-expression of second argument.<br/>
	 *
	 * @modified 2004.11.08
	 * @since 2004.11.01 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String[] getSyntaxOfSmConjunction(SSmNode dnm)
	{
		String sx[]=new String[5];
		String des=dnm.getAttrXCPT();
		Util.log("\tgetSyntaxOfSmConjunction: "+des+" = ");
		String ln="";
		String fileIn = Util.AAj_sDir +"AAjKB" +File.separator
									+"AAjINDEXES" +File.separator
									+"MapeinoSemasialTextual.eln.xml";
		String lnsx="";
		try {
			FileReader fr= new FileReader(fileIn);
			BufferedReader br= new BufferedReader (fr);
			while ((ln=br.readLine()) != null)
			{
				if (ln.indexOf(des+"\"")!=-1) //this is our line
				{
					lnsx=ln;
					break;
				}
			}
			br.close();
		}
		catch (EOFException e){System.out.println("SmMaperTxVerbToLogoEn: EOFException");}
		catch (FileNotFoundException e){System.out.println("SmMaperTxVerbToLogoEn: FNFException");}
		catch (IOException e){System.out.println("SmMaperTxVerbToLogoEn: IOException");}

		String[] ar1=(Util.getXmlAttribute(lnsx,"TXeXP1_B")).split(",");
		String[] ar2=(Util.getXmlAttribute(lnsx,"TXeXP1_A")).split(",");
		if (ar1[0].startsWith("1"))
			sx[0]="0";
		else
			sx[0]="1";
		sx[1]=ar1[1];
		sx[2]=Util.getXmlAttribute(lnsx,"TxEXP");
		if (ar2[0].startsWith("1"))
			sx[3]="0";
		else
			sx[3]="1";
		sx[4]=ar2[1];

		Util.logArrayString(sx);
		return sx;
	}

}
