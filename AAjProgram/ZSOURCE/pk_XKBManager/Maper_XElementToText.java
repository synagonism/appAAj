/*
 * Maper_XElementToText.java - Generates the Text of a Sensorial-Definition.
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
import java.io.IOException;
import java.util.Vector;

/**
 * <b>INPUT</b>: an xml-element of a sensorial-b-concept, and a language,
 * <b>OUTPUT</b>: the Text of this element in the specified-language.<p>
 *
 * <b>CODE</b>:<br/><code>
 * Maper_XElementToText mp = new Maper_XElementToText(xElm, lang);
 * output = mp.textOut;</code>
 *
 * @modified
 * @since 2009.01.15 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_XElementToText
{

	/** The input xml-element. */
	public Vector<String> xmlElement;
	/** The input language, in which we want the output text. */
	public String lang;
	/** The output text. */
	public String textOut;


	/**
	 * @param xElm
	 * 		The xml-element we want to map with text.
	 * @param lg
	 * 		The language we want to present the generated-text.
	 * @modified
	 * @since 2009.01.15 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_XElementToText(Vector<String> xElm, String lg)
	{
		xmlElement = xElm;
		lang = lg;

		Util.log(null);
		Util.log("\tSTART-MAPPING: xmlElement to Text("+lang+"):");

		if (lang.equals("eng")){
//			Maper_XElementToText_Eng engMaper =
//				new Maper_XElementToText_Eng(xmlElement);
//			textOut=engMaper.textOut;
		} else if (lang.equals("eln")){
//			Maper_XElementToTextEln elnMaper = new Maper_XElementToTextEln(xmlElement);
//			text=elnMaper.text;
		}
		//addLng

	}


	/**
	 * The compiler demanded it.
	 *
	 * @modified 2009.11.17
	 * @since 2009.11.17 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public Maper_XElementToText()
	{
	}

	/**
	 * Returns the name of a concept the formal-name of which
	 * the xElement contains.
	 * The name is the first logal-concept's name.
	 *
	 * @modified 2009.11.18
	 * @since 2009.01.15 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public String getNameOfConceptInXElement(String xElm)
	{
		String cpt=Util.getXmlAttribute(xElm, "NEWxCPT");
		if (cpt.equals("none"))
			cpt=Util.getXmlAttribute(xElm, "FRnAME");
		if (cpt.equals("none"))
			System.out.println("PROBLEM: Maper_XElementToText.getNameOfConceptInXelement: " +xElm);
		cpt= Util.getXCpt_sName(cpt,"eng");
		cpt= cpt.replace(' ','-');
		return cpt;
	}
}
