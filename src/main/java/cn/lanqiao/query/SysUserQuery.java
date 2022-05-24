package cn.lanqiao.query;

import cn.lanqiao.common.BaseQuery;
import lombok.Data;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/5/23 19:14
 */
@Data
public class SysUserQuery extends BaseQuery {
    private String username;
    private  String phoneNumber;
}
