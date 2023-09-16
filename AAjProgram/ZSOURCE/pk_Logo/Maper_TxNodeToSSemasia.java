/*
 * Maper_TxNodeToSSemasia.java - A maper of Text-To-SSemasia.
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

import java.io.IOException;
import java.io.Writer;
import pk_Util.Util;
import pk_SSemasia.*;

/**
 * <b>INPUT</b>: a java-Text, its language, and a writer,
 * <b>OUTPUT</b>: the jSSemasia of the text (subWorldview) in the specified-language.<p>
 *
 * CODE:<code><br/>
 * Maper_TxNodeToSSemasia tm = new Maper_TxNodeToSSemasia(txt, lang, writer);</code>
 *
 * @modified 2004.11.16
 * @since 2004.10.16 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Maper_TxNodeToSSemasia
{

	public TxNode jVdtr; //the input java-Text
	public String lang;
	public Writer writer;
	public SSmNode jSSm; 		//the output jSSemasia


	/**
	 * @param jtm
	 * 		The java-Text we want to map.
	 * @param lg
	 * 		The language we want to present the generated-semasial.
	 * @param wr
	 * 		The writer in which the program will write the char-semasial.
	 * @modified 2004.11.16
	 * @since 2004.10.16 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_TxNodeToSSemasia(TxNode jtm, String lg, Writer wr)
	{
		jVdtr = jtm;
		writer = wr;
		lang = lg;

		Util.log(null);
		Util.log("\tSTART-MAPPING: java-TEXT to jSSemasia:");

		//we can include the write method here. 2004.10.17
		if (lang.equals("eng")){
			Maper_TxNodeToSSemasia_Eng entm = new Maper_TxNodeToSSemasia_Eng(jVdtr);
			jSSm=entm.mapJText();
		} else if (lang.equals("eln")){
			//Maper_TxNodeToSSemasiaEln elg=new Maper_TxNodeToSSemasiaEln();
			//jSSm=elg.generateSSMA(jtm);
		}

		try {
			jSSm.mapToCharNode(writer);
		} catch (IOException e) {
			System.out.println("Maper_TxNodeToSSemasia");
		}
	}


	/**
	 * @param jtm
	 * 		The java-Text we want to map.
	 * @param lg
	 * 		The language we want to present the generated-semasial.
	 * @modified
	 * @since 2004.11.21 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Maper_TxNodeToSSemasia(TxNode jtm, String lg)
	{
		jVdtr = jtm;
		lang = lg;

		Util.log(null);
		Util.log("\tSTART-MAPPING: java-TEXT to jSSemasia:");

		//we can include the write method here. 2004.10.17
		if (lang.equals("eng")){
			Maper_TxNodeToSSemasia_Eng entm = new Maper_TxNodeToSSemasia_Eng(jVdtr);
			jSSm=entm.mapJText();
		} else if (lang.equals("eln")){
			//Maper_TxNodeToSSemasiaEl elg=new Maper_TxNodeToSSemasiaEl();
			//jSSm=elg.generateSSMA(jtm);
		}
	}


}
