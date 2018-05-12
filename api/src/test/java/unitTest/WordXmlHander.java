package unitTest;

import cn.crap.utils.Tools;
import cn.crap.utils.WordUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 使用英文占位符导出，xml中会被分割成多个
 * 1. 编辑resources下的interfaceTemplete.docx
 * 2. 导出为interfaceTemplete.xml
 * 3. 使用程序替换xml中的汉字
 * 4. 处理循环
 *
 * 注意：html问题
 */
public class WordXmlHander {
    private static Map<String, String> placeHolder = new HashMap<>();
    private static String templateFileName = "interfaceTempleteSrc";
    private static final String templateFolder = WordUtils.class.getResource("/").getPath().replaceAll("test-classes", "classes");


    static {
        placeHolder.put("接口名", "\\$\\{interfacePDFDto.model.interfaceName\\}");
        placeHolder.put("接口备注", "\\$\\{interfacePDFDto.model.remark\\}");
        placeHolder.put("接口地址", "\\$\\{interfacePDFDto.model.fullUrl\\}");
        placeHolder.put("接口版本", "\\$\\{interfacePDFDto.model.version\\}");
        placeHolder.put("接口正确地址", "\\$\\{interfacePDFDto.trueMockUrl\\}");
        placeHolder.put("接口错误地址", "\\$\\{interfacePDFDto.falseMockUrl\\}");
        placeHolder.put("接口请求方式", "\\$\\{interfacePDFDto.model.method\\}");
    }

    public static void main(String[] args) throws Exception{
        String xmlPath = templateFolder + "/" + templateFileName + ".xml";
        String xmlContent = Tools.readFile(xmlPath);
        Set<Map.Entry<String, String>> entries = placeHolder.entrySet();
        for (Map.Entry<String, String> entry : entries){
            xmlContent = xmlContent.replaceAll(entry.getKey(), entry.getValue());
        }
        Tools.staticize(xmlContent, "/Users/apple/ijworkspace/ApiManager/api/src/main/resources/interfaceTemplete.xml");
    }
}
