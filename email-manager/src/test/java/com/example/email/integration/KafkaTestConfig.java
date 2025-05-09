package com.example.email.integration;

import com.example.email.dto.UserEventDTO;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
public class KafkaTestConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserEventDTO> kafkaListenerContainerFactory(
            ConsumerFactory<String, UserEventDTO> consumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserEventDTO> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, UserEventDTO> consumerFactory(EmbeddedKafkaBroker embeddedKafkaBroker) {
        Map<String, Object> props = new HashMap<>(KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker));
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(UserEventDTO.class, false)
        );
    }
}
