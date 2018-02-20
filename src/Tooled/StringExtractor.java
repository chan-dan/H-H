package Tooled;

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

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

class StringExtractor {

	/*
	 * This method extracts the data out of all the listed files in the input
	 * directory and returns all the available data in xlsx files to the calling
	 * variable.
	 */
	String readExcelFiles(String inputDir) {
		String parsedString = null;
		List<File> listOfFiles = FileUtil.readFiles(inputDir);
		File file = null;
		for (int k = 0; k < listOfFiles.size(); k++) {
			file = listOfFiles.get(k);
			if (file != null
					&& file.isFile()
					&& (file.getName().endsWith("xlsx") || file.getName()
							.endsWith("XLSX"))) {
				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(file);
					XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
					XSSFSheet sheet = workBook.getSheetAt(0);
					Iterator rows = sheet.rowIterator();

					while (rows.hasNext()) {
						XSSFRow row = (XSSFRow) rows.next();
						Iterator cells = row.cellIterator();
						while (cells.hasNext()) {
							XSSFCell cell = (XSSFCell) cells.next();
							parsedString += cell.getStringCellValue() + "\t";
						}
						parsedString+="\r\n";
					}
				} catch (FileNotFoundException fnf) {
					// Do Nothing
				} catch (IOException io) {
					// Do Nothing
				}
			}
		}
		System.out.println(parsedString);
		return parsedString;
	}

	/*
	 * This method extracts the data out of all the listed files in the input
	 * directory and returns all the available data in csv files to the calling
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
						&& (file.getName().endsWith("txt") || file.getName()
								.endsWith("TXT"))) {
					FileReader fileReader = new FileReader(file.getPath());

					bufferedReader = new BufferedReader(fileReader);
					String line = bufferedReader.readLine();

					while (line != null) {
						totalText += line;
						totalText += "\n";
						line = bufferedReader.readLine();
					}

				} else if (file != null
						&& file.isFile()
						&& (file.getName().endsWith("xlsx") || file.getName()
								.endsWith("XLSX"))) {
					readExcelFiles(inputDir);

				}else if (file != null
						&& file.isFile()
						&& (file.getName().endsWith("docx") || file.getName()
								.endsWith("DOCX"))) {
					readDocxFiles(inputDir);

				} else {
					System.out.println("Not a valid file or access denied");
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

	String readDocxFiles(String inputDir) {
		String parsedString = null;
		WordExtractor extractor=null;
		List<File> listOfFiles = FileUtil.readFiles(inputDir);
		File file = null;
		for (int k = 0; k < listOfFiles.size(); k++) {
			file = listOfFiles.get(k);
			if (file != null
					&& file.isFile()
					&& (file.getName().endsWith("docx") || file.getName()
							.endsWith("DOCX"))) {
				InputStream inputStream = null;
				try {
					inputStream = new FileInputStream(file);
					XWPFDocument document=new XWPFDocument(inputStream);
					List<XWPFTable> tables = document.getTables();

					for (XWPFTable table : tables) {
	                    parsedString+=table.getText();
	                }
					document.close();
				}catch (FileNotFoundException fnf)
			            {
			            	//Do nothing
			                fnf.printStackTrace();
			            }catch(IOException io){
			            	//Do Nothing
			            	io.printStackTrace();
			            }
				}
			}
			System.out.println(parsedString);
			return parsedString;
		
	}

	/*
	 * This loads all the available regexes and matches them up to the data read
	 * from the text files
	 */
	void matchBCF(ExcelCreator fileCreator, String currentLine) {
		int cap_gain = 0;
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
			if (currentPattern.pattern().toString()
					.contentEquals(FileConstants.depreciationRegex)) {
				handleDepreciation(fileCreator, currentLine, finalDeprecation,
						signs_for_regexes, fields_for_regexes, currentPattern);

			} else if (currentPattern.pattern().equals(FileConstants.g_a_regex)) {
				handleGA_Regex(fileCreator, currentLine, totalg_a,
						signs_for_regexes, fields_for_regexes, currentPattern);
			} else if (currentPattern.pattern().toString()
					.contentEquals(FileConstants.capital_gain)) {
				handleCapitalGain(fileCreator, currentLine, cap_gain,
						signs_for_regexes, fields_for_regexes, currentPattern);

			} else if (matcher.find()) {
				handleFields(fileCreator, matcher, signs_for_regexes,
						fields_for_regexes, currentPattern);
			}
		}
	}

	private void handleCapitalGain(ExcelCreator fileCreator,
			String currentLine, int cap_gain,
			Map<Pattern, Integer> signs_for_regexes,
			Map<Pattern, String> fields_for_regexes, Pattern currentPattern) {
		String[] dividedRegex = (FileConstants.capital_gain).split("@@");
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
					intermediate = (intermediate).replace("(", "");
					intermediate = (intermediate).replace(")", "");
					if (intermediate != null && !intermediate.isEmpty()) {
						try {
							int data = Integer.parseInt(intermediate);
							cap_gain += data;
						} catch (NumberFormatException nfe) {
							nfe.printStackTrace();
						}
					}
				}
			}
		}
		cap_gain = cap_gain * (signs_for_regexes.get(currentPattern));
		String[] row_col = (fields_for_regexes.get(currentPattern)).split(",");
		int row = Integer.parseInt(row_col[0]);
		int col = Integer.parseInt(row_col[1]);
		fileCreator.addDataToBCF(row, col, cap_gain);

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
				int row = 0;
				int col = 0;
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
					fileCreator.addDataToBCF(row, col, "Enter manually;");
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
						int row = 0;
						int col = 0;
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

							totalg_a = totalg_a
									* (signs_for_regexes.get(currentPattern));
							String[] row_col = (fields_for_regexes
									.get(currentPattern)).split(",");
							row = Integer.parseInt(row_col[0]);
							col = Integer.parseInt(row_col[1]);
							fileCreator.addDataToBCF(row, col, totalg_a);
						} catch (NumberFormatException nfe) {
							nfe.printStackTrace();
							fileCreator.addDataToBCF(row, col,
									"Enter manually;");
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
		}
		finalDeprecation = finalDeprecation
				* (signs_for_regexes.get(currentPattern));
		String[] row_col = (fields_for_regexes.get(currentPattern)).split(",");
		int row = Integer.parseInt(row_col[0]);
		int col = Integer.parseInt(row_col[1]);
		fileCreator.addDataToBCF(row, col, finalDeprecation);
	}

	/*
	 * This one handles the data in the Balance Sheet Area
	 */

	public void matchBSA(ExcelCreator fileCreator, String word) {
		RegexLoader myLoader = new RegexLoader();
		List<Pattern> allRegexes = myLoader
				.loadRegexes(FileConstants.BSAREGEX_FILE);
		List<Integer> allSigns = myLoader
				.loadSigns(FileConstants.BSAEXCEL_SIGNS);
		String entities = myLoader
				.loadEntities(FileConstants.BSAEXCEL_ENTITIES);

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
							// intermediate = (intermediate).replace("-", "");
							intermediate = (intermediate).replace(",", "");
							intermediate = (intermediate).replace(".", "");
							intermediate = (intermediate).replace("_", "");
							intermediate = (intermediate).replace("l", "1");
							intermediate = (intermediate).replace("O", "0");
							intermediate = (intermediate).replace(")", "");
							intermediate = (intermediate).replace("(", "");
							if (intermediate != null && !intermediate.isEmpty()) {
								int row = 0;
								int col = 0;
								long data = 0;
								try {
									data = Long.parseLong(intermediate);
									data = data
											* signs_for_regexes
													.get(currentPattern);
								} catch (NumberFormatException nfe) {
									String[] row_col = (fields_for_regexes
											.get(currentPattern)).split(",");
									row = Integer.parseInt(row_col[0]);
									col = Integer.parseInt(row_col[1]);
									fileCreator.addDataToBSA(row, col,
											"Enter manually;");
								}
								String[] row_col = (fields_for_regexes
										.get(currentPattern)).split(",");
								row = Integer.parseInt(row_col[0]);
								col = Integer.parseInt(row_col[1]);
								fileCreator.addDataToBSA(row, col, data);
							}
						}
					}
				}
			}
		}
	}
}
