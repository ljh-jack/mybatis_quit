package cn.lanqiao.service.impl;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.DateUtils;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.entity.SysDept;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.mapper.SysDeptMapper;
import cn.lanqiao.mapper.SysUserMapper;
import cn.lanqiao.query.SysDeptQuery;
import cn.lanqiao.service.ISysDeptService;
import cn.lanqiao.vo.DeptListVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements ISysDeptService {
@Resource
private SysDeptMapper sysDeptMapper;
@Resource
private SysUserMapper sysUserMapper;
    @Override
    public JsonResult getList() {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNotNull("dept_name");
        List<SysDept> deptList = sysDeptMapper.selectList(queryWrapper);
        ArrayList<DeptListVo> deptListVoList= new ArrayList<>();
        if(!deptList.isEmpty()){
       deptList.forEach(item->{
           DeptListVo deptListVo = new DeptListVo();
           BeanUtils.copyProperties(item,deptListVo);
           deptListVoList.add(deptListVo);
       });
   }
        return JsonResult.success("操作成功",deptListVoList,deptList.size());
    }

    @Override
    public JsonResult setStatus(SysDept entity) {
        if(StringUtils.isEmpty(entity.getId())){
            return  JsonResult.error("ID不能为空");
        }if(StringUtils.isEmpty(entity.getStatus())){
            return  JsonResult.error("状态不能为空");
        }
        entity.setUpdateBy(null);
        entity.setUpdateTime(DateUtils.now());
        boolean result = this.updateById(entity);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    @Override
    public JsonResult edit(SysDept entity) {
        boolean result  = false;
        if(entity.getId()!=null || StringUtils.isNotEmpty(entity.getId())){
            LambdaQueryWrapper<SysDept> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(SysDept::getDeptName,entity.getDeptName());
            lambdaQueryWrapper.eq(SysDept::getParentId,entity.getParentId());
            lambdaQueryWrapper.ne(SysDept::getId,entity.getId());
            Integer count = sysDeptMapper.selectCount(lambdaQueryWrapper);
            if(count>0){
                return  JsonResult.error("用户已经存在");
            }
        }
        LambdaQueryWrapper<SysDept> lambdaQueryWrapper2 = new LambdaQueryWrapper<>();
        lambdaQueryWrapper2.eq(SysDept::getDeptName,entity.getDeptName());
        lambdaQueryWrapper2.eq(SysDept::getParentId,entity.getParentId());
        Integer count = sysDeptMapper.selectCount(lambdaQueryWrapper2);
        if(count>0){
            return  JsonResult.error("用户已经存在");
        }else {
            entity.setStatus("1");
            entity.setCreateBy(null);
            entity.setUpdateTime(DateUtils.now());
            entity.setCreateTime(DateUtils.now());
            result = this.save(entity);
        }
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    @Override
    public  Object  getInfo(Long id) {
        return sysDeptMapper.selectById(id);
    }

    @Override
    public JsonResult deleteByIds(String id) {
        Integer integer = sysUserMapper.selectCount(new LambdaQueryWrapper<SysUser>().eq(SysUser::getDeptId, id));
        if(integer>0){
            return JsonResult.error("当前部门存在员工，不能删除");
        };
//        查询是不是最高部门
        if(id.equals("100")){
            return JsonResult.error("当前部门为最高级别，不能直接删除");
        }
        Integer count = sysDeptMapper.selectCount(new
                LambdaQueryWrapper<SysDept>()
                .eq(SysDept::getParentId,id)
        );

        if(count>0){
            return JsonResult.error("当前部门存在子部门，不能直接删除");
        };
        int i = sysDeptMapper.deleteById(id);
        if(i==0){
            return JsonResult.error("删除失败");
        }
        return   JsonResult.success();

    }
}

