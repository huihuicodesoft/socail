package cn.com.wh.ring.app.service.comment;

import cn.com.wh.ring.app.bean.pojo.CommentPojo;
import cn.com.wh.ring.app.bean.pojo.EvaluatePojo;
import cn.com.wh.ring.app.dao.evaluate.EvaluateDao;
import cn.com.wh.ring.app.dao.comment.CommentDao;
import cn.com.wh.ring.app.dao.post.PostDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.TokenHelper;
import cn.com.wh.ring.common.response.ReturnCode;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Hui on 2017/8/3.
 */
@Service
@Transactional
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;
    @Autowired
    EvaluateDao evaluateDao;
    @Autowired
    PostDao postDao;

    @Override
    public Long comment(Long postId, String comment) {
        if (Strings.isNullOrEmpty(comment)) {
            throw new ServiceException(ReturnCode.ERROR_POST_COMMENT_NULL, "error_post_comment_null");
        }
        Long userId = TokenHelper.getCurrentSubjectUserId();
        CommentPojo commentPojoTemp = new CommentPojo();
        commentPojoTemp.setHostId(postId);
        commentPojoTemp.setHostType(CommentPojo.HOST_POST);
        commentPojoTemp.setContent(comment);
        commentPojoTemp.setUserId(userId);
        commentDao.insert(commentPojoTemp);
        postDao.increaseCommentNumber(postId);
        return commentPojoTemp.getId();
    }

    @Override
    public void praise(Long id) {
        String currentMark = TokenHelper.getCurrentSubjectUuidOrUserId();
        int type = TokenHelper.getCurrentSubjectType();
        EvaluatePojo evaluatePojo = evaluateDao.query(id, EvaluatePojo.HOST_TYPE_COMMENT, currentMark, type);
        if (evaluatePojo == null) {
            evaluatePojo = new EvaluatePojo();
            evaluatePojo.setHostId(id);
            evaluatePojo.setHostType(EvaluatePojo.HOST_TYPE_COMMENT);
            evaluatePojo.setMark(currentMark);
            evaluatePojo.setMarkType(type);
            evaluatePojo.setType(EvaluatePojo.TYPE_PRAISE);
            evaluateDao.insert(evaluatePojo);
            commentDao.increasePraiseNumber(id);
        } else {
            if (evaluatePojo.getType() == EvaluatePojo.TYPE_PRAISE) {
                throw ServiceException.create(ReturnCode.ERROR_PRAISED, "error_praised");
            } else if (evaluatePojo.getType() == EvaluatePojo.TYPE_CRITICIZED) {
                throw ServiceException.create(ReturnCode.ERROR_CRITICIZED, "error_criticized");
            }
        }
    }

    @Override
    public void criticize(Long id) {
        String currentMark = TokenHelper.getCurrentSubjectUuidOrUserId();
        int type = TokenHelper.getCurrentSubjectType();
        EvaluatePojo evaluatePojo = evaluateDao.query(id, EvaluatePojo.HOST_TYPE_COMMENT, currentMark, type);
        if (evaluatePojo == null) {
            evaluatePojo = new EvaluatePojo();
            evaluatePojo.setHostId(id);
            evaluatePojo.setHostType(EvaluatePojo.HOST_TYPE_COMMENT);
            evaluatePojo.setMark(currentMark);
            evaluatePojo.setMarkType(type);
            evaluatePojo.setType(EvaluatePojo.TYPE_CRITICIZED);
            evaluateDao.insert(evaluatePojo);
            commentDao.increaseCriticizeNumber(id);
        } else {
            if (evaluatePojo.getType() == EvaluatePojo.TYPE_PRAISE) {
                throw ServiceException.create(ReturnCode.ERROR_PRAISED, "error_praised");
            } else if (evaluatePojo.getType() == EvaluatePojo.TYPE_CRITICIZED) {
                throw ServiceException.create(ReturnCode.ERROR_CRITICIZED, "error_criticized");
            }
        }
    }
}
