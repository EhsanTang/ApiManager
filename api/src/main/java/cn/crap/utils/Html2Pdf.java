package cn.crap.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

public class Html2Pdf {
    public static final String DEST = "D:/hero.pdf";
  
    /**
     * Creates a PDF with the words "Hello World"
     * @param file
     * @throws Exception 
     */
    public void createPdf(String file) throws Exception {
        // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, HttpPostGet.GetString("http://localhost:8080/CrapApi/interface/detail/pdf.do?id=ddd690f3-b87c-41ed-825d-0c39c3d413ff"), Charset.forName("UTF-8"));
        // step 5
        document.close();
    }
  
    /**
     * Main method
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        new Html2Pdf().createPdf(DEST);
    }
}

