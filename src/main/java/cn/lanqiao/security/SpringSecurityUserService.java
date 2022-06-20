package cn.lanqiao.security;

import cn.lanqiao.entity.SysMenu;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Component
public class SpringSecurityUserService implements UserDetailsService{
    //密文加密
    @Autowired
    private ISysUserService iSysUserService;
    /**
     * 根据用户名加载用户信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> mapList = iSysUserService.getByLoginName(username);//模拟根据用户名查询数据库
        SysUser userInDb = (SysUser)mapList.get("user");
        if(userInDb == null){
            //根据用户名没有查询到用户
            return null;
        }
        UserSecrity userSecrity = new UserSecrity(userInDb.getUserName(),userInDb.getUserType(),userInDb.getAvatar(),userInDb.getPassword(),
                userInDb.getSalt(),userInDb.getStatus(),userInDb.getId(),userInDb.getDelFlag());
        List<GrantedAuthority> list = new ArrayList<>();
        //授权，后期需要改为查询数据库动态获得用户拥有的权限和角色
        Set<SysMenu> menus = (Set<SysMenu>)mapList.get("menu");
        if(menus!=null){
            menus.forEach(item->{
                list.add(new SimpleGrantedAuthority(item.getUrl()));
            });
        }
        userSecrity.setGrantedAuthorities(list);
        return userSecrity;
    }
}