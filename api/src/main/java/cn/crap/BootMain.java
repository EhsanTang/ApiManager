package cn.crap;

import cn.crap.config.FilterConfig;
import cn.crap.config.InterceptorConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author James
 */
@SpringBootApplication
@MapperScan(basePackages = {"cn.crap.dao"})
@Import(value = {FilterConfig.class, InterceptorConfig.class})
public class BootMain {

    public static void main(String[] args) {
        SpringApplication.run(BootMain.class, args);
    }
}
