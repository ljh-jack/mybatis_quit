package cn.lanqiao.service;

import cn.lanqiao.entity.SysMenu;
import cn.lanqiao.vo.tree.TreeVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
public interface ISysMenuService extends IService<SysMenu> {

    List<TreeVo> getListById(String roleId);
}
