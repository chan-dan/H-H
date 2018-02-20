package Tooled;


class FileConstants {
	static String datapath = "./tessdata";
	static String INPUT_DIR = "./input/";
	static String OUTPUT_FILENAME = "./output/H&H Main CF Template 2-25-2016.xlsx";
	static String OUTPUT_CLONE = "./output/Result.xlsx";
	static String BCFREGEX_FILE = "./utils/BCFRegexes.txt";
	static String BCFEXCEL_ENTITIES = "./utils/BCFEntities.txt";
	static String BCFEXCEL_SIGNS = "./utils/BCFSigns.txt";
	static String BSAREGEX_FILE = "./utils/BSARegexes.txt";
	static String BSAEXCEL_ENTITIES = "./utils/BSAEntities.txt";
	static String BSAEXCEL_SIGNS = "./utils/BSASigns.txt";
	static String depreciationRegex = "[\\s]Depr.*?14[\\t]+([\\d\\.\\,]+)[\\t]+[\\r\\n]@@[\\s]Section.*?11[\\t]+([\\d\\.\\,]+)[\\t]+[\\r\\n]";
	static String g_a_regex = "Total[a-zA-Z0-9\\(\\)\\t\\-\\. ]+20[\\t]+([\\d\\.\\,]+)\\n@@[\\s]Dep[a-zA-Z0-9\\(\\)\\t\\- ]+14[\\t]+([\\d\\.\\,]+)\\n@@[\\s]Sec[a-zA-Z0-9\\(\\)\\t ]+11[\\t]+([\\d\\.\\,]+)\\n@@[a-zA-Z0-9\\t\\. ]+12[ ]?a[\\t]+([\\d\\.\\,]+)\\n@@[a-zA-Z0-9\\(\\)\\t ]+16[ ]?c[\\t]+([\\d\\.\\,]+)\\n@@[\\s]In[a-zA-Z0-9\\t ]+13[\\t]+([\\d\\.\\,]+)\\n@@[\\s]T[a-zA-Z0-9\\t ]+12[\\t]+([\\d\\.\\,]+)\\n@@[a-zA-Z0-9\\(\\)\\-\\—\\.\\t ]+7[\\t]+([\\d\\.\\,]+)\\n@@[a-zA-Z\\(\\)\\.\\,\\t ]+8[\\t]+([\\d\\.\\,]+)\\n@@R[a-zA-Z\\.\\,\\t ]+9[\\t]+([\\d\\.\\,]+)\\n@@[a-zA-Z\\.\\,\\t ]+10[\\t]+([\\d\\.\\,]+)\\n@@[a-zA-Z\\.\\,\\t ]+11[\\t]+([\\d\\.\\,]+)\\n@@[a-zA-Z\\.\\,\\t ]+16[\\t]+([\\d\\.\\,]+)\\n";
	static String total_deductions = "Total[a-zA-Z0-9\\(\\)\\t\\-\\. ]+20[\\t]+([\\d\\.\\,]+)\\n";
	static String capital_gain = "[\\s]Net[a-zA-Z0-9\\.\\,\\t\\(\\)\\- ]+4[\\t]+([\\d\\.\\,\\(\\)]+)[\\r\\n]@@[\\s]Net[a-zA-Z0-9\\-\\(\\)\\t ]+8[ ]?[a][\\t]+([\\d\\.\\,]+)[\\r\\n]@@[\\s]Net[a-zA-Z0-9\\-\\(\\)\\t ]+9[\\t]+([\\d\\.\\,]+)[\\r\\n]";

}
