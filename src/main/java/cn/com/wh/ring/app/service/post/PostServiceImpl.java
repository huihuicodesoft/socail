package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.Evaluate;
import cn.com.wh.ring.app.bean.pojo.Post;
import cn.com.wh.ring.app.bean.pojo.ReportPost;
import cn.com.wh.ring.app.bean.request.PostPublish;
import cn.com.wh.ring.app.bean.request.Report;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.constant.PostConstants;
import cn.com.wh.ring.app.constant.UserConstants;
import cn.com.wh.ring.app.dao.post.PostDao;
import cn.com.wh.ring.app.dao.evaluate.EvaluateDao;
import cn.com.wh.ring.app.dao.report.ReportPostDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.common.response.ReturnCode;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.utils.JacksonUtils;
import org.apache.log4j.Logger;
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
    private static final Logger logger = Logger.getLogger(PostServiceImpl.class.getName());

    @Autowired
    PostDao postDao;
    @Autowired
    EvaluateDao evaluateDao;
    @Autowired
    ReportPostDao reportPostDao;

    @Override
    public Long publish(PostPublish postPublish) {
        Post post = new Post();

        List<String> list = postPublish.getMediaContent();
        if (list != null && !list.isEmpty()) {
            int mediaType = postPublish.getMediaType();
            if (mediaType == PostConstants.MEDIA_TYPE_PHOTO) {
                if (list.size() <= PostConstants.MAX_PHOTO_NUMBER) {
                    try {
                        post.setMediaContent(JacksonUtils.toJSon(list));
                    } catch (Exception e) {
                        logger.error("图片内容jackson转化异常 =>" + e.toString());
                        throw ServiceException.create(ReturnCode.ERROR_INFO, "error_info");
                    }
                    post.setMediaType(mediaType);
                } else {
                    throw ServiceException.create(ReturnCode.ERROR_POST_ILLEGAL_MEDIA_PHOTO_NUMBER, "error_post_illegal_media_photo_number");
                }
            } else if (mediaType == PostConstants.MEDIA_TYPE_VIDEO) {
                if (list.size() == PostConstants.MAX_VIDEO_NUMBER) {
                    try {
                        post.setMediaContent(JacksonUtils.toJSon(list));
                    } catch (Exception e) {
                        logger.error("视频内容jackson转化异常 =>" + e.toString());
                        throw ServiceException.create(ReturnCode.ERROR_INFO, "error_info");
                    }
                    post.setMediaType(mediaType);
                } else {
                    throw ServiceException.create(ReturnCode.ERROR_POST_ILLEGAL_MEDIA_VIDEO_NUMBER, "error_post_illegal_media_video_number");
                }
            } else {
                throw ServiceException.create(ReturnCode.ERROR_POST_ILLEGAL_MEDIA_TYPE, "error_post_illegal_media_type");
            }
        }

        post.setUserId(Long.valueOf(TokenHelper.getCurrentMark()));
        post.setDescription(postPublish.getDescription());
        post.setAddressCode(postPublish.getAddressCode());
        post.setType(postPublish.getType());
        post.setAnonymous(postPublish.isAnonymous() ? Constants.BOOLEAN_TRUE : Constants.BOOLEAN_FALSE);
        postDao.insert(post);
        return post.getId();
    }

    @Override
    public void praise(Long id) {
        String currentMark = TokenHelper.getCurrentMark();
        String currentMarkType = TokenHelper.getCurrentMarkType();
        int type = TokenHelper.isUserByType(currentMarkType) ? UserConstants.TYPE_USER : UserConstants.TYPE_TOURIST;
        Evaluate evaluate = evaluateDao.query(id, Constants.EVALUATE_TYPE_HOST_POST, currentMark, type);
        if (evaluate == null) {
            evaluate = new Evaluate();
            evaluate.setHostId(id);
            evaluate.setHostType(Constants.EVALUATE_TYPE_HOST_POST);
            evaluate.setMark(currentMark);
            evaluate.setMarkType(type);
            evaluate.setType(Constants.EVALUATE_TYPE_PRAISE);
            evaluateDao.insert(evaluate);
            postDao.increasePraiseNumber(id);
        } else {
            if (evaluate.getType() == Constants.EVALUATE_TYPE_PRAISE) {
                throw ServiceException.create(ReturnCode.ERROR_PRAISED, "error_praised");
            } else if (evaluate.getType() == Constants.EVALUATE_TYPE_CRITICIZED) {
                throw ServiceException.create(ReturnCode.ERROR_CRITICIZED, "error_criticized");
            }
        }
    }

    @Override
    public void criticize(Long id) {
        String currentMark = TokenHelper.getCurrentMark();
        String currentMarkType = TokenHelper.getCurrentMarkType();
        int type = TokenHelper.isUserByType(currentMarkType) ? UserConstants.TYPE_USER : UserConstants.TYPE_TOURIST;
        Evaluate evaluate = evaluateDao.query(id, Constants.EVALUATE_TYPE_HOST_POST, currentMark, type);
        if (evaluate == null) {
            evaluate = new Evaluate();
            evaluate.setHostId(id);
            evaluate.setHostType(Constants.EVALUATE_TYPE_HOST_POST);
            evaluate.setMark(currentMark);
            evaluate.setMarkType(type);
            evaluate.setType(Constants.EVALUATE_TYPE_CRITICIZED);
            evaluateDao.insert(evaluate);
            postDao.increaseCriticizeNumber(id);
        } else {
            if (evaluate.getType() == Constants.EVALUATE_TYPE_PRAISE) {
                throw ServiceException.create(ReturnCode.ERROR_PRAISED, "error_praised");
            } else if (evaluate.getType() == Constants.EVALUATE_TYPE_CRITICIZED) {
                throw ServiceException.create(ReturnCode.ERROR_CRITICIZED, "error_criticized");
            }
        }
    }

    @Override
    public void report(Long id, Report report) {
        ReportPost reportPost = new ReportPost();
        reportPost.setPostId(id);
        reportPost.setMark(TokenHelper.getCurrentMark());
        reportPost.setContent(report.getContent());
        reportPost.setContentType(report.getContentType());
        reportPostDao.insert(reportPost);
        postDao.increaseReportNumber(id);
    }
}
