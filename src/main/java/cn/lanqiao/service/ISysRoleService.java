package cn.lanqiao.service;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 角色信息表 服务类
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
public interface ISysRoleService extends IService<SysRole> {
    /**
     * 根据查询条件获取数据列表
     * @param query 查询条件
     * @return
     */
    JsonResult getList(BaseQuery query);

    /**
     *根据ID获取记录信息
     * @param id 记录ID
     * @return
     */
    Object getInfo(Long id);

    /**
     * 添加用户并且添加权限信息
     * @param entity
     * @param menuIds
     * @return
     */
    JsonResult edit(SysRole entity, String menuIds);

    /**
     * 根据ID删除记录
     * @param ids
     * @return
     */
    JsonResult deleteByIds(String ids);

}
