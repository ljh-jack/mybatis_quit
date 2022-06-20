package cn.lanqiao.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.lanqiao.entity.UserEntity;
import cn.lanqiao.security.UserSecrity;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.core.userdetails.User;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/17 10:20
 */
public class Test {
        public static void main(String[] args) throws IOException {
                List<UserEntity> user = new ArrayList<>();
                //此处模拟五条数据
                for(int i=0;i<1;i++) {
                        UserEntity userEntity = new UserEntity();
                        userEntity.setDeptId("105");
                        userEntity.setEmail("31432524542");
                        userEntity.setLoginName("ljh");
                        userEntity.setRemark("刘备");
                        userEntity.setUserType("1");
                        userEntity.setSex("男");
                        userEntity.setUserName("小王");
                        userEntity.setUserType("1");
                        userEntity.setPhoneNumber("1234534");
                        user.add(userEntity);
                }
                FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\yy\\Desktop\\用户表.xls");
                Workbook exportExcel = ExcelExportUtil.exportExcel(new ExportParams("历年数据导入", "数据表"), UserEntity.class, user);
                exportExcel.write(fileOutputStream);
                fileOutputStream.close();
                exportExcel.close();
        }
}
