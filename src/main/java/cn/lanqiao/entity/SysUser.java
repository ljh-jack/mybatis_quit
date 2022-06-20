package cn.lanqiao.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户信息表
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class SysUser implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 部门ID
     */
    @TableField("dept_id")
    private String deptId;

    /**
     * 登录账号
     */
    @TableField("login_name")
    private String loginName;

    /**
     * 用户昵称
     */
    @TableField("user_name")
    private String userName;

    /**
     * 用户类型（1系统用户 2普通用户）
     */
    @TableField("user_type")
    private String userType;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 手机号码
     */
    @TableField("phone_number")
    private String phoneNumber;

    /**
     * 用户性别（1男 2女 3未知）
     */
    @TableField("sex")
    private String sex;

    /**
     * 头像路径
     */
    @TableField("avatar")
    private String avatar;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 盐加密
     */
    @TableField("salt")
    private String salt;

    /**
     * 帐号状态（1正常 2停用）
     */
    @TableField("status")
    private String status;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField("del_flag")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @TableField("login_ip")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @TableField("login_date")
    private LocalDateTime loginDate;

    /**
     * 创建者
     */
    @TableField("create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    @TableField("update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;
    public SysUser(String deptId, String userName, String loginName, String userType, String email, String phoneNumber, String s, String defaultPwd, String one, String remark, LocalDateTime now) {
        this.deptId = deptId;
        this.userName= userName;
        this.loginName = loginName;
        this.userType =userType;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password =defaultPwd;
        this.sex = one;
        this.remark =remark;
        this.createTime =now;
    }
}
