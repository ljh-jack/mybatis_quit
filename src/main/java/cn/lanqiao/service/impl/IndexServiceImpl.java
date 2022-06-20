package cn.lanqiao.service.impl;

import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.DateUtils;
import cn.lanqiao.entity.SysMenu;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.mapper.SysMenuMapper;
import cn.lanqiao.mapper.SysUserMapper;
import cn.lanqiao.service.IndexService;
import cn.lanqiao.vo.home.HomeInfo;
import cn.lanqiao.vo.home.LogoInfo;
import cn.lanqiao.vo.home.MenuInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class IndexServiceImpl extends BaseController implements IndexService {
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private   LogoInfo logoInfo;
    @Autowired
    private   HomeInfo homeInfo;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Override
    public  Map<String,Object> getIndexInfo() {
        //获取所有菜单
        String userId = getLoginSysUser().getId();
        SysUser sysUser = new SysUser();
        sysUser.setId(userId);
        sysUser.setLoginIp("127.0.0.1");
        sysUser.setLoginDate(DateUtils.now());
        userMapper.updateById(sysUser);
        List<SysMenu> sysMenus = sysMenuMapper.findMenuById(userId);
        List<MenuInfo> menuInfos = MenuInfo.convertMenuInfo(sysMenus);
        //配接出前端需要的结构
        Map<String,Object> menuMap = new HashMap<>();
        menuMap.put("homeInfo",homeInfo);
        menuMap.put("logoInfo",logoInfo);
        menuMap.put("menuInfo",menuInfos);
        return menuMap;

    }
    @Override
    public boolean updatePwd(String oldPassword, String newPassword) {
        SysUser sysUser = userService.getById(getLoginSysUser().getId());
        boolean matches = passwordEncoder.matches(oldPassword, getLoginSysUser().getPassword());
        if(!matches){
            return  false;
        }else {
        sysUser.setPassword(passwordEncoder.encode(newPassword));
        sysUser.setUpdateTime(DateUtils.now());
        userService.updateById(sysUser);
        }
        return true;
    }
}
