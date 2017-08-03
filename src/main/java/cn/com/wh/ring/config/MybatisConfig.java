package cn.com.wh.ring.config;

import cn.com.wh.ring.Application;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Hui on 2017/6/15.
 *
 * MyBatis在spring容器中的配置
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackageClasses = Application.class, annotationClass = Repository.class)
public class MybatisConfig {
    @Autowired
    DataSource dataSource;

    @Bean
    public SqlSessionFactoryBean getSqlSessionFactoryBean(ApplicationContext context) throws IOException{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setConfiguration(getConfiguration());
        String path = Application.class.getPackage().getName().replace(".", "/");
        sqlSessionFactoryBean.setTypeAliasesPackage(Application.class.getPackage().getName());
        sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:/"+path + "/**/*Dao.xml"));
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{pageInterceptor()});
        return sqlSessionFactoryBean;
    }

    private org.apache.ibatis.session.Configuration getConfiguration(){
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setDefaultStatementTimeout(25000);
        return configuration;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public PageInterceptor pageInterceptor(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("properties", "dialect=mysql");
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
