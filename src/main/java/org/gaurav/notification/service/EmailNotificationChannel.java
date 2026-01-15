package org.gaurav.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.gaurav.notification.dto.Notification;
import org.gaurav.notification.type.ChannelType;
import org.springframework.stereotype.Component;

import static org.gaurav.notification.type.ChannelType.EMAIL;

@Slf4j
@Component
public class EmailNotificationChannel implements NotificationChannel {

    @Override
    public ChannelType type() {
        return EMAIL;
    }

    @Override
    public void send(Notification notification) {
        // use JavaMailSender
        log.info("Sending EMAIL to {}", notification.getRecipient().getEmail());
    }
}
