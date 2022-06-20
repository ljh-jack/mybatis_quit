package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysRole;
import cn.lanqiao.query.SysRoleQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/13 12:02
 */
@Controller
@RequestMapping(SysRoleController.SOURCE)
public class SysRoleController extends BaseController {

    public static final String SOURCE="sysRole";

    /**
     * 返回角色主页面
     * @return
     */
    @PreAuthorize("hasAuthority('/sysRole/toIndex')")
    @RequestMapping("/toIndex")
    public String toIndex() {
        return SysRoleController.SOURCE + "/index";
    }

    /**
     * 添加角色页面
     * @return
     */
    @PreAuthorize("hasAuthority('/sysRole/saveSysRole')")
    @RequestMapping("/addUI")
    public String addUI() {
        return SysRoleController.SOURCE + "/add";
    }

    /**
     * 修改角色页面
     * @return
     */
    @PreAuthorize("hasAuthority('/sysRole/updateSysRole')")
    @RequestMapping("/editUI")
    public ModelAndView editUI(Long id, ModelAndView view) {
        SysRole sysRole = (SysRole) roleService.getInfo(id);
        view.addObject("info", sysRole);
        view.setViewName(SysRoleController.SOURCE + "/edit");
        return view;
    }

    /**
     * 查询角色
     * @param query
     * @return
     */
    @ResponseBody //回传json数据
    @RequestMapping("/list")
    @PreAuthorize("hasAnyAuthority('/sysDept/findSysDeptPage','/sysUser/findSysUserPage','/sysMenu/findSysMenuPage','/sysRole/findSysRolePage')")
    public JsonResult list(SysRoleQuery query) {
        return roleService.getList(query);
    }

    /**
     * 添加角色
     * @param entity 实体对象
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('/sysRole/updateSysRole','/sysRole/saveSysRole')")
    public JsonResult add(@RequestBody SysRoleQuery entity) {
        SysRole sysRole = new SysRole();
        // 拷贝属性
        BeanUtils.copyProperties(entity, sysRole);
        return roleService.edit(sysRole,entity.getMenuIds());
    }

    /**
     * 按id删除角色
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAuthority('/sysRole/deleteSysRole')")
    public JsonResult delete(String ids) {
        return roleService.deleteByIds(ids);
    }

}

