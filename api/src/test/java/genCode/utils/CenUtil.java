package genCode.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CenUtil {
  private static String packagePath = "genCode/genTemple";

  public static String processServiceImp(String tableName) throws FileNotFoundException, IOException {
	  StringBuilder sb = readTemple("ServiceImp");
	  return sb.toString().replaceAll("_DEMAIN_", initcap(tableName)).replaceAll("_TABLEID_", tableName.toUpperCase());
  }

	/**
	 * 根据模板自动生成adapter
	 * @param tableName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String processAdapter(String tableName) throws FileNotFoundException, IOException {
		StringBuilder sb = readTemple("Adapter");
		String result = sb.toString().replaceAll("_DEMAIN_", initcap(tableName));
		return String.format(result, processAllToDto(), processAllToModel());
	}

	/**
	 * 根据模板自动生成dto
	 * @param tableName
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String processDto(String tableName) throws FileNotFoundException, IOException {
		StringBuilder sb = readTemple("Dto");
		StringBuilder temple = new StringBuilder();
		for (int i = 0; i < GenMain.colnames.length; i++) {
			temple.append("\tprivate " + sqlType2JavaType(GenMain.colTypes[i]) + " " + GenMain.colnames[i] + ";\r\n");
		}
		String fields = temple.toString();
		temple = new StringBuilder();

		processAllMethod(temple);
		return String.format(sb.toString(), initcap(tableName), fields, temple.toString());
	}


	/**
	 * 生成所有toDto字段
	 *
	 */
	public static String processAllToDto() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < GenMain.colnames.length; i++) {
			sb.append("dto.set" +initcap(GenMain.colnames[i])+ "(model.get"+ initcap(GenMain.colnames[i]) +"());\r\n\t\t");
		}
		return sb.toString();
	}

	/**
	 * 生成所有toModel字段
	 *
	 */
	public static String processAllToModel() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < GenMain.colnames.length; i++) {
			sb.append("model.set" +initcap(GenMain.colnames[i])+ "(dto.get"+ initcap(GenMain.colnames[i]) +"());\r\n\t\t");
		}
		return sb.toString();
	}

  /**
	 * 生成所有的方法
	 * 
	 * @param sb
	 */
	public static void processAllMethod(StringBuilder sb) {
		for (int i = 0; i < GenMain.colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(GenMain.colnames[i]) + "(" + sqlType2JavaType(GenMain.colTypes[i]) + " "
					+ GenMain.colnames[i] + "){\r\n");
			sb.append("\t\tthis." + GenMain.colnames[i] + "=" + GenMain.colnames[i] + ";\r\n");
			sb.append("\t}\r\n");

			sb.append("\tpublic " + sqlType2JavaType(GenMain.colTypes[i]) + " get" + initcap(GenMain.colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + GenMain.colnames[i] + ";\r\n");
			sb.append("\t}\r\n\r\n");
		}
	}
	
	
  
  
  private static StringBuilder readTemple(String fileName) throws FileNotFoundException, IOException {
	String path = System.getProperty("user.dir") + "/src/test/java/" + packagePath.replaceAll("\\.", "/");
	return readFile(path + "/" + fileName);
  }


	public static StringBuilder readFile(String fileName) throws FileNotFoundException, IOException {
		FileReader fr = new FileReader(fileName);
		BufferedReader br = new BufferedReader(fr);
		String s;
		StringBuilder sb = new StringBuilder();
		while ((s = br.readLine()) != null) {
			sb.append(s+"\r\n");
		}
		fr.close();
		br.close();
		return sb;
	}
  
	
	private static String sqlType2MybatisType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "BIT";
		} else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")) {
			return "INTEGER";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "BIGINT";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "FLOAT";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")) {
			return "DECIMAL";
		}else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")) {
			return "VARCHAR";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "DATE";
		}else if (sqlType.equalsIgnoreCase("text")) {
			return "TEXT";
		}else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "TINYINT";
		}else if (sqlType.equalsIgnoreCase("TIMESTAMP")){
			return "VARCHAR";
		}
		return "";
	}
	public static String sqlType2JavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("bit")) {
			return "Boolean";
		} else if (sqlType.equalsIgnoreCase("tinyint")) {
			return "Byte";
		} else if (sqlType.equalsIgnoreCase("smallint")) {
			return "Short";
		} else if (sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer")) {
			return "Integer";
		} else if (sqlType.equalsIgnoreCase("bigint")) {
			return "Long";
		} else if (sqlType.equalsIgnoreCase("float")) {
			return "Float";
		} else if (sqlType.equalsIgnoreCase("decimal") || sqlType.equalsIgnoreCase("numeric")
				|| sqlType.equalsIgnoreCase("real")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("money") || sqlType.equalsIgnoreCase("smallmoney")) {
			return "Double";
		} else if (sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")
				|| sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("nchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("datetime")) {
			return "Date";
		}else if (sqlType.equalsIgnoreCase("TIMESTAMP")){
			return "Date";
		}

		else if (sqlType.equalsIgnoreCase("image")) {
			return "Blob";
		} else if (sqlType.equalsIgnoreCase("text")) {
			return "Clob";
		}
		return null;
	}
	/**
	 * 把输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String initcap(String str) {
		str=str.replace("customer_", "");
		str = getCamelStr(str);
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}
	
	// 例：user_name --> userName
	public static String getCamelStr(String s) {
			//s = s.toLowerCase();
			while (s.indexOf("_") > 0) {
				int index = s.indexOf("_");
				s = s.substring(0, index) + s.substring(index + 1, index + 2).toUpperCase() + s.substring(index + 2);
			}
			return s;
	}
	
	public static void writeStringToFile(File file, String content) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		writer.write(content);
		writer.close();
		
	}
}
