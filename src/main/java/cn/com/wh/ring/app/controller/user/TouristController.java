package cn.com.wh.ring.app.controller.user;

import cn.com.wh.ring.app.bean.request.TouristVo;
import cn.com.wh.ring.app.helper.OSHelper;
import cn.com.wh.ring.app.service.user.UserTouristService;
import cn.com.wh.ring.common.response.Response;
import cn.com.wh.ring.common.response.ResponseHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Hui on 2017/7/10.
 */
@RestController
@RequestMapping("rest/tourist")
@Api("游客用户控制器")
public class TouristController {
    @Autowired
    UserTouristService userTouristService;

    @PostMapping("v1/record")
    @ApiOperation(value = "记录设备登录")
    @ApiImplicitParams({@ApiImplicitParam(name = "ApiVersion", value = "A1", required = true, allowableValues = "A1,I1", paramType = "header")})
    public Response<?> addUser(@RequestHeader("ApiVersion") String apiVersion, @RequestBody TouristVo touristVo) {
        String token = userTouristService.recordAccessInfo(touristVo, OSHelper.getOSCode(apiVersion));
        return ResponseHelper.createSuccessResponse(token);
    }
}
