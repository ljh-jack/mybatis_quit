package cn.lanqiao.query;

import cn.lanqiao.common.BaseQuery;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/5/30 19:41
 */
@Data
public class SysRoleQuery extends BaseQuery {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 创建者
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新者
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 备注
     */
    private String remark;
    /**
     * 角色id
     */
    private String menuIds;
}
