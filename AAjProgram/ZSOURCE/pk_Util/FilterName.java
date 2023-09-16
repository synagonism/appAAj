package pk_Util;

import java.io.*;

/**
 * Filters the file-names that end with a given name.
 *
 * @modified 1999.03.21
 * @author nikas
 */
public class FilterName implements FilenameFilter {

	String filter;

	public FilterName (String fltr){
		filter = fltr;
	}

 	public boolean accept(File dir, String name) {
  	String lower1 = name.toLowerCase();
  	String lower2 = filter.toLowerCase();
 		if (lower1.startsWith(lower2)) return true;
   	return false;
	}

}

