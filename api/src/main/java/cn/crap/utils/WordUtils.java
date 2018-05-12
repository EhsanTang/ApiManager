package cn.crap.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URLEncoder;
import java.util.Map;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class WordUtils {
    private static Configuration configuration = null;
    private static final String templateFolder = WordUtils.class.getResource("/").getPath();

    static {
        configuration = new Configuration(Configuration.VERSION_2_3_28);
        configuration.setDefaultEncoding("utf-8");
        try {
            configuration.setDirectoryForTemplateLoading(new File(templateFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void downloadWord(HttpServletResponse response, Map map, String name) throws Exception {
        Template freemarkerTemplate = configuration.getTemplate("interfaceTemplete.xml");
        File file = createDoc(map, freemarkerTemplate);
        DownloadUtils.downloadWord(response, file, name, false);
    }

    private static File createDoc(Map<?, ?> dataMap, Template template) throws Exception{
        String destDir = Tools.getServicePath() + "resources/download";
        destDir += "/word_" + System.currentTimeMillis() + Tools.getChar(20) + ".doc";
        File f = new File(destDir);
        Template t = template;
        Writer w = null;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
            t.process(dataMap, w);
        }finally {
            if (t != null){
                try {
                    w.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return f;
    }
}