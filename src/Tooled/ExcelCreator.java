package Tooled;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCreator {

	FileInputStream input_document =null;
	 Workbook my_xls_workbook;
	 Sheet my_worksheet ;
	 FileOutputStream output_file;
//	 Cell cell = null;
	public ExcelCreator(String fileName){
		try {
			createClone(fileName);															/*Creates a clone file of the provided template, it doesn't have to be copied over and over again 
			 																				  To change the name of the file, change it in the FileConstants class.			*/
			 input_document = new FileInputStream(new File(FileConstants.OUTPUT_CLONE));    /*Reads stream from the cloned file */
			 my_xls_workbook = WorkbookFactory.create(input_document);
			 output_file =new FileOutputStream(new File(FileConstants.OUTPUT_CLONE));		/*Writes data to the cloned file */
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			System.out.println("Problem generating output file");
			e.printStackTrace();
		}
	}
	
	/*public void addData(String filename,int row,int cellNo, String data){
           try {
//        	  sheet = workbook.getSheetAt(2);
        	   Row rowhead = sheet.getRow(row);
        	   rowhead.getCell(cellNo).setCellValue(data);
        	FileOutputStream outputFile=new FileOutputStream(filename);
			workbook.write(outputFile);
			outputFile.close();
           System.out.println("data added");
           } catch (IOException e) {
        	   e.printStackTrace();
           }
	}*/
	public void addDataToBCF(int row,int cellNo, long data){
		 //			 FileInputStream input_document = new FileInputStream(new File(filename));
		             //Access the workbook
		//			 Workbook my_xls_workbook = WorkbookFactory.create(input_document); 7
		             //Access the worksheet, so that we can update / modify it.
		//             Sheet my_worksheet = my_xls_workbook.getSheet("Business Cash Flow"); 
		//             Cell cell = null; 
		             // Access the cell first to update the value
						Cell cell=null;
		             my_worksheet = my_xls_workbook.getSheetAt(2);
		             cell = my_worksheet.getRow(row).getCell(cellNo);
		             // Get current value and then add 5 to it 
		             cell.setCellValue(data);
		             
		//             input_document.close();
		             //Open FileOutputStream to write updates
		//             FileOutputStream output_file =new FileOutputStream(new File(filename));
		             //write changes
		             //close the stream
		//             output_file.close();
		
		//			 sheet = workbook.getSheetAt(2);
		//        	 Row rowhead = sheet.getRow(row);
		//        	 rowhead.getCell(cellNo).setCellValue(data);
		//        	 FileOutputStream outputFile=new FileOutputStream(filename);
		//			workbook.write(outputFile);
		//			outputFile.close();
		           System.out.println("data added to sheet Business Cash Flow");
	}
	
	
	public void addDataToBCF(int row,int cellNo, String data){
		 
						Cell cell=null;
		             my_worksheet = my_xls_workbook.getSheetAt(2);
		             cell = my_worksheet.getRow(row).getCell(cellNo);
		             cell.setCellValue(data);
		           System.out.println("data added to sheet Business Cash Flow");
	}
	
	
	public void addDataToBSA(int row,int cellNo, long data){
			
		Cell cell=null;
		 my_worksheet = my_xls_workbook.getSheetAt(3);
         cell = my_worksheet.getRow(row).getCell(cellNo);
         // Get current value and then add 5 to it 
         cell.setCellValue(data);
       System.out.println("data added to sheet BSA");
		
		
	}
	
	public void addDataToBSA(int row,int cellNo, String data){
		
		Cell cell=null;
		 my_worksheet = my_xls_workbook.getSheetAt(3);
         cell = my_worksheet.getRow(row).getCell(cellNo);
         // Get current value and then add 5 to it 
         cell.setCellValue(data);
       System.out.println("data added to sheet BSA");
		
		
	}
	
	
	/*public int getRow(Pattern currentPattern){
		String details=FileConstants.Excel_Heads.get(currentPattern);
		String[] rowcol=details.split(",");
		int row=Integer.parseInt(rowcol[0]);
		return row;
	}
	
	public int getCol(Pattern currentPattern){
		String details=FileConstants.Excel_Heads.get(currentPattern);
		String[] rowcol=details.split(",");
		int col=Integer.parseInt(rowcol[1]);
		return col;
	}
	
	public int getBSARow(Pattern currentPattern){
		String details=FileConstants.BSAExcel_Heads.get(currentPattern);
		String[] rowcol=details.split(",");
		int row=Integer.parseInt(rowcol[0]);
		return row;
	}
	
	public int getBSACol(Pattern currentPattern){
		String details=FileConstants.BSAExcel_Heads.get(currentPattern);
		String[] rowcol=details.split(",");
		int col=Integer.parseInt(rowcol[1]);
		return col;
	}*/
	
	public void closeStreams(){
		try {
			my_xls_workbook.write(output_file);
			input_document.close();
			output_file.flush();
			output_file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		}
	
	public void createClone(String inputPath){
		try {
			FileUtils.copyFile(new File(inputPath), new File(FileConstants.OUTPUT_CLONE));
		} catch (IOException e) {
			System.out.println("Some problem cloning");
			e.printStackTrace();
		}
	}
	
	/*calculate all the formulas*/
	public void evaluate(String outputFile){
		FileInputStream fis;
		//			fis = new FileInputStream(new File(outputFile));
		//			Workbook wb = new XSSFWorkbook(fis); //or new XSSFWorkbook("/somepath/test.xls")
					FormulaEvaluator evaluator = my_xls_workbook.getCreationHelper().createFormulaEvaluator();
					for (Sheet sheet : my_xls_workbook) {
						for (Row r : sheet) {
							for (Cell c : r) {
								if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
									evaluator.evaluateFormulaCell(c);
								}
							}
						}
					}
		        
		  
		
	}
	}
