import pk_XKBManager.*;
import pk_XKBEditor.*;
import pk_Logo.*;
import pk_Util.*;
import pk_SSemasia.*;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class Test
{
//	String file = "c:\\java\\AAj\\working\\concepts\\eng\\infotech\\AAj'Sensorial-BConcept@hknu.it-19.xml";
//	String file = "c:\\java\\AAj\\working\\concepts\\eng\\infotech\\Integer@hknu.it-35#19.xml";

	public static void main(String[] args)
	{
		new Test();
	}

	public Test()
	{

/*
		NodoTP n0 = new NodoTP("lev0", null);

		NodoTP n1a = new NodoTP("lev1a", null);
		NodoTP n1b = new NodoTP("lev1b", null);
		NodoTP n1c = new NodoTP("lev1c", null);

		NodoTP n2a = new NodoTP("lev2a", null);
		NodoTP n2b = new NodoTP("lev2b", null);
		NodoTP n2c = new NodoTP("lev2c", null);
		NodoTP n2d = new NodoTP("lev2d", null);
		NodoTP n2e = new NodoTP("lev2e", null);
		NodoTP n2f = new NodoTP("lev2f", null);

		n0.appendChild(n1a);
		n0.appendChild(n1b);
		n1a.appendSibl(n1c);

		n1a.appendChild(n2a);
		n1b.appendChild(n2b);
		n1b.appendChild(n2c);
		n1b.appendChild(n2d);
		n1c.appendChild(n2e);

//		n1b.replaceChild(5,n2a);
		System.out.println(n0.toString());

		System.out.println("index of sibl l2d: "+n2d.getIndexSibl());
		System.out.println("index of child l2d: "+n1b.getChild(2).getIndexSibl() );

//		n0.replaceChild(1,n2f);
//		n1b.replace(n2f);
		n1a.replaceSiblNext(n2f);
		System.out.println(n0.toString());

//		n0.appendChild(n1b);
//		System.out.println(n0.toString());


//		System.out.println("first sibling of n2d: "+n2d.getSiblFirst().getName());
//		System.out.println("depth of l2d: "+n2d.getDepth());
//		System.out.println("n2b's name: "+n1b.getChild(0).getName());
//		System.out.println("n1b's name: "+n0.getChild(1).getName());

//		String a1[] = {"bbb", "zzz", "aa", "dd", "ddd"};
//		String a2[] = {"bbb", "zzz", "aa", "dd", "ddd"};
//		if (Util.areTwoArrayStringEqual(a1,a2))
//			System.out.println("equal");
//		else
//			System.out.println("ANequal");

//		String a[] = {"bbb", "zzz", "aa", "dd", "ddd"};
//		String b[] = Util.sortArrayString(a,"eng");
//		Util.printArrayString(b);
//			Insert ins = new Insert();
//			ins.updateTermFiles("hknu.meta-1", "all");
*/
		String ar[]=Textual.getTermsOfTxConceptAsArrayIfXConcept(
				"hknu.symb-23","NameNounNone", "eng");
		Util.printArrayString(ar);
		System.out.println("test");
//		String s=n.getForms();
//		System.out.println(s);
//		Vector v = new Vector();
//		v.add("aaaAtt1@hknu.it-38.xml,aaaGen@hknu.it-75.xml");
//		replaceGeneric("it-37", v);

  }







}
