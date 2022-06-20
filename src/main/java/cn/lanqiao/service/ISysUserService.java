package cn.lanqiao.service;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/13 12:02
 */
public interface ISysUserService extends IService<SysUser> {

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
     * 根据实体对象添加、编辑记录
     * @param entity
     * @return
     */
    JsonResult edit(SysUser entity, String roleIds);

    /**
     * 根据ID删除记录
     * @param ids
     * @return
     */
    JsonResult deleteByIds(String ids);

    /**
     * 设置状态
     * @param entity
     * @return
     */
    JsonResult setStatus(SysUser entity);

    /**
     * 通过用户码获取登录信息
     * @param username
     * @return
     */
    Map<String,Object> getByLoginName(String username);
    JsonResult uploadUsers(MultipartFile file);
}
