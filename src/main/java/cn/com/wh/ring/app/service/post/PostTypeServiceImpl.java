package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.PostType;
import cn.com.wh.ring.app.bean.response.Page;
import cn.com.wh.ring.app.constant.Constants;
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
    public Page<PostType> query(cn.com.wh.ring.app.bean.request.Page page) {
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<PostType> list = postTypeDao.query(page.getMaxId(), Constants.BOOLEAN_FALSE);
        return new Page(list);
    }
}
