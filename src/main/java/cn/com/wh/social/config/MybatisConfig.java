package cn.com.wh.social.config;

import cn.com.wh.social.Application;
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
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
