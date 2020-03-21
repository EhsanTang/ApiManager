package cn.crap;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author James
 */
@SpringBootApplication
@EnableSwagger2
@MapperScan(basePackages = {"cn.crap.dao"})
public class BootMain {

    public static void main(String[] args) {
        SpringApplication.run(BootMain.class, args);
    }
}
