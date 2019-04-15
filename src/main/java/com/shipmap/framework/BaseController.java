package com.shipmap.framework;

import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.service.TokenService;
import com.shipmap.framework.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller基类
 * Created by wangfan on 2018-02-22 上午 11:29.
 */
public class BaseController {
    @Autowired
    TokenService tokenService;

    /**
     * 获取当前登录的用户
     */
    public ActiveUser getActiveUser(HttpServletRequest request) {
        Object obj = request.getAttribute(JwtTokenUtil.TOKEN_HEADER);
        if (obj != null) {
            return tokenService.getActiveUserByToken(String.valueOf(obj));
        }
        return null;
    }

}
