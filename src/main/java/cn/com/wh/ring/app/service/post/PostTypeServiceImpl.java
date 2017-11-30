package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.PostTypePojo;
import cn.com.wh.ring.app.bean.request.PageRequest;
import cn.com.wh.ring.app.bean.response.PageResponse;
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
    public PageResponse<PostTypePojo> query(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNumber(), pageRequest.getPageSize());
        List<PostTypePojo> list = postTypeDao.query(pageRequest.getMaxId(), Constants.BOOLEAN_FALSE);
        return new PageResponse(list);
    }
}
