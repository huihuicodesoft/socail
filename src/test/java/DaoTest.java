import cn.com.wh.ring.app.dao.user.UserTouristDao;
import cn.com.wh.ring.app.bean.pojo.UserTouristPojo;
import cn.com.wh.ring.config.DruidConfig;
import cn.com.wh.ring.config.MybatisConfig;
import cn.com.wh.ring.config.WebContextConfig;
import cn.com.wh.ring.config.WebMvcConfig;
import junit.CustomRunner;
import junit.RunTimes;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hui on 2017/6/23.
 */
@RunWith(CustomRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {WebContextConfig.class, MybatisConfig.class, DruidConfig.class, WebMvcConfig.class})
@Transactional
public class DaoTest  extends AbstractJUnit4SpringContextTests {
    @Autowired
    UserTouristDao userTouristDao;

    @org.junit.Test
    @RunTimes(1)
    public void test(){
        try{
            UserTouristPojo userTouristPojo = new UserTouristPojo();
            userTouristPojo.setTerminalMark("terminal_2");
            userTouristDao.insertOrUpdate(userTouristPojo);
        }catch (Exception e){
            System.out.print("错误 = " + e.toString());
        }
    }
}
