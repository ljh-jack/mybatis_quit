package cn.lanqiao.service;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
public interface ISysDeptService extends IService<SysDept> {

    /**
     * 查询部门获取部门列表
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
     * 根据实体对象添加、编辑记录
     * @param entity
     * @return
     */
    JsonResult edit(SysDept entity);

    /**
     * 根据ID删除记录
     * @param id
     * @return
     */
    JsonResult deleteByIds(String id);

    /**
     * 设置状态
     * @param entity
     * @return
     */
    JsonResult setStatus(SysDept entity);
}
