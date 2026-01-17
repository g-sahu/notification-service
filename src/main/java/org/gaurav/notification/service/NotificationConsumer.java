package org.gaurav.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.gaurav.notification.dto.Notification;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

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
        log.info("Received notification event [id={}, channels={}]", notificationId, notification.getChannels());

        try {
            notificationDispatcher.dispatch(notification);
            ack.acknowledge(); // offset committed here
        } catch (Exception e) {
            log.error("Failed to process notification {}", notificationId, e);
            throw e; // let Kafka retry
        }
    }
}
