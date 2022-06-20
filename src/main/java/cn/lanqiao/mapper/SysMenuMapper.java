package cn.lanqiao.mapper;

import cn.lanqiao.entity.SysMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select sm.* from sys_menu sm,sys_role_menu srm,sys_user_role sur where srm.menu_id=sm.id and srm.role_id=sur.role_id and sur.user_id=#{id}")
    Set<SysMenu> selectMenusByUserId(String id);

    @Select("SELECT * from sys_menu where parent_id=(select id from sys_menu where url=#{url}) and menu_type=#{type}")
    List<SysMenu>  findMenuByParent(@Param("type") String type, @Param("url") String url);

    @Select("SELECT sm.* from sys_role_menu srm LEFT JOIN sys_menu sm on srm.menu_id=sm.id LEFT JOIN sys_user_role sur on sur.role_id = srm.role_id WHERE sur.user_id = #{id}")
    List<SysMenu> findMenuById(String id);

    @Delete("DELETE sm,srm FROM sys_menu sm LEFT JOIN sys_role_menu srm ON sm.id=srm.menu_id WHERE sm.id=#{id}")
    int del(String id);
}
