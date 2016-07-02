package cn.crap.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class GetTextFromFile {
	public static String getText(String filePath) throws Exception {
		if (!filePath.startsWith("http://") && !filePath.startsWith("https://")) {
			filePath = Tools.getServicePath(Tools.getRequest()) + filePath;
		}
		if (filePath.toUpperCase().endsWith(".PDF")) {
			return getTextFromPDF(filePath);
		} else if (filePath.toUpperCase().endsWith(".DOCX") || filePath.toUpperCase().endsWith(".DOC")) {
			return getTextFromWord(filePath);
		} else if (filePath.toUpperCase().endsWith(".TXT")) {
			return getTextFromTxt(filePath);
		}
		return "";
	}

	private static String getTextFromPDF(String pdfFilePath) throws IOException {
		String result = null;
		FileInputStream is = null;
		PDDocument document = null;
		try {
			is = new FileInputStream(pdfFilePath);
			document = PDDocument.load(is);
			PDFTextStripper stripper = new PDFTextStripper();
			result = stripper.getText(document);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (document != null) {
				try {
					document.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public static String getTextFromWord(String worldFilePth) throws Exception {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(worldFilePth);
			HWPFDocument doc = new HWPFDocument(fis);
			Range rang = doc.getRange();
			return rang.text();
		} catch (Exception e) {
			throw e;
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	public static String getTextFromTxt(String worldFilePth) throws Exception {
		StringBuilder result = new StringBuilder();
		FileReader fis = null;
		BufferedReader br = null;
		try {
			fis = new FileReader(worldFilePth);
			br = new BufferedReader(fis);// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result.append(s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result.toString();

	}
}
