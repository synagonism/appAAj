/*
 * Maper_SSemasiaToText.java - Generates the Text of a SSemasia.
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
package pk_SSemasia;

import java.io.IOException;
import java.io.Writer;
import pk_Util.Util;
import pk_Logo.Exception_Logo_GeneratingText;

/**
 * <b>INPUT</b>: a jSSemasia, a writer and a language,
 * <b>OUTPUT</b>: the Text of the ssemasia in the specified-language.<p>
 *
 * <b>CODE</b>: <code><br/>
 * Maper_SSemasiaToText jSSm = new Maper_SSemasiaToText(jSSemasia, lang, writer);</code>
 *
 * @modified 2004.07.18
 * @since 2001.02.17 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_SSemasiaToText
{

	/** The input ssemasia */
	public SSmNode jSSemasia;
	public Writer writer;
	public String lang;
	/** The output mtext */
	public String text;


	/**
	 * @param jSSm
	 * 		The java-SSemasia we want to map.
	 * @param wr
	 * 		The writer in which the program will write the logal.
	 * @param lg
	 * 		The language we want to present the generated-logal.
	 * @modified 2005.09.20
	 * @since 2001.02.17 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_SSemasiaToText(SSmNode jSSm, String lg, Writer wr)
	{
		jSSemasia = jSSm;
		writer = wr;
		lang = lg;

		Util.log(null);
		Util.log("\tSTART-MAPPING: jSSemasia to Text("+lang+"):");

		if (lang.equals("eng")){
			Maper_SSemasiaToText_Eng eng = new Maper_SSemasiaToText_Eng(jSSemasia);
			text=eng.mapSSemasia();
		} else if (lang.equals("eln")){
			Maper_SSemasiaToText_Eln elm = new Maper_SSemasiaToText_Eln(jSSemasia);
			text=elm.mapSSemasia();
		}
		//addLng

		if (writer!=null) {
			try {
				writer.write(text);
				writer.close();
			} catch (IOException e) {
				System.out.println("Maper_SSemasiaToText.");
			}
		}
	}


	/**
	 * @param jSSm
	 * 		The SSemasia we want to map.
	 * @param lg
	 * 		The language we want to present the generated-text.
	 * @modified 2005.09.20
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_SSemasiaToText(SSmNode jSSm, String lg)
	{
		this(jSSm, lg, null);
	}


}
