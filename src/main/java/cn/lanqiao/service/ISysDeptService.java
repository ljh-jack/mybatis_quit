package cn.lanqiao.service;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysDept;
import cn.lanqiao.query.SysDeptQuery;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
public interface ISysDeptService extends IService<SysDept> {


    JsonResult getList();

    JsonResult setStatus(SysDept entity);

    JsonResult edit(SysDept entity);

    Object  getInfo(Long id);

    JsonResult deleteByIds(String id);
}
