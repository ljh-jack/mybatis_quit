package cn.lanqiao.mapper;

import cn.lanqiao.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    int addRole(SysRole entity);

    @Select("SELECT sr.* FROM `sys_user_role` sur inner JOIN sys_role sr on sur.role_id = sr.id where sur.user_id = #{userId}")
    List<SysRole> findRoleByUserId(Long userId);
}
