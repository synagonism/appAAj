/*
 * TxNode.java - Represents ANY node of a Text (subWorldview).
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.*;

import pk_Util.Util;
import pk_Util.Textual;

/**
 * A java node-of-Text.<p>
 *
 * Specific: Content-Nodes (maped), Token-Nodes(nonmaped)
 *
 * @modified
 * @since 2004.10.10 (v00.02.00)
 * @author HoKoNoUmo
 */
public class TxNode
{

	/**
	 * The child-nodes of a node.
	 * LinkedList ensures the same order as in the SSMA.
	 */
//	public LinkedList txnd_sVoChildren=new LinkedList();
	public Vector<TxNode> txnd_sVoChildren=new Vector<TxNode>();

	/** The vector of TxStops and TxNonstopNodes of the node. 2006.10.29*/
	public Vector<TxNode> txnd_sVoTxSecondaryNode=new Vector<TxNode>();
	//what is the use of this? 2008.11.12
	public Vector<TxNode> txnd_sVoTxTerminal=new Vector<TxNode>();
	public Vector<TxNode> txnd_sVoTxConcept=new Vector<TxNode>();
	//SENTENCE:
	//the "outermost" children 2006.11.05
	public Vector<TxNode> txnd_sVoOutermostPart=new Vector<TxNode>();

	/** The line-number where the node starts. */
	public int tnLineNr;

	/**
	 * The type of a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/sbcpt_59.html#foEtoNodo">
	 * node</a> denotes what specific node it is.<br/>
	 * <b>NODE-CONTENT</b>:<ol>
	 *   <li>UNKNOWN:
	 *   <li>TEXTNODE:
	 *   <li>TxParagraph:
	 *   <li>TxSupersentence:
	 *   <li>TxSentence:
	 *   <li>TxVerbArgument: TxSubject, TxObject, TxObject2, TxAgent,
	 * 									TxSubjectComplement.
	 *   <li>TxNounStructure: TxNounStructure, StrukturoAnr, ...
	 *   <li>TxConcept: TxVerb, TxConjunction, TxNounCaseNominative,
	 * 									TxNounCasePossessive, TxNounAdjective, TxNounAdverb,
	 										TxNounSpecial.
	 *   <li>TxTerminal:	TxTerm, TxAuxiliary,
	 * 									TxPhatic, TxInterjection.</ol>
	 *
	 * <b>NON-CONTENT</b>:<ol>
	 *   <li>UNKNOWN:
	 *   <li>TxStopNode:
	 *   <li>TxStopNode_Main:
	 *   <li>TxStopNode_Secondary: TxBlankspace, TxPunctuation.
	 *   <li>TxStopNode_Period:
	 *   <li>TxNonstopNode:
	 *   <li>TxNonstopNode_Main: plain-text between blank lines.
	 *   <li>TxNonstopNode_Secondary: free-syllable-structure.
	 *   <li>TxNonstopNode_Period:</ol>
	 */
	public String txnd_sType;
	/** The textual_expression of a java-TxNode. */
	public String txnd_sExpression;

	/** The next-higher-node (parent) of this node. */
	public TxNode txnd_sParent;

	/** The natural-language of the node. */
	public String txnd_sLng;
	/** the string that denotes the brainual--sub-worldview the text (subWorldview) denotes. */
	public String txContext="unknown";

	/** for printing. */
	public String txIndent="\t";


	/** If a tx_unit_structure is (a term in many languages), this hashtable
	 *  contains the words in the languages other than the
	 *  logal's language.<br/>
	 *  KEY: the String "Word.lang"<br/>
	 *  VALUE: the vector with the tx_syntax of the word. */
	public Hashtable txnd_s_HToTxWord=null; //2006.03.18


	//TEXTUAL-NONSTOP-NODE
	/** KEY: a possible TxTerminal of a tx_nonstopNode
	 *  VALUE: the tx_nonstopNodes and tx_stopNodes consumed by the tx_terminal. */
	public Hashtable<TxNode,String> txnd_s_HToTxTerminal= new Hashtable<TxNode,String>();
	//TxTerminal
	/** KEY: a possible LG-CONCEPT of this tx_terminal
	 *  VALUE: the nodes consumed by the tx_concept. */
	public Hashtable<TxNode,String> txnd_s_HToTxConcept=new Hashtable<TxNode,String>(); //2006.03.05


	/** the white-space at the begining|end of a <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango_ho.html#tx_nonstopNode">
	 * tx_nonstopNode</a>. */
//	public String tnWSpaceBeg="";
//	public String tnWSpaceEnd="";


	//LG-CONCEPTS:
	/** The unique-name of the concept(BConcept@hknu.meta-1) it denotes. */
	public String txnd_s_Cpt="";
	/** We need the termTxCpt_sName of an instance-tx_verb to find its syntax. 2006.10.16*/
	public String txnd_s_Name="";
	/** An instance--tx-concept has ONE lg-concept's--term (termTxCpt_sName or lcptTerm)
	 * and SOME tx_auxiliaries. 2004.09.08 */
	public String tnTerm_TxConcept="";
	/** Because tx_concepts are many, I gave them a number to distiguish
	 * them: vrb019, nnc1, adj1, ... */
	public String txnd_s_Number="";
	/** An individual-tx_concept denotes ONE or MANY xConcepts
	 * depending on its syntax. */
	public Vector txnd_s_VoSrCpt= new Vector();
	/** A tx_concept has no attributes, but DENOTES attributes
	 * of a sr-concept: sin,plu */
	public String tnAttrNumber="";
	/** 1, 2, 3 */
	public String tnAttrPerson="";
	/** none, def, def */
	public String tnAttrInstanceness="";
	/** mas,fem,neu for greek lang */
	public String tnAttrGender="";
	/**
	 * The semasial-attributes the tx_concept denotes.
	 * In case of a pronometo in the form: attr1=xxx;attr2=yyyy. 2006.10.07
	 */
	public String txnd_s_SmAttrs="";
	/** In case it denotes many xConcepts. 2006.10.20 */
	public Vector<String> txnd_s_VoSmAttrs= new Vector<String>();


	//STRUKTURO-NODES and LG-CONCEPTS
	/** KEY: a possible STRUKTURO of this tx_concept
	 *  VALUE: the nodes consumed by the structure (before;after). */
	public Hashtable<TxNode,String> txnd_s_HToStrukturo=new Hashtable<TxNode,String>(); //2006.10.23 //2006.11.11


	/** the number-of-tx_syntax a word can have */
	public int tnNoSks=0;
	/** each tx_syntax of a word is an XML-ELEMENT with attributes.2004.09.05 */
	/** the vector-of-tx_syntax of a word|TxVerb 2006.10.16 */
	public Vector<String> txnd_s_VoSyntax= new Vector<String>();

	/** A node, especially onomero-structures, DENOTES something such as:
	 * place, time, etc.  We need this info to figure out tx_syntax. */
	public String txnd_s_SrSubWorldview=null; //2006.10.13



	/**
	 * Constructs a node with the specified name and parent.
	 * We can NOT have a node without a name and parent.
	 *
	 * @param name	The name (type) of this node.
	 * @param prnt	The parent-node of this node.
	 * @modified
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode(String name, TxNode prnt, String language)
	{
		txnd_sType=name;
		txnd_sParent=prnt;
		txnd_sLng=language;
	}


	/**
	 * Adds a child-node to THE END of the vector-of-children.<br/>
	 * IF the child belogs to another node, first it removes it.
	 *
	 * @param child	The child node to add.
	 * @modified 2004.10.20
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void addChild(TxNode child)
	{
		//every node belongs to ONE node.
		TxNode chParent = child.getParent();
		if (chParent!=null && chParent!=this)
			chParent.removeChild(child);

		if (txnd_sVoChildren.contains(child)) return;
		txnd_sVoChildren.addElement(child);
		child.txnd_sParent = this;
	}


	/**
	 * Adds an array of children.
	 * @param newNodes
	 * 		The array of child-nodes to be added.
	 * @modified 2004.10.10
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void addChildren(TxNode newNodes[])
//		throws Exception_Logo_OperatingText
	{
		int numParts = newNodes.length;
		for (int con=0; con<numParts; con++)
			if (newNodes[con] != null)
				addChild(newNodes[con]);
	}


	/**
	 * Inserts a child node at index.
	 * @modified 2006.10.09
	 * @since 2004.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void insertChild(int i, TxNode child)
	{
		//every node belongs to ONE node.
		TxNode chParent  = child.getParent();
		if (chParent != this && chParent!=null)
			chParent.removeChild(child);

		if (txnd_sVoChildren.contains(child))//cnvntn: children are different
			return;
		txnd_sVoChildren.insertElementAt(child, i);
		child.setParent(this);
	}


	/**
	 * Replaces a child node. In the new node, it adds the children of
	 * the old. The tx_expression of the new is that of the old.
	 *
	 * @param tnOld	The old-child-node we want to replace.
	 * @param tnNew	The new-node we want to replace with the old.
	 * @modified 2006.11.08
	 * @since 2006.11.08 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void interchangeChild(TxNode tnOld, TxNode tnNew)
	{
		int i = txnd_sVoChildren.indexOf(tnOld);
		tnNew.txnd_sExpression=tnOld.txnd_sExpression;
		//put the children of old in new
		for (int k=0; k<tnOld.txnd_sVoChildren.size(); k++){
			TxNode nfr = tnOld.txnd_sVoChildren.get(k);
			tnNew.addChild(nfr);
		}
		removeChild(tnOld);
		insertChild(i,tnNew);
	}


	/**
	 * Removes a child node. Sets <CODE>null</CODE> in child's parent.
	 *
	 * @param child	The child-node to remove.
	 * @modified 2004.10.12
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeChild(TxNode child)
	{
		txnd_sVoChildren.remove(child);
		child.setParent(null);
	}


	/**
	 * @modified 2004.10.18
	 * @since 2004.10.18 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeChildren()
	{
		for(int k=0; k<txnd_sVoChildren.size(); k++)
		{
			removeChild(txnd_sVoChildren.get(k));
		}
	}


	/**
	 * Replaces a child node. In the new node, it adds the consumed-nodes.
	 * Also sets as ekspresion in new the sum of consumed-nodes's ekspresion.
	 *
	 * @param tnOld	The old-child-node we want to replace.
	 * @param tnNew	The new-node we want to replace with the old.
	 * @param j		The number of consumed-nodes in parent.
	 * @modified 2006.10.12
	 * @since 2006.10.09 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void replaceChild(TxNode tnOld, TxNode tnNew, int j)
	{
		int i = txnd_sVoChildren.indexOf(tnOld);
		//by adding a node in a new, it is removed from its parent.
		tnNew.txnd_sExpression="";
		for (int k=1; k<j+1; k++){
			TxNode nfr = txnd_sVoChildren.get(i);
			//the relplaced nodes are becoming children of newnode.
			tnNew.addChild(nfr);
			tnNew.txnd_sExpression=tnNew.txnd_sExpression + nfr.txnd_sExpression;
		}
		insertChild(i,tnNew);
	}


	/**
	 * Returns the child with the specified index.
	 * @modified 2004.10.19
	 * @since 2004.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode getChild(int i)
	{
		return txnd_sVoChildren.get(i);
	}


	/**
	 *
	 * @modified 2006.11.08
	 * @since 2006.11.08 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode getChildNextTxNonstopNode(int i)
	{
		for (int j=i+1; j<txnd_sVoChildren.size(); j++){
			TxNode ch = txnd_sVoChildren.get(j);
			if (!ch.isTxStop())
				return ch;
		}
		return null;
	}


	/**
	 *
	 * @modified 2006.11.12
	 * @since 2006.11.12 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode getChildNextTxNonstopNode(TxNode ch)
	{
		return getChildNextTxNonstopNode(txnd_sVoChildren.indexOf(ch));
	}


	/**
	 * Returns the FIRST child with a specific name or null.
	 *
	 * @param name	The name of the child.
	 * @modified 2004.10.10
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode getChildWithName(String name)
	{
		for (TxNode ch : txnd_sVoChildren)
			if (ch.hasName(name)) return ch;
		return null;
	}


	/**
	 * Returns an array with the children of the node.
	 * @modified 2004.06.29
	 * @since 2004.06.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode[] getChildrenArray()
	{
		TxNode ch[]=new TxNode[txnd_sVoChildren.size()];
		for(int k=0; k<txnd_sVoChildren.size(); k++)
		{
			ch[k]=txnd_sVoChildren.get(k);
		}
		return ch;
	}


	/**
	 *
	 * @modified 2006.10.20
	 * @since 2006.10.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode[] getChildrenArrayTxNonstopNode()
	{
		Vector<TxNode> voc = new Vector<TxNode>();
		for(int k=0; k<txnd_sVoChildren.size(); k++)
		{
			TxNode tn = txnd_sVoChildren.get(k);
			if (!tn.isTxStop())
				voc.add(tn);
		}
		TxNode ch[]=new TxNode[voc.size()];
		ch=voc.toArray(ch);
		return ch;
	}


	/**
	 * Returns the nesting-level of this node.  A nesting-level of zero
	 * 		indicates that this node is at the outermost level.
	 * @modified 2004.10.10
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getDepth()
	{
		int depth;
		TxNode highernode = this;

		depth = -1;
		do
		{
			highernode = highernode.getParent();
			depth++;
		}
		while (highernode != null);

		return depth;
	}


	/**
	 * Returns the index of the node in the vector of children
	 * of its parent.
	 * If parent is null then the index is -1.
	 * @modified 2004.10.30
	 * @since 2004.10.30 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getIndex()
	{
		int i=-1;
		TxNode p = getParent();
		if (p!=null)
			i=p.txnd_sVoChildren.indexOf(this);
		return i;
	}


	/**
	 * Returns the index of a child.
	 *
	 * @modified 2006.10.29
	 * @since 2006.10.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getIndexOfChild(TxNode child)
	{
		return txnd_sVoChildren.indexOf(child);
	}


	/**
	 * Returns the line's number (<code> >= 0</code>) in the
	 * source data on which the element is found.<p>
	 *
	 * If this method returns <code>0</code> there is no
	 * associated source data.
	 * @modified 2004.10.10
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getLineNr()
	{
			return this.tnLineNr;
	}


	/**
	 *
	 * @modified 2006.10.20
	 * @since 2006.10.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Vector<String> getSmAttributes()
	{
			BufferedReader br =null;
			String line;
			String mapeinoFile="";
			if (txnd_sLng.equals("eng")){
				mapeinoFile=Util.AAj_sDir+"AAjKB"+File.separator
									+"AAjINDEXES" +File.separator
									+"MapeinoSemasialTextual.eng.xml";
			}
			else if (txnd_sLng.equals("eln")){
				mapeinoFile=Util.AAj_sDir+"AAjKB"+File.separator
									+"AAjINDEXES" +File.separator
									+"MapeinoSemasialTextual.eln.xml";
			}

			try {
				br = new BufferedReader(new FileReader(mapeinoFile));
				while ((line=br.readLine())!=null){
					if (line.startsWith("<SMvERB")){
						if (line.indexOf("LGcPT=\""+txnd_s_Number+"\"")!=-1){
							String ma = Util.getXmlAttribute(line,"SMaTTR");
							txnd_s_VoSmAttrs.add(ma);
						}
					}
				}
				br.close();
			}
			catch (IOException ioe){
				System.out.println("TxNode. ioe: ");
			}
		return txnd_s_VoSmAttrs;
	}


	/**
	 * Returns the name of this node.
	 */
	public String getName()
	{
		return txnd_sType;
	}


	/**
	 * Returns the number of children.
	 * @modified 2004.10.19
	 * @since 2004.10.18 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getNumOfChildren()
	{
		return txnd_sVoChildren.size();
	}


	/**
	 * Returns the number of children with a specified-name.
	 * @modified 2004.10.19
	 * @since 2004.10.18 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getNumOfChildWithName(String name)
	{
		int noc=0;
		TxNode chn[]=getChildrenArray();
		for (int n=0; n<chn.length; n++)
			if (chn[n].getName().equals(name))
				noc=noc+1;
		return noc;
	}


	/**
	 * Returns the next-higher-node of this node or null
	 * 		if this is the outermost node.
	 * @modified 2004.10.10
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode getParent()
	{
		if (txnd_sParent == null)
			return null;

		return txnd_sParent;
	}


	/**
	 * Returns the sibling-node of this node with the specified index.
	 * @modified 2004.10.30
	 * @since 2004.10.30 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public TxNode getSibling(int i)
	{
		TxNode s=null;
		TxNode p=getParent();
		if (p==null)
			return null;
		s=p.getChild(i);
		return s;
	}


	/**
	 *
	 * @modified 2006.10.23
	 * @since 2006.10.22 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Vector<String> getTxSyntaxFromConcept()
	{
		//toDo: Put all syntax in term-files. 2008.03.27
		//TxVerb, TxNoun, TxConjunction
		String name = txnd_sType.toUpperCase();

		boolean found=false;
		BufferedReader br =null;
		String line;
		String sConceptFile = Util.getXCpt_sLastFullFileName(txnd_s_Cpt);
		try {
			br = new BufferedReader(new FileReader(sConceptFile));
			while ((line=br.readLine())!=null && !found){
				if (line.startsWith("<"+name)){
					String txr = Util.getXmlAttribute(line, "TxEXP");
					if (txr.toLowerCase().equals(txnd_s_Name.toLowerCase())){
						while ((line=br.readLine()).startsWith("<SYNTAX")){
							txnd_s_VoSyntax.add(line);
							tnNoSks++;
						}
						found=true;
					}
				}
			}
			br.close();
		}
		catch (IOException ioe){
			System.out.println("logo.TxNode ioe: for fileTerminal");
		}

		return txnd_s_VoSyntax;
	}


	/**
	 * Searches the vector-of-tx_syntax for a value
	 * in an attribute.
	 *
	 * @param att	The attribute of the tx_syntax we want to search.
	 * @param value	The value of the attribure of the tx_syntax
	 * 		we want to check.
	 * @modified 2006.11.02
	 * @since 2004.09.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	protected boolean checkTxSyntax(String att, String value)
	{
		String sks_a;
		for(String sks : txnd_s_VoSyntax)
		{
			sks_a = Util.getXmlAttribute(sks,att);
			//with startsWith method we can search for an tx_auxiliary, lg-concept's--term...
			if (sks_a.indexOf(value)!=-1)
				return true;
		}
		return false;
	}


	/**
	 * Tests if the HToTxConcept of a node contains exactly
	 * 1 tx_concept with the input name.
	 *
	 * @modified 2006.11.07
	 * @since 2006.11.07 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean containsTxCpt_s_Type1(String knr)
	{
		Hashtable<TxNode,String> ht = txnd_s_HToTxConcept;
		if (ht.size()!=1){
			return false;
		}
		else {
			TxNode nd = ht.keys().nextElement();
			String ndn = nd.txnd_sType;
			if (ndn.equals(knr))
				return true;
			else
				return false;
		}
	}


	/**
	 * Tests if the HToTxConcept of a term contains exactly
	 * 2 tx_concepts with the input names.
	 *
	 * @modified 2006.10.14
	 * @since 2006.10.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean containsTxCpt_s_Type2(String knr1, String knr2)
	{
		Hashtable<TxNode,String> ht = txnd_s_HToTxConcept;
		if (ht.size()!=2)
			return false;
		else {
			Iterator<TxNode> it = ht.keySet().iterator();
			TxNode n1 = it.next();
			String n1n = n1.txnd_sType;
			TxNode n2 = it.next();
			String n2n = n2.txnd_sType;
			if (n1n.equals(knr1) && n2n.equals(knr2))
				return true;
			else if (n1n.equals(knr2) && n2n.equals(knr1))
				return true;
			else
				return false;
		}
	}


	/**
	 *
	 * @modified 2006.10.20
	 * @since 2006.10.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean denoteSmAttribute(String sma)
	{
		if (txnd_s_VoSmAttrs.size()<1)
			txnd_s_VoSmAttrs = getSmAttributes();
		//denotes an attribute if all xConcepts have the attribute:
		for (String attr : txnd_s_VoSmAttrs)
		{
			if (attr.indexOf(sma)==-1)
				return false;
		}
		return true;
	}


	/**
	 * Tests if a child-node with an input-name follows a child at
	 * index. TxBlankspaces and tx_punctuations are not considered.
	 * @modified 2006.10.14
	 * @since 2006.10.14 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean followsChild(int i, String nm)
	{
		Vector<TxNode> voc = txnd_sVoChildren;
		int j = i+1;
		TxNode ch = voc.get(j);
		while(ch.txnd_sType.equals("TxPunctuation")
					|| ch.txnd_sType.equals("TxBlankspace")){
			j=j+1;
			ch = voc.get(j);
		}
		if (ch.txnd_sType.equals(nm))
			return true;
		else
			return false;
	}

	/**
	 * Returns true if this node has a given name.
	 * @modified 2004.10.18
	 * @since 2004.10.10
	 * @author HoKoNoUmo
	 */
	public boolean hasName(String name)
	{
		return getName().equals(name);
	}


	/**
	 * Returns <code>true</code> if this node is a lower-node (not child)
	 * of the specified node.
	 * @param higher	The node being checked for as the higher-node
	 * 		of this node.
	 * @modified 2004.10.10
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isLowerOf(TxNode higher)
	{
		TxNode highernode;
		highernode = this;

		do
		{
			highernode = highernode.getParent();
			if (highernode.equals(higher))
				return true;
		}
		while (highernode != null);

		return false;
	}


	/**
	 *
	 * @modified 2006.10.29
	 * @since 2006.10.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isNextTxNonstopNodeScaned(TxNode child)
	{
		if (//getIndexOfChild(child)==getNumOfChildren()-1 &&
				txnd_sExpression.endsWith(child.txnd_sExpression))
			return true;
		else
			return false;
	}


	/**
	 * TxPunctuation is a TxNode if its tx_expression is comprised of
	 * tx_punctuations: "( ) [ ] , .
	 *
	 * @modified 2006.10.07
	 * @since 2006.10.07 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isTxPunctuation()
	{
		if (txnd_sType.equals("TxPunctuation"))
			return true;
		else
			return false;
	}


	/**
	 * TxBlankspace is a TxNode if its tx_expression is comprised of
	 * spaces, tabs, or newlines (blankspace-characters).
	 * @modified 2006.09.20
	 * @since 2006.09.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isTxBlankspace()
	{
		if (txnd_sType.equals("TxBlankspace"))
			return true;
		else
			return false;
/*
		char[] ach = txnd_sExpression.toCharArray();
		for (int i=0; i<ach.length; i++) {
			if (!Textual.isTxBlankspace(ach[i]))
				return false;
		}
		return true;
*/
	}


	/**
	 * Returns true if this node has children. (opposite: isLeaf)
	 * @modified 2004.10.10
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isStem()
	{
		if (txnd_sVoChildren.size()>0)
			return true;

		return false;
	}


	/**
	 * TxStop is a blankspace or punctuation node.
	 * @modified 2006.10.20
	 * @since 2006.10.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isTxStop()
	{
		if (txnd_sType.equals("TxBlankspace")||txnd_sType.equals("TxPunctuation"))
			return true;
		else
			return false;
	}


	/**
	 *
	 * @modified 2006.10.23
	 * @since 2006.10.23 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isType(String tp)
	{
		if (tp.equals("TxNounStructure")){
			if (txnd_sType.equals("TxNounStructure")||
					txnd_sType.equals("TxNoun")||
					txnd_sType.equals("TxNounCaseNominative")||
					txnd_sType.equals("TxNounCasePossessive")||
					txnd_sType.equals("NounAdjectiveTx")||
					txnd_sType.equals("NounAdverbTx")||
					txnd_sType.equals("TxNounSpecial")||
					txnd_sType.equals("TxNounSpecialNominative")||
					txnd_sType.equals("TxNounSpecialPossessive")||
					txnd_sType.equals("TxNounSpecialAdjective")||
					txnd_sType.equals("TxNounSpecialAdverb"))
				return true;
			else
				return false;
		}
		else if (tp.equals("TxNoun")){
			if (txnd_sType.equals("TxNoun")||
					txnd_sType.equals("TxNounCaseNominative")||
					txnd_sType.equals("TxNounCasePossessive"))
				return true;
			else
				return false;
		}
		else
			return false;

	}


	/**
	 * Sets the language of this node.
	 * @modified 2004.11.21
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void setLang(String lg)
	{
		txnd_sLng=lg;
	}


	/**
	 * Sets the name of this node.
	 * @param newName	The name for this node.
	 * @modified 2004.10.10
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void setName(String newName)
	{
		txnd_sType = newName;
	}


	/**
	 * Sets this node's next-higher-node.<br/>
	 * Null indicates the outermost node.
	 *
	 * @param highernode this node's next-higher-node or null.
	 * @modified
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void setParent(TxNode highernode)
	{
		//if (txnd_sParent.equals(highernode))
		//	return;

		txnd_sParent = highernode;
		//highernode.addChild(this);
	}


	/**
	 * Writes the java-TxNode to a character-Writer.
	 * @modified 2008.11.15
	 * @since 2004.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void mapToCharNode(Writer wr)
	{
		try {
			if (!txnd_sType.equals("TxBlankspace"))
				wr.write("\n"+txIndent+txnd_sType+":"+txnd_sExpression);
		} catch (IOException e) {
			System.out.println("TxNode.mapToCharNode: IOException");
		}
		TxNode chn[]=getChildrenArray();
		for(int i=0; i<chn.length; i++)
		{
			chn[i].txIndent=txIndent+"\t";
			chn[i].mapToCharNode(wr);
		}
	}


	/**
	 * @modified 2006.11.03
	 * @since 2004.11.18 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void toLog()
	{
		if (txnd_sType.startsWith("TxAuxiliary")||txnd_sType.startsWith("TxTerm"))
			Util.log("\n"+txIndent+txnd_sType+":"+txnd_sExpression+", nosx="+txnd_s_VoSyntax.size());
		else
			Util.log("\n"+txIndent+txnd_sType+":"+txnd_sExpression);
		TxNode chn[]=getChildrenArray();
		for(int i=0; i<chn.length; i++)
		{
			chn[i].txIndent=txIndent+"\t";
			chn[i].toLog();
		}
	}


	/**
	 * Maps the java-TxNode to characters (char-TxNode).
	 * @modified 2004.10.17
	 * @since 2004.10.10 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String toString()
	{
		Writer wr = new StringWriter();
		mapToCharNode(wr);
		return wr.toString();
	}


}
