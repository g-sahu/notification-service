package org.gaurav.notification.service;

import org.gaurav.notification.dto.Notification;
import org.gaurav.notification.type.ChannelType;

public interface NotificationChannel {

    ChannelType type();
    void send(Notification notification);
}
