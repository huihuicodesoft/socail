package cn.com.wh.ring.config;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

/**
 * Created by Hui on 2017/6/15.
 *
 * web容器配置
 */

public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

    /**
     * 运行在spring容器 时期
     * @return
     */
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{
                WebContextConfig.class,
                MybatisConfig.class,
                DruidConfig.class,
                SwaggerConfig.class};
    }

    /**
     * 运行在servlet context 时期
     * @return
     */
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[]{
                WebMvcConfig.class
        };
    }

    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    protected Filter[] getServletFilters() {
        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);

        DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
        delegatingFilterProxy.setTargetFilterLifecycle(true);
        delegatingFilterProxy.setTargetBeanName("shiroFilterFactoryBean");
        return new Filter[]{characterEncodingFilter, delegatingFilterProxy};
    }
}
