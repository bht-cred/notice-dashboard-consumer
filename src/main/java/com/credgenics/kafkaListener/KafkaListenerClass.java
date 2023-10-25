package com.credgenics.kafkaListener;

import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class KafkaListenerClass {

    @KafkaListener(
            topics = "bht_test_1",
            groupId = "group1",
            containerFactory = "factory"
    )
    void listener(String data){
        System.out.println("Listener" + data + " !");
    }
}
