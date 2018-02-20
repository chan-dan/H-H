package Tooled;

public class CallerClass {

	public static void main(String[] args){
		StringExtractor extractor=new StringExtractor();
		String readData=extractor.readExcelFiles(FileConstants.INPUT_DIR);				
		ExcelCreator excelCreator= new ExcelCreator(FileConstants.OUTPUT_FILENAME);
		extractor.matchBCF(excelCreator, readData);						/*This part is to read the cash flow*/
		extractor.matchBSA(excelCreator, readData);						/*This part is to read the excel file with data of balance sheet */
		excelCreator.evaluate(FileConstants.OUTPUT_FILENAME);
		excelCreator.closeStreams();
	}
}
