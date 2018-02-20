package Tooled;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

class RegexLoader {
	
	
	
	
	
	/*
	 * Load up the regexes in the listed file.
	 * */
	public List<Pattern> loadRegexes(String regexFile) {
		List<String> allRegexes = new ArrayList<String>();
		List<Pattern> allPatterns = new ArrayList<Pattern>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(regexFile));
			String line = br.readLine();

			while (line != null) {
				allRegexes.add(line);
				line = br.readLine();
			}
			allPatterns = compileLoadedRegexes(allRegexes);

		} catch (FileNotFoundException fof) {
			System.out.println("Regex file cannot be found");
			fof.printStackTrace();
		} catch (IOException io) {
			System.out.println("Regex file cannot be opened");
			io.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return allPatterns;
	}

	
	
	
	
	
	
	/*
	 * This method is needed to convert the loaded regexes to Patterns
	 */
	public static List<Pattern> compileLoadedRegexes(List<String> allRegexes) {
		List<Pattern> list_patterns = new ArrayList<Pattern>();
		for (int i = 0; i < allRegexes.size(); i++) {
			Pattern pattern = Pattern.compile(allRegexes.get(i),
					Pattern.CASE_INSENSITIVE);
			list_patterns.add(pattern);
		}
		return list_patterns;
	}

	
	
	
	
	
	
	/*
	 * Loading the signs of the individual entities to be uploaded into the
	 * excel file
	 */
	public List<Integer> loadSigns(String fileName) {
		List<Integer> allSigns = new ArrayList<Integer>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();

			while (line != null) {
				allSigns.add(Integer.parseInt(line));
				line=br.readLine();
			}
		} catch (FileNotFoundException fof) {
			System.out.println("Signs file cannot be found");
			fof.printStackTrace();
		} catch (IOException io) {
			System.out.println("Signs file cannot be opened");
			io.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return allSigns;
	}
	
	
	
	
	
	/*
	 * to create a map of the patterns the respective signs to be used before excel upload
	 * */
	public Map<Pattern, Integer> createRegexSignMap(List<Pattern> regexes, List<Integer> signs){
		 Map<Pattern, Integer> signs_for_regexes = new HashMap<Pattern, Integer>();
		for(int i=0;i<signs.size();i++){
			signs_for_regexes.put(regexes.get(i), signs.get(i));
		}
		return signs_for_regexes;
	}
	
	
	
	
	
	/*
	 * This is to create the map of Patterns and the respective rows and the columns in the Excel file
	 * 
	 * */
	public Map<Pattern, String> createExcelMap(List<Pattern> regexes,String entities){
		 Map<Pattern, String> rows_columns_in_excel = new HashMap<Pattern, String>();
		String[] details=entities.split("\r\n");
		for(int i=0;i<details.length;i++){
			rows_columns_in_excel.put(regexes.get(i), details[i]);
		}
		return rows_columns_in_excel;
	}
	

	
	
	
	
	

	/*
	 * This loads the details of the rows and columns to be stored in the Excel file based
	 *  on the Pattern
	 * */
	public String loadEntities(String fileName) {
		BufferedReader br = null;
		String entities="";
		try {
			br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				entities+=line+"\r\n";
				line = br.readLine();
			}
		} catch (FileNotFoundException fof) {
			System.out.println("Entities file cannot be found");
			fof.printStackTrace();
		} catch (IOException io) {
			System.out.println("Entitied file cannot be opened");
			io.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return entities;
	}
}
