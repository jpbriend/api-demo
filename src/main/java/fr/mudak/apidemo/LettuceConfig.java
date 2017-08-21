package fr.mudak.apidemo;

import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisURI;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.resource.ClientResources;
import com.lambdaworks.redis.resource.DefaultClientResources;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LettuceConfig {

    @Value("${redisHost}")
    private String redisHost;

    @Value("${redisPort}")
    private String redisPort;

    @Value("${redisPassword}")
    private String redisPassword;


    @Bean(destroyMethod = "shutdown")
    ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean(destroyMethod = "shutdown")
    RedisClient redisClient(ClientResources clientResources) {

        return RedisClient.create(clientResources, RedisURI.Builder.redis(redisHost, Integer.parseInt(redisPort)).withPassword(redisPassword).withDatabase(1).build());
    }

    @Bean(destroyMethod = "close")
    StatefulRedisConnection<String, String> connection(RedisClient redisClient) {
        return redisClient.connect();
    }
}
