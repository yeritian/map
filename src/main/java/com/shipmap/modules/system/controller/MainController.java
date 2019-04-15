package com.shipmap.modules.system.controller;

import com.shipmap.framework.BaseController;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.model.AuthInfo;
import com.shipmap.framework.service.TokenService;
import com.shipmap.framework.service.exception.AuthFailException;
import com.shipmap.framework.service.exception.UserDisabledException;
import com.shipmap.framework.service.exception.UserNotFoundException;
import com.shipmap.framework.utils.IpUtils;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.system.model.User;
import com.shipmap.modules.system.service.AuthorityService;
import com.shipmap.modules.system.service.RoleService;
import com.shipmap.modules.system.service.UserRoleService;
import com.shipmap.modules.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class MainController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private TokenService tokenService;

    @GetMapping("/userInfo")
    public Result userInfo(HttpServletRequest request) {
        ActiveUser activeUserId = getActiveUser(request);
        User user = userService.selectById(activeUserId.getUserId());
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result login(String username, String password, HttpServletRequest request) {
        if (StringUtil.isBlank(username) || StringUtil.isBlank(password)) {
            //账号和密码为空
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        AuthInfo authInfo = null;
        try {
            authInfo = tokenService.auth(username, password, IpUtils.getIpAddr(request), false);
        } catch (UserDisabledException e) {
            //用户被管理员禁止登陆
            return Result.failure(ResultCode.USER_ACCOUNT_FORBIDDEN);
        } catch (UserNotFoundException e) {
            //用户不存在
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        } catch (AuthFailException e) {
            //密码错误
            return Result.failure(ResultCode.USER_LOGIN_ERROR);
        }
        return Result.success(authInfo);
    }

    @GetMapping("/menus")
    public Result menus(HttpServletRequest request) {
        ActiveUser activeUser = getActiveUser(request);
        return Result.success(activeUser.getMenuMap());
    }

    @GetMapping("/userCss")
    public void userCss(HttpServletRequest request, HttpServletResponse response) {
        ActiveUser activeUser = getActiveUser(request);
        response.setContentType("text/css;charset=UTF-8");
        if (activeUser == null) {
            return;
        }
        try {
            response.getWriter().write(activeUser.getCssStr());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
