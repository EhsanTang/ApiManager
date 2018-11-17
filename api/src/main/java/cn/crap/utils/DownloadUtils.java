package cn.crap.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Map;

public class DownloadUtils {
    public static void downloadWord(HttpServletResponse response, File file, String name, boolean isPdf) throws Exception {
        InputStream fin = null;
        ServletOutputStream out = null;
        BufferedInputStream bin = null;
        byte[] buffer = new byte[1024];
        try {
            name = name.trim();
            fin = new FileInputStream(file);
            response.setCharacterEncoding("utf-8");
            response.setContentType(isPdf ? "application/pdf" : "application/msword");

            String fileName = "CrapApi_" + name + (isPdf ? ".pdf" : ".doc");
            response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode(fileName, "UTF-8"))));

            out = response.getOutputStream();
            bin = new BufferedInputStream(new FileInputStream(file));

            // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while ((bytesToRead = bin.read(buffer)) != -1){
                out.write(buffer, 0, bytesToRead);
            }
            out.flush();
        } finally {
            if (fin != null){
                fin.close();
            }
            if (out != null){
                out.close();
            }
            if (bin != null){
                bin.close();
            }
            if (file != null) {
                file.delete();
            }
        }
    }
}