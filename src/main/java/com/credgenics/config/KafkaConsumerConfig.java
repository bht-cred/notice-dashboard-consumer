package com.credgenics.config;

import com.credgenics.kafkaproject.KafkaProjectApplication;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.protocol.types.Field;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;


@Configuration
public class KafkaConsumerConfig {

    Logger logger = LoggerFactory.getLogger(KafkaProjectApplication.class);

    @Value("${spring.kafka.bootstrap.servers}")
    private String bootstrapServer;

    public HashMap<String, Object> consumerConfig(){

        HashMap<String,Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        logger.info(">>>logger setup successful!");
        logger.debug(">>>logger setup successful!");
        return props;
    }

    @Bean
    public ConsumerFactory<String,String> consumerFactory(){
        System.out.println("Consumer Config");
        HashMap<String,Object> consumerConfig1 = consumerConfig();
        System.out.println(consumerConfig1);
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,String>> factory(ConsumerFactory<String,String> consumerFactory){
        ConcurrentKafkaListenerContainerFactory<String,String> factory = new ConcurrentKafkaListenerContainerFactory();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    // Creating a Listener
//    public ConcurrentKafkaListenerContainerFactory
//    concurrentKafkaListenerContainerFactory()
//    {
//        ConcurrentKafkaListenerContainerFactory<
//                String, String> factory
//                = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(consumerFactory());
//        return factory;
//    }
}
