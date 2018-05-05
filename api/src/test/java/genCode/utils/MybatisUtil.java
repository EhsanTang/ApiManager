package genCode.utils;

import java.util.ArrayList;
import java.io.File;
import java.util.List;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisUtil {
//    public static void main(String[] args)
//            throws Exception {
//
//        new MybatisUtil().generateMain();
//    }

    public void generateMain() throws Exception {
        String f = MybatisUtil.class.getClassLoader().getResource("").getPath();
        generate(f);
    }

    public static void generate(String f)
            throws Exception {
        List warnings = new ArrayList();
        boolean overwrite = true;
        File configFile = new File(f);
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);

        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(new ProgressCallback() {
            public void startTask(String arg0) {
            }

            public void saveStarted(int arg0) {
            }

            public void introspectionStarted(int arg0) {
            }

            public void generationStarted(int arg0) {
            }

            public void done() {
            }

            public void checkCancel()
                    throws InterruptedException {
            }
        });
    }
}