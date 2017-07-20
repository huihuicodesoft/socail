package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.PostPojo;
import cn.com.wh.ring.app.bean.pojo.PraisePojo;
import cn.com.wh.ring.app.bean.pojo.ReportPostPojo;
import cn.com.wh.ring.app.bean.request.PostPublish;
import cn.com.wh.ring.app.bean.request.Report;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.constant.PostConstants;
import cn.com.wh.ring.app.dao.post.PostDao;
import cn.com.wh.ring.app.dao.praise.PraiseDao;
import cn.com.wh.ring.app.dao.report.ReportPostDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.common.response.ReturnCode;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.utils.JacksonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Hui on 2017/7/17.
 */
@Service
@Transactional
public class PostServiceImpl implements PostService {
    @Autowired
    PostDao postDao;
    @Autowired
    PraiseDao praiseDao;
    @Autowired
    ReportPostDao reportPostDao;

    @Override
    public void publish(PostPublish postPublish) throws Exception {
        PostPojo postPojo = new PostPojo();

        List<String> list = postPublish.getMediaContent();
        if (list != null && !list.isEmpty()) {
            int mediaType = postPublish.getMediaType();
            if (mediaType == PostConstants.MEDIA_TYPE_PHOTO) {
                if (list.size() <= PostConstants.MAX_PHOTO_NUMBER) {
                    postPojo.setMediaContent(JacksonUtils.toJSon(list));
                    postPojo.setMediaType(mediaType);
                } else {
                    throw ServiceException.create(ReturnCode.ERROR_POST_ILLEGAL_MEDIA_PHOTO_NUMBER_, "error_post_illegal_media_photo_number");
                }
            } else if (mediaType == PostConstants.MEDIA_TYPE_VIDEO) {
                if (list.size() == PostConstants.MAX_VIDEO_NUMBER) {
                    postPojo.setMediaContent(JacksonUtils.toJSon(list));
                    postPojo.setMediaType(mediaType);
                } else {
                    throw ServiceException.create(ReturnCode.ERROR_POST_ILLEGAL_MEDIA_VIDEO_NUMBER_, "error_post_illegal_media_video_number");
                }
            }
        }

        postPojo.setUserId(Long.valueOf(TokenHelper.getCurrentMark()));
        postPojo.setDescription(postPublish.getDescription());
        postPojo.setAddressCode(postPublish.getAddressCode());
        postPojo.setType(postPublish.getType());
        postPojo.setAnonymous(postPublish.isAnonymous() ? Constants.BOOLEAN_TRUE : Constants.BOOLEAN_FALSE);
        postDao.insert(postPojo);
    }

    @Override
    public void praise(Long id) {
        PraisePojo praisePojo = new PraisePojo();
        praisePojo.setPostId(id);
        praisePojo.setMark(TokenHelper.getCurrentMark());
        praisePojo.setIsBad(Constants.BOOLEAN_FALSE);
        PraisePojo pojo = praiseDao.query(praisePojo);
        if (pojo == null) {
            praiseDao.insert(praisePojo);
            postDao.increasePraiseNumber(id);
        } else {
            if (pojo.getIsBad() == Constants.BOOLEAN_FALSE) {
                throw ServiceException.create(ReturnCode.ERROR_POST_PRAISED_, "error_post_praised");
            } else {
                throw ServiceException.create(ReturnCode.ERROR_POST_CRITICIZED, "error_post_criticized");
            }
        }
    }

    @Override
    public void criticize(Long id) {
        PraisePojo praisePojo = new PraisePojo();
        praisePojo.setPostId(id);
        praisePojo.setMark(TokenHelper.getCurrentMark());
        praisePojo.setIsBad(Constants.BOOLEAN_TRUE);
        PraisePojo pojo = praiseDao.query(praisePojo);
        if (pojo == null) {
            praiseDao.insert(praisePojo);
            postDao.increaseCriticizeNumber(id);
        } else {
            if (pojo.getIsBad() == Constants.BOOLEAN_FALSE) {
                throw ServiceException.create(ReturnCode.ERROR_POST_PRAISED_, "error_post_praised");
            } else {
                throw ServiceException.create(ReturnCode.ERROR_POST_CRITICIZED, "error_post_criticized");
            }
        }
    }

    @Override
    public void report(Long id, Report report) {
        ReportPostPojo reportPostPojo = new ReportPostPojo();
        reportPostPojo.setPostId(id);
        reportPostPojo.setMark(TokenHelper.getCurrentMark());
        reportPostPojo.setContent(report.getContent());
        reportPostPojo.setContentType(report.getContentType());
        reportPostDao.insert(reportPostPojo);
        postDao.increaseReportNumber(id);
    }
}
