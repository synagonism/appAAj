package pk_Util;

/**
 * This java-class represents ANY node of a PART-WHOLE TreeStructure.
 *
 * @modified 2007.04.03
 * @since 2004.10.19 (v00.02.00)
 * @author HoKoNoUmo
 */

import java.util.*;
import java.io.*;

public class NodeWhoPar
{

	/* Every node has a name denoting what specific node it is. */
	public String ntp_Name; //the TYPE

	/* The next-higher-node (parent) of this node. */
	public NodeWhoPar ntp_Parent;

	/* The first child nodo. */
	public NodeWhoPar ntp_ChildFirst;

	/* The next sibling nodo. */
	public NodeWhoPar ntp_SiblNext;
	/* The previous sibling nodo. */
	public NodeWhoPar ntp_SiblPrev;

	/* We need it to write it in a character-writer. */
	public String ntp_Indent="";


	/**
	 * Constructs a node with the specified name and parent.
	 * We can NOT have a node without a name and parent.
	 *
	 * @param name	The name (type) of this node.
	 * @param parent	The parent-node of this node.
	 * @modified 2007.04.04
	 * @since 2004.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar(String name, NodeWhoPar parent)
	{
		ntp_Name=name;
		ntp_Parent=parent;
		ntp_ChildFirst=null;
		ntp_SiblNext=null;
		ntp_SiblPrev=null;
	}


	/**
	 * Appends a child-nodo to this-nodo.
	 *
	 * @modified 2007.04.03
	 * @since 2007.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void appendChild(NodeWhoPar child)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar cparent = child.getParent();
		if ((cparent != null) && (cparent != this))
			System.out.println(
				"NodeWhoPar.appendChild: child belongs to another node already.");

		NodeWhoPar c = getChildLast();
		if (c==null){
			ntp_ChildFirst = child;
			child.ntp_SiblPrev = null;
			child.ntp_SiblNext = null;
		}
		else {
			c.ntp_SiblNext = child;
			child.ntp_SiblPrev = c;
			child.ntp_SiblNext = null;
		}
		child.ntp_Parent = this;
	}


	/**
	 * Appends a sibling-nodo on the SAME parent with this.
	 *
	 * @modified 2007.04.03
	 * @since 2007.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void appendSibl(NodeWhoPar sibl)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar sparent = sibl.getParent();
		if ((sparent!=null) && (sparent!=ntp_Parent))
			System.out.println(
				"NodeWhoPar.appendSibl: child belongs to another node already.");

		NodeWhoPar s = getSiblLast();
		//the last it is not null because this node is at leat a sibling.
		s.ntp_SiblNext = sibl;
		sibl.ntp_SiblPrev = s;
		sibl.ntp_SiblNext = null;
		sibl.ntp_Parent = ntp_Parent;
	}


	/**
	 * Returns an array with the children of THIS node.
	 *
	 * @modified
	 * @since 2004.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar[] getChildArray()
	{
		NodeWhoPar ch[]=new NodeWhoPar[getNumOfChildren()];
		NodeWhoPar c = ntp_ChildFirst;
		int count = 0;
		while (c!=null) {
			ch[count]=c;
			c = c.ntp_SiblNext;
			count++;
		}
		return ch;
	}


	/**
	 *
	 * @modified 2007.04.03
	 * @since 2007.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getChildFirst() {
		return ntp_ChildFirst;
	}


	/**
	 * Retrieves the last child of this nodo. IF null, there is no child.
	 *
	 * @modified 2007.04.03
	 * @since 2007.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getChildLast() {
		NodeWhoPar c = ntp_ChildFirst;
		if (c == null) {
			return null;
		}
		while (c.ntp_SiblNext != null) {
			c = c.ntp_SiblNext;
		}
		return c;
	}

	/**
	 * Returns the child with the specified index.
	 * If index is greater than the number of children, null is returned.
	 *
	 * @param i	The index (0-n) of child.
	 * @modified 2007.04.05
	 * @since 2004.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getChild(int i)
	{
		NodeWhoPar ch[] = getChildArray();
		if (i >= ch.length)
			return null;
		else
			return ch[i];
	}


	/**
	 * Returns the FIRST child with a specific name.
	 * If <code>null</code>, there is no child with this name.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getChildWithName(String name)
	{
		NodeWhoPar ch[]=getChildArray();
		for (int n=0; n<ch.length; n++){
			if (ch[n].getName().equals(name))
				return ch[n];
		}
		return null;
	}


	/**
	 * Returns the sibling-index (0-n) of THIS node, on the same parent.
	 * If -1, then problem.
	 *
	 * @modified 2007.04.05
	 * @since 2007.04.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getIndexSibl()
	{
		NodeWhoPar ch[]=ntp_Parent.getChildArray();
		for (int n=0; n<ch.length; n++){
			if (ch[n].equals(this))
				return n;
		}
		return -1;
	}


	/**
	 * Returns the nesting-level of this node. A nesting-level of zero
	 * indicates that this node is at the outermost level.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getDepth()
	{
		int depth;
		NodeWhoPar highernode = this;

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
	 * Returns the name for this node.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getName()
	{
		return ntp_Name;
	}


	/**
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public int getNumOfChildren()
	{
		NodeWhoPar c = ntp_ChildFirst;
		int count = 0;
		while (c!=null) {
			c = c.ntp_SiblNext;
			count++;
		}
		return count;
	}


	/**
	 * Returns the next-higher-node of this node or null
	 * if this is the outermost node.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getParent()
	{
		return ntp_Parent;
	}


	/**
	 * Returns the sibling (on the same parent) with the specified index.
	 * If index is greater than the number of siblings, null is returned.
	 *
	 * @param i	The index (0-n) of child.
	 * @modified 2007.04.05
	 * @since 2007.04.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getSibl(int i)
	{
		NodeWhoPar s[] = ntp_Parent.getChildArray();
		if (i >= s.length)
			return null;
		else
			return s[i];
	}


	/**
	 * Returns the first sibling node, on the SAME parent.
	 * If <code>null</code>, then THIS node is the first one.
	 *
	 * @modified 2007.04.05
	 * @since 2007.04.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getSiblFirst() {
		NodeWhoPar s = ntp_SiblPrev;
		if (s == null) {
			return null;
		}
		while (s.ntp_SiblPrev != null) {
			s = s.ntp_SiblPrev;
		}
		return s;
	}


	/**
	 * Returns the last sibling of this node, on the SAME parent.
	 * If <code>null</code>, then this node is the last one.
	 *
	 * @modified 2007.04.05
	 * @since 2007.04.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getSiblLast() {
		NodeWhoPar s = ntp_SiblNext;
		if (s == null) {
			return null;
		}
		while (s.ntp_SiblNext != null) {
			s = s.ntp_SiblNext;
		}
		return s;
	}


	/**
	 * Gets the next sibling nodo, on the SAME parent.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public NodeWhoPar getSiblNext() {
		return ntp_SiblNext;
	}


	/**
	 * Gets the previous sibling nodo, on the SAME parent.
	 * If null, this is the first child of parent.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	 public NodeWhoPar getSiblPrev() {
		return ntp_SiblPrev;
	}


	/**
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean hasChildren() {
		return ntp_ChildFirst != null;
	}


	/**
	 * @param i	The index (0-n) where we want to insert the child.
	 * @modified 2007.04.03
	 * @since 2007.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void insertChild(int i, NodeWhoPar child)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar cparent = child.getParent();
		if ((cparent != null) && (cparent != this))
			System.out.println(
				"NodeWhoPar.insertChild: child belongs to another node already.");

		if (i >= getNumOfChildren())
			appendChild(child);
		else {
			NodeWhoPar oldch = getChild(i);
			child.ntp_SiblPrev = oldch.ntp_SiblPrev;
			child.ntp_SiblNext = oldch;
			child.ntp_Parent = this;
		}
	}


	/**
	 *
	 * @modified 2007.04.03
	 * @since 2007.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void insertSiblNext(NodeWhoPar sibl)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar sparent = sibl.getParent();
		if ((sparent!=null) && (sparent!=ntp_Parent))
			System.out.println(
				"NodeWhoPar.insertNext: child belongs to another node already.");

		sibl.ntp_SiblPrev = this;
		sibl.ntp_SiblNext = ntp_SiblNext;
		sibl.ntp_Parent = this;
	}


	/**
	 *
	 * @modified 2007.04.03
	 * @since 2007.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void insertSiblPrev(NodeWhoPar sibl)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar sparent = sibl.getParent();
		if ((sparent!=null) && (sparent!=ntp_Parent))
			System.out.println(
				"NodeWhoPar.insertPrevious: child belongs to another node already.");

		sibl.ntp_SiblPrev = ntp_SiblPrev;
		sibl.ntp_SiblNext = this;
		sibl.ntp_Parent = this;
	}


	/**
	 *
	 * @modified 2007.04.03
	 * @since 2007.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void insertSibl(int i, NodeWhoPar sibl)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar sparent = sibl.getParent();
		if ((sparent!=null) && (sparent!=ntp_Parent))
			System.out.println(
				"NodeWhoPar.insertNext: child belongs to another node already.");

		NodeWhoPar olds = getSibl(i);
		if (olds == null)
			appendSibl(sibl);
		else {
			sibl.ntp_SiblPrev = olds.ntp_SiblPrev;
			sibl.ntp_SiblNext = olds;
			sibl.ntp_Parent = this;
		}
	}


	/**
	 *
	 * @modified 2007.04.05
	 * @since 2007.04.05 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isChild(NodeWhoPar child)
	{
		NodeWhoPar c = ntp_ChildFirst;
		while (c!=null) {
			if (c.equals(child))
				return true;
			c = c.ntp_SiblNext;
		}
		return false;
	}


	/**
	 * Returns true if this node has NO children.
	 *
	 * @modified 2007.04.05
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isLeaf()
	{
		if (hasChildren())
			return false;
		else
			return true;
	}


	/**
	 * Returns true if THIS node is a lower-node (not child) of
	 * the specified node.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public boolean isLowerOf(NodeWhoPar higher)
	{
		NodeWhoPar highernode;
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
	 * Sets a new-name for this node.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void setName(String newName)
	{
		ntp_Name = newName;
	}


	/**
	 * Sets this node's next-higher-node. Also appends this node in
	 * the child list of parent.
	 * If parent is null, then THIS node is the outermost node.
	 *
	 * @modified 2007.04.04
	 * @since 2004.10.19
	 * @author HoKoNoUmo
	 */
	public void setParent(NodeWhoPar highernode)
	{
		ntp_Parent = highernode;
		if (highernode!=null)
			if (!highernode.isChild(this))
				highernode.appendChild(this);
	}


	/**
	 * Removes THIS node.
	 *
	 * @modified 2007.04.06
	 * @since 2007.04.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void remove()
	{
		NodeWhoPar sp = ntp_SiblPrev;
		NodeWhoPar sn = ntp_SiblNext;
		sp.ntp_SiblNext = sn;
		sn.ntp_SiblPrev = sp;

		ntp_Parent = null;
	}


	/**
	 * Removes ANY specified node.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void remove(NodeWhoPar child)
	{
		NodeWhoPar cp = child.ntp_SiblPrev;
		NodeWhoPar cn = child.ntp_SiblNext;
		cp.ntp_SiblNext = cn;
		cn.ntp_SiblPrev = cp;

		child.ntp_Parent = null;
	}


	/**
	 * Removes a child with a given index.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeChild(int i)
	{
		remove(getChild(i));
	}


	/**
	 * Removes the next-sibling node, on same parent.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeSiblNext()
	{
		if (getSiblNext()!=null)
			ntp_Parent.removeChild(getIndexSibl()+1);
	}


	/**
	 * Removes the previous sibling node, on same parent.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeSiblPrev()
	{
		if (getSiblPrev()!=null)
			ntp_Parent.removeChild(getIndexSibl()-1);
	}


	/**
	 * Removes the sibling node, on same parent, with index i.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void removeSibl(int i)
	{
		if (getSibl(i)!=null)
			ntp_Parent.removeChild(i);
	}


	/**
	 * Replaces THIS node with another. The new reserves its children.
	 *
	 * @modified 2007.04.06
	 * @since 2007.04.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void replace(NodeWhoPar nn)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar nnparent = nn.getParent();
		if ((nnparent != null) && (nnparent != ntp_Parent)){
			System.out.println("NodeWhoPar.replace: node belongs to another node already.");
			nnparent = ntp_Parent;
		}
		ntp_SiblPrev.ntp_SiblNext = nn;
		ntp_SiblNext.ntp_SiblPrev = nn;
		nn.ntp_SiblPrev = ntp_SiblPrev;
		nn.ntp_SiblNext = ntp_SiblNext;
	}


	/**
	 * Replaces ANY old-node in the treeStructure with a new one.
	 *
	 * @modified 2007.04.06
	 * @since 2007.04.06 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void replace(NodeWhoPar on, NodeWhoPar nn)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar nnparent = nn.getParent();
		if ((nnparent != null) && (nnparent != on.ntp_Parent)){
			System.out.println("NodeWhoPar.replace: node belongs to another node already.");
			nnparent = on.ntp_Parent;
		}

		on.ntp_Parent = null;

		on.ntp_SiblPrev.ntp_SiblNext = nn;
		on.ntp_SiblNext.ntp_SiblPrev = nn;
		nn.ntp_SiblPrev = on.ntp_SiblPrev;
		nn.ntp_SiblNext = on.ntp_SiblNext;
	}


	/**
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void replaceChild(int i, NodeWhoPar newChild)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar cparent = newChild.getParent();
		if ((cparent != null) && (cparent != this)){
			System.out.println("NodeWhoPar.replaceChild: child belongs to another node already.");
			cparent = this;
		}

		NodeWhoPar oldc = getChild(i);
		if (oldc == null)
			throw new RuntimeException("There is no child to replace!!");
		oldc.ntp_Parent = null;

		oldc.ntp_SiblPrev.ntp_SiblNext = newChild;
		oldc.ntp_SiblNext.ntp_SiblPrev = newChild;
		newChild.ntp_SiblPrev = oldc.ntp_SiblPrev;
		newChild.ntp_SiblNext = oldc.ntp_SiblNext;
	}


	/**
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void replaceSiblNext(NodeWhoPar newSibl)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar sparent = newSibl.getParent();
		if ((sparent!=null) && (sparent!=ntp_Parent)){
			System.out.println("NodeWhoPar.replaceSiblNext: child belongs to another node already.");
			sparent = ntp_Parent;
		}

		NodeWhoPar olds = getSiblNext();
		if (olds == null)
			throw new RuntimeException("There is no sibling to replace!!");
		olds.ntp_SiblPrev.ntp_SiblNext = newSibl;
		olds.ntp_SiblNext.ntp_SiblPrev = newSibl;
		newSibl.ntp_SiblPrev = this;
		newSibl.ntp_SiblNext = olds.ntp_SiblNext;
	}


	/**
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void replaceSiblPrev(NodeWhoPar newSibl)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar sparent = newSibl.getParent();
		if ((sparent!=null) && (sparent!=ntp_Parent))
			System.out.println("NodeWhoPar.replaceSiblPrev: child belongs to another node already.");
			sparent = ntp_Parent;

		NodeWhoPar olds = getSiblPrev();
		if (olds == null)
			throw new RuntimeException("There is no sibling to replace!!");

		olds.ntp_SiblPrev.ntp_SiblNext = newSibl;
		olds.ntp_SiblNext.ntp_SiblPrev = newSibl;
		newSibl.ntp_SiblPrev = olds.ntp_SiblPrev;
		newSibl.ntp_SiblNext = this;
	}


	/**
	 * Replaces a sibling of this node (on the same parent) with index i,
	 * with a new one.
	 *
	 * @modified 2007.04.04
	 * @since 2007.04.04 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void replaceSibl(int i, NodeWhoPar newSibl)
	{
		//every nodo belongs to ONE nodo.
		NodeWhoPar sparent = newSibl.getParent();
		if ((sparent!=null) && (sparent!=ntp_Parent)){
			System.out.println("NodeWhoPar.replaceSibling: child belongs to another node already.");
			sparent = ntp_Parent;
		}

		NodeWhoPar olds = getSibl(i);
		if (olds == null)
			throw new RuntimeException("There is no sibling to replace!!");
		olds.ntp_SiblPrev.ntp_SiblNext = newSibl;
		olds.ntp_SiblNext.ntp_SiblPrev = newSibl;
		newSibl.ntp_SiblPrev = olds.ntp_SiblPrev;
		newSibl.ntp_SiblNext = olds.ntp_SiblNext;
	}


	/**
	 * Maps a java-NodeWhoPar to a char-NodeWhoPar by printing its name and
	 * using indents to show the treeStructure's levels.
	 *
	 * @param wr	The writer to write the char-data to.
	 * @throws java.io.IOException
	 *		If the char-data could not be written to the writer.
	 * @modified
	 * @since 2004.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public void mapToCharNode(Writer wr)
		throws IOException
	{
		if (ntp_Name == null) {
			return;
		}

		wr.write("\n"+ ntp_Indent+"<"+ ntp_Name);
		if (!hasChildren()) {
			wr.write('/'); wr.write('>');
		} else {
			wr.write('>');
			NodeWhoPar chn[]=getChildArray();
			for (int j=0; j<chn.length; j++){
				NodeWhoPar child = chn[j];
				child.ntp_Indent= ntp_Indent+"    ";
				child.mapToCharNode(wr);
			}
			wr.write("\n"+ ntp_Indent+"</"+ ntp_Name+">");
		}
	}


	/**
	 * Maps the java-NodeWhoPar to char-TxNode (String).
	 * @modified
	 * @since 2004.10.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String toString()
	{
		Writer wr = new StringWriter();
		try {
			mapToCharNode(wr);
		} catch (IOException e) {
			System.out.println("NodeWhoPar.toString: IOException");
		}
		return wr.toString();
	}


}
