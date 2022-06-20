package cn.lanqiao.vo.home;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
//解决获取配置文件中文乱码
@PropertySource(value="classpath:system.properties",encoding="utf-8")
@Component
public class HomeInfo {
    @Value("${home_info_title}")
    private String title;
    @Value("${home_info_href}")
    private String href;
}
