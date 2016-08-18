package cn.crap.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import cn.crap.framework.SpringContextHolder;
import cn.crap.inter.service.ICacheService;
import cn.crap.service.CacheService;

public class Html2Pdf {
	/**
	 * 
	 * @param file
	 * @throws Exception
	 */

	public static String createPdf(HttpServletRequest request, String interFaceId) throws Exception {
		try {
			String destDir = Tools.getServicePath(request) + "resources/upload/pdf";
			// 根据当前时间获取文件夹名（0/1）
			long newFile = ( System.currentTimeMillis()/(1000 * 60 * 60 * 24) ) % 2;
			
			// 创建文件夹
			if (!new File(destDir + "/" + newFile).exists()) {
				new File(destDir + "/" + newFile).mkdirs();
			}
			
			long oldFile = (newFile + 1) % 2;
			// 删除昨天的文件夹
			if (new File(destDir + "/" + oldFile).exists()) {
				deleteDir(new File(destDir + "/" + oldFile));
			}
			
			destDir += "/"+newFile+"/interface" + Tools.getChar(32) + ".pdf";
			
			Document document = new Document();
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(destDir));
			document.open();
			ICacheService cacheService = SpringContextHolder.getBean("cacheService", CacheService.class);
			XMLWorkerHelper.getInstance().parseXHtml(writer, document, HttpPostGet.GetString(
					cacheService.getSetting(Const.SETTING_DOMAIN).getValue() + "/front/interface/detail/pdf.do?id=" + interFaceId),
					Charset.forName("UTF-8"), new ChinaFont());
			document.close();
			return destDir;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	
	private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }

}
