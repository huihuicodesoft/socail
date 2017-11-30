package cn.com.wh.ring.app.service.post;

import cn.com.wh.ring.app.bean.pojo.EvaluatePojo;
import cn.com.wh.ring.app.bean.pojo.PostPojo;
import cn.com.wh.ring.app.bean.pojo.PostTypePojo;
import cn.com.wh.ring.app.bean.pojo.ReportPostPojo;
import cn.com.wh.ring.app.bean.request.AddressRequest;
import cn.com.wh.ring.app.bean.request.PageRequest;
import cn.com.wh.ring.app.bean.request.ReportRequest;
import cn.com.wh.ring.app.bean.response.PageResponse;
import cn.com.wh.ring.app.bean.request.PostPublishRequest;
import cn.com.wh.ring.app.bean.response.PostResponse;
import cn.com.wh.ring.app.constant.Constants;
import cn.com.wh.ring.app.dao.address.AddressDao;
import cn.com.wh.ring.app.dao.post.PostDao;
import cn.com.wh.ring.app.dao.evaluate.EvaluateDao;
import cn.com.wh.ring.app.dao.post.PostTypeDao;
import cn.com.wh.ring.app.dao.report.ReportPostDao;
import cn.com.wh.ring.app.exception.ServiceException;
import cn.com.wh.ring.app.helper.FileHelper;
import cn.com.wh.ring.app.service.address.AddressService;
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
    AddressService addressService;

    @Autowired
    EvaluateDao evaluateDao;
    @Autowired
    ReportPostDao reportPostDao;
    @Autowired
    FileHelper fileHelper;

    @Override
    public Long publish(PostPublishRequest postPublishRequest) {
        PostPojo postPojo = new PostPojo();

        List<String> list = postPublishRequest.getMediaContent();
        if (list != null && !list.isEmpty()) {
            if (isAllImage(list)) {
                if (list.size() <= Constants.MAX_PHOTO_NUMBER) {
                    try {
                        postPojo.setMediaContent(JacksonUtils.toJSon(list));
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
                        postPojo.setMediaContent(JacksonUtils.toJSon(list));
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

        postPojo.setUserId(TokenHelper.getCurrentSubjectUserId());
        postPojo.setPostTypeId(postPublishRequest.getPostType());
        postPojo.setDescription(postPublishRequest.getDescription());
        AddressRequest addressRequest = postPublishRequest.getAddress();
        addressService.bind(postPojo, addressRequest);
        postPojo.setState(PostPojo.STATE_CHECKING);
        postPojo.setAnonymous(postPublishRequest.isAnonymous() ? Constants.BOOLEAN_TRUE : Constants.BOOLEAN_FALSE);
        postDao.insert(postPojo);
        return postPojo.getId();
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
    public PageResponse<PostResponse> queryByUserId(Long userId, PageRequest pageRequest) {
        if (userId == null) {
            userId = TokenHelper.getCurrentSubjectUserId();
        }
        PageHelper.startPage(pageRequest.getPageNumber(), pageRequest.getPageSize());
        List<PostPojo> list = postDao.queryByUserId(userId, pageRequest.getMaxId());
        return covertListToPage(list);
    }

    @Override
    public PageResponse<PostResponse> query(PageRequest pageRequest) {
        PageHelper.startPage(pageRequest.getPageNumber(), pageRequest.getPageSize());
        List<PostPojo> list = postDao.queryByState(PostPojo.STATE_CHECK_SUCCESS, pageRequest.getMaxId());
        return covertListToPage(list);
    }

    private PageResponse covertListToPage(List<PostPojo> list) {
        PageResponse result = new PageResponse(list);
        List<PostResponse> responseList = new ArrayList<>();
        List<PostPojo> tempList = result.getList();
        for (PostPojo postPojo : tempList) {
            PostResponse temp = new PostResponse();
            PostTypePojo postTypePojo = postTypeDao.queryById(postPojo.getPostTypeId());

            temp.setId(postPojo.getId());
            temp.setUserId(postPojo.getUserId());
            temp.setRegion(addressService.getRegion(postPojo.getAddressId()));
            temp.setAnonymous(postPojo.getAnonymous() == Constants.BOOLEAN_TRUE);
            if (postTypePojo != null)
                temp.setPostType(postTypePojo.getId(), postTypePojo.getName());

            String names = postPojo.getMediaContent();
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

            temp.setCommentNumber(postPojo.getCommentNumber());
            temp.setCriticizeNumber(postPojo.getCriticizeNumber());
            temp.setPraiseNumber(postPojo.getPraiseNumber());
            temp.setReportNumber(postPojo.getReportNumber());
            temp.setCreationTime(postPojo.getCreationTime());
            temp.setDescription(postPojo.getDescription());
            responseList.add(temp);
        }
        result.setList(responseList);
        return result;
    }

    @Override
    public void praise(Long id) {
        String currentMark = TokenHelper.getCurrentSubjectUuidOrUserId();
        int type = TokenHelper.getCurrentSubjectType();
        EvaluatePojo evaluatePojo = evaluateDao.query(id, EvaluatePojo.HOST_TYPE_POST, currentMark, type);
        if (evaluatePojo == null) {
            evaluatePojo = new EvaluatePojo();
            evaluatePojo.setHostId(id);
            evaluatePojo.setHostType(EvaluatePojo.HOST_TYPE_POST);
            evaluatePojo.setMark(currentMark);
            evaluatePojo.setMarkType(type);
            evaluatePojo.setType(EvaluatePojo.TYPE_PRAISE);
            evaluateDao.insert(evaluatePojo);
            postDao.increasePraiseNumber(id);
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
        EvaluatePojo evaluatePojo = evaluateDao.query(id, EvaluatePojo.HOST_TYPE_POST, currentMark, type);
        if (evaluatePojo == null) {
            evaluatePojo = new EvaluatePojo();
            evaluatePojo.setHostId(id);
            evaluatePojo.setHostType(EvaluatePojo.HOST_TYPE_POST);
            evaluatePojo.setMark(currentMark);
            evaluatePojo.setMarkType(type);
            evaluatePojo.setType(EvaluatePojo.TYPE_CRITICIZED);
            evaluateDao.insert(evaluatePojo);
            postDao.increaseCriticizeNumber(id);
        } else {
            if (evaluatePojo.getType() == EvaluatePojo.TYPE_PRAISE) {
                throw ServiceException.create(ReturnCode.ERROR_PRAISED, "error_praised");
            } else if (evaluatePojo.getType() == EvaluatePojo.TYPE_CRITICIZED) {
                throw ServiceException.create(ReturnCode.ERROR_CRITICIZED, "error_criticized");
            }
        }
    }

    @Override
    public void report(Long id, ReportRequest reportRequest) {
        ReportPostPojo reportPostPojo = new ReportPostPojo();
        reportPostPojo.setPostId(id);
        reportPostPojo.setMark(TokenHelper.getCurrentSubjectUuidOrUserId());
        reportPostPojo.setContent(reportRequest.getContent());
        reportPostPojo.setContentType(reportRequest.getContentType());
        reportPostDao.insert(reportPostPojo);
        postDao.increaseReportNumber(id);
    }
}
