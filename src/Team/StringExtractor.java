package Team;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

class StringExtractor {
	
	
	
	
	
	
	
	String readExcelFile(String inputDir){
		String parsedString=null;
		InputStream inputStream = null;
		POIFSFileSystem fileSystem = null;
		try
		{
		inputStream = new FileInputStream(inputDir);

		fileSystem = new POIFSFileSystem (inputStream);

		HSSFWorkbook workBook = new HSSFWorkbook (fileSystem);
		HSSFSheet sheet = workBook.getSheetAt (0);
		Iterator rows = sheet.rowIterator ();

		while (rows.hasNext ())
		{
		HSSFRow row = (HSSFRow) rows.next ();
		Iterator cells = row.cellIterator ();
			while (cells.hasNext ())
				{
				HSSFCell cell = (HSSFCell) cells.next ();
				parsedString += cell.getStringCellValue()+"\t";
				}
			}
		}catch(FileNotFoundException fnf){
				//Do Nothing
		}catch(IOException io){
				//Do Nothing
		}
		return null;
	}

	/*
	 * This method extracts the data out of all the listed files in the input
	 * directory and returns all the available data in txt files to the calling
	 * variable.
	 */
	String readParsedFiles(String inputDir) {

		List<File> listOfFiles = FileUtil.readFiles(inputDir);
		File file = null;
		String totalText = null;
		BufferedReader bufferedReader = null;
		for (int k = 0; k < listOfFiles.size(); k++) {
			file = listOfFiles.get(k);
			try {
				if (file != null
						&& file.isFile()
						&& (file.getName().endsWith("csv") || file.getName()
								.endsWith("CSV"))) {
					FileReader fileReader = new FileReader(file.getPath());

					bufferedReader = new BufferedReader(fileReader);

					while ((bufferedReader.readLine()) != null) {

						totalText += bufferedReader.readLine() + "\n";
					}

				} else {
					System.out.println("Not a valid csv file or access denied");
				}
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return totalText;
	}

	/*
	 * This loads all the available regexes and matches them up to the data read
	 * from the text files
	 */
	void matchBCF(ExcelCreator fileCreator, String currentLine) {
		int finalDeprecation = 0;
		int totalg_a = 0;
		RegexLoader myLoader = new RegexLoader();
		Matcher matcher = null;

		List<Pattern> allRegexes = myLoader
				.loadRegexes(FileConstants.BCFREGEX_FILE);
		List<Integer> allSigns = myLoader
				.loadSigns(FileConstants.BCFEXCEL_SIGNS);
		String entities = myLoader
				.loadEntities(FileConstants.BCFEXCEL_ENTITIES);

		Map<Pattern, Integer> signs_for_regexes = myLoader.createRegexSignMap(
				allRegexes, allSigns);
		Map<Pattern, String> fields_for_regexes = myLoader.createExcelMap(
				allRegexes, entities);

		for (int i = 0; i < allRegexes.size(); i++) {
			Pattern currentPattern = allRegexes.get(i);
			matcher = currentPattern.matcher(currentLine);
			if (currentPattern.pattern().equals(
					Pattern.compile(FileConstants.depreciationRegex).pattern())) {
				handleDepreciation(fileCreator, currentLine, finalDeprecation,
						signs_for_regexes, fields_for_regexes, currentPattern);

			} else if (currentPattern.pattern().equals(FileConstants.g_a_regex)) {
				handleGA_Regex(fileCreator, currentLine, totalg_a,
						signs_for_regexes, fields_for_regexes, currentPattern);
			} else if (matcher.find()) {
				handleFields(fileCreator, matcher, signs_for_regexes,
						fields_for_regexes, currentPattern);
			}
		}
	}

	private void handleFields(ExcelCreator fileCreator, Matcher matcher,
			Map<Pattern, Integer> signs_for_regexes,
			Map<Pattern, String> fields_for_regexes, Pattern currentPattern) {
		String intermediate = (matcher.group(1));
		if (intermediate != null && !intermediate.isEmpty()) {
			intermediate = intermediate.trim();
			intermediate = (intermediate).replace(" ", "");
			intermediate = (intermediate).replace(",", "");
			intermediate = (intermediate).replace(".", "");
			intermediate = (intermediate).replace("_", "");
			intermediate = (intermediate).replace("l", "1");
			intermediate = (intermediate).replace("O", "0");
			if (intermediate != null && !intermediate.isEmpty()) {
				int row=0;
				int col=0;
				try {
					long data = Long.parseLong(intermediate);
					data = data * signs_for_regexes.get(currentPattern);
					String[] row_col = (fields_for_regexes.get(currentPattern))
							.split(",");
					row = Integer.parseInt(row_col[0]);
					col = Integer.parseInt(row_col[1]);
					fileCreator.addDataToBCF(row, col, data);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					fileCreator.addDataToBCF(row, col,"Enter manually;");
				}
			}
		}
	}

	private void handleGA_Regex(ExcelCreator fileCreator, String currentLine,
			int totalg_a, Map<Pattern, Integer> signs_for_regexes,
			Map<Pattern, String> fields_for_regexes, Pattern currentPattern) {
		String[] dividedRegex = (FileConstants.g_a_regex).split("@@");
		for (int j = 0; j < dividedRegex.length; j++) {
			Pattern p = Pattern.compile(dividedRegex[j]);
			Matcher match = p.matcher(currentLine);
			if (match.find()) {
				String intermediate = (match.group(1));
				intermediate = intermediate.trim();
				if (intermediate != null && !intermediate.isEmpty()) {
					intermediate = (intermediate).replace(" ", "");
					intermediate = (intermediate).replace(",", "");
					intermediate = (intermediate).replace(".", "");
					intermediate = (intermediate).replace("_", "");
					intermediate = (intermediate).replace("l", "1");
					intermediate = (intermediate).replace("O", "0");
					if (intermediate != null && !intermediate.isEmpty()) {
						int row=0;
						int col=0;
						try {
							int data = Integer.parseInt(intermediate);
							if (p.pattern().equals(
									Pattern.compile(
											FileConstants.total_deductions)
											.pattern())) {
								// Do Nothing
							} else {
								data = -1 * data;
							}
							totalg_a += data;
						
					
				

				totalg_a = totalg_a * (signs_for_regexes.get(currentPattern));
				String[] row_col = (fields_for_regexes.get(currentPattern))
						.split(",");
				 row = Integer.parseInt(row_col[0]);
				 col = Integer.parseInt(row_col[1]);
				fileCreator.addDataToBCF(row, col, totalg_a);
				} catch (NumberFormatException nfe) {
					nfe.printStackTrace();
					fileCreator.addDataToBCF(row, col,"Enter manually;");
				}
					}
				}

			}
		}
	}

	private void handleDepreciation(ExcelCreator fileCreator,
			String currentLine, int finalDeprecation,
			Map<Pattern, Integer> signs_for_regexes,
			Map<Pattern, String> fields_for_regexes, Pattern currentPattern) {
		String[] dividedRegex = (FileConstants.depreciationRegex).split("@@");
		for (int j = 0; j < dividedRegex.length; j++) {
			Pattern p = Pattern.compile(dividedRegex[j]);
			Matcher match = p.matcher(currentLine);
			if (match.find()) {
				String intermediate = (match.group(1));
				intermediate = intermediate.trim();
				if (intermediate != null && !intermediate.isEmpty()) {
					intermediate = (intermediate).replace(" ", "");
					intermediate = (intermediate).replace(",", "");
					intermediate = (intermediate).replace(".", "");
					intermediate = (intermediate).replace("_", "");
					intermediate = (intermediate).replace("l", "1");
					intermediate = (intermediate).replace("O", "0");
					// intermediate = (intermediate).replace("\r\n", "");
					if (intermediate != null && !intermediate.isEmpty()) {
						try {
							int data = Integer.parseInt(intermediate);
							finalDeprecation += data;
						} catch (NumberFormatException nfe) {
							nfe.printStackTrace();
						}
					}
				}
			}
			finalDeprecation = finalDeprecation
					* (signs_for_regexes.get(currentPattern));
			String[] row_col = (fields_for_regexes.get(currentPattern))
					.split(",");
			int row = Integer.parseInt(row_col[0]);
			int col = Integer.parseInt(row_col[1]);
			fileCreator.addDataToBCF(row, col, finalDeprecation);
		}
	}

	/*
	 * This one handles the data in the Balance Sheet Area
	 */

	private static void matchBSA(String outputFile, ExcelCreator fileCreator, String word) {
		RegexLoader myLoader = new RegexLoader();
		List<Pattern> allRegexes = myLoader
				.loadRegexes(FileConstants.BCFREGEX_FILE);
		List<Integer> allSigns = myLoader
				.loadSigns(FileConstants.BCFEXCEL_SIGNS);
		String entities = myLoader
				.loadEntities(FileConstants.BCFEXCEL_ENTITIES);
		
		Map<Pattern, Integer> signs_for_regexes = myLoader.createRegexSignMap(
				allRegexes, allSigns);
		Map<Pattern, String> fields_for_regexes = myLoader.createExcelMap(
				allRegexes, entities);


		for (int i = 0; i < allRegexes.size(); i++) {
			Pattern currentPattern = allRegexes.get(i);
			Matcher mat = currentPattern.matcher(word);
			if (mat.find()) {
				if (mat.groupCount() > 0) {
					String intermediate = (mat.group(1));
					if (intermediate != null && !intermediate.isEmpty()) {
						intermediate = intermediate.trim();
						if (intermediate != null && !intermediate.isEmpty()) {
							intermediate = (intermediate).replace(" ", "");
							intermediate = (intermediate).replace(",", "");
							intermediate = (intermediate).replace(".", "");
							intermediate = (intermediate).replace("_", "");
							intermediate = (intermediate).replace("l", "1");
							intermediate = (intermediate).replace("O", "0");
							if (intermediate != null && !intermediate.isEmpty()) {
								int row=0;
								int col=0;
								try {
									long data = Long.parseLong(intermediate);
									data = data
											* signs_for_regexes.get(currentPattern);
									String[] row_col = (fields_for_regexes.get(currentPattern))
											.split(",");
									row = Integer.parseInt(row_col[0]);
									col = Integer.parseInt(row_col[1]);
									fileCreator.addDataToBSA(row, col,data);
								} catch (NumberFormatException nfe) {
									fileCreator.addDataToBSA(row, col,"Enter manually;");
								}
							}
						}
					}
				}
			}
		}
	}
}
