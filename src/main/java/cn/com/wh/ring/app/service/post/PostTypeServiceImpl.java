package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.PostTypePojo;
import cn.com.wh.ring.app.bean.response.Page;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.constant.PermisisonConstants;
import cn.com.wh.ring.app.constant.PostConstants;
import cn.com.wh.ring.app.dao.post.PostTypeDao;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hui on 2017/8/1.
 */
@Service
@Transactional
public class PostTypeServiceImpl implements PostTypeService {
    @Autowired
    PostTypeDao postTypeDao;

    @Override
    public Page<PostTypePojo> query(Long maxId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PostTypePojo> list = postTypeDao.query(maxId, PostConstants.POST_TYPE_STATE_USING);
        return new Page(list);
    }
}
