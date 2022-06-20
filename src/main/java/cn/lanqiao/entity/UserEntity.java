package cn.lanqiao.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/16 23:53
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@ExcelTarget("userEntity")
public class UserEntity implements Serializable {
    @Excel(name = "部门编号",isImportField = "true")
    private String deptId;
    @Excel(name = "姓名", isImportField = "true")
    private String loginName; //姓名
    @Excel(name = "性别", isImportField = "true")
    private String sex; //性别
    @Excel(name = "邮箱", isImportField = "true")
    private String email; //邮箱
    @Excel(name = "电话号码", isImportField = "true")
    private String phoneNumber; //邮箱
    @Excel(name = "用户昵称", isImportField = "true")
    private String userName;
    @Excel(name = "用户类型", isImportField = "true")
    private String userType;
    @Excel(name = "备注", isImportField = "true")
    private String remark;


}
