/*
 * SSmNode.java - Represents any node of a SensorialSemasia.
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

import java.util.*;
import java.io.*;

/**
 * This java-class represents ANY node of a <b>sensorial-semasial--sub-worldview</b>
 * in XML format.<p>
 *
 * VALIDATION: I try the methods of a semasial-node to ensure validation
 *		data as possible. 2004.06.28<p>
 *
 * In litterature "node" is called only the "argument-nodes". 2008.10.12
 *
 * @modified 2004.10.08
 * @since 2000 (v00.02.00)
 * @author HoKoNoUmo
 */
public class SSmNode
{
	/** The next-higher-node (parent) of this node. */
	public SSmNode ssnd_s_Parent;

	/** The child-nodes of a node. */
	public Vector<SSmNode> ssnd_s_Children=new Vector<SSmNode>();

	/**
	 * The attributes given to the element.<br/>
	 * LinkedHashMap ensures the same order as in the SSMA.<br/>
	 * LNG (SSMA)	The semasial is language dependent. 2004.09.18<br/>
	 * LABEL, TRM, SMaTTR, INSTANCE<br/>
	 * ARGS (SmConjunction)
	 */
	//WORKING: to use independent-variables for each xml-attribute.2006.01.12
	public LinkedHashMap<String,String> ssnd_s_Attributes=new LinkedHashMap<String,String>();

	/** The name of the node denoting what specific node it is. */
	public String ssnd_s_Name;

	/** The natural-language of the node. The Semasial--sub-worldview is language
	 * dependent.	 The default language is english. */
	public String ssnd_s_Lng="eng";

	/** The text mapped with this node. */
	public String ssnd_s_TxExpression;
	/**
	 * To ensure the POSITION of comments, we can set the CONVENTION
	 * that every comment belongs to the NEXT node. 2004.06.28
	 */
	public String smComment;

	/** The line-number where the node starts. */
	public int smLineNr;

	/** To use in printing */
	public String smIndent="";


	/**
	 * Constructs a node with the specified name, parent and language.
	 *
	 * @param nm	The name (type) of this node.
	 * @param prnt	The parent-node of this node.
	 * @param lng The natural-language of the node.
	 * @modified 2006.01.13
	 * @since 2006.01.13 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode(String nm, SSmNode prnt, String lng)
	{
		ssnd_s_Name=nm;
		ssnd_s_Parent=prnt;
		ssnd_s_Lng=lng;
	}


	/**
	 * Constructs a node with the specified name and parent.
	 * We can NOT have a node without a name and parent.
	 *
	 * @param nm	The name (type) of this node.
	 * @param prnt	The parent-node of this node.
	 * @modified 2004.06.26
	 * @since 2004.06.26 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode(String nm, SSmNode prnt)
	{
		ssnd_s_Name=nm;
		ssnd_s_Parent=prnt;
	}


	/**
	 * Adds a child-node.
	 *
	 * @param child
	 *	 The child element to add.
	 */
	public void addChild(SSmNode child)
		throws Exception_SSemasia_Operating
	{
		//every node belongs to ONE node.
		SSmNode hparent	= child.getParent();
		if ((hparent != null) && (hparent != this))
			throw new Exception_SSemasia_Operating(
			"SSmNode.addChild: child belongs to another node already.");

		if (ssnd_s_Children.contains(child)) return;
		ssnd_s_Children.addElement(child);
		child.ssnd_s_Parent = this;

		if (child.getName().equals("SmConjunction")){
		}
	}


	/**
	 * Adds an array of children.
	 *
	 * @param newNodes
	 *		The array of child-nodes to be added.
	 */
	public void addChildren(SSmNode newNodes[])
		throws Exception_SSemasia_Operating
	{
		int numParts = newNodes.length;
		for (int con=0; con<numParts; con++)
			if (newNodes[con] != null)
				addChild(newNodes[con]);
	}


	/**
	 * Returns an array with the argument-nodes of a ssconjunction-node ONLY.
	 * @modified 2004.06.26
	 * @since 2004.06.26 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode[] getArgNodes()
		throws Exception_SSemasia_Operating
	{
		if (!this.getName().equals("SmConjunction")){
			throw new Exception_SSemasia_Operating(
				"SSmNode.getArgNodes: you try to get argument-nodes from NOT a SmConjunction.");
		}
		String sar=getAttribute("ARGS");
		//The arguments are children of the parent of this sm_conjunction.
		StringTokenizer stk = new StringTokenizer(sar, ",");
		int t = stk.countTokens();
		SSmNode a[] = new SSmNode[t];
		SSmNode pr = this.getParent();
		SSmNode child=null;
		for(int i=0; i<t ; i++)
		{
			child=pr.getChildWithLabel(stk.nextToken());
			if (child==null)
				throw new Exception_SSemasia_Operating(
					"SSmNode.getArgNodes: ssconjunction-node with missing sbling.");
			a[i]=child;
		}

		return a;
	}


	/**
	 * Returns the value of an attribute by looking up a key in a hashtable.
	 *
	 * @param key The name of the attribute.
	 * @modified 2004.09.13 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getAttribute(String key)
		throws Exception_SSemasia_Operating
	{
		String vl;
		String a[];
		if (key.equals("MOOD")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[0];
		}
		else if (key.equals("TENSE")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[1];
		}
		else if (key.equals("INSTANTNESS")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[2];
		}
		else if (key.equals("VOICE")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[3];
		}
		else if (key.equals("PERFECTIVENESS")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[4];
		}
		else if (key.equals("INTERROGATION")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[5];
		}
		else if (key.equals("AFFIRMATION")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[6];
		}
		else if (key.equals("NUMBER")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			if (ssnd_s_Name.equals("SmVerb"))
				vl=a[7];
			else
				vl=a[1]; //for SmNoun
		}
		else if (key.equals("PERSON")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[8];
		}
		else if (key.equals("DEFINITENESS")){
			vl= ssnd_s_Attributes.get("SMaTTR");
			a=vl.split(",");
			vl=a[0];
		}
		else {
			vl= ssnd_s_Attributes.get(key);
			if (vl==null)
				if (key.equals("INSTANCE"))
					vl="";
				else
					throw new Exception_SSemasia_Operating("SSmNode.getAttribute: "
						+ "no such attribute: " +key);
		}
		return vl;
	}


	/**
	 * Returns an array containing all the argument-child-nodes.
	 *		The children that is NOT ssconjunctions.
	 */
	public SSmNode[] getChildArgNodes()
	{
		Vector<SSmNode> carg=new Vector<SSmNode>();
		SSmNode mn;
		for(int n=0; n<ssnd_s_Children.size(); n++)
		{
			mn=ssnd_s_Children.get(n);
			if ( ! mn.getName().equals("SmConjunction"))
				carg.add(mn);
		}

		SSmNode amn[]=new SSmNode[carg.size()];
		for(int k=0; k<carg.size(); k++)
		{
			amn[k]=carg.get(k);
		}
		return amn;
	}


	/**
	 * Returns an array containing all child-ssconjunction-nodes
	 * of this node.
	 */
	public SSmNode[] getChildConjNodes()
	{
		Vector<SSmNode> cc=new Vector<SSmNode>();
		SSmNode mn;
		for(int n=0; n<ssnd_s_Children.size(); n++)
		{
			mn=ssnd_s_Children.get(n);
			if (mn.getName().equals("SmConjunction"))
				cc.add(mn);
		}

		SSmNode mc[]=new SSmNode[cc.size()];
		for(int k=0; k<cc.size(); k++)
		{
			mc[k]=cc.get(k);
		}
		return mc;
	}


	/**
	 * Returns the child with the specific designator.
	 *
	 * @modified 2004.06.29
	 * @since 2004.06.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode getChildWithAttrXCPT(String des)
		throws Exception_SSemasia_Operating
	{
		SSmNode chn[]=getChildrenArray();
		SSmNode mn=null;
		for (int n=0; n<chn.length; n++)
			if (chn[n].getAttrXCPT().equals(des))
				mn=chn[n];
		return mn;
	}


	/**
	 * Returns the child with the	 specific label.
	 *
	 * @param label The label of the child.
	 * @modified 2004.06.21
	 * @since 2004.06.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode getChildWithLabel(String label)
		throws Exception_SSemasia_Operating
	{
		SSmNode mn=null;
		for (int n=0; n<ssnd_s_Children.size(); n++)
			if ((ssnd_s_Children.get(n)).getAttrLabel().equals(label))
				mn=ssnd_s_Children.get(n);
		return mn;
	}


	/**
	 * Returns the FIRST child with the specific name.
	 *
	 * @param name	The name of the child.
	 */
	public SSmNode getChildWithName(String name)
	{
		SSmNode chn[]=getChildrenArray();
		SSmNode mn=null;
		for (int n=0; n<chn.length; n++)
			if (chn[n].getName().equals(name))
				mn=chn[n];
		return mn;
	}


	/**
	 * Returns an array with the children of the node.
	 * @modified 2004.06.29
	 * @since 2004.06.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public SSmNode[] getChildrenArray()
	{
		SSmNode ch[]=new SSmNode[ssnd_s_Children.size()];
		for(int k=0; k<ssnd_s_Children.size(); k++)
		{
			ch[k]=ssnd_s_Children.get(k);
		}
		return ch;
	}


	/**
	 * Returns the comment-string associated with this node or null.
	 */
	public String getComment()
	{
		return smComment;
	}


	/**
	 * Returns the nesting-level of this node.<p>
	 *
	 * A nesting-level of zero indicates that this node is
	 * at the <code>outermost level</code>.
	 */
	public int getDepth()
	{
		int depth;
		SSmNode highernode = this;

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
	 * Returns this node's designator or "no" if there is no designator.
	 */
	public String getAttrXCPT()
		throws Exception_SSemasia_Operating
	{
		if (ssnd_s_Attributes.containsKey("XCPT"))
			return getAttribute("XCPT");
		else
			return "no";
	}


	/**
	 * Returns this node's label or "no" if there is no label.
	 * @modified 2003.11.18
	 * @since 2003.11.18 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getAttrLabel()
		throws Exception_SSemasia_Operating
	{
		if (ssnd_s_Attributes.containsKey("LABEL"))
			return getAttribute("LABEL");
		else
			return "no";
	}


	/**
	 * Returns the line's number (<code> >= 0</code>) in the
	 * source data on which the element is found.<p>
	 *
	 * If this method returns <code>0</code> there is no
	 * associated source data.
	 *
	 */
	public int getLineNr()
	{
			return this.smLineNr;
	}


	/**
	 * Returns the name of this node.
	 */
	public String getName()
	{
		return ssnd_s_Name;
	}


	/**
	 * Returns the next-higher-node of this node or <code>null</code>
	 * if this is the outermost node.
	 */
	public SSmNode getParent()
	{
		if (ssnd_s_Parent == null)
			return null;

		return ssnd_s_Parent;
	}


	/**
	 * Returns an array containing the ssconjunction-nodes of this node
	 * on the same level.
	 */
	public SSmNode[] getSiblingConjNodes()
		throws Exception_SSemasia_Operating
	{
		if (this.getName().equals("SmConjunction"))
			return null;
		else {
			SSmNode corelArr[], finalArr[];
			Vector<SSmNode> corelVec;
			SSmNode parentxnode = getParent();

			if (parentxnode == null)
				return null;

			corelArr = parentxnode.getChildConjNodes();
			corelVec = new Vector<SSmNode>(corelArr.length);

			for (int corel=0; corel<corelArr.length; corel++)
				if (corelArr[corel].hasArgNode(this))
					corelVec.addElement(corelArr[corel]);

			finalArr = new SSmNode[corelVec.size()];
			corelVec.copyInto(finalArr);

			return finalArr;
		}
	}


//************************************************************************
// has mentods
//************************************************************************


	/**
	 * Tests if a RELATION-NODE has this node as argument.
	 * @modified 2004.06.28
	 * @since 2004.06.28 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean hasArgNode(SSmNode arg)
		throws Exception_SSemasia_Operating
	{
		SSmNode ar[]=getArgNodes();
		for(int i=0; i<ar.length; i++)
		{
			if (ar[i].equals(arg)) return true;
		}

		return false;
	}


	/**
	 * Returns <code>true</code> if the specified corelaton is part
	 * of this node.
	 * @param corelaton The child-ssconjunction-node being checked for.
	 */
	public boolean hasChildConjNode(SSmNode corelaton)
	{
		SSmNode cc[]=getChildConjNodes();
		Vector<SSmNode> childCorelatons = new Vector<SSmNode>();
		for(int i=0; i<cc.length; i++)
		{
			childCorelatons.add(cc[i]);
		}
		return childCorelatons.contains(corelaton);
	}

	/**
	 * Returns <code>true</code> if the specified corelatons
	 * are part of this node.
	 * @param corelatonArr	The array of child-ssconjunction-nodes
	 * being checked for.
	 */
	public boolean hasChildConjNodes(SSmNode corelatonArr[])
	{
		SSmNode cc[]=getChildConjNodes();
		Vector<SSmNode> childCorelatons = new Vector<SSmNode>();
		for(int i=0; i<cc.length; i++)
		{
			childCorelatons.add(cc[i]);
		}
		for (int rel = 0; rel < corelatonArr.length; rel++)
			if (!childCorelatons.contains(corelatonArr[rel]))
				return false;

		return true;
	}


	/**
	 * Returns <code>true</code> if the specified part is
	 * a child-argument-node of this node.
	 * @param part
	 *		The child-argument-node being checked for.
	 */
	public boolean hasChildArgNode(SSmNode part)
	{
		SSmNode carg[]=getChildArgNodes();
		Vector<SSmNode> childArguments = new Vector<SSmNode>();
		for(int i=0; i<carg.length; i++)
		{
			childArguments.add(carg[i]);
		}
		return childArguments.contains(part);
	}


	/**
	 * Returns <code>true</code> if the specified nodes are
	 * child-argument-nodes of this node.
	 * @param argsArr the array of child-nodes being checked for.
	 */
	public boolean hasChildArgNodes(SSmNode argsArr[])
	{
		SSmNode carg[]=getChildArgNodes();
		Vector<SSmNode> childArguments = new Vector<SSmNode>();
		for(int i=0; i<carg.length; i++)
		{
			childArguments.add(carg[i]);
		}
		for (int con = 0; con < argsArr.length; con++)
			if (!childArguments.contains(argsArr[con]))
				return false;

		return true;
	}

	/**
	 * Returs <code>true</code> if this node has children.
	 */
	public boolean isStem()
	{
		if (ssnd_s_Children.size()>0)
			return true;

		return false;
	}


	/**
	 * Returns <code>true</code> if this node is a SmSpecialNoun.<br/>
	 * Its names starts with "Spec".
	 * @modified
	 * @since 2001.06.23
	 * @author HoKoNoUmo
	 */
	public boolean isSmSpecialNoun()
	{
		return getName().startsWith("SmSpecial");
	}


	/**
	 * Returns <code>true</code> if this node is a lower-node (not child)
	 * of the specified node.
	 * @param higher	The node being checked for as the higher-node
	 * of this node.
	 */
	public boolean isLowerOf(SSmNode higher)
	{
		SSmNode highernode;
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


//************************************************************************
//************************************************************************


	/**
	 * Adds or modifies an attribute.
	 *
	 * @param key
	 *		The name of the attribute.
	 * @param value
	 *		The value of the attribute.
	 *
	 */
	public void setAttribute(String key, String value)
	{
		this.ssnd_s_Attributes.put(key, value);
	}


	/**
	 * Sets the comment string for this node.
	 *
	 * @param newComment	the new comment string for this node.
	 */
	public void setComment(String newComment)
	{
		smComment = newComment;
	}


	/**
	 * Sets the designator attribute of this node.
	 */
	public void setDesignator(String newDesignator)
	{
		setAttribute("XCPT", newDesignator);
	}


	/**
	 * Sets the label attribute of this node.
	 * @modified 2003.11.18
	 * @since 2003.11.18 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void setLabel(String newLabel)
	{
		setAttribute("LABEL", newLabel);
	}


	/**
	 * Sets the language of this node.
	 * @modified 2006.01.11
	 * @since 2006.01.11 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void setLang(String lg)
	{
		ssnd_s_Lng=lg;
	}


	/**
	 * Sets the name of this node.
	 */
	public void setName(String newName)
	{
		ssnd_s_Name = newName;
	}


	/**
	 * Sets this node's next-higher-node.
	 * Null indicates the outermost node.
	 *
	 * @param highernode this node's next-higher-node or null.
	 * @modified 2003.03.09
	 * @since 2001.02.28
	 * @author HoKoNoUmo
	 */
	public void setParent(SSmNode highernode)
		throws Exception_SSemasia_Operating
	{
		if (ssnd_s_Parent.equals(highernode))
			return;

		ssnd_s_Parent = highernode;
		highernode.addChild(this);
	}


	/**
	 * Removes an attribute.<p>
	 *
	 * <b>Preconditions:</b><br/>
	 *	- <code>name != null</code>
	 *	- <code>name</code> is a valid XML unique-name
	 *
	 * @param key The name of the attribute.
	 */
	public void removeAttribute(String key)
	{
			this.ssnd_s_Attributes.remove(key);
	}


	/**
	 * Removes a child element.
	 *
	 * @param child The child element to remove.
	 *
	 */
	public void removeChild(SSmNode child)
	{
		this.ssnd_s_Children.removeElement(child);
	}


	/**
	 * Maps a java-SSmNode to a char-SSmNode
	 *
	 * @param wr		The writer to write the char-data to.
	 * @throws java.io.IOException
	 *		If the char-data could not be written to the writer.
	 * @modified 2004.10.17
	 * @since 2004.06.29 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void mapToCharNode(Writer wr)
		throws IOException
	{
/*
		System.out.println();
		System.out.println("SSmNode XML-ELEMENT:");
		System.out.println("NAME			 = " +ssnd_s_Name);
		System.out.println("LABEL			 = " +getAttrLabel());
		System.out.println("TERMR = " +getAttrXCPT());

		System.out.println("CHILDREN	 = ");
		SSmNode chn[]=getChildrenArray();
		for(int i=0; i<chn.length; i++)
		{
			System.out.print(chn[i].getName() +", ");
		}

		System.out.println("ATTRIBUTES = ");
		if (!ssnd_s_Attributes.isEmpty()) {
			for (Iterator i=ssnd_s_Attributes.entrySet().iterator(); i.hasNext();) {
				Map.Entry e = (Map.Entry) i.next();
				String key =  e.getKey();
				String value =  e.getValue();
				System.out.print(value +", ");
			}
		}
*/
		if (ssnd_s_Name == null) {
			return;
		}

		wr.write("\n"+smIndent+"<"+ssnd_s_Name);
		if (! ssnd_s_Attributes.isEmpty()) {
			for (Iterator i=ssnd_s_Attributes.entrySet().iterator(); i.hasNext(); ) {
				Map.Entry e = (Map.Entry) i.next();
				String key =  (String)e.getKey();
				String value =  (String)e.getValue();
				wr.write(" " +key +"=\"" +value +"\"");
			}
		}
		if (ssnd_s_Children.isEmpty()) {
			wr.write('/'); wr.write('>');
		} else {
			wr.write('>');
			SSmNode chn[]=getChildrenArray();
			for (int j=0; j<chn.length; j++){
				SSmNode child = chn[j];
				child.smIndent=smIndent+"		 ";
				child.mapToCharNode(wr);
			}
			wr.write("\n"+smIndent+"</"+ssnd_s_Name+">");
		}
	}


	/**
	 * Returns char-SSmNode  of a java-SSmNode.
	 * @modified 2004.10.17
	 * @since 2004.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String toString()
	{
		Writer wr = new StringWriter();
		try {
			mapToCharNode(wr);
		} catch (IOException e) {
			System.out.println("SSmNode.toString: IOException");
		}
		return wr.toString();
	}


}
