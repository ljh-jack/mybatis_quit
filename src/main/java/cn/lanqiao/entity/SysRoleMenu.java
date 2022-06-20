package cn.lanqiao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author Ljh
 * @since 2022-05-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysRoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 角色ID
     */
    @TableField("role_id")
    private String roleId;

    /**
     * 菜单ID
     */
    @TableField("menu_id")
    private String menuId;

    public SysRoleMenu() {
    }

    public SysRoleMenu(String menuId, String roleId) {
        this.menuId=menuId;
        this.roleId=roleId;
    }

}
