//package cn.lanqiao.config;
//
//import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
//import com.mchange.v2.c3p0.ComboPooledDataSource;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionTemplate;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//
//import java.beans.PropertyVetoException;
//import java.sql.SQLException;
//
//@Configuration
//@MapperScan(basePackages = {"cn.lanqiao.dao"})
//public class MybatisPlusConfig {
//
//
//    @Value("${jdbc.driver}")
//    private String driver;
//    @Value("${jdbc.url}")
//    private String url;
//    @Value("${jdbc.username}")
//    private String userName;
//    @Value("${jdbc.password}")
//    private String password;
//
//    /**
//     * 获取数据库的数据源
//     * @return
//     * @throws PropertyVetoException
//     * @throws SQLException
//     */
//    @Bean("dataSource")//将返回值添加到容器
//    public ComboPooledDataSource getDataSource() throws PropertyVetoException {
//        //创建数据源
//        ComboPooledDataSource dataSource = new ComboPooledDataSource();
//        //设置数据库连接参数
//        dataSource.setDriverClass(driver);
//        dataSource.setJdbcUrl(url);
//        dataSource.setUser(userName);
//        dataSource.setPassword(password);        //获得连接对象
//        return dataSource;
//    }
//
//    @Bean
//    public SqlSessionTemplate sqlSessionTemplate() throws Exception {
//        return new SqlSessionTemplate(sqlSessionFactory());
//    }
//
//
//
//    //使用 SqlSessionFactoryBean来创建 SqlSessionFactory。并配置这个工厂bean
//    @Bean
//    public SqlSessionFactory sqlSessionFactory() throws Exception {
//        MybatisSqlSessionFactoryBean factoryBean = new MybatisSqlSessionFactoryBean();
//        factoryBean.setDataSource(getDataSource());
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        factoryBean.setConfigLocation(new ClassPathResource("applicationContext-mybatis.xml"));
//        factoryBean.setMapperLocations(resolver.getResources("classpath:/mapper/*.xml"));
//        return factoryBean.getObject();
//    }
//
//    //事物支持
//    @Bean
//    public DataSourceTransactionManager transactionManager() throws PropertyVetoException {
//        return new DataSourceTransactionManager(getDataSource());
//    }
//}
