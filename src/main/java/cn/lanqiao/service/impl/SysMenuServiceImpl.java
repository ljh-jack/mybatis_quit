package cn.lanqiao.service.impl;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.DateUtils;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.entity.SysMenu;
import cn.lanqiao.entity.SysRoleMenu;
import cn.lanqiao.mapper.SysMenuMapper;
import cn.lanqiao.mapper.SysRoleMenuMapper;
import cn.lanqiao.query.SysMenuQuery;
import cn.lanqiao.service.ISysMenuService;
import cn.lanqiao.vo.MenuListVo;
import cn.lanqiao.vo.tree.TreeVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService {
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Autowired
    private  SysMenuMapper menuMapper;
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
    @Override
    public JsonResult getList(BaseQuery query) {
        SysMenuQuery sysMenuQuery = (SysMenuQuery) query;
        QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(sysMenuQuery.getMenuName())) {
            queryWrapper.like("menu_name",sysMenuQuery.getMenuName());
        }
        ArrayList<MenuListVo> menuListVoList = new ArrayList<>();
        List<SysMenu> menuList = menuMapper.selectList(queryWrapper);
        if(!menuList.isEmpty()){
            menuList.forEach(menu->{
                MenuListVo menuListVo = new MenuListVo();
                BeanUtils.copyProperties(menu,menuListVo);
                menuListVoList.add(menuListVo);
            });
        }
        return JsonResult.success("操作成功",menuListVoList,menuList.size());
    }

    @Override
    public Object getInfo(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    @Transactional
    public JsonResult deleteByIds(String id) {
        if (StringUtils.isNotEmpty(id)) {
            int i = menuMapper.del(id);
            if (i > 0) {
                return JsonResult.success("删除成功");
            }
        }
        return JsonResult.error("删除失败");
    }

    @Override
    @Transactional
    public JsonResult edit(SysMenu entity) {
        JsonResult result = new JsonResult();
        if (entity.getId() != null || StringUtils.isNotEmpty(entity.getId())) {
            //修改记录
            //判断用户是否存在
            LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getId, entity.getId());
            Integer count = menuMapper.selectCount(wrapper);
            if (count > 0) {
                entity.setUpdateTime(DateUtils.now());
                entity.setUpdateBy(null);
                entity.setIcon("fa "+entity.getIcon());
                int update = menuMapper.updateById(entity);
                if (update > 0) {
                    result = JsonResult.success("修改成功!");
                } else {
                    result = JsonResult.error("修改失败!");
                }
            }
        } else {
            Integer count1 = menuMapper.selectCount(new LambdaQueryWrapper<SysMenu>()
                    .eq(SysMenu::getMenuName, entity.getMenuName()));
            if (count1 <= 0) {
                entity.setCreateBy(null);
                entity.setCreateTime(DateUtils.now());
                entity.setIcon("fa "+entity.getIcon());
                boolean b = this.save(entity);
                if (b) {
                    result = JsonResult.success("添加成功！");
                } else {
                    result = JsonResult.success("添加失败！");
                }
            } else {
                result = JsonResult.error("该菜单已经存在");
            }
        }
        return result;
    }

    @Override
    public List<SysMenu> findMenuByParent(String type, String url) {
        return menuMapper.findMenuByParent(type, url);
    }
}
