package org.gaurav.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.gaurav.notification.dto.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Slf4j
@Component
public class NotificationConsumer {
    private final NotificationDispatcher notificationDispatcher;

    public NotificationConsumer(NotificationDispatcher notificationDispatcher) {
        this.notificationDispatcher = notificationDispatcher;
    }

    @KafkaListener(topics = "${app.kafka.topic.notifications}")
    public void consume(Notification notification, Acknowledgment ack) {
        String notificationId = notification.getNotificationId();
        JsonMapper jsonMapper = new JsonMapper();
        String n = jsonMapper.writeValueAsString(notification);
        log.info("Received notification event: {}", n);

        try {
            notificationDispatcher.dispatch(notification);
            ack.acknowledge(); // offset committed here
        } catch (Exception e) {
            log.error("Failed to process notification {}", notificationId, e);
            throw e; // let Kafka retry
        }
    }
}
