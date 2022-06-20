package cn.lanqiao.vo.home;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@PropertySource(value="classpath:system.properties",encoding="utf-8")
@Component
public class LogoInfo {
    @Value("${logo_info_title}")
    private String title;
    @Value("${logo_info_image}")
    private String image;
    @Value("${logo_info_href}")
    private String href;
}
