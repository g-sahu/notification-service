package org.gaurav.notification.service;

import org.gaurav.notification.dto.Notification;
import org.gaurav.notification.type.ChannelType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

@Component
public class NotificationDispatcher {
    private final Map<ChannelType, NotificationChannel> channelMap;

    public NotificationDispatcher(List<NotificationChannel> channelList) {
        this.channelMap = channelList.stream()
                                     .collect(toMap(NotificationChannel::type, c -> c));
    }

    public void dispatch(Notification notification) {
        notification.getChannels()
                    .stream()
                    .map(channelMap :: get)
                    .forEach(notificationChannel -> notificationChannel.send(notification));
    }
}
