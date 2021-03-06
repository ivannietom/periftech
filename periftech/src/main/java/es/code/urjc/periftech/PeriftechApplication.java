package es.code.urjc.periftech;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.session.hazelcast.config.annotation.web.http.EnableHazelcastHttpSession;

import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;

@EnableCaching
@SpringBootApplication
@EnableHazelcastHttpSession
public class PeriftechApplication {

	public static void main(String[] args) {
		SpringApplication.run(PeriftechApplication.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager("categorias");
	}
	
	@Bean
	 public Config config() {
	 Config config = new Config();
	 JoinConfig joinConfig = config.getNetworkConfig().getJoin();
	 joinConfig.getMulticastConfig().setEnabled(false);
	 
	 List<String> serversList = new ArrayList<String>();
     serversList.add("periftech1");
     serversList.add("periftech2");
	 
	 joinConfig.getTcpIpConfig().setEnabled(true).setMembers(serversList);
	 return config;
	 }

}
