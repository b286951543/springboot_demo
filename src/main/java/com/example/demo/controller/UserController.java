package com.example.demo.controller;

import com.example.demo.mapper.UserRoleMapper;
import com.example.demo.model.CommonPage;
import com.example.demo.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private UserRoleMapper userRoleMapper;

    // http://localhost:8080/api/user/role/list
    @GetMapping("role/list")
    @ResponseBody // 将方法的返回值，以特定的格式写入到response的body区域，进而将数据返回，如果是一个对象，就会返回json
    public List<UserRole> getUserRole() {
        return userRoleMapper.getRoles();
    }

    // 分页查询
    @GetMapping("role/list2")
    @ResponseBody
    public CommonPage<UserRole> getUserRole2() {
        // 是通过拦截sql来实现的，会执行下面两个sql
        // SELECT count(0) FROM user_role WHERE id < 100
        // select * from user_role where id < 100 LIMIT ?
        CommonPage.setPageHelper(1, 10);
        List<UserRole> list = userRoleMapper.getRoles();
        return  CommonPage.restPage(list);
    }

    // http://localhost:8080/api/user/role?id=2
    @GetMapping("role")
    @ResponseBody
    public UserRole getUserRole(@RequestParam Integer id) {
        return userRoleMapper.getRole(id);
    }

    // http://localhost:8080/api/user/role/update?id=2
    @GetMapping("role/update")
    @ResponseBody
    public String updateUserRole(@RequestParam Integer id) {
        if(userRoleMapper.updateRole(id) <= 0) {
            return "fail";
        }

        return "success";
    }

    @GetMapping("role/add")
    @ResponseBody
    public String addUserRole(@RequestParam String name) {
        if(userRoleMapper.addRole(name) <= 0) {
            return "fail";
        }

        return "success";
    }

    @GetMapping("role/delete")
    @ResponseBody
    public String deleteUserRole(@RequestParam Integer id) {
        if(userRoleMapper.deleteRole(id) <= 0) {
            return "fail";
        }

        return "success";
    }

    // http://localhost:8080/api/user/trans?name=xiaohong
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("trans")
    @ResponseBody
    public String transUser(@RequestParam String name) throws Exception {
        userRoleMapper.addRole(name);
        if(name.equals("xiaohong")) {
            // 异常需要被抛出，否则不能自动回滚
            throw new Exception("trans error");
        }

        userRoleMapper.updateRole(2);
        return "success";
    }

    @Transactional(rollbackFor = Exception.class)
    @GetMapping("trans2")
    @ResponseBody
    public String transUser2(@RequestParam String name) {
        userRoleMapper.addRole(name);
        if(name.equals("xiaohong")) {
            // 手动回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return "fail";
        }

        userRoleMapper.updateRole(2);
        return "success";
    }
}
