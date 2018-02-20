package Team;

import static net.sourceforge.tess4j.ITessAPI.TRUE;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import net.sourceforge.tess4j.ITessAPI.ETEXT_DESC;
import net.sourceforge.tess4j.ITessAPI.TessBaseAPI;
import net.sourceforge.tess4j.ITessAPI.TessPageIterator;
import net.sourceforge.tess4j.ITessAPI.TessPageSegMode;
import net.sourceforge.tess4j.ITessAPI.TessResultIterator;
import net.sourceforge.tess4j.TessAPI1;
import net.sourceforge.tess4j.util.ImageIOHelper;
import net.sourceforge.tess4j.util.PdfUtilities;

import com.sun.jna.Pointer;

public class Tesseract2Test {
	static int finalDeprecation=0;
	static int totalg_a=0;
	static List<String> takeOutCharacters=new ArrayList<String>();
	static String pngConstant="png";
	public static void main(String[] args) {
		cleanOutput(FileConstants.INPUT_DIR);
		takeOutCharacters.add(",");
		takeOutCharacters.add(" ");
		takeOutCharacters.add("_");
		takeOutCharacters.add(".");
		String outputFile= FileConstants.OUTPUT_FILENAME;
		String collectiveOutputFile=FileConstants.OUTPUT_FILENAME;
		ExcelCreator ec=new ExcelCreator(outputFile);		/*Create a stream for the template excel file */
		MyReader myReader=new MyReader();
		myReader.loadEmUp(FileConstants.BCFREGEX_FILE);			/*load up the regexes to match*/
		String entities=myReader.loadBCFEntities(FileConstants.BCFEXCEL_ENTITIES); 				/* load the row and cell details */
		myReader.loadBCFSigns(FileConstants.BCFEXCEL_SIGNS);
		myReader.createMap(entities);
		myReader.createMap(FileConstants.bcfsigns);
		myReader.loadBSARegexesUp(FileConstants.BSAREGEX_FILE);
		String BSAEntities=myReader.loadBSAEntities(FileConstants.BSAEXCEL_ENTITIES);
		myReader.loadBSASigns(FileConstants.BSAEXCEL_SIGNS);
		myReader.createBSAMap(BSAEntities);
		myReader.createBSAMap(FileConstants.BSAsigns);
		String language = "eng";
		String word="";
		TessBaseAPI handle = TessAPI1.TessBaseAPICreate();
		int numPages = 0;
		Matcher matcher=null;
		List<File> listOfFiles=FileUtil.readFiles(FileConstants.INPUT_DIR);
		File pdf=null;
		for(int k=0;k<listOfFiles.size();k++){
		pdf = listOfFiles.get(k);
		if(pdf!=null && pdf.isFile() && pdf.exists()){
		numPages = PdfUtilities.getPdfPageCount(pdf);
		File[] tiff = new File[numPages];
		tiff = PdfUtilities.convertPdf2Png(pdf);
		for (int i = 0; i < tiff.length; i++) {
			
			BufferedImage image = null;
			try {
				image = ImageIO.read(new FileInputStream(tiff[i]));
			} catch (FileNotFoundException e) {

				e.printStackTrace();
			} catch (IOException e) {

				e.printStackTrace();
			}

			ByteBuffer buf = ImageIOHelper.convertImageData(image);
			int bpp = image.getColorModel().getPixelSize();
			int bytespp = bpp / 8;
			int bytespl = (int) Math.ceil(image.getWidth() * bpp / 8.0);

			TessAPI1.TessBaseAPIInit3(handle, FileConstants.datapath, language);
			TessAPI1.TessBaseAPISetPageSegMode(handle, TessPageSegMode.PSM_AUTO);
			TessAPI1.TessBaseAPISetImage(handle, buf, image.getWidth(),
					image.getHeight(), bytespp, bytespl);

			ETEXT_DESC monitor = new ETEXT_DESC();
			TessAPI1.TessBaseAPIRecognize(handle, monitor);
			TessResultIterator ri = TessAPI1.TessBaseAPIGetIterator(handle);
			TessPageIterator pi = TessAPI1
					.TessResultIteratorGetPageIterator(ri);
			TessAPI1.TessPageIteratorBegin(pi);
			System.out
					.println("Bounding boxes:\nchar(s) left top right bottom confidence font-attributes");
			int level = TessAPI1.TessPageIteratorLevel.RIL_TEXTLINE;
			int height = image.getHeight();

			do {
				Pointer ptr = TessAPI1.TessResultIteratorGetUTF8Text(ri, level);
//				String middle=ptr.getString(0);
				if(ptr!=null){
				word += ptr.getString(0);
				}
				TessAPI1.TessDeleteText(ptr);
				float confidence = TessAPI1.TessResultIteratorConfidence(ri,
						level);
				IntBuffer leftB = IntBuffer.allocate(1);
				IntBuffer topB = IntBuffer.allocate(1);
				IntBuffer rightB = IntBuffer.allocate(1);
				IntBuffer bottomB = IntBuffer.allocate(1);
				TessAPI1.TessPageIteratorBoundingBox(pi, level, leftB, topB,
						rightB, bottomB);
//				storeConfidence(ptr.getString(0),confidence,confidenceMap);
				// int left = leftB.get();
				// int top = topB.get();
				// int right = rightB.get();
				// int bottom = bottomB.get();
//				System.out.print(word/* +"confidence: " +confidence */);
				// System.out.println();
				// IntBuffer boldB = IntBuffer.allocate(1);
				// IntBuffer italicB = IntBuffer.allocate(1);
				// IntBuffer underlinedB = IntBuffer.allocate(1);
				// IntBuffer monospaceB = IntBuffer.allocate(1);
				// IntBuffer serifB = IntBuffer.allocate(1);
				// IntBuffer smallcapsB = IntBuffer.allocate(1);
				// IntBuffer pointSizeB = IntBuffer.allocate(1);
				// IntBuffer fontIdB = IntBuffer.allocate(1);
				// String fontName =
				// TessAPI1.TessResultIteratorWordFontAttributes(ri, boldB,
				// italicB,
				// underlinedB,
				// monospaceB, serifB, smallcapsB, pointSizeB, fontIdB);
				// boolean bold = boldB.get() == TRUE;
				// boolean italic = italicB.get() == TRUE;
				// boolean underlined = underlinedB.get() == TRUE;
				// boolean monospace = monospaceB.get() == TRUE;
				// boolean serif = serifB.get() == TRUE;
				// boolean smallcaps = smallcapsB.get() == TRUE;
				// int pointSize = pointSizeB.get();
				// int fontId = fontIdB.get();

				// System.out.println(String.format(" font: %s, size: %d, font id: %d, bold: %b,"
				// +
				// " italic: %b, underlined: %b, monospace: %b, serif: %b, smallcap: %b",
				// fontName, pointSize, fontId, bold, italic, underlined,
				// monospace,
				// serif, smallcaps));
			} while (TessAPI1.TessPageIteratorNext(pi, level) == TRUE);
		}
//		matchBCF(outputFile, ec,matcher, word);
//		matchBSA(collectiveOutputFile, ec, word);
		System.out.println(word);
		ec.evaluate(FileConstants.OUTPUT_FILENAME);
		ec.closeStreams();
//		displayConfidence(confidenceMap);
//		tiff=null;
//		pdf=null;
//		numPages=0;
//		handle=null;
//		finalDeprecation=0;
//		totalg_a=0;
		
		}
//		cleanOutput(FileConstants.INPUT_DIR);
	}
		
	}
	private static void cleanOutput(String oUTPUT_FILENAME) {
		File dir= new File(oUTPUT_FILENAME);
		if(dir!=null && dir.isDirectory()){
		for(File file: dir.listFiles()){
			if(file.getName().endsWith(pngConstant)|| file.getName().endsWith(pngConstant.toUpperCase())){
			file.delete();
			}
		}
	  }
	}
	private static void displayConfidence(HashMap<String, Float> confidenceMap) {
		JOptionPane.showMessageDialog(null,"Following values have confidence less than 90");
		for(int i=0;i<confidenceMap.size();i++){
			JOptionPane.showMessageDialog(null, confidenceMap.get(i));

		}
		
	}
	
	
	private static void matchBCF(String outputFile, ExcelCreator fileCreator,
			Matcher matcher, String currentLine) {
		for (int i = 0; i < FileConstants.allPatterns.size(); i++) {
			Pattern currentPattern = FileConstants.allPatterns.get(i);
			matcher = currentPattern.matcher(currentLine);
			if (currentPattern.pattern().equals(
					Pattern.compile(FileConstants.depreciationRegex).pattern())) {
				String[] dividedRegex = (FileConstants.depreciationRegex)
						.split("@@");
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
							intermediate = (intermediate).replace("\r\n", "");
							if (intermediate != null && !intermediate.isEmpty()) {
								try{
								int data = Integer.parseInt(intermediate);
								finalDeprecation += data;
								}catch(NumberFormatException nfe){
									//Do nothing
								}
							}
						}
					}
					finalDeprecation = finalDeprecation
							* (FileConstants.Sign_Heads.get(currentPattern));
					int row = fileCreator.getRow(currentPattern);
					int col = fileCreator.getCol(currentPattern);
//					fileCreator.addDataToBCF(outputFile, row, col,
//							finalDeprecation);
				}

			} else if (currentPattern.pattern().equals(FileConstants.g_a_regex)) {
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
							intermediate = (intermediate).replace("\r\n", "");
							if (intermediate != null && !intermediate.isEmpty()) {
								try {
									int data = Integer.parseInt(intermediate);
									if (p.pattern()
											.equals(Pattern
													.compile(
															FileConstants.total_deductions)
													.pattern())) {
										// Do Nothing
									} else {
										data = -1 * data;
									}
									totalg_a += data;
								} catch (NumberFormatException nfe) {
									// Do Nothing
								}
							}
						}

						totalg_a = totalg_a
								* (FileConstants.Sign_Heads.get(currentPattern));
						int row = fileCreator.getRow(currentPattern);
						int col = fileCreator.getCol(currentPattern);
//						fileCreator
//								.addDataToBCF(outputFile, row, col, totalg_a);

					}
				}
			} else if (matcher.find()) {
				String intermediate = (matcher.group(1));
				if (intermediate != null && !intermediate.isEmpty()) {
					intermediate = intermediate.trim();
					intermediate = (intermediate).replace(" ", "");
					intermediate = (intermediate).replace(",", "");
					intermediate = (intermediate).replace(".", "");
					intermediate = (intermediate).replace("_", "");
					intermediate = (intermediate).replace("l", "1");
					intermediate = (intermediate).replace("O", "0");
					intermediate = (intermediate).replace("\r\n", "");
					if (intermediate != null && !intermediate.isEmpty()) {
						try{
						long data = Long.parseLong(intermediate);
						data = data
								* FileConstants.Sign_Heads.get(currentPattern);
						int row = fileCreator.getRow(currentPattern);
						int col = fileCreator.getCol(currentPattern);
//						fileCreator.addDataToBCF(outputFile, row, col, data);
						}catch(NumberFormatException nfe){
							//Do nothing
						}
					}
				}
			}
		}
	}
	
	private static void matchBSA(String outputFile, ExcelCreator ec, String word) {
		for (int i = 0; i < FileConstants.BSAPatterns.size(); i++) {
			Pattern currentPattern = FileConstants.BSAPatterns.get(i);
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
							intermediate = (intermediate).replace("\r\n", "");
							if (intermediate != null && !intermediate.isEmpty()) {
								try{
								long data = Long.parseLong(intermediate);
								data = data
										* FileConstants.BSASign_Heads
												.get(currentPattern);
								int row = ec.getBSARow(currentPattern);
								int col = ec.getBSACol(currentPattern);
//								ec.addDataToBSA(outputFile, row, col, data);
								}catch(NumberFormatException nfe){
									//Do nothing
								}
							}
						}
					}
				}
			}
		}
	}
	
	
}