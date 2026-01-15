package org.gaurav.notification.dto;

import lombok.Data;
import org.gaurav.notification.type.ChannelType;

import java.util.List;

@Data
public class Notification {
    private String notificationId;
    private String type;
    private Recipient recipient;
    private List<ChannelType> channels;
    private Object payload;
}
