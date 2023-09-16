/*
 * Maper_XDefinitionToText.java - Generates the Text of a Sensorial-Definition.
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

/**
 * <b>INPUT</b>: an xml-Definition, and a language,
 * <b>OUTPUT</b>: the Text of the definition in the specified-language.<p>
 *
 * <b>CODE</b>: <code><br/>
 * Maper_XDefinitionToText mp = new Maper_XDefinitionToText(xDef, lang);
 * output = mp.txtDefinition;</code>
 *
 * @modified 2004.07.18
 * @since 2008.10.17 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_XDefinitionToText
{

	/** The input xml-definition */
	public String xmlDefinition;
	public String lang;
	/** The output text-definition */
	public String txtDefinition;


	/**
	 * @param xDef
	 * 		The xml-Definition we want to map with text.
	 * @param lg
	 * 		The language we want to present the generated-text.
	 * @param readTxt
	 * 		true if we want the program to read the definition.<br/>
	 * 		false if we want the program to create the definition.
	 * @modified 2009.10.22
	 * @since 2008.10.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_XDefinitionToText(String xDef, String lg, boolean readTxt)
	{
		xmlDefinition = xDef;
		lang = lg;

		Util.log(null);
		Util.log("\tSTART-MAPPING: xmlDefinition to Text("+lang+"):");

		if (lang.equals("eng")){
			Maper_XDefinitionToText_Eng engMaper =
				new Maper_XDefinitionToText_Eng(xmlDefinition, readTxt);
			txtDefinition=engMaper.txtDefinition;
		} else if (lang.equals("eln")){
//			Maper_XDefinitionToTextEln elnMaper = new Maper_XDefinitionToTextEln(xmlDefinition);
//			text=elnMaper.text;
		}
		//addLng

	}


	/**
	 *
	 * @modified 2009.11.13
	 * @since 2009.11.13 (v00.02.01)
	 * @author HoKoNoUmo
	 */
	public Maper_XDefinitionToText(String xDef, String lg)
	{
		this(xDef, lg, false);
	}
}
