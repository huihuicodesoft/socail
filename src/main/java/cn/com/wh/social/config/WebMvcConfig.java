package cn.com.wh.social.config;

import cn.com.wh.social.Application;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Hui on 2017/6/15.
 *
 *
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = Application.class,
        includeFilters = @ComponentScan.Filter(Controller.class))
public class WebMvcConfig extends WebMvcConfigurerAdapter{
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(getMappingJackson2HttpMessageConverter());
    }

    private MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON_UTF8));
        return mappingJackson2HttpMessageConverter;
    }
}
