package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysMenu;
import cn.lanqiao.query.SysMenuQuery;
import cn.lanqiao.vo.tree.TreeVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/13 12:02
 */
@Controller
@RequestMapping(SysMenuController.SOURCE)
public class SysMenuController extends BaseController {
    public static final String SOURCE="sysMenu";

    /**
     * 返回菜单主页面
     *
     * @return
     */
    @RequestMapping("/toIndex")
    @PreAuthorize("hasAuthority('/sysMenu/toIndex')")
    public String toIndex() {
        return SysMenuController.SOURCE + "/index";
    }

    /**
     * 添加菜单页面
     * @return
     */
    @RequestMapping("/addUI")
    public String addUI() {
        return SysMenuController.SOURCE + "/add";
    }

    /**
     * 修改菜单页面
     * @return
     */
    @RequestMapping("/editUI")
    @PreAuthorize("hasAuthority('/sysMenu/updateSysMenu')")
    public ModelAndView editUI(Long id, ModelAndView view) {
        SysMenu sysMenu =(SysMenu) menuService.getInfo(id);
        view.addObject("info", sysMenu);
        view.setViewName(SysMenuController.SOURCE + "/edit");
        return view;
    }

    /**
     * 返回权限树
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping ("/listTree")
    @PreAuthorize("hasAnyAuthority('/sysDept/findSysDeptPage','/sysUser/findSysUserPage','/sysMenu/findSysMenuPage','/sysRole/findSysRolePage')")
    public List<TreeVo> listTree(String  roleId) {
        List<TreeVo> listTree = menuService.getListById(roleId);
        return listTree;
    }

    /**
     * 查询所有权限
     *
     * @param smq
     * @return
     */
    @ResponseBody //回传json数据
    @RequestMapping("/list")
    @PreAuthorize("hasAuthority('/sysMenu/findAllSysMenu')")
    public JsonResult list(SysMenuQuery smq) {
        return menuService.getList(smq);
    }

    /**
     * 添加或者修改菜单权限信息
     * @param entity
     * @return
     */
    @RequestMapping("/edit")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('/sysMenu/updateSysMenu','/sysMenu/saveSysMenu')")
    public JsonResult edit(@RequestBody SysMenu entity){
        return menuService.edit(entity);
    }
    /**
     * 根据菜单id进行删除
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('/sysMenu/deleteSysMenu')")
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delById(String id){
        return menuService.deleteByIds(id);
    }
}

