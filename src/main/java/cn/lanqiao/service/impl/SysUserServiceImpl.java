package cn.lanqiao.service.impl;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.*;
import cn.lanqiao.constant.UserConstant;
import cn.lanqiao.entity.SysDept;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.mapper.SysDeptMapper;
import cn.lanqiao.mapper.SysUserMapper;
import cn.lanqiao.query.SysUserQuery;
import cn.lanqiao.service.ISysDeptService;
import cn.lanqiao.service.ISysUserService;
import cn.lanqiao.vo.UserListVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.util.BeanUtil;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author ljh
 * @since 2022-05-23
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Resource
    private SysUserMapper sysUserMapper;
//    直接注入service
    @Resource
    private ISysDeptService sysDeptService;
    @Override
    public JsonResult getList(BaseQuery query) {
        //将查询条件转为用户查询条件
        SysUserQuery sysUserQuery = (SysUserQuery) query;
//        创建一个条件构造器
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        //判断不为空的时候拼接条件
        if(!StringUtils.isEmpty(sysUserQuery.getPhoneNumber())){
            queryWrapper.like("phone_number",sysUserQuery.getPhoneNumber());
        }//判断不为空的时候拼接条件
        if(!StringUtils.isEmpty(sysUserQuery.getLoginName())){
            queryWrapper.like("login_name",sysUserQuery.getLoginName());
        }
        if(!StringUtils.isEmpty(sysUserQuery.getDeptId())){
           //根据部门名称 查询所有用户的信息
            String deptId = sysUserQuery.getDeptId();
            if (!"100".equals(deptId))queryWrapper.eq("dept_id",deptId);
        }
//        存在的用户
        queryWrapper.eq("del_flag", "0");
        queryWrapper.orderByDesc("id");
        Page<SysUser> page = new Page<>(sysUserQuery.getPage(), sysUserQuery.getLimit(), true);
        IPage<SysUser> data = sysUserMapper.selectPage(page, queryWrapper);
//        得到所有用户集合
        List<SysUser> userList = data.getRecords();
//        创建一个vo的集合
        List<UserListVo> userListVoList = new ArrayList<>();
        if(!userList.isEmpty()){
//            获取到所有的部门信息
            List<SysDept> deptList = sysDeptService.list();
            Map<String, SysDept> sysDeptMap = deptList.stream().collect(Collectors.toMap(SysDept::getId, sysDept -> sysDept));
//            将每一个用户加入到vo类中 copy
            userList.forEach(user->{
                UserListVo userListVo = new UserListVo();
                BeanUtils.copyProperties(user,userListVo);
//                转为用户的性别
                if(!StringUtils.isEmpty(userListVo.getSex())){
                    userListVo.setSex(UserConstant.USER_SEX_LIST.get(userListVo.getSex()));
                }
//                转为用户的类型
                if(!StringUtils.isEmpty(userListVo.getUserType())){
                    userListVo.setUserType(UserConstant.USER_TYPE_LIST.get(userListVo.getUserType()));
                }
//                如果部门id不为空
                if(StringUtils.isNotEmpty(userListVo.getDeptId())){
//                    得到部门名称
                    String deptName = sysDeptMap.get(userListVo.getDeptId()).getDeptName();
                    userListVo.setDeptName(deptName);
                }
              userListVoList.add(userListVo);
            });
        }
        return JsonResult.success("操作成功", userListVoList, data.getTotal());
    }

    @Override
    public Object getInfo(Long id) {
        SysUser entity = (SysUser) getById(id);
        return entity;
    }

    @Override
    public JsonResult edit(SysUser entity) {
        boolean result = false;
        if (entity.getId() != null || StringUtils.isNotEmpty(entity.getId())) {
// 修改记录
// 判断是否用户名已存在
            Integer count = sysUserMapper.selectCount(new
                    LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getLoginName, entity.getLoginName())
                    .ne(SysUser::getId, entity.getId())
                    .eq(SysUser::getDelFlag, 0));
            if (count > 0) {
                return JsonResult.error("当前用户名已存在");
            }
            entity.setUpdateTime(DateUtils.now());
            entity.setUpdateBy(null);
            result = this.updateById(entity);
        } else {
// 新增记录
// 判断是否用户名已存在
            Integer count = sysUserMapper.selectCount(new
                    LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getLoginName, entity.getLoginName())
                    .eq(SysUser::getDelFlag, 0));
            if (count > 0) {
                return JsonResult.error("当前用户名已存在");
            }
            entity.setCreateBy(null);
            entity.setCreateTime(DateUtils.now());
            entity.setDelFlag("0");
            entity.setAvatar(UpdateUtils.DEFAUlT_IMG);
            result = this.save(entity);
        }
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    @Override
    public JsonResult deleteByIds(String ids) {
        String[] item = ids.split(",");
// 设置del_flag=0
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<SysUser>();
        updateWrapper.set("del_flag", 1).in("id",item);
        boolean result = update(updateWrapper);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    @Override
    public JsonResult setStatus(SysUser entity) {
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

}
