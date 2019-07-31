package cn.crap.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class WordUtils {
    private static final String templateFolder = WordUtils.class.getResource("/").getPath();
    private static Configuration configuration = null;

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

    private static File createDoc(Map<?, ?> dataMap, Template template) throws Exception {
        String destDir = Tools.getServicePath() + "META-INF/resources/resources/download";
        destDir += "/word_" + System.currentTimeMillis() + Tools.getChar(20) + ".doc";
        File f = new File(destDir);
        Template t = template;
        Writer w = null;
        try {
            // 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
            w = new OutputStreamWriter(new FileOutputStream(f), StandardCharsets.UTF_8);
            t.process(dataMap, w);
        } finally {
            if (t != null) {
                try {
                    w.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return f;
    }
}