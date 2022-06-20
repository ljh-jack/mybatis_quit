package cn.lanqiao.service.impl;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.DateUtils;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.entity.SysRole;
import cn.lanqiao.entity.SysRoleMenu;
import cn.lanqiao.entity.SysUserRole;
import cn.lanqiao.mapper.SysRoleMapper;
import cn.lanqiao.mapper.SysRoleMenuMapper;
import cn.lanqiao.query.SysRoleQuery;
import cn.lanqiao.service.ISysRoleMenuService;
import cn.lanqiao.service.ISysRoleService;
import cn.lanqiao.vo.RoleListVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 角色信息表 服务实现类
 * </p>
 *
 * @author  Ljh
 * @since 2022-05-23
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

    @Override
    public JsonResult getList(BaseQuery query) {
        SysRoleQuery sysRoleQuery = (SysRoleQuery) query;
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        //角色名称
        if (!StringUtils.isEmpty(sysRoleQuery.getRoleName())){
            queryWrapper.like("role_name",sysRoleQuery.getRoleName());
        }

        //查询数据 分页需要配置分页插件是否无法找到total
        IPage<SysRole> page = new Page<>(sysRoleQuery.getPage(), sysRoleQuery.getLimit(), true);
        IPage<SysRole> data = sysRoleMapper.selectPage(page, queryWrapper);

        List<SysRole> roleList = data.getRecords();
        //返回给页面
        ArrayList<RoleListVo> roleListVoList = new ArrayList<>();
        //=============构造列表对象==============//
        if(!roleList.isEmpty()){
            roleList.forEach(role->{
                RoleListVo roleListVo = new RoleListVo();
                BeanUtils.copyProperties(role,roleListVo);
                roleListVoList.add(roleListVo);
            });
        }
        return JsonResult.success("操作成功",roleListVoList,data.getTotal());
    }

    @Override
    public Object getInfo(Long id) {
        return sysRoleMapper.selectById(id);
    }



    @Override
    @Transactional
    public JsonResult edit(SysRole entity, String menuIds) {
        boolean result = false;
        if (entity.getId() != null || StringUtils.isNotEmpty(entity.getId())) {
            // 修改记录
            // 判断是否角色名已存在
            Integer count = sysRoleMapper.selectCount(new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getRoleName, entity.getRoleName())
                    .ne(SysRole::getId, entity.getId()));
            if (count > 0) {
                return JsonResult.error("当前角色名已存在");
            }
            entity.setUpdateTime(DateUtils.now());
            entity.setUpdateBy(null);
            //删除菜单角色映射表
            boolean isSuccess =sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId,entity.getId()));
            List<SysRoleMenu> srmList = new ArrayList<>();
            //添加映射菜单
            if(StringUtils.isNotEmpty(menuIds) && isSuccess){
                String[] munus = menuIds.split(",");
                for (String menuId : munus) {
                    srmList.add(new SysRoleMenu(menuId,entity.getId()));
                }
                //保存角色和权限映射信息
                result =sysRoleMenuService.saveBatch(srmList);
            }
//            System.out.println(srmList.size());
            //保存角色
            if(result){
                result = sysRoleMapper.updateById(entity)==1;
            }

        } else {
            // 新增记录
            // 判断是否角色名已存在
            Integer count = sysRoleMapper.selectCount(new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getRoleName, entity.getRoleName()));
            if (count > 0) {
                return JsonResult.error("当前角色名已存在");
            }
            entity.setCreateBy(null);
            entity.setCreateTime(DateUtils.now());
            //添加用户并且返回用户id
            int num = sysRoleMapper.addRole(entity);
            List<SysRoleMenu> srmList = new ArrayList<>();
            if(StringUtils.isNotEmpty(menuIds) && num>0){
                for (String menuId : menuIds.split(",")) {
                    srmList.add(new SysRoleMenu(menuId,entity.getId()));
                }
                //保存角色和权限映射信息
                result = sysRoleMenuService.saveBatch(srmList);
            }

        }
        if (!result) {
            //如果失败回滚所有操作
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return JsonResult.error();
        }
        return JsonResult.success();
    }


    @Override
    @Transactional
    public JsonResult deleteByIds(String ids) {
        if(StringUtils.isNotEmpty(ids)){
            String[] item = ids.split(",");
            List<String> list = Arrays.asList(item);
            int num = sysRoleMapper.deleteBatchIds(list);
            boolean isSuccess = sysRoleMenuService.remove(new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, list));
            if(num>0 && isSuccess){
                return JsonResult.success("删除成功");
            }
        }
        //如果失败回滚所有操作
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        return JsonResult.error();
    }
}
