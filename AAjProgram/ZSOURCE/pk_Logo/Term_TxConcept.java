/**
 * Term_TxConcept.java - Contains the functionality of txCpt's-terms.
 * :font:monospaced16 :tab:2 :indent:2 :wrap:72
 *
 * Copyright (C) 2001 Nikos Kasselouris
 * nikkas@otenet.gr
 * users.otenet.gr/~nikkas/
 *
 * This program is free software; you can redistribute it and/or
 * modified it under the terms of the GNU Generic Public License
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

import java.util.Locale;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Contains the functionality of <a href="file:///G:/FILE1/AAjWORKING/AAjSite/eng/sbcpt/lango_ho.html#foEtoKoncosTermo">
 * tx-concept's-terms</a>.<p>
 *
 * TxConceptTerms and tx_auxiliaries create
 * <b>instance--tx-concepts</b>.
 * Any Text is comprised of instance--tx-concepts.
 *
 * @modified 2009.08.17
 * @since 2001.03.11 (v00.02.00)
 * @author HoKoNoUmo
 */
public class Term_TxConcept
{
	/**
	 * The types of a lg-concept's--term are:
	 * term_TxNoun/termTxNoun_Case/termTxNoun_Adjective/termTxNoun_Adverb/
	 * term_TxConjunction/term_TxVerb.
	 */
	public String termTxCpt_sType;

	/** The name of a tx-cpt is its MAIN term. */
	public String termTxCpt_sName="";

	/** all terms of a lg-concept */
	public String termTxCpt_sAll="";

	public String stem="",stem2="",stem3="";

	/** The RULE a language uses to create its tx-cpt's-terms: rlElnTrmNnCs1, rlElnTrmNnCs2,...*/
	public String termTxCpt_sRule;

	/** The language to which the syn belongs eg: eln,eng. */
	public String lang;

	/** to present how we found its tx-concept's-terms. */
	public String note="";


	/**
	 * @modified
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public Term_TxConcept(String nam, String tp, String lg)
	{
		termTxCpt_sName=nam;
		termTxCpt_sType=tp;
		lang=lg;
	}


	/**
	 * @modified 2004.09.08
	 * @since 2004.09.08 (v00.02.00)
	 * @author HoKoNoUmo
	 */
	public Term_TxConcept(String tp, String lg)
	{
		termTxCpt_sType=tp;
		lang=lg;
	}



	/**
	 * Returns the first letters from a word
	 *
	 * @param word		The word we want its first-letters (stem).
	 * @param prefix	The number of first letters we want.
	 * @modified
	 * @since 2001.03.18
	 * @author HoKoNoUmo
	 */
	public String getFirstLettersIfPrefix(String word, int prefix)
	{
		return word.substring(0, prefix);
	}


	/**
	 * Returns the stem (word-suffix).
	 * @param word		The word we want its first-letters (stem).
	 * @param suffix	The number of letters of suffix of this word.
	 * @modified 2001.03.18
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public String getFirstLettersIfSuffix(String word, int suffix)
	{
		return word.substring(0, word.length()-suffix);
	}


	/**
	 * From a string extracts the last characters we want.
	 *
	 * @param word		The word from which we want the last letters.
	 * @param prefix	The number of first characters we DON'T want.
	 * @modified
	 * @since 2001.03.18
	 * @author HoKoNoUmo
	 */
	public String getLastLettersIfPrefix(String word, int prefix)
	{
		return word.substring(prefix, word.length());
	}


	/**
	 * From a string extracts the last characters we want.
	 *
	 * @param suffix	The number of characters to extract.
	 * @modified 2001.03.18
	 * @since 2001.03.14
	 * @author HoKoNoUmo
	 */
	public String getLastLettersIfSuffix(String str, int suffix)
	{
		return str.substring(str.length()-suffix, str.length());
	}


	/**
	 * Eg: απλώνω >> άπλωνω!
	 * @modified 2001.03.21
	 * @since 2001.03.17
	 * @author HoKoNoUmo
	 */
	public String greekTonosDecrease(String word)
	{
		word=word.toLowerCase(new Locale("el","GR"));
		int indexTonos=greekTonosFindIndex(word);
		word=greekTonosRemove(word);

		String word2="";
		try { word2=word.substring(0,indexTonos);}
		catch (StringIndexOutOfBoundsException  sioofe){System.out.println("ERROR LgConcept.greekTonosDecrease");}
		int maxVowel=findIndexOfLastGreekVowel(word2);
		word=greekTonosSetOnIndex(word,maxVowel);

		return word;
	}


	/**
	 * Find the syllable with tonos.<p>
	 * Returns <ul><li>
	 *   1 for ligousa<li>
	 *   2 for paraligousa<li>
	 *   3 for proparaligousa<li>
	 *   -1 for a mistake</ul>
	 * @modified
	 * @since 2001.03.21
	 * @author HoKoNoUmo
	 */
	public int greekTonosFind(String word)
	{
		word=word.toLowerCase(new Locale("el","GR"));
		int tonos=greekTonosFindIndex(word);
		int ligousa=-1;
		int paraligousa=-1;
		int proparaligousa=-1;

		word=greekTonosRemove(word);
		ligousa=findIndexOfLastGreekVowel(word);
		if (ligousa!=-1)
			paraligousa=findIndexOfLastGreekVowel( word.substring(0,ligousa) );
		if (paraligousa!=-1)
			proparaligousa=findIndexOfLastGreekVowel( word.substring(0,paraligousa) );

		if (tonos==ligousa)
			return 1;
		else if (tonos==paraligousa)
			return 2;
		else if (tonos==proparaligousa)
			return 3;
		else
			return -1; //in case of a mistake.
	}//greekTonosFind.


	/**
	 * Reeturns the index of tonos of a greek-word.<p>
	 * <b>Precoditions</b>:<br/>
	 *	  ** Τα ΔΙΨΗΦΑ ΦΩΝΗΕΝΤΑ:<br/>
	 *  - εί,οί,αί,ού	θεωρούνται μία συλλαβή.<br/>
	 *  ** Οι συνδυασμοί αύ,εύ, θεωρούνται μία συλλαβή.<br/>
	 *  ** Οι παρακάτω Καταχρηστικοί-δίφθογγοι κάνουν μία συλλαβή:<br/>
	 *  - ια   πιά-νω	| πιές<br/>
	 *  - υα   γυα-λί<br/>
	 *  - εια  θειά-φι/βοή-θεια<br/>
	 *  - οια  ποιά		| ποιές	|	ποιοί	| ποιούς<br/>
	 *  ** Οι ΔΙΦΘΟΓΓΟΙ κάνουν μία συλλαβή:<br/>
	 *	  - αι  νε-ράι-δα<br/>
	 *	  - αη  αη-δό-νι<br/>
	 *	  - οι  ρόι-δι<br/>
	 *	  - οη  βόη-θα<br/>
	 * @modified 2001.04.08
	 * @since 2001.03.21
	 * @author HoKoNoUmo
	 */
	public int greekTonosFindIndex(String word)
	{
		word=word.toLowerCase(new Locale("el","GR"));
		int indexTonos=-1;

		if (word.indexOf("ά")!=-1)
		{
			if (word.indexOf("ά")!=0)
			{
				//δεν υπάρχει άλλος τρόπος να καταλάβει πότε ΠΡΟΦΕΡΕΤΑΙ μαζί. 2001.04.08
				//ΑΝ το άκουγε, ΤΟΤΕ θα το καταλάβαινε!!
				//ψηφιακό, δημόσια, ακρίβεια, σπάνια, τεράστια, τεράστιες,
				//για, έπιασα, χρόνια, μάτια, τεράστιες,
				if (	word.charAt(word.indexOf("ά")-1)=='j' ) //καταχρηστικός δίφθογγος.
					indexTonos=word.indexOf("ά")-1;
				else
					indexTonos=word.indexOf("ά");
			}
			else
				indexTonos=word.indexOf("ά");
		}
		else if (word.indexOf("έ")!=-1)
		{
			if (word.indexOf("έ")!=0)
			{
				if (	word.charAt(word.indexOf("έ")-1)=='j' ) //καταχρηστικός δίφθογγος.
					indexTonos=word.indexOf("έ")-1;
				else
					indexTonos=word.indexOf("έ");
			}
			else
				indexTonos=word.indexOf("έ");
		}
		else if (word.indexOf("ή")!=-1)
		{
			indexTonos=word.indexOf("ή");
		}
		else if (word.indexOf("\u03af")!=-1)//ί
		{
			if (word.indexOf("\u03af")!=0)
			{
				if (	word.charAt(word.indexOf("\u03af")-1)=='ε'		//ει
						||word.charAt(word.indexOf("\u03af")-1)=='ο' 	//οι
						||word.charAt(word.indexOf("\u03af")-1)=='α' )	//αι
					indexTonos=word.indexOf("\u03af")-1;
				else
					indexTonos=word.indexOf("\u03af");
			}
			else
				indexTonos=word.indexOf("\u03af");
		}
		else if (word.indexOf("ό")!=-1)
		{
			indexTonos=word.indexOf("ό");
		}
		else if (word.indexOf("ύ")!=-1)
		{
			if (word.indexOf("ύ")!=0)
			{
				//if previous letter o, decrease tonos.
				if (	word.charAt(word.indexOf("ύ")-1)=='ο' 	//ού
						||word.charAt(word.indexOf("ύ")-1)=='α' 	//αύ
						||word.charAt(word.indexOf("ύ")-1)=='ε' )	//εύ, απαγορεύεται
					indexTonos=word.indexOf("ύ")-1;
				else
					indexTonos=word.indexOf("ύ");
			}
			else
				indexTonos=word.indexOf("ύ");
		}
		else if (word.indexOf("\u03ce")!=-1) //ώ
		{
			indexTonos=word.indexOf("\u03ce");
		}
		else if (word.indexOf("ΰ")!=-1)
		{
			indexTonos=word.indexOf("ΰ");
		}
		else if (word.indexOf("ΐ")!=-1)
		{
			indexTonos=word.indexOf("ΐ");
		}

		return indexTonos;
	}//greekTonosFindIndex.


	/**
	 * Παράδειγμα: άνθρωπος ==> ανθρώπος
	 * @modified 2001.05.16
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public String greekTonosIncrease(String word)
	{
		word=word.toLowerCase(new Locale("el","GR"));
		int indexTonos=greekTonosFindIndex(word);
		word=greekTonosRemove(word);
		//find the min vowel AFTER tonos.
		String word2;
		int minVowel;
		//σε εί/ού... δίνει σαν τόνο το προηγούμενο φωνήεν.
		if (word.charAt(indexTonos+1)=='ι'||word.charAt(indexTonos+1)=='υ')//word has no tonos
		{
			word2=getLastLettersIfPrefix(word,indexTonos+2);
			minVowel=indexTonos+2+findIndexOfFirstGreekVowel(word2);
		}
		else {
			word2=getLastLettersIfPrefix(word,indexTonos+1);
			minVowel=indexTonos+1+findIndexOfFirstGreekVowel(word2);
		}
		return greekTonosSetOnIndex(word,minVowel);
	}


	/**
	 * Removes tonos of a word.
	 * @modified 2001.03.21
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public String greekTonosRemove(String word)
	{
		if (word.indexOf("ά")!=-1)
			return word.replace('ά','α');
		else if (word.indexOf("έ")!=-1)
			return word.replace('έ','ε');
		else if (word.indexOf("ή")!=-1)
			return word.replace('ή','η');
		else if (word.indexOf("\u03af")!=-1)
			return word.replace('\u03af','ι');
		else if (word.indexOf("ό")!=-1)
			return word.replace('ό','ο');
		else if (word.indexOf("ύ")!=-1)
			return word.replace('ύ','υ');
		else if (word.indexOf("ώ")!=-1)
			return word.replace('ώ','ω');
		else if (word.indexOf("ΰ")!=-1)
			return word.replace('ΰ','ϋ');
		else if (word.indexOf("ΐ")!=-1)
			return word.replace('ΐ','ϊ');
		else
			return word;
	}//greekTonosRemove.


	/**
	 * Removes the first tonos of a word with two tonos.<p>
	 * We need this method in cases where when we create a syn-termTxCpt_sName,
	 * the word has 2 tonos the old (from stem) and the new (from suffix).
	 * @modified
	 * @since 2001.03.11
	 * @author HoKoNoUmo
	 */
	public String greekTonosRemoveFirst(String word)
	{
		int indexFirst;
		int indexOne=-1;
		int indexTwo=-1;

		word=word.toLowerCase(new Locale("el","GR"));

		//find first tonos-index
		if (word.indexOf("ά")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("ά"); else indexTwo=word.indexOf("ά");
		else if (word.indexOf("έ")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("έ"); else indexTwo=word.indexOf("έ");
		else if (word.indexOf("ή")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("ή"); else indexTwo=word.indexOf("ή");
		else if (word.indexOf("\u03af")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("\u03af"); else indexTwo=word.indexOf("\u03af");
		else if (word.indexOf("ό")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("ό"); else indexTwo=word.indexOf("ό");
		else if (word.indexOf("ύ")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("ύ"); else indexTwo=word.indexOf("ύ");
		else if (word.indexOf("ώ")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("ώ"); else indexTwo=word.indexOf("ώ");
		else if (word.indexOf("ΰ")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("ΰ"); else indexTwo=word.indexOf("ΰ");
		else if (word.indexOf("ΐ")!=-1)
			if (indexOne!=-1) indexOne=word.indexOf("ΐ"); else indexTwo=word.indexOf("ΐ");
		if (indexOne<indexTwo)
			indexFirst=indexOne;
		else
			indexFirst=indexTwo;

		//find the letter of first tonos.
		char c=word.charAt(indexFirst);

		if (c=='ά')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"α"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else if (c=='έ')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"ε"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else if (c=='ή')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"η"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else if (c=='\u03af')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"ι"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else if (c=='ό')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"ο"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else if (c=='ύ')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"υ"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else if (c=='ώ')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"ω"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else if (c=='ΰ')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"υ"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else if (c=='ΐ')
			return 	word.substring(0,word.indexOf(indexFirst))
							+"ι"
							+word.substring(word.indexOf(indexFirst)+1,word.length());
		else
			return word;
	}//greekTonosRemoveFirst.


	/**
	 * Sets the tonos on a word's ligousa.<p>
	 * <b>GIVEN</b>: the word has no tonos.
	 * @modified 2001.03.21
	 * @since 2001.03.19
	 * @author HoKoNoUmo
	 */
	public String greekTonosSetOnFirst(String word)
	{
		word=word.toLowerCase(new Locale("el","GR"));
		//find the MAX index of vowels.
		int maxVowel=findIndexOfLastGreekVowel(word);

		return greekTonosSetOnIndex(word, maxVowel);
	}//greekTonosSetOnFirst.


	/**
	 * Sets the tonos on a word's paraligousa.<p>
	 * <b>GIVEN</b>: the word has no tonos.
	 * @modified
	 * @since 2001.05.24
	 * @author HoKoNoUmo
	 */
	public String greekTonosSetOnSecond(String word)
	{
		word=word.toLowerCase(new Locale("el","GR"));
		int maxVowel=findIndexOfLastGreekVowel(word);
		String word2=word.substring(0,maxVowel);
		maxVowel=findIndexOfLastGreekVowel(word2);
		return greekTonosSetOnIndex(word, maxVowel);
	}//greekTonosSetOnSecond.


	/**
	 * Set the tonos of a word on a given index.<p>
	 * <b>GIVEN<b>:<br/>
	 *   - The word has NO tonos.<br/>
	 *   - The index of αι,ει,οι,αυ,ευ,ου is on first letter.
	 * @param word
	 *			The word on which we want to set tonos on a given index.
	 * @param maxVowel
	 *  		The index of the vowel we want to put the tonos.
	 *
	 * @modified 2001.03.22
	 * @since 2001.03.21
	 * @author HoKoNoUmo
	 */
	public String greekTonosSetOnIndex(String word, int maxVowel)
	{
		char c=word.charAt(maxVowel);

		if (c=='α')
		{
			//if α is **NOT** the last-vowel
			if (maxVowel!=word.length()-1)
			{
				if (word.charAt(maxVowel+1)=='ι')
					return word.substring(0,maxVowel+1) +"\u03af" +word.substring(maxVowel+2,word.length());
				else if (word.charAt(maxVowel+1)=='υ')
					return word.substring(0,maxVowel+1) +"ύ" +word.substring(maxVowel+2,word.length());
				else
					return word.substring(0,maxVowel) +"ά" +word.substring(maxVowel+1,word.length());
			}
			else
				return word.substring(0,maxVowel) +"ά" +word.substring(maxVowel+1,word.length());
		}
		else if (c=='ε')
		{
			//if ε is **NOT** the last-vowel
			if (maxVowel!=word.length()-1)
			{
				if (word.charAt(maxVowel+1)=='ι')
					return word.substring(0,maxVowel+1) +"\u03af" +word.substring(maxVowel+2,word.length());
				else if (word.charAt(maxVowel+1)=='υ')
					return word.substring(0,maxVowel+1) +"ύ" +word.substring(maxVowel+2,word.length());
				else
					return word.substring(0,maxVowel) +"έ" +word.substring(maxVowel+1,word.length());
			}
			else
				return word.substring(0,maxVowel) +"έ" +word.substring(maxVowel+1,word.length());
		}
		else if (c=='ο')
		{
			//if ο is **NOT** the last-vowel
			if (maxVowel!=word.length()-1)
			{
				if (word.charAt(maxVowel+1)=='ι')
					return word.substring(0,maxVowel+1) +"\u03af" +word.substring(maxVowel+2,word.length());
				else if (word.charAt(maxVowel+1)=='υ')
					return word.substring(0,maxVowel+1) +"ύ" +word.substring(maxVowel+2,word.length());
				else
					return word.substring(0,maxVowel) +"ό" +word.substring(maxVowel+1,word.length());
			}
			else
				return word.substring(0,maxVowel) +"ό" +word.substring(maxVowel+1,word.length());
		}
		else if (c=='η')
			return 	word.substring(0,maxVowel) +"ή" +word.substring(maxVowel+1,word.length());
		else if (c=='ι')
			return 	word.substring(0,maxVowel) +"ί" +word.substring(maxVowel+1,word.length());
		else if (c=='υ')
			return 	word.substring(0,maxVowel) +"ύ" +word.substring(maxVowel+1,word.length());
		else if (c=='ω')
			return 	word.substring(0,maxVowel) +"ώ" +word.substring(maxVowel+1,word.length());
		else if (c=='ϋ')
			return 	word.substring(0,maxVowel) +"ΰ" +word.substring(maxVowel+1,word.length());
		else if (c=='ϊ')
			return 	word.substring(0,maxVowel) +"ΐ" +word.substring(maxVowel+1,word.length());
		else
			return word;
	}//greekTonosSetOnIndex.


	/**
	 * Returns the index of first-vowel of a greek-word without tonos.
	 *	 IF ει,οι,αι,ου,αυ,ευ, returns the index of the first vowel.
	 * @modified
	 * @since 2001.03.21
	 * @author HoKoNoUmo
	 */
	public int findIndexOfFirstGreekVowel(String word)
	{
		word=greekTonosRemove(word);
		int minVowel=1000;
		if (word.indexOf("α")<minVowel && word.indexOf("α")!=-1)
			minVowel=word.indexOf("α");
		if (word.indexOf("ε")<minVowel && word.indexOf("ε")!=-1)
			minVowel=word.indexOf("ε");
		if (word.indexOf("ο")<minVowel && word.indexOf("ο")!=-1)
			minVowel=word.indexOf("ο");
		if (word.indexOf("η")<minVowel && word.indexOf("η")!=-1)
			minVowel=word.indexOf("η");
		if (word.indexOf("ω")<minVowel && word.indexOf("ω")!=-1)
			minVowel=word.indexOf("ω");
		if (word.indexOf("ι")<minVowel && word.indexOf("ι")!=-1)
		{
			if (word.indexOf("ι")!=0)
			{
				if (	word.charAt(word.indexOf("ι")-1)=='ε'		//ει
						||word.charAt(word.indexOf("ι")-1)=='ο' 	//οι
						||word.charAt(word.indexOf("ι")-1)=='α' )	//αι
					minVowel=word.indexOf("ι")-1;
				else
					minVowel=word.indexOf("ι");
			}
			else
				minVowel=word.indexOf("ι");
		}
		if (word.indexOf("υ")<minVowel && word.indexOf("υ")!=-1)
		{
			if (word.indexOf("υ")!=0)
			{
				if (	word.charAt(word.indexOf("υ")-1)=='ο' 	//ού
						||word.charAt(word.indexOf("υ")-1)=='α' 	//αύ
						||word.charAt(word.indexOf("υ")-1)=='ε' )	//εύ, απαγορεύεται
					minVowel=word.indexOf("υ")-1;
				else
					minVowel=word.indexOf("υ");
			}
			else
				minVowel=word.indexOf("υ");
		}
		if (word.indexOf("ϋ")<minVowel && word.indexOf("ϋ")!=-1)
			minVowel=word.indexOf("ϋ");
		if (word.indexOf("ϊ")<minVowel && word.indexOf("ϊ")!=-1)
			minVowel=word.indexOf("ϊ");

		return minVowel;
	}//findIndexOfFirstGreekVowel.


	/**
	 * Rreturns the index of last-vowel of a greek-word without tonos.
	 *	 IF ει,οι,αι,ου,αυ,ευ, returns the index of the first vowel.
	 * @modified
	 * @since 2001.03.21
	 * @author HoKoNoUmo
	 */
	public int findIndexOfLastGreekVowel(String word)
	{
		word=greekTonosRemove(word);
		int maxVowel=-1;

		if (word.lastIndexOf("α")>maxVowel)
			maxVowel=word.lastIndexOf("α");
		if (word.lastIndexOf("ε")>maxVowel)
			maxVowel=word.lastIndexOf("ε");
		if (word.lastIndexOf("ο")>maxVowel)
			maxVowel=word.lastIndexOf("ο");

		if (word.lastIndexOf("η")>maxVowel)
			maxVowel=word.lastIndexOf("η");
		if (word.lastIndexOf("ω")>maxVowel)
			maxVowel=word.lastIndexOf("ω");

		if (word.lastIndexOf("ι")>maxVowel)
		{
			if (word.lastIndexOf("ι")>0)
			{
				if ( (word.charAt(word.lastIndexOf("ι")-1)=='ε')		//ει
						||(word.charAt(word.lastIndexOf("ι")-1)=='ο') 	//οι
						||(word.charAt(word.lastIndexOf("ι")-1)=='α') )	//αι
					maxVowel=word.lastIndexOf("ι")-1;
				else
					maxVowel=word.lastIndexOf("ι");
			}
			else
				maxVowel=word.lastIndexOf("ι");
		}

		if (word.lastIndexOf("υ")>maxVowel)
		{
			if (word.lastIndexOf("υ")!=0)
			{
				if (	word.charAt(word.lastIndexOf("υ")-1)=='ο' 	//ού
						||word.charAt(word.lastIndexOf("υ")-1)=='α' 	//αύ
						||word.charAt(word.lastIndexOf("υ")-1)=='ε' )	//εύ, απαγορεύεται
					maxVowel=word.lastIndexOf("υ")-1;
				else
					maxVowel=word.lastIndexOf("υ");
			}
			else
				maxVowel=word.lastIndexOf("υ");
		}

		if (word.lastIndexOf("ϋ")>maxVowel)
			maxVowel=word.lastIndexOf("ϋ");
		if (word.lastIndexOf("ϊ")>maxVowel)
			maxVowel=word.lastIndexOf("ϊ");

		return maxVowel;
	}//findIndexOfLastGreekVowel.


	/**
	 * Returns the <code>number</code> of vowels
	 * (αι,ει,οι,ου,αυ,ευ counts one) of a word, ie its syllables.
	 * @modified
	 * @since 2001.03.21
	 * @author HoKoNoUmo
	 */
	public int findNumberOfGreekVowels(String word)
	{
		int syllable=0;
		int index=-1;

		while (word.length()>0)
		{
			index=findIndexOfLastGreekVowel(word);
			if (index!=-1)
			{
				syllable++;
				word=word.substring(0,index);
			}
			else break;
		}

		return syllable;
	}//findNumberOfGreekVowels.


	/**
	 * Test if a character is consonant.
	 * @modified
	 * @since 2001.03.22
	 * @author HoKoNoUmo
	 */
	public boolean isLetterConsonantEnglish(char c)
	{
		//what u/w/y?
		if (c=='b' ||
				c=='c' ||
				c=='d' ||
				c=='f' ||
				c=='g' ||
				c=='h' ||
				c=='j' ||
				c=='k' ||
				c=='l' ||
				c=='m' ||
				c=='n' ||
				c=='p' ||
				c=='q' ||
				c=='r' ||
				c=='s' ||
				c=='t' ||
				c=='v' ||
				c=='x' ||
				c=='z' ||

				c=='B' ||
				c=='C' ||
				c=='D' ||
				c=='F' ||
				c=='G' ||
				c=='H' ||
				c=='J' ||
				c=='K' ||
				c=='L' ||
				c=='M' ||
				c=='N' ||
				c=='P' ||
				c=='Q' ||
				c=='R' ||
				c=='S' ||
				c=='T' ||
				c=='V' ||
				c=='X' ||
				c=='Z'
			)
			return true;
		else
			return false;
	}//isLetterConsonantEnglish.


	/**
	 * Test if a character is vowel.
	 * @modified
	 * @since 2001.03.14
	 * @author HoKoNoUmo
	 */
	public boolean isLetterVowelEnglish(char c)
	{
		//what about w/u/y?
		if (c=='a' ||
				c=='A' ||
				c=='e' ||
				c=='E' ||
				c=='o' ||
				c=='O' ||
				c=='i' ||
				c=='I'    )
			return true;
		else
			return false;
	}//isLetterVowelEnglish


	/**
	 * Test if a character is vowel-greek.
	 * @modified
	 * @since 2001.03.15
	 * @author HoKoNoUmo
	 */
	public boolean isLetterVowelGreek(char c)
	{
		if (c=='\u03b1' ||//α
				c=='\u03ac' ||//ά
				c=='\u0391' ||//A
				c=='\u0386' ||//Α and tonos
				c=='\u03b5' ||//ε
				c=='\u03ad' ||//έ
				c=='\u0395' ||//Ε
				c=='\u0388' ||//Έ
				c=='\u03b7' ||//η
				c=='\u03ae' ||//ή
				c=='\u0397' ||//Η
				c=='\u0389' ||//Ή
				c=='\u03b9' ||//ι
				c=='\u03af' ||//ί
				c=='\u0399' ||//Ι
				c=='\u038a' ||//Ί
				c=='\u03ca' ||//ϊ
				c=='\u03aa' ||//Ϊ
				c=='\u0390' ||//ΐ
//				c=='\u0390' ||//
				c=='\u03bf' ||//ο
				c=='\u03cc' ||//ό
				c=='\u039f' ||//Ο
				c=='\u038c' ||//Ό
				c=='\u03c5' ||//υ
				c=='\u03cd' ||//ύ
				c=='\u03a5' ||//Υ
				c=='\u038e' ||//Ύ
				c=='\u03cb' ||//ϋ
				c=='\u03b0' ||//ΰ
				c=='\u03ab' ||//Ϋ
//				c=='\u03ab' ||//
				c=='\u03c9' ||//ω
				c=='\u03ce' ||//ώ
				c=='\u03a9' ||//Ω
				c=='\u038f'  )//Ώ
			return true;
		else
			return false;
	}
}
