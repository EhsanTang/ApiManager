package unitTest;

import cn.crap.utils.Tools;
import cn.crap.utils.WordUtils;

import java.util.*;

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
    private static List<String> placeHolder = new ArrayList<>();
    private static String templateFileName = "interfaceTempleteSrc";
    private static final String templateFolder = WordUtils.class.getResource("/").getPath().replaceAll("test-classes", "classes");

    static {
        placeHolder.add("interfacePDFDto.model.interfaceName");
        placeHolder.add("interfacePDFDto.model.remark");
        placeHolder.add("interfacePDFDto.model.fullUrl");
        placeHolder.add("interfacePDFDto.model.version");
        placeHolder.add("interfacePDFDto.trueMockUrl");
        placeHolder.add("interfacePDFDto.falseMockUrl");
        placeHolder.add("interfacePDFDto.model.method");
        placeHolder.add("interfacePDFDto.model.contentType");
        placeHolder.add("interfacePDFDto.model.trueExam");
        placeHolder.add("interfacePDFDto.model.falseExam");
        placeHolder.add("interfacePDFDto.model.requestExam");
        placeHolder.add("interfacePDFDto.customParams");
        // header
        placeHolder.add("CAH.name");
        placeHolder.add("CAH.necessary");
        placeHolder.add("CAH.type");
        placeHolder.add("CAH.def");
        placeHolder.add("CAH.remark");

        // error
        placeHolder.add("CAE.errorCode");
        placeHolder.add("CAE.errorMsg");

        // response
        placeHolder.add("CAR.deep");
        placeHolder.add("CAR.name");
        placeHolder.add("CAR.type");
        placeHolder.add("CAR.def");
        placeHolder.add("CAR.necessary");
        placeHolder.add("CAR.remark");

        // param
        placeHolder.add("CAP.name");
        placeHolder.add("CAP.necessary");
        placeHolder.add("CAP.paramPosition");
        placeHolder.add("CAP.type");
        placeHolder.add("CAP.def");
        placeHolder.add("CAP.remark");

        // paramRemark
        placeHolder.add("CAPR.deep");
        placeHolder.add("CAPR.name");
        placeHolder.add("CAPR.type");
        placeHolder.add("CAPR.necessary");
        placeHolder.add("CAPR.remark");

        /**
         * 修改xml，添加循环
         */
        // <#list interfacePDFDto.headers as CAH>
        // <#list interfacePDFDto.formParams as CAP>
        // <#list interfacePDFDto.paramRemarks as CAPR>
        // <#list interfacePDFDto.responseParam as CAR>
        // <#list interfacePDFDto.errors as CAE>
        //
        // <w:tr w:rsidR
        // </w:tr>
        // </#list>
        //
//        <#if condition>
//        <#else>
//        </#if>
    }

    public static void main(String[] args) throws Exception{
        String xmlPath = templateFolder + "/" + templateFileName + ".xml";
        String xmlContent = Tools.readFile(xmlPath);
        for (String str : placeHolder){
            xmlContent = xmlContent.replaceAll(str, "\\$\\{" + str + "\\}");
        }
        Tools.staticize(xmlContent, "/Users/apple/ijworkspace/ApiManager/api/src/main/resources/interfaceTemplete.xml");
    }
}
