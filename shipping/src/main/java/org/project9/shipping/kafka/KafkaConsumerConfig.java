package org.project9.shipping.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServer;

    @Value("${topicOrders}")
    private String topicOrders;

    @Value("${topicInvoicing}")
    private String topicInvoicing;

    @Value("${kafkaGroup}")
    private String consumerGroup;

    @Bean
    public Map<String, Object> orderConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                consumerGroup);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return props;
    }

    @Bean
    public Map<String, Object> invoicingConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServer);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                consumerGroup);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, String> orderFactory() {
        return new DefaultKafkaConsumerFactory<>(orderConfigs());
    }

    @Bean
    public ConsumerFactory<String, String> invoicingFactory() {
        return new DefaultKafkaConsumerFactory<>(invoicingConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryOrder() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderFactory());
        factory.setRecordFilterStrategy(record ->
                !record.key().equals("order_completed") && !record.key().equals("order_validation"));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactoryInvoicing() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(invoicingFactory());
        factory.setRecordFilterStrategy(record ->
                !record.key().equals("order_paid"));
        return factory;
    }

}
