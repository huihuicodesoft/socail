package cn.com.wh.ring.app.dao.report;

import cn.com.wh.ring.app.bean.pojo.ReportPostPojo;
import org.springframework.stereotype.Repository;

/**
 * Created by Hui on 2017/7/18.
 */
@Repository
public interface ReportPostDao {
    void insert(ReportPostPojo reportPostPojo);
}
