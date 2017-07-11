package cn.com.wh.ring.config;

import cn.com.wh.ring.app.auth.ShiroFilterManager;
import cn.com.wh.ring.app.auth.TokenRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Hui on 2017/7/6.
 */
@Configuration
public class ShiroConfig {
    @Bean
    public Realm tokenRealm() {
        return new TokenRealm();
    }

    @Bean
    public LifecycleBeanPostProcessor instanceLifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    private Collection<Realm> securityRealms() {
        Collection<Realm> realms = new ArrayList<Realm>();
        realms.add(tokenRealm());
        return realms;
    }

    @Bean
    public DefaultWebSecurityManager instanceDefaultWebSecurityManager(){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        defaultWebSecurityManager.setRealms(securityRealms());
        return defaultWebSecurityManager;
    }

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean instanceShiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilters(ShiroFilterManager.getFilters());
        shiroFilterFactoryBean.setFilterChainDefinitionMap(ShiroFilterManager.getFilterChainDefinition());
        return shiroFilterFactoryBean;
    }
}
