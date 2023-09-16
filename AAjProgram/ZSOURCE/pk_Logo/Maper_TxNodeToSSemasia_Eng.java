/*
 * Maper_TxNodeToSSemasia_Eng.java - Inputs a java-eng-Text (subWorldview)
 * and outputs its mapped AAj-java-eng-SSemasial--sub-worldview.
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package pk_Logo;

import java.io.*;
import pk_Util.LabelFactory;
import pk_Util.Util;
import pk_XKBManager.AAj;
import pk_SSemasia.*;

/**
 * Inputs an english-jText and outputs its mapped english-jSSemasia.
 * @modified
 * @since 2004.10.16 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_TxNodeToSSemasia_Eng

{
	/* The text (subWorldview) we want to map to its english-ssemasia */
	public TxNode jTNd;

	public LabelFactory lf;

	/**
	 * @modified
	 * @since 2004.10.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_TxNodeToSSemasia_Eng (TxNode txnd)
	{
		jTNd=txnd;
		lf= new LabelFactory();
	}


	/**
	 * Maps a java-Text to a java-SSemasia.
	 *
	 * @modified 2004.10.20
	 * @since 2001.03.02
	 * @author HoKoNoUmo
	 */
	public SSmNode mapJText()
	{
		if (!jTNd.getName().equals("TEXTNODE")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a SSMA.");
		}

		TxNode	aoc[]=jTNd.getChildrenArray();//arrayOfchildren
		SSmNode	outermostNdm = new SSmNode("SSMA", null);
		outermostNdm.setAttribute("LNG", "eng");

		//The text-model has a sequence of textual-nodes.
		//The sequence denotes its corelatons.

		SSmNode cmn=null;
		if (aoc.length==1)	{
			cmn = mapTxNode(aoc[0], "independent");
			outermostNdm.addChild(cmn);
		}
		else {
			cmn = mapTxNode(aoc[0], "independent");
			outermostNdm.addChild(cmn);
			for(int i=1; i<aoc.length; i++)
			{
				cmn = mapTxNode(aoc[i], "sequence");
				outermostNdm.addChild(cmn);
			}
		}

		return outermostNdm;
	}


	/**
	 * PARAGRAFER: a textual-node which is comprised of a sequence of
	 * tx_periods.
	 *
	 * @modified 2005.09.24
	 * @since 2004.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxParagraph (TxNode prgr, String sntx)
	{
		if ( ! prgr.getName().equals("TxParagraph")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a Paragrapher.");
		}

		SSmNode mprgr= new SSmNode("SmParagraph", null);
		for(int i=0; i<prgr.getNumOfChildren(); i++)
		{
			TxNode chld = prgr.getChild(i);
			SSmNode mchld;
			if (chld.getName().equals("TxSupersentence"))
				mchld = mapTxSupersentence(chld, "independent");
			else
				mchld = mapTxSentence(chld, "independent");
			String lbls=lf.getNextLabel();
			mprgr.setAttribute("LABEL", lbls);
			mprgr.addChild(mchld);
		}
		return mprgr;
	}


	/**
	 * @modified 2004.10.17
	 * @since 2004.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxSupersentence (TxNode frsr, String sntx)
	{
		if ( ! frsr.getName().equals("TxSupersentence")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a TxSupersentence.");
		}
		SSmNode mfrsr= new SSmNode("SmSupersentence", null);

		//A TxSupersentence could be a sentence or corelated-sentences.
		if (frsr.getNumOfChildren()==1){
			SSmNode sentence = mapTxSentence(frsr.getChild(0), "independent");
			String lbls=lf.getNextLabel();
			mfrsr.setAttribute("LABEL", lbls);
			mfrsr.addChild(sentence);
		}


		return mfrsr;
	}


	/**
	 * sntx: independent,
	 * @modified 2004.10.18
	 * @since 2004.10.18 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxSentence (TxNode str, String sntx)
	{
		if (! str.getName().equals("TxSentence")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a TxSentence.");
		}
		SSmNode mstr= new SSmNode("SmSentence", null);
		String lb=lf.getNextLabel();
		mstr.setAttribute("LABEL", lb);

		//every sentence has ONE tx_verb or ONE StrukturoVbr (is he writing).
		//map tx_verb: generate sm_verm
		String lblv="";
		if (str.getChildWithName("Verb_Tx")!=null){
			SSmNode mvrb = mapTxVerb(str.getChildWithName("Verb_Tx"));
			mstr.addChild(mvrb);
			lblv=mvrb.getAttrLabel();

			//map lg_subject: generate smNd and sm_conjunction
			SSmNode msbr = mapTxSubject(str.getChildWithName("TxSubject"));
			String lbls=msbr.getAttrLabel();
			mstr.addChild(msbr);
			SSmNode mcrls = new SSmNode("SmConjunction", mstr);
			mcrls.setAttribute("XCPT", "sm_subjecteino");
			mcrls.setAttribute("ARGS", lblv+","+lbls);
			mstr.addChild(mcrls);
		}

		//map 3rd child: lg_object
		if (str.getChildWithName("Verbelero2")!=null){
		}

		//map 4th child:...
		if (str.getChildWithName("TxObject")!=null){
			SSmNode mobr = mapTxObject(str.getChildWithName("TxObject"));
			mstr.addChild(mobr);
			SSmNode mcrlo = new SSmNode("SmConjunction", mstr);
			mcrlo.setAttribute("XCPT", "sm_objecteino");
			mcrlo.setAttribute("ARGS", lblv+","+mobr.getAttrLabel());
			mstr.addChild(mcrlo);
		}

		return mstr;
	}


	/**
	 * Maps a TxVerb to its SmVerb depending on its attributes.
	 * @modified 2004.10.19
	 * @since 2004.10.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxVerb (TxNode vrb)
	{
		if (! vrb.getName().equals("Verb_Tx")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a TxVerb.");
		}
		String vdesr=vrb.txnd_s_Cpt;
		String vnumber=vrb.txnd_s_Number;
		String vinumber=vrb.tnAttrNumber;
		String viperson=vrb.tnAttrPerson;
		Util.log("\ttx_verb: "+vdesr+","+vnumber+","+vinumber+","+viperson);//

		SSmNode mvrb= new SSmNode("SmVerb",null);
		//<SmVerb LABEL="x1" TRM="Naming@hknu.symb-17.xml" SMaTTR="ind,pre,ins,pass,iper,ning,aff,sin,3"/>
		mvrb.setAttribute("LABEL", lf.getNextLabel());
		mvrb.setAttribute("XCPT", vdesr);

		//to find attr we need number and person (lg_subject I,you,...
		String attr="";
		String ln="";
		String fileIn = Util.AAj_sDir
									+"AAjKB" +File.separator
									+"AAjINDEXES" +File.separator
									+"MapeinoSemasialTextual.eng.xml";
		try {
			FileReader fr= new FileReader(fileIn);
			BufferedReader br= new BufferedReader (fr);
			while ((ln=br.readLine()) != null)
			{
				if (ln.indexOf(","+vinumber+","
					+viperson+"\" LGcPT=\""+vnumber)!=-1) //this is our line
				{
					attr=Util.getXmlAttribute(ln,"SMaTTR");
					break;
				}
			}
			br.close();
		}
		catch (EOFException e){System.out.println("Maper_TxNodeToSSemasia_Eng: EOFException");}
		catch (FileNotFoundException e){System.out.println("Maper_TxNodeToSSemasia_Eng: FNFException");}
		catch (IOException e){System.out.println("Maper_TxNodeToSSemasia_Eng: IOException");}

		mvrb.setAttribute("SMaTTR", attr);

		return mvrb;
	}


	/**
	 * @modified 2004.10.19
	 * @since 2004.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxSubject(TxNode sbr)
	{
		if (! sbr.getName().equals("TxSubject")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a TxSubject.");
		}
		SSmNode msbr=null;
		//?do lg_subjects have always one child?
		//no 2004.10.20
		if (sbr.getNumOfChildren()==1){
			msbr = mapTxNode(sbr.getChild(0), "independent");
			String lbls=lf.getNextLabel();
			msbr.setAttribute("LABEL", lbls);
		}
		return msbr;
	}


	/**
	 * @modified 2004.10.20
	 * @since 2004.10.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxObject(TxNode obr)
	{
		if (! obr.getName().equals("TxObject")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a TxObject.");
		}
		SSmNode mobr=null;
		if (obr.getNumOfChildren()==1){
			mobr = mapTxNode(obr.getChild(0), "independent");
		}
		return mobr;
	}


	/**
	 * Maps a StrukturoTxNounCaseNominativero.
	 * English have only StrukturoTxNounCaseNominativero. 2006.01.10
	 * @modified 2004.10.20
	 * @since 2004.10.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxNounStructure(TxNode nns, String sntx)
	{
		//case nns: (any) (node).
		//case nns: (any node) (of) (a concept-model)
		if ( ! nns.getName().equals("TxNounStructure")) {
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: "
						+"it is not a TxNounStructure.");
		}

		SSmNode mns=new SSmNode("SmNounStructure", null);
		//<SmNounStructure LABEL="b">
		//	<SmNoun LABEL="b1" TRM="Tree-network's-node@hknu.symb-11.xml" SMaTTR="random,sin"/>
		//	<SmSpecialNoun LABEL="b2" TRM="Quantity@hknu.symb-18@" SMaTTR="idef.any,any"/>
		//	<SmConjunction TRM="att.int.property.nounemo" ARGS="b1,b2"/>
		//</SmNounStructure>
		String lbl=lf.getNextLabel();
		mns.setAttribute("LABEL", lbl);

		//IF there is no tx_conjunction-child
		if (nns.getChildWithName("Conjunction_Tx")==null){
			SSmNode mn1 = mapTxNode(nns.getChild(0), "tx_nounAdjective");
			SSmNode mn2 = mapTxNode(nns.getChild(1), "independent");
			mns.addChild(mn1);
			mns.addChild(mn2);
			SSmNode mn3 = new SSmNode("SmConjunction", mns);
			mn3.setAttribute("XCPT", "att.int.property.mo");
			mn3.setAttribute("ARGS", mn2.getAttrLabel()+","+mn1.getAttrLabel());
			mns.addChild(mn3);
		}
		else {
			SSmNode mn1 = mapTxNode(nns.getChild(0), "nominative");
			SSmNode mn2 = mapTxNode(nns.getChild(2), "nominative");
			mns.addChild(mn1);
			mns.addChild(mn2);
			SSmNode mn3 = mapTxConjunction(nns.getChild(1), mn1, mn2);
			mns.addChild(mn3);
		}

		return mns;
	}


	/**
	 * Maps a text-TxNounCaseNominative to its semasial-Node depending on its sntx.
	 *
	 * @modified 2004.10.20
	 * @since 2004.06.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxNounCaseNominative(TxNode nnr, String sntx)
	{
		if ( ! nnr.getName().equals("TxNounCaseNominative")) {
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: "
						+" it is not a TxNounCaseNominative.");
		}

		SSmNode mnr=new SSmNode("SmNoun", null);
		//<SmNoun LABEL="x3" TRM="Concept@hknu.meta-1@.xml" SMaTTR="generic,sin"/>
		mnr.setAttribute("LABEL", lf.getNextLabel());
		mnr.setAttribute("XCPT", nnr.txnd_s_Cpt);
		mnr.setAttribute("SMaTTR", nnr.tnAttrInstanceness+","+nnr.tnAttrNumber);

		return mnr;
	}


	/**
	 * @modified
	 * @since 2004.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxNounCasePossessive(TxNode mns, String sntx)
	{
		if ( ! mns.getName().equals("SmNounStructure")) {
			throw new Exception_SSemasia_Generating(
				"Maper_TxNodeToSSemasia_Eng: It is not a SmNounStructure.");
		}

		SSmNode ndm=null;
		return ndm;
	}


	/**
	 * Maps a TxConjunction to its semasial-TxConjunction.
	 * @modified 2004.10.20
	 * @since 2004.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxConjunction(TxNode cr)
	{
		if ( ! cr.getName().equals("Conjunction_Tx")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a TxConjunction.");
		}
		//<SmConjunction TRM="att.internal.nounemo" ARGS="a,b"/>

		//IF the same tx_conjunction and the same sntx (nominative tx_conjunction nominative)
		//denotes many ssconjunctions
		//THEN the corelaton of its arguments shows us the m-tx_conjunction. 2004.10.23

		//1) if syntax are more than one
		//2) check sntx of arguments (tx_verb, nominative, etc)
		//   if syntax are more than one
		//3) check the corelaton of the concepts of the arguments.

		SSmNode mcr=new SSmNode("SmConjunction", null);

		return mcr;
	}


	/**
	 * @modified 2004.10.30
	 * @since 2004.10.30 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxConjunction(TxNode cr, SSmNode arg1, SSmNode arg2)
	{
		if ( ! cr.getName().equals("Conjunction_Tx")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a TxConjunction.");
		}
		//<SmConjunction TRM="att.internal.nounemo" ARGS="a,b"/>
		SSmNode mcr=new SSmNode("SmConjunction", null);

		//1) if syntax are more than one
		//2) check sntx of arguments (tx_verb, nominative, etc)
		//   if syntax are more than one
		//3) check the corelaton of the concepts of the arguments.

		TxNode word = cr.getChild(0);//most tx-conjunctions have one word.
		if (word.txnd_s_VoSyntax.size()==1){
			String sx = word.txnd_s_VoSyntax.get(0);
			mcr.setAttribute("XCPT", Util.getXmlAttribute(sx,"SMcPT"));
			String[] ar=(Util.getXmlAttribute(sx,"TXeXP1_B")).split(",");
			if (ar[0].startsWith("1"))
				mcr.setAttribute("ARGS", arg1.getAttrLabel()+","+arg2.getAttrLabel());
			else
				mcr.setAttribute("ARGS", arg2.getAttrLabel()+","+arg1.getAttrLabel());
		}

		//in case of many syntax, we could find a generic-rule or|and
		//to write specific code for specific tx-conjunctions that are exceptions
		//of this generic rule. 2004.10.31

		return mcr;
	}


	/**
	 * @modified
	 * @since 2004.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxNounSpecial(TxNode p, String sntx)
	{
		if ( ! p.getName().equals("TxNounSpecial")){
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a TxNounSpecial-node.");
		}
		//<SmSpecialNoun LABEL="b2" TRM="Quantity@hknu.symb-18@" SMaTTR="idef.any,any"/>

		SSmNode mp= new SSmNode("SmSpecialNoun", null);
		String lbl=lf.getNextLabel();
		mp.setAttribute("LABEL", lbl);
		mp.setAttribute("XCPT", p.txnd_s_Cpt);

		String attr="";
		//to find attr we need number and the sntx (nominative, tx_nounAdjective)
		String ln="";
		String fileIn = Util.AAj_sDir
									+"AAjKB" +File.separator
									+"AAjINDEXES" +File.separator
									+"MapeinoSemasialTextual.eng.xml";
		try {
			FileReader fr= new FileReader(fileIn);
			BufferedReader br= new BufferedReader (fr);
			while ((ln=br.readLine()) != null)
			{
				if (sntx.equals("nominative")){
					if (ln.indexOf("SPECIAL_NOMINATIVE=\""+p.txnd_s_Number)!=-1) //this is our line
					{
						attr=Util.getXmlAttribute(ln,"SMaTTR");
						break;
					}
				}
				else if (sntx.equals("tx_nounAdjective")){
					if (ln.indexOf("SPECIAL_ADJECTIVE=\""+p.txnd_s_Number)!=-1) //this is our line
					{
						attr=Util.getXmlAttribute(ln,"SMaTTR");
						break;
					}
				}
			}
			br.close();
		}
		catch (EOFException e){System.out.println("Maper_TxNodeToSSemasia_Eng: EOFException");}
		catch (FileNotFoundException e){System.out.println("Maper_TxNodeToSSemasia_Eng: FNFException");}
		catch (IOException e){System.out.println("Maper_TxNodeToSSemasia_Eng: IOException");}

		mp.setAttribute("SMaTTR", attr);
		return mp;
	}


	/**
	 * SYNTAX:<br/>
	 * 		- independent:<br/>
	 * 		- sequence:
	 * @modified
	 * @since 2004.10.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode mapTxNode(TxNode txnd, String sntx)
	{
		SSmNode ndm=null;
		if (txnd.getName().equals("TxParagraph"))
			ndm=mapTxParagraph(txnd, sntx);
		else if (txnd.getName().equals("TxSupersentence"))
			ndm=mapTxSupersentence(txnd, sntx);
		else if (txnd.getName().equals("TxSentence"))
			ndm=mapTxSentence(txnd, sntx);
		else if (txnd.getName().equals("Verb_Tx"))
			ndm=mapTxVerb(txnd);
		else if (txnd.getName().equals("Conjunction_Tx"))
			ndm=mapTxConjunction(txnd);
		else if (txnd.getName().equals("TxNounStructure"))
			ndm=mapTxNounStructure(txnd, sntx);
		else if (txnd.getName().equals("TxNounCaseNominative"))
			ndm=mapTxNounCaseNominative(txnd, sntx);
		else if (txnd.getName().equals("TxNounCasePossessive"))
			ndm=mapTxNounCasePossessive(txnd, sntx);
		else if (txnd.getName().equals("TxNounSpecial"))
			ndm=mapTxNounSpecial(txnd, sntx);
		else
			throw new Exception_SSemasia_Generating("Maper_TxNodeToSSemasia_Eng: It is not a SSMA-node: " +txnd.getName());
		return ndm;
	}


}
