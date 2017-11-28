package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.Evaluate;
import cn.com.wh.ring.app.bean.pojo.Post;
import cn.com.wh.ring.app.bean.pojo.PostType;
import cn.com.wh.ring.app.bean.pojo.ReportPost;
import cn.com.wh.ring.app.bean.response.Page;
import cn.com.wh.ring.app.bean.request.PostPublish;
import cn.com.wh.ring.app.bean.request.Report;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.dao.post.PostDao;
import cn.com.wh.ring.app.dao.evaluate.EvaluateDao;
import cn.com.wh.ring.app.dao.post.PostTypeDao;
import cn.com.wh.ring.app.dao.report.ReportPostDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.FileHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.app.utils.JacksonUtils;
import com.github.pagehelper.PageHelper;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    PostTypeDao postTypeDao;
    @Autowired
    EvaluateDao evaluateDao;
    @Autowired
    ReportPostDao reportPostDao;
    @Autowired
    FileHelper fileHelper;

    @Override
    public Long publish(PostPublish postPublish) {
        Post post = new Post();

        List<String> list = postPublish.getMediaContent();
        if (list != null && !list.isEmpty()) {
            if (isAllImage(list)) {
                if (list.size() <= Constants.MAX_PHOTO_NUMBER) {
                    try {
                        post.setMediaContent(JacksonUtils.toJSon(list));
                    } catch (Exception e) {
                        logger.error("图片内容jackson转化异常 =>" + e.toString());
                        throw ServiceException.create(ReturnCode.ERROR_INFO, "error_info");
                    }
                } else {
                    throw ServiceException.create(ReturnCode.ERROR_POST_ILLEGAL_MEDIA_PHOTO_NUMBER, "error_post_illegal_media_photo_number");
                }
            } else if (isAllVideo(list)) {
                if (list.size() == Constants.MAX_VIDEO_NUMBER) {
                    try {
                        post.setMediaContent(JacksonUtils.toJSon(list));
                    } catch (Exception e) {
                        logger.error("视频内容jackson转化异常 =>" + e.toString());
                        throw ServiceException.create(ReturnCode.ERROR_INFO, "error_info");
                    }
                } else {
                    throw ServiceException.create(ReturnCode.ERROR_POST_ILLEGAL_MEDIA_VIDEO_NUMBER, "error_post_illegal_media_video_number");
                }
            } else {
                throw ServiceException.create(ReturnCode.ERROR_POST_ILLEGAL_MEDIA_TYPE, "error_post_illegal_media_type");
            }
        }

        post.setUserId(TokenHelper.getCurrentSubjectUserId());
        post.setPostTypeId(postPublish.getPostType());
        post.setDescription(postPublish.getDescription());
        post.setAddressCode(postPublish.getAddressCode());
        post.setAnonymous(postPublish.isAnonymous() ? Constants.BOOLEAN_TRUE : Constants.BOOLEAN_FALSE);
        postDao.insert(post);
        return post.getId();
    }

    private boolean isAllImage(List<String> list) {
        for (String name : list) {
            if (!fileHelper.isPhoto(name) && !fileHelper.isGif(name)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAllVideo(List<String> list) {
        for (String name : list) {
            if (!fileHelper.isVideo(name)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Page<cn.com.wh.ring.app.bean.response.Post> queryByUserId(Long userId, cn.com.wh.ring.app.bean.request.Page page) {
        if (userId == null) {
            userId = TokenHelper.getCurrentSubjectUserId();
        }
        PageHelper.startPage(page.getPageNumber(), page.getPageSize());
        List<Post> list = postDao.queryByUserId(userId, page.getMaxId(), Constants.BOOLEAN_FALSE);
        Page result = new Page(list);

        List<cn.com.wh.ring.app.bean.response.Post> responseList = new ArrayList<>();
        List<Post> tempList = result.getList();
        for (Post post : tempList) {
            cn.com.wh.ring.app.bean.response.Post temp = new cn.com.wh.ring.app.bean.response.Post();
            PostType postType = postTypeDao.queryById(post.getPostTypeId());

            temp.setId(post.getId());
            temp.setUserId(post.getUserId());
            temp.setAddressCode(post.getAddressCode());
            temp.setAnonymous(post.getAnonymous() == Constants.BOOLEAN_TRUE);
            if (postType != null)
                temp.setPostType(postType.getId(), postType.getName());

            String names = post.getMediaContent();
            if (!Strings.isNullOrEmpty(names)) {
                try {
                    List<String> files = new ArrayList<>();
                    List<String> nameList = JacksonUtils.readValue(names, List.class);
                    for (String name : nameList) {
                        files.add(fileHelper.getFileImageUrl(name));
                    }
                    temp.setMediaList(files);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            temp.setCommentNumber(post.getCommentNumber());
            temp.setCriticizeNumber(post.getCriticizeNumber());
            temp.setPraiseNumber(post.getPraiseNumber());
            temp.setReportNumber(post.getReportNumber());
            temp.setCreationTime(post.getCreationTime());
            temp.setDescription(post.getDescription());
            responseList.add(temp);
        }
        result.setList(responseList);
        return result;
    }

    @Override
    public void praise(Long id) {
        String currentMark = TokenHelper.getCurrentSubjectUuidOrUserId();
        int type = TokenHelper.getCurrentSubjectType();
        Evaluate evaluate = evaluateDao.query(id, Evaluate.HOST_TYPE_POST, currentMark, type);
        if (evaluate == null) {
            evaluate = new Evaluate();
            evaluate.setHostId(id);
            evaluate.setHostType(Evaluate.HOST_TYPE_POST);
            evaluate.setMark(currentMark);
            evaluate.setMarkType(type);
            evaluate.setType(Evaluate.TYPE_PRAISE);
            evaluateDao.insert(evaluate);
            postDao.increasePraiseNumber(id);
        } else {
            if (evaluate.getType() == Evaluate.TYPE_PRAISE) {
                throw ServiceException.create(ReturnCode.ERROR_PRAISED, "error_praised");
            } else if (evaluate.getType() == Evaluate.TYPE_CRITICIZED) {
                throw ServiceException.create(ReturnCode.ERROR_CRITICIZED, "error_criticized");
            }
        }
    }

    @Override
    public void criticize(Long id) {
        String currentMark = TokenHelper.getCurrentSubjectUuidOrUserId();
        int type = TokenHelper.getCurrentSubjectType();
        Evaluate evaluate = evaluateDao.query(id, Evaluate.HOST_TYPE_POST, currentMark, type);
        if (evaluate == null) {
            evaluate = new Evaluate();
            evaluate.setHostId(id);
            evaluate.setHostType(Evaluate.HOST_TYPE_POST);
            evaluate.setMark(currentMark);
            evaluate.setMarkType(type);
            evaluate.setType(Evaluate.TYPE_CRITICIZED);
            evaluateDao.insert(evaluate);
            postDao.increaseCriticizeNumber(id);
        } else {
            if (evaluate.getType() == Evaluate.TYPE_PRAISE) {
                throw ServiceException.create(ReturnCode.ERROR_PRAISED, "error_praised");
            } else if (evaluate.getType() == Evaluate.TYPE_CRITICIZED) {
                throw ServiceException.create(ReturnCode.ERROR_CRITICIZED, "error_criticized");
            }
        }
    }

    @Override
    public void report(Long id, Report report) {
        ReportPost reportPost = new ReportPost();
        reportPost.setPostId(id);
        reportPost.setMark(TokenHelper.getCurrentSubjectUuidOrUserId());
        reportPost.setContent(report.getContent());
        reportPost.setContentType(report.getContentType());
        reportPostDao.insert(reportPost);
        postDao.increaseReportNumber(id);
    }
}
