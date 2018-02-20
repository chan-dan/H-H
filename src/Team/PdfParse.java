package Team;

import java.io.*;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
 
public class PdfParse {
public static void main(String[] args) {
File imageFile = new File("./input/-myimage-0000.jpg");
Tesseract instance = Tesseract.getInstance();
 
try {
 
String result = instance.doOCR(imageFile);
System.out.println(result);
 
} catch (TesseractException e) {
System.err.println(e.getMessage());
}
}
}