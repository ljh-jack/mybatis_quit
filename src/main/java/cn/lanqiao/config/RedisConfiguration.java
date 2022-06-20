package cn.lanqiao.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author ljh
 * @version 1.0
 * @date 2022/6/13 12:02
 */
@PropertySource("classpath:redis.properties")
@Configuration
public class RedisConfiguration {
    @Value("${redis.host}")
    private String host;
    @Value("${redis.port}")
    private int port;
    @Value("${redis.timeout}")
    private int timeout;
    @Value("${redis.password}")
    private String password;
    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        //自定义Jackson序列化配置
        Jackson2JsonRedisSerializer jsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jsonRedisSerializer.setObjectMapper(objectMapper);
        //key使用String的序列化方式
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringRedisSerializer);
        //hash的key也是用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        //value的key使用jackson的序列化方式
        template.setValueSerializer(jsonRedisSerializer);
        //hash的value也是用jackson的序列化方式
        template.setHashValueSerializer(jsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisConnectionFactory factory(JedisPoolConfig jedisPoolConfig) {
        JedisConnectionFactory redis = new JedisConnectionFactory();
        redis.setHostName(host);
        redis.setPort(port);
        redis.setTimeout(timeout);
        redis.setPassword(password);
        redis.setPoolConfig(jedisPoolConfig);
        return redis;
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        //redisURL 与 redisPort 的配置文件
        JedisPoolConfig config = new JedisPoolConfig();
        //设置最大连接数（100个足够用了，没必要设置太大）
        config.setMaxTotal(20);
        //最大空闲连接数
        config.setMaxIdle(5);
        //获取Jedis连接的最大等待时间（50秒）
        config.setMaxWaitMillis(50 * 1000);
        //在获取Jedis连接时，自动检验连接是否可用
        config.setTestOnBorrow(true);
        //在将连接放回池中前，自动检验连接是否有效
        config.setTestOnReturn(true);
        //自动测试池中的空闲连接是否都是可用连接
        config.setTestWhileIdle(true);
        return config;
    }

}