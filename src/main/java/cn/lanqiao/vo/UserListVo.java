package cn.lanqiao.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/13 12:02
 */
@Data
public class UserListVo {
    /**
     * 用户ID
     */
    private String id;

    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 登录账号
     */
    private String loginName;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户性别（1男 2女 3未知）
     */
    private String sex;

    /**
     * 用户类型（1系统用户 2普通用户）
     */
    private String userType;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账号状态（1正常 2停用
     */
    private String status;

    /**
     * 显示顺序
     */
    private String orderNum;

    /**
     * 最后登录Ip
     */
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime loginDate;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime updateTime;

}
