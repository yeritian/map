package com.shipmap.modules.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.model.ActiveUser;
import com.shipmap.framework.service.TokenService;
import com.shipmap.framework.service.exception.AuthFailException;
import com.shipmap.framework.service.exception.RoleNotFoundException;
import com.shipmap.framework.service.exception.UserExistException;
import com.shipmap.framework.service.exception.UserNotFoundException;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.system.model.Role;
import com.shipmap.modules.system.model.User;
import com.shipmap.modules.system.model.UserRole;
import com.shipmap.modules.system.service.RoleService;
import com.shipmap.modules.system.service.UserRoleService;
import com.shipmap.modules.system.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/query")
    public Result list(Integer page, Integer limit, User user) {
        if (page == null) {
            page = 0;
        }
        if (limit == null) {
            limit = 10;
        }
        Page<User> userPage = userService.findByUser(new Page<>(page, limit), user);
        List<User> userList = userPage.getRecords();
        // 关联查询role
        List<String> userIds = new ArrayList<String>();
        for (User one : userList) {
            userIds.add(one.getId());
        }
        //内存换IO
        List<UserRole> userRoles = userRoleService.selectList(new EntityWrapper().in("user_id", userIds));
        List<Role> roles = roleService.selectList(null);
        for (User one : userList) {
            List<Role> tempUrs = new ArrayList<>();
            for (UserRole ur : userRoles) {
                if (one.getId().equals(ur.getUserId())) {
                    for (Role r : roles) {
                        if (ur.getRoleId().equals(r.getId())) {
                            tempUrs.add(r);
                        }
                    }
                }
            }
            one.setRoles(tempUrs);
        }
        return PageResult.toResult(userList, userPage.getTotal());
    }

    @PostMapping()
    public Result add(User user, String roleIds) {
        String[] split = roleIds.split(",");
        try {
            boolean result = tokenService.addAuth(user, split);
            if (result) {
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (UserExistException e) {
            //用户已存在
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        } catch (RoleNotFoundException e) {
            //角色与服务端不匹配
            return Result.failure(ResultCode.PARAM_IS_INVALID);
        }
    }

    @PutMapping()
    public Result update(User user, String roleIds) {
        String[] split = roleIds.split(",");
        try {
            boolean result = tokenService.updateAuth(user, split);
            if (result) {
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (UserExistException e) {
            //用户已存在
            return Result.failure(ResultCode.USER_HAS_EXISTED);
        } catch (RoleNotFoundException e) {
            //角色与服务端不匹配
            return Result.failure(ResultCode.PARAM_IS_INVALID);
        }
    }

    @PutMapping("/state")
    public Result updateState(String userId, @RequestParam(defaultValue = "0") Integer state) {
        if (StringUtil.isBlank(userId)) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        try {
            boolean result = tokenService.setAuthState(userId, state);
            if (result) {
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (UserNotFoundException e) {
            //用户不存在
            return Result.failure(ResultCode.AUTH_EXPIRE);
        }
    }

    @PutMapping("/psw")
    public Result updatePsw(String oldPsw, String newPsw, HttpServletRequest request) {
        ActiveUser activeUser = getActiveUser(request);
        try {
            boolean result = tokenService.setAuthPassword(activeUser.getUserId(), oldPsw, newPsw);
            if (result) {
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (UserNotFoundException e) {
            //用户不存在
            return Result.failure(ResultCode.AUTH_EXPIRE);
        } catch (AuthFailException e) {
            //密码错误
            ResultCode authFail = ResultCode.AUTH_FAIL;
            return Result.failure(authFail);
        }
    }

    @PutMapping("/psw/{id}")
    public Result resetPsw(@PathVariable("id") String userId) {
        try {
            boolean result = tokenService.setResetAuth(userId, "123456");
            if (result) {
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (UserNotFoundException e) {
            return Result.failure(ResultCode.PARAM_IS_INVALID);
        }
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String userId) {
        try {
            boolean result = tokenService.removeAuth(userId);
            if (result) {
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (UserNotFoundException e) {
            return Result.failure(ResultCode.PARAM_IS_INVALID);
        }
    }

    @GetMapping("/checkUserName")
    public Result checkUserNameIsExists(String username) {
        if (StringUtil.isBlank(username)) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        boolean result = tokenService.queryAuthIsExistsByUsername(username);
        return Result.success("flag", result);
    }
}
