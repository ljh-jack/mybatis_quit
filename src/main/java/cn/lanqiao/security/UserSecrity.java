package cn.lanqiao.security;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserSecrity  implements UserDetails {

    /**
     * 登录账号
     */
    private String username;

    /**
     * 用户类型（00系统用户 01普通用户）
     */
    private String userType;

    /**
     * 头像路径
     */
    private String avatar;

    /**
     * 密码
     */
    private String password;

    /**
     * 盐加密
     */
    private String salt;

    /**
     * 帐号状态（1正常 2停用）
     */
    private String status;

    /**
     * 主键ID
     */
    private String id;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;



    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public UserSecrity(String username, String userType, String avatar, String password, String salt, String status, String id, String delFlag) {
        this.username = username;
        this.userType = userType;
        this.avatar = avatar;
        this.password = password;
        this.salt = salt;
        this.status = status;
        this.id = id;
        this.delFlag = delFlag;
    }

    @TableField(exist = false)
    List<GrantedAuthority> grantedAuthorities=new ArrayList<GrantedAuthority>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getGrantedAuthorities();
    }
    public List<GrantedAuthority> getGrantedAuthorities() {
        return grantedAuthorities;
    }
    public void setGrantedAuthorities(List<GrantedAuthority> grantedAuthorities) {
        this.grantedAuthorities = grantedAuthorities;
    }
    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return true;
    }
    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        if("0".equals(this.getDelFlag()) && "1".equals(this.getStatus())){
            return true;
        }
        return false;
    }



}