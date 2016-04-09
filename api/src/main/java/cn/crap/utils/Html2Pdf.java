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
     * @param file
     * @throws Exception 
     */
	private static int i=0;
    public static String createPdf(HttpServletRequest request,String interFaceId) throws Exception {
    	String destDir = Tools.getServicePath(request)+"resources/upload/pdf";
    	if(!new File(destDir).exists()){  
    		new File(destDir).mkdirs();  
	     } 
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destDir+"/temp"+i%17+".pdf"));
        i++;
        document.open();
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, HttpPostGet.GetString(Cache.getSetting(Const.SETTING_DOMAIN).getValue()+
        		"/interface/detail/pdf.do?id="+interFaceId), Charset.forName("UTF-8"));
        document.close();
        return destDir+"/temp.pdf";
    }
  
}

