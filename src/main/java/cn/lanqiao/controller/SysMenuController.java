package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.vo.tree.TreeVo;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 菜单权限表 前端控制器
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
@RestController
@RequestMapping(SysMenuController.SOURCE)
public class SysMenuController extends BaseController {
    static final String SOURCE = "sysMenu";
    /**
     * 返回权限树
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping ("/listTree")
    public List<TreeVo> listTree(String  roleId) {
        return menuService.getListById(roleId);
    }
}

