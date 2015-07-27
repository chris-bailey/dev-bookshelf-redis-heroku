package com.chrisbaileydeveloper.bookshelf.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@Profile(Constants.SPRING_PROFILE_DEVELOPMENT)
public class DevDatabaseConfiguration {
	
    @Bean
    public JedisConnectionFactory jedisConnFactory() {
		JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
        return jedisConnFactory;
    }
}
