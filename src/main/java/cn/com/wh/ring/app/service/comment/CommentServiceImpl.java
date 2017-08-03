package cn.com.wh.ring.app.service.comment;

import cn.com.wh.ring.app.bean.pojo.Evaluate;
import cn.com.wh.ring.app.bean.pojo.Comment;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.constant.UserConstants;
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
        String mark = TokenHelper.getCurrentMark();
        Comment commentTemp = new Comment();
        commentTemp.setHostId(postId);
        commentTemp.setHostType(Constants.COMMENT_HOST_POST);
        commentTemp.setContent(comment);
        commentTemp.setUserId(Long.valueOf(mark));
        commentDao.insert(commentTemp);
        postDao.increaseCommentNumber(postId);
        return commentTemp.getId();
    }

    @Override
    public void praise(Long id) {
        String currentMark = TokenHelper.getCurrentMark();
        String currentMarkType = TokenHelper.getCurrentMarkType();
        int type = TokenHelper.isUserByType(currentMarkType) ? UserConstants.TYPE_USER : UserConstants.TYPE_TOURIST;
        Evaluate evaluate = evaluateDao.query(id, Constants.EVALUATE_TYPE_HOST_COMMENT, currentMark, type);
        if (evaluate == null) {
            evaluate = new Evaluate();
            evaluate.setHostId(id);
            evaluate.setHostType(Constants.EVALUATE_TYPE_HOST_COMMENT);
            evaluate.setMark(currentMark);
            evaluate.setMarkType(type);
            evaluate.setType(Constants.EVALUATE_TYPE_PRAISE);
            evaluateDao.insert(evaluate);
            commentDao.increasePraiseNumber(id);
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
        Evaluate evaluate = evaluateDao.query(id, Constants.EVALUATE_TYPE_HOST_COMMENT, currentMark, type);
        if (evaluate == null) {
            evaluate = new Evaluate();
            evaluate.setHostId(id);
            evaluate.setHostType(Constants.EVALUATE_TYPE_HOST_COMMENT);
            evaluate.setMark(currentMark);
            evaluate.setMarkType(type);
            evaluate.setType(Constants.EVALUATE_TYPE_CRITICIZED);
            evaluateDao.insert(evaluate);
            commentDao.increaseCriticizeNumber(id);
        } else {
            if (evaluate.getType() == Constants.EVALUATE_TYPE_PRAISE) {
                throw ServiceException.create(ReturnCode.ERROR_PRAISED, "error_praised");
            } else if (evaluate.getType() == Constants.EVALUATE_TYPE_CRITICIZED) {
                throw ServiceException.create(ReturnCode.ERROR_CRITICIZED, "error_criticized");
            }
        }
    }
}
