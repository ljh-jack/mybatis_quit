package cn.lanqiao.service.impl;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.DateUtils;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.constant.UserConstant;
import cn.lanqiao.entity.SysUser;
import cn.lanqiao.mapper.SysUserMapper;
import cn.lanqiao.query.SysUserQuery;
import cn.lanqiao.service.ISysUserService;
import cn.lanqiao.vo.UserListVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
        if(!StringUtils.isEmpty(sysUserQuery.getUsername())){
            queryWrapper.like("phone_name",sysUserQuery.getUsername());
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
              userListVoList.add(userListVo);
            });
        }
        return JsonResult.success("操作成功", userListVoList, data.getTotal());
    }

    @Override
    public Object getInfo(Long id) {
        return null;
    }

    @Override
    public JsonResult edit(SysUser entity) {
        return null;
    }

    @Override
    public JsonResult deleteByIds(String ids) {
        return null;
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
