/*
 * package com.cognizant.ims.common;
 * 
 * import org.springframework.beans.factory.annotation.Value; import
 * org.springframework.cache.annotation.EnableCaching; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.data.redis.cache.RedisCacheConfiguration; import
 * org.springframework.data.redis.cache.RedisCacheManager; import
 * org.springframework.data.redis.connection.RedisStandaloneConfiguration;
 * import
 * org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
 * import org.springframework.data.redis.core.RedisTemplate; import
 * org.springframework.data.redis.serializer.RedisSerializationContext; import
 * org.springframework.data.redis.serializer.RedisSerializer;
 * 
 * @Configuration //@EnableCaching public class RedisConfiguration {
 * 
 * @Value("${redis.hostname:localhost}") private String redisHostName;
 * 
 * @Value("${redis.port:6379}") private int redisPort;
 * 
 * @Bean public LettuceConnectionFactory redisConnectionFactory() { return new
 * LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHostName,
 * redisPort)); }
 * 
 * @Bean public RedisTemplate<Object, Object> redisTemplate() {
 * RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<Object,
 * Object>(); redisTemplate.setConnectionFactory(redisConnectionFactory());
 * return redisTemplate; }
 * 
 * @Bean public RedisCacheManager redisCacheManager(LettuceConnectionFactory
 * lettuceConnectionFactory) {
 *//**
	 * If we want to use JSON Serialized then use the below config snippet
	 */
/*
 * // RedisCacheConfiguration redisCacheConfiguration =
 * RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues() //
 * .entryTtl(Duration.ofHours(redisDataTTL)) //
 * .serializeValuesWith(RedisSerializationContext.SerializationPair.
 * fromSerializer(RedisSerializer.json()));
 * 
 *//**
	 * If we want to use JAVA Serialized then use the below config snippet
	 *//*
		 * 
		 * RedisCacheConfiguration redisCacheConfiguration =
		 * RedisCacheConfiguration.defaultCacheConfig()
		 * .disableCachingNullValues().serializeValuesWith(
		 * RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.
		 * java()));
		 * 
		 * redisCacheConfiguration.usePrefix();
		 * 
		 * return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(
		 * lettuceConnectionFactory) .cacheDefaults(redisCacheConfiguration).build();
		 * 
		 * }
		 * 
		 * }
		 */