package cn.lanqiao.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/13 12:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleVo{

    /**
     * 用户id
     */
    private String id;

    /**
     * 用户登录名
     */
    private String loginName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户类型
     */
    private String userType;

    /**
     * 性别
     */
    private String sex;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 角色名称
     */
    private String roleName;
}
