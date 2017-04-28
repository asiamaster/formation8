package com.dili;

import com.dili.utils.base.MyMapper;
import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 由MyBatis Generator工具自动生成
 */
//@EnableDiscoveryClient
//@EnableFeignClients
//@EnableHystrix
//@EnableHystrixDashboard
//@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
//@EnableScheduling
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = {ThymeleafAutoConfiguration.class, VelocityAutoConfiguration.class})
@MapperScan(basePackages = "com.dili.formation8.dao", markerInterface = MyMapper.class)
//@ImportResource(locations = "classpath:applicationContext.xml")
@ComponentScan(basePackages={"com.dili.utils","com.dili.formation8"})
//@EnableEncryptableProperties
//@PropertySource(name="EncryptedProperties", value = "classpath:security.properties")
@EncryptablePropertySource(name = "EncryptedProperties", value = "classpath:security.properties")
//@ServletComponentScan
/**
 * 除了内嵌容器的部署模式，Spring Boot也支持将应用部署至已有的Tomcat容器, 或JBoss, WebLogic等传统Java EE应用服务器。
 * 以Maven为例，首先需要将<packaging>从jar改成war，然后取消spring-boot-maven-plugin，然后修改Application.java
 * 继承SpringBootServletInitializer
 */
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
    }


}
