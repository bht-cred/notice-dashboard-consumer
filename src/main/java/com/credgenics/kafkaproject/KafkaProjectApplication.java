package com.credgenics.kafkaproject;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;

@ComponentScan(basePackages = "com.credgenics.*")
@SpringBootApplication
public class KafkaProjectApplication {

	@Autowired
	KafkaTemplate kafkaTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public static void main(String[] args) {
		SpringApplication.run(KafkaProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(KafkaTemplate<String,String> kafkaTemplate){
		System.out.println("PRODUCING MESSAGE");
		try {

			kafkaTemplate.send("bht_test_1","data_23_oct_1");
			System.out.println("Sent");


			String sql = "INSERT INTO case_links (batch_id) VALUES ("
					+ "'55fc6412-8643-45e4-ae32-3941bc0f326f')";

			int rows = jdbcTemplate.update(sql);
			if (rows > 0) {
				System.out.println("A new row has been inserted.");
			}

			String redisValue = redisTemplate.opsForValue().get("bht_1");
			System.out.println("redisValue : bht_1 " + redisValue);

			redisTemplate.opsForValue().set("bht_2","spring boot");
			System.out.println("redisValue : bht_2 value set");

		} catch (Exception e) {
			System.out.println("Exception");
		}
		return args -> {
			kafkaTemplate.send("bht_test_1","data_23_oct_2");
		};
	}

}
