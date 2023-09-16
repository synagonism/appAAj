/*
 * Maper_SSemasiaToSSemasia.java - 	It takes a jSSemasia and maps it to
 * another jSSemasia in another language.
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
package pk_SSemasia;

import java.io.IOException;
import java.io.Writer;
import pk_Util.Util;

/**
 * <b>INPUT</b>: a jSSemasia, and a language,
 * <b>OUTPUT</b>: a new jSSemasia in the specified-language.<p>

 * <b>CODE</b>: <CODE><br/>
 * Maper_SSemasiaToSSemasia mmapor = new Maper_SSemasiaToSSemasia(jSSemasia, lang);<br/>
 * SSmNode newSSemasia = mmapor.jSSmOut; </CODE>
 *
 * @modified
 * @since 2004.11.20 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_SSemasiaToSSemasia
{

	/** The jSSemasia we want to map to another-jSSemasia */
	public SSmNode jSsmaIn;
	public String lang;
	public Writer writer;
	/** The output jSSemasia */
	public SSmNode jSSmOut;


	/**
	 * @param jSSm
	 * 		The input SSemasia we want to map.
	 * @param lg
	 * 		The language we want to present the generated-model.
	 * @param wr
	 * 		The writer in which the program will write the char-semasial.
	 * @modified
	 * @since 2004.11.20 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_SSemasiaToSSemasia(SSmNode jSSm, String lg, Writer wr)
	{
		jSsmaIn = jSSm;
		writer = wr;
		lang = lg;

		Util.log(null);
		Util.log("\tSTART-MAPPING: jSSemasia to jSSemasia ("+lg+")");

		if (lang.equals("eng")){
			Maper_SSemasiaToSSemasia_Eng enm = new Maper_SSemasiaToSSemasia_Eng(jSsmaIn);
			jSSmOut=enm.mapSSemasia();
		} else if (lang.equals("eln")){
			Maper_SSemasiaToSSemasia_Eln elm = new Maper_SSemasiaToSSemasia_Eln(jSsmaIn);
			jSSmOut=elm.mapSSemasia();
		}
		//addLng

		if (writer!=null) {
			try {
				jSSmOut.mapToCharNode(writer);
			} catch (IOException e) {
				System.out.println("Maper_SSemasiaToSSemasia");
			}
		}
	}


	/**
	 * @param jSSm
	 * 		The input SSemasia we want to map.
	 * @param lg
	 * 		The language we want to present the generated-model.
	 * @modified 2005.09.20
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_SSemasiaToSSemasia(SSmNode jSSm, String lg)
	{
		this(jSSm, lg, null);
	}

}
