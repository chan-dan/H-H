package Team;

import java.io.File;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	
	public static String escapeRegex(String aRegexFragment){
	    final StringBuilder result = new StringBuilder();

	    final StringCharacterIterator iterator = 
	      new StringCharacterIterator(aRegexFragment)
	    ;
	    char character =  iterator.current();
	    while (character != CharacterIterator.DONE ){
	      /*
	       All literals need to have backslashes doubled.
	      */
	      if (character == '.') {
	        result.append("\\.");
	      }
	      else if (character == '\\') {
	        result.append("\\\\");
	      }
	      else if (character == '?') {
	        result.append("\\?");
	      }
	      else if (character == '*') {
	        result.append("\\*");
	      }
	      else if (character == '+') {
	        result.append("\\+");
	      }
	      else if (character == '&') {
	        result.append("\\&");
	      }
	      else if (character == ':') {
	        result.append("\\:");
	      }
	      else if (character == '{') {
	        result.append("\\{");
	      }
	      else if (character == '}') {
	        result.append("\\}");
	      }
	      else if (character == '[') {
	        result.append("\\[");
	      }
	      else if (character == ']') {
	        result.append("\\]");
	      }
	      else if (character == '(') {
	        result.append("\\(");
	      }
	      else if (character == ')') {
	        result.append("\\)");
	      }
	      else if (character == '^') {
	        result.append("\\^");
	      }
	      else if (character == '$') {
	        result.append("\\$");
	      }
	      else {
	        //the char is not a special one
	        //add it to the result as is
	        result.append(character);
	      }
	      character = iterator.next();
	    }
	    return result.toString();
	  }

	public static List<File> readFiles(String path){
		File folder = new File(path);
		List<File> listedFiles=new ArrayList<File>();
		File[] listOfFiles = folder.listFiles();
		System.out.println("Files Found are:");
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        listedFiles.add(file);
		        System.out.println(file.getName());
		    }
		}
		return listedFiles;
	}
	
	public static void escapeSlash(String regex){
		String newRegex=regex.replace("\\","\\\\");
		System.out.println(newRegex);
	}
	public static void main(String[] args){
//		escapeSlash("20\\r\\n[\\w\\s\\—\\-\\~\\.)\\(]+[\\.]+?\\r\\n[\\w\\s\\.]+?[\\>]?\\r\\n20\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n@@14\\r\\n[\\w\\s\\—\\-\\~\\)\\(]+[\\.]+?\\r\\n[\\w\\s\\.]+?14\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n@@11\\r\\n[\\w\\s\\—\\-\\~\\)\\(]+[\\.]+?\\r\\n[\\w\\s\\.]+?11\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n12a@@12a\\r\\n[\\w\\s\\.\\,]+[\\.]+?\\r\\n12[ ]?a\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\nb@@c\\r\\n[\\w\\s\\.\\,]+[\\.]+?\\r\\n16[ ]?C\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n@@13\\r\\n[\\w\\s\\(\\)\\.\\,]+[\\.]+?\\r\\n13\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n@@12\\r\\n[\\w\\s\\]+[\\.]+?\\r\\n12\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n@@7\\r\\n[\\w\\s\\.\\,\\—\\-\\~\\)\\(]+[\\.]+?\\r\\n7\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n8@@8\\r\\n[\\w\\s\\.\\,\\(\\)\\—\\-\\~]+[\\.]+\\r\\n8\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n@@9\\r\\n[\\w\\s\\.\\,\\(\\)\\—\\-\\~]+[\\.]+\\r\\n9\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n10@@10\\r\\n[\\w\\s\\.\\,\\(\\)\\—\\-\\~]+[\\.]+\\r\\n10\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n11@@11\\r\\n[\\w\\s\\.\\,\\(\\)\\—\\-\\~]+[\\.]+\\r\\n11\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n12@@16\\r\\n[\\w\\s\\]+[\\.]+?\\r\\n16\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n");
	}
}
