package com.example.demo.mapper;

import com.example.demo.model.UserRole;

import java.util.List;

public interface UserRoleMapper {
    // 查询用户角色列表
    List<UserRole> getRoles();

    // 根据id查询
    UserRole getRole(Integer id);

    // 修改
    int updateRole(Integer id);

    // 新增
    int addRole(String userRole);

    // 删除
    int deleteRole(Integer id);
}
