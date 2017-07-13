package cn.com.wh.ring.config;

import cn.com.wh.ring.Application;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by Hui on 2017/6/15.
 *
 * 通用的spring容器配置
 */
@Configuration
@ComponentScan(basePackageClasses = Application.class)
public class WebContextConfig{
    @Bean
    public static PropertyPlaceholderConfigurer getPropertyPlaceholderConfigurer(){
        PropertyPlaceholderConfigurer placeholderConfigurer = new PropertyPlaceholderConfigurer();
        placeholderConfigurer.setLocation(new ClassPathResource("/config.properties"));
        return placeholderConfigurer;
    }
}
