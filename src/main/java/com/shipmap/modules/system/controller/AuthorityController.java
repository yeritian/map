package com.shipmap.modules.system.controller;

import com.shipmap.framework.BaseController;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.utils.ReflectUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.system.model.Authority;
import com.shipmap.modules.system.service.AuthorityService;
import com.shipmap.modules.system.service.RoleAuthorityService;
import com.shipmap.modules.system.service.exception.AuthorityHaveChildNodesException;
import com.shipmap.modules.system.service.exception.AuthorityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authority")
public class AuthorityController extends BaseController {
    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private RoleAuthorityService roleauthorityService;

    @GetMapping
    public Result list(String roleId) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<Authority> authoritys = authorityService.findByAuthority(null);
        List<String> roleAuths = authorityService.listByRoleId(roleId);
        for (int i = 0; i < authoritys.size(); i++) {
            Authority authority = authoritys.get(i);
            Map<String, Object> map = ReflectUtil.objectToMap(authority);
            if (roleAuths != null) {
                for (int j = 0; j < roleAuths.size(); j++) {
                    if (authority.getAuthority().equals(roleAuths.get(j))) {
                        map.put("checked", 1);
                        break;
                    }
                }
            } else {
                map.put("checked", 0);
            }
            maps.add(map);
        }
        return PageResult.toResult(maps);
    }

    @PostMapping("/role")
    public Result addRoleAuth(Authority authority) {
        if (authority == null || StringUtil.isBlank(authority.getParentId())) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        boolean result = authorityService.addAuthority(authority);
        if (result) {
            return Result.success();
        }
        return Result.failure(ResultCode.ERROR);
    }

    @PutMapping("/role")
    public Result updateRoleAuth(Authority authority) {
        boolean result = authorityService.updateAuthority(authority);
        if (result) {
            return Result.success();
        }
        return Result.failure(ResultCode.ERROR);
    }

    @DeleteMapping("/role")
    public Result delAuthority(String authorityId) {
        try {
            boolean result = authorityService.delAuthority(authorityId);
            if (result) {
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (AuthorityNotFoundException e) {
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        } catch (AuthorityHaveChildNodesException e) {
            return Result.failure(ResultCode.DATA_BIND_DATA_ERROR);
        }
    }
}
