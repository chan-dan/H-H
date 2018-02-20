package Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

class FileConstants {
	static String datapath = "./tessdata";
	static String INPUT_DIR = "./input/";
	static String OUTPUT_FILENAME = "./output/H&H Main CF Template 2-25-2016.xlsx";
	static String BCFREGEX_FILE = "./utils/BCFRegexes.txt";
	static String BCFEXCEL_ENTITIES = "./utils/BCFEntities.txt";
	static String BCFEXCEL_SIGNS = "./utils/BCFSigns.txt";
	static String BSAREGEX_FILE = "./utils/BSARegexes.txt";
	static String BSAEXCEL_ENTITIES = "./utils/BSAEntities.txt";
	static String BSAEXCEL_SIGNS = "./utils/BSASigns.txt";
//	static String DIR_TO_PARSED_FILES="./input/";
	static List<Pattern> allPatterns = new ArrayList<Pattern>();
	static List<Integer> bcfsigns = new ArrayList<Integer>();
	static Map<Pattern, String> Excel_Heads = new HashMap<Pattern, String>();
	static Map<Pattern, Integer> Sign_Heads = new HashMap<Pattern, Integer>();
	static String depreciationRegex = "Depreciation[a-zA-Z0-9\\(\\)\\t\\- ]+14[\\t]+([\\d\\.\\,]+)\\r\\n@@Section\\s+[a-zA-Z0-9\\(\\)\\t ]+11[\\t]+([\\d\\.\\,]+)\\r\\n";
	static String g_a_regex = "Total[a-zA-Z0-9\\(\\)\\t\\-\\. ]+20[\\t]+([\\d\\.\\,]+)\\r\\n@@Depreciation[a-zA-Z0-9\\(\\)\\t\\- ]+14[\\t]+([\\d\\.\\,]+)\\r\\n@@Section\\s+[a-zA-Z0-9\\(\\)\\t ]+11[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z0-9\\t\\. ]+12[ ]?a[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z0-9\\(\\)\\t ]+16[ ]?c[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z0-9\\t ]+13[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z0-9\\t ]+12[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z0-9\\(\\)\\-\\—\\.\\t ]+7[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z\\(\\)\\.\\,\\t ]+8[\\t]+([\\d\\.\\,]+)\\r\\n@@Re[a-zA-Z\\.\\,\\t ]+9[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z\\.\\,\\t ]+10[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z\\.\\,\\t ]+11[\\t]+([\\d\\.\\,]+)\\r\\n@@[a-zA-Z\\.\\,\\t ]+16[\\t]+([\\d\\.\\,]+)\\r\\n";
	static String total_deductions = "20\\r\\n[\\w\\s\\—\\-\\~\\.)\\(]+[\\.]+?\\r\\n[\\w\\s\\.]+?[\\>]?\\r\\n20\\r\\n[\\—\\-\\~]?([\\w\\,\\s]+)[\\.]?\\r\\n";
	static List<Pattern> BSAPatterns = new ArrayList<Pattern>();
	static List<Integer> BSAsigns = new ArrayList<Integer>();
	static Map<Pattern, String> BSAExcel_Heads = new HashMap<Pattern, String>();
	static Map<Pattern, Integer> BSASign_Heads = new HashMap<Pattern, Integer>();

}
