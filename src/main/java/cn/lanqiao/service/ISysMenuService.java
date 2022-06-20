package cn.lanqiao.service;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysMenu;
import cn.lanqiao.vo.tree.TreeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author  Ljh
 * @since 2022-05-23
 */
public interface ISysMenuService extends IService<SysMenu> {
    /*
            修改角色信息获取菜单
         */
    List<TreeVo> getListById(String id);



    /**
     * 根据查询条件获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    JsonResult getList(BaseQuery query);



    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */

    Object getInfo(Long id);

    /**
     * 根据ID删除记录
     *
     * @param ids 记录ID
     * @return
     */
    JsonResult deleteByIds(String ids);

    /**
     * 添加菜单并且添加权限信息
     * @param entity
     * @return
     */
    JsonResult edit(SysMenu entity);

    /**
     * 根据父节点查询菜单
     * @return
     */
    List<SysMenu> findMenuByParent(String type,String url);
}
