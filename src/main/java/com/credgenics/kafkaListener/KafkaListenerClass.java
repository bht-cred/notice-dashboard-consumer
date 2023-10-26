package com.credgenics.kafkaListener;

import com.credgenics.service.DashboardMessageListenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.stereotype.Component;
import org.springframework.kafka.annotation.KafkaListener;

@Component
public class KafkaListenerClass {

    @Autowired
    DashboardMessageListenerService dashboardMessageListenerService;

    @KafkaListener(
            topics = "bht_test_1",
            groupId = "group1",
            containerFactory = "factory"
    )
    void listener(String data){
        System.out.println("Listener" + data + " !");
        dashboardMessageListenerService.executePhysicalDashboardService(data);
    }
}
