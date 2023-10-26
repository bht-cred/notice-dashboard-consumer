package com.credgenics.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProduceMessage {

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Bean
    public void sendMessage() {
        System.out.println("PRODUCE");
//        kafkaTemplate.send("bht_test_1","data_23_oct_3");
    }

}
