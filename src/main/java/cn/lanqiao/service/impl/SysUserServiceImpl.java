package cn.lanqiao.service.impl;

import cn.lanqiao.common.BaseQuery;
import cn.lanqiao.common.utils.DateUtils;
import cn.lanqiao.common.utils.JsonResult;
import cn.lanqiao.common.utils.StringUtils;
import cn.lanqiao.common.utils.UpdateUtils;
import cn.lanqiao.constant.LoginConstant;
import cn.lanqiao.constant.UserConstant;
import cn.lanqiao.entity.*;
import cn.lanqiao.mapper.SysMenuMapper;
import cn.lanqiao.mapper.SysRoleMapper;
import cn.lanqiao.mapper.SysUserMapper;
import cn.lanqiao.mapper.SysUserRoleMapper;
import cn.lanqiao.query.SysUserQuery;
import cn.lanqiao.service.ISysDeptService;
import cn.lanqiao.service.ISysUserRoleService;
import cn.lanqiao.service.ISysUserService;
import cn.lanqiao.utils.ExcelUtil;
import cn.lanqiao.vo.UserListVo;
import cn.lanqiao.vo.UserRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author  Ljh
 * @since 2022-05-23
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Resource
    protected SysUserMapper userMapper;

    @Resource
    private SysRoleMapper roleMapper;

    @Resource
    protected ISysDeptService sysDeptService;
    @Resource
    private ISysUserRoleService userRoleService;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private BCryptPasswordEncoder passwordEncoder;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public JsonResult getList(BaseQuery query) {
        SysUserQuery sysUserQuery = (SysUserQuery) query;
        //查询条件
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        //人员姓名/手机号
        if (!StringUtils.isEmpty(sysUserQuery.getUserName())) {
            queryWrapper.like("user_name", sysUserQuery.getUserName());
        }
        if (!StringUtils.isEmpty(sysUserQuery.getPhoneNumber())) {
            queryWrapper.like("phone_number", sysUserQuery.getPhoneNumber());
        }
        if (!StringUtils.isEmpty(sysUserQuery.getDeptId())) {
            String deptId = sysUserQuery.getDeptId();
            if (!"100".equals(deptId)) queryWrapper.eq("dept_id", deptId);
        }
        queryWrapper.eq("del_flag", "0");
        queryWrapper.orderByDesc("id");

        //查询数据 分页需要配置分页插件是否无法找到total
        IPage<SysUser> page = new Page<>(sysUserQuery.getPage(), sysUserQuery.getLimit(), true);
        IPage<SysUser> data = userMapper.selectPage(page, queryWrapper);

        List<SysUser> userList = data.getRecords();
        List<UserListVo> userListVoList = new ArrayList<>();
        if (!userList.isEmpty()) {
            List<SysDept> list = sysDeptService.list();
            Map<String, SysDept> mapDept = list.stream().collect(Collectors.toMap(SysDept::getId, sysDept -> sysDept));
            userList.forEach(item -> {
                UserListVo userListVo = new UserListVo();
                //拷贝属性
                BeanUtils.copyProperties(item, userListVo);
                //性别描述
                if (StringUtils.isNotEmpty(userListVo.getSex())) {
                    userListVo.setSex(UserConstant.USER_SEX_LIST.get(userListVo.getSex()));
                }
                //用户类型
                if (StringUtils.isNotEmpty(userListVo.getUserType())) {
                    userListVo.setUserType(UserConstant.USER_TYPE_LIST.get(userListVo.getUserType()));
                }
                //部门名称
                if (StringUtils.isNotEmpty(userListVo.getDeptId())) {
                    String deptName = mapDept.get(userListVo.getDeptId()).getDeptName();
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
        List<SysRole> roleByUserId = roleMapper.findRoleByUserId(id);
        UserRoleVo userRoleVo = new UserRoleVo();
        BeanUtils.copyProperties(entity, userRoleVo);
        if (roleByUserId.size() != 0) {
            StringBuilder userIds = new StringBuilder();
            StringBuilder roleNames = new StringBuilder();
            for (SysRole sysRole : roleByUserId) {
                userIds.append(sysRole.getId()).append(",");
                roleNames.append(sysRole.getRoleName()).append(",");
            }
            userIds.deleteCharAt(userIds.lastIndexOf(","));
            roleNames.deleteCharAt(roleNames.lastIndexOf(","));
            userRoleVo.setRoleId(userIds.toString());
            userRoleVo.setRoleName(roleNames.toString());
            return userRoleVo;
        }
        return userRoleVo;
    }

    @Override
    @Transactional
    public JsonResult edit(SysUser entity, String roleIds) {
        boolean result = false;
        if (entity.getId() != null || StringUtils.isNotEmpty(entity.getId())) {
            // 修改记录
            // 判断是否用户名已存在
            Integer count = userMapper.selectCount(new LambdaQueryWrapper<SysUser>()
                    .eq(SysUser::getLoginName, entity.getLoginName())
                    .ne(SysUser::getId, entity.getId())
                    .eq(SysUser::getDelFlag, 0));
            if (count > 0) {
                return JsonResult.error("当前用户名已存在");
            }
            entity.setUpdateTime(DateUtils.now());
            entity.setUpdateBy(null);
            boolean isSuccess = userRoleService.remove(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, entity.getId()));
            List<SysUserRole> srmList = new ArrayList<>();
            //添加映射菜单
            if (StringUtils.isNotEmpty(roleIds) && isSuccess) {
                String[] roles = roleIds.split(",");
                for (String roleId : roles) {
                    srmList.add(new SysUserRole(roleId, entity.getId()));
                }
                //保存角色和权限权限映射信息
                result = userRoleService.saveBatch(srmList);
            }
            //保存角色
            if (result) result = userMapper.updateById(entity) == 1;

        } else {
            // 新增记录
            // 判断是否用户名已存在
            Integer count = userMapper.selectCount(new LambdaQueryWrapper<SysUser>().eq(SysUser::getLoginName, entity.getLoginName()).eq(SysUser::getDelFlag, 1));
            if (count > 0) {
                return JsonResult.error("当前用户名已存在");
            }
            entity.setLoginIp("127.0.0.1");
            entity.setCreateBy(null);
            entity.setCreateTime(DateUtils.now());
            entity.setDelFlag("0");
            entity.setAvatar(UpdateUtils.DEFAUlT_IMG);
            String encode = passwordEncoder.encode("123456");
            entity.setPassword(encode);
            int num = userMapper.insert(entity);
            List<SysUserRole> srmList = new ArrayList<>();
            if (StringUtils.isNotEmpty(roleIds) && num > 0) {
                for (String roleId : roleIds.split(",")) {
                    srmList.add(new SysUserRole(roleId, entity.getId()));
                }
            }
            //保存角色和权限映射信息
            result = userRoleService.saveBatch(srmList);
        }
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success();
    }

    @Override
    @Transactional
    public JsonResult deleteByIds(String ids) {
        if (ids == null) return JsonResult.error();
        String[] item = ids.split(",");
        // 设置del_flag=0
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<SysUser>();
        updateWrapper.set("del_flag", 1).in("id", item);
        boolean result = update(updateWrapper);
        if (!result) {
            return JsonResult.error();
        }
        return JsonResult.success("删除成功");
    }

    @Override
    public JsonResult setStatus(SysUser entity) {
        if (StringUtils.isEmpty(entity.getId())) {
            return JsonResult.error("ID不能为空");
        }
        if (StringUtils.isEmpty(entity.getStatus())) {
            return JsonResult.error("状态不能为空");
        }
        entity.setUpdateTime(DateUtils.now());
        entity.setUpdateBy(null);
        boolean status = this.updateById(entity);
        if (!status) {
            return JsonResult.error("修改失败");
        }
        return JsonResult.success();
    }

    @Override
    public Map<String, Object> getByLoginName(String loginName) {
        HashMap<String, Object> map = new HashMap<>();
        SysUser sysUser = userMapper.selectOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getLoginName, loginName));
        if (sysUser != null) {
            Set<SysMenu> menuList = sysMenuMapper.selectMenusByUserId(sysUser.getId());
            map.put("user", sysUser);
            map.put("menu", menuList);
        }
        return map;
    }

    @Override
    public JsonResult uploadUsers(MultipartFile file) {
        if (file == null) {
            return JsonResult.error("上传失败");
        } else {
            final String man = "男", woman = "女", one = "1", two = "2", three = "3";
            List<SysUser> users = ExcelUtil.uploadFile(file).stream().map(userEntity -> new SysUser(
                    userEntity.getDeptId(),
                    userEntity.getUserName(),
                    userEntity.getLoginName(),
                    userEntity.getUserType(),
                    userEntity.getEmail(),
                    userEntity.getPhoneNumber(),
                    userEntity.getSex().contains(man) ? one : (userEntity.getSex().contains(woman) ? two : three),
                    UserConstant.DEFAULT_PWD,
                    one,
                    userEntity.getRemark(),
                    DateUtils.now()
            )).collect(Collectors.toList());
            //添加操作
            boolean saveBatch = this.saveBatch(users);
            if (!saveBatch){
                return JsonResult.success("上传失败");
            }
        }
        return JsonResult.success("上传成功");

        }

}
