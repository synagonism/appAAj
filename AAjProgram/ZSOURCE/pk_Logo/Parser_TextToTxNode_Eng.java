/*
 * Parser_TextToTxNode_Eng.java - Parses English-Plain-Text and
 * outputs its java-TxNode.
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package pk_Logo;

import java.io.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.JOptionPane;

import pk_XKBManager.AAj;
import pk_SSemasia.SSmNode;
import pk_Util.LabelFactory;
import pk_Util.Textual;
import pk_Util.Util;

/**
 * Parses English-Plain-TxExpression and CREATES its {@link pk_Logo.TxNode
 * java-TxNode}.<p>
 *
 * CODE:<code><br/>
 * Parser_TextToTxNode_Eng parser = new Parser_TextToTxNode_Eng(context);<br/>
 * TxNode txNd = parser.parseFromReader(reader);</code><p>
 *
 * To write this code I got ideas from Marc De Scheemaecker's
 * NanoXML 2 Lite.
 *
 * @modified 2008.11.06
 * @since 2008.11.06 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Parser_TextToTxNode_Eng extends Parser_Text
{

	/**
	 * @param cntxt	The brainual--sub-worldview the text describes.
	 * @modified
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Parser_TextToTxNode_Eng(String cntxt)
	{
		super(cntxt, "eng");
	}


	/**
	 * Reads english-plain-TxExpression from a java.io.Reader
	 * and outputs its {@link pk_Logo.TxNode java-TxNode}.<p>
	 *
	 * The input-txExpression describes anyone's-subWorldview
	 * but the output jTxNode is understood through the AAj's
	 * <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/aaj-sbccs.html#nlKB">
	 * XKnowledgeBase</a>.<p>
	 *
	 * @param reader	The Reader which contains the input plain-tx_expression.
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode parseFromReader(Reader reader)throws IOException
	{
		//outermost node
		TxNode txNdOutMost = new TxNode("TEXTNODE", null, "eng");
		String mainNoStpNd_s_expr;
		TxNode txNd=null;

		for (;;) {
			tmpbuf.setLength(0);
			char ch='n';
			try {
				ch = scanTxNonstopnodeMain(reader, tmpbuf);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			mainNoStpNd_s_expr=tmpbuf.toString();
			mainNoStpNd_s_expr=Textual.trimEnd(mainNoStpNd_s_expr);

			//only one main-tx_nonstopNode:
			//tx_period_s_whole, tx_period, tx_period_s_part
			String mnstopType = findTypeOfTxNonstopNodeMain(mainNoStpNd_s_expr);
			Util.log("\tPERIOD'S-WHOLE-NONSTOP-NODE: "+mnstopType +" = "+mainNoStpNd_s_expr);

			if (mnstopType.equals("tx_period_s_whole")){
//				txnode=identifyTxPeriod_s_Whole(mainNoStpNd_s_expr, lineNrSrc, outermostNode, context);
//				outermostNode.addChild(txnode);
			}
			else if (mnstopType.equals("tx_period")){
				TxNode tnp = new TxNode("TxPeriod", txNdOutMost, "eng");
				tnp.txnd_sExpression=mainNoStpNd_s_expr;
				tnp=identifyTxPeriod(tnp, lineNrSrc, txNdOutMost, context);
				txNdOutMost.addChild(tnp);
			}
			else if (mnstopType.equals("tx_period_s_part")){
//				txnode=identifyTxPeriod_s_Part(mainNoStpNd_s_expr, lineNrSrc, outermostNode, context);
//				outermostNode.addChild(txnode);
			} else
				throw throwSyntaxError("Unknown main-tx_nonstopNode");

			if (ch=='\u0000')
				return txNdOutMost;
		}

	}


	/**
	 * <b>INPUT</b>: the expression of a main_tx_nonstop_node.<br/>
	 * <b>OUTPUT</b>: its <code>type</code>:
	 * (tx_period, tx_period_s_whole, tx_period_s_part)<p>
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String findTypeOfTxNonstopNodeMain(String tk)
	{
		if (tk.endsWith(".") || tk.endsWith(":") ||
				tk.endsWith("?") || tk.endsWith("!") ) {
			//it is a tx_period or tx_period_s_whole
			if ((tk.indexOf(".")!=-1 && tk.indexOf(".")<tk.length()-2) ||
					(tk.indexOf(":")!=-1 && tk.indexOf(":")<tk.length()-2) ||
					(tk.indexOf("?")!=-1 && tk.indexOf("?")<tk.length()-2) ||
					(tk.indexOf("!")!=-1 && tk.indexOf("!")<tk.length()-2) )
				return "tx_period_s_whole";
			else
				return "tx_period";
		} else
				return "tx_period_s_part"; //tx_noun-structure or tx_verb or tx_title
	}


	/**
	 * <b>INPUT</b>:<br/>
	 *  - a tx_period node (its expression ends with a dot and does not include a dot),
	 * 			we know only its expression,<br/>
	 *  - its line-number in the source,<br/>
	 *  - its parent TxNode, and<br/>
	 *  - a string indicating is context (its mapped brainual--sub-worldview).<p>
	 *
	 * <b>OUTPUT</b>:<br/>
	 *  - its content-TxNode (a tx_supersentence or a tx_sentence) we create
	 * after identifying it.
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode identifyTxPeriod(TxNode tx_period, int lineNr,
																TxNode parentNd, String cntxt)
	{
		lineNrSrcBeg=lineNr;
		context=cntxt;

		//findChildTxSecondaryNodes
		try {
			tx_period=findChildTxSecondaryNodes(tx_period);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		tx_period.toLog();

		//terminals: terms, auxiliaries, symbolnames
		tx_period=findChildTxTerminals(tx_period);
		//logs the TERMINALS of the tx_period. nnn
		Vector voy=tx_period.txnd_sVoChildren;
		Util.log("\tTxPERIOD'S TxTERMINASL:");
		for(int i=0; i<voy.size(); i++) {
			TxNode tmnl = (TxNode) voy.get(i);
			Util.log("\t"+tmnl.txnd_sExpression+":  syntax="+tmnl.txnd_s_VoSyntax.size());
			for(int k=0; k<tmnl.txnd_s_VoSyntax.size(); k++) {
				String r=tmnl.txnd_s_VoSyntax.get(k);
				Util.log("\t\t"+r);
			}
		}

		//next: tx_concept identification
		tx_period=findChildTxConcepts(tx_period);
//Vector voc=tx_period.txnd_sVoChildren;//nnn
//for(int c=0; c<voc.size(); c++) {
//	TxNode tl = (TxNode) voc.get(c);
//	System.out.println(tl.txnd_sExpression+": "+tl.txnd_sType);
//}

		//next: tx_conjunction-structures.
		tx_period=findChildTxCptStructures(tx_period);
		//txnode.toLog();

		//next: tx_verb (sm_verm)
		//next: ssconjunctions (constituents)
		//next: txnodes.
		tx_period=findChildTxVerb_s_Arguments(tx_period, parentNd);
//		tx_period.toLog();


		//a) if the first word is not a CONJUNCTION, THEN lg_subject begins.
		//if (checkTxSyntaxOfTerminalExactly((TxNode)voy.get(0),"TRMNLtYPE","nmc"))
		//	idxsb=0; //lg_subject-begins at index 0

		//find the sentecer's tx_verb.
		//tx_verb=findTxVerbOfTxSentence(voy);

		//find the concept's syntax
		//[name] {call:pas} @entity@ $by !person!$ = [Concept] {is called} @...@
		//[entity] {call:pas} @name@ $by !person!$ = [...] {is called} @concept@
		//#s:name #vrb:call=pas #vc:entity #by$:person #ex:[Concept] {is called} @...@

		return tx_period;
	}


	/**
	 * <b>INPUT</b>: a main-tx_nonstopNode with no children, only its tx_expression.<br/>
	 * OUPTUT: the same node with its secondary-tx_nonstopNodes and its
	 * secondary-tx_stopNodes (tx_blankspaces and tx_punctuations).
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode findChildTxSecondaryNodes(TxNode tnIn) throws IOException
	{
		Reader reader = new StringReader(tnIn.txnd_sExpression);
		String tktrEks =""; //the tx_expression of tx_nonstopNode.

		//finds the ONE or MORE next tx_stopNodes and ONE tx_nonstopNode.
		TxNode tnNew=null;

		for (;;) {
			char ch = scanChar(reader);

			//case1
			if (ch=='\u0000'){
				if (!tktrEks.equals("")){
					tnNew = new TxNode("TxNonstopNode",tnIn,"eng");
					tnNew.txnd_sExpression=tktrEks;
					tnIn.addChild(tnNew);
					tnIn.txnd_sVoTxSecondaryNode.add(tnNew);
				}
				break;
			}

			//case2
			else if (Textual.isCharTxPunctuation(ch,"eng")){
				if (tktrEks.equals("")){
					tnNew = new TxNode("TxPunctuation",tnIn,"eng");
					tnNew.txnd_sExpression=String.valueOf(ch);
					tnIn.addChild(tnNew);
					tnIn.txnd_sVoTxSecondaryNode.add(tnNew);
				}
				else {
					tnNew = new TxNode("TxNonstopNode",tnIn,"eng");
					tnNew.txnd_sExpression=tktrEks;
					tktrEks = "";
					tnIn.addChild(tnNew);
					tnIn.txnd_sVoTxSecondaryNode.add(tnNew);

					tnNew = new TxNode("TxPunctuation",tnIn,"eng");
					tnNew.txnd_sExpression=String.valueOf(ch);
					tnIn.addChild(tnNew);
					tnIn.txnd_sVoTxSecondaryNode.add(tnNew);
				}
			}

			//case3
			else if (Textual.isCharTxBlankspace(ch)){
				if (tktrEks.equals("")){
					scanCharBack(reader,ch);
					tmpbuf.setLength(0);
					ch=scanTxBlankspace(reader,tmpbuf);
					tnNew = new TxNode("TxBlankspace",tnIn,"eng");
					tnNew.txnd_sExpression=tmpbuf.toString();
					tnIn.addChild(tnNew);
					tnIn.txnd_sVoTxSecondaryNode.add(tnNew);
					scanCharBack(reader,ch);
				}
				else {
					tnNew = new TxNode("TxNonstopNode",tnIn,"eng");
					tnNew.txnd_sExpression=tktrEks;
					tktrEks = "";
					tnIn.addChild(tnNew);
					tnIn.txnd_sVoTxSecondaryNode.add(tnNew);

					scanCharBack(reader,ch);
					tmpbuf.setLength(0);
					ch=scanTxBlankspace(reader,tmpbuf);
					tnNew = new TxNode("TxBlankspace",tnIn,"eng");
					tnNew.txnd_sExpression=tmpbuf.toString();
					tnIn.addChild(tnNew);
					tnIn.txnd_sVoTxSecondaryNode.add(tnNew);
					scanCharBack(reader,ch);
				}
			}

			//case4: a letter or symbol
			else
				tktrEks = tktrEks+String.valueOf(ch);
		}
		return tnIn;
	}


	/**
	 * <b>INPUT</b>: a node with children at least tx_concepts and tx_stopNodes.<p>
	 * <b>OUTPUT</b>: the same node with its parts of tx_period
	 * (tx_verb(s) and tx_verb_s_arguments).
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode findTxPeriod_s_Parts(TxNode tnIn)
	{
		//FIRST PART:
		//::mdt: >>Concept<< is called every node of a concept-model.
		Vector<TxNode> vch = tnIn.txnd_sVoChildren;
		TxNode ch = vch.get(0);
		Hashtable<TxNode,String> htStr = findTxStructure(ch,tnIn);
		//replaceStrukturo
		TxNode tnNew = null;
		String cnsm = null;
		if (!htStr.isEmpty()) {
			tnNew = htStr.keys().nextElement();
			cnsm = htStr.elements().nextElement();
			if (cnsm.equals("interchange"))
				tnIn.interchangeChild(ch,tnNew);
			else
				tnIn.replaceChild(ch,tnNew,Integer.parseInt(cnsm));
			tnIn.txnd_sVoOutermostPart.add(tnNew);
		}
		else
			tnIn.txnd_sVoOutermostPart.add(ch);
		//if next-node is tx_verb, THEN the previous is lg_subject
		if (tnIn.followsChild(0,"Verb_Tx")) {
			ch = vch.get(0);
			TxNode prt = new TxNode("TxSubject",tnIn,"eng");
			prt.txnd_sExpression=ch.txnd_sExpression;
			tnIn.replaceChild(ch,prt,1);
			tnIn.txnd_sVoOutermostPart.add(prt);
			tnIn.txnd_sVoOutermostPart.add(tnIn.getChildNextTxNonstopNode(0)); //tx_verb
		}
		//case12: tx_special_noun
		//::mdt: >>I<< don't drink much. [wn 2.0]
		//case13: structure-tx_conjunction
		//::mdt: >>in most people<< the speech center is in the left hemisphere. [wn 2.0]
		//case14: structure-onomero
		//::mdt: #sbj:>>A piece of pepperoni pizza<< #vrb:would satisfy #obj:his hunger.
		//case15: structure-tx_special_noun.
		//::mdt: #sbj:>>That car<< #vrb:runs #vba:fast.
		//case16: structure-sentesero
		//::mdt: #sbj:>>the planet (the spacecraft flew behind)<< #vrb:has #sjc:a strong magnetic field.


		//SECOND PARTETRO:
		ch = tnIn.getChildNextTxNonstopNode(3);
		htStr = findTxStructure(ch,tnIn);
		if (!htStr.isEmpty()) {
			tnNew = htStr.keys().nextElement();
			cnsm = htStr.elements().nextElement();
			tnIn.replaceChild(ch, tnNew, Integer.parseInt(cnsm));
		}
		else
			Util.log("NO structure for : "+ch.txnd_sExpression);

		return tnIn;
	}


	/**
	 * <b>INPUT</b>: a node (the parentNd) with children tx_concepts or structures
	 * and a child.
	 * <b>OUTPUT</b>: the structure this child CAN create with its neighbors.<p>
	 *
	 * If isEmpty(), the child does NOT create a structure.
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Hashtable<TxNode,String> findTxStructure(TxNode ch, TxNode parentNd)
	{
Util.log("> find structure of: "+ch.txnd_sExpression);
		Hashtable<TxNode,String> htSkt = new Hashtable<TxNode,String>();
		Vector<TxNode> vok = parentNd.txnd_sVoChildren;
		int i = vok.indexOf(ch);

		//CASE1: NOUNS
		if (ch.txnd_sType.equals("TxNoun")){
			TxNode ch2 = vok.get(i+2);

			//case11: follows tx_conjunction
			// >>node<< of model
			if (ch2.txnd_sType.equals("Conjunction_Tx")){
				Hashtable<TxNode,String> htKrr = findTxStructureConjunction(ch2,parentNd);
				if (!htKrr.isEmpty()){
					TxNode tnNew = htKrr.keys().nextElement();
					String cm = htKrr.elements().nextElement();
					Util.log(">>> tx_conjunction's consumed: "+cm);
					htKrr.remove(tnNew);
					if (cm.indexOf(";")!=-1){
						String[] ar = cm.split(";");
						TxNode tnBefore = vok.get((i+2)-Integer.parseInt(ar[0]));
						Util.log(">>> tx_conjunction's before: "+tnBefore.txnd_sExpression);
						parentNd.replaceChild(tnBefore, tnNew, Integer.parseInt(ar[0])+1+Integer.parseInt(ar[1]));
					}
//					htSkt.put(tnNew,"1");
					return htSkt;
				}
				else
					Util.log(">> no valid syntax of tx_conjunction ");
			}
			//case12: follows tx_verb ==> stop.
			//::mdt: #sbj:My >>brother<< #vrb:is #sjc:a research associate.
		}

		//CASE2: TxADJECTIVES
		else if (ch.txnd_sType.equals("NounAdjectiveTx"))
			return findTxStructureAdjective(ch,parentNd);

		//CASE3: TxADVERBS:

		//CASE4: tx_special_nouns:
		else if (ch.txnd_sType.startsWith("Special"))
			return findTxStructureSpecialNoun(ch,parentNd);

		//CASE4: TxVERBS: leave it as it is. 2008.11.06

		return htSkt;
	}


	/**
	 * <b>INPUT</b>: a node (the parentNd) with children tx_concepts or structures
	 * and a TxADJECTIVE child.
	 * <b>OUTPUT</b>: the structure this child CAN create with its neighbors.<p>
	 *
	 * If isEmpty(), the child does NOT create a structure.
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Hashtable<TxNode,String> findTxStructureAdjective(TxNode ch, TxNode parentNd)
	{
Util.log("> find structure of tx_nounAdjective: "+ch.txnd_sExpression);
		Hashtable<TxNode,String> htSkt = new Hashtable<TxNode,String>();
		Vector<TxNode> vok = parentNd.txnd_sVoChildren;
		int i = vok.indexOf(ch);

		//CASE1: An ADJECTIVE which is AND tx_noun followed by
		//a tx_verb, it is a tx_noun.
		//EXMPL: Concept is-called ...
		//The HToTxConcept contains a tx_noun and tx_nounAdjective
		boolean b1 = ch.containsTxCpt_s_Type1("TxNoun");
		//Follows tx_verb
		boolean b2 = parentNd.followsChild(i,"Verb_Tx");
//Util.log(" tx_nounAdjective:"+b1+", "+b2);
		if (b1 && b2){
			TxNode tnNew = ch.txnd_s_HToTxConcept.keys().nextElement();
			htSkt.put(tnNew,"interchange");
			return htSkt;
		}

		//continue ADJECTIVE

		return htSkt;
	}


	/**
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Hashtable<TxNode,String> findTxStructureConjunction(TxNode ch, TxNode parentNd)
	{
Util.log("> find structure of tx_conjunction: "+ch.txnd_sExpression);
		//RULE: the tx_syntax of the tx_verb helps to figure out
		//the structure a tx_conjunction is created.
		//...node of a concept-model.
		//#s:the economy #vrb:has benefited #fromwhat:from a boom in tourism.
		//::mdt: #sbj:My father-in-law #vrb:locked #obj:his keys
		//#place:in the trunk of a borrowed car.


		//the problem is ONLY to figure out if a prepositional-phrase creates
		//a STRUKTURO with previous structure or is a verb's-argument.

		Hashtable<TxNode,String> htKrr = new Hashtable<TxNode,String>();
		Vector<TxNode> vok = parentNd.txnd_sVoChildren;
		int i = vok.indexOf(ch);

		//first find structure of node AFTER tx_conjunction
		TxNode post = vok.get(i+2);
		//post has the same parentNd with child
		Hashtable<TxNode,String> htStr = findTxStructure(post,parentNd);
		//replaceStrukturoFound
		TxNode tnNew = null;
		String cnsm = null;
		if (!htStr.isEmpty()) {
			tnNew = htStr.keys().nextElement();
			cnsm = htStr.elements().nextElement();
			if (cnsm.equals("interchange")){
				parentNd.interchangeChild(post,tnNew);
			}
			else {
				int icnsm = Integer.parseInt(cnsm);
				parentNd.replaceChild(post,tnNew,icnsm);
			}
			htStr.remove(tnNew);
			if (!htStr.isEmpty())
				post.txnd_s_HToStrukturo.putAll(htStr);
		}

		Vector<String> voStx = ch.getTxSyntaxFromConcept();
		//<SNTX NDB1="TxNounStructure" NDA1="StukturoOnr" EXMPL=""/>
		for (String st : voStx){
			Hashtable<TxNode,String> htstk = isValidTxSyntaxConjunction(st,vok,i);
			if (!htstk.isEmpty()){
				//store all the valid tx_syntax
				TxNode nd = htstk.keys().nextElement();
				String csm = htstk.elements().nextElement();
				htStr.put(nd, csm);
			}
		}
		return htStr;
	}


	/**
	 * <b>INPUT</b>: a vector of nodes, at least tx_concepts, the index of
	 * a tx_conjunction and its syntax.<p>
	 * <b>OUTPUT</b>: A Hashtable with KEY the structure created and VALUE
	 * the number of the consumed tx_concepts before and after it
	 * in the form: "n1;n2".<p>
	 *
	 * If isEmpty(), we can not create a structure.
	 *
	 * @param stx	The syntax of the tx_conjunction.
	 * @param vok	The vector of tx_concepts.
	 * @param i		The index of the tx_conjunction in the vok.
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Hashtable<TxNode,String> isValidTxSyntaxConjunction(String stx, Vector<TxNode> vok, int i)
	{
		//<SNTX NDB1="TxNounStructure;Entity@hknu.symb-7@"
		//NDA1="StukturoOnr;Attribute@hknu.symb-21@" EXMPL=""/>
Util.log(">> sintaksTxConjunction: "+stx);
		Hashtable<TxNode,String> htKrr = new Hashtable<TxNode,String>();
		TxNode k = vok.get(i);

//Validating a SYNTAX we MUST validate not only the
//SintaksERO: structure tx_conjunction stukturo
//but AND its SintaksEPTO: Entity@ Attribute@
//nikkas, 2008.11.06

		String nnb=""; //Number of Nodes Before.
		String nna=""; //Number of Nodes After.
		TxNode tnOut;

		String[] sltrb = new String[4];//String of Textual Before
		String[] sltra = new String[4];

		String[] sbptb = new String[4];//String of Brainepto Before
		String[] sbpta = new String[4];

//		TxNode[] ndb = new TxNode[4];
//		TxNode[] nda = new TxNode[4];
		TxNode ndb0=null; //Node before
		TxNode ndb1=null;
		TxNode ndb2=null;
		TxNode ndb3=null;
		TxNode nda0=null;
		TxNode nda1=null;
		TxNode nda2=null;
		TxNode nda3=null;

		for (int j=0; j<5; j++){
			if (stx.indexOf("NDB"+(j+1))!=-1){
				String st=Util.getXmlAttribute(stx,"NDB"+(j+1));
				sltrb[j]=st.substring(0,st.indexOf(";"));
				sbptb[j]=st.substring(st.indexOf(";")+1);
			}
		}
		for (int j=0; j<5; j++){
			if (stx.indexOf("NDA"+(j+1))!=-1){
				String st=Util.getXmlAttribute(stx,"NDA"+(j+1));
				sltra[j]=st.substring(0,st.indexOf(";"));
				sbpta[j]=st.substring(st.indexOf(";")+1);
			}
		}
//Util.printArrayString(sltrb);
//Util.printArrayString(sbptb);
//Util.printArrayString(sltra);
//Util.printArrayString(sbpta);

		//CASE1
		if (sltrb[0]==null){
			nnb="";
			if (sltra[0]==null){
				nna="";
				tnOut= new TxNode(k.txnd_sType,null,"eng");
				htKrr.put(tnOut,nnb+";"+nna);
				return htKrr;
			}
			else if (sltra[1]==null){
				//ppp to be continue
			}
		}

		//CASE2
		else if (sltrb[1]==null){
			nnb="2"; //node1b and stop-node
			if (i-2<0)
				return htKrr;
			else {
				ndb0 = vok.get(i-2);
				if (!ndb0.isType(sltrb[0]))
						return htKrr;
				//
				if (sltra[0]==null){
					nna="";
					tnOut= new TxNode("TxNounStructure",null,"eng");
					htKrr.put(tnOut,nnb+";"+nna);
					return htKrr;
				}
				else if (sltra[1]==null){
					nna="2";
					if (i+2>vok.size())
						return htKrr;
					else {
						nda0 = vok.get(i+2);
						if (!nda0.isType(sltra[0]))
								return htKrr;
						tnOut= new TxNode("TxNounStructure",null,"eng");
						htKrr.put(tnOut,nnb+";"+nna);
						return htKrr;
					}
				}
				else if (sltra[2]==null){
				}
			}
		}
		//CASE3
		else if (sltrb[2]==null){
			nnb="4";
			if (i-4<0)
				return null;
			else {
				ndb1 = vok.get(i-4);
				if (!ndb1.isType(sltrb[1]))
						return htKrr;
			}
		}

		//IF sbpt1=entity and sbpt2=atribo
		//THEN we must check if Util.isAtriboOf(nodo2.txnd_s_Cpt,nodo1.txnd_s_Cpt)
		return htKrr;
	}


	/**
	 * <b>INPUT</b>: a node with children at least tx_concepts AND a tx_special_noun.
	 * <b>OUTPUT</b>: the structure the tx_special_noun CAN create with its neighbors
	 * and the number of nodes consumed.<p>
	 *
	 * If isEmpty(), the child does NOT create a structure.
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Hashtable<TxNode,String> findTxStructureSpecialNoun(TxNode ch, TxNode parentNd)
	{
Util.log("> find structure of tx_special_noun: "+ch.txnd_sExpression);
		Hashtable<TxNode,String> htSkt = new Hashtable<TxNode,String>();
		Vector<TxNode> vok = parentNd.txnd_sVoChildren;
		int i = vok.indexOf(ch);

		//CASE1: SPECIAL_NOMINATIVES:
		//::mdt: #sbj:>>I<< myself #vrb:believe #obj:that ...
		//::mdt: #sbj:>>you<< #vrb:can find #what:this food #place:anywhere.
		//::mdt: #sbj:I #vrb:destroyed #obj:>>everything<<.

		//CASE2: PROAKUSATIVEROS:
		//::mdt: #sbj:That she is failing #vrb:puzzles #obj:>>me<<.

		//CASE3: PROPOZESIVEROS:

		//CASE4: SPECIAL_ADJECTIVES:
		//RULE: a TxNounSpecialAdjective creates a structure with its next node.
		//EXMPL: >>every<< hierarchy-node of a concept-model.
		//EXMPL: he goes #where:south #when:>>every<< winter #why:for the golfing. [wn 2.0]
		//::mdt: #sbj:>>My<< brother #vrb:is #sjc:a research associate.
		if (ch.txnd_sType.equals("TxNounSpecialAdjective")){
			//i+1 ==> TxBlankspace
			TxNode ch2 = vok.get(i+2);
			Hashtable<TxNode,String> ht = findTxStructure(ch2,parentNd);
			if (!ht.isEmpty()){
				//1.create the next structure
				//2.create the new structure with protx_nounAdjective.
				TxNode tnNew = new TxNode("TxNounStructure", parentNd, "eng");
				htSkt.put(tnNew,"3");
				return htSkt;
			}
			else {
				//CAN create structure with next node;
				TxNode tnNew = new TxNode("TxNounStructure", parentNd, "eng");
				htSkt.put(tnNew,"3");
				return htSkt;
			}
		}

		//CASE5: SPECIAL_ADVERBS
		//RULE: a tx_special_noun in the begining, is a node by itself:
		//EXMPL: #time:Today #sbj:we #vrb:will look #place:at where we live.

		return htSkt;
	}


//**********************************************************************
//**********************************************************************
//**********************************************************************


/**
	 * Returns the next NON-STOP node.
	 * IF it is <CODE>null</CODE>, then we reached the end.
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode scanTxNonstopNode(Reader reader, TxNode tnIn)throws IOException
	{
		String tktrEks =""; //the tx_expression of tx_nonstopNode.

		//finds the ONE or MORE next tx_stopNodes and ONE tx_nonstopNode.
		TxNode tnTxBlankspace = null;
		TxNode tnTxPunctuation = null;
		TxNode tnTxNonstopNode = null;

		for (;;) {
			char ch = scanChar(reader);

			//case1
			if (ch=='\u0000'){
				if (!tktrEks.equals("")){
					tnTxNonstopNode = new TxNode("TxNonstopNode",tnIn,"eng");
					tnTxNonstopNode.txnd_sExpression=tktrEks;
					tnIn.addChild(tnTxNonstopNode);
					return tnTxNonstopNode;
				}
				else
					return null;
			}

			//case2
			else if (Textual.isCharTxPunctuation(ch,"eng")){
				if (tktrEks.equals("")){
					tnTxPunctuation = new TxNode("TxPunctuation",tnIn,"eng");
					tnTxPunctuation.txnd_sExpression=String.valueOf(ch);
					tnIn.addChild(tnTxPunctuation);
				}
				else {
					tnTxNonstopNode = new TxNode("TxNonstopNode",tnIn,"eng");
					tnTxNonstopNode.txnd_sExpression=tktrEks;
					tnIn.addChild(tnTxNonstopNode);

					tnTxPunctuation = new TxNode("TxPunctuation",tnIn,"eng");
					tnTxPunctuation.txnd_sExpression=String.valueOf(ch);
					tnIn.addChild(tnTxPunctuation);
					return tnTxNonstopNode;
				}
			}

			//case3
			else if (Textual.isCharTxBlankspace(ch)){
				if (tktrEks.equals("")){
					scanCharBack(reader,ch);
					tmpbuf.setLength(0);
					ch=scanTxBlankspace(reader,tmpbuf);
					tnTxBlankspace = new TxNode("TxBlankspace",tnIn,"eng");
					tnTxBlankspace.txnd_sExpression=tmpbuf.toString();
					tnIn.addChild(tnTxBlankspace);
					scanCharBack(reader,ch);
				}
				else {
					tnTxNonstopNode = new TxNode("TxNonstopNode",tnIn,"eng");
					tnTxNonstopNode.txnd_sExpression=tktrEks;
					tnIn.addChild(tnTxNonstopNode);

					scanCharBack(reader,ch);
					tmpbuf.setLength(0);
					ch=scanTxBlankspace(reader,tmpbuf);
					tnTxBlankspace = new TxNode("TxBlankspace",tnIn,"eng");
					tnTxBlankspace.txnd_sExpression=tmpbuf.toString();
					tnIn.addChild(tnTxBlankspace);
					scanCharBack(reader,ch);
					return tnTxNonstopNode;
				}
			}

			//case4: a letter or symbol
			else
				tktrEks =tktrEks+String.valueOf(ch);
		}
	}


	/**
	 * Scans for the next english-stop-node-character from the reader.
	 * The scanned tx_expression is appended to <code>result</code>.
	 *
	 * @return
	 * 		The NEXT english-stop-node-character found.
	 * 		If it is '\u0000' we have reach the end of string.
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public char scanTxNonstopNodeCharEng(Reader reader, StringBuffer result)throws IOException
	{
		for (;;) {
			char ch = scanChar(reader);
			//stops when finds not a "character".
			if (ch=='\u0000'){
				return ch;
			}
			else if (Textual.isCharStopSecondary(ch,"eng"))
				return ch;
			else
				result.append(ch);
		}
	}


	/**
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode identifyTxPeriod_s_Whole(String sprd, int lineNr,
																	TxNode parentNd, String cntxt)
	{
		TxNode txnode = new TxNode("TxParagraph", parentNd, "eng");
		String tx_nonstopNode;
		Reader reader = new StringReader(sprd);

		for (;;) {
			tmpbuf.setLength(0);
			char ch='n';
			try {
				ch = scanTxPeriod(reader, tmpbuf);
			} catch (IOException e) {
				System.out.println(e.toString());
			}
			String spr = tmpbuf.toString();
//			TxNode pr = identifyTxPeriod(spr,lineNrSrcBeg,txnode,cntxt);
//			txnode.addChild(pr);
			if (ch=='\u0000')
				return txnode;
		}
	}


	/**
	 * Scans the next tx_period-stopChar from the reader.
	 * The scanned text is appended to <code>result</code>.
	 *
	 * @return
	 * 		The NEXT stop_tx_period-character found.
	 * 		If it is '\u0000' we have reach the end of string.
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public char scanTxPeriod(Reader reader, StringBuffer result) throws IOException
	{
		for(;;) {
			char ch = scanChar(reader);
			if (ch=='\u0000'){
				return ch;
			}
			else if (isCharTxPeriodStop(ch)){
				result.append(ch);
				ch = scanTxBlankspace(reader);
				return ch;
			}
			else
				result.append(ch);
		}
	}


	/**
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode identifyTxPeriod_s_Part(String subprd,
																int lineNr,
																TxNode parentNd,
																String cntxt)
	{
		//it is a titler or a tx_verb or an onomero-structure.
		//		if (tk.startsWith("SECTER")||tk.startsWith("CHAPTER"))
		//			return "titler";
		// A tx_period_s_part could be and a sentence or supersentence.

		Reader reader = new StringReader(subprd);
		lineNrSrc=lineNr;
		lineNrSrcBeg=lineNr;
		context=cntxt;

		TxNode txnode = new TxNode(null, parentNd, "eng");
		txnode.txnd_sExpression=subprd;

//		try {
//			txnode=findChildTxTerminals(txnode);
//		} catch (IOException e) {
//			System.out.println(e.toString());
//		}


		//next: tx_concept identification
		txnode=findChildTxConcepts(txnode);

		//next: tx_conjunction-structures.
		txnode=findChildTxCptStructures(txnode);

		return txnode;
	}


	/**
	 * <b>INPUT</b>: a node with children its TxSecondaryNodes.<br/>
	 *
	 * <b>OUTPUT</b>: the same node with children its TxTerminal-Nodes
	 * (TxTerm, TxAuxiliary, Number, SymbolName) and its
	 * TxStopSecondaryNodes (TxBlankspace and TxPunctuation).
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode findChildTxTerminals(TxNode tnIn) //throws IOException
	{
		TxNode chldNew=null;
		Vector<TxNode> vc = tnIn.txnd_sVoChildren;

		for(int x=0; x<vc.size(); x++)
		{
			TxNode chNode = vc.get(x);
			String chExp = chNode.txnd_sExpression;
			//expression modifications
			chExp = chExp.toLowerCase();
			chExp = chExp.replace('-',' '); //cnvntn, terms with "-" I use with space.2008.11.16
//System.out.println(chExp);
			//TxPunctuation and TxBlankspace are tx_stopNodes.
			if (chNode.hasName("TxPunctuation") || chNode.hasName("TxBlankspace"))
			{}

			//1. symbol-name
			else if (Textual.isTxSymbolName(chExp)){
				chldNew = new TxNode("TxSymbolName", tnIn, "eng");
				tnIn.replaceChild(chNode,chldNew,1);
				tnIn.txnd_sVoTxTerminal.add(chldNew);
			}

			//2. symbol-name-number
			else if (Textual.isNumber(chExp)){
				chldNew = new TxNode("Number", tnIn, "eng");
				tnIn.replaceChild(chNode,chldNew,1);
				tnIn.txnd_sVoTxTerminal.add(chldNew);
			}

			else {
				//the possible terminals (=syntax) of tx_secondary_nonstop_nodes
				//key: the terminal-node
				//value: the number (as string) of consumed nodes to create this terminal.
				Hashtable<TxNode,String> htOfTrmnl = new Hashtable<TxNode,String>();
				Vector<String> voStx = new Vector<String>();

				//because we parse ENGLISH text, we begin from english language
				//and then we search in other languages. 2008.11.12
				String idx=chExp.substring(0,1);
				boolean found=false;
				BufferedReader br =null;
				String line;
				String fileTerminal=Util.AAj_sDir+"AAjKB" +File.separator
									+"AAjINDEXES" +File.separator
									+"eng" +File.separator +"index_terminal_eng.xml";
				String fileTerm=Util.AAj_sDir+"AAjKB" +File.separator
									+"AAjINDEXES" +File.separator
									+"eng" +File.separator +"index_term_eng_"+idx+".xml";

				//3. tx_special_noun, tx_interjection, tx_phatic
				//tx_auxiliary, most-used-term.
				try {
					//we search ALL most-used-terminals
					//IF they are stored alphabetically we can stop when we find our terminal.
					br = new BufferedReader(new FileReader(fileTerminal));
					while ((line=br.readLine())!=null){
						if (line.startsWith("<TRMNL")){
							//nnn to be changed for multiword terminals.
							//treeStructure node
							//concept-model ==> concept model
							if (line.indexOf("TxEXP=\""+chExp+"\"")!=-1){
								found=true;
								//on type we create the new node.
								String tp = Util.getXmlAttribute(line, "TRMNLtYPE");
								Vector<String> v = new Vector<String>();
								v.add(line);
								while (((line=br.readLine())!=null)&& line.startsWith("<TRMNL")&&
											(line.indexOf("TxEXP=\""+chExp+"\"")!=-1)){
									v.add(line);
								}
								if (tp.startsWith("aux")){
									chldNew = new TxNode("TxAuxiliary",tnIn,"eng");
									chldNew.txnd_sExpression=chExp;
									chldNew.txnd_s_VoSyntax=v;
									htOfTrmnl.put(chldNew, "1");
								}
								else if (tp.startsWith("trm")){
									chldNew = new TxNode("TxTerm",tnIn,"eng");
									chldNew.txnd_sExpression=chExp;
									chldNew.txnd_s_VoSyntax=v;
									htOfTrmnl.put(chldNew, "1");
								}
								//else ... toDo
							}
						}
					}
					br.close();
				}
				catch (IOException ioe){
					System.out.println("Parser_TextToTxNode_Eng.findChildTxTerminals ioe: fileTerminal");
				}

				File f = new File(fileTerm);
				if (!found && f.exists())
				{
					//4. term
					try {
						br = new BufferedReader(new FileReader(fileTerm));
						while ((line=br.readLine())!=null && !found){
							if (line.startsWith("<TRMNL")){

								//CASE41: ManyWord Terminal
								if (line.indexOf("TxEXP=\""+chExp+" ")!=-1)
								{
									int j=1;
									String tmlExp=chExp;
									while (true)
									{
										//if x+1 TxBlankspace
										TxNode cNd1 = vc.get(x+j);
										if(!cNd1.txnd_sType.equals("TxBlankspace"))
											break;
										//check x+(x+2) match
										TxNode cNd2 = vc.get(x+j+1);
										tmlExp=tmlExp+" "+cNd2.txnd_sExpression;
										if (line.indexOf("TxEXP=\""+tmlExp+" ")!=-1){
											j=j+2;
											continue;
										}
										else
											break;
									}

//System.out.println("j="+j+", exp="+tmlExp);
									if (line.indexOf("TxEXP=\""+tmlExp+"\"")!=-1){
										voStx.add(line);
										//put and all other lines with same tx_expression
										while (((line=br.readLine())!=null)
											&& (line.indexOf("TxEXP=\""+tmlExp+"\"")!=-1)){
											voStx.add(line);
										}
										chldNew = new TxNode("TxTerm",tnIn,"eng");
										chldNew.txnd_sExpression=tmlExp;
										chldNew.txnd_s_VoSyntax=voStx;
										htOfTrmnl.put(chldNew, String.valueOf(j+2));
										found=true;
										x=x+j-2;
									}
								}

								//CASE42: OneWord Terminal
								else if (line.indexOf("TxEXP=\""+chExp+"\"")!=-1){
									voStx.add(line);
									//put and all other lines with same tx_expression
									while (((line=br.readLine())!=null)&& (line.indexOf("TxEXP=\""+chExp+"\"")!=-1)){
										voStx.add(line);
									}
									chldNew = new TxNode("TxTerm",tnIn,"eng");
									chldNew.txnd_sExpression=chExp;
									chldNew.txnd_s_VoSyntax=voStx;
									htOfTrmnl.put(chldNew, "1");
									found=true;
								}
							}
						}
						br.close();
					}
					catch (IOException ioe){
						System.out.println("Parser_TextEng.findChildTxTerminals ioe: fileTerm");
					}
				}

				if (htOfTrmnl.size()!=0){
					//make the first terminal as tx_terminal-node
					chldNew = htOfTrmnl.keys().nextElement();
					String cm = htOfTrmnl.elements().nextElement();
					htOfTrmnl.remove(0);
					if (htOfTrmnl.size()!=0)
						chNode.txnd_s_HToTxTerminal.putAll(htOfTrmnl);
					tnIn.replaceChild(chNode,chldNew,Integer.parseInt(cm));
					tnIn.txnd_sVoTxTerminal.add(chldNew);
				}
				else {
					//6. UNKNOWN
					JOptionPane.showMessageDialog(null,
						"TxNonstopNode >>> " +chExp +" <<< identified as NOT TxTerminal",
						"Warning", JOptionPane.WARNING_MESSAGE);
					chldNew = new TxNode("UNKNOWN-TxTerminal", tnIn, "eng");
					tnIn.replaceChild(chNode,chldNew,1);
					tnIn.txnd_sVoTxTerminal.add(chldNew);
				}
			}
		}

		return tnIn;
	}


	/**
	 * <b>INPUT</b>: a TxNode with children terminals
	 * whose tx_syntax we know.<b>
	 *
	 * <b>OUTPUT</b>: the same node with children the identified tx_concepts.
	 *
	 * @modified
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode findChildTxConcepts(TxNode tnIn)
	{
		Vector voTml = tnIn.txnd_sVoChildren;

		for(int i=0; i<voTml.size(); i++)
		{
			TxNode tmnl = (TxNode) voTml.get(i);

			//when a human reads a text get the first valid syntax
			//he thinks AND IF this will lead him to something unsemasial
			//comes back and finds another valid syntax. 2008.11.06
			TxNode tnNew=null;
			for(int j=0; j<tmnl.txnd_s_VoSyntax.size(); j++){
				String stx = tmnl.txnd_s_VoSyntax.get(j);
				String exp=Util.getXmlAttribute(stx,"TxEXP");
				String tp = Util.getXmlAttribute(stx, "TRMNLtYPE");
				String kptr=Util.getXmlAttribute(stx,"LGcPT");
				String e1b=Util.getXmlAttribute(stx,"TXeXP1_B");
				String e1a=Util.getXmlAttribute(stx,"TXeXP1_A");
//System.out.println(i+": type="+tp);//nnn

				//AUXILIARIES:
				if (tp.startsWith("auxNn")){
					//if i+1=TxBlankspace
					TxNode tmnl2 = (TxNode) voTml.get(i+2);

					//a man | the man
					if (checkTxSyntaxOfTerminalStartsWith(tmnl2,"TRMNLtYPE", "trmNnCsNm")){
						tnNew = new TxNode("TxNounCaseNominative", tnIn, "eng");
						int n = tnIn.txnd_sVoChildren.indexOf(tmnl);
						tnNew.addChild(tmnl);
						tnNew.addChild(tmnl2);
						tnIn.insertChild(n,tnNew);
						tnNew.txnd_sExpression=tmnl.txnd_sExpression+" "+tmnl2.txnd_sExpression;
						tnNew.txnd_s_Cpt=Util.getXmlAttribute(tmnl2.txnd_s_VoSyntax.get(0), "XCPT");
						if (checkTxSyntaxOfTerminalExactly(tmnl2,"TRMNLtYPE", "trmNnCsNm1"))
							tnNew.tnAttrNumber="sin";
						else
							tnNew.tnAttrNumber="plu";
						if (tmnl.txnd_sExpression.equals("the")){
							tnNew.tnAttrInstanceness="con";
						} else if (tmnl.txnd_sExpression.equals("a")){
							tnNew.tnAttrInstanceness="ran";
						}
						break;
					}

					//a young man
					else if (checkTxSyntaxOfTerminalExactly(tmnl2,"TRMNLtYPE", "trmNnAj")){
						TxNode y3 = (TxNode) voTml.get(i+4);
						if (checkTxSyntaxOfTerminalStartsWith(y3,"TRMNLtYPE", "trmNnCsNm")){
							tnNew = new TxNode("TxNounStructure", tnIn, "eng");
							int n = tnIn.txnd_sVoChildren.indexOf(tmnl);
							tnNew.addChild(tmnl);
							tnNew.addChild(tmnl2);
							tnNew.addChild(y3);
							tnIn.insertChild(n,tnNew);
							tnNew.txnd_sExpression=tmnl.txnd_sExpression+" "+tmnl2.txnd_sExpression+" "+y3.txnd_sExpression;
							break;
						}
					}

					//a very unsure young man. [wn 2.0]

				}

				else if (tp.startsWith("auxVrb")){
					//i+1 = TxBlankspace
					TxNode tmnl1 = (TxNode) voTml.get(i+1);
					TxNode tmnl2 = (TxNode) voTml.get(i+2);
					String stx2 = tmnl2.txnd_s_VoSyntax.get(0);

					if (i==0){
						//question
					}
					else if (e1a.startsWith("trmVrb")){
						TxNode tmlBf = (TxNode) voTml.get(i-2); //TrmlBefore
						//is writing | is written
						if (checkTxSyntaxOfTerminalExactly(tmnl2,"TRMNLtYPE", e1a)){
							tnNew = new TxNode("Verb_Tx", tnIn, "eng");
							tnNew.txnd_sExpression=tmnl.txnd_sExpression+" "+tmnl2.txnd_sExpression;
							int n = tnIn.txnd_sVoChildren.indexOf(tmnl);
							tnNew.addChild(tmnl);
							tnNew.addChild(tmnl1);
							tnNew.addChild(tmnl2);
							tnNew.txnd_s_Cpt=Util.getXmlAttribute(stx2, "XCPT");
							if (tmlBf.txnd_sExpression.equals("I")){
								tnNew.tnAttrNumber="sin";
								tnNew.tnAttrPerson="1";
							} else if (tmlBf.txnd_sExpression.equals("you")){
								tnNew.tnAttrNumber="plu";
								tnNew.tnAttrPerson="2";
							} else if (tmlBf.txnd_sExpression.equals("he")||tmlBf.txnd_sExpression.equals("she")||tmlBf.txnd_sExpression.equals("it")){
								tnNew.tnAttrNumber="sin";
								tnNew.tnAttrPerson="3";
							} else if (tmlBf.txnd_sExpression.equals("we")){
								tnNew.tnAttrNumber="plu";
								tnNew.tnAttrPerson="1";
							} else if (tmlBf.txnd_sExpression.equals("they")){
								tnNew.tnAttrNumber="plu";
								tnNew.tnAttrPerson="3";
							} else {
								tnNew.tnAttrNumber="sin";
								tnNew.tnAttrPerson="3";
							}
							tnIn.insertChild(n,tnNew);
							break;
						}
					}
				}

				//TERMS-CONJUNCTIONS:
				else if (tp.startsWith("trmCnj")){
					//if endsWith a, then and the next word is tx_conjunction
					//I don't understand the previous. 2008.11.13
					tnNew = new TxNode("Conjunction_Tx", tnIn, "eng");
					int n = tnIn.txnd_sVoChildren.indexOf(tmnl);
					tnNew.addChild(tmnl);
					tnIn.insertChild(n,tnNew);
					tnNew.txnd_sExpression=tmnl.txnd_sExpression;
					break;
				}

				//ADJECTIVE:
				if (tp.startsWith("trmNnAj")){
					//if i+1=TxBlankspace
					TxNode tmnl2 = (TxNode) voTml.get(i+2);

					//every person
					if (checkTxSyntaxOfTerminalStartsWith(tmnl2,"TRMNLtYPE", "trmNnCsNm")){
						tnNew = new TxNode("TxNounStructure", tnIn, "eng");
						TxNode tmnl1 = (TxNode) voTml.get(i+1);
						int n = tnIn.txnd_sVoChildren.indexOf(tmnl);
						tnNew.addChild(tmnl);
						tnNew.addChild(tmnl1);
						tnNew.addChild(tmnl2);
						tnIn.insertChild(n,tnNew);
						tnNew.txnd_sExpression=tmnl.txnd_sExpression
																	+" "
																	+tmnl2.txnd_sExpression;
						break;
					}
				}

				//TERM-NOUN
				//a tx_term_noun in-front of an tx_auxiliary-tx_verb
				//or a lg-concept's--term-tx_conjunction is a tx_noun.
				else if (tp.startsWith("trmNnCsNm")){//termNominative
					//in the begining
					if (i==0){
						//1) if i+1=TxPunctation (Concept, is called ...), It is a noun.
						TxNode tmnl2 = (TxNode) voTml.get(i+1);
						TxNode tmnl3 = (TxNode) voTml.get(i+2);
						if (tmnl2.txnd_sType.equals("TxPunctuation")){
							tnNew = new TxNode("TxNounCaseNominative", tnIn, "eng");
							int n = tnIn.txnd_sVoChildren.indexOf(tmnl);
							tnNew.addChild(tmnl);
							tnIn.insertChild(n,tnNew);
							tnNew.txnd_sExpression=tmnl.txnd_sExpression;
							tnNew.txnd_s_Cpt=Util.getXmlAttribute(tmnl.txnd_s_VoSyntax.get(0), "XCPT");
							if (checkTxSyntaxOfTerminalExactly(tmnl,"TRMNLtYPE", "trmNnCsNm1"))
								tnNew.tnAttrNumber="sin";
							else
								tnNew.tnAttrNumber="plu";
							tnNew.tnAttrInstanceness="gen"; //because there is no tx_auxiliary
							break;
						}
						else if (checkTxSyntaxOfTerminalExactly(tmnl3,"TRMNLtYPE", "auxVrb")
								||checkTxSyntaxOfTerminalExactly(tmnl3,"TRMNLtYPE", "trmCnj")){
							tnNew = new TxNode("TxNounCaseNominative", tnIn, "eng");
							int n = tnIn.txnd_sVoChildren.indexOf(tmnl);
							tnNew.addChild(tmnl);
							tnIn.insertChild(n,tnNew);
							tnNew.txnd_sExpression=tmnl.txnd_sExpression;
							tnNew.txnd_s_Cpt=Util.getXmlAttribute(tmnl.txnd_s_VoSyntax.get(0), "XCPT");
							if (checkTxSyntaxOfTerminalExactly(tmnl,"TRMNLtYPE", "trmNnCsNm1"))
								tnNew.tnAttrNumber="sin";
							else
								tnNew.tnAttrNumber="plu";
							tnNew.tnAttrInstanceness="gen"; //because there is no tx_auxiliary
							//or "concrete" if the xcpt is a ProperName.
							break;
						}
					}
				}

				//SPECIAL_NOUNS (PRONOUNS)
				else if (tp.startsWith("trmSpNnAj")){
					if (i+1==voTml.size()){
						//this is the last word, then it is a pronominative
					}
					else {
						TxNode tmnl2 = (TxNode) voTml.get(i+1);
						if (checkTxSyntaxOfTerminalStartsWith(tmnl2,"TRMNLtYPE", "trmNnCs")){
							//any node
							tnNew = new TxNode("TxNounStructure", tnIn, "eng");
							int n = tnIn.txnd_sVoChildren.indexOf(tmnl);
							tnNew.txnd_sExpression=tmnl.txnd_sExpression+" "+tmnl2.txnd_sExpression;

							TxNode por = new TxNode("TxNounSpecial", tnNew, "eng");
							por.txnd_sExpression=tmnl.txnd_sExpression;
							por.addChild(tmnl);
							por.txnd_s_Cpt=Util.getXmlAttribute(tmnl.txnd_s_VoSyntax.get(0), "XCPT");
							por.txnd_s_Number=Util.getXmlAttribute(tmnl.txnd_s_VoSyntax.get(0), "LGcPT");

							TxNode nnr = new TxNode("TxNounCaseNominative", tnNew, "eng");
							nnr.txnd_sExpression=tmnl2.txnd_sExpression;
							nnr.addChild(tmnl2);
							nnr.txnd_s_Cpt=Util.getXmlAttribute(tmnl2.txnd_s_VoSyntax.get(0), "XCPT");
							if (checkTxSyntaxOfTerminalExactly(tmnl2,"TRMNLtYPE", "trmNnCsNm1"))
								nnr.tnAttrNumber="sin";
							else
								nnr.tnAttrNumber="plu";
							if (tmnl.txnd_sExpression.equals("any")
								||tmnl.txnd_sExpression.equals("each")
								||tmnl.txnd_sExpression.equals("either")
								||tmnl.txnd_sExpression.equals("every")
								||tmnl.txnd_sExpression.equals("one"))
								nnr.tnAttrInstanceness="ran";
							else
								nnr.tnAttrInstanceness="con";

							tnNew.addChild(por);
							tnNew.addChild(nnr);
							tnIn.insertChild(n,tnNew);
							break;
						}
					}
				}

				//a TERM-ADJECTIVE must be followed by another or a tx_noun

			}//list of syntax of one terminal
		}//list of terminals

		return tnIn;
	}


	/**
	 * <b>INPUT</b>: a TxNode with children tx_concepts.<b>
	 *
	 * <b>OUTPUT</b>: the same node with children the identified
	 * tx_conceptual_structures.<p>
	 *
	 * It's imposible to find always the LgConcept-Structures of a
	 * sentence, IF we don't know the tx_syntax of a tx_verb. 2008.11.06
	 *
	 * @modified
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode findChildTxCptStructures(TxNode tnIn)
	{
		Vector voCpt = tnIn.txnd_sVoChildren;

		for(int i=0; i<voCpt.size(); i++)
		{
			TxNode ch=(TxNode)voCpt.get(i);
			if (ch.txnd_sType.equals("Conjunction_Tx")){

				//the big problem I have to solve is to find the rules with
				//which we can distiguish what a tx_conjunction corelates.
				//1) an onomero-structure OR a tx_verb's-argument?
				//#s:the economy #vrb:has benefited #fromwhat:from a boom in tourism.

				if (ch.txnd_sExpression.equals("of")){
					//all "of" tx-conjunctions are tx_noun-tx-conjunctions
					TxNode ch1=(TxNode)voCpt.get(i-2);
					TxNode ch2=(TxNode)voCpt.get(i-1);//TxBlankspace
					TxNode ch3=(TxNode)voCpt.get(i+1);//TxBlankspace
					TxNode ch4=(TxNode)voCpt.get(i+2);
					TxNode tnNew = new TxNode("TxNounStructure", tnIn, "eng");
					int n = tnIn.txnd_sVoChildren.indexOf(ch1);
					tnNew.addChild(ch1);
					tnNew.addChild(ch2);
					tnNew.addChild(ch);
					tnNew.addChild(ch3);
					tnNew.addChild(ch4);
					tnNew.txnd_sExpression=ch1.txnd_sExpression+" "
						+ch.txnd_sExpression+" "+ch4.txnd_sExpression;
					tnIn.insertChild(n,tnNew);
				}
			}
		}
		return tnIn;
	}


	/**
	 * <b>INPUT</b>: a TxNode with children tx_conceptual_structures.<b>
	 *
	 * <b>OUTPUT</b>: the same node with children the identified
	 * tx_verb's-arguments: tx_verb, tx_subject, tx_object1, tx_object2,
	 * tx_agent, tx_subject_complement and any other tx_verb_argument.<p>
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode findChildTxVerb_s_Arguments(TxNode tnIn, TxNode parentNd)
	{
		TxNode aoc[] = tnIn.getChildrenArray();
//System.out.println("=====================");
//Vector voch=tnIn.txnd_sVoChildren;//nnn
//for(int c=0; c<voch.size(); c++) {
//	TxNode tl = (TxNode) voch.get(c);
//	System.out.println(tl.txnd_sExpression+": "+tl.txnd_sType);
//}

		//if one TxVerb|StrukturoVbr, then one sentence
		if (tnIn.getNumOfChildWithName("Verb_Tx")==1){
			TxNode sentence = new TxNode("TxSentence", parentNd, "eng");
			sentence.txnd_sExpression=tnIn.txnd_sExpression;
			//cases:
			//1. S+V:           <(my head)(aches)>
			//3. S+V+SC:         <(greece)(is)(a small country)>.
			//4. S+V+O:         <(we all)(enjoy)(a party)>.
			//2. S+V+VC:         <(we)(live)(in) (greece)>.
			//5. S+V+O+A:       <(I)(put)(my car)(in the garage)>.
			//6. S+V+Oind+Odir: <(my father)(gave)(me)(this watch)>.
			//7. S+V+O+C:       <(work)(makes)(me)(tired)>

			//we need to know the syntax of the tx_verb.
			//THEN we will know how to deal with the tx-conjunctions of the tnIn.
			if (tnIn.getNumOfChildren()==2){
			}
			else if (tnIn.getNumOfChildren()==3){
				if (aoc[1].hasName("Verb_Tx")){
					//IF tx_verb is a relation-tx_verb
					String ct=aoc[1].txnd_s_Cpt;
					if (! Util.isSpecificOf(ct,"hknu.symb-16")){
						TxNode lg_subject = new TxNode("TxSubject", sentence, "eng");
						tnIn.removeChild(aoc[0]);
						lg_subject.addChild(aoc[0]);
						lg_subject.txnd_sExpression=aoc[0].txnd_sExpression;
						TxNode lg_object = new TxNode("TxObject", sentence, "eng");
						tnIn.removeChild(aoc[2]);
						lg_object.addChild(aoc[2]);
						lg_object.txnd_sExpression=aoc[2].txnd_sExpression;

						sentence.addChild(lg_subject);
						tnIn.removeChild(aoc[1]);
						sentence.addChild(aoc[1]);
						sentence.addChild(lg_object);

						if (parentNd!=null){
							parentNd.removeChild(tnIn);
							parentNd.addChild(sentence);
						}
					}
				}
			}
			else {
				return tnIn;
			}

			return sentence;
		}

		else if (tnIn.getNumOfChildWithName("Verb_Tx")==2){
			return tnIn;
		}
		else
			return tnIn;

	}


	/**
	 * Tests if a java-char separates tx_periods.
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isCharTxPeriodStop(char ch)
	{
		String ln="";
		String[] enp = null;
		String fl= AAj.AAj_sDir +"AAjKB" +File.separator
							+"AAjINDEXES" +File.separator +"textual_strings.xml";
		try {
			FileInputStream fis = new FileInputStream(fl);
			InputStreamReader isr = new InputStreamReader(fis, "UTF-16");
			BufferedReader br = new BufferedReader(isr);
			while ( (ln=br.readLine())!=null )
			{
				if (ln.startsWith("JvCHAR_STOP_PERIOD_eng")){
					enp=ln.substring(ln.indexOf(" ")).split(" ");
					break;
				}
			}
			br.close();
		}
		catch (EOFException e){
			System.out.println("Parser_TextToTxNode_Eng.isCharTxPeriodStop: EOFException");}
		catch (FileNotFoundException e)	{
			System.out.println("Parser_TextToTxNode_Eng.isCharTxPeriodStop: FileNotFoundException");}
		catch (IOException e){
			System.out.println("Parser_TextToTxNode_Eng.isCharTxPeriodStop: IOException");}

		if (Util.arrayStringContains(enp, String.valueOf(ch)))
			return true;
		else
			return false;
	}


	/**
	 * Tests if a string is matched against a vector of tx_nonstopNodes at a
	 * specific index.<p>
	 *
	 * RETURNS an array of strings with key the string and value the number of
	 * tx_nonstopNodes consumed.<br/>
	 * IF {"mismatch", "0"} is returned, we found a mismatch.
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String[] txNonstopNodes_Match_TxExpression_At_Index(String txtr,Vector<TxNode> vot,int i)
	{
		String[] result = new String[2];
		String[] result2 = {"mismatch","0"};

		TxNode y = vot.get(i);
		String ytxt =y.txnd_sExpression;

		String newTxtr=""; //multiword
		int lastIndx=0; //in multiwords

		if (txtr.indexOf(";") == -1)
		{
			if (ytxt.equals(txtr))
			{
				//case1: "there"
				result[0]=txtr;
				result[1]="1";
				return result;
			}
			else {
				//case2: mismatch
				return result2;
			}
		}
		else {
			//case3: multiword tx_expression
			String[] atr = txtr.split(";");
			if (ytxt.equals(atr[0]))
			{
				newTxtr=atr[0];
				lastIndx=1;
			}
			else {
				//this never be the case;
				return result2;
			}
			for(int j=1; j<atr.length; j++)
			{
				//is the next tx_nonstopNode whitespace?
				TxNode n1=vot.get(i+j);
				if (!n1.txnd_sType.equals("TxBlankspace"))
					return result2;
				else {
					newTxtr=newTxtr+" ";
					lastIndx=lastIndx+1;
				}
				TxNode n2=vot.get(i+j+1);
				if (!n2.txnd_sExpression.equals(atr[j]))
					return result2;
				else {
					newTxtr=newTxtr+n2.txnd_sExpression;
					lastIndx=lastIndx+1;
				}
			}
			result[0]=newTxtr;
			result[1]=String.valueOf(lastIndx);
			return result;
		}
	}


	/**
	 * Tests if an expression is contained by a vector of tx_nonstopNodes
	 * AFTER an index.
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean txNonstopNodes_Contain_TxExpression_After_Index(String txtr,
																						Vector<TxNode> vot, int i)
	{
		for(int j=i+1; j<vot.size(); j++)
		{
			TxNode y = vot.get(j);
			String ytxt =y.txnd_sExpression;
			if (txtr.indexOf(";") == -1)
			{
				if (ytxt.equals(txtr))
				{
					//case1: "there"
					return true;
				}
			}
			else {
				//case2: multiword tx_expression
				String[] atr = txtr.split(";");
				if (ytxt.equals(atr[0]))
				{
					if (j+atr.length-1 <= vot.size())
					{
						boolean foundtxt=true;
						for(int k=1; k<atr.length; k++)
						{
							TxNode n1=vot.get(j+k);
							if (n1.txnd_sType.equals("Whitespace"))
							{
								TxNode n2=vot.get(j+k+1);
								if (!n2.txnd_sExpression.equals(atr[k+1]))
									foundtxt=false;
							}
							else foundtxt=false;
						}
						if (foundtxt)
							return true;
					}
				}
			}
		}
		return false;
	}


	/**
	 * ::mdt: he bought the car WITH a credit card.
	 * ==> #sbj:he #vrb:bought #obj:the car #how:with a credit card.<br/>
	 * ::mdt: he bought the car WITH a sunroof.
	 * ==> #sbj:he #vrb:bought #obj:the car with a sunroof.<p>
	 *
	 * We must know the syntax of the tx_verb.
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void disambiguatingTxConjunction()
	{

	}


	/**
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void disambiguatingTxConjunction_TxNounAdverb()
	{
		//a tx_concept which is TxConjunction AND TxNounAdverb
		//confuses the reader:
		//EXMPL: The rebels fled into the mountains, leaving #place:behind
		//#obj:their weapons and supplies.
		//EXMPL: They were parked #place:behind the truck.
		//BUT the language with "in front" uses "of" when denotes tx_conjunction
		//and it does NOT create ambiguity.

	}


	/**
	 * Disambiquates a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#foEtoKoncosTermo">lg-concept's--term</a>
	 * if it is a
	 * <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#">tx_verb</a> or
	 * <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#tx_conjunction">tx_conjunction</a>.<br/>
	 * EXAMPLE:<br/>
	 * ::mdt: Time flies LIKE an arrow.<br/>
	 * ::mdt: Fruit flies LIKE a banana.<p>
	 *
	 * How many cases exist?
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void disambiguatingTxVerbTxConjunction()
	{
	}


	/**
	 * Disambiquates a
	 * <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#foEtoKoncosTermo">
	 * lg-concept's--term</a> if it is a tx_verb or tx_noun.<br/>
	 * EXAMPLE:<br/>
	 * ::mdt: Time FLIES like an arrow.<br/>
	 * ::mdt: Fruit FLIES like a banana.<p>
	 *
	 *
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void disambiguatingTxVerbTxNoun()
	{
			//RULE: A term which is tx_verb and tx_noun, is a tx_noun
			//if in front of it there is tx_conjunction or tx_special_noun.
			//EXMPL: Most of us have had to take a duff job sometime
			//in our LIVES when opportunities were scarce.
			//EXMPL: He LIVES near the borders
	}


	/**
	 * Disambiquates a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#foEtoKoncosTermo">
	 * lg-concept's--term</a> of a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#tx_verb">
	 * tx_verb</a> when it denotes many <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#nlBConcept">
	 * b-concepts</a>.<br/>
	 *
	 * EXAMPLE:<br/>
	 * ::mdt: Bobbie will CALL you tomorrow with details about the agenda.<br/>
	 * ::mdt: I CALL concept every node of a concept-model.<p>
	 *
	 * How many cases exist?
	 * @modified 2008.11.06
	 * @since 2008.11.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void disambiguatingTxVerbTxVerb()
	{
	}


}
