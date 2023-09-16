package pk_Util;

import java.io.*;

/**
 * Filters the file-names that have a given ID.
 *
 * @modified 2009.10.23
 * @since 1999.03.22
 * @author HoKoNoUmo
 */
public class FilterID implements FilenameFilter {

	String filter;

	public FilterID (String fltr){
		filter = fltr +"@.xml";
	}

	public boolean accept(File dir, String name) {
		String lower1 = name.toLowerCase();
		String lower2 = filter.toLowerCase();
		if (lower1.endsWith(lower2)) return true;
		return false;
	}

}

