package com.chrisbaileydeveloper.bookshelf.config;


import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import redis.clients.jedis.Protocol;

@Configuration
@Profile(Constants.SPRING_PROFILE_PRODUCTION)
public class ProdDatabaseConfiguration {
	
	@Bean
	public JedisConnectionFactory jedisConnFactory() {
		
		try {
			String redistogoUrl = System.getenv("REDISTOGO_URL");
			URI redistogoUri = new URI(redistogoUrl);

			JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();

			jedisConnFactory.setUsePool(true);
			jedisConnFactory.setHostName(redistogoUri.getHost());
			jedisConnFactory.setPort(redistogoUri.getPort());
			jedisConnFactory.setTimeout(Protocol.DEFAULT_TIMEOUT);
			jedisConnFactory.setPassword(redistogoUri.getUserInfo().split(":", 2)[1]);

			return jedisConnFactory;

		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
}
