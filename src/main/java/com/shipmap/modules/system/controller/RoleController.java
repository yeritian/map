package com.shipmap.modules.system.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.shipmap.framework.PageResult;
import com.shipmap.framework.Result;
import com.shipmap.framework.ResultCode;
import com.shipmap.framework.service.exception.RoleNotFoundException;
import com.shipmap.framework.utils.JSONUtil;
import com.shipmap.framework.utils.StringUtil;
import com.shipmap.modules.system.model.Role;
import com.shipmap.modules.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping()
    public Result list(String keyword) {
        System.out.println("我是新增功能的控制查询---------------------------------------");
        List<Role> list = roleService.selectList(new EntityWrapper<Role>().orderBy("create_time", true));
        // 筛选结果
        if (keyword != null && !keyword.trim().isEmpty()) {
            keyword = keyword.trim();
            Iterator<Role> iterator = list.iterator();
            while (iterator.hasNext()) {
                Role next = iterator.next();
                boolean b = next.getRoleName().contains(keyword) || next.getComments().contains(keyword);
                if (!b) {
                    iterator.remove();
                }
            }
        }
        return PageResult.toResult(list);
    }

    @PostMapping()
    public Result add(Role role) {
        System.out.println("---------------我是新增功能的控制层");
        //非空判断
        if (role == null) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        //执行新增
        boolean result = roleService.insert(role);
        if (result) {
            //新增成功
            return Result.success();
        }
        return Result.failure(ResultCode.ERROR);
    }

    @PutMapping()
    public Result update(Role role) {
        System.out.println("---------------------我是新增功能更新");
        //非空判断
        if (role == null) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        //检测更新id
        Role tempRole = roleService.selectById(role.getId());
        if (tempRole == null) {
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
        //更改项
        tempRole.setComments(role.getComments());
        tempRole.setRoleName(role.getRoleName());
        //执行更改
        boolean result = roleService.updateById(tempRole);
        if (result) {
            //更新成功
            return Result.success();
        }
        return Result.failure(ResultCode.ERROR);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable("id") String roleId) {
        //非空判断
        if (StringUtil.isBlank(roleId)) {
            return Result.failure(ResultCode.PARAM_IS_BLANK);
        }
        try {
            //执行删除
            boolean result = roleService.delRoleAndAuthorityByRoleId(roleId);
            if (result) {
                //删除成功
                return Result.success();
            } else {
                return Result.failure(ResultCode.ERROR);
            }
        } catch (RoleNotFoundException e) {
            //删除的角色id不存在
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
    }

    @PutMapping("/authority")
    public Result setRoleAndAuthority(String roleId, String authIds) {
        String[] authorityIds = null;
        try {
            List<String> strList = JSONUtil.parseArray(authIds);
            authorityIds = strList.toArray(new String[strList.size()]);
        } catch (Exception e) {
            //参数格式错误
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
        try {
            //执行设置角色功能
            roleService.setRoleAndAuthority(roleId, authorityIds);
            return Result.success();
        } catch (RoleNotFoundException e) {
            //角色id不存在
            return Result.failure(ResultCode.RESULE_DATA_NONE);
        }
    }
}
