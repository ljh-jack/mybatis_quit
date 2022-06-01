package cn.lanqiao.service;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysRole;
import cn.lanqiao.query.SysRoleQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
public interface ISysRoleService extends IService<SysRole> {

    JsonResult getList(BaseQuery query);

    JsonResult deleteByIds(String ids);

    Object getInfo(Long id);

    JsonResult edit(SysRole sysRole, String menuIds);
    
}
