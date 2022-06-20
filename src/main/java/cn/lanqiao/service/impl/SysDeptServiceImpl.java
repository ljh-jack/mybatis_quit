package cn.lanqiao.service.impl;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.DateUtils;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.entity.SysDept;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.mapper.SysDeptMapper;
import cn.lanqiao.mapper.SysUserMapper;
import cn.lanqiao.service.ISysDeptService;
import cn.lanqiao.vo.DeptListVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {

    @Resource
    protected SysDeptMapper sysDeptMapper;
    @Resource
    protected SysUserMapper sysUserMapper;

    @Override
    public JsonResult getList(BaseQuery query) {
//        SysDeptQuery sysDept = (SysDeptQuery)query;
        //查询条件
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();

//        queryWrapper.ne("creat_by",sysDept.getCreatBy());
        queryWrapper.orderByDesc("order_num");

        List<SysDept> deptList = sysDeptMapper.selectList(queryWrapper);
        List<DeptListVo> deptListVoList = new ArrayList<>();
        if (!deptList.isEmpty()){
            deptList.forEach(item -> {
                DeptListVo deptListVo = new DeptListVo();
                //拷贝属性
                BeanUtils.copyProperties(item,deptListVo);
                deptListVoList.add(deptListVo);
            });
        }
        return JsonResult.success("操作成功",deptListVoList);
    }

    @Override public Object getInfo(Long id) { return sysDeptMapper.selectById(id); }

    @Override
    @Transactional
    public JsonResult edit(SysDept entity) {
        boolean result = false;
        if (entity.getId() != null || StringUtils.isNotEmpty(entity.getId())){
            //修改记录
            //判断是否部门名已经存在
            Integer count = sysDeptMapper.selectCount(
                    new LambdaQueryWrapper<SysDept>()
                            .eq(SysDept::getDeptName, entity.getDeptName())
                            .eq(SysDept::getParentId, entity.getParentId())
                            .ne(SysDept::getId, entity.getId())
            );
            if (count>0){
                return JsonResult.error("当前部门已经存在");
            }
            entity.setUpdateTime(DateUtils.now());
            entity.setUpdateBy(null);
            result = this.updateById(entity);
        }else{
            //新增记录
            //判断是否部门名已经存在
            Integer count = sysDeptMapper.selectCount(
                    new LambdaQueryWrapper<SysDept>()
                            .eq(SysDept::getDeptName, entity.getDeptName())
                            .eq(SysDept::getParentId, entity.getParentId())
            );
            if (count>0){
                return JsonResult.error("当前部门已存在");
            }
            entity.setStatus("1");
            entity.setCreateBy(null);
            entity.setCreateTime(DateUtils.now());
            result = this.save(entity);
        }
        if (!result){
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    @Override
    @Transactional
    public JsonResult deleteByIds(String id) {
        /*
        删除不能删除的情况：1、部门下面有子部门
                         2、部门为最上级部门
                         3、部门下有员工
         */
        //校验是否为最高级部门
        if(id.equals("100"))return JsonResult.error("当前部门为最高级部门，不能直接删除");

        //校验部门下是否有员工
        Integer userCount = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>().eq(SysUser::getDeptId, id));
        if (userCount>0)return JsonResult.error("当前部门下有员工，不能直接删除");

        //校验是否有子元素
        Integer deptCount = sysDeptMapper.selectCount(
                new LambdaQueryWrapper<SysDept>()
                        .eq(SysDept::getParentId, id)
        );
        if (deptCount>0){
            return JsonResult.error("当前部门存在子部门，不能直接删除");
        }
        int delete = sysDeptMapper.deleteById(id);
        if (delete==0){
            return JsonResult.error("删除失败");
        }
        return JsonResult.success();
    }

    @Override
    public JsonResult setStatus(SysDept entity) {
        if (StringUtils.isEmpty(entity.getId())){
            return JsonResult.error("ID不能为空");
        }
        if (StringUtils.isEmpty(entity.getStatus())){
            return JsonResult.error("状态不能为空");
        }
        entity.setUpdateTime(DateUtils.now());
        entity.setUpdateBy(null);
        boolean status = this.updateById(entity);
        if (!status){
            return JsonResult.error("修改失败");
        }
        return JsonResult.success();
    }
}
