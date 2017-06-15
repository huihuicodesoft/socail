package cn.com.wh.social.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.filter.stat.StatFilter;
import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hui on 2017/6/15.
 *
 * Druid数据源在spring容器中的配置
 */
@Configuration
public class DruidConfig {
    @Value("${db.url}")
    String url;
    @Value("${db.username}")
    String username;
    @Value("${db.password}")
    String password;

    @Bean
    public DataSource instanceDruidDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        try {
            dataSource.setFilters("stat,log4j");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dataSource.setMaxActive(20);
        dataSource.setInitialSize(1);
        dataSource.setMaxWait(60000);
        dataSource.setMinIdle(1);
        dataSource.setValidationQuery("select 1");
        dataSource.setValidationQueryTimeout(6000);
        dataSource.setTestOnBorrow(false);
        dataSource.setTimeBetweenLogStatsMillis(300000);
        List proxyFilters = new ArrayList<Filter>();
        proxyFilters.add(getLog4jFilter());
        proxyFilters.add(getStatFilter());
        dataSource.setProxyFilters(proxyFilters);
        return dataSource;
    }

    private Log4jFilter getLog4jFilter() {
        Log4jFilter log4jFilter = new Log4jFilter();
        log4jFilter.setConnectionLogErrorEnabled(true);
        log4jFilter.setStatementLogErrorEnabled(true);
        log4jFilter.setResultSetLogErrorEnabled(true);
        return log4jFilter;
    }

    private StatFilter getStatFilter() {
        StatFilter statFilter = new StatFilter();
        statFilter.setSlowSqlMillis(3000);
        statFilter.setLogSlowSql(true);
        return statFilter;
    }
}
