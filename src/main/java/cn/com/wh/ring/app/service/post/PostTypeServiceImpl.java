package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.PostType;
import cn.com.wh.ring.app.bean.response.Page;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.constant.PostConstants;
import cn.com.wh.ring.app.dao.post.PostTypeDao;
import com.github.pagehelper.PageHelper;
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
    public Page<PostType> query(Long maxId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<PostType> list = postTypeDao.query(maxId,  Constants.BOOLEAN_FALSE);
        return new Page(list);
    }
}
