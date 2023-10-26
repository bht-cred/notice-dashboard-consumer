package com.credgenics.kafkaproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import java.util.HashMap;

@ComponentScan(basePackages = "com.credgenics.*")
@SpringBootApplication
public class KafkaProjectApplication {

	Logger logger = LoggerFactory.getLogger(KafkaProjectApplication.class);

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

//			kafkaTemplate.send("bht_test_1","data_23_oct_1");
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
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return args -> {
//			testing message
			HashMap<String,Object> ass = new HashMap<String,Object>();
			ass.put("company_id","9164f205-1b0f-41ee-b05e-1e21464cbcb6");
			ass.put("notice_type","bht");
			ass.put("allocation_month","2023-10-01");
//			ass.put("physical_notice_type","bht_type_1");
			ass.put("x-cg-user","{\"user1\":123}");
			ass.put("x-cg-company","{\"company_id\":\"9164f205-1b0f-41ee-b05e-1e21464cbcb6\"}");
			String json = new ObjectMapper().writeValueAsString(ass);
			System.out.println(json);
			kafkaTemplate.send("bht_test_1",json);
		};
	}

}
