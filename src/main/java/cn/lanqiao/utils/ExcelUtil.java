package cn.lanqiao.utils;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.lanqiao.entity.UserEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/16 23:52
 */
public class ExcelUtil {
    private static List<UserEntity> uploadFileList;
    /**
     * 导入文件
     *
     * @param uploadFile 文件
     * @return
     */
    public static List<UserEntity> uploadFile(MultipartFile uploadFile) {
//将MultipartFile转换为InputStream流
        InputStream inputStream = null;
        try {
            inputStream = uploadFile.getInputStream();
            ImportParams params = new ImportParams();
//表格标题(title)所占的行数
            params.setTitleRows(1);
//表格字段名所占的行数
            params.setHeadRows(1);
//参数1：文件流 参数2：导入类型，即导入数据所对应的实体类型 参数3：导入的配置对象
                    uploadFileList = ExcelImportUtil.importExcel(inputStream,
                    UserEntity.class, params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uploadFileList;
    }
    /**
     * 导出 Excel (下载)
     * @param response Web 请求
     * @param list 需要导出的数据集合
     * @param title 文件表头
     * @param sheetName 工作表名
     * @param fileName 文件名
     */
    public static void exportExcel(HttpServletResponse response,
                                   List<UserEntity> list, String title, String sheetName, String fileName) {
        ServletOutputStream os = null;
//生成Excel
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(title,
                sheetName), UserEntity.class, list);
        try {
//设置请求头
            response.setHeader("content-disposition", "attachment:fileName=" + URLEncoder.encode(fileName, "UTF-8"));
//获取字节输出流
            os = response.getOutputStream();
//将文件写入到指定输出流中
            workbook.write(os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
//释放资源
            try {
                if (os != null) os.close();
                if (workbook != null) workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
