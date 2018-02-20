package Team;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class MyReader {
	public void loadEmUp(String regexFile) {
		List<String> allRegexes = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(regexFile));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				// sb.append(line);
				allRegexes.add(line);
				line = br.readLine();
			}
			compileEm(allRegexes);

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
	}

	public static void compileEm(List<String> allRegexes) {
		for (int i = 0; i < allRegexes.size(); i++) {
			Pattern pattern = Pattern.compile(allRegexes.get(i),Pattern.CASE_INSENSITIVE);
			FileConstants.allPatterns.add(pattern);
			
		}

	}
	

	public String loadBCFEntities(String fileName) {
		BufferedReader br = null;
		String entities="";
		try {
			br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
//				 sb.append(line);
//				FileConstants.Excel_Heads.add(line);
				entities+=line+"\r\n";
				line = br.readLine();
			}
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
		return entities;
	}
	
	public void loadBCFSigns(String fileName) {
		BufferedReader br = null;
//		String sign="";
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();

			while (line != null) {
//				sign+=line+"\r\n";
				FileConstants.bcfsigns.add(Integer.parseInt(line));
				line = br.readLine();
			}
		} catch (FileNotFoundException fof) {
			System.out.println("signs file cannot be found");
			fof.printStackTrace();
		} catch (IOException io) {
			System.out.println("signs file cannot be opened");
			io.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	
	
	public void createMap(String entities){
		String[] details=entities.split("\r\n");
		for(int i=0;i<details.length;i++){
			FileConstants.Excel_Heads.put(FileConstants.allPatterns.get(i), details[i]);
		}
	}
	
	public void createMap(List<Integer> signs){
		for(int i=0;i<signs.size();i++){
			FileConstants.Sign_Heads.put(FileConstants.allPatterns.get(i), signs.get(i));
		}
	}
	public void createBSAMap(String entities){
		String[] details=entities.split("\r\n");
		for(int i=0;i<details.length;i++){
			FileConstants.BSAExcel_Heads.put(FileConstants.BSAPatterns.get(i), details[i]);
		}
	}
	
	public void createBSAMap(List<Integer> signs){
		for(int i=0;i<signs.size();i++){
			FileConstants.BSASign_Heads.put(FileConstants.BSAPatterns.get(i), signs.get(i));
		}
	}
	public void loadBSARegexesUp(String regexFile) {
		List<String> allRegexes = new ArrayList<String>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(regexFile));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				// sb.append(line);
				allRegexes.add(line);
				line = br.readLine();
			}
			compileBSARegexes(allRegexes);

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
	}

	public static void compileBSARegexes(List<String> allRegexes) {
		for (int i = 0; i < allRegexes.size(); i++) {
			Pattern pattern = Pattern.compile(allRegexes.get(i),Pattern.CASE_INSENSITIVE);
			FileConstants.BSAPatterns.add(pattern);
			
		}

	}
	public String loadBSAEntities(String fileName) {
		BufferedReader br = null;
		String entities="";
		try {
			br = new BufferedReader(new FileReader(fileName));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
//				 sb.append(line);
//				FileConstants.Excel_Heads.add(line);
				entities+=line+"\r\n";
				line = br.readLine();
			}
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
		return entities;
	}
	
	public void loadBSASigns(String fileName) {
		BufferedReader br = null;
//		String sign="";
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line = br.readLine();

			while (line != null) {
//				sign+=line+"\r\n";
				FileConstants.BSAsigns.add(Integer.parseInt(line));
				line = br.readLine();
			}
		} catch (FileNotFoundException fof) {
			System.out.println("signs file cannot be found");
			fof.printStackTrace();
		} catch (IOException io) {
			System.out.println("signs file cannot be opened");
			io.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
}
