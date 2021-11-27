package org.project9.shipping.kafka;

import com.google.gson.Gson;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.project9.shipping.data.ShippingCreateRequest;
import org.project9.shipping.data.ShippingUpdateInvoicing;
import org.project9.shipping.data.ShippingUpdateRequest;
import org.project9.shipping.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerShippings {

    @Autowired
    ShippingService service;

    @KafkaListener(topics = "${topicOrders}", groupId = "${kafkaGroup}")
    public void listenShippingTopic(ConsumerRecord<String, String> record) {
        String message = record.value();
        String key = record.key();
        if(message != null && !message.isEmpty()) {
            System.out.println(message);
            if(key.equals("order_validation")) {
                ShippingUpdateRequest updateStatus = new Gson().fromJson(message, ShippingUpdateRequest.class);
                service.updateStatus(updateStatus);
            }
            else if(key.equals("order_completed")) {
                ShippingCreateRequest createShipping = new Gson().fromJson(message, ShippingCreateRequest.class);
                service.addShipping(createShipping);
            }
        }
    }

    @KafkaListener(topics = "${topicInvoicing}", groupId = "${kafkaGroup}")
    public void listenShippingInvoice(ConsumerRecord<String, String> record) {
        String message = record.value();
        String key = record.key();
        if(message != null && !message.isEmpty()) {
            System.out.println(message);
            if(key.equals("order_paid")) {
                ShippingUpdateInvoicing updateStatus = new Gson().fromJson(message, ShippingUpdateInvoicing.class);
                service.updateStatusInvoicing(updateStatus);
            }
        }
    }

}
