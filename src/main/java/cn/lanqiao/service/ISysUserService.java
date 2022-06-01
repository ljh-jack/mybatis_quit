package cn.lanqiao.service;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户信息表 服务类
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
public interface ISysUserService extends IService<SysUser> {
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
     * 根据实体对象添加、编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult edit(SysUser entity);



    /**
     * 根据ID删除记录
     *
     * @param ids 记录ID
     * @return
     */
    JsonResult deleteByIds(String ids);

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    JsonResult setStatus(SysUser entity);

}
