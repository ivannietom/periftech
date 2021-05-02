package es.code.urjc.periftech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
public class PeriftechApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeriftechApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("categorias");
	}
	
}
