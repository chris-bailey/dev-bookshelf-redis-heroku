package com.chrisbaileydeveloper.bookshelf.config;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.chrisbaileydeveloper.bookshelf.domain.Book;

@Configuration
public class DatabaseConfiguration {

	@Inject
	private JedisConnectionFactory jedisConnFactory;
	
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
    	StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
    	return stringRedisSerializer;
    }
    
    @Bean
    public JacksonJsonRedisSerializer<Book> jacksonJsonRedisJsonSerializer() {
    	JacksonJsonRedisSerializer<Book> jacksonJsonRedisJsonSerializer = new JacksonJsonRedisSerializer<>(Book.class);
    	return jacksonJsonRedisJsonSerializer;
    }
    
	@Bean
	public RedisTemplate<String, Book> redisTemplate() {
		RedisTemplate<String, Book> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnFactory);
		redisTemplate.setKeySerializer(stringRedisSerializer());
		redisTemplate.setValueSerializer(jacksonJsonRedisJsonSerializer());
		return redisTemplate;
	}
}
