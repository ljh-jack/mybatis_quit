package cn.lanqiao.common.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.multipart.MultipartFile;

@PropertySource("classpath:system.properties")
public class UpdateUtils {

    @Value("defaultImg")
    public static String DEFAUlT_IMG = "/static/images/1518162958511.jpeg";

    public static String upload(MultipartFile file) {
        return "";
    }
}
