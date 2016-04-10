package cn.crap.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;
import javax.servlet.http.HttpServletRequest;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Html2Pdf {
	/**
	 * Creates a PDF with the words "Hello World"
	 * 
	 * @param file
	 * @throws Exception
	 */
	private static int i = 0;

	public static String  createPdf(HttpServletRequest request, String interFaceId) throws Exception {
		try {
			String destDir = Tools.getServicePath(request) + "resources/upload/pdf";
			if (!new File(destDir).exists()) {
				new File(destDir).mkdirs();
			}
			synchronized (Html2Pdf.class) {
				destDir += "/temp" + i % 17 + ".pdf";// 同时允许17个人下载
				i++;
				i=i%17;
			}
			
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destDir));
			document.open();
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, HttpPostGet.GetString(
					Cache.getSetting(Const.SETTING_DOMAIN).getValue() + "/interface/detail/pdf.do?id=" + interFaceId),
					Charset.forName("UTF-8"));
			document.close();
			return destDir;
		} catch (Exception e) {
			e.printStackTrace();
			String pdfUrl = Cache.getSetting(Const.SETTING_DOMAIN).getValue() + "/interface/detail/pdf.do?id="
					+ interFaceId;
			System.out.println("pdfUrl:" + pdfUrl);
			String pdfContent = HttpPostGet.Get(
					Cache.getSetting(Const.SETTING_DOMAIN).getValue() + "/interface/detail/pdf.do?id=" + interFaceId);
			System.out.println("pdfContent:" + pdfContent);
			throw e;
		}
	}

}
