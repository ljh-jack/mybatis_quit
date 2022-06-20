package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysDept;
import cn.lanqiao.query.SysDeptQuery;
import cn.lanqiao.vo.tree.TreeNodeVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping(SysDeptController.SOURCE)
public class SysDeptController extends BaseController {

    public static final String SOURCE = "sysDept";

    /**
     * 返回部门主页面
     *
     * @return
     */
    @PreAuthorize("hasAuthority('/sysDept/toIndex')")
    @RequestMapping("/toIndex")
    public String toIndex() {
        return SysDeptController.SOURCE + "/index";
    }

    /**
     * 添加部门页面
     * @return
     */
    @PreAuthorize("hasAuthority('/sysDept/saveSysDept')")
    @RequestMapping("/addUI")
    public String addUI() {
        return SysDeptController.SOURCE + "/add";
    }

    /**
     * 修改部门页面
     * @return
     */
    @PreAuthorize("hasAuthority('/sysDept/updateSysDept')")
    @RequestMapping("/editUI")
    public ModelAndView editUI(Long id, ModelAndView view) {
        SysDept sysDept = (SysDept) deptService.getInfo(id);
        view.addObject("info", sysDept);
        view.setViewName(SysDeptController.SOURCE + "/edit");
        return view;
    }

    /**
     * 查询所有部门
     * @param query
     * @return
     */
    @PreAuthorize("hasAuthority('/sysDept/findSysDeptPage')")
    @ResponseBody //回传json数据
    @RequestMapping("/list")
    public JsonResult list(SysDeptQuery query) {
        return deptService.getList(query);
    }


    /**
     * 添加部门
     *
     * @param entity 实体对象
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('/sysDept/updateSysDept','/sysDept/saveSysDept')")
    public JsonResult add(@RequestBody SysDept entity) {
        return deptService.edit(entity);
    }

    /**
     * 返回权限树
     *
     * @param query
     * @return
     */
    @ResponseBody
    @RequestMapping("/listTree")
    @PreAuthorize("hasAnyAuthority('/sysDept/findSysDeptPage','/sysUser/findSysUserPage','/sysMenu/findSysMenuPage','/sysRole/findSysRolePage')")
    public List<TreeNodeVo> listTree(SysDeptQuery query) {
        //查询所有的部门
        List<SysDept> list = deptService.list();
        //将部门转换成部门树
        return TreeNodeVo.convertTreeNodeVo(list, "0");
    }

    /**
     * 修改部门
     * @param entity
     * @return
     */
    @ResponseBody
    @PostMapping("/edit")
    @PreAuthorize("hasAuthority('/sysDept/updateSysDept')")
    public JsonResult edit(@RequestBody SysDept entity) {
        return deptService.edit(entity);
    }

    /**
     * 按id删除部门
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @PreAuthorize("hasAnyAuthority('/sysDept/deleteSysDept')")
    public JsonResult delete(String id) {
        return deptService.deleteByIds(id);
    }

    /**
     * 修改状态
     *
     * @param sysDept
     * @return
     */
    @RequestMapping("/setStatus")
    @ResponseBody
    @PreAuthorize("hasAuthority('/sysDept/updateSysDept')")
    public JsonResult setStatus(SysDept sysDept) {
        return deptService.setStatus(sysDept);
    }
}

