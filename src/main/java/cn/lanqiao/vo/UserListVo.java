package cn.lanqiao.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/5/23 19:06
 */
@Data
public class UserListVo {
    private String id;
    /**
     * 部门ID
     */
    private String deptId;
    private  String deptName;
    /**
     * 登录账号
     */
    private String loginName;
    /**
     * 用户昵称
     */
    private String userName;
    /**
     * 用户类型（1系统用户 2普通用户）
     */
    private String userType;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 用户性别（1男 2女 3未知）
     */
    private String sex;
    /**
     * 头像路径
     */
    private String avatar;
    /**
     * 密码
     */
//    private String password;
    /**
     * 盐加密
     */
//    private String salt;
    /**
     * 帐号状态（1正常 2停用）
     */
    private String status;
    /**
     * 删除标志（0代表存在 1代表删除）
     */
//    private String delFlag;
    /**
     * 最后登录IP
     */
    private String loginIp;
    /**
     * 最后登录时间
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime loginDate;
    /**
     * 创建者
     */
//    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime createTime;
    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")
    private LocalDateTime updateTime;

}
