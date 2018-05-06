package ren.ascence.dns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("ren.ascence.dns")
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class Main {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
        
        System.out.println("http://localhost:8080/test");
        System.out.println("http://localhost:8080/index");
        System.out.println("http://localhost:8080/img/jdp.jpg");
    }
}
