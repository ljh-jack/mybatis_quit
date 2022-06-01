package cn.lanqiao.service.impl;

import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.entity.SysMenu;
import cn.lanqiao.entity.SysRoleMenu;
import cn.lanqiao.mapper.SysMenuMapper;
import cn.lanqiao.mapper.SysRoleMenuMapper;
import cn.lanqiao.service.ISysMenuService;
import cn.lanqiao.vo.tree.TreeVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Override
    public List<TreeVo> getListById(String roleId) {
        if(StringUtils.isEmpty(roleId)){
            return TreeVo.convertTreeVo(this.list(),null);
        }
        else {
            List<SysRoleMenu> menuList = roleMenuMapper.selectList(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
            if(menuList!=null){
                //获取有权限的id
                List<String> menuIds = menuList.stream().map(SysRoleMenu::getMenuId).collect(Collectors.toList());
                return TreeVo.convertTreeVo(this.list(),null,menuIds);
            }
            return null;
        }
    }
}
