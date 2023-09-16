/*
 * Maper_XDefinitionToText_Eng.java - Generates the Text of a Sensorial-Definition.
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

import pk_Util.Util;
import pk_Util.Textual;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Vector;

/**
 * <b>INPUT</b>: an xml-Definition.
 * <b>OUTPUT</b>: the Text of the definition in English-language.<p>
 *
 * <b>CODE</b>: <code><br/>
 * Maper_XDefinitionToText_Eng mp = new Maper_XDefinitionToText_Eng(xDef);<br/>
 * output = mp.txtDefinition;</code>
 *
 * @modified 2009.10.12
 * @since 2008.10.17 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_XDefinitionToText_Eng
{

	/** The output text-definition */
	public String txtDefinition;


	/**
	 * @param xDef
	 * 		The xml-Definition we want to map with text.
	 * @param readText
	 * 		true if we want the program to create the definition.<br/>
	 * 		false if we want the program just read the definition.
	 * @modified 2009.11.02
	 * @since 2008.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_XDefinitionToText_Eng(String xDef, boolean readText)
	{

		if (xDef.startsWith("<DEFINITION_PART "))
			txtDefinition=mapXDefinitionPart(xDef, readText);
		else if (xDef.startsWith("<DEFINITION_PART_START"))
			txtDefinition=mapXDefinitionPartStart();
		else if (xDef.startsWith("<DEFINITION_PART_END"))
			txtDefinition="";

		else if (xDef.startsWith("<DEFINITION_WHOLE "))
			txtDefinition="";
		else if (xDef.startsWith("<DEFINITION_WHOLE_START"))
			txtDefinition=mapXDefinitionWholeStart();
		else if (xDef.startsWith("<DEFINITION_WHOLE_END"))
			txtDefinition="";

		else if (xDef.startsWith("<DEFINITION_SPECIFIC "))
			txtDefinition="";
		else if (xDef.startsWith("<DEFINITION_SPECIFIC_START"))
			txtDefinition=mapXDefinitionSpecificStart();//=GenEnd
		else if (xDef.startsWith("<DEFINITION_SPECIFIC_END"))
			txtDefinition="";

		else if (xDef.startsWith("<DEFINITION_GENERIC "))
			txtDefinition=mapXDefinitionGeneric(xDef, readText);
		else if (xDef.startsWith("<DEFINITION_GENERIC_START"))
			txtDefinition=mapXDefinitionGenericStart();//=SpeEnd
		else if (xDef.startsWith("<DEFINITION_GENERIC_END"))
			txtDefinition=mapXDefinitionGenericEnd();//=SpeStart

		else if (xDef.startsWith("<DEFINITION_ATTRIBUTE "))
			txtDefinition="";
		else if (xDef.startsWith("<DEFINITION_ATTRIBUTE_START"))
			txtDefinition=mapXDefinitionAttributeStart();//=SpeEnd
		else if (xDef.startsWith("<DEFINITION_ATTRIBUTE_END"))
			txtDefinition="";//=SpeStart

		else if (xDef.startsWith("<DEFINITION_RELATOR"))
			txtDefinition=mapXDefinitionRelator(xDef, readText);

		else
			txtDefinition="unknown definition";
	}


	/**
	 *
	 * @modified 2009.11.13
	 * @since 2009.11.13 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public Maper_XDefinitionToText_Eng(String xDef)
	{
		this(xDef, false);
	}


	/**
	 * Maps a generic-xdefinition.
	 *
	 * @param xDef	The xml-Definition we want to map to text.
	 * @param readText
	 * 		false if we want the program to create the definition.<br/>
	 * 		true if we want the program just read the definition.
	 * @modified 2009.11.02
	 * @since 2009.11.02 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionGeneric(String xDef, boolean readText)
	{
		/*
		<DEFINITION_GENERIC CREATION="y" CREATED="2009.11.02" AUTHOR="HoKoNoUmo">
		<EngMainName TxEXP="Entity" TRMrULE="rlEngTrmNnCs22"/>
		<RELATION FRnAME="refino_generic">
		<XCPT FRnAME="Entity's-attribute-relation@hknu.symb-20@"/>
		<XCPT FRnAME="Entity's-non-attribute-relation@hknu.symb-19@"/>
		</RELATION>
		<Description_eng CREATED="2009.11.02" AUTHOR="HoKoNoUmo">
		RELATION is any entity's-attribute or entity's-nonattribute relation.
		</Description_eng>
		</DEFINITION_GENERIC>
		*/
		String txt="";
		Vector<String> voln = Util.getVectorOfLinesOfString(xDef);
		String ln="";
		int i=0;

		if (readText){
			//read the english text
			for (i = 0; i < voln.size(); i++) {
				ln=voln.get(i);
				if (ln.startsWith("<Description_eng")){
					i=i+1;
					ln=voln.get(i);
					while (!ln.startsWith("</Description_eng")){
						txt=txt+ln;
						i=i+1;
						ln=voln.get(i);
					}
					break;
				}
			}
		}
		else {
			//create the text
			//txt="name" is any "xcpt1" or "xcpt2".

			//1. "name"
			String name="";
			for (String s : voln){
				if (s.startsWith("<EngMainName")){
					name=Util.getXmlAttribute(s,"TxEXP");
					break;
				}
			}
			txt=name;

			//2. is
			txt = txt +" is any of ";

			//3. arguments
			for (i = 0; i < voln.size(); i++) {
				ln=voln.get(i);
				if (ln.startsWith("<RELATION FRnAME=\"refino_generic")){
					i=i+1;
					ln=voln.get(i);
					//always there are arguments
					txt= txt +Util.getNameOfConceptInXElement(ln,"eng");
					i= i+1;
					ln=voln.get(i);
					while (!ln.startsWith("</RELATION")){
						txt= txt +" or " +Util.getNameOfConceptInXElement(ln,"eng");
						i=i+1;
						ln=voln.get(i);
					}
					break;
				}
			}

			txt=txt +".";
		}

		return txt;
	}

	/**
	 * Returns the string that this is an ending-definition.
	 *
	 * @modified 2009.04.03
	 * @since 2009.04.03 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionGenericEnd()
	{
		String txt="This is a GENERIC-END b-concept in current sub-worldview.";
		return txt;
	}


	/**
	 * Returns the string that current-concept is a start-concept.
	 * It is NOT generic of other concepts (it is specific-end)
	 * and it has generic.
	 *
	 * @modified 2009.08.24
	 * @since 2009.02.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionGenericStart()
	{
		String txt="This is a GENERIC-START b-concept, in current sub-worldview.";
		return txt;
	}


	/**
	 * Maps a part-definition.
	 *
	 * @param xDef
	 * 		The xml-Definition we want to map with text.
	 * @param readText
	 * 		true if we want the program to create the definition.<br/>
	 * 		false if we want the program just read the definition.
	 * @modified 2009.08.14
	 * @since 2008.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionPart(String xDef, boolean readText)
	{
		/*
		<DEFINITION_PART LNG="eng" CREATED="2008.04.18" AUTHOR="HoKoNoUmo">
		<Text_eng LASTmOD="2004.03.23" CREATED="2001.09.30" AUTHOR="HoKoNoUmo">
		Concept is every tree network node of a b-conceptual--sub-worldview.
		</Text_eng>
		<Name_NounCase LNG="eng" TxEXP="Concept" TRMrULE="rlEngTrmNnCs11"/>
		<SOURCE LABEL="b1" FRnAME="SubWorldview_conceptual@hknu.symb-6@"/>
		<PRODUCT
		<XCPT LABEL="b2" FRnAME="Tree-network's-node@hknu.symb-11@"/>
		<XCPT LABEL="b3" FRnAME="Number_every@hknu.symb-22#1@"/>
		<XCPT LABEL="b4" FRnAME="Quantiteino@hknu.symb-23.xml" ARGS="b1,b2"/>
		</PRODUCT>
		</DEFINITION_PART>
		*/
		String txt="";
		Vector<String> voln = Util.getVectorOfLinesOfString(xDef);
		String ln="";
		int i=0;

		readText=false;//llll

		if (readText){
			//read the english text
			for (i = 0; i < voln.size(); i++) {
				ln=voln.get(i);
				if (ln.startsWith("<Text_eng")){
					i=i+1;
					ln=voln.get(i);
					while (!ln.startsWith("</Text_eng")){
						txt=txt+ln;
						i=i+1;
						ln=voln.get(i);
					}
					break;
				}
			}
		}
		else {
			//create the text
			//txt="name" is "product" of a "source".

			//1. "name"
			String name="";
			for (String s : voln){
				if (s.startsWith("<Name_NounCase LNG=\"eng\"")){
					name=Util.getXmlAttribute(s,"TxEXP");
					break;
				}
			}
			if (name.equals("")){
				name=Util.getXCpt_sName(AAj.xcpt_sFormalID, "eng");
				name=Textual.setFirstCapital(name);
			}
			txt=name;


			//2. is
			txt = txt +" is ";

			//3. "product"
			Vector<String> prdct= new Vector<String>();
			for (i = 0; i < voln.size(); i++) {
				ln=voln.get(i);
				if (ln.startsWith("<PRODUCT")){
					prdct.add(ln);
					if (ln.endsWith("/>")){
						break;
					} else {
						i=i+1;
						ln=voln.get(i);
						while (!ln.startsWith("</PRODUCT")){
							prdct.add(ln);
							i=i+1;
							ln=voln.get(i);
						}
						prdct.add(ln); //</PRODUCT
						break;
					}
				}
			}
			Maper_XElementToText_Eng mse = new Maper_XElementToText_Eng(prdct);
			String to = mse.textOut;
			txt = txt +to;

			//4. of
			txt = txt +" of a ";

			//5. "source"
			String src="";
			for (i = 0; i < voln.size(); i++) {
				ln=voln.get(i);
				if (ln.startsWith("<SOURCE")){
					src=ln;
					break;
				}
			}
			src=mse.getNameOfConceptInXElement(src);
			txt=txt +src+".";
		}


		return txt;
	}


	/**
	 * Returns the string that this is a start-concept. It is NOT
	 * part of other concepts and it has parts.
	 *
	 * @modified 2009.02.19
	 * @since 2009.02.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionPartStart()
	{
		String txt="This is a PART-START b-concept, in current sub-worldview.";
		return txt;
	}


	/**
	 * Returns the string that this is a start-concept. It is NOT
	 * specific of other concepts.
	 *
	 * @modified 2009.08.24
	 * @since 2009.02.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionSpecificStart()
	{
		String txt="This is a SPECIFIC-START b-concept, in current sub-worldview.";
		return txt;
	}


	/**
	 * Returns the string that current-concept is a start-concept.
	 * It is NOT whole-concept for other concepts (it is part-end)
	 * and it has whole.
	 *
	 * @modified 2009.02.19
	 * @since 2009.02.19 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionWholeStart()
	{
		String txt="This is a WHOLE-START b-concept, in current sub-worldview.";
		return txt;
	}


	/**
	 * Returns the string that current-concept is a start-concept.
	 *
	 * @modified 2009.09.29
	 * @since 2009.09.29 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionAttributeStart()
	{
		String txt="This is an ATTRIBUTE-START b-concept, in current sub-worldview.";
		return txt;
	}


	/**
	 * Returns the text mapped to this xdefinition.
	 *
	 * @modified 2009.11.16
	 * @since 2009.11.16 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public String mapXDefinitionRelator(String xDef, boolean readText)
	{
		String txt="";
		Vector<String> voln = Util.getVectorOfLinesOfString(xDef);
		String ln="";
		int i=0;

		if (readText)
			txt= getDescriptionOfXDefinition(xDef);
		else {
			//create the text
			//The presense or not of any COMMONNESS between the entity and any other-enity.
			//txt="name" I call "refino".

			//1. "name"
			txt= getNameOfXDefinition(voln);

			//2. I call
			txt = txt +" I call ";

			//3. "refino"
			Vector<String> rfn= new Vector<String>();
			for (i= 0; i< voln.size(); i++) {
				ln=voln.get(i);
				if (ln.startsWith("<REFINO ")){
					rfn.add(ln);
					i= i+1;
					while (i< voln.size()){
						ln= voln.get(i);
						rfn.add(ln);
						i= i+1;
					}
				}
			}
			rfn= Util.extractFirstRefino(rfn);
			Maper_XElementToText_Eng mxt = new Maper_XElementToText_Eng(rfn);
			txt = txt +mxt.textOut;

		}

		return txt;
	}

	/**
	 * Returns the english-description of an xDefinition.
	 *
	 * @modified 2009.11.16
	 * @since 2009.11.12 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public String getDescriptionOfXDefinition(String xDef)
	{
		String txt="";
		Vector<String> voln = Util.getVectorOfLinesOfString(xDef);
		String ln="";
		int i=0;
		for (i = 0; i < voln.size(); i++) {
			ln=voln.get(i);
			if (ln.startsWith("<Description_eng")){
				i=i+1;
				ln=voln.get(i);
				while (!ln.startsWith("</Description_eng")){
					txt=txt+ln;
					i=i+1;
					ln=voln.get(i);
				}
				break;
			}
		}
		return txt;
	}


	/**
	 * Returns the name of the concept defined in an xdefinition
	 * given as a vector-of-lines.
	 *
	 * @modified 2009.11.16
	 * @since 2009.11.16 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public String getNameOfXDefinition(Vector<String> vectorOfLines)
	{
		String name="";
		for (String s : vectorOfLines){
			if (s.startsWith("<EngMainName")){
				name=Util.getXmlAttribute(s,"TxEXP");
				break;
			}
		}
		name=name.replace(' ','-');
		return name;
	}
}
