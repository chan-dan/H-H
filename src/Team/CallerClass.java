package Team;

public class CallerClass {

	public static void main(String[] args){
		StringExtractor extractor=new StringExtractor();
		String readData=extractor.readParsedFiles(FileConstants.INPUT_DIR);
		ExcelCreator excelCreator= new ExcelCreator(FileConstants.OUTPUT_FILENAME);
		extractor.matchBCF(excelCreator, readData);
	}
}
