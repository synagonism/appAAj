/*
 * Maper_XElementToText_Eng.java - Generates the English-Text of a Sensorial-Element.
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Generic Public License for more details.
 *
 * You should have received a copy of the GNU Generic Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package pk_XKBManager;

import pk_Util.Extract;
import pk_Util.Textual;
import pk_Util.Util;
import java.io.IOException;
import java.util.Vector;

/**
 * <b>INPUT</b>: an xml-element of a sensorial-b-concept as a vector,
 * <b>OUTPUT</b>: the english-text of this element.<p>
 *
 * <b>CODE</b>:<br/><code>
 * Maper_XElementToText_Eng mp = new Maper_XElementToText_Eng(xElm);
 * output = mp.textOut;</code>
 *
 * @modified
 * @since 2009.01.15 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_XElementToText_Eng extends Maper_XElementToText
{


	/**
	 * The constructor.
	 *
	 * @param xElm
	 * 		The xml-element we want to map with text.
	 * @modified 2009.01.15
	 * @since 2009.01.15 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_XElementToText_Eng(Vector<String> xElm)
	{
		xmlElement = xElm;

		Util.log(null);
		Util.log("\tSTART-MAPPING: xmlElement to English-Text:");

		String elm = xElm.firstElement();
		if (elm.startsWith("<REFINO")){
			textOut=mapRefino(xElm);
		} else if (elm.startsWith("<PRODUCT")){
			textOut=mapPRODUCT(xElm);
		} else if (elm.startsWith("<SOURCE")){
			textOut=getNameOfConceptInXElement(elm);//the source-element has one line.
		}

	}


	/**
	 * Maps the text of a PRODUCT element of a definition.
	 *
	 * @modified 2009.01.15
	 * @since 2009.01.15 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapPRODUCT(Vector<String> xElm)
	{
		String to="";
		String fln = xElm.firstElement();//FirstLine

		if (fln.endsWith("/>"))
			to=getNameOfConceptInXElement(fln);
		else {
			//the element has many lines.
//			<PRODUCT>
//			<XCPT LABEL="b1" FRnAME="Tree-network's-node@hknu.symb-11@"/>
//			<XCPT LABEL="b2" FRnAME="Number_every@hknu.symb-22#1@"/>
//			<XCPT LABEL="b3" FRnAME="Quantiteino@hknu.symb-23.xml" ARGS="b1,b2"/>
//			</PRODUCT>

			//find relations.
			Vector<String> vor = new Vector<String>();//VectorOfRelations
			for (String l : xElm)
				if (l.indexOf("ARGS=")!=-1)
					vor.add(l);

			//find text for each relation.
			//the text of the last, is the text of product.
			for (int i=0; i<vor.size(); i++){
				String rl = vor.get(i);//line with relation

				String[] rlArg=(Util.getXmlAttribute(rl,"ARGS")).split(",");//the arguments
				//<XCPT LABEL="b3" FRnAME="Quantiteino@hknu.symb-23.xml" ARGS="b1,b2"/>
				//we must know the "syntax" of relation.
				Extract ex = new Extract();
				Vector<String> vos = ex.extractName(Util.getXmlAttribute(rl,"FRnAME"),
											"NameNone","eng");
				if (vos.size()!=0){
					//<NameNone ARGS="1:Entity@hknu.symb-7@,2:Quantity@hknu.symb-18@"
					//TXeXP1_B="2,nnAj01" TXeXP1_A="1,nnCsNm01" EXMPL="5 man" CREATED="2009.01.19" AUTHOR="HoKoNoUmo"/>
					String stx = vos.firstElement();//syntax

					//a) text of ArgumentBefore
					String[] stxExB=(Util.getXmlAttribute(stx,"TXeXP1_B")).split(",");//ExpressionBefore
					//TXeXP1_B="2,nnAj01"
					//From the VectorElement we find the right argument
					String ab="";//ArgumentBefore
					String abLabel=rlArg[Integer.parseInt(stxExB[0])-1];
					for (String l : xElm){
						if (l.indexOf("LABEL=\""+abLabel) != -1)
							ab=l;
					}
					//ab=<XCPT LABEL="b2" FRnAME="Number_every@hknu.symb-22#1@"/>
					String abCpt=Util.getXmlAttribute(ab,"FRnAME");
					//get LogalConcept nnAj01
					String abTxCpt= Textual.getTxConceptIndividualIfXConcept(
							abCpt,stxExB[1],"eng");

					//a) text of ArgumentAfter
					String[] stxExA=(Util.getXmlAttribute(stx,"TXeXP1_A")).split(",");
					String aa="";
					String aaLabel=rlArg[Integer.parseInt(stxExA[0])-1];
					for (String l : xElm){
						if (l.indexOf("LABEL=\""+aaLabel) != -1)
							aa=l;
					}
					String aaCpt=Util.getXmlAttribute(aa,"FRnAME");
					String aaTxCpt= Textual.getTxConceptIndividualIfXConcept(
							aaCpt,stxExA[1],"eng");

					to=abTxCpt +" " +aaTxCpt;
					//nnn
				} else{
					vos = ex.extractName(Util.getXmlAttribute(rl,"FRnAME"),
											"Name_Conjunction","eng");
					if (vos.size()!=0){
						//toDo

					} else {
						vos = ex.extractName(Util.getXmlAttribute(rl,"FRnAME"),
												"Name_Verb","eng");
					}
				}

				//store the text with the label of relation

				if (i+1 == vor.size()){
					//this is the last ruturn it

				}
			}

		}

		return to;
	}


	/**
	 * Returns the text of a refino xelement, given as a vector-of-lines.
	 *
	 * @modified 2009.11.16
	 * @since 2009.11.16 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public String mapRefino(Vector<String> xElm)
	{
		String txt="";
		String arg1="", arg2="", arg3="";
		Vector<String> rfn;
		int idx=0;
		String rfnName= Util.getXmlAttribute(xElm.get(0), "FRnAME");

		if (rfnName.equals("refino_non")){
			//one argument
			arg1= xElm.get(1);
			if (arg1.startsWith("<XCPT"))
				arg1= getNameOfConceptInXElement(arg1);
			else {
				//the argument is refino.
				arg1= mapRefino(Util.extractRefinoAtIndex(xElm,1));
			}
			txt= "not " +arg1;
		}

		else if (rfnName.equals("refino_eitherOr")){
			//two arguments
			arg1= xElm.get(1);
			if (arg1.startsWith("<XCPT")){
				arg1= getNameOfConceptInXElement(arg1);
				arg2= xElm.get(2);
				if (arg2.startsWith("<XCPT"))
					arg2= getNameOfConceptInXElement(arg2);
				else
					arg2= mapRefino(Util.extractRefinoAtIndex(xElm,2));
			} else {
				//the arg1 is refino.
				rfn= Util.extractRefinoAtIndex(xElm,1);
				arg1= mapRefino(rfn);
				arg2= xElm.get(rfn.size()+1);
				if (arg2.startsWith("<XCPT"))
					arg2= getNameOfConceptInXElement(arg2);
				else
					arg2= mapRefino(Util.extractRefinoAtIndex(xElm,rfn.size()+1));
			}
			txt= "either " +arg1 +" or " +arg2;
		}

		else if (rfnName.equals("refino_betweenAnd")){
			//three arguments
			arg1= xElm.get(1);
			if (arg1.startsWith("<XCPT")){
				arg1= getNameOfConceptInXElement(arg1);
				arg2= xElm.get(2);
				if (arg2.startsWith("<XCPT")){
					arg2= getNameOfConceptInXElement(arg2);
					arg3= xElm.get(3);
					if (arg3.startsWith("<XCPT")) {
						arg3= getNameOfConceptInXElement(arg3);
					} else {
						arg3= mapRefino(Util.extractRefinoAtIndex(xElm,3));
					}
				} 	else {
					//arg2 refino
					rfn= Util.extractRefinoAtIndex(xElm,2);
//Util.printVectorOfString(rfn);
					arg2= mapRefino(rfn);
					idx= rfn.size()+2;
					arg3= xElm.get(idx);
//System.out.println("===");
//System.out.println("index:"+idx);
//System.out.println(arg3);
//Util.printVectorOfString(xElm);
					if (arg3.startsWith("<XCPT")) {
						arg3= getNameOfConceptInXElement(arg3);
					} else {
						arg3= mapRefino(Util.extractRefinoAtIndex(xElm,idx));
					}
				}
			} else {
				//the arg1 is refino.
				rfn= Util.extractRefinoAtIndex(xElm,1);
				arg1= mapRefino(rfn);
				idx= rfn.size()+1;
				arg2= xElm.get(idx);
				if (arg2.startsWith("<XCPT")) {
					arg2= getNameOfConceptInXElement(arg2);
					idx= idx+1;
					arg3= xElm.get(idx);
					if (arg3.startsWith("<XCPT")) {
						arg3= getNameOfConceptInXElement(arg3);
					} else {
						arg3= mapRefino(Util.extractRefinoAtIndex(xElm,idx));
					}
				} else {
					//arg2 is refino
					rfn= Util.extractRefinoAtIndex(xElm,idx);
					arg2= mapRefino(rfn);
					idx= idx +rfn.size();//it has increased at second argument
					arg3= xElm.get(idx);
					if (arg3.startsWith("<XCPT")) {
						arg3= getNameOfConceptInXElement(arg3);
					} else {
						arg3= mapRefino(Util.extractRefinoAtIndex(xElm,idx));
					}
				}
			}

			txt= arg3 +" between " +arg1 +" and " +arg2;
		}


		return txt;
	}


}
