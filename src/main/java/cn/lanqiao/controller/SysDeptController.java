package cn.lanqiao.controller;


import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.entity.SysDept;
import cn.lanqiao.query.SysDeptQuery;
import cn.lanqiao.vo.tree.TreeNodeVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
@Controller
@RequestMapping(SysDeptController.SOURCE)
public class SysDeptController extends BaseController {
    static final String SOURCE = "sysDept";
    /**
     * 返回用户页面
     * @return
     */
    @RequestMapping("toIndex")
    public String toIndex(){
        return SysDeptController.SOURCE+"/index";
    }
    @RequestMapping("/list")
    @ResponseBody
    public JsonResult list(){
        return deptService.getList();
    }
    @RequestMapping("/setStatus")
    @ResponseBody
    public JsonResult setStatus(SysDept entity) {
        return deptService.setStatus(entity);
    }
    /**
     * 添加部门页面
     * @return
     */
    @RequestMapping("/addUI")
    public String addUI() {
        return SysDeptController.SOURCE+"/add";
    }
    /**
     * 添加部门
     * @param entity 实体对象
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public JsonResult add(@RequestBody SysDept entity) {

        return deptService.edit(entity);
    }

    @RequestMapping("/listTree")
    @ResponseBody
    public List<TreeNodeVo> listTree(SysDeptQuery query){
        List<SysDept> list = deptService.list();
return TreeNodeVo.convertTreeNodeVo(list,"0");

    }
    /**
     * 添加部门页面
     * @return
     */
    @RequestMapping("/editUI")
    public ModelAndView editUI(Long id, ModelAndView view) {
        SysDept sysDept =(SysDept)deptService.getInfo(id);
        view.addObject("info",sysDept);
        view.setViewName(SysDeptController.SOURCE+"/edit");
        return view ;
    }
    /**
     * 修改部门
     * @param entity
     * @return
     */
    @ResponseBody
    @PostMapping("/edit")
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
    public JsonResult delete(String id) {
        return deptService.deleteByIds(id);
    }



}

