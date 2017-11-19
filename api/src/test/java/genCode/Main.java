package genCode;

import genCode.utils.GenMain;
import genCode.utils.MybatisUtil;
import genCode.utils.CenUtil;

import java.io.File;

public class Main {

    /**
     * 自动生成mybatis model、dto、mapper、service、adapter
     * @param args
     */
    public static void main(String args[]) throws Exception{
        String tableName = "projectUser";
        /************* mybatis 自动生成*******************/
        System.out.println("------mybatis-----");
        String f = null;
        try {
            f = Main.class.getResource("/dao-generator.xml").getPath();
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("-----end: mybatis-------"+f);
        MybatisUtil.generate(f);

        /*************** 自定义生成dto、service、adapter********/
        GenMain genMain = new GenMain(tableName);


        /********  mybatis 自动生成的xml中需要删除部分代码，因为id不是自增的***/
        String fileName = System.getProperty("user.dir") + "/src/test/java/genCode/genResult/cn/crap/dao/mybatis/" +CenUtil.initcap(tableName)+ "Mapper.xml";
        String xml = CenUtil.readFile(fileName).toString();
        xml = xml.replaceAll("<selectKey resultType=\"java.lang.String\" keyProperty=\"id\" order=\"BEFORE\" >", "");
        xml = xml.replaceAll("SELECT LAST_INSERT_ID\\(\\)", "");
        xml = xml.replaceAll("</selectKey>", "");

        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }

        CenUtil.writeStringToFile(new File(fileName), xml);

        //



    }
}
