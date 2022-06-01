package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysDept;
import cn.lanqiao.entity.SysRole;
import cn.lanqiao.query.SysRoleQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

/**
 * <p>
 * 角色信息表 前端控制器
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
@Controller
@RequestMapping(SysRoleController.SOURCE)
public class SysRoleController extends BaseController {
    static final String SOURCE = "sysRole";
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult list(SysRoleQuery srq){
        return  roleService.getList(srq);
    }
    /**
     * 返回用户页面
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(){
        return SysRoleController.SOURCE+"/index";
    }
    /**
     * 按id删除角色
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    public JsonResult delete(String ids) {
        return roleService.deleteByIds(ids);
    }
    /**
     * 编辑用户页面
     * @return
     */
    @RequestMapping("/editUI")
    public ModelAndView editUI(Long id, ModelAndView view) {
        SysRole sysRole =(SysRole)roleService.getInfo(id);
        view.addObject("info", sysRole);
        view.setViewName(SysRoleController.SOURCE+"/edit");
        return view ;
    }
    @RequestMapping("/addUI")
    public String addUI() {
        return SysRoleController.SOURCE+"/add";
    }
    /**
     * 添加角色
     * @param entity 实体对象
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody SysRoleQuery entity) {
        SysRole sysRole = new SysRole();
        // 拷贝属性
        BeanUtils.copyProperties(entity, sysRole);
        return roleService.edit(sysRole,entity.getMenuIds());
    }


}

