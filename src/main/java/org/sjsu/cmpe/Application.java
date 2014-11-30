package org.sjsu.cmpe;

import org.group.binary.dao.impl.ZillowPropertyDAOImpl;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
    	ApplicationContext applicationContext = SpringApplication.run(Application.class, args);
        ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        ZillowPropertyDAOImpl zillowDao = (ZillowPropertyDAOImpl)context.getBean("zillowProperyDao");
    }

}