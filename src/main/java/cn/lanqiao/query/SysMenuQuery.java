package cn.lanqiao.query;

import cn.lanqiao.common.BaseController;
import cn.lanqiao.common.BaseQuery;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/1 19:50
 */
@Data
public class SysMenuQuery  extends BaseQuery {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单ID
     */
    private String id;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父菜单ID
     */

    private String parentId;

    /**
     * 显示顺序
     */

    private String orderNum;

    /**
     * 请求地址
     */

    private String url;

    /**
     * 菜单类型（M目录 C菜单 F按钮）
     */

    private String menuType;

    /**
     * 菜单图标
     */

    private String icon;

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


}
